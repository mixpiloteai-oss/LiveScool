package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.ContentStatus
import com.livemachine.academy.data.model.Exercise
import com.livemachine.academy.data.model.Lesson
import com.livemachine.academy.data.model.MachineControl
import com.livemachine.academy.data.model.ManualDoc.PARAMETER_GUIDE
import com.livemachine.academy.data.model.ManualRef
import com.livemachine.academy.data.model.QuizQuestion

/**
 * Module 8 — Live et transitions.
 *
 * Sources : manuel d'utilisation (partie française) et electribe sampler Parameter Guide.
 */
object Module8Content {

    private const val M = "m8"

    // Manuel d'utilisation
    private val OM_STEP_JUMP = ManualRef("Description par section › Section du mode de pad › Step Jump", "24")
    private val OM_MUTE = ManualRef("Edition d'un motif › 1. Etouffement d'une partie", "32")
    private val OM_ERASE = ManualRef("Edition d'un motif › 2. Effacement d'une partie", "32")
    private val OM_PATTERN_SET = ManualRef("Production de musique › 5. Jeu avec un groupe de motifs", "30")
    private val OM_GATE_ARP = ManualRef("Production de musique › 3. Utilisation de la fonction Gate Arpeggiator", "29")
    private val OM_TOUCH_SCALE = ManualRef("Production de musique › 2. Jouer de la musique avec le pad tactile", "29")
    private val OM_EFFETS_MAITRES = ManualRef("Production de musique › 4. Utilisation des effets maîtres", "29")
    private val OM_PAD_TACTILE = ManualRef("Description par section › Section du pad tactile", "22")
    private val OM_EVENT_REC = ManualRef("Autres réglages › 9. Utilisation de la fonction Event Recorder", "33–34")
    private val OM_TEMPO = ManualRef("Production de musique › 6. Réglage du tempo (BPM)", "30")
    private val OM_SAUVEGARDE = ManualRef("Enregistrement de données de jeu › 4. Sauvegarde d'un motif", "31")

    // Guide des paramètres
    private val PG_KNOB_MODE = ManualRef("6. GLOBAL PARAMETERS › KNOB MODE", "8", PARAMETER_GUIDE)
    private val PG_TEMPO_LOCK = ManualRef("6. GLOBAL PARAMETERS › TEMPO LOCK", "8", PARAMETER_GUIDE)
    private val PG_AUTO_OFF = ManualRef("6. GLOBAL PARAMETERS › AUTO POWER OFF", "8", PARAMETER_GUIDE)
    private val PG_TOUCH_RANGE = ManualRef("6. GLOBAL PARAMETERS › TOUCH SCALE RANGE", "8", PARAMETER_GUIDE)
    private val PG_PTN_LOCK = ManualRef("6. GLOBAL PARAMETERS › PTN. CHANGE LOCK", "9", PARAMETER_GUIDE)
    private val PG_XY_CAL = ManualRef("6. GLOBAL PARAMETERS › XY CALIBRATION", "9", PARAMETER_GUIDE)
    private val PG_PATTERN = ManualRef("1. PATTERN PARAMETERS", "3", PARAMETER_GUIDE)
    private val PG_LAST_STEP = ManualRef("2. PART PARAMETERS › LAST STEP", "4", PARAMETER_GUIDE)
    private val PG_EVENT = ManualRef("8. EVENT REC/PLAY", "10", PARAMETER_GUIDE)
    private val PG_SHORTCUTS = ManualRef("9. Appendix › Shortcut list", "11", PARAMETER_GUIDE)

    private fun q(id: String, prompt: String, options: List<String>, correct: Int, explanation: String, ref: ManualRef) =
        QuizQuestion(id, prompt, options, correct, explanation, ref)

