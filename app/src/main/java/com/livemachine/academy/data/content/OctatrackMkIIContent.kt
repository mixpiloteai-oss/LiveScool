package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule
import com.livemachine.academy.data.model.ManualDoc
import com.livemachine.academy.data.model.ManualRef

/** Référence au manuel Octatrack MKII (pages imprimées, identiques à celles du PDF). */
internal fun ot(chapter: String, pages: String) = ManualRef(chapter, pages, ManualDoc.OCTATRACK_MKII_MANUAL)

/**
 * Elektron Octatrack MKII.
 *
 * Source unique : Octatrack MKII User Manual, OS 1.40C (Elektron, en anglais),
 * fichier docs/manual/elektron-octatrack-mkii.pdf. Identifiants préfixés « ot2- ».
 */
object OctatrackMkIIContent {
    const val note =
        "Parcours rédigé d'après l'Octatrack MKII User Manual pour l'OS 1.40C (Elektron, en anglais). Les noms des touches et " +
            "des menus sont ceux de la machine, en anglais. Les pistes MIDI et l'éditeur audio ne sont pas encore couverts. " +
            "Ne pas confondre avec l'Octatrack MKI."
    const val sourcesLabel = "Elektron Octatrack MKII — User Manual, OS 1.40C (EN), p. 12–118."

    val modules: List<CourseModule> = OctatrackMkIIModulesA.modules + OctatrackMkIIModulesB.modules

    private val FACADE = ot("3.1 Front panel", "12–13")
    private val ARRIERE = ot("3.2 Rear connectors", "14")

    val sections: List<MachineSection> = listOf(
        MachineSection(
            "Sampling et pistes",
            "Échantillonnage en direct et sélection des huit pistes audio.",
            listOf(
                cmd("[REC1] / [REC2] / [REC3]", "Entrées AB, CD, sources internes.", FACADE),
                cmd("[TRACK 1–8]", "Pistes ; [FUNC] = mute, [CUE] = pré-écoute.", FACADE),
                cmd("LEVEL et DATA ENTRY", "Niveau de piste et paramètres.", FACADE),
            ),
            FACADE,
        ),
        MachineSection(
            "Pages, menus et scènes",
            "Cinq pages de paramètres par piste, menus du projet et crossfader.",
            listOf(
                cmd("[SRC] [AMP] [LFO] [FX1] [FX2]", "Pages MAIN ; [FUNC] ou double appui = SETUP.", FACADE),
                cmd("[PROJ] [PART] [AED] [MIX] [ARR] [MIDI]", "Projet, parts, éditeur, mixeur, arrangeur, MIDI.", FACADE),
                cmd("[SCENE A] / [SCENE B] et crossfader", "Scènes et transitions.", FACADE),
                cmd("[TEMPO]", "Tempo ; [FUNC] + [TEMPO] = tap.", FACADE),
            ),
            FACADE,
        ),
        MachineSection(
            "Séquenceur et transport",
            "Seize touches de pas, patterns, banques et enregistrement.",
            listOf(
                cmd("[TRIG 1–16], [PAGE]", "Pas, pages ; [FUNC] + [PAGE] = longueur.", FACADE),
                cmd("[BANK] / [PTN]", "Banques et patterns.", FACADE),
                cmd("[RECORD] / [PLAY] / [STOP]", "Enregistrement, transport ; avec [FUNC] : copier, effacer, coller.", FACADE),
                cmd("[YES] / [NO] / flèches", "Valider, revenir, naviguer.", FACADE),
            ),
            FACADE,
        ),
        MachineSection(
            "Prises et carte",
            "Sorties principales et de pré-écoute, entrées, MIDI, USB, carte Compact Flash.",
            listOf(
                cmd("MAIN OUT L/R, CUE OUT L/R, HEADPHONES", "Sorties.", ARRIERE),
                cmd("INPUT AB, INPUT CD", "Entrées audio à échantillonner ou traiter.", ARRIERE),
                cmd("MIDI IN / OUT / THRU, USB", "Synchro et ordinateur.", ARRIERE),
                cmd("Lecteur Compact Flash", "Sets, projets et samples.", ARRIERE),
            ),
            ARRIERE,
        ),
    )
}
