package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** Circuit Tracks — modules 1 à 3 : interface, pistes, synthés. */
internal object CircuitTracksModulesA {

    // Références du guide (pages imprimées)
    private val GLOSSAIRE = ct("Présentation du matériel › Glossaire", "12–15")
    private val DESSUS = ct("Présentation du matériel › Vue du dessus", "16–18")
    private val ARRIERE = ct("Présentation du matériel › Vue arrière", "19")
    private val ALIM = ct("Introduction › Alimentation", "10–11")
    private val ALLUMER = ct("Principes élémentaires › Allumer l'appareil", "20–21")
    private val PREMIERS_PAS = ct("Principes élémentaires › Premiers pas", "22")
    private val CHARGER = ct("Principes élémentaires › Le chargement et la sauvegarde", "23–24")
    private val PROJETS = ct("Projets › Changer, supprimer, sauvegarder", "95–96")
    private val CARAC = ct("Introduction › Caractéristiques principales", "6–7")
    private val ZERO = ct("Principes élémentaires › Démarrer de zéro", "25–27")
    private val MIDI_INTRO = ct("Les pistes MIDI › Introduction et modèles", "58")
    private val RYTHMES = ct("Les rythmes", "61")
    private val PATCHES = ct("Les synthétiseurs › Choisir les patches", "34–35")
    private val SAMPLES = ct("Les rythmes › Choisir des échantillons sonores", "63")
    private val PATTERNS = ct("Les patterns", "73")
    private val SYNTHE = ct("Les synthétiseurs › Utiliser un synthétiseur", "28–29")
    private val ETENDUE = ct("Les synthétiseurs › La vue Note étendue", "29–30")
    private val GAMMES = ct("Les synthétiseurs › Scales", "31–33")
    private val FONDAMENTALE = ct("Les synthétiseurs › La note fondamentale", "33–34")
    private val TEMPS_REEL = ct("Les synthétiseurs › Enregistrer un pattern de synthé", "36–37")
    private val CLAVIER_EXT = ct("Les synthétiseurs › Enregistrer à partir d'un contrôleur externe", "38–39")

    // ---------------------------------------------------------------------
    // Module 1 — Interface et navigation
    // ---------------------------------------------------------------------
    private const val M1 = "ct-m1"

    private val m1l1 = ctLesson(
        M1, 1, "La façade et le principe des vues", listOf(DESSUS, GLOSSAIRE), 15,
        photo = listOf("ct-grid", "ct-filter", "ct-macros", "ct-volume", "ct-tracks", "ct-note", "ct-settings", "ct-shift"),
        objective = "Situer chaque touche de la façade et comprendre que la grille de 32 pads change de rôle selon la vue choisie.",
        explanation = "La Circuit Tracks n'a pas d'écran : tout passe par la grille de 4 × 8 pads RVB. Chaque touche ouvre une « vue », " +
            "c'est-à-dire une façon d'utiliser la grille : jouer des notes, régler la vélocité, choisir un pattern, mixer… " +
            "Les huit potentiomètres Macro changent eux aussi de fonction selon la vue. Seuls le Master Filter et le Master Volume " +
            "gardent toujours le même rôle. Beaucoup de touches ont une seconde fonction, écrite en petit, accessible avec Shift.",
        controls = listOf(
            cmd("Grille de 32 pads", "Joue, affiche et édite selon la vue ouverte.", DESSUS),
            cmd("Master Filter", "Filtre passe-bas / passe-haut sur tout le mixage, toujours actif.", DESSUS),
            cmd("Macros 1 à 8", "Encodeurs dont la fonction dépend de la vue.", DESSUS),
            cmd("Master Volume", "Niveau global des sorties audio.", DESSUS),
            cmd("Touches de pistes (Synth 1-2, MIDI 1-2, Drum 1-4)", "Choisissent la piste et ouvrent sa vue Note.", DESSUS),
            cmd("Note, Velocity, Gate, Pattern Settings (Probability en Shift)", "Les touches Step : vues d'édition des pas.", DESSUS),
            cmd("Scales, Patterns, Mixer, FX, Preset", "Vues gammes, patterns, mixage, effets et sons.", DESSUS),
            cmd("Record, Play, Tempo/Swing, Clear, Duplicate, Save, Projects, Shift", "Transport, tempo, édition et mémoire.", DESSUS),
        ),
        steps = listOf(
            "Pose la machine éteinte devant toi, grille vers toi, et repère la grille de 32 pads.",
            "Repère le grand potentiomètre Master Filter, puis les huit Macros et le Master Volume.",
            "Trouve les huit touches de pistes, puis les touches Step : Note, Velocity, Gate et Pattern Settings.",
            "Lis la petite inscription au-dessus de Pattern Settings : c'est Probability, accessible avec Shift.",
            "Repère Scales, Patterns, Mixer, FX et Preset : chacune ouvre une vue différente.",
            "Termine par Record et Play, Tempo/Swing, Clear, Duplicate, Save, Projects et Shift.",
            "Repère les deux touches fléchées (touches Octave) : selon la vue, elles changent d'octave ou de page.",
        ),
        expected = "Tu sais nommer chaque touche et dire si elle sert à jouer, à éditer, à naviguer ou à mémoriser.",
        exerciseTitle = "Le tour de façade",
        exercise = listOf(
            "Fais lire la liste des commandes à quelqu'un, ou lis-la toi-même en cachant les rôles.",
            "Pour chaque commande, touche-la sur la machine en moins de 3 secondes.",
            "Dis à voix haute si elle reste fixe (Master Filter, Master Volume) ou si elle dépend de la vue.",
        ),
        success = "Toutes les commandes trouvées en moins de 3 secondes, sans erreur sur leur rôle.",
        mistakes = listOf(
            "Chercher un écran : toutes les informations s'affichent sur la grille de pads.",
            "Croire que les légendes sous les Macros sont valables partout : elles décrivent la fonction sur les patches de synthé d'origine (p. 16).",
            "Oublier les secondes fonctions écrites en petit, accessibles avec Shift.",
        ),
        quiz = listOf(
            qz("Quelle commande garde toujours la même fonction, quelle que soit la vue ?",
                listOf("La Macro 1", "Le Master Filter", "La touche Preset"), 1,
                "Le Master Filter agit sur tout le mixage et il est toujours actif.", DESSUS),
            qz("Comment ouvre-t-on la vue Probability ?",
                listOf("Shift + Pattern Settings", "Shift + Gate", "Shift + Velocity"), 0,
                "Probability est la fonction secondaire de la touche Pattern Settings.", DESSUS),
            qz("Que se passe-t-il avec une pression longue sur une touche de vue ?",
                listOf("La vue s'ouvre définitivement", "La vue s'affiche tant que la touche est tenue", "La touche est verrouillée"), 1,
                "Pression longue = vue momentanée ; au relâchement, la vue précédente revient. Pression courte = la vue reste.", DESSUS),
            qz("Combien de pads compte la grille ?",
                listOf("16", "32", "64"), 1,
                "La grille compte 32 pads (4 × 8) sensibles à la vélocité.", DESSUS),
        ),
    )

