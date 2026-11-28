package com.livemachine.academy

import com.livemachine.academy.data.content.CatalogMachines
import com.livemachine.academy.data.content.DelugeContent
import com.livemachine.academy.data.content.MachineCatalog
import com.livemachine.academy.data.model.ContentStatus
import com.livemachine.academy.data.model.ManualDoc
import com.livemachine.academy.data.model.ManualRef
import com.livemachine.academy.domain.ProgressRules
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DelugeContentTest {
    private val modules = DelugeContent.modules
    private val lessons = modules.flatMap { it.lessons }

    @Test
    fun `sept modules de trois lecons avec identifiants dlg`() {
        assertEquals((1..7).toList(), modules.map { it.number })
        assertEquals((1..7).map { "dlg-m$it" }, modules.map { it.id })
        modules.forEach { assertEquals(it.id, 3, it.lessons.size) }
        assertEquals(modules, CatalogMachines.deluge.modules)
        assertTrue(MachineCatalog.all.contains(CatalogMachines.deluge))
    }

    @Test
    fun `identifiants uniques prefixes et routes vers le Deluge`() {
        val ids = lessons.map { it.id } + lessons.mapNotNull { it.exercise?.id } + lessons.flatMap { l -> l.quiz.map { it.id } }
        assertEquals(ids.size, ids.toSet().size)
        ids.forEach {
            assertTrue("$it sans préfixe dlg-", it.startsWith("dlg-"))
            assertEquals(it, CatalogMachines.deluge.id, MachineCatalog.machineOfProgressId(it))
        }
        modules.forEach { m ->
            m.lessons.forEach { assertEquals(m.id, it.moduleId) }
            assertEquals(CatalogMachines.deluge.id, MachineCatalog.machineOfProgressId(ProgressRules.finalQuizId(m.id)))
        }
    }

    @Test
    fun `toutes les lecons sont verifiees et completes`() {
        lessons.forEach { l ->
            assertEquals("${l.id} non vérifiée", ContentStatus.VERIFIED, l.status)
            assertTrue(l.id, l.manualRefs.isNotEmpty() && l.steps.size >= 3 && l.controls.isNotEmpty())
            assertTrue(l.id, !l.objective.isNullOrBlank() && !l.explanation.isNullOrBlank() && !l.expectedResult.isNullOrBlank())
            assertTrue(l.id, l.exercise != null && l.commonMistakes.isNotEmpty() && l.quiz.size >= 4)
            l.quiz.forEach { q -> assertTrue(q.id, q.correctIndex in q.options.indices) }
        }
        modules.forEach { m -> assertTrue(m.id, m.lessons.sumOf { it.quiz.size } >= ProgressRules.FINAL_QUIZ_SIZE) }
    }

    /** Toutes les références citent le manuel Deluge, à des pages qui existent. */
    @Test
    fun `references au manuel Deluge valides`() {
        val refs: List<ManualRef> = lessons.flatMap { l ->
            l.manualRefs + l.controls.mapNotNull { it.manualRef } + l.quiz.mapNotNull { it.manualRef }
        } + DelugeContent.sections.flatMap { s -> s.controls.mapNotNull { it.manualRef } + s.manualRef }
        lessons.forEach { l -> l.quiz.forEach { assertTrue(it.id, it.manualRef != null) } }
        refs.forEach { ref ->
            assertEquals(ref.chapter, ManualDoc.DELUGE_GUIDE, ref.doc)
            val pages = Regex("\\d+").findAll(ref.pages).map { it.value.toInt() }.toList()
            assertTrue("Pages illisibles : ${ref.pages}", pages.isNotEmpty())
            pages.forEach { assertTrue("Page $it hors manuel (${ref.chapter})", it in ref.doc.validPages) }
        }
    }
}
