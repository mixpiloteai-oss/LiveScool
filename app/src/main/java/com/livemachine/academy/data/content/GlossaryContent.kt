package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.GlossaryCategory.LIVE
import com.livemachine.academy.data.model.GlossaryCategory.SEQUENCING
import com.livemachine.academy.data.model.GlossaryCategory.SOUND
import com.livemachine.academy.data.model.GlossaryCategory.STYLE
import com.livemachine.academy.data.model.GlossaryTerm

/**
 * Glossaire de notions musicales générales. Les termes propres à la machine
 * (noms de menus, de paramètres) seront ajoutés depuis le manuel officiel,
 * avec leur référence de page.
 */
object GlossaryContent {
    val terms: List<GlossaryTerm> = listOf(
        GlossaryTerm("BPM", "Battements par minute : le tempo du morceau.", SEQUENCING),
        GlossaryTerm("Pas (step)", "Une case d'une grille de séquenceur. Une mesure en 4/4 découpée en doubles croches compte 16 pas.", SEQUENCING),
        GlossaryTerm("Pattern", "Une boucle rythmique et mélodique de quelques mesures, rejouée en boucle.", SEQUENCING),
        GlossaryTerm("Swing", "Léger décalage d'un pas sur deux pour donner un groove moins mécanique.", SEQUENCING),
        GlossaryTerm("Sample", "Un enregistrement audio court (kick, voix, boucle…) rejoué par la machine.", SOUND),
        GlossaryTerm("Filtre passe-bas", "Coupe les fréquences aiguës au-dessus de la fréquence de coupure.", SOUND),
        GlossaryTerm("Fréquence de coupure (cutoff)", "La fréquence à partir de laquelle un filtre agit.", SOUND),
        GlossaryTerm("Résonance", "Accentue les fréquences autour de la coupure ; poussée fort, elle donne le son « acide ».", SOUND),
        GlossaryTerm("Enveloppe", "Évolution d'un paramètre dans le temps à chaque note (attaque, déclin…).", SOUND),
        GlossaryTerm("LFO", "Oscillateur basse fréquence qui fait varier un paramètre de façon cyclique.", SOUND),
        GlossaryTerm("Distorsion", "Saturation qui ajoute des harmoniques et de l'agressivité au son.", SOUND),
        GlossaryTerm("Automation / modulation", "Variation d'un paramètre au cours du temps, à la main ou enregistrée.", SOUND),
        GlossaryTerm("Mute", "Couper temporairement une piste sans l'effacer.", LIVE),
        GlossaryTerm("Break", "Passage où l'on retire le kick ou plusieurs éléments pour créer une attente.", LIVE),
        GlossaryTerm("Montée (build-up)", "Passage qui fait grimper la tension avant le retour du kick.", LIVE),
        GlossaryTerm("Drop", "Retour brutal du kick et de la basse après une montée.", LIVE),
        GlossaryTerm("Hardtek", "Tekno de free party, kick saturé et basse roulante, souvent autour de 170–190 BPM.", STYLE),
        GlossaryTerm("Tribe", "Tekno tribale de free party, basses hypnotiques et percussions répétitives.", STYLE),
        GlossaryTerm("TribeCore", "Mélange de tribe et de hardcore : plus rapide et plus saturé.", STYLE),
        GlossaryTerm("Mentalcore", "Tekno rapide et intense, aux sons acides et aux montées « mentales » qui durent.", STYLE),
        GlossaryTerm("Acidcore", "Hardcore construit autour de lignes de basse acides, filtre résonant très présent.", STYLE),
    ).sortedBy { it.term.lowercase() }
}
