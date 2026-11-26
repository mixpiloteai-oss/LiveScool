package com.livemachine.academy.domain

import com.livemachine.academy.data.model.CourseModule
import com.livemachine.academy.data.model.QuizQuestion
import com.livemachine.academy.data.model.UserProgress
import kotlin.math.hypot
import kotlin.random.Random

/** Règles de progression, sans dépendance Android (testées unitairement). */
object ProgressRules {
    /** Score minimal pour réussir un quiz (leçon ou final). */
    const val PASS_RATIO = 0.8f
    const val FINAL_QUIZ_SIZE = 10

    fun finalQuizId(moduleId: String) = "final-$moduleId"

    fun lessonsDone(module: CourseModule, progress: UserProgress): Boolean =
        module.lessons.isNotEmpty() && module.lessons.all { progress.isLessonDone(it.id) }

    fun finalPassed(module: CourseModule, progress: UserProgress): Boolean =
        (progress.bestQuizScores[finalQuizId(module.id)]?.ratio ?: 0f) >= PASS_RATIO

    /** Un module est validé si toutes ses leçons sont faites ou si son quiz final est réussi. */
    fun isValidated(module: CourseModule, progress: UserProgress): Boolean =
        lessonsDone(module, progress) || finalPassed(module, progress)

    /** Le premier module est toujours ouvert ; les suivants s'ouvrent quand le précédent est validé. */
    fun isUnlocked(modules: List<CourseModule>, index: Int, progress: UserProgress, unlockAll: Boolean): Boolean =
        unlockAll || index <= 0 || isValidated(modules[index - 1], progress)

    /** Compétence acquise : toutes les leçons faites ET quiz final réussi. */
    fun competenceAcquired(module: CourseModule, progress: UserProgress): Boolean =
        lessonsDone(module, progress) && finalPassed(module, progress)

    fun finalQuizQuestions(module: CourseModule, random: Random = Random.Default): List<QuizQuestion> =
        module.lessons.flatMap { it.quiz }.shuffled(random).take(FINAL_QUIZ_SIZE)

    /** Questions ratées encore à réviser, dans l'ordre du cours. */
    fun reviewQuestions(modules: List<CourseModule>, progress: UserProgress): List<QuizQuestion> =
        modules.flatMap { m -> m.lessons.flatMap { it.quiz } }.filter { it.id in progress.wrongQuestions }
}

/** Règle de l'identification sur photo : coordonnées normalisées (0..1). */
object PhotoRules {
    const val HIT_TOLERANCE = 0.08f

    fun isHit(tapX: Float, tapY: Float, targetX: Float, targetY: Float, tolerance: Float = HIT_TOLERANCE): Boolean =
        hypot(tapX - targetX, tapY - targetY) <= tolerance
}
