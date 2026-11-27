package com.livemachine.academy.data.audio

import java.io.File

/** Fichier WAV temporaire (cache de l'app) pour l'écoute avant envoi ; supprimé après analyse ou abandon. */
class TempAudioStore(cacheDir: File) {
    private val dir = File(cacheDir, "audio")
    val file: File get() = File(dir, "extrait.wav")

    fun write(clip: AudioClip): File {
        dir.mkdirs()
        file.writeBytes(WavCodec.encodePcm16Mono(clip.samples, clip.sampleRate))
        return file
    }

    /** Supprime tous les fichiers audio temporaires. */
    fun clear() {
        dir.listFiles()?.forEach { it.delete() }
    }
}
