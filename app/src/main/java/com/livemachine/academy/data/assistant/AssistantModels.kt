package com.livemachine.academy.data.assistant

import kotlinx.serialization.Serializable

/** Message du chat, conservé sur le téléphone (historique par machine). */
@Serializable
data class ChatMessage(val role: String, val text: String, val at: Long = 0L) {
    companion object {
        const val USER = "user"
        const val ASSISTANT = "assistant"
    }
}

/** Contrat v1 avec le backend (`backend/assistant`). Aucun secret : la clé IA reste sur le serveur. */
@Serializable
data class AssistantRequest(
    val machine: MachineInfo,
    val lesson: LessonInfo? = null,
    val level: String,
    val context: List<ContextItem>,
    val history: List<HistoryItem>,
    val message: String,
)

@Serializable data class MachineInfo(val id: String, val name: String)
@Serializable data class LessonInfo(val id: String, val title: String)
@Serializable data class ContextItem(val title: String, val text: String, val ref: String)
@Serializable data class HistoryItem(val role: String, val text: String)

@Serializable data class AssistantReply(val reply: String, val truncated: Boolean = false)
@Serializable data class ApiErrorBody(val error: ApiError? = null)
@Serializable data class ApiError(val code: String = "", val message: String = "")
