package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.ContentStatus
import com.livemachine.academy.data.model.Exercise
import com.livemachine.academy.data.model.Lesson
import com.livemachine.academy.data.model.MachineControl
import com.livemachine.academy.data.model.ManualDoc.PARAMETER_GUIDE
import com.livemachine.academy.data.model.ManualRef
import com.livemachine.academy.data.model.QuizQuestion

/**
 * Module 7 — Variations et construction musicale.
 *
 * Sources : manuel d'utilisation (partie française) et electribe sampler Parameter Guide.
 * Le moment exact où la machine passe d'un pattern à l'autre quand on en choisit un nouveau
 * pendant la lecture n'est pas documenté : les leçons font écouter plutôt que de l'affirmer.
 */
object Module7Content {

    private const val M = "m7"

    // Manuel d'utilisation
    private val OM_SAUVEGARDE = ManualRef("Enregistrement de données de jeu › 4. Sauvegarde d'un motif", "31")
    private val OM_MOTIF = ManualRef("Pour produire du son › 3. Lecture d'un motif", "27")
    private val OM_PATTERN_SET = ManualRef("Production de musique › 5. Jeu avec un groupe de motifs", "30")
    private val OM_PATTERN_SET_BOUTON = ManualRef("Description par section › Section du mode de pad › Pattern Set", "24")
    private val OM_ERASE = ManualRef("Edition d'un motif › 2. Effacement d'une partie", "32")
    private val OM_MUTE = ManualRef("Edition d'un motif › 1. Etouffement d'une partie", "32")
    private val OM_EXPORT_PSET = ManualRef("Autres réglages › Exporter un groupe de motifs sous forme de fichier WAV", "33")

    // Guide des paramètres
    private val PG_PATTERN = ManualRef("1. PATTERN PARAMETERS", "3", PARAMETER_GUIDE)
    private val PG_CHAIN = ManualRef("1. PATTERN PARAMETERS › CHAIN TO / CHAIN REPEAT", "3", PARAMETER_GUIDE)
    private val PG_CHAIN_MODE = ManualRef("6. GLOBAL PARAMETERS › CHAIN MODE", "9", PARAMETER_GUIDE)
    private val PG_TEMPO_LOCK = ManualRef("6. GLOBAL PARAMETERS › TEMPO LOCK", "8", PARAMETER_GUIDE)
    private val PG_PART = ManualRef("2. PART PARAMETERS", "4", PARAMETER_GUIDE)
    private val PG_PART_UTILITY = ManualRef("4. PART UTILITY", "5", PARAMETER_GUIDE)
    private val PG_SHORTCUTS = ManualRef("9. Appendix › Shortcut list", "11", PARAMETER_GUIDE)

    private fun q(id: String, prompt: String, options: List<String>, correct: Int, explanation: String, ref: ManualRef) =
        QuizQuestion(id, prompt, options, correct, explanation, ref)

