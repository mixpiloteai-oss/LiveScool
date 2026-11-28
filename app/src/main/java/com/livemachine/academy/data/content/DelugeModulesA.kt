package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** Deluge — modules 1 à 4 : prise en main, pas à pas, enregistrement et timing, gammes et clavier. */
internal object DelugeModulesA {

    // Références du guide (pages imprimées ; page du PDF = page imprimée + 6)
    private val BOITE = dlg("1.2 What's in the Box", "3")
    private val FACADE = dlg("1.3 Hardware Overview", "4–5")
    private val ARRIERE = dlg("Rear Panel, Notes", "6–8")
    private val LIBRAIRIE = dlg("2.1 Factory Library", "12")
    private val ALLUMAGE = dlg("2.2 Power Up", "16")
    private val COMMANDES = dlg("2.3 General Operating Controls", "17")
    private val VUES = dlg("2.4 Views", "18")
    private val SYNTH_KIT = dlg("2.5 Synths and Kits", "19")
    private val GRILLE = dlg("2.6 Clip View: Overview", "20–21")
    private val PRESETS = dlg("Selecting a Synth / Kit Preset", "22–23")
    private val SAVE_PRESET = dlg("Saving a Synth / Kit Preset", "24")
    private val DIRECTION = dlg("3.2 Playing a Sequence, Play Direction", "35–37")
    private val TEMPO = dlg("3.3 Tempo & Swing", "38–39")
    private val PAS_A_PAS = dlg("3.4 Step Recording", "40–41")
    private val LIVE = dlg("3.4 Live Recording", "42–43")
    private val DECOMPTE = dlg("Setting a Recording Count-In", "44")
    private val VELOCITE = dlg("3.5 Note Characteristics", "45")
    private val QUANTIZE = dlg("Recording Quantization, Clearing Clip Notes", "46")
    private val TRIOLETS = dlg("Triplet View", "47")
    private val LONGUEUR = dlg("Editing Notes' Length", "48")
    private val GAMMES = dlg("3.6 Note Scales", "50–51")
    private val TONIQUE = dlg("Root Note, Changing Scale, Transposing", "52")
    private val CUSTOM = dlg("Creating a Custom Scale", "53")
    private val CLAVIER = dlg("3.7 Keyboard View & Chords", "54–55")
    private val NAVIGATION = dlg("3.8 Navigating the Grid", "56–59")
    private val LONGUEUR_CLIP = dlg("3.9 Clip Length & Position", "60–61")
    private val CROSS = dlg("3.10 Cross-Screen Editing", "62–63")

    // ---------------------------------------------------------------------
    // Module 1 — Prise en main
    // ---------------------------------------------------------------------
    private const val M1 = "dlg-m1"

    private val m1l1 = ctLesson(
        M1, 1, "Alimentation, carte SD et sorties", listOf(BOITE, ARRIERE, ALLUMAGE), 15,
        objective = "Allumer le Deluge sans risque, avec sa carte SD, et le relier à ton écoute.",
        explanation = "Le Deluge se recharge par USB ou par une alimentation 9 à 12 V DC (non fournie) à centre NÉGATIF, 500 mA minimum : " +
            "une alimentation à centre positif peut l'endommager. Sa batterie interne tient environ 6 heures ; la LED est jaune en " +
            "charge, verte quand c'est chargé, rouge si la batterie est faible. La carte SD (FAT32, contacts vers le haut) contient " +
            "presets et samples, lus en direct : elle doit rester insérée. L'interrupteur est à l'arrière. Sorties : L / Mono et R " +
            "(6,35 mm) ; avec seulement L branchée, tout passe en mono. Casque en 3,5 mm.",
        controls = listOf(
            cmd("Alimentation 9–12 V DC centre négatif", "Ou USB pour charger.", ARRIERE),
            cmd("Interrupteur On/Off arrière", "Mise en marche.", ALLUMAGE),
            cmd("Fente SD (avant)", "Carte insérée contacts vers le haut.", ARRIERE),
            cmd("Line Out L / Mono et R", "Sorties principales.", ARRIERE),
        ),
        steps = listOf(
            "Vérifie que ton alimentation est bien à centre négatif, ou branche le câble USB.",
            "Insère la carte SD contacts vers le haut.",
            "Relie L / Mono et R à ta table (ou seulement L pour du mono).",
            "Allume avec l'interrupteur arrière : le Deluge démarre en vue Clip avec le synthé « 000 Rich Saw Bass ».",
        ),
        expected = "Le Deluge est allumé en vue Clip, prêt à jouer.",
        exerciseTitle = "Check-list d'installation",
        exercise = listOf(
            "Note la couleur de la LED d'alimentation pendant la charge.",
            "Joue quelques notes sur les pads Audition à droite pour vérifier le son.",
        ),
        success = "Tu entends le synthé de départ sur tes enceintes.",
        mistakes = listOf(
            "Utiliser une alimentation à centre positif : le guide prévient qu'elle peut endommager le Deluge.",
            "Retirer la carte SD en jouant : les samples sont lus directement dessus.",
        ),
        quiz = listOf(
            qz("Quelle polarité pour l'alimentation DC ?", listOf("Centre positif", "Centre négatif"), 1,
                "9 à 12 V DC, centre négatif, 500 mA minimum.", ARRIERE),
            qz("Autonomie de la batterie ?", listOf("Environ 6 heures", "30 minutes"), 0,
                "Environ 6 heures sur une charge complète.", ALLUMAGE),
            qz("Avec seulement la sortie L branchée…", listOf("Le son passe en mono", "Aucun son"), 0,
                "Le Deluge détecte la sortie seule et sort tout en mono.", ARRIERE),
            qz("Format de la carte SD ?", listOf("FAT32", "NTFS"), 0,
                "N'importe quelle taille, formatée en FAT32.", LIBRAIRIE),
        ),
    )

