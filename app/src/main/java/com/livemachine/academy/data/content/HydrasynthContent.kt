package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule
import com.livemachine.academy.data.model.ManualDoc
import com.livemachine.academy.data.model.ManualRef

/** Référence au manuel de l'Hydrasynth (pages imprimées = pages du PDF). */
internal fun hyd(chapter: String, pages: String) = ManualRef(chapter, pages, ManualDoc.HYDRASYNTH_MANUAL)

/**
 * ASM Hydrasynth Desktop.
 *
 * Source unique : Hydrasynth Owner's Manual Version 2.0 (Ashun Sound Machines, en anglais), commun aux modèles clavier et
 * Desktop ; fichier docs/manual/asm-hydrasynth-desktop.pdf. Identifiants préfixés « hyd- ».
 * Les fonctions réservées au modèle clavier (ruban, mode accord, aftertouch du clavier) ne sont pas enseignées.
 */
object HydrasynthContent {
    const val note =
        "Parcours rédigé d'après le Owner's Manual de l'Hydrasynth, version 2.0 (ASM, en anglais), pour le modèle Desktop " +
            "(pads). Les fonctions réservées au modèle clavier (ruban, mode accord) ne sont pas enseignées ; Mutants, Step LFO, " +
            "CV/Gate et réglages System Setup détaillés pas encore."
    const val sourcesLabel = "ASM Hydrasynth — Owner's Manual v2.0 (EN), p. 12–94."

    val modules: List<CourseModule> = HydrasynthModulesA.modules + HydrasynthModulesB.modules

    private val CONCEPTS = hyd("General Concepts", "17–18")
    private val PADS = hyd("Hydrasynth Desktop, SHIFT + Pad Functions", "26–28")
    private val OSC = hyd("The Oscillator Group", "35–36")
    private val MIXER = hyd("The Mixer Module", "43–44")
    private val FILTRES = hyd("The Filters and their Controls", "45–48")
    private val ENV = hyd("The Envelopes", "50–51")
    private val LFO = hyd("The LFOs", "55–56")
    private val MATRIX = hyd("The Mod Matrix", "85–86")
    private val MACROS = hyd("Mastering the Macros", "81")
    private val ARP = hyd("The Arpeggiator Section", "77–78")
    private val FX = hyd("The Effects", "62–66")
    private val BRANCHER = hyd("Plug It In", "12")

    val sections: List<MachineSection> = listOf(
        MachineSection(
            "Navigation",
            "Deux écrans, molette Patch, 8 boutons et 8 encodeurs autour de l'écran droit, boutons d'accès orange.",
            listOf(
                cmd("Patch knob, flèches, BROWSE", "Choisir un patch.", CONCEPTS),
                cmd("HOME, EXIT, PAGE haut / bas", "Navigation.", CONCEPTS),
                cmd("Control knobs / buttons", "Paramètres ou macros selon la page.", CONCEPTS),
                cmd("INIT, RANDOM, SAVE", "Initialiser, randomiser, sauvegarder.", CONCEPTS),
            ),
            CONCEPTS,
        ),
        MachineSection(
            "Moteur sonore",
            "3 oscillateurs, mixer, 2 filtres, ampli, 5 enveloppes, 5 LFO, matrice de 32 routes.",
            listOf(
                cmd("OSC 1–3, MIXER", "Sources et dosage.", OSC),
                cmd("FILTER 1, FILTER 2, AMP", "Filtres et amplitude.", FILTRES),
                cmd("ENV 1–5, LFO 1–5", "Modulateurs.", ENV),
                cmd("MOD MATRIX", "Routes de modulation.", MATRIX),
            ),
            OSC,
        ),
        MachineSection(
            "Jeu et effets",
            "Macros sur la page Home, arpégiateur, effets Pre-FX, Delay, Reverb, Post-FX.",
            listOf(
                cmd("MACRO ASSIGN", "Créer les macros.", MACROS),
                cmd("Arpeggiator : ON, LATCH, Mode, Octave, Division, Swing", "Arpèges.", ARP),
                cmd("PRE-FX, DELAY, REVERB, POST-FX", "Effets.", FX),
                cmd("LFO", "Modulation cyclique.", LFO),
            ),
            MACROS,
        ),
        MachineSection(
            "Pads et prises",
            "Pads RGB du Desktop ; sorties jack, casque, MIDI, USB (MIDI seulement), CV/Gate.",
            listOf(
                cmd("SHIFT + pads", "Gamme, octave, mode des pads, tonalité.", PADS),
                cmd("Sorties, Master Volume", "Audio vers la table.", BRANCHER),
                cmd("MIXER : niveaux, pan, routage filtres", "Mélange des sources.", MIXER),
            ),
            PADS,
        ),
    )
}
