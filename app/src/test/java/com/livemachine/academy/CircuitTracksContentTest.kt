package com.livemachine.academy

import com.livemachine.academy.data.content.CircuitTracksContent
import com.livemachine.academy.data.content.MachineCatalog
import com.livemachine.academy.data.model.ContentStatus
import com.livemachine.academy.data.model.ManualDoc
import com.livemachine.academy.data.model.ManualRef
import com.livemachine.academy.domain.ProgressRules
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CircuitTracksContentTest {
    private val modules = CircuitTracksContent.modules
    private val lessons = modules.flatMap { it.lessons }

    @Test
    fun `douze modules dans l'ordre avec identifiants ct`() {
        assertEquals(12, modules.size)
        assertEquals((1..12).toList(), modules.map { it.number })
        assertEquals((1..12).map { "ct-m$it" }, modules.map { it.id })
        assertEquals(modules, MachineCatalog.circuitTracks.modules)
    }

    @Test
    fun `identifiants uniques et prefixes`() {
        val ids = lessons.map { it.id } + lessons.mapNotNull { it.exercise?.id } + lessons.flatMap { l -> l.quiz.map { it.id } } +
            CircuitTracksContent.challenges.map { it.id } + CircuitTracksContent.identifiableControls.map { it.id }
        assertEquals(ids.size, ids.toSet().size)
        ids.forEach { assertTrue("$it sans préfixe ct-", it.startsWith("ct-")) }
        modules.forEach { m -> m.lessons.forEach { assertEquals(m.id, it.moduleId) } }
    }

    @Test
    fun `toutes les lecons sont verifiees et completes`() {
        assertTrue(lessons.isNotEmpty())
        lessons.forEach { l ->
            assertEquals("${l.id} non vérifiée", ContentStatus.VERIFIED, l.status)
            assertTrue(l.id, l.manualRefs.isNotEmpty() && l.steps.isNotEmpty() && l.controls.isNotEmpty())
            assertTrue(l.id, !l.objective.isNullOrBlank() && !l.explanation.isNullOrBlank() && !l.expectedResult.isNullOrBlank())
            assertTrue(l.id, l.exercise != null && l.commonMistakes.isNotEmpty() && l.quiz.size >= 3)
        }
    }

    @Test
    fun `chaque module a assez de questions pour le quiz final`() {
        modules.forEach { m ->
            assertTrue("${m.id}", m.lessons.sumOf { it.quiz.size } >= ProgressRules.FINAL_QUIZ_SIZE)
        }
    }

    /** Toutes les références citent le guide Circuit Tracks, à des pages qui existent. */
    @Test
    fun `references au guide Circuit Tracks valides`() {
        val refs: List<ManualRef> = lessons.flatMap { l ->
            l.manualRefs + l.controls.mapNotNull { it.manualRef } + l.quiz.mapNotNull { it.manualRef }
        } + CircuitTracksContent.sections.flatMap { s -> s.controls.mapNotNull { it.manualRef } + s.manualRef } +
            CircuitTracksContent.identifiableControls.map { it.manualRef } +
            CircuitTracksContent.challenges.flatMap { it.manualRefs } +
            CircuitTracksContent.glossary.mapNotNull { it.manualRef }
        lessons.forEach { l -> l.quiz.forEach { assertTrue(it.id, it.manualRef != null) } }
        refs.forEach { ref ->
            assertEquals(ref.chapter, ManualDoc.CIRCUIT_TRACKS_GUIDE, ref.doc)
            val pages = Regex("\\d+").findAll(ref.pages).map { it.value.toInt() }.toList()
            assertTrue("Pages illisibles : ${ref.pages}", pages.isNotEmpty())
            pages.forEach { assertTrue("Page $it hors guide (${ref.chapter})", it in ref.doc.validPages) }
        }
    }

    @Test
    fun `les defis visent des modules existants`() {
        val ids = modules.map { it.id }.toSet()
        assertTrue(CircuitTracksContent.challenges.isNotEmpty())
        CircuitTracksContent.challenges.forEach { assertTrue(it.requiredModuleId, it.requiredModuleId in ids) }
    }
}
