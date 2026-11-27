package com.livemachine.academy.data.audio

/** Extrait prêt à écouter et à envoyer : mono, 22 050 Hz, PCM 16 bits. */
class AudioClip(val samples: ShortArray, val sampleRate: Int, val source: String, val truncated: Boolean = false)

class UnsupportedAudioException(message: String) : Exception(message)