    private val m1l2 = ctLesson(
        M1, 2, "Allumer, écouter une démo, lire les couleurs", listOf(ALIM, ARRIERE, ALLUMER, PREMIERS_PAS), 15,
        photo = listOf("ct-play", "ct-tracks", "ct-grid"),
        objective = "Allumer la machine, lancer un projet de démo et reconnaître chaque piste à sa couleur.",
        explanation = "La Circuit Tracks fonctionne sur batterie (jusqu'à 4 heures), sur secteur ou sur un port USB d'ordinateur. " +
            "Le bouton d'alimentation se tient environ une seconde pour éviter les erreurs. Au démarrage, la grille montre une animation " +
            "pendant le chargement du Pack. 16 projets de démo sont déjà en mémoire : Play les lance. " +
            "Dans la vue Note, les deux rangées du haut montrent les pas du pattern et les deux du bas servent à jouer. " +
            "Chaque piste a sa couleur, et on la retrouve dans presque toutes les vues.",
        controls = listOf(
            cmd("Bouton d'alimentation (arrière)", "Pression d'environ une seconde pour allumer ou éteindre ; vert pendant la charge.", ARRIERE),
            cmd("Port USB-C", "Alimentation, charge et MIDI ; il ne transmet pas d'audio.", ARRIERE),
            cmd("Sorties L/Mono et R, prise casque", "Sorties audio ; les sorties principales restent actives avec un casque.", ARRIERE),
            cmd("Play", "Lance ou arrête la lecture.", PREMIERS_PAS),
            cmd("Synth 1, Synth 2, Drum 1", "Affichent la vue Note de la piste.", PREMIERS_PAS),
        ),
        steps = listOf(
            "Branche le casque ou les sorties L/R vers ton système d'écoute, volume bas.",
            "Tiens le bouton d'alimentation environ une seconde et regarde l'animation de chargement.",
            "Appuie sur Play : la première démo démarre.",
            "Appuie sur Synth 1 : en haut, la tête de lecture blanche avance sur les pas ; en bas, la zone de jeu.",
            "Passe sur Synth 2 puis Drum 1 et compare les couleurs : violet, vert pâle, orange.",
            "Appuie de nouveau sur Play pour arrêter.",
        ),
        expected = "La démo joue, et tu reconnais la piste affichée à la couleur de ses pads.",
        exerciseTitle = "Les couleurs à l'aveugle",
        exercise = listOf(
            "Lance une démo et appuie sur une touche de piste au hasard sans la regarder.",
            "Regarde seulement la grille et annonce la piste d'après sa couleur.",
            "Vérifie avec le tableau : Synth 1 violet, Synth 2 vert pâle, MIDI 1 bleu, MIDI 2 rose, Drum 1 orange, Drum 2 jaune, Drum 3 mauve, Drum 4 turquoise.",
        ),
        success = "Huit pistes reconnues à leur couleur sans erreur.",
        mistakes = listOf(
            "Appuyer brièvement sur le bouton d'alimentation : il faut le tenir environ une seconde (p. 19).",
            "Attendre du son par l'USB : le port USB ne transmet pas d'audio (p. 19).",
            "S'inquiéter du symbole de batterie faible : il apparaît sous 15 % de charge, il suffit de brancher l'adaptateur (p. 11).",
        ),
        quiz = listOf(
            qz("Quelle autonomie maximale annonce le guide sur batterie ?",
                listOf("1 heure", "4 heures", "10 heures"), 1,
                "La batterie intégrée offre jusqu'à 4 heures, selon son état.", ALIM),
            qz("Que montrent les deux rangées du haut dans la vue Note d'un synthé ?",
                listOf("Le clavier", "Les pas du pattern", "Le mixeur"), 1,
                "En haut les pas du pattern, en bas la zone de jeu.", PREMIERS_PAS),
            qz("Quelle est la couleur de la piste Drum 1 ?",
                listOf("Orange", "Turquoise", "Violet"), 0,
                "Drum 1 est orange ; Synth 1 est violet et Drum 4 turquoise.", PREMIERS_PAS),
            qz("Que signale le bouton d'alimentation allumé en vert ?",
                listOf("Le Save Lock est actif", "La batterie est en charge", "Une carte microSD est détectée"), 1,
                "La LED du bouton s'allume en vert pendant la charge de la batterie.", ALIM),
        ),
    )

