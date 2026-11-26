package com.livemachine.academy.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.livemachine.academy.data.content.MachineCatalog
import com.livemachine.academy.data.model.QuizScore
import com.livemachine.academy.data.model.UserProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Progression de l'utilisateur, sauvegardée séparément pour chaque machine :
 * un fichier DataStore par machine. L'Electribe garde le fichier historique « progress ».
 * Chaque écriture va dans le fichier de la machine propriétaire de l'identifiant
 * ([MachineCatalog.machineOfProgressId]) ; la lecture réunit les fichiers.
 */
class ProgressRepository(
    private val stores: Map<String, DataStore<Preferences>>,
    private val defaultMachineId: String = MachineCatalog.ELECTRIBE_ID,
    private val machineOf: (String) -> String = MachineCatalog::machineOfProgressId,
) {
    init {
        require(defaultMachineId in stores) { "Fichier de progression manquant pour $defaultMachineId" }
    }

    /** Progression de toutes les machines réunie (les identifiants sont uniques entre machines). */
    val progress: Flow<UserProgress> = combine(stores.values.map { it.data }) { all ->
        all.map(::read).reduce { a, b -> a.merge(b) }
    }

    /** Progression d'une seule machine, lue dans son propre fichier. */
    fun progressOf(machineId: String): Flow<UserProgress> = store(machineId).data.map(::read)

    suspend fun setLessonCompleted(lessonId: String, completed: Boolean) = toggle(COMPLETED_LESSONS, lessonId, completed)

    suspend fun setExerciseCompleted(exerciseId: String, completed: Boolean) = toggle(COMPLETED_EXERCISES, exerciseId, completed)

    suspend fun setStepCompleted(ownerId: String, index: Int, completed: Boolean) =
        toggle(COMPLETED_STEPS, UserProgress.stepKey(ownerId, index), completed)

    suspend fun setChallengeCompleted(challengeId: String, completed: Boolean) = toggle(COMPLETED_CHALLENGES, challengeId, completed)

    /** Une réponse fausse place la question en révision ; une bonne réponse l'en retire. */
    suspend fun recordAnswer(questionId: String, correct: Boolean) = toggle(WRONG_QUESTIONS, questionId, !correct)

    /** Enregistre le score s'il bat le meilleur score précédent. */
    suspend fun recordQuizScore(quizId: String, score: QuizScore) {
        val key = stringPreferencesKey(QUIZ_PREFIX + quizId)
        storeFor(quizId).edit { prefs ->
            val best = parseScore(prefs[key])
            if (best == null || score.ratio > best.ratio) prefs[key] = "${score.correct}/${score.total}"
        }
    }

    /** Efface la progression d'une machine, sans toucher aux autres. */
    suspend fun reset(machineId: String) {
        store(machineId).edit { prefs ->
            SET_KEYS.forEach { prefs.remove(it) }
            prefs.asMap().keys.filter { it.name.startsWith(QUIZ_PREFIX) }
                .map { stringPreferencesKey(it.name) }
                .forEach { prefs.remove(it) }
        }
    }

    /**
     * Déplace dans leur propre fichier les données d'autres machines enregistrées dans le
     * fichier historique (versions ≤ 1.3.0 : un seul fichier pour tout). Sans effet ensuite.
     */
    suspend fun migrateLegacy() {
        val legacy = store(defaultMachineId)
        val prefs = legacy.data.first()
        SET_KEYS.forEach { key ->
            prefs[key].orEmpty().filter { owner(it) != defaultMachineId }.groupBy(::owner).forEach { (machineId, values) ->
                stores[machineId]?.edit { it[key] = it[key].orEmpty() + values } ?: return@forEach
                legacy.edit { it[key] = it[key].orEmpty() - values.toSet() }
            }
        }
        prefs.asMap().forEach { (k, v) ->
            if (!k.name.startsWith(QUIZ_PREFIX)) return@forEach
            val machineId = machineOf(k.name.removePrefix(QUIZ_PREFIX))
            val target = stores[machineId] ?: return@forEach
            if (machineId == defaultMachineId) return@forEach
            val key = stringPreferencesKey(k.name)
            target.edit { t ->
                val moved = parseScore(v as? String)
                val best = parseScore(t[key])
                if (moved != null && (best == null || moved.ratio > best.ratio)) t[key] = v as String
            }
            legacy.edit { it.remove(key) }
        }
    }

    private fun owner(value: String): String = machineOf(value.substringBefore('#'))

    private fun store(machineId: String): DataStore<Preferences> = stores[machineId] ?: stores.getValue(defaultMachineId)

    private fun storeFor(id: String): DataStore<Preferences> = store(machineOf(id))

    private suspend fun toggle(key: Preferences.Key<Set<String>>, value: String, present: Boolean) {
        storeFor(value.substringBefore('#')).edit { prefs ->
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
        val SET_KEYS = listOf(COMPLETED_LESSONS, COMPLETED_EXERCISES, WRONG_QUESTIONS, COMPLETED_STEPS, COMPLETED_CHALLENGES)
        const val QUIZ_PREFIX = "quiz_"

        fun read(prefs: Preferences) = UserProgress(
            completedLessons = prefs[COMPLETED_LESSONS].orEmpty(),
            completedExercises = prefs[COMPLETED_EXERCISES].orEmpty(),
            bestQuizScores = prefs.asMap()
                .filterKeys { it.name.startsWith(QUIZ_PREFIX) }
                .mapNotNull { (key, value) -> parseScore(value as? String)?.let { key.name.removePrefix(QUIZ_PREFIX) to it } }
                .toMap(),
            wrongQuestions = prefs[WRONG_QUESTIONS].orEmpty(),
            completedSteps = prefs[COMPLETED_STEPS].orEmpty(),
            completedChallenges = prefs[COMPLETED_CHALLENGES].orEmpty(),
        )

        fun UserProgress.merge(o: UserProgress) = UserProgress(
            completedLessons = completedLessons + o.completedLessons,
            completedExercises = completedExercises + o.completedExercises,
            bestQuizScores = (bestQuizScores.keys + o.bestQuizScores.keys).associateWith { k ->
                listOfNotNull(bestQuizScores[k], o.bestQuizScores[k]).maxBy { it.ratio }
            },
            wrongQuestions = wrongQuestions + o.wrongQuestions,
            completedSteps = completedSteps + o.completedSteps,
            completedChallenges = completedChallenges + o.completedChallenges,
        )

        fun parseScore(raw: String?): QuizScore? {
            val parts = raw?.split("/") ?: return null
            val correct = parts.getOrNull(0)?.toIntOrNull() ?: return null
            val total = parts.getOrNull(1)?.toIntOrNull() ?: return null
            return QuizScore(correct, total)
        }
    }
}
