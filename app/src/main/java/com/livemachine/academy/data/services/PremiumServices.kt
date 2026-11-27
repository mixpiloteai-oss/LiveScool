package com.livemachine.academy.data.services

import com.livemachine.academy.data.assistant.AssistantRequest
import com.livemachine.academy.data.audio.AudioAnalysisRequest
import com.livemachine.academy.data.audio.AudioAnalysisResult
import com.livemachine.academy.domain.result.AppError
import com.livemachine.academy.domain.result.AppResult

/**
 * Contrats des fonctions IA. L'assistant a une implémentation réseau ([com.livemachine.academy.data.assistant.HttpAssistantClient]) ;
 * l'analyse audio aussi ([com.livemachine.academy.data.audio.HttpAudioAnalysisClient]). Une vraie implémentation devra citer les manuels
 * officiels pour toute information sur une machine (règle du projet).
 */
/** Assistant musical : la requête porte la machine, la leçon et un contexte court issu des leçons. */
interface MusicAssistant {
    suspend fun ask(request: AssistantRequest): AppResult<String>
}

/** Analyse d'un extrait : mesures objectives côté serveur + conseils IA. N'est appelé qu'après consentement explicite. */
interface AudioAnalyzer {
    suspend fun analyze(request: AudioAnalysisRequest): AppResult<AudioAnalysisResult>
}

class UnavailableMusicAssistant : MusicAssistant {
    override suspend fun ask(request: AssistantRequest): AppResult<String> = AppResult.Failure(AppError.NotAvailable)
}

class UnavailableAudioAnalyzer : AudioAnalyzer {
    override suspend fun analyze(request: AudioAnalysisRequest): AppResult<AudioAnalysisResult> = AppResult.Failure(AppError.NotAvailable)
}