    private val m1l3 = ctLesson(
        M1, 3, "Charger et sauvegarder un projet", listOf(CHARGER, PROJETS), 20,
        photo = listOf("ct-projects", "ct-save", "ct-clear", "ct-arrows", "ct-shift"),
        objective = "Ouvrir n'importe quel projet, sauvegarder ton travail sans écraser l'original et effacer un projet.",
        explanation = "Un projet contient tout ce qu'il faut pour rejouer un morceau : patterns, sons, automation… La machine a 64 emplacements, " +
            "sur deux pages de 32 dans la vue Projects. Save pressé deux fois sauvegarde dans le dernier emplacement utilisé, donc écrase la version " +
            "précédente. Pour garder l'original, passe par la vue Projects : Save, puis choisis un autre pad. " +
            "En lecture, un projet choisi attend la fin du pattern en cours ; avec Shift, il démarre tout de suite.",
        controls = listOf(
            cmd("Projects", "Ouvre la vue des 64 emplacements de projets.", CHARGER),
            cmd("Touches fléchées", "Changent de page (projets 1–32 / 33–64).", CHARGER),
            cmd("Save", "Première pression : clignote ; seconde : sauvegarde.", CHARGER),
            cmd("Clear (dans la vue Projects)", "Efface le projet sélectionné, et lui seul.", PROJETS),
            cmd("Shift + pad de projet", "Lance le projet immédiatement pendant la lecture.", CHARGER),
        ),
        steps = listOf(
            "Appuie sur Projects : blanc = projet sélectionné, bleu vif = emplacement occupé, bleu pâle = vide.",
            "Choisis une autre démo et appuie sur Play pour l'écouter.",
            "Pendant la lecture, touche un autre projet : il démarre à la fin du pattern en cours.",
            "Recommence en tenant Shift : le changement est immédiat.",
            "Arrête la lecture. Dans la vue Projects, appuie sur Save puis sur un pad vide : il clignote en vert, c'est sauvegardé.",
            "Pour effacer ce nouveau projet, tiens Clear et appuie sur son pad (seul le projet sélectionné peut être effacé).",
        ),
        expected = "Tu as une copie d'une démo dans un emplacement libre, et l'original n'a pas changé.",
        exerciseTitle = "Copie de sécurité",
        exercise = listOf(
            "Ouvre la démo 1, modifie un détail (un pas de batterie en plus).",
            "Sauvegarde dans un emplacement vide de la page 2 en passant par la vue Projects.",
            "Recharge la démo 1 et vérifie qu'elle n'a pas changé, puis recharge ta copie.",
        ),
        success = "Deux projets différents : l'original intact et ta copie modifiée.",
        mistakes = listOf(
            "Appuyer deux fois sur Save hors de la vue Projects en pensant créer une copie : cela remplace le dernier projet utilisé (p. 24).",
            "Vouloir effacer un projet non sélectionné : Clear n'efface que le projet sélectionné, par sécurité (p. 95).",
            "S'étonner du tempo : un projet lancé à l'arrêt prend son tempo sauvegardé, lancé en lecture il garde le tempo en cours (p. 23).",
        ),
        quiz = listOf(
            qz("Combien d'emplacements de projets propose la mémoire interne ?",
                listOf("16", "32", "64"), 2,
                "64 emplacements, sur deux pages de 32.", CHARGER),
            qz("Que fait Save pressé deux fois hors de la vue Projects ?",
                listOf("Crée un nouveau projet", "Sauvegarde dans le dernier emplacement utilisé", "Exporte sur microSD"), 1,
                "La version précédente dans ce dernier emplacement est remplacée.", CHARGER),
            qz("Pendant la lecture, comment faire démarrer un autre projet tout de suite ?",
                listOf("Tenir Shift en choisissant le projet", "Appuyer deux fois sur Play", "Tenir Clear"), 0,
                "Sans Shift, le projet attend la fin du pattern en cours.", CHARGER),
            qz("Un projet lancé pendant la lecture joue à quel tempo ?",
                listOf("Au tempo sauvegardé avec lui", "Au tempo actuel", "À 120 BPM"), 1,
                "En lecture, le tempo actuel est conservé : pratique pour enchaîner les projets.", CHARGER),
        ),
    )

