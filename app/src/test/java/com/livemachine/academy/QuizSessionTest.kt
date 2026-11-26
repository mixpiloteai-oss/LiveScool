package com.livemachine.academy

import com.livemachine.academy.data.model.QuizQuestion
import com.livemachine.academy.ui.screens.quiz.QuizSession
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class QuizSessionTest {
    // Questions factices : elles testent le moteur, pas le contenu du cours.
    private val questions = listOf(
        QuizQuestion("q1", "A ?", listOf("x", "y"), correctIndex = 0, explanation = ""),
        QuizQuestion("q2", "B ?", listOf("x", "y", "z"), correctIndex = 2, explanation = ""),
    )

    @Test
    fun `validation sans choix ne fait rien`() {
        val s = QuizSession(questions).validate()
        assertFalse(s.validated)
    }

    @Test
    fun `parcours complet compte les bonnes reponses`() {
        var s = QuizSession(questions)
        s = s.select(0).validate()
        assertTrue(s.isCurrentCorrect)
        s = s.next().select(1).validate()
        assertFalse(s.isCurrentCorrect)
        s = s.next()
        assertTrue(s.isFinished)
        assertNull(s.current)
        assertEquals(1, s.score.correct)
        assertEquals(2, s.score.total)
    }

    @Test
    fun `on ne peut pas changer de reponse apres validation`() {
        val s = QuizSession(questions).select(1).validate().select(0)
        assertEquals(1, s.selected)
    }

    @Test
    fun `suivant impossible avant validation`() {
        val s = QuizSession(questions).select(0).next()
        assertEquals(0, s.index)
    }

    @Test
    fun `recommencer remet a zero`() {
        val s = QuizSession(questions).select(0).validate().next().restart()
        assertEquals(0, s.index)
        assertEquals(0, s.correctCount)
    }
}
