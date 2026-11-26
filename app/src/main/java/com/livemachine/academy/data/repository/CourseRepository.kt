package com.livemachine.academy.data.repository

import com.livemachine.academy.data.content.GlossaryContent
import com.livemachine.academy.data.content.MachineCatalog
import com.livemachine.academy.data.model.CourseModule
import com.livemachine.academy.data.model.Exercise
import com.livemachine.academy.data.model.GlossaryTerm
import com.livemachine.academy.data.model.Lesson
import com.livemachine.academy.data.model.MachineProfile
import com.livemachine.academy.data.model.StyleChallenge

/**
 * Accès en lecture au contenu pédagogique embarqué (hors ligne), pour toutes les machines.
 * Les identifiants de modules et de leçons sont uniques entre machines.
 */
class CourseRepository(
    private val machines: List<MachineProfile> = MachineCatalog.all,
    private val glossary: List<GlossaryTerm> = GlossaryContent.terms,
) {
    fun machines(): List<MachineProfile> = machines

    fun machine(id: String?): MachineProfile = machines.firstOrNull { it.id == id } ?: machines.first()

    fun modules(machineId: String?): List<CourseModule> = machine(machineId).modules

    /** Recherche d'un module, toutes machines confondues. */
    fun module(id: String): CourseModule? = machines.flatMap { it.modules }.firstOrNull { it.id == id }

    fun machineOfModule(moduleId: String): MachineProfile? = machines.firstOrNull { m -> m.modules.any { it.id == moduleId } }

    /** Toutes les leçons, toutes machines confondues. */
    fun lessons(): List<Lesson> = machines.flatMap { it.lessons }

    fun lesson(id: String): Lesson? = lessons().firstOrNull { it.id == id }

    fun exercises(machineId: String?): List<Exercise> = machine(machineId).lessons.mapNotNull { it.exercise }

    fun challenges(machineId: String?): List<StyleChallenge> = machine(machineId).challenges

    /** Termes généraux + termes propres à la machine. */
    fun glossary(machineId: String?): List<GlossaryTerm> =
        glossary.filter { it.machineId == null || it.machineId == machine(machineId).id }

    /** Leçon à laquelle appartient une question de quiz. */
    fun lessonOfQuestion(questionId: String): Lesson? = lessons().firstOrNull { l -> l.quiz.any { it.id == questionId } }

    /** Première leçon non terminée de la machine, dans l'ordre du parcours. */
    fun nextLesson(machineId: String?, completed: Set<String>): Lesson? =
        machine(machineId).lessons.firstOrNull { it.id !in completed }
}
