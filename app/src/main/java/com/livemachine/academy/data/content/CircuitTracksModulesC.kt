package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** Circuit Tracks — modules 7 à 9 : automation, macros, effets et mixage. */
internal object CircuitTracksModulesC {

    private val DESSUS = ct("Présentation du matériel › Vue du dessus", "16")
    private val MACROS = ct("Les synthétiseurs › Parcourir les macros", "35–36")
    private val MACROS_DRUM = ct("Les rythmes › Utiliser les macros pour créer des rythmes", "64")
    private val AUTO_SYNTH = ct("Les synthétiseurs › Enregistrer les mouvements des potentiomètres", "37–38")
    private val AUTO_DRUM = ct("Les rythmes › Enregistrer les mouvements des potentiomètres", "71–72")
    private val MIDI_MODELES = ct("Les pistes MIDI › Choisir les modèles", "58")
    private val MIXER = ct("Mélangeur", "88")
    private val PANO = ct("Mélangeur › Le panoramique", "89")
    private val MUTE_JEU = ct("Mélangeur › Jouer quand le son des pistes est coupé", "89")
    private val FX = ct("La section d'effets", "90")
    private val REVERB = ct("La section d'effets › La réverbe", "91")
    private val DELAI = ct("La section d'effets › Le délai", "91–92")
    private val AUTO_FX = ct("La section d'effets › Automatiser les envois des effets", "92")
    private val SIDECHAIN = ct("Les sidechains", "93–94")
    private val FILTRE = ct("Le potentiomètre de filtre", "94")
    private val COMPRESSEUR = ct("Annexe › La vue Setup avancée › Le compresseur global", "106")
    private val VEL_FIXE = ct("Les synthétiseurs › Vélocité fixe", "44")
    private val COMPONENTS = ct("Components", "101")

    // ---------------------------------------------------------------------
    // Module 7 — Automation
    // ---------------------------------------------------------------------
    private const val M7 = "ct-m7"

    private val m7l1 = ctLesson(
        M7, 1, "Enregistrer les mouvements des Macros en direct", listOf(AUTO_SYNTH, AUTO_DRUM, MIDI_MODELES), 15,
        photo = listOf("ct-rec", "ct-macros", "ct-velocity", "ct-gate"),
        objective = "Enregistrer un mouvement de Macro dans le pattern pour qu'il se rejoue à chaque tour.",
        explanation = "En lecture, Record allumé, tourne une Macro : sa LED passe au rouge et le mouvement s'inscrit dans le pattern. " +
            "Ressors de Record avant que la boucle recommence, sinon la machine réécrit l'automation avec la position actuelle du potentiomètre. " +
            "Sur une piste Drum, seules les Macros paires agissent, et le mouvement n'est enregistré que si la vue Velocity, Gate ou Probability est ouverte. " +
            "Les Macros des pistes MIDI s'automatisent de la même façon.",
        controls = listOf(
            cmd("Record + Macro", "Enregistre le mouvement (LED rouge).", AUTO_SYNTH),
            cmd("Velocity / Gate / Probability (piste Drum)", "Vues nécessaires pour enregistrer les Macros de batterie.", AUTO_DRUM),
        ),
        steps = listOf(
            "Lance une basse sur Synth 1, vue Note.",
            "Appuie sur Record, ouvre lentement la Macro 5 sur une mesure, puis appuie sur Record avant la fin de la boucle.",
            "Écoute : le filtre s'ouvre seul à chaque tour.",
            "Drum 1 : ouvre la vue Velocity, Record, tourne la Macro 4 sur la seconde moitié, puis Record.",
            "Vérifie que le mouvement se rejoue sur le kick.",
        ),
        expected = "Un mouvement de filtre sur la basse et un changement de déclin sur le kick, rejoués à chaque boucle.",
        exerciseTitle = "La montée enregistrée",
        exercise = listOf(
            "Sur un pattern de 32 pas, enregistre une ouverture de filtre progressive sur toute la longueur.",
            "Sors de Record juste avant la fin de la boucle.",
        ),
        success = "Le filtre s'ouvre de façon régulière à chaque tour, sans saut au début.",
        style = "Acidcore : ouverture de filtre et de résonance automatisée sur la basse.",
        mistakes = listOf(
            "Rester en Record quand la boucle repart : l'automation est remplacée par la position actuelle (p. 37).",
            "Tourner une Macro de batterie en vue Note : sur une piste Drum, il faut la vue Velocity, Gate ou Probability (p. 71–72).",
            "Tourner les Macros impaires sur une piste Drum : seules les paires agissent (p. 71).",
        ),
        quiz = listOf(
            qz("Quelle couleur prend la LED d'une Macro pendant l'enregistrement de son mouvement ?",
                listOf("Vert", "Rouge", "Blanc"), 1,
                "Dès que tu la tournes en Record, sa LED devient rouge.", AUTO_SYNTH),
            qz("Pourquoi faut-il sortir de Record avant la fin de la boucle ?",
                listOf("Pour sauvegarder", "Sinon l'automation est réécrite avec la position actuelle", "Pour éviter de couper le son"), 1,
                "Au tour suivant, la machine remplacerait le mouvement enregistré.", AUTO_SYNTH),
            qz("Dans quelles vues enregistre-t-on les Macros d'une piste Drum ?",
                listOf("Note ou Preset", "Velocity, Gate ou Probability", "Mixer ou FX"), 1,
                "Seulement dans ces trois vues.", AUTO_DRUM),
            qz("Les Macros des pistes MIDI peuvent-elles s'automatiser ?",
                listOf("Oui, comme les autres", "Non", "Seulement avec Components"), 0,
                "Play, Record et tourne les potentiomètres, comme pour un synthé.", MIDI_MODELES),
        ),
    )

