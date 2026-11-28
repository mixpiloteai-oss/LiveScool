package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule
import com.livemachine.academy.data.model.ManualDoc
import com.livemachine.academy.data.model.ManualRef

/** Référence au manuel MiniBrute 2S (pages imprimées ; page du PDF = page imprimée + 6). */
internal fun mb2s(chapter: String, pages: String) = ManualRef(chapter, pages, ManualDoc.MINIBRUTE_2S_MANUAL)

/**
 * Arturia MiniBrute 2S.
 *
 * Source unique : Manuel utilisateur MiniBrute 2S v1.0 (Arturia, en français),
 * fichier docs/manual/arturia-minibrute-2s.pdf. Identifiants préfixés « mb2s- ».
 * Les réglages accessibles seulement par le logiciel MIDI Control Center ne sont pas enseignés.
 */
object MiniBrute2SContent {
    const val note =
        "Parcours rédigé d'après le Manuel utilisateur du MiniBrute 2S, version 1.0 (Arturia, en français). " +
            "Les réglages qui demandent le logiciel MIDI Control Center ne sont pas enseignés ici. Ne pas confondre avec le " +
            "MiniBrute 2 (sans séquenceur) ni le MiniBrute d'origine."
    const val sourcesLabel = "Arturia MiniBrute 2S — Manuel utilisateur v1.0 (FR), p. 7–138."

    val modules: List<CourseModule> = MiniBrute2SModulesA.modules + MiniBrute2SModulesB.modules

    private val SEQ = mb2s("4.5 La partie séquenceur", "24–25")
    private val VCO1 = mb2s("5.2 VCO 1", "31–33")
    private val VCO2 = mb2s("5.3 VCO 2", "34–35")
    private val FILTRE = mb2s("5.4 La partie Filter", "36–39")
    private val AMP = mb2s("5.5 La partie Amplificateur", "40–41")
    private val MIXER = mb2s("5.6 La partie Osc Mixer", "42–45")
    private val LFO = mb2s("5.1 Les LFO", "28–30")
    private val PATCH = mb2s("7.1 Patchbay : concepts généraux", "59–61")
    private val ARRIERE = mb2s("4.6 Le panneau arrière", "27")

    val sections: List<MachineSection> = listOf(
        MachineSection(
            "Oscillateurs et mélangeur",
            "Deux VCO analogiques, bruit et entrée externe, dosés par les curseurs de l'Osc Mixer.",
            listOf(
                cmd("VCO 1 : Fine Tune, Glide, Pulse Width, Metalizer, FM, Ultrasaw Amt, PWM, Metal Mod", "Hauteur et formes d'onde du VCO 1.", VCO1),
                cmd("VCO 2 : Tune, Wave, Range", "Second oscillateur, sous-oscillateur ou LFO.", VCO2),
                cmd("Curseurs dent de scie, carrée, triangle, Noise, Ext, Osc 2", "Mélange vers le filtre.", MIXER),
            ),
            VCO1,
        ),
        MachineSection(
            "Filtre, enveloppes et sortie",
            "Filtre multimode, enveloppe ADSR (filtre), enveloppe AD (amplitude), Brute Factor.",
            listOf(
                cmd("Mode, Cutoff, Resonance, FM, RM, Att 1 > Cutoff", "Filtre.", FILTRE),
                cmd("Curseurs ADSR / AD, interrupteurs Gate-Trig et Once-Loop", "Enveloppes.", MIXER),
                cmd("Brute Factor, Master Volume, Global Tune, Att 2 > Amp", "Amplificateur et sortie.", AMP),
                cmd("LFO 1 et 2 : Wave, Rate, Sync", "Modulations cycliques.", LFO),
            ),
            FILTRE,
        ),
        MachineSection(
            "Séquenceur et pads",
            "Pads d'une octave, transport, chargement, pistes, pages et arpégiateur.",
            listOf(
                cmd("Arp/Loop, Tempo/Value, Tap, Sync", "Arpégiateur, tempo et horloge.", SEQ),
                cmd("Chain / Load, Track Select, Record/Stop/Play", "Motifs, pistes et transport.", SEQ),
                cmd("Save / Copy / Paste / Erase, Last Step, pages 16–64", "Gestion et longueur des motifs.", SEQ),
                cmd("Potentiomètres de pas 1–16, pads, Octave / Transpose", "Édition des pas et jeu.", SEQ),
            ),
            SEQ,
        ),
        MachineSection(
            "Patchbay et prises",
            "Patchbay de 48 points et panneau arrière.",
            listOf(
                cmd("Patchbay (entrées, sorties, inscriptions bleues)", "Routages et modulations.", PATCH),
                cmd("Master, casque, MIDI In/Out, USB, alimentation", "Prises arrière.", ARRIERE),
            ),
            PATCH,
        ),
    )
}
