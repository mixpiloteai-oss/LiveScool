package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** Take 5 — modules 1 à 4 : prise en main, oscillateurs et mélangeur, filtre, enveloppes. */
internal object Take5ModulesA {

    // Références du guide (pages imprimées ; page du PDF = page imprimée + 9)
    private val ARRIERE = tk5("Rear Panel Connections", "2–3")
    private val SETUP = tk5("Setting Up the Take 5", "3")
    private val CAL = tk5("Tune the Oscillators and Filters", "4")
    private val PROGRAMMES = tk5("Listen to the Factory Sounds", "4–5")
    private val ECRAN = tk5("Using the Main Display", "5")
    private val BASIC = tk5("Creating a Program from Scratch", "6")
    private val SAVE = tk5("Saving a Program, Canceling Save", "6–7")
    private val LIVE_PANEL = tk5("Live Panel Mode", "7")
    private val GLOBAL = tk5("Global Settings", "10–11")
    private val POT_MODE = tk5("Global Settings, Pot Mode", "11")
    private val OSC_ECOUTE = tk5("Oscillators, To listen to the oscillators", "16–18")
    private val OSC_PARAM = tk5("Oscillator Parameters", "18–19")
    private val OSC_PLUS = tk5("Additional Oscillator Parameters", "19–20")
    private val MIXER = tk5("Mixer, Mixer Parameters", "20–21")
    private val FILTRE = tk5("Filter, To hear the effect of the filter", "21–22")
    private val FILTRE_PARAM = tk5("Filter Parameters, Key>LPF Freq", "22–23")
    private val ENV = tk5("Envelopes", "23–24")
    private val ROUTING = tk5("Using the Env Routing Button", "24")
    private val ENV_FILTRE = tk5("About the Filter Envelope", "24–26")
    private val ENV_PARAM = tk5("Envelope Parameters, Additional Envelope Parameters", "26–27")
    private val ENV_AMP = tk5("About the Amp Envelope", "28–29")
    private val AUX = tk5("Auxiliary Envelope", "29–30")
    private val BASSE = tk5("Synthesis 101: Synth Bass", "57–60")

    // ---------------------------------------------------------------------
    // Module 1 — Prise en main
    // ---------------------------------------------------------------------
    private const val M1 = "tk5-m1"

    private val m1l1 = ctLesson(
        M1, 1, "Brancher, allumer, calibrer", listOf(ARRIERE, SETUP, CAL), 15,
        objective = "Installer le Take 5, le relier à ton écoute et lancer la calibration des oscillateurs et des filtres.",
        explanation = "À l'arrière : interrupteur Power On/Off, prise secteur IEC (100 à 240 V), USB (MIDI, sans pilote), MIDI In/Out/Thru, " +
            "prise Footswitch (sustain ou maintien de l'arpégiateur), prise Pedal (pédale d'expression TRS), deux sorties audio " +
            "asymétriques 6,35 mm et un casque stéréo. Le guide branche d'abord le secteur et les pédales, allume le Take 5, relie " +
            "les sorties gauche et droite, monte l'ampli puis le volume du Take 5. À la première utilisation, on lance la calibration " +
            "(Global > Cal Voices > Write) ; on peut la refaire les jours suivants, le Take 5 apprend la plage de températures. " +
            "Ne jamais l'éteindre pendant la calibration.",
        controls = listOf(
            cmd("Power On/Off", "Interrupteur à bascule.", ARRIERE),
            cmd("Audio Outputs L/R", "Sorties 6,35 mm asymétriques.", ARRIERE),
            cmd("Headphones", "Casque, volume réglé par le bouton volume.", ARRIERE),
            cmd("Global > Cal Voices", "Calibration des VCO et des filtres.", CAL),
        ),
        steps = listOf(
            "Branche le câble secteur et, si tu en as, la pédale de sustain (Footswitch) et la pédale d'expression (Pedal).",
            "Allume le Take 5, puis relie les sorties gauche et droite à ta table ou à tes enceintes.",
            "Monte le volume de l'ampli, puis celui du Take 5.",
            "Appuie sur global, choisis Cal Voices avec le bouton select, puis appuie sur write ; attends la fin sans éteindre.",
        ),
        expected = "Le Take 5 a fini sa calibration et ses commandes répondent de nouveau.",
        exerciseTitle = "Calibration de début de session",
        exercise = listOf(
            "Les premiers jours, relance la calibration une fois par session.",
            "Note si l'accord te paraît plus stable d'un jour à l'autre.",
        ),
        success = "La calibration se lance et se termine sans erreur.",
        mistakes = listOf(
            "Éteindre pendant la calibration : le guide demande de ne pas couper l'alimentation.",
            "Monter le volume du Take 5 avant celui de l'ampli : le guide fait l'inverse.",
        ),
        quiz = listOf(
            qz("Quel type de sorties audio a le Take 5 ?", listOf("XLR symétriques", "Jacks 6,35 mm asymétriques", "Mini-jack"), 1,
                "Deux sorties 6,35 mm asymétriques, gauche et droite.", ARRIERE),
            qz("Comment lancer la calibration ?", listOf("Global > Cal Voices > Write", "Maintenir Hold", "Bank + 16"), 0,
                "Bouton global, Cal Voices avec select, puis write.", CAL),
            qz("Pourquoi refaire la calibration les premiers jours ?", listOf("Le Take 5 apprend la plage de températures", "Pour effacer les sons"), 0,
                "Il apprend la plage de températures et reste accordé dans cette plage.", CAL),
            qz("À quoi sert la prise USB ?", listOf("MIDI avec un ordinateur", "Sortie audio", "Alimentation"), 0,
                "MIDI bidirectionnel, classe compatible, sans pilote.", ARRIERE),
        ),
    )