    private val m7l2 = ctLesson(
        M7, 2, "Automation pas par pas", listOf(AUTO_SYNTH, AUTO_DRUM, MUTE_JEU), 15,
        photo = listOf("ct-rec", "ct-macros"),
        objective = "Placer un réglage précis sur un pas donné, sans jouer en direct.",
        explanation = "À l'arrêt, Record allumé, tiens un pas et tourne une Macro : la valeur est écrite sur ce pas. " +
            "Cela marche aussi en lecture : Record allumé, tiens le pas et tourne. Sur une piste Drum, fais-le dans la vue Velocity, Gate ou Probability. " +
            "L'automation est stockée à part des notes : tu peux changer le sample d'un pas sans perdre son réglage. " +
            "Astuce du guide : sur une piste coupée dans le Mixer, les pads de pas deviennent des déclencheurs, et l'automation par pas s'applique à chacun.",
        controls = listOf(
            cmd("Record + tenir un pas + Macro", "Écrit la valeur sur ce pas.", AUTO_SYNTH),
            cmd("Vue Velocity, Gate ou Probability (Drum)", "Pour l'automation par pas de la batterie.", AUTO_DRUM),
        ),
        steps = listOf(
            "Arrête la lecture. Synth 1, vue Note, Record.",
            "Tiens le pas 13 : sa note sonne ; tourne la Macro 6 (résonance) vers le haut, puis relâche.",
            "Ressors de Record et lance la lecture : seul le pas 13 est plus résonant.",
            "Drum 2, vue Gate, Record : tiens le pas 13 et tourne la Macro 2 (pitch).",
            "Change le sample du pas 13 (Sample Flip) : le réglage de pitch reste.",
        ),
        expected = "Un accent sonore précis sur un seul pas de la basse et de la snare.",
        exerciseTitle = "Le pas spécial",
        exercise = listOf(
            "Sur la snare, donne un pitch différent aux pas 13 et 15.",
            "Sur la basse, donne une résonance forte au dernier pas seulement.",
        ),
        success = "Seuls les pas choisis changent de son, le reste du pattern est intact.",
        mistakes = listOf(
            "Oublier Record : sans lui, tourner une Macro en tenant un pas ne s'enregistre pas (p. 38).",
            "Faire l'automation de batterie en vue Note : utilise Velocity, Gate ou Probability (p. 72).",
        ),
        quiz = listOf(
            qz("Peut-on enregistrer une valeur de Macro sur un pas à l'arrêt ?",
                listOf("Oui : Record, tenir le pas, tourner la Macro", "Non, seulement en lecture", "Seulement avec un contrôleur MIDI"), 0,
                "Le pas tenu joue sa note et reçoit la nouvelle valeur.", AUTO_SYNTH),
            qz("Changer le sample d'un pas efface-t-il son automation ?",
                listOf("Oui", "Non, l'automation est stockée à part", "Seulement en Sample Flip"), 1,
                "Les données d'automation sont indépendantes des données du pattern.", AUTO_DRUM),
            qz("Sur une piste coupée dans le Mixer, que deviennent les pads de pas ?",
                listOf("Ils s'éteignent", "Ils peuvent jouer les notes en direct", "Ils effacent le pattern"), 1,
                "Le séquenceur ne joue plus la piste : les pads de pas déclenchent les notes à la main.", MUTE_JEU),
            qz("Faut-il que la lecture soit lancée pour l'automation par pas ?",
                listOf("Oui", "Non, elle marche aussi à l'arrêt", "Seulement sur les pistes MIDI"), 1,
                "Elle marche à l'arrêt comme en lecture, Record allumé.", AUTO_SYNTH),
        ),
    )

