package com.livemachine.academy.data.content

/**
 * Fiche de la machine. Les zones de façade et leurs commandes seront
 * détaillées uniquement d'après le manuel officiel.
 */
object MachineContent {
    const val brand = "Korg"
    const val modelLabel = "Electribe Sampler 2 (rose)"
    const val modelNote =
        "Nom exact du modèle et version du système à confirmer avec le manuel officiel " +
            "que tu fourniras. Aucune commande n'est décrite avant cette vérification."

    /** Zones prévues pour la fiche ; le contenu viendra du manuel. */
    val plannedSections = listOf(
        "Façade : vue d'ensemble",
        "Écran et navigation",
        "Pads et touches de pas",
        "Potentiomètres de son",
        "Section filtre et effets",
        "Transport et tempo",
        "Connectique (face arrière)",
    )
}
