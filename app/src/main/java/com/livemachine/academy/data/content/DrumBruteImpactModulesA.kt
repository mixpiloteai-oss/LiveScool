package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** DrumBrute Impact — modules 1 à 4 : prise en main, tempo et enregistrement, instruments, Color et accents. */
internal object DrumBruteImpactModulesA {

    // Références du manuel (pages imprimées ; page du PDF = page imprimée + 6)
    private val CONNEXIONS = dbi("1.1 Établir les connexions", "4–5")
    private val FACADE_HAUT = dbi("1.2 Transport, motifs, effets", "6–7")
    private val SORTIE = dbi("1.2.10 Circuit de sortie", "11")
    private val PADS = dbi("1.4 Instruments, pads, Color", "15–16")
    private val ARRIERE = dbi("1.5–1.6 Panneau arrière", "17–19")
    private val SAVE_BTN = dbi("1.2.7 Boutons Copy & Erase / Save", "9")
    private val MOTIFS = dbi("2.1.1–2.1.2 Sélectionner une banque, un motif", "20")
    private val AT_END = dbi("2.2.2 Options de configuration", "24")
    private val ALL_NOTES_OFF = dbi("2.4 All Notes Off", "27")
    private val SAUVER = dbi("2.1.6 Sauvegardez ce que vous avez fait !", "22")
    private val RESTAURER = dbi("4.3–4.4 Restaurer et sauvegarder le motif", "57")
    private val TEMPO = dbi("2.1.3 Régler le tempo", "21")
    private val METRO = dbi("2.1.4 Activer le métronome", "21")
    private val TEMPO_FIN = dbi("4.1.1 Contrôler le tempo", "38")
    private val SYNC = dbi("8. Synchronisation", "73–74")
    private val ENREG = dbi("4.1.3 Enregistrer un motif", "40")
    private val ENREG_RAPIDE = dbi("2.1.5 Enregistrer un motif", "22")
    private val EFFACER_LIVE = dbi("4.1.6 Suppression localisée", "43")
    private val DIVISION = dbi("4.1.2 Time Division", "39")
    private val MUTE_SOLO = dbi("4.1.4 Fonctions Mute/Solo", "41–42")
    private val PARTAGES = dbi("3.1 Concepts généraux", "28")
    private val QUATRE_SONS = dbi("3.1.5 Chaque instrument a quatre sons", "29")
    private val PARAMS_1 = dbi("3.2.1–3.2.4 Kick, Snare 1, Snare 2, Toms", "30")
    private val PARAMS_2 = dbi("3.2.5–3.2.7 Cymbal, Cowbell, Hi-Hats", "31")
    private val FM = dbi("3.2.8–3.2.9 FM Drum, qu'est-ce que la FM ?", "32–33")
    private val COLOR_TABLE = dbi("3.3–3.4 Le mode Color, tableau", "34")
    private val COLOR_INSTR = dbi("3.5–3.6 Mode Color : niveau instrument et piste", "35")
    private val UN_INSTR_4_SONS = dbi("3.7 Un instrument, quatre sons", "36")
    private val STEP_ACCENT = dbi("4.2.1–4.2.2 Modes Step et Accent", "47")
    private val COLOR_PHASES = dbi("4.2.3–4.2.5 Mode Color, phases rouge et bleue, sélection", "48")

    // ---------------------------------------------------------------------
    // Module 1 — Prise en main
    // ---------------------------------------------------------------------
    private const val M1 = "dbi-m1"

    private val m1l1 = ctLesson(
        M1, 1, "Façade, sorties et alimentation", listOf(CONNEXIONS, ARRIERE, SORTIE, FACADE_HAUT), 15,
        objective = "Brancher la DrumBrute Impact, repérer ses zones et comprendre comment le son sort de la machine.",
        explanation = "L'Impact ne s'alimente pas par l'USB : il faut l'adaptateur DC 12 V fourni et l'interrupteur à l'arrière. " +
            "Le son sort par la prise Mix et le casque, dont le niveau est réglé par le potentiomètre Level. Les sorties " +
            "individuelles (Kick, Snares, Hi Hats, FM Drum) retirent l'instrument branché du mix et du casque. Le potentiomètre " +
            "Distortion sature tout le mix ; son bouton l'active ou le coupe, mais il n'agit pas sur les sorties individuelles.",
        controls = listOf(
            cmd("DC 12 V et interrupteur", "Alimentation (l'USB n'alimente pas la machine).", ARRIERE),
            cmd("Sortie Mix et casque", "Sortie principale ; même signal au casque.", ARRIERE),
            cmd("Level (Output)", "Niveau de la sortie Mix et du casque.", SORTIE),
            cmd("Distortion + bouton", "Saturation du mix global, marche/arrêt.", SORTIE),
            cmd("Sorties individuelles", "Kick, Snares, Hi Hats, FM Drum séparés.", ARRIERE),
        ),
        steps = listOf(
            "Branche l'adaptateur DC fourni, puis relie la sortie Mix à ta table ou à tes enceintes (ou un casque).",
            "Mets l'interrupteur arrière sur On.",
            "Monte doucement le potentiomètre Level et frappe quelques pads.",
            "Active la distorsion avec son bouton et tourne le potentiomètre Distortion : écoute le mix se saturer.",
            "Coupe la distorsion pour revenir au son propre.",
        ),
        expected = "La machine sonne par la sortie Mix et tu entends la distorsion agir sur tout le mix.",
        exerciseTitle = "Repérage de la façade",
        exercise = listOf(
            "Montre la zone transport, la zone des instruments, les pads et la bande tactile.",
            "Dis quelles sorties individuelles existent et ce qui se passe quand on y branche un câble.",
        ),
        success = "Tu cites les quatre sorties individuelles et tu sais qu'elles retirent l'instrument du mix.",
        style = "Hardtek : la distorsion du mix donne vite un son de free party, à doser.",
        mistakes = listOf(
            "Brancher seulement l'USB : la machine ne s'allume pas sans son adaptateur.",
            "Se demander pourquoi le kick a disparu du mix : un câble est branché sur sa sortie individuelle.",
        ),
        quiz = listOf(
            qz("La DrumBrute Impact peut-elle s'alimenter par l'USB ?", listOf("Oui", "Non, il faut l'adaptateur DC fourni"), 1,
                "Le manuel précise que l'Impact ne s'alimente pas par le port USB.", CONNEXIONS),
            qz("Que se passe-t-il quand on branche un câble sur la sortie individuelle Kick ?",
                listOf("Rien", "Le kick est retiré de la sortie Mix et du casque", "Le kick est doublé"), 1,
                "Une sortie individuelle branchée retire ce canal des sorties Mix et casque.", ARRIERE),
            qz("La distorsion agit-elle sur les sorties individuelles ?", listOf("Oui", "Non"), 1,
                "La distorsion est en fin de chaîne du mix et n'affecte pas les sorties individuelles.", SORTIE),
            qz("Que règle le potentiomètre Level de la partie Output ?",
                listOf("Le volume du kick", "Le niveau de la sortie Mix et du casque", "Le volume du métronome"), 1,
                "Il règle le niveau de sortie maître, casque compris.", SORTIE),
        ),
    )

