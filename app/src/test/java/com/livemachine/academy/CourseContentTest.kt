package com.livemachine.academy

import com.livemachine.academy.data.content.CourseContent
import com.livemachine.academy.data.content.GlossaryContent
import com.livemachine.academy.data.content.MachineContent
import com.livemachine.academy.data.model.ContentStatus
import com.livemachine.academy.data.model.GlossaryCategory
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
            assertTrue("${lesson.id} : explication manquante", !lesson.explanation.isNullOrBlank())
            assertTrue("${lesson.id} : commandes manquantes", lesson.controls.isNotEmpty())
            assertTrue("${lesson.id} : résultat attendu manquant", !lesson.expectedResult.isNullOrBlank())
            assertTrue("${lesson.id} : exercice manquant", lesson.exercise != null)
            assertTrue("${lesson.id} : erreurs fréquentes manquantes", lesson.commonMistakes.isNotEmpty())
            assertTrue("${lesson.id} : quiz trop court", lesson.quiz.size >= 3)
            lesson.quiz.forEach { q -> assertTrue("${q.id} : page manquante", q.manualRef != null) }
            lesson.controls.forEach { c -> assertTrue("${c.name} : page manquante", c.manualRef != null) }
        }
    }

    @Test
    fun `le module 1 est entierement verifie`() {
        val m1 = modules.first { it.id == "m1" }
        assertTrue(m1.lessons.isNotEmpty())
        m1.lessons.forEach { assertEquals("${it.id} non vérifiée", ContentStatus.VERIFIED, it.status) }
    }

    /** Toutes les pages citées doivent tomber dans la partie française du manuel (p. 20 à 38). */
    @Test
    fun `les pages citees existent dans la partie francaise du manuel`() {
        val refs = lessons.flatMap { l ->
            l.manualRefs + l.controls.mapNotNull { it.manualRef } + l.quiz.mapNotNull { it.manualRef }
        } + GlossaryContent.terms.mapNotNull { it.manualRef } +
            MachineContent.sections.flatMap { s -> s.controls.mapNotNull { it.manualRef } + s.manualRef }
        refs.forEach { ref ->
            val pages = Regex("\\d+").findAll(ref.pages).map { it.value.toInt() }.toList()
            assertTrue("Pages illisibles : ${ref.pages}", pages.isNotEmpty())
            pages.forEach { assertTrue("Page hors manuel FR : $it (${ref.chapter})", it in 20..38) }
        }
    }

    @Test
    fun `les termes machine du glossaire citent le manuel`() {
        GlossaryContent.terms.filter { it.category == GlossaryCategory.MACHINE }.forEach {
            assertTrue("${it.term} : page manquante", it.manualRef != null)
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
