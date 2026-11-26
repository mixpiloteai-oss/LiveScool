package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.ContentStatus
import com.livemachine.academy.data.model.Exercise
import com.livemachine.academy.data.model.Lesson
import com.livemachine.academy.data.model.MachineControl
import com.livemachine.academy.data.model.ManualRef
import com.livemachine.academy.data.model.QuizQuestion

/**
 * Module 1 — Interface et navigation.
 *
 * Source : manuel d'utilisation electribe/electribe sampler (Korg, 2015),
 * partie française, pages 22 à 34. Les numéros de page sont ceux imprimés
 * dans le manuel (identiques aux pages du PDF fourni).
 * Vocabulaire du manuel français : « motif » = pattern, « partie » = part.
 */
object Module1Content {

    private const val M = "m1"

    // Références du manuel utilisées dans ce module
    private val FACADE = ManualRef("Introduction › 1. Description par section", "22–24")
    private val PAD_TACTILE = ManualRef("Description par section › Section du pad tactile", "22")
    private val TRANSPORT = ManualRef("Description par section › Section de transport", "22")
    private val COMMUNE = ManualRef("Description par section › Section commune", "22–23")
    private val EDITION = ManualRef("Description par section › Section d'édition", "23–24")
    private val PARTIE = ManualRef("Description par section › Section d'édition de partie", "24")
    private val MODE_PAD = ManualRef("Description par section › Section du mode de pad", "24")
    private val ALIM = ManualRef("Pour produire du son › 1. Mise sous tension/hors tension", "27")
    private val SECTEUR = ManualRef("Introduction › 3. Connexion de l'adaptateur secteur", "25–26")
    private val CARTE = ManualRef("Introduction › 4. Insérer et retirer une carte de mémoire", "26")
    private val AFFICHAGE = ManualRef("Pour produire du son › 2. Manipulations de base", "27")
    private val MENUS = ManualRef("Autres réglages › Méthode d'affichage de chaque menu de réglage", "32")
    private val CONTRASTE = ManualRef("Autres réglages › 3. Réglage de contraste de l'affichage", "32")
    private val AUTO_OFF = ManualRef("Autres réglages › 5. Coupure automatique d'alimentation", "33")
    private val MOTIF = ManualRef("Pour produire du son › 3. Lecture d'un motif", "27")
    private val PARTIE_SEL = ManualRef("Pour produire du son › 4. Sélection d'une partie", "27")
    private val TEMPO = ManualRef("Production de musique › 6. Réglage du tempo (BPM)", "30")
    private val METRONOME = ManualRef("Autres réglages › 1. Réglage du métronome", "32")
    private val SAUVEGARDE = ManualRef("Enregistrement de données de jeu › 4. Sauvegarde d'un motif", "31")
    private val FACTORY = ManualRef("Autres réglages › 10. Formatage des données utilisateur", "34")

    private fun q(
        id: String,
        prompt: String,
        options: List<String>,
        correct: Int,
        explanation: String,
        ref: ManualRef,
    ) = QuizQuestion(id, prompt, options, correct, explanation, ref)

