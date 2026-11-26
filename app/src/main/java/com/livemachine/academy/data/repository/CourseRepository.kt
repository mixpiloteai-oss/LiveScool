package com.livemachine.academy.data.repository

import com.livemachine.academy.data.content.ChallengeContent
import com.livemachine.academy.data.content.CourseContent
import com.livemachine.academy.data.content.GlossaryContent
import com.livemachine.academy.data.model.CourseModule
import com.livemachine.academy.data.model.Exercise
import com.livemachine.academy.data.model.GlossaryTerm
import com.livemachine.academy.data.model.Lesson
import com.livemachine.academy.data.model.StyleChallenge

/** Accès en lecture au contenu pédagogique embarqué (hors ligne). */
class CourseRepository(
    private val modules: List<CourseModule> = CourseContent.modules,
    private val glossary: List<GlossaryTerm> = GlossaryContent.terms,
) {
    fun modules(): List<CourseModule> = modules

    fun module(id: String): CourseModule? = modules.firstOrNull { it.id == id }

    fun lessons(): List<Lesson> = modules.flatMap { it.lessons }

    fun lesson(id: String): Lesson? = lessons().firstOrNull { it.id == id }

    fun exercises(): List<Exercise> = lessons().mapNotNull { it.exercise }

    fun glossary(): List<GlossaryTerm> = glossary

    fun challenges(): List<StyleChallenge> = ChallengeContent.challenges

    /** Leçon à laquelle appartient une question de quiz. */
    fun lessonOfQuestion(questionId: String): Lesson? = lessons().firstOrNull { l -> l.quiz.any { it.id == questionId } }

    /** Première leçon non terminée, dans l'ordre du parcours. */
    fun nextLesson(completed: Set<String>): Lesson? =
        lessons().firstOrNull { it.id !in completed }
}
