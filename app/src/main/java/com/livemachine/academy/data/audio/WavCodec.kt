package com.livemachine.academy.data.audio

import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.roundToInt

/** Conversion d'échantillons en WAV PCM 16 bits mono, mixage mono et rééchantillonnage linéaire. Pur JVM, testé. */
object WavCodec {
    fun encodePcm16Mono(samples: ShortArray, sampleRate: Int): ByteArray {
        val dataBytes = samples.size * 2
        val buf = ByteBuffer.allocate(44 + dataBytes).order(ByteOrder.LITTLE_ENDIAN)
        buf.put("RIFF".toByteArray(Charsets.US_ASCII)).putInt(36 + dataBytes)
        buf.put("WAVE".toByteArray(Charsets.US_ASCII))
        buf.put("fmt ".toByteArray(Charsets.US_ASCII)).putInt(16).putShort(1).putShort(1)
            .putInt(sampleRate).putInt(sampleRate * 2).putShort(2).putShort(16)
        buf.put("data".toByteArray(Charsets.US_ASCII)).putInt(dataBytes)
        samples.forEach { buf.putShort(it) }
        return buf.array()
    }

    /** Signal entrelacé (-1..1) → mono par moyenne des canaux. */
    fun downmix(interleaved: FloatArray, channels: Int): FloatArray {
        if (channels <= 1) return interleaved
        val frames = interleaved.size / channels
        return FloatArray(frames) { i ->
            var s = 0f
            for (c in 0 until channels) s += interleaved[i * channels + c]
            s / channels
        }
    }

    /** Rééchantillonnage linéaire (suffisant pour mesurer tempo et équilibre). */
    fun resample(input: FloatArray, fromRate: Int, toRate: Int): FloatArray {
        if (fromRate == toRate || input.isEmpty()) return input
        val outLen = (input.size.toLong() * toRate / fromRate).toInt()
        val ratio = fromRate.toDouble() / toRate
        return FloatArray(outLen) { i ->
            val pos = i * ratio
            val k = pos.toInt()
            val frac = (pos - k).toFloat()
            val a = input[k.coerceAtMost(input.size - 1)]
            val b = input[(k + 1).coerceAtMost(input.size - 1)]
            a + (b - a) * frac
        }
    }

    fun toPcm16(input: FloatArray): ShortArray =
        ShortArray(input.size) { (input[it].coerceIn(-1f, 1f) * 32767f).roundToInt().toShort() }
}