    // ---------------------------------------------------------------------
    // 1.1 Vue d'ensemble de la façade
    // ---------------------------------------------------------------------
    private val lesson1 = Lesson(
        id = "$M-l1",
        moduleId = M,
        order = 1,
        title = "Vue d'ensemble de la façade",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(FACADE),
        durationMinutes = 15,
        objective = "Savoir montrer chaque section de la façade et dire à quoi elle sert, avant de toucher au son.",
        explanation = "Le manuel découpe le panneau supérieur en 13 zones numérotées (p. 22). Pour t'y retrouver, " +
            "regroupe-les en 7 familles : alimentation et niveaux, écran, pad tactile, transport, section commune " +
            "(navigation), section d'édition du son, et la zone des pads (boutons Step, édition de partie, " +
            "mode de pad, pads de déclenchement). Attention : les illustrations du manuel montrent l'electribe, " +
            "pas l'electribe sampler. Le fonctionnement de base est le même sur les deux (p. 22).",
        controls = listOf(
            MachineControl("Bouton d'alimentation, Volume, Input Level", "Allumer/éteindre ; volume de la sortie ligne et du casque ; niveau du signal reçu sur Audio In.", FACADE),
            MachineControl("Écran", "Affiche les infos sur les patterns, les parts et les paramètres.", FACADE),
            MachineControl("Pad tactile + Gate Arp, Touch Scale, Master Fx, MFX Hold", "Surface qui pilote le Gate Arpeggiator, le Touch Scale ou l'effet maître.", PAD_TACTILE),
            MachineControl("Transport : Rec, Stop, Play/Pause, Tap", "Enregistrer, arrêter, lancer/mettre en pause, taper le tempo.", TRANSPORT),
            MachineControl("Section commune : commande de valeur, < >, Menu/Enter, Exit, Shift, Write", "Naviguer dans les menus, changer une valeur, sauvegarder.", COMMUNE),
            MachineControl("Section d'édition : Oscillator, Filter, Modulation, Amp/EG, Insert Fx", "Modifier le son de la part sélectionnée.", EDITION),
            MachineControl("Boutons Step", "Indiquent la position ou l'octave de la séquence, et la fonction actuelle des pads.", FACADE),
            MachineControl("Édition de partie : <Part, Part>, Part Mute, Part Erase", "Choisir une part, la couper, effacer ses notes.", PARTIE),
            MachineControl("Mode de pad : Trigger, Sequencer, Keyboard, Chord, Step Jump, Pattern Set", "Définit ce que font les pads de déclenchement.", MODE_PAD),
            MachineControl("Pads de déclenchement", "Leur rôle dépend du mode de pad choisi.", MODE_PAD),
        ),
        steps = listOf(
            "Machine éteinte devant toi, repère le bouton d'alimentation puis les commandes Volume et Input Level.",
            "Repère l'écran, puis le pad tactile et ses quatre boutons : Gate Arp, Touch Scale, Master Fx et MFX Hold.",
            "Trouve la section de transport : enregistrement (Rec), arrêt (Stop), lecture/pause (Play/Pause) et Tap.",
            "Trouve la section commune : la commande de valeur, les boutons < et >, Menu/Enter, Exit, Shift et Write.",
            "Parcours la section d'édition en lisant les sérigraphies : Oscillator, Filter, Modulation, Amp/EG, Insert Fx.",
            "Repère les boutons Step, puis les boutons <Part, Part>, Part Mute et Part Erase.",
            "Termine par la section du mode de pad (Trigger, Sequencer, Keyboard, Chord, Step Jump, Pattern Set) et les pads de déclenchement.",
        ),
        expectedResult = "Tu peux poser le doigt sur n'importe quelle section citée ci-dessus sans chercher, et dire en une phrase à quoi elle sert.",
        exercise = Exercise(
            id = "$M-l1-ex",
            lessonId = "$M-l1",
            title = "Le tour de façade minuté",
            instructions = listOf(
                "Ouvre la liste « Commandes utilisées » de cette leçon et cache la colonne des rôles.",
                "Pour chaque ligne, touche la commande sur ta machine en moins de 3 secondes.",
                "Dis à voix haute son rôle, puis vérifie dans l'app.",
            ),
            successCriteria = "Les 10 lignes trouvées en moins de 3 secondes chacune, sans erreur de rôle.",
        ),
        commonMistakes = listOf(
            "Se fier aux illustrations du manuel pour l'electribe sampler : elles représentent l'electribe (p. 22). Lis plutôt les sérigraphies de ta machine.",
            "Confondre le pad tactile (la surface que l'on frotte) et les pads de déclenchement (ceux qui déclenchent les parts).",
            "Croire que MFX Hold agit seul : il ne sert que lorsque le bouton Master Fx est actif (p. 22).",
            "Confondre Volume (sortie ligne et casque) et Input Level (niveau reçu sur Audio In) (p. 22).",
        ),
        quiz = listOf(
            q("$M-l1-q1", "À quoi sert la commande Input Level ?",
                listOf("Régler le volume du casque", "Régler le niveau du signal reçu sur la prise Audio In", "Régler le volume d'une part"),
                1, "Input Level règle le niveau d'entrée du signal reçu à la prise Audio In. Le volume casque/ligne, c'est la commande Volume.", FACADE),
            q("$M-l1-q2", "Quels boutons forment la section du mode de pad ?",
                listOf("Trigger, Sequencer, Keyboard, Chord, Step Jump, Pattern Set", "Part Mute, Part Erase, <Part, Part>", "Gate Arp, Touch Scale, Master Fx, MFX Hold"),
                0, "Ces six boutons définissent la fonction des pads de déclenchement.", MODE_PAD),
            q("$M-l1-q3", "Que permet le bouton MFX Hold ?",
                listOf("Couper l'effet d'insertion", "Conserver l'état de l'effet maître sans garder les doigts sur le pad tactile", "Mémoriser le tempo"),
                1, "Quand Master Fx est actif, MFX Hold garde l'état de l'effet maître réglé avec le pad tactile.", PAD_TACTILE),
            q("$M-l1-q4", "Dans quelle section se trouve le bouton Tap ?",
                listOf("La section commune", "La section de transport", "La section du mode de pad"),
                1, "Tap fait partie de la section de transport, avec Rec, Stop et Play/Pause.", TRANSPORT),
        ),
    )

