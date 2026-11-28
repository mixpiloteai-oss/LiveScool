package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule
import com.livemachine.academy.data.model.ManualDoc
import com.livemachine.academy.data.model.ManualRef

/** Référence au guide de l'OP-1 field (pages du PDF ; le guide n'imprime pas de numéros). */
internal fun op1f(chapter: String, pages: String) = ManualRef(chapter, pages, ManualDoc.OP1_FIELD_GUIDE)

/**
 * Teenage Engineering OP-1 field.
 *
 * Source unique : OP-1 field user guide, firmware 1.7.0 (REV 10, en anglais), fichier
 * docs/manual/teenage-engineering-op-1-field.pdf. Les pages citées sont celles du PDF.
 * Identifiants préfixés « op1f- ». Ne vaut pas pour l'OP-1 d'origine.
 */
object OP1FieldContent {
    const val note =
        "Parcours rédigé d'après le guide de l'OP-1 field, firmware 1.7.0 (Teenage Engineering, en anglais). Le guide n'imprime " +
            "pas de numéros de page : les pages citées sont celles du PDF. Le détail de chaque moteur, effet et séquenceur, la radio, " +
            "le Bluetooth et le mode disque ne sont pas enseignés. Ne pas confondre avec l'OP-1 d'origine."
    const val sourcesLabel = "Teenage Engineering OP-1 field — User guide fw 1.7.0 (EN), p. 4–54 du PDF."

    val modules: List<CourseModule> = OP1FieldModules.modules

    private val FACADE = op1f("overview, power on", "4–5")
    private val MODES = op1f("start, shift, help", "6–7")
    private val ENCODEURS = op1f("guide conventions", "8")
    private val PRESETS = op1f("preset sounds, save a preset", "9–10")
    private val SYNTH = op1f("synth mode, envelope, effects, lfo", "11, 22–23, 29")
    private val DRUM = op1f("drum mode, drum sampler", "35–36")
    private val TAPE = op1f("tape mode, record to tape, tape tricks", "39–40")
    private val MIXER = op1f("mixer, equalizer, master out", "43–44")
    private val TEMPO = op1f("tempo, sync modes", "52–53")

    val sections: List<MachineSection> = listOf(
        MachineSection(
            "Modes et navigation",
            "Quatre modes principaux (synth, drum, tape, mixer), touches T1–T4, quatre encodeurs de couleur.",
            listOf(
                cmd("synth, drum, tape, mixer", "Modes principaux.", MODES),
                cmd("T1–T4", "Modules ou pistes selon le mode.", MODES),
                cmd("Encodeurs bleu, ocre, gris, orange", "Réglages ; shift = réglage fin.", ENCODEURS),
                cmd("shift, help", "Fonctions secondaires, aide.", MODES),
            ),
            MODES,
        ),
        MachineSection(
            "Sons",
            "Moteurs de synthèse et de batterie, presets 1–8, enveloppe, effets, LFO.",
            listOf(
                cmd("Touches 1–8, shift + 1–8", "Presets.", PRESETS),
                cmd("T1 moteur, T2 enveloppe, T3 FX, T4 lfo", "Modules d'un son.", SYNTH),
                cmd("drum, clavier", "Kits de batterie.", DRUM),
            ),
            SYNTH,
        ),
        MachineSection(
            "Bande et mixage",
            "Bande 4 pistes stéréo, tape tricks, mixer, égaliseur, effet master, tempo.",
            listOf(
                cmd("record, play, stop, flèches", "Transport de la bande.", TAPE),
                cmd("lift, drop, split", "Édition de la bande.", TAPE),
                cmd("mixer : T1–T4", "Niveaux, EQ, effet master, sortie.", MIXER),
                cmd("tempo, sync", "Tempo et synchronisation.", TEMPO),
            ),
            TAPE,
        ),
        MachineSection(
            "Prises et alimentation",
            "Haut-parleur, micro, sortie et entrée 3,5 mm, USB-C (audio, MIDI, charge, données), interrupteur.",
            listOf(
                cmd("Sortie 3,5 mm", "Casque, table, enceintes.", FACADE),
                cmd("Entrée 3,5 mm", "Audio externe niveau ligne.", FACADE),
                cmd("USB-C, interrupteur", "Charge, audio, MIDI ; marche / arrêt.", FACADE),
            ),
            FACADE,
        ),
    )
}
