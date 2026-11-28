package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule
import com.livemachine.academy.data.model.ManualDoc
import com.livemachine.academy.data.model.ManualRef

/** Référence au Quick Start Guide de la TD-3 (pages imprimées du guide). */
internal fun td3(chapter: String, pages: String) = ManualRef(chapter, pages, ManualDoc.TD3_GUIDE)

/**
 * Behringer TD-3.
 *
 * Source unique : TD-3 Quick Start Guide V 5.0 (Behringer / Music Tribe, multilingue), fichier docs/manual/behringer-td-3.pdf.
 * Sections françaises (p. 24–25, 42–43, 64–67), recoupées avec l'anglais (p. 56–59) où le français est ambigu.
 * Identifiants préfixés « td3- ». Poly Chain et détail MIDI non enseignés. Ne vaut pas pour les TD-3-MO / TD-3-AM.
 */
object TD3Content {
    const val note =
        "Parcours rédigé d'après le Quick Start Guide de la TD-3, version 5.0 (Behringer), sections française et anglaise. " +
            "C'est le seul document officiel fourni : la fonction Poly Chain, le détail MIDI et l'écriture des triolets ne sont pas " +
            "enseignés. Ne pas confondre avec les TD-3-MO et TD-3-AM."
    const val sourcesLabel = "Behringer TD-3 — Quick Start Guide V 5.0 (FR/EN), p. 24–67."

    val modules: List<CourseModule> = TD3ModulesA.modules + TD3ModulesB.modules

    private val SYNTH = td3("Étape 2 : Réglages, section du synthétiseur", "24")
    private val SEQ = td3("Section séquenceur", "24–25")
    private val ARRIERE = td3("Face arrière, panneau de connexions", "24–25")
    private val OPERATION = td3("Opération TD-3", "64–65")
    private val PISTES = td3("Opération TD-3 (suite), pistes", "66–67")

    val sections: List<MachineSection> = listOf(
        MachineSection(
            "Synthétiseur",
            "Un VCO (dent de scie inversée ou pulsation), filtre passe-bas, enveloppe, accent.",
            listOf(
                cmd("TUNE, WAVEFORM", "Hauteur et forme d'onde.", SYNTH),
                cmd("CUTOFF, RESONANCE", "Filtre passe-bas.", SYNTH),
                cmd("ENVELOPE, DECAY, ACCENT", "Mouvement du filtre et accent.", SYNTH),
            ),
            SYNTH,
        ),
        MachineSection(
            "Distorsion et sortie",
            "Distorsion avant le volume final.",
            listOf(
                cmd("DISTORTION ON/OFF", "Active la distorsion.", SYNTH),
                cmd("DRIVE, TONE, LEVEL", "Quantité, tonalité, niveau.", SYNTH),
                cmd("VOLUME, TEMPO", "Sortie et vitesse.", SYNTH),
            ),
            SYNTH,
        ),
        MachineSection(
            "Séquenceur",
            "Clavier de 13 touches, modes PITCH et TIME, motifs A/B en 4 groupes, 7 pistes.",
            listOf(
                cmd("MODE (Track Write/Play, Pattern Play/Write)", "Mode de travail.", SEQ),
                cmd("TRACK/PATTERN GROUP", "Piste 1–7 ou groupe I–IV.", SEQ),
                cmd("PITCH MODE, TIME MODE, FUNCTION", "Notes, rythme, mode normal.", SEQ),
                cmd("ACCENT/PATTERN A, SLIDE/PATTERN B", "Accent, slide, banques A et B.", SEQ),
                cmd("WRITE/NEXT/TAP, BACK, CLEAR, START/STOP", "Avancer, revenir, effacer, lire.", OPERATION),
            ),
            OPERATION,
        ),
        MachineSection(
            "Prises",
            "OUTPUT 6,35 mm, PHONES, MIDI IN / OUT-THRU, USB, FILTER IN, SYNC IN, CV OUT, GATE OUT, DC 9 V.",
            listOf(
                cmd("OUTPUT, PHONES", "Sortie ligne et casque.", ARRIERE),
                cmd("MIDI IN, MIDI OUT/THRU, USB", "MIDI.", ARRIERE),
                cmd("FILTER IN, SYNC IN, CV OUT, GATE OUT", "Minijacks du panneau de connexions.", ARRIERE),
                cmd("Pistes (TRACK WRITE / PLAY)", "Enchaînement des motifs.", PISTES),
            ),
            ARRIERE,
        ),
    )
}