    private val m7l3 = ctLesson(
        M7, 3, "Effacer l'automation, automatiser mixage et effets", listOf(AUTO_SYNTH, AUTO_DRUM, MIXER, PANO, AUTO_FX), 15,
        photo = listOf("ct-clear", "ct-macros", "ct-mixer", "ct-fx"),
        objective = "Effacer une automation ratée et automatiser niveaux, panoramique et envois d'effets.",
        explanation = "Pour effacer l'automation d'une Macro, tiens Clear et tourne cette Macro d'au moins 20 % : la LED devient rouge. " +
            "Attention, c'est tout le pattern qui perd l'automation de cette Macro, pas seulement un pas. Sur la batterie, tourne vers la gauche. " +
            "Les niveaux du Mixer, le panoramique et les envois de réverbe et de délai s'automatisent aussi en Record. " +
            "Pour le panoramique : Clear + rotation vers la gauche efface l'automation, Clear + rotation vers la droite recentre la piste.",
        controls = listOf(
            cmd("Clear + Macro (≥ 20 %)", "Efface l'automation de cette Macro sur tout le pattern.", AUTO_SYNTH),
            cmd("Record + Macro (vue Mixer)", "Automatise le niveau d'une piste.", MIXER),
            cmd("Clear + Macro (vue panoramique)", "Gauche : efface l'automation ; droite : recentre.", PANO),
            cmd("Record + Macro (vue FX)", "Automatise l'envoi de réverbe ou de délai.", AUTO_FX),
        ),
        steps = listOf(
            "Tiens Clear et tourne la Macro 5 de ta basse : son automation disparaît.",
            "Mixer, Record : baisse puis remonte la Macro 2 (Synth 2) sur une mesure, puis Record.",
            "FX : choisis une réverbe, Record, monte l'envoi de la snare sur le dernier pas seulement, puis Record.",
            "Écoute le résultat, puis efface l'envoi automatisé avec Clear + Macro.",
        ),
        expected = "Tu sais nettoyer une automation et créer des mouvements de mixage et d'effets enregistrés.",
        exerciseTitle = "Le coup de réverbe",
        exercise = listOf(
            "Automatise un envoi de réverbe qui ne frappe que la dernière snare du pattern.",
            "Automatise un panoramique gauche-droite sur les charleys.",
        ),
        success = "Réverbe audible uniquement sur la dernière snare, charleys qui bougent dans l'espace stéréo.",
        style = "Mentalcore : coups de réverbe et de délai automatisés pour marquer les fins de phrase.",
        mistakes = listOf(
            "Croire que Clear + Macro efface un seul pas : c'est tout le pattern pour cette Macro (p. 38).",
            "Tourner trop peu : il faut au moins 20 % de rotation pour effacer (p. 38).",
            "Confondre les sens pour le panoramique : à droite on recentre, à gauche on efface (p. 89).",
        ),
        quiz = listOf(
            qz("Que fait Clear + rotation d'une Macro ?",
                listOf("Efface l'automation de cette Macro sur tout le pattern", "Efface le pas en cours", "Remet la Macro à zéro sans effacer"), 0,
                "L'effacement concerne tout le pattern pour cette Macro.", AUTO_SYNTH),
            qz("De combien faut-il tourner la Macro, Clear tenu ?",
                listOf("5 %", "20 %", "100 %"), 1,
                "Au moins 20 % de sa rotation.", AUTO_DRUM),
            qz("Dans la vue panoramique, que fait Clear + rotation vers la droite ?",
                listOf("Efface l'automation", "Recentre la piste", "Mute la piste"), 1,
                "La LED devient mauve pour confirmer le recentrage.", PANO),
            qz("Les envois d'effets peuvent-ils s'automatiser ?",
                listOf("Non", "Oui, en tournant les Macros en Record dans la vue FX", "Seulement la réverbe"), 1,
                "Réverbe et délai s'automatisent tous les deux.", AUTO_FX),
        ),
    )

    private val m7 = CourseModule(
        id = M7, number = 7, title = "Automation",
        summary = "Mouvements de Macros en direct et par pas, effacement, mixage et effets.",
        goal = "Faire évoluer le son tout seul au fil du pattern.",
        styleFocus = "Filtres acides qui s'ouvrent, coups de réverbe, basses qui bougent sans les mains.",
        lessons = listOf(m7l1, m7l2, m7l3),
    )

