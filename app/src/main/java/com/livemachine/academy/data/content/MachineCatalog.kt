package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.MachineProfile

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
    )

    val circuitTracks = MachineProfile(
        id = CIRCUIT_TRACKS_ID,
        name = "Circuit Tracks",
        manufacturer = "Novation",
        shortDescription = CircuitTracksContent.shortDescription,
        note = CircuitTracksContent.note,
        sourcesLabel = CircuitTracksContent.sourcesLabel,
        modules = CircuitTracksContent.modules,
    )

    val all: List<MachineProfile> = listOf(electribe, circuitTracks)

    fun byId(id: String?): MachineProfile = all.firstOrNull { it.id == id } ?: electribe
}
