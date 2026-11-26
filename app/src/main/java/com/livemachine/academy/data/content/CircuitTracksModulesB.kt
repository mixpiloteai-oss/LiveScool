package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** Circuit Tracks — modules 4 à 6 : batterie, séquenceur, micro-pas et variations. */
internal object CircuitTracksModulesB {

    private val PREMIERS_PAS = ct("Principes élémentaires › Premiers pas", "22")
    private val ZERO = ct("Principes élémentaires › Démarrer de zéro", "25–26")
    private val RYTHMES = ct("Les rythmes", "61")
    private val JOUER_RYTHMES = ct("Les rythmes › Jouer des rythmes", "61–62")
    private val DRUM_ETENDUE = ct("Les rythmes › La vue Drum étendue", "62–63")
    private val SAMPLES = ct("Les rythmes › Choisir des échantillons sonores", "63")
    private val FLIP = ct("Les rythmes › La fonction Sample Flip", "63–64")
    private val MANUEL_DRUM = ct("Les rythmes › Programmer des frappes manuellement", "65–66")
    private val REC_DRUM = ct("Les rythmes › Enregistrer un pattern rythmique", "65")
    private val COMPONENTS = ct("Components", "101")
    private val REC_SYNTH = ct("Les synthétiseurs › Enregistrer un pattern de synthé en temps réel", "36–37")
    private val METRONOME = ct("Le tempo et le swing › La piste de métronome", "87")
    private val SANS_QUANTIF = ct("Les synthétiseurs › Enregistrer sans quantification", "38")
    private val EDITER = ct("Les synthétiseurs › Éditer les pas", "39–40")
    private val SUPPR_DUPL = ct("Les synthétiseurs › Supprimer et dupliquer", "41")
    private val SUPPR_DRUM = ct("Les rythmes › Supprimer et dupliquer", "72")
    private val STEP_PAGE = ct("Les patterns › La page Step et les patterns de 16/32 pas", "75–76")
    private val VELOCITE = ct("Les synthétiseurs › Vélocité", "42–45")
    private val VEL_DRUM = ct("Les rythmes › Vélocité", "69–71")
    private val GATE = ct("Les synthétiseurs › Gate", "46–47")
    private val LIEES = ct("Les synthétiseurs › Notes liées / Drones", "52–53")
    private val MICRO_SYNTH = ct("Les synthétiseurs › Éditer les micro-pas", "49–52")
    private val MICRO_DRUM = ct("Les rythmes › Éditer les micro-pas", "67–69")
    private val PROBA = ct("Les synthétiseurs › Les probabilités", "48–49")
    private val PROBA_DRUM = ct("Les rythmes › Les probabilités", "71")
    private val MUTATE = ct("Les synthétiseurs › La fonction Mutate", "57")
    private val PARAM_PATTERN = ct("Les paramètres des patterns › Points de début et de fin", "54–55")
    private val SENS = ct("Les paramètres des patterns › Le sens de la lecture", "56")
    private val VITESSE = ct("Les paramètres des patterns › La vitesse de synchronisation", "56–57")

    // ---------------------------------------------------------------------
    // Module 4 — Drum tracks et samples
    // ---------------------------------------------------------------------
    private const val M4 = "ct-m4"

