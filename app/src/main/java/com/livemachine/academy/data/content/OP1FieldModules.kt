package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** OP-1 field — modules 1 à 5 : prise en main, synthé, batterie et échantillonnage, bande, mixage et tempo. */
internal object OP1FieldModules {

    // Références du guide (pages du PDF)
    private val FACADE = op1f("overview", "4")
    private val ALIM = op1f("power on, charging", "5")
    private val MODES = op1f("start", "6")
    private val AIDE = op1f("shift, help, track keys T1-T4", "7")
    private val ENCODEURS = op1f("guide conventions, encoder commands", "8")
    private val PRESETS = op1f("preset sounds", "9")
    private val SAVE = op1f("save a preset, manage presets", "10")
    private val MOTEURS = op1f("synth mode, synthesis selection", "11")
    private val ENVELOPPE = op1f("envelope", "22")
    private val EFFETS = op1f("effects", "23")
    private val LFO = op1f("lfo", "29")
    private val DRUM = op1f("drum mode", "35")
    private val DRUM_SAMPLER = op1f("drum sampler", "36")
    private val SAMPLER = op1f("synth sampler, sampling", "20–21")
    private val INPUT = op1f("input", "54")
    private val TAPE = op1f("tape mode", "39")
    private val TAPE_EDIT = op1f("record to tape, tape edit, tape tricks", "40")
    private val TAPE_SHIFT = op1f("shift functions", "41")
    private val TAPE_STYLES = op1f("tape browser, tape styles", "42")
    private val MIXER = op1f("mixer", "43")
    private val EQ = op1f("equalizer, master effect", "44")
    private val SOUND_PATH = op1f("sound path", "46")
    private val TEMPO = op1f("tempo", "52")
    private val SYNC = op1f("sync modes", "53")
    private val SEQ = op1f("sequencers", "47")

    // ---------------------------------------------------------------------
    // Module 1 — Prise en main
    // ---------------------------------------------------------------------
    private const val M1 = "op1f-m1"

    private val m1l1 = ctLesson(
        M1, 1, "Façade, charge et mise en marche", listOf(FACADE, ALIM), 10,
        objective = "Connaître les prises et charger l'OP-1 field.",
        explanation = "À gauche : haut-parleur et volume. À droite : sortie 3,5 mm (casque, table, enceintes), entrée 3,5 mm (audio externe de " +
            "niveau ligne), USB-C (audio, MIDI, charge, données), LED de charge et interrupteur. En haut à droite : micro et vu-mètre. " +
            "Interrupteur vers le haut = marche ; l'écran affiche le logo et la version du firmware, puis l'écran tape. La charge se fait par " +
            "USB-C ; help maintenu affiche le niveau de batterie. Charger au moins tous les 6 mois. Les données sont sauvegardées " +
            "automatiquement.",
        controls = listOf(
            cmd("Sortie / entrée 3,5 mm", "Audio.", FACADE),
            cmd("USB-C", "Charge, audio, MIDI, données.", FACADE),
            cmd("Interrupteur", "Haut = marche.", ALIM),
            cmd("help maintenu", "Niveau de batterie.", ALIM),
        ),
        steps = listOf(
            "Branche l'USB-C à un chargeur jusqu'à la fin de la charge.",
            "Allume (interrupteur vers le haut) : l'écran tape apparaît.",
            "Maintiens help pour voir la batterie.",
            "Relie la sortie 3,5 mm à ta table.",
        ),
        expected = "L'OP-1 field est chargé, allumé et relié à ta table.",
        exerciseTitle = "Prêt pour la session",
        exercise = listOf("Vérifie la batterie avant chaque session et recharge si besoin."),
        success = "Tu n'es jamais à court de batterie.",
        mistakes = listOf("Chercher un bouton Save général : les données sont enregistrées automatiquement."),
        quiz = listOf(
            qz("Comment charger ?", listOf("Par l'USB-C", "Par la sortie 3,5 mm"), 0, "Ordinateur ou chargeur USB.", ALIM),
            qz("Voir la batterie ?", listOf("Maintenir help", "shift + tape"), 0, "Les LED indiquent le niveau.", ALIM),
            qz("Faut-il sauvegarder la session ?", listOf("Non, c'est automatique", "Oui, à chaque fois"), 0, "Tout est retrouvé au rallumage.", ALIM),
            qz("Entrée 3,5 mm pour…", listOf("Audio externe niveau ligne", "Le casque"), 0, "Ex. un autre synthé.", FACADE),
        ),
    )