    // ---------------------------------------------------------------------
    // 8.1 Régler la machine pour la scène
    // ---------------------------------------------------------------------
    private val lesson1 = Lesson(
        id = "$M-l1",
        moduleId = M,
        order = 1,
        title = "Régler la machine pour la scène",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(PG_KNOB_MODE, PG_PTN_LOCK, PG_TEMPO_LOCK, PG_AUTO_OFF, PG_XY_CAL),
        durationMinutes = 10,
        objective = "Régler les paramètres Global qui évitent les accidents en live.",
        explanation = "Cinq réglages Global protègent ton live. KNOB MODE sur « Catch » : une commande ne change la valeur " +
            "qu'une fois arrivée à la valeur réelle, pas de saut de son en changeant de part ou de pattern. PTN. CHANGE " +
            "LOCK sur On : sur l'écran de sélection, la commande de valeur ne change de pattern qu'avec Shift enfoncé, plus " +
            "de changement par erreur. TEMPO LOCK sur On : le tempo reste le même quand tu passes à un pattern d'un autre " +
            "tempo. AUTO POWER OFF sur « Disable » : la machine s'éteint sinon après 4 heures sans manipulation, même si un " +
            "pattern tourne. XY CALIBRATION : recalibre la zone active du pad tactile en touchant les coins inférieur gauche " +
            "et supérieur droit, en suivant l'écran.",
        controls = listOf(
            MachineControl("KNOB MODE → Catch", "Pas de saut de valeur.", PG_KNOB_MODE),
            MachineControl("PTN. CHANGE LOCK → On", "Changer de pattern demande Shift + commande de valeur.", PG_PTN_LOCK),
            MachineControl("TEMPO LOCK → On", "Tempo figé en changeant de pattern.", PG_TEMPO_LOCK),
            MachineControl("AUTO POWER OFF → Disable", "Pas d'extinction automatique.", PG_AUTO_OFF),
            MachineControl("XY CALIBRATION", "Calibrage de la zone active du pad tactile.", PG_XY_CAL),
        ),
        steps = listOf(
            "Ouvre le menu Global (Menu/Enter, puis « GLOBAL PARAMETER »).",
            "KNOB MODE : choisis « Catch ».",
            "PTN. CHANGE LOCK : choisis « On ». TEMPO LOCK : choisis selon ton set (On si tous tes morceaux sont au même tempo).",
            "AUTO POWER OFF : choisis « Disable ».",
            "Lance XY CALIBRATION et suis les indications de l'écran.",
            "Sauvegarde avec Write, éteins, rallume et vérifie tes réglages.",
        ),
        expectedResult = "La machine ne saute plus de valeur, ne change plus de pattern par erreur et ne s'éteindra pas en plein set.",
        exercise = Exercise(
            id = "$M-l1-ex",
            lessonId = "$M-l1",
            title = "La check-list d'avant-set",
            instructions = listOf(
                "Écris ta check-list Global : KNOB MODE, PTN. CHANGE LOCK, TEMPO LOCK, AUTO POWER OFF, LCD CONTRAST.",
                "Avant ta prochaine session, vérifie-la en moins de 2 minutes.",
            ),
            successCriteria = "Check-list vérifiée en moins de 2 minutes, sans chercher les menus.",
            styleHint = "Free party : une machine sur piles en extérieur ? Pense aussi à BATTERY TYPE et POWER SAVE (leçon 1.3).",
        ),
        commonMistakes = listOf(
            "Oublier PTN. CHANGE LOCK sur On en répétition, et ne plus réussir à changer de pattern : avec On, il faut maintenir Shift (guide p. 9).",
            "Laisser AUTO POWER OFF sur « 4 hours » pour un long set : la machine s'éteint même si un pattern tourne (guide p. 8).",
            "Passer en « Catch » juste avant le set sans s'y être entraîné : la commande ne réagit qu'une fois arrivée à la valeur réelle (guide p. 8).",
        ),
        quiz = listOf(
            q("$M-l1-q1", "Avec PTN. CHANGE LOCK sur On, comment change-t-on de pattern sur l'écran de sélection ?",
                listOf("Commande de valeur seule", "Commande de valeur avec Shift enfoncé", "Bouton Exit"), 1,
                "Avec On, le pattern change quand on tourne la commande de valeur en maintenant Shift.", PG_PTN_LOCK),
            q("$M-l1-q2", "Quel KNOB MODE évite les sauts de son en live ?",
                listOf("Jump", "Catch", "Value Scale"), 1, "Catch évite que le son change brutalement.", PG_KNOB_MODE),
            q("$M-l1-q3", "Avec AUTO POWER OFF sur « 4 hours », la machine s'éteint-elle si un pattern tourne sans manipulation ?",
                listOf("Non", "Oui, après 4 heures sans aucune manipulation", "Seulement sur piles"), 1,
                "Même si un pattern joue en continu, la machine s'éteint s'il n'y a eu aucune manipulation pendant la durée.", PG_AUTO_OFF),
            q("$M-l1-q4", "Comment se fait XY CALIBRATION ?",
                listOf("En touchant les coins inférieur gauche et supérieur droit du pad tactile", "En tapant le tempo", "En appuyant sur tous les pads"), 0,
                "On suit la procédure affichée et on touche les coins inférieur gauche et supérieur droit.", PG_XY_CAL),
        ),
    )