    private val m4l1 = ctLesson(
        M4, 1, "Programmer un rythme et comprendre les kits", listOf(PREMIERS_PAS, RYTHMES, JOUER_RYTHMES), 15,
        objective = "Programmer les quatre pistes Drum pas à pas et trouver vite le bon type de son dans un kit.",
        explanation = "Sur une piste Drum, les 16 pads du bas affichent une page de samples ; les touches fléchées passent aux 4 pages. " +
            "Chaque page est un kit organisé de la même façon : 1-2 kicks, 3-4 snares, 5-6 charleys fermés, 7-8 charleys ouverts, " +
            "9 à 12 percussions, 13 à 16 sons mélodiques. Une pression courte sur un sample le rend actif ; une pression longue le " +
            "joue sans changer le sample actif. Les pas qui contiennent une frappe sont bleu vif.",
        controls = listOf(
            cmd("Drum 1 à Drum 4", "Choisissent la piste Drum à programmer.", RYTHMES),
            cmd("Pads de samples (rangées 3-4)", "Pression courte : sample actif ; pression longue : simple écoute.", JOUER_RYTHMES),
            cmd("Touches fléchées", "Passent aux quatre pages (kits) de samples.", RYTHMES),
            cmd("Pads de pas (rangées 1-2)", "Ajoutent ou enlèvent la frappe du sample actif.", JOUER_RYTHMES),
        ),
        steps = listOf(
            "Projet vide. Drum 1 : par défaut le kick 1 est actif (page 1, slot 1).",
            "Place le kick sur 1, 5, 9 et 13.",
            "Drum 2 (snare 1 par défaut, slot 3) : pas 5 et 13.",
            "Drum 3 (charley fermé 1, slot 5) : un pas sur deux.",
            "Drum 4 (percussion 1, slot 9) : deux ou trois pas à contretemps.",
            "Pendant la lecture, change de kit sur Drum 3 avec les touches fléchées et choisis un autre charley par pression courte.",
        ),
        expected = "Un rythme sur quatre pistes, et tu sais où chercher kick, snare, charley ou percussion dans n'importe quel kit.",
        exerciseTitle = "Tour des kits",
        exercise = listOf(
            "Garde le même rythme et écoute les 4 pages de kits sur les quatre pistes.",
            "Note la page dont le kick convient le mieux à ton style.",
        ),
        success = "Un kit choisi pour chaque piste, en restant dans la bonne famille de sons (slots).",
        style = "Tribe : percussions des slots 9 à 12 en motifs répétitifs sur Drum 4.",
        mistakes = listOf(
            "Tenir un pad de sample en voulant le choisir : une pression longue le joue mais ne le rend pas actif (p. 62).",
            "Chercher la vue Drum dans une autre touche : comme pour les synthés, c'est la touche de piste qui ouvre la vue Note (p. 61).",
            "Mélanger les familles : garder l'ordre des slots du kit évite de chercher en live (p. 22).",
        ),
        quiz = listOf(
            qz("Dans un kit, où sont les charleys ouverts ?",
                listOf("Slots 5 et 6", "Slots 7 et 8", "Slots 13 à 16"), 1,
                "5-6 charleys fermés, 7-8 charleys ouverts.", PREMIERS_PAS),
            qz("Que fait une pression longue sur un pad de sample ?",
                listOf("Le rend actif", "Le joue sans changer le sample actif", "L'efface"), 1,
                "Seule une pression courte change le sample actif.", JOUER_RYTHMES),
            qz("Quel sample est actif par défaut sur Drum 2 ?",
                listOf("Kick 1 (slot 1)", "Snare 1 (slot 3)", "Charley fermé 1 (slot 5)"), 1,
                "Drum 2 démarre sur la snare 1, page 1 slot 3.", RYTHMES),
            qz("Combien de pages de samples propose la vue Note d'une piste Drum ?",
                listOf("2", "4", "8"), 1,
                "Quatre pages de 16 samples.", RYTHMES),
        ),
    )

    private val m4l2 = ctLesson(
        M4, 2, "Choisir les samples et jouer les quatre pistes à la fois", listOf(SAMPLES, DRUM_ETENDUE, REC_DRUM, COMPONENTS), 15,
        objective = "Choisir un sample dans la vue Preset et enregistrer les quatre pistes Drum en jouant en direct.",
        explanation = "La vue Preset d'une piste Drum montre les 64 samples en deux pages de 32 : plus rapide pour comparer. " +
            "La vue Drum étendue (Shift + Note) réduit la grille à quatre pads, 29 à 32, un par piste Drum, chacun avec son sample actif : " +
            "on joue toute la batterie comme sur des pads de boîte à rythmes, et avec Record on l'enregistre. " +
            "Tes propres samples se chargent avec Novation Components.",
        controls = listOf(
            cmd("Preset (piste Drum)", "64 samples en 2 pages de 32.", SAMPLES),
            cmd("Shift + Note (piste Drum)", "Vue Drum étendue : pads 29 à 32 = Drum 1 à 4.", DRUM_ETENDUE),
            cmd("Record", "Enregistre les frappes jouées en direct.", REC_DRUM),
        ),
        steps = listOf(
            "Drum 1, Preset : compare quelques kicks sur les deux pages et choisis-en un.",
            "Fais de même pour Drum 2, Drum 3 et Drum 4.",
            "Shift + Note : seuls les pads 29 à 32 sont allumés.",
            "Lance la lecture et joue les quatre pads pour trouver un groove.",
            "Appuie sur Record et joue le groove une fois, puis Record pour arrêter.",
        ),
        expected = "Les quatre pistes Drum sont enregistrées en une seule prise jouée.",
        exerciseTitle = "Le batteur",
        exercise = listOf(
            "Pattern vide, métronome allumé (Shift + Clear).",
            "En vue Drum étendue, enregistre kick et snare en direct, puis ajoute charley et percussion à la deuxième boucle.",
        ),
        success = "Un rythme complet enregistré en direct, calé sur les pas.",
        mistakes = listOf(
            "Oublier que chaque pad de la vue étendue joue le sample actif de sa piste : choisis les samples avant (p. 63).",
            "Appuyer sur un pas en vue Note à l'arrêt pour écouter une frappe : sur une piste Drum, on n'entend rien ; utilise Velocity ou Gate (p. 65).",
            "Chercher l'import de samples dans la machine : il passe par Components (p. 101).",
        ),
        quiz = listOf(
            qz("Quels pads restent actifs dans la vue Drum étendue ?",
                listOf("1 à 4", "17 à 20", "29 à 32"), 2,
                "Un pad par piste Drum, du 29 au 32.", DRUM_ETENDUE),
            qz("Comment ouvrir la vue Drum étendue ?",
                listOf("Shift + Note", "Shift + Preset", "Shift + Mixer"), 0,
                "C'est la vue secondaire de la touche Note.", DRUM_ETENDUE),
            qz("Sur quelle page s'ouvre la vue Preset de Drum 3 ?",
                listOf("Page 1", "Page 2", "Page 4"), 1,
                "Drum 1 et 2 ouvrent la page 1, Drum 3 et 4 la page 2.", SAMPLES),
            qz("Avec quel outil charge-t-on ses propres samples ?",
                listOf("La vue Setup", "Novation Components", "La vue Packs"), 1,
                "Components sert à charger des samples, des patches et des Packs.", COMPONENTS),
        ),
    )

