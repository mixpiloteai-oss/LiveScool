package com.livemachine.academy

import com.livemachine.academy.data.content.ChallengeContent
import com.livemachine.academy.data.content.CourseContent
import com.livemachine.academy.data.content.MachineContent
import com.livemachine.academy.data.model.QuizScore
import com.livemachine.academy.data.model.UserProgress
import com.livemachine.academy.data.repository.MachinePhotoRepository
import com.livemachine.academy.data.repository.PhotoMark
import com.livemachine.academy.domain.PhotoRules
import com.livemachine.academy.domain.ProgressRules
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

class ProgressRulesTest {
    private val modules = CourseContent.modules
    private val m1 = modules[0]

    private fun allLessonsOf(index: Int) = modules[index].lessons.map { it.id }.toSet()

    @Test
    fun `seul le premier module est ouvert au depart`() {
        val p = UserProgress()
        assertTrue(ProgressRules.isUnlocked(modules, 0, p, unlockAll = false))
        assertFalse(ProgressRules.isUnlocked(modules, 1, p, unlockAll = false))
    }

    @Test
    fun `terminer les lecons du module 1 ouvre le module 2`() {
        val p = UserProgress(completedLessons = allLessonsOf(0))
        assertTrue(ProgressRules.isUnlocked(modules, 1, p, unlockAll = false))
        assertFalse(ProgressRules.isUnlocked(modules, 2, p, unlockAll = false))
    }

    @Test
    fun `reussir le quiz final suffit a valider un module`() {
        val p = UserProgress(bestQuizScores = mapOf(ProgressRules.finalQuizId(m1.id) to QuizScore(8, 10)))
        assertTrue(ProgressRules.isValidated(m1, p))
        assertTrue(ProgressRules.isUnlocked(modules, 1, p, unlockAll = false))
    }

    @Test
    fun `un quiz final sous 80 pour cent ne valide pas`() {
        val p = UserProgress(bestQuizScores = mapOf(ProgressRules.finalQuizId(m1.id) to QuizScore(7, 10)))
        assertFalse(ProgressRules.isValidated(m1, p))
    }

    @Test
    fun `le mode libre ouvre tout`() {
        modules.indices.forEach { assertTrue(ProgressRules.isUnlocked(modules, it, UserProgress(), unlockAll = true)) }
    }

    @Test
    fun `la competence demande lecons et quiz final`() {
        val lessonsOnly = UserProgress(completedLessons = allLessonsOf(0))
        assertFalse(ProgressRules.competenceAcquired(m1, lessonsOnly))
        val both = lessonsOnly.copy(bestQuizScores = mapOf(ProgressRules.finalQuizId(m1.id) to QuizScore(10, 10)))
        assertTrue(ProgressRules.competenceAcquired(m1, both))
    }

    @Test
    fun `quiz final de 10 questions distinctes issues du module`() {
        val questions = ProgressRules.finalQuizQuestions(m1, Random(1))
        val moduleIds = m1.lessons.flatMap { it.quiz }.map { it.id }.toSet()
        assertEquals(ProgressRules.FINAL_QUIZ_SIZE, questions.size)
        assertEquals(questions.size, questions.map { it.id }.toSet().size)
        assertTrue(questions.all { it.id in moduleIds })
    }

    @Test
    fun `la revision reprend les questions ratees`() {
        val wrong = setOf(m1.lessons[0].quiz[0].id, modules[3].lessons[1].quiz[0].id)
        val review = ProgressRules.reviewQuestions(modules, UserProgress(wrongQuestions = wrong))
        assertEquals(wrong, review.map { it.id }.toSet())
    }

    @Test
    fun `identifiants de questions uniques sur tout le cours`() {
        val ids = modules.flatMap { m -> m.lessons.flatMap { l -> l.quiz.map { it.id } } }
        assertEquals(ids.size, ids.toSet().size)
    }

    @Test
    fun `touche sur la photo`() {
        assertTrue(PhotoRules.isHit(0.50f, 0.50f, 0.52f, 0.53f))
        assertFalse(PhotoRules.isHit(0.10f, 0.10f, 0.50f, 0.50f))
    }

    @Test
    fun `reperes photo encodes puis decodes`() {
        val marks = mapOf("write" to PhotoMark(0.25f, 0.75f), "exit" to PhotoMark(0.5f, 0.1f))
        assertEquals(marks, MachinePhotoRepository.decodeMarks(MachinePhotoRepository.encodeMarks(marks)))
        assertTrue(MachinePhotoRepository.decodeMarks("corrompu").isEmpty())
    }

    @Test
    fun `defis coherents`() {
        val moduleIds = modules.map { it.id }.toSet()
        val challenges = ChallengeContent.challenges
        assertEquals(challenges.size, challenges.map { it.id }.toSet().size)
        challenges.forEach {
            assertTrue("${it.id} : module inconnu", it.requiredModuleId in moduleIds)
            assertTrue("${it.id} : pages manquantes", it.manualRefs.isNotEmpty())
            assertTrue("${it.id} : étapes manquantes", it.steps.isNotEmpty())
        }
    }

    @Test
    fun `commandes a reperer uniques`() {
        val ids = MachineContent.identifiableControls.map { it.id }
        assertEquals(ids.size, ids.toSet().size)
        assertTrue(ids.size >= 4)
    }
}