    private val m1l2 = ctLesson(
        M1, 2, "Banques, motifs et transport", listOf(MOTIFS, FACADE_HAUT, AT_END, ALL_NOTES_OFF), 15,
        objective = "Choisir une banque et un motif, lancer et arrêter la lecture, et décider quand le motif suivant démarre.",
        explanation = "L'Impact contient 64 motifs : 4 banques (A–D) de 16. Bank puis un des boutons de Pas 1–4 choisit la banque ; " +
            "Ptrn puis un bouton de Pas 1–16 choisit le motif. Pendant la lecture, le motif change tout de suite ou attend la fin du " +
            "motif en cours : Shift + Pas 14 (At End) bascule ce choix. Play sert aussi de pause, Stop arrête. Trois appuis rapides " +
            "sur Stop envoient « All Notes Off » si une note MIDI reste bloquée.",
        controls = listOf(
            cmd("Bank + Pas 1–4", "Choisit la banque A, B, C ou D.", MOTIFS),
            cmd("Ptrn + Pas 1–16", "Choisit le motif.", MOTIFS),
            cmd("Play / Stop", "Lecture-pause / arrêt.", FACADE_HAUT),
            cmd("Shift + Pas 14", "At End : le changement attend la fin du motif.", AT_END),
        ),
        steps = listOf(
            "Appuie sur Bank puis sur le Pas 1 : banque A.",
            "Appuie sur Ptrn puis sur le Pas 1, et lance avec Play.",
            "Choisis le motif 2 pendant la lecture et note quand il démarre.",
            "Maintiens Shift et appuie sur le Pas 14 pour changer le réglage At End, puis recommence le changement de motif.",
            "Appuie sur Play pour mettre en pause, puis sur Stop.",
        ),
        expected = "Tu changes de motif à l'instant voulu : tout de suite ou à la fin du motif en cours.",
        exerciseTitle = "Enchaînement propre",
        exercise = listOf(
            "Active At End (le Pas 14 doit être allumé quand tu maintiens Shift).",
            "Enchaîne quatre motifs de la banque A en les choisissant pendant la lecture.",
        ),
        success = "Chaque nouveau motif démarre exactement à la fin du précédent.",
        mistakes = listOf(
            "Oublier d'appuyer sur Ptrn après avoir choisi la banque.",
            "Vouloir changer de motif pendant l'enregistrement : la sélection est impossible en cours d'enregistrement.",
        ),
        quiz = listOf(
            qz("Combien de motifs contient l'Impact ?", listOf("16", "64", "128"), 1,
                "64 motifs, en 4 banques de 16.", MOTIFS),
            qz("Que fait Shift + Pas 14 ?", listOf("Active At End", "Active le BPM global", "Active la polyrythmie"), 0,
                "Shift + 14 bascule la fonction At End (changement en fin de motif).", AT_END),
            qz("Comment envoyer un « All Notes Off » ?", listOf("Trois appuis rapides sur Stop", "Shift + Play", "Save + Stop"), 0,
                "Trois appuis rapides sur Stop envoient la commande All Notes Off.", ALL_NOTES_OFF),
            qz("Que fait un appui sur Play pendant la lecture ?", listOf("Pause", "Changement de banque", "Enregistrement"), 0,
                "Le bouton Play sert aussi de bouton Pause.", FACADE_HAUT),
        ),
    )