    private val m4l3 = ctLesson(
        M4, 3, "Sample Flip : plusieurs samples sur une piste", listOf(FLIP, MANUEL_DRUM, ZERO), 15,
        objective = "Faire jouer des samples différents sur une même piste Drum, pas par pas ou en direct.",
        explanation = "Normalement une piste Drum joue son sample actif. Sample Flip permet de changer de sample sur certains pas seulement. " +
            "Pas à pas : tiens le pad du pas (il devient rouge), puis touche un autre sample : le pas devient rose. " +
            "En direct : Record allumé, joue différents pads de samples ; chaque frappe garde son sample. " +
            "Les pas avec le sample actif restent bleus, les pas « flippés » sont roses ; tiens un pas rose pour savoir lequel il joue.",
        controls = listOf(
            cmd("Pression longue sur un pas + pad de sample", "Assigne un autre sample à ce pas (pas rose).", MANUEL_DRUM),
            cmd("Tenir un pad de sample + pads de pas", "Place ce sample sur plusieurs pas.", FLIP),
            cmd("Record + pads de samples", "Sample Flip en direct.", FLIP),
        ),
        steps = listOf(
            "Drum 3 avec un charley fermé sur tous les pas impairs.",
            "Tiens le pas 15 : il devient rouge ; touche un charley ouvert (slot 7) : le pas 15 devient rose.",
            "Tiens un sample de percussion et touche les pas 4 et 12 : ils deviennent roses.",
            "Tiens un pas rose pour vérifier le sample qu'il joue.",
            "Record allumé, joue en direct deux samples différents sur Drum 4, puis arrête Record.",
        ),
        expected = "Une seule piste Drum joue charley fermé, charley ouvert et percussion.",
        exerciseTitle = "Une piste, trois sons",
        exercise = listOf(
            "Sur Drum 4, construis une boucle de percussions qui utilise au moins trois samples différents.",
            "Garde Drum 1 à 3 pour kick, snare et charleys.",
        ),
        success = "Trois samples audibles sur Drum 4, pas roses visibles dans la vue Note.",
        style = "Tribe : une piste de percussions qui varie entre trois toms ou congas.",
        mistakes = listOf(
            "Appuyer brièvement sur le pas puis sur un sample : la pression courte place le sample actif ; il faut tenir le pas (p. 66).",
            "Confondre rose et bleu : bleu = sample actif, rose = sample remplacé (p. 64).",
            "Le manuel mentionne aussi des samples inversés (rose également, p. 22) sans détailler la manipulation : à tester sur ta machine.",
        ),
        quiz = listOf(
            qz("De quelle couleur s'allume un pas dont le sample a été remplacé ?",
                listOf("Bleu vif", "Rose", "Vert"), 1,
                "Les pas « flippés » sont roses ; ceux du sample actif bleu vif.", FLIP),
            qz("Comment assigner un autre sample à un seul pas ?",
                listOf("Tenir le pas, puis toucher le sample", "Shift + pas", "Clear + sample"), 0,
                "Le pas tenu devient rouge, puis rose après le choix du sample.", MANUEL_DRUM),
            qz("Sample Flip fonctionne-t-il pendant la lecture ?",
                listOf("Non, seulement à l'arrêt", "Oui, en Stop comme en Play", "Seulement en vue Preset"), 1,
                "La fonction opère dans les modes Stop et Play.", MANUEL_DRUM),
            qz("Dans quelles vues peut-on faire un Sample Flip en direct ?",
                listOf("Vue Note ou vue Preset de la piste Drum", "Vue Mixer", "Vue FX"), 0,
                "La vue Preset donne accès à deux fois plus de samples à la fois.", FLIP),
        ),
    )

    private val m4 = CourseModule(
        id = M4, number = 4, title = "Drum tracks et samples",
        summary = "Kits, samples, vue Drum étendue, Sample Flip.",
        goal = "Construire une batterie complète et variée sur les quatre pistes Drum.",
        styleFocus = "Kick lourd sur Drum 1, percussions tribales qui changent de sample sur Drum 4.",
        lessons = listOf(m4l1, m4l2, m4l3),
    )

    // ---------------------------------------------------------------------
    // Module 5 — Séquenceur et patterns
    // ---------------------------------------------------------------------
    private const val M5 = "ct-m5"

