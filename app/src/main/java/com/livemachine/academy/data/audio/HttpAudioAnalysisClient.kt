package com.livemachine.academy.data.audio

import com.livemachine.academy.data.assistant.ApiErrorBody
import com.livemachine.academy.data.services.AudioAnalyzer
import com.livemachine.academy.domain.result.AppError
import com.livemachine.academy.domain.result.AppResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/** Envoie l'extrait (WAV en base64) au backend d'analyse ; l'audio n'est envoyé qu'à la demande de l'utilisateur. */
class HttpAudioAnalysisClient(
    private val baseUrl: String,
    private val connectTimeoutMs: Int = 15_000,
    private val readTimeoutMs: Int = 120_000,
) : AudioAnalyzer {
    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true; explicitNulls = false }

    override suspend fun analyze(request: AudioAnalysisRequest): AppResult<AudioAnalysisResult> = withContext(Dispatchers.IO) {
        val connection = try {
            URL(baseUrl.trimEnd('/') + "/v1/audio-analysis").openConnection() as HttpURLConnection
        } catch (e: IOException) {
            return@withContext AppResult.Failure(AppError.Offline)
        }
        try {
            val payload = json.encodeToString(AudioAnalysisRequest.serializer(), request).toByteArray()
            connection.requestMethod = "POST"
            connection.connectTimeout = connectTimeoutMs
            connection.readTimeout = readTimeoutMs
            connection.doOutput = true
            connection.setFixedLengthStreamingMode(payload.size)
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
            connection.outputStream.use { it.write(payload) }
            val code = connection.responseCode
            val body = (if (code in 200..299) connection.inputStream else connection.errorStream)
                ?.bufferedReader()?.use { it.readText() }.orEmpty()
            if (code in 200..299) {
                val result = runCatching { json.decodeFromString(AudioAnalysisResult.serializer(), body) }.getOrNull()
                    ?: return@withContext AppResult.Failure(AppError.Unexpected("Réponse illisible du serveur."))
                if (result.summary.isEmpty()) return@withContext AppResult.Failure(AppError.Unexpected("Aucune mesure reçue."))
                AppResult.Success(result)
            } else {
                val message = runCatching { json.decodeFromString(ApiErrorBody.serializer(), body).error?.message }.getOrNull().orEmpty()
                AppResult.Failure(
                    when (code) {
                        429 -> AppError.RateLimited
                        in 400..499 -> AppError.Rejected(message.ifBlank { "Extrait refusé par le serveur." })
                        else -> AppError.Unexpected(message.ifBlank { "Analyse momentanément indisponible." })
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
