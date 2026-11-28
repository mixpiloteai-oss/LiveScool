package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** Deluge — modules 5 à 7 : séquençage créatif, le son, kits et vue Song. */
internal object DelugeModulesB {

    // Références du guide (pages imprimées ; page du PDF = page imprimée + 6)
    private val PROBA = dlg("3.11 Probability", "64")
    private val ITERATION = dlg("3.11 Iteration Dependance", "65")
    private val AUTOMATION = dlg("3.12 Parameter Automation", "66–69")
    private val EUCLIDE = dlg("3.13 Euclidean Sequencing", "70–71")
    private val AFFECT = dlg("2.8 Clip View: Parameter Affect Group", "27–29")
    private val MODES = dlg("4.2–4.4 Subtractive, FM, Wavetable", "77–79")
    private val NOUVEAU_SYNTH = dlg("4.7 Creating a New Synthesizer", "87–88")
    private val EDITEUR = dlg("4.8 Sound Editor", "90–93")
    private val FILTRES = dlg("4.10 Filters", "98")
    private val MOD = dlg("6.1 Modulation Routing Basics", "118–121")
    private val ENV_LFO = dlg("6.3 Envelopes and Low Frequency Oscillators", "125–126")
    private val KIT_BASE = dlg("5.2 Kit Basics", "109–111")
    private val KIT_NOUVEAU = dlg("5.3 Kit Presets & Samples", "112–115")
    private val SONG = dlg("7.2 Song View Basics", "137–138")
    private val LAUNCH = dlg("7.3 Advanced Control of Single Clips", "139–141")
    private val EDIT_SONG = dlg("7.4 Editing Clips in Song View", "142–144")
    private val SECTIONS = dlg("7.5 Song Sections", "145–146")
    private val SAVE_SONG = dlg("7.6 Saving & Loading Songs", "147–150")

    // ---------------------------------------------------------------------
    // Module 5 — Séquençage créatif
    // ---------------------------------------------------------------------
    private const val M5 = "dlg-m5"

    private val m5l1 = ctLesson(
        M5, 1, "Probabilité et itérations", listOf(PROBA, ITERATION), 15,
        objective = "Faire varier un motif d'un cycle à l'autre sans le réécrire.",
        explanation = "Probabilité : maintenir un ou plusieurs pads et tourner SELECT vers la gauche ; de 5 à 100 % de chances que la note " +
            "joue (100 % = toujours). Plusieurs notes au même pourcentage proposent l'option LATCHING (la note ne joue que si la " +
            "précédente équivalente a joué). Itération : maintenir un pad et tourner SELECT vers la droite, de « 1 of 2 » à « 8 of 8 » : " +
            "« 1 of 2 » joue la note au 1er de chaque cycle de 2, « 3 of 4 » au 3e de chaque cycle de 4.",
        controls = listOf(
            cmd("Pad + (SELECT) vers la gauche", "Probabilité 5–100 %.", PROBA),
            cmd("Pad + (SELECT) vers la droite", "Itération 1 of 2 … 8 of 8.", ITERATION),
        ),
        steps = listOf(
            "Maintiens un charley et tourne SELECT à gauche jusqu'à 50 %.",
            "Écoute : il joue une fois sur deux en moyenne.",
            "Maintiens un coup de caisse claire et tourne SELECT à droite jusqu'à « 4 of 4 ».",
            "Écoute : il ne joue qu'à la 4e mesure.",
        ),
        expected = "Un motif qui varie tout seul, avec une relance toutes les 4 mesures.",
        exerciseTitle = "Percussions vivantes",
        exercise = listOf(
            "Mets trois percussions à 30, 60 et 80 %.",
            "Ajoute un tom en « 2 of 2 ».",
        ),
        success = "La boucle ne se répète jamais exactement pareil.",
        style = "Tribe : percussions à probabilité réduite pour un groove qui ne lasse pas.",
        mistakes = listOf(
            "Tourner SELECT dans le mauvais sens : gauche = probabilité, droite = itération.",
            "Mettre le kick en probabilité : la base du morceau devient instable.",
        ),
        quiz = listOf(
            qz("Probabilité minimale ?", listOf("5 %", "0 %"), 0,
                "De 5 à 100 %.", PROBA),
            qz("Comment régler une itération ?", listOf("Pad + SELECT vers la droite", "Pad + SELECT vers la gauche"), 0,
                "Droite = itération.", ITERATION),
            qz("« 3 of 4 » joue…", listOf("Au 3e cycle sur 4", "3 fois sur 4"), 0,
                "Au 3e de chaque cycle de 4.", ITERATION),
            qz("Revenir à « toujours » ?", listOf("100 %", "50 %"), 0,
                "100 % = joue à chaque cycle.", PROBA),
        ),
    )