    private val m5l1 = ctLesson(
        M5, 1, "Enregistrer en temps réel", listOf(REC_SYNTH, METRONOME, REC_DRUM, EDITER), 15,
        objective = "Enregistrer synthés et batterie en jouant, avec le métronome, et faire des punch-in précis.",
        explanation = "Record allumé, tout ce que tu joues s'ajoute au pattern ; la tête de lecture devient rouge pour te le rappeler. " +
            "Le métronome s'allume avec Shift + Clear et son volume se règle dans la vue Tempo avec la Macro 5. " +
            "Si tu tiens Record plus d'une demi-seconde, l'enregistrement s'arrête dès que tu le relâches : c'est un punch-in/out d'une seule touche. " +
            "Play relance le pattern au pas 1 ; Shift + Play reprend là où il s'était arrêté.",
        controls = listOf(
            cmd("Record", "Pression courte : marche/arrêt ; tenue : enregistrement momentané.", REC_SYNTH),
            cmd("Shift + Clear", "Métronome marche/arrêt.", METRONOME),
            cmd("Tempo/Swing + Macro 5", "Volume du métronome.", METRONOME),
            cmd("Shift + Play", "Reprend la lecture au pas où elle s'était arrêtée.", EDITER),
        ),
        steps = listOf(
            "Programme un kick 4/4 sur Drum 1 et lance la lecture.",
            "Shift + Clear pour le métronome ; règle son volume avec Tempo/Swing puis Macro 5.",
            "Synth 1, vue Note : répète ta basse sans enregistrer.",
            "Appuie sur Record et joue-la ; appuie de nouveau sur Record à la fin de la boucle.",
            "Pour corriger un passage, tiens Record seulement pendant les notes à refaire, puis relâche.",
        ),
        expected = "Ta basse est enregistrée, et tu sais corriger un passage sans toucher au reste.",
        exerciseTitle = "Punch-in",
        exercise = listOf(
            "Enregistre une basse de 16 pas.",
            "Refais seulement les pas 9 à 12 en tenant Record pendant ce passage.",
        ),
        success = "Seul le passage visé a changé.",
        mistakes = listOf(
            "Laisser Record allumé pendant plusieurs boucles en jouant n'importe quoi : tout s'ajoute au pattern (p. 36).",
            "Chercher le volume du métronome dans le Mixer : il se règle avec la Macro 5 dans la vue Tempo (p. 87).",
            "S'attendre à ce que Play reprenne au même pas : il repart du début, sauf avec Shift + Play (p. 39).",
        ),
        quiz = listOf(
            qz("Quelle couleur prend la tête de lecture en mode Record ?",
                listOf("Blanc", "Rouge", "Vert"), 1,
                "Elle passe du blanc au rouge pour signaler l'enregistrement.", REC_SYNTH),
            qz("Comment allumer le métronome ?",
                listOf("Shift + Clear", "Shift + Tempo/Swing", "Shift + Record"), 0,
                "Shift + Clear ; Clear vert vif = métronome actif.", METRONOME),
            qz("Que se passe-t-il si tu tiens Record plus d'une demi-seconde ?",
                listOf("Le pattern s'efface", "L'enregistrement s'arrête au relâchement", "La quantification se coupe"), 1,
                "Action momentanée : pratique pour le punch-in/out.", REC_SYNTH),
            qz("Comment reprendre la lecture au pas où elle s'était arrêtée ?",
                listOf("Play", "Shift + Play", "Record + Play"), 1,
                "Sans Shift, le pattern repart du premier pas.", EDITER),
        ),
    )

