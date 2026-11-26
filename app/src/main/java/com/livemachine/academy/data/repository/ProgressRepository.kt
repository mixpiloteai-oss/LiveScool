package com.livemachine.academy.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.livemachine.academy.data.model.QuizScore
import com.livemachine.academy.data.model.UserProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** Progression de l'utilisateur, stockée localement avec DataStore. */
class ProgressRepository(private val dataStore: DataStore<Preferences>) {

    val progress: Flow<UserProgress> = dataStore.data.map { prefs ->
        UserProgress(
            completedLessons = prefs[COMPLETED_LESSONS].orEmpty(),
            completedExercises = prefs[COMPLETED_EXERCISES].orEmpty(),
            bestQuizScores = prefs.asMap()
                .filterKeys { it.name.startsWith(QUIZ_PREFIX) }
                .mapNotNull { (key, value) ->
                    parseScore(value as? String)?.let { key.name.removePrefix(QUIZ_PREFIX) to it }
                }
                .toMap(),
            wrongQuestions = prefs[WRONG_QUESTIONS].orEmpty(),
            completedSteps = prefs[COMPLETED_STEPS].orEmpty(),
            completedChallenges = prefs[COMPLETED_CHALLENGES].orEmpty(),
        )
    }

    suspend fun setLessonCompleted(lessonId: String, completed: Boolean) = toggle(COMPLETED_LESSONS, lessonId, completed)

    suspend fun setExerciseCompleted(exerciseId: String, completed: Boolean) = toggle(COMPLETED_EXERCISES, exerciseId, completed)

    suspend fun setStepCompleted(lessonId: String, index: Int, completed: Boolean) =
        toggle(COMPLETED_STEPS, UserProgress.stepKey(lessonId, index), completed)

    suspend fun setChallengeCompleted(challengeId: String, completed: Boolean) = toggle(COMPLETED_CHALLENGES, challengeId, completed)

    /** Une réponse fausse place la question en révision ; une bonne réponse l'en retire. */
    suspend fun recordAnswer(questionId: String, correct: Boolean) = toggle(WRONG_QUESTIONS, questionId, !correct)

    /** Enregistre le score s'il bat le meilleur score précédent. */
    suspend fun recordQuizScore(quizId: String, score: QuizScore) {
        val key = stringPreferencesKey(QUIZ_PREFIX + quizId)
        dataStore.edit { prefs ->
            val best = parseScore(prefs[key])
            if (best == null || score.ratio > best.ratio) {
                prefs[key] = "${score.correct}/${score.total}"
            }
        }
    }

    suspend fun reset() {
        dataStore.edit { prefs ->
            listOf(COMPLETED_LESSONS, COMPLETED_EXERCISES, WRONG_QUESTIONS, COMPLETED_STEPS, COMPLETED_CHALLENGES)
                .forEach { prefs.remove(it) }
            prefs.asMap().keys
                .filter { it.name.startsWith(QUIZ_PREFIX) }
                .map { stringPreferencesKey(it.name) }
                .forEach { prefs.remove(it) }
        }
    }

    private suspend fun toggle(key: Preferences.Key<Set<String>>, value: String, present: Boolean) {
        dataStore.edit { prefs ->
            val current = prefs[key].orEmpty()
            prefs[key] = if (present) current + value else current - value
        }
    }

    private companion object {
        val COMPLETED_LESSONS = stringSetPreferencesKey("completed_lessons")
        val COMPLETED_EXERCISES = stringSetPreferencesKey("completed_exercises")
        val WRONG_QUESTIONS = stringSetPreferencesKey("wrong_questions")
        val COMPLETED_STEPS = stringSetPreferencesKey("completed_steps")
        val COMPLETED_CHALLENGES = stringSetPreferencesKey("completed_challenges")
        const val QUIZ_PREFIX = "quiz_"

        fun parseScore(raw: String?): QuizScore? {
            val parts = raw?.split("/") ?: return null
            val correct = parts.getOrNull(0)?.toIntOrNull() ?: return null
            val total = parts.getOrNull(1)?.toIntOrNull() ?: return null
            return QuizScore(correct, total)
        }
    }
}