    private val m1 = CourseModule(
        id = M1, number = 1, title = "Interface et navigation",
        summary = "Façade, vues, alimentation, projets.",
        goal = "Te repérer sans écran, ouvrir et sauvegarder un projet en toute sécurité.",
        styleFocus = "En live, savoir où est chaque vue évite les silences et les fausses manœuvres.",
        lessons = listOf(m1l1, m1l2, m1l3),
    )

    // ---------------------------------------------------------------------
    // Module 2 — Organisation des pistes
    // ---------------------------------------------------------------------
    private const val M2 = "ct-m2"

    private val m2l1 = ctLesson(
        M2, 1, "Les huit pistes et leurs rôles", listOf(CARAC, GLOSSAIRE, MIDI_INTRO, RYTHMES, PATTERNS), 15,
        photo = listOf("ct-tracks", "ct-note"),
        objective = "Savoir ce que fait chaque type de piste et choisir la bonne piste pour chaque élément d'un morceau.",
        explanation = "Un projet compte huit pistes : deux synthés numériques polyphoniques (6 voix), deux pistes MIDI qui pilotent des machines " +
            "externes, et quatre pistes de batterie à samples. Chaque piste a sa propre mémoire de huit patterns. " +
            "Dans les projets d'origine, Synth 1 a des sons monophoniques, pratiques pour la basse, et Synth 2 des sons polyphoniques " +
            "pour les accords. Les pistes MIDI ne produisent aucun son interne : elles envoient des notes et des CC à un autre appareil.",
        controls = listOf(
            cmd("Synth 1, Synth 2", "Pistes de synthé interne, 6 voix de polyphonie.", CARAC),
            cmd("MIDI 1, MIDI 2", "Pistes qui pilotent des appareils externes en MIDI.", MIDI_INTRO),
            cmd("Drum 1 à Drum 4", "Pistes de batterie à samples.", RYTHMES),
            cmd("Touche de piste", "Une pression ouvre toujours la vue Note de la piste.", GLOSSAIRE),
        ),
        steps = listOf(
            "Lance une démo et appuie sur chaque touche de piste l'une après l'autre.",
            "Sur Synth 1 et Synth 2, joue quelques pads du bas : ce sont des claviers.",
            "Sur Drum 1 à Drum 4, joue les pads du bas : chacun déclenche un sample.",
            "Sur MIDI 1 : sans appareil externe branché, tu n'entends rien. C'est normal.",
            "Décide pour un futur morceau : basse sur Synth 1, nappe ou accords sur Synth 2, kick sur Drum 1, snare sur Drum 2.",
        ),
        expected = "Tu sais dire pour chaque piste si elle joue un synthé interne, des samples ou une machine externe.",
        exerciseTitle = "Plan de pistes",
        exercise = listOf(
            "Choisis un style (hardtek, tribe, acidcore ou mentalcore).",
            "Écris un plan : quel élément sur quelle piste (kick, snare, charley, percussion, basse, lead).",
            "Vérifie qu'aucun son de batterie n'est sur une piste de synthé et inversement.",
        ),
        success = "Un plan complet de 6 à 8 pistes, cohérent avec leurs rôles.",
        style = "Hardtek / tribe : Drum 1 kick, Synth 1 basse roulante, Synth 2 lead ou nappe.",
        mistakes = listOf(
            "Chercher du son sur une piste MIDI sans machine branchée : elle ne pilote aucun moteur interne (p. 58).",
            "Penser qu'un pattern est commun à toutes les pistes : chaque piste a ses huit patterns (p. 73).",
            "Confondre Drum 1–4 (les pistes) et les slots 1–16 d'un kit (les samples d'une page).",
        ),
        quiz = listOf(
            qz("Combien de pistes de batterie compte la Circuit Tracks ?",
                listOf("2", "4", "8"), 1,
                "Quatre pistes Drum, deux Synth et deux MIDI : huit pistes au total.", CARAC),
            qz("Combien de voix de polyphonie a chaque piste de synthé ?",
                listOf("4", "6", "8"), 1,
                "Chaque synthé offre 6 voix.", CARAC),
            qz("Que font les pistes MIDI ?",
                listOf("Jouent des samples internes", "Pilotent des équipements externes", "Enregistrent l'audio"), 1,
                "Elles n'ont pas de moteur interne : elles envoient notes et CC.", MIDI_INTRO),
            qz("Combien de patterns chaque piste peut-elle mémoriser dans un projet ?",
                listOf("4", "8", "16"), 1,
                "Huit patterns par piste.", PATTERNS),
        ),
    )

