package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** SP-404MKII — modules 5 à 7 : édition, séquenceur de motifs, tempo, chaînes et mode DJ. */
internal object SP404MkIIModulesB {

    // Références du manuel de référence (pages imprimées = pages du PDF)
    private val START_END = sp4("Réglage des régions de lecture et de boucle (DÉBUT/FIN)", "43")
    private val MARK = sp4("Marquage et fractionnement des échantillons (MARK)", "45")
    private val CHOP = sp4("Utilisation d'un marqueur pour diviser (CHOP)", "47")
    private val PITCH = sp4("Modification de la hauteur ou de la vitesse (HAUTEUR/VITESSE)", "51")
    private val ORGA = sp4("Organisation des échantillons : échange, copie", "55")
    private val PROTEGER = sp4("Protéger, Suppression de l'échantillon d'un pad", "56")
    private val PTN_LECTURE = sp4("Lecture d'un motif", "58")
    private val PTN_REC = sp4("Création d'un nouveau motif (temps réel)", "59–60")
    private val TR_REC = sp4("Création d'un nouveau motif (TR-REC)", "61–62")
    private val PTN_RESAMPLE = sp4("Rééchantillonnage d'un motif", "64")
    private val CHAIN = sp4("Lecture de patterns dans l'ordre (PATTERN CHAIN)", "66")
    private val PTN_EDIT = sp4("Modification d'un motif, Dupliquer", "69")
    private val BPM_SYNC = sp4("Lecture d'un échantillon dans le Tempo (BPM SYNC)", "13")
    private val TEMPO = sp4("Réglage du Tempo", "74")
    private val TEMPO_SEL = sp4("Réglage du Tempo pour une banque ou un projet", "76")
    private val TAP = sp4("Réglage du tempo avec le rythme (Tap Tempo)", "77")
    private val DJ = sp4("Mixage des échantillons (MODE DJ)", "78")

    // ---------------------------------------------------------------------
    // Module 5 — Éditer un échantillon
    // ---------------------------------------------------------------------
    private const val M5 = "sp4-m5"

    private val m5l1 = ctLesson(
        M5, 1, "Début, fin et point de boucle", listOf(START_END), 15,
        objective = "Retirer le silence d'un échantillon et régler sa boucle.",
        explanation = "START/END ouvre l'écran des marqueurs ; on choisit le pad. CTRL 1 déplace le début, CTRL 2 le point de boucle (boucle " +
            "activée), CTRL 3 la fin. SHIFT + CTRL zoome autour d'un point ; tourner VALUE zoome autour du dernier point manipulé. DEL " +
            "remet début et fin à zéro (confirmer OK). ROLL fait écouter les dernières secondes avant la fin. RESAMPLE allumé cale le " +
            "début sur le passage à zéro le plus proche (moins de clics).",
        controls = listOf(
            cmd("START/END", "Écran des marqueurs.", START_END),
            cmd("CTRL 1 / CTRL 2 / CTRL 3", "Début / boucle / fin.", START_END),
            cmd("RESAMPLE (dans l'écran)", "Calage sur le passage à zéro.", START_END),
            cmd("DEL", "Réinitialiser.", START_END),
        ),
        steps = listOf(
            "Appuie sur START/END puis sur le pad de ton stab.",
            "Avance le début avec CTRL 1 jusqu'à l'attaque.",
            "Recule la fin avec CTRL 3 pour couper la queue.",
            "Allume RESAMPLE pour caler le début sur un passage à zéro.",
        ),
        expected = "Le stab démarre pile sur l'attaque, sans silence ni clic.",
        exerciseTitle = "Kick serré",
        exercise = listOf(
            "Échantillonne un kick, coupe le début et raccourcis la fin.",
        ),
        success = "Le kick tombe exactement sur le temps quand tu tapes le pad.",
        style = "Hardtek : un kick court et calé, sans silence au début.",
        mistakes = listOf(
            "Couper le début trop loin : l'attaque du son disparaît.",
        ),
        quiz = listOf(
            qz("Quelle molette déplace le début ?", listOf("CTRL 1", "CTRL 3"), 0,
                "CTRL 1 début, CTRL 3 fin.", START_END),
            qz("Point de boucle ?", listOf("CTRL 2 (boucle activée)", "VALUE"), 0,
                "Quand la boucle est activée.", START_END),
            qz("Éviter les clics au début ?", listOf("RESAMPLE allumé (passage à zéro)", "DEL"), 0,
                "SNAP to Zero-Cross.", START_END),
            qz("Réinitialiser début et fin ?", listOf("DEL", "EXIT"), 0,
                "Puis confirmer OK.", START_END),
        ),
    )

