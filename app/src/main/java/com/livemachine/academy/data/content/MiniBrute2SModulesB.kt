package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** MiniBrute 2S — modules 5 à 7 : patchbay, séquenceur, pistes avancées et arpégiateur. */
internal object MiniBrute2SModulesB {

    private val PATCH_CONCEPTS = mb2s("7.1.1–7.1.3 Patchbay : parties, entrées/sorties, pleine échelle", "59–60")
    private val PRECABLAGE = mb2s("7.1.4 Les connexions pré-câblées", "61")
    private val LFO_FILTRE = mb2s("3.4.1 Balayer le filtre avec un LFO", "13")
    private val VIBRATO = mb2s("3.4.2 Ajouter un vibrato avec le VCO 2", "14")
    private val ATTENUATEURS = mb2s("7.12 La partie Attenuators", "78–79")
    private val SYNC_BTN = mb2s("3.6.1 Le bouton Sync", "17")
    private val SEQ_ECOUTE = mb2s("3.6.3 Le séquenceur", "19")
    private val LIRE = mb2s("9.1 Lire un motif", "101–102")
    private val CHARGER = mb2s("9.3.3 Charger un motif, Instant Change", "106")
    private val TEMPS_REEL = mb2s("3.6.3.1 Enregistrer un motif (temps réel)", "19–20")
    private val PAS_A_PAS = mb2s("3.6.3.2 Enregistrer un motif (pas à pas)", "21")
    private val ENREG = mb2s("9.2 Enregistrer un motif", "103–104")
    private val TRANSPORT = mb2s("8.13 Les contrôles de transport", "95–96")
    private val EDITION_REEL = mb2s("10.5 L'édition en temps réel", "118")
    private val POTARDS_PAS = mb2s("10.6 L'édition en mode Step", "119–120")
    private val RAM_FLASH = mb2s("9.3.1–9.3.2 Mémoire flash vs RAM, banques", "105")
    private val REVENIR = mb2s("9.3.4 Revenir à un motif", "107")
    private val SAUVER = mb2s("9.3.5 Sauvegarder un motif", "108")
    private val COPIER = mb2s("9.3.6–9.3.8 Copier, coller, effacer un motif", "109")
    private val CHAINE = mb2s("9.4 Créer une chaîne", "110")
    private val PISTES = mb2s("10.1–10.2 Pistes et types de pistes", "111–113")
    private val GATE = mb2s("10.2.6 Gate et Step Repeat", "116")
    private val MUTE_DIV = mb2s("10.3–10.4 Sourdine, divisions temporelles", "117–118")
    private val PAGES = mb2s("10.7–10.8 Les pages", "122–123")
    private val LONGUEUR = mb2s("10.9 Prolonger un motif, longueur", "124–127")
    private val SENS = mb2s("8.12 Sens de la lecture", "94")
    private val SWING = mb2s("8.10 Swing", "92")
    private val GAMMES = mb2s("8.14 Sélection de la gamme", "97–100")
    private val TEMPO = mb2s("8.6–8.9 Métronome, Tempo, Tap, Time Division", "91")
    private val ARP_INTRO = mb2s("3.6.2 L'arpégiateur", "18")
    private val ARP = mb2s("11.3 Le fonctionnement de base", "130–132")
    private val ARP_MODES = mb2s("11.4 Les modes de l'arpégiateur", "133–135")
    private val ARP_16 = mb2s("11.5 Créer un arpège multi octave", "136")

    // ---------------------------------------------------------------------
    // Module 5 — La patchbay
    // ---------------------------------------------------------------------
    private const val M5 = "mb2s-m5"