    // ---------------------------------------------------------------------
    // Module 8 — Macros et contrôle des sons
    // ---------------------------------------------------------------------
    private const val M8 = "ct-m8"

    private val m8l1 = ctLesson(
        M8, 1, "Les Macros d'un synthé", listOf(MACROS, DESSUS, COMPONENTS), 20,
        photo = listOf("ct-macros"),
        objective = "Sculpter un patch de synthé en direct avec les huit Macros.",
        explanation = "Sur les patches d'origine, chaque Macro peut agir sur jusqu'à quatre paramètres du patch. Les légendes donnent la fonction générale : " +
            "1 Oscillator, 2 Oscillator Mod, 3 Amp Envelope, 4 Filter Envelope, 5 Filter Frequency, 6 Resonance, 7 Modulation, 8 FX. " +
            "Mais l'effet entendu dépend du patch, et certains patches, comme ceux créés avec Components, ont d'autres affectations. " +
            "Les encodeurs sont sans fin : la luminosité de la LED indique la valeur. Le guide conseille d'essayer et de tourner deux Macros ensemble.",
        controls = listOf(
            cmd("Macros 1 à 8 (piste synthé)", "Jusqu'à 4 paramètres par Macro ; LED de la couleur du synthé.", MACROS),
            cmd("Légendes sous les Macros", "Fonction générale sur les patches d'origine.", DESSUS),
        ),
        steps = listOf(
            "Lance une basse sur Synth 1, vue Note.",
            "Tourne chaque Macro de 1 à 8 et note ce qui change.",
            "Reviens au son de départ en observant la luminosité des LED.",
            "Tourne ensemble la Macro 5 (filtre) et la 6 (résonance).",
            "Change de patch et refais le tour : les effets ne sont pas les mêmes.",
        ),
        expected = "Tu sais quelles Macros donnent du mordant, de la longueur ou de la modulation au patch utilisé.",
        exerciseTitle = "Carte des Macros",
        exercise = listOf(
            "Pour deux patches, écris en une phrase l'effet de chaque Macro.",
            "Choisis les deux Macros que tu utiliseras en live sur chacun.",
        ),
        success = "Deux fiches de 8 lignes et deux Macros « live » choisies par patch.",
        style = "Acidcore : Macros 5 et 6 ensemble sur une basse pour le cri acide.",
        mistakes = listOf(
            "Croire qu'une Macro a toujours le même effet : il dépend du patch (p. 35).",
            "Chercher une butée : les encodeurs sont sans fin, regarde la LED (p. 35).",
            "Oublier que les patches créés dans Components peuvent avoir d'autres affectations (p. 35).",
        ),
        quiz = listOf(
            qz("Sur combien de paramètres une Macro peut-elle agir au maximum, sur un patch d'origine ?",
                listOf("1", "2", "4"), 2,
                "Jusqu'à quatre paramètres par Macro.", MACROS),
            qz("Quelle est la légende de la Macro 5 ?",
                listOf("Resonance", "Filter Frequency", "Amp Envelope"), 1,
                "5 Filter Frequency, 6 Resonance.", MACROS_DRUM),
            qz("Comment connaître la valeur d'une Macro ?",
                listOf("Par sa butée", "Par la luminosité de sa LED", "Sur l'écran"), 1,
                "Les encodeurs sont infinis ; la LED reflète la valeur.", MACROS),
            qz("Les Macros peuvent-elles être pilotées par un contrôleur MIDI externe ?",
                listOf("Oui, par messages CC", "Non", "Seulement par Program Change"), 0,
                "La réception des CC doit être active (réglage par défaut).", MACROS),
        ),
    )

