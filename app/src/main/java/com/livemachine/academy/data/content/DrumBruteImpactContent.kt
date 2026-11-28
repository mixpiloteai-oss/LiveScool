package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule
import com.livemachine.academy.data.model.ManualDoc
import com.livemachine.academy.data.model.ManualRef

/** Référence au manuel DrumBrute Impact (pages imprimées ; page du PDF = page imprimée + 6). */
internal fun dbi(chapter: String, pages: String) = ManualRef(chapter, pages, ManualDoc.DRUMBRUTE_IMPACT_MANUAL)

/**
 * Arturia DrumBrute Impact.
 *
 * Source unique : Manuel utilisateur DrumBrute Impact v1.0 (Arturia, 2018, en français),
 * fichier docs/manual/arturia-drumbrute-impact.pdf. Identifiants préfixés « dbi- ».
 * Les réglages accessibles seulement par le logiciel MIDI Control Center ne sont pas enseignés (cours 100 % machine).
 */
object DrumBruteImpactContent {
    const val note =
        "Parcours rédigé d'après le Manuel utilisateur du DrumBrute Impact, version 1.0 (Arturia, 2018, en français). " +
            "Les réglages qui demandent le logiciel MIDI Control Center ne sont pas enseignés ici. Ne pas confondre avec la " +
            "DrumBrute d'origine, plus grande."
    const val sourcesLabel = "Arturia DrumBrute Impact — Manuel utilisateur v1.0 (FR), p. 4–74."

    val modules: List<CourseModule> = DrumBruteImpactModulesA.modules + DrumBruteImpactModulesB.modules

    private val HAUT = dbi("1.2 Transport, motifs, effets", "6–12")
    private val INSTR = dbi("1.4 Instruments, pads, Color", "15–16")
    private val UTILS = dbi("1.3 Les fonctions utilitaires", "13–14")
    private val ARRIERE = dbi("1.5–1.6 Panneau arrière", "17–19")

    val sections: List<MachineSection> = listOf(
        MachineSection(
            "Transport, modes et motifs",
            "Partie haute : tempo, transport, modes, boutons de Pas, copie/effacement/sauvegarde.",
            listOf(
                cmd("Affichage Tempo/Value, Rate/Fine, Tap Tempo", "Tempo et valeurs de Swing/Random.", HAUT),
                cmd("Record / Stop / Play", "Enregistrement et transport (Play = pause).", HAUT),
                cmd("Song / Bank / Ptrn / Step / Accent", "Modes de la machine.", HAUT),
                cmd("16 boutons de Pas, Last Step, << >>", "Motifs, pas, longueur et groupes de pas.", HAUT),
                cmd("Copy / Erase / Save", "Copier, effacer, sauvegarder.", HAUT),
            ),
            HAUT,
        ),
        MachineSection(
            "Effets de motif et sortie",
            "Swing, Random, distorsion du mix, niveau, bande tactile.",
            listOf(
                cmd("Swing / Random / Current Track", "Groove et imprévisibilité, global ou par instrument.", HAUT),
                cmd("Distortion + bouton, Level", "Saturation du mix et niveau de sortie.", HAUT),
                cmd("Bande tactile", "Roller, Looper, Step Repeat.", HAUT),
            ),
            HAUT,
        ),
        MachineSection(
            "Instruments et pads",
            "Dix instruments analogiques sur huit pads, chacun avec ses potentiomètres et son effet Color.",
            listOf(
                cmd("Potentiomètres d'instruments", "Decay, Pitch, Tone… et Level de chaque canal.", INSTR),
                cmd("Boutons Type", "Choix Tom Hi/Low et Cymbal/Cowbell.", INSTR),
                cmd("8 pads sensibles à la vélocité", "Jeu et saisie des notes.", INSTR),
                cmd("Color (Shift + Color = Color Hold)", "Effet Color par instrument ou par pas.", INSTR),
                cmd("Métronome On / Metro Volume, Mute / Solo, Shift", "Fonctions utilitaires.", UTILS),
            ),
            INSTR,
        ),
        MachineSection(
            "Panneau arrière",
            "Horloge, MIDI, USB, alimentation, casque, sortie Mix et sorties individuelles.",
            listOf(
                cmd("Clock In/Out, MIDI In/Out, USB", "Synchronisation et ordinateur.", ARRIERE),
                cmd("DC 12 V et interrupteur", "Alimentation fournie obligatoire.", ARRIERE),
                cmd("Casque et sortie Mix", "Sorties principales.", ARRIERE),
                cmd("Sorties individuelles", "Kick, Snares, Hi Hats, FM Drum.", ARRIERE),
            ),
            ARRIERE,
        ),
    )
}
