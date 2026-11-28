package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule
import com.livemachine.academy.data.model.ManualDoc
import com.livemachine.academy.data.model.ManualRef

/** Référence au mode d'emploi de la TR-8S (pages imprimées = pages du PDF). */
internal fun tr8s(chapter: String, pages: String) = ManualRef(chapter, pages, ManualDoc.TR8S_MANUAL)

/**
 * Roland TR-8S.
 *
 * Source unique : Mode d'emploi TR-8S (Roland, 2018, en français), fichier docs/manual/roland-tr-8s.pdf.
 * Identifiants préfixés « tr8s- ». Le mode d'emploi renvoie au Manuel de référence (PDF non fourni) pour le
 * détail des paramètres : ces détails ne sont pas enseignés.
 */
object TR8SContent {
    const val note =
        "Parcours rédigé d'après le Mode d'emploi de la TR-8S (Roland, 2018, en français). Ce document renvoie au Manuel de " +
            "référence pour le détail de certains paramètres (types d'effets, échantillons, MIDI) : ces détails ne sont pas enseignés. " +
            "Ne pas confondre avec la TR-8 (sans « S »)."
    const val sourcesLabel = "Roland TR-8S — Mode d'emploi (FR, 2018), p. 4–21."

    val modules: List<CourseModule> = TR8SModulesA.modules + TR8SModulesB.modules

    private val PANNEAU = tr8s("Description des panneaux, Panneau supérieur", "4–5")
    private val PADS = tr8s("Pad [1]–[16]", "6")
    private val ARRIERE = tr8s("Face arrière", "6–7")
    private val MOTIFS = tr8s("Présentation du TR-8S", "8")
    private val TR_REC = tr8s("TR-REC (enregistrement par pas)", "11")
    private val KIT = tr8s("Modification d'un kit ou d'un instrument", "14–16")
    private val EFFETS = tr8s("Réglage de la réverbération ou du retard, Effet Master", "10")
    private val TEMPO = tr8s("Réglage du tempo", "20")

    val sections: List<MachineSection> = listOf(
        MachineSection(
            "Séquenceur et pads",
            "128 motifs (16 × 8 banques), 8 variations A–H et 2 transitions par motif ; 16 pads de pas.",
            listOf(
                cmd("START/STOP, PTN SELECT, TR-REC, INST REC, INST PLAY", "Lecture et modes d'enregistrement.", PANNEAU),
                cmd("Boutons [A]–[H], LAST, SUB", "Variations, longueur, sous-pas.", PANNEAU),
                cmd("Pads [1]–[16]", "Pas, motifs ou jeu selon le mode.", PADS),
                cmd("ACCENT [STEP] / [LEVEL]", "Accents.", TR_REC),
            ),
            MOTIFS,
        ),
        MachineSection(
            "Instruments et kits",
            "11 instruments (BD à RC) par kit, 128 kits.",
            listOf(
                cmd("TUNE, DECAY, CTRL, fader de niveau", "Son de chaque instrument.", PANNEAU),
                cmd("Boutons de sélection [BD]–[RC], MUTE", "Choisir, couper.", PANNEAU),
                cmd("KIT, INST, SAMPLE, CTRL SELECT", "Kits, sons, échantillons, rôle des CTRL.", KIT),
            ),
            KIT,
        ),
        MachineSection(
            "Effets et transitions",
            "Réverbération, retard, effet Master, transitions automatiques ou manuelles.",
            listOf(
                cmd("REVERB [LEVEL], DELAY [LEVEL] / [TIME] / [FEEDBACK]", "Effets d'envoi.", EFFETS),
                cmd("MASTER FX [ON] / [CTRL]", "Effet général.", EFFETS),
                cmd("AUTO FILL IN [ON], [AUTO FILL IN], [MANUAL TRIG]", "Transitions.", EFFETS),
                cmd("TEMPO, SHUFFLE", "Tempo et swing.", TEMPO),
            ),
            EFFETS,
        ),
        MachineSection(
            "Prises",
            "DC IN, POWER, USB, MIDI, carte SD, TRIGGER OUT, EXT IN, ASSIGNABLE OUT 1–6, MIX OUT, PHONES.",
            listOf(
                cmd("MIX OUT (L/MONO, R), PHONES", "Sortie principale et casque.", ARRIERE),
                cmd("ASSIGNABLE OUT/TRIGGER OUT 1–6", "Sorties séparées ou déclencheurs.", ARRIERE),
                cmd("EXT IN, MIDI, USB", "Entrées et synchronisation.", ARRIERE),
            ),
            ARRIERE,
        ),
    )
}