    private val m8l2 = ctLesson(
        M8, 2, "Les Macros de la batterie", listOf(MACROS_DRUM), 15,
        photo = listOf("ct-macros"),
        objective = "Transformer un sample de batterie avec les quatre Macros paires.",
        explanation = "Sur une piste Drum, seules les Macros paires sont actives, avec une fonction fixe : 2 Pitch (hauteur), 4 durée du déclin, " +
            "6 distorsion, 8 EQ. Leur LED prend la couleur de la piste. Le résultat dépend beaucoup du sample : " +
            "un même réglage sonne très différemment sur un kick ou une percussion.",
        controls = listOf(
            cmd("Macro 2", "Pitch du sample.", MACROS_DRUM),
            cmd("Macro 4", "Durée de l'enveloppe de déclin.", MACROS_DRUM),
            cmd("Macro 6", "Distorsion.", MACROS_DRUM),
            cmd("Macro 8", "EQ.", MACROS_DRUM),
        ),
        steps = listOf(
            "Lance un kick 4/4 sur Drum 1.",
            "Baisse la Macro 2 pour un kick plus grave.",
            "Raccourcis le déclin avec la Macro 4 pour un kick sec, puis allonge-le.",
            "Monte la Macro 6 pour saturer le kick.",
            "Ajuste la Macro 8 pour garder le kick lisible.",
        ),
        expected = "Un kick grave, court et saturé, fait avec les seules Macros de la piste.",
        exerciseTitle = "Le kick du style",
        exercise = listOf(
            "Crée deux versions du même kick : une sèche et une saturée et longue.",
            "Sauvegarde chaque version dans un projet différent.",
        ),
        success = "Deux kicks clairement différents à partir du même sample.",
        style = "Hardtek : déclin court et distorsion marquée sur Drum 1.",
        mistakes = listOf(
            "Tourner les Macros impaires sur une piste Drum : elles n'ont aucun effet (p. 64).",
            "Juger un réglage sur un seul sample : l'effet varie beaucoup d'un sample à l'autre (p. 64).",
        ),
        quiz = listOf(
            qz("Quelles Macros sont actives sur une piste Drum ?",
                listOf("1, 3, 5 et 7", "2, 4, 6 et 8", "Toutes"), 1,
                "Seulement les Macros paires.", MACROS_DRUM),
            qz("Quelle Macro règle la distorsion d'une piste Drum ?",
                listOf("Macro 2", "Macro 6", "Macro 8"), 1,
                "2 Pitch, 4 déclin, 6 distorsion, 8 EQ.", MACROS_DRUM),
            qz("Quelle Macro raccourcit un kick ?",
                listOf("Macro 4", "Macro 6", "Macro 8"), 0,
                "La Macro 4 règle la durée du déclin.", MACROS_DRUM),
            qz("Les fonctions des Macros de batterie changent-elles selon le sample ?",
                listOf("Oui", "Non, elles sont fixes ; seul le résultat sonore varie", "Seulement après un Sample Flip"), 1,
                "Fonctions fixes, résultat très dépendant du sample.", MACROS_DRUM),
        ),
    )

    private val m8l3 = ctLesson(
        M8, 3, "Master Filter et dynamique de jeu", listOf(FILTRE, DESSUS, VEL_FIXE), 15,
        photo = listOf("ct-filter", "ct-volume", "ct-velocity", "ct-shift"),
        objective = "Utiliser le Master Filter pour des transitions et choisir entre jeu dynamique et vélocité fixe.",
        explanation = "Le Master Filter traite tout le son de sortie, entrées audio comprises. Au centre (cran), aucun filtrage et LED blanche faible. " +
            "Vers la gauche : passe-bas, les aigus disparaissent et le son s'étouffe. Vers la droite : passe-haut, le kick et les basses disparaissent. " +
            "La LED devient bleu pâle et s'intensifie. Côté pads, la vélocité suit ta force de frappe ; Shift + Velocity la fixe à 96 " +
            "pour un jeu régulier, utile en live sur une batterie.",
        controls = listOf(
            cmd("Master Filter", "Centre : rien ; gauche : passe-bas ; droite : passe-haut.", FILTRE),
            cmd("Master Volume", "Niveau global des sorties.", DESSUS),
            cmd("Shift + Velocity", "Vélocité fixe à 96, ou variable.", VEL_FIXE),
        ),
        steps = listOf(
            "Lance un pattern complet.",
            "Tourne le Master Filter vers la droite sur 4 temps : le kick disparaît.",
            "Ramène-le au cran central d'un coup : le kick revient.",
            "Tourne vers la gauche pour étouffer le morceau, puis rouvre lentement.",
            "Shift + Velocity : vérifie la couleur de la touche (vert = fixe), joue les pads fort et doucement.",
        ),
        expected = "Tu fais disparaître et revenir le kick au filtre, au temps près.",
        exerciseTitle = "Le faux break",
        exercise = listOf(
            "Pendant 8 temps, filtre le morceau en passe-haut jusqu'à ne garder que les aigus.",
            "Ramène le filtre au centre pile sur le premier temps suivant.",
        ),
        success = "Le retour du kick tombe exactement sur le temps.",
        style = "Hardtek / tribe : passe-haut avant le retour du kick, la transition classique en free party.",
        mistakes = listOf(
            "Oublier de recentrer le filtre : tout le morceau reste filtré, le cran central sert de repère (p. 94).",
            "Croire que le Master Filter ne touche que les pistes internes : il traite aussi les entrées audio (p. 94).",
            "Penser que la vélocité fixe corrige les notes déjà enregistrées : elle ne change que les suivantes (p. 44).",
        ),
        quiz = listOf(
            qz("Que fait le Master Filter tourné vers la droite ?",
                listOf("Passe-bas : les aigus disparaissent", "Passe-haut : les graves disparaissent", "Il monte le volume"), 1,
                "À droite, passe-haut : le kick et les basses disparaissent.", FILTRE),
            qz("Que signale la LED du Master Filter blanche et faible ?",
                listOf("Filtre au centre, aucun filtrage", "Filtre au maximum", "Filtre en panne"), 0,
                "Au cran central, aucun filtrage n'est appliqué.", FILTRE),
            qz("Le Master Filter agit-il sur les entrées audio externes ?",
                listOf("Oui", "Non", "Seulement l'entrée 1"), 0,
                "Il traite la somme des pistes internes et des deux entrées audio.", FILTRE),
            qz("Que donne Shift + Velocity quand la touche Velocity est verte ?",
                listOf("Vélocité variable", "Vélocité fixe à 96", "Vélocité à 127"), 1,
                "Vert = vélocité fixe ; rouge = vélocité variable.", VEL_FIXE),
        ),
    )