    private val m5l2 = ctLesson(
        M5, 2, "Éditer les pas : ajouter, effacer, copier, passer à 32", listOf(EDITER, SUPPR_DUPL, SUPPR_DRUM, STEP_PAGE), 20,
        objective = "Corriger un pattern pas par pas et l'allonger à 32 pas.",
        explanation = "Sur un synthé, tenir un pas montre ses notes en rouge sur le clavier et les joue. Tenir un pas + toucher une note l'ajoute ; " +
            "même geste sur une note rouge : elle est retirée. Pas besoin de Record. Clear + pas efface toutes les notes du pas d'un coup. " +
            "Duplicate + pas source + pas destination copie un pas. La touche Step Page passe le pattern à 32 pas et montre les pas 17 à 32. " +
            "Duplicate + Step Page copie les pas 1-16 sur 17-32 ; Clear + Step Page revient à 16 pas sans perdre les pas 17-32.",
        controls = listOf(
            cmd("Tenir un pas + pad du clavier", "Ajoute ou retire une note sur ce pas.", SUPPR_DUPL),
            cmd("Clear + pas", "Efface toutes les notes du pas.", SUPPR_DUPL),
            cmd("Duplicate + pas source + pas destination", "Copie un pas.", SUPPR_DUPL),
            cmd("Step Page (1-16 / 17-32)", "Passe à 32 pas, change de page (bleu = 1-16, orange = 17-32).", STEP_PAGE),
            cmd("Duplicate + Step Page", "Allonge à 32 pas en copiant 1-16 sur 17-32.", STEP_PAGE),
            cmd("Clear + Step Page", "Revient à 16 pas ; les pas 17-32 sont conservés.", STEP_PAGE),
        ),
        steps = listOf(
            "À l'arrêt, sur Synth 1, tiens un pas bleu vif : ses notes s'allument en rouge et sonnent.",
            "Tiens ce pas et touche une autre note : elle s'ajoute. Touche une note rouge : elle disparaît.",
            "Tiens Clear et touche un pas : il est vidé.",
            "Tiens Duplicate, touche le pas 1 puis le pas 9 : le pas 1 est copié sur le 9.",
            "Duplicate + Step Page : le pattern fait 32 pas, la seconde moitié est une copie.",
            "Appuie sur Step Page pour voir les pas 17-32 et modifie quelques notes pour varier la fin.",
        ),
        expected = "Un pattern de 32 pas dont la seconde moitié est une variation de la première.",
        exerciseTitle = "Question / réponse",
        exercise = listOf(
            "Prends une basse de 16 pas.",
            "Passe à 32 pas par copie, puis change seulement les 4 derniers pas de la page 2.",
        ),
        success = "Les 28 premiers pas identiques aux deux moitiés, une fin différente.",
        mistakes = listOf(
            "Ne pas trouver une note en tenant un pas : elle est peut-être dans une autre octave, utilise les touches Octave (p. 40).",
            "Vouloir effacer un pas dans la vue Pattern Settings : ce n'est pas possible (p. 41).",
            "Oublier qu'effacer un pas remet aussi à zéro sa vélocité, ses micro-pas et sa probabilité (p. 72).",
        ),
        quiz = listOf(
            qz("Faut-il activer Record pour ajouter une note en tenant un pas ?",
                listOf("Oui", "Non", "Seulement en lecture"), 1,
                "La saisie manuelle fonctionne sans Record.", SUPPR_DUPL),
            qz("Que fait Clear + Step Page ?",
                listOf("Efface les pas 17 à 32", "Ramène le pattern à 16 pas en gardant les pas 17-32 en mémoire", "Efface tout le pattern"), 1,
                "Les notes de 17 à 32 réapparaissent si tu repasses à 32 pas.", STEP_PAGE),
            qz("De quelle couleur est la touche Step Page quand elle montre les pas 17 à 32 ?",
                listOf("Bleu", "Orange", "Rouge"), 1,
                "Bleu pour la page 1, orange pour la page 2.", PARAM_PATTERN),
            qz("Que fait Duplicate + Step Page ?",
                listOf("Copie 1-16 sur 17-32 et passe à 32 pas", "Duplique le projet", "Copie la page 2 sur la page 1"), 0,
                "L'automation est copiée aussi ; l'ancienne page 2 est remplacée.", STEP_PAGE),
        ),
    )

    private val m5l3 = ctLesson(
        M5, 3, "Vélocité, gate et notes liées", listOf(VELOCITE, VEL_DRUM, GATE, LIEES), 20,
        objective = "Régler l'accent et la durée de chaque pas, et tenir une note sur tout le pattern.",
        explanation = "Vue Velocity : en haut les pas, en bas un fader de 16 pads ; chaque pad vaut 8 (12 pads = 96). Touche un pas puis un pad du fader. " +
            "En lecture, tiens le pas pendant le réglage. Shift + Velocity active la vélocité fixe (96) pour tout ce que tu joues ; " +
            "les frappes posées à la main sur les pas ont toujours une vélocité fixe. Vue Gate : le nombre de pads allumés = durée en pas, " +
            "de 1/6 à 16 pas ; retouche le dernier pad allumé pour retirer 1/6 de pas. Pour une note liée (drone), ouvre la vue Micro Step " +
            "(Gate une seconde fois), active la liaison avec le pad 24 et règle le gate pour atteindre la note suivante.",
        controls = listOf(
            cmd("Velocity", "Vue de la vélocité de chaque pas (16 niveaux affichés).", VELOCITE),
            cmd("Shift + Velocity", "Vélocité fixe (96) marche/arrêt, réglage global.", VEL_DRUM),
            cmd("Gate", "Vue de la durée des notes, de 1/6 à 16 pas.", GATE),
            cmd("Gate (2e pression) + pad 24", "Liaison de la note (drone).", LIEES),
        ),
        steps = listOf(
            "Drum 3 (charleys) : Velocity. Touche les pas pairs et mets-les à 5 pads (40) ; laisse les impairs forts.",
            "En lecture, tiens un pas et change sa vélocité pour l'entendre en direct.",
            "Tiens un pas vide en vue Velocity et touche un pad bas : tu ajoutes une ghost note.",
            "Synth 1 : Gate. Mets une note à 1 pas, une autre à 4 pas ; retouche le pad 4 pour obtenir 3 pas et 5/6.",
            "Synth 2 : une note au pas 1, Gate sur 16 (pad 32), puis Gate de nouveau et pad 24 : la note est liée.",
        ),
        expected = "Des charleys qui respirent grâce aux accents et une nappe qui tient sans interruption.",
        exerciseTitle = "Groove dynamique",
        exercise = listOf(
            "Sur un pattern de charleys, crée un motif d'accents fort / faible / moyen / faible.",
            "Sur la basse, alterne notes courtes (gate 1) et une note longue (gate 3) en fin de mesure.",
        ),
        success = "Les accents s'entendent nettement et la note longue se distingue.",
        style = "Hardtek : basse courte et sèche (gate bas) pour laisser respirer le kick.",
        mistakes = listOf(
            "Croire que la vélocité fixe modifie les notes déjà enregistrées : elle ne change que ce que tu joues ensuite (p. 44).",
            "Chercher à régler le gate d'un pas vide : un pas sans note a un gate de zéro et ne s'édite pas (p. 47).",
            "Oublier que la vélocité fixe est globale : elle s'applique à toutes les pistes (p. 70).",
        ),
        quiz = listOf(
            qz("Quelle vélocité donne la vélocité fixe ?",
                listOf("64", "96", "127"), 1,
                "96, soit 12 pads allumés dans la vue Velocity.", VELOCITE),
            qz("Dans la vue Velocity, combien vaut chaque pad du fader ?",
                listOf("4", "8", "16"), 1,
                "16 pads de 8 niveaux : 5 pads = 40.", VEL_DRUM),
            qz("Quelle est la plage du gate ?",
                listOf("1 à 8 pas", "1/6 à 16 pas", "1/4 à 32 pas"), 1,
                "De 1/6 à 16 pas, par sixièmes de pas : 96 valeurs.", GATE),
            qz("Quel pad active la liaison d'une note dans la vue Micro Step ?",
                listOf("Le pad 17", "Le pad 24", "Le pad 32"), 1,
                "Le pad 24 active ou désactive la liaison du pas.", LIEES),
        ),
    )