    // ---------------------------------------------------------------------
    // 1.2 Allumer, éteindre, revenir au point de départ
    // ---------------------------------------------------------------------
    private val lesson2 = Lesson(
        id = "$M-l2",
        moduleId = M,
        order = 2,
        title = "Allumer, éteindre et revenir au point de départ",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(ALIM, SECTEUR, CARTE),
        durationMinutes = 10,
        objective = "Allumer et éteindre la machine correctement, et savoir revenir à l'écran de sélection de pattern depuis n'importe quel écran.",
        explanation = "Pour allumer, on garde le bouton d'alimentation enfoncé jusqu'à l'écran de démarrage ; pour éteindre, " +
            "jusqu'à ce que l'affichage s'éteigne. Au démarrage, la machine affiche l'écran de sélection de pattern : " +
            "c'est ton point de départ. Depuis n'importe quel autre écran, deux pressions sur Exit t'y ramènent. " +
            "La machine s'éteint seule après 4 heures sans manipulation (Auto Power Off) ; on peut le désactiver " +
            "dans le menu Global (vu à la leçon 1.3).",
        controls = listOf(
            MachineControl("Bouton d'alimentation", "Maintenir pour allumer (jusqu'à l'écran de démarrage) ou éteindre (jusqu'à l'extinction de l'affichage).", ALIM),
            MachineControl("Prise DC 9V", "Pour l'adaptateur secteur fourni (n'utilise que celui-là).", SECTEUR),
            MachineControl("Exit", "Deux pressions : retour à l'écran de sélection de pattern.", ALIM),
            MachineControl("Menu/Enter", "Depuis l'écran de sélection de pattern, ouvre le menu d'édition.", AFFICHAGE),
        ),
        steps = listOf(
            "Branche l'adaptateur secteur fourni à la prise DC 9V, ou mets six piles AA (p. 25–26).",
            "Mains loin des pads et des boutons, maintiens le bouton d'alimentation jusqu'à ce que l'écran de démarrage s'affiche.",
            "Attends l'écran de sélection de pattern : c'est ton point de départ.",
            "Appuie sur Menu/Enter pour entrer dans le menu d'édition.",
            "Appuie deux fois sur Exit : tu reviens à l'écran de sélection de pattern.",
            "Pour éteindre, maintiens le bouton d'alimentation jusqu'à ce que l'affichage s'éteigne.",
        ),
        expectedResult = "La machine démarre sur l'écran de sélection de pattern, tu sais y revenir avec deux pressions sur Exit, et elle s'éteint proprement.",
        exercise = Exercise(
            id = "$M-l2-ex",
            lessonId = "$M-l2",
            title = "Retour au point de départ",
            instructions = listOf(
                "Allume la machine sans toucher aux pads.",
                "Ouvre le menu d'édition avec Menu/Enter et fais défiler quelques paramètres avec < et >.",
                "Reviens à l'écran de sélection de pattern avec Exit (deux pressions).",
                "Recommence trois fois, puis éteins la machine.",
            ),
            successCriteria = "Tu reviens à chaque fois à l'écran de sélection de pattern sans éteindre la machine.",
        ),
        commonMistakes = listOf(
            "Toucher un pad ou un bouton pendant la mise sous tension : le manuel l'interdit (p. 27).",
            "Relâcher le bouton d'alimentation trop tôt : il faut le maintenir jusqu'à l'écran de démarrage (ou jusqu'à l'extinction).",
            "Appuyer une seule fois sur Exit et croire être revenu au départ : il faut deux pressions (p. 27).",
            "Insérer ou retirer la carte SD machine allumée : risque de perte de données ou de carte endommagée (p. 26).",
            "Être surpris par une extinction pendant une longue session : Auto Power Off coupe après 4 heures sans manipulation (p. 27).",
        ),
        quiz = listOf(
            q("$M-l2-q1", "Depuis n'importe quel écran, comment revenir à l'écran de sélection de pattern ?",
                listOf("Appuyer deux fois sur Exit", "Appuyer sur Stop", "Maintenir Shift"),
                0, "Deux pressions sur Exit affichent l'écran de sélection de pattern depuis tout autre écran.", ALIM),
            q("$M-l2-q2", "Au bout de combien de temps sans manipulation l'Auto Power Off éteint-il la machine ?",
                listOf("30 minutes", "1 heure", "4 heures"),
                2, "La fonction éteint la machine 4 heures après la dernière manipulation. On peut la désactiver (AUTO POWER OFF, menu Global).", ALIM),
            q("$M-l2-q3", "Que faut-il éviter pendant la mise sous tension ?",
                listOf("Tourner le Volume", "Toucher les pads de déclenchement ou les boutons", "Brancher un casque"),
                1, "Le manuel demande de ne toucher aucun pad ni bouton pendant la mise sous tension.", ALIM),
            q("$M-l2-q4", "Quand peut-on insérer ou retirer une carte SD ?",
                listOf("Uniquement machine éteinte", "Pendant la lecture", "Quand l'écran de sélection est affiché"),
                0, "Ne jamais insérer ni retirer la carte quand l'instrument est sous tension.", CARTE),
        ),
    )