    private val m8 = CourseModule(
        id = M8, number = 8, title = "Macros et contrôle des sons",
        summary = "Macros de synthé, Macros de batterie, Master Filter, vélocité.",
        goal = "Façonner chaque son en direct et piloter les transitions au filtre.",
        styleFocus = "Kicks saturés aux Macros de batterie, coups de filtre pour les transitions.",
        lessons = listOf(m8l1, m8l2, m8l3),
    )

    // ---------------------------------------------------------------------
    // Module 9 — Effets, sidechain et mixage
    // ---------------------------------------------------------------------
    private const val M9 = "ct-m9"

    private val m9l1 = ctLesson(
        M9, 1, "Le Mixer : niveaux, mute et panoramique", listOf(MIXER, PANO), 15,
        photo = listOf("ct-mixer", "ct-macros", "ct-arrows", "ct-clear"),
        objective = "Équilibrer les huit pistes, couper des pistes et les placer dans l'espace stéréo.",
        explanation = "Dans la vue Mixer, la rangée 1 contient un bouton mute par piste. Les Macros règlent le niveau de chaque piste, " +
            "dans l'ordre : Synth 1, Synth 2, MIDI 1 / entrée 1, MIDI 2 / entrée 2, Drum 1 à 4. Les Macros 3 et 4 règlent donc le niveau " +
            "des entrées audio. Toutes les pistes démarrent à 100 (sur 127). Une touche fléchée bascule les Macros sur le panoramique : " +
            "LED blanche au centre, bleue à gauche, rose à droite.",
        controls = listOf(
            cmd("Mixer", "Ouvre la vue de mixage (et les scènes en bas).", MIXER),
            cmd("Rangée 1 (vue Mixer)", "Mute de chaque piste.", MIXER),
            cmd("Macros 1 à 8 (vue Mixer)", "Niveaux ; Macros 3 et 4 = entrées audio 1 et 2.", MIXER),
            cmd("Touches fléchées (vue Mixer)", "Basculent entre niveaux et panoramique.", PANO),
            cmd("Clear + Macro vers la droite (panoramique)", "Recentre la piste.", PANO),
        ),
        steps = listOf(
            "Lance un pattern complet et ouvre le Mixer.",
            "Coupe puis rallume chaque piste avec la rangée 1 pour entendre son rôle.",
            "Règle les niveaux : kick et basse d'abord, puis le reste autour.",
            "Passe au panoramique avec la touche fléchée, place les charleys un peu à droite et une percussion à gauche.",
            "Recentre une piste avec Clear + rotation vers la droite, puis reviens aux niveaux.",
        ),
        expected = "Un mix équilibré, kick et basse au centre, éléments secondaires répartis.",
        exerciseTitle = "Le mix en 2 minutes",
        exercise = listOf(
            "Pars d'une démo, mets tous les niveaux au minimum.",
            "Remonte les pistes une par une en commençant par le kick, et place deux pistes en stéréo.",
        ),
        success = "Toutes les pistes audibles, aucune ne masque le kick.",
        mistakes = listOf(
            "Chercher un fader par piste : ce sont les Macros, dans l'ordre des pistes (p. 88).",
            "Oublier que le mute stoppe aussi les notes et les CC d'une piste MIDI (p. 88).",
            "Rester en mode panoramique sans le savoir : regarde quelle touche fléchée est allumée (p. 89).",
        ),
        quiz = listOf(
            qz("Quelles Macros règlent le niveau des entrées audio 1 et 2 dans la vue Mixer ?",
                listOf("1 et 2", "3 et 4", "7 et 8"), 1,
                "Les Macros des pistes MIDI règlent aussi les entrées audio.", MIXER),
            qz("Quel est le niveau par défaut des pistes ?",
                listOf("64", "100", "127"), 1,
                "100, sur une plage de 0 à 127.", MIXER),
            qz("De quelle couleur est la LED d'une piste placée à droite ?",
                listOf("Bleue", "Rose", "Blanche"), 1,
                "Blanche au centre, bleue à gauche, rose à droite.", PANO),
            qz("Où se trouvent les mutes dans la vue Mixer ?",
                listOf("Rangée 1", "Rangée 3", "Rangée 4"), 0,
                "La rangée 1 ; les deux rangées du bas sont les scènes.", MIXER),
        ),
    )

