package com.livemachine.academy

import com.livemachine.academy.data.content.CourseContent
import com.livemachine.academy.data.content.GlossaryContent
import com.livemachine.academy.data.model.ContentStatus
import com.livemachine.academy.data.repository.CourseRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CourseContentTest {
    private val modules = CourseContent.modules
    private val lessons = CourseContent.lessons

    @Test
    fun `les neuf modules prevus sont presents dans l'ordre`() {
        assertEquals(9, modules.size)
        assertEquals((1..9).toList(), modules.map { it.number })
    }

    @Test
    fun `identifiants uniques`() {
        assertEquals(modules.size, modules.map { it.id }.toSet().size)
        assertEquals(lessons.size, lessons.map { it.id }.toSet().size)
        assertEquals(GlossaryContent.terms.size, GlossaryContent.terms.map { it.term }.toSet().size)
    }

    @Test
    fun `chaque lecon appartient a son module`() {
        modules.forEach { m -> m.lessons.forEach { assertEquals(m.id, it.moduleId) } }
    }

    /** Règle du projet : rien n'est « vérifié » sans référence au manuel officiel. */
    @Test
    fun `une lecon verifiee cite le manuel partout`() {
        lessons.filter { it.status == ContentStatus.VERIFIED }.forEach { lesson ->
            assertTrue("${lesson.id} : pages du manuel manquantes", lesson.manualRefs.isNotEmpty())
            assertTrue("${lesson.id} : objectif manquant", !lesson.objective.isNullOrBlank())
            assertTrue("${lesson.id} : étapes manquantes", lesson.steps.isNotEmpty())
            assertTrue("${lesson.id} : quiz manquant", lesson.quiz.isNotEmpty())
            lesson.quiz.forEach { q -> assertTrue("${q.id} : page manquante", q.manualRef != null) }
            lesson.controls.forEach { c -> assertTrue("${c.name} : page manquante", c.manualRef != null) }
        }
    }

    @Test
    fun `lecon suivante suit l'ordre du parcours`() {
        val repo = CourseRepository()
        val first = repo.lessons().first()
        assertEquals(first.id, repo.nextLesson(emptySet())?.id)
        assertEquals(repo.lessons().getOrNull(1)?.id, repo.nextLesson(setOf(first.id))?.id)
    }
}
