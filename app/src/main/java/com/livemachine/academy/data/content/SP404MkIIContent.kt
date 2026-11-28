package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule
import com.livemachine.academy.data.model.ManualDoc
import com.livemachine.academy.data.model.ManualRef

/** Référence au manuel de référence du SP-404MKII (pages imprimées = pages du PDF). */
internal fun sp4(chapter: String, pages: String) = ManualRef(chapter, pages, ManualDoc.SP404MKII_MANUAL)

/**
 * Roland SP-404MKII.
 *
 * Source unique : Manuel de référence SP-404MKII (Roland, en français, version logicielle 2.00 ou ultérieure),
 * fichier docs/manual/roland-sp-404mkii.pdf (pages lues en images, le texte n'étant pas extractible).
 * Identifiants préfixés « sp4- ». Non couverts : USB Audio, projets, paramètres UTILITY, import/export, liste MFX détaillée.
 */
object SP404MkIIContent {
    const val note =
        "Parcours rédigé d'après le Manuel de référence du SP-404MKII (Roland, en français), pour la version logicielle 2.00 ou " +
            "ultérieure. Il couvre le jeu des échantillons, les effets, l'échantillonnage, l'édition, le séquenceur de motifs, le tempo " +
            "et le mode DJ. Les réglages UTILITY, l'import/export et le détail de chaque effet MFX ne sont pas encore enseignés."
    const val sourcesLabel = "Roland SP-404MKII — Manuel de référence (FR), p. 4–78."

    val modules: List<CourseModule> = SP404MkIIModulesA.modules + SP404MkIIModulesB.modules

    private val CONNEXIONS = sp4("Connexion d'un équipement externe", "6")
    private val ALIM = sp4("À propos de l'alimentation, Mise sous/hors tension", "8–10")
    private val LECTURE = sp4("Lecture des échantillons (MODE SAMPLE)", "11–12")
    private val JEU = sp4("GATE, LOOP, REVERSE, ROLL", "14–17")
    private val EFFETS = sp4("Utilisation des effets", "27–29")
    private val ECH = sp4("Échantillonnage", "35–37")
    private val EDIT = sp4("Modification d'un échantillon", "43–51")
    private val PATTERN = sp4("Pattern SEQUENCER", "58–62")
    private val TEMPO = sp4("Réglage du Tempo", "74–77")
    private val DJ = sp4("Mixage des échantillons (MODE DJ)", "78")

    val sections: List<MachineSection> = listOf(
        MachineSection(
            "Pads et banques",
            "16 pads, 10 banques d'échantillons (A à J) et 10 banques de motifs, regroupées en projets.",
            listOf(
                cmd("Pads [1] à [16]", "Jouer les échantillons ou les motifs.", LECTURE),
                cmd("Bank [A/F] à [E/J]", "Changer de banque.", LECTURE),
                cmd("GATE, LOOP, REVERSE, ROLL, HOLD", "Manières de jouer un échantillon.", JEU),
                cmd("SHIFT, EXIT, VALUE", "Fonctions secondaires, sortie, réglages.", LECTURE),
            ),
            LECTURE,
        ),
        MachineSection(
            "Effets",
            "Boutons d'effet, molettes CTRL 1 à 3 et quatre bus d'effets.",
            listOf(
                cmd("FILTER+DRIVE, RESONATOR, DELAY, ISOLATOR, DJFX LOOPER, MFX", "Choisir l'effet.", EFFETS),
                cmd("CTRL 1, CTRL 2, CTRL 3", "Régler l'effet.", EFFETS),
                cmd("BUS FX, REMAIN", "Bus 1 / 2 et envoi de chaque pad.", EFFETS),
            ),
            EFFETS,
        ),
        MachineSection(
            "Échantillonnage, édition et motifs",
            "REC, RESAMPLE, RECORD SETTING, START/END, MARK, PITCH/SPEED, PATTERN SELECT, PATTERN EDIT, SUB PAD.",
            listOf(
                cmd("REC, RECORD SETTING, RESAMPLE", "Enregistrer et rééchantillonner.", ECH),
                cmd("START/END, MARK, PITCH/SPEED", "Éditer un échantillon.", EDIT),
                cmd("PATTERN SELECT, PATTERN EDIT, SUB PAD", "Séquenceur de motifs.", PATTERN),
                cmd("BPM SYNC, SHIFT + pad [11]", "Tempo et synchronisation.", TEMPO),
                cmd("Bank [D/I] + [E/J]", "Mode DJ.", DJ),
            ),
            PATTERN,
        ),
        MachineSection(
            "Prises et alimentation",
            "DC IN, POWER, USB-C, MIDI, LINE IN, LINE OUT, PHONES, INPUT (micro/guitare), piles.",
            listOf(
                cmd("LINE OUT", "Vers l'ampli ou les enceintes.", CONNEXIONS),
                cmd("LINE IN, INPUT, MIC/GUITAR", "Entrées instrument, micro ou guitare.", CONNEXIONS),
                cmd("DC IN, USB, piles, POWER", "Alimentation et mise en marche.", ALIM),
                cmd("VOLUME", "Volume général.", ALIM),
            ),
            CONNEXIONS,
        ),
    )
}
