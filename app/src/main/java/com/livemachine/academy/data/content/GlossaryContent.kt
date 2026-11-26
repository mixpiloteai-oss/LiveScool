package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.GlossaryCategory.LIVE
import com.livemachine.academy.data.model.GlossaryCategory.MACHINE
import com.livemachine.academy.data.model.GlossaryCategory.SEQUENCING
import com.livemachine.academy.data.model.GlossaryCategory.SOUND
import com.livemachine.academy.data.model.GlossaryCategory.STYLE
import com.livemachine.academy.data.model.GlossaryTerm
import com.livemachine.academy.data.model.ManualDoc.PARAMETER_GUIDE
import com.livemachine.academy.data.model.ManualRef

/**
 * Glossaire. Les termes de la catégorie MACHINE viennent du manuel officiel et
 * citent leur page ; les autres sont des notions musicales générales.
 */
object GlossaryContent {
    val terms: List<GlossaryTerm> = listOf(
        GlossaryTerm("EXPORT ALL PATTERN / EXPORT ALL SAMPLE", "Sauvegardes complètes sur carte : patterns + réglages Global ; samples, rechargés automatiquement au démarrage.", MACHINE, ManualRef("7. DATA UTILITY", "9–10", PARAMETER_GUIDE)),
        GlossaryTerm("CLOCK MODE", "Horloge de référence : Internal (maître), Auto, External USB, External MIDI ou External Sync.", MACHINE, ManualRef("6. GLOBAL PARAMETERS › CLOCK MODE", "7", PARAMETER_GUIDE)),
        GlossaryTerm("Step Jump", "Pendant la lecture, un pad fait sauter instantanément au pas correspondant ; plusieurs pads jouent les pas dans l'ordre d'enfoncement.", MACHINE, ManualRef("Description par section › Section du mode de pad", "24")),
        GlossaryTerm("Event Recorder", "Enregistre une performance live (patterns, commandes, pads) sur la carte SD ; ne modifie pas les patterns. Demande CLOCK MODE sur Internal.", MACHINE, ManualRef("Autres réglages › 9. Event Recorder", "33–34")),
        GlossaryTerm("Gate Arpeggiator", "Rejoue la part au tempo pendant qu'on tient son pad ; au pad tactile, X = vitesse du gate, Y = longueur du son.", MACHINE, ManualRef("Production de musique › 3. Gate Arpeggiator", "29")),
        GlossaryTerm("Pattern Set", "Patterns mémorisés sous les pads (4 groupes de 16) pour les lancer d'une pression pendant la lecture.", MACHINE, ManualRef("Production de musique › 5. Jeu avec un groupe de motifs", "30")),
        GlossaryTerm("CHAIN (CHAIN TO / CHAIN REPEAT)", "Enchaînement automatique : pattern suivant et nombre de répétitions. Demande CHAIN MODE sur On (menu Global).", MACHINE, ManualRef("1. PATTERN PARAMETERS", "3", PARAMETER_GUIDE)),
        GlossaryTerm("Motion sequence", "Enregistrement des mouvements de commandes d'une part (ou de l'effet maître), rejoués à chaque tour. Jusqu'à 24, modes Smooth ou Trigger Hold.", MACHINE, ManualRef("Enregistrement de données de jeu › 3. Motion Seq", "30–31")),
        GlossaryTerm("Type de modulation", "Combinaison source (enveloppe, LFO, S&H, aléatoire) + destination (Filter, Pitch, OSC, Level, Pan, IFX) : 72 types.", MACHINE, ManualRef("9. Appendix › Modulation Type List", "15–16", PARAMETER_GUIDE)),
        GlossaryTerm("Effet d'insertion (IFX)", "Effet propre à chaque part : 38 types, intensité réglée par Edit, activé par IFX On.", MACHINE, ManualRef("9. Appendix › IFX Type List", "17", PARAMETER_GUIDE)),
        GlossaryTerm("Effet maître (MFX)", "Effet unique du pattern (32 types), joué au pad tactile ; chaque part y est envoyée ou non avec MFX Send.", MACHINE, ManualRef("Production de musique › 4. Utilisation des effets maîtres", "29")),
        GlossaryTerm("EG Int", "Intensité avec laquelle l'enveloppe de la section Amp/EG fait bouger la coupure du filtre.", MACHINE, ManualRef("Description par section › Section d'édition › Filter", "23")),
        GlossaryTerm("Last Step", "Dernier pas joué par une part (1 à 16) : permet des parts plus courtes que les autres, donc des polyrythmies.", MACHINE, ManualRef("2. PART PARAMETERS › LAST STEP", "4", PARAMETER_GUIDE)),
        GlossaryTerm("STEP EDIT", "Édition pas par pas : note (jusqu'à 4), gate time (00 à 96 ou TIE), vélocité (001 à 127).", MACHINE, ManualRef("3. STEP EDIT", "5", PARAMETER_GUIDE)),
        GlossaryTerm("Groove (GROOVE TYPE / DEPTH)", "Placement « joué » appliqué à une part : 25 types, intensité 0 à 127.", MACHINE, ManualRef("2. PART PARAMETERS", "4", PARAMETER_GUIDE)),
        GlossaryTerm("KNOB MODE", "Réglage Global : Jump (la valeur saute à la commande), Catch (la valeur ne bouge qu'une fois rejointe), Value Scale (relatif).", MACHINE, ManualRef("6. GLOBAL PARAMETERS › KNOB MODE", "8", PARAMETER_GUIDE)),
        GlossaryTerm("Oscillateur (OSC)", "Source sonore d'une part : 421 types d'usine en catégories (Analog, Audio In, Kick, Snare… PCM), plus les samples utilisateur.", MACHINE, ManualRef("9. Appendix › OSC Type List", "12–14", PARAMETER_GUIDE)),
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
        GlossaryTerm("Menu Global", "Réglages généraux (métronome, contraste, Auto Power Off, KNOB MODE…), accessibles via « GLOBAL PARAMETER ». Sauvegarde avec Write.", MACHINE, ManualRef("Autres réglages", "32")),
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
