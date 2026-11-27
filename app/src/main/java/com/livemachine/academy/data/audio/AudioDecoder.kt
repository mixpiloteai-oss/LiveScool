package com.livemachine.academy.data.audio

import android.content.Context
import android.media.AudioFormat
import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import android.net.Uri
import com.livemachine.academy.domain.audio.AudioClipRules
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.ByteOrder

/**
 * Décode un fichier choisi par l'utilisateur (MP3, AAC/M4A, WAV, FLAC, OGG… selon les décodeurs du téléphone)
 * en mono 22 050 Hz, limité à [AudioClipRules.MAX_SECONDS] secondes. Tout reste sur le téléphone.
 */
class AudioDecoder(private val context: Context) {

    suspend fun decode(uri: Uri, displayName: String): AudioClip = withContext(Dispatchers.Default) {
        val extractor = MediaExtractor()
        try {
            try {
                extractor.setDataSource(context, uri, null)
            } catch (e: Exception) {
                throw UnsupportedAudioException("Ce fichier ne peut pas être lu.")
            }
            val track = (0 until extractor.trackCount).firstOrNull {
                extractor.getTrackFormat(it).getString(MediaFormat.KEY_MIME)?.startsWith("audio/") == true
            } ?: throw UnsupportedAudioException("Aucune piste audio dans ce fichier.")
            extractor.selectTrack(track)
            val format = extractor.getTrackFormat(track)
            val mime = format.getString(MediaFormat.KEY_MIME)!!
            val codec = try {
                MediaCodec.createDecoderByType(mime).apply { configure(format, null, null, 0) }
            } catch (e: Exception) {
                throw UnsupportedAudioException("Format audio non pris en charge par ce téléphone ($mime).")
            }
            var sampleRate = format.getInteger(MediaFormat.KEY_SAMPLE_RATE)
            var channels = format.getInteger(MediaFormat.KEY_CHANNEL_COUNT)
            var floatPcm = false
            val out = FloatBuffer()
            var truncated = false
            codec.start()
            try {
                val info = MediaCodec.BufferInfo()
                var inputDone = false
                var outputDone = false
                while (!outputDone) {
                    if (!inputDone) {
                        val inIndex = codec.dequeueInputBuffer(10_000)
                        if (inIndex >= 0) {
                            val buffer = codec.getInputBuffer(inIndex)!!
                            val size = extractor.readSampleData(buffer, 0)
                            if (size < 0) {
                                codec.queueInputBuffer(inIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM)
                                inputDone = true
                            } else {
                                codec.queueInputBuffer(inIndex, 0, size, extractor.sampleTime, 0)
                                extractor.advance()
                            }
                        }
                    }
                    val outIndex = codec.dequeueOutputBuffer(info, 10_000)
                    when {
                        outIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED -> {
                            val f = codec.outputFormat
                            sampleRate = f.getInteger(MediaFormat.KEY_SAMPLE_RATE)
                            channels = f.getInteger(MediaFormat.KEY_CHANNEL_COUNT)
                            floatPcm = f.containsKey(MediaFormat.KEY_PCM_ENCODING) &&
                                f.getInteger(MediaFormat.KEY_PCM_ENCODING) == AudioFormat.ENCODING_PCM_FLOAT
                        }
                        outIndex >= 0 -> {
                            val buffer = codec.getOutputBuffer(outIndex)!!.order(ByteOrder.LITTLE_ENDIAN)
                            buffer.position(info.offset)
                            buffer.limit(info.offset + info.size)
                            if (floatPcm) {
                                val fb = buffer.asFloatBuffer()
                                while (fb.hasRemaining()) out.add(fb.get())
                            } else {
                                val sb = buffer.asShortBuffer()
                                while (sb.hasRemaining()) out.add(sb.get() / 32768f)
                            }
                            codec.releaseOutputBuffer(outIndex, false)
                            if (info.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0) outputDone = true
                            if (out.size >= (AudioClipRules.MAX_SECONDS.toLong() * sampleRate * channels)) {
                                truncated = true
                                outputDone = true
                            }
                        }
                    }
                }
            } finally {
                runCatching { codec.stop() }
                codec.release()
            }
            if (out.size == 0) throw UnsupportedAudioException("Aucun son n'a pu être décodé.")
            val maxFrames = AudioClipRules.MAX_SECONDS * sampleRate
            val mono = WavCodec.downmix(out.toArray(), channels).let { if (it.size > maxFrames) it.copyOf(maxFrames) else it }
            val resampled = WavCodec.resample(mono, sampleRate, AudioClipRules.SAMPLE_RATE)
            AudioClip(WavCodec.toPcm16(resampled), AudioClipRules.SAMPLE_RATE, displayName, truncated)
        } finally {
            extractor.release()
        }
    }

    /** Tableau de flottants extensible (évite des millions d'objets Float). */
    private class FloatBuffer {
        private var data = FloatArray(1 shl 16)
        var size = 0
            private set

        fun add(v: Float) {
            if (size == data.size) data = data.copyOf(data.size * 2)
            data[size++] = v
        }

        fun toArray(): FloatArray = data.copyOf(size)
    }
}
