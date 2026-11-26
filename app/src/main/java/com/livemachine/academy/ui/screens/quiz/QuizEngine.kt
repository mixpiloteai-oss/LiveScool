package com.livemachine.academy.ui.screens.quiz

import com.livemachine.academy.data.model.QuizQuestion
import com.livemachine.academy.data.model.QuizScore

/** Logique pure du quiz (sans Android), testable unitairement. */
data class QuizSession(
    val questions: List<QuizQuestion>,
    val index: Int = 0,
    val selected: Int? = null,
    val validated: Boolean = false,
    val correctCount: Int = 0,
) {
    val current: QuizQuestion? get() = questions.getOrNull(index)
    val isFinished: Boolean get() = index >= questions.size
    val isCurrentCorrect: Boolean get() = validated && selected == current?.correctIndex
    val score: QuizScore get() = QuizScore(correctCount, questions.size)

    fun select(option: Int): QuizSession = if (validated || isFinished) this else copy(selected = option)

    fun validate(): QuizSession {
        val q = current ?: return this
        val choice = selected ?: return this
        if (validated) return this
        return copy(validated = true, correctCount = correctCount + if (choice == q.correctIndex) 1 else 0)
    }

    fun next(): QuizSession = if (!validated) this else copy(index = index + 1, selected = null, validated = false)

    fun restart(): QuizSession = QuizSession(questions)
}
