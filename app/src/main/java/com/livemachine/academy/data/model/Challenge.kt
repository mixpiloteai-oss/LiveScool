package com.livemachine.academy.data.model

enum class MusicStyle(val label: String) {
    HARDTEK("Hardtek"),
    TRIBE("Tribe"),
    ACIDCORE("Acidcore"),
    MENTALCORE("Mentalcore"),
}

/** Défi pratique, à réaliser sur la vraie machine, validé par l'utilisateur. */
data class StyleChallenge(
    val id: String,
    val style: MusicStyle,
    val title: String,
    /** Module qu'il faut avoir validé pour débloquer le défi. */
    val requiredModuleId: String,
    val goal: String,
    val steps: List<String>,
    val successCriteria: String,
    val manualRefs: List<ManualRef>,
)

/** Commande à repérer sur la photo de l'utilisateur. */
data class IdentifiableControl(
    val id: String,
    val name: String,
    val hint: String,
    val manualRef: ManualRef,
)

/** Types d'ateliers de création musicale. */
enum class WorkshopKind(val label: String) {
    PATTERN("Programmer un pattern"),
    VARIATION("Créer une variation"),
    AUTOMATION("Modifier une automation"),
    MACROS("Utiliser les macros"),
    TRANSITION("Préparer une transition"),
    LIVE_SEQUENCE("Construire une séquence live"),
}

/**
 * Atelier de création, réalisé sur la vraie machine : étapes cochables
 * (progression « id#index »), puis validation manuelle du résultat.
 */
data class CreativeWorkshop(
    val id: String,
    val kind: WorkshopKind,
    val title: String,
    /** Module qu'il faut avoir validé pour ouvrir l'atelier. */
    val requiredModuleId: String,
    val goal: String,
    val steps: List<String>,
    val successCriteria: String,
    val manualRefs: List<ManualRef>,
)