    private val m2l2 = ctLesson(
        M2, 2, "Premier beat de zéro", listOf(ZERO), 20,
        photo = listOf("ct-projects", "ct-tracks", "ct-grid", "ct-rec", "ct-play"),
        objective = "Partir d'un projet vide et programmer kick, snare et quelques notes de synthé.",
        explanation = "Dans un projet vide, un pattern fait 16 pas par défaut (on peut aller jusqu'à 32 sur chaque piste). " +
            "Sur une piste Drum, les deux rangées du haut sont les 16 pas, celles du bas les samples d'une page. Tu choisis un sample, " +
            "puis tu touches des pas : ils s'allument et se jouent. Une nouvelle pression enlève le pas. " +
            "Sur un synthé, Record allumé, tout ce que tu joues sur le clavier s'inscrit dans les pas.",
        controls = listOf(
            cmd("Projects + pad vide", "Ouvre un projet vierge.", ZERO),
            cmd("Pads de pas (rangées 1-2)", "Ajoutent ou retirent une frappe.", ZERO),
            cmd("Pads de samples (rangées 3-4)", "Choisissent le sample de la piste.", ZERO),
            cmd("Record", "Enregistre ce que tu joues sur le synthé.", ZERO),
        ),
        steps = listOf(
            "Projects, choisis un emplacement vide, puis Drum 1.",
            "Choisis un kick dans les slots 1 ou 2 du kit (rangées du bas).",
            "Appuie brièvement sur les pas 1, 5, 9 et 13, puis sur Play.",
            "Drum 2 : choisis une snare (slots 3 ou 4) et place-la sur les pas 5 et 13.",
            "Synth 1 : écoute le clavier en jouant les pads du bas sans enregistrer.",
            "Appuie sur Record, joue quelques notes en rythme, puis Record de nouveau pour arrêter.",
        ),
        expected = "Un pattern de 16 pas tourne : kick sur les temps, snare sur 2 et 4, et une courte ligne de synthé.",
        exerciseTitle = "Le squelette en 3 minutes",
        exercise = listOf(
            "Projet vide, chronomètre lancé.",
            "Kick sur 1-5-9-13, snare sur 5-13, une charley sur Drum 3 aux pas impairs.",
            "Ajoute une basse de 4 notes sur Synth 1 avec Record.",
        ),
        success = "Le pattern tourne en boucle en moins de 3 minutes, sans pas parasite.",
        style = "Hardtek : garde le kick sur les 4 temps, il porte tout le morceau.",
        mistakes = listOf(
            "Tenir un pad de pas trop longtemps : une pression longue n'a pas le même effet qu'une pression courte (p. 25).",
            "Oublier d'allumer Record sur le synthé : sans lui, les notes jouées ne sont pas enregistrées (p. 27).",
            "Croire qu'on doit arrêter la lecture pour ajouter ou retirer un pas de batterie : c'est possible pendant la lecture (p. 26).",
        ),
        quiz = listOf(
            qz("Quelle est la longueur par défaut d'un pattern ?",
                listOf("8 pas", "16 pas", "32 pas"), 1,
                "16 pas par défaut, extensible à 32 sur n'importe quelle piste.", ZERO),
            qz("Dans quels slots d'un kit trouve-t-on généralement les kicks ?",
                listOf("1 et 2", "3 et 4", "13 à 16"), 0,
                "Kicks en 1-2, snares en 3-4.", ZERO),
            qz("Comment retirer une frappe d'un pas ?",
                listOf("Clear + Play", "Appuyer de nouveau sur le pad du pas", "Shift + pad"), 1,
                "Les pads de pas fonctionnent comme des interrupteurs.", ZERO),
            qz("Sur combien d'octaves s'étend le clavier de la vue Note d'un synthé (hors gamme chromatique) ?",
                listOf("Une", "Deux", "Quatre"), 1,
                "Deux octaves ; les pads plus pâles sont les fondamentales.", ZERO),
        ),
    )

