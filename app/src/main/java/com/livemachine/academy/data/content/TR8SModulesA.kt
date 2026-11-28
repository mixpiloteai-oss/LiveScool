package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** TR-8S — modules 1 à 3 : prise en main, TR-REC, jeu en temps réel. */
internal object TR8SModulesA {

    // Références du mode d'emploi (pages imprimées = pages du PDF)
    private val PANNEAU = tr8s("Description des panneaux", "4–5")
    private val ARRIERE = tr8s("Face arrière (connexion de votre équipement)", "6–7")
    private val ALLUMAGE = tr8s("Mise sous / hors tension", "7")
    private val MEMOIRE = tr8s("Présentation du TR-8S", "8")
    private val WRITE = tr8s("Enregistrer le motif, le kit ou les réglages système (WRITE)", "18")
    private val LECTURE = tr8s("Lecture de motifs, Sélection d'un motif, Variations", "9")
    private val LAST = tr8s("Modification de la longueur du motif (LAST STEP)", "9")
    private val TR_REC = tr8s("TR-REC (enregistrement par pas)", "11")
    private val TEMPS_FAIBLES = tr8s("Jouer des temps faibles, Flam Spacing", "19")
    private val INST_REC = tr8s("INST-REC (enregistrement en temps réel)", "12")
    private val INST_PLAY = tr8s("INST-PLAY (performance), ROLL", "13")
    private val CLEAR = tr8s("Supprimer un motif (CLEAR)", "19")
    private val MUTE = tr8s("Désactiver le son (MUTE), SOLO", "20")

    // ---------------------------------------------------------------------
    // Module 1 — Prise en main
    // ---------------------------------------------------------------------
    private const val M1 = "tr8s-m1"

    private val m1l1 = ctLesson(
        M1, 1, "Brancher et allumer", listOf(ARRIERE, ALLUMAGE), 10,
        objective = "Relier la TR-8S à ton écoute et l'allumer dans le bon ordre.",
        explanation = "Volume baissé et appareils éteints avant de brancher. Adaptateur fourni sur DC IN (seulement celui-là). MIX OUT " +
            "(L/MONO, R) vers l'ampli ou les enceintes, PHONES pour le casque. Les prises ASSIGNABLE OUT/TRIGGER OUT 1 à 6 servent de " +
            "sorties séparées ou de déclencheurs ; EXT IN reçoit une source externe ; MIDI et USB servent à la synchronisation. Allumage : " +
            "la TR-8S d'abord, puis le système connecté ; extinction : le système d'abord, puis la TR-8S. Un léger bruit à l'allumage " +
            "est normal.",
        controls = listOf(
            cmd("DC IN, POWER", "Alimentation et interrupteur.", ARRIERE),
            cmd("MIX OUT, PHONES", "Sortie principale et casque.", ARRIERE),
            cmd("VOLUME", "Volume de MIX OUT et PHONES.", PANNEAU),
        ),
        steps = listOf(
            "Volume au minimum, relie MIX OUT à ta table ou tes enceintes.",
            "Branche l'adaptateur fourni sur DC IN.",
            "Allume la TR-8S, puis l'ampli, et monte le volume.",
            "Pour éteindre : l'ampli d'abord, puis la TR-8S.",
        ),
        expected = "La TR-8S sonne sur tes enceintes sans bruit fort.",
        exerciseTitle = "Ordre d'allumage",
        exercise = listOf("Éteins et rallume tout dans l'ordre du mode d'emploi."),
        success = "Aucun claquement dans les enceintes.",
        mistakes = listOf(
            "Utiliser un autre adaptateur : le mode d'emploi l'interdit (polarité ou tension différente).",
            "Allumer l'ampli avant la TR-8S.",
        ),
        quiz = listOf(
            qz("Que faut-il allumer en premier ?", listOf("La TR-8S", "L'ampli"), 0,
                "TR-8S d'abord, puis le système connecté.", ALLUMAGE),
            qz("Sortie principale ?", listOf("MIX OUT", "EXT IN"), 0,
                "MIX OUT L/MONO et R.", ARRIERE),
            qz("Le bouton VOLUME règle-t-il ASSIGNABLE OUT ?", listOf("Non", "Oui"), 0,
                "Il règle MIX OUT et PHONES seulement.", PANNEAU),
            qz("Que faut-il éteindre en dernier ?", listOf("La TR-8S", "L'ampli"), 0,
                "Le système d'abord, puis la TR-8S.", ALLUMAGE),
        ),
    )

