package com.livemachine.academy.data.assistant

import com.livemachine.academy.data.services.MusicAssistant
import com.livemachine.academy.domain.result.AppError
import com.livemachine.academy.domain.result.AppResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/**
 * Client HTTP du backend de l'assistant (sans dépendance : HttpURLConnection + kotlinx.serialization).
 * Le fournisseur IA et sa clé sont côté serveur ; l'app ne connaît que [baseUrl].
 */
class HttpAssistantClient(
    private val baseUrl: String,
    private val connectTimeoutMs: Int = 15_000,
    private val readTimeoutMs: Int = 90_000,
    /** Identifiant d\'installation envoyé au serveur pour les quotas (en-tête X-LMA-Install). */
    private val installId: suspend () -> String? = { null },
) : MusicAssistant {

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true; explicitNulls = false }

    override suspend fun ask(request: AssistantRequest): AppResult<String> = withContext(Dispatchers.IO) {
        val connection = try {
            (URL(baseUrl.trimEnd('/') + "/v1/assistant").openConnection() as HttpURLConnection)
        } catch (e: IOException) {
            return@withContext AppResult.Failure(AppError.Offline)
        }
        try {
            connection.requestMethod = "POST"
            connection.connectTimeout = connectTimeoutMs
            connection.readTimeout = readTimeoutMs
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
            installId()?.let { connection.setRequestProperty("X-LMA-Install", it) }
            connection.setRequestProperty("Accept", "application/json")
            connection.outputStream.use { it.write(json.encodeToString(AssistantRequest.serializer(), request).toByteArray()) }
            val code = connection.responseCode
            val body = (if (code in 200..299) connection.inputStream else connection.errorStream)
                ?.bufferedReader()?.use { it.readText() }.orEmpty()
            if (code in 200..299) {
                val reply = runCatching { json.decodeFromString(AssistantReply.serializer(), body) }.getOrNull()
                    ?: return@withContext AppResult.Failure(AppError.Unexpected("Réponse illisible du serveur."))
                if (reply.reply.isBlank()) return@withContext AppResult.Failure(AppError.Unexpected("Réponse vide."))
                AppResult.Success(reply.reply)
            } else {
                val apiError = runCatching { json.decodeFromString(ApiErrorBody.serializer(), body).error }.getOrNull()
                if (code == 429 && apiError?.code == "quota_exceeded") {
                    return@withContext AppResult.Failure(AppError.QuotaExceeded(apiError.message))
                }
                val message = runCatching { apiError?.message }
                    .getOrNull().orEmpty()
                AppResult.Failure(
                    when (code) {
                        429 -> AppError.RateLimited
                        in 400..499 -> AppError.Rejected(message.ifBlank { "Question refusée." })
                        else -> AppError.Unexpected(message.ifBlank { "Assistant momentanément indisponible." })
                    },
                )
            }
        } catch (e: IOException) {
            AppResult.Failure(AppError.Offline)
        } finally {
            connection.disconnect()
        }
    }
}
