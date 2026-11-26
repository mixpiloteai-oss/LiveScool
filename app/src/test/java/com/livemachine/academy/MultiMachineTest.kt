package com.livemachine.academy

import com.livemachine.academy.data.content.MachineCatalog
import com.livemachine.academy.data.model.AppSettings
import com.livemachine.academy.data.model.GlossaryCategory
import com.livemachine.academy.data.model.UserProgress
import com.livemachine.academy.data.repository.CourseRepository
import com.livemachine.academy.data.repository.MachinePhotoRepository
import com.livemachine.academy.domain.ProgressRules
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MultiMachineTest {
    private val repo = CourseRepository()
    private val electribe = MachineCatalog.ELECTRIBE_ID
    private val circuit = MachineCatalog.CIRCUIT_TRACKS_ID

    @Test
    fun `deux machines avec identifiants uniques`() {
        val ids = MachineCatalog.all.map { it.id }
        assertEquals(listOf(electribe, circuit), ids)
        assertEquals("Novation", MachineCatalog.circuitTracks.manufacturer)
        assertEquals("Korg", MachineCatalog.electribe.manufacturer)
    }

    @Test
    fun `l Electribe reste la machine par defaut`() {
        assertEquals(electribe, AppSettings().selectedMachineId)
        assertEquals(electribe, MachineCatalog.byId(null).id)
        assertEquals(electribe, MachineCatalog.byId("inconnue").id)
    }

    /** Les identifiants stockés dans la progression ne doivent jamais changer. */
    @Test
    fun `identifiants de progression de l Electribe inchanges`() {
        val modules = repo.modules(electribe)
        assertEquals((1..9).map { "m$it" }, modules.map { it.id })
        assertEquals("m1-l1", modules[0].lessons[0].id)
        assertEquals("m1-l1-ex", modules[0].lessons[0].exercise?.id)
        assertEquals("m1-l1-q1", modules[0].lessons[0].quiz[0].id)
        assertEquals("final-m1", ProgressRules.finalQuizId("m1"))
    }

    @Test
    fun `identifiants uniques entre machines`() {
        val modules = MachineCatalog.all.flatMap { it.modules }.map { it.id }
        val lessons = repo.lessons().map { it.id }
        assertEquals(modules.size, modules.toSet().size)
        assertEquals(lessons.size, lessons.toSet().size)
        MachineCatalog.circuitTracks.modules.forEach { assertTrue(it.id.startsWith("ct-")) }
    }

    @Test
    fun `progression ancienne relue pour l Electribe et separee de la Circuit Tracks`() {
        val old = UserProgress(completedLessons = repo.modules(electribe)[0].lessons.map { it.id }.toSet())
        assertTrue(ProgressRules.isValidated(repo.modules(electribe)[0], old))
        assertEquals(repo.modules(electribe)[1].lessons[0].id, repo.nextLesson(electribe, old.completedLessons)?.id)
        assertNull(repo.nextLesson(circuit, old.completedLessons))
        assertTrue(repo.exercises(circuit).isEmpty())
        assertTrue(repo.challenges(circuit).isEmpty())
    }

    @Test
    fun `glossaire : termes machine seulement pour leur machine`() {
        assertTrue(repo.glossary(electribe).any { it.category == GlossaryCategory.MACHINE })
        assertTrue(repo.glossary(circuit).none { it.category == GlossaryCategory.MACHINE })
        assertTrue(repo.glossary(circuit).isNotEmpty())
    }

    @Test
    fun `photo de l Electribe garde ses anciens noms`() {
        assertEquals("machine_photo.jpg", MachinePhotoRepository.photoFileName(electribe))
        assertEquals("photo_version", MachinePhotoRepository.versionKeyName(electribe))
        assertEquals("photo_marks", MachinePhotoRepository.marksKeyName(electribe))
        assertEquals("machine_photo_circuit-tracks.jpg", MachinePhotoRepository.photoFileName(circuit))
        assertEquals("photo_marks_circuit-tracks", MachinePhotoRepository.marksKeyName(circuit))
    }
}
