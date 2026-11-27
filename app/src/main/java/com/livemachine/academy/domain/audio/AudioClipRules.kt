package com.livemachine.academy.domain.audio

/** Règles de l'analyse audio, pures et testées : limites, résumé des données envoyées, conditions d'envoi. */
object AudioClipRules {
    const val SAMPLE_RATE = 22_050
    const val MIN_SECONDS = 5
    const val MAX_SECONDS = 60

    /** Taille du WAV PCM 16 bits mono envoyé (en-tête de 44 octets compris). */
    fun wavBytes(samples: Int) = 44L + samples * 2L

    fun durationSeconds(samples: Int, sampleRate: Int = SAMPLE_RATE) = samples.toDouble() / sampleRate

    fun validate(samples: Int): String? {
        val d = durationSeconds(samples)
        return when {
            d < MIN_SECONDS -> "Extrait trop court : $MIN_SECONDS secondes minimum."
            d > MAX_SECONDS + 0.5 -> "Extrait trop long : $MAX_SECONDS secondes maximum."
            else -> null
        }
    }

    /** L'envoi exige un extrait valide, le consentement explicite et aucun envoi en cours. */
    fun canSend(samples: Int?, consent: Boolean, busy: Boolean): Boolean =
        samples != null && validate(samples) == null && consent && !busy

    fun formatSize(bytes: Long): String =
        if (bytes >= 1_000_000) String.format(java.util.Locale.FRANCE, "%.1f Mo", bytes / 1_000_000.0)
        else "${bytes / 1000} ko"

    /** Lignes affichées avant l'envoi : exactement ce qui part vers le serveur. */
    fun payloadDescription(samples: Int, machineName: String, style: String, goal: String, level: String, lessonCount: Int): List<String> = listOf(
        "Audio : WAV mono 22 kHz, ${durationSeconds(samples).toInt()} s, ${formatSize(wavBytes(samples))}",
        "Réglages : $machineName, style $style, objectif $goal, niveau $level",
        "Texte : $lessonCount extrait(s) de leçons de l'app, pour citer le manuel",
        "Rien d'autre : ni ton nom, ni ta position, ni d'autres fichiers",
    )
}