    private val m5l2 = ctLesson(
        M5, 2, "Le séquençage euclidien", listOf(EUCLIDE), 15,
        objective = "Générer des rythmes répartis automatiquement sur une ligne.",
        explanation = "Le Deluge répartit un nombre d'événements sur une longueur de ligne. Maintenir le pad Audition de la ligne (ou SHIFT + " +
            "Audition pour rester silencieux) : tourner SCROLL ◄► règle la longueur de la ligne ; appuyer et tourner SCROLL ▼▲ règle le " +
            "nombre d'événements ; appuyer et tourner SCROLL ◄► décale leur position. On empile plusieurs lignes de longueurs différentes " +
            "pour des rythmes complexes.",
        controls = listOf(
            cmd("Audition + rotation (SCROLL ◄►)", "Longueur de la ligne.", EUCLIDE),
            cmd("Audition + appui/rotation (SCROLL ▼▲)", "Nombre d'événements.", EUCLIDE),
            cmd("Audition + appui/rotation (SCROLL ◄►)", "Décalage.", EUCLIDE),
        ),
        steps = listOf(
            "Sur un kit, maintiens le pad Audition d'une percussion.",
            "Règle 5 événements avec appui + rotation de SCROLL ▼▲.",
            "Règle la longueur de la ligne à 12 pas avec rotation de SCROLL ◄►.",
            "Décale les coups avec appui + rotation de SCROLL ◄►, puis recommence sur une autre ligne avec une autre longueur.",
        ),
        expected = "Deux lignes euclidiennes de longueurs différentes se croisent.",
        exerciseTitle = "Rythme tribal",
        exercise = listOf(
            "Crée trois lignes euclidiennes : 3 sur 8, 5 sur 12, 7 sur 16.",
            "Garde un kick droit en dessous.",
        ),
        success = "Les percussions semblent générées, le kick reste stable.",
        style = "Tribe : les rythmes euclidiens donnent vite des percussions tribales.",
        mistakes = listOf(
            "Confondre rotation simple et appui + rotation : longueur et décalage utilisent le même bouton.",
            "Lâcher le pad Audition pendant le réglage.",
        ),
        quiz = listOf(
            qz("Régler le nombre d'événements ?", listOf("Audition + appui/rotation SCROLL ▼▲", "TEMPO"), 0,
                "Appui + rotation de SCROLL ▼▲.", EUCLIDE),
            qz("Régler la longueur de la ligne ?", listOf("Audition + rotation SCROLL ◄►", "SHIFT + SCALE"), 0,
                "Rotation simple de SCROLL ◄►.", EUCLIDE),
            qz("Rester silencieux pendant le réglage ?", listOf("SHIFT + Audition", "MUTE"), 0,
                "SHIFT + Audition sélectionne sans jouer.", EUCLIDE),
            qz("Euclidien appliqué…", listOf("Ligne par ligne", "À toute la chanson"), 0,
                "Le Deluge l'applique par ligne.", EUCLIDE),
        ),
    )