    private val m9l2 = ctLesson(
        M9, 2, "Réverbe et délai", listOf(FX, REVERB, DELAI), 15,
        photo = listOf("ct-fx", "ct-macros"),
        objective = "Choisir une réverbe et un délai et doser leur envoi piste par piste.",
        explanation = "La vue FX propose 16 délais (rangées 1-2, pads pêche) et 8 réverbes (rangée 3, pads crème). " +
            "Une seule réverbe et un seul délai servent pour tout le projet ; chaque piste dose son envoi avec sa Macro, dans le même ordre que le Mixer. " +
            "Les Macros règlent l'envoi de l'effet dont tu as touché un preset en dernier. Les délais sont calés sur le tempo, " +
            "du slapback au 4th Triplets Ping Pong Wide ; les réverbes vont de la Small Chamber au Large Hall à réflexions longues.",
        controls = listOf(
            cmd("FX", "Ouvre la vue des effets.", FX),
            cmd("Pads 1 à 16 (vue FX)", "16 presets de délai, calés sur le tempo.", DELAI),
            cmd("Pads 17 à 24 (vue FX)", "8 presets de réverbe.", REVERB),
            cmd("Macros 1 à 8 (vue FX)", "Envoi de chaque piste vers le dernier effet choisi.", DELAI),
        ),
        steps = listOf(
            "Ouvre FX et touche le pad 17 (Small Chamber), puis le 22 (Large Hall).",
            "Monte la Macro 6 (Drum 2) : la snare prend la réverbe.",
            "Touche le délai 12 (8th Ping Pong) : les Macros règlent maintenant le délai.",
            "Monte l'envoi de Synth 2 (Macro 2) pour un écho stéréo sur le lead.",
            "Garde le kick (Macro 5) sans effet pour qu'il reste net.",
        ),
        expected = "Snare avec de l'espace, lead avec un écho stéréo, kick sec.",
        exerciseTitle = "Espace et profondeur",
        exercise = listOf(
            "Essaie trois réverbes sur la snare et garde la plus adaptée au tempo.",
            "Ajoute un délai en triolets sur une percussion.",
        ),
        success = "Le kick reste sec, les effets s'entendent sans brouiller le rythme.",
        style = "Mentalcore : délai ping-pong sur le lead pour les passages planants.",
        mistakes = listOf(
            "Croire qu'on peut mettre une réverbe différente par piste : un seul preset est actif pour tout le projet (p. 91).",
            "Monter une Macro en pensant régler la réverbe alors que le dernier pad touché était un délai (p. 91).",
            "Mettre de la réverbe sur le kick : il perd son impact.",
        ),
        quiz = listOf(
            qz("Combien de presets de délai propose la vue FX ?",
                listOf("8", "16", "32"), 1,
                "16 délais et 8 réverbes.", FX),
            qz("Peut-on utiliser deux réverbes différentes sur deux pistes ?",
                listOf("Oui", "Non, un seul preset de réverbe à la fois", "Seulement sur les synthés"), 1,
                "On ne dose que l'envoi de chaque piste.", REVERB),
            qz("Quel effet les Macros règlent-elles dans la vue FX ?",
                listOf("Toujours la réverbe", "L'effet du dernier preset touché", "Les deux en même temps"), 1,
                "Elles suivent le dernier pad d'effet sur lequel tu as appuyé.", DELAI),
            qz("Le temps des délais est-il lié au tempo ?",
                listOf("Oui", "Non", "Seulement en Ping Pong"), 0,
                "Tous les délais sont associés au tempo.", FX),
        ),
    )