    // ---------------------------------------------------------------------
    // 1.3 Naviguer dans les menus
    // ---------------------------------------------------------------------
    private val lesson3 = Lesson(
        id = "$M-l3",
        moduleId = M,
        order = 3,
        title = "Naviguer dans les menus",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(COMMUNE, AFFICHAGE, MENUS, CONTRASTE, AUTO_OFF),
        durationMinutes = 15,
        objective = "Ouvrir le menu d'édition et le menu Global, choisir un paramètre, changer sa valeur et la sauvegarder.",
        explanation = "La navigation repose sur quatre commandes. Menu/Enter ouvre le menu d'édition depuis l'écran de " +
            "sélection de pattern et valide les choix. Les boutons < et > choisissent le paramètre. La commande de " +
            "valeur change la valeur du paramètre affiché. Exit, pressé deux fois, ramène à l'écran de sélection. " +
            "Shift + commande de valeur change la taille du pas de réglage (elle dépend du paramètre). " +
            "Les menus Global, Data Utility et Part Utility s'ouvrent depuis le menu d'édition : fais défiler avec " +
            "< ou > jusqu'à leur nom, puis Menu/Enter. Pour garder un réglage Global, appuie sur Write : sinon il est " +
            "perdu à l'extinction.",
        controls = listOf(
            MachineControl("Menu/Enter", "Ouvre le menu d'édition ; entre dans un sous-menu ; valide.", COMMUNE),
            MachineControl("Boutons < et >", "Choisissent le paramètre à éditer.", COMMUNE),
            MachineControl("Commande de valeur", "Change la valeur du paramètre affiché à l'écran.", COMMUNE),
            MachineControl("Shift + commande de valeur", "Change la valeur par petits pas ou par pas plus importants.", COMMUNE),
            MachineControl("Exit", "Deux pressions : retour à l'écran de sélection de pattern.", COMMUNE),
            MachineControl("Write", "Sauvegarde les réglages Global (et les patterns).", MENUS),
        ),
        steps = listOf(
            "Depuis l'écran de sélection de pattern, appuie sur Menu/Enter : le menu d'édition s'ouvre.",
            "Avec < ou >, fais défiler jusqu'à « GLOBAL PARAMETER », puis appuie sur Menu/Enter.",
            "Avec < ou >, va jusqu'au paramètre « LCD CONTRAST ».",
            "Tourne la commande de valeur pour régler un contraste confortable.",
            "Appuie sur Write pour sauvegarder le réglage Global.",
            "Appuie deux fois sur Exit pour revenir à l'écran de sélection de pattern.",
        ),
        expectedResult = "Le contraste de l'écran a changé, et il est toujours le même après avoir éteint puis rallumé la machine.",
        exercise = Exercise(
            id = "$M-l3-ex",
            lessonId = "$M-l3",
            title = "Préparer la machine pour les longues sessions",
            instructions = listOf(
                "Dans le menu Global, règle LCD CONTRAST pour que l'écran soit lisible dans ta pièce.",
                "Toujours dans le menu Global, choisis la valeur de AUTO POWER OFF : « Off » pour qu'elle ne s'éteigne jamais seule, « 4 hours » pour économiser l'énergie.",
                "Sauvegarde avec Write, puis reviens à l'écran de sélection de pattern.",
                "Éteins, rallume, et vérifie dans le menu Global que tes deux réglages sont restés.",
            ),
            successCriteria = "Après redémarrage, LCD CONTRAST et AUTO POWER OFF ont gardé tes valeurs.",
            styleHint = "En free party ou en live, une machine qui s'éteint seule en plein set, c'est le cauchemar : vérifie AUTO POWER OFF avant chaque date.",
        ),
        commonMistakes = listOf(
            "Oublier d'appuyer sur Write après un réglage Global : il est perdu à l'extinction (p. 32).",
            "Tourner la commande de valeur sans vérifier quel paramètre est affiché : c'est toujours le paramètre affiché qui change (p. 22).",
            "Chercher le menu Global ailleurs que dans le menu d'édition : on y accède par « GLOBAL PARAMETER » puis Menu/Enter (p. 32).",
        ),
        quiz = listOf(
            q("$M-l3-q1", "Depuis l'écran de sélection de pattern, comment ouvrir le menu d'édition ?",
                listOf("Shift + Write", "Menu/Enter", "Exit"),
                1, "Menu/Enter ouvre le menu d'édition depuis l'écran de sélection de pattern.", AFFICHAGE),
            q("$M-l3-q2", "Quels boutons choisissent le paramètre à éditer ?",
                listOf("Les boutons < et >", "Les boutons <Part, Part>", "Les boutons Step"),
                0, "Les boutons de curseur < et > servent à sélectionner le paramètre à éditer.", COMMUNE),
            q("$M-l3-q3", "Tu as changé un réglage Global et tu éteins sans appuyer sur Write. Que se passe-t-il ?",
                listOf("Le réglage est gardé", "Le réglage est perdu et l'ancien réglage revient", "La machine refuse de s'éteindre"),
                1, "Sans sauvegarde, les réglages sont perdus au profit des réglages d'origine.", MENUS),
            q("$M-l3-q4", "Comment ouvrir le menu Global ?",
                listOf("Maintenir Shift et appuyer sur Menu/Enter", "Dans le menu d'édition, afficher « GLOBAL PARAMETER » avec < ou >, puis Menu/Enter", "Appuyer deux fois sur Exit"),
                1, "On ouvre le menu d'édition, on affiche GLOBAL PARAMETER avec < ou >, puis on appuie sur Menu/Enter.", MENUS),
        ),
    )

