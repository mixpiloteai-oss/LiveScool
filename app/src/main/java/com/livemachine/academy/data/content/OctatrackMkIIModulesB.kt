package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** Octatrack MKII — modules 5 à 7 : variations et parts, scènes et tempo, sampling live et arrangeur. */
internal object OctatrackMkIIModulesB {

    private val MICRO = ot("12.12 Micro timing", "77")
    private val CONDITIONS = ot("12.13 Conditional locks", "77–78")
    private val FILL = ot("12.14 Fill mode", "78")
    private val ECHELLE = ot("12.15 Scale setup", "78")
    private val PAGE = ot("3.1 Front panel ([PAGE])", "12")
    private val PARTS = ot("10.2 Parts", "52–53")
    private val SCENES = ot("10.3–10.3.1 Scenes, assigning and adjusting", "53")
    private val SCENES_VOLUME = ot("10.3.2–10.3.5 Scene volume locking, mute, copy, clear", "54")
    private val SCENES_DEMO = ot("6.1.3 Experimenting with scenes", "22")
    private val TEMPO = ot("8.9 Tempo menu", "43")
    private val FACADE = ot("3.1 Front panel", "12–13")
    private val CUE = ot("11.4.1–11.4.2 Track main and cue level", "57")
    private val RECORDERS = ot("9. Track recorders and Pickup machines", "44")
    private val REC_SETUP = ot("9.1 Recording edit and setup menus", "45–47")
    private val MANUEL = ot("9.2.1 Manual sampling", "47–48")
    private val LECTURE_BUFFER = ot("9.2.3 Playback of captured recorder samples", "48–49")
    private val PICKUP = ot("9.3 Pickup machine sampling", "49")
    private val ARRANGER = ot("14. The arranger", "87")
    private val ARR_EDIT = ot("14.1.1 Edit", "87–88")

    // ---------------------------------------------------------------------
    // Module 5 — Variations et parts
    // ---------------------------------------------------------------------
    private const val M5 = "ot2-m5"

    private val m5l1 = ctLesson(
        M5, 1, "Conditions de trig et mode FILL", listOf(CONDITIONS, FILL), 20,
        objective = "Faire jouer certains trigs seulement à certains passages, au hasard ou en mode FILL.",
        explanation = "En GRID RECORDING, maintiens un trig et appuie sur [LEFT] ou [RIGHT] : le menu de micro timing s'ouvre avec " +
            "TRIG CONDITION, réglée avec LEVEL. Conditions : X% (probabilité), A:B (joue au passage A sur un cycle de B), 1ST " +
            "(premier passage), PRE et NEI (selon la condition précédente ou la piste voisine), FILL (seulement en mode FILL), chacune " +
            "aussi en version inversée. Mode FILL : [DOWN] + [PAGE] pour un tour, [UP] + [PAGE] maintenus tant qu'on les tient, " +
            "[UP] + [DOWN] + [PAGE] pour le verrouiller.",
        controls = listOf(
            cmd("[TRIG] maintenu + [LEFT]/[RIGHT], puis LEVEL", "TRIG CONDITION du pas.", CONDITIONS),
            cmd("[DOWN] + [PAGE]", "FILL pour un tour de pattern.", FILL),
            cmd("[UP] + [PAGE] maintenus", "FILL tant que les touches sont tenues.", FILL),
        ),
        steps = listOf(
            "En GRID RECORDING, pose une caisse claire sur le pas 16.",
            "Maintiens [TRIG 16], appuie sur [RIGHT], puis règle TRIG CONDITION sur 1:4 avec LEVEL.",
            "Sur un charley, mets 50% sur quelques pas.",
            "Pose un crash avec la condition FILL.",
            "Lance la lecture et maintiens [UP] + [PAGE] : le crash joue.",
        ),
        expected = "La caisse claire tombe un tour sur quatre, les charleys varient et le crash ne joue qu'en FILL.",
        exerciseTitle = "Variation sur commande",
        exercise = listOf(
            "Prépare un roulement réglé en FILL.",
            "Déclenche-le avec [DOWN] + [PAGE] juste avant de changer de pattern.",
        ),
        success = "Le roulement n'arrive qu'au tour choisi.",
        style = "Tribe : conditions A:B et probabilité pour des percussions qui ne se répètent jamais pareil.",
        mistakes = listOf(
            "Chercher TRIG CONDITION hors GRID RECORDING : il faut que ce mode soit actif.",
            "Croire que la condition FILL marche seule : il faut activer le mode FILL.",
        ),
        quiz = listOf(
            qz("Comment ouvrir le menu où se règle TRIG CONDITION ?",
                listOf("Maintenir le trig et appuyer sur [LEFT] ou [RIGHT]", "[FUNC] + [PAGE]", "[TRIG] + [YES]"), 0,
                "En GRID RECORDING, trig maintenu + flèche ouvre le menu de micro timing.", CONDITIONS),
            qz("Avec 1:2, le trig joue…", listOf("Au 1er, 3e, 5e passage…", "À chaque passage", "Une seule fois"), 0,
                "1:2 joue au premier passage puis tous les deux passages.", CONDITIONS),
            qz("Comment activer FILL pour un seul tour ?", listOf("[DOWN] + [PAGE]", "[UP] + [PAGE]", "[YES] + [PAGE]"), 0,
                "[DOWN] + [PAGE] active FILL pour un cycle de pattern.", FILL),
            qz("Que signifie X% ?", listOf("Une probabilité de jouer", "Un volume", "Un swing"), 0,
                "C'est une condition de probabilité.", CONDITIONS),
        ),
    )