    private val m5l3 = ctLesson(
        M5, 3, "L'automation des paramètres", listOf(AUTOMATION), 20,
        objective = "Enregistrer un balayage de filtre ou de panoramique dans le clip.",
        explanation = "En enregistrement (RECORD puis PLAY), choisir un paramètre (ex. LEVEL / PAN) et tourner UPPER ou LOWER : le mouvement " +
            "est enregistré en rythme ; il est écrasé si on continue au tour suivant ; une transition douce ramène à la valeur d'origine. " +
            "Pas par pas : maintenir un pad et tourner la commande enregistre une valeur jusqu'au pas suivant. L'automation reste même " +
            "si on efface les notes. SHIFT + appui sur la commande l'efface (« AUTOMATION DELETED »). Le stutter ne s'automatise pas.",
        controls = listOf(
            cmd("RECORD + PLAY + (UPPER)/(LOWER)", "Enregistrer un mouvement.", AUTOMATION),
            cmd("Pad + (UPPER)/(LOWER)", "Valeur sur un pas.", AUTOMATION),
            cmd("SHIFT + appui (UPPER)/(LOWER)", "Effacer l'automation.", AUTOMATION),
        ),
        steps = listOf(
            "Choisis le bouton du filtre (Cutoff / Res).",
            "RECORD puis PLAY, et ouvre lentement le filtre avec UPPER pendant un tour.",
            "Arrête, désarme RECORD et écoute la montée rejouée.",
            "Efface-la avec SHIFT + appui sur UPPER.",
        ),
        expected = "La montée de filtre se rejoue toute seule à chaque cycle.",
        exerciseTitle = "Montée automatisée",
        exercise = listOf(
            "Sur un clip de 4 mesures, automatise une ouverture de filtre progressive.",
            "Automatise aussi un pan différent sur deux pas de percussion.",
        ),
        success = "La montée et les pans se rejouent sans toucher aux commandes.",
        style = "Hardtek : montée de filtre automatisée avant le retour du kick.",
        mistakes = listOf(
            "Continuer à tourner au tour suivant : l'automation est écrasée.",
            "Croire qu'effacer les notes efface l'automation : elle reste.",
        ),
        quiz = listOf(
            qz("L'automation survit-elle à l'effacement des notes ?", listOf("Oui", "Non"), 0,
                "Elle est indépendante des notes.", AUTOMATION),
            qz("Effacer l'automation d'un paramètre ?", listOf("SHIFT + appui sur la commande", "PLAY"), 0,
                "« AUTOMATION DELETED » s'affiche.", AUTOMATION),
            qz("Valeur sur un seul pas ?", listOf("Maintenir le pad et tourner", "Maintenir SCALE"), 0,
                "La valeur tient jusqu'au pas suivant.", AUTOMATION),
            qz("Quel paramètre ne s'automatise pas ?", listOf("Stutter", "Pan"), 0,
                "Le stutter ne peut pas être automatisé.", AUTOMATION),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 6 — Le son
    // ---------------------------------------------------------------------
    private const val M6 = "dlg-m6"

    private val m6l1 = ctLesson(
        M6, 1, "Le groupe de paramètres rapides", listOf(AFFECT), 15,
        objective = "Modeler le son en direct avec les deux commandes dorées et les 8 boutons.",
        explanation = "Huit boutons choisissent la paire de paramètres réglée par UPPER (au-dessus) et LOWER (en dessous) : Level / Pan, " +
            "Cutoff / Res (FM : modulateurs), Attack / Release (enveloppe 1), Delay Time / Amount, Sidechain / Reverb, Mod Rate / Depth, " +
            "Stutter / Custom 1, Custom 2 / Custom 3. Appuyer sur UPPER ou LOWER donne une fonction secondaire (ex. pente du filtre " +
            "12 dB, 24 dB ou DRIVE). Stutter agit tant qu'on appuie. Dans un kit, seule la ligne choisie change, sauf avec AFFECT ENTIRE.",
        controls = listOf(
            cmd("8 boutons de paramètres", "Choisir la paire réglée.", AFFECT),
            cmd("(UPPER) / (LOWER)", "Régler ; appuyer = fonction secondaire.", AFFECT),
            cmd("AFFECT ENTIRE", "Tout le kit à la fois.", AFFECT),
        ),
        steps = listOf(
            "Sur un synthé, choisis Cutoff / Res et ferme le filtre avec UPPER.",
            "Monte la résonance avec LOWER.",
            "Appuie sur LOWER et choisis DRIVE.",
            "Sur un kit, active AFFECT ENTIRE et baisse le Level de tout le kit.",
        ),
        expected = "Tu changes filtre, résonance et niveau en direct.",
        exerciseTitle = "Break au filtre",
        exercise = listOf(
            "Pendant la lecture, ferme le filtre du kit entier (AFFECT ENTIRE) sur 4 mesures.",
            "Rouvre-le d'un coup pour le retour.",
        ),
        success = "Le break et le retour sont nets.",
        style = "Tribe / hardtek : filtre fermé puis retour brutal, le geste de base du live.",
        mistakes = listOf(
            "Régler un kit sans AFFECT ENTIRE et ne changer qu'une ligne.",
            "Croire que Custom 1 à 3 font toujours la même chose : ils dépendent du preset.",
        ),
        quiz = listOf(
            qz("Quel bouton pour le filtre ?", listOf("Cutoff / Res", "Level / Pan"), 0,
                "UPPER = cutoff, LOWER = résonance.", AFFECT),
            qz("Pentes du filtre passe-bas ?", listOf("12 dB, 24 dB, DRIVE", "6 dB seulement"), 0,
                "Choisies en appuyant sur la commande.", AFFECT),
            qz("Stutter agit…", listOf("Tant qu'on appuie", "Jusqu'au prochain PLAY"), 0,
                "Il s'arrête au relâchement.", AFFECT),
            qz("Agir sur tout le kit ?", listOf("AFFECT ENTIRE", "CROSS SCREEN"), 0,
                "Sinon seule la ligne choisie change.", AFFECT),
        ),
    )

    private val m6l2 = ctLesson(
        M6, 2, "Éditeur de son, modes et filtres", listOf(NOUVEAU_SYNTH, EDITEUR, MODES, FILTRES), 20,
        objective = "Créer un synthé et accéder à tous ses paramètres.",
        explanation = "SHIFT + SYNTH crée un clip synthé vierge (onde carrée soustractive). Appuyer sur SELECT ouvre l'éditeur de son en " +
            "menus (BACK / UNDO pour remonter) ; plus rapide : SHIFT (ou Audition) + le pad du paramètre sur la grille, puis tourner " +
            "SELECT. SYNTH MODE : SUBTRACTIVE (défaut), RINGMOD ou FM ; le wavetable s'obtient en mettant le type d'oscillateur sur " +
            "WAVETABLE. Le synthé a un passe-bas (12, 24 dB ou DRIVE) et un passe-haut. « FEATURE NOT (YET?) IMPLEMENTED » ou « CANT » " +
            "signalent un paramètre indisponible. Sauvegarde : SAVE + SYNTH.",
        controls = listOf(
            cmd("SHIFT + SYNTH", "Nouveau synthé.", NOUVEAU_SYNTH),
            cmd("(SELECT) appuyé", "Éditeur de son.", EDITEUR),
            cmd("SHIFT + pad de paramètre", "Raccourci direct.", EDITEUR),
            cmd("SYNTH MODE", "Soustractif, RingMod, FM.", NOUVEAU_SYNTH),
        ),
        steps = listOf(
            "En vue Clip, SHIFT + SYNTH : un nouveau synthé carré.",
            "Appuie sur SELECT, va dans OSCILLATOR 1 > TYPE et choisis SAW.",
            "Avec SHIFT + pad, ouvre la fréquence du LPF et règle-la avec SELECT.",
            "Passe le SYNTH MODE sur FM, écoute, reviens en SUBTRACTIVE et sauvegarde avec SAVE + SYNTH.",
        ),
        expected = "Un synthé maison en dent de scie filtrée, sauvegardé.",
        exerciseTitle = "Basse acide maison",
        exercise = listOf(
            "Crée une basse : dent de scie, LPF en 24 dB, résonance haute.",
            "Écris une ligne en doubles croches et joue Cutoff en direct.",
        ),
        success = "La basse crie quand tu ouvres le filtre.",
        style = "Acidcore : résonance haute, filtre joué à la main.",
        mistakes = listOf(
            "Chercher un mode WAVETABLE dans SYNTH MODE : il passe par le type d'oscillateur.",
            "Oublier de sauvegarder le preset après l'édition.",
        ),
        quiz = listOf(
            qz("Créer un synthé vierge ?", listOf("SHIFT + SYNTH", "LOAD + SYNTH"), 0,
                "Un synthé carré soustractif est créé.", NOUVEAU_SYNTH),
            qz("Modes de SYNTH MODE ?", listOf("Subtractive, RingMod, FM", "Wavetable seulement"), 0,
                "Le wavetable passe par le type d'oscillateur.", NOUVEAU_SYNTH),
            qz("Raccourci vers un paramètre ?", listOf("SHIFT + pad de la grille", "PLAY + pad"), 0,
                "Puis SELECT pour régler.", EDITEUR),
            qz("Quels filtres ?", listOf("Passe-bas et passe-haut", "Passe-bande seulement"), 0,
                "LPF et HPF.", FILTRES),
        ),
    )

    private val m6l3 = ctLesson(
        M6, 3, "Moduler : LFO et enveloppes", listOf(MOD, ENV_LFO), 15,
        objective = "Relier un LFO ou une enveloppe à un paramètre.",
        explanation = "D'abord SHIFT + pad de la destination (ex. fréquence du LPF), puis SHIFT + pad de la source (ex. LFO 1) : l'écran " +
            "montre la profondeur, réglée avec SELECT (positive ou négative ; 0 = pas de lien). BACK / UNDO valide. Sources : LFO 1 " +
            "(global, ne redémarre pas, peut se caler sur le tempo), LFO 2 (par voix, redémarre à chaque note), ENV 1 (volume par " +
            "défaut), ENV 2, vélocité, note, aléatoire, sidechain, aftertouch, X et Y. Pour supprimer un lien : remettre sa profondeur à 0.",
        controls = listOf(
            cmd("SHIFT + pad destination", "Choisir le paramètre modulé.", MOD),
            cmd("SHIFT + pad source", "Choisir la source.", MOD),
            cmd("(SELECT)", "Profondeur ; 0 = supprimer.", MOD),
        ),
        steps = listOf(
            "SHIFT + pad de fréquence du LPF.",
            "SHIFT + pad LFO 1 : règle la profondeur à 20 avec SELECT.",
            "Valide avec BACK / UNDO et écoute le filtre onduler.",
            "Remets la profondeur à 0 pour supprimer le lien.",
        ),
        expected = "Le filtre bouge tout seul au rythme du LFO.",
        exerciseTitle = "Wobble calé",
        exercise = listOf(
            "Relie LFO 1 au filtre et règle sa synchro (SYNC) sur 1/8.",
            "Ajoute ENV 2 vers le filtre pour une attaque marquée.",
        ),
        success = "Le filtre ondule en rythme et claque à chaque note.",
        style = "Mentalcore : filtre modulé et calé sur le tempo pour les basses.",
        mistakes = listOf(
            "Choisir la source avant la destination : le guide commence par la destination.",
            "Croire que LFO 2 est global : il est par voix et redémarre à chaque note.",
        ),
        quiz = listOf(
            qz("Par quoi commencer ?", listOf("La destination", "La source"), 0,
                "SHIFT + pad destination, puis source.", MOD),
            qz("Supprimer un lien de modulation ?", listOf("Profondeur à 0", "Éteindre le Deluge"), 0,
                "Une valeur non nulle crée le lien.", MOD),
            qz("Quel LFO redémarre à chaque note ?", listOf("LFO 1", "LFO 2"), 1,
                "LFO 2 est par voix.", ENV_LFO),
            qz("Que règle ENV 1 par défaut ?", listOf("Le volume", "Le tempo"), 0,
                "ENV 1 contrôle l'amplitude par défaut.", ENV_LFO),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 7 — Kits et vue Song
    // ---------------------------------------------------------------------
    private const val M7 = "dlg-m7"

    private val m7l1 = ctLesson(
        M7, 1, "Construire un kit", listOf(KIT_BASE, KIT_NOUVEAU), 20,
        objective = "Créer un kit avec tes samples et le ranger.",
        explanation = "SHIFT + KIT crée un kit vierge et ouvre le navigateur : SELECT choisit un sample, appuyer le charge sur la première " +
            "ligne libre. AUDITION + KIT charge un sample sur une autre ligne. Maintenir SELECT sur un dossier puis choisir ALL charge " +
            "tout le dossier, une ligne par sample (8 samples par dossier conseillés). AUDITION + SYNTH met un synthé sur une ligne. " +
            "Réordonner : maintenir Audition puis Mute et tourner SCROLL ▼▲. Renommer : SHIFT + NAME. Sauvegarder : SAVE + KIT.",
        controls = listOf(
            cmd("SHIFT + KIT", "Kit vierge et navigateur.", KIT_NOUVEAU),
            cmd("AUDITION + KIT", "Sample sur une ligne.", KIT_NOUVEAU),
            cmd("Maintenir (SELECT) > ALL", "Charger un dossier entier.", KIT_NOUVEAU),
            cmd("AUDITION + MUTE + (SCROLL ▼▲)", "Déplacer une ligne.", KIT_BASE),
        ),
        steps = listOf(
            "SHIFT + KIT, choisis un kick dans SAMPLES > DRUMS et appuie sur SELECT.",
            "AUDITION + KIT sur la ligne suivante pour une caisse claire.",
            "Charge un dossier de percussions avec SELECT maintenu > ALL.",
            "Remets le kick en bas avec AUDITION + MUTE + SCROLL ▼▲, puis SAVE + KIT.",
        ),
        expected = "Ton propre kit, rangé et sauvegardé.",
        exerciseTitle = "Kit free party",
        exercise = listOf(
            "Monte un kit de 8 lignes : kick, caisse claire, deux charleys, quatre percussions.",
            "Renomme le kick avec SHIFT + NAME.",
        ),
        success = "Le kit se recharge avec ses noms et son ordre.",
        style = "Hardtek / tribe : kick en bas, percussions au-dessus.",
        mistakes = listOf(
            "Charger un dossier de 100 samples d'un coup : le guide conseille environ 8 par dossier.",
            "Remplacer avec LOAD et perdre les réglages : BROWSE garde les paramètres de la ligne.",
        ),
        quiz = listOf(
            qz("Créer un kit vierge ?", listOf("SHIFT + KIT", "LOAD + KIT"), 0,
                "Le navigateur s'ouvre aussitôt.", KIT_NOUVEAU),
            qz("Charger tout un dossier ?", listOf("Maintenir SELECT puis ALL", "PLAY + KIT"), 0,
                "Une ligne par sample.", KIT_NOUVEAU),
            qz("Mettre un synthé sur une ligne ?", listOf("AUDITION + SYNTH", "SHIFT + SYNTH"), 0,
                "La ligne devient un synthé.", KIT_NOUVEAU),
            qz("Déplacer une ligne ?", listOf("AUDITION + MUTE + SCROLL ▼▲", "SHIFT + SCALE"), 0,
                "Les autres lignes se décalent.", KIT_BASE),
        ),
    )

    private val m7l2 = ctLesson(
        M7, 2, "La vue Song : lancer les clips", listOf(SONG, LAUNCH, EDIT_SONG), 15,
        objective = "Gérer tous tes clips et les lancer en rythme.",
        explanation = "SONG (bleu) affiche un clip par ligne. Un pad sur une ligne vide crée un clip (synthé par défaut) ; sur une ligne " +
            "occupée, il ouvre le clip. La colonne de droite devient LAUNCH : un appui arme le clip (rouge clignotant) qui démarre au " +
            "prochain tour, ou s'arrête à la fin du tour (vert clignotant) ; SHIFT + LAUNCH agit tout de suite. Pendant le compte à " +
            "rebours, SELECT change le nombre de tours. Solo : SCROLL ◄► + LAUNCH. Cloner : maintenir un clip et appuyer sur une autre " +
            "ligne ; supprimer : maintenir + SAVE / DELETE (sans annulation). Deux clips du même preset ne jouent pas ensemble.",
        controls = listOf(
            cmd("SONG", "Vue Song.", SONG),
            cmd("LAUNCH / SHIFT + LAUNCH", "Lancer armé / immédiat.", LAUNCH),
            cmd("(SCROLL ◄►) + LAUNCH", "Solo.", LAUNCH),
            cmd("Pad maintenu + pad / SAVE-DELETE", "Cloner / supprimer.", EDIT_SONG),
        ),
        steps = listOf(
            "Appuie sur SONG et crée un deuxième clip sur une ligne vide.",
            "Lance la lecture, puis arme ce clip avec LAUNCH : il démarre au tour suivant.",
            "Arrête un clip avec SHIFT + LAUNCH.",
            "Mets un clip en solo avec SCROLL ◄► + LAUNCH, puis appuie sur LAUNCH pour sortir du solo.",
        ),
        expected = "Tu fais entrer et sortir les clips en rythme.",
        exerciseTitle = "Break et retour",
        exercise = listOf(
            "Arme l'arrêt du clip de kick, laisse tourner 4 mesures, réarme son retour.",
            "Règle le compte à rebours à 2 tours avec SELECT.",
        ),
        success = "Le kick revient pile au bon moment.",
        style = "Tribe / hardtek : le retour du kick après un break doit tomber pile sur le 1.",
        mistakes = listOf(
            "Supprimer un clip par erreur : il n'y a pas d'annulation en vue Song.",
            "Utiliser le même preset sur deux clips en voulant les entendre ensemble : un seul joue.",
        ),
        quiz = listOf(
            qz("LAUNCH clignote rouge…", listOf("Clip armé pour démarrer", "Clip en solo"), 0,
                "Il démarrera au prochain tour.", LAUNCH),
            qz("Lancer tout de suite ?", listOf("SHIFT + LAUNCH", "LAUNCH seul"), 0,
                "Sans attendre la fin du tour.", LAUNCH),
            qz("Supprimer un clip peut-il s'annuler ?", listOf("Oui", "Non"), 1,
                "Undo / redo ne sont pas disponibles.", EDIT_SONG),
            qz("Deux clips du même preset…", listOf("Ne jouent pas ensemble", "Jouent ensemble"), 0,
                "Un seul joue à la fois.", SONG),
        ),
    )

    private val m7l3 = ctLesson(
        M7, 3, "Sections et sauvegarde de la chanson", listOf(SECTIONS, SAVE_SONG), 15,
        objective = "Grouper les clips en sections et sauvegarder ton morceau.",
        explanation = "La colonne Audition devient SECTION en vue Song : chaque couleur est une section. SHIFT + SECTION change la section d'un " +
            "clip. Appuyer sur un pad SECTION arme tous les clips de la section. Maintenir SECTION + tourner SELECT règle la répétition : " +
            "INFINITE (défaut), un nombre de tours (puis section suivante) ou LAUNCH NON-EXCLUSIVELY. Sauvegarde : SAVE, nom ou numéro " +
            "proposé (des sous-numéros évitent d'écraser), SAVE pour confirmer. LOAD charge (aperçu sur la grille) ; possible en lecture, " +
            "le changement se fait en fin de boucle. SHIFT + LOAD = nouvelle chanson vide.",
        controls = listOf(
            cmd("SECTION / SHIFT + SECTION", "Lancer / changer de section.", SECTIONS),
            cmd("SECTION maintenu + (SELECT)", "Répétitions de la section.", SECTIONS),
            cmd("SAVE / LOAD", "Sauvegarder / charger une chanson.", SAVE_SONG),
            cmd("SHIFT + LOAD", "Nouvelle chanson vide.", SAVE_SONG),
        ),
        steps = listOf(
            "Range intro, montée et drop dans trois sections avec SHIFT + SECTION.",
            "Règle la section intro sur 2 tours avec SECTION maintenu + SELECT.",
            "Lance l'intro : elle passe seule à la section suivante.",
            "Appuie sur SAVE, nomme la chanson et confirme avec SAVE.",
        ),
        expected = "Ton morceau enchaîne ses sections et il est sauvegardé.",
        exerciseTitle = "Live en trois sections",
        exercise = listOf(
            "Joue ton set en lançant les sections à la main.",
            "Charge une autre chanson pendant la lecture pour enchaîner.",
        ),
        success = "Le passage d'une chanson à l'autre se fait en fin de boucle, sans coupure.",
        style = "Hardtek / tribe : intro, montée, break sans kick, retour.",
        mistakes = listOf(
            "Oublier de sauvegarder la chanson : les motifs ne sont pas dans les presets.",
            "Faire SHIFT + LOAD sans avoir sauvegardé : la chanson est effacée.",
        ),
        quiz = listOf(
            qz("Répétition par défaut d'une section ?", listOf("INFINITE", "1 tour"), 0,
                "Elle tourne jusqu'à changement manuel.", SECTIONS),
            qz("Changer la section d'un clip ?", listOf("SHIFT + SECTION", "SECTION seul"), 0,
                "SECTION seul arme la section.", SECTIONS),
            qz("Charger en lecture ?", listOf("Possible, changement en fin de boucle", "Impossible"), 0,
                "La transition se fait sans coupure.", SAVE_SONG),
            qz("Nouvelle chanson vide ?", listOf("SHIFT + LOAD", "SHIFT + SAVE"), 0,
                "« CLEAR SONG » puis LOAD.", SAVE_SONG),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(
            M5, 5, "Séquençage créatif",
            "Probabilité, itérations, euclidien, automation.",
            "Faire évoluer les boucles toutes seules.",
            "Probabilités et euclidien donnent des percussions tribales qui ne lassent pas.",
            listOf(m5l1, m5l2, m5l3),
        ),
        CourseModule(
            M6, 6, "Le son",
            "Paramètres rapides, éditeur de son, modes de synthèse, modulation.",
            "Créer et jouer tes sons.",
            "Filtre joué en direct et modulations calées : acidcore et mentalcore.",
            listOf(m6l1, m6l2, m6l3),
        ),
        CourseModule(
            M7, 7, "Kits et vue Song",
            "Kits personnalisés, lancement de clips, sections, sauvegarde.",
            "Construire et jouer un morceau complet.",
            "Sections et clips armés : un live autonome sur une seule machine.",
            listOf(m7l1, m7l2, m7l3),
        ),
    )
}