    // ---------------------------------------------------------------------
    // 1.4 Lancer, arrêter et choisir un pattern
    // ---------------------------------------------------------------------
    private val lesson4 = Lesson(
        id = "$M-l4",
        moduleId = M,
        order = 4,
        title = "Lancer, arrêter et choisir un pattern",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(MOTIF, PARTIE_SEL, TRANSPORT),
        durationMinutes = 15,
        objective = "Choisir un pattern, le lancer et l'arrêter, puis écouter et sélectionner ses parts.",
        explanation = "Un pattern (« motif » dans le manuel) est un bloc de composition : 16 parts et un effet maître. " +
            "La machine peut en mémoriser 250. Une part (« partie ») est le plus petit bloc d'un pattern : elle contient " +
            "son son, ses rythmes ou phrases, l'état de ses effets et ses données de jeu. Sur l'écran de sélection de " +
            "pattern, la commande de valeur choisit le pattern, même pendant la lecture. Play/Pause lance la lecture " +
            "depuis le début du pattern, qui tourne en boucle ; Stop l'arrête.",
        controls = listOf(
            MachineControl("Commande de valeur", "Sur l'écran de sélection, choisit le pattern (aussi pendant la lecture).", MOTIF),
            MachineControl("Play/Pause", "Lance la lecture ; pendant la lecture, la met en pause.", TRANSPORT),
            MachineControl("Stop", "Arrête la lecture.", TRANSPORT),
            MachineControl("Trigger", "Allumé : chaque pad déclenche sa part, pour l'écouter.", PARTIE_SEL),
            MachineControl("<Part, Part>", "Sélectionne la part à éditer ; son pad s'allume en bleu.", PARTIE_SEL),
        ),
        steps = listOf(
            "Reviens à l'écran de sélection de pattern (deux pressions sur Exit).",
            "Tourne la commande de valeur pour choisir un pattern.",
            "Appuie sur Play/Pause : le pattern joue depuis le début et tourne en boucle.",
            "Pendant la lecture, tourne la commande de valeur pour passer à un autre pattern.",
            "Appuie sur Stop pour arrêter.",
            "Appuie sur Trigger (il s'allume), puis tape chaque pad pour écouter le son de chaque part.",
            "Avec <Part, Part>, sélectionne une part : son pad s'allume en bleu.",
        ),
        expectedResult = "Tu lances et arrêtes un pattern, tu changes de pattern pendant la lecture, et tu sais quelle part est sélectionnée grâce au pad bleu.",
        exercise = Exercise(
            id = "$M-l4-ex",
            lessonId = "$M-l4",
            title = "La chasse aux sons",
            instructions = listOf(
                "Parcours les patterns avec la commande de valeur, en écoutant chacun quelques mesures.",
                "Avec Trigger allumé, écoute les parts des patterns qui t'accrochent.",
                "Note 3 patterns dont au moins une part pourrait servir dans tes styles (kick, basse, percussion), avec le numéro de pattern et le pad de la part.",
            ),
            successCriteria = "Tu as une liste de 3 patterns, avec pour chacun la part intéressante, et tu les retrouves sur la machine sans hésiter.",
            styleHint = "Hardtek et tribe : cherche des kicks secs et des basses qui roulent ; acidcore : des sons de basse qui réagissent bien au filtre.",
        ),
        commonMistakes = listOf(
            "Utiliser Play/Pause pour arrêter : pendant la lecture, il met en pause. Pour arrêter, c'est Stop (p. 22).",
            "Taper les pads sans que Trigger soit allumé et ne pas entendre la part attendue : le rôle des pads dépend du mode de pad (p. 24).",
            "Changer de pattern alors que le pattern en cours a été modifié : toutes les modifications non sauvegardées sont perdues (p. 31). Voir la leçon 1.6.",
        ),
        quiz = listOf(
            q("$M-l4-q1", "Combien de patterns la machine peut-elle mémoriser ?",
                listOf("64", "128", "250"),
                2, "L'instrument permet de créer et mémoriser jusqu'à 250 patterns.", MOTIF),
            q("$M-l4-q2", "De quoi un pattern est-il constitué ?",
                listOf("De 16 parts et d'un effet maître", "De 8 parts et de 2 effets", "D'une seule part"),
                0, "Un pattern est constitué de 16 parts et d'un effet maître.", MOTIF),
            q("$M-l4-q3", "Comment écouter le son d'une part avec les pads ?",
                listOf("Allumer Sequencer et taper le pad", "Allumer Trigger et taper le pad", "Maintenir Shift et taper le pad"),
                1, "Avec Trigger allumé, chaque pad permet de choisir la part et d'écouter son son.", PARTIE_SEL),
            q("$M-l4-q4", "Peut-on changer de pattern pendant la lecture ?",
                listOf("Non, il faut d'abord appuyer sur Stop", "Oui, avec la commande de valeur", "Oui, mais seulement avec Tap"),
                1, "Le manuel précise qu'on peut changer de pattern pendant la lecture.", MOTIF),
            q("$M-l4-q5", "Que fait Play/Pause pendant la lecture ?",
                listOf("Il relance le pattern depuis le début", "Il met la lecture en pause", "Il sauvegarde le pattern"),
                1, "Play/Pause lance ou met temporairement en pause la lecture.", TRANSPORT),
        ),
    )

