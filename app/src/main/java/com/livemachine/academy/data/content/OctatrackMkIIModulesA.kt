package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** Octatrack MKII — modules 1 à 4 : prise en main, projets et samples, machines, séquenceur. */
internal object OctatrackMkIIModulesA {

    // Références du manuel (pages imprimées = pages du PDF)
    private val FACADE = ot("3.1 Front panel", "12–13")
    private val ARRIERE = ot("3.2–3.5 Rear connectors, CF card, connecting", "14–15")
    private val STRUCTURE = ot("4. Overview of the structure", "16–17")
    private val INFOS = ot("4.12 How information is handled", "18")
    private val INTERFACE = ot("5.1–5.4 Screen, parameter editing, copy/clear/paste", "19–20")
    private val DEMO = ot("6.1 Demo mode", "22–23")
    private val MONTER = ot("6.2 Mounting a set and loading a project", "23–24")
    private val SETS = ot("7. Sets / 7.2 Audio pool", "26–27")
    private val PROJETS = ot("8. Projects", "28")
    private val CHARGER = ot("6.3 Loading samples", "24–25")
    private val NAVIGATEUR = ot("8.3.2–8.3.3 SRC setup menu, file browser", "30–31")
    private val PROJ_MENU = ot("8.4.1 Project menu", "31–32")
    private val MACHINES = ot("4.11 Machines", "17")
    private val ASSIGNER = ot("11.2–11.3 Assigning machines and samples", "55–56")
    private val PAGES = ot("11.4 Track parameter pages", "57")
    private val FLEX = ot("Appendix A.2 Flex machine", "118")
    private val AMP = ot("11.4.5 AMP main", "58")
    private val LFO = ot("11.4.7–11.4.8 LFO main and setup", "59–60")
    private val FX = ot("11.4.10 FX1 and FX2 / 11.5 Direct track muting", "62")
    private val BANQUES = ot("10.1 Banks", "52")
    private val PATTERNS = ot("12.2 Basic pattern operations", "64–65")
    private val GRID = ot("12.3.1 Grid recording mode", "65")
    private val LIVE = ot("12.3.2 Live recording mode", "65–66")
    private val TRIGS = ot("12.4 Trig types", "66–67")
    private val PLOCKS = ot("12.5–12.6 Parameter locks, sample locks", "67–68")

    // ---------------------------------------------------------------------
    // Module 1 — Prise en main
    // ---------------------------------------------------------------------
    private const val M1 = "ot2-m1"

    private val m1l1 = ctLesson(
        M1, 1, "Façade, prises et carte Compact Flash", listOf(FACADE, ARRIERE, INFOS), 20,
        objective = "Repérer les zones de l'Octatrack MKII, la brancher et manipuler la carte sans perdre de données.",
        explanation = "Sur la façade : [REC1]/[REC2] (échantillonner les entrées AB et CD), [REC3] (sources internes), les 8 touches " +
            "[TRACK], LEVEL et les encodeurs DATA ENTRY, les touches de menu ([PROJ], [PART], [AED], [MIX], [ARR], [MIDI]), " +
            "[SCENE A]/[SCENE B] et le crossfader, les [TRIG 1–16], [PTN], [BANK], [CUE] et le transport. À l'arrière : MAIN OUT, " +
            "CUE OUT (sorties de pré-écoute), INPUT AB et CD, MIDI, USB, lecteur Compact Flash. On n'éteint jamais la machine " +
            "pendant que la LED <CARD STATUS> clignote : la carte est en cours d'écriture.",
        controls = listOf(
            cmd("[TRACK 1–8]", "Choix de la piste ; [FUNC] + [TRACK] = mute.", FACADE),
            cmd("Crossfader et [SCENE A]/[SCENE B]", "Passage d'une scène à l'autre.", FACADE),
            cmd("[CUE]", "Envoie une piste vers les sorties CUE.", FACADE),
            cmd("MAIN OUT / CUE OUT / INPUT AB, CD", "Sorties principales, pré-écoute, entrées.", ARRIERE),
            cmd("<CARD STATUS>", "Activité de la carte ; ne pas éteindre quand elle clignote.", INFOS),
        ),
        steps = listOf(
            "Tout éteint, branche l'adaptateur fourni puis relie MAIN OUT L/R à ta table.",
            "Insère la carte Compact Flash et allume.",
            "Repère [REC1], [REC2], [REC3], les touches [TRACK], le crossfader et les touches [SCENE].",
            "Repère [PROJ], [PART], [AED], [MIX], [ARR], [MIDI] et leurs fonctions secondaires (avec [FUNC]).",
            "Avant d'éteindre, vérifie que la LED <CARD STATUS> est verte, faible et fixe.",
        ),
        expected = "La machine démarre et tu sais quand on peut l'éteindre sans risque.",
        exerciseTitle = "Repérage complet",
        exercise = listOf(
            "Nomme le rôle de [REC1], [REC2] et [REC3].",
            "Montre les prises CUE OUT et explique à quoi elles servent.",
        ),
        success = "Tu distingues l'échantillonnage externe (REC1/2) et interne (REC3), et les sorties MAIN/CUE.",
        mistakes = listOf(
            "Éteindre ou retirer la carte pendant que la LED clignote : risque de données corrompues.",
            "Utiliser une autre alimentation que celle fournie : le manuel l'interdit.",
        ),
        quiz = listOf(
            qz("À quoi sert [REC3] ?", listOf("Échantillonner des sources internes", "Échantillonner l'entrée AB", "Lancer la lecture"), 0,
                "[REC1]/[REC2] pour les entrées externes, [REC3] pour les sources internes.", FACADE),
            qz("Comment couper une piste directement ?", listOf("[FUNC] + [TRACK]", "[CUE] + [TRACK]", "[TRACK] + [STOP]"), 0,
                "[FUNC] + [TRACK] met la piste en sourdine.", FACADE),
            qz("Quand peut-on éteindre l'Octatrack sans risque ?",
                listOf("LED <CARD STATUS> verte faible et fixe", "LED qui clignote", "N'importe quand"), 0,
                "Ne jamais éteindre quand la LED clignote : la carte est en écriture.", INFOS),
            qz("À quoi servent les sorties CUE ?", listOf("À pré-écouter des pistes séparément", "À brancher le MIDI", "À alimenter"), 0,
                "[CUE] + [TRACK] envoie la piste vers les sorties de pré-écoute.", FACADE),
        ),
    )