    private val m1l2 = ctLesson(
        M1, 2, "Motifs, variations, kits et sauvegarde", listOf(MEMOIRE, WRITE), 15,
        objective = "Comprendre la mémoire de la TR-8S et ne jamais perdre un réglage.",
        explanation = "128 motifs (16 motifs × 8 banques). Chaque motif a 8 variations (A à H) et 2 motifs de transition. Un kit regroupe " +
            "les 11 instruments (BD à RC) ; il y a 128 kits. Les modifications restent en mémoire tant qu'on n'éteint pas ; à " +
            "l'extinction, tout revient à l'état sauvegardé. Pour garder : WRITE puis Pattern, Kit ou System, destination, OK. " +
            "Raccourcis : WRITE + PTN SELECT (motif), WRITE + KIT (kit), SHIFT + WRITE (motif et kit écrasés ensemble). Un « * » signale " +
            "un kit modifié non sauvegardé.",
        controls = listOf(
            cmd("WRITE", "Sauvegarder motif, kit ou système.", WRITE),
            cmd("SHIFT + WRITE", "Écraser motif et kit d'un coup.", MEMOIRE),
            cmd("VALUE, ENTER", "Choisir et confirmer.", WRITE),
        ),
        steps = listOf(
            "Modifie le TUNE du BD.",
            "Appuie sur WRITE, choisis « Kit », un numéro libre, puis OK.",
            "Après une modification de motif, fais SHIFT + WRITE.",
        ),
        expected = "Tes réglages survivent à l'extinction.",
        exerciseTitle = "Test d'extinction",
        exercise = listOf("Modifie un kit sans sauvegarder, éteins, rallume : constate la perte. Recommence en sauvegardant."),
        success = "Tu ne perds plus tes réglages.",
        mistakes = listOf("Éteindre sans WRITE : les modifications sont perdues."),
        quiz = listOf(
            qz("Variations par motif ?", listOf("8 (A à H)", "4"), 0, "Plus 2 transitions.", MEMOIRE),
            qz("Instruments par kit ?", listOf("11", "16"), 0, "De BD à RC.", MEMOIRE),
            qz("Que se passe-t-il à l'extinction sans WRITE ?", listOf("Retour à l'état sauvegardé", "Sauvegarde auto"), 0,
                "Les modifications sont perdues.", MEMOIRE),
            qz("Sauvegarder motif et kit ensemble ?", listOf("SHIFT + WRITE", "COPY + KIT"), 0, "Écrasement des deux.", MEMOIRE),
        ),
    )

