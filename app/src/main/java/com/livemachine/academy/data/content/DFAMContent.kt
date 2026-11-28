package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule
import com.livemachine.academy.data.model.ManualDoc
import com.livemachine.academy.data.model.ManualRef

/** Référence au manuel du DFAM (pages imprimées = pages du PDF). */
internal fun dfam(chapter: String, pages: String) = ManualRef(chapter, pages, ManualDoc.DFAM_MANUAL)

/**
 * Moog DFAM (Drummer From Another Mother).
 *
 * Source unique : DFAM Manual (Moog Music, 2017, en anglais), fichier docs/manual/moog-dfam.pdf.
 * Identifiants préfixés « dfam- ». Le montage en Eurorack (p. 32) n'est pas enseigné ;
 * les réglages des presets (p. 33–37) sont des schémas : on renvoie au manuel sans les recopier.
 */
object DFAMContent {
    const val note =
        "Parcours rédigé d'après le manuel officiel du DFAM (Moog Music, 2017, en anglais). Le DFAM n'a ni mémoire, ni MIDI, " +
            "ni écran : tout se règle sur la façade et la patchbay. Le montage en rack Eurorack n'est pas enseigné ici."
    const val sourcesLabel = "Moog DFAM — Manual (2017, EN), p. 6–31."

    val modules: List<CourseModule> = DFAMModulesA.modules + DFAMModulesB.modules

    private val VCO = dfam("Voltage Controlled Oscillators", "13–14")
    private val MOD_VCO = dfam("Oscillator Modulation", "15–16")
    private val MIXER = dfam("Mixer", "16–17")
    private val VCF = dfam("Voltage Controlled Filter", "17–20")
    private val VCA = dfam("Voltage Controlled Amplifier", "20–21")
    private val SEQ = dfam("Analog 8-Step Sequencer", "22–23")
    private val PATCH = dfam("Patchbay", "24–29")
    private val SETUP = dfam("Setup and Connections", "6")

    val sections: List<MachineSection> = listOf(
        MachineSection(
            "Oscillateurs et mélangeur",
            "Deux VCO (triangle ou carré), Hard Sync, FM 1→2, bruit blanc ou entrée externe.",
            listOf(
                cmd("VCO 1 FREQUENCY / VCO 2 FREQUENCY", "Hauteur de départ de chaque oscillateur (10 octaves).", VCO),
                cmd("VCO 1 WAVE / VCO 2 WAVE", "Triangle ou carré.", VCO),
                cmd("HARD SYNC, 1→2 FM AMOUNT", "Interaction des deux oscillateurs.", VCO),
                cmd("SEQ PITCH MOD, VCO DECAY, VCO 1/2 EG AMOUNT", "Modulation de hauteur par le séquenceur et l'enveloppe.", MOD_VCO),
                cmd("VCO 1 LEVEL, NOISE / EXT LEVEL, VCO 2 LEVEL", "Mélangeur à trois voies.", MIXER),
            ),
            VCO,
        ),
        MachineSection(
            "Filtre et amplificateur",
            "Filtre ladder 4 pôles passe-bas ou passe-haut, deux enveloppes de decay, volume.",
            listOf(
                cmd("VCF (LP / HP), CUTOFF, RESONANCE", "Mode et fréquence de coupure du filtre.", VCF),
                cmd("VCF DECAY, VCF EG AMOUNT, NOISE / VCF MOD", "Mouvement du filtre à chaque note.", VCF),
                cmd("VCA EG (FAST / SLOW), VCA DECAY", "Attaque et longueur du son.", VCA),
                cmd("VOLUME", "Niveau de sortie et du casque.", VCA),
            ),
            VCF,
        ),
        MachineSection(
            "Séquenceur 8 pas",
            "Deux rangées de huit potentiomètres : hauteur et vélocité par pas.",
            listOf(
                cmd("PITCH 1–8", "Hauteur ajoutée ou retirée à chaque pas.", SEQ),
                cmd("VELOCITY 1–8", "Amplitude des trois enveloppes à chaque pas.", SEQ),
                cmd("RUN / STOP, TEMPO", "Lecture et vitesse.", SEQ),
                cmd("ADVANCE, TRIGGER", "Avancer d'un pas ou rejouer le pas courant, à l'arrêt.", SEQ),
            ),
            SEQ,
        ),
        MachineSection(
            "Patchbay et prises",
            "24 points 3,5 mm : 15 entrées (texte normal), 9 sorties (lettres inversées). Sortie audio et alimentation à l'arrière.",
            listOf(
                cmd("Entrées (TRIGGER, VELOCITY, TEMPO, ADV / CLOCK…)", "Contrôle externe des fonctions.", PATCH),
                cmd("Sorties (VCA, VCO 1, VCO 2, TRIGGER, PITCH, VELOCITY…)", "Sons et tensions disponibles.", PATCH),
                cmd("AUDIO OUT (jack 6,35 mm), 12 V DC", "Sortie mono/casque et alimentation.", SETUP),
            ),
            PATCH,
        ),
    )
}