    private val m1l2 = ctLesson(
        M1, 2, "Choisir et sauvegarder un programme", listOf(PROGRAMMES, SAVE), 15,
        objective = "Naviguer dans les banques, écouter les sons d'usine et sauvegarder ton propre son.",
        explanation = "Le Take 5 contient une banque Factory, protégée, et une banque User, modifiable (identiques à la livraison). " +
            "Bouton factory allumé = programmes d'usine ; éteint = programmes User. Pour choisir un programme : maintenir bank et " +
            "appuyer sur un sélecteur 1–16 pour la centaine, relâcher, puis appuyer sur un sélecteur 1–16. La plupart des programmes " +
            "d'usine ont une séquence : play la fait entendre. Sauvegarder : write (la LED clignote), puis write encore pour le même " +
            "emplacement ; ou choisir un autre emplacement avec bank + sélecteurs avant le second write. Un programme Factory modifié " +
            "est sauvegardé dans la banque User. Pour annuler : program settings pendant que write clignote.",
        controls = listOf(
            cmd("factory", "Banque d'usine (allumé) ou User (éteint).", PROGRAMMES),
            cmd("bank + sélecteurs 1–16", "Centaine puis programme.", PROGRAMMES),
            cmd("write", "Sauvegarde (deux appuis).", SAVE),
            cmd("program settings", "Annule une sauvegarde en cours.", SAVE),
        ),
        steps = listOf(
            "Maintiens bank, appuie sur 1, relâche, puis appuie sur 5 : tu es sur un programme de la première centaine.",
            "Appuie sur play du séquenceur pour entendre la séquence associée, puis à nouveau pour l'arrêter.",
            "Tourne un bouton du filtre, puis appuie deux fois sur write pour sauvegarder.",
            "Recommence une sauvegarde et annule-la avec program settings pendant que write clignote.",
        ),
        expected = "Tu changes de programme, tu sauvegardes et tu sais annuler.",
        exerciseTitle = "Ta banque de live",
        exercise = listOf(
            "Choisis trois sons d'usine qui te servent (une basse, un lead, une nappe).",
            "Sauvegarde-les à la suite dans la banque User.",
        ),
        success = "Tes trois sons se rappellent avec trois sélecteurs voisins.",
        mistakes = listOf(
            "Chercher à écraser un programme Factory : il est protégé, la sauvegarde va dans la banque User.",
            "Appuyer une seule fois sur write : la LED clignote mais rien n'est encore sauvegardé.",
        ),
        quiz = listOf(
            qz("Que se passe-t-il si tu sauvegardes un programme Factory modifié ?", listOf("Il écrase l'original", "Il va dans la banque User"), 1,
                "Les programmes Factory ne peuvent pas être écrasés.", SAVE),
            qz("Comment entendre la séquence d'un programme d'usine ?", listOf("play du séquenceur", "Hold", "Unison"), 0,
                "La plupart des programmes d'usine ont une séquence associée.", PROGRAMMES),
            qz("Combien d'appuis sur write pour sauvegarder au même endroit ?", listOf("Un", "Deux"), 1,
                "Premier appui : la LED clignote ; second appui : sauvegarde.", SAVE),
            qz("Comment annuler une sauvegarde ?", listOf("program settings pendant que write clignote", "Éteindre"), 0,
                "program settings arrête le clignotement et annule.", SAVE),
        ),
    )