    private val m2l3 = ctLesson(
        M2, 3, "Choisir le son de chaque piste avec Preset", listOf(PATCHES, SAMPLES, MIDI_INTRO), 15,
        photo = listOf("ct-preset", "ct-arrows"),
        objective = "Ouvrir la vue Preset de n'importe quelle piste et changer de patch, de sample ou de modèle MIDI.",
        explanation = "La touche Preset ouvre une vue différente selon la piste : 128 patches pour un synthé (4 pages de 32), " +
            "64 samples pour une piste Drum (2 pages de 32), 8 modèles pour une piste MIDI. Le pad du son choisi est blanc. " +
            "Changer de son ne modifie le projet sauvegardé que si tu sauvegardes de nouveau.",
        controls = listOf(
            cmd("Preset", "Ouvre la vue des sons de la piste sélectionnée.", PATCHES),
            cmd("Touches fléchées", "Changent de page de presets.", PATCHES),
            cmd("Pad de preset", "Choisit le patch, le sample ou le modèle.", SAMPLES),
        ),
        steps = listOf(
            "Lance ton beat, sélectionne Synth 1 puis Preset.",
            "Essaie trois patches pendant la lecture ; change de page avec les touches fléchées.",
            "Sélectionne Drum 1 puis Preset : choisis un autre kick parmi les 64 samples.",
            "Sélectionne MIDI 1 puis Preset : la rangée du haut montre les 8 modèles.",
            "Recharge le projet sans sauvegarder : les sons d'origine reviennent.",
        ),
        expected = "Tu changes le son de n'importe quelle piste en quelques secondes, sans arrêter la musique.",
        exerciseTitle = "Trois ambiances, un pattern",
        exercise = listOf(
            "Garde le même pattern et crée trois versions en changeant seulement les presets de Synth 1 et Drum 1.",
            "Sauvegarde chaque version dans un emplacement différent.",
        ),
        success = "Trois projets au même rythme mais au son nettement différent.",
        mistakes = listOf(
            "Changer de patch pile sur une note : selon le moment, la transition peut ne pas être parfaitement fluide (p. 34).",
            "Croire que le nouveau son est sauvegardé automatiquement : il faut sauvegarder le projet (p. 34).",
            "Chercher 128 sons sur une piste Drum : elle en propose 64 (p. 63).",
        ),
        quiz = listOf(
            qz("Combien de patches propose une piste de synthé ?",
                listOf("64", "128", "256"), 1,
                "128 patches, répartis en 4 pages de 32.", PATCHES),
            qz("Combien de samples une piste Drum peut-elle utiliser ?",
                listOf("32", "64", "128"), 1,
                "64 samples, en 2 pages de 32 dans la vue Preset.", SAMPLES),
            qz("Que montre la vue Preset d'une piste MIDI ?",
                listOf("8 modèles MIDI", "128 patches", "16 canaux"), 0,
                "Huit modèles, qui définissent les CC envoyés par les Macros.", MIDI_INTRO),
            qz("Un changement de patch modifie-t-il le projet sauvegardé ?",
                listOf("Oui, immédiatement", "Non, sauf si tu sauvegardes", "Seulement à l'extinction"), 1,
                "Le patch sauvegardé reste tant que tu ne mémorises pas de nouveau le projet.", PATCHES),
        ),
    )

    private val m2 = CourseModule(
        id = M2, number = 2, title = "Organisation des pistes",
        summary = "Synthés, pistes MIDI, pistes Drum, premier beat, presets.",
        goal = "Répartir les éléments d'un morceau sur les huit pistes et choisir leurs sons.",
        styleFocus = "Une piste par rôle : kick, snare, charleys, percussions, basse et lead restent lisibles en live.",
        lessons = listOf(m2l1, m2l2, m2l3),
    )

    // ---------------------------------------------------------------------
    // Module 3 — Synthés et paramètres sonores
    // ---------------------------------------------------------------------
    private const val M3 = "ct-m3"

    private val m3l1 = ctLesson(
        M3, 1, "Jouer un synthé : vue Note, octaves, vue étendue", listOf(SYNTHE, ETENDUE, ZERO), 15,
        photo = listOf("ct-note", "ct-arrows", "ct-shift"),
        objective = "Jouer une mélodie sur la grille, changer d'octave et utiliser la vue Note étendue.",
        explanation = "Dans la vue Note, la rangée 3 est une octave au-dessus de la rangée 4. Pour monter, commence par les pads 25 à 32, " +
            "puis continue sur 18 à 24 : le pad 32 et le pad 17 sont la même note. Les touches fléchées décalent le clavier d'une octave ; " +
            "les deux ensemble reviennent à l'octave par défaut (fondamentale Do 4). Shift + Note donne la vue étendue : les pas disparaissent " +
            "et la grille entière devient un clavier de quatre octaves.",
        controls = listOf(
            cmd("Synth 1 / Synth 2 + Note", "Ouvre la vue Note du synthé.", SYNTHE),
            cmd("Touches fléchées (Octave)", "Montent ou descendent le clavier d'une octave ; les deux ensemble : octave par défaut.", ZERO),
            cmd("Shift + Note", "Vue Note étendue (ou Note une seconde fois).", ETENDUE),
        ),
        steps = listOf(
            "Synth 1, Note. Joue les pads 25 à 32 puis 18 à 24 : la gamme monte sur deux octaves.",
            "Repère les pads plus pâles : ce sont les fondamentales.",
            "Appuie sur la touche fléchée du haut, rejoue, puis sur celle du bas deux fois.",
            "Appuie sur les deux touches fléchées ensemble : retour à l'octave par défaut.",
            "Shift + Note : joue sur les quatre rangées. Appuie de nouveau sur Note pour revenir aux pas.",
        ),
        expected = "Tu joues une ligne qui monte sur deux octaves et tu sais revenir à l'octave par défaut.",
        exerciseTitle = "La gamme sur quatre octaves",
        exercise = listOf(
            "En vue étendue, joue toutes les notes de la plus grave à la plus aiguë.",
            "Reviens en vue Note et rejoue la même ligne en utilisant les touches Octave.",
        ),
        success = "La gamme jouée sans fausse note dans les deux vues.",
        mistakes = listOf(
            "Jouer la rangée 3 avant la rangée 4 en pensant monter : la rangée 3 est l'octave du dessus (p. 29).",
            "Chercher les pas en vue étendue : ils sont remplacés par le clavier (p. 29).",
            "Croire que les touches Octave transposent un pattern déjà enregistré : il faut Shift + touche fléchée (p. 37).",
        ),
        quiz = listOf(
            qz("Quelle note correspond à la fondamentale de l'octave par défaut ?",
                listOf("Do 3", "Do 4", "La 4"), 1,
                "La fondamentale par défaut est le Do 4.", ZERO),
            qz("Comment revenir à l'octave par défaut ?",
                listOf("Shift + Note", "Les deux touches fléchées ensemble", "Clear + Note"), 1,
                "Appuyer sur les deux touches en même temps rétablit l'octave par défaut.", ZERO),
            qz("Que fait la vue Note étendue ?",
                listOf("Ajoute 16 pas", "Transforme toute la grille en clavier", "Affiche la vélocité"), 1,
                "Les rangées du haut deviennent deux octaves de plus.", ETENDUE),
            qz("Quel pad joue la même note que le pad 17 ?",
                listOf("Le pad 24", "Le pad 25", "Le pad 32"), 2,
                "La note la plus aiguë de l'octave basse (pad 32) est la plus grave de l'octave haute (pad 17).", SYNTHE),
        ),
    )

