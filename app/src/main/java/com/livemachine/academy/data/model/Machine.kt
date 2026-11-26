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
) {
    val fullName: String get() = "$manufacturer $name"
    val lessons: List<Lesson> get() = modules.flatMap { it.lessons }
    val hasCourse: Boolean get() = lessons.isNotEmpty()
}
