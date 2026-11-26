package com.livemachine.academy.data.model

data class QuizScore(val correct: Int, val total: Int) {
    val ratio: Float get() = if (total == 0) 0f else correct.toFloat() / total
}

data class UserProgress(
    val completedLessons: Set<String> = emptySet(),
    val completedExercises: Set<String> = emptySet(),
    val bestQuizScores: Map<String, QuizScore> = emptyMap(),
) {
    fun isLessonDone(lessonId: String) = lessonId in completedLessons
}

enum class TextSize(val label: String, val scale: Float) {
    NORMAL("Normale", 1.0f),
    LARGE("Grande", 1.15f),
    EXTRA_LARGE("Très grande", 1.3f),
}

data class AppSettings(
    val textSize: TextSize = TextSize.NORMAL,
    /** Garde l'écran allumé pendant qu'on manipule la machine. */
    val keepScreenOn: Boolean = true,
)
