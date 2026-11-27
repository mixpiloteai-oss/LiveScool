package com.livemachine.academy.data.audio

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.core.content.ContextCompat
import com.livemachine.academy.domain.audio.AudioClipRules
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

/**
 * Enregistrement au microphone, uniquement à la demande de l'utilisateur (bouton « Enregistrer »),
 * arrêté par lui ou au bout de [AudioClipRules.MAX_SECONDS] secondes. Rien n'est écrit sur disque ici.
 */
class AudioRecorder(private val context: Context) {
    @Volatile private var stopRequested = false

    fun hasPermission() =
        ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED

    fun stop() {
        stopRequested = true
    }

    /** Enregistre jusqu'à [stop] ou la durée maximale ; [onProgress] reçoit les secondes écoulées. */
    @SuppressLint("MissingPermission") // permission vérifiée juste avant
    suspend fun record(onProgress: (Int) -> Unit): AudioClip = withContext(Dispatchers.IO) {
        if (!hasPermission()) throw SecurityException("Micro non autorisé.")
        stopRequested = false
        val rate = AudioClipRules.SAMPLE_RATE
        val minBuf = AudioRecord.getMinBufferSize(rate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT)
        if (minBuf <= 0) throw UnsupportedAudioException("Enregistrement non pris en charge sur ce téléphone.")
        val recorder = AudioRecord(MediaRecorder.AudioSource.MIC, rate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, minBuf * 4)
        if (recorder.state != AudioRecord.STATE_INITIALIZED) {
            recorder.release()
            throw UnsupportedAudioException("Le micro n'est pas disponible.")
        }
        val total = ShortArray(rate * AudioClipRules.MAX_SECONDS)
        var count = 0
        val chunk = ShortArray(minBuf)
        try {
            recorder.startRecording()
            var lastSecond = -1
            while (coroutineContext.isActive && !stopRequested && count < total.size) {
                val n = recorder.read(chunk, 0, minOf(chunk.size, total.size - count))
                if (n < 0) break
                chunk.copyInto(total, count, 0, n)
                count += n
                val sec = count / rate
                if (sec != lastSecond) {
                    lastSecond = sec
                    onProgress(sec)
                }
            }
        } finally {
            runCatching { recorder.stop() }
            recorder.release()
        }
        AudioClip(total.copyOf(count), rate, "Enregistrement micro")
    }
}