    private val m5 = CourseModule(
        id = M5, number = 5, title = "Séquenceur et patterns",
        summary = "Enregistrement en direct, édition des pas, 32 pas, vélocité, gate.",
        goal = "Enregistrer, corriger et nuancer un pattern complet de 16 ou 32 pas.",
        styleFocus = "Accents de charleys, basses courtes et nappes tenues : la base d'un groove hypnotique.",
        lessons = listOf(m5l1, m5l2, m5l3),
    )

    // ---------------------------------------------------------------------
    // Module 6 — Micro-steps, probabilité et variations
    // ---------------------------------------------------------------------
    private const val M6 = "ct-m6"

    private val m6l1 = ctLesson(
        M6, 1, "Enregistrement non quantifié et micro-pas", listOf(SANS_QUANTIF, MICRO_SYNTH, MICRO_DRUM), 20,
        objective = "Décaler des notes et des frappes entre les pas, et créer des roulements de batterie.",
        explanation = "Chaque pas est divisé en six micro-pas. Par défaut, l'enregistrement est quantifié : la note va sur le pas le plus proche. " +
            "Shift + Record le coupe : la note va sur le micro-pas le plus proche. En tenant Shift, Record vert = quantifié, rouge = non quantifié. " +
            "Synthé : vue Micro Step (Shift + Gate), la rangée 3 donne la position (pads 1 à 6), la rangée 4 la note à décaler. " +
            "Batterie : dans la vue Gate, les pads 17 à 22 sont les micro-pas et on peut en allumer plusieurs : un pas joue alors plusieurs frappes.",
        controls = listOf(
            cmd("Shift + Record", "Enregistrement quantifié / non quantifié.", SANS_QUANTIF),
            cmd("Shift + Gate (synthé)", "Vue Micro Step : position et choix de la note.", MICRO_SYNTH),
            cmd("Gate (piste Drum), pads 17 à 22", "Micro-pas de la frappe, plusieurs possibles.", MICRO_DRUM),
        ),
        steps = listOf(
            "Tiens Shift et regarde Record : vert = quantification active.",
            "Shift + Record pour la couper, enregistre quelques charleys en jouant un peu en avance ou en retard.",
            "Drum 2 (snare sur 5 et 13), Gate : touche le pas 13, éteins le pad 17 et allume le 20 : la snare arrive un demi-pas plus tard.",
            "Touche le pas vide 15 et allume les pads 17, 19 et 21 : trois frappes du sample actif, un roulement.",
            "Synth 2 : un accord de 3 notes, Shift + Gate, choisis chaque note en rangée 4 et décale-les de 0, 1 et 2 micro-pas : l'accord s'égrène.",
        ),
        expected = "Une snare décalée, un roulement en fin de pattern et un accord joué « à la main ».",
        exerciseTitle = "Le roulement de fin",
        exercise = listOf(
            "Sur les pas 13 à 16 de la snare, crée un roulement qui s'accélère (1, 2, 3 puis 6 micro-pas allumés).",
            "Remets la quantification avant d'enregistrer autre chose.",
        ),
        success = "Le roulement s'entend avant le retour au pas 1, et Record est de nouveau vert avec Shift.",
        style = "Hardtek / tribecore : roulements de snare en fin de boucle pour relancer.",
        mistakes = listOf(
            "Oublier de remettre la quantification : les enregistrements suivants restent décalés (p. 38).",
            "Chercher les micro-pas de batterie en vue Micro Step : sur une piste Drum, ils sont dans la vue Gate (p. 67).",
            "Vouloir des vélocités différentes dans un roulement : toutes les frappes d'un pas ont la vélocité et le sample du pas (p. 69).",
        ),
        quiz = listOf(
            qz("En combien de micro-pas un pas est-il divisé ?",
                listOf("4", "6", "8"), 1,
                "Six micro-pas : un « écart » = un sixième de pas.", SANS_QUANTIF),
            qz("Shift tenu, Record est rouge : qu'est-ce que cela signifie ?",
                listOf("Enregistrement en cours", "Enregistrement non quantifié", "Métronome coupé"), 1,
                "Vert = quantifié, rouge = non quantifié.", SANS_QUANTIF),
            qz("Où règle-t-on les micro-pas d'une piste Drum ?",
                listOf("Dans la vue Gate, pads 17 à 22", "Dans la vue Velocity", "Dans la vue Pattern Settings"), 0,
                "La vue Gate d'une piste Drum montre les six micro-pas.", MICRO_DRUM),
            qz("Sur quel micro-pas tombent les frappes posées à la main en mode Stop ?",
                listOf("Le premier, sur le temps", "Le dernier", "Au hasard"), 0,
                "Elles sont toujours sur le premier micro-pas.", MICRO_DRUM),
        ),
    )