    private val m1l3 = ctLesson(
        M1, 3, "Sauvegarder et restaurer", listOf(SAUVER, SAVE_BTN, RESTAURER), 15,
        objective = "Ne jamais perdre un motif : savoir quand et comment sauvegarder, et revenir à la dernière sauvegarde.",
        explanation = "Les modifications ne sont pas gardées automatiquement : elles sont perdues à l'extinction, et aussi si tu " +
            "changes de banque ou passes en mode Song. Save + Ptrn sauve le motif en cours, Save + Bank sauve la banque entière, " +
            "Save + Song le morceau. Pour annuler des changements non sauvés : Shift + Ptrn restaure le motif, Shift + Bank la banque " +
            "(à faire avant toute sauvegarde, copie ou effacement, qui réécrivent la mémoire).",
        controls = listOf(
            cmd("Save + Ptrn", "Sauve le motif en cours.", SAUVER),
            cmd("Save + Bank", "Sauve les 16 motifs de la banque.", SAVE_BTN),
            cmd("Shift + Ptrn / Shift + Bank", "Restaure le motif / la banque sauvegardés.", RESTAURER),
        ),
        steps = listOf(
            "Modifie un motif (ajoute quelques coups).",
            "Maintiens Save et appuie sur Ptrn : les deux boutons clignotent.",
            "Modifie à nouveau le motif, puis maintiens Shift et appuie sur Ptrn : le motif revient à la version sauvée.",
            "Fais la même chose au niveau de la banque avec Save + Bank.",
        ),
        expected = "Le motif sauvegardé revient tel quel, même après extinction.",
        exerciseTitle = "Test d'extinction",
        exercise = listOf(
            "Crée un motif, sauve-le avec Save + Ptrn.",
            "Éteins puis rallume l'Impact et vérifie qu'il est là.",
            "Recommence sans sauver et constate la perte.",
        ),
        success = "Le motif sauvé est intact ; le motif non sauvé est perdu.",
        mistakes = listOf(
            "Changer de banque avant de sauver : les modifications de la banque d'origine sont perdues.",
            "Restaurer après avoir copié ou effacé : la mémoire a déjà été réécrite.",
        ),
        quiz = listOf(
            qz("Comment sauver le motif en cours ?", listOf("Save + Ptrn", "Shift + Ptrn", "Save + Play"), 0,
                "Maintenir Save et appuyer sur Ptrn.", SAUVER),
            qz("Que se passe-t-il si tu passes à une autre banque sans sauver ?",
                listOf("Rien", "Les modifications de la banque d'origine sont perdues"), 1,
                "Le manuel avertit que changer de banque fait perdre les modifications non sauvées.", RESTAURER),
            qz("Que fait Shift + Ptrn ?", listOf("Sauve le motif", "Restaure la dernière sauvegarde du motif", "Efface le motif"), 1,
                "Shift + Ptrn ramène le motif à sa dernière version sauvegardée.", RESTAURER),
            qz("Comment sauver une banque complète en une fois ?", listOf("Save + Bank", "Copy + Bank", "Shift + Bank"), 0,
                "Save + Bank sauvegarde les 16 motifs de la banque.", SAVE_BTN),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 2 — Tempo et enregistrement
    // ---------------------------------------------------------------------
    private const val M2 = "dbi-m2"

    private val m2l1 = ctLesson(
        M2, 1, "Tempo, métronome et synchronisation", listOf(TEMPO, TEMPO_FIN, METRO, SYNC), 20,
        objective = "Régler le tempo, utiliser le métronome et choisir si l'Impact est maître ou esclave.",
        explanation = "En horloge interne, le tempo va de 30 à 300 BPM : potentiomètre Rate/Fine, Tap Tempo, et Shift + Rate/Fine " +
            "pour les décimales. Chaque motif garde son tempo, sauf si Shift + Pas 15 (Global BPM) impose le même à tous. Le métronome " +
            "s'active avec On, son volume avec Metro Volume ; maintenir On et choisir un pas règle sa valeur (1/8 à 1/32). Le bouton " +
            "Sync fait défiler INT, USB, MIDI et CLK ; machine à l'arrêt, Sync maintenu + Rate/Fine choisit le format d'horloge (1P, 2P, 24P, 48P).",
        controls = listOf(
            cmd("Rate/Fine (+ Shift)", "Tempo ; avec Shift, décimales.", TEMPO_FIN),
            cmd("Tap Tempo", "Tempo tapé à la main.", TEMPO_FIN),
            cmd("Shift + Pas 15", "Global BPM pour tous les motifs.", TEMPO),
            cmd("On / Metro Volume", "Métronome et volume du clic.", METRO),
            cmd("Sync", "Horloge INT, USB, MIDI ou CLK.", SYNC),
        ),
        steps = listOf(
            "Vérifie que la LED INT est allumée (bouton Sync).",
            "Règle 160 BPM avec Rate/Fine, puis ajuste les décimales en maintenant Shift.",
            "Tape le tempo sur Tap Tempo pour essayer.",
            "Active le métronome avec On et règle son volume.",
            "Maintiens On et choisis la valeur de clic 1/8, puis relâche.",
        ),
        expected = "Le tempo affiché correspond à ton réglage et le métronome clique à la valeur choisie.",
        exerciseTitle = "Tempo commun",
        exercise = listOf(
            "Donne un tempo différent à deux motifs et sauve-les.",
            "Active Global BPM et vérifie que les deux motifs jouent maintenant au même tempo.",
        ),
        success = "Avec Global BPM, les deux motifs gardent le même tempo quand tu passes de l'un à l'autre.",
        style = "Hardtek / tribe : entre 150 et 180 BPM.",
        mistakes = listOf(
            "Utiliser Tap Tempo en synchro externe : il ne fonctionne qu'en horloge interne.",
            "Changer la synchro pendant la lecture : c'est impossible, arrête d'abord.",
        ),
        quiz = listOf(
            qz("Quelle est la plage de tempo en horloge interne ?", listOf("60–200 BPM", "30–300 BPM", "20–999 BPM"), 1,
                "Avec Sync sur Internal, la plage est de 30 à 300 BPM.", TEMPO),
            qz("Que fait Shift + Pas 15 ?", listOf("Global BPM", "At End", "Roller/Looper"), 0,
                "Shift + 15 active le tempo global pour tous les motifs et morceaux.", TEMPO),
            qz("Comment régler la valeur du clic du métronome ?",
                listOf("Maintenir On et choisir un bouton de pas", "Tourner Rate/Fine", "Shift + Metro"), 0,
                "On maintenu + un pas entre 1/8 et 1/32 ; le bouton choisi devient bleu.", METRO),
            qz("Quel format d'horloge est réglé par défaut ?", listOf("1P", "24P", "48P"), 1,
                "La fréquence par défaut est 24P (DIN sync standard).", SYNC),
        ),
    )

    private val m2l2 = ctLesson(
        M2, 2, "Enregistrer en jouant sur les pads", listOf(ENREG, ENREG_RAPIDE, EFFACER_LIVE), 20,
        objective = "Enregistrer un rythme en direct, corriger les erreurs pendant la boucle et choisir la quantification.",
        explanation = "Record + Play lance l'enregistrement : les pads joués sont capturés. Si le motif tourne déjà, Record entre " +
            "et sort de l'enregistrement à la volée. Par défaut, les notes sont recalées sur la division temporelle ; Shift + Record " +
            "passe en mode non quantifié (Record allumé), avec une marge de ± 50 % de la division. Pour retirer des notes ratées : " +
            "maintenir Erase et taper le pad au moment où la note passe.",
        controls = listOf(
            cmd("Record + Play", "Lance l'enregistrement.", ENREG),
            cmd("Pads (sensibles à la vélocité)", "Jouent et enregistrent les instruments.", PADS),
            cmd("Shift + Record", "Enregistrement non quantifié.", ENREG),
            cmd("Erase maintenu + pad", "Efface les notes de cet instrument en boucle.", EFFACER_LIVE),
        ),
        steps = listOf(
            "Choisis un motif vide et active le métronome.",
            "Maintiens Record et appuie sur Play.",
            "Joue le kick sur chaque temps sur un tour, puis la caisse claire au tour suivant.",
            "Si une note est en trop, maintiens Erase et tape le pad à cet endroit.",
            "Appuie sur Record pour sortir, puis sauve avec Save + Ptrn.",
        ),
        expected = "Le rythme joué tourne en boucle, recalé sur la grille.",
        exerciseTitle = "Rythme hardtek joué",
        exercise = listOf(
            "Enregistre kick, caisse claire et charleston fermée en trois passages.",
            "Corrige au moins une erreur avec Erase + pad.",
            "Sauve le motif.",
        ),
        success = "Le motif tourne sans les notes ratées et il est sauvegardé.",
        style = "Hardtek : kick sur chaque temps, charleston fermée sur les contretemps.",
        mistakes = listOf(
            "Oublier que tout ce qui est joué en Record est enregistré : pour choisir un instrument sans jouer, Shift + pad.",
            "Oublier de sauver après l'enregistrement.",
        ),
        quiz = listOf(
            qz("Comment démarrer un enregistrement depuis l'arrêt ?", listOf("Record + Play", "Shift + Play", "Save + Record"), 0,
                "Maintenir Record et appuyer sur Play.", ENREG),
            qz("Par défaut, l'enregistrement est-il quantifié ?", listOf("Oui, sur la division temporelle", "Non"), 0,
                "Les notes sont quantifiées selon la division temporelle choisie.", ENREG),
            qz("Comment supprimer des notes pendant que le motif boucle en Record ?",
                listOf("Erase maintenu + pad", "Stop × 3", "Shift + Erase"), 0,
                "Maintenir Erase et taper le pad au moment des notes indésirables.", EFFACER_LIVE),
            qz("Que fait Shift + Record ?", listOf("Désactive la quantification", "Efface le motif", "Sauve le motif"), 0,
                "Shift + Record bascule l'enregistrement non quantifié.", ENREG),
        ),
    )

    private val m2l3 = ctLesson(
        M2, 3, "Division temporelle, Mute et Solo", listOf(DIVISION, MUTE_SOLO), 20,
        objective = "Changer la résolution du motif et isoler ou couper des instruments en direct.",
        explanation = "Shift + Pas 1 à 5 choisit la division temporelle : 1/8, 1/8T, 1/16 (défaut), 1/16T, 1/32 ; elle double ou " +
            "divise la vitesse de lecture et donne accès aux triolets. Mute et Solo créent chacun un groupe : appuie sur Mute (ou " +
            "Solo), puis tape les pads des instruments concernés ; le bouton active/désactive le groupe. Shift + Mute (ou Solo) " +
            "efface le groupe. Leur état est global (tous les motifs et morceaux) et n'est pas gardé à l'extinction.",
        controls = listOf(
            cmd("Shift + Pas 1–5", "Division temporelle 1/8 à 1/32 (triolets inclus).", DIVISION),
            cmd("Mute / Solo + pads", "Composer et activer le groupe Mute ou Solo.", MUTE_SOLO),
            cmd("Shift + Mute / Solo", "Efface le groupe.", MUTE_SOLO),
        ),
        steps = listOf(
            "Lance un motif, maintiens Shift et appuie sur le Pas 5 (1/32) : écoute, puis reviens au Pas 3 (1/16).",
            "Appuie sur Mute, puis tape les pads Kick et Snare 1 : ils sont coupés.",
            "Appuie sur Mute pour désactiver le groupe et les réentendre.",
            "Appuie sur Solo et tape le pad Closed Hat pour l'isoler, puis désactive Solo.",
            "Efface les groupes avec Shift + Mute et Shift + Solo.",
        ),
        expected = "La vitesse de lecture change avec la division et les groupes coupent ou isolent les instruments choisis.",
        exerciseTitle = "Break au Mute",
        exercise = listOf(
            "Prépare un groupe Mute avec le kick seul.",
            "Pendant la lecture, active le groupe pendant 4 mesures (break) puis désactive-le sur le premier temps.",
        ),
        success = "Le kick disparaît puis revient pile sur le temps.",
        style = "Tribe / hardtek : couper le kick puis le relancer est le geste de base du live.",
        mistakes = listOf(
            "Croire que le groupe Mute est propre au motif : il est global à tous les motifs et morceaux.",
            "Oublier que les mutes ne sont pas gardés à l'extinction.",
        ),
        quiz = listOf(
            qz("Quelle est la division temporelle par défaut ?", listOf("1/8", "1/16", "1/32"), 1,
                "Le réglage par défaut est 1/16.", DIVISION),
            qz("Comment choisir la division 1/32 ?", listOf("Shift + Pas 5", "Shift + Pas 16", "Ptrn + Pas 5"), 0,
                "Shift + les Pas 1 à 5 choisissent 1/8, 1/8T, 1/16, 1/16T et 1/32.", DIVISION),
            qz("Le groupe Mute concerne…", listOf("Le motif en cours", "Tous les motifs et morceaux"), 1,
                "L'état des boutons Mute/Solo est global.", MUTE_SOLO),
            qz("Comment effacer le groupe Solo ?", listOf("Shift + Solo", "Erase + Solo", "Solo × 3"), 0,
                "Shift + Solo efface le groupe Solo.", MUTE_SOLO),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 3 — Les instruments
    // ---------------------------------------------------------------------
    private const val M3 = "dbi-m3"

    private val m3l1 = ctLesson(
        M3, 1, "Dix instruments sur huit pads", listOf(PADS, PARTAGES), 15,
        objective = "Comprendre les canaux partagés et choisir quel instrument joue un pad.",
        explanation = "Dix instruments analogiques se partagent huit pads. Tom Hi/Low et Cymbal/Cowbell partagent un canal : " +
            "le bouton Type choisit l'instrument joué par le pad, mais chacun garde sa propre piste dans le motif. Les deux toms " +
            "partagent aussi leurs réglages. Closed Hat et Open Hat utilisent le même circuit : une seule s'entend à la fois et la " +
            "charleston fermée coupe l'ouverte. Pour choisir un instrument sans le déclencher, maintiens Shift et tape son pad.",
        controls = listOf(
            cmd("Pads", "Jouent les instruments, sensibles à la vélocité.", PADS),
            cmd("Bouton Type", "Choisit Tom Hi ou Low, Cymbal ou Cowbell.", PARTAGES),
            cmd("Shift + pad", "Sélectionne un instrument sans le jouer.", PADS),
        ),
        steps = listOf(
            "Frappe chaque pad et nomme l'instrument entendu.",
            "Appuie sur le bouton Type du canal Tom et frappe le pad : tu passes du tom haut au tom bas.",
            "Fais de même pour Cymbal et Cowbell.",
            "Joue la charleston ouverte puis immédiatement la fermée : la fermée coupe l'ouverte.",
            "Maintiens Shift et tape un pad : l'instrument est choisi sans son.",
        ),
        expected = "Tu sais jouer les dix instruments et choisir lequel est actif sur les canaux partagés.",
        exerciseTitle = "Les dix sons",
        exercise = listOf(
            "Joue successivement les dix instruments sans te tromper.",
            "Enregistre une partie Tom Hi et une partie Tom Low dans le même motif.",
        ),
        success = "Les deux toms ont chacun leur partie dans le motif.",
        mistakes = listOf(
            "Croire qu'un pad partagé n'a qu'une piste : chaque instrument a sa propre piste.",
            "S'étonner que l'accord des deux toms change ensemble : ils partagent leurs réglages.",
        ),
        quiz = listOf(
            qz("Combien d'instruments sur les huit pads ?", listOf("8", "10", "16"), 1,
                "Dix instruments analogiques se partagent huit pads.", PADS),
            qz("À quoi sert le bouton Type ?",
                listOf("Choisir l'instrument joué sur un canal partagé", "Changer de banque", "Activer Color"), 0,
                "Il bascule le pad partagé entre ses deux instruments.", PARTAGES),
            qz("Closed Hat et Open Hat peuvent-ils sonner en même temps ?", listOf("Oui", "Non, un seul à la fois"), 1,
                "Ils sont générés par le même circuit ; la fermée coupe toujours l'ouverte.", PARTAGES),
            qz("Comment sélectionner un instrument sans le déclencher ?", listOf("Shift + pad", "Type + pad", "Mute + pad"), 0,
                "Maintenir Shift et taper le pad.", PADS),
        ),
    )

    private val m3l2 = ctLesson(
        M3, 2, "Régler chaque instrument", listOf(PARAMS_1, PARAMS_2), 20,
        objective = "Sculpter le kick, les caisses claires, les toms, la cymbale et les charlestons avec leurs potentiomètres.",
        explanation = "Kick : Decay (durée), Pitch (accord), Level. Snare 1 : Decay (composant Snap), Tone/Snap (équilibre). " +
            "Snare 2 : Decay et Tone (balayage du filtre). Toms : Pitch et Level communs aux deux. Cymbal : Cym Decay (sans effet " +
            "sur la Cowbell). Hi-Hats : Hats Tone (filtre des deux charlestons), Open Hat Decay (durée de l'ouverte). Chaque canal a " +
            "son Level. Attention : les mouvements de potentiomètres ne sont pas enregistrés dans le motif.",
        controls = listOf(
            cmd("Kick Decay / Pitch / Level", "Durée, accord et volume de la grosse caisse.", PARAMS_1),
            cmd("Snare 1 Tone/Snap, Snare 2 Tone", "Timbre des caisses claires.", PARAMS_1),
            cmd("Cym Decay", "Durée de la cymbale.", PARAMS_2),
            cmd("Hats Tone / Open Hat Decay", "Filtre des charlestons, durée de l'ouverte.", PARAMS_2),
        ),
        steps = listOf(
            "Lance un motif avec kick, caisses claires et charlestons.",
            "Raccourcis le Decay du kick puis monte son Pitch : écoute le kick devenir plus sec et plus aigu.",
            "Tourne Tone/Snap de la Snare 1 d'un extrême à l'autre.",
            "Règle Hats Tone puis allonge Open Hat Decay.",
            "Équilibre les volumes avec les Level de chaque canal.",
        ),
        expected = "Chaque instrument a le caractère que tu veux et le mix est équilibré.",
        exerciseTitle = "Deux kicks pour deux styles",
        exercise = listOf(
            "Règle un kick court et aigu, puis un kick long et grave.",
            "Note les positions des potentiomètres pour chacun.",
        ),
        success = "Tu retrouves chaque kick à partir de tes notes.",
        style = "Hardtek : kick court et percutant ; tribe : kick plus long qui ronfle.",
        mistakes = listOf(
            "Chercher à enregistrer un mouvement de potentiomètre dans le motif : ce n'est pas possible.",
            "Tourner Cym Decay avec la Cowbell sélectionnée : il n'a aucun effet sur elle.",
        ),
        quiz = listOf(
            qz("Que règle Pitch sur le kick ?", listOf("Sa durée", "Son accord", "Son volume"), 1,
                "Pitch modifie l'accord de la grosse caisse.", PARAMS_1),
            qz("Que règle Tone/Snap sur la Snare 1 ?",
                listOf("L'équilibre entre les composants Tone et Snap", "Le volume", "La durée du Tone"), 0,
                "Il modifie l'équilibre Tone / Snap de la caisse claire 1.", PARAMS_1),
            qz("Que règle Hats Tone ?",
                listOf("La durée de l'ouverte", "Le filtre des deux charlestons", "Le volume de la fermée"), 1,
                "Hats Tone règle la coupure du filtre de la fermée et de l'ouverte.", PARAMS_2),
            qz("Les mouvements des potentiomètres d'instruments sont-ils enregistrés dans le motif ?", listOf("Oui", "Non"), 1,
                "Le manuel précise qu'ils ne sont pas enregistrés.", PADS),
        ),
    )

    private val m3l3 = ctLesson(
        M3, 3, "La percussion FM", listOf(FM), 20,
        objective = "Comprendre la FM et tirer de la percussion FM des sons métalliques ou toniques.",
        explanation = "La FM Drum a un Porteur (Carrier), dont on entend le son, et un Modulateur qui modifie ce son sans être " +
            "entendu directement. Carrier Pitch règle la hauteur du Porteur, Mod Pitch celle du Modulateur, FM Amount la quantité " +
            "de modulation, Decay la durée, Level le volume. Le caractère change radicalement selon le rapport entre Carrier Pitch et Mod Pitch.",
        controls = listOf(
            cmd("Carrier Pitch", "Hauteur du Porteur.", FM),
            cmd("Mod Pitch", "Fréquence du Modulateur.", FM),
            cmd("FM Amount", "Impact du Modulateur sur le Porteur.", FM),
            cmd("Decay / Level", "Durée et volume.", FM),
        ),
        steps = listOf(
            "Mets Mod Pitch, FM Amount et Level au minimum, Decay et Carrier Pitch au maximum.",
            "Monte doucement Level en frappant le pad FM : tu entends un son pur.",
            "Balaye Carrier Pitch, puis Decay, en écoutant.",
            "Monte prudemment FM Amount, puis place-le à 12 h.",
            "Tourne doucement Mod Pitch : le caractère du son change fortement.",
        ),
        expected = "Tu passes d'un son pur à des sons métalliques et complexes en jouant sur FM Amount et les deux hauteurs.",
        exerciseTitle = "Trois percussions FM",
        exercise = listOf(
            "Crée un bip court et pur, un son métallique et un son grave et complexe.",
            "Programme le son métallique en contretemps dans un motif.",
        ),
        success = "Les trois sons sont nettement différents et le motif garde son groove.",
        style = "Mentalcore : la FM donne vite des percussions métalliques et agressives.",
        mistakes = listOf(
            "Monter FM Amount d'un coup avec le volume fort : procède prudemment.",
            "Chercher le son du Modulateur seul : on ne l'entend qu'à travers le Porteur.",
        ),
        quiz = listOf(
            qz("Quelle partie de la FM entend-on directement ?", listOf("Le Porteur", "Le Modulateur"), 0,
                "On n'entend jamais le Modulateur seul : il agit sur le Porteur.", FM),
            qz("Que règle FM Amount ?",
                listOf("Le volume", "L'impact du Modulateur sur le Porteur", "La durée"), 1,
                "FM Amount règle la quantité de modulation.", FM),
            qz("Que règle Mod Pitch ?", listOf("La fréquence du Modulateur", "La hauteur du Porteur", "Le decay"), 0,
                "Mod Pitch change la fréquence du Modulateur.", FM),
            qz("Qu'est-ce qui change radicalement le caractère du son ?",
                listOf("Le rapport entre Carrier Pitch et Mod Pitch", "Le métronome", "Le tempo"), 0,
                "Le manuel montre que modifier ce rapport transforme le son global.", FM),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 4 — Color et accents
    // ---------------------------------------------------------------------
    private const val M4 = "dbi-m4"

    private val m4l1 = ctLesson(
        M4, 1, "L'effet Color de chaque instrument", listOf(COLOR_TABLE, COLOR_INSTR, PADS), 15,
        objective = "Connaître l'effet Color de chaque instrument et l'activer pour tout l'instrument.",
        explanation = "Chaque instrument a un effet Color, sauf la Cowbell : Kick = Drive (saturation propre au kick, disponible " +
            "sur sa sortie individuelle), Snare 1 = Body, Snare 2 = Clap, Toms = Decay, Cymbal = Tone, Closed Hat = CH Decay, " +
            "Open Hat = Harmonics, FM Drum = Pitch Envelope. Pour l'activer sur tout l'instrument : maintiens Color et tape le pad " +
            "jusqu'à ce qu'il devienne orange. C'est non destructif, mais tant qu'il reste actif, tous les coups de cet instrument sont colorés.",
        controls = listOf(
            cmd("Color + pad", "Active/désactive Color pour tout l'instrument (pad orange).", COLOR_INSTR),
            cmd("Texte sous les pads", "Nom de l'effet Color de chaque instrument.", PADS),
        ),
        steps = listOf(
            "Frappe le pad Kick et écoute.",
            "Maintiens Color et tape le pad Kick jusqu'à ce qu'il soit orange, puis relâche Color.",
            "Frappe le kick : le Drive est actif.",
            "Recommence avec la Snare 2 (Clap) et la FM Drum (Pitch Envelope).",
            "Désactive-les de la même façon (pad éteint).",
        ),
        expected = "Tu entends l'effet Color de chaque instrument et tu sais le retirer.",
        exerciseTitle = "Tour des couleurs",
        exercise = listOf(
            "Active tour à tour l'effet Color de chaque instrument et décris-le en un mot.",
            "Vérifie que la Cowbell n'a pas d'effet Color.",
        ),
        success = "Tes descriptions correspondent au tableau du manuel (p. 34).",
        mistakes = listOf(
            "Laisser Color actif sur l'instrument : plus aucun coup non coloré n'est possible sur cette piste.",
            "Chercher un effet Color sur la Cowbell : elle n'en a pas.",
        ),
        quiz = listOf(
            qz("Quel est l'effet Color du Kick ?", listOf("Body", "Drive", "Clap"), 1,
                "Kick = Drive, une saturation indépendante de la distorsion maître.", COLOR_TABLE),
            qz("Quel instrument n'a pas d'effet Color ?", listOf("Cymbal", "Cowbell", "Snare 2"), 1,
                "La Cowbell est le seul instrument sans effet Color.", COLOR_TABLE),
            qz("Quel est l'effet Color de la Snare 2 ?", listOf("Clap", "Harmonics", "Decay"), 0,
                "Snare 2 = Clap.", COLOR_TABLE),
            qz("Comment activer Color pour tout un instrument ?",
                listOf("Maintenir Color et taper le pad jusqu'à l'orange", "Shift + pad", "Save + Color"), 0,
                "Le pad allumé en orange indique que l'instrument est coloré.", COLOR_INSTR),
        ),
    )

    private val m4l2 = ctLesson(
        M4, 2, "Un instrument, quatre sons", listOf(QUATRE_SONS, UN_INSTR_4_SONS, STEP_ACCENT), 20,
        objective = "Programmer, sur une même piste, des coups normaux, accentués, colorés et colorés-accentués.",
        explanation = "Chaque instrument a quatre versions : normal, normal accentué, coloré, coloré accentué. En mode Step, les " +
            "boutons de Pas posent des coups ; en mode Accent, ils posent des accents ; en maintenant Color, ils posent des événements " +
            "Color. Un événement Color ne déclenche rien seul : il ne s'entend que sur un pas déjà activé en mode Step ou Accent.",
        controls = listOf(
            cmd("Step", "Mode Step : pose/retire des coups.", STEP_ACCENT),
            cmd("Accent", "Mode Accent : pose des accents (rouge).", STEP_ACCENT),
            cmd("Color maintenu + Pas", "Pose des événements Color sur les pas.", UN_INSTR_4_SONS),
        ),
        steps = listOf(
            "Choisis un motif vide, règle 60 BPM, et sélectionne le kick avec Shift + pad Kick.",
            "Appuie sur Step et pose des coups sur les Pas 1, 5, 9 et 13.",
            "Appuie sur Accent et pose des accents sur les Pas 5 et 13.",
            "Maintiens Color et ajoute des événements sur les Pas 9 et 13.",
            "Lance Play : les quatre coups sonnent normal, accentué, coloré, coloré-accentué.",
        ),
        expected = "Les quatre kicks du motif sonnent chacun différemment.",
        exerciseTitle = "Kick qui évolue",
        exercise = listOf(
            "Sur un motif de 16 pas, fais varier les quatre versions du kick au fil de la mesure.",
            "Retire les coups des Pas 9 et 13 en mode Step et vérifie que leurs événements Color ne jouent plus.",
        ),
        success = "Sans coup sur le pas, l'événement Color reste silencieux.",
        style = "Hardtek : un kick avec Drive sur certains temps seulement donne du relief.",
        mistakes = listOf(
            "Poser un événement Color sur un pas vide et s'attendre à entendre quelque chose.",
            "Oublier de sélectionner l'instrument avant d'éditer ses pas.",
        ),
        quiz = listOf(
            qz("Combien de versions de son a chaque instrument ?", listOf("2", "4", "8"), 1,
                "Normal, accentué, coloré, coloré accentué.", QUATRE_SONS),
            qz("Un événement Color sur un pas sans coup est-il entendu ?", listOf("Oui", "Non"), 1,
                "Un événement Color n'est pas un déclencheur : il faut un coup en mode Step ou Accent.", UN_INSTR_4_SONS),
            qz("Quelle couleur indique un pas accentué ?", listOf("Bleu", "Rouge", "Orange"), 1,
                "Les accents s'affichent en rouge, en mode Accent comme en mode Step.", STEP_ACCENT),
            qz("Comment poser des événements Color sur des pas ?",
                listOf("Maintenir Color et appuyer sur les Pas", "Shift + Pas", "Accent + Pas"), 0,
                "Tant que Color est maintenu, les boutons de Pas éditent les événements Color.", UN_INSTR_4_SONS),
        ),
    )

    private val m4l3 = ctLesson(
        M4, 3, "Color Hold et lecture des couleurs", listOf(PADS, COLOR_PHASES), 15,
        objective = "Verrouiller le mode Color pour jouer en direct et lire l'état des pas (éteint, bleu, rouge).",
        explanation = "Shift + Color verrouille les pads (et les boutons de Pas en mode Step) en mode Color Hold : tu actives ou " +
            "coupes l'effet Color de plusieurs instruments sans maintenir Color, même en live. Color quitte ce mode. En mode Step " +
            "ou Accent, un pas est éteint (vide), bleu (coup) ou rouge (coup accentué) ; les appuis successifs font passer d'un état " +
            "à l'autre selon le mode.",
        controls = listOf(
            cmd("Shift + Color", "Mode Color Hold.", PADS),
            cmd("Boutons de Pas", "Éteint / bleu / rouge selon le mode.", COLOR_PHASES),
        ),
        steps = listOf(
            "Lance un motif, maintiens Shift et appuie sur Color : Color Hold est actif.",
            "Tape les pads Kick et Snare 2 pour activer leur Color en direct, puis retape-les pour couper.",
            "Appuie sur Color pour sortir du Color Hold.",
            "En mode Step, appuie plusieurs fois sur un même Pas et observe le passage éteint → bleu → éteint.",
            "En mode Accent, fais de même et observe le rouge.",
        ),
        expected = "Tu actives les couleurs en live d'une seule main et tu lis l'état de chaque pas.",
        exerciseTitle = "Montée colorée",
        exercise = listOf(
            "En Color Hold, active progressivement le Color du kick, puis de la Snare 2, puis des charlestons sur 8 mesures.",
            "Coupe-les tous d'un coup avant le retour du thème.",
        ),
        success = "La montée est audible et le retour au son normal est net.",
        style = "Tribecore : faire monter la tension en ajoutant des couleurs, puis tout couper.",
        mistakes = listOf(
            "Oublier de quitter Color Hold : les pads ne jouent plus normalement.",
            "Confondre bleu (coup) et rouge (accent) en mode Step.",
        ),
        quiz = listOf(
            qz("Comment activer le mode Color Hold ?", listOf("Shift + Color", "Color × 2", "Save + Color"), 0,
                "Maintenir Shift et appuyer sur Color.", PADS),
            qz("Comment quitter le Color Hold ?", listOf("Appuyer de nouveau sur Color", "Stop", "Shift + Stop"), 0,
                "Un nouvel appui sur Color quitte le mode.", PADS),
            qz("En mode Step, que signifie un pas bleu ?", listOf("Un coup", "Un accent", "Un événement Color"), 0,
                "Bleu = événement en cours ; rouge = accent ; éteint = vide.", COLOR_PHASES),
            qz("Pendant l'enregistrement, comment voir la piste d'un instrument sans jouer de note ?",
                listOf("Shift + pad", "Taper le pad", "Color + pad"), 0,
                "En enregistrement, taper le pad ajoute une note ; Shift + pad le sélectionne seulement.", COLOR_PHASES),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(
            M1, 1, "Prise en main",
            "Branchements, motifs, transport et sauvegarde.",
            "Brancher l'Impact, naviguer dans les motifs et ne jamais perdre ton travail.",
            "Base de tous les styles : la distorsion du mix et les enchaînements sont au cœur de la free party.",
            listOf(m1l1, m1l2, m1l3),
        ),
        CourseModule(
            M2, 2, "Tempo et enregistrement",
            "Tempo, synchro, enregistrement en direct, division, Mute et Solo.",
            "Enregistrer un rythme en jouant et le contrôler en direct.",
            "Hardtek et tribe se jouent entre 150 et 180 BPM, avec des breaks au Mute.",
            listOf(m2l1, m2l2, m2l3),
        ),
        CourseModule(
            M3, 3, "Les instruments",
            "Canaux partagés, réglages de chaque instrument, percussion FM.",
            "Sculpter un kit complet à partir des dix instruments analogiques.",
            "Kick court et sec, caisses saturées, FM métallique : la palette des styles visés.",
            listOf(m3l1, m3l2, m3l3),
        ),
        CourseModule(
            M4, 4, "Color et accents",
            "Effets Color, quatre sons par instrument, Color Hold.",
            "Varier chaque piste avec accents et couleurs, et colorer en live.",
            "Les couleurs et accents donnent la dynamique qui manque souvent aux boucles répétitives.",
            listOf(m4l1, m4l2, m4l3),
        ),
    )
}
