package com.livemachine.academy.data.services

import com.livemachine.academy.data.assistant.AssistantRequest
import com.livemachine.academy.domain.result.AppError
import com.livemachine.academy.domain.result.AppResult

/**
 * Contrats des fonctions IA. L'assistant a une implémentation réseau ([com.livemachine.academy.data.assistant.HttpAssistantClient]) ;
 * l'analyse audio n'est pas encore implémentée. Une vraie implémentation devra citer les manuels
 * officiels pour toute information sur une machine (règle du projet).
 */
/** Assistant musical : la requête porte la machine, la leçon et un contexte court issu des leçons. */
interface MusicAssistant {
    suspend fun ask(request: AssistantRequest): AppResult<String>
}

data class AudioAnalysis(val bpm: Float?, val notes: List<String>)

interface AudioAnalyzer {
    /** [audioPath] : fichier enregistré sur le téléphone ; rien n'est envoyé sans accord explicite. */
    suspend fun analyze(audioPath: String): AppResult<AudioAnalysis>
}

class UnavailableMusicAssistant : MusicAssistant {
    override suspend fun ask(request: AssistantRequest): AppResult<String> = AppResult.Failure(AppError.NotAvailable)
}

class UnavailableAudioAnalyzer : AudioAnalyzer {
    override suspend fun analyze(audioPath: String): AppResult<AudioAnalysis> = AppResult.Failure(AppError.NotAvailable)
}
