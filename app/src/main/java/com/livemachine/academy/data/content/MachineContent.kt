package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.MachineControl
import com.livemachine.academy.data.model.ManualRef

data class MachineSection(
    val title: String,
    val summary: String,
    val controls: List<MachineControl>,
    val manualRef: ManualRef,
)

/** Fiche de la machine, d'après le manuel d'utilisation (partie française). */
object MachineContent {
    const val brand = "Korg"
    const val modelLabel = "electribe sampler"
    const val modelNote =
        "Ta machine rose est l'electribe sampler (modèle electribe2s dans la table MIDI du manuel). " +
            "Le manuel d'utilisation couvre à la fois l'electribe et l'electribe sampler : les opérations de base " +
            "sont identiques, mais ses illustrations représentent l'electribe."
    const val manualSource =
        "Manuel d'utilisation electribe/electribe sampler (Korg, 2015), partie française, p. 20–38."

    private val P22 = ManualRef("Introduction › 1. Description par section", "22")
    private val P23 = ManualRef("Introduction › 1. Description par section", "22–23")
    private val P24 = ManualRef("Introduction › 1. Description par section", "24")
    private val P25 = ManualRef("Introduction › 2. Connexions", "25")

    val sections: List<MachineSection> = listOf(
        MachineSection(
            "Alimentation et niveaux",
            "Allumer la machine et régler les niveaux de sortie et d'entrée.",
            listOf(
                MachineControl("Bouton d'alimentation", "Met l'instrument sous tension et hors tension.", P22),
                MachineControl("Volume", "Volume de la sortie ligne et de la prise casque.", P22),
                MachineControl("Input Level", "Niveau du signal reçu sur la prise Audio In.", P22),
            ),
            P22,
        ),
        MachineSection(
            "Écran et pad tactile",
            "L'écran affiche patterns, parts et paramètres. Le pad tactile pilote Gate Arpeggiator, Touch Scale ou l'effet maître.",
            listOf(
                MachineControl("Écran", "Informations sur les patterns, les parts et les paramètres.", P22),
                MachineControl("Gate Arp", "Active la fonction Gate Arpeggiator.", P22),
                MachineControl("Touch Scale", "Active la fonction Touch Scale.", P22),
                MachineControl("Master Fx", "Active/désactive la commande de l'effet maître au pad tactile.", P22),
                MachineControl("MFX Hold", "Garde l'état de l'effet maître sans les doigts sur le pad (Master Fx actif).", P22),
            ),
            P22,
        ),
        MachineSection(
            "Transport",
            "Lecture, arrêt, enregistrement et tempo.",
            listOf(
                MachineControl("Rec", "Lance l'enregistrement des données de jeu ; une nouvelle pression l'arrête et la lecture continue.", P22),
                MachineControl("Stop", "Arrête la lecture.", P22),
                MachineControl("Play/Pause", "Lance la lecture ou la met en pause.", P22),
                MachineControl("Tap", "Règle le tempo par quelques pressions successives.", P22),
            ),
            P22,
        ),
        MachineSection(
            "Section commune (navigation)",
            "Les commandes pour se déplacer dans les menus et sauvegarder.",
            listOf(
                MachineControl("Commande de valeur", "Change la valeur du paramètre affiché.", P22),
                MachineControl("< et >", "Choisissent le paramètre à éditer.", P23),
                MachineControl("Menu/Enter", "Ouvre les menus (pattern, part, Global, Utility) et valide.", P23),
                MachineControl("Exit", "Deux pressions : retour à l'écran de sélection de pattern.", P23),
                MachineControl("Shift", "Combinée à une autre commande : fonctions secondaires (Shift + Write, Shift + valeur…).", P23),
                MachineControl("Write", "Sauvegarde les patterns et les réglages Global.", P23),
            ),
            P23,
        ),
        MachineSection(
            "Section d'édition du son",
            "Modifie le son de la part sélectionnée. Détaillée au Module 3 (paramètres sonores) et au Module 5 (filtres et effets).",
            listOf(
                MachineControl("Oscillator", "Type de forme d'onde, hauteur (Pitch/Glide), Edit.", ManualRef("Description par section › Section d'édition", "23")),
                MachineControl("Filter", "Fréquence de coupure, Resonance, EG Int ; boutons LPF, HPF, BPF.", ManualRef("Description par section › Section d'édition", "23")),
                MachineControl("Modulation", "Type de modulation, Depth, Speed.", ManualRef("Description par section › Section d'édition", "23")),
                MachineControl("Amp/EG", "Level, Pan, Attack, Decay/Release ; boutons MFX Send et Amp EG.", ManualRef("Description par section › Section d'édition", "23")),
                MachineControl("Insert Fx", "Type d'effet d'insertion, Edit, bouton IFX On.", ManualRef("Description par section › Section d'édition", "23–24")),
            ),
            ManualRef("Description par section › Section d'édition", "23–24"),
        ),
        MachineSection(
            "Parts, mode de pad et pads",
            "Choisir une part, définir ce que font les pads, et jouer.",
            listOf(
                MachineControl("Boutons Step", "Position ou octave de la séquence, fonction actuelle des pads.", P24),
                MachineControl("<Part, Part>", "Choisissent la part ; l'écran indique la part sélectionnée.", P24),
                MachineControl("Part Mute / Part Erase", "Couper le son d'une part / effacer ses notes pendant le jeu.", P24),
                MachineControl("Trigger, Sequencer, Keyboard, Chord", "Pads en déclenchement, en entrée de pas, en clavier, en accords.", P24),
                MachineControl("Step Jump, Pattern Set", "Sauter à un pas pendant la lecture ; lancer un pattern mémorisé sous un pad.", P24),
                MachineControl("Pads de déclenchement", "Leur rôle dépend du mode de pad.", P24),
            ),
            P24,
        ),
        MachineSection(
            "Connectique (face arrière)",
            "Les prises de la machine.",
            listOf(
                MachineControl("Audio Out L/R", "Sorties audio ; seule la prise L branchée = sortie mono.", P25),
                MachineControl("Audio In", "Entrée d'une source audio externe.", P25),
                MachineControl("Sync In / Sync Out", "Synchronisation avec la série volca ou des séquenceurs analogiques.", P25),
                MachineControl("MIDI IN/OUT", "Via les câbles de conversion MIDI fournis.", P25),
                MachineControl("Casque, DC 9V, USB, fente carte SD", "Écoute, alimentation, ordinateur, carte mémoire.", P25),
            ),
            P25,
        ),
    )
}