    private val m3l2 = ctLesson(
        M3, 2, "Gammes et note fondamentale", listOf(GAMMES, FONDAMENTALE, TEMPS_REEL, CLAVIER_EXT), 20,
        photo = listOf("ct-scales", "ct-note"),
        objective = "Choisir une gamme et une tonalité pour jouer sans fausse note, et transposer un morceau en direct.",
        explanation = "La vue Scales règle le clavier des synthés et des pistes MIDI. En bas, 16 gammes (pads 17 à 32) : mineure naturelle, " +
            "majeure, dorienne, phrygienne… jusqu'à la chromatique (pad 32), qui donne les 12 notes sur une seule octave. " +
            "En haut, une octave de Do à Si pour choisir la fondamentale. Changer la gamme ou la fondamentale en lecture transpose " +
            "ce qui est enregistré, sans rien détruire : revenir à l'ancien réglage rend les notes d'origine. La gamme est sauvegardée avec le projet.",
        controls = listOf(
            cmd("Scales", "Ouvre la vue des gammes et de la fondamentale.", GAMMES),
            cmd("Pads 17 à 32 (vue Scales)", "Choix parmi 16 gammes ; pad 32 = chromatique.", GAMMES),
            cmd("Rangées 1-2 (vue Scales)", "Choix de la fondamentale, de Do à Si.", FONDAMENTALE),
        ),
        steps = listOf(
            "Enregistre une courte mélodie sur Synth 2.",
            "Scales : pendant la lecture, touche successivement Mineure naturelle (17), Phrygienne (20) et Hongroise mineure (27).",
            "Écoute comment l'ambiance change sans réenregistrer.",
            "Dans les rangées du haut, choisis La comme fondamentale : tout le morceau est transposé.",
            "Reviens sur Do et sur la gamme de départ : la mélodie d'origine revient.",
            "Appuie sur Note pour quitter la vue Scales.",
        ),
        expected = "La même mélodie joue dans plusieurs gammes et tonalités, sans réenregistrement.",
        exerciseTitle = "Choisir la couleur sombre",
        exercise = listOf(
            "Fais tourner une basse sur Synth 1.",
            "Essaie toutes les gammes mineures et modales et retiens les deux plus sombres pour ton style.",
            "Sauvegarde le projet avec celle que tu préfères.",
        ),
        success = "Une gamme choisie et sauvegardée, retrouvée au rechargement du projet.",
        style = "Mentalcore / acidcore : les gammes Phrygienne ou Hongroise mineure donnent vite une couleur tendue.",
        mistakes = listOf(
            "Croire qu'une gamme ne concerne qu'un synthé : elle s'applique aux deux synthés et aux pistes MIDI (p. 37).",
            "Chercher les 12 notes sur deux octaves en chromatique : la vue Note n'en montre qu'une, la vue étendue deux (p. 32–33).",
            "Jouer hors gamme depuis un clavier MIDI : la note est calée sur la plus proche de la gamme ; choisis Chromatique si tu veux toutes les notes (p. 39).",
        ),
        quiz = listOf(
            qz("Combien de gammes propose la vue Scales ?",
                listOf("8", "12", "16"), 2,
                "16 gammes, sur les pads 17 à 32.", GAMMES),
            qz("Quel pad sélectionne la gamme chromatique ?",
                listOf("Pad 17", "Pad 24", "Pad 32"), 2,
                "Le pad 32, en bas à droite.", GAMMES),
            qz("Changer de gamme sur un projet existant efface-t-il des notes ?",
                listOf("Oui, les notes hors gamme", "Non, l'opération n'est pas destructive", "Oui, tout le pattern"), 1,
                "La machine remplace intelligemment les notes, et l'ancienne gamme rend les notes d'origine.", FONDAMENTALE),
            qz("À quelles pistes s'appliquent la gamme et la fondamentale ?",
                listOf("Synth 1 seulement", "Aux deux synthés et aux pistes MIDI", "À toutes les pistes, Drum comprises"), 1,
                "Elles concernent les deux pistes de synthé et les pistes MIDI.", TEMPS_REEL),
        ),
    )