    // ---------------------------------------------------------------------
    // 1.5 Régler le tempo
    // ---------------------------------------------------------------------
    private val lesson5 = Lesson(
        id = "$M-l5",
        moduleId = M,
        order = 5,
        title = "Régler le tempo",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(TEMPO, METRONOME, COMMUNE),
        durationMinutes = 10,
        objective = "Régler le tempo au bouton Tap ou au paramètre BPM, et activer le métronome.",
        explanation = "Le tempo s'applique au pattern et au Gate Arpeggiator. Deux méthodes : taper le bouton Tap au tempo " +
            "voulu (l'intervalle entre tes pressions sert de base), ou choisir le paramètre BPM dans le menu d'édition " +
            "et le régler avec la commande de valeur. Le métronome (METRONOME, menu Global) sonne sur chaque noire. " +
            "Ses réglages « Rec 0 », « Rec 1 » et « Rec 2 » le font jouer seulement pendant l'enregistrement, « Off » " +
            "le coupe. La plage de tempo n'est pas indiquée dans le manuel d'utilisation : elle figure dans le Guide " +
            "des paramètres.",
        controls = listOf(
            MachineControl("Tap", "Quelques pressions au tempo voulu règlent le BPM.", TEMPO),
            MachineControl("Paramètre BPM (menu d'édition) + commande de valeur", "Réglage précis du tempo.", TEMPO),
            MachineControl("Shift + commande de valeur", "Change la taille du pas de réglage.", COMMUNE),
            MachineControl("METRONOME (menu Global)", "Réglage du métronome, qui sonne sur chaque noire.", METRONOME),
        ),
        steps = listOf(
            "Lance un pattern avec Play/Pause.",
            "Tape plusieurs fois sur Tap, régulièrement, au tempo que tu veux.",
            "Ouvre le menu d'édition (Menu/Enter) et va jusqu'au paramètre BPM avec < ou > : lis la valeur obtenue.",
            "Ajuste le BPM avec la commande de valeur ; essaie aussi avec Shift enfoncé pour voir la différence de pas.",
            "Va dans le menu Global, choisis METRONOME et active-le avec la commande de valeur.",
            "Écoute le métronome sur le pattern, puis remets-le sur « Off ». Sauvegarde avec Write si tu veux garder le réglage du métronome.",
        ),
        expectedResult = "Le pattern joue au tempo que tu as tapé, puis à la valeur précise que tu as réglée ; le métronome se cale sur chaque noire.",
        exercise = Exercise(
            id = "$M-l5-ex",
            lessonId = "$M-l5",
            title = "Tempo à l'oreille",
            instructions = listOf(
                "Fais tourner dans ta tête (ou sur ton téléphone, volume bas) un morceau de ton style préféré.",
                "Tape son tempo sur Tap, puis lis la valeur dans le paramètre BPM.",
                "Ajuste au BPM exact avec la commande de valeur, en écoutant si le pattern « colle ».",
                "Recommence avec un morceau d'un autre style visé.",
            ),
            successCriteria = "Pour deux morceaux, l'écart entre ta valeur tapée et la valeur finale est faible, et tu sais la corriger au paramètre BPM.",
            styleHint = "Compare les tempos de tes morceaux hardtek, tribe et mentalcore : ça t'aidera à prévoir l'ordre de ton futur set.",
        ),
        commonMistakes = listOf(
            "Taper Tap une ou deux fois seulement : le manuel parle de « quelques pressions successives » (p. 22).",
            "Laisser le métronome actif pendant la lecture : choisis « Rec 0 », « Rec 1 » ou « Rec 2 » pour ne l'entendre qu'en enregistrement, ou « Off » (p. 32).",
            "Tourner la commande de valeur en croyant régler le tempo alors qu'un autre paramètre est affiché : vérifie que l'écran affiche BPM.",
        ),
        quiz = listOf(
            q("$M-l5-q1", "Comment fonctionne le bouton Tap ?",
                listOf("Il double le tempo", "L'intervalle entre tes pressions sert de base au tempo", "Il remet le tempo par défaut"),
                1, "Tu tapes au tempo voulu : l'intervalle entre tes pressions est utilisé comme base de tempo.", TEMPO),
            q("$M-l5-q2", "Où régler le tempo avec précision ?",
                listOf("Paramètre BPM du menu d'édition, avec la commande de valeur", "Menu Data Utility", "Commande Level"),
                0, "On choisit le paramètre BPM dans le menu d'édition et on le règle avec la commande de valeur.", TEMPO),
            q("$M-l5-q3", "Sur quelles valeurs de note le métronome sonne-t-il ?",
                listOf("Chaque croche", "Chaque noire", "Chaque mesure"),
                1, "Le métronome produit un son sur chaque noire.", METRONOME),
            q("$M-l5-q4", "Quel réglage du métronome le fait jouer seulement pendant l'enregistrement ?",
                listOf("Off", "Rec 0, Rec 1 ou Rec 2", "On"),
                1, "Les réglages Rec 0, Rec 1 et Rec 2 limitent le métronome à l'enregistrement.", METRONOME),
        ),
    )

