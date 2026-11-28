package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** DrumBrute Impact — modules 5 à 7 : édition au pas, groove et longueur, performance et morceaux. */
internal object DrumBruteImpactModulesB {

    private val STEP_ACCENT = dbi("4.2.1–4.2.2 Modes Step et Accent", "47")
    private val SELECTION = dbi("4.2.4–4.2.5 Phases, sélectionner un élément", "48")
    private val STEP_REPEAT = dbi("4.2.6 La fonction Step Repeat", "49–50")
    private val DECALAGE = dbi("4.2.7 Décalage temporel des événements", "51")
    private val EFFETS_MOTIF = dbi("1.2.8 Effets de motifs", "10")
    private val SWING = dbi("4.1.7 Swing", "44–45")
    private val RANDOM = dbi("4.1.8 Random", "46")
    private val LONGUEUR = dbi("4.2.10 Longueur d'un motif", "55–56")
    private val PROLONGER = dbi("7.1–7.2 Prolonger et raccourcir un motif", "67–68")
    private val POLY = dbi("7.3 Mode Polyrhythm", "69–71")
    private val SHIFT_TABLE = dbi("7.4 Tableau du bouton Shift", "72")
    private val BANDE = dbi("1.2.11 Roller/Looper/Step Repeat", "12")
    private val LOOPER_ROLLER = dbi("2.3.3 Looper/Roller", "26–27")
    private val ROLLER_REC = dbi("4.1.5 Utiliser le Roller tout en enregistrant", "42")
    private val COPIER_PISTE = dbi("4.2.8 Copier une piste de batterie", "52–53")
    private val EFFACER_PISTE = dbi("4.2.9 Supprimer une piste de batterie", "54")
    private val COPIER_MOTIF = dbi("4.5 Copier un motif", "58")
    private val COPIER_EFFACER = dbi("6. Les fonctions Copier/Supprimer", "64–66")
    private val SONG = dbi("5.1–5.3 Qu'est-ce qu'un morceau, sélectionner, créer", "59–60")
    private val SONG_SAVE = dbi("5.4–5.6 Restaurer, sauvegarder, supprimer un morceau", "60–61")
    private val SONG_LIVE = dbi("5.7–5.8 Performance live en mode Song", "62–63")

    // ---------------------------------------------------------------------
    // Module 5 — Édition au pas
    // ---------------------------------------------------------------------
    private const val M5 = "dbi-m5"

    private val m5l1 = ctLesson(
        M5, 1, "Programmer au pas (mode Step)", listOf(STEP_ACCENT, SELECTION), 20,
        objective = "Construire un motif bouton par bouton, instrument par instrument.",
        explanation = "Le bouton Step affiche, sur les 16 boutons de Pas, les coups de l'instrument sélectionné : allumé = coup, " +
            "éteint = pas vide. On active ou désactive un pas en appuyant dessus. Pour choisir l'instrument : à l'arrêt, tape son pad " +
            "(il reste allumé) ; pendant l'enregistrement, utilise Shift + pad pour ne pas ajouter de note. L'édition est possible " +
            "en enregistrement comme en pause.",
        controls = listOf(
            cmd("Step", "Mode Step.", STEP_ACCENT),
            cmd("Boutons de Pas 1–16", "Activent/désactivent les coups.", STEP_ACCENT),
            cmd("Pad (ou Shift + pad)", "Sélectionne l'instrument édité.", SELECTION),
        ),
        steps = listOf(
            "À l'arrêt, choisis un motif vide et appuie sur Step.",
            "Tape le pad Kick pour le sélectionner, puis active les Pas 1, 5, 9 et 13.",
            "Tape le pad Snare 1 et active les Pas 5 et 13.",
            "Tape le pad Closed Hat et active les Pas 3, 7, 11 et 15.",
            "Lance Play, écoute, puis sauve avec Save + Ptrn.",
        ),
        expected = "Un rythme complet est programmé sans jouer sur les pads.",
        exerciseTitle = "Trois patterns de base",
        exercise = listOf(
            "Programme au pas un rythme hardtek, un rythme tribe et un rythme plus cassé sur trois motifs.",
            "Sauve chacun avec Save + Ptrn.",
        ),
        success = "Les trois motifs sont sauvegardés et se distinguent à l'écoute.",
        style = "Tribe : ajoute des toms et la cowbell en contretemps.",
        mistakes = listOf(
            "Éditer les pas du mauvais instrument : vérifie quel pad est allumé.",
            "Taper un pad pendant l'enregistrement pour le sélectionner : ça ajoute une note, utilise Shift + pad.",
        ),
        quiz = listOf(
            qz("En mode Step, que montre un bouton de Pas allumé ?", listOf("Un coup sur ce pas", "La position de lecture", "Un motif"), 0,
                "Un pas allumé contient un événement pour l'instrument sélectionné.", STEP_ACCENT),
            qz("Comment sélectionner un instrument à l'arrêt ?", listOf("Taper son pad", "Shift + Step", "Type + Step"), 0,
                "En pause, il suffit de taper le pad : il reste allumé.", SELECTION),
            qz("Peut-on éditer en mode Step pendant l'enregistrement ?", listOf("Oui", "Non"), 0,
                "Les modifications sont possibles en enregistrement comme en pause.", STEP_ACCENT),
            qz("Un pas activé en mode Step s'allume en…", listOf("Bleu", "Orange", "Blanc"), 0,
                "Les événements activés en mode Step sont allumés en bleu.", STEP_ACCENT),
        ),
    )

