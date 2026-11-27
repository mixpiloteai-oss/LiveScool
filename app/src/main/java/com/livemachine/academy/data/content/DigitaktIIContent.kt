package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule
import com.livemachine.academy.data.model.ManualDoc
import com.livemachine.academy.data.model.ManualRef

/** Référence au manuel Digitakt II (pages imprimées, identiques à celles du PDF). */
internal fun dt(chapter: String, pages: String) = ManualRef(chapter, pages, ManualDoc.DIGITAKT_II_MANUAL)

/**
 * Elektron Digitakt II.
 *
 * Source unique : Digitakt II User Manual, OS 1.17 (Elektron, en anglais), fichier docs/manual/elektron-digitakt-ii.pdf.
 * Aucune fonction n'est décrite sans sa page. Identifiants préfixés « dt2- ».
 */
object DigitaktIIContent {
    const val note =
        "Parcours rédigé d'après le Digitakt II User Manual pour l'OS 1.17 (Elektron, en anglais). Les noms des touches et " +
            "des menus sont ceux de la machine, en anglais. Si ta machine a un autre OS, certaines fonctions peuvent différer. " +
            "Ne pas confondre avec la Digitakt d'origine (Digitakt I)."
    const val sourcesLabel = "Elektron Digitakt II — User Manual, OS 1.17 (EN), p. 10–118."

    val modules: List<CourseModule> = DigitaktIIModulesA.modules + DigitaktIIModulesB.modules

    private val FACADE = dt("3.1 Front panel", "12–13")
    private val ARRIERE = dt("3.2 Rear connectors", "14")

    val sections: List<MachineSection> = listOf(
        MachineSection(
            "Écran et réglages",
            "Les huit encodeurs règlent les paramètres affichés à l'écran, placés comme eux.",
            listOf(
                cmd("DATA ENTRY A–H", "Paramètres affichés ; appuyer en tournant = grands pas.", FACADE),
                cmd("LEVEL/DATA", "Niveau de la piste active, défilement des listes.", FACADE),
                cmd("[YES] / [NO] / flèches", "Valider, revenir, naviguer.", FACADE),
                cmd("MAIN VOLUME", "Sorties principales et casque.", FACADE),
            ),
            FACADE,
        ),
        MachineSection(
            "Pages de paramètres",
            "Six touches ouvrent les pages de la piste active ; leurs fonctions orange ouvrent des menus.",
            listOf(
                cmd("[TRIG PARAMETERS]", "Note, vélocité, longueur, probabilité, conditions (orange : QUANTIZE).", FACADE),
                cmd("[SRC] / [FLTR] / [AMP]", "Source, filtre, enveloppe (orange : MACHINE, SETUP, SEQUENCER).", FACADE),
                cmd("[FX] / [MOD]", "Effets de piste, LFO (orange : SEND FX, MIXER).", FACADE),
            ),
            FACADE,
        ),
        MachineSection(
            "Séquenceur et transport",
            "Les 16 touches [TRIG] servent de pas, de sélecteurs et de clavier selon le mode.",
            listOf(
                cmd("[TRIG 1–16]", "Pas, pistes, patterns, clavier ; orange : QUICK MUTE.", FACADE),
                cmd("[TRK] / [PTN] / [SONG]", "Choix de piste, pattern, song (orange : MUTE, banque, édition du song).", FACADE),
                cmd("[RECORD] / [PLAY] / [STOP]", "Enregistrement et transport (orange : copier, effacer, coller).", FACADE),
                cmd("[PAGE] / [KEYBOARD] / [FUNC]", "Pages et FILL, mode clavier, fonctions orange.", FACADE),
            ),
            FACADE,
        ),
        MachineSection(
            "Prises arrière",
            "Sorties, entrées à échantillonner, MIDI et USB.",
            listOf(
                cmd("OUT L/R, HEADPHONES", "Sorties principales et casque.", ARRIERE),
                cmd("IN L/R", "Entrées pour sampler ou traiter une source externe.", ARRIERE),
                cmd("MIDI IN / OUT / THRU", "Synchro et pilotage d'autres machines.", ARRIERE),
                cmd("USB", "Ordinateur, MIDI et audio (classe compatible).", ARRIERE),
            ),
            ARRIERE,
        ),
    )
}