    private val m6l2 = ctLesson(
        M6, 2, "Probabilité et Mutate", listOf(PROBA, PROBA_DRUM, MUTATE), 15,
        objective = "Faire varier un pattern tout seul avec la probabilité, et le transformer avec Mutate.",
        explanation = "Chaque pas a une probabilité d'être joué, de 100 % à 12,5 % en huit valeurs, réglée dans la vue Probability " +
            "(Shift + Pattern Settings) avec les pads 17 à 24. Toutes les notes d'un pas sont jouées ensemble ou pas du tout ; " +
            "sur la batterie, la probabilité vaut pour le pas entier, micro-pas compris. Mutate (Shift + Duplicate) mélange les notes " +
            "du pattern en cours sur d'autres pas, avec tous leurs réglages. Mutate ne s'annule pas : sauvegarde avant.",
        controls = listOf(
            cmd("Shift + Pattern Settings", "Vue Probability.", PROBA),
            cmd("Pads 17 à 24 (vue Probability)", "8 valeurs : 1 pad = 12,5 %, 8 pads = 100 %.", PROBA),
            cmd("Shift + Duplicate", "Mutate : redistribue au hasard les notes du pattern en cours.", MUTATE),
        ),
        steps = listOf(
            "Drum 3, charleys sur tous les pas. Shift + Pattern Settings.",
            "À l'arrêt, touche un pas pair puis le pad 20 (4 pads = 50 %). Fais de même sur d'autres pas pairs.",
            "Lance la lecture : les charleys pairs changent à chaque tour.",
            "En lecture, tiens un pas et touche le pad 18 (25 %) pour l'entendre plus rarement.",
            "Sauvegarde le projet, puis Shift + Duplicate sur la piste de percussions : écoute le résultat. Recommence si besoin.",
        ),
        expected = "Les charleys varient sans intervention, et Mutate t'a donné une nouvelle idée de percussions.",
        exerciseTitle = "Le pattern vivant",
        exercise = listOf(
            "Sur Drum 4, mets 3 percussions à 37,5 % et 2 à 75 %.",
            "Écoute 8 tours : aucune boucle ne doit sonner exactement pareil.",
        ),
        success = "Variation audible d'un tour à l'autre, sans perdre le kick.",
        style = "Tribe : percussions à probabilité réduite pour un groove qui ne lasse jamais.",
        mistakes = listOf(
            "Mettre une probabilité sur le kick principal : le groove perd son appui.",
            "Utiliser Mutate sans sauvegarde : l'opération ne s'annule pas (p. 57).",
            "Penser que Mutate touche toute la chaîne : seul le pattern en cours de lecture est modifié (p. 57).",
            "Oublier que réenregistrer une note sur un pas remet sa probabilité à 100 % (p. 49).",
        ),
        quiz = listOf(
            qz("Quelle probabilité donnent 4 pads allumés ?",
                listOf("25 %", "50 %", "75 %"), 1,
                "Chaque pad vaut 12,5 % : 4 pads = 50 %.", PROBA),
            qz("Sur une piste Drum, à quoi s'applique la probabilité ?",
                listOf("À chaque micro-pas", "Au pas entier", "À la piste entière"), 1,
                "Toutes les frappes du pas, micro-pas compris, sont jouées ou non ensemble.", PROBA_DRUM),
            qz("Comment déclencher Mutate ?",
                listOf("Shift + Duplicate", "Shift + Clear", "Duplicate + Patterns"), 0,
                "Mutate est la fonction secondaire de Duplicate.", MUTATE),
            qz("Que change Mutate ?",
                listOf("Le nombre de notes", "La place des notes, avec leurs réglages", "Les samples de la piste"), 1,
                "Le nombre de notes et les sons restent, ils sont réassignés à d'autres pas.", MUTATE),
        ),
    )

