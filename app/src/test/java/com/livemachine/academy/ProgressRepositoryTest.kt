package com.livemachine.academy

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.livemachine.academy.data.content.MachineCatalog
import com.livemachine.academy.data.model.QuizScore
import com.livemachine.academy.data.repository.ProgressRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import java.nio.file.Files

/** Sauvegarde séparée par machine : un fichier DataStore par machine. */
class ProgressRepositoryTest {
    private val electribe = MachineCatalog.ELECTRIBE_ID
    private val circuit = MachineCatalog.CIRCUIT_TRACKS_ID
    private val dir: File = Files.createTempDirectory("progress").toFile()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val legacyStore = store("progress")
    private val circuitStore = store("progress_circuit-tracks")
    private val repo = ProgressRepository(mapOf(electribe to legacyStore, circuit to circuitStore))

    private fun store(name: String): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(scope = scope) { File(dir, "$name.preferences_pb") }

    @After
    fun tearDown() {
        scope.cancel()
        dir.deleteRecursively()
    }

    @Test
    fun `chaque ecriture va dans le fichier de sa machine`() = runBlocking {
        repo.setLessonCompleted("m1-l1", true)
        repo.setLessonCompleted("ct-m1-l1", true)
        repo.setStepCompleted("ct-w-live", 0, true)
        repo.setChallengeCompleted("ct-w-live", true)
        repo.recordAnswer("ct-m1-l1-q2", correct = false)
        repo.recordQuizScore("final-ct-m1", QuizScore(9, 10))
        repo.recordQuizScore("final-m1", QuizScore(8, 10))

        val e = repo.progressOf(electribe).first()
        val c = repo.progressOf(circuit).first()
        assertEquals(setOf("m1-l1"), e.completedLessons)
        assertEquals(setOf("final-m1"), e.bestQuizScores.keys)
        assertTrue(e.completedSteps.isEmpty() && e.completedChallenges.isEmpty() && e.wrongQuestions.isEmpty())
        assertEquals(setOf("ct-m1-l1"), c.completedLessons)
        assertEquals(setOf("ct-w-live#0"), c.completedSteps)
        assertEquals(setOf("ct-w-live"), c.completedChallenges)
        assertEquals(setOf("ct-m1-l1-q2"), c.wrongQuestions)
        assertEquals(QuizScore(9, 10), c.bestQuizScores["final-ct-m1"])

        val all = repo.progress.first()
        assertEquals(setOf("m1-l1", "ct-m1-l1"), all.completedLessons)
        assertEquals(setOf("final-m1", "final-ct-m1"), all.bestQuizScores.keys)
    }

    @Test
    fun `une bonne reponse retire la question de la revision`() = runBlocking {
        repo.recordAnswer("ct-m2-l1-q1", correct = false)
        repo.recordAnswer("ct-m2-l1-q1", correct = true)
        assertTrue(repo.progressOf(circuit).first().wrongQuestions.isEmpty())
    }

    @Test
    fun `reinitialiser une machine ne touche pas l autre`() = runBlocking {
        repo.setLessonCompleted("m1-l1", true)
        repo.setLessonCompleted("ct-m1-l1", true)
        repo.recordQuizScore("ct-m1-l1", QuizScore(4, 4))
        repo.reset(circuit)
        assertTrue(repo.progressOf(circuit).first().completedLessons.isEmpty())
        assertTrue(repo.progressOf(circuit).first().bestQuizScores.isEmpty())
        assertEquals(setOf("m1-l1"), repo.progressOf(electribe).first().completedLessons)
    }

    /** Versions ≤ 1.3.0 : tout était dans « progress » ; les données Circuit Tracks y sont déplacées. */
    @Test
    fun `migration du fichier historique`() = runBlocking {
        legacyStore.edit {
            it[stringSetPreferencesKey("completed_lessons")] = setOf("m1-l1", "ct-m1-l1")
            it[stringSetPreferencesKey("completed_steps")] = setOf("m1-l1#0", "ct-m1-l1#2")
            it[stringSetPreferencesKey("wrong_questions")] = setOf("m2-l1-q1", "ct-m3-l1-q1")
            it[stringPreferencesKey("quiz_ct-m1-l1")] = "3/4"
            it[stringPreferencesKey("quiz_final-ct-m1")] = "8/10"
            it[stringPreferencesKey("quiz_m1-l1")] = "4/4"
        }
        repo.migrateLegacy()
        repo.migrateLegacy() // sans effet la seconde fois

        val e = repo.progressOf(electribe).first()
        val c = repo.progressOf(circuit).first()
        assertEquals(setOf("m1-l1"), e.completedLessons)
        assertEquals(setOf("m1-l1#0"), e.completedSteps)
        assertEquals(setOf("m2-l1-q1"), e.wrongQuestions)
        assertEquals(setOf("m1-l1"), e.bestQuizScores.keys)
        assertEquals(setOf("ct-m1-l1"), c.completedLessons)
        assertEquals(setOf("ct-m1-l1#2"), c.completedSteps)
        assertEquals(setOf("ct-m3-l1-q1"), c.wrongQuestions)
        assertEquals(QuizScore(8, 10), c.bestQuizScores["final-ct-m1"])
        assertEquals(QuizScore(3, 4), c.bestQuizScores["ct-m1-l1"])
    }
}