    private val m5l1 = ctLesson(
        M5, 1, "Lire la patchbay", listOf(PATCH_CONCEPTS, PRECABLAGE), 15,
        objective = "Reconnaître les entrées, les sorties et les connexions pré-câblées de la patchbay.",
        explanation = "La patchbay de 48 points est découpée en parties entourées d'une ligne blanche (FILTER, AMP, INVERTER…). " +
            "Une sortie a une case blanche avec texte ou dessin sous la prise ; une entrée n'a que des mots. On relie toujours une " +
            "sortie vers une entrée. Les sorties sont « à pleine échelle » : les curseurs de l'Osc Mixer ne règlent pas leur niveau. " +
            "Les inscriptions bleues sous les entrées indiquent le pré-câblage (par exemple KBD sous VCO 1 Pitch) : brancher un câble " +
            "dans cette entrée coupe la connexion par défaut.",
        controls = listOf(
            cmd("Prises de sortie (case blanche)", "Sources de signal.", PATCH_CONCEPTS),
            cmd("Prises d'entrée (mots seuls)", "Destinations.", PATCH_CONCEPTS),
            cmd("Inscriptions bleues", "Connexions pré-câblées, coupées par un câble.", PRECABLAGE),
        ),
        steps = listOf(
            "Repère sur la patchbay les lignes blanches qui délimitent chaque partie.",
            "Trouve trois sorties (case blanche) et trois entrées (mots seuls).",
            "Trouve l'entrée Pitch de la partie VCO 1 et lis son inscription bleue (KBD).",
            "Explique à voix haute ce qui se passe si tu y branches un câble.",
        ),
        expected = "Tu distingues entrées et sorties et tu sais qu'un câble coupe le pré-câblage.",
        exerciseTitle = "Carte de la patchbay",
        exercise = listOf(
            "Dessine les parties de la patchbay avec, pour chacune, une sortie et une entrée.",
            "Note les inscriptions bleues que tu trouves.",
        ),
        success = "Ton schéma distingue correctement entrées et sorties.",
        mistakes = listOf(
            "Relier deux entrées ou deux sorties : on relie toujours une sortie à une entrée.",
            "Croire que le curseur de l'Osc Mixer règle une sortie de la patchbay : elles sont à pleine échelle.",
        ),
        quiz = listOf(
            qz("Comment reconnaître une sortie ?", listOf("Une case blanche sous la prise", "Seulement des mots", "Une LED"), 0,
                "Les sorties ont une case blanche avec texte ou dessin.", PATCH_CONCEPTS),
            qz("Que signifie une inscription bleue sous une entrée ?", listOf("Une connexion pré-câblée", "Une fonction Shift"), 0,
                "Elle indique le routage d'entrée par défaut.", PRECABLAGE),
            qz("Que fait un câble branché dans l'entrée VCO 1 Pitch ?",
                listOf("Il coupe le suivi des pads (KBD) et impose sa source", "Rien"), 0,
                "Le câble interrompt la connexion pré-câblée.", PRECABLAGE),
            qz("Les curseurs de l'Osc Mixer règlent-ils les sorties d'ondes de la patchbay ?", listOf("Oui", "Non"), 1,
                "Les sorties sont à pleine échelle, indépendantes des curseurs.", PATCH_CONCEPTS),
        ),
    )

    private val m5l2 = ctLesson(
        M5, 2, "Premier câble : LFO vers filtre", listOf(LFO_FILTRE), 15,
        objective = "Moduler la coupure du filtre avec un LFO grâce à un câble.",
        explanation = "Exemple du manuel : tous les curseurs au minimum sauf l'onde carrée ; Cutoff et Resonance au centre ; FM du " +
            "filtre au centre ; mode BP. LFO 1 en sinus, Rate au centre. Un câble va de la sortie Out 1 (partie LFO 1&2) à l'entrée " +
            "Cutoff (partie FILTER). En jouant un pad, le timbre balaie comme un wah-wah ; Rate règle la vitesse, Resonance l'accentue.",
        controls = listOf(
            cmd("Sortie Out 1 (LFO 1&2)", "Signal du LFO 1.", LFO_FILTRE),
            cmd("Entrée Cutoff (FILTER)", "Module la coupure.", LFO_FILTRE),
            cmd("Rate (LFO 1)", "Vitesse du balayage.", LFO_FILTRE),
        ),
        steps = listOf(
            "Curseurs au minimum, onde carrée au maximum.",
            "Cutoff, Resonance et FM du filtre au centre, mode BP.",
            "LFO 1 : Wave sur sinus, Rate au centre.",
            "Branche un câble de Out 1 (LFO 1&2) vers Cutoff (FILTER).",
            "Joue un pad, puis change Rate et Resonance.",
        ),
        expected = "Le son balaie en rythme comme un wah-wah.",
        exerciseTitle = "Wah calé",
        exercise = listOf(
            "Passe le LFO 1 en Sync sur Seq et règle une vitesse à la croche.",
            "Joue une ligne de basse avec ce balayage.",
        ),
        success = "Le balayage tombe en rythme avec le tempo.",
        style = "Tribe : un filtre qui balaie en rythme donne du mouvement aux basses répétitives.",
        mistakes = listOf(
            "Brancher le câble dans la mauvaise direction (entrée vers entrée).",
            "Laisser Resonance au minimum : l'effet est beaucoup moins audible.",
        ),
        quiz = listOf(
            qz("Où brancher le LFO 1 pour moduler le filtre ?",
                listOf("De Out 1 (LFO 1&2) vers Cutoff (FILTER)", "De Cutoff vers Out 1", "Dans l'entrée Master"), 0,
                "Sortie Out 1 vers entrée Cutoff.", LFO_FILTRE),
            qz("Quel mode de filtre utilise l'exemple ?", listOf("BP", "LP", "Notch"), 0,
                "Le manuel règle le filtre en passe-bande (BP).", LFO_FILTRE),
            qz("Qu'est-ce qui règle la vitesse du balayage ?", listOf("Rate du LFO 1", "Cutoff", "Master Volume"), 0,
                "Le potentiomètre Rate accélère ou ralentit l'effet.", LFO_FILTRE),
            qz("Comment accentuer l'effet ?", listOf("Monter Resonance", "Baisser le volume", "Débrancher le câble"), 0,
                "Jouer avec la résonance accentue le wah-wah.", LFO_FILTRE),
        ),
    )