    private val m9l3 = ctLesson(
        M9, 3, "Sidechain et compresseur global", listOf(SIDECHAIN, COMPRESSEUR), 20,
        photo = listOf("ct-fx", "ct-shift"),
        objective = "Faire « pomper » synthés et entrées audio au rythme du kick, et connaître le compresseur global.",
        explanation = "La vue Side Chain (Shift + FX) fait baisser le niveau d'un synthé, ou d'une entrée audio via les pistes MIDI, à chaque frappe d'une piste Drum. " +
            "Rangées 3 et 4 : pad 1 = OFF, pads 2 à 8 = 7 presets, pour Synth 1 et Synth 2. Pads 5 à 8 de la rangée du haut : choix de la piste Drum qui déclenche. " +
            "Les touches fléchées passent à la page des pistes MIDI. Le ducking continue même si la piste source est à 0 dans le Mixer, mais s'arrête si elle est mutée. " +
            "Un compresseur global traite toutes les sorties ; il se coupe ou s'active dans la vue Setup avancée avec la touche FX.",
        controls = listOf(
            cmd("Shift + FX", "Vue Side Chain (ou FX une seconde fois).", SIDECHAIN),
            cmd("Rangées 3-4, pads 1 à 8", "OFF et 7 presets pour chaque synthé (ou entrée).", SIDECHAIN),
            cmd("Rangée du haut, pads 5 à 8", "Piste Drum source du sidechain.", SIDECHAIN),
            cmd("Shift à l'allumage, puis FX", "Compresseur global marche/arrêt (vue Setup avancée).", COMPRESSEUR),
        ),
        steps = listOf(
            "Sur Synth 2, une note de gate 16 qui tient tout le pattern ; kick 4/4 sur Drum 1.",
            "Shift + FX : sur la rangée de Synth 2, touche un preset (pads 2 à 8), puis Drum 1 comme source (rangée du haut).",
            "Essaie les 7 presets et écoute la nappe pomper.",
            "Baisse Drum 1 à 0 dans le Mixer : le pompage continue sans kick audible.",
            "Remets Drum 1, puis désactive le sidechain avec le pad OFF.",
        ),
        expected = "La nappe s'efface à chaque kick puis remonte : l'effet de pompage.",
        exerciseTitle = "Le pompage invisible",
        exercise = listOf(
            "Crée un sidechain déclenché par une piste Drum à niveau 0 qui joue des noires.",
            "Applique-le à la basse pour la rythmer sans ajouter de son.",
        ),
        success = "La basse pompe régulièrement alors que la piste source est inaudible.",
        style = "Hardtek : basse qui pompe sous le kick pour un bas du spectre propre.",
        mistakes = listOf(
            "Muter la piste source : le sidechain s'arrête ; baisse plutôt son niveau à 0 (p. 94).",
            "Chercher le sidechain des entrées audio sur la page des synthés : passe à la page MIDI avec les touches fléchées (p. 94).",
            "Chercher le compresseur dans la vue FX : il se règle dans la vue Setup avancée (p. 106).",
        ),
        quiz = listOf(
            qz("Comment ouvrir la vue Side Chain ?",
                listOf("Shift + FX", "Shift + Mixer", "Shift + Gate"), 0,
                "Ou FX une seconde fois si tu es déjà dans la vue FX.", SIDECHAIN),
            qz("Combien de presets de sidechain sont disponibles ?",
                listOf("4", "7", "16"), 1,
                "Sept presets, plus le pad OFF.", SIDECHAIN),
            qz("Si la piste source est mutée dans le Mixer, que devient le sidechain ?",
                listOf("Il continue", "Il est désactivé", "Il passe au maximum"), 1,
                "Mutée, elle ne déclenche plus ; à niveau 0, elle continue à déclencher.", SIDECHAIN),
            qz("Où active-t-on ou coupe-t-on le compresseur global ?",
                listOf("Dans la vue FX", "Dans la vue Setup avancée, touche FX", "Dans Components seulement"), 1,
                "FX vert vif = compresseur actif.", COMPRESSEUR),
        ),
    )

    private val m9 = CourseModule(
        id = M9, number = 9, title = "Effets, sidechain et mixage",
        summary = "Mixer, panoramique, réverbe, délai, sidechain, compresseur.",
        goal = "Obtenir un mix lisible et vivant, prêt à jouer fort.",
        styleFocus = "Kick sec et devant, basse qui pompe, effets réservés aux éléments aigus.",
        lessons = listOf(m9l1, m9l2, m9l3),
    )

    val modules: List<CourseModule> = listOf(m7, m8, m9)
}
