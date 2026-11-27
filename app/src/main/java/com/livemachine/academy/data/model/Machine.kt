package com.livemachine.academy.data.model

import com.livemachine.academy.data.content.MachineSection

/**
 * Une machine enseignée : son parcours, sa fiche, ses défis et ses commandes à repérer.
 * Les identifiants de modules/leçons doivent être uniques entre machines
 * (la progression est stockée par identifiant).
 */
data class MachineProfile(
    val id: String,
    val name: String,
    val manufacturer: String,
    val shortDescription: String,
    /** Précisions sur le modèle et les sources (affichées sur la fiche). */
    val note: String,
    /** Documents officiels utilisés, ou message demandant le manuel. */
    val sourcesLabel: String,
    val modules: List<CourseModule>,
    val sections: List<MachineSection> = emptyList(),
    val identifiableControls: List<IdentifiableControl> = emptyList(),
    val challenges: List<StyleChallenge> = emptyList(),
    /** Ateliers de création musicale guidés, propres à la machine. */
    val workshops: List<CreativeWorkshop> = emptyList(),
    /** Préfixe des identifiants de progression de la machine ("" = machine historique, l'Electribe). */
    val progressPrefix: String = "",
    val category: MachineCategory = MachineCategory.GROOVEBOX,
    /** Type précis affiché sur la fiche (ex. « Sampler de performance »). */
    val kind: String = category.label,
    /** Styles musicaux travaillés avec cette machine. */
    val styles: List<String> = emptyList(),
    /** Intérêt pédagogique (fiche de présentation générale, pas une leçon). */
    val pedagogy: String = "",
    /** Usage en live (présentation générale). */
    val liveUse: String = "",
    /** Manuels officiels du modèle exact, à déposer dans docs/manual/ (jamais versionnés). */
    val manuals: List<ManualSpec> = emptyList(),
) {
    val fullName: String get() = "$manufacturer $name"
    val lessons: List<Lesson> get() = modules.flatMap { it.lessons }
    val hasCourse: Boolean get() = lessons.isNotEmpty()
    val courseStatusLabel: String get() = if (hasCourse) "Cours disponible" else PENDING_COURSE_LABEL

    companion object {
        const val PENDING_COURSE_LABEL = "Cours en attente du manuel officiel"
    }
}

/** Catégories du catalogue. */
enum class MachineCategory(val label: String) {
    GROOVEBOX("Groovebox"),
    SAMPLER("Sampler"),
    DRUM_MACHINE("Boîte à rythmes"),
    SYNTH("Synthétiseur"),
    SEQUENCER("Séquenceur"),
    HYBRID("Machine hybride"),
}

/**
 * Manuel officiel attendu pour UN modèle exact.
 * [fileName] : nom du PDF à déposer dans docs/manual/ ; [title] : document du fabricant à fournir.
 */
data class ManualSpec(val fileName: String, val title: String)
