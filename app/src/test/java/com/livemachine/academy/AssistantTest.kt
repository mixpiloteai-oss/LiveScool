package com.livemachine.academy

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.livemachine.academy.data.assistant.AssistantHistoryRepository
import com.livemachine.academy.data.assistant.AssistantRequest
import com.livemachine.academy.data.assistant.ChatMessage
import com.livemachine.academy.data.assistant.HttpAssistantClient
import com.livemachine.academy.data.assistant.MachineInfo
import com.livemachine.academy.data.content.MachineCatalog
import com.livemachine.academy.data.model.UserProgress
import com.livemachine.academy.domain.assistant.AssistantContextBuilder
import com.livemachine.academy.domain.result.AppError
import com.livemachine.academy.domain.result.AppResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import java.net.ServerSocket
import java.nio.file.Files

class AssistantTest {
    private val ct = MachineCatalog.circuitTracks
    private val electribe = MachineCatalog.electribe

    // --- Contexte envoyé à l'IA -------------------------------------------------

    @Test
    fun `le contexte commence par la lecon ouverte avec ses pages du manuel`() {
        val lesson = ct.lessons.first { it.id == "ct-m6-l2" }
        val req = AssistantContextBuilder.build(ct, lesson, emptyList(), "Comment varier mes charleys ?", UserProgress())
        assertEquals("Novation Circuit Tracks", req.machine.name)
        assertEquals("ct-m6-l2", req.lesson?.id)
        assertEquals(lesson.title, req.context.first().title)
        assertTrue(req.context.first().ref.contains("Guide Circuit Tracks"))
        assertTrue(req.context.first().ref.contains("48"))
        assertTrue(req.context.size <= 1 + AssistantContextBuilder.RELATED_MAX)
        assertTrue(req.context.all { it.text.length <= AssistantContextBuilder.SNIPPET_MAX_CHARS })
    }

    @Test
    fun `recherche des lecons pertinentes limitee a la machine choisie`() {
        val found = AssistantContextBuilder.relevantLessons(ct, "Comment régler le sidechain sur ma basse ?")
        assertTrue(found.map { it.id }.toString(), found.any { it.id == "ct-m9-l3" })
        assertTrue(found.all { it.id.startsWith("ct-") })
        assertTrue(AssistantContextBuilder.relevantLessons(electribe, "sidechain").none { it.id.startsWith("ct-") })
        assertTrue(AssistantContextBuilder.relevantLessons(ct, "?").isEmpty())
    }

    @Test
    fun `historique et message limites pour economiser les jetons`() {
        val history = (1..20).map { ChatMessage(if (it % 2 == 0) "assistant" else "user", "m$it") }
        val req = AssistantContextBuilder.build(ct, null, history, "x".repeat(900), UserProgress())
        assertEquals(AssistantContextBuilder.HISTORY_MAX, req.history.size)
        assertEquals("m20", req.history.last().text)
        assertEquals(AssistantContextBuilder.MESSAGE_MAX_CHARS, req.message.length)
        assertEquals(null, req.lesson)
    }

    @Test
    fun `niveau deduit de la progression de la machine`() {
        assertEquals("débutant", AssistantContextBuilder.level(ct, UserProgress()))
        val half = UserProgress(completedLessons = ct.lessons.take(ct.lessons.size / 2).map { it.id }.toSet())
        assertEquals("intermédiaire", AssistantContextBuilder.level(ct, half))
        assertEquals("avancé", AssistantContextBuilder.level(ct, UserProgress(completedLessons = ct.lessons.map { it.id }.toSet())))
        // La progression Electribe ne compte pas pour la Circuit Tracks
        assertEquals("débutant", AssistantContextBuilder.level(ct, UserProgress(completedLessons = electribe.lessons.map { it.id }.toSet())))
    }

    // --- Client HTTP ------------------------------------------------------------

    private val request = AssistantRequest(MachineInfo("circuit-tracks", "Novation Circuit Tracks"), null, "débutant", emptyList(), emptyList(), "Bonjour")

    private fun <T> withServer(status: Int, body: String, block: (String, () -> String) -> T): T = withHttpServer(status, body, block)

    @Test
    fun `reponse du backend`() = withServer(200, """{"reply":"Salut !","truncated":false,"extra":1}""") { url, received ->
        val result = runBlocking { HttpAssistantClient(url).ask(request) }
        assertEquals(AppResult.Success("Salut !"), result)
        assertTrue(received().contains("\"machine\":{\"id\":\"circuit-tracks\""))
        assertTrue(received().contains("\"message\":\"Bonjour\""))
    }

    @Test
    fun `erreurs du backend traduites`() {
        withServer(429, """{"error":{"code":"rate_limited","message":"Trop"}}""") { url, _ ->
            assertEquals(AppResult.Failure(AppError.RateLimited), runBlocking { HttpAssistantClient(url).ask(request) })
        }
        withServer(429, """{"error":{"code":"quota_exceeded","message":"Limite atteinte : 5 questions à l'assistant par jour avec l'offre gratuite."}}""") { url, received ->
            val result = runBlocking { HttpAssistantClient(url, installId = { "11111111-1111-4111-8111-111111111111" }).ask(request) }
            assertEquals(AppResult.Failure(AppError.QuotaExceeded("Limite atteinte : 5 questions à l'assistant par jour avec l'offre gratuite.")), result)
        }
        withServer(400, """{"error":{"code":"message_too_long","message":"Message limité à 500 caractères."}}""") { url, _ ->
            assertEquals(AppResult.Failure(AppError.Rejected("Message limité à 500 caractères.")), runBlocking { HttpAssistantClient(url).ask(request) })
        }
        withServer(502, "pas du json") { url, _ ->
            assertEquals(AppResult.Failure(AppError.Unexpected("Assistant momentanément indisponible.")), runBlocking { HttpAssistantClient(url).ask(request) })
        }
        withServer(200, "{}") { url, _ ->
            assertTrue(runBlocking { HttpAssistantClient(url).ask(request) } is AppResult.Failure)
        }
    }

    @Test
    fun `serveur injoignable donne Offline`() {
        val port = ServerSocket(0).use { it.localPort } // port libéré : connexion refusée
        val result = runBlocking { HttpAssistantClient("http://127.0.0.1:$port", connectTimeoutMs = 2_000).ask(request) }
        assertEquals(AppResult.Failure(AppError.Offline), result)
    }

    // --- Historique local --------------------------------------------------------

    @Test
    fun `historique separe par machine borne et effacable`() = runBlocking {
        val dir: File = Files.createTempDirectory("assistant").toFile()
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        try {
            val repo = AssistantHistoryRepository(PreferenceDataStoreFactory.create(scope = scope) { File(dir, "a.preferences_pb") })
            repeat(45) { repo.append(ct.id, ChatMessage("user", "q$it")) }
            repo.append(electribe.id, ChatMessage("user", "electribe"))
            val ctHistory = repo.history(ct.id).first()
            assertEquals(AssistantHistoryRepository.MAX_STORED, ctHistory.size)
            assertEquals("q44", ctHistory.last().text)
            assertEquals(listOf("electribe"), repo.history(electribe.id).first().map { it.text })
            repo.clear(ct.id)
            assertTrue(repo.history(ct.id).first().isEmpty())
            assertEquals(1, repo.history(electribe.id).first().size)
        } finally {
            scope.cancel()
            dir.deleteRecursively()
        }
    }
}
