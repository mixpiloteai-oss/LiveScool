package com.livemachine.academy.domain.assistant

import com.livemachine.academy.data.assistant.AssistantRequest
import com.livemachine.academy.data.assistant.ChatMessage
import com.livemachine.academy.data.assistant.ContextItem
import com.livemachine.academy.data.assistant.HistoryItem
import com.livemachine.academy.data.assistant.LessonInfo
import com.livemachine.academy.data.assistant.MachineInfo
import com.livemachine.academy.data.model.Lesson
import com.livemachine.academy.data.model.MachineProfile
import com.livemachine.academy.data.model.UserProgress
import java.text.Normalizer

/**
 * Construit la requête envoyée à l'assistant avec un contexte volontairement court
 * (économie de jetons) : la leçon ouverte + les leçons les plus proches de la question,
 * chacune avec ses références au manuel officiel. Logique pure, testée.
 */
object AssistantContextBuilder {
    const val MESSAGE_MAX_CHARS = 500
    const val HISTORY_MAX = 6
    const val RELATED_MAX = 3
    const val SNIPPET_MAX_CHARS = 650

    fun level(machine: MachineProfile, progress: UserProgress): String {
        val total = machine.lessons.size
        if (total == 0) return "débutant"
        val ratio = machine.lessons.count { it.id in progress.completedLessons }.toFloat() / total
        return when {
            ratio < 0.25f -> "débutant"
            ratio < 0.7f -> "intermédiaire"
            else -> "avancé"
        }
    }

    fun build(
        machine: MachineProfile,
        lesson: Lesson?,
        history: List<ChatMessage>,
        message: String,
        progress: UserProgress,
    ): AssistantRequest {
        val related = relevantLessons(machine, message, exclude = setOfNotNull(lesson?.id))
        return AssistantRequest(
            machine = MachineInfo(machine.id, machine.fullName),
            lesson = lesson?.let { LessonInfo(it.id, it.title) },
            level = level(machine, progress),
            context = listOfNotNull(lesson).plus(related).map(::snippet),
            history = history.takeLast(HISTORY_MAX).map { HistoryItem(it.role, it.text) },
            message = message.trim().take(MESSAGE_MAX_CHARS),
        )
    }

    /** Résumé d'une leçon : objectif, commandes avec pages, début de l'explication. */
    fun snippet(lesson: Lesson): ContextItem {
        val controls = lesson.controls.joinToString(" ; ") { c ->
            c.name + " : " + c.role + (c.manualRef?.let { " (p. ${it.pages})" } ?: "")
        }
        val text = buildString {
            lesson.objective?.let { append("Objectif : ").append(it).append('\n') }
            if (controls.isNotEmpty()) append("Commandes : ").append(controls).append('\n')
            lesson.explanation?.let { append(it) }
        }
        return ContextItem(
            title = lesson.title,
            text = clip(text.trim(), SNIPPET_MAX_CHARS),
            ref = lesson.manualRefs.joinToString(" ; ") { "${it.doc.label}, ${it.chapter}, p. ${it.pages}" },
        )
    }

    /** Recherche par mots-clés dans les leçons de la machine (aucun appel réseau). */
    fun relevantLessons(machine: MachineProfile, question: String, exclude: Set<String> = emptySet()): List<Lesson> {
        val words = words(question)
        if (words.isEmpty()) return emptyList()
        return machine.lessons
            .filter { it.id !in exclude }
            .map { l ->
                val title = words(l.title)
                val body = words(listOfNotNull(l.objective, l.explanation).joinToString(" ") + " " +
                    l.controls.joinToString(" ") { it.name + " " + it.role })
                l to words.sumOf { w -> (if (w in title) 3 else 0) + (if (w in body) 1 else 0) }
            }
            .filter { it.second > 0 }
            .sortedByDescending { it.second }
            .take(RELATED_MAX)
            .map { it.first }
    }

    private val STOP = setOf("comment", "quoi", "est-ce", "pour", "avec", "dans", "faire", "veux", "peux", "mon", "cette", "quel", "quelle", "quand", "sont", "avoir", "etre", "plus", "moins", "une", "des", "les")

    private fun words(text: String): Set<String> =
        Normalizer.normalize(text.lowercase(), Normalizer.Form.NFD)
            .replace(Regex("\\p{M}+"), "")
            .split(Regex("[^a-z0-9]+"))
            .filter { it.length >= 4 && it !in STOP }
            .map { if (it.length > 5 && it.endsWith("s")) it.dropLast(1) else it }
            .toSet()

    private fun clip(text: String, max: Int) = if (text.length <= max) text else text.take(max - 1) + "…"
}
