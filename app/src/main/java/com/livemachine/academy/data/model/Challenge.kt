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