    private val m3l3 = ctLesson(
        M3, 3, "Patches, pré-écoute et polyphonie", listOf(PATCHES, TEMPS_REEL), 15,
        photo = listOf("ct-preset", "ct-shift", "ct-rec"),
        objective = "Choisir rapidement un patch adapté à la basse ou aux accords, avec ou sans pré-écoute.",
        explanation = "Dans la vue Preset d'un synthé, toucher un pad fait entendre le patch (pré-écoute), dans la gamme et la tonalité choisies. " +
            "En live, tiens Shift en touchant le pad pour changer de son sans faire entendre la note de test. La pré-écoute est désactivée " +
            "pendant l'enregistrement en lecture. Chaque synthé joue jusqu'à 6 notes par pas si le patch est polyphonique. " +
            "De nouveaux patches se chargent et s'éditent avec Novation Components.",
        controls = listOf(
            cmd("Preset (piste synthé)", "Vue des 128 patches.", PATCHES),
            cmd("Shift + pad de patch", "Change de patch sans pré-écoute.", PATCHES),
            cmd("Record", "Enregistre jusqu'à 6 notes par pas.", TEMPS_REEL),
        ),
        steps = listOf(
            "Synth 1, Preset : touche plusieurs patches et écoute la pré-écoute.",
            "Lance la lecture d'un pattern de Synth 1 et change de patch en tenant Shift : aucune note de test.",
            "Sur Synth 2, choisis un patch d'accords et enregistre un accord de 3 notes sur un pas.",
            "Essaie le même accord avec un patch monophonique : une seule note sonne à la fois.",
        ),
        expected = "Tu changes de patch en live sans note parasite et tu sais si un patch est fait pour les accords.",
        exerciseTitle = "Basse et accords",
        exercise = listOf(
            "Trouve un patch de basse pour Synth 1 et un patch d'accords pour Synth 2.",
            "Enregistre deux accords de 3 à 6 notes sur Synth 2.",
            "Pendant la lecture, change trois fois de patch sur Synth 1 avec Shift.",
        ),
        success = "Accords complets sur Synth 2 et changements de patch silencieux sur Synth 1.",
        mistakes = listOf(
            "Oublier Shift en live : la pré-écoute joue la fondamentale par-dessus ta musique (p. 34–35).",
            "Attendre la pré-écoute en enregistrant en lecture : elle est désactivée dans ce cas (p. 35).",
            "Enregistrer des accords sur un patch monophonique : la polyphonie dépend du patch choisi (p. 36).",
        ),
        quiz = listOf(
            qz("Comment désactiver la pré-écoute d'un patch ?",
                listOf("Tenir Shift en touchant le pad", "Couper le Master Volume", "Tenir Clear"), 0,
                "Shift + pad change de patch sans note de test.", PATCHES),
            qz("Combien de notes au maximum peut-on placer sur un même pas d'un synthé ?",
                listOf("3", "6", "8"), 1,
                "Six notes, si le patch est polyphonique.", TEMPS_REEL),
            qz("Dans les projets d'origine, quel synthé est monophonique ?",
                listOf("Synth 1", "Synth 2", "Aucun"), 0,
                "Synth 1 pour les basses, Synth 2 polyphonique pour les claviers.", TEMPS_REEL),
            qz("Dans quel cas la pré-écoute est-elle désactivée automatiquement ?",
                listOf("En mode Record avec la lecture active", "Quand la batterie est faible", "En gamme chromatique"), 0,
                "Pendant l'enregistrement en lecture, la pré-écoute est coupée.", PATCHES),
        ),
    )

    private val m3 = CourseModule(
        id = M3, number = 3, title = "Synthés et paramètres sonores",
        summary = "Jouer, gammes, fondamentale, patches, polyphonie.",
        goal = "Jouer et enregistrer des lignes de synthé justes, dans la bonne tonalité et avec le bon patch.",
        styleFocus = "Basse roulante mono sur Synth 1, leads et accords tendus sur Synth 2.",
        lessons = listOf(m3l1, m3l2, m3l3),
    )

    val modules: List<CourseModule> = listOf(m1, m2, m3)
}
