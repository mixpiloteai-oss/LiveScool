package com.livemachine.academy.data.commerce

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.livemachine.academy.data.assistant.ApiErrorBody
import com.livemachine.academy.domain.commerce.Entitlements
import com.livemachine.academy.domain.result.AppError
import com.livemachine.academy.domain.result.AppResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/** Source des droits. Le plan affiché et appliqué vient uniquement d'un jeton serveur vérifié. */
interface EntitlementRepository {
    val entitlements: Flow<Entitlements>

    /** Détails pour l'écran Premium (usage, abonnement, limites) — affichage seulement, jamais pour décider du plan. */
    val details: Flow<EntitlementResponse?> get() = flowOf(null)

    suspend fun refresh(): AppResult<Unit> = AppResult.Failure(AppError.NotAvailable)

    /** Envoie un achat Google Play au serveur, qui le vérifie auprès de Google avant d'accorder Premium. */
    suspend fun verifyPurchase(purchaseToken: String): AppResult<Unit> = AppResult.Failure(AppError.NotAvailable)

    /** Oublie le jeton local (après réinitialisation de l'identifiant d'installation). */
    suspend fun clearLocal() {}
}

/** Sans serveur configuré : toujours l'offre gratuite. */
class FreeEntitlementRepository : EntitlementRepository {
    override val entitlements: Flow<Entitlements> = flowOf(Entitlements.Free)
}

/**
 * Droits vérifiés par le serveur. Le dernier jeton reçu est gardé pour l'usage hors ligne, mais il est
 * revérifié (signature, installation, expiration ≤ 72 h) à chaque lecture : une valeur locale modifiée
 * ou périmée redonne l'offre gratuite.
 */
class RemoteEntitlementRepository(
    private val dataStore: DataStore<Preferences>,
    private val installIds: InstallIdRepository,
    private val baseUrl: String,
    private val publicKey: String,
    private val clock: () -> Long = System::currentTimeMillis,
) : EntitlementRepository {
    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }

    override val details: Flow<EntitlementResponse?> = dataStore.data.map { prefs ->
        prefs[RESPONSE]?.let { runCatching { json.decodeFromString(EntitlementResponse.serializer(), it) }.getOrNull() }
    }

    override val entitlements: Flow<Entitlements> = combine(details, flow { emit(installIds.get()) }) { response, installId ->
        response?.let { EntitlementVerifier.verify(it.token, publicKey, installId, clock()) } ?: Entitlements.Free
    }

    override suspend fun refresh(): AppResult<Unit> = call("GET", "/v1/entitlement", null)

    override suspend fun verifyPurchase(purchaseToken: String): AppResult<Unit> =
        call("POST", "/v1/billing/verify", """{"purchaseToken":${JsonPrimitive(purchaseToken)}}""")

    private suspend fun call(method: String, path: String, body: String?): AppResult<Unit> = withContext(Dispatchers.IO) {
        val installId = installIds.get()
        val connection = try {
            URL(baseUrl.trimEnd('/') + path).openConnection() as HttpURLConnection
        } catch (e: IOException) {
            return@withContext AppResult.Failure(AppError.Offline)
        }
        try {
            connection.requestMethod = method
            connection.connectTimeout = 15_000
            connection.readTimeout = 30_000
            connection.setRequestProperty("X-LMA-Install", installId)
            connection.setRequestProperty("Accept", "application/json")
            if (body != null) {
                connection.doOutput = true
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
                connection.outputStream.use { it.write(body.toByteArray()) }
            }
            val code = connection.responseCode
            val text = (if (code in 200..299) connection.inputStream else connection.errorStream)
                ?.bufferedReader()?.use { it.readText() }.orEmpty()
            if (code !in 200..299) {
                val message = runCatching { json.decodeFromString(ApiErrorBody.serializer(), text).error?.message }.getOrNull().orEmpty()
                return@withContext AppResult.Failure(
                    if (code in 400..499) AppError.Rejected(message.ifBlank { "Demande refusée." })
                    else AppError.Unexpected(message.ifBlank { "Serveur indisponible." }),
                )
            }
            val response = runCatching { json.decodeFromString(EntitlementResponse.serializer(), text) }.getOrNull()
                ?: return@withContext AppResult.Failure(AppError.Unexpected("Réponse illisible."))
            // On ne garde que des réponses dont le jeton est valide pour CETTE installation.
            if (EntitlementVerifier.verify(response.token, publicKey, installId, clock()) == null) {
                return@withContext AppResult.Failure(AppError.Unexpected("Jeton de droits invalide."))
            }
            dataStore.edit { it[RESPONSE] = json.encodeToString(EntitlementResponse.serializer(), response) }
            AppResult.Success(Unit)
        } catch (e: IOException) {
            AppResult.Failure(AppError.Offline)
        } finally {
            connection.disconnect()
        }
    }

    override suspend fun clearLocal() {
        dataStore.edit { it.remove(RESPONSE) }
    }

    private companion object {
        val RESPONSE = stringPreferencesKey("entitlement_response")
    }
}