    private val m1l2 = ctLesson(
        M1, 2, "Vues, volume, tempo et lecture", listOf(COMMANDES, VUES, FACADE), 15,
        objective = "Se repérer dans les vues et maîtriser les commandes de base.",
        explanation = "Quatre vues principales : Clip (un instrument et sa séquence, bouton CLIP bleu), Song (tous les clips, une ligne " +
            "par clip, SONG bleu), Arranger (SONG clignote bleu) et Keyboard (la grille devient un instrument). OUTPUT LEVEL règle la " +
            "sortie et le casque, sans affichage de niveau : prudence. TEMPO change le tempo ; appuyer et tourner = pas de 1 BPM. " +
            "PLAY démarre (vert) et arrête ; la lecture reprend alors depuis le début. SHIFT donne les fonctions secondaires.",
        controls = listOf(
            cmd("(OUTPUT LEVEL)", "Volume des sorties et du casque.", COMMANDES),
            cmd("(TEMPO)", "Tempo ; appuyer + tourner = 1 BPM.", COMMANDES),
            cmd("PLAY", "Lecture / arrêt.", COMMANDES),
            cmd("SONG, CLIP, KEYBOARD", "Changer de vue.", VUES),
        ),
        steps = listOf(
            "Baisse OUTPUT LEVEL avant de mettre le casque, puis monte doucement.",
            "Appuie sur PLAY, puis à nouveau pour arrêter.",
            "Tourne TEMPO, puis appuie et tourne pour régler au BPM près.",
            "Passe de CLIP à SONG et observe la couleur des boutons.",
        ),
        expected = "Tu changes de vue et règles le tempo sans hésiter.",
        exerciseTitle = "Tempo de set",
        exercise = listOf(
            "Règle exactement 160 BPM avec TEMPO enfoncé.",
            "Passe en vue Song puis reviens en vue Clip.",
        ),
        success = "Le tempo affiché est 160 et tu retrouves ton clip.",
        style = "Hardtek / tribe : travaille entre 150 et 180 BPM.",
        mistakes = listOf(
            "Monter OUTPUT LEVEL sans repère : il n'y a pas d'indicateur de niveau.",
            "Chercher à reprendre là où on s'est arrêté : PLAY repart du début.",
        ),
        quiz = listOf(
            qz("Comment régler le tempo au BPM près ?", listOf("Appuyer et tourner TEMPO", "Maintenir PLAY"), 0,
                "Appuyer en tournant donne des pas de 1 BPM.", COMMANDES),
            qz("Quelle couleur signale la vue Clip ?", listOf("CLIP allumé bleu", "SONG clignotant"), 0,
                "CLIP bleu = vue Clip ; SONG clignotant = Arranger.", VUES),
            qz("Combien de vues principales ?", listOf("Deux", "Quatre"), 1,
                "Clip, Song, Arranger et Keyboard.", VUES),
            qz("OUTPUT LEVEL règle…", listOf("Sorties et casque", "Le tempo"), 0,
                "Le volume principal et le casque.", COMMANDES),
        ),
    )