    private val m5l2 = ctLesson(
        M5, 2, "Marqueurs et découpe (CHOP)", listOf(MARK, CHOP), 20,
        objective = "Découper une boucle de batterie sur plusieurs pads.",
        explanation = "Choisir le pad, puis SHIFT + START/END : écran des marqueurs. CTRL 1 déplace le curseur, MARK ajoute un marqueur, " +
            "CTRL 3 en sélectionne un, DEL le supprime ; RESAMPLE allumé cale sur un passage à zéro. Les pads font écouter chaque marqueur. " +
            "Puis VALUE > ASSIGN TO PAD : choisir les pads de destination (les pads vides clignotent jaune), MARK confirme l'ordre, VALUE " +
            "affecte les morceaux. VALUE > DELETE ALL MARKERS efface tous les marqueurs.",
        controls = listOf(
            cmd("SHIFT + START/END", "Écran des marqueurs.", MARK),
            cmd("MARK, DEL", "Ajouter / supprimer un marqueur.", MARK),
            cmd("VALUE > ASSIGN TO PAD", "Répartir sur les pads.", CHOP),
        ),
        steps = listOf(
            "Choisis le pad d'une boucle de batterie, puis SHIFT + START/END.",
            "Place un marqueur sur chaque coup avec CTRL 1 et MARK.",
            "Appuie sur VALUE, choisis ASSIGN TO PAD.",
            "Sélectionne des pads vides, puis VALUE pour affecter.",
        ),
        expected = "Chaque coup de la boucle est sur son propre pad.",
        exerciseTitle = "Break réarrangé",
        exercise = listOf(
            "Découpe un break en 8 morceaux.",
            "Rejoue-les dans un autre ordre sur les pads.",
        ),
        success = "Un nouveau rythme fait avec les morceaux du break.",
        style = "Tribe : un break découpé et rejoué dans un ordre nouveau.",
        mistakes = listOf(
            "Affecter sur des pads déjà occupés : les échantillons existants sont écrasés.",
        ),
        quiz = listOf(
            qz("Ouvrir l'écran des marqueurs ?", listOf("SHIFT + START/END", "START/END seul"), 0,
                "START/END seul règle début et fin.", MARK),
            qz("Ajouter un marqueur ?", listOf("MARK", "REC"), 0,
                "À la position du curseur.", MARK),
            qz("Répartir sur les pads ?", listOf("VALUE > ASSIGN TO PAD", "COPY"), 0,
                "Puis choix des pads et VALUE.", CHOP),
            qz("Pad déjà occupé choisi comme destination ?", listOf("Son échantillon est écrasé", "Il est ignoré"), 0,
                "L'échantillon affecté est alors écrasé.", CHOP),
        ),
    )