    private val m6l3 = ctLesson(
        M6, 3, "Longueur, sens et vitesse du pattern", listOf(PARAM_PATTERN, SENS, VITESSE), 20,
        objective = "Créer de la polyrythmie avec des longueurs différentes, et changer le sens ou la vitesse d'une piste.",
        explanation = "Dans la vue Pattern Settings, le pad sable marque le dernier pas : touche un autre pas pour raccourcir. " +
            "Shift + pas déplace le point de départ. Les notes hors zone sont gardées (rouge pâle). Chaque piste a sa propre longueur : " +
            "une piste de 12 pas contre une de 16 se décale à chaque tour. Pads 29 à 32 : sens avant, inverse, ping-pong, aléatoire. " +
            "Rangée 3 : vitesse de 1/4 à 1/32T, 1/16 par défaut. En lecture, sens et vitesse changent à la fin du cycle en cours.",
        controls = listOf(
            cmd("Pattern Settings", "Vue longueur, sens et vitesse de la piste.", PARAM_PATTERN),
            cmd("Pad de pas (vue Pattern Settings)", "Nouveau point de fin.", PARAM_PATTERN),
            cmd("Shift + pad de pas", "Nouveau point de départ.", PARAM_PATTERN),
            cmd("Pads 29 à 32", "Sens : avant, inverse, ping-pong, aléatoire.", SENS),
            cmd("Rangée 3", "Vitesse : 1/4, 1/4T, 1/8, 1/8T, 1/16, 1/16T, 1/32, 1/32T.", VITESSE),
        ),
        steps = listOf(
            "Drum 4 avec une boucle de percussions de 16 pas. Pattern Settings.",
            "Touche le pas 12 : la piste fait 12 pas et se décale par rapport au kick.",
            "Sur Synth 1, essaie le ping-pong (pad 31) puis l'aléatoire (pad 32).",
            "Sur Synth 2, passe la vitesse en 1/8 : la nappe avance deux fois moins vite.",
            "Remets 16 pas, sens avant et 1/16 pour revenir au point de départ.",
        ),
        expected = "Une percussion qui tourne en 12 contre 16 et une basse qui change de sens.",
        exerciseTitle = "Polyrythmie",
        exercise = listOf(
            "Kick 16 pas, percussion 12 pas, charley 7 pas.",
            "Écoute combien de tours il faut pour que tout revienne en phase.",
        ),
        success = "Les trois pistes tournent ensemble avec des cycles différents, sans que le kick bouge.",
        style = "Tribe / mentalcore : motifs de 12 ou 7 pas qui créent un effet hypnotique.",
        mistakes = listOf(
            "S'inquiéter des notes disparues après un raccourcissement : elles reviennent si tu remets la fin d'origine (p. 54).",
            "Oublier Shift pour déplacer le début : sans Shift, tu déplaces la fin (p. 55).",
            "S'attendre à un changement de sens ou de vitesse immédiat en lecture : il arrive à la fin du cycle (p. 56–57).",
        ),
        quiz = listOf(
            qz("Comment déplacer le point de départ d'un pattern ?",
                listOf("Tenir Shift et toucher le pas", "Tenir Clear et toucher le pas", "Duplicate + pas"), 0,
                "Sans Shift, le pas touché devient le point de fin.", PARAM_PATTERN),
            qz("Quel pad choisit la lecture en ping-pong ?",
                listOf("Pad 29", "Pad 31", "Pad 32"), 1,
                "29 avant, 30 inverse, 31 ping-pong, 32 aléatoire.", SENS),
            qz("Quelle vitesse de synchronisation est réglée par défaut ?",
                listOf("1/8", "1/16", "1/32"), 1,
                "1/16 : chaque pas est une double croche.", VITESSE),
            qz("Changer le sens de lecture en Play prend effet quand ?",
                listOf("Immédiatement", "À la fin du cycle du pattern", "Au prochain démarrage"), 1,
                "Le pattern termine son cycle avant de changer de sens.", SENS),
        ),
    )

    private val m6 = CourseModule(
        id = M6, number = 6, title = "Micro-steps, probabilité et variations",
        summary = "Micro-pas, roulements, probabilité, Mutate, polyrythmie.",
        goal = "Rendre un pattern vivant et imprévisible sans le réécrire.",
        styleFocus = "Grooves tribaux et mentaux qui évoluent seuls : probabilité et longueurs décalées.",
        lessons = listOf(m6l1, m6l2, m6l3),
    )

    val modules: List<CourseModule> = listOf(m4, m5, m6)
}
