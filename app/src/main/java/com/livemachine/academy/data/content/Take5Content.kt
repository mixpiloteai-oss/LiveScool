package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule
import com.livemachine.academy.data.model.ManualDoc
import com.livemachine.academy.data.model.ManualRef

/** Référence au guide du Take 5 (pages imprimées ; page du PDF = page imprimée + 9). */
internal fun tk5(chapter: String, pages: String) = ManualRef(chapter, pages, ManualDoc.TAKE5_GUIDE)

/**
 * Sequential Take 5 (version clavier).
 *
 * Source unique : Take 5 User's Guide, version 2.0, mai 2025 (Sequential, en anglais),
 * fichier docs/manual/sequential-take-5.pdf. Identifiants préfixés « tk5- ».
 * Les fonctions propres au Take 5 Module (version sans clavier) et les transferts SysEx ne sont pas enseignés.
 */
object Take5Content {
    const val note =
        "Parcours rédigé d'après le User's Guide du Take 5, version 2.0 (Sequential, mai 2025, en anglais), pour la version " +
            "clavier (44 touches). Les commandes propres au Take 5 Module et les transferts SysEx ne sont pas enseignés."
    const val sourcesLabel = "Sequential Take 5 — User's Guide v2.0 (EN), p. 2–73."

    val modules: List<CourseModule> = Take5ModulesA.modules + Take5ModulesB.modules

    private val OSC = tk5("Oscillators, Oscillator Parameters", "16–19")
    private val MIXER = tk5("Mixer Parameters", "20–21")
    private val FILTRE = tk5("Filter, Filter Parameters", "21–22")
    private val ENV = tk5("Envelopes, Envelope Parameters", "23–27")
    private val LFO = tk5("Low Frequency Oscillators", "30–33")
    private val MOD = tk5("Modulation", "34–36")
    private val FX = tk5("Effects, Vintage Knob, Overdrive", "40–42")
    private val ARP = tk5("Arpeggiator, Clock Parameters", "43–47")
    private val SEQ = tk5("Step Sequencer", "48–49")
    private val JEU = tk5("Transpose, Hold, Glide, Pitch and Mod Wheels", "50–53")
    private val ARRIERE = tk5("Rear Panel Connections", "2–3")
    private val PROG = tk5("Setting Up the Take 5", "4–7")

    val sections: List<MachineSection> = listOf(
        MachineSection(
            "Sources et filtre",
            "Deux VCO analogiques et un sous-oscillateur, mélangeur avec bruit blanc ou rose, filtre analogique avec Drive.",
            listOf(
                cmd("Octave, Pitch, Shape (Osc 1 et 2), Sync 2-1", "Hauteur et forme d'onde des oscillateurs.", OSC),
                cmd("Mixer : Osc 1, Osc 1 Sub, Osc 2, FM 2-1, Noise", "Niveaux des sources.", MIXER),
                cmd("Cutoff, Resonance, Drive", "Filtre.", FILTRE),
            ),
            OSC,
        ),
        MachineSection(
            "Enveloppes et modulations",
            "Deux enveloppes à 5 étapes, deux LFO, matrice de modulation.",
            listOf(
                cmd("Env 1 / Env 2 : Attack, Decay, Sustain, Release, Amount, Velocity", "Forme du filtre et du volume.", ENV),
                cmd("Env Routing", "Destinations des enveloppes.", ENV),
                cmd("LFO 1 (global) et LFO 2 (par voix) : Freq, Amount, Shape, Clk Sync, Dest", "Modulations cycliques.", LFO),
                cmd("Source, Destination, Src Assign, Dest Assign", "Matrice de modulation.", MOD),
            ),
            ENV,
        ),
        MachineSection(
            "Jeu et effets",
            "Programmes, unisson, effets, Vintage, Overdrive, glide, molettes, arpégiateur et séquenceur.",
            listOf(
                cmd("Bank, sélecteurs 1–16, Factory, Write, Preset", "Choisir et sauvegarder les programmes.", PROG),
                cmd("Effect (type, time, depth/mix, feedback/misc), Reverb", "Effets numériques.", FX),
                cmd("Vintage, Overdrive", "Caractère et saturation.", FX),
                cmd("Arpeggiator, BPM, Clk Divide, Tap Tempo", "Arpégiateur et horloge.", ARP),
                cmd("Step Seq : Record, Play", "Séquenceur pas à pas jusqu'à 64 pas.", SEQ),
                cmd("Transpose, Hold, Glide, molettes Pitch et Mod", "Jeu en direct.", JEU),
            ),
            FX,
        ),
        MachineSection(
            "Prises arrière",
            "Interrupteur, secteur, USB, MIDI, pédales, sorties et casque.",
            listOf(
                cmd("Power On/Off, AC Power", "Alimentation secteur 100–240 V.", ARRIERE),
                cmd("USB, MIDI In / Out / Thru", "MIDI avec un ordinateur ou d'autres machines.", ARRIERE),
                cmd("Footswitch, Pedal", "Pédale de sustain et pédale d'expression.", ARRIERE),
                cmd("Audio Outputs L/R, Headphones", "Sorties 6,35 mm asymétriques et casque.", ARRIERE),
            ),
            ARRIERE,
        ),
    )
}