    private val m5l2 = ctLesson(
        M5, 2, "Micro timing, répétitions et longueur", listOf(MICRO, ECHELLE, PAGE), 20,
        objective = "Décaler un trig dans le temps, le répéter et régler la longueur du pattern.",
        explanation = "Dans le même menu (trig maintenu + flèche), [LEFT]/[RIGHT] déplace le trig sur une grille de 1/384 (en avance ou " +
            "en retard) et TRIG COUNT ([UP]/[DOWN]) ajoute de 2 à 8 répétitions réparties jusqu'au pas suivant. [FUNC] + [PAGE] " +
            "ouvre SCALE SETUP : en mode NORMAL, toutes les pistes ont la même longueur (jusqu'à 64 pas) ; [PAGE] passe d'une page " +
            "à l'autre en GRID RECORDING, et les LED <PAGE> montrent la page jouée ou éditée.",
        controls = listOf(
            cmd("[TRIG] maintenu + [LEFT]/[RIGHT]", "Micro timing (grille 1/384).", MICRO),
            cmd("TRIG COUNT ([UP]/[DOWN])", "2 à 8 répétitions du trig.", MICRO),
            cmd("[FUNC] + [PAGE]", "SCALE SETUP : longueur du pattern.", ECHELLE),
            cmd("[PAGE] et LED <PAGE>", "Page éditée en GRID RECORDING.", PAGE),
        ),
        steps = listOf(
            "En GRID, maintiens un trig de charley et décale-le un peu avec [RIGHT].",
            "Sur la caisse claire du pas 16, règle TRIG COUNT sur 4 avec [UP].",
            "Ouvre [FUNC] + [PAGE] et règle le pattern sur 32 pas.",
            "Appuie sur [PAGE] pour éditer la page 2 et ajoute une variation.",
        ),
        expected = "Le charley groove, la caisse claire roule et le pattern dure deux mesures.",
        exerciseTitle = "Fin de phrase",
        exercise = listOf(
            "Sur un pattern de 64 pas, ajoute un roulement avec TRIG COUNT seulement dans la dernière page.",
        ),
        success = "Le roulement n'arrive qu'à la quatrième mesure.",
        mistakes = listOf(
            "Décaler le premier pas vers l'arrière sans le vouloir : il est joué à la fin du pattern.",
            "Éditer la mauvaise page : vérifie la LED <PAGE> allumée.",
        ),
        quiz = listOf(
            qz("Quelle est la précision du micro timing ?", listOf("1/16", "1/96", "1/384"), 2,
                "Le trig se place sur une grille de 1/384.", MICRO),
            qz("Combien de répétitions TRIG COUNT peut-il ajouter ?", listOf("2 à 8", "1 à 3", "jusqu'à 64"), 0,
                "TRIG COUNT de 2 à 8 répartit des répétitions jusqu'au pas suivant.", MICRO),
            qz("Comment ouvrir SCALE SETUP ?", listOf("[FUNC] + [PAGE]", "[PAGE] seul", "[FUNC] + [PTN]"), 0,
                "[FUNC] + [PAGE] ouvre le réglage de longueur.", ECHELLE),
            qz("Longueur maximale d'un pattern ?", listOf("16", "32", "64"), 2,
                "La longueur totale peut aller jusqu'à 64 pas.", ECHELLE),
        ),
    )