    private val m1l2 = ctLesson(
        M1, 2, "Modes, T1–T4 et encodeurs", listOf(MODES, AIDE, ENCODEURS), 15,
        objective = "Se déplacer entre les modes et comprendre les commandes.",
        explanation = "Quatre modes : synth (jouer des notes), drum (kits de batterie), tape (enregistrer et superposer sur 4 pistes stéréo) et " +
            "mixer (niveaux, pan, EQ, effets, drive). T1–T4 : en synth/drum, moteur, enveloppe, effets, LFO ; en tape, pistes 1 à 4 ; en " +
            "mixer, mixer, EQ, effet master, sortie. Les 4 encodeurs (bleu, ocre, gris, orange) règlent le paramètre de la même couleur à " +
            "l'écran ; shift + encodeur = réglage fin ; un appui confirme ou remet par défaut. help affiche une aide ; shift + help ouvre le " +
            "guide intégré.",
        controls = listOf(
            cmd("synth, drum, tape, mixer", "Modes.", MODES),
            cmd("T1–T4", "Modules ou pistes.", AIDE),
            cmd("Encodeurs de couleur", "Réglages.", ENCODEURS),
            cmd("shift + help", "Guide intégré.", AIDE),
        ),
        steps = listOf(
            "Appuie sur synth et joue quelques notes.",
            "Passe T1, T2, T3, T4 et observe l'écran.",
            "Tourne l'encodeur bleu, puis shift + bleu pour un réglage fin.",
            "Ouvre le guide intégré avec shift + help.",
        ),
        expected = "Tu sais où tu es et quoi tourner.",
        exerciseTitle = "Tour des modes",
        exercise = listOf("Passe par les quatre modes et appuie sur help dans chacun."),
        success = "Tu connais le rôle de T1–T4 dans chaque mode.",
        mistakes = listOf("Oublier que T1–T4 changent de rôle selon le mode."),
        quiz = listOf(
            qz("Pistes de la bande ?", listOf("4 stéréo", "8 mono"), 0, "T1–T4 en mode tape.", MODES),
            qz("T2 en mode synth ?", listOf("Enveloppe", "Effets"), 0, "T1 moteur, T3 FX, T4 LFO.", AIDE),
            qz("Réglage fin ?", listOf("shift + encodeur", "help + encodeur"), 0, "En général.", ENCODEURS),
            qz("Guide intégré ?", listOf("shift + help", "help seul"), 0, "help seul = aide rapide.", AIDE),
        ),
    )

