package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.ContentStatus
import com.livemachine.academy.data.model.Exercise
import com.livemachine.academy.data.model.Lesson
import com.livemachine.academy.data.model.MachineControl
import com.livemachine.academy.data.model.ManualDoc.PARAMETER_GUIDE
import com.livemachine.academy.data.model.ManualRef
import com.livemachine.academy.data.model.QuizQuestion

/**
 * Module 4 — Séquenceur et patterns.
 *
 * Sources : manuel d'utilisation (partie française) et electribe sampler Parameter Guide.
 * Le Step Jump et l'enchaînement de patterns (Pattern Set, CHAIN) sont traités aux Modules 7 et 8.
 */
object Module4Content {

    private const val M = "m4"

    // Manuel d'utilisation
    private val OM_PAS_A_PAS = ManualRef("Enregistrement de données de jeu › 2. Edition pas à pas", "30")
    private val OM_TEMPS_REEL = ManualRef("Enregistrement de données de jeu › 1. Enregistrement en temps réel", "30")
    private val OM_MODE_PAD = ManualRef("Description par section › Section du mode de pad", "24")
    private val OM_STEP = ManualRef("Description par section › Boutons Step", "24")
    private val OM_REGLAGES_MOTIF = ManualRef("Edition d'un motif › 4. Changer les réglages de motif", "32")
    private val OM_ERASE = ManualRef("Edition d'un motif › 2. Effacement d'une partie", "32")
    private val OM_CLAVIER = ManualRef("Production de musique › 1. Jouer avec les pads › Mode de clavier", "28")
    private val OM_SAUVEGARDE = ManualRef("Enregistrement de données de jeu › 4. Sauvegarde d'un motif", "31")

    // Guide des paramètres
    private val PG_PATTERN = ManualRef("1. PATTERN PARAMETERS", "3", PARAMETER_GUIDE)
    private val PG_PART = ManualRef("2. PART PARAMETERS", "4", PARAMETER_GUIDE)
    private val PG_STEP_EDIT = ManualRef("3. STEP EDIT", "5", PARAMETER_GUIDE)
    private val PG_TRIGGER_MODE = ManualRef("6. GLOBAL PARAMETERS › TRIGGER MODE", "7", PARAMETER_GUIDE)
    private val PG_METRONOME = ManualRef("6. GLOBAL PARAMETERS › METRONOME", "8", PARAMETER_GUIDE)
    private val PG_SHORTCUTS = ManualRef("9. Appendix › Shortcut list", "11", PARAMETER_GUIDE)
    private val PG_SCALES = ManualRef("9. Appendix › Scale List", "16", PARAMETER_GUIDE)
    private val PG_GROOVES = ManualRef("9. Appendix › Groove Type List", "17", PARAMETER_GUIDE)

    private fun q(id: String, prompt: String, options: List<String>, correct: Int, explanation: String, ref: ManualRef) =
        QuizQuestion(id, prompt, options, correct, explanation, ref)