    private val m1l3 = ctLesson(
        M1, 3, "Lire et choisir les motifs", listOf(LECTURE), 15,
        objective = "Lancer un motif, changer de motif et enchaîner les variations.",
        explanation = "START/STOP lance et arrête ; SHIFT + START/STOP revient au début. Choisir un motif : maintenir PTN SELECT + pad [1]–[8] " +
            "(banque), relâcher, puis pad [1]–[16] (motif). Pendant la lecture, le nouveau motif démarre quand la lecture revient au " +
            "premier pas. Deux pads en même temps = une plage de motifs lus à la suite. Les boutons [A]–[H] choisissent la variation ; " +
            "maintenir l'un et appuyer sur d'autres les fait jouer une fois chacune dans l'ordre (vert fixe = prête, vert clignotant = " +
            "en lecture).",
        controls = listOf(
            cmd("START/STOP", "Lecture / arrêt.", LECTURE),
            cmd("PTN SELECT + pads", "Banque puis motif.", LECTURE),
            cmd("[A]–[H]", "Variations.", LECTURE),
        ),
        steps = listOf(
            "Lance la lecture avec START/STOP.",
            "Maintiens PTN SELECT, appuie sur pad [2], relâche, puis pad [5].",
            "Maintiens [A] et appuie sur [B] : A puis B s'enchaînent.",
        ),
        expected = "Les variations A et B s'enchaînent en boucle.",
        exerciseTitle = "Enchaînement A-A-A-B",
        exercise = listOf("Fais jouer A, puis A+B, et observe les couleurs des boutons."),
        success = "Tu sais quelle variation joue rien qu'en regardant les boutons.",
        style = "Hardtek : trois mesures identiques et une variation qui relance.",
        mistakes = listOf("S'attendre à un changement immédiat : il se fait au retour du premier pas."),
        quiz = listOf(
            qz("Choisir la banque ?", listOf("PTN SELECT + pad [1]–[8]", "SHIFT + pad"), 0, "Puis pad [1]–[16].", LECTURE),
            qz("Quand le nouveau motif démarre-t-il ?", listOf("Au retour au premier pas", "Immédiatement"), 0, "Le pad clignote en attendant.", LECTURE),
            qz("Revenir au début du motif ?", listOf("SHIFT + START/STOP", "CLEAR"), 0, "Retour au premier pas.", LECTURE),
            qz("Bouton de variation vert clignotant = …", listOf("En lecture", "Prête"), 0, "Vert fixe = prête.", LECTURE),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 2 — TR-REC
    // ---------------------------------------------------------------------
    private const val M2 = "tr8s-m2"

    private val m2l1 = ctLesson(
        M2, 1, "Programmer pas à pas", listOf(TR_REC), 20,
        objective = "Écrire un rythme instrument par instrument sur les 16 pas.",
        explanation = "TR-REC, choisir la variation (A–H), START/STOP pour écouter pendant qu'on programme. Un bouton de sélection [BD]–[RC] " +
            "choisit l'instrument ; les pads [1]–[16] allument les pas où il joue. On répète pour chaque instrument. Maintenir un bouton " +
            "d'instrument et appuyer sur CLEAR efface tout cet instrument ; CLEAR pendant la lecture l'efface seulement tant qu'on le " +
            "tient. Penser à sauvegarder avec WRITE.",
        controls = listOf(
            cmd("TR-REC", "Enregistrement par pas.", TR_REC),
            cmd("[BD]–[RC]", "Instrument programmé.", TR_REC),
            cmd("Pads [1]–[16]", "Pas.", TR_REC),
            cmd("Instrument + CLEAR", "Effacer un instrument.", TR_REC),
        ),
        steps = listOf(
            "TR-REC, variation A, START/STOP.",
            "BD : pads 1, 5, 9, 13.",
            "SD : pads 5 et 13 ; CH : pads 3, 7, 11, 15.",
            "Sauvegarde avec WRITE + PTN SELECT.",
        ),
        expected = "Un rythme de base qui tourne.",
        exerciseTitle = "Rythme hardtek",
        exercise = listOf("Programme un kick à chaque temps et un charley ouvert sur les contretemps, puis une variation B avec un break."),
        success = "A et B jouent bien, sauvegardés.",
        style = "Hardtek : kick sur chaque temps, charley ouvert sur les contretemps.",
        mistakes = listOf("Oublier de choisir l'instrument avant les pads : les pas vont sur le mauvais son."),
        quiz = listOf(
            qz("Choisir l'instrument à programmer ?", listOf("Boutons [BD]–[RC]", "Pads [1]–[16]"), 0, "Les pads sont les pas.", TR_REC),
            qz("Effacer tout un instrument ?", listOf("Maintenir son bouton + CLEAR", "SHIFT + CLEAR"), 0, "Dans la variation choisie.", TR_REC),
            qz("CLEAR pendant la lecture…", listOf("Efface tant qu'il est tenu", "Efface tout le motif"), 0, "Seulement l'instrument choisi.", TR_REC),
            qz("Peut-on écouter pendant TR-REC ?", listOf("Oui, avec START/STOP", "Non"), 0, "On programme en écoutant.", TR_REC),
        ),
    )

    private val m2l2 = ctLesson(
        M2, 2, "Accents, sous-pas, flam et temps faibles", listOf(TR_REC, TEMPS_FAIBLES), 15,
        objective = "Donner du relief et des ornements à un rythme.",
        explanation = "Accents : ACCENT [STEP], puis les pads des pas accentués ; ACCENT [LEVEL] règle leur volume. Sous-pas : SUB puis un pad " +
            "(ou SUB + pad) ; SUB + VALUE choisit 1/2, 1/3 ou 1/4 (duolets, triolets, quartolets). SHIFT + SUB alterne SUB STEP et FLAM : " +
            "on place alors des flams (Flam Spacing 0 = pas de flam). Temps faible : SHIFT + pad (ou mode PAD dans UTILITY > GENERAL > " +
            "WeakBeat). Son alternatif (sons « / », ex. 707Bass1/2) : bouton d'instrument maintenu + pad.",
        controls = listOf(
            cmd("ACCENT [STEP] / [LEVEL]", "Placer et doser les accents.", TR_REC),
            cmd("SUB, SUB + VALUE", "Sous-pas 1/2, 1/3, 1/4.", TR_REC),
            cmd("SHIFT + SUB", "SUB STEP ou FLAM.", TR_REC),
            cmd("SHIFT + pad", "Temps faible.", TEMPS_FAIBLES),
        ),
        steps = listOf(
            "ACCENT [STEP], allume les pas 1 et 9, règle ACCENT [LEVEL].",
            "Sur la caisse claire, SUB + pad 16 en 1/3 : un triolet de relance.",
            "SHIFT + SUB pour passer en FLAM, puis un pad : un flam.",
            "SHIFT + pad pour un temps faible sur un charley.",
        ),
        expected = "Un rythme accentué, avec une relance en triolet.",
        exerciseTitle = "Relance tribecore",
        exercise = listOf("Ajoute des sous-pas 1/4 sur les deux derniers pas de la caisse claire."),
        success = "La relance roule avant le retour au pas 1.",
        style = "Tribecore : roulements de caisse claire en fin de boucle.",
        mistakes = listOf("Laisser SUB sur FLAM sans le voir : les pads placent des flams au lieu de sous-pas."),
        quiz = listOf(
            qz("Divisions de SUB ?", listOf("1/2, 1/3, 1/4", "1/8, 1/16"), 0, "Avec SUB + VALUE.", TR_REC),
            qz("Passer en FLAM ?", listOf("SHIFT + SUB", "SHIFT + ACCENT"), 0, "Alterne avec SUB STEP.", TR_REC),
            qz("Placer les accents ?", listOf("ACCENT [STEP] + pads", "TUNE"), 0, "ACCENT [LEVEL] pour le volume.", TR_REC),
            qz("Flam Spacing à 0…", listOf("Pas d'effet flam", "Flam maximal"), 0, "0 = aucun flam.", TEMPS_FAIBLES),
        ),
    )

    private val m2l3 = ctLesson(
        M2, 3, "Longueur des motifs (LAST STEP)", listOf(LAST), 15,
        objective = "Raccourcir un motif ou un seul instrument pour des rythmes décalés.",
        explanation = "LAST, puis [A]–[H] pour la variation, puis un pad [1]–[16] = dernier pas. Pour un instrument seul : LAST, bouton [BD]–[RC], " +
            "puis le pad du dernier pas. Plusieurs variations ou instruments : maintenir un bouton et appuyer sur les autres. Réinitialiser " +
            "un instrument : LAST, son bouton (il clignote), puis CLEAR ; il reprend la longueur de la variation.",
        controls = listOf(
            cmd("LAST", "Mode dernier pas.", LAST),
            cmd("[A]–[H] ou [BD]–[RC] + pad", "Longueur de la variation ou de l'instrument.", LAST),
            cmd("LAST + instrument + CLEAR", "Réinitialiser.", LAST),
        ),
        steps = listOf(
            "LAST, bouton du charley fermé, pad 12 : le charley boucle sur 12 pas.",
            "LAST, bouton d'une percussion, pad 7.",
            "Écoute les cycles se décaler par rapport au kick sur 16.",
        ),
        expected = "Des percussions qui tournent sur 12 et 7 pas contre un kick sur 16.",
        exerciseTitle = "Polyrythmie",
        exercise = listOf("Mets trois percussions sur 16, 12 et 5 pas."),
        success = "Le groove évolue sans programmer de nouvelles mesures.",
        style = "Tribe : des percussions sur des cycles de longueurs différentes.",
        mistakes = listOf("Raccourcir le kick : la base du morceau se décale."),
        quiz = listOf(
            qz("Longueur d'un seul instrument ?", listOf("LAST + son bouton + pad", "SUB + pad"), 0, "Le pad = dernier pas.", LAST),
            qz("Réinitialiser un instrument ?", listOf("LAST, son bouton, CLEAR", "WRITE"), 0, "Il reprend la longueur générale.", LAST),
            qz("La longueur est-elle propre à chaque variation ?", listOf("Oui", "Non"), 0, "Réglable par variation.", LAST),
            qz("Plusieurs instruments à la fois ?", listOf("Maintenir un bouton, appuyer sur d'autres", "Impossible"), 0, "Sélection multiple.", LAST),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 3 — Jeu en temps réel
    // ---------------------------------------------------------------------
    private const val M3 = "tr8s-m3"

    private val m3l1 = ctLesson(
        M3, 1, "Enregistrer en jouant (INST REC)", listOf(INST_REC), 15,
        objective = "Enregistrer ta performance sur les pads [1] (BD) à [11] (RC).",
        explanation = "INST REC, START/STOP, choisir la variation [A]–[H], puis jouer les pads [1] (BD) à [11] (RC) : le motif sélectionné est " +
            "modifié. Les mouvements de la section instrument (TUNE, DECAY…) ne sont pas enregistrés ainsi. SUB, SHIFT + SUB (flam), " +
            "SHIFT + pad (temps faible) et bouton d'instrument + pad (son alternatif) marchent aussi. Le pad Inst, sensible à la vélocité, " +
            "joue l'instrument choisi par son bouton.",
        controls = listOf(
            cmd("INST REC", "Enregistrement en temps réel.", INST_REC),
            cmd("Pads [1]–[11]", "Un instrument par pad.", INST_REC),
            cmd("Pad Inst", "Jeu sensible à la vélocité.", INST_REC),
        ),
        steps = listOf(
            "INST REC, START/STOP, variation C.",
            "Joue des percussions sur les pads [8] à [11].",
            "Ajoute un temps faible avec SHIFT + pad.",
            "Sauvegarde avec WRITE.",
        ),
        expected = "Tes percussions jouées sont dans la variation C.",
        exerciseTitle = "Percussions jouées",
        exercise = listOf("Enregistre une ligne de percussions en deux passes."),
        success = "Le groove joué tourne avec le reste.",
        style = "Tribe : percussions jouées à la main.",
        mistakes = listOf("Croire que TUNE et DECAY sont enregistrés : il faut MOTION."),
        quiz = listOf(
            qz("Pads de jeu en INST REC ?", listOf("[1] (BD) à [11] (RC)", "[1] à [16]"), 0, "Un instrument par pad.", INST_REC),
            qz("TUNE et DECAY s'enregistrent-ils ?", listOf("Non", "Oui"), 0, "Opérations de la section instrument non enregistrées.", INST_REC),
            qz("Pad sensible à la vélocité ?", listOf("Le pad Inst", "Les pads [1]–[16]"), 0, "Volume selon la frappe.", INST_REC),
            qz("Son alternatif ?", listOf("Bouton d'instrument + pad", "SHIFT + SUB"), 0, "Pour les sons « / ».", INST_REC),
        ),
    )

    private val m3l2 = ctLesson(
        M3, 2, "Jouer en direct (INST PLAY) et roulements", listOf(INST_PLAY), 10,
        objective = "Jouer par-dessus le motif sans le modifier.",
        explanation = "INST PLAY : les pads [1] (BD) à [11] (RC) jouent en direct sans toucher au motif. Roulement : maintenir le pad [12] " +
            "(double croche), [13] (triple croche) ou les deux (quadruple croche) et appuyer sur un pad [1]–[11]. Autre méthode : INST " +
            "PLAY + pad [12]/[13] pour le verrouiller, puis les pads ; un nouvel appui annule. SUB, flam et temps faibles marchent aussi.",
        controls = listOf(
            cmd("INST PLAY", "Jeu sans enregistrer.", INST_PLAY),
            cmd("Pad [12] / [13] + pad", "Roulement.", INST_PLAY),
        ),
        steps = listOf(
            "Lance un motif, appuie sur INST PLAY.",
            "Maintiens le pad [12] et appuie sur la caisse claire : roulement en doubles croches.",
            "Passe à [12] + [13] pour des quadruples croches.",
        ),
        expected = "Des roulements par-dessus le motif, sans le changer.",
        exerciseTitle = "Roulement de relance",
        exercise = listOf("Juste avant chaque 4e mesure, fais un roulement de caisse claire."),
        success = "La relance tombe pile.",
        style = "Hardtek : le roulement avant le retour du kick.",
        mistakes = listOf("Jouer en INST REC en voulant improviser : la performance serait enregistrée."),
        quiz = listOf(
            qz("INST PLAY modifie-t-il le motif ?", listOf("Non", "Oui"), 0, "La performance ne change pas le motif.", INST_PLAY),
            qz("Pad [13] = …", listOf("Triple croche", "Double croche"), 0, "[12] double, [13] triple.", INST_PLAY),
            qz("[12] + [13] = …", listOf("Quadruple croche", "Croche"), 0, "Roulement le plus rapide.", INST_PLAY),
            qz("Verrouiller le roulement ?", listOf("INST PLAY + pad [12]/[13]", "SHIFT + ROLL"), 0, "Un nouvel appui annule.", INST_PLAY),
        ),
    )

    private val m3l3 = ctLesson(
        M3, 3, "Mute, solo et effacement", listOf(MUTE, CLEAR), 10,
        objective = "Construire des breaks en coupant et isolant les instruments.",
        explanation = "MUTE puis les boutons [BD]–[RC] coupent (allumé) ou rétablissent (éteint) chaque instrument ; ou MUTE maintenu + bouton. " +
            "Cela marche en TR-REC, PTN SELECT, INST REC et INST PLAY. Solo : MUTE allumé, SHIFT + bouton d'instrument. MUTE + CLEAR annule " +
            "toutes les coupures. Effacer le motif d'un instrument : bouton d'instrument maintenu + CLEAR (variation choisie seulement).",
        controls = listOf(
            cmd("MUTE + [BD]–[RC]", "Couper / rétablir.", MUTE),
            cmd("MUTE allumé + SHIFT + instrument", "Solo.", MUTE),
            cmd("MUTE + CLEAR", "Tout rétablir.", MUTE),
        ),
        steps = listOf(
            "Pendant la lecture, MUTE puis BD : break sans kick.",
            "Rétablis le BD pile sur le 1.",
            "Mets la caisse claire en solo, puis MUTE + CLEAR pour tout rétablir.",
        ),
        expected = "Un break sans kick et un retour net.",
        exerciseTitle = "Break de 8 mesures",
        exercise = listOf("Coupe BD et SD 8 mesures, réintroduis la SD puis le BD."),
        success = "Les retours tombent sur le premier temps.",
        style = "Tribe / hardtek : break sans kick puis retour.",
        mistakes = listOf("Confondre coupure et effacement : CLEAR avec un instrument efface ses pas."),
        quiz = listOf(
            qz("Mettre en solo ?", listOf("MUTE allumé, SHIFT + instrument", "SHIFT + MUTE"), 0, "Seul cet instrument s'entend.", MUTE),
            qz("Annuler toutes les coupures ?", listOf("MUTE + CLEAR", "START/STOP"), 0, "Instantané.", MUTE),
            qz("MUTE marche en INST PLAY ?", listOf("Oui", "Non"), 0, "Dans tous les modes cités.", MUTE),
            qz("Instrument maintenu + CLEAR…", listOf("Efface ses pas", "Le coupe"), 0, "Dans la variation choisie.", CLEAR),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(M1, 1, "Prise en main", "Branchements, mémoire, sauvegarde, lecture des motifs et variations.",
            "Installer la TR-8S et lire ses motifs.", "Les variations A–H structurent un morceau hardtek.", listOf(m1l1, m1l2, m1l3)),
        CourseModule(M2, 2, "Programmer (TR-REC)", "Pas, accents, sous-pas, flams, temps faibles, longueur.",
            "Écrire des rythmes complets.", "Accents et relances donnent l'énergie de la free party.", listOf(m2l1, m2l2, m2l3)),
        CourseModule(M3, 3, "Jouer en direct", "INST REC, INST PLAY, roulements, mute, solo.",
            "Jouer et enregistrer en temps réel.", "Breaks et roulements sont les gestes de base du live.", listOf(m3l1, m3l2, m3l3)),
    )
}