    private val m1l3 = ctLesson(
        M1, 3, "Presets : charger et sauvegarder", listOf(PRESETS, SAVE), 10,
        objective = "Choisir des sons et garder tes réglages.",
        explanation = "En synth et drum, les touches 1–8 rappellent 8 presets. Un preset réunit les 4 modules (T1 moteur, T2 enveloppe, T3 FX, " +
            "T4 LFO). shift + 1–8 ouvre la liste : bleu choisit le moteur, ocre le preset. shift + drop randomise le preset chargé. Garder " +
            "un son : maintenir la touche 1–8 deux secondes ; un « snapshot » est rangé dans le dossier snapshot. lift et drop copient et " +
            "collent les presets entre dossiers ; shift + T1 dans le navigateur crée un dossier.",
        controls = listOf(
            cmd("Touches 1–8", "Presets.", PRESETS),
            cmd("shift + 1–8", "Navigateur de presets.", PRESETS),
            cmd("Touche 1–8 maintenue 2 s", "Snapshot.", SAVE),
            cmd("shift + drop", "Preset aléatoire.", PRESETS),
        ),
        steps = listOf(
            "En synth, essaie les touches 1 à 8.",
            "shift + 3 : choisis un autre moteur et preset pour la touche 3.",
            "Retouche le son, puis maintiens 3 deux secondes.",
            "Retrouve le snapshot dans le dossier snapshot (shift + 1–8).",
        ),
        expected = "Ton son modifié est sauvegardé en snapshot.",
        exerciseTitle = "Huit sons de live",
        exercise = listOf("Range sur 1–8 tes sons de set : basse, lead, nappe, kit…"),
        success = "Chaque touche donne un son prêt à jouer.",
        mistakes = listOf("Confondre shift + 1–8 (charger un preset) et maintien de 1–8 (sauvegarder)."),
        quiz = listOf(
            qz("Sauvegarder un son ?", listOf("Maintenir sa touche 1–8 deux secondes", "shift + drop"), 0, "Snapshot.", SAVE),
            qz("Modules d'un preset ?", listOf("Moteur, enveloppe, FX, LFO", "Bande et mixer"), 0, "T1 à T4.", PRESETS),
            qz("Preset aléatoire ?", listOf("shift + drop", "shift + help"), 0, "Après chargement.", PRESETS),
            qz("Créer un dossier ?", listOf("shift + T1 dans le navigateur", "lift + drop"), 0, "Dossier user.", SAVE),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 2 — Synthé
    // ---------------------------------------------------------------------
    private const val M2 = "op1f-m2"

    private val m2l1 = ctLesson(
        M2, 1, "Changer de moteur de synthèse", listOf(MOTEURS), 10,
        objective = "Choisir le moteur de synthèse d'un son.",
        explanation = "L'OP-1 field a plusieurs moteurs de synthèse (dont cluster, dimension, dr wave, fm, pulse, sampler, string, vocoder). " +
            "synth (touche avec une onde), puis T1 affiche le moteur. shift + T1 ouvre la liste : encodeur bleu pour parcourir, T1 ou appui " +
            "sur bleu pour confirmer. shift + T1 ne change que le moteur ; shift + 1–8 change les 4 modules.",
        controls = listOf(
            cmd("synth", "Mode synthé.", MOTEURS),
            cmd("shift + T1", "Choisir le moteur.", MOTEURS),
            cmd("Encodeur bleu, T1", "Parcourir, confirmer.", MOTEURS),
        ),
        steps = listOf(
            "synth, puis T1 : le moteur s'affiche.",
            "shift + T1, tourne le bleu jusqu'à fm, confirme avec T1.",
            "Joue et tourne les quatre encodeurs.",
        ),
        expected = "Le même preset sonne avec un autre moteur.",
        exerciseTitle = "Tour des moteurs",
        exercise = listOf("Essaie trois moteurs sur la même mélodie et garde le plus adapté."),
        success = "Tu choisis un moteur selon le son voulu.",
        mistakes = listOf("Utiliser shift + 1–8 en voulant ne changer que le moteur : tout le preset change."),
        quiz = listOf(
            qz("Changer seulement le moteur ?", listOf("shift + T1", "shift + 1–8"), 0, "Les autres modules restent.", MOTEURS),
            qz("Confirmer le moteur ?", listOf("T1 ou appui sur bleu", "help"), 0, "Après avoir parcouru la liste.", MOTEURS),
            qz("Module du moteur ?", listOf("T1", "T4"), 0, "Premier module.", MOTEURS),
            qz("Touche synth ?", listOf("Celle avec une onde", "Celle avec un micro"), 0, "Symbole d'onde.", MOTEURS),
        ),
    )

    private val m2l2 = ctLesson(
        M2, 2, "Enveloppe et effets", listOf(ENVELOPPE, EFFETS), 15,
        objective = "Façonner le son dans le temps et ajouter un effet.",
        explanation = "T2 : enveloppe ADSR (attack, decay, sustain, release) ; shift dans cet écran donne le mode de jeu, le portamento, la " +
            "plage de bend et le volume. T3 : effets stéréo, un seul actif à la fois ; shift + T3 ouvre la liste (bleu pour parcourir, T3 " +
            "pour choisir) ; T3 à nouveau active ou coupe l'effet. Les encodeurs de couleur règlent l'effet. L'effet master du mixer (mixer " +
            "puis T3) traite tout le mix et n'est pas enregistré sur la bande.",
        controls = listOf(
            cmd("T2 (shift = mode de jeu)", "Enveloppe ADSR, portamento.", ENVELOPPE),
            cmd("T3, shift + T3", "Effet, choix de l'effet.", EFFETS),
        ),
        steps = listOf(
            "T2 : attack court, decay court, sustain bas : un son percussif.",
            "shift dans T2 : règle le portamento.",
            "shift + T3 : choisis delay, confirme avec T3.",
            "Règle l'effet avec les encodeurs, coupe-le avec T3.",
        ),
        expected = "Un son court avec un écho.",
        exerciseTitle = "Pluck à écho",
        exercise = listOf("Fais un pluck et ajoute un delay pour une mélodie aérée."),
        success = "Le pluck répète en rythme.",
        mistakes = listOf("Chercher deux effets en même temps sur un son : un seul est actif."),
        quiz = listOf(
            qz("Enveloppe ?", listOf("T2", "T4"), 0, "ADSR.", ENVELOPPE),
            qz("Effets actifs en même temps ?", listOf("Un", "Quatre"), 0, "Un seul à la fois.", EFFETS),
            qz("L'effet master est-il enregistré sur la bande ?", listOf("Non", "Oui"), 0, "Seulement au mixdown.", EFFETS),
            qz("Portamento ?", listOf("shift dans l'écran enveloppe", "T3"), 0, "Réglages de jeu.", ENVELOPPE),
        ),
    )

    private val m2l3 = ctLesson(
        M2, 3, "Le LFO", listOf(LFO), 10,
        objective = "Ajouter du mouvement à un son.",
        explanation = "T4 : le LFO module un paramètre du moteur, de l'enveloppe ou des effets. T4 à nouveau l'active ou le coupe ; shift + T4 " +
            "ouvre la liste des LFO (dont random, element, midi, tremolo, value, velocity). Il marche pareil en synth et en drum. Conseil du " +
            "guide : une modulation légère rend souvent un preset plus vivant.",
        controls = listOf(
            cmd("T4", "Activer / couper le LFO.", LFO),
            cmd("shift + T4", "Choisir le type de LFO.", LFO),
        ),
        steps = listOf(
            "T4, puis shift + T4 : choisis random.",
            "Règle vitesse, quantité et destination avec les encodeurs.",
            "Coupe et rallume avec T4 pour comparer.",
        ),
        expected = "Le son bouge doucement tout seul.",
        exerciseTitle = "Nappe vivante",
        exercise = listOf("Ajoute un LFO léger à une nappe tenue."),
        success = "La nappe évolue sans devenir instable.",
        style = "Tribe : une nappe qui respire sous la boucle.",
        mistakes = listOf("Modulation trop forte : le guide conseille souvent une modulation subtile."),
        quiz = listOf(
            qz("Module du LFO ?", listOf("T4", "T2"), 0, "Quatrième module.", LFO),
            qz("Choisir le type ?", listOf("shift + T4", "shift + T1"), 0, "Liste des LFO.", LFO),
            qz("Que peut moduler le LFO ?", listOf("Moteur, enveloppe ou effets", "La bande"), 0, "Tout paramètre de ces modules.", LFO),
            qz("Le LFO marche-t-il en drum ?", listOf("Oui", "Non"), 0, "Comme en synth.", LFO),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 3 — Batterie et échantillonnage
    // ---------------------------------------------------------------------
    private const val M3 = "op1f-m3"

    private val m3l1 = ctLesson(
        M3, 1, "Le mode drum", listOf(DRUM), 10,
        objective = "Charger et jouer un kit de batterie.",
        explanation = "drum active le mode batterie : un échantillonneur de batterie stéréo (drum) et un synthé de batterie à deux oscillateurs " +
            "(dbox). Comme en synth, T1–T4 (moteur, enveloppe, effets, LFO) et les touches 1–8 sont actifs. shift + 1–8 ouvre la liste des " +
            "moteurs et des kits ; on confirme avec la touche 1–8.",
        controls = listOf(
            cmd("drum", "Mode batterie.", DRUM),
            cmd("shift + 1–8", "Charger un kit.", DRUM),
            cmd("Clavier", "Jouer les sons du kit.", DRUM),
        ),
        steps = listOf(
            "Appuie sur drum et joue le clavier.",
            "shift + 2 : choisis un autre kit, confirme avec 2.",
            "Essaie un kit dbox.",
        ),
        expected = "Deux kits différents rangés sur 1 et 2.",
        exerciseTitle = "Kit hardtek",
        exercise = listOf("Trouve un kit avec un kick percutant et range-le sur la touche 1."),
        success = "Ton kit principal est toujours sur 1.",
        style = "Hardtek : un kick percutant sous la main.",
        mistakes = listOf("Chercher des notes de mélodie : en drum, les touches jouent des sons de batterie."),
        quiz = listOf(
            qz("Moteurs de batterie ?", listOf("drum (sampler) et dbox (synthé)", "fm et string"), 0, "Deux moteurs.", DRUM),
            qz("Charger un kit ?", listOf("shift + 1–8", "T1"), 0, "Puis confirmer avec 1–8.", DRUM),
            qz("dbox est…", listOf("Un synthé de batterie à deux oscillateurs", "Un échantillonneur"), 0, "Dual oscillator.", DRUM),
            qz("T3 en drum ?", listOf("Effets", "Piste 3"), 0, "Comme en synth.", DRUM),
        ),
    )

    private val m3l2 = ctLesson(
        M3, 2, "Construire un kit (drum sampler)", listOf(DRUM_SAMPLER), 15,
        objective = "Découper un échantillon sur les touches du clavier.",
        explanation = "Dans le drum sampler, chaque touche joue une portion de l'échantillon : appuyer sur une touche, régler le point d'entrée, " +
            "puis le point de sortie, et appuyer à nouveau sur la même touche pour confirmer. Les encodeurs règlent accord, entrée, sortie, " +
            "mode de jeu ; avec shift, accord fin, zoom, sens, pan, fondu, gain. Taper ocre ou gris zoome pour placer les points au plus près. " +
            "Canaux stéréo ou empilés.",
        controls = listOf(
            cmd("Touche du clavier", "Choisir le son à régler.", DRUM_SAMPLER),
            cmd("Encodeurs : in, out, tuning", "Points d'entrée / sortie, accord.", DRUM_SAMPLER),
            cmd("Appui ocre / gris", "Zoom.", DRUM_SAMPLER),
        ),
        steps = listOf(
            "Dans un kit drum sampler, appuie sur une touche.",
            "Règle le point d'entrée, puis de sortie.",
            "Appuie à nouveau sur la touche pour confirmer.",
            "Zoome pour caler l'attaque exactement.",
        ),
        expected = "La touche joue exactement la partie voulue.",
        exerciseTitle = "Break découpé",
        exercise = listOf("Découpe un break sur 6 touches et rejoue-les dans un autre ordre."),
        success = "Un nouveau rythme avec le même break.",
        style = "Tribe : un break découpé et rejoué autrement.",
        mistakes = listOf("Oublier de réappuyer sur la touche : le réglage n'est pas confirmé."),
        quiz = listOf(
            qz("Confirmer un découpage ?", listOf("Réappuyer sur la même touche", "help"), 0, "Après le point de sortie.", DRUM_SAMPLER),
            qz("Zoomer ?", listOf("Appui sur ocre ou gris", "shift + T1"), 0, "Taper à nouveau pour sortir.", DRUM_SAMPLER),
            qz("Pan et gain ?", listOf("Avec shift", "Dans le mixer seulement"), 0, "Paramètres décalés.", DRUM_SAMPLER),
            qz("Canaux ?", listOf("Stéréo ou empilés", "Mono seulement"), 0, "Stereo L+R ou A+B.", DRUM_SAMPLER),
        ),
    )

    private val m3l3 = ctLesson(
        M3, 3, "Échantillonner", listOf(SAMPLER, INPUT), 15,
        objective = "Enregistrer un son dans le sampler.",
        explanation = "Le synth sampler est un échantillonneur chromatique stéréo avec boucle. Pour échantillonner : moteur sampler choisi, touche " +
            "input (micro), choisir la source ; gris = seuil, orange = gain. Maintenir une touche et faire le son, relâcher, puis jouer le " +
            "clavier et ajuster début et fin. shift + input ouvre l'écran d'entrée : micro / ligne, radio FM, audio USB, ou rééchantillonnage " +
            "de la sortie (ear).",
        controls = listOf(
            cmd("input", "Activer l'entrée / échantillonner.", INPUT),
            cmd("shift + input", "Choisir la source.", INPUT),
            cmd("Gris / orange", "Seuil / gain.", INPUT),
        ),
        steps = listOf(
            "shift + T1 : choisis le moteur sampler.",
            "shift + input : source line in (un synthé branché).",
            "Règle seuil et gain, maintiens une touche et joue ton synthé.",
            "Relâche, joue le clavier, ajuste début et fin.",
        ),
        expected = "Ton son externe joué chromatiquement.",
        exerciseTitle = "Rééchantillonner",
        exercise = listOf("Choisis la source « output to input » et rééchantillonne une boucle avec effet."),
        success = "Un nouveau son créé à partir de ton propre mix.",
        style = "Mentalcore : rééchantillonner pour dégrader les sons.",
        mistakes = listOf("Seuil trop haut : l'enregistrement ne démarre pas."),
        quiz = listOf(
            qz("Écran d'entrée ?", listOf("shift + input", "shift + tape"), 0, "Choix de la source.", INPUT),
            qz("Rééchantillonner la sortie ?", listOf("Source « output to input »", "Radio FM"), 0, "ear.", INPUT),
            qz("Seuil d'enregistrement ?", listOf("Encodeur gris", "Encodeur bleu"), 0, "Orange = gain.", INPUT),
            qz("Le synth sampler est…", listOf("Chromatique et stéréo", "Mono, une note"), 0, "Avec boucle.", SAMPLER),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 4 — La bande
    // ---------------------------------------------------------------------
    private const val M4 = "op1f-m4"

    private val m4l1 = ctLesson(
        M4, 1, "Enregistrer sur la bande", listOf(TAPE, TAPE_SHIFT), 15,
        objective = "Enregistrer et superposer des pistes.",
        explanation = "Chaque bande a 4 pistes stéréo et 6 minutes par piste (vitesse normale) ; 8 bandes en mémoire. Choisir un son, puis tape : " +
            "T1–T4 = pistes. Choisir une piste, trouver un endroit vide, régler niveau et pan avec l'orange, maintenir record et jouer, stop " +
            "pour finir, rembobiner et écouter. shift + record arme l'enregistrement ; play donne alors un décompte au tempo.",
        controls = listOf(
            cmd("tape, T1–T4", "Mode bande, pistes.", TAPE),
            cmd("record (maintenu), stop, play", "Enregistrer, arrêter, lire.", TAPE),
            cmd("Flèches", "Rembobiner / avancer.", TAPE_EDIT),
            cmd("shift + record", "Armer avec décompte.", TAPE_SHIFT),
        ),
        steps = listOf(
            "Choisis un kit en drum, puis tape.",
            "T1, maintiens record et joue 4 mesures, stop.",
            "Rembobine, choisis une basse en synth, revient en tape, T2, enregistre par-dessus.",
        ),
        expected = "Deux pistes superposées sur la bande.",
        exerciseTitle = "Boucle 4 pistes",
        exercise = listOf("Enregistre batterie, basse, nappe et percussions sur les 4 pistes."),
        success = "Une boucle complète sur la bande.",
        mistakes = listOf("Oublier de choisir le son avant d'entrer en mode tape."),
        quiz = listOf(
            qz("Durée par piste ?", listOf("6 minutes", "1 minute"), 0, "À vitesse normale.", TAPE),
            qz("Bandes en mémoire ?", listOf("8", "2"), 0, "Changement instantané.", TAPE),
            qz("Enregistrer ?", listOf("Maintenir record et jouer", "shift + stop"), 0, "stop pour finir.", TAPE),
            qz("Décompte avant enregistrement ?", listOf("shift + record puis play", "help + record"), 0, "Au tempo actuel.", TAPE_SHIFT),
        ),
    )

    private val m4l2 = ctLesson(
        M4, 2, "Éditer la bande", listOf(TAPE_EDIT, TAPE_SHIFT), 15,
        objective = "Couper, déplacer et annuler sur la bande.",
        explanation = "lift prend (ou efface) une prise, drop la replace à un autre endroit, split coupe une prise. shift + flèches : annuler / " +
            "rétablir. shift + lift : lift all (les 4 pistes dans une boucle active) ; merge drop les dépose fusionnées sur une piste ; join " +
            "réunit deux prises voisines d'une piste. Une grille de bande au tempo (de 1 mesure à la croche) aide à caler les prises.",
        controls = listOf(
            cmd("lift, drop, split", "Prendre, poser, couper.", TAPE_EDIT),
            cmd("shift + flèches", "Annuler / rétablir.", TAPE_EDIT),
            cmd("lift all, merge drop, join", "Éditions avancées.", TAPE_SHIFT),
        ),
        steps = listOf(
            "Place-toi sur une prise, appuie sur lift.",
            "Déplace-toi et appuie sur drop : la prise est copiée.",
            "Coupe une prise avec split.",
            "Annule la dernière action.",
        ),
        expected = "Une prise copiée et une autre coupée.",
        exerciseTitle = "Arrangement",
        exercise = listOf("Copie ta boucle de batterie sur 16 mesures avec lift et drop."),
        success = "Le morceau s'allonge sans réenregistrer.",
        mistakes = listOf("Appuyer sur lift en croyant copier : la prise est retirée de la bande, il faut drop pour la reposer."),
        quiz = listOf(
            qz("Couper une prise ?", listOf("split", "drop"), 0, "Divise la prise.", TAPE_EDIT),
            qz("Reposer une prise ?", listOf("drop", "lift"), 0, "Dernière prise en mémoire.", TAPE_EDIT),
            qz("Fusionner 4 pistes sur une ?", listOf("lift all puis merge drop", "join"), 0, "Dans une boucle active.", TAPE_SHIFT),
            qz("Réunir deux prises voisines ?", listOf("join", "split"), 0, "Même piste.", TAPE_SHIFT),
        ),
    )

    private val m4l3 = ctLesson(
        M4, 3, "Tape tricks et styles de bande", listOf(TAPE_EDIT, TAPE_STYLES), 15,
        objective = "Jouer la bande comme un instrument.",
        explanation = "En mode tape, les touches 1–8 deviennent des tape tricks : 1 point d'entrée de boucle, 2 point de sortie, 3 boucle on/off, " +
            "4 break (arrête la bande), 5 reverse, 6 chop (répétition calée au tempo), 7 et 8 mémoires de paramètres. Styles de bande : " +
            "studio 4-track, vintage 4-track, porta 4-track et disk mini, chacun avec sa couleur ; jusqu'à 8 bandes de 6 minutes et 4 pistes " +
            "stéréo.",
        controls = listOf(
            cmd("1, 2, 3", "Boucle : entrée, sortie, on/off.", TAPE_EDIT),
            cmd("4, 5, 6", "Break, reverse, chop.", TAPE_EDIT),
            cmd("Navigateur de bandes", "Styles, nouvelle bande.", TAPE_STYLES),
        ),
        steps = listOf(
            "Pendant la lecture, pose une boucle avec 1 et 2, active-la avec 3.",
            "Essaie 6 (chop) en rythme.",
            "Appuie sur 4 pour un break, puis relance.",
            "Essaie 5 (reverse).",
        ),
        expected = "Tu transformes la bande en direct.",
        exerciseTitle = "Live à la bande",
        exercise = listOf("Joue ta boucle avec chop et reverse comme transitions."),
        success = "Les transitions tombent en rythme.",
        style = "Free party : boucles cassées et inversées en direct.",
        mistakes = listOf("Chercher les presets sur 1–8 en mode tape : ce sont des tape tricks."),
        quiz = listOf(
            qz("Touche 6 en mode tape ?", listOf("chop", "reverse"), 0, "Répétition calée au tempo.", TAPE_EDIT),
            qz("Touche 4 ?", listOf("break (arrête la bande)", "boucle"), 0, "Arrêt.", TAPE_EDIT),
            qz("Styles de bande ?", listOf("4", "8"), 0, "studio, vintage, porta, disk mini.", TAPE_STYLES),
            qz("Activer la boucle ?", listOf("Touche 3", "Touche 8"), 0, "Après 1 et 2.", TAPE_EDIT),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 5 — Mixage, tempo et séquenceurs
    // ---------------------------------------------------------------------
    private const val M5 = "op1f-m5"

    private val m5l1 = ctLesson(
        M5, 1, "Le mixer", listOf(MIXER, EQ, SOUND_PATH), 15,
        objective = "Mixer les 4 pistes de la bande.",
        explanation = "mixer, puis T1 : niveaux et pan des 4 pistes ; shift + T1–T4 coupe une piste. T2 : égaliseur 3 bandes master. T3 : effet " +
            "master sur le mix (non enregistré sur la bande, mais dans le mixdown). T4 : sortie (volume et drive). shift + mixer ouvre la " +
            "page sound path : le trajet du son du clavier ou de la bande jusqu'à la sortie ; un symbole d'alerte signale un niveau à zéro.",
        controls = listOf(
            cmd("mixer : T1", "Niveaux, pan, mute (shift + T).", MIXER),
            cmd("T2, T3, T4", "EQ, effet master, sortie.", MIXER),
            cmd("shift + mixer", "Sound path.", SOUND_PATH),
        ),
        steps = listOf(
            "mixer, T1 : équilibre les 4 pistes.",
            "Coupe la piste 1 avec shift + T1, puis rétablis.",
            "T2 : retire un peu de grave.",
            "Vérifie le trajet avec shift + mixer.",
        ),
        expected = "Un mix équilibré des 4 pistes.",
        exerciseTitle = "Break au mixer",
        exercise = listOf("Coupe la piste de batterie 4 mesures puis rétablis-la."),
        success = "Le retour tombe pile.",
        style = "Tribe / hardtek : break sans kick puis retour.",
        mistakes = listOf("Plus de son : un niveau critique peut être à zéro (voir sound path)."),
        quiz = listOf(
            qz("Couper une piste ?", listOf("shift + T1–T4 dans le mixer", "lift"), 0, "Page T1.", MIXER),
            qz("Égaliseur master ?", listOf("T2 du mixer", "T2 du synth"), 0, "3 bandes.", MIXER),
            qz("Sound path ?", listOf("shift + mixer", "shift + help"), 0, "Trajet du son.", SOUND_PATH),
            qz("Volume de sortie et drive ?", listOf("T4 du mixer", "Encodeur bleu en synth"), 0, "Master out.", MIXER),
        ),
    )

    private val m5l2 = ctLesson(
        M5, 2, "Tempo et synchronisation", listOf(TEMPO, SYNC), 10,
        objective = "Régler le tempo et se caler sur d'autres machines.",
        explanation = "L'écran tempo règle les BPM pour les séquenceurs, les LFO calés et la bande : bleu ou tap tempo. Ocre : mode de sync — " +
            "free (horloge interne, rien d'envoyé ni reçu), beat match (horloge maître interne), midi sync (horloge externe reçue), PO sync / " +
            "1/16 sync (pour Pocket Operators et modulaires ; la sortie devient deux mono : gauche = sync, droite = audio). Gris = vitesse de " +
            "bande ; orange = métronome. Les flèches gauche / droite poussent le tempo pour se recaler à l'oreille.",
        controls = listOf(
            cmd("Bleu, tap tempo", "BPM.", TEMPO),
            cmd("Ocre", "Mode de sync.", TEMPO),
            cmd("Orange", "Métronome.", TEMPO),
            cmd("Flèches", "Pousser le tempo.", TEMPO),
        ),
        steps = listOf(
            "Ouvre l'écran tempo, règle 160 BPM.",
            "Mode midi sync et branche l'horloge de ta machine maîtresse.",
            "Active le métronome avec l'orange.",
        ),
        expected = "L'OP-1 field suit le tempo de ton set.",
        exerciseTitle = "Calage à l'oreille",
        exercise = listOf("En mode free, cale-toi sur un morceau en poussant le tempo avec les flèches."),
        success = "Les deux restent ensemble.",
        mistakes = listOf("PO sync sans le savoir : la sortie gauche devient un signal de synchro."),
        quiz = listOf(
            qz("Recevoir une horloge externe ?", listOf("midi sync", "free"), 0, "Horloge externe reçue.", SYNC),
            qz("PO sync : sortie gauche = …", listOf("Signal de sync", "Audio"), 0, "Droite = audio.", SYNC),
            qz("Pousser le tempo ?", listOf("Flèches gauche / droite", "help"), 0, "Jam non synchronisé.", TEMPO),
            qz("Métronome ?", listOf("Encodeur orange", "Encodeur bleu"), 0, "Tourner ou taper.", TEMPO),
        ),
    )

    private val m5l3 = ctLesson(
        M5, 3, "Les séquenceurs", listOf(SEQ), 10,
        objective = "Programmer des notes avec un séquenceur.",
        explanation = "L'OP-1 field a sept séquenceurs (dont arpeggio, finger, sketch). Synth et drum ont chacun leur mémoire de séquenceur. " +
            "Différence avec la bande : la bande enregistre de l'audio, le séquenceur des notes, qu'on peut rejouer avec un autre son. " +
            "shift + touche séquenceur ouvre la liste (bleu pour choisir, appui ou touche séquenceur pour activer) ; la touche séquenceur seule " +
            "l'active ou le coupe.",
        controls = listOf(
            cmd("shift + séquenceur", "Choisir un séquenceur.", SEQ),
            cmd("Touche séquenceur", "Activer / couper.", SEQ),
        ),
        steps = listOf(
            "En drum, shift + séquenceur, choisis un séquenceur, active-le.",
            "Joue des notes : le séquenceur les répète.",
            "Change de kit : les mêmes notes jouent un autre son.",
        ),
        expected = "Un motif de notes qui survit au changement de son.",
        exerciseTitle = "Motif puis bande",
        exercise = listOf("Programme un motif de batterie et enregistre-le sur une piste de la bande."),
        success = "Le motif est sur la bande, prêt à être arrangé.",
        mistakes = listOf("Croire que le séquenceur enregistre de l'audio : il garde des notes."),
        quiz = listOf(
            qz("Nombre de séquenceurs ?", listOf("7", "2"), 0, "Originaux.", SEQ),
            qz("Bande ou séquenceur : qui garde de l'audio ?", listOf("La bande", "Le séquenceur"), 0, "Le séquenceur garde des notes.", SEQ),
            qz("Choisir un séquenceur ?", listOf("shift + touche séquenceur", "shift + T4"), 0, "Puis bleu.", SEQ),
            qz("Synth et drum partagent-ils le séquenceur ?", listOf("Non, mémoire séparée", "Oui"), 0, "Chacun sa mémoire.", SEQ),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(M1, 1, "Prise en main", "Façade, charge, modes, T1–T4, encodeurs, presets.",
            "Se repérer sur l'OP-1 field.", "Un OP-1 field chargé, des sons rangés sur 1–8.", listOf(m1l1, m1l2, m1l3)),
        CourseModule(M2, 2, "Le synthé", "Moteurs, enveloppe, effets, LFO.",
            "Créer et modeler un son.", "Des sons vivants pour les nappes et les leads.", listOf(m2l1, m2l2, m2l3)),
        CourseModule(M3, 3, "Batterie et échantillonnage", "Mode drum, découpe d'échantillons, sampler.",
            "Faire tes kits et tes samples.", "Breaks découpés et sons rééchantillonnés.", listOf(m3l1, m3l2, m3l3)),
        CourseModule(M4, 4, "La bande", "Enregistrement, édition, tape tricks, styles de bande.",
            "Construire un morceau à la bande.", "Boucles cassées, inversées et coupées en direct.", listOf(m4l1, m4l2, m4l3)),
        CourseModule(M5, 5, "Mixage, tempo et séquenceurs", "Mixer, sound path, tempo, sync, séquenceurs.",
            "Finaliser et intégrer au set.", "Synchronisé, l'OP-1 field complète le live.", listOf(m5l1, m5l2, m5l3)),
    )
}