    private val m5l2 = ctLesson(
        M5, 2, "Répétition de pas (Step Repeat)", listOf(STEP_REPEAT, BANDE), 15,
        objective = "Faire jouer un coup plusieurs fois dans un seul pas, sans changer la division du motif.",
        explanation = "En mode Step, maintiens un pas allumé : une LED blanche sous la bande tactile montre sa valeur de répétition " +
            "(1 par défaut). En glissant le doigt sur la bande, tu choisis 2, 3 ou 4 : le coup est répété autant de fois dans le pas. " +
            "Un appui bref sur le pas l'active/désactive et remet la répétition à 1 ; maintenu 700 ms ou plus, il permet de voir ou " +
            "changer la répétition sans l'éteindre.",
        controls = listOf(
            cmd("Pas maintenu", "Affiche la valeur de répétition (LED blanche).", STEP_REPEAT),
            cmd("Bande tactile", "Choisit la valeur de répétition.", STEP_REPEAT),
        ),
        steps = listOf(
            "Fais boucler un motif avec un kick sur chaque temps.",
            "Passe en mode Step et sélectionne le kick avec Shift + pad Kick.",
            "Maintiens le Pas 13 (allumé) et glisse le doigt sur la bande jusqu'à 2.",
            "Écoute : au pas 13, le kick joue deux fois.",
            "Essaie 3 et 4 sur d'autres pas.",
        ),
        expected = "Les pas choisis jouent des doubles, triples ou quadruples coups.",
        exerciseTitle = "Roulement programmé",
        exercise = listOf(
            "Sur la caisse claire, mets des répétitions croissantes (1, 2, 3, 4) sur les quatre derniers pas.",
            "Écoute le roulement qui monte en fin de motif.",
        ),
        success = "Le roulement accélère sur les quatre derniers pas.",
        style = "Tribecore : les répétitions rapides donnent l'énergie des fins de phrase.",
        mistakes = listOf(
            "Appuyer brièvement au lieu de maintenir : le pas s'éteint et sa répétition revient à 1.",
            "Changer la division du motif alors que Step Repeat suffit.",
        ),
        quiz = listOf(
            qz("Quelle est la valeur de répétition par défaut ?", listOf("0", "1", "2"), 1,
                "La LED blanche s'allume par défaut sur 1.", STEP_REPEAT),
            qz("Comment changer la répétition d'un pas ?",
                listOf("Maintenir le pas et glisser sur la bande", "Shift + pas", "Swing + pas"), 0,
                "En maintenant le pas, la bande tactile choisit la valeur.", STEP_REPEAT),
            qz("Que fait un appui bref sur un pas en mode Step ?",
                listOf("Active/désactive et remet la répétition à 1", "Copie le pas", "Rien"), 0,
                "Un appui bref bascule l'événement et réinitialise sa répétition à 1.", STEP_REPEAT),
            qz("Combien de temps faut-il maintenir un pas pour voir sa répétition sans le désactiver ?",
                listOf("100 ms", "700 ms ou plus", "3 s"), 1,
                "Maintenu 700 ms ou plus, on peut voir ou changer la valeur sans basculer l'événement.", STEP_REPEAT),
        ),
    )