    // ---------------------------------------------------------------------
    // 8.2 Mutes et effacements en direct
    // ---------------------------------------------------------------------
    private val lesson2 = Lesson(
        id = "$M-l2",
        moduleId = M,
        order = 2,
        title = "Mutes en direct",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_MUTE, OM_ERASE, PG_SHORTCUTS),
        durationMinutes = 15,
        objective = "Construire et déconstruire un pattern en direct en coupant et en rétablissant les parts.",
        explanation = "Part Mute est l'outil de base du live : bouton allumé, un pad coupe sa part (pad éteint), une nouvelle " +
            "pression la rétablit. Shift + Part Mute rétablit toutes les parts d'un coup : idéal pour un drop. Part Erase " +
            "efface les notes d'une part tant que tu maintiens son pad pendant la lecture : c'est définitif pour le pattern " +
            "en cours d'édition, donc à réserver aux gestes voulus.",
        controls = listOf(
            MachineControl("Part Mute + pad", "Coupe / rétablit une part.", OM_MUTE),
            MachineControl("Shift + Part Mute", "Rétablit toutes les parts.", PG_SHORTCUTS),
            MachineControl("Part Erase + pad maintenu", "Efface les notes de la part pendant la lecture.", OM_ERASE),
        ),
        steps = listOf(
            "Lance ton DROP A et allume Part Mute.",
            "Coupe toutes les parts sauf le kick, une à une.",
            "Réintroduis-les une par une, en fin de phrase, sur 8 tours.",
            "Coupe tout sauf une percussion pour un mini-break, puis Shift + Part Mute pour tout faire revenir d'un coup.",
            "Recommence en changeant l'ordre de réintroduction.",
        ),
        expectedResult = "Tu construis une montée et un drop en direct avec un seul pattern, juste avec les mutes.",
        exercise = Exercise(
            id = "$M-l2-ex",
            lessonId = "$M-l2",
            title = "Un morceau avec un seul pattern",
            instructions = listOf(
                "Joue 5 minutes sur un seul pattern, uniquement avec Part Mute et Shift + Part Mute.",
                "Fais au moins deux breaks et deux drops.",
            ),
            successCriteria = "5 minutes tenues, deux breaks et deux drops nets, sans erreur de mute.",
            styleHint = "Tribe et hardtek : le jeu de mutes sur un groove qui tourne longtemps est au cœur du live tekno.",
        ),
        commonMistakes = listOf(
            "Appuyer sur Part Erase au lieu de Part Mute : les notes sont effacées au lieu d'être coupées (manuel p. 32).",
            "Réintroduire une part au hasard : vise le début d'une phrase pour que l'entrée sonne voulue.",
            "Oublier que Shift + Part Mute rétablit toutes les parts, y compris celles que tu voulais garder coupées (guide p. 11).",
        ),
        quiz = listOf(
            q("$M-l2-q1", "Quel raccourci rétablit toutes les parts coupées ?",
                listOf("Shift + Part Mute", "Shift + Part Erase", "Exit deux fois"), 0, "Shift + Part Mute annule la coupure de toutes les parts.", PG_SHORTCUTS),
            q("$M-l2-q2", "Part Mute allumé, une part coupée a son pad…",
                listOf("Allumé en bleu", "Éteint", "Clignotant"), 1, "Le pad de déclenchement s'éteint quand la part est coupée.", OM_MUTE),
            q("$M-l2-q3", "Que fait Part Erase quand tu maintiens un pad pendant la lecture ?",
                listOf("Il coupe le son temporairement", "Il efface les notes de la part", "Il copie la part"), 1,
                "Maintenir le pad efface les données de note de la partie.", OM_ERASE),
        ),
    )

    // ---------------------------------------------------------------------
    // 8.3 Step Jump
    // ---------------------------------------------------------------------
    private val lesson3 = Lesson(
        id = "$M-l3",
        moduleId = M,
        order = 3,
        title = "Step Jump : sauter dans le pattern",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_STEP_JUMP, PG_PATTERN, PG_LAST_STEP),
        durationMinutes = 15,
        objective = "Faire des répétitions, des bégaiements et des cassures en sautant instantanément vers un pas.",
        explanation = "Bouton Step Jump allumé pendant la lecture, un pad fait sauter instantanément la lecture au pas " +
            "correspondant (1 à 16). Si tu appuies sur plusieurs pads, la machine joue chaque pas l'un après l'autre, dans " +
            "l'ordre où tu les as enfoncés. Cas particuliers : en BEAT 8Tri ou 16Tri, les pads 13 à 16 correspondent aux " +
            "pas 1 à 4 de la mesure suivante ; si une part a un LAST STEP plus court, les pads au-delà correspondent aux " +
            "pas 1 et suivants de la mesure suivante.",
        controls = listOf(
            MachineControl("Step Jump", "Allumé pendant la lecture : les pads font sauter la lecture.", OM_STEP_JUMP),
            MachineControl("Un pad", "Saute au pas correspondant (1 à 16).", OM_STEP_JUMP),
            MachineControl("Plusieurs pads", "Jouent les pas dans l'ordre d'enfoncement.", OM_STEP_JUMP),
        ),
        steps = listOf(
            "Lance ton DROP A et allume Step Jump.",
            "Appuie sur le pad 1 régulièrement : la lecture revient au début à chaque pression.",
            "Appuie plusieurs fois de suite sur le pad 13 : le dernier temps bégaie.",
            "Maintiens le pad 1 puis le pad 5 : les deux pas sont joués l'un après l'autre.",
            "Éteins Step Jump pour revenir à la lecture normale, en fin de phrase.",
        ),
        expectedResult = "Tu sais faire bégayer, répéter ou casser le pattern en direct, puis revenir proprement à la lecture normale.",
        exercise = Exercise(
            id = "$M-l3-ex",
            lessonId = "$M-l3",
            title = "Le roulement de fin de phrase",
            instructions = listOf(
                "Sur la dernière mesure d'une phrase de 8, utilise Step Jump pour faire bégayer les derniers pas.",
                "Reviens à la lecture normale juste avant le début de la phrase suivante.",
            ),
            successCriteria = "Un roulement de fin de phrase propre, et une reprise pile au début de la phrase suivante.",
            styleHint = "Tribecore et mentalcore : les bégaiements de kick avant un drop sont très efficaces.",
        ),
        commonMistakes = listOf(
            "Utiliser Step Jump machine arrêtée : il agit pendant la lecture du séquenceur (manuel p. 24).",
            "Oublier d'éteindre Step Jump : les pads ne font alors plus ce que tu attends.",
            "Être surpris par les pads 13 à 16 en ternaire : ils renvoient à la mesure suivante (guide p. 3).",
        ),
        quiz = listOf(
            q("$M-l3-q1", "Que se passe-t-il si tu appuies sur plusieurs pads en Step Jump ?",
                listOf("Seul le dernier pas joue", "Chaque pas est joué successivement, dans l'ordre d'enfoncement", "La lecture s'arrête"), 1,
                "L'instrument joue successivement chaque pas selon l'ordre d'enfoncement des pads.", OM_STEP_JUMP),
            q("$M-l3-q2", "En BEAT 16Tri, à quoi correspondent les pads 13 à 16 en Step Jump ?",
                listOf("Aux pas 13 à 16", "Aux pas 1 à 4 de la mesure suivante", "À rien"), 1,
                "Ils sont assignés aux pas 1 à 4 de la mesure suivante.", PG_PATTERN),
            q("$M-l3-q3", "Combien de pas peut-on atteindre directement avec les pads en Step Jump ?",
                listOf("8", "16", "64"), 1, "On peut passer à tout pas voulu, de 1 à 16.", OM_STEP_JUMP),
        ),
    )

    // ---------------------------------------------------------------------
    // 8.4 Jouer au pad tactile : Gate Arp et Touch Scale
    // ---------------------------------------------------------------------
    private val lesson4 = Lesson(
        id = "$M-l4",
        moduleId = M,
        order = 4,
        title = "Gate Arp et Touch Scale",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_GATE_ARP, OM_TOUCH_SCALE, PG_PATTERN, PG_TOUCH_RANGE, PG_SHORTCUTS),
        durationMinutes = 15,
        objective = "Jouer une part en direct au pad tactile, en rythme (Gate Arpeggiator) ou en notes (Touch Scale).",
        explanation = "Le Gate Arpeggiator rejoue automatiquement le son de la part au tempo : Gate Arp allumé, Trigger " +
            "allumé, maintiens le pad de la part et utilise le pad tactile. L'axe X change la vitesse du gate, l'axe Y la " +
            "longueur du son. Tu peux aussi utiliser Keyboard ou Chord pour choisir la hauteur avec les pads. GATE ARP " +
            "(paramètre de pattern, 1 à 50) choisit le motif ; Shift + Gate Arp affiche cette page. Le Touch Scale joue des " +
            "notes en frottant ou tapotant le pad tactile : Touch Scale allumé, les notes suivent KEY et SCALE (Shift + " +
            "Touch Scale affiche SCALE), et TOUCH SCALE RANGE (menu Global, 1 à 4 octaves) règle la tessiture ; on la " +
            "décale aussi avec Keyboard puis un bouton Step.",
        controls = listOf(
            MachineControl("Gate Arp + Trigger + pad maintenu + pad tactile", "Gate Arpeggiator : X = vitesse du gate, Y = longueur du son.", OM_GATE_ARP),
            MachineControl("GATE ARP (Shift + Gate Arp)", "Motif du Gate Arpeggiator, 1 à 50.", PG_PATTERN),
            MachineControl("Touch Scale + pad tactile", "Jouer des notes en frottant ou tapotant.", OM_TOUCH_SCALE),
            MachineControl("TOUCH SCALE RANGE (menu Global)", "Tessiture du pad tactile, 1 à 4 octaves.", PG_TOUCH_RANGE),
        ),
        steps = listOf(
            "Lance un pattern. Allume Gate Arp, puis Trigger.",
            "Maintiens le pad d'une percussion et pose un doigt sur le pad tactile : la percussion est rejouée en rythme.",
            "Déplace le doigt de gauche à droite (X, vitesse) puis de bas en haut (Y, longueur).",
            "Shift + Gate Arp : change de motif et recommence.",
            "Éteins Gate Arp, allume Touch Scale, sélectionne ta basse et frotte le pad tactile : tu joues des notes de la gamme.",
            "Règle TOUCH SCALE RANGE pour une tessiture plus ou moins large.",
        ),
        expectedResult = "Tu fais rouler une percussion au Gate Arp et tu improvises quelques notes au Touch Scale, toujours dans la gamme.",
        exercise = Exercise(
            id = "$M-l4-ex",
            lessonId = "$M-l4",
            title = "Roulements et réponses",
            instructions = listOf(
                "Pendant 8 tours, fais rouler une caisse claire au Gate Arp en accélérant sur l'axe X à l'approche du drop.",
                "Pendant le break suivant, improvise une réponse mélodique au Touch Scale sur ta basse.",
            ),
            successCriteria = "Un roulement qui accélère jusqu'au drop et une réponse mélodique qui reste dans la gamme.",
            styleHint = "Hardtek : les roulements de caisse claire accélérés avant le retour du kick sont un classique.",
        ),
        commonMistakes = listOf(
            "Toucher le pad tactile sans maintenir le pad de la part en Gate Arp : rien n'est rejoué (manuel p. 29).",
            "Avoir Master Fx allumé en même temps : le pad tactile sert déjà à l'effet maître (manuel p. 22).",
            "Utiliser un objet pointu sur le pad tactile : uniquement le bout des doigts (manuel p. 29).",
            "S'étonner que le timbre ne change pas en Touch Scale : selon les réglages de la part, il se peut qu'il ne change pas (manuel p. 29).",
        ),
        quiz = listOf(
            q("$M-l4-q1", "En Gate Arpeggiator, que règle l'axe X du pad tactile ?",
                listOf("La longueur du son", "La vitesse du gate", "Le volume"), 1, "Axe X : vitesse du gate. Axe Y : longueur du son.", OM_GATE_ARP),
            q("$M-l4-q2", "Combien de motifs GATE ARP la machine propose-t-elle ?",
                listOf("16", "50", "72"), 1, "GATE ARP : 1 à 50.", PG_PATTERN),
            q("$M-l4-q3", "Quel réglage fixe la tessiture du Touch Scale ?",
                listOf("TOUCH SCALE RANGE (1 à 4 octaves)", "GATE ARP", "KNOB MODE"), 0,
                "TOUCH SCALE RANGE définit la plage de hauteur du pad tactile.", PG_TOUCH_RANGE),
            q("$M-l4-q4", "Quel raccourci affiche la page GATE ARP ?",
                listOf("Shift + Gate Arp", "Shift + Touch Scale", "Shift + Tap"), 0, "Shift + Gate Arp affiche la page GATE ARP.", PG_SHORTCUTS),
        ),
    )

    // ---------------------------------------------------------------------
    // 8.5 Transitions entre morceaux
    // ---------------------------------------------------------------------
    private val lesson5 = Lesson(
        id = "$M-l5",
        moduleId = M,
        order = 5,
        title = "Transitions entre morceaux",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_PATTERN_SET, OM_EFFETS_MAITRES, OM_PAD_TACTILE, OM_TEMPO, PG_TEMPO_LOCK, OM_SAUVEGARDE),
        durationMinutes = 20,
        objective = "Passer d'un morceau à l'autre sans trou, en combinant Pattern Set, effet maître, mutes et tempo.",
        explanation = "Une transition combine des outils déjà vus. Le Pattern Set lance le pattern du morceau suivant d'une " +
            "pression. L'effet maître, avec MFX Hold, masque le changement : un écho ou une réverbe qui continue pendant " +
            "que les parts changent. Les mutes préparent le terrain : couper le kick avant le changement rend l'arrivée du " +
            "nouveau morceau plus nette. Pour le tempo : chaque pattern a son BPM ; avec TEMPO LOCK sur Off, le tempo suit " +
            "le nouveau pattern, avec On il reste figé. Tu peux aussi ajuster le tempo à la volée avec Tap ou le paramètre " +
            "BPM. Rappel : l'exercice 7.1 t'a fait vérifier à quel moment ta machine change de pattern ; cale tes gestes " +
            "sur ce moment.",
        controls = listOf(
            MachineControl("Pattern Set + pad", "Lance le pattern du morceau suivant.", OM_PATTERN_SET),
            MachineControl("Master Fx + MFX Hold", "Effet maître figé pendant la transition.", OM_PAD_TACTILE),
            MachineControl("TEMPO LOCK", "Le tempo suit ou non le nouveau pattern.", PG_TEMPO_LOCK),
            MachineControl("Tap / BPM", "Ajuster le tempo à la volée.", OM_TEMPO),
        ),
        steps = listOf(
            "Range la fin d'un morceau et le début d'un autre dans le même groupe de Pattern Set.",
            "Pendant le dernier pattern du premier morceau, envoie les parts voulues dans un effet maître d'écho (MFX Send).",
            "Allume Master Fx, place ton doigt sur le pad tactile, puis allume MFX Hold.",
            "Coupe le kick avec Part Mute, puis lance le premier pattern du morceau suivant avec son pad de Pattern Set.",
            "Éteins MFX Hold pour laisser l'écho s'éteindre et laisse entrer le nouveau morceau.",
            "Recommence avec TEMPO LOCK sur On puis sur Off si tes deux morceaux n'ont pas le même tempo.",
        ),
        expectedResult = "Le passage d'un morceau à l'autre se fait sans silence ni choc de tempo involontaire.",
        exercise = Exercise(
            id = "$M-l5-ex",
            lessonId = "$M-l5",
            title = "Trois transitions types",
            instructions = listOf(
                "Transition « coupure » : break, puis entrée directe du morceau suivant.",
                "Transition « écho » : effet maître figé avec MFX Hold pendant le changement.",
                "Transition « tempo » : passer d'un morceau tribe à un morceau plus rapide en ajustant le BPM.",
            ),
            successCriteria = "Les trois transitions maîtrisées, et pour chacune tu sais dans quel contexte l'utiliser.",
            styleHint = "Du tribe au mentalcore : monter le tempo progressivement entre deux morceaux garde le public avec toi.",
        ),
        commonMistakes = listOf(
            "Changer de pattern sans avoir sauvegardé une modification faite en live : elle est perdue (manuel p. 31).",
            "Laisser TEMPO LOCK sur On en oubliant qu'il fige le tempo : le morceau suivant joue au tempo du précédent (guide p. 8).",
            "Laisser MFX Hold allumé après la transition : l'effet continue sur le nouveau morceau.",
        ),
        quiz = listOf(
            q("$M-l5-q1", "Avec TEMPO LOCK sur Off, que fait le tempo quand tu passes à un pattern d'un autre BPM ?",
                listOf("Il suit le BPM du nouveau pattern", "Il reste figé", "Il revient à 120"), 0,
                "Avec On, le tempo est verrouillé ; sur Off, chaque pattern joue à son propre tempo.", PG_TEMPO_LOCK),
            q("$M-l5-q2", "Quel bouton garde l'effet maître actif sans le doigt sur le pad tactile ?",
                listOf("MFX Hold", "MFX Send", "IFX On"), 0, "MFX Hold conserve l'état de l'effet maître.", OM_PAD_TACTILE),
            q("$M-l5-q3", "Que se passe-t-il si tu changes de pattern après l'avoir modifié sans Write ?",
                listOf("Les modifications sont perdues", "Elles sont sauvegardées", "Le pattern est dupliqué"), 0,
                "Changer de motif sans sauvegarder entraîne la perte des changements.", OM_SAUVEGARDE),
        ),
    )

    // ---------------------------------------------------------------------
    // 8.6 Enregistrer sa performance (Event Recorder)
    // ---------------------------------------------------------------------
    private val lesson6 = Lesson(
        id = "$M-l6",
        moduleId = M,
        order = 6,
        title = "Enregistrer sa performance",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_EVENT_REC, PG_EVENT),
        durationMinutes = 15,
        objective = "Enregistrer un live complet sur la carte SD avec l'Event Recorder, puis le réécouter.",
        explanation = "L'Event Recorder enregistre ton jeu en direct : patterns lancés, manipulations de commandes et de pads, " +
            "changements de son et de tempo. Il faut une carte SD (sinon « No Card ») et CLOCK MODE sur « Internal ». Les " +
            "fichiers sont écrits sur la carte, 100 au maximum. Ce qui est enregistré ne modifie pas tes patterns, et ne " +
            "peut pas être sauvegardé dans un pattern. Pour réécouter : EVENT PLAYER, choisir le fichier, puis Play/Pause ; " +
            "Stop revient au début du fichier, Exit quitte. Pendant la lecture d'un fichier, les autres opérations ne sont " +
            "pas possibles.",
        controls = listOf(
            MachineControl("EVENT REC/PLAY (menu d'édition)", "Accès à l'Event Recorder et à l'Event Player.", OM_EVENT_REC),
            MachineControl("Menu/Enter puis Play/Pause", "Prêt à enregistrer, puis début de l'enregistrement.", OM_EVENT_REC),
            MachineControl("Stop puis Exit", "Fin du jeu, puis arrêt de l'enregistrement.", OM_EVENT_REC),
            MachineControl("EVENT PLAYER", "Relire un fichier enregistré.", OM_EVENT_REC),
        ),
        steps = listOf(
            "Vérifie que la carte SD est en place (insérée machine éteinte) et que CLOCK MODE est sur « Internal ».",
            "Dans le menu d'édition, choisis « EVENT REC/PLAY » et appuie sur Menu/Enter.",
            "Appuie à nouveau sur Menu/Enter : l'Event Recorder est prêt à enregistrer.",
            "Appuie sur Play/Pause pour lancer l'enregistrement, puis appuie plusieurs fois sur Exit pour revenir à l'écran de sélection de pattern et joue.",
            "À la fin, appuie sur Stop : le nom du fichier s'affiche. Appuie sur Exit pour arrêter l'enregistrement.",
            "Pour réécouter : EVENT PLAYER, choisis le fichier avec la commande de valeur et Menu/Enter, puis Play/Pause.",
        ),
        expectedResult = "Un fichier de ta performance sur la carte, que tu peux rejouer sur la machine pour écouter ton live.",
        exercise = Exercise(
            id = "$M-l6-ex",
            lessonId = "$M-l6",
            title = "Le live enregistré",
            instructions = listOf(
                "Enregistre 10 minutes de live avec au moins deux morceaux et une transition.",
                "Réécoute-le avec l'Event Player et note trois moments à améliorer.",
            ),
            successCriteria = "Un enregistrement complet et trois points d'amélioration notés pour le Module 9.",
            styleHint = "Se réécouter est le moyen le plus rapide de progresser sur les transitions et la gestion de l'énergie.",
        ),
        commonMistakes = listOf(
            "Lancer l'Event Recorder sans carte : la machine affiche « No Card » et la fonction est inutilisable (manuel p. 34).",
            "Être synchronisé sur une horloge externe : l'Event Recorder demande CLOCK MODE sur « Internal » (guide p. 10).",
            "Croire que l'enregistrement modifie les patterns : il n'affecte pas les données des patterns (manuel p. 34).",
            "Accumuler les fichiers : 100 au maximum (guide p. 10).",
        ),
        quiz = listOf(
            q("$M-l6-q1", "Quel réglage de CLOCK MODE l'Event Recorder demande-t-il ?",
                listOf("Internal", "External MIDI", "Auto"), 0, "EVENT REC/PLAY est disponible si CLOCK MODE est réglé sur Internal.", PG_EVENT),
            q("$M-l6-q2", "Combien de fichiers d'Event Recorder peut-on avoir au maximum ?",
                listOf("24", "64", "100"), 2, "Il peut y avoir 100 fichiers au maximum.", PG_EVENT),
            q("$M-l6-q3", "Un enregistrement de l'Event Recorder modifie-t-il les patterns ?",
                listOf("Oui", "Non", "Seulement le pattern en cours"), 1,
                "Les données enregistrées n'affectent pas les données des motifs.", OM_EVENT_REC),
            q("$M-l6-q4", "Que s'affiche-t-il si on utilise l'Event Recorder sans carte ?",
                listOf("« Card Full »", "« No Card »", "« Protected »"), 1, "Sans carte, l'instrument affiche « No Card ».", OM_EVENT_REC),
        ),
    )

    val lessons: List<Lesson> = listOf(lesson1, lesson2, lesson3, lesson4, lesson5, lesson6)
}