    // ---------------------------------------------------------------------
    // 4.1 Programmer pas à pas
    // ---------------------------------------------------------------------
    private val lesson1 = Lesson(
        id = "$M-l1",
        moduleId = M,
        order = 1,
        title = "Programmer pas à pas",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_PAS_A_PAS, OM_MODE_PAD, OM_STEP, PG_STEP_EDIT),
        durationMinutes = 15,
        objective = "Allumer et éteindre les pas d'une part avec les pads, en mode Sequencer.",
        explanation = "Bouton Sequencer allumé, les pads servent à entrer les pas de la part sélectionnée : un pad allumé " +
            "est un pas actif (ON), un pad éteint un pas inactif (OFF), et chaque pression inverse l'état. Les boutons " +
            "Step définissent la longueur de l'enregistrement ; le nombre de diodes rouges allumées l'indique. Par " +
            "défaut, un pas activé joue la note C4 (60). Si tu réactives un pas que tu avais éteint, il retrouve sa note " +
            "d'origine. Tu peux programmer machine arrêtée, ou pendant la lecture après Play/Pause.",
        controls = listOf(
            MachineControl("Sequencer", "Allumé : les pads entrent les pas de la part sélectionnée.", OM_MODE_PAD),
            MachineControl("<Part, Part>", "Choisit la part à programmer.", OM_PAS_A_PAS),
            MachineControl("Pads de déclenchement", "Chaque pression active (pad allumé) ou désactive (pad éteint) un pas.", OM_PAS_A_PAS),
            MachineControl("Boutons Step", "Définissent la longueur ; les diodes rouges l'indiquent.", OM_STEP),
            MachineControl("Play/Pause", "Écouter le résultat pendant que tu programmes.", OM_PAS_A_PAS),
        ),
        steps = listOf(
            "Ouvre ton pattern de travail du Module 3 et sélectionne la part du kick avec <Part, Part>.",
            "Appuie sur Sequencer : il s'allume.",
            "Appuie sur les pads 1, 5, 9 et 13 : ils s'allument, ce sont tes pas actifs.",
            "Lance la lecture avec Play/Pause : le kick joue sur chaque temps.",
            "Sélectionne la part de basse et active les pads 3, 7, 11 et 15 : la basse tombe entre les kicks.",
            "Éteins un pas en rappuyant sur son pad, puis rallume-le. Sauvegarde avec Write.",
        ),
        expectedResult = "Un kick sur les temps et une basse en contretemps qui tournent en boucle, programmés sans jouer en temps réel.",
        exercise = Exercise(
            id = "$M-l1-ex",
            lessonId = "$M-l1",
            title = "Le squelette tekno",
            instructions = listOf(
                "Kick sur 1, 5, 9, 13.",
                "Basse sur les contretemps (3, 7, 11, 15), puis essaie aussi de la doubler sur les pas pairs.",
                "Charley fermé sur tous les pas impairs, clap ou caisse claire sur 5 et 13.",
            ),
            successCriteria = "Quatre parts programmées qui tournent ensemble sans se marcher dessus, pattern sauvegardé.",
            styleHint = "Hardtek et tribe : la basse qui « roule » vient souvent de notes sur les doubles croches entre les kicks.",
        ),
        commonMistakes = listOf(
            "Taper les pads sans que Sequencer soit allumé : en mode Trigger, ils jouent le son au lieu d'entrer des pas (manuel p. 24).",
            "Programmer la mauvaise part : vérifie la part sélectionnée avant de toucher les pads (manuel p. 30).",
            "Changer de pattern sans Write : la programmation est perdue (manuel p. 31).",
        ),
        quiz = listOf(
            q("$M-l1-q1", "En mode Sequencer, que signifie un pad allumé ?",
                listOf("La part est coupée", "Le pas est actif (ON)", "Le pad est en panne"), 1,
                "Le statut alterne entre ON (pad allumé) et OFF (pad éteint) à chaque pression.", OM_PAS_A_PAS),
            q("$M-l1-q2", "Quelle note un pas activé joue-t-il par défaut ?",
                listOf("C3", "C4 (60)", "A4"), 1, "Par défaut, le numéro de note C4 (60) est produit.", OM_PAS_A_PAS),
            q("$M-l1-q3", "Que se passe-t-il si tu réactives un pas que tu avais éteint ?",
                listOf("Il joue C4", "Il retrouve sa note d'origine", "Il est effacé"), 1,
                "Quand on réactive un pas existant, la note originale lui est assignée.", OM_PAS_A_PAS),
            q("$M-l1-q4", "Qu'indique le nombre de diodes rouges allumées ?",
                listOf("Le tempo", "La longueur de l'enregistrement", "Le volume"), 1,
                "Le nombre de diodes rouges allumées indique visuellement la longueur.", OM_PAS_A_PAS),
        ),
    )

    // ---------------------------------------------------------------------
    // 4.2 Enregistrer en temps réel
    // ---------------------------------------------------------------------
    private val lesson2 = Lesson(
        id = "$M-l2",
        moduleId = M,
        order = 2,
        title = "Enregistrer en temps réel",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_TEMPS_REEL, OM_ERASE, PG_METRONOME, PG_SHORTCUTS),
        durationMinutes = 15,
        objective = "Enregistrer ce que tu joues aux pads sans arrêter la lecture, avec le métronome, et effacer les erreurs à la volée.",
        explanation = "Pendant la lecture, sélectionne une part et appuie sur Rec : il s'allume et tout ce que tu joues sur les " +
            "pads (ou au pad tactile) est enregistré. Le pattern tourne en boucle et l'enregistrement continue à chaque " +
            "tour. Une nouvelle pression sur Rec arrête l'enregistrement (il s'éteint). Le métronome aide à rester calé : " +
            "METRONOME sur « Rec 1 » ou « Rec 2 » le fait jouer seulement pendant l'enregistrement, « Rec 0 » sans " +
            "décompte, « On » pendant tout l'enregistrement. Pour corriger : Part Erase allumé, maintiens le pad de la " +
            "part pendant la lecture pour effacer ses notes. Shift + Play/Pause relance la lecture depuis le début du pattern.",
        controls = listOf(
            MachineControl("Rec", "Démarre l'enregistrement (allumé) ; une nouvelle pression l'arrête.", OM_TEMPS_REEL),
            MachineControl("METRONOME (menu Global)", "Off, Rec 0, Rec 1, Rec 2 ou On.", PG_METRONOME),
            MachineControl("Shift + pad 16", "Affiche la page METRONOME.", PG_SHORTCUTS),
            MachineControl("Part Erase + pad (pendant la lecture)", "Efface les notes de la part tant que le pad est maintenu.", OM_ERASE),
            MachineControl("Shift + Play/Pause", "Lecture depuis le début du pattern.", PG_SHORTCUTS),
        ),
        steps = listOf(
            "Maintiens Shift et appuie sur le pad 16 : règle METRONOME sur « Rec 1 ».",
            "Lance le pattern avec Play/Pause et sélectionne une part de percussion.",
            "Appuie sur Rec : il s'allume.",
            "Allume Trigger et joue la percussion sur son pad, au métronome, pendant quelques tours.",
            "Appuie à nouveau sur Rec pour arrêter l'enregistrement : la lecture continue avec ce que tu as joué.",
            "Si une note est de trop : allume Part Erase et maintiens le pad de la part pendant la lecture.",
            "Sauvegarde avec Write.",
        ),
        expectedResult = "Ta partie jouée à la main tourne dans le pattern, et tu sais effacer une prise ratée sans arrêter.",
        exercise = Exercise(
            id = "$M-l2-ex",
            lessonId = "$M-l2",
            title = "Percussions à la main",
            instructions = listOf(
                "Sur ton squelette de la leçon 4.1, enregistre en temps réel une percussion qui répond au kick.",
                "Fais trois prises ; efface avec Part Erase celles qui ne te plaisent pas.",
                "Garde la meilleure et sauvegarde.",
            ),
            successCriteria = "Une percussion jouée à la main, calée sur le tempo, sans notes parasites.",
            styleHint = "Tribe : les percussions jouées à la main, légèrement irrégulières, donnent le côté organique.",
        ),
        commonMistakes = listOf(
            "Croire qu'il faut arrêter la lecture pour enregistrer : l'enregistrement se fait pendant que le pattern tourne (manuel p. 30).",
            "Laisser METRONOME sur « On » et l'entendre pendant tout l'enregistrement ; choisis « Rec 0 », « Rec 1 », « Rec 2 » ou « Off » selon ton besoin (guide p. 8).",
            "Appuyer sur Part Erase sans maintenir le pad : l'effacement se fait tant que le pad est maintenu, pendant la lecture (manuel p. 32).",
        ),
        quiz = listOf(
            q("$M-l2-q1", "Comment arrêter un enregistrement en temps réel sans arrêter la lecture ?",
                listOf("Stop", "Appuyer à nouveau sur Rec", "Exit"), 1, "Une nouvelle pression sur Rec arrête l'enregistrement ; le bouton s'éteint.", OM_TEMPS_REEL),
            q("$M-l2-q2", "Que fait le réglage METRONOME « Rec 0 » ?",
                listOf("Le métronome joue sans décompte, seulement pendant l'enregistrement", "Le métronome est coupé", "Le métronome joue tout le temps"), 0,
                "Rec 0, Rec 1 et Rec 2 ne jouent que pendant l'enregistrement ; Rec 0 n'a pas de décompte.", PG_METRONOME),
            q("$M-l2-q3", "Quel raccourci relance la lecture depuis le début du pattern ?",
                listOf("Shift + Play/Pause", "Shift + Stop", "Shift + Rec"), 0, "Shift + Play/Pause : lecture depuis le début du pattern.", PG_SHORTCUTS),
            q("$M-l2-q4", "Pendant l'enregistrement en temps réel, que fait le pattern ?",
                listOf("Il s'arrête à la fin", "Il est répété et l'enregistrement se poursuit", "Il revient au pattern 1"), 1,
                "Le motif est répété et l'enregistrement se poursuit.", OM_TEMPS_REEL),
        ),
    )

    // ---------------------------------------------------------------------
    // 4.3 Longueur, beat et Last Step
    // ---------------------------------------------------------------------
    private val lesson3 = Lesson(
        id = "$M-l3",
        moduleId = M,
        order = 3,
        title = "Longueur, beat et Last Step",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_REGLAGES_MOTIF, PG_PATTERN, PG_PART, PG_SHORTCUTS),
        durationMinutes = 15,
        objective = "Régler la longueur et la subdivision d'un pattern, et donner à une part une longueur différente pour créer des décalages.",
        explanation = "LENGTH (paramètre de pattern, de 1 à 4) règle la longueur du pattern. BEAT règle la subdivision : 16, " +
            "32, 8Tri ou 16Tri (ternaire). Ces deux réglages valent pour tout le pattern. LAST STEP (paramètre de part, de 1 " +
            "à 16) est propre à chaque part : normalement 16, mais avec 11 par exemple la part boucle sur 11 pas pendant " +
            "que les autres bouclent sur 16. Les notes au-delà du Last Step sont gardées mais ne jouent pas. En 8Tri et " +
            "16Tri, une part a au maximum 12 pas. Attention : le Last Step ne se synchronise pas avec un séquenceur externe " +
            "ou un autre modèle d'electribe. Raccourcis : Shift + pad 2 affiche LENGTH, Shift + pad 7 affiche LAST STEP.",
        controls = listOf(
            MachineControl("LENGTH", "Longueur du pattern, de 1 à 4.", PG_PATTERN),
            MachineControl("BEAT", "16, 32, 8Tri ou 16Tri.", PG_PATTERN),
            MachineControl("LAST STEP", "Dernier pas de la part, de 1 à 16.", PG_PART),
            MachineControl("Shift + pad 2 / Shift + pad 7", "Pages LENGTH / LAST STEP.", PG_SHORTCUTS),
        ),
        steps = listOf(
            "Lance ton squelette de la leçon 4.1.",
            "Maintiens Shift et appuie sur le pad 2 : la page LENGTH s'affiche. Regarde sa valeur, laisse-la sur 1 pour l'instant.",
            "Sélectionne une part de percussion, puis maintiens Shift et appuie sur le pad 7 : la page LAST STEP s'affiche.",
            "Règle LAST STEP sur 12 avec la commande de valeur : la percussion se décale par rapport au kick à chaque tour.",
            "Essaie 11, puis 15, et écoute comment le décalage évolue.",
            "Remets 16 si tu veux revenir au départ ; les notes au-delà du Last Step n'ont pas été effacées. Sauvegarde avec Write.",
        ),
        expectedResult = "Une percussion qui tourne sur un cycle différent du kick et crée un motif qui évolue sans que tu touches à rien.",
        exercise = Exercise(
            id = "$M-l3-ex",
            lessonId = "$M-l3",
            title = "Polyrythmie hypnotique",
            instructions = listOf(
                "Garde kick et basse sur 16 pas.",
                "Mets une percussion sur LAST STEP 12 et une autre sur LAST STEP 10.",
                "Écoute au moins 8 tours : le motif doit évoluer sans devenir confus.",
            ),
            successCriteria = "Deux percussions décalées qui évoluent sur la durée, kick et basse restant stables.",
            styleHint = "Tribe et mentalcore : les polyrythmies créent la transe sans ajouter de nouveaux sons.",
        ),
        commonMistakes = listOf(
            "Changer LENGTH ou BEAT en croyant modifier une seule part : ce sont des réglages du pattern (guide p. 3).",
            "Croire que les notes après le Last Step sont effacées : elles sont gardées mais ne jouent pas (guide p. 4).",
            "Compter sur le Last Step en synchro avec un autre appareil : il ne se synchronise pas avec un séquenceur externe (guide p. 4).",
            "Régler un Last Step au-dessus de 12 en 8Tri ou 16Tri : il est ramené à 12 (guide p. 4).",
        ),
        quiz = listOf(
            q("$M-l3-q1", "Quelle est la plage de LENGTH ?",
                listOf("1 à 4", "1 à 16", "1 à 64"), 0, "LENGTH règle la longueur du motif, entre 1 et 4.", OM_REGLAGES_MOTIF),
            q("$M-l3-q2", "Quelles valeurs peut prendre BEAT ?",
                listOf("16, 32, 8Tri, 16Tri", "4/4, 3/4, 6/8", "1 à 4"), 0, "BEAT : 16, 32, 8Tri ou 16Tri.", PG_PATTERN),
            q("$M-l3-q3", "LAST STEP est un réglage…",
                listOf("De pattern", "De part", "Global"), 1, "LAST STEP fait partie des paramètres de part, sauvegardés pour chaque part.", PG_PART),
            q("$M-l3-q4", "Que deviennent les notes placées après le Last Step ?",
                listOf("Elles sont effacées", "Elles sont gardées mais ne jouent pas", "Elles jouent au tour suivant"), 1,
                "Les notes de la portion raccourcie sont préservées mais pas jouées.", PG_PART),
        ),
    )

    // ---------------------------------------------------------------------
    // 4.4 Éditer chaque pas (STEP EDIT)
    // ---------------------------------------------------------------------
    private val lesson4 = Lesson(
        id = "$M-l4",
        moduleId = M,
        order = 4,
        title = "Éditer chaque pas",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(PG_STEP_EDIT, PG_SHORTCUTS, OM_PAS_A_PAS),
        durationMinutes = 20,
        objective = "Régler la note, la durée et la force de chaque pas avec STEP EDIT.",
        explanation = "Chaque pas d'une part contient cinq données : le trigger (le pas joue ou non), la note, le gate time " +
            "(durée), la vélocité (force) et la motion sequence. STEP EDIT (Shift + pad 12) permet de les régler pas par " +
            "pas. STEP NUMBER choisit le pas à éditer, le « target step », de 1.01 à 4.16 (mesure.pas) : avec la commande " +
            "de valeur (Shift + valeur avance d'une longueur entière) ou en appuyant sur un pad, ce qui inverse aussi son " +
            "état ON/OFF. NOTE (C-1 à G9) : jusqu'à 4 notes par pas, Shift + valeur change d'octave. GATE TIME (00 à 96, " +
            "ou TIE) : 96 dure exactement un pas ; TIE enchaîne avec le pas suivant sans relancer oscillateur, " +
            "enveloppe ni modulation si la note est la même. VELOCITY : 001 à 127. Un pas éteint ne joue pas, même si tu " +
            "as changé sa note ou sa durée.",
        controls = listOf(
            MachineControl("Shift + pad 12", "Affiche la page STEP EDIT.", PG_SHORTCUTS),
            MachineControl("STEP NUMBER", "Pas à éditer, de 1.01 à 4.16.", PG_STEP_EDIT),
            MachineControl("NOTE", "Note du pas (jusqu'à 4 notes) ; Shift + valeur = par octave.", PG_STEP_EDIT),
            MachineControl("GATE TIME", "Durée du pas, 00 à 96 ou TIE.", PG_STEP_EDIT),
            MachineControl("VELOCITY", "Force du pas, 001 à 127.", PG_STEP_EDIT),
        ),
        steps = listOf(
            "Sélectionne ta part de basse, maintiens Shift et appuie sur le pad 12 : STEP EDIT s'ouvre sur STEP NUMBER.",
            "Avec la commande de valeur, choisis le premier pas de basse (par exemple 1.03).",
            "Avec >, va sur NOTE et change la note ; essaie Shift + valeur pour changer d'octave.",
            "Va sur GATE TIME : raccourcis à 24 pour une note sèche, puis essaie TIE vers un pas suivant de même note.",
            "Va sur VELOCITY et baisse-la sur un pas sur deux pour créer des accents.",
            "Recommence pour les autres pas de basse, écoute en lecture, puis sauvegarde avec Write.",
        ),
        expectedResult = "Ta ligne de basse a plusieurs notes, des durées différentes et des accents, sans avoir été jouée en temps réel.",
        exercise = Exercise(
            id = "$M-l4-ex",
            lessonId = "$M-l4",
            title = "Ligne de basse en quatre notes",
            instructions = listOf(
                "Programme 8 pas de basse en mode Sequencer.",
                "Dans STEP EDIT, donne-leur 4 notes différentes de ta gamme, dont une à l'octave grave.",
                "Raccourcis le GATE TIME de la moitié des pas et baisse leur VELOCITY.",
            ),
            successCriteria = "Une ligne de basse de 8 pas, 4 notes, avec des pas courts et accentués qui se distinguent à l'oreille.",
            styleHint = "Acidcore : alterner pas courts, pas liés (TIE) et accents fait la ligne acide, avant même le filtre.",
        ),
        commonMistakes = listOf(
            "Modifier la note d'un pas éteint et ne rien entendre : il ne joue qu'une fois son trigger activé (guide p. 5).",
            "Appuyer sur un pad pour choisir le pas et inverser son ON/OFF sans le vouloir : passe par la commande de valeur (guide p. 5).",
            "Utiliser TIE sur deux notes différentes en attendant une liaison : TIE n'évite la relance que si la note suivante est la même (guide p. 5).",
        ),
        quiz = listOf(
            q("$M-l4-q1", "Combien de notes un pas peut-il contenir au maximum ?",
                listOf("1", "4", "16"), 1, "On peut enregistrer jusqu'à quatre numéros de note par pas.", PG_STEP_EDIT),
            q("$M-l4-q2", "Avec un GATE TIME de 96, combien de temps dure la note ?",
                listOf("La moitié d'un pas", "Exactement un pas", "Toute la mesure"), 1,
                "Avec 96, la durée de la note est exactement celle d'un pas.", PG_STEP_EDIT),
            q("$M-l4-q3", "Que fait GATE TIME réglé sur TIE ?",
                listOf("Il coupe la note", "Si le pas suivant a la même note, oscillateur, enveloppe et modulation ne sont pas relancés", "Il double la vélocité"), 1,
                "TIE évite la relance quand le pas suivant porte la même note.", PG_STEP_EDIT),
            q("$M-l4-q4", "Quel raccourci ouvre STEP EDIT ?",
                listOf("Shift + pad 12", "Shift + pad 2", "Shift + Write"), 0, "Shift + pad 12 affiche la page STEP EDIT.", PG_SHORTCUTS),
            q("$M-l4-q5", "Quelle est la plage de VELOCITY d'un pas ?",
                listOf("0 à 96", "001 à 127", "1 à 16"), 1, "VELOCITY va de 001 à 127.", PG_STEP_EDIT),
        ),
    )

    // ---------------------------------------------------------------------
    // 4.5 Swing et groove
    // ---------------------------------------------------------------------
    private val lesson5 = Lesson(
        id = "$M-l5",
        moduleId = M,
        order = 5,
        title = "Swing et groove",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(PG_PATTERN, PG_PART, PG_GROOVES, PG_SHORTCUTS, OM_REGLAGES_MOTIF),
        durationMinutes = 10,
        objective = "Sortir de la grille métronomique avec le swing du pattern et le groove de chaque part.",
        explanation = "SWING (paramètre de pattern, de -50 % à +50 %) décale les pas pairs ; +33 % est proche d'un shuffle " +
            "parfait. Avec un Last Step impair, ou certains effets maîtres qui pilotent le séquenceur, ce sont parfois les " +
            "pas impairs qui sont décalés. GROOVE TYPE et GROOVE DEPTH (paramètres de part) appliquent un groove inspiré " +
            "de la façon de jouer de certains instruments : 25 types, de Conga 1 à Decrescendo (Bongo, Cabasa, Off Beat, " +
            "On Beat, Laidback, Rushbeat…), avec une intensité de 0 à 127. Raccourcis : Shift + pad 1 (SWING), Shift + " +
            "pad 8 (GROOVE TYPE), Shift + pad 9 (GROOVE DEPTH).",
        controls = listOf(
            MachineControl("SWING (Shift + pad 1)", "Décalage des pas pairs, -50 % à +50 % ; tout le pattern.", PG_PATTERN),
            MachineControl("GROOVE TYPE (Shift + pad 8)", "Type de groove de la part, 25 choix.", PG_PART),
            MachineControl("GROOVE DEPTH (Shift + pad 9)", "Intensité du groove, 0 à 127.", PG_PART),
        ),
        steps = listOf(
            "Lance ton pattern. Maintiens Shift et appuie sur le pad 1 : la page SWING s'affiche.",
            "Monte le swing jusqu'à +33 % : écoute le charley devenir shuffle. Reviens ensuite à une valeur plus faible.",
            "Sélectionne une part de percussion, Shift + pad 8, et choisis un GROOVE TYPE (par exemple Conga 1).",
            "Shift + pad 9 et monte GROOVE DEPTH progressivement en écoutant.",
            "Compare avec la même part sans groove (DEPTH à 0). Sauvegarde avec Write.",
        ),
        expectedResult = "Le pattern respire : le charley est moins mécanique et la percussion a un placement plus « joué ».",
        exercise = Exercise(
            id = "$M-l5-ex",
            lessonId = "$M-l5",
            title = "Grille droite contre groove",
            instructions = listOf(
                "Garde le kick sans groove, bien droit.",
                "Donne un groove différent à deux percussions (types et profondeurs différents).",
                "Trouve un swing léger qui ne fait pas « boiter » le kick.",
            ),
            successCriteria = "Un kick droit et des percussions groovées qui restent en place ensemble.",
            styleHint = "Hardtek : kick et basse droits, groove seulement sur les percussions. Tribe : n'hésite pas à pousser le groove des percussions.",
        ),
        commonMistakes = listOf(
            "Croire que SWING ne touche qu'une part : c'est un réglage du pattern, toutes les parts sont concernées (guide p. 3).",
            "Choisir un GROOVE TYPE en laissant GROOVE DEPTH à 0 : aucun effet (guide p. 4).",
            "S'étonner que le swing touche les pas impairs avec un Last Step impair ou certains effets maîtres : c'est prévu (guide p. 3).",
        ),
        quiz = listOf(
            q("$M-l5-q1", "Quelle valeur de SWING est proche d'un shuffle parfait ?",
                listOf("+12 %", "+33 %", "+50 %"), 1, "Un réglage de +33 % donne un timing proche d'un shuffle parfait.", PG_PATTERN),
            q("$M-l5-q2", "Quels pas le SWING décale-t-il normalement ?",
                listOf("Les pas pairs", "Les pas impairs", "Le premier pas de chaque mesure"), 0,
                "SWING décale le timing des pas pairs, en pourcentage.", PG_PATTERN),
            q("$M-l5-q3", "Combien de types de groove la machine propose-t-elle ?",
                listOf("8", "25", "50"), 1, "La Groove Type List va de 1 Conga 1 à 25 Decrescendo.", PG_GROOVES),
            q("$M-l5-q4", "GROOVE TYPE et GROOVE DEPTH sont des réglages…",
                listOf("De pattern", "De part", "Global"), 1, "Ce sont des paramètres de part.", PG_PART),
        ),
    )

    // ---------------------------------------------------------------------
    // 4.6 Gammes et transposition de la séquence
    // ---------------------------------------------------------------------
    private val lesson6 = Lesson(
        id = "$M-l6",
        moduleId = M,
        order = 6,
        title = "Gammes et transposition de la séquence",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(PG_PATTERN, PG_PART, PG_SCALES, PG_TRIGGER_MODE, OM_CLAVIER),
        durationMinutes = 15,
        objective = "Enregistrer des notes dans une gamme, transposer toute une séquence en changeant la tonique, et jouer une séquence depuis un pad.",
        explanation = "KEY (C à B) et SCALE (paramètres de pattern) fixent la tonique et la gamme des pads en mode Keyboard " +
            "et du pad tactile. La liste compte 35 gammes, de Chromatic à Octave, dont Aeolian, Phrygian, les pentatoniques, " +
            "Arabic, Gypsy et une gamme « Bass Line ». SCALE MODE (paramètre de part, Off/On) fait suivre la hauteur des " +
            "notes enregistrées quand tu changes KEY ou SCALE ; il faut l'activer avant d'enregistrer la part. Enfin, le " +
            "réglage Global TRIGGER MODE change ce que joue un pad en mode Trigger : « Normal » (la note C4), « Seq 1st » " +
            "(la première note enregistrée dans la part) ou « Seq Play » (la séquence de la part, tant que le pad est tenu).",
        controls = listOf(
            MachineControl("KEY / SCALE (Shift + pad 4 / pad 5)", "Tonique et gamme du pattern.", PG_SHORTCUTS),
            MachineControl("SCALE MODE", "La séquence de la part suit KEY et SCALE (à activer avant d'enregistrer).", PG_PART),
            MachineControl("Keyboard", "Les pads jouent la part dans la gamme choisie.", OM_CLAVIER),
            MachineControl("TRIGGER MODE (menu Global)", "Normal, Seq 1st ou Seq Play.", PG_TRIGGER_MODE),
        ),
        steps = listOf(
            "Choisis une part de basse vide. Dans le menu d'édition, mets SCALE MODE sur On.",
            "Shift + pad 4 : règle KEY. Shift + pad 5 : choisis une SCALE (par exemple Phrygian ou Bass Line).",
            "Passe en Keyboard, lance la lecture, appuie sur Rec et enregistre une ligne de quelques notes (leçon 4.2).",
            "Arrête l'enregistrement, puis change KEY : toute la ligne est transposée.",
            "Dans le menu Global, mets TRIGGER MODE sur « Seq Play ». En mode Trigger, maintiens le pad de la basse : sa séquence joue tant que tu tiens.",
            "Remets TRIGGER MODE sur « Normal » si tu préfères, puis sauvegarde avec Write.",
        ),
        expectedResult = "Ta ligne de basse se transpose quand tu changes la tonique, et tu sais lancer la séquence d'une part en tenant son pad.",
        exercise = Exercise(
            id = "$M-l6-ex",
            lessonId = "$M-l6",
            title = "Changer de tonalité en live",
            instructions = listOf(
                "Enregistre une basse avec SCALE MODE sur On.",
                "Pendant la lecture, change KEY tous les 4 tours, sur 3 toniques différentes.",
                "Note les toniques qui sonnent le mieux avec ton kick.",
            ),
            successCriteria = "La basse suit chaque changement de tonique sans fausse note, et tu as noté tes 3 toniques.",
            styleHint = "Mentalcore et acidcore : monter la tonique d'un ton ou deux pendant une montée renforce la tension.",
        ),
        commonMistakes = listOf(
            "Activer SCALE MODE après avoir enregistré : il doit être sur On avant d'enregistrer la part pour que la hauteur suive (guide p. 4).",
            "Croire que KEY et SCALE sont propres à une part : ce sont des paramètres de pattern (guide p. 3).",
            "Oublier que TRIGGER MODE est Global : il s'applique à tous les patterns (guide p. 7).",
        ),
        quiz = listOf(
            q("$M-l6-q1", "Combien de gammes la Scale List compte-t-elle ?",
                listOf("12", "35", "50"), 1, "La liste va de 1 Chromatic à 35 Octave.", PG_SCALES),
            q("$M-l6-q2", "Quand activer SCALE MODE pour que la hauteur suive les changements de KEY ?",
                listOf("Avant d'enregistrer la part", "Après avoir enregistré", "Jamais, c'est automatique"), 0,
                "Pour que la hauteur suive correctement, active-le avant d'enregistrer le jeu de la part.", PG_PART),
            q("$M-l6-q3", "Avec TRIGGER MODE sur « Seq Play », que fait un pad en mode Trigger ?",
                listOf("Il joue C4", "Il joue la séquence de la part tant qu'il est tenu", "Il efface la part"), 1,
                "Seq Play : la séquence enregistrée de la part joue tant que tu maintiens le pad.", PG_TRIGGER_MODE),
            q("$M-l6-q4", "Quel raccourci affiche la page KEY ?",
                listOf("Shift + pad 4", "Shift + pad 12", "Shift + Tap"), 0, "Shift + pad 4 affiche la page KEY.", PG_SHORTCUTS),
        ),
    )

    val lessons: List<Lesson> = listOf(lesson1, lesson2, lesson3, lesson4, lesson5, lesson6)
}