    private val m1l3 = ctLesson(
        M1, 3, "Basic Program, Live Panel et écran", listOf(BASIC, LIVE_PANEL, ECRAN, POT_MODE), 15,
        objective = "Repartir d'un son neutre, entendre la façade telle quelle et comprendre le comportement des potentiomètres.",
        explanation = "Le Basic Program est un point de départ : un oscillateur en dent de scie, filtre, enveloppes et molettes de base. " +
            "On le rappelle par Global > Basic Program > Write. En mode Live Panel (bouton preset éteint), le son suit exactement la " +
            "position des boutons : idéal pour apprendre. L'écran affiche les menus Global et Program : select fait défiler les " +
            "paramètres, value règle la valeur. Le réglage global Pot Mode décide comment réagissent les potentiomètres (boutons " +
            "striés à course limitée) : Relative (relatif à la valeur mémorisée), Passthru (rien tant qu'on ne passe pas par la " +
            "valeur mémorisée), Jump (la valeur saute à la position du bouton).",
        controls = listOf(
            cmd("Global > Basic Program", "Son de départ neutre.", BASIC),
            cmd("preset", "Éteint = Live Panel.", LIVE_PANEL),
            cmd("select / value", "Parcourir et régler les menus.", ECRAN),
            cmd("Global > Pot Mode", "Relative, Passthru ou Jump.", POT_MODE),
        ),
        steps = listOf(
            "Appuie sur global, choisis Basic Program avec select, puis write.",
            "Joue une note : une simple dent de scie filtrée.",
            "Éteins preset : le son correspond maintenant à la position de tous les boutons.",
            "Dans le menu Global, choisis le Pot Mode qui te convient, puis rallume preset.",
        ),
        expected = "Tu sais repartir de zéro et tu as choisi ton Pot Mode.",
        exerciseTitle = "Du neutre au son",
        exercise = listOf(
            "Rappelle le Basic Program, puis modifie seulement Cutoff et Resonance.",
            "Sauvegarde ce son dans un emplacement libre de la banque User.",
        ),
        success = "Ton premier programme fait maison est sauvegardé.",
        mistakes = listOf(
            "Changer de programme en Live Panel et s'étonner que le son ne change pas : c'est le principe du mode.",
            "Croire qu'un bouton est cassé en mode Passthru : il n'agit qu'après avoir croisé la valeur mémorisée.",
        ),
        quiz = listOf(
            qz("Que contient le Basic Program ?", listOf("Un oscillateur en dent de scie et des réglages simples", "Tous les effets activés"), 0,
                "Un oscillateur dent de scie, filtre, enveloppes et molettes de base.", BASIC),
            qz("Comment entrer en mode Live Panel ?", listOf("Éteindre preset", "Maintenir write"), 0,
                "preset éteint : le son suit la façade.", LIVE_PANEL),
            qz("En Pot Mode Jump…", listOf("La valeur saute à la position du bouton", "Le bouton n'agit jamais"), 0,
                "Jump utilise la position absolue du potentiomètre.", POT_MODE),
            qz("Quel bouton règle la valeur dans l'écran ?", listOf("select", "value"), 1,
                "select fait défiler, value règle.", ECRAN),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 2 — Oscillateurs et mélangeur
    // ---------------------------------------------------------------------
    private const val M2 = "tk5-m2"

    private val m2l1 = ctLesson(
        M2, 1, "Octave, Pitch et Shape", listOf(OSC_ECOUTE, OSC_PARAM), 15,
        objective = "Régler la hauteur et la forme d'onde des deux oscillateurs.",
        explanation = "Le Take 5 a deux oscillateurs analogiques (VCO) et un sous-oscillateur. Octave règle la hauteur de base sur 5 " +
            "octaves ; Pitch est un accord fin de ±7 demi-tons, cranté à midi ; Shape passe en continu du sinus à la dent de scie " +
            "puis à l'impulsion, dont la largeur varie (trop étroite, le son « disparaît »). Dans le Basic Program, seul l'oscillateur 1 " +
            "sonne, en dent de scie. Désaccorder légèrement les deux oscillateurs donne du mouvement et de l'épaisseur ; on peut aussi " +
            "les accorder sur un intervalle (tierce, quinte, sixte).",
        controls = listOf(
            cmd("Octave", "Hauteur de base sur 5 octaves.", OSC_PARAM),
            cmd("Pitch", "Accord fin ±7 demi-tons, cranté à midi.", OSC_PARAM),
            cmd("Shape", "Sinus → dent de scie → impulsion.", OSC_PARAM),
            cmd("Osc 2 (Mixer)", "Fait entendre l'oscillateur 2.", OSC_ECOUTE),
        ),
        steps = listOf(
            "Rappelle le Basic Program et tiens une note.",
            "Tourne Shape de l'oscillateur 1 : sinus, dent de scie, impulsion.",
            "Monte Osc 2 dans le mélangeur et donne-lui une autre forme d'onde.",
            "Désaccorde légèrement l'oscillateur 2 avec Pitch, puis essaie une quinte.",
            "Change l'Octave d'un oscillateur et écoute le mélange.",
        ),
        expected = "Deux oscillateurs légèrement désaccordés donnent un son plus épais.",
        exerciseTitle = "Stab en quinte",
        exercise = listOf(
            "Accorde l'oscillateur 2 une quinte au-dessus de l'oscillateur 1, les deux en dent de scie.",
            "Joue des accords courts : c'est un stab.",
        ),
        success = "Un stab plein et large sort de chaque accord.",
        style = "Tribe : des stabs en quinte sur les contretemps.",
        mistakes = listOf(
            "Réduire l'impulsion à l'extrême et croire à une panne : le son peut disparaître.",
            "Oublier que l'oscillateur 2 est à zéro dans le Basic Program.",
        ),
        quiz = listOf(
            qz("Quelle est la plage de Octave ?", listOf("2 octaves", "5 octaves", "10 octaves"), 1,
                "Octave règle la hauteur de base sur 5 octaves.", OSC_PARAM),
            qz("Quelle est la plage de Pitch ?", listOf("±1 demi-ton", "±7 demi-tons", "±2 octaves"), 1,
                "Accord fin de 7 demi-tons vers le haut ou le bas.", OSC_PARAM),
            qz("Quelles formes d'onde donne Shape ?", listOf("Sinus, dent de scie, impulsion", "Triangle seulement"), 0,
                "Sinus, dent de scie et impulsion, en continu.", OSC_PARAM),
            qz("Dans le Basic Program, quel oscillateur sonne ?", listOf("Les deux", "L'oscillateur 1 seul"), 1,
                "Osc 2, Sub et Noise sont à zéro.", OSC_ECOUTE),
        ),
    )

    private val m2l2 = ctLesson(
        M2, 2, "Hard Sync et FM 2-1", listOf(OSC_ECOUTE, OSC_PARAM, MIXER), 15,
        objective = "Créer des leads tranchants avec la synchronisation et des timbres métalliques avec la FM.",
        explanation = "Sync 2-1 force le cycle de l'oscillateur 1 à repartir avec celui de l'oscillateur 2 : formes d'onde complexes, " +
            "surtout quand l'oscillateur synchronisé est plus aigu que le maître. Le guide propose : sync activé sur l'oscillateur 1, " +
            "son Octave à +2, puis tourner son Pitch en tenant une note. FM 2-1, dans le mélangeur, module la fréquence de " +
            "l'oscillateur 1 par l'oscillateur 2 ; le niveau Osc 2 règle la quantité. En changeant l'intervalle de l'oscillateur 2, " +
            "on obtient des harmoniques complexes et des textures métalliques.",
        controls = listOf(
            cmd("sync (Oscillateur 1)", "Synchronisation 2-1.", OSC_PARAM),
            cmd("Pitch (Oscillateur 1)", "Avec sync, balaie le timbre.", OSC_ECOUTE),
            cmd("FM 2-1", "FM de l'oscillateur 2 vers l'oscillateur 1.", MIXER),
            cmd("Osc 2 (Mixer)", "Quantité de FM quand FM 2-1 est actif.", MIXER),
        ),
        steps = listOf(
            "Oscillateurs 1 et 2 audibles, active sync sur l'oscillateur 1 et mets son Octave sur +2.",
            "Tiens une note et tourne Pitch de l'oscillateur 1 : le son de sync classique.",
            "Désactive sync, active FM 2-1 et monte progressivement Osc 2.",
            "Change l'intervalle de l'oscillateur 2 et écoute le timbre devenir métallique.",
        ),
        expected = "Tu entends le balayage de sync, puis des sons métalliques en FM.",
        exerciseTitle = "Lead mentalcore",
        exercise = listOf(
            "Fabrique un lead en sync, oscillateur 1 deux octaves plus haut.",
            "Balaie Pitch pendant une phrase de huit mesures.",
        ),
        success = "Le lead change de timbre sans changer de note.",
        style = "Mentalcore : leads synchronisés agressifs.",
        mistakes = listOf(
            "Laisser l'oscillateur synchronisé plus grave que le maître : l'effet est faible.",
            "Monter Osc 2 en croyant monter son volume alors que FM 2-1 est actif : il règle la quantité de FM.",
        ),
        quiz = listOf(
            qz("Quand le sync est-il le plus riche ?", listOf("Oscillateur synchronisé plus aigu que le maître", "Plus grave"), 0,
                "Le guide le précise : surtout quand il est réglé plus haut.", OSC_PARAM),
            qz("Que règle Osc 2 quand FM 2-1 est actif ?", listOf("La quantité de FM", "Le tempo"), 0,
                "Le niveau de l'oscillateur 2 contrôle la quantité.", MIXER),
            qz("Quel réglage d'Octave propose le guide pour le sync ?", listOf("+2 sur l'oscillateur 1", "−2 sur l'oscillateur 2"), 0,
                "Sync sur l'oscillateur 1, Octave à +2, puis on tourne Pitch.", OSC_ECOUTE),
            qz("Quel type de son donne la FM 2-1 ?", listOf("Harmoniques complexes, métalliques", "Sinus pur"), 0,
                "Des harmoniques complexes et des textures métalliques.", MIXER),
        ),
    )

    private val m2l3 = ctLesson(
        M2, 3, "Le mélangeur : sous-oscillateur et bruit", listOf(MIXER, OSC_PLUS), 15,
        objective = "Doser les sources et utiliser le sous-oscillateur, le bruit et le contournement du filtre.",
        explanation = "Le mélangeur règle Osc 1, Osc 1 Sub (sous-oscillateur), Osc 2 et Noise. Il faut monter au moins une source pour " +
            "entendre quelque chose (ou faire auto-osciller le filtre avec la résonance au maximum). Noise : à droite de 0, bruit " +
            "blanc ; à gauche, bruit rose. Dans le menu Program, Osc 2 Filter Bypass envoie l'oscillateur 2 directement à " +
            "l'amplificateur, sans filtre, pour des sons en couches ; Osc 1 / Osc 2 Key Follow décident si l'oscillateur suit le clavier.",
        controls = listOf(
            cmd("Osc 1, Osc 1 Sub, Osc 2", "Niveaux des oscillateurs.", MIXER),
            cmd("Noise", "À droite : bruit blanc ; à gauche : bruit rose.", MIXER),
            cmd("Program > Osc 2 Filter Bypass", "Oscillateur 2 sans filtre.", OSC_PLUS),
            cmd("Program > Osc 1 / Osc 2 Key Follow", "Suivi du clavier.", OSC_PLUS),
        ),
        steps = listOf(
            "Depuis le Basic Program, monte Osc 1 Sub : la basse s'épaissit.",
            "Tourne Noise à droite (bruit blanc), puis à gauche (bruit rose).",
            "Monte Osc 2, puis dans program active Osc 2 Filter Bypass et ferme le filtre : l'oscillateur 2 reste brillant.",
            "Remets Osc 2 Filter Bypass sur Off.",
        ),
        expected = "Tu sais épaissir avec le sous-oscillateur et ajouter un souffle.",
        exerciseTitle = "Basse épaisse",
        exercise = listOf(
            "Crée une basse avec Osc 1 et Osc 1 Sub, sans Osc 2.",
            "Ajoute juste un peu de bruit pour l'attaque.",
        ),
        success = "La basse est pleine dans le grave sans être brouillonne.",
        style = "Hardtek : sous-oscillateur pour remplir le bas sous le kick.",
        mistakes = listOf(
            "Toutes les sources à zéro : aucun son.",
            "Oublier Osc 2 Filter Bypass activé : le filtre semble ne plus agir sur tout le son.",
        ),
        quiz = listOf(
            qz("Noise tourné à gauche de 0 donne…", listOf("Du bruit blanc", "Du bruit rose"), 1,
                "À gauche : rose ; à droite : blanc.", MIXER),
            qz("Que fait Osc 2 Filter Bypass ?", listOf("Envoie l'oscillateur 2 sans filtre à l'amplificateur", "Coupe l'oscillateur 2"), 0,
                "L'oscillateur 2 contourne le filtre.", OSC_PLUS),
            qz("Que faut-il pour entendre un son ?", listOf("Au moins une source montée dans le mélangeur", "Rien"), 0,
                "Ou la résonance au maximum pour faire auto-osciller le filtre.", MIXER),
            qz("Où se trouve Key Follow ?", listOf("Menu Program", "Face arrière"), 0,
                "Dans les paramètres supplémentaires du menu Program.", OSC_PLUS),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 3 — Le filtre
    // ---------------------------------------------------------------------
    private const val M3 = "tk5-m3"

    private val m3l1 = ctLesson(
        M3, 1, "Cutoff, Resonance, Drive", listOf(FILTRE, FILTRE_PARAM), 15,
        objective = "Retirer des aigus, accentuer la coupure et saturer l'entrée du filtre.",
        explanation = "Le filtre soustrait des fréquences. Cutoff à gauche coupe les aigus ; complètement à gauche, on n'entend plus rien. " +
            "Resonance accentue une bande étroite autour de la coupure ; très haute, le filtre auto-oscille et produit sa propre note. " +
            "Drive pousse le signal entrant : plus de volume, de saturation et de chaleur (effet plus ou moins subtil selon le son). " +
            "Dans le menu Program, Key>LPF Freq fait s'ouvrir le filtre quand on joue plus aigu ; à 127, il suit au demi-ton.",
        controls = listOf(
            cmd("Cutoff", "Fréquence de coupure.", FILTRE_PARAM),
            cmd("Resonance", "Accentuation autour de la coupure.", FILTRE_PARAM),
            cmd("Drive", "Saturation à l'entrée du filtre.", FILTRE_PARAM),
            cmd("Program > Key>LPF Freq", "Suivi du clavier par le filtre.", FILTRE_PARAM),
        ),
        steps = listOf(
            "Rappelle le Basic Program, tiens une note et tourne Cutoff vers la gauche.",
            "Remets Cutoff à mi-course et monte Resonance à mi-course.",
            "Balaie Cutoff : le balayage résonant classique.",
            "Monte Drive et écoute la saturation.",
        ),
        expected = "Tu entends le balayage résonant et la chaleur du Drive.",
        exerciseTitle = "Montée filtrée",
        exercise = listOf(
            "Sur une nappe, ouvre Cutoff lentement pendant huit mesures.",
            "Ajoute un peu de Resonance pour que la montée se remarque.",
        ),
        success = "La montée est audible et régulière.",
        style = "Tribe / hardtek : la montée filtrée avant le retour du kick.",
        mistakes = listOf(
            "Fermer complètement Cutoff et croire que le synthé est muet.",
            "Pousser Resonance au maximum sans le vouloir : le filtre siffle.",
        ),
        quiz = listOf(
            qz("Cutoff tourné à fond à gauche…", listOf("Plus aucun son", "Son maximum"), 0,
                "Toutes les fréquences sont filtrées.", FILTRE),
            qz("Que fait une très forte Resonance ?", listOf("Auto-oscillation du filtre", "Coupe les oscillateurs"), 0,
                "Le filtre peut générer sa propre hauteur.", FILTRE_PARAM),
            qz("Que fait Drive ?", listOf("Pousse l'entrée du filtre, sature", "Allonge la release"), 0,
                "Plus de volume, de saturation et de chaleur.", FILTRE_PARAM),
            qz("Key>LPF Freq à 127…", listOf("Le filtre suit le clavier au demi-ton", "Le filtre est fermé"), 0,
                "Utile pour jouer le filtre auto-oscillant en notes.", FILTRE_PARAM),
        ),
    )

    private val m3l2 = ctLesson(
        M3, 2, "L'enveloppe de filtre", listOf(ENV_FILTRE, ENV_PARAM), 20,
        objective = "Faire évoluer le timbre de chaque note avec l'enveloppe 1.",
        explanation = "Par défaut, l'enveloppe 1 pilote le filtre. Amount (bipolaire) dose son effet : positif = comportement standard, " +
            "négatif = enveloppe inversée. Attack règle la vitesse d'ouverture, Decay le passage au niveau de Sustain, Sustain le niveau " +
            "tenu, Release la fermeture après relâchement. La release du filtre dépend de celle de l'amplificateur : pour l'entendre, " +
            "la release de l'enveloppe 2 doit être longue. Velocity active fait s'ouvrir le filtre quand on joue fort.",
        controls = listOf(
            cmd("Env 1 Amount", "Quantité d'enveloppe sur la coupure, bipolaire.", ENV_PARAM),
            cmd("Env 1 Attack / Decay / Sustain / Release", "Forme de l'enveloppe.", ENV_PARAM),
            cmd("Env 1 Velocity", "Le jeu fort ouvre davantage.", ENV_PARAM),
        ),
        steps = listOf(
            "Basic Program, Cutoff vers 8 h : le son est presque fermé.",
            "Monte Amount de l'enveloppe 1 vers 15 h et joue.",
            "Frappe une note en tournant Decay, puis Attack.",
            "Tiens une note en réglant Sustain.",
            "Mets la release de l'enveloppe 2 vers 13 h, puis règle la release de l'enveloppe 1.",
        ),
        expected = "Chaque note s'ouvre puis se referme selon l'enveloppe.",
        exerciseTitle = "Pluck",
        exercise = listOf(
            "Fais un son court : Attack à 0, Decay court, Sustain à 0.",
            "Joue un motif rapide de huit notes.",
        ),
        success = "Chaque note claque puis s'éteint vite.",
        style = "Tribecore : plucks courts sur des motifs rapides.",
        mistakes = listOf(
            "Chercher la release du filtre avec une release d'amplificateur courte : on ne l'entend pas.",
            "Laisser Amount à 0 : l'enveloppe ne fait rien.",
        ),
        quiz = listOf(
            qz("Quelle enveloppe pilote le filtre par défaut ?", listOf("Env 1", "Env 2"), 0,
                "L'enveloppe 1 est routée vers le filtre par défaut.", ENV_FILTRE),
            qz("Un Amount négatif…", listOf("Inverse l'enveloppe", "Coupe le filtre"), 0,
                "Amount est bipolaire : négatif = inversé.", ENV_PARAM),
            qz("Pourquoi la release du filtre peut-elle être inaudible ?", listOf("Release de l'amplificateur trop courte", "Velocity active"), 0,
                "Elle agit avec l'enveloppe d'amplitude.", ENV_FILTRE),
            qz("Velocity activée sur l'enveloppe 1…", listOf("Plus on joue fort, plus le filtre s'ouvre", "Le volume baisse"), 0,
                "La vélocité influence la coupure.", ENV_PARAM),
        ),
    )

    private val m3l3 = ctLesson(
        M3, 3, "Recette : la basse synthé", listOf(BASSE), 20,
        objective = "Construire une basse classique à partir du Basic Program.",
        explanation = "La version courte du guide : Basic Program ; Octave de l'oscillateur 1 sur −2 ; Cutoff à zéro ; Amount de " +
            "l'enveloppe 1 au maximum ; Resonance vers 9 h (le « zap ») ; jouer des notes graves. On affine avec l'Amount et le " +
            "Decay de l'enveloppe 1. La dent de scie est un bon départ (harmoniques paires et impaires) ; le carré a seulement les " +
            "impaires ; le sinus n'a pas d'harmoniques et renforce la fondamentale.",
        controls = listOf(
            cmd("Octave (Osc 1)", "−2 pour la basse.", BASSE),
            cmd("Cutoff", "À zéro, l'enveloppe fait tout.", BASSE),
            cmd("Env 1 Amount", "Au maximum.", BASSE),
            cmd("Resonance", "Vers 9 h pour le « zap ».", BASSE),
        ),
        steps = listOf(
            "Rappelle le Basic Program.",
            "Mets l'Octave de l'oscillateur 1 sur −2 et Cutoff à zéro.",
            "Monte Amount de l'enveloppe 1 au maximum et Resonance vers 9 h.",
            "Joue des notes graves, puis ajuste Amount et Decay de l'enveloppe 1.",
        ),
        expected = "Une basse synthé ronde avec un claquement de filtre à chaque note.",
        exerciseTitle = "Basse acide",
        exercise = listOf(
            "Monte Resonance au-delà de 9 h et raccourcis le Decay.",
            "Joue une ligne en doubles croches en tournant Cutoff.",
        ),
        success = "La basse « crie » quand tu ouvres le filtre.",
        style = "Acidcore : résonance haute et decay court, filtre joué à la main.",
        mistakes = listOf(
            "Oublier le Basic Program et partir d'un son chargé d'effets.",
            "Laisser Cutoff ouvert : l'enveloppe n'a plus d'effet audible.",
        ),
        quiz = listOf(
            qz("Quelle Octave pour l'oscillateur 1 dans la recette ?", listOf("+2", "−2", "0"), 1,
                "Octave à −2.", BASSE),
            qz("Où mettre l'Amount de l'enveloppe 1 ?", listOf("À fond à droite", "À zéro"), 0,
                "Au maximum, pour ouvrir le filtre autant que possible.", BASSE),
            qz("Quelle Resonance donne le « zap » ?", listOf("Vers 9 h", "Au maximum"), 0,
                "Vers 9 h dans la recette.", BASSE),
            qz("Quelle onde n'a pas d'harmoniques ?", listOf("Dent de scie", "Carré", "Sinus"), 2,
                "Le sinus renforce la fondamentale.", BASSE),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 4 — Enveloppes
    // ---------------------------------------------------------------------
    private const val M4 = "tk5-m4"

    private val m4l1 = ctLesson(
        M4, 1, "L'enveloppe d'amplitude", listOf(ENV_AMP, ENV), 15,
        objective = "Donner au volume la forme voulue : percussif, tenu ou progressif.",
        explanation = "Les deux enveloppes ont 5 étapes (Delay, Attack, Decay, Sustain, Release). Par défaut, l'enveloppe 2 règle le " +
            "volume. Exemple du guide : vent = attaque lente, long maintien ; caisse claire = attaque sèche, sans sustain, peu de " +
            "decay. Avec Sustain à zéro, seul le Decay s'entend même en tenant la touche.",
        controls = listOf(
            cmd("Env 2 Attack", "Montée du volume.", ENV_AMP),
            cmd("Env 2 Decay / Sustain", "Chute et niveau tenu.", ENV_AMP),
            cmd("Env 2 Release", "Fin du son après relâchement.", ENV_AMP),
        ),
        steps = listOf(
            "Basic Program : tourne Attack de l'enveloppe 2 en frappant une note, puis remets-le à zéro.",
            "Mets Sustain à zéro : le son ne tient plus.",
            "Règle Decay vers 15 h.",
            "Monte Release en frappant des notes.",
        ),
        expected = "Tu passes d'un son percussif à une nappe en quatre réglages.",
        exerciseTitle = "Nappe et stab",
        exercise = listOf(
            "Fais une nappe : Attack et Release longs, Sustain haut.",
            "Transforme-la en stab : Attack à 0, Sustain à 0, Decay court.",
        ),
        success = "Les deux sons sont nettement différents.",
        mistakes = listOf(
            "Confondre Decay et Release : Decay agit pendant que la touche est tenue.",
            "Sustain à zéro avec un Decay très court : le son est presque inaudible.",
        ),
        quiz = listOf(
            qz("Combien d'étapes ont les enveloppes ?", listOf("4", "5 (Delay, Attack, Decay, Sustain, Release)"), 1,
                "Deux enveloppes à 5 étapes.", ENV),
            qz("Quelle enveloppe règle le volume par défaut ?", listOf("Env 1", "Env 2"), 1,
                "L'enveloppe 2 est l'enveloppe d'amplitude par défaut.", ENV_AMP),
            qz("Sustain à zéro, que reste-t-il ?", listOf("Le Decay", "Rien"), 0,
                "Seule la partie Decay s'entend.", ENV_AMP),
            qz("Quelle forme pour une caisse claire ?", listOf("Attaque sèche, pas de sustain", "Attaque lente, long sustain"), 0,
                "C'est l'exemple du guide.", ENV_AMP),
        ),
    )

    private val m4l2 = ctLesson(
        M4, 2, "Env Routing et enveloppe auxiliaire", listOf(ROUTING, AUX), 15,
        objective = "Choisir ce que pilotent les enveloppes et libérer l'enveloppe 1 pour d'autres modulations.",
        explanation = "Le bouton env routing fait défiler les options. Env 1 : Filter (coupure) ou Aux (libre, pour la matrice de " +
            "modulation). Env 2 : Amp (volume), Filter + Amp (coupure et volume) ou Filter + Gate (coupure, volume simplement " +
            "ouvert/fermé à chaque touche). Aux n'est disponible que si Env 2 est sur Filter + Amp ou Filter + Gate. Avec Velocity " +
            "de l'enveloppe 2 active, la vélocité n'agit alors que sur la coupure. L'enveloppe auxiliaire peut moduler la hauteur, " +
            "la forme d'onde ou le bruit via la matrice.",
        controls = listOf(
            cmd("env routing", "Fait défiler les destinations.", ROUTING),
            cmd("Env 1 = Aux", "Enveloppe libre pour la matrice.", AUX),
            cmd("Env 2 = Filter + Amp / Filter + Gate", "Une enveloppe pour deux rôles.", ROUTING),
        ),
        steps = listOf(
            "Appuie sur env routing et observe les voyants : passe Env 2 sur Filter + Amp.",
            "Mets ensuite Env 1 sur Aux.",
            "Essaie Filter + Gate : le volume s'ouvre et se ferme à chaque touche.",
            "Reviens à Env 1 = Filter, Env 2 = Amp.",
        ),
        expected = "Tu sais libérer l'enveloppe 1 et revenir au routage par défaut.",
        exerciseTitle = "Orgue filtré",
        exercise = listOf(
            "Règle Env 2 sur Filter + Gate et joue des accords.",
            "Ajuste l'enveloppe 2 pour un filtre qui claque à chaque accord.",
        ),
        success = "Les accords sonnent nets avec un claquement de filtre.",
        mistakes = listOf(
            "Chercher Aux avec Env 2 sur Amp : il n'est disponible qu'en Filter + Amp ou Filter + Gate.",
            "Oublier que l'enveloppe auxiliaire ne fait rien sans routage dans la matrice.",
        ),
        quiz = listOf(
            qz("Quand Env 1 peut-elle être Aux ?", listOf("Env 2 sur Filter + Amp ou Filter + Gate", "Toujours"), 0,
                "Aux exige qu'Env 2 prenne en charge le filtre.", ROUTING),
            qz("Que fait Filter + Gate ?", listOf("Env 2 sur la coupure, volume ouvert/fermé à chaque touche", "Coupe le filtre"), 0,
                "Le volume est simplement déclenché.", ROUTING),
            qz("À quoi sert une enveloppe Aux ?", listOf("Moduler une destination via la matrice", "Régler le tempo"), 0,
                "Elle est routée librement dans la matrice.", AUX),
            qz("Quel bouton change le routage ?", listOf("env routing", "unison"), 0,
                "Chaque appui passe à l'option suivante.", ROUTING),
        ),
    )

    private val m4l3 = ctLesson(
        M4, 3, "Delay, répétition et vélocité", listOf(ENV_PARAM), 15,
        objective = "Utiliser les paramètres cachés des enveloppes pour des sons rythmiques.",
        explanation = "Dans le menu Program : Env 1 Delay et Env 2 Delay retardent le début de l'attaque après la note ; Env Repeat " +
            "(Env1, Env2 ou Env1+2) fait boucler Delay, Attack et Decay tant que la touche est tenue (Sustain règle le niveau de fin " +
            "du Decay) ; la Release démarre au relâchement. Exemple d'usine : G06 Bubbly. Sur la façade, Velocity fait réagir " +
            "l'enveloppe à la force de frappe.",
        controls = listOf(
            cmd("Program > Env 1 / Env 2 Delay", "Retard avant l'attaque.", ENV_PARAM),
            cmd("Program > Env Repeat", "Boucle Delay-Attack-Decay.", ENV_PARAM),
            cmd("Velocity", "Réponse à la frappe.", ENV_PARAM),
        ),
        steps = listOf(
            "Appuie sur program et trouve Env Repeat avec select.",
            "Règle-le sur Env1, puis tiens une note : le filtre bat en boucle.",
            "Raccourcis Attack et Decay de l'enveloppe 1 pour accélérer la boucle.",
            "Remets Env Repeat sur Off.",
        ),
        expected = "Le filtre pulse tant que la note est tenue.",
        exerciseTitle = "Pulsation hypnotique",
        exercise = listOf(
            "Avec Env Repeat sur Env1, cale la vitesse de la boucle à l'oreille sur ton tempo.",
            "Tiens un accord pendant huit mesures.",
        ),
        success = "La pulsation reste régulière et musicale.",
        style = "Tribe : une pulsation de filtre qui tourne sur un accord tenu.",
        mistakes = listOf(
            "Croire que la Release boucle : elle ne démarre qu'au relâchement.",
            "Oublier de sauvegarder : Env Repeat appartient au programme.",
        ),
        quiz = listOf(
            qz("Quelles étapes boucle Env Repeat ?", listOf("Delay, Attack, Decay", "Release"), 0,
                "Delay, Attack et Decay se répètent tant que la touche est tenue.", ENV_PARAM),
            qz("Où se trouve Env Repeat ?", listOf("Menu Program", "Menu Global"), 0,
                "Paramètre supplémentaire du menu Program.", ENV_PARAM),
            qz("Que fait Env Delay ?", listOf("Retarde le début de l'attaque", "Ajoute un écho"), 0,
                "Un délai entre la note et l'attaque.", ENV_PARAM),
            qz("Quel programme d'usine illustre Env Repeat ?", listOf("G06 Bubbly", "A02 White Room Hoover"), 0,
                "Le guide cite G06 Bubbly.", ENV_PARAM),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(
            M1, 1, "Prise en main",
            "Connexions, calibration, programmes, sauvegarde, Basic Program et Live Panel.",
            "Installer le Take 5 et gérer tes programmes.",
            "Une banque User rangée = des changements de son rapides en live.",
            listOf(m1l1, m1l2, m1l3),
        ),
        CourseModule(
            M2, 2, "Oscillateurs et mélangeur",
            "Octave, Pitch, Shape, sync, FM, sous-oscillateur et bruit.",
            "Fabriquer la matière sonore de base.",
            "Stabs en quinte et leads synchronisés : la couleur tribe et mentalcore.",
            listOf(m2l1, m2l2, m2l3),
        ),
        CourseModule(
            M3, 3, "Le filtre",
            "Cutoff, Resonance, Drive, enveloppe de filtre, recette de basse.",
            "Sculpter le timbre et construire une basse.",
            "Le filtre résonant joué à la main est la base de l'acidcore.",
            listOf(m3l1, m3l2, m3l3),
        ),
        CourseModule(
            M4, 4, "Les enveloppes",
            "Enveloppe d'amplitude, routage, enveloppe auxiliaire, répétition.",
            "Donner une forme à chaque note.",
            "Stabs courts et pulsations de filtre donnent le rythme sans batterie.",
            listOf(m4l1, m4l2, m4l3),
        ),
    )
}
