package com.livemachine.academy.data.model

data class QuizScore(val correct: Int, val total: Int) {
    val ratio: Float get() = if (total == 0) 0f else correct.toFloat() / total
}

data class UserProgress(
    val completedLessons: Set<String> = emptySet(),
    val completedExercises: Set<String> = emptySet(),
    val bestQuizScores: Map<String, QuizScore> = emptyMap(),
    /** Identifiants des questions ratées, à réviser (retirées quand elles sont réussies). */
    val wrongQuestions: Set<String> = emptySet(),
    /** Étapes guidées faites sur la machine, au format « lessonId#index ». */
    val completedSteps: Set<String> = emptySet(),
    val completedChallenges: Set<String> = emptySet(),
) {
    fun isLessonDone(lessonId: String) = lessonId in completedLessons
    fun isStepDone(lessonId: String, index: Int) = stepKey(lessonId, index) in completedSteps

    companion object {
        fun stepKey(lessonId: String, index: Int) = "$lessonId#$index"
    }
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
    /** Mode libre : tous les modules sont accessibles sans validation du précédent. */
    val unlockAllModules: Boolean = false,
)
