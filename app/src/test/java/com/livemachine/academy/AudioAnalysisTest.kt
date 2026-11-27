package com.livemachine.academy

import com.livemachine.academy.data.assistant.MachineInfo
import com.livemachine.academy.data.audio.AudioAnalysisRequest
import com.livemachine.academy.data.audio.AudioPayload
import com.livemachine.academy.data.audio.HttpAudioAnalysisClient
import com.livemachine.academy.data.audio.MeasureItem
import com.livemachine.academy.data.audio.WavCodec
import com.livemachine.academy.domain.audio.AudioClipRules
import com.livemachine.academy.domain.result.AppError
import com.livemachine.academy.domain.result.AppResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.net.ServerSocket
import java.nio.ByteBuffer
import java.nio.ByteOrder

class AudioAnalysisTest {
    private val rate = AudioClipRules.SAMPLE_RATE

    // --- WAV, mixage, rééchantillonnage ------------------------------------------

    @Test
    fun `en-tete WAV PCM 16 bits mono conforme`() {
        val samples = shortArrayOf(0, 1000, -1000, 32767, -32768)
        val wav = WavCodec.encodePcm16Mono(samples, rate)
        val b = ByteBuffer.wrap(wav).order(ByteOrder.LITTLE_ENDIAN)
        assertEquals("RIFF", String(wav, 0, 4))
        assertEquals("WAVE", String(wav, 8, 4))
        assertEquals(1, b.getShort(20).toInt()) // PCM
        assertEquals(1, b.getShort(22).toInt()) // mono
        assertEquals(rate, b.getInt(24))
        assertEquals(16, b.getShort(34).toInt())
        assertEquals(samples.size * 2, b.getInt(40))
        assertEquals(AudioClipRules.wavBytes(samples.size), wav.size.toLong())
        val back = ShortArray(samples.size) { b.getShort(44 + it * 2) }
        assertArrayEquals(samples, back)
    }

    @Test
    fun `mixage mono et reechantillonnage`() {
        val stereo = floatArrayOf(1f, 0f, 0.5f, 0.5f, -1f, 1f)
        assertArrayEquals(floatArrayOf(0.5f, 0.5f, 0f), WavCodec.downmix(stereo, 2), 1e-6f)
        val oneSecond48k = FloatArray(48_000) { (it % 100) / 100f }
        val out = WavCodec.resample(oneSecond48k, 48_000, rate)
        assertEquals(rate, out.size)
        assertEquals(0f, out[0], 1e-6f)
        assertEquals(44_100, WavCodec.resample(FloatArray(44_100), 44_100, 44_100).size)
        assertArrayEquals(shortArrayOf(32767, -32767, 0), WavCodec.toPcm16(floatArrayOf(2f, -1f, 0f)))
    }

    // --- Règles : durée, consentement, données affichées --------------------------

    @Test
    fun `limites de duree`() {
        assertTrue(AudioClipRules.validate(rate * 4)!!.contains("trop court"))
        assertTrue(AudioClipRules.validate(rate * 61)!!.contains("trop long"))
        assertNull(AudioClipRules.validate(rate * 30))
        assertNull(AudioClipRules.validate(rate * AudioClipRules.MAX_SECONDS))
        // 60 s en WAV mono 16 bits 22 kHz : environ 2,6 Mo (sous la limite serveur de 6 Mo, base64 compris)
        assertEquals(2_646_044L, AudioClipRules.wavBytes(rate * 60))
        assertTrue(AudioClipRules.wavBytes(rate * 60) * 4 / 3 < 6 * 1024 * 1024)
    }

    @Test
    fun `envoi seulement avec extrait valide et consentement explicite`() {
        val ok = rate * 20
        assertFalse(AudioClipRules.canSend(null, consent = true, busy = false))
        assertFalse(AudioClipRules.canSend(ok, consent = false, busy = false))
        assertFalse(AudioClipRules.canSend(ok, consent = true, busy = true))
        assertFalse(AudioClipRules.canSend(rate * 2, consent = true, busy = false))
        assertTrue(AudioClipRules.canSend(ok, consent = true, busy = false))
    }

    @Test
    fun `donnees envoyees affichees clairement`() {
        val lines = AudioClipRules.payloadDescription(rate * 42, "Novation Circuit Tracks", "hardtek", "live", "intermédiaire", 3)
        assertTrue(lines[0], lines[0].contains("42 s") && lines[0].contains("1,9 Mo") && lines[0].contains("WAV mono"))
        assertTrue(lines[1].contains("hardtek") && lines[1].contains("live") && lines[1].contains("Circuit Tracks"))
        assertTrue(lines.any { it.startsWith("Rien d'autre") })
    }

    // --- Client réseau --------------------------------------------------------------

    private val request = AudioAnalysisRequest(
        MachineInfo("circuit-tracks", "Novation Circuit Tracks"), "hardtek", "live", "débutant", emptyList(),
        AudioPayload("wav", java.util.Base64.getEncoder().encodeToString(WavCodec.encodePcm16Mono(ShortArray(rate * 6), rate))),
    )

    @Test
    fun `resultat recu mesures et conseils separes`() {
        val body = """{"measures":{"tempo":{"bpm":145}},"summary":[{"label":"Tempo","value":"≈ 145 BPM (confiance élevée)","kind":"mesure"},
            {"label":"Graves entre les kicks","value":"présents","kind":"estimation"}],"advice":"## Mesures","adviceError":null}"""
        withHttpServer(200, body) { url, received ->
            val result = runBlocking { HttpAudioAnalysisClient(url).analyze(request) }
            result as AppResult.Success
            assertEquals(MeasureItem("Tempo", "≈ 145 BPM (confiance élevée)", "mesure"), result.value.summary[0])
            assertEquals("estimation", result.value.summary[1].kind)
            assertEquals("## Mesures", result.value.advice)
            assertTrue(received().contains("\"format\":\"wav\""))
            assertTrue(received().contains("\"style\":\"hardtek\""))
        }
    }

    @Test
    fun `mesures sans conseils quand l IA est indisponible`() {
        withHttpServer(200, """{"summary":[{"label":"Tempo","value":"≈ 128 BPM"}],"advice":null,"adviceError":"Conseils IA indisponibles"}""") { url, _ ->
            val result = runBlocking { HttpAudioAnalysisClient(url).analyze(request) } as AppResult.Success
            assertNull(result.value.advice)
            assertEquals("Conseils IA indisponibles", result.value.adviceError)
        }
    }

    @Test
    fun `format non supporte debit et pannes`() {
        withHttpServer(415, """{"error":{"code":"unsupported_format","message":"Format non pris en charge : seul le WAV est accepté par le serveur."}}""") { url, _ ->
            assertEquals(
                AppResult.Failure(AppError.Rejected("Format non pris en charge : seul le WAV est accepté par le serveur.")),
                runBlocking { HttpAudioAnalysisClient(url).analyze(request) },
            )
        }
        withHttpServer(429, "{}") { url, _ ->
            assertEquals(AppResult.Failure(AppError.RateLimited), runBlocking { HttpAudioAnalysisClient(url).analyze(request) })
        }
        withHttpServer(200, """{"summary":[]}""") { url, _ ->
            assertTrue(runBlocking { HttpAudioAnalysisClient(url).analyze(request) } is AppResult.Failure)
        }
        val port = ServerSocket(0).use { it.localPort }
        assertEquals(
            AppResult.Failure(AppError.Offline),
            runBlocking { HttpAudioAnalysisClient("http://127.0.0.1:$port", connectTimeoutMs = 2_000).analyze(request) },
        )
    }
}
