package com.livemachine.academy

import com.livemachine.academy.data.content.CatalogMachines
import com.livemachine.academy.data.content.MachineCatalog
import com.livemachine.academy.data.model.MachineCategory
import com.livemachine.academy.data.model.MachineProfile
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class CatalogTest {
    private val all = MachineCatalog.all
    /** Machines dont le cours est rédigé d'après leur manuel officiel. */
    private val withCourse = setOf(
        MachineCatalog.ELECTRIBE_ID, MachineCatalog.CIRCUIT_TRACKS_ID, CatalogMachines.digitaktII.id, CatalogMachines.drumBruteImpact.id,
        CatalogMachines.miniBrute2S.id, CatalogMachines.octatrackMkII.id, CatalogMachines.dfam.id, CatalogMachines.take5.id, CatalogMachines.deluge.id, CatalogMachines.sp404MkII.id,
        CatalogMachines.tr8s.id, CatalogMachines.td3.id, CatalogMachines.hydrasynthDesktop.id, CatalogMachines.op1Field.id,
    )
    private val pending = all.filter { it.id !in withCourse }

    @Test
    fun `catalogue de 20 machines aux identifiants et prefixes uniques`() {
        assertEquals(20, all.size)
        assertEquals(20, all.map { it.id }.toSet().size)
        val prefixes = all.map { it.progressPrefix }.filter { it.isNotEmpty() }
        assertEquals(19, prefixes.size)
        assertEquals(prefixes.size, prefixes.toSet().size)
        prefixes.forEach { p -> assertTrue(p, prefixes.none { it != p && it.startsWith(p) }) }
    }

    @Test
    fun `chaque fiche est complete`() {
        all.forEach { m ->
            assertTrue(m.id, m.manufacturer.isNotBlank() && m.name.isNotBlank() && m.kind.isNotBlank())
            assertTrue(m.id, m.shortDescription.isNotBlank() && m.pedagogy.isNotBlank() && m.liveUse.isNotBlank())
            assertTrue(m.id, m.styles.isNotEmpty())
            assertTrue(m.id, m.manuals.isNotEmpty() && m.manuals.all { it.fileName.endsWith(".pdf") && it.title.isNotBlank() })
        }
        assertEquals(MachineCategory.entries.toSet(), all.map { it.category }.toSet())
        assertTrue(all.map { it.manufacturer }.toSet().size >= 15)
    }

    @Test
    fun `un manuel par modele exact`() {
        val files = all.flatMap { m -> m.manuals.map { it.fileName } }
        assertEquals(files.size, files.toSet().size)
        CatalogMachines.all.forEach { m -> assertEquals(m.id, 1, m.manuals.size) }
    }

    @Test
    fun `aucun cours sans manuel officiel`() {
        assertEquals(withCourse, all.filter { it.hasCourse }.map { it.id }.toSet())
        assertEquals(6, pending.size)
        pending.forEach { m ->
            assertFalse(m.id, m.hasCourse)
            assertTrue(m.id, m.modules.isEmpty() && m.sections.isEmpty() && m.identifiableControls.isEmpty())
            assertTrue(m.id, m.challenges.isEmpty() && m.workshops.isEmpty())
            assertEquals(MachineProfile.PENDING_COURSE_LABEL, m.courseStatusLabel)
        }
    }

    @Test
    fun `cours existants conserves`() {
        assertEquals(54, MachineCatalog.electribe.lessons.size)
        assertEquals(36, MachineCatalog.circuitTracks.lessons.size)
        assertTrue(MachineCatalog.electribe.hasCourse && MachineCatalog.circuitTracks.hasCourse)
    }

    @Test
    fun `progression routee vers la bonne machine`() {
        CatalogMachines.all.forEach { m ->
            assertEquals(m.id, MachineCatalog.machineOfProgressId("${m.progressPrefix}m1-l1"))
            assertEquals(m.id, MachineCatalog.machineOfProgressId("final-${m.progressPrefix}m1"))
        }
        assertEquals(MachineCatalog.ELECTRIBE_ID, MachineCatalog.machineOfProgressId("m1-l1"))
    }

    @Test
    fun `liste des manuels a fournir a jour`() {
        val readme = listOf(File("../docs/manual/README.md"), File("docs/manual/README.md")).first { it.exists() }.readText()
        all.flatMap { it.manuals }.forEach { assertTrue(it.fileName, readme.contains(it.fileName)) }
    }
}
