package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.MachineCategory
import com.livemachine.academy.data.model.MachineProfile
import com.livemachine.academy.data.model.ManualSpec

/** Les machines proposées dans l'app. */
object MachineCatalog {
    /** Identifiant historique : la progression existante appartient à cette machine. */
    const val ELECTRIBE_ID = "electribe-sampler"
    const val CIRCUIT_TRACKS_ID = "circuit-tracks"

    val electribe = MachineProfile(
        id = ELECTRIBE_ID,
        name = "Electribe Sampler 2",
        manufacturer = "Korg",
        shortDescription = "Station de production dance à samples, pads et séquenceur, version rose.",
        note = MachineContent.modelNote,
        sourcesLabel = MachineContent.manualSource,
        modules = CourseContent.modules,
        sections = MachineContent.sections,
        identifiableControls = MachineContent.identifiableControls,
        challenges = ChallengeContent.challenges,
        category = MachineCategory.GROOVEBOX,
        kind = "Groovebox sampler",
        styles = listOf("Hardtek", "Tribe", "Acidcore", "Mentalcore"),
        pedagogy = "Apprendre toute la chaîne d'un morceau sur une seule machine : samples, pads, séquenceur, effets et arrangement.",
        liveUse = "Enchaînement de patterns, jeu aux pads et effets en direct.",
        manuals = listOf(
            ManualSpec("ER2ER2S_OM_EFGS2.pdf", "manuel d'utilisation electribe / electribe sampler (Korg)"),
            ManualSpec("39d238e0f840ee25e68f1618517b4731.pdf", "electribe sampler Parameter Guide (Korg)"),
        ),
    )

    val circuitTracks = MachineProfile(
        id = CIRCUIT_TRACKS_ID,
        name = "Circuit Tracks",
        manufacturer = "Novation",
        shortDescription = CircuitTracksContent.shortDescription,
        note = CircuitTracksContent.note,
        sourcesLabel = CircuitTracksContent.sourcesLabel,
        modules = CircuitTracksContent.modules,
        sections = CircuitTracksContent.sections,
        identifiableControls = CircuitTracksContent.identifiableControls,
        challenges = CircuitTracksContent.challenges,
        workshops = CircuitTracksWorkshops.workshops,
        progressPrefix = "ct-",
        category = MachineCategory.GROOVEBOX,
        kind = "Groovebox synthé et batterie",
        styles = listOf("Techno", "Tribe", "House"),
        pedagogy = "Comprendre les pistes synthé, batterie et MIDI d'une groovebox complète, avec macros et automations.",
        liveUse = "Patterns, mutes, macros et transitions joués en direct.",
        manuals = listOf(ManualSpec("Circuit Tracks User Guide Version 2 - FR.pdf", "Circuit Tracks User Guide V2, français (Novation)")),
    )

    /** Catalogue complet : 20 machines (2 avec cours, 18 en attente de leur manuel officiel). */
    val all: List<MachineProfile> = listOf(electribe, circuitTracks) + CatalogMachines.all

    fun byId(id: String?): MachineProfile = all.firstOrNull { it.id == id } ?: electribe

    /**
     * Machine propriétaire d'un identifiant de progression (leçon, exercice, question, étape,
     * défi, atelier, ou quiz final « final-<module> »). Sans préfixe connu : l'Electribe.
     */
    fun machineOfProgressId(id: String): String {
        val base = id.substringBefore('#').removePrefix("final-")
        return all.firstOrNull { it.progressPrefix.isNotEmpty() && base.startsWith(it.progressPrefix) }?.id ?: ELECTRIBE_ID
    }
}