    private val m5l3 = ctLesson(
        M5, 3, "Doser une modulation : atténuateurs et vibrato", listOf(ATTENUATEURS, VIBRATO), 20,
        objective = "Limiter la force d'une modulation avec les atténuateurs et faire un vibrato sans câble.",
        explanation = "Une sortie de LFO branchée directement peut être trop forte. On la fait passer par un atténuateur : source dans " +
            "In 1 (ou In 2), puis Out 1 > Cutoff (ou Out 2 > Amp) vers la destination ; le potentiomètre Att 1 > Cutoff (ou Att 2 > Amp) " +
            "règle la quantité. Attention : ces potentiomètres gardent leur pré-câblage tant qu'on ne branche rien dans l'entrée " +
            "Cutoff (ou Amp) correspondante. Vibrato sans câble : VCO 2 en Range LFO, puis FM du VCO 1 ; Tune du VCO 2 règle la vitesse.",
        controls = listOf(
            cmd("In 1 / Out 1 > Cutoff", "Entrée et sortie de l'atténuateur 1.", ATTENUATEURS),
            cmd("Att 1 > Cutoff / Att 2 > Amp", "Quantité de modulation transmise.", ATTENUATEURS),
            cmd("Range LFO + FM (VCO 1)", "Vibrato pré-câblé par le VCO 2.", VIBRATO),
        ),
        steps = listOf(
            "Débranche tous les câbles.",
            "Mets Range du VCO 2 sur LFO, joue un pad et monte doucement FM du VCO 1 : vibrato.",
            "Tourne Tune du VCO 2 pour accélérer le vibrato.",
            "Branche Out 2 du LFO dans In 1, puis Out 1 > Cutoff vers une destination (par exemple Pitch 2).",
            "Règle la quantité avec Att 1 > Cutoff.",
        ),
        expected = "Le vibrato apparaît sans câble, et la modulation câblée devient dosable.",
        exerciseTitle = "Modulation dosée",
        exercise = listOf(
            "Fais passer un LFO par un atténuateur vers une destination de ton choix.",
            "Trouve le réglage le plus subtil encore audible.",
        ),
        success = "Tu passes d'une modulation imperceptible à une modulation forte en tournant un seul potentiomètre.",
        mistakes = listOf(
            "Oublier que Att 1 > Cutoff agit aussi sur la coupure tant que l'entrée Cutoff est libre.",
            "Mettre FM du VCO 1 trop haut avec Range sur Fine : on obtient du bruit plutôt qu'un vibrato.",
        ),
        quiz = listOf(
            qz("À quoi servent les atténuateurs ?", listOf("À limiter la force d'un signal de modulation", "À amplifier l'audio", "À accorder"), 0,
                "Ils « maîtrisent » le signal de sortie d'une source.", ATTENUATEURS),
            qz("Quel potentiomètre règle l'atténuateur 1 ?", listOf("Att 1 > Cutoff", "Att 2 > Amp", "Rate"), 0,
                "Att 1 > Cutoff règle la quantité de l'atténuateur 1.", ATTENUATEURS),
            qz("Comment obtenir un vibrato sans câble ?", listOf("Range VCO 2 sur LFO puis FM du VCO 1", "Brute Factor", "RM"), 0,
                "Le VCO 2 module le VCO 1 par la connexion pré-câblée FM.", VIBRATO),
            qz("Qu'est-ce qui règle la vitesse de ce vibrato ?", listOf("Tune du VCO 2", "Glide", "Cutoff"), 0,
                "Tourner Tune du VCO 2 accélère le vibrato.", VIBRATO),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 6 — Le séquenceur
    // ---------------------------------------------------------------------
    private const val M6 = "mb2s-m6"

    private val m6l1 = ctLesson(
        M6, 1, "Charger, lire et transposer un motif", listOf(SYNC_BTN, SEQ_ECOUTE, LIRE, CHARGER), 20,
        objective = "Lancer les motifs, les enchaîner et les transposer pendant la lecture.",
        explanation = "D'abord, Sync sur INT (sinon Play clignote vite). Load maintenu + un pad charge un motif (Tempo/Value change de " +
            "banque A–D) ; pads bleus = motifs avec données, éteints = vides, clignotant = actif. Play/Pause lance ou met en pause ; " +
            "Stop revient au début ; Shift + Play redémarre au début sans arrêter. Shift + Load bascule le changement instantané. " +
            "Pendant la lecture, maintiens le pad Transpose (pad 16) et touche un pad pour transposer (par rapport à Do).",
        controls = listOf(
            cmd("Sync", "INT pour l'horloge interne.", SYNC_BTN),
            cmd("Load + pad (+ Tempo/Value)", "Charge un motif, choix de banque.", CHARGER),
            cmd("Play/Pause, Stop, Shift + Play", "Lecture, arrêt, redémarrage.", LIRE),
            cmd("Transpose (pad 16) + pad", "Transposition du motif.", LIRE),
        ),
        steps = listOf(
            "Appuie sur Sync jusqu'à ce que la LED INT s'allume.",
            "Maintiens Load et appuie sur un pad allumé, puis Play.",
            "Maintiens Transpose et appuie sur le pad D# : le motif monte d'une tierce mineure ; reviens avec Transpose + C.",
            "Charge un autre motif pendant la lecture avec Load + pad.",
            "Appuie sur Shift + Play pour redémarrer au début.",
        ),
        expected = "Les motifs s'enchaînent et se transposent sans arrêter la musique.",
        exerciseTitle = "Grille de transposition",
        exercise = listOf(
            "Fais tourner une basse et transpose-la sur quatre mesures : C, C, D#, F.",
            "Reviens à C pour finir.",
        ),
        success = "La basse suit ta grille sans décalage.",
        style = "Acidcore : transposer une seule ligne acide suffit à faire une progression.",
        mistakes = listOf(
            "Oublier Sync sur INT : le séquenceur attend une horloge externe.",
            "Charger un motif d'une autre banque sans sauver : les changements non sauvés de la banque en cours sont perdus.",
        ),
        quiz = listOf(
            qz("Comment charger un motif ?", listOf("Maintenir Load et appuyer sur un pad", "Shift + pad", "Save + pad"), 0,
                "Load maintenu + le pad du motif.", CHARGER),
            qz("Que fait Stop ?", listOf("Met en pause", "Arrête et revient au début du motif", "Efface le motif"), 1,
                "Stop réinitialise le séquenceur au début du motif.", LIRE),
            qz("Comment transposer d'une tierce mineure ?", listOf("Transpose + D#", "Shift + D#", "Load + D#"), 0,
                "La transposition est relative à Do : D# = tierce mineure.", LIRE),
            qz("Que fait Shift + Load ?", listOf("Active/désactive le changement instantané", "Sauve le motif", "Efface la banque"), 0,
                "Instant Change : le motif suivant démarre au prochain pas au lieu d'attendre la fin.", CHARGER),
        ),
    )

    private val m6l2 = ctLesson(
        M6, 2, "Enregistrer en temps réel ou pas à pas", listOf(TEMPS_REEL, PAS_A_PAS, ENREG, POTARDS_PAS, EDITION_REEL), 25,
        objective = "Enregistrer une ligne en jouant, ou l'écrire pas à pas avec les potentiomètres de pas.",
        explanation = "Temps réel : Record + Play (Record bleu), métronome avec Shift + Sync ; ce que tu joues est quantifié sur la " +
            "division temporelle, avec la vélocité et la pression. Un appui très bref sur Erase efface les pas joués pendant l'appui. " +
            "Pas à pas : Record seul (Record rouge) ; les pads activent ou coupent les pas, et le potentiomètre au-dessus de chaque " +
            "pas règle sa note (le premier cran fait entendre la note, le suivant la change). Record ou Stop pour sortir.",
        controls = listOf(
            cmd("Record + Play", "Enregistrement en temps réel (bleu).", ENREG),
            cmd("Record (seul)", "Enregistrement pas à pas (rouge).", ENREG),
            cmd("Potentiomètres de pas 1–16", "Note (ou autre valeur) de chaque pas.", POTARDS_PAS),
            cmd("Erase (très bref)", "Efface les pas joués pendant l'appui.", EDITION_REEL),
        ),
        steps = listOf(
            "Charge un motif vide, active le métronome avec Shift + Sync.",
            "Maintiens Record, appuie sur Play et joue une basse sur les pads ; Record pour sortir.",
            "Charge un autre motif vide, appuie sur Record seul (rouge) puis Play.",
            "Active quelques pas avec les pads, puis tourne le potentiomètre au-dessus de chacun pour choisir la note.",
            "Sauve avec Save + le pad qui clignote.",
        ),
        expected = "Une ligne jouée et une ligne écrite pas à pas tournent, et l'une est sauvegardée.",
        exerciseTitle = "Ligne acide pas à pas",
        exercise = listOf(
            "Écris 16 pas d'une ligne de basse en choisissant chaque note au potentiomètre.",
            "Laisse quatre pas éteints pour le groove.",
        ),
        success = "La ligne tourne avec les silences prévus.",
        style = "Acidcore : écris la ligne pas à pas, puis joue le filtre en direct.",
        mistakes = listOf(
            "Confondre les couleurs : bleu = temps réel, rouge = pas à pas.",
            "Charger un autre motif avant de sauver : le nouveau motif est perdu.",
        ),
        quiz = listOf(
            qz("Quelle couleur de Record indique l'enregistrement en temps réel ?", listOf("Bleu", "Rouge", "Violet"), 0,
                "Bleu = temps réel, rouge = pas à pas.", PAS_A_PAS),
            qz("En pas à pas, comment choisir la note d'un pas ?",
                listOf("Tourner le potentiomètre au-dessus du pas", "Taper le pad plus fort", "Shift + pad"), 0,
                "La hauteur de chaque pas s'entre avec le potentiomètre de pas.", PAS_A_PAS),
            qz("Comment activer le métronome ?", listOf("Shift + Sync", "Shift + Tap", "Record + Sync"), 0,
                "Shift + Sync active ou coupe le métronome.", TEMPS_REEL),
            qz("En temps réel, comment effacer quelques pas ratés ?",
                listOf("Appui très bref sur Erase au moment voulu", "Stop × 3", "Shift + Erase"), 0,
                "Les pas joués pendant l'appui sur Erase sont effacés.", EDITION_REEL),
        ),
    )

    private val m6l3 = ctLesson(
        M6, 3, "Sauvegarder, copier et enchaîner", listOf(RAM_FLASH, SAUVER, REVENIR, COPIER, CHAINE), 20,
        objective = "Ne rien perdre : comprendre la mémoire, sauver, revenir en arrière, copier et faire une chaîne.",
        explanation = "64 motifs en 4 banques, mais une seule banque est en mémoire de travail (RAM) : charger un motif d'une autre " +
            "banque efface les changements non sauvés. Save maintenu montre les motifs modifiés en violet ; appuie sur chacun pour " +
            "les sauver. Shift + Save (Revert) revient à la version sauvée du motif en cours. Copy + pad copie un motif, Paste + pad " +
            "le colle (Tempo/Value pour une autre banque). Erase + pad efface. Chain maintenu + pads crée une chaîne de 16 motifs " +
            "maximum dans la banque ; Play la joue.",
        controls = listOf(
            cmd("Save + pads violets", "Sauve les motifs modifiés.", SAUVER),
            cmd("Shift + Save", "Revert : revient à la version sauvée.", REVENIR),
            cmd("Copy / Paste / Erase + pad", "Copier, coller, effacer un motif.", COPIER),
            cmd("Chain + pads", "Chaîne de motifs (16 max, même banque).", CHAINE),
        ),
        steps = listOf(
            "Modifie deux motifs de la banque, maintiens Save et regarde les pads violets.",
            "Appuie sur chaque pad violet, puis relâche Save.",
            "Modifie un motif puis annule avec Shift + Save.",
            "Copie un motif avec Copy + pad, puis colle-le avec Paste + un pad éteint.",
            "Maintiens Chain et appuie sur 1, 1, 2, 3 ; relâche et lance Play.",
        ),
        expected = "Tes motifs sont sauvés, copiés et enchaînés dans l'ordre voulu.",
        exerciseTitle = "Structure en chaîne",
        exercise = listOf(
            "Crée quatre variantes d'un motif par copier-coller.",
            "Enchaîne-les dans une chaîne intro, thème, break, thème, et sauve tout.",
        ),
        success = "La chaîne joue ta structure et les motifs sont toujours là après extinction.",
        mistakes = listOf(
            "Charger un motif d'une autre banque avant de sauver : tous les changements de la banque sont perdus.",
            "Vouloir sauver un motif ailleurs avec Save : on ne sauve un motif que sur lui-même, sinon Copy/Paste.",
        ),
        quiz = listOf(
            qz("Combien de banques sont en mémoire de travail à la fois ?", listOf("1", "2", "4"), 0,
                "Une seule banque de 16 motifs est en RAM.", RAM_FLASH),
            qz("Quelle couleur montrent les motifs modifiés non sauvés quand Save est maintenu ?", listOf("Bleu", "Violet", "Rouge"), 1,
                "Violet = modifié par rapport à la sauvegarde.", SAUVER),
            qz("Comment revenir à la version sauvée du motif en cours ?", listOf("Shift + Save", "Load + même pad", "Stop × 3"), 0,
                "Shift + Save (Revert) ; recharger avec Load ne fonctionne pas.", REVENIR),
            qz("Une chaîne peut-elle mélanger plusieurs banques ?", listOf("Oui", "Non"), 1,
                "La chaîne se construit avec les 16 motifs de la banque chargée.", CHAINE),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 7 — Pistes avancées et arpégiateur
    // ---------------------------------------------------------------------
    private const val M7 = "mb2s-m7"

    private val m7l1 = ctLesson(
        M7, 1, "Quatre pistes, gate et répétitions", listOf(PISTES, GATE, MUTE_DIV, POTARDS_PAS), 20,
        objective = "Utiliser les pistes Pitch, Gate, Velo/Mod 1 et Press/Mod 2, la durée de gate et les répétitions de pas.",
        explanation = "Chaque motif a quatre pistes : Pitch et Gate (liées), Velo/Mod 1 et Press/Mod 2 (configurables : maintenir leur " +
            "bouton et tourner Tempo/Value change le type, par exemple Env ou LFO). Sur la piste Gate, le potentiomètre de pas règle " +
            "la durée (1 à 99, ou Tie qui lie au pas suivant) ; Shift + potentiomètre donne la répétition du pas (1 à 4). Sur Pitch, " +
            "Shift + potentiomètre règle le Slide. Shift + bouton de piste coupe la piste. Shift + pads 5 à 8 choisit la division (1/4 à 1/32).",
        controls = listOf(
            cmd("Track Select (Pitch, Gate, Velo/Mod 1, Press/Mod 2)", "Choix de la piste éditée.", PISTES),
            cmd("Potentiomètre de pas (piste Gate)", "Durée 1–99 ou Tie.", GATE),
            cmd("Shift + potentiomètre de pas", "Fonction secondaire : Step Repeat, Slide…", POTARDS_PAS),
            cmd("Shift + bouton de piste / Shift + pads 5–8", "Sourdine / division temporelle.", MUTE_DIV),
        ),
        steps = listOf(
            "En mode pas à pas (Record rouge), sélectionne la piste Gate.",
            "Raccourcis la durée de quelques pas et mets un pas sur Tie.",
            "Avec Shift, tourne le potentiomètre d'un pas pour lui donner 3 répétitions.",
            "Sélectionne la piste Pitch et mets du Slide sur un pas avec Shift + potentiomètre.",
            "Coupe la piste Pitch/Gate avec Shift + son bouton, puis relance-la.",
        ),
        expected = "La ligne a des notes courtes, liées, répétées et glissées.",
        exerciseTitle = "Basse qui roule",
        exercise = listOf(
            "Sur une basse de 16 pas, mets des répétitions de 2 et 3 sur quatre pas.",
            "Ajoute du Slide sur deux notes.",
        ),
        success = "La basse roule et glisse comme prévu.",
        style = "Tribecore : les répétitions de pas donnent des basses qui roulent.",
        mistakes = listOf(
            "Chercher la répétition sur la piste Pitch : elle est sur la piste Gate (Shift + potentiomètre).",
            "Oublier que couper Pitch coupe aussi Gate.",
        ),
        quiz = listOf(
            qz("Combien de pistes par motif ?", listOf("2", "4", "8"), 1,
                "Pitch, Gate, Velo/Mod 1 et Press/Mod 2.", PISTES),
            qz("Que fait la valeur Tie sur la piste Gate ?", listOf("Lie le pas au suivant", "Coupe le pas", "Double la vitesse"), 0,
                "Le gate reste ouvert jusqu'au prochain pas réglé entre 1 et 99.", GATE),
            qz("Où règle-t-on la répétition d'un pas ?", listOf("Shift + potentiomètre sur la piste Gate", "Piste Pitch", "Load"), 0,
                "La fonction secondaire de la piste Gate est Step Repeat (1 à 4).", GATE),
            qz("Comment mettre une piste en sourdine ?", listOf("Shift + son bouton de piste", "Erase + piste", "Stop"), 0,
                "Shift + bouton Track Select ; la LED de la piste s'éteint.", MUTE_DIV),
        ),
    )

    private val m7l2 = ctLesson(
        M7, 2, "Longueur, gammes, sens et swing", listOf(LONGUEUR, PAGES, GAMMES, SENS, SWING, TEMPO), 20,
        objective = "Allonger un motif jusqu'à 64 pas et le faire varier par la gamme, le sens de lecture et le swing.",
        explanation = "Last Step + pad fixe une longueur de moins de 16 pas ; Last Step + bouton de page 32/48/64 ajoute des pages " +
            "vides, Shift + bouton de page recopie le début. Shift + Last Step active Page Follow. Shift + pads 9 à 16 choisit la gamme " +
            "(Chromatique, Majeure, Mineure, Dorienne, Mixolydienne, Mineure harmonique, Blues, Utilisateur) : elle filtre les notes " +
            "jouées sans modifier celles enregistrées. Shift + pads 1 à 4 : sens Fwd, Rev, Alt, Rand. Shift + Tempo/Value : swing de 50 à 75 %. Tempo de 30 à 240 BPM.",
        controls = listOf(
            cmd("Last Step + pad / bouton de page", "Longueur du motif.", LONGUEUR),
            cmd("Shift + bouton de page", "Prolonge en recopiant.", LONGUEUR),
            cmd("Shift + pads 9–16", "Gammes.", GAMMES),
            cmd("Shift + pads 1–4", "Sens de lecture.", SENS),
            cmd("Shift + Tempo/Value", "Swing.", SWING),
        ),
        steps = listOf(
            "Sur un motif de 16 pas, appuie sur Shift + bouton 32 : il passe à 32 pas recopiés.",
            "Active Page Follow avec Shift + Last Step et lance Play.",
            "Choisis la gamme Mineure avec Shift + un pad de gamme, puis reviens en Chromatique.",
            "Essaie les sens Rev puis Alt avec Shift + pads 2 et 3.",
            "Monte le swing avec Shift + Tempo/Value.",
        ),
        expected = "Le motif dure deux mesures et change de couleur avec la gamme, le sens et le swing.",
        exerciseTitle = "Une ligne, quatre versions",
        exercise = listOf(
            "À partir d'une même ligne, crée quatre versions : Majeure, Mineure, sens Rev et sens Alt.",
            "Reviens à la version d'origine (Chromatique, Fwd).",
        ),
        success = "Tu passes d'une version à l'autre et tu retrouves l'original.",
        style = "Mentalcore : la gamme Mineure harmonique donne vite une couleur tendue.",
        mistakes = listOf(
            "Croire que la gamme modifie les notes enregistrées : elle ne fait que filtrer la lecture.",
            "Confondre allonger (pages vides) et prolonger (copie du début).",
        ),
        quiz = listOf(
            qz("Comment prolonger un motif en recopiant son début ?", listOf("Shift + bouton de page", "Last Step + bouton de page", "Copy + page"), 0,
                "Shift + bouton de page copie les pas existants ; Last Step + page ajoute des pas vides.", LONGUEUR),
            qz("Une gamme change-t-elle les notes enregistrées ?", listOf("Oui", "Non, elle filtre la lecture"), 1,
                "Le changement de gamme est réversible : les notes d'origine sont gardées.", GAMMES),
            qz("Que fait le sens Alt ?", listOf("Avant puis arrière", "Aléatoire", "Toujours arrière"), 0,
                "Alternate : 1, 2, 3, 4 | 4, 3, 2, 1…", SENS),
            qz("Quelle est la plage du swing ?", listOf("50–75 %", "0–100 %", "60–90 %"), 0,
                "De 50 % (pas de swing) à 75 %.", SWING),
        ),
    )

    private val m7l3 = ctLesson(
        M7, 3, "L'arpégiateur", listOf(ARP_INTRO, ARP, ARP_MODES, ARP_16), 20,
        objective = "Jouer des arpèges, choisir leur mode, les tenir et les étendre sur plusieurs octaves.",
        explanation = "Sync sur INT, puis le bouton On (partie Arp/Loop) active l'arpégiateur ; il faut l'activer avant de tenir les " +
            "pads. Maintenir On (Arp/Loop) et tourner Tempo/Value choisit le mode : Up, Down, Inclusive, Exclusive, Random, Order, " +
            "Up x2, Down x2. Shift + Arp/Loop active Hold (On clignote) : l'arpège continue sans tenir les pads, et tant qu'un pad " +
            "reste enfoncé on peut ajouter des notes, même sur d'autres octaves avec Oct ±, jusqu'à 16. La division (Shift + pads " +
            "5–8) est partagée avec le séquenceur. Le gate de l'arpégiateur est fixé à 50 %.",
        controls = listOf(
            cmd("On (Arp/Loop)", "Active l'arpégiateur.", ARP),
            cmd("Arp/Loop maintenu + Tempo/Value", "Mode d'arpège.", ARP),
            cmd("Shift + Arp/Loop", "Hold.", ARP),
            cmd("Oct − / Oct + (en Hold)", "Ajouter des notes sur d'autres octaves.", ARP_16),
        ),
        steps = listOf(
            "Vérifie Sync sur INT, appuie sur On, puis tiens trois pads : l'arpège joue.",
            "Maintiens Arp/Loop et tourne Tempo/Value pour essayer Up, Down et Random.",
            "Active Hold avec Shift + Arp/Loop et relâche les pads : l'arpège continue.",
            "Garde un pad enfoncé, appuie sur Oct + et ajoute des notes plus aiguës.",
            "Coupe avec On (ou Stop).",
        ),
        expected = "L'arpège tourne seul, dans le mode choisi, sur plusieurs octaves.",
        exerciseTitle = "Arpège de transe",
        exercise = listOf(
            "Crée un arpège de 6 notes sur deux octaves en mode Up x2.",
            "Joue avec le Cutoff et la résonance pendant qu'il tourne en Hold.",
        ),
        success = "L'arpège tourne seul pendant que tu sculptes le son.",
        style = "Mentalcore : un arpège rapide en Hold + filtre résonant, effet garanti.",
        mistakes = listOf(
            "Tenir les pads avant d'appuyer sur On : l'arpégiateur ne joue rien.",
            "Vouloir transposer l'arpège actif : il faut l'enregistrer dans un motif puis transposer le motif.",
        ),
        quiz = listOf(
            qz("Comment choisir le mode d'arpège ?", listOf("Maintenir Arp/Loop et tourner Tempo/Value", "Shift + pads 1–4", "Load + pad"), 0,
                "On maintient le bouton Arp/Loop et on tourne Tempo/Value.", ARP),
            qz("Comment activer Hold ?", listOf("Shift + Arp/Loop", "Arp/Loop × 2", "Shift + Play"), 0,
                "Shift + Arp/Loop ; le bouton On clignote.", ARP),
            qz("Combien de notes au maximum dans un arpège ?", listOf("8", "16", "32"), 1,
                "Jusqu'à 16 notes avec Hold et les boutons Oct.", ARP_16),
            qz("Quelle est la durée de gate de l'arpégiateur ?", listOf("Réglable par pas", "Fixée à 50 %"), 1,
                "Le gate de l'arpégiateur est fixé à 50 %.", mb2s("8.11 Durée de Gate : Seq vs Arp", "93")),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(
            M5, 5, "La patchbay",
            "Entrées, sorties, pré-câblage, LFO vers filtre, atténuateurs et vibrato.",
            "Lire la patchbay et créer ses premières modulations câblées.",
            "Les modulations câblées donnent des sons évolutifs impossibles autrement.",
            listOf(m5l1, m5l2, m5l3),
        ),
        CourseModule(
            M6, 6, "Le séquenceur",
            "Charger, lire, transposer, enregistrer, sauvegarder et chaîner.",
            "Enregistrer, sauver et enchaîner tes motifs.",
            "Une ligne acide séquencée + le filtre en direct = la base de l'acidcore.",
            listOf(m6l1, m6l2, m6l3),
        ),
        CourseModule(
            M7, 7, "Pistes avancées et arpégiateur",
            "Pistes, gate, répétitions, longueur, gammes, sens, swing et arpégiateur.",
            "Faire varier une séquence et jouer des arpèges en direct.",
            "Répétitions, gammes tendues et arpèges rapides servent la tribecore et la mentalcore.",
            listOf(m7l1, m7l2, m7l3),
        ),
    )
}