    private val m1l3 = ctLesson(
        M1, 3, "Charger et sauvegarder un preset", listOf(SYNTH_KIT, PRESETS, SAVE_PRESET), 15,
        objective = "Choisir un synthé ou un kit et sauvegarder ta version.",
        explanation = "En vue Clip, SYNTH (rouge) ou KIT (rouge) choisit le type ; SELECT fait défiler les presets, les pads Audition les " +
            "font entendre. LOAD + SYNTH (ou KIT) ouvre une liste avec recherche au clavier de la grille. Un preset ne contient que le " +
            "son, jamais la séquence : les motifs sont sauvegardés avec la chanson. SAVE + SYNTH (ou KIT) propose le prochain numéro " +
            "libre (ex. 171A) pour ne rien écraser ; SELECT permet de choisir un preset existant ; SAVE confirme, BACK / UNDO annule.",
        controls = listOf(
            cmd("SYNTH / KIT", "Type de clip.", PRESETS),
            cmd("(SELECT)", "Parcourir les presets.", PRESETS),
            cmd("LOAD + SYNTH / KIT", "Liste avec recherche.", PRESETS),
            cmd("SAVE + SYNTH / KIT", "Sauvegarde du preset.", SAVE_PRESET),
        ),
        steps = listOf(
            "En vue Clip, appuie sur SYNTH et tourne SELECT ; écoute avec les pads Audition.",
            "Appuie sur KIT et tourne SELECT : écoute chaque ligne avec les pads Audition.",
            "Modifie un réglage, puis SAVE + SYNTH : note le numéro proposé.",
            "Confirme avec SAVE.",
        ),
        expected = "Ton preset est sauvegardé sous un nouveau numéro, l'original reste intact.",
        exerciseTitle = "Trois sons de base",
        exercise = listOf(
            "Choisis une basse, un lead et un kit dans la librairie d'usine.",
            "Note leurs numéros pour les retrouver.",
        ),
        success = "Tu retrouves chaque son en moins de 10 secondes.",
        mistakes = listOf(
            "Croire que le preset garde le motif : il ne garde que le son.",
            "Forcer l'écrasement d'un preset d'usine sans le vouloir en tournant SELECT pendant la sauvegarde.",
        ),
        quiz = listOf(
            qz("Un preset de synthé contient-il la séquence ?", listOf("Oui", "Non, seulement le son"), 1,
                "Les motifs sont sauvegardés avec la chanson.", SYNTH_KIT),
            qz("Que propose SAVE + SYNTH ?", listOf("Le prochain numéro libre", "D'écraser l'original"), 0,
                "Ex. 171A, pour éviter les écrasements.", SAVE_PRESET),
            qz("Comment chercher un preset par nom ?", listOf("LOAD + SYNTH", "SHIFT + PLAY"), 0,
                "Le clavier de la grille apparaît pour la recherche.", PRESETS),
            qz("Comment annuler une sauvegarde ?", listOf("BACK / UNDO", "PLAY"), 0,
                "BACK / UNDO annule.", SAVE_PRESET),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 2 — Le pas à pas
    // ---------------------------------------------------------------------
    private const val M2 = "dlg-m2"

    private val m2l1 = ctLesson(
        M2, 1, "Programmer sur la grille", listOf(GRILLE, PAS_A_PAS), 20,
        objective = "Écrire un motif de batterie et une ligne de synthé pas à pas.",
        explanation = "En vue Clip, chaque colonne est un pas (doubles croches par défaut, une mesure) et chaque ligne une note (synthé, " +
            "grave en bas) ou un son (kit, un son par ligne). Appuyer sur un pad l'allume ou l'éteint. À l'arrêt, le pad joue aussi le " +
            "son ; en lecture, il s'inscrit silencieusement. BACK / UNDO annule, SHIFT + BACK / UNDO rétablit. La colonne Mute coupe " +
            "(jaune) ou rétablit (vert) une ligne.",
        controls = listOf(
            cmd("Pads de la grille", "Allumer / éteindre un pas.", PAS_A_PAS),
            cmd("Pads Audition", "Écouter une note ou un son.", GRILLE),
            cmd("Pads Mute", "Couper / rétablir une ligne.", GRILLE),
            cmd("BACK / UNDO", "Annuler la dernière action.", PAS_A_PAS),
        ),
        steps = listOf(
            "Charge le kit 000 TR-808 et allume le kick (ligne du bas) sur les pas 1, 5, 9 et 13.",
            "Ajoute une caisse claire sur 5 et 13.",
            "Appuie sur PLAY : le curseur blanc défile.",
            "Coupe la ligne du kick avec son pad Mute, puis rétablis-la.",
        ),
        expected = "Un rythme de base tourne et tu sais couper une ligne.",
        exerciseTitle = "Kick hardtek",
        exercise = listOf(
            "Programme un kick à chaque temps et un charley ouvert sur chaque contretemps.",
            "En lecture, ajoute et retire des pas sans arrêter.",
        ),
        success = "Le motif évolue sans jamais s'arrêter.",
        style = "Hardtek : kick sur chaque temps, charley sur les contretemps.",
        mistakes = listOf(
            "Appuyer sur un pad allumé en croyant le jouer : il s'éteint.",
            "Oublier que la grille peut dépasser les 16 × 8 pads visibles.",
        ),
        quiz = listOf(
            qz("Résolution par défaut d'une colonne ?", listOf("Double croche", "Noire"), 0,
                "1/16 sur une mesure.", PAS_A_PAS),
            qz("Dans un kit, une ligne représente…", listOf("Un son", "Une note de gamme"), 0,
                "Chaque son a sa ligne.", GRILLE),
            qz("Pad Mute jaune signifie…", listOf("Ligne coupée", "Ligne active"), 0,
                "Jaune = coupé, vert = actif.", GRILLE),
            qz("Comment rétablir après BACK / UNDO ?", listOf("SHIFT + BACK / UNDO", "PLAY"), 0,
                "SHIFT + BACK / UNDO = redo.", PAS_A_PAS),
        ),
    )

    private val m2l2 = ctLesson(
        M2, 2, "Longueur, vélocité et répétitions", listOf(LONGUEUR, VELOCITE, QUANTIZE), 15,
        objective = "Allonger les notes, doser leur force et créer des roulements.",
        explanation = "Longueur : maintenir le pad de début et appuyer sur celui de fin (même ligne) ; la partie prolongée est plus " +
            "sombre ; appuyer sur une extension raccourcit. Vélocité : maintenir le pad et tourner SCROLL ◄►, de 1 à 127 (défaut 64 ; " +
            "la dernière valeur réglée sert pour les notes suivantes). Les pads ne sont pas sensibles à la frappe. Répétition : " +
            "maintenir le pad, appuyer et tourner SCROLL ▼▲ : la note se répète à intervalles égaux. Effacer tout le clip : " +
            "SCROLL ◄► + BACK / UNDO (notes et automation).",
        controls = listOf(
            cmd("Pad début + pad fin", "Longueur de note.", LONGUEUR),
            cmd("Pad + (SCROLL ◄►)", "Vélocité.", VELOCITE),
            cmd("Pad + appui/rotation (SCROLL ▼▲)", "Répétitions de la note.", VELOCITE),
            cmd("(SCROLL ◄►) + BACK / UNDO", "Effacer le clip.", QUANTIZE),
        ),
        steps = listOf(
            "Sur un clip synthé, maintiens le pas 1 et appuie sur le pas 4 : la note dure 4 pas.",
            "Maintiens un pas de charley et tourne SCROLL ◄► pour baisser sa vélocité.",
            "Maintiens le dernier pas de caisse claire, appuie et tourne SCROLL ▼▲ : un roulement.",
            "Écoute le résultat en lecture.",
        ),
        expected = "Des notes longues, des charleys plus doux et un roulement de fin de mesure.",
        exerciseTitle = "Roulement de relance",
        exercise = listOf(
            "Ajoute un roulement de 4 répétitions sur le dernier pas de la caisse claire.",
            "Baisse la vélocité d'un charley sur deux.",
        ),
        success = "Le motif respire et relance à chaque fin de mesure.",
        style = "Tribecore : roulements de caisse claire en fin de boucle.",
        mistakes = listOf(
            "Frapper plus fort sur un pad pour accentuer : les pads ne sont pas sensibles à la vélocité.",
            "Effacer le clip par erreur avec SCROLL ◄► + BACK / UNDO : notes et automation partent ensemble.",
        ),
        quiz = listOf(
            qz("Vélocité par défaut ?", listOf("64", "127"), 0,
                "64, ou la dernière valeur utilisée.", VELOCITE),
            qz("Comment régler la vélocité d'un pas ?", listOf("Maintenir le pad et tourner SCROLL ◄►", "Frapper plus fort"), 0,
                "Les pads ne sont pas sensibles à la frappe.", VELOCITE),
            qz("Comment allonger une note ?", listOf("Pad de début + pad de fin", "SHIFT + PLAY"), 0,
                "Sur la même ligne.", LONGUEUR),
            qz("Que fait pad + appui/rotation SCROLL ▼▲ ?", listOf("Répète la note", "Transpose le clip"), 0,
                "Répétitions à intervalles égaux dans le pas.", VELOCITE),
        ),
    )

    private val m2l3 = ctLesson(
        M2, 3, "Longueur du clip, zoom et défilement", listOf(NAVIGATION, LONGUEUR_CLIP), 15,
        objective = "Allonger un clip, le dupliquer et naviguer au-delà des 16 pas visibles.",
        explanation = "SCROLL ◄► déplace la vue dans le temps, SCROLL ▼▲ monte ou descend (notes ou sons hors de vue). Appuyer et tourner " +
            "SCROLL ◄► zoome (de la double croche jusqu'au 1/128) ; un simple appui affiche le zoom. Longueur : SHIFT + SCROLL ◄► " +
            "tourné (affichage mesures : temps : doubles croches) ; colonnes hors longueur en gris. SHIFT + appui sur SCROLL ◄► " +
            "duplique le clip et son contenu (le zoom peut changer automatiquement).",
        controls = listOf(
            cmd("(SCROLL ◄►) / (SCROLL ▼▲)", "Défilement horizontal / vertical.", NAVIGATION),
            cmd("Appui + rotation (SCROLL ◄►)", "Zoom.", NAVIGATION),
            cmd("SHIFT + rotation (SCROLL ◄►)", "Longueur du clip.", LONGUEUR_CLIP),
            cmd("SHIFT + appui (SCROLL ◄►)", "Multiplier le clip.", LONGUEUR_CLIP),
        ),
        steps = listOf(
            "Sur ton motif d'une mesure, fais SHIFT + appui sur SCROLL ◄► : il passe à deux mesures.",
            "Tourne SCROLL ◄► pour voir la deuxième mesure et modifie-la.",
            "Raccourcis le clip à 12 pas avec SHIFT + rotation SCROLL ◄►.",
            "Appuie sur SCROLL ◄► pour lire le zoom actuel.",
        ),
        expected = "Un clip de deux mesures avec une variation dans la seconde.",
        exerciseTitle = "Variation en mesure 4",
        exercise = listOf(
            "Duplique ton motif deux fois (4 mesures).",
            "Modifie seulement la mesure 4 pour un break.",
        ),
        success = "Les trois premières mesures sont identiques, la quatrième relance.",
        style = "Hardtek / tribe : trois mesures identiques et une quatrième qui relance.",
        mistakes = listOf(
            "Perdre ses notes de vue : elles existent toujours hors de la grille visible.",
            "Confondre rotation et appui sur SCROLL ◄► avec SHIFT : l'une règle la longueur, l'autre duplique.",
        ),
        quiz = listOf(
            qz("Zoom maximal ?", listOf("1/32", "1/128"), 1,
                "Jusqu'au 1/128 de note.", NAVIGATION),
            qz("Comment dupliquer un clip ?", listOf("SHIFT + appui sur SCROLL ◄►", "LOAD + KIT"), 0,
                "Le contenu est copié et la longueur doublée.", LONGUEUR_CLIP),
            qz("Longueur par défaut d'un clip ?", listOf("1 mesure", "4 mesures"), 0,
                "Une mesure par défaut.", LONGUEUR_CLIP),
            qz("Colonnes grises = …", listOf("Hors de la longueur du clip", "Notes coupées"), 0,
                "Elles signalent la fin du clip.", LONGUEUR_CLIP),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 3 — Enregistrement live et timing
    // ---------------------------------------------------------------------
    private const val M3 = "dlg-m3"

    private val m3l1 = ctLesson(
        M3, 1, "Enregistrer en direct", listOf(LIVE, DECOMPTE, QUANTIZE), 15,
        objective = "Jouer une partie aux pads Audition et l'enregistrer dans le clip.",
        explanation = "RECORD arme l'enregistrement (rouge), PLAY lance. Si le clip contient déjà des notes, l'enregistrement dure la " +
            "longueur du clip (curseur blanc) ; s'il est vide, il s'allonge sans limite (curseur rouge). On joue sur les pads Audition. " +
            "PLAY arrête, RECORD désarme ; recommencer = superposer (overdub). La quantification par défaut est au 1/32 (menu " +
            "SHIFT + SELECT > RECORDING > QUANTIZATION). Un décompte 4-3-2-1 s'active dans RECORDING > COUNT-IN.",
        controls = listOf(
            cmd("RECORD", "Armer l'enregistrement.", LIVE),
            cmd("Pads Audition", "Jouer en direct.", LIVE),
            cmd("SHIFT + SELECT > RECORDING", "Quantification et décompte.", QUANTIZE),
        ),
        steps = listOf(
            "Sur un clip kit avec des notes, appuie sur RECORD puis PLAY.",
            "Joue des charleys sur les pads Audition.",
            "Arrête avec PLAY, désarme RECORD et écoute.",
            "Active le décompte dans le menu RECORDING > COUNT-IN.",
        ),
        expected = "Ta partie jouée est enregistrée et calée sur la grille.",
        exerciseTitle = "Percussions jouées",
        exercise = listOf(
            "Enregistre des percussions en deux passes superposées.",
            "Annule la dernière passe avec BACK / UNDO si elle ne te plaît pas.",
        ),
        success = "Deux couches de percussions jouées tournent ensemble.",
        style = "Tribe : percussions jouées à la main pour un groove vivant.",
        mistakes = listOf(
            "Enregistrer sur un clip vide en voulant une mesure : il s'allonge sans limite.",
            "Oublier de désarmer RECORD : la passe suivante s'ajoute.",
        ),
        quiz = listOf(
            qz("Clip vide + enregistrement…", listOf("Longueur illimitée (curseur rouge)", "Une mesure fixe"), 0,
                "Mode d'extension automatique.", LIVE),
            qz("Quantification par défaut ?", listOf("1/32", "1/4"), 0,
                "32nd notes par défaut.", QUANTIZE),
            qz("Comment superposer une passe ?", listOf("Refaire RECORD puis PLAY", "SHIFT + KIT"), 0,
                "Les étapes répétées créent des overdubs.", LIVE),
            qz("Où activer le décompte ?", listOf("RECORDING > COUNT-IN", "Bouton SCALE"), 0,
                "Dans le menu des réglages.", DECOMPTE),
        ),
    )

    private val m3l2 = ctLesson(
        M3, 2, "Tempo, tap tempo, métronome et swing", listOf(TEMPO), 15,
        objective = "Régler le tempo à l'oreille et donner du swing.",
        explanation = "TAP TEMPO : au moins deux appuis, la précision augmente avec le nombre de tapes. SHIFT + TAP TEMPO allume ou éteint le " +
            "métronome (aigu en début de mesure). SHIFT + rotation de TEMPO règle le swing de 1 à 99 : 50 = aucun, au-dessus les notes " +
            "sont en retard, en dessous en avance. L'intervalle de swing (16e par défaut) se règle dans le menu SWING INTERVAL et est " +
            "sauvegardé avec la chanson.",
        controls = listOf(
            cmd("TAP TEMPO", "Tempo tapé.", TEMPO),
            cmd("SHIFT + TAP TEMPO", "Métronome.", TEMPO),
            cmd("SHIFT + rotation (TEMPO)", "Swing de 1 à 99.", TEMPO),
            cmd("SHIFT + SELECT > SWING INTERVAL", "Valeur du swing.", TEMPO),
        ),
        steps = listOf(
            "Tape plusieurs fois TAP TEMPO au rythme d'un morceau.",
            "Allume le métronome avec SHIFT + TAP TEMPO, lance PLAY.",
            "Règle le swing à 58 avec SHIFT + TEMPO.",
            "Reviens à 50 pour un rythme droit.",
        ),
        expected = "Le motif prend du swing puis redevient droit.",
        exerciseTitle = "Swing léger",
        exercise = listOf(
            "Sur ton motif de charleys, essaie 54, 58 puis 62.",
            "Garde le réglage qui groove sans paraître décalé.",
        ),
        success = "Tu choisis une valeur de swing à l'oreille.",
        style = "Hardtek : garde le kick droit, un swing léger sur les charleys.",
        mistakes = listOf(
            "Croire que 0 = pas de swing : le neutre est 50.",
            "Oublier que le swing s'applique à toute la chanson, pas à une seule note.",
        ),
        quiz = listOf(
            qz("Valeur de swing neutre ?", listOf("0", "50"), 1,
                "50 = pas de swing.", TEMPO),
            qz("Comment allumer le métronome ?", listOf("SHIFT + TAP TEMPO", "RECORD"), 0,
                "Le bouton devient vert.", TEMPO),
            qz("Swing au-dessus de 50…", listOf("Notes en retard", "Notes en avance"), 0,
                "51–99 = en retard.", TEMPO),
            qz("Intervalle de swing par défaut ?", listOf("1/16", "1/4"), 0,
                "16th par défaut.", TEMPO),
        ),
    )

    private val m3l3 = ctLesson(
        M3, 3, "Sens de lecture, triolets et Cross-Screen", listOf(DIRECTION, TRIOLETS, CROSS), 15,
        objective = "Varier la lecture et éditer toutes les mesures d'un coup.",
        explanation = "SHIFT + DIRECTION ouvre le sens de lecture : FORWARD, REVERSE, PING-PONG (et NONE pour une ligne de kit). Dans un " +
            "kit, AFFECT ENTIRE éteint = sens par ligne ; allumé = pour tout le kit. TRIPLETS VIEW affiche 3 colonnes sur 4 pour écrire " +
            "des triolets. CROSS SCREEN : une modification sur l'écran visible est répétée sur les écrans équivalents hors de vue " +
            "(au même zoom), pratique après avoir dupliqué un motif.",
        controls = listOf(
            cmd("SHIFT + DIRECTION", "Sens de lecture.", DIRECTION),
            cmd("TRIPLETS VIEW", "Grille en triolets.", TRIOLETS),
            cmd("CROSS SCREEN", "Édition sur tous les écrans équivalents.", CROSS),
        ),
        steps = listOf(
            "Sélectionne une ligne de kit, AFFECT ENTIRE éteint, puis SHIFT + DIRECTION et choisis REVERSE.",
            "Active TRIPLETS VIEW et place trois coups dans un temps.",
            "Duplique ton motif en 4 mesures, active CROSS SCREEN et ajoute un kick en fin de mesure 1.",
            "Vérifie que le kick apparaît dans les 4 mesures.",
        ),
        expected = "Une ligne jouée à l'envers, des triolets et une modification répétée sur 4 mesures.",
        exerciseTitle = "Percussions à l'envers",
        exercise = listOf(
            "Mets une ligne de percussions en PING-PONG.",
            "Laisse les autres lignes en FORWARD.",
        ),
        success = "Une percussion « tourne » autrement que le reste du motif.",
        style = "Tribe : une ligne en ping-pong donne un groove hypnotique.",
        mistakes = listOf(
            "Régler le sens avec AFFECT ENTIRE allumé et changer tout le kit sans le vouloir.",
            "Oublier que Cross-Screen dépend du zoom.",
        ),
        quiz = listOf(
            qz("Sens de lecture disponibles ?", listOf("FORWARD, REVERSE, PING-PONG", "Seulement FORWARD"), 0,
                "Plus NONE pour les lignes de kit.", DIRECTION),
            qz("Que fait CROSS SCREEN ?", listOf("Répète l'édition sur les écrans équivalents", "Efface le clip"), 0,
                "Les écrans hors de vue sont modifiés aussi.", CROSS),
            qz("TRIPLETS VIEW affiche…", listOf("3 colonnes sur 4", "8 colonnes"), 0,
                "La 4e colonne est grisée.", TRIOLETS),
            qz("Sens par ligne de kit : AFFECT ENTIRE…", listOf("Éteint", "Allumé"), 0,
                "Allumé, le réglage s'applique à tout le kit.", DIRECTION),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 4 — Gammes et clavier
    // ---------------------------------------------------------------------
    private const val M4 = "dlg-m4"

    private val m4l1 = ctLesson(
        M4, 1, "Le mode Scale", listOf(GAMMES, TONIQUE), 15,
        objective = "Verrouiller les notes dans une gamme et en changer.",
        explanation = "Les nouveaux clips synthé, MIDI et CV sont en mode Scale (majeur par défaut) : les lignes ne montrent que les notes " +
            "de la gamme. SCALE éteint = chromatique (un demi-ton par ligne). Avec des notes déjà écrites, allumer SCALE fait deviner au " +
            "Deluge la gamme et la tonique. SHIFT + SCALE fait défiler 7 gammes : majeur, mineur, dorien, phrygien, lydien, mixolydien, " +
            "locrien ; les notes se déplacent. SCALE + pad Audition fixe la tonique. Toutes les pistes en Scale partagent la même gamme.",
        controls = listOf(
            cmd("SCALE", "Mode gamme (bleu) ou chromatique.", GAMMES),
            cmd("SHIFT + SCALE", "Changer de gamme.", TONIQUE),
            cmd("SCALE + pad Audition", "Choisir la tonique.", TONIQUE),
        ),
        steps = listOf(
            "Écris une petite ligne de basse sur un clip synthé en mode Scale.",
            "Appuie sur SHIFT + SCALE jusqu'à PHRYGIAN.",
            "Fixe la tonique avec SCALE + un pad Audition.",
            "Éteins SCALE pour voir la grille chromatique, puis rallume.",
        ),
        expected = "Ta ligne passe en phrygien et reste juste.",
        exerciseTitle = "Basse sombre",
        exercise = listOf(
            "Écris une basse en mineur, puis essaie phrygien.",
            "Garde la plus tendue.",
        ),
        success = "La basse change de couleur sans fausse note.",
        style = "Mentalcore / acidcore : le phrygien donne vite une couleur tendue.",
        mistakes = listOf(
            "Changer la gamme d'un clip en croyant ne toucher que lui : tous les clips en Scale suivent.",
            "S'étonner de notes déplacées après l'analyse de gamme : le guide prévient que cela peut arriver.",
        ),
        quiz = listOf(
            qz("Gamme par défaut ?", listOf("Majeur", "Mineur"), 0,
                "Les nouveaux clips sont en majeur.", GAMMES),
            qz("Combien de gammes avec SHIFT + SCALE ?", listOf("7", "12"), 0,
                "Sept gammes occidentales.", GAMMES),
            qz("Comment fixer la tonique ?", listOf("SCALE + pad Audition", "SHIFT + PLAY"), 0,
                "On choisit la note voulue.", TONIQUE),
            qz("Changer la gamme d'un clip en Scale…", listOf("Change aussi les autres clips en Scale", "Ne change que lui"), 0,
                "Tous les clips en Scale partagent la gamme.", TONIQUE),
        ),
    )

    private val m4l2 = ctLesson(
        M4, 2, "Transposer et gammes personnalisées", listOf(TONIQUE, CUSTOM), 15,
        objective = "Transposer un clip et créer ta propre gamme.",
        explanation = "Appuyer et tourner SCROLL ▼▲ transpose d'une octave ; SHIFT + appui et rotation de SCROLL ▼▲ transpose au demi-ton " +
            "(tous les clips en Scale suivent). Gamme personnalisée : éteindre SCALE, écrire les notes voulues, rallumer SCALE ; si elles " +
            "ne forment pas une des 7 gammes, le Deluge crée « OTHER SCALE ». En mode Scale, maintenir un pad Audition et tourner SELECT " +
            "dièse ou bémolise la note de cette ligne.",
        controls = listOf(
            cmd("Appui + rotation (SCROLL ▼▲)", "Transposition à l'octave.", TONIQUE),
            cmd("SHIFT + appui + rotation (SCROLL ▼▲)", "Transposition au demi-ton.", TONIQUE),
            cmd("Pad Audition + (SELECT)", "Dièse / bémol d'une ligne.", CUSTOM),
        ),
        steps = listOf(
            "Transpose ta basse d'une octave vers le bas.",
            "Transpose-la de deux demi-tons vers le haut.",
            "Éteins SCALE, écris des notes hors gamme, puis rallume SCALE : lis le nom de gamme affiché.",
        ),
        expected = "Tu transposes et tu obtiens une gamme personnalisée.",
        exerciseTitle = "Progression transposée",
        exercise = listOf(
            "Fais tourner une boucle et transpose-la de 0, +3, +5 puis 0 demi-tons toutes les 4 mesures.",
        ),
        success = "La progression se joue en direct sans réécrire les notes.",
        style = "Hardtek : une basse transposée fait avancer le morceau.",
        mistakes = listOf(
            "Transposer à l'octave en voulant un demi-ton : il faut SHIFT.",
            "Oublier que tous les clips en Scale sont transposés ensemble.",
        ),
        quiz = listOf(
            qz("Transposer d'un demi-ton ?", listOf("SHIFT + appui + rotation SCROLL ▼▲", "Rotation simple de SCROLL ▼▲"), 0,
                "Sans SHIFT, c'est à l'octave.", TONIQUE),
            qz("Notes hors des 7 gammes + SCALE…", listOf("OTHER SCALE", "Erreur"), 0,
                "Une gamme personnalisée est créée.", CUSTOM),
            qz("Dièser une ligne en mode Scale ?", listOf("Pad Audition + SELECT", "KIT + SELECT"), 0,
                "Maintenir Audition et tourner SELECT.", CUSTOM),
            qz("Rotation simple de SCROLL ▼▲ (sans appui)…", listOf("Fait défiler les lignes", "Transpose"), 0,
                "Il faut appuyer pour transposer.", dlg("3.8 Navigating the Grid", "56")),
        ),
    )

    private val m4l3 = ctLesson(
        M4, 3, "La vue Keyboard", listOf(CLAVIER, LIVE), 15,
        objective = "Jouer et enregistrer des mélodies et accords sur la grille isomorphe.",
        explanation = "KEYBOARD transforme la grille en instrument (synthé, MIDI, CV ; pas pour les kits). Une colonne = un demi-ton, une " +
            "ligne = une quarte (5 demi-tons), comme une basse. Un accord garde la même forme partout (disposition isomorphe). Jouer une " +
            "note allume aussi ses octaves. On enregistre comme en vue Clip : RECORD, PLAY, jouer sur la grille.",
        controls = listOf(
            cmd("KEYBOARD", "Vue clavier.", CLAVIER),
            cmd("Pads de la grille", "Notes : colonnes en demi-tons, lignes en quartes.", CLAVIER),
            cmd("RECORD + PLAY", "Enregistrer ce qui est joué.", LIVE),
        ),
        steps = listOf(
            "Sur un clip synthé, appuie sur KEYBOARD.",
            "Joue un accord mineur, puis déplace la même forme ailleurs.",
            "Appuie sur RECORD puis PLAY et joue une mélodie.",
            "Reviens en vue Clip pour voir les notes enregistrées.",
        ),
        expected = "Ta mélodie est enregistrée depuis la vue Keyboard.",
        exerciseTitle = "Stabs d'accords",
        exercise = listOf(
            "Enregistre quatre stabs d'accords avec la même forme décalée.",
            "Raccourcis les notes en vue Clip.",
        ),
        success = "Quatre accords courts tournent en boucle.",
        style = "Tribe : stabs d'accords courts sur les contretemps.",
        mistakes = listOf(
            "Chercher la vue Keyboard sur un kit : elle n'existe pas pour les kits.",
            "Jouer comme sur un piano : la grille est en quartes, pas en touches blanches et noires.",
        ),
        quiz = listOf(
            qz("Intervalle entre deux lignes ?", listOf("Une quarte (5 demi-tons)", "Une octave"), 0,
                "Comme une guitare basse.", CLAVIER),
            qz("Intervalle entre deux colonnes ?", listOf("Un demi-ton", "Un ton"), 0,
                "Chaque colonne = un demi-ton.", CLAVIER),
            qz("Keyboard disponible pour les kits ?", listOf("Oui", "Non"), 1,
                "Pas pour les kits.", LIVE),
            qz("Que signifie « isomorphe » ?", listOf("Un accord garde la même forme partout", "Les pads sont sensibles"), 0,
                "La forme de l'accord ne change pas.", CLAVIER),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(
            M1, 1, "Prise en main",
            "Alimentation, carte SD, vues, tempo, presets.",
            "Allumer, se repérer et choisir ses sons.",
            "Un Deluge sur batterie = un live complet n'importe où.",
            listOf(m1l1, m1l2, m1l3),
        ),
        CourseModule(
            M2, 2, "Le pas à pas",
            "Grille, longueur, vélocité, répétitions, longueur de clip.",
            "Écrire des motifs complets sur la grille.",
            "Kick droit, roulements et variations de 4e mesure : la base hardtek.",
            listOf(m2l1, m2l2, m2l3),
        ),
        CourseModule(
            M3, 3, "Enregistrement et timing",
            "Live, quantification, tempo, swing, sens de lecture, triolets, Cross-Screen.",
            "Enregistrer en jouant et faire groover.",
            "Percussions jouées et lignes en ping-pong pour la tribe.",
            listOf(m3l1, m3l2, m3l3),
        ),
        CourseModule(
            M4, 4, "Gammes et clavier",
            "Mode Scale, transposition, gammes personnalisées, vue Keyboard.",
            "Composer juste et jouer des accords.",
            "Gammes tendues et basses transposées pour la mentalcore et l'acidcore.",
            listOf(m4l1, m4l2, m4l3),
        ),
    )
}