    private val m5l3 = ctLesson(
        M5, 3, "Les parts : changer tous les sons d'un coup", listOf(PARTS), 15,
        objective = "Lier des patterns à des parts différentes et revenir instantanément aux réglages sauvés.",
        explanation = "Chaque banque a 4 parts ; tous les patterns utilisent la part 1 par défaut. [PART] ouvre PART QUICK SELECT : " +
            "choisis une part et [YES] pour la lier au pattern actif (une part modifiée non sauvée porte un astérisque). [FUNC] + " +
            "[PART] ouvre PART EDIT : RENAME, SAVE, SAVE ALL, RELOAD. Une part sauvée peut être rechargée en direct avec [FUNC] + " +
            "[CUE] : idéal après avoir beaucoup tourné les boutons.",
        controls = listOf(
            cmd("[PART]", "PART QUICK SELECT : lier une part au pattern.", PARTS),
            cmd("[FUNC] + [PART]", "PART EDIT : renommer, sauver, recharger.", PARTS),
            cmd("[FUNC] + [CUE]", "Recharge la part sauvée.", PARTS),
        ),
        steps = listOf(
            "Sur le pattern A01, ouvre [FUNC] + [PART] et choisis SAVE pour sauver la part 1.",
            "Copie A01 sur A02, puis sur A02 ouvre [PART], choisis la part 2 et [YES].",
            "Sur la part 2, change les samples ou les effets de deux pistes.",
            "Reviens sur A01 : il garde la part 1 et ses sons.",
            "Déforme la part 1, puis recharge-la avec [FUNC] + [CUE].",
        ),
        expected = "A01 et A02 ont la même séquence mais des sons différents, et la part 1 revient en un geste.",
        exerciseTitle = "Même séquence, autre ambiance",
        exercise = listOf(
            "Crée deux parts pour un même morceau : une sèche et une très saturée.",
            "Passe de l'une à l'autre en changeant de pattern.",
        ),
        success = "Le changement de pattern change toute l'ambiance sonore.",
        style = "Mentalcore : une part « destruction » pour les passages les plus durs.",
        mistakes = listOf(
            "Utiliser [FUNC] + [CUE] sur une part jamais sauvée : « SAVE PART FIRST » s'affiche.",
            "Oublier que tous les patterns utilisent la part 1 par défaut.",
        ),
        quiz = listOf(
            qz("Combien de parts par banque ?", listOf("1", "4", "16"), 1,
                "Chaque banque contient 4 parts.", PARTS),
            qz("Quelle part utilisent les patterns par défaut ?", listOf("La part 1", "Aucune", "La part 4"), 0,
                "Tous les patterns d'une banque utilisent la part 1 par défaut.", PARTS),
            qz("Que fait [FUNC] + [CUE] ?", listOf("Recharge la part sauvée", "Sauve la part", "Coupe les sorties CUE"), 0,
                "Il rétablit les réglages sauvegardés de la part.", PARTS),
            qz("Que signifie un astérisque à côté d'une part ?", listOf("Modifiée et non sauvée", "Vide", "Verrouillée"), 0,
                "L'astérisque indique une part modifiée non sauvegardée.", PARTS),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 6 — Scènes, crossfader et tempo
    // ---------------------------------------------------------------------
    private const val M6 = "ot2-m6"

    private val m6l1 = ctLesson(
        M6, 1, "Scènes et crossfader", listOf(SCENES, SCENES_DEMO), 20,
        objective = "Préparer deux états du son et passer de l'un à l'autre avec le crossfader.",
        explanation = "Chaque part a 16 scènes. Une scène est un ensemble de valeurs verrouillées. [SCENE A] + [TRIG] place une scène " +
            "dans l'emplacement A, [SCENE B] + [TRIG] dans le B. Crossfader à gauche : scène A pleinement active ; à droite : scène B ; " +
            "entre les deux, les valeurs sont interpolées. Pour verrouiller une valeur dans une scène : maintiens sa touche [SCENE] et " +
            "tourne l'encodeur ; maintenir [SCENE] et appuyer sur l'encodeur retire le lock. Les locks de scène sont prioritaires " +
            "sur les parameter locks quand on bouge le crossfader.",
        controls = listOf(
            cmd("[SCENE A] / [SCENE B] + [TRIG]", "Assigne une scène à l'emplacement A ou B.", SCENES),
            cmd("[SCENE] maintenu + encodeur", "Verrouille une valeur dans la scène.", SCENES),
            cmd("Crossfader", "Interpole entre la scène A et la scène B.", SCENES),
        ),
        steps = listOf(
            "Assigne la scène 1 à A ([SCENE A] + [TRIG 1]) et la scène 2 à B ([SCENE B] + [TRIG 2]).",
            "Maintiens [SCENE B], choisis une piste et ferme son filtre ; ajoute de la réverbération.",
            "Laisse la scène A vide (elle garde les réglages de la part).",
            "Lance un pattern et glisse lentement le crossfader de gauche à droite.",
        ),
        expected = "Le son passe progressivement de normal à filtré et réverbéré.",
        exerciseTitle = "Montée au crossfader",
        exercise = listOf(
            "Fais une scène B qui ferme le filtre de toutes les pistes sauf le kick.",
            "Sur 8 mesures, pousse le crossfader vers B, puis reviens d'un coup vers A pour le drop.",
        ),
        success = "La montée est progressive et le drop est net.",
        style = "Hardtek / tribe : la montée filtrée au crossfader puis le retour brutal font réagir le public.",
        mistakes = listOf(
            "Assigner deux scènes vides : le crossfader n'a aucun effet.",
            "Tourner un encodeur sans maintenir [SCENE] : c'est la part qui change, pas la scène.",
        ),
        quiz = listOf(
            qz("Combien de scènes par part ?", listOf("2", "8", "16"), 2,
                "Chaque part contient 16 scènes.", SCENES),
            qz("Crossfader complètement à droite :", listOf("Scène B active", "Scène A active", "Aucune scène"), 0,
                "À droite, la scène de l'emplacement B est pleinement active.", SCENES),
            qz("Comment verrouiller une valeur dans une scène ?",
                listOf("Maintenir [SCENE] et tourner l'encodeur", "Maintenir [TRIG]", "[FUNC] + encodeur"), 0,
                "Comme un parameter lock, mais en maintenant la touche [SCENE].", SCENES),
            qz("Qui est prioritaire quand on bouge le crossfader ?", listOf("Les locks de scène", "Les parameter locks"), 0,
                "Les locks de scène ont priorité pour des transitions sans à-coups.", SCENES),
        ),
    )

    private val m6l2 = ctLesson(
        M6, 2, "Fondus de volume et mute de scène", listOf(SCENES_VOLUME), 15,
        objective = "Faire des fondus entre pistes sans creux de volume et couper/relancer une scène.",
        explanation = "En maintenant une touche [SCENE], des paramètres spéciaux apparaissent : XLV (sur LEVEL, après effets), XVOL " +
            "(page AMP, avant effets) et XDIR (mixer, entrées), verrouillables sur MIN (coupé) ou MAX. Ils font un fondu à énergie " +
            "constante, sans creux au centre du crossfader. [FUNC] + [SCENE A]/[SCENE B] coupe une scène : les valeurs de la part " +
            "reviennent. Copier une scène : [SCENE] + [RECORD], coller : [SCENE] + [STOP], effacer : [SCENE] + [PLAY].",
        controls = listOf(
            cmd("XLV / XVOL / XDIR (avec [SCENE])", "Fondus de volume à énergie constante.", SCENES_VOLUME),
            cmd("[FUNC] + [SCENE A]/[SCENE B]", "Mute de la scène.", SCENES_VOLUME),
            cmd("[SCENE] + [RECORD]/[STOP]/[PLAY]", "Copier / coller / effacer une scène.", SCENES_VOLUME),
        ),
        steps = listOf(
            "Prépare deux pistes : un morceau sur la 1, un autre sur la 2.",
            "Maintiens [SCENE A], sélectionne la piste 2 et mets XLV sur MIN ; fais l'inverse pour la piste 1 avec [SCENE B].",
            "Glisse le crossfader : les deux pistes se croisent sans baisse de volume au milieu.",
            "Coupe la scène B avec [FUNC] + [SCENE B], puis relance-la.",
        ),
        expected = "Le passage d'une piste à l'autre est fluide, comme sur une table de mixage DJ.",
        exerciseTitle = "Enchaînement DJ",
        exercise = listOf(
            "Enchaîne deux boucles au crossfader avec XLV.",
            "Copie la scène A dans une autre scène pour la réutiliser.",
        ),
        success = "Le volume reste constant pendant tout le fondu.",
        mistakes = listOf(
            "Utiliser LEVEL au lieu de XLV : un creux de volume apparaît au centre.",
            "Chercher XLV sans maintenir [SCENE] : il n'apparaît qu'avec la touche maintenue.",
        ),
        quiz = listOf(
            qz("Pourquoi utiliser XLV plutôt que LEVEL dans une scène ?",
                listOf("Fondu sans creux de volume au centre", "Plus fort", "Pour les effets"), 0,
                "XLV, XVOL et XDIR font un fondu à énergie constante.", SCENES_VOLUME),
            qz("Quelles valeurs peut prendre XLV ?", listOf("MIN ou MAX", "0 à 127", "-64 à 63"), 0,
                "XLV se verrouille sur MIN (coupé) ou MAX.", SCENES_VOLUME),
            qz("Comment couper une scène ?", listOf("[FUNC] + [SCENE]", "[SCENE] + [PLAY]", "[NO] + [SCENE]"), 0,
                "[FUNC] + [SCENE A]/[SCENE B] coupe la scène.", SCENES_VOLUME),
            qz("Comment effacer une scène ?", listOf("[SCENE] + [PLAY]", "[SCENE] + [STOP]", "[SCENE] + [RECORD]"), 0,
                "[SCENE] + [PLAY] efface ; refaire la combinaison annule.", SCENES_VOLUME),
        ),
    )

    private val m6l3 = ctLesson(
        M6, 3, "Tempo, calage et pré-écoute", listOf(TEMPO, FACADE, CUE), 15,
        objective = "Régler le tempo, se caler sur une autre source et pré-écouter une piste au casque.",
        explanation = "[TEMPO] : LEVEL règle les BPM entiers, [UP]/[DOWN] les décimales ; [FUNC] + [YES] choisit un tempo commun au " +
            "projet ou propre à chaque pattern. [FUNC] + [TEMPO] tapé en rythme calcule le tempo après 4 frappes. [LEFT]/[RIGHT] " +
            "poussent le tempo de 10 % tant qu'ils sont tenus ; [TRACK] + [LEFT]/[RIGHT] ne poussent que les samples des pistes tenues. " +
            "[FUNC] + [MIX] active le métronome. [CUE] + [TRACK] envoie une piste vers les sorties CUE ; [CUE] + LEVEL règle son niveau CUE.",
        controls = listOf(
            cmd("[TEMPO] + LEVEL / [UP]/[DOWN]", "BPM entiers / décimales.", TEMPO),
            cmd("[FUNC] + [TEMPO]", "Tap tempo.", TEMPO),
            cmd("[LEFT] / [RIGHT]", "Pousse le tempo de 10 % (temporaire).", TEMPO),
            cmd("[CUE] + [TRACK]", "Pré-écoute de la piste sur CUE.", FACADE),
        ),
        steps = listOf(
            "Ouvre [TEMPO], règle 160 BPM avec LEVEL.",
            "Tape le tempo avec [FUNC] + [TEMPO] sur un morceau externe.",
            "Corrige les dérives en maintenant [LEFT] ou [RIGHT].",
            "Active le métronome avec [FUNC] + [MIX], puis coupe-le.",
            "Mets une piste en CUE avec [CUE] + [TRACK] et écoute-la au casque relié aux sorties CUE.",
        ),
        expected = "L'Octatrack reste calé sur la source externe et tu pré-écoutes une piste sans l'envoyer au public.",
        exerciseTitle = "Calage à l'oreille",
        exercise = listOf(
            "Cale l'Octatrack sur un morceau joué ailleurs pendant une minute.",
            "Pré-écoute la piste suivante en CUE avant de la faire entrer dans le mix.",
        ),
        success = "Les kicks restent ensemble et la piste entre au bon moment.",
        style = "Hardtek / tribe : entre 150 et 180 BPM ; la pré-écoute permet de préparer le morceau suivant.",
        mistakes = listOf(
            "Changer le tempo du pattern en croyant changer celui du projet : vérifie le mode avec [FUNC] + [YES].",
            "Oublier que le coup de pouce [LEFT]/[RIGHT] n'est que temporaire.",
        ),
        quiz = listOf(
            qz("Comment taper le tempo ?", listOf("[FUNC] + [TEMPO] en rythme", "[TEMPO] + [YES]", "LEVEL"), 0,
                "Après quatre frappes, la moyenne devient le nouveau tempo.", TEMPO),
            qz("Que fait [LEFT] ou [RIGHT] hors menu ?", listOf("Pousse le tempo de 10 % temporairement", "Change de pattern", "Coupe le son"), 0,
                "Le tempo revient à sa valeur au relâchement.", TEMPO),
            qz("Comment activer le métronome ?", listOf("[FUNC] + [MIX]", "[FUNC] + [TEMPO]", "[MIX] + [YES]"), 0,
                "[FUNC] + [MIX] active ou coupe le métronome.", FACADE),
            qz("Comment envoyer une piste en pré-écoute ?", listOf("[CUE] + [TRACK]", "[FUNC] + [TRACK]", "[TRACK] + [PLAY]"), 0,
                "La piste est routée vers les sorties CUE.", FACADE),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 7 — Sampling live et arrangeur
    // ---------------------------------------------------------------------
    private const val M7 = "ot2-m7"

    private val m7l1 = ctLesson(
        M7, 1, "Échantillonner avec les enregistreurs de piste", listOf(RECORDERS, REC_SETUP, MANUEL, LECTURE_BUFFER), 25,
        objective = "Enregistrer une entrée ou une piste interne et rejouer tout de suite l'enregistrement.",
        explanation = "Chaque piste a un enregistreur et un tampon (buffer) placé dans la liste Flex. [FUNC] + [REC1] ouvre RECORDING " +
            "SETUP 1 : INAB/INCD (entrées), SRC3 (source interne : MAIN, CUE, T1–T8), RLEN (durée, ou MAX), TRIG (ONE, ONE2, HOLD). " +
            "[TRACK] + [REC1] enregistre l'entrée AB, + [REC2] l'entrée CD, + [REC3] la source interne. Pour rejouer, assigne le " +
            "tampon à une machine Flex (astuce : tampon 1 sur la piste 1). Les tampons sont perdus à l'extinction : sauve-les avec " +
            "[FUNC] + [REC3] > SAVE THIS RECORDING.",
        controls = listOf(
            cmd("[FUNC] + [REC1] / [REC2]", "RECORDING SETUP 1 et 2.", REC_SETUP),
            cmd("[TRACK] + [REC1]/[REC2]/[REC3]", "Enregistrement manuel.", MANUEL),
            cmd("Tampons d'enregistrement (liste Flex)", "À assigner à une machine Flex.", LECTURE_BUFFER),
            cmd("[FUNC] + [REC3]", "RECORDING EDIT : sauver les enregistrements.", REC_SETUP),
        ),
        steps = listOf(
            "Branche une source sur INPUT AB.",
            "Sur la piste 1, assigne une machine Flex et le tampon d'enregistrement 1.",
            "Ouvre [FUNC] + [REC1], règle INAB sur A B et RLEN sur 16.",
            "Pendant la lecture, appuie sur [TRACK 1] + [REC1] : l'enregistrement dure 16 pas.",
            "Déclenche la piste 1 : tu entends l'enregistrement. Sauve-le avec [FUNC] + [REC3] > SAVE THIS RECORDING.",
        ),
        expected = "Une mesure de ta source est enregistrée, rejouée et sauvée sur la carte.",
        exerciseTitle = "Resampling interne",
        exercise = listOf(
            "Règle SRC3 sur MAIN et enregistre une mesure de ton propre mix avec [TRACK] + [REC3].",
            "Rejoue-la sur une autre piste Flex et déforme-la avec RATE ou des effets.",
        ),
        success = "Tu rejoues et transformes un extrait de ton propre mix.",
        style = "Free party : le resampling du mix permet des breaks et des boucles « cassées » en direct.",
        mistakes = listOf(
            "Oublier d'assigner le tampon à une machine Flex : rien ne joue l'enregistrement.",
            "Éteindre sans sauver : les tampons sont perdus.",
        ),
        quiz = listOf(
            qz("Quelle combinaison enregistre l'entrée AB sur la piste ?", listOf("[TRACK] + [REC1]", "[TRACK] + [REC3]", "[FUNC] + [REC1]"), 0,
                "[TRACK] + [REC1] = entrée AB, [REC2] = CD, [REC3] = interne.", MANUEL),
            qz("Où se trouvent les tampons d'enregistrement ?", listOf("Dans la liste Flex", "Dans la liste Static", "Dans l'audio pool"), 0,
                "Les huit tampons sont placés dans la liste Flex.", LECTURE_BUFFER),
            qz("Les tampons sont-ils gardés à l'extinction ?", listOf("Oui", "Non, il faut les sauver"), 1,
                "Ils sont perdus à l'extinction ou au changement de projet.", RECORDERS),
            qz("Quel réglage choisit une source interne ?", listOf("SRC3", "INAB", "RLEN"), 0,
                "SRC3 : MAIN, CUE ou une des pistes T1–T8.", REC_SETUP),
        ),
    )

    private val m7l2 = ctLesson(
        M7, 2, "Les machines Pickup : boucler en direct", listOf(PICKUP, REC_SETUP), 20,
        objective = "Utiliser l'Octatrack comme looper : enregistrer une boucle et en superposer d'autres.",
        explanation = "Une machine Pickup est liée à l'enregistreur et au tampon de sa piste : pas besoin d'assigner de tampon. Ses réglages " +
            "viennent de RECORDING SETUP (source, durée, fondus). La première Pickup qui enregistre est la maître : le tempo de " +
            "l'Octatrack s'adapte à sa boucle. Les suivantes sont esclaves : avec LEN sur X1 elles ont la même longueur que la maître, " +
            "X2 le double. Les Pickup ne se séquencent pas et ne se verrouillent pas. La piste doit être active pour enregistrer.",
        controls = listOf(
            cmd("Machine Pickup", "Looper lié à l'enregistreur de la piste.", PICKUP),
            cmd("LEN (X1, X2…)", "Longueur d'une boucle esclave par rapport à la maître.", PICKUP),
            cmd("[FUNC] + [REC1]", "Source et réglages d'enregistrement.", REC_SETUP),
        ),
        steps = listOf(
            "Assigne une machine Pickup aux pistes 1 et 2.",
            "Dans [FUNC] + [REC1], choisis l'entrée de ta source.",
            "Sélectionne la piste 1 et enregistre une boucle : elle devient la maître et fixe le tempo.",
            "Sur la piste 2, règle LEN sur X1 et enregistre une seconde partie.",
            "Écoute les deux boucles ensemble.",
        ),
        expected = "Deux boucles jouent ensemble, calées, et le tempo suit la première.",
        exerciseTitle = "Looper live",
        exercise = listOf(
            "Construis un morceau en direct avec trois boucles Pickup issues d'une même source.",
        ),
        success = "Les trois boucles restent calées entre elles.",
        mistakes = listOf(
            "Essayer de séquencer une Pickup : ce n'est pas possible.",
            "Enregistrer sur une piste non active : la Pickup ne peut pas enregistrer.",
        ),
        quiz = listOf(
            qz("Que se passe-t-il quand la première Pickup enregistre ?",
                listOf("Le tempo s'adapte à sa boucle", "Rien", "Le pattern s'arrête"), 0,
                "La première est maître et ajuste le tempo de l'Octatrack.", PICKUP),
            qz("Avec LEN sur X2, une boucle esclave dure…", listOf("Deux fois la maître", "La moitié", "Pareil"), 0,
                "X1 = même longueur, X2 = double.", PICKUP),
            qz("Peut-on verrouiller des paramètres sur une Pickup ?", listOf("Oui", "Non"), 1,
                "Les Pickup ne se séquencent pas et ne se verrouillent pas.", PICKUP),
            qz("Faut-il assigner un tampon à une Pickup ?", listOf("Oui", "Non, elle est liée à celui de sa piste"), 1,
                "La Pickup est liée à l'enregistreur et au tampon de sa piste.", PICKUP),
        ),
    )

    private val m7l3 = ctLesson(
        M7, 3, "L'arrangeur", listOf(ARRANGER, ARR_EDIT), 20,
        objective = "Construire un morceau complet ligne par ligne et le jouer.",
        explanation = "Chaque projet a 8 arrangements de 256 lignes maximum. [ARR] ouvre le menu ; EDIT ouvre l'éditeur : chaque ligne " +
            "a un pattern (PAT), un nombre de répétitions (REP), un départ (OF), une longueur (LN), des scènes A et B, un tempo (B). " +
            "[FUNC] + [DOWN] insère une ligne, [FUNC] + [UP] en supprime une. Avant A01 dans PAT : HALT (arrêt), LOOP (boucle), " +
            "JUMP (saut), REM (texte). [FUNC] + [ARR] active le mode ARRANGEMENT, puis [PLAY] ; [STOP] deux fois revient au début.",
        controls = listOf(
            cmd("[ARR] > EDIT", "Éditeur d'arrangement.", ARR_EDIT),
            cmd("[FUNC] + [DOWN] / [FUNC] + [UP]", "Insérer / supprimer une ligne.", ARR_EDIT),
            cmd("PAT, REP, SCENE, B", "Pattern, répétitions, scènes, tempo de la ligne.", ARR_EDIT),
            cmd("[FUNC] + [ARR]", "Active le mode ARRANGEMENT.", ARRANGER),
        ),
        steps = listOf(
            "Ouvre [ARR], choisis EDIT.",
            "Sur la première ligne, choisis le pattern d'intro dans PAT et 2 répétitions dans REP.",
            "Insère des lignes avec [FUNC] + [DOWN] pour le thème (4), le break (2) et le retour (4).",
            "Sur la ligne du break, assigne une scène B qui ferme le filtre.",
            "Active le mode avec [FUNC] + [ARR] et lance [PLAY].",
        ),
        expected = "Le morceau se déroule seul, scènes comprises.",
        exerciseTitle = "Morceau complet",
        exercise = listOf(
            "Construis un arrangement de 8 lignes avec une boucle LOOP sur le thème.",
            "Termine par une ligne HALT.",
        ),
        success = "Le morceau boucle sur le thème puis s'arrête à la fin.",
        style = "Hardtek / tribe : intro, montée, break sans kick, retour ; l'arrangeur le joue pour toi.",
        mistakes = listOf(
            "Oublier d'activer le mode ARRANGEMENT avec [FUNC] + [ARR] : [PLAY] joue seulement le pattern.",
            "Chercher HALT, LOOP ou JUMP ailleurs que dans la colonne PAT, avant A01.",
        ),
        quiz = listOf(
            qz("Combien d'arrangements par projet ?", listOf("1", "8", "16"), 1,
                "Chaque projet contient 8 arrangements.", ARRANGER),
            qz("Comment insérer une ligne ?", listOf("[FUNC] + [DOWN]", "[FUNC] + [UP]", "[ARR] + [YES]"), 0,
                "[FUNC] + [DOWN] insère, [FUNC] + [UP] supprime.", ARR_EDIT),
            qz("Comment activer le mode ARRANGEMENT ?", listOf("[FUNC] + [ARR]", "[ARR] seul", "[PLAY] × 2"), 0,
                "[FUNC] + [ARR] active ou coupe le mode.", ARRANGER),
            qz("Quelle commande fait boucler une partie de l'arrangement ?", listOf("LOOP", "HALT", "REM"), 0,
                "LOOP boucle entre la ligne 000 et la ligne de la commande.", ARR_EDIT),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(
            M5, 5, "Variations et parts",
            "Conditions, FILL, micro timing, répétitions, longueur et parts.",
            "Faire évoluer les patterns et changer tous les sons d'un coup.",
            "Les variations conditionnelles et les parts évitent la monotonie des longues boucles.",
            listOf(m5l1, m5l2, m5l3),
        ),
        CourseModule(
            M6, 6, "Scènes, crossfader et tempo",
            "Scènes, fondus de volume, tempo, calage et pré-écoute.",
            "Jouer des transitions au crossfader et caler l'Octatrack en live.",
            "Le crossfader est l'outil de transition par excellence en free party.",
            listOf(m6l1, m6l2, m6l3),
        ),
        CourseModule(
            M7, 7, "Sampling live et arrangeur",
            "Enregistreurs de piste, machines Pickup et arrangeur.",
            "Échantillonner et boucler en direct, puis structurer un morceau complet.",
            "Le resampling en direct et les boucles Pickup font de l'Octatrack un instrument de live complet.",
            listOf(m7l1, m7l2, m7l3),
        ),
    )
}