    // ---------------------------------------------------------------------
    // 1.6 Sauvegarder son travail
    // ---------------------------------------------------------------------
    private val lesson6 = Lesson(
        id = "$M-l6",
        moduleId = M,
        order = 6,
        title = "Sauvegarder son travail",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(SAUVEGARDE, FACTORY),
        durationMinutes = 15,
        objective = "Sauvegarder un pattern en écrasant son emplacement, ou en copie sous un nouveau nom et un autre numéro.",
        explanation = "Changer de pattern sans sauvegarder efface toutes les modifications du pattern en cours. Deux façons de " +
            "sauvegarder. Write seul : tu choisis l'emplacement avec la commande de valeur, puis tu appuies à nouveau sur " +
            "Write (« Working... » s'affiche). Shift + Write : « enter NAME » s'affiche, tu renommes le pattern, tu choisis " +
            "le numéro de destination, puis tu appuies sur Write. Le bouton Write reste allumé pendant la sauvegarde : " +
            "n'éteins jamais la machine avant qu'il s'éteigne. Exit annule une sauvegarde en cours de préparation.",
        controls = listOf(
            MachineControl("Write", "Lance la sauvegarde ; une seconde pression confirme. Allumé tant que la sauvegarde n'est pas terminée.", SAUVEGARDE),
            MachineControl("Shift + Write", "Sauvegarde avec un nouveau nom (copie de pattern).", SAUVEGARDE),
            MachineControl("Boutons < et >", "Déplacent le curseur dans le nom.", SAUVEGARDE),
            MachineControl("Menu/Enter", "Confirme le caractère saisi.", SAUVEGARDE),
            MachineControl("Commande de valeur", "Choisit le numéro de pattern de destination.", SAUVEGARDE),
            MachineControl("Exit", "Annule la sauvegarde.", SAUVEGARDE),
        ),
        steps = listOf(
            "Choisis un pattern et modifie-le légèrement (par exemple tourne la commande Level d'une part).",
            "Maintiens Shift et appuie sur Write : « enter NAME » s'affiche.",
            "Avec < et >, place le curseur sur un caractère, change-le, puis confirme avec Menu/Enter. Écris un nom court.",
            "Avec la commande de valeur, choisis un numéro de pattern dont tu n'as pas besoin.",
            "Appuie à nouveau sur Write. Attends que le bouton Write s'éteigne.",
            "La machine sélectionne maintenant le pattern de destination : passe à un autre pattern, reviens-y et vérifie ta modification.",
        ),
        expectedResult = "Ta copie existe sous son nouveau nom, à l'emplacement choisi, et ta modification est toujours là quand tu reviens au pattern.",
        exercise = Exercise(
            id = "$M-l6-ex",
            lessonId = "$M-l6",
            title = "Le réflexe Write",
            instructions = listOf(
                "Copie un pattern d'usine qui te plaît vers un emplacement libre avec Shift + Write, en lui donnant un nom à toi.",
                "Sur cette copie, fais une modification, puis sauvegarde-la avec Write seul (remplacement).",
                "Fais exprès une modification sans sauvegarder, change de pattern, puis reviens : constate qu'elle est perdue.",
            ),
            successCriteria = "Ta copie garde la modification sauvegardée, et tu as constaté la perte de la modification non sauvegardée.",
            styleHint = "Prends l'habitude de donner à tes patterns des noms qui disent le style et le rôle (intro, montée, drop) : ça préparera le Module 9.",
        ),
        commonMistakes = listOf(
            "Changer de pattern avant d'avoir sauvegardé : toutes les modifications sont perdues (p. 31).",
            "Éteindre la machine pendant la sauvegarde : attends que le bouton Write s'éteigne (p. 31).",
            "Écraser par erreur un pattern que tu voulais garder : vérifie le numéro de destination avant la seconde pression sur Write. Exit annule (p. 31).",
            "Confondre « Factory Reset » avec une simple remise à zéro : il efface tous les patterns créés par l'utilisateur (p. 34).",
        ),
        quiz = listOf(
            q("$M-l6-q1", "Que se passe-t-il si tu changes de pattern sans avoir sauvegardé tes modifications ?",
                listOf("Elles sont gardées en mémoire temporaire", "Elles sont perdues", "La machine sauvegarde automatiquement"),
                1, "Changer de pattern sans sauvegarder entraîne la perte de tous les changements.", SAUVEGARDE),
            q("$M-l6-q2", "Quelle combinaison permet de sauvegarder sous un nouveau nom ?",
                listOf("Shift + Write", "Shift + Exit", "Menu/Enter + Write"),
                0, "Shift + Write affiche « enter NAME » pour renommer et sauvegarder une copie.", SAUVEGARDE),
            q("$M-l6-q3", "Comment savoir que la sauvegarde est terminée ?",
                listOf("L'écran affiche « Working... »", "Le bouton Write s'éteint", "Le pattern redémarre"),
                1, "Write reste allumé pendant toute la sauvegarde et s'éteint quand elle est terminée.", SAUVEGARDE),
            q("$M-l6-q4", "Comment annuler une sauvegarde en cours de préparation ?",
                listOf("Bouton Stop", "Bouton Exit", "Éteindre la machine"),
                1, "Le bouton Exit annule la sauvegarde.", SAUVEGARDE),
        ),
    )

    val lessons: List<Lesson> = listOf(lesson1, lesson2, lesson3, lesson4, lesson5, lesson6)
}
