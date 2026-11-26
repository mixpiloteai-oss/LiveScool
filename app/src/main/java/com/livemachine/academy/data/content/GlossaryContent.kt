package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.GlossaryCategory.LIVE
import com.livemachine.academy.data.model.GlossaryCategory.MACHINE
import com.livemachine.academy.data.model.GlossaryCategory.SEQUENCING
import com.livemachine.academy.data.model.GlossaryCategory.SOUND
import com.livemachine.academy.data.model.GlossaryCategory.STYLE
import com.livemachine.academy.data.model.GlossaryTerm
import com.livemachine.academy.data.model.ManualRef

/**
 * Glossaire. Les termes de la catégorie MACHINE viennent du manuel officiel et
 * citent leur page ; les autres sont des notions musicales générales.
 */
object GlossaryContent {
    val terms: List<GlossaryTerm> = listOf(
        GlossaryTerm("Sample utilisateur", "Sample créé ou importé sur la machine. Jusqu'à 499, environ 270 s au total (mono). Perdu à l'extinction s'il n'est pas exporté sur carte.", MACHINE, ManualRef("Fonctions de l'electribe sampler", "34")),
        GlossaryTerm("Rééchantillonnage", "Enregistrer la sortie de la machine (un pattern ou une part) pour en faire un nouveau sample.", MACHINE, ManualRef("Enregistrement et édition d'échantillons › 2. Rééchantillonnage", "35–36")),
        GlossaryTerm("SAMPLE EDIT", "Menu d'édition des samples : points de départ, de fin et de boucle, accord, découpe, niveau, nom, effacement.", MACHINE, ManualRef("Enregistrement et édition d'échantillons › 3. Édition d'un échantillon", "36")),
        GlossaryTerm("Gate Time", "Durée de lecture d'un sample, réglée par Decay/Release quand Amp EG est coupé (127 = jusqu'au bout).", MACHINE, ManualRef("Fonctions de l'electribe sampler › 2. Changements", "34")),
        GlossaryTerm("Part Mute / Part Erase", "Part Mute coupe le son d'une part sans rien effacer ; Part Erase efface ses notes pendant la lecture.", MACHINE, ManualRef("Edition d'un motif", "32")),
        GlossaryTerm("Voice Assign", "Réglage de la part qui détermine le jeu de plusieurs pads à la fois ; Poly 1 ou Poly 2 pour les accords.", MACHINE, ManualRef("Production de musique › 1. Jouer avec les pads", "28")),
        GlossaryTerm("Pattern (« motif »)", "Bloc de composition constitué de 16 parts et d'un effet maître. La machine en mémorise jusqu'à 250.", MACHINE, ManualRef("Pour produire du son › 3. Lecture d'un motif", "27")),
        GlossaryTerm("Part (« partie »)", "Plus petit bloc d'un pattern : son, rythmes ou phrases, état des effets, données de jeu.", MACHINE, ManualRef("Pour produire du son › 4. Sélection d'une partie", "27")),
        GlossaryTerm("Écran de sélection de pattern", "Écran affiché au démarrage, d'où l'on choisit le pattern. Deux pressions sur Exit y ramènent.", MACHINE, ManualRef("Pour produire du son › 1. Mise sous tension/hors tension", "27")),
        GlossaryTerm("Commande de valeur", "Change la valeur du paramètre affiché à l'écran.", MACHINE, ManualRef("Description par section › Section commune", "22")),
        GlossaryTerm("Menu d'édition", "S'ouvre avec Menu/Enter depuis l'écran de sélection de pattern ; < et > choisissent le paramètre.", MACHINE, ManualRef("Pour produire du son › 2. Manipulations de base", "27")),
        GlossaryTerm("Menu Global", "Réglages généraux (métronome, contraste, Auto Power Off…), accessibles via « GLOBAL PARAMETER ». À sauvegarder avec Write.", MACHINE, ManualRef("Autres réglages", "32")),
        GlossaryTerm("Write", "Sauvegarde le pattern (et les réglages Global). Shift + Write : sauvegarde sous un nouveau nom.", MACHINE, ManualRef("Enregistrement de données de jeu › 4. Sauvegarde d'un motif", "31")),
        GlossaryTerm("Tap", "Bouton qui règle le tempo d'après l'intervalle entre quelques pressions successives.", MACHINE, ManualRef("Production de musique › 6. Réglage du tempo (BPM)", "30")),
        GlossaryTerm("Mode de pad", "Section de boutons (Trigger, Sequencer, Keyboard, Chord, Step Jump, Pattern Set) qui définit le rôle des pads de déclenchement.", MACHINE, ManualRef("Description par section › Section du mode de pad", "24")),
        GlossaryTerm("Pad tactile", "Surface qui pilote le Gate Arpeggiator, le Touch Scale ou l'effet maître.", MACHINE, ManualRef("Description par section › Section du pad tactile", "22")),
        GlossaryTerm("Auto Power Off", "Extinction automatique après 4 heures sans manipulation ; réglable dans le menu Global.", MACHINE, ManualRef("Autres réglages › 5. Coupure automatique d'alimentation", "33")),
        GlossaryTerm("BPM", "Battements par minute : le tempo du morceau.", SEQUENCING),
        GlossaryTerm("Pas (step)", "Une case d'une grille de séquenceur. Une mesure en 4/4 découpée en doubles croches compte 16 pas.", SEQUENCING),
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