    private val m1l2 = ctLesson(
        M1, 2, "Set, projet, banques, patterns, parts et scènes", listOf(STRUCTURE), 15,
        objective = "Comprendre l'organisation de l'Octatrack pour savoir où se trouvent tes sons et tes séquences.",
        explanation = "Un set (sur la carte) contient des projets et un audio pool, la réserve de samples. Un projet contient 16 " +
            "banques, 8 arrangements, 8 enregistreurs de piste et deux listes de 128 emplacements de samples (Flex, en mémoire vive, " +
            "et Static, lus depuis la carte). Chaque banque a 16 patterns et 4 parts. Un pattern contient la séquence ; une part " +
            "contient les machines, les samples, les effets, les réglages des pistes et 16 scènes. Chaque pattern est lié à une part.",
        controls = listOf(
            cmd("[PROJ]", "Menu projet (sets, projets, sauvegarde).", FACADE),
            cmd("[BANK] / [PTN]", "Choix de banque et de pattern.", FACADE),
            cmd("[PART]", "Choix de la part liée au pattern.", FACADE),
        ),
        steps = listOf(
            "Ouvre [PROJ] et lis le nom du set et du projet actifs en haut de l'écran.",
            "Ressors avec [NO].",
            "Appuie sur [PART] et regarde quelle part est liée au pattern en cours, puis [NO].",
            "Dessine la hiérarchie set > projet > banque > patterns et parts > scènes.",
        ),
        expected = "Tu sais ce que contient chaque niveau et où il se règle.",
        exerciseTitle = "Ranger un live",
        exercise = listOf(
            "Décide comment tu organiserais un set de 4 morceaux : une banque par morceau ?",
            "Vérifie avec la page 17 qu'une banque a bien assez de patterns et de parts pour un morceau.",
        ),
        success = "Ton plan tient dans les limites du manuel (16 patterns et 4 parts par banque).",
        mistakes = listOf(
            "Confondre pattern (séquence) et part (sons, machines, effets, scènes).",
            "Croire que les samples sont dans le projet : ils viennent de l'audio pool du set.",
        ),
        quiz = listOf(
            qz("Combien de banques dans un projet ?", listOf("4", "8", "16"), 2,
                "Un projet contient 16 banques.", STRUCTURE),
            qz("Que contient une part ?",
                listOf("Machines, samples, effets, réglages et 16 scènes", "Seulement la séquence", "Le tempo"), 0,
                "La part regroupe tout le son ; le pattern la séquence.", STRUCTURE),
            qz("Où sont stockés les samples d'un set ?", listOf("Dans l'audio pool", "Dans chaque pattern", "Dans les scènes"), 0,
                "L'audio pool du set contient les samples utilisés par ses projets.", STRUCTURE),
            qz("Combien de parts par banque ?", listOf("2", "4", "16"), 1,
                "Chaque banque a 16 patterns et 4 parts.", STRUCTURE),
        ),
    )

