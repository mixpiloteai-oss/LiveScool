package com.livemachine.academy.data.services

import com.livemachine.academy.domain.result.AppError
import com.livemachine.academy.domain.result.AppResult

/**
 * Contrats des futures fonctions Premium. Aucune n'est implémentée : les versions actuelles
 * répondent « pas encore disponible ». Une vraie implémentation devra citer les manuels
 * officiels pour toute information sur une machine (règle du projet).
 */
interface MusicAssistant {
    suspend fun ask(machineId: String, question: String): AppResult<String>
}

data class AudioAnalysis(val bpm: Float?, val notes: List<String>)

interface AudioAnalyzer {
    /** [audioPath] : fichier enregistré sur le téléphone ; rien n'est envoyé sans accord explicite. */
    suspend fun analyze(audioPath: String): AppResult<AudioAnalysis>
}

class UnavailableMusicAssistant : MusicAssistant {
    override suspend fun ask(machineId: String, question: String): AppResult<String> = AppResult.Failure(AppError.NotAvailable)
}

class UnavailableAudioAnalyzer : AudioAnalyzer {
    override suspend fun analyze(audioPath: String): AppResult<AudioAnalysis> = AppResult.Failure(AppError.NotAvailable)
}