    private val m5l3 = ctLesson(
        M5, 3, "Hauteur, vitesse et rangement", listOf(PITCH, ORGA, PROTEGER), 15,
        objective = "Accorder un échantillon et ranger tes pads sans perte.",
        explanation = "PITCH/SPEED, puis le pad : CTRL 1 vitesse (50–150 %, impossible si BPM SYNC est actif), CTRL 2 hauteur (−12 à +12), " +
            "SHIFT + CTRL 2 réglage fin, CTRL 3 volume (0–127), SHIFT + CTRL 3 panoramique ; VALUE choisit le réglage BPM (AUTO / MANU) ; " +
            "SHIFT + VALUE le mode vinyle (hauteur et vitesse liées). Rangement : SHIFT + pad [5] échange deux pads ; COPY copie un pad " +
            "vers un autre (écrase la destination) ; SHIFT + COPY protège une banque ; DEL + pad supprime.",
        controls = listOf(
            cmd("PITCH/SPEED", "Vitesse, hauteur, volume.", PITCH),
            cmd("SHIFT + pad [5]", "Échanger deux pads.", ORGA),
            cmd("COPY", "Copier un pad.", ORGA),
            cmd("SHIFT + COPY / DEL", "Protéger / supprimer.", PROTEGER),
        ),
        steps = listOf(
            "PITCH/SPEED, choisis ton stab et baisse la hauteur de 5 avec CTRL 2.",
            "Copie ce pad vers un pad vide avec COPY, puis remonte la copie de 7.",
            "Protège la banque avec SHIFT + COPY.",
        ),
        expected = "Deux versions accordées du stab, banque protégée.",
        exerciseTitle = "Stabs accordés",
        exercise = listOf(
            "Fais 4 copies d'un stab accordées sur une petite progression.",
        ),
        success = "Les 4 pads jouent une progression juste.",
        style = "Tribe : stabs accordés en progression sur les contretemps.",
        mistakes = listOf(
            "Copier sur un pad occupé : son échantillon est effacé.",
            "Chercher à changer la vitesse avec BPM SYNC actif : c'est impossible.",
        ),
        quiz = listOf(
            qz("Plage de hauteur ?", listOf("−12 à +12", "−24 à +24"), 0,
                "Hors mode vinyle.", PITCH),
            qz("Plage de vitesse ?", listOf("50–150 %", "10–400 %"), 0,
                "Indisponible si BPM SYNC est actif.", PITCH),
            qz("Échanger deux pads ?", listOf("SHIFT + pad [5]", "SHIFT + pad [1]"), 0,
                "Écran EXCHANGE PAD.", ORGA),
            qz("Protéger une banque ?", listOf("SHIFT + COPY", "SHIFT + DEL"), 0,
                "Puis OK avec VALUE.", PROTEGER),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 6 — Le séquenceur de motifs
    // ---------------------------------------------------------------------
    private const val M6 = "sp4-m6"

    private val m6l1 = ctLesson(
        M6, 1, "Lire les motifs", listOf(PTN_LECTURE), 10,
        objective = "Lancer et enchaîner des motifs en direct.",
        explanation = "PATTERN SELECT affiche « Ptn » et le tempo ; les pads qui contiennent un motif sont violets. Un pad lance son motif ; " +
            "un autre pad pendant la lecture le met en attente : il démarre à la fin du motif en cours. SUB PAD + pad change de motif " +
            "immédiatement. Pour arrêter : le pad en cours (blanc) ou EXIT. REC pendant la lecture passe en enregistrement au début de la " +
            "mesure suivante ; SHIFT + REC passe en TR-REC.",
        controls = listOf(
            cmd("PATTERN SELECT", "Mode motifs.", PTN_LECTURE),
            cmd("Pad", "Lancer / mettre en attente.", PTN_LECTURE),
            cmd("SUB PAD + pad", "Changer tout de suite.", PTN_LECTURE),
            cmd("EXIT", "Arrêter.", PTN_LECTURE),
        ),
        steps = listOf(
            "Appuie sur PATTERN SELECT : repère les pads violets.",
            "Lance un motif, puis appuie sur un autre : il démarre à la fin du premier.",
            "Essaie SUB PAD + pad pour changer immédiatement.",
            "Arrête avec EXIT.",
        ),
        expected = "Tu enchaînes les motifs proprement.",
        exerciseTitle = "Enchaînement de motifs",
        exercise = listOf(
            "Enchaîne quatre motifs en attente, sans coupure.",
        ),
        success = "Chaque motif démarre exactement à la fin du précédent.",
        mistakes = listOf(
            "Utiliser SUB PAD par réflexe : le changement immédiat peut casser le rythme.",
        ),
        quiz = listOf(
            qz("Couleur des pads avec motif ?", listOf("Violet", "Orange"), 0,
                "En mode PATTERN SELECT.", PTN_LECTURE),
            qz("Autre pad pendant la lecture ?", listOf("Démarre à la fin du motif en cours", "Coupe tout"), 0,
                "Mode attente.", PTN_LECTURE),
            qz("Changer immédiatement ?", listOf("SUB PAD + pad", "SHIFT + pad"), 0,
                "Sans attendre la fin.", PTN_LECTURE),
            qz("Passer en TR-REC pendant la lecture ?", listOf("SHIFT + REC", "HOLD + REC"), 0,
                "REC seul = temps réel.", PTN_LECTURE),
        ),
    )

    private val m6l2 = ctLesson(
        M6, 2, "Enregistrer un motif en temps réel", listOf(PTN_REC), 20,
        objective = "Enregistrer ce que tu joues sur les pads en boucle.",
        explanation = "PATTERN SELECT, REC, puis un pad rouge clignotant (destination). Écran de réglage : CTRL 1 tempo (40–200), CTRL 2 " +
            "longueur (1 à 64 mesures), CTRL 3 force de quantification (0–100 %) ou shuffle (−50 à +50, +10 à +16 donnent un bon groove) ; " +
            "VALUE la quantification (GRID 32, 16, 8, 4, SHUFFLE 16, 8…) ; SHIFT + CTRL 2 la signature ; REMAIN choisit temps réel ou " +
            "TR-REC. REC lance : le motif boucle sur sa longueur et on peut superposer. DEL maintenu efface les pads joués. Deux appuis sur " +
            "EXIT terminent ; le motif est sauvegardé automatiquement. Décompte : SHIFT + pad [10].",
        controls = listOf(
            cmd("PATTERN SELECT + REC + pad", "Destination du motif.", PTN_REC),
            cmd("CTRL 1 / CTRL 2 / CTRL 3", "Tempo / longueur / quantification.", PTN_REC),
            cmd("VALUE", "Grille de quantification.", PTN_REC),
            cmd("DEL maintenu", "Effacer en jouant.", PTN_REC),
        ),
        steps = listOf(
            "PATTERN SELECT, REC, choisis un pad rouge.",
            "Règle 1 mesure, quantification GRID 16, tempo 160.",
            "Appuie sur REC et joue kick et caisse claire ; superpose les charleys au tour suivant.",
            "Appuie deux fois sur EXIT.",
        ),
        expected = "Un motif d'une mesure enregistré sur un pad violet.",
        exerciseTitle = "Motif hardtek",
        exercise = listOf(
            "Enregistre un motif de 2 mesures : kick à chaque temps, percussions jouées.",
            "Efface une erreur avec DEL maintenu.",
        ),
        success = "Le motif boucle sans erreur.",
        style = "Hardtek : kick régulier, percussions jouées par-dessus.",
        mistakes = listOf(
            "Oublier de régler la longueur avant d'enregistrer : on ne peut pas la changer pendant l'enregistrement.",
        ),
        quiz = listOf(
            qz("Longueur maximale ?", listOf("64 mesures", "16 mesures"), 0,
                "1 à 64 mesures.", PTN_REC),
            qz("Shuffle conseillé ?", listOf("+10 à +16", "−50"), 0,
                "Donne une agréable sensation de jeu.", PTN_REC),
            qz("Terminer l'enregistrement ?", listOf("Deux appuis sur EXIT", "POWER"), 0,
                "Un appui arrête l'enregistrement, deux la lecture.", PTN_REC),
            qz("Effacer en enregistrant ?", listOf("DEL maintenu", "COPY"), 0,
                "Les pads joués pendant l'appui sont effacés.", PTN_REC),
        ),
    )

    private val m6l3 = ctLesson(
        M6, 3, "Programmer pas à pas (TR-REC)", listOf(TR_REC, PTN_RESAMPLE), 20,
        objective = "Placer les coups sur une grille de 16 pas, comme une boîte à rythmes.",
        explanation = "Comme en temps réel, mais REMAIN passe en « TR-REC » (REMAIN clignote). Après REC, SUB PAD + pad choisit l'échantillon ; " +
            "les pads [1] à [16] deviennent les pas : on allume ceux où le son doit jouer. CTRL 2 règle la hauteur, CTRL 3 la vélocité " +
            "(0–127), SHIFT + CTRL 1 le tempo, SHIFT + CTRL 2 le shuffle ; VALUE change de mesure. CTRL 1 règle les sous-pas. Le motif " +
            "fini peut être rééchantillonné en un seul échantillon (PATTERN SELECT, RESAMPLE).",
        controls = listOf(
            cmd("REMAIN", "Mode TR-REC.", TR_REC),
            cmd("SUB PAD + pad", "Choisir l'échantillon.", TR_REC),
            cmd("Pads [1] à [16]", "Pas du motif.", TR_REC),
            cmd("CTRL 2 / CTRL 3", "Hauteur / vélocité.", TR_REC),
        ),
        steps = listOf(
            "PATTERN SELECT, REC, pad rouge, puis REMAIN jusqu'à TR-REC.",
            "REC, puis SUB PAD + pad du kick ; allume les pas 1, 5, 9, 13.",
            "SUB PAD + pad du charley ; allume les pas 3, 7, 11, 15 et baisse leur vélocité avec CTRL 3.",
            "Termine avec deux appuis sur EXIT.",
        ),
        expected = "Un rythme programmé pas à pas.",
        exerciseTitle = "Grille tribe",
        exercise = listOf(
            "Programme un kick droit et deux percussions décalées en TR-REC.",
            "Rééchantillonne le motif en une boucle.",
        ),
        success = "Le motif devient une boucle jouable sur un pad.",
        style = "Tribe : percussions décalées autour d'un kick droit.",
        mistakes = listOf(
            "Oublier SUB PAD pour changer d'échantillon : les pads servent alors de pas.",
        ),
        quiz = listOf(
            qz("Passer en TR-REC ?", listOf("REMAIN (clignote)", "HOLD"), 0,
                "REMAIN allumé = temps réel.", TR_REC),
            qz("Choisir l'échantillon à placer ?", listOf("SUB PAD + pad", "BUS FX"), 0,
                "Les pads servent ensuite de pas.", TR_REC),
            qz("Vélocité d'un pas ?", listOf("CTRL 3 (0–127)", "CTRL 1"), 0,
                "CTRL 2 règle la hauteur.", TR_REC),
            qz("Changer de mesure ?", listOf("VALUE", "SHIFT + REC"), 0,
                "Pour les motifs de plus d'une mesure.", TR_REC),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 7 — Tempo, chaînes et mode DJ
    // ---------------------------------------------------------------------
    private const val M7 = "sp4-m7"

    private val m7l1 = ctLesson(
        M7, 1, "Tempo et BPM SYNC", listOf(BPM_SYNC, TEMPO, TEMPO_SEL, TAP), 15,
        objective = "Caler toutes les boucles sur un même tempo.",
        explanation = "Trois tempos : celui de l'échantillon, celui de la banque (commun à ses échantillons et motifs) et celui du projet. " +
            "SHIFT + pad [11] ouvre TEMPO SEL : CTRL 1 choisit PROJECT ou la banque, VALUE règle le tempo, EXIT valide. Dans cet écran, " +
            "on peut taper le tempo sur SUB PAD (il clignote orange). BPM SYNC fait lire l'échantillon au tempo de la banque ou du projet ; " +
            "SHIFT + BPM SYNC l'active pour toute la banque. Il faut que le tempo de chaque échantillon soit correct.",
        controls = listOf(
            cmd("BPM SYNC", "Caler l'échantillon sur le tempo.", BPM_SYNC),
            cmd("SHIFT + pad [11]", "Tempo banque ou projet.", TEMPO_SEL),
            cmd("SUB PAD (tap)", "Taper le tempo.", TAP),
        ),
        steps = listOf(
            "SHIFT + pad [11], CTRL 1 sur PROJECT, tempo 160 avec VALUE, EXIT.",
            "Choisis une boucle et allume BPM SYNC : elle se cale sur 160.",
            "Active BPM SYNC pour toute la banque avec SHIFT + BPM SYNC.",
            "Retape le tempo sur SUB PAD dans l'écran TEMPO SEL.",
        ),
        expected = "Toutes les boucles de la banque jouent au même tempo.",
        exerciseTitle = "Boucles calées",
        exercise = listOf(
            "Lance deux boucles d'origines différentes avec BPM SYNC à 170.",
        ),
        success = "Les deux boucles restent ensemble.",
        style = "Hardtek / tribe : tout le set calé entre 150 et 180 BPM.",
        mistakes = listOf(
            "Activer BPM SYNC sur un échantillon dont le tempo est mal défini : la vitesse sera fausse.",
        ),
        quiz = listOf(
            qz("Ouvrir TEMPO SEL ?", listOf("SHIFT + pad [11]", "SHIFT + pad [5]"), 0,
                "Choix banque ou projet.", TEMPO_SEL),
            qz("BPM SYNC pour toute la banque ?", listOf("SHIFT + BPM SYNC", "HOLD + BPM SYNC"), 0,
                "Active ou désactive pour la banque.", BPM_SYNC),
            qz("Taper le tempo sur…", listOf("SUB PAD", "EXIT"), 0,
                "SUB PAD clignote orange.", TAP),
            qz("Combien de niveaux de tempo ?", listOf("Échantillon, banque, projet", "Un seul"), 0,
                "Trois niveaux.", TEMPO),
        ),
    )

    private val m7l2 = ctLesson(
        M7, 2, "Éditer et chaîner les motifs", listOf(PTN_EDIT, CHAIN), 15,
        objective = "Allonger un motif et construire l'ordre d'un morceau.",
        explanation = "PATTERN SELECT, PATTERN EDIT, puis le pad : CTRL 1 tempo, CTRL 2 début de boucle, CTRL 3 longueur (1, 2, 4, 8, 16, 32, " +
            "64 mesures). VALUE > DUPLICATE copie le motif à sa suite. PATTERN CHAIN : en mode PATTERN SELECT, maintenir HOLD et appuyer " +
            "sur un pad choisit une chaîne (1 à 16) ; on ajoute les motifs dans l'ordre avec les pads (16 motifs maximum par chaîne), DEL " +
            "retire, EXIT sauvegarde. REMAIN règle la répétition : toute la chaîne, le motif courant ou aucune.",
        controls = listOf(
            cmd("PATTERN EDIT", "Tempo, début de boucle, longueur.", PTN_EDIT),
            cmd("VALUE > DUPLICATE", "Doubler le motif.", PTN_EDIT),
            cmd("HOLD + pad", "Choisir une chaîne.", CHAIN),
            cmd("REMAIN", "Mode de répétition.", CHAIN),
        ),
        steps = listOf(
            "PATTERN SELECT, PATTERN EDIT, pad de ton motif : DUPLICATE.",
            "Maintiens HOLD et appuie sur pad [1] : chaîne 1.",
            "Ajoute intro, motif, motif, break, motif avec les pads.",
            "EXIT pour sauvegarder, puis REMAIN sur « répéter tout ».",
        ),
        expected = "Un morceau complet qui s'enchaîne tout seul.",
        exerciseTitle = "Morceau en chaîne",
        exercise = listOf(
            "Construis une chaîne : intro, montée, break sans kick, retour.",
        ),
        success = "La chaîne joue le morceau sans intervention.",
        style = "Hardtek / tribe : intro, montée, break sans kick, retour.",
        mistakes = listOf(
            "Oublier EXIT : la chaîne n'est enregistrée qu'en sortant.",
        ),
        quiz = listOf(
            qz("Motifs maximum par chaîne ?", listOf("16", "64"), 0,
                "Et 16 chaînes par projet.", CHAIN),
            qz("Choisir une chaîne ?", listOf("HOLD + pad", "SHIFT + pad"), 0,
                "En mode PATTERN SELECT.", CHAIN),
            qz("Doubler un motif ?", listOf("VALUE > DUPLICATE", "COPY"), 0,
                "Dans PATTERN EDIT.", PTN_EDIT),
            qz("Longueurs dans PATTERN EDIT ?", listOf("1, 2, 4, 8, 16, 32, 64", "1 à 100"), 0,
                "Sur CTRL 3.", PTN_EDIT),
        ),
    )

    private val m7l3 = ctLesson(
        M7, 3, "Le mode DJ", listOf(DJ), 15,
        objective = "Mixer deux échantillons comme deux platines.",
        explanation = "Appuyer ensemble sur Bank [D/I] et [E/J] : DJ MIXER. VALUE ouvre le menu : CH1 SELECT ou CH2 SELECT, puis un pad pour " +
            "choisir l'échantillon, ENTER pour valider. CTRL 1 et CTRL 2 règlent les niveaux CH1 et CH2, CTRL 3 la balance de pré-écoute " +
            "au casque. Pads [13] / [15] : lecture/pause CH1 / CH2 ; [9] / [11] : retour au début ; [14] / [16] : envoi en pré-écoute ; " +
            "[10] ou [12] : SYNC, le CH1 suit le tempo du CH2.",
        controls = listOf(
            cmd("Bank [D/I] + [E/J]", "Entrer en mode DJ.", DJ),
            cmd("CTRL 1 / CTRL 2 / CTRL 3", "Niveaux CH1, CH2, pré-écoute.", DJ),
            cmd("Pads [13] / [15], [9] / [11]", "Lecture/pause, retour au début.", DJ),
            cmd("Pad [10] ou [12]", "Synchroniser CH1 sur CH2.", DJ),
        ),
        steps = listOf(
            "Bank [D/I] + [E/J] ensemble.",
            "VALUE > CH1 SELECT, choisis une boucle ; idem pour CH2.",
            "Lance CH2 avec pad [15], puis CH1 avec pad [13], et appuie sur [10] pour synchroniser.",
            "Fais le fondu avec CTRL 1 et CTRL 2.",
        ),
        expected = "Deux boucles mixées et calées.",
        exerciseTitle = "Transition de set",
        exercise = listOf(
            "Prépare la boucle suivante en pré-écoute au casque, puis fais-la entrer.",
        ),
        success = "La transition est fluide, sans décalage.",
        style = "Free party : enchaîner les boucles comme un DJ entre deux morceaux.",
        mistakes = listOf(
            "Niveau CH1 ou CH2 à zéro : on n'entend pas l'échantillon en pré-écoute.",
        ),
        quiz = listOf(
            qz("Entrer en mode DJ ?", listOf("Bank [D/I] + [E/J]", "SHIFT + PATTERN SELECT"), 0,
                "Appui simultané.", DJ),
            qz("Synchroniser ?", listOf("Pad [10] ou [12]", "Pad [1]"), 0,
                "Le CH1 suit le tempo du CH2.", DJ),
            qz("Que règle CTRL 3 ?", listOf("La balance de pré-écoute", "Le tempo"), 0,
                "Mixage CUE au casque.", DJ),
            qz("Lecture/pause du CH1 ?", listOf("Pad [13]", "Pad [16]"), 0,
                "Pad [15] pour le CH2.", DJ),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(
            M5, 5, "Éditer un échantillon",
            "Début, fin, boucle, marqueurs, découpe, hauteur, vitesse, rangement.",
            "Préparer des échantillons propres et jouables.",
            "Breaks découpés et stabs accordés pour la tribe.",
            listOf(m5l1, m5l2, m5l3),
        ),
        CourseModule(
            M6, 6, "Le séquenceur de motifs",
            "Lecture, enregistrement en temps réel, TR-REC, rééchantillonnage.",
            "Enregistrer et jouer des motifs.",
            "Des motifs de batterie programmés pour tenir le live.",
            listOf(m6l1, m6l2, m6l3),
        ),
        CourseModule(
            M7, 7, "Tempo, chaînes et mode DJ",
            "Tempo, BPM SYNC, édition et chaînes de motifs, mode DJ.",
            "Construire et mixer un set complet.",
            "Chaînes de motifs et mode DJ pour un set autonome.",
            listOf(m7l1, m7l2, m7l3),
        ),
    )
}
