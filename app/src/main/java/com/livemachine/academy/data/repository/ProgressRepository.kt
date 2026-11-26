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
        )
    }

    suspend fun setLessonCompleted(lessonId: String, completed: Boolean) {
        dataStore.edit { prefs ->
            val current = prefs[COMPLETED_LESSONS].orEmpty()
            prefs[COMPLETED_LESSONS] = if (completed) current + lessonId else current - lessonId
        }
    }

    suspend fun setExerciseCompleted(exerciseId: String, completed: Boolean) {
        dataStore.edit { prefs ->
            val current = prefs[COMPLETED_EXERCISES].orEmpty()
            prefs[COMPLETED_EXERCISES] = if (completed) current + exerciseId else current - exerciseId
        }
    }

    /** Enregistre le score s'il bat le meilleur score précédent. */
    suspend fun recordQuizScore(lessonId: String, score: QuizScore) {
        val key = stringPreferencesKey(QUIZ_PREFIX + lessonId)
        dataStore.edit { prefs ->
            val best = parseScore(prefs[key])
            if (best == null || score.ratio > best.ratio) {
                prefs[key] = "${score.correct}/${score.total}"
            }
        }
    }

    suspend fun reset() {
        dataStore.edit { prefs ->
            prefs.remove(COMPLETED_LESSONS)
            prefs.remove(COMPLETED_EXERCISES)
            prefs.asMap().keys
                .filter { it.name.startsWith(QUIZ_PREFIX) }
                .map { stringPreferencesKey(it.name) }
                .forEach { prefs.remove(it) }
        }
    }

    private companion object {
        val COMPLETED_LESSONS = stringSetPreferencesKey("completed_lessons")
        val COMPLETED_EXERCISES = stringSetPreferencesKey("completed_exercises")
        const val QUIZ_PREFIX = "quiz_"

        fun parseScore(raw: String?): QuizScore? {
            val parts = raw?.split("/") ?: return null
            val correct = parts.getOrNull(0)?.toIntOrNull() ?: return null
            val total = parts.getOrNull(1)?.toIntOrNull() ?: return null
            return QuizScore(correct, total)
        }
    }
}