    // ---------------------------------------------------------------------
    // 7.1 Organiser une famille de patterns
    // ---------------------------------------------------------------------
    private val lesson1 = Lesson(
        id = "$M-l1",
        moduleId = M,
        order = 1,
        title = "Organiser une famille de patterns",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_SAUVEGARDE, OM_MOTIF, PG_SHORTCUTS, PG_PATTERN, PG_TEMPO_LOCK),
        durationMinutes = 15,
        objective = "Décliner un pattern principal en plusieurs copies rangées et nommées, prêtes à devenir intro, montée, break et drop.",
        explanation = "Un morceau sur l'electribe, c'est une suite de patterns (250 emplacements). La méthode la plus simple : " +
            "finir un pattern principal, puis le copier plusieurs fois avec Shift + Write, en donnant à chaque copie un nom " +
            "et un numéro voisin. Shift + commande de valeur sélectionne les patterns de 10 en 10 : ranger chaque morceau " +
            "dans sa dizaine (par exemple 101 à 109) permet de le retrouver vite. Attention : le BPM est un paramètre de " +
            "pattern, chaque copie garde le sien. Si tu changes le tempo d'un pattern, les autres ne suivent pas ; le " +
            "réglage Global TEMPO LOCK fige le tempo actuel même quand tu passes à un pattern d'un autre tempo.",
        controls = listOf(
            MachineControl("Shift + Write", "Sauvegarde une copie sous un nouveau nom et un nouveau numéro.", OM_SAUVEGARDE),
            MachineControl("Shift + commande de valeur", "Sélection des patterns de 10 en 10.", PG_SHORTCUTS),
            MachineControl("BPM (paramètre de pattern)", "Chaque pattern a son propre tempo.", PG_PATTERN),
            MachineControl("TEMPO LOCK (menu Global)", "On : le tempo ne change pas en changeant de pattern.", PG_TEMPO_LOCK),
        ),
        steps = listOf(
            "Choisis une dizaine libre pour ton morceau (par exemple 101 à 109). Shift + commande de valeur pour y aller vite.",
            "Sauvegarde ton pattern principal dans le premier emplacement, nommé par exemple « DROP A ».",
            "Shift + Write : copie-le dans l'emplacement suivant sous le nom « INTRO ».",
            "Recommence pour « MONTEE », « BREAK » et « DROP B ».",
            "Vérifie que tous ont le même BPM (Shift + Tap affiche la page BPM).",
            "Note dans un coin la carte de ta dizaine : numéro et rôle de chaque pattern.",
        ),
        expectedResult = "Cinq patterns identiques au départ, nommés, rangés à la suite et au même tempo : la matière première du morceau.",
        exercise = Exercise(
            id = "$M-l1-ex",
            lessonId = "$M-l1",
            title = "La carte du morceau",
            instructions = listOf(
                "Prépare la famille de patterns d'un morceau dans une seule dizaine.",
                "Passe d'un pattern à l'autre pendant la lecture avec la commande de valeur, et écoute à quel moment le changement se fait.",
            ),
            successCriteria = "Tu retrouves chaque pattern de la famille en quelques secondes, et tu sais à quel moment le changement de pattern intervient sur ta machine.",
            styleHint = "Hardtek et tribe : un morceau tient souvent sur peu de patterns ; c'est la façon de les enchaîner qui fait le morceau.",
        ),
        commonMistakes = listOf(
            "Copier avec Write seul : tu écrases l'emplacement choisi sans renommer. Pour une copie nommée, c'est Shift + Write (manuel p. 31).",
            "Changer le BPM d'un seul pattern de la famille : les autres gardent l'ancien tempo (guide p. 3).",
            "Laisser TEMPO LOCK sur On sans s'en souvenir et ne plus entendre le tempo propre de chaque pattern (guide p. 8).",
        ),
        quiz = listOf(
            q("$M-l1-q1", "Que fait Shift + commande de valeur sur l'écran de sélection de pattern ?",
                listOf("Il sélectionne les patterns de 10 en 10", "Il change le tempo", "Il copie le pattern"), 0,
                "Shift + Value : sélection du numéro de pattern par pas de 10.", PG_SHORTCUTS),
            q("$M-l1-q2", "Le BPM est-il commun à tous les patterns ?",
                listOf("Oui", "Non, c'est un paramètre de chaque pattern", "Seulement dans une même dizaine"), 1,
                "Les paramètres de pattern, dont le BPM, sont sauvegardés indépendamment pour chaque pattern.", PG_PATTERN),
            q("$M-l1-q3", "Que fait TEMPO LOCK sur On ?",
                listOf("Il bloque la lecture", "Il fige le tempo actuel, même en passant à un pattern d'un autre tempo", "Il verrouille la sauvegarde"), 1,
                "Le tempo ne change pas même si tu passes à un pattern dont le tempo est différent.", PG_TEMPO_LOCK),
        ),
    )

    // ---------------------------------------------------------------------
    // 7.2 Variations par soustraction
    // ---------------------------------------------------------------------
    private val lesson2 = Lesson(
        id = "$M-l2",
        moduleId = M,
        order = 2,
        title = "Variations par soustraction",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_MUTE, OM_ERASE, PG_PART_UTILITY, PG_SHORTCUTS),
        durationMinutes = 15,
        objective = "Créer une intro et un break en retirant des éléments du pattern principal.",
        explanation = "La variation la plus efficace est souvent le retrait. Pour tester : Part Mute coupe une part sans rien " +
            "effacer, et Shift + Part Mute annule toutes les coupures d'un coup. Pour graver la variation dans un pattern " +
            "copié : CLEAR SEQUENCE (PART UTILITY) efface toute la séquence d'une part, et Part Erase efface ses notes " +
            "pendant la lecture. Une intro garde par exemple le kick seul ou les percussions seules ; un break retire le " +
            "kick et garde l'ambiance.",
        controls = listOf(
            MachineControl("Part Mute + pad", "Couper / rétablir une part pour tester.", OM_MUTE),
            MachineControl("Shift + Part Mute", "Annule la coupure de toutes les parts.", PG_SHORTCUTS),
            MachineControl("CLEAR SEQUENCE (Shift + pad 14)", "Efface la séquence de la part sélectionnée.", PG_SHORTCUTS),
            MachineControl("Part Erase + pad (pendant la lecture)", "Efface les notes de la part.", OM_ERASE),
        ),
        steps = listOf(
            "Ouvre ton pattern « BREAK » et lance-le.",
            "Allume Part Mute et coupe le kick, puis la basse : écoute ce qui reste.",
            "Shift + Part Mute pour tout rétablir.",
            "Quand tu sais quoi retirer, sélectionne la part du kick et efface sa séquence avec CLEAR SEQUENCE (Shift + pad 14).",
            "Fais de même pour les autres parts à retirer, puis sauvegarde avec Write.",
            "Fais pareil dans « INTRO » en ne gardant que deux ou trois parts.",
        ),
        expectedResult = "Une intro et un break qui sonnent comme des versions allégées du drop, gravées dans leurs patterns.",
        exercise = Exercise(
            id = "$M-l2-ex",
            lessonId = "$M-l2",
            title = "Le break qui donne faim",
            instructions = listOf(
                "Dans « BREAK », retire le kick et au moins une autre part.",
                "Garde un élément qui tient le tempo (charley, percussion, écho).",
                "Enchaîne BREAK puis DROP A à la main : le retour du kick doit frapper.",
            ),
            successCriteria = "Le retour du kick après le break crée un vrai impact.",
            styleHint = "Tribecore et hardtek : plus le break est vide, plus le retour du kick saturé fait d'effet sur le dancefloor.",
        ),
        commonMistakes = listOf(
            "Effacer des parts dans le pattern principal au lieu de la copie : travaille toujours sur une copie (leçon 7.1).",
            "Compter sur Part Mute pour graver la variation : il coupe le son sans rien effacer, et le manuel ne dit pas si l'état des coupures est sauvegardé. Vérifie-le sur ta machine, ou efface la séquence dans la copie (manuel p. 32).",
            "Utiliser CLEAR MOTION au lieu de CLEAR SEQUENCE : il n'efface que la motion (guide p. 5).",
        ),
        quiz = listOf(
            q("$M-l2-q1", "Quel raccourci annule la coupure de toutes les parts ?",
                listOf("Shift + Part Mute", "Shift + Part Erase", "Shift + Exit"), 0, "Shift + Part Mute annule la coupure de toutes les parts.", PG_SHORTCUTS),
            q("$M-l2-q2", "Que fait CLEAR SEQUENCE ?",
                listOf("Il efface la séquence (trigger, note, gate time) de la part sélectionnée", "Il efface le sample", "Il efface tout le pattern"), 0,
                "CLEAR SEQUENCE supprime les données de séquence de la part sélectionnée.", PG_PART_UTILITY),
            q("$M-l2-q3", "Quel raccourci affiche la page CLEAR SEQUENCE ?",
                listOf("Shift + pad 14", "Shift + pad 15", "Shift + pad 3"), 0, "Shift + pad 14 affiche la page CLEAR SEQUENCE.", PG_SHORTCUTS),
        ),
    )

    // ---------------------------------------------------------------------
    // 7.3 Variations par transformation
    // ---------------------------------------------------------------------
    private val lesson3 = Lesson(
        id = "$M-l3",
        moduleId = M,
        order = 3,
        title = "Variations par transformation",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(PG_PART, PG_PART_UTILITY, PG_PATTERN),
        durationMinutes = 15,
        objective = "Créer un « DROP B » qui reste reconnaissable mais apporte du neuf, en modifiant peu de choses.",
        explanation = "Une bonne variation garde l'ossature et change un ou deux éléments. Les outils vus aux modules précédents " +
            "s'y prêtent : changer quelques notes de la basse (STEP EDIT), raccourcir une percussion avec LAST STEP, " +
            "changer de GROOVE TYPE, de type d'effet d'insertion ou de type de modulation, ou ajouter une motion sequence. " +
            "COPY PART SOUND permet de récupérer un son d'une part vers une autre sans sa séquence ; COPY PART copie le son " +
            "et la séquence. Les paramètres de part (LAST STEP, GROOVE, VOICE ASSIGN…) sont sauvegardés pour chaque part, " +
            "les paramètres de pattern (SWING, KEY, SCALE…) pour chaque pattern.",
        controls = listOf(
            MachineControl("LAST STEP, GROOVE TYPE / DEPTH", "Paramètres de part, propres à chaque part.", PG_PART),
            MachineControl("SWING, KEY, SCALE", "Paramètres de pattern.", PG_PATTERN),
            MachineControl("COPY PART / COPY PART SOUND", "Copier son et séquence, ou seulement le son.", PG_PART_UTILITY),
        ),
        steps = listOf(
            "Ouvre « DROP B ».",
            "Change deux notes de la basse dans STEP EDIT (Shift + pad 12).",
            "Mets une percussion sur LAST STEP 12 (Shift + pad 7).",
            "Change le type d'effet d'insertion du kick ou de la basse.",
            "Enchaîne DROP A puis DROP B à la main et compare.",
            "Sauvegarde avec Write.",
        ),
        expectedResult = "DROP B est clairement de la même famille que DROP A, avec un changement net à l'oreille.",
        exercise = Exercise(
            id = "$M-l3-ex",
            lessonId = "$M-l3",
            title = "Trois variations, une ossature",
            instructions = listOf(
                "Crée trois copies du drop, avec un seul type de changement chacune : notes, rythme (LAST STEP ou groove), son (IFX ou modulation).",
                "Joue-les à la suite et garde les deux meilleures.",
            ),
            successCriteria = "Deux variations retenues, chacune reconnaissable et chacune apportant une seule nouveauté claire.",
            styleHint = "Mentalcore : une variation de basse d'un demi-ton ou d'une octave sur le drop B relance la tension sans casser l'hypnose.",
        ),
        commonMistakes = listOf(
            "Tout changer en même temps : la variation ne sonne plus comme le même morceau.",
            "Changer KEY ou SWING en pensant ne toucher qu'une part : ce sont des paramètres de pattern (guide p. 3).",
            "Copier une part avec COPY PART et écraser la séquence de destination alors que tu ne voulais que le son (guide p. 5).",
        ),
        quiz = listOf(
            q("$M-l3-q1", "LAST STEP et GROOVE TYPE sont sauvegardés…",
                listOf("Pour chaque part", "Pour tout le pattern", "En Global"), 0, "Les paramètres de part sont sauvegardés indépendamment pour chaque part.", PG_PART),
            q("$M-l3-q2", "Quelle fonction copie uniquement le son d'une part vers une autre ?",
                listOf("COPY PART", "COPY PART SOUND", "Shift + Write"), 1, "COPY PART SOUND copie seulement les données de son.", PG_PART_UTILITY),
            q("$M-l3-q3", "SWING est un paramètre…",
                listOf("De part", "De pattern", "Global"), 1, "SWING fait partie des paramètres de pattern.", PG_PATTERN),
        ),
    )

    // ---------------------------------------------------------------------
    // 7.4 Enchaîner automatiquement (CHAIN)
    // ---------------------------------------------------------------------
    private val lesson4 = Lesson(
        id = "$M-l4",
        moduleId = M,
        order = 4,
        title = "Enchaîner automatiquement les patterns",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(PG_CHAIN, PG_CHAIN_MODE),
        durationMinutes = 15,
        objective = "Faire jouer tout seul un enchaînement de patterns avec un nombre de répétitions précis.",
        explanation = "Deux paramètres de pattern règlent l'enchaînement. CHAIN TO (Off ou 1 à 250) : quand le pattern a fini " +
            "de jouer, le pattern indiqué démarre automatiquement ; sur Off, le pattern continue de tourner. CHAIN REPEAT " +
            "(1 à 64) : combien de fois le pattern joue avant de passer au suivant ; sans effet si CHAIN TO est sur Off. " +
            "Exemple du guide : pattern 1 avec CHAIN TO 2 et CHAIN REPEAT 2, pattern 2 avec CHAIN TO Off : le pattern 1 joue " +
            "deux fois puis le pattern 2 tourne. En mettant CHAIN TO du dernier pattern sur le premier, on crée une boucle. " +
            "Condition indispensable : le paramètre Global CHAIN MODE doit être sur On, sinon CHAIN TO et CHAIN REPEAT n'ont " +
            "aucun effet.",
        controls = listOf(
            MachineControl("CHAIN TO", "Pattern suivant (Off, 1 à 250).", PG_CHAIN),
            MachineControl("CHAIN REPEAT", "Nombre de lectures avant de passer au suivant (1 à 64).", PG_CHAIN),
            MachineControl("CHAIN MODE (menu Global)", "Doit être sur On pour que la chaîne fonctionne.", PG_CHAIN_MODE),
        ),
        steps = listOf(
            "Dans le menu Global, règle CHAIN MODE sur On.",
            "Ouvre « INTRO » : dans le menu d'édition, règle CHAIN TO sur le numéro de « MONTEE » et CHAIN REPEAT sur 4. Sauvegarde.",
            "Ouvre « MONTEE » : CHAIN TO sur « DROP A », CHAIN REPEAT sur 2. Sauvegarde.",
            "Ouvre « DROP A » : CHAIN TO sur Off. Sauvegarde.",
            "Reviens sur « INTRO » et lance la lecture : écoute l'enchaînement se faire tout seul.",
            "Compte les répétitions pour vérifier tes réglages.",
        ),
        expectedResult = "INTRO joue 4 fois, MONTEE 2 fois, puis DROP A tourne en boucle, sans que tu touches la machine.",
        exercise = Exercise(
            id = "$M-l4-ex",
            lessonId = "$M-l4",
            title = "Morceau en boucle",
            instructions = listOf(
                "Chaîne INTRO → MONTEE → DROP A → BREAK → DROP B, avec des répétitions adaptées à chaque rôle.",
                "Mets CHAIN TO de DROP B sur INTRO pour que tout boucle.",
                "Écoute un cycle complet sans intervenir.",
            ),
            successCriteria = "Le morceau complet tourne en boucle sans intervention, avec des durées de sections qui te conviennent.",
            styleHint = "Hardtek : des sections en multiples de 4 ou 8 répétitions gardent la carrure attendue par le dancefloor.",
        ),
        commonMistakes = listOf(
            "Régler CHAIN TO et CHAIN REPEAT sans activer CHAIN MODE : aucun effet (guide p. 3 et 9).",
            "Régler CHAIN REPEAT avec CHAIN TO sur Off : la répétition est ignorée (guide p. 3).",
            "Oublier de sauvegarder chaque pattern après avoir réglé sa chaîne : ce sont des paramètres de pattern.",
        ),
        quiz = listOf(
            q("$M-l4-q1", "Quelle condition faut-il remplir pour que CHAIN TO fonctionne ?",
                listOf("CHAIN MODE sur On dans le menu Global", "TEMPO LOCK sur On", "Pattern Set allumé"), 0,
                "Le paramètre Global CHAIN MODE doit être sur ON.", PG_CHAIN_MODE),
            q("$M-l4-q2", "Quelle est la plage de CHAIN REPEAT ?",
                listOf("1 à 16", "1 à 64", "1 à 250"), 1, "CHAIN REPEAT : 1 à 64.", PG_CHAIN),
            q("$M-l4-q3", "Pattern 1 : CHAIN TO 2, CHAIN REPEAT 2. Pattern 2 : CHAIN TO Off. Que se passe-t-il ?",
                listOf("Les deux patterns alternent sans fin", "Le pattern 1 joue deux fois, puis le pattern 2 tourne", "Le pattern 2 joue deux fois"), 1,
                "C'est l'exemple du guide : après deux lectures du pattern 1, le pattern 2 continue.", PG_CHAIN),
            q("$M-l4-q4", "Que fait CHAIN TO sur Off ?",
                listOf("Le pattern s'arrête", "Le pattern actuel continue de tourner", "La machine passe au pattern suivant"), 1,
                "Sur Off, le pattern actuel continue de jouer.", PG_CHAIN),
        ),
    )

    // ---------------------------------------------------------------------
    // 7.5 Le Pattern Set
    // ---------------------------------------------------------------------
    private val lesson5 = Lesson(
        id = "$M-l5",
        moduleId = M,
        order = 5,
        title = "Ranger ses patterns sous les pads (Pattern Set)",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_PATTERN_SET, OM_PATTERN_SET_BOUTON, OM_EXPORT_PSET),
        durationMinutes = 15,
        objective = "Mémoriser les patterns d'un morceau sous les pads pour les lancer d'une seule pression.",
        explanation = "Le Pattern Set mémorise un pattern sous un pad pour changer de pattern vite pendant la lecture. Il y a " +
            "quatre groupes, choisis avec les boutons Step 1 à 4, de 16 pads chacun : 64 emplacements. Pour assigner : " +
            "Pattern Set allumé, choisis le groupe (Step 1 à 4), sélectionne le pattern avec la commande de valeur, puis " +
            "maintiens Pattern Set et appuie sur le pad de destination. Pour jouer : Pattern Set allumé, choisis le " +
            "groupe, puis appuie sur le pad du pattern voulu. Les Pattern Sets peuvent aussi être exportés en fichiers WAV " +
            "(EXPORT P.SET AUDIO, menu DATA UTILITY).",
        controls = listOf(
            MachineControl("Pattern Set", "Allumé : les pads lancent les patterns mémorisés.", OM_PATTERN_SET_BOUTON),
            MachineControl("Boutons Step 1 à 4", "Choisissent le groupe de Pattern Set.", OM_PATTERN_SET),
            MachineControl("Pattern Set maintenu + pad", "Assigne le pattern actif à ce pad.", OM_PATTERN_SET_BOUTON),
        ),
        steps = listOf(
            "Appuie sur Pattern Set : il s'allume.",
            "Appuie sur Step 1 pour choisir le groupe 1.",
            "Avec la commande de valeur, sélectionne « INTRO ».",
            "Maintiens Pattern Set et appuie sur le pad 1 : INTRO est mémorisé sous ce pad.",
            "Recommence pour MONTEE (pad 2), DROP A (pad 3), BREAK (pad 4) et DROP B (pad 5).",
            "Lance la lecture et appuie sur les pads 1 à 5 pour passer d'une section à l'autre.",
        ),
        expectedResult = "Les sections de ton morceau sont rangées dans l'ordre sous les pads 1 à 5 du groupe 1, et tu les lances d'une pression.",
        exercise = Exercise(
            id = "$M-l5-ex",
            lessonId = "$M-l5",
            title = "Un morceau par rangée",
            instructions = listOf(
                "Range un morceau sous les pads 1 à 8 du groupe 1, et un deuxième sous les pads 9 à 16.",
                "Joue le premier morceau en entier en ne touchant que les pads, puis passe au second.",
            ),
            successCriteria = "Deux morceaux joués de bout en bout uniquement avec les pads du Pattern Set.",
            styleHint = "Tribe : garder une rangée de pads par morceau permet d'improviser l'ordre des sections selon la réaction du public.",
        ),
        commonMistakes = listOf(
            "Appuyer sur le pad sans maintenir Pattern Set au moment d'assigner : l'assignation demande de maintenir Pattern Set (manuel p. 24).",
            "Oublier de choisir le groupe avec Step 1 à 4 avant d'assigner ou de jouer (manuel p. 30).",
            "Confondre Pattern Set et CHAIN : le Pattern Set lance le pattern quand tu appuies, CHAIN enchaîne tout seul.",
        ),
        quiz = listOf(
            q("$M-l5-q1", "Comment assigner le pattern actif à un pad ?",
                listOf("Maintenir Pattern Set et appuyer sur le pad", "Maintenir Shift et appuyer sur le pad", "Appuyer deux fois sur le pad"), 0,
                "Maintenez Pattern Set enfoncé tout en appuyant sur un pad pour lui assigner le motif actif.", OM_PATTERN_SET_BOUTON),
            q("$M-l5-q2", "Avec quoi choisit-on le groupe de Pattern Set ?",
                listOf("Les boutons Step 1 à 4", "<Part, Part>", "Le pad tactile"), 0, "Le groupe se choisit avec les boutons de pas 1 à 4.", OM_PATTERN_SET),
            q("$M-l5-q3", "Combien d'emplacements de Pattern Set y a-t-il ?",
                listOf("16", "64", "250"), 1, "Quatre groupes de 16 pads ; l'export audio des Pattern Sets porte sur les numéros 1 à 64.", OM_EXPORT_PSET),
        ),
    )

    // ---------------------------------------------------------------------
    // 7.6 Atelier : construire un morceau complet
    // ---------------------------------------------------------------------
    private val lesson6 = Lesson(
        id = "$M-l6",
        moduleId = M,
        order = 6,
        title = "Atelier : un morceau complet",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_SAUVEGARDE, PG_CHAIN, PG_CHAIN_MODE, OM_PATTERN_SET),
        durationMinutes = 30,
        objective = "Assembler un morceau complet, de l'intro à la fin, qui tourne seul avec CHAIN et se joue aussi à la main avec le Pattern Set.",
        explanation = "Cet atelier rassemble le module. La structure de base : INTRO → MONTEE → DROP A → BREAK → MONTEE → DROP B " +
            "→ OUTRO. Chaque section est un pattern de la même famille (leçon 7.1), allégé (7.2) ou transformé (7.3). La " +
            "chaîne (7.4) donne une version automatique, pratique pour écouter le morceau en entier et juger les durées. Le " +
            "Pattern Set (7.5) donne une version à jouer à la main. Une même MONTEE peut servir deux fois dans la chaîne si " +
            "tu la dupliques : CHAIN TO est un seul réglage par pattern, donc deux passages vers deux destinations " +
            "différentes demandent deux patterns.",
        controls = listOf(
            MachineControl("Shift + Write", "Dupliquer une section.", OM_SAUVEGARDE),
            MachineControl("CHAIN TO / CHAIN REPEAT + CHAIN MODE", "La version automatique.", PG_CHAIN),
            MachineControl("Pattern Set", "La version jouée à la main.", OM_PATTERN_SET),
        ),
        steps = listOf(
            "Duplique MONTEE en « MONTEE 2 » (Shift + Write).",
            "Crée « OUTRO » à partir d'INTRO, en retirant encore une part.",
            "Règle la chaîne : INTRO → MONTEE → DROP A → BREAK → MONTEE 2 → DROP B → OUTRO, avec les répétitions voulues ; OUTRO sur Off.",
            "Vérifie CHAIN MODE sur On et écoute le morceau en entier sans toucher la machine.",
            "Ajuste les CHAIN REPEAT des sections trop longues ou trop courtes.",
            "Range aussi les sections dans un groupe de Pattern Set, et joue le morceau à la main.",
        ),
        expectedResult = "Un morceau complet, au moins 5 minutes, qui tourne seul, et que tu sais aussi jouer à la main section par section.",
        exercise = Exercise(
            id = "$M-l6-ex",
            lessonId = "$M-l6",
            title = "Ton premier morceau electribe",
            instructions = listOf(
                "Construis un morceau complet dans le style visé de ton choix.",
                "Écoute-le en version automatique, puis joue-le à la main en changeant l'ordre ou la durée d'une section.",
                "Note ce qui marche mieux à la main qu'en automatique : ce sera la matière du Module 8.",
            ),
            successCriteria = "Le morceau tient de bout en bout dans les deux versions, et tu as noté au moins une idée de jeu live.",
            styleHint = "Choisis un seul style pour ce premier morceau : hardtek, tribe, tribecore, mentalcore ou acidcore. Le mélange viendra au set.",
        ),
        commonMistakes = listOf(
            "Faire pointer deux fois le même pattern vers deux suites différentes : CHAIN TO n'a qu'une valeur par pattern ; duplique la section.",
            "Oublier un CHAIN TO sur Off en fin de morceau : la chaîne boucle ou part vers un pattern inattendu.",
            "Ne pas écouter le morceau en entier en version automatique : c'est le meilleur moyen de juger les durées.",
        ),
        quiz = listOf(
            q("$M-l6-q1", "Pourquoi dupliquer la MONTEE pour l'utiliser deux fois dans une chaîne vers deux sections différentes ?",
                listOf("Parce que CHAIN TO n'a qu'une valeur par pattern", "Parce qu'un pattern ne peut jouer qu'une fois", "Pour changer de tempo"), 0,
                "CHAIN TO est un paramètre de pattern : un seul pattern suivant par pattern.", PG_CHAIN),
            q("$M-l6-q2", "Comment terminer une chaîne sur une section qui tourne en boucle ?",
                listOf("CHAIN TO de cette section sur Off", "CHAIN REPEAT sur 64", "CHAIN MODE sur Off"), 0,
                "Avec CHAIN TO sur Off, le pattern actuel continue de jouer.", PG_CHAIN),
            q("$M-l6-q3", "Quelle version du morceau permet de changer l'ordre des sections en jouant ?",
                listOf("La chaîne (CHAIN)", "Le Pattern Set", "INITIALIZE PATTERN"), 1, "Le Pattern Set lance le pattern du pad sur lequel tu appuies.", OM_PATTERN_SET),
        ),
    )

    val lessons: List<Lesson> = listOf(lesson1, lesson2, lesson3, lesson4, lesson5, lesson6)
}