    private val m1l3 = ctLesson(
        M1, 3, "Premiers sons en mode démo", listOf(DEMO, INTERFACE), 15,
        objective = "Jouer les patterns de démonstration et apprendre à régler, revenir et copier.",
        explanation = "Sans carte, maintenir [YES] à l'allumage lance le mode démo : 16 patterns A01–A16 jouables (rien n'est sauvé). " +
            "[PTN] + [TRIG] change de pattern, [FUNC] + [TRACK] coupe une piste. Une touche de page ([SRC], [AMP]…) ouvre les " +
            "paramètres de la piste ; l'encodeur A de [SRC] règle PTCH. Appuyer sur un encodeur en tournant va par pas de 7 ; " +
            "[FUNC] en tournant fait sauter aux valeurs clés. [FUNC] + [CUE] recharge les réglages d'origine de la part. " +
            "Copier/coller/effacer : [FUNC] + [RECORD]/[STOP]/[PLAY].",
        controls = listOf(
            cmd("[YES] à l'allumage (sans carte)", "Mode démo.", DEMO),
            cmd("[PTN] + [TRIG]", "Change de pattern.", DEMO),
            cmd("[SRC] + encodeur A (PTCH)", "Hauteur du sample de la piste.", DEMO),
            cmd("[FUNC] + [CUE]", "Recharge les réglages de la part.", DEMO),
            cmd("[FUNC] + [RECORD]/[STOP]/[PLAY]", "Copier / coller / effacer.", INTERFACE),
        ),
        steps = listOf(
            "Carte retirée, maintiens [YES] et allume : mode démo.",
            "Appuie sur [PLAY], puis [PTN] + [TRIG 2] pour passer à A02.",
            "Coupe une piste avec [FUNC] + [TRACK], puis rallume-la.",
            "Choisis une piste avec [TRACK], appuie sur [SRC] et tourne l'encodeur A : la hauteur change.",
            "Reviens aux réglages d'origine avec [FUNC] + [CUE].",
        ),
        expected = "Les patterns de démo jouent et tu sais déformer puis rétablir un son.",
        exerciseTitle = "Remix de démo",
        exercise = listOf(
            "Sur A01, change la hauteur de deux pistes et coupe-en une.",
            "Reviens à l'état d'origine en un geste.",
        ),
        success = "Après [FUNC] + [CUE], le pattern sonne comme au départ.",
        mistakes = listOf(
            "Chercher à sauver en mode démo : rien n'y est conservé.",
            "Oublier de sélectionner la bonne piste avant de tourner un encodeur.",
        ),
        quiz = listOf(
            qz("Comment entrer en mode démo ?", listOf("Maintenir [YES] à l'allumage, sans carte", "[FUNC] + [PROJ]", "[NO] à l'allumage"), 0,
                "Sans carte, [YES] maintenu à l'allumage lance le mode démo.", DEMO),
            qz("Les changements en mode démo sont-ils sauvegardés ?", listOf("Oui", "Non"), 1,
                "On ne peut rien sauvegarder en mode démo.", DEMO),
            qz("Que fait un encodeur appuyé pendant qu'on le tourne ?", listOf("Pas de 7", "Remise à zéro", "Verrouillage"), 0,
                "Les paramètres varient par incréments de 7.", INTERFACE),
            qz("Que fait [FUNC] + [CUE] ?", listOf("Recharge les réglages de la part", "Coupe les sorties CUE", "Sauve le projet"), 0,
                "Il ramène les paramètres de la part à leur état d'origine.", DEMO),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 2 — Projets et samples
    // ---------------------------------------------------------------------
    private const val M2 = "ot2-m2"

    private val m2l1 = ctLesson(
        M2, 1, "Monter un set et ouvrir un projet", listOf(MONTER, SETS, PROJETS), 20,
        objective = "Préparer la carte : monter (activer) un set, puis créer ou charger un projet.",
        explanation = "Il faut un set monté et un projet chargé pour travailler normalement. [PROJ] > PROJECT > CHANGE (section SET) " +
            "ouvre la liste des sets : <CREATE NEW SET> en crée un. Ensuite PROJECT > CHANGE (section PROJECT) charge un projet ou " +
            "en crée un vide. La carte fournie contient un set et un projet « PRESETS ». Si tu travailles hors projet, utilise SAVE TO " +
            "NEW pour garder tes changements.",
        controls = listOf(
            cmd("[PROJ] > PROJECT > CHANGE (SET)", "Monter ou créer un set.", MONTER),
            cmd("[PROJ] > PROJECT > CHANGE (PROJECT)", "Charger ou créer un projet.", MONTER),
            cmd("[YES] / [NO]", "Valider, annuler.", INTERFACE),
        ),
        steps = listOf(
            "Carte insérée, ouvre [PROJ], choisis PROJECT et valide avec [YES].",
            "Descends jusqu'à CHANGE dans la section SET et valide.",
            "Choisis « PRESETS » (carte fournie) ou <CREATE NEW SET>, nomme-le et valide.",
            "Dans la liste des projets qui s'ouvre, choisis « PRESETS » ou <CREATE EMPTY PROJECT>.",
        ),
        expected = "Un set est monté et un projet est chargé : son nom s'affiche en haut du menu [PROJ].",
        exerciseTitle = "Projet de répétition",
        exercise = listOf(
            "Crée un nouveau projet vide nommé « LIVE1 » dans ton set.",
            "Vérifie son nom dans le menu [PROJ].",
        ),
        success = "Le projet LIVE1 est actif.",
        mistakes = listOf(
            "Travailler sans projet chargé : les changements seront perdus en changeant de set.",
            "Changer de set sans avoir fait SAVE TO NEW alors qu'aucun projet n'était actif.",
        ),
        quiz = listOf(
            qz("Que faut-il pour que l'Octatrack fonctionne normalement ?",
                listOf("Un set monté et un projet chargé", "Seulement une carte", "Un ordinateur"), 0,
                "Un set monté et un projet chargé sont nécessaires.", SETS),
            qz("Où monte-t-on un set ?", listOf("[PROJ] > PROJECT > CHANGE (SET)", "[PART]", "[AED]"), 0,
                "Les sets se montent et se créent dans le menu PROJECT.", MONTER),
            qz("Que contient le set de la carte fournie ?", listOf("Un projet « PRESETS »", "Rien", "16 sets"), 0,
                "La carte livrée contient le set et le projet PRESETS.", MONTER),
            qz("Hors projet, comment garder ses changements ?", listOf("SAVE TO NEW", "SYNC TO CARD", "RELOAD"), 0,
                "SAVE TO NEW enregistre l'état actuel dans un nouveau projet.", PROJETS),
        ),
    )

    private val m2l2 = ctLesson(
        M2, 2, "Remplir l'audio pool et charger des samples", listOf(SETS, CHARGER, NAVIGATEUR), 20,
        objective = "Copier tes samples sur la carte, puis les charger dans les emplacements Flex ou Static d'un projet.",
        explanation = "Pour remplir l'audio pool : relie la machine en USB, [PROJ] > SYSTEM > USB DISK MODE, puis copie tes fichiers " +
            "dans le dossier AUDIO du set (formats : WAV ou AIFF 16 ou 24 bits, 44,1 kHz, mono ou stéréo). Ensuite, sur une piste " +
            "avec une machine Flex ou Static, [FUNC] + [SRC] ouvre la liste d'emplacements ; [YES] sur un emplacement vide ouvre le " +
            "navigateur de fichiers. Le smiley indique si le fichier est compatible (un 48 kHz jouera à la mauvaise vitesse). " +
            "[FUNC] + [YES] fait écouter.",
        controls = listOf(
            cmd("[PROJ] > SYSTEM > USB DISK MODE", "La carte apparaît sur l'ordinateur.", SETS),
            cmd("[FUNC] + [SRC]", "Liste des emplacements et des machines.", CHARGER),
            cmd("Navigateur de fichiers (smiley)", "Choisir et vérifier un sample.", NAVIGATEUR),
            cmd("[FUNC] + [YES] / [CUE] + [YES]", "Écoute sur MAIN / sur CUE.", NAVIGATEUR),
        ),
        steps = listOf(
            "Relie l'Octatrack en USB, active USB DISK MODE et copie des samples dans le dossier AUDIO du set.",
            "Démonte la carte côté ordinateur, puis quitte le mode disque.",
            "Choisis la piste 1, appuie sur [FUNC] + [SRC].",
            "Sélectionne un emplacement vide, [YES], puis navigue jusqu'à ton sample.",
            "Écoute avec [FUNC] + [YES] et charge-le avec [YES].",
        ),
        expected = "Ton sample est chargé dans un emplacement et peut être utilisé par la piste.",
        exerciseTitle = "Kit de départ",
        exercise = listOf(
            "Charge un kick, une caisse claire, un charley et une boucle dans quatre emplacements Flex.",
            "Vérifie le smiley pour chacun.",
        ),
        success = "Les quatre samples ont un smiley content et sont dans la liste Flex.",
        mistakes = listOf(
            "Utiliser des fichiers 48 kHz : ils jouent à la mauvaise vitesse (smiley indifférent).",
            "Mettre les samples dans les dossiers de projet plutôt que dans AUDIO : le manuel conseille AUDIO.",
        ),
        quiz = listOf(
            qz("Quels fichiers l'Octatrack lit-il ?", listOf("WAV/AIFF 16 ou 24 bits, 44,1 kHz", "MP3", "WAV 48 kHz seulement"), 0,
                "Flex et Static gèrent le WAV/AIFF 16 ou 24 bits à 44,1 kHz.", SETS),
            qz("Dans quel dossier du set copier les samples ?", listOf("AUDIO", "PROJECT", "BANK"), 0,
                "Le dossier AUDIO est l'audio pool du set.", SETS),
            qz("Que signifie un smiley triste dans le navigateur ?",
                listOf("Fichier trop gros ou incompatible", "Fichier parfait", "Fichier déjà chargé"), 0,
                "Triste : trop gros ou incompatible ; indifférent : mauvaise fréquence.", NAVIGATEUR),
            qz("Comment écouter un sample sur les sorties principales ?", listOf("[FUNC] + [YES]", "[CUE] + [YES]", "[PLAY]"), 0,
                "[FUNC] + [YES] écoute sur MAIN, [CUE] + [YES] sur CUE.", NAVIGATEUR),
        ),
    )

    private val m2l3 = ctLesson(
        M2, 3, "Sauvegarder et revenir en arrière", listOf(INFOS, PROJETS, PROJ_MENU), 15,
        objective = "Comprendre la sauvegarde automatique et garder des points de retour pour ton projet.",
        explanation = "Tout changement dans un projet est gardé automatiquement sur la carte, même après extinction. La commande SAVE " +
            "([FUNC] + [PROJ]) fige un état : RELOAD y revient. SAVE CURRENT BANK et RELOAD CURRENT BANK font la même chose pour la " +
            "banque active. Avant de retirer la carte, il faut faire SYNC TO CARD. SAVE TO NEW crée une copie du projet sous un autre nom.",
        controls = listOf(
            cmd("[FUNC] + [PROJ]", "Sauvegarde rapide du projet.", PROJ_MENU),
            cmd("[PROJ] > PROJECT > RELOAD", "Revient à l'état sauvegardé.", PROJ_MENU),
            cmd("SYNC TO CARD", "À faire avant d'éjecter la carte.", PROJ_MENU),
            cmd("SAVE / RELOAD CURRENT BANK", "Sauvegarde et retour pour la banque active.", PROJ_MENU),
        ),
        steps = listOf(
            "Quand ton projet te plaît, appuie sur [FUNC] + [PROJ].",
            "Modifie franchement plusieurs patterns.",
            "Ouvre [PROJ] > PROJECT > RELOAD et valide : tout revient à l'état sauvé.",
            "Avant de retirer la carte, lance SYNC TO CARD.",
        ),
        expected = "Tu reviens à un état précis de ton projet quand tu veux.",
        exerciseTitle = "Point de retour avant le live",
        exercise = listOf(
            "Sauve ton projet, expérimente 10 minutes, puis reviens à l'état sauvé avec RELOAD.",
            "Fais SYNC TO CARD avant d'éjecter la carte.",
        ),
        success = "Après RELOAD, le projet est exactement celui que tu avais sauvé.",
        mistakes = listOf(
            "Croire qu'il faut sauver pour ne rien perdre : la mise en cache est automatique, SAVE sert de point de retour.",
            "Retirer la carte sans SYNC TO CARD : risque de pertes.",
        ),
        quiz = listOf(
            qz("Les changements d'un projet sont-ils perdus à l'extinction ?", listOf("Oui", "Non, ils sont gardés automatiquement"), 1,
                "Tous les changements sont mis en cache sur la carte.", PROJETS),
            qz("À quoi sert SAVE ?", listOf("Créer un point de retour pour RELOAD", "Éviter toute perte à l'extinction", "Exporter"), 0,
                "Sauver puis recharger est le seul moyen de revenir à un état précis.", PROJ_MENU),
            qz("Que faire avant d'éjecter la carte ?", listOf("SYNC TO CARD", "PURGE SAMPLES", "Rien"), 0,
                "SYNC TO CARD synchronise le cache avec la carte.", PROJ_MENU),
            qz("Quel raccourci sauve le projet ?", listOf("[FUNC] + [PROJ]", "[FUNC] + [YES]", "[PROJ] + [PLAY]"), 0,
                "[FUNC] + [PROJ] fait une sauvegarde rapide.", PROJ_MENU),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 3 — Machines et pistes
    // ---------------------------------------------------------------------
    private const val M3 = "ot2-m3"

    private val m3l1 = ctLesson(
        M3, 1, "Les machines et leur assignation", listOf(MACHINES, ASSIGNER, CHARGER), 20,
        objective = "Choisir la bonne machine pour chaque piste et lui donner un sample.",
        explanation = "Chaque piste audio reçoit une machine : Flex (sample en mémoire vive, manipulations les plus poussées), Static " +
            "(sample lu depuis la carte, jusqu'à 2 Go), Thru (écoute et traite les entrées), Neighbor (traite la sortie de la piste " +
            "précédente, pour enchaîner des effets) et Pickup (looper). Double appui rapide sur [TRACK] ouvre QUICK ASSIGN ; " +
            "[FUNC] + [SRC] ouvre SRC SETUP : [LEFT] pour la liste des machines, [YES] pour assigner, [RIGHT] pour la liste des samples.",
        controls = listOf(
            cmd("Double appui sur [TRACK]", "Menu QUICK ASSIGN.", ASSIGNER),
            cmd("[FUNC] + [SRC]", "Menu SRC SETUP (machines, samples).", ASSIGNER),
            cmd("[LEFT] / [RIGHT]", "Liste des machines / liste des samples.", ASSIGNER),
        ),
        steps = listOf(
            "Choisis la piste 1 et ouvre [FUNC] + [SRC].",
            "Appuie sur [LEFT] pour la liste des machines, choisis Flex et valide avec [YES].",
            "Appuie sur [RIGHT] et assigne un sample avec [YES].",
            "Sur la piste 2, fais un double appui rapide sur [TRACK 2] et assigne une machine Static avec un long sample.",
        ),
        expected = "La piste 1 joue un sample Flex, la piste 2 un long sample Static.",
        exerciseTitle = "Quatre pistes prêtes",
        exercise = listOf(
            "Prépare quatre pistes Flex avec kick, caisse claire, charley et percussion.",
            "Ajoute une piste Static avec une longue boucle ou un morceau.",
        ),
        success = "Les cinq pistes jouent leur sample quand on les déclenche.",
        mistakes = listOf(
            "Mettre une machine Thru et chercher un sample : seules Flex et Static jouent des samples.",
            "Mettre une machine Neighbor sur la piste 1 : elle écoute la piste précédente.",
        ),
        quiz = listOf(
            qz("Quelle machine charge le sample en mémoire vive ?", listOf("Flex", "Static", "Thru"), 0,
                "Flex charge en RAM ; Static lit depuis la carte.", MACHINES),
            qz("Quelle machine sert de looper ?", listOf("Pickup", "Neighbor", "Flex"), 0,
                "Les machines Pickup sont faites pour boucler en direct.", MACHINES),
            qz("Comment ouvrir QUICK ASSIGN ?", listOf("Double appui rapide sur [TRACK]", "[FUNC] + [TRACK]", "[CUE] + [TRACK]"), 0,
                "Un double appui rapide sur la touche [TRACK].", ASSIGNER),
            qz("Que fait une machine Thru ?", listOf("Écoute et traite les entrées", "Joue un long sample", "Boucle"), 0,
                "Thru permet d'écouter les entrées et de les traiter avec filtres et effets.", MACHINES),
        ),
    )

    private val m3l2 = ctLesson(
        M3, 2, "Pages de paramètres et machine Flex", listOf(PAGES, FLEX), 20,
        objective = "Utiliser les pages MAIN et SETUP, et les réglages essentiels d'une machine Flex.",
        explanation = "Chaque piste a cinq pages : [SRC], [AMP], [LFO], [FX1], [FX2]. Un appui ouvre la page MAIN (paramètres " +
            "verrouillables, assignables aux scènes et aux LFO) ; [FUNC] + la touche ou un double appui ouvre la page SETUP. Machine " +
            "Flex : PTCH (± 1 octave), STRT (début), LEN (longueur), RATE (vitesse, négatif = à l'envers), RTRG et RTIM (répétitions). " +
            "LEVEL règle la piste après effets. Touche de page + [YES] rend la page aléatoire, + [NO] la remet à son état sauvé.",
        controls = listOf(
            cmd("[SRC] / [AMP] / [LFO] / [FX1] / [FX2]", "Pages MAIN ; [FUNC] ou double appui = SETUP.", PAGES),
            cmd("PTCH / STRT / LEN / RATE", "Hauteur, début, longueur, vitesse.", FLEX),
            cmd("RTRG / RTIM", "Nombre et intervalle des répétitions.", FLEX),
            cmd("Page + [YES] / Page + [NO]", "Aléatoire / retour à l'état sauvé.", PAGES),
        ),
        steps = listOf(
            "Sur une piste Flex avec une boucle, appuie sur [SRC].",
            "Monte PTCH puis remets-le au centre.",
            "Avance STRT pour démarrer plus loin, raccourcis LEN.",
            "Mets RATE en négatif : la boucle joue à l'envers.",
            "Essaie [SRC] + [YES] pour un réglage aléatoire, puis [SRC] + [NO] pour revenir.",
        ),
        expected = "La boucle change de hauteur, de portion et de sens, puis revient à l'état sauvé.",
        exerciseTitle = "Boucle déformée",
        exercise = listOf(
            "À partir d'une boucle, fais un roulement avec RTRG et RTIM.",
            "Fais une version inversée avec RATE négatif.",
        ),
        success = "Les deux versions sont nettement différentes de la boucle d'origine.",
        style = "Tribe / hardtek : retrigs et boucles inversées pour les transitions.",
        mistakes = listOf(
            "Chercher à verrouiller un paramètre de page SETUP : seuls les paramètres MAIN se verrouillent.",
            "Mettre RATE à 0 : le sample ne joue plus du tout.",
        ),
        quiz = listOf(
            qz("Comment ouvrir la page SETUP de [SRC] ?", listOf("[FUNC] + [SRC] ou double appui", "[SRC] + [YES]", "[CUE] + [SRC]"), 0,
                "[FUNC] + la touche de page, ou un double appui rapide.", PAGES),
            qz("Que fait RATE négatif sur une machine Flex ?", listOf("Joue le sample à l'envers", "Coupe le son", "Double la hauteur"), 0,
                "Les valeurs négatives font jouer le sample à l'envers ; 0 l'arrête.", FLEX),
            qz("Quelle plage a PTCH ?", listOf("± 1 octave", "± 2 octaves", "± 5 octaves"), 0,
                "Le maximum monte d'une octave, le minimum descend d'une octave.", FLEX),
            qz("Quels paramètres peut-on verrouiller par pas ?", listOf("Ceux des pages MAIN", "Ceux des pages SETUP"), 0,
                "Seuls les paramètres MAIN sont verrouillables et assignables aux scènes.", PAGES),
        ),
    )

    private val m3l3 = ctLesson(
        M3, 3, "Enveloppe, LFO, effets et mutes", listOf(AMP, LFO, FX), 20,
        objective = "Façonner le volume, animer un paramètre avec un LFO et ajouter deux effets par piste.",
        explanation = "[AMP] : ATK, HOLD (en pas), REL, VOL (avant effets) et BAL. [LFO] : vitesse SPD et profondeur DEP des trois LFO, " +
            "toujours calés sur le tempo ; dans la page SETUP, PMTR choisit le paramètre modulé, WAVE la forme, TRIG le comportement " +
            "(FREE, TRIG, HOLD, ONE). Chaque piste a deux emplacements d'effets : FX1 (filtre, EQ, phaser, compresseur, lo-fi…) et " +
            "FX2 (les mêmes plus delay, réverbérations). On les choisit dans la page SETUP de [FX1]/[FX2]. [FUNC] + [TRACK] coupe une piste.",
        controls = listOf(
            cmd("[AMP] : ATK, HOLD, REL, VOL, BAL", "Enveloppe et placement.", AMP),
            cmd("[LFO] : SPD, DEP ; SETUP : PMTR, WAVE, TRIG", "Trois LFO synchronisés.", LFO),
            cmd("[FUNC] + [FX1]/[FX2]", "Choix de l'effet.", FX),
            cmd("[FUNC] + [TRACK]", "Mute direct.", FX),
        ),
        steps = listOf(
            "Sur une piste de charley, raccourcis HOLD et REL dans [AMP].",
            "Ouvre [FUNC] + [FX1], choisis le filtre multimode et valide.",
            "Ouvre [FUNC] + [LFO], choisis comme PMTR la fréquence du filtre.",
            "Reviens sur [LFO], règle SPD sur 16 et monte DEP.",
            "Mets la Echo Freeze Delay en FX2 et dose-la.",
        ),
        expected = "Le charley est plus court, son filtre bouge en rythme et un écho apparaît.",
        exerciseTitle = "Piste vivante",
        exercise = listOf(
            "Sur une boucle de percussions, ajoute un filtre animé par LFO et une réverbération en FX2.",
            "Coupe et relance la piste avec [FUNC] + [TRACK] en rythme.",
        ),
        success = "La boucle bouge seule et tu la coupes/relances en mesure.",
        style = "Mentalcore : filtre résonant modulé et delay en FX2 pour les montées.",
        mistakes = listOf(
            "Chercher la réverbération en FX1 : les réverbs et le delay sont seulement en FX2.",
            "Régler SPD sans choisir de PMTR : le LFO ne module rien d'utile.",
        ),
        quiz = listOf(
            qz("Où se trouvent les réverbérations ?", listOf("En FX2", "En FX1", "Dans [AMP]"), 0,
                "Delay et réverbs ne sont disponibles qu'en FX2.", FX),
            qz("Les LFO sont-ils synchronisés au tempo ?", listOf("Oui, toujours", "Non"), 0,
                "Les LFO sont toujours calés sur le tempo.", LFO),
            qz("Où choisit-on le paramètre modulé par un LFO ?", listOf("PMTR dans LFO SETUP", "DEP", "AMP"), 0,
                "PMTR choisit le paramètre parmi les pages MAIN.", LFO),
            qz("Que règle VOL dans [AMP] ?", listOf("Le volume avant effets", "Le volume général", "La balance"), 0,
                "VOL règle le volume de la piste avant les effets.", AMP),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 4 — Le séquenceur
    // ---------------------------------------------------------------------
    private const val M4 = "ot2-m4"

    private val m4l1 = ctLesson(
        M4, 1, "Banques, patterns, transport et chaînes", listOf(BANQUES, PATTERNS), 15,
        objective = "Changer de banque et de pattern sans coupure, et enchaîner des patterns.",
        explanation = "[BANK] + [TRIG] choisit la banque, puis un [TRIG] choisit le pattern (vert = avec données, rouge = actif). " +
            "[PTN] + [TRIG] change de pattern dans la banque ; il faut choisir dans les 4 secondes. [PLAY] lance, un second [PLAY] " +
            "met en pause ; [STOP] arrête (le delay continue), [STOP] + [STOP] coupe tout. Chaîne : maintenir [PTN] puis les [TRIG] " +
            "dans l'ordre voulu ; uniquement dans la même banque et sans répéter un pattern.",
        controls = listOf(
            cmd("[BANK] + [TRIG] puis [TRIG]", "Banque puis pattern.", BANQUES),
            cmd("[PTN] + [TRIG]", "Pattern de la banque (4 s pour choisir).", PATTERNS),
            cmd("[PLAY] / [STOP] / [STOP] + [STOP]", "Lecture-pause, arrêt, arrêt total.", PATTERNS),
            cmd("[PTN] maintenu + [TRIG] dans l'ordre", "Chaîne de patterns.", PATTERNS),
        ),
        steps = listOf(
            "Appuie sur [BANK] + [TRIG 1] puis [TRIG 1] : pattern A01.",
            "Lance [PLAY], puis [PTN] + [TRIG 2] pour passer à A02.",
            "Maintiens [PTN] et appuie sur [TRIG 1], [TRIG 3], [TRIG 2] : chaîne A01 > A03 > A02.",
            "Appuie sur [STOP], puis [STOP] encore pour défaire la chaîne.",
        ),
        expected = "Les patterns s'enchaînent sans coupure, dans l'ordre de la chaîne.",
        exerciseTitle = "Ordre de passage",
        exercise = listOf(
            "Crée une chaîne de quatre patterns de la même banque.",
            "Joue-la deux fois, puis défais-la.",
        ),
        success = "La chaîne tourne en boucle dans le bon ordre.",
        mistakes = listOf(
            "Mettre deux fois le même pattern dans une chaîne : c'est impossible.",
            "Attendre plus de 4 secondes après [PTN] : la sélection est annulée.",
        ),
        quiz = listOf(
            qz("Combien de temps pour choisir après [PTN] ?", listOf("1 s", "4 s", "10 s"), 1,
                "Il faut choisir dans les quatre secondes.", PATTERNS),
            qz("Une chaîne peut-elle contenir deux fois le même pattern ?", listOf("Oui", "Non"), 1,
                "Une chaîne ne peut pas contenir le même pattern deux fois.", PATTERNS),
            qz("Que fait [STOP] + [STOP] ?", listOf("Arrête tout, delay compris, et envoie All Notes Off", "Efface le pattern", "Pause"), 0,
                "Double [STOP] coupe toutes les pistes et l'Echo Freeze Delay.", PATTERNS),
            qz("Quelle couleur indique un pattern contenant des données ?", listOf("Vert", "Rouge", "Éteint"), 0,
                "Vert = données, rouge = actif, éteint = vide.", PATTERNS),
        ),
    )

    private val m4l2 = ctLesson(
        M4, 2, "Programmer en GRID et LIVE RECORDING", listOf(GRID, LIVE), 20,
        objective = "Poser des trigs au pas, puis enregistrer en direct pendant la lecture.",
        explanation = "[RECORD] active le GRID RECORDING (touche fixe) : choisis la piste avec [TRACK], pose ou retire des sample " +
            "trigs (rouges) avec les [TRIG] ; [PAGE] change de page au-delà de 16 pas. [TRIG] + [YES] écoute un trig, [FUNC] + " +
            "[LEFT]/[RIGHT] décale toute la piste. [RECORD] + [PLAY] lance le LIVE RECORDING (touche clignotante) : [TRIG 9–16] " +
            "posent des trigs sur les pistes 1 à 8 et les encodeurs tournés créent des parameter locks. [NO] + [TRACK] efface en rythme ; " +
            "[PLAY] sort sans arrêter, [STOP] arrête tout.",
        controls = listOf(
            cmd("[RECORD]", "GRID RECORDING.", GRID),
            cmd("[RECORD] + [PLAY]", "LIVE RECORDING.", LIVE),
            cmd("[TRIG 9–16] en LIVE", "Trigs des pistes 1 à 8.", LIVE),
            cmd("[NO] + [TRACK]", "Efface en rythme les trigs de la piste.", LIVE),
        ),
        steps = listOf(
            "Appuie sur [RECORD], choisis la piste du kick avec [TRACK 1] et pose des trigs sur 1, 5, 9, 13.",
            "Choisis la piste 2 et pose la caisse claire sur 5 et 13 ; lance [PLAY].",
            "Sors du GRID, puis maintiens [RECORD] et appuie sur [PLAY].",
            "Joue [TRIG 11] (piste 3) en rythme pour enregistrer un charley.",
            "Efface une partie ratée avec [NO] + [TRACK 3], puis [PLAY] pour sortir.",
        ),
        expected = "Un rythme complet tourne : kick et caisse claire au pas, charley joué.",
        exerciseTitle = "Groove mixte",
        exercise = listOf(
            "Programme kick et caisse claire en GRID.",
            "Enregistre percussions et coupures de filtre en LIVE.",
        ),
        success = "Le pattern garde ton jeu en direct et tes réglages de filtre.",
        style = "Hardtek : kick au pas, le reste joué à la main.",
        mistakes = listOf(
            "En LIVE, appuyer sur [TRIG 1–8] au lieu de [TRIG 9–16] pour poser des trigs.",
            "Tourner un encodeur sans le vouloir en LIVE : cela crée des locks.",
        ),
        quiz = listOf(
            qz("Comment entrer en LIVE RECORDING ?", listOf("[RECORD] + [PLAY]", "[RECORD] seul", "[FUNC] + [RECORD]"), 0,
                "Maintenir [RECORD] et appuyer sur [PLAY] ; la touche clignote.", LIVE),
            qz("En LIVE, quel [TRIG] pose un trig sur la piste 1 ?", listOf("[TRIG 1]", "[TRIG 9]", "[TRIG 16]"), 1,
                "[TRIG 9] = piste 1, [TRIG 10] = piste 2, etc.", LIVE),
            qz("Comment effacer en rythme les trigs d'une piste en LIVE ?", listOf("[NO] + [TRACK]", "[FUNC] + [PLAY]", "[STOP] × 2"), 0,
                "[NO] + [TRACK] efface les trigs atteints par la lecture.", LIVE),
            qz("Comment décaler tous les trigs d'une piste en GRID ?",
                listOf("[FUNC] + [LEFT]/[RIGHT]", "[PAGE] + [LEFT]", "[TRACK] + [RIGHT]"), 0,
                "En GRID RECORDING, [FUNC] + flèches décale la piste.", GRID),
        ),
    )

    private val m4l3 = ctLesson(
        M4, 3, "Types de trigs, parameter locks et sample locks", listOf(TRIGS, PLOCKS), 25,
        objective = "Utiliser les différents trigs et donner à chaque pas ses propres réglages, voire son propre sample.",
        explanation = "Sample trig (rouge) : joue le sample. Lock trig ([FUNC] + [TRIG], vert pâle) : porte des locks sans jouer. " +
            "Trigless trig (vert vif, obtenu avec [TRIG] + [NO]) : relance LFO et enveloppes d'effets sans jouer le sample. One shot " +
            "([FUNC] + [TRIG] sur un trig existant, jaune) : ne joue qu'une fois. Parameter lock : en GRID, maintiens un trig et tourne " +
            "un encodeur (trig qui clignote) ; maintenir et appuyer sur l'encodeur le retire. Sample lock : maintiens un trig et utilise " +
            "[UP]/[DOWN] ou LEVEL pour choisir un autre sample, puis [YES].",
        controls = listOf(
            cmd("[FUNC] + [TRIG]", "Lock trig (ou one shot sur un trig existant).", TRIGS),
            cmd("[TRIG] + [NO]", "Convertit en trigless / lock trig.", TRIGS),
            cmd("[TRIG] maintenu + encodeur", "Parameter lock.", PLOCKS),
            cmd("[TRIG] maintenu + [UP]/[DOWN] ou LEVEL", "Sample lock.", PLOCKS),
        ),
        steps = listOf(
            "En GRID, sur la caisse claire, maintiens [TRIG 13] et monte PTCH dans [SRC] : seul ce coup change.",
            "Maintiens [TRIG 16] et choisis un autre sample avec [UP]/[DOWN], puis [YES].",
            "Pose un lock trig avec [FUNC] + [TRIG] sur un pas vide et verrouille un filtre.",
            "Retire le lock de PTCH en maintenant [TRIG 13] et en appuyant sur l'encodeur.",
        ),
        expected = "Certains pas sonnent différemment et un pas joue un autre sample.",
        exerciseTitle = "Une piste, plusieurs sons",
        exercise = listOf(
            "Sur une seule piste, fais jouer trois samples différents grâce aux sample locks.",
            "Ajoute des locks de hauteur sur deux pas.",
        ),
        success = "La piste alterne les sons comme prévu.",
        style = "Tribecore : sample locks pour varier les percussions sur une seule piste.",
        mistakes = listOf(
            "Tourner l'encodeur sans maintenir le trig : toute la piste change.",
            "Confondre trigless (relance LFO/enveloppes) et lock trig (ne relance rien).",
        ),
        quiz = listOf(
            qz("Comment poser un lock trig ?", listOf("[FUNC] + [TRIG]", "[TRIG] + [YES]", "[CUE] + [TRIG]"), 0,
                "[FUNC] + [TRIG] pose un lock trig (vert pâle).", TRIGS),
            qz("Que relance un trigless trig ?", listOf("LFO et enveloppes d'effets, sans le sample", "Le sample", "Rien"), 0,
                "Les trigless trigs déclenchent LFO et enveloppes d'effets.", TRIGS),
            qz("Comment faire jouer un autre sample sur un pas ?",
                listOf("Maintenir le trig et choisir avec [UP]/[DOWN] ou LEVEL", "[FUNC] + [SRC]", "[PTN] + [TRIG]"), 0,
                "C'est le sample lock.", PLOCKS),
            qz("Quelle couleur indique un one shot trig ?", listOf("Jaune", "Rouge", "Vert"), 0,
                "Le trig passe du rouge au jaune.", TRIGS),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(
            M1, 1, "Prise en main",
            "Façade, carte, structure et mode démo.",
            "Brancher l'Octatrack, comprendre son organisation et jouer les démos.",
            "La base avant d'utiliser l'Octatrack comme centre d'un live free party.",
            listOf(m1l1, m1l2, m1l3),
        ),
        CourseModule(
            M2, 2, "Projets et samples",
            "Sets, projets, audio pool, chargement et sauvegarde.",
            "Préparer la carte, charger tes samples et sécuriser ton projet.",
            "Un live solide commence par un projet propre et sauvegardé.",
            listOf(m2l1, m2l2, m2l3),
        ),
        CourseModule(
            M3, 3, "Machines et pistes",
            "Flex, Static, Thru, Neighbor, Pickup ; paramètres, LFO, effets.",
            "Assigner machines et samples et façonner chaque piste.",
            "Flex et effets permettent de transformer n'importe quelle boucle.",
            listOf(m3l1, m3l2, m3l3),
        ),
        CourseModule(
            M4, 4, "Le séquenceur",
            "Patterns, chaînes, GRID et LIVE RECORDING, trigs et locks.",
            "Programmer et varier des patterns pas à pas et en direct.",
            "Locks et sample locks rendent les boucles techno et tribe vivantes.",
            listOf(m4l1, m4l2, m4l3),
        ),
    )
}
