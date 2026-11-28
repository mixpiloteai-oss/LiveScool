package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule
import com.livemachine.academy.data.model.ManualDoc
import com.livemachine.academy.data.model.ManualRef

/** Référence au guide officiel du Deluge (pages imprimées ; page du PDF = page imprimée + 6). */
internal fun dlg(chapter: String, pages: String) = ManualRef(chapter, pages, ManualDoc.DELUGE_GUIDE)

/**
 * Synthstrom Deluge.
 *
 * Source unique : Deluge Official Guidebook, OS 4.1.0 (Synthstrom Audible, en anglais),
 * fichier docs/manual/synthstrom-deluge.pdf. Identifiants préfixés « dlg- ».
 * Non couverts pour l'instant : pistes audio et looping, vue Arranger, MIDI, CV, effets de chanson détaillés.
 */
object DelugeContent {
    const val note =
        "Parcours rédigé d'après le Deluge Official Guidebook, OS 4.1.0 (Synthstrom Audible, en anglais). Il couvre la prise en " +
            "main, le séquenceur, les gammes, les synthés, les kits et la vue Song. Les pistes audio, le looping, la vue Arranger, " +
            "le MIDI et le CV ne sont pas encore enseignés. Les fonctions peuvent différer avec un autre firmware."
    const val sourcesLabel = "Synthstrom Deluge — Official Guidebook OS 4.1.0 (EN), p. 3–151."

    val modules: List<CourseModule> = DelugeModulesA.modules + DelugeModulesB.modules

    private val FACADE = dlg("1.3 Hardware Overview", "4–5")
    private val ARRIERE = dlg("1.3 Hardware Overview, Rear Panel", "6–8")
    private val VUES = dlg("2.4 Views", "18")
    private val AFFECT = dlg("2.8 Clip View: Parameter Affect Group", "27–29")
    private val SEQ = dlg("3.4 Recording Pattern Sequences", "40–43")
    private val SCALE = dlg("3.6 Note Scales", "50–53")
    private val EDITEUR = dlg("4.8 Sound Editor", "90–93")
    private val KIT = dlg("5.2–5.3 Kit Basics, Kit Presets & Samples", "109–115")
    private val SONG = dlg("7.2–7.5 Song View, Sections", "137–146")
    private val SAVE = dlg("7.6 Saving & Loading Songs", "147–150")

    val sections: List<MachineSection> = listOf(
        MachineSection(
            "Façade et grille",
            "Grille de 16 × 8 pads, colonnes Mute / Launch et Audition / Section, boutons de vue et de type de clip.",
            listOf(
                cmd("Grille 16 × 8", "Séquençage, raccourcis avec SHIFT, clavier de texte.", FACADE),
                cmd("Mute / Launch, Audition / Section", "Couper, lancer, écouter, sections.", FACADE),
                cmd("SONG, CLIP, KEYBOARD", "Vues principales.", VUES),
                cmd("SYNTH, KIT, MIDI, CV, SCALE, CROSS SCREEN", "Type de clip et modes.", FACADE),
            ),
            FACADE,
        ),
        MachineSection(
            "Commandes rotatives et transport",
            "SELECT, SCROLL ◄► et ▼▲, UPPER / LOWER, TEMPO, LEVEL ; PLAY, RECORD, SHIFT.",
            listOf(
                cmd("(SELECT)", "Presets, paramètres, menus.", FACADE),
                cmd("(SCROLL ◄►) / (SCROLL ▼▲)", "Déplacement et zoom dans la grille.", FACADE),
                cmd("(UPPER) / (LOWER) + 8 boutons de paramètres", "Groupe de paramètres rapides.", AFFECT),
                cmd("(TEMPO), TAP, (OUTPUT LEVEL)", "Tempo et volume.", FACADE),
                cmd("PLAY, RECORD, SHIFT", "Transport et fonctions secondaires.", FACADE),
            ),
            AFFECT,
        ),
        MachineSection(
            "Séquenceur, sons et chansons",
            "Clips synthé et kit, gammes, éditeur de son, vue Song et sauvegarde.",
            listOf(
                cmd("Pads de la grille", "Notes pas à pas.", SEQ),
                cmd("SCALE, SHIFT + SCALE", "Gamme et changement de gamme.", SCALE),
                cmd("Éditeur de son : SELECT ou SHIFT + pad", "Tous les paramètres du son.", EDITEUR),
                cmd("AUDITION + KIT", "Charger un sample sur une ligne de kit.", KIT),
                cmd("LAUNCH, SECTION", "Lancer clips et sections.", SONG),
                cmd("SAVE, LOAD", "Chansons sur la carte SD.", SAVE),
            ),
            SEQ,
        ),
        MachineSection(
            "Prises et alimentation",
            "Entrée ligne, micro, sorties L/Mono et R, casque, MIDI, USB, CV/Gate, Clock In, alimentation 9–12 V centre négatif, batterie.",
            listOf(
                cmd("Line Out L / Mono et R", "Sorties 6,35 mm ; L seule = mono.", ARRIERE),
                cmd("Headphones, Line In, Mic", "Casque et entrées.", ARRIERE),
                cmd("MIDI In / Out, USB", "MIDI et alimentation USB.", ARRIERE),
                cmd("PSU 9–12 V DC centre négatif, interrupteur", "Alimentation et mise en marche.", ARRIERE),
            ),
            ARRIERE,
        ),
    )
}
