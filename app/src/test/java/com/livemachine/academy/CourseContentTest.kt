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
    fun `les modules 1 a 5 sont entierement verifies`() {
        listOf("m1", "m2", "m3", "m4", "m5").forEach { id ->
            val module = modules.first { it.id == id }
            assertTrue("$id sans leçons", module.lessons.isNotEmpty())
            module.lessons.forEach { assertEquals("${it.id} non vérifiée", ContentStatus.VERIFIED, it.status) }
        }
    }

    /** Les pages citées doivent exister dans le document cité (manuel FR p. 20–38, guide p. 3–19). */
    @Test
    fun `les pages citees existent dans le document cite`() {
        val refs = lessons.flatMap { l ->
            l.manualRefs + l.controls.mapNotNull { it.manualRef } + l.quiz.mapNotNull { it.manualRef }
        } + GlossaryContent.terms.mapNotNull { it.manualRef } +
            MachineContent.sections.flatMap { s -> s.controls.mapNotNull { it.manualRef } + s.manualRef }
        refs.forEach { ref ->
            val pages = Regex("\\d+").findAll(ref.pages).map { it.value.toInt() }.toList()
            assertTrue("Pages illisibles : ${ref.pages}", pages.isNotEmpty())
            pages.forEach { assertTrue("Page $it hors ${ref.doc.label} (${ref.chapter})", it in ref.doc.validPages) }
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