    private val m5l3 = ctLesson(
        M5, 3, "Décaler un coup dans le temps", listOf(DECALAGE, SHIFT_TABLE), 15,
        objective = "Avancer ou retarder un coup (ou toute une piste) pour donner du groove.",
        explanation = "En mode Step, maintiens un pas allumé et tourne le potentiomètre Swing : vers la gauche, l'affichage va de " +
            "-01 à -50 et le coup est joué plus tôt ; vers la droite, de 01 à 50, plus tard (jusqu'à 50 % de la valeur du pas). " +
            "Shift + Swing sans maintenir de pas décale toute la piste de l'instrument ; c'est aussi un moyen simple de recaler " +
            "un instrument enregistré sans quantification.",
        controls = listOf(
            cmd("Pas maintenu + Swing", "Décale un seul coup (-50 à 50).", DECALAGE),
            cmd("Shift + Swing", "Décale toute la piste de l'instrument.", SHIFT_TABLE),
        ),
        steps = listOf(
            "Fais boucler un motif avec kick, caisse claire et charleston fermée en croches.",
            "En mode Step, sélectionne la charleston fermée avec Shift + pad.",
            "Maintiens un pas allumé et tourne Swing à gauche : le coup arrive plus tôt.",
            "Sur un autre pas, tourne à droite : le coup arrive plus tard.",
            "Sauve avec Save + Ptrn quand le groove te plaît.",
        ),
        expected = "La charleston n'est plus mécanique : certains coups sont en avance ou en retard.",
        exerciseTitle = "Caisse claire en retard",
        exercise = listOf(
            "Retarde légèrement toutes les caisses claires avec Shift + Swing.",
            "Compare avec la version d'origine et garde celle que tu préfères.",
        ),
        success = "Le décalage s'entend et donne une sensation plus « lourde ».",
        mistakes = listOf(
            "Tourner Swing sans maintenir de pas ni Shift : c'est le swing du motif qui change.",
            "Oublier de sauver le motif après le réglage.",
        ),
        quiz = listOf(
            qz("Comment décaler un seul coup ?", listOf("Maintenir son pas et tourner Swing", "Shift + Random", "Last Step + Swing"), 0,
                "Pas maintenu + Swing décale cet événement.", DECALAGE),
            qz("Que signifie une valeur négative (-01 à -50) ?", listOf("Le coup est joué plus tôt", "Plus tard", "Plus fort"), 0,
                "Les valeurs négatives avancent l'événement.", DECALAGE),
            qz("Que fait Shift + Swing ?",
                listOf("Décale tous les pas de l'instrument actuel", "Efface le swing", "Change de banque"), 0,
                "Shift + Swing décale toute la piste de l'instrument.", SHIFT_TABLE),
            qz("Jusqu'à combien peut-on décaler un coup ?", listOf("10 %", "50 % de la valeur du pas", "100 %"), 1,
                "Le décalage va jusqu'à 50 % de la valeur temporelle du motif.", DECALAGE),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 6 — Groove, longueur et polyrythmie
    // ---------------------------------------------------------------------
    private const val M6 = "dbi-m6"

    private val m6l1 = ctLesson(
        M6, 1, "Swing et Random", listOf(EFFETS_MOTIF, SWING, RANDOM), 15,
        objective = "Rendre un motif moins mécanique avec le Swing et plus imprévisible avec le Random.",
        explanation = "Le potentiomètre Swing va de 50 % (temps égaux) à 75 % : la deuxième note de chaque paire est jouée plus " +
            "tard. Random (0 à 100 %) modifie au hasard le rythme, la présence des coups et les accents. Par défaut ils agissent sur " +
            "tout le motif ; après un appui sur Current Track, ils ne concernent que l'instrument sélectionné. Pour revenir à un réglage " +
            "commun, il faut désactiver Current Track pour chaque instrument.",
        controls = listOf(
            cmd("Swing", "Ternarisation de 50 à 75 %.", SWING),
            cmd("Random", "Imprévisibilité de 0 à 100 %.", RANDOM),
            cmd("Current Track", "Applique Swing/Random à l'instrument sélectionné.", SWING),
        ),
        steps = listOf(
            "Lance un motif en croches ou doubles croches.",
            "Monte Swing vers 60 % et écoute, puis reviens à 50 %.",
            "Sélectionne la charleston, appuie sur Current Track et monte Swing : seule la charleston swingue.",
            "Monte Random à 20 % sur tout le motif, puis à 80 %.",
            "Reviens à 0 % de Random.",
        ),
        expected = "Le motif swingue globalement ou sur un seul instrument, et le Random le fait varier.",
        exerciseTitle = "Charleston vivante",
        exercise = listOf(
            "Laisse le kick droit, mets un peu de Swing et 15 % de Random sur la seule charleston (Current Track).",
            "Sauve le motif.",
        ),
        success = "Le kick reste stable pendant que la charleston varie.",
        style = "Hardtek : garde le kick droit, laisse le swing aux charlestons.",
        mistakes = listOf(
            "Mettre du Random sur tout le motif : le kick devient instable.",
            "Oublier de désactiver Current Track pour revenir à un réglage commun.",
        ),
        quiz = listOf(
            qz("Quelle est la valeur maximale du Swing ?", listOf("60 %", "75 %", "100 %"), 1,
                "Le Swing va de 50 % (défaut) à 75 %.", SWING),
            qz("Qu'est-ce que Random peut modifier ?",
                listOf("Le rythme, la présence des coups et les accents", "Le tempo", "Les sons"), 0,
                "Random agit sur le motif rythmique, les Step on/off et les Accent on/off.", RANDOM),
            qz("À quoi sert Current Track ?",
                listOf("À appliquer Swing ou Random à l'instrument sélectionné", "À changer de piste MIDI", "À copier une piste"), 0,
                "Après Current Track, Swing et Random ne concernent que l'instrument en cours.", SWING),
            qz("Swing à 50 % donne…", listOf("Des croches égales", "Un shuffle maximal"), 0,
                "À 50 %, chaque note a un temps égal.", SWING),
        ),
    )

    private val m6l2 = ctLesson(
        M6, 2, "Longueur du motif jusqu'à 64 pas", listOf(LONGUEUR, PROLONGER), 20,
        objective = "Raccourcir ou allonger un motif et éditer les pas au-delà de 16.",
        explanation = "Par défaut un motif fait 16 pas, jusqu'à 64 (quatre groupes de 16). Last Step maintenu + un bouton de Pas fixe " +
            "le dernier pas. Pour 32 pas : Last Step maintenu, un appui sur >> (LED blanche sur 32), puis le Pas 16. << et >> " +
            "ensemble activent le mode Pattern Follow (affichage qui suit la lecture). Shift + >> recopie le motif à sa suite " +
            "(16 → 32 → 48…) ; pour raccourcir, Last Step + << choisit le groupe.",
        controls = listOf(
            cmd("Last Step + Pas", "Fixe le dernier pas.", LONGUEUR),
            cmd("<< / >>", "Groupes de pas ; ensemble = Pattern Follow.", LONGUEUR),
            cmd("Shift + >>", "Prolonge en recopiant le motif.", PROLONGER),
        ),
        steps = listOf(
            "Sur un motif de 16 pas, maintiens Last Step et appuie sur le Pas 12 : le motif fait 12 pas, écoute.",
            "Remets-le à 16 de la même façon.",
            "Appuie sur Shift + >> : le motif passe à 32 pas, recopié.",
            "Appuie sur << et >> ensemble pour activer Pattern Follow, puis Play.",
            "Désactive Pattern Follow, appuie sur >> pour afficher les pas 17–32 et modifie la fin du motif.",
        ),
        expected = "Le motif dure deux mesures, la seconde modifiée, et tu vois les pas 17–32.",
        exerciseTitle = "Phrase de 4 mesures",
        exercise = listOf(
            "Crée un motif de 64 pas avec Shift + >> (deux fois depuis 16 pas).",
            "Ajoute un roulement uniquement dans les 16 derniers pas.",
        ),
        success = "Le roulement n'arrive qu'à la quatrième mesure.",
        mistakes = listOf(
            "Éditer en mode Pattern Follow alors que l'affichage change sans cesse : désactive-le pour éditer un groupe.",
            "Oublier que Shift + >> recopie les coups existants.",
        ),
        quiz = listOf(
            qz("Quelle est la longueur maximale d'un motif ?", listOf("32 pas", "64 pas", "128 pas"), 1,
                "Jusqu'à 64 pas, en quatre groupes de 16.", LONGUEUR),
            qz("Comment fixer un motif à 12 pas ?", listOf("Last Step + Pas 12", "Shift + Pas 12", "Ptrn + Pas 12"), 0,
                "Maintenir Last Step et appuyer sur le Pas 12.", LONGUEUR),
            qz("Que fait Shift + >> sur un motif de 16 pas ?",
                listOf("Il passe à 32 pas en recopiant les pas 1–16", "Il passe à 17 pas vides", "Il est effacé"), 0,
                "Les événements sont copiés et ajoutés à la fin.", PROLONGER),
            qz("Comment activer le mode Pattern Follow ?", listOf("<< et >> ensemble", "Shift + Step", "Last Step + Play"), 0,
                "Appuyer en même temps sur << et >> l'active ou le coupe.", LONGUEUR),
        ),
    )

    private val m6l3 = ctLesson(
        M6, 3, "Mode Polyrhythm", listOf(POLY, SHIFT_TABLE), 20,
        objective = "Donner à chaque instrument sa propre longueur de boucle pour créer des rythmes qui se croisent.",
        explanation = "Shift + Pas 16 active le mode Polyrhythm pour le motif (réglage gardé par motif). Chaque instrument peut " +
            "alors avoir sa longueur : sélectionne l'instrument, passe en mode Step, maintiens Last Step et appuie sur le pas qui " +
            "termine sa boucle. Les boucles de longueurs différentes se décalent et ne se retrouvent qu'après de nombreuses mesures.",
        controls = listOf(
            cmd("Shift + Pas 16", "Active le mode Polyrhythm du motif.", POLY),
            cmd("Last Step + Pas (par instrument)", "Longueur de la boucle de l'instrument.", POLY),
        ),
        steps = listOf(
            "Choisis un motif vide, maintiens Shift et appuie sur le Pas 16 (il devient bleu).",
            "Tape le pad Kick, appuie sur Step, maintiens Last Step et appuie sur le Pas 3, puis active le Pas 1.",
            "Tape le pad Snare 1, Last Step + Pas 4, puis active le Pas 1.",
            "Tape le pad Snare 2, Last Step + Pas 5, puis active le Pas 1.",
            "Lance Play : les trois boucles s'entrecroisent.",
        ),
        expected = "Les trois instruments jouent des cycles de 3, 4 et 5 pas qui se décalent.",
        exerciseTitle = "Tribal polyrythmique",
        exercise = listOf(
            "Garde le kick en 16 pas, ajoute des toms en 3 pas et une cowbell en 5 pas.",
            "Sauve le motif.",
        ),
        success = "Le kick reste stable et les percussions tournent en décalé.",
        style = "Tribe : des percussions sur des cycles de 3 et 5 pas donnent un groove hypnotique.",
        mistakes = listOf(
            "Oublier d'activer Polyrhythm : Last Step change alors la longueur de tout le motif.",
            "Oublier de sauvegarder : le réglage est gardé par motif.",
        ),
        quiz = listOf(
            qz("Comment activer le mode Polyrhythm ?", listOf("Shift + Pas 16", "Shift + Pas 9", "Last Step + 16"), 0,
                "Shift + Pas 16 l'active pour le motif.", POLY),
            qz("Le réglage Polyrhythm est gardé…", listOf("Pour tous les motifs", "Séparément pour chaque motif"), 1,
                "Il est conservé séparément pour chaque motif.", dbi("2.2.2.4 Fonction Polyrhythm", "24")),
            qz("Comment donner une boucle de 5 pas à la Snare 2 ?",
                listOf("Sélectionner la Snare 2 puis Last Step + Pas 5", "Shift + Pas 5", "Swing à 5"), 0,
                "En Polyrhythm, Last Step fixe la longueur de l'instrument sélectionné.", POLY),
            qz("Pourquoi les boucles se décalent-elles ?",
                listOf("Parce qu'elles ont des longueurs différentes", "À cause du Random", "À cause du swing"), 0,
                "Chaque instrument boucle sur sa propre longueur.", POLY),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 7 — Performance, copies et morceaux
    // ---------------------------------------------------------------------
    private const val M7 = "dbi-m7"

    private val m7l1 = ctLesson(
        M7, 1, "La bande tactile : Looper et Roller", listOf(BANDE, LOOPER_ROLLER, ROLLER_REC), 20,
        objective = "Utiliser la bande tactile pour boucler une partie du motif ou faire des roulements en direct.",
        explanation = "Shift + Pas 9 bascule la bande entre Roller (Pas 9 allumé quand Shift est maintenu) et Looper (éteint). " +
            "En lecture, Looper boucle une partie du motif : la position du doigt règle la longueur (1/4 la plus longue, 1/32 la plus " +
            "courte). Roller : doigt sur la bande + un ou plusieurs pads = roulements à la valeur choisie. En enregistrement, seul " +
            "le Roller est disponible et il écrit les roulements dans le motif, limités par la division temporelle.",
        controls = listOf(
            cmd("Shift + Pas 9", "Bascule Roller / Looper.", LOOPER_ROLLER),
            cmd("Bande tactile", "Longueur de boucle ou vitesse de roulement.", BANDE),
            cmd("Doigt sur la bande + pad", "Roulement de l'instrument (mode Roller).", LOOPER_ROLLER),
        ),
        steps = listOf(
            "Lance un motif, maintiens Shift et regarde le Pas 9 : éteint = Looper.",
            "Pose le doigt sur la bande à 1/4, puis glisse vers 1/16 : la boucle raccourcit. Relâche.",
            "Passe en Roller avec Shift + Pas 9.",
            "Pose le doigt sur la bande à 1/16 et frappe le pad Snare 1 : roulement.",
            "En enregistrement (Record + Play), fais un roulement de caisse claire en fin de motif.",
        ),
        expected = "La boucle se resserre avec le Looper et les roulements sont enregistrés avec le Roller.",
        exerciseTitle = "Montée au Looper",
        exercise = listOf(
            "Pendant la lecture, boucle 1/4, puis 1/8, puis 1/16 sur huit mesures.",
            "Relâche la bande pile sur le premier temps pour relancer le motif.",
        ),
        success = "La montée est rythmique et le retour tombe sur le temps.",
        style = "Hardtek / tribe : la boucle qui se resserre avant le retour est un effet de live classique.",
        mistakes = listOf(
            "Chercher le Looper pendant l'enregistrement : il n'est pas disponible.",
            "Vouloir enregistrer un roulement 1/32 avec une division 1/16 : il ne sera pas enregistré.",
        ),
        quiz = listOf(
            qz("Comment basculer entre Roller et Looper ?", listOf("Shift + Pas 9", "Shift + Pas 16", "Color + bande"), 0,
                "Shift + Pas 9 ; Pas 9 allumé avec Shift = Roller.", LOOPER_ROLLER),
            qz("En Looper, quelle position donne la boucle la plus longue ?", listOf("1/4", "1/32"), 0,
                "1/4 correspond à la plus grande boucle, 1/32 à la plus petite.", LOOPER_ROLLER),
            qz("Le Looper est-il disponible pendant l'enregistrement ?", listOf("Oui", "Non"), 1,
                "Seul le Roller fonctionne en cours d'enregistrement.", ROLLER_REC),
            qz("Qu'est-ce qui limite les roulements enregistrés ?",
                listOf("La division temporelle du motif", "Le volume", "Le tempo global"), 0,
                "Avec une division 1/16, les roulements 1/32 ne sont pas enregistrés.", LOOPER_ROLLER),
        ),
    )

    private val m7l2 = ctLesson(
        M7, 2, "Copier et effacer", listOf(COPIER_PISTE, EFFACER_PISTE, COPIER_MOTIF, COPIER_EFFACER), 20,
        objective = "Dupliquer des pistes et des motifs pour créer des variations, et effacer sans erreur.",
        explanation = "Copier une piste : Copy, pad source, (Ptrn + Pas, ou Bank + Pas puis Ptrn + Pas, pour un autre motif), puis " +
            "pad de destination. Copier un motif : sélectionne-le, Copy, Ptrn, puis le Pas de destination (Bank d'abord pour une " +
            "autre banque). Effacer une piste : Erase puis le pad ; un motif : Erase, Ptrn, puis le Pas ; une banque : Erase, Bank, " +
            "puis le Pas. Ces opérations écrasent la destination et ne peuvent pas être annulées.",
        controls = listOf(
            cmd("Copy", "Copie de piste, motif ou banque.", COPIER_EFFACER),
            cmd("Erase", "Effacement de piste, motif ou banque.", COPIER_EFFACER),
            cmd("Ptrn / Bank + Pas", "Choisissent le motif ou la banque concernés.", COPIER_MOTIF),
        ),
        steps = listOf(
            "Sélectionne le motif 1 (Ptrn + Pas 1), appuie sur Copy, puis Ptrn, puis le Pas 8 : le motif 1 est copié en 8.",
            "Sélectionne le motif 8 avec le Pas 8 et modifie-le pour en faire une variation.",
            "Copie la piste Closed Hat du motif 8 vers le motif 1 : Copy, pad Closed Hat, Ptrn, Pas 1, pad Closed Hat.",
            "Efface la piste FM Drum d'un motif : Erase, puis pad FM Drum.",
            "Sauve la banque avec Save + Bank.",
        ),
        expected = "Le motif 8 est une variation du motif 1 et les pistes sont copiées ou effacées comme voulu.",
        exerciseTitle = "Famille de motifs",
        exercise = listOf(
            "À partir d'un motif principal, crée trois copies : intro sans kick, principal, et break avec seulement les percussions.",
            "Sauve la banque.",
        ),
        success = "Les quatre motifs se ressemblent et s'enchaînent naturellement.",
        mistakes = listOf(
            "Copier sur un emplacement occupé sans vérifier : il est écrasé (les emplacements occupés sont en rouge).",
            "Copier vers un autre instrument en espérant garder les couleurs : les données Color ne suivent pas.",
        ),
        quiz = listOf(
            qz("Une copie de motif peut-elle être annulée ?", listOf("Oui", "Non"), 1,
                "La destination est écrasée et l'opération ne peut pas être annulée.", COPIER_EFFACER),
            qz("Comment effacer une piste d'instrument ?", listOf("Erase puis le pad", "Shift + pad", "Erase + Play"), 0,
                "Erase, puis taper le pad de la piste à supprimer.", EFFACER_PISTE),
            qz("En copiant une piste vers un autre instrument, les données Color sont-elles copiées ?", listOf("Oui", "Non"), 1,
                "Les informations Color ne sont copiées que vers le même instrument.", COPIER_PISTE),
            qz("Avant de choisir la destination, comment savoir si un motif est occupé ?",
                listOf("Il est allumé en rouge", "Il clignote en vert", "Ce n'est pas possible"), 0,
                "Les motifs non vides sont allumés en rouge, les vides éteints.", COPIER_EFFACER),
        ),
    )

    private val m7l3 = ctLesson(
        M7, 3, "Créer un morceau (mode Song)", listOf(SONG, SONG_SAVE, SONG_LIVE), 20,
        objective = "Enchaîner des motifs dans un morceau, le sauvegarder et le jouer en live.",
        explanation = "16 morceaux de 16 motifs chacun. Song puis un Pas choisit le morceau. Pour le créer : Song, choix du Pas, " +
            "Record (Song, Ptrn et Record clignotent), puis appuie sur les Pas des motifs dans l'ordre (répéter un Pas répète le motif ; " +
            "Bank permet de prendre d'autres banques). Record pour terminer, Save + Song pour sauver. Le morceau boucle à la fin ; " +
            "il reprend le tempo de chaque motif sauf avec Global BPM. Shift + Song restaure les morceaux sauvegardés.",
        controls = listOf(
            cmd("Song + Pas", "Sélectionne un morceau.", SONG),
            cmd("Record (en mode Song)", "Entre/sort de la composition du morceau.", SONG),
            cmd("Save + Song", "Sauvegarde le morceau.", SONG_SAVE),
            cmd("Erase + Song + Pas", "Efface un morceau.", SONG_SAVE),
        ),
        steps = listOf(
            "Appuie sur Song puis sur le Pas 1.",
            "Appuie sur Record : Song, Ptrn et Record clignotent.",
            "Appuie sur les Pas 1, 1, 2, 2, 3, 3, 3, 3 pour ordonner tes motifs.",
            "Appuie sur Record pour terminer, puis Play.",
            "Sauve avec Save + Song.",
        ),
        expected = "Le morceau joue les motifs dans l'ordre voulu et boucle à la fin.",
        exerciseTitle = "Morceau complet",
        exercise = listOf(
            "Construis un morceau intro (2), principal (4), break (2), principal (4) avec les motifs de la leçon précédente.",
            "Joue-le en ajoutant des mutes et le Roller en direct.",
        ),
        success = "Le morceau se déroule seul et tu peux le faire vivre en direct.",
        style = "Hardtek / tribe : intro, montée, break sans kick, puis retour.",
        mistakes = listOf(
            "Dépasser 16 motifs : un morceau en contient au maximum 16, répétitions comprises.",
            "Oublier Save + Song : le morceau n'est pas gardé.",
        ),
        quiz = listOf(
            qz("Combien de motifs au maximum dans un morceau ?", listOf("8", "16", "64"), 1,
                "Chaque morceau peut faire 16 motifs de long, répétitions comprises.", SONG),
            qz("Comment entrer en composition de morceau ?", listOf("Record en mode Song", "Shift + Song", "Copy + Song"), 0,
                "Après avoir choisi le morceau, Record active la composition.", SONG),
            qz("Comment sauvegarder le morceau ?", listOf("Save + Song", "Save + Ptrn", "Record + Song"), 0,
                "Save + Song sauvegarde le morceau.", SONG_SAVE),
            qz("Les groupes Mute/Solo sont-ils les mêmes en mode Song et en mode Pattern ?", listOf("Oui", "Non"), 0,
                "Les groupes Mute/Solo sont partagés entre les deux modes.", SONG_LIVE),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(
            M5, 5, "Édition au pas",
            "Mode Step, répétition de pas et décalage temporel.",
            "Programmer un motif au pas, ajouter des répétitions et décaler des coups.",
            "La précision du pas à pas sert les rythmes hardtek ; les répétitions, la tribecore.",
            listOf(m5l1, m5l2, m5l3),
        ),
        CourseModule(
            M6, 6, "Groove, longueur et polyrythmie",
            "Swing, Random, longueurs jusqu'à 64 pas, mode Polyrhythm.",
            "Faire évoluer un motif sur plusieurs mesures et créer des polyrythmies.",
            "Les polyrythmies donnent le côté hypnotique de la tribe.",
            listOf(m6l1, m6l2, m6l3),
        ),
        CourseModule(
            M7, 7, "Performance et morceaux",
            "Looper, Roller, copies, effacements et mode Song.",
            "Jouer en live avec la bande tactile et construire un morceau complet.",
            "Looper et Roller sont les gestes de performance de la free party.",
            listOf(m7l1, m7l2, m7l3),
        ),
    )
}
