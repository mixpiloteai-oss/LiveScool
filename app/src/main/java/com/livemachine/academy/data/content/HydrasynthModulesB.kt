package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** Hydrasynth — modules 5 à 7 : modulation, jeu (macros, arpégiateur, voix), effets et sauvegarde. */
internal object HydrasynthModulesB {

    // Références du manuel (pages imprimées = pages du PDF)
    private val ENV = hyd("The Envelopes", "50")
    private val ENV_PARAM = hyd("Envelope parameters: page 1, Quantize", "51")
    private val LFO = hyd("The LFOs, LFO parameters: page 1", "55")
    private val LFO_PLUS = hyd("BPM Sync, TrigSync, LFO parameters: page 2", "56")
    private val RACCOURCI = hyd("Module Shortcuts, Create Mod routes, Copy / Paste", "33–34")
    private val MATRIX = hyd("The Mod Matrix, Creating Mod Routes", "85")
    private val MATRIX_NOTES = hyd("Direct Assignment, Notes About Mod Routes", "86")
    private val MACROS_HOME = hyd("The Home page: Macro City", "14")
    private val MACROS = hyd("Mastering the Macros", "81")
    private val ARP_BASE = hyd("Arpeggiator basics", "13–14")
    private val ARP = hyd("Arp Edit Mode, Arp parameters", "77–78")
    private val VOICE = hyd("The Voice Module", "68")
    private val FX = hyd("The Effects, Pre- and Post-FX", "62")
    private val FX_DIST = hyd("Compressor, Distort, Delay Types", "65")
    private val DELAY_REVERB = hyd("Delay and Reverb Parameters, Freeze", "66")
    private val SAVE = hyd("Save the Patch, Patch Protection", "94")

    // ---------------------------------------------------------------------
    // Module 5 — Modulation
    // ---------------------------------------------------------------------
    private const val M5 = "hyd-m5"

    private val m5l1 = ctLesson(
        M5, 1, "Les enveloppes", listOf(ENV, ENV_PARAM), 15,
        objective = "Donner une forme dans le temps au filtre et au volume.",
        explanation = "5 enveloppes identiques, de type DAHDSR (Delay, Attack, Hold, Decay, Sustain, Release), avec courbes réglables, boucle " +
            "possible et jusqu'à 4 sources de déclenchement. ENV 1 est pré-câblée vers les filtres, ENV 2 vers l'ampli. BPM Sync : durées " +
            "en valeurs rythmiques (de 1/64T à 16 mesures) au lieu de secondes. Quantize rend l'enveloppe en escalier. Raccourci : sur la " +
            "page 1, SHIFT + maintenir le bouton d'une étape règle sa durée au temps d'appui.",
        controls = listOf(
            cmd("ENV 1–5", "Pages des enveloppes.", ENV),
            cmd("Attack, Decay, Sustain, Release, Delay, Hold", "Étapes.", ENV_PARAM),
            cmd("BPM Sync, Quantize", "Calage au tempo, escalier.", ENV_PARAM),
        ),
        steps = listOf(
            "INIT deux fois, ferme la coupure et monte l'ENV 1 du filtre au maximum.",
            "[ENV 1] : Attack 272 ms.",
            "Quantize sur 9, puis Attack à 1,53 s : l'attaque monte par marches.",
            "Remets Quantize sur Off.",
        ),
        expected = "Un son de cuivre dont l'attaque peut être lisse ou en escalier.",
        exerciseTitle = "Pluck calé",
        exercise = listOf("Active BPM Sync sur l'ENV 1 et règle un Decay de 1/16 pour un pluck rythmé."),
        success = "Le pluck suit le tempo.",
        style = "Tribecore : plucks courts calés sur le tempo.",
        mistakes = listOf("Attendre un effet de BPM Sync sur le Sustain : c'est un niveau, pas une durée."),
        quiz = listOf(
            qz("Nombre d'enveloppes ?", listOf("5", "2"), 0, "Identiques.", ENV),
            qz("Forme des enveloppes ?", listOf("DAHDSR", "AD"), 0, "Delay et Hold en plus.", ENV),
            qz("ENV 2 est pré-câblée vers…", listOf("L'ampli", "Le filtre"), 0, "ENV 1 vers les filtres.", ENV),
            qz("Quantize sur une enveloppe ?", listOf("Effet en escalier", "Calage au tempo"), 0, "Réduit la résolution.", ENV_PARAM),
        ),
    )

    private val m5l2 = ctLesson(
        M5, 2, "Les LFO", listOf(LFO, LFO_PLUS), 15,
        objective = "Créer des mouvements cycliques, libres ou calés au tempo.",
        explanation = "5 LFO identiques. Wave : Sine, Triangle, Saw Up, Saw Down, Square, Pulse, S&H, Noise, Random, Step. Rate : 0,02 à 150 Hz, " +
            "ou valeurs rythmiques avec BPM Sync. TrigSync : Poly (un LFO par voix), Single (un LFO pour toutes les voix, relancé à chaque " +
            "note), Off (libre). Delay et Fade In retardent ou adoucissent le départ ; Phase règle le point de départ ; Level dose toutes ses " +
            "routes à la fois. One-Shot fait un seul cycle. LFO 1 est pré-câblé vers les filtres, LFO 2 vers l'ampli.",
        controls = listOf(
            cmd("Wave, Rate, BPM Sync", "Forme et vitesse.", LFO),
            cmd("TrigSync", "Poly, Single, Off.", LFO),
            cmd("Delay, Fade In, Phase, Level", "Départ et intensité.", LFO),
            cmd("One-Shot", "Un seul cycle.", LFO_PLUS),
        ),
        steps = listOf(
            "[LFO 1] : Wave S&H, BPM Sync On, Rate 1/16.",
            "Dans [FILTER 1], monte LFO 1 amount.",
            "Mets TrigSync sur Single pour relancer à chaque note.",
        ),
        expected = "Le filtre saute au hasard en rythme.",
        exerciseTitle = "Basse instable",
        exercise = listOf("Sur une basse tenue, essaie S&H, puis Square, toujours calés au tempo."),
        success = "La basse change de couleur à chaque double croche.",
        style = "Mentalcore : S&H synchronisé sur le filtre.",
        mistakes = listOf("TrigSync sur Off en attendant un départ identique à chaque note."),
        quiz = listOf(
            qz("Rate maximal sans BPM Sync ?", listOf("150 Hz", "10 Hz"), 0, "De 0,02 à 150 Hz.", LFO),
            qz("TrigSync Poly ?", listOf("Un LFO par voix", "Un seul LFO libre"), 0, "Idéal pour les nappes.", LFO_PLUS),
            qz("LFO pré-câblé vers les filtres ?", listOf("LFO 1", "LFO 2"), 0, "LFO 2 vers l'ampli.", LFO),
            qz("À quoi sert Level ?", listOf("Doser toutes les routes du LFO d'un coup", "Le volume du patch"), 0, "Réglage fin global.", LFO_PLUS),
        ),
    )

    private val m5l3 = ctLesson(
        M5, 3, "La matrice de modulation", listOf(MATRIX, MATRIX_NOTES, RACCOURCI), 20,
        objective = "Relier n'importe quelle source à n'importe quel paramètre.",
        explanation = "MOD MATRIX : 32 routes par patch (source, destination, profondeur positive ou négative). Raccourci le plus rapide : " +
            "maintenir le bouton du module source (ex. LFO 3) et appuyer sur le bouton du module destination : la matrice s'ouvre au " +
            "premier emplacement libre ; l'encodeur du haut choisit le paramètre, celui du bas la profondeur. Assignation directe : sur la " +
            "page du paramètre, maintenir la source, les boutons allumés sont les destinations possibles. INIT + bouton du haut vide un " +
            "emplacement. Copier un module vers un module identique : SAVE maintenu, source, puis destination.",
        controls = listOf(
            cmd("Source maintenue + destination", "Créer une route.", RACCOURCI),
            cmd("MOD MATRIX", "32 routes.", MATRIX),
            cmd("INIT + bouton du haut", "Vider une route.", MATRIX_NOTES),
            cmd("SAVE + source + destination", "Copier un module.", RACCOURCI),
        ),
        steps = listOf(
            "Maintiens [LFO 3] et appuie sur [OSC 1] : la matrice s'ouvre.",
            "Encodeur du haut : choisis WaveScan ; encodeur du bas : profondeur.",
            "Joue : le timbre évolue.",
            "Copie l'ENV 1 vers l'ENV 2 : SAVE, ENV 1, ENV 2.",
        ),
        expected = "Une route LFO 3 → WaveScan qui fait évoluer le son.",
        exerciseTitle = "Trois routes",
        exercise = listOf("Crée trois routes : LFO vers WaveScan, enveloppe vers pan, vélocité vers coupure."),
        success = "Le son réagit à chaque route.",
        mistakes = listOf("Croire qu'un oscillateur peut être une source : ce n'est pas possible."),
        quiz = listOf(
            qz("Routes par patch ?", listOf("32", "8"), 0, "En plus des macros.", MATRIX),
            qz("Raccourci de création ?", listOf("Maintenir la source, appuyer sur la destination", "SHIFT + MOD MATRIX"), 0, "Ouvre le 1er emplacement libre.", RACCOURCI),
            qz("Vider un emplacement ?", listOf("INIT + bouton du haut", "EXIT"), 0, "Route effacée.", MATRIX_NOTES),
            qz("Un oscillateur peut-il être source ?", listOf("Non", "Oui"), 0, "Ni les Mutants.", MATRIX_NOTES),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 6 — Jeu : macros, arpégiateur, voix
    // ---------------------------------------------------------------------
    private const val M6 = "hyd-m6"

    private val m6l1 = ctLesson(
        M6, 1, "Les macros", listOf(MACROS_HOME, MACROS), 20,
        objective = "Contrôler plusieurs paramètres d'un seul bouton en live.",
        explanation = "Sur la page HOME, les 8 encodeurs et boutons pilotent 8 macros ; chacune agit sur jusqu'à 8 paramètres. Un tiret = macro " +
            "vide. Créer : MACRO ASSIGN, bouton de la macro (Edit), INIT + bouton pour la vider, bouton 2 pour assigner, puis un bouton de " +
            "module (ex. [OSC 1]) ; régler la profondeur du bouton rotatif et la valeur du bouton (BTN VALUE). INIT maintenu + encodeur fait " +
            "sauter la macro à 0.",
        controls = listOf(
            cmd("HOME + encodeurs / boutons", "Jouer les macros.", MACROS_HOME),
            cmd("MACRO ASSIGN", "Créer et éditer.", MACROS),
            cmd("INIT + encodeur", "Macro à 0.", MACROS),
        ),
        steps = listOf(
            "MACRO ASSIGN, Macro 1 : INIT + bouton 1 pour la vider.",
            "Bouton 1 à nouveau, puis bouton 2 (Assign), puis [OSC 1] (Pitch).",
            "Règle la profondeur avec l'encodeur 6.",
            "HOME : tourne l'encodeur 1 et écoute.",
        ),
        expected = "Une macro qui change la hauteur de l'oscillateur 1.",
        exerciseTitle = "Macro de montée",
        exercise = listOf("Fais une macro qui ouvre le filtre, monte la réverbération et la distorsion ensemble."),
        success = "Un seul geste fait la montée.",
        style = "Hardtek : une macro pour les montées et les retours.",
        mistakes = listOf("Oublier HOME : sur une page d'édition, les encodeurs ne pilotent pas les macros."),
        quiz = listOf(
            qz("Paramètres par macro ?", listOf("Jusqu'à 8", "1"), 0, "Et 8 macros par patch.", MACROS),
            qz("Où jouer les macros ?", listOf("Page HOME", "MOD MATRIX"), 0, "Encodeurs et boutons.", MACROS_HOME),
            qz("Macro vide ?", listOf("Un tiret s'affiche", "Elle clignote"), 0, "Un nombre = assignée.", MACROS),
            qz("Remettre une macro à 0 ?", listOf("INIT + encodeur", "EXIT"), 0, "Sans valeurs intermédiaires.", MACROS),
        ),
    )

    private val m6l2 = ctLesson(
        M6, 2, "L'arpégiateur", listOf(ARP_BASE, ARP), 15,
        objective = "Faire tourner des arpèges calés et variés.",
        explanation = "ON active l'arpégiateur ; LATCH maintient ; Mode, Octave, Division, Swing (8 positions) et TRIPLET sont sur le panneau. " +
            "SHIFT + ON ouvre l'édition : Tempo (TAP TEMPO 3 fois ou plus), Division (12 valeurs), Swing (50 à 75 %), Gate (5 à 100 %), " +
            "Octave mode, Octave (1 à 6), Mode (Up, Down, Up/Dn, Up & Dn, Order, Random, Chord, Phrase) ; page 2 : Ratchet (1, 2, 4, 8 " +
            "subdivisions), Chance (0 à 100 %), Phrase (64 phrases), Length, Tap Trig.",
        controls = listOf(
            cmd("ON, LATCH, TRIPLET, TAP TEMPO", "Activer, maintenir, triolets, tempo.", ARP_BASE),
            cmd("SHIFT + ON", "Édition.", ARP),
            cmd("Ratchet, Chance", "Répétitions et probabilité.", ARP),
        ),
        steps = listOf(
            "ON, tiens un accord, LATCH.",
            "Mode Up, Octave 2, Division 1/16.",
            "SHIFT + ON, page 2 : Ratchet 2, Chance 30 %.",
            "Tape le tempo avec TAP TEMPO.",
        ),
        expected = "Un arpège avec des répétitions aléatoires.",
        exerciseTitle = "Arpège tribecore",
        exercise = listOf("Mode Random, Octave 3, Ratchet 4, Chance 50 %."),
        success = "L'arpège ne se répète jamais pareil.",
        style = "Tribecore : arpèges rapides avec répétitions aléatoires.",
        mistakes = listOf("Chercher Ratchet sur le panneau du Desktop : il est dans l'édition (page 2)."),
        quiz = listOf(
            qz("Ouvrir l'édition de l'arpégiateur ?", listOf("SHIFT + ON", "SHIFT + LATCH"), 0, "Deux pages.", ARP),
            qz("Valeurs de Ratchet ?", listOf("1, 2, 4, 8", "1 à 16"), 0, "Subdivisions d'un pas.", ARP),
            qz("Plage d'octaves ?", listOf("1 à 6", "1 à 3"), 0, "Paramètre Octave.", ARP),
            qz("Nombre de phrases ?", listOf("64", "16"), 0, "Mode Phrase.", ARP),
        ),
    )

    private val m6l3 = ctLesson(
        M6, 3, "Le module Voice", listOf(VOICE), 10,
        objective = "Choisir polyphonie, unisson et caractère analogique.",
        explanation = "[VOICE] page 1 : Polyphony (Poly, Mono, MonoLo, MonoHi, Unison, UnisonLo, UnisonHi), Density (1 à 8 voix en unisson), " +
            "Detune (0 à 127), Analog Feel (dérive des paramètres), Random Phase (les oscillateurs ne repartent pas à 0°), Stereo Mode et " +
            "Width, Warm Mode. Lo / Hi = priorité à la note la plus grave ou la plus aiguë. Le Desktop a 8 voix.",
        controls = listOf(
            cmd("Polyphony", "Poly, Mono, Unison (+ Lo / Hi).", VOICE),
            cmd("Density, Detune", "Unisson.", VOICE),
            cmd("Analog Feel, Random Phase, Warm Mode", "Caractère.", VOICE),
        ),
        steps = listOf(
            "[VOICE], Polyphony sur Unison.",
            "Density 8, monte Detune.",
            "Monte Analog Feel et active Random Phase.",
        ),
        expected = "Un lead épais de 8 voix désaccordées.",
        exerciseTitle = "Lead hoover",
        exercise = listOf("Unison, Density 8, Detune élevé, une dent de scie : joue une ligne grave."),
        success = "Un son large et agressif.",
        style = "Mentalcore : leads épais en unisson.",
        mistakes = listOf("Régler Density en mode Poly : il n'agit qu'en unisson."),
        quiz = listOf(
            qz("Voix maximum en unisson ?", listOf("8", "4"), 0, "Density 1 à 8.", VOICE),
            qz("MonoLo = …", listOf("Priorité à la note la plus grave", "La plus aiguë"), 0, "Lo = grave.", VOICE),
            qz("Random Phase ?", listOf("Les oscillateurs ne repartent pas à 0°", "Notes aléatoires"), 0, "Comme un synthé analogique.", VOICE),
            qz("Density agit en…", listOf("Unison", "Poly"), 0, "Seulement en unisson.", VOICE),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 7 — Effets et sauvegarde
    // ---------------------------------------------------------------------
    private const val M7 = "hyd-m7"

    private val m7l1 = ctLesson(
        M7, 1, "Pre-FX et Post-FX", listOf(FX, FX_DIST), 15,
        objective = "Colorer le son avant et après délai et réverb.",
        explanation = "Ordre : Pre-FX > Delay > Reverb > Post-FX > sortie. Pre-FX et Post-FX ont les mêmes types : Chorus, Flanger, Rotary, Phaser, " +
            "Lo-Fi, Tremolo, EQ, Compressor, Distort (Bypass par défaut), chacun avec des préréglages de départ. Distort : Drive, Tone " +
            "(coupe-haut ou coupe-bas), Asym, Curve (0 = overdrive, plus haut = plus dur), Output, Dry/Wet. Compressor : Sidechain (horloge " +
            "de l'arpégiateur, Tap, entrées CV), Ratio, Threshold, Attack, Release.",
        controls = listOf(
            cmd("PRE-FX / POST-FX > FXtype", "Type d'effet.", FX),
            cmd("Distort : Drive, Tone, Curve", "Saturation.", FX_DIST),
            cmd("Compressor : Sidechain BPM Duck", "Pompage au tempo.", FX_DIST),
        ),
        steps = listOf(
            "[PRE-FX] : Distort, monte Drive.",
            "Monte Curve pour une distorsion plus dure.",
            "[POST-FX] : Compressor, Sidechain sur BPM Duck.",
        ),
        expected = "Un son saturé qui pompe au tempo.",
        exerciseTitle = "Basse qui pompe",
        exercise = listOf("Basse distordue en Pre-FX et compresseur BPM Duck en Post-FX."),
        success = "La basse laisse respirer le kick.",
        style = "Hardtek : basse saturée qui pompe sous le kick.",
        mistakes = listOf("Mettre la distorsion en Post-FX en voulant saturer avant la réverb."),
        quiz = listOf(
            qz("Ordre des effets ?", listOf("Pre-FX, Delay, Reverb, Post-FX", "Reverb, Delay, Pre-FX"), 0, "Puis sortie.", FX),
            qz("Curve à 0 sur Distort ?", listOf("Overdrive", "Distorsion dure"), 0, "Plus haut = plus dur.", FX_DIST),
            qz("Sidechain au tempo ?", listOf("BPM Duck", "Mod In 1"), 0, "Horloge de l'arpégiateur.", FX_DIST),
            qz("Type par défaut ?", listOf("Bypass", "Chorus"), 0, "Effet désactivé.", FX),
        ),
    )

    private val m7l2 = ctLesson(
        M7, 2, "Délai et réverbération", listOf(FX_DIST, DELAY_REVERB), 15,
        objective = "Créer de l'espace et figer une réverbération.",
        explanation = "5 délais : Basic Mono, Basic Stereo, Pan Delay, LRC Delay, Reverse. Paramètres : Time (1 ms à 3 s, ou valeur rythmique " +
            "avec BPM sync), Feedback, Wet Tone (passe-bas ou passe-haut), Feed Tone, Dry/Wet. 4 réverbs : Hall, Room, Plate, Cloud ; " +
            "PreDelay, Time (120 ms à 90 s, ou Freeze), Tone, Hi Damp, Lo Damp, Dry/Wet. Freeze : page 1 de la réverb, SHIFT + bouton 3 ; " +
            "avec Hi / Lo Damp à 0, la réverb tient indéfiniment et les notes jouées s'y ajoutent.",
        controls = listOf(
            cmd("DELAY : type, Time, Feedback, BPM sync", "Écho.", DELAY_REVERB),
            cmd("REVERB : type, Time, Damp", "Espace.", DELAY_REVERB),
            cmd("SHIFT + bouton 3 (Reverb)", "Freeze.", DELAY_REVERB),
        ),
        steps = listOf(
            "[DELAY] : Pan Delay, BPM sync On, Time 1/8.",
            "[REVERB] : Cloud, monte Dry/Wet.",
            "Hi et Lo Damp à 0, puis SHIFT + bouton 3 : Freeze.",
        ),
        expected = "Un écho calé et une nappe de réverb figée.",
        exerciseTitle = "Break figé",
        exercise = listOf("Au break, fige la réverb sur un accord, puis relâche au retour."),
        success = "Le break tient sur la réverb figée.",
        style = "Mentalcore : réverb figée pour les breaks planants.",
        mistakes = listOf("Freeze avec du Damp : la réverb s'éteint quand même."),
        quiz = listOf(
            qz("Délai qui joue à l'envers ?", listOf("Reverse", "LRC"), 0, "Joue la période à l'envers.", FX_DIST),
            qz("Types de réverb ?", listOf("Hall, Room, Plate, Cloud", "Spring seulement"), 0, "Quatre types.", DELAY_REVERB),
            qz("Freeze indéfini si…", listOf("Hi et Lo Damp à 0", "Dry/Wet à 0"), 0, "Sans amortissement.", DELAY_REVERB),
            qz("Activer Freeze ?", listOf("SHIFT + bouton 3 (page 1 Reverb)", "INIT + REVERB"), 0, "Le temps passe à Freeze.", DELAY_REVERB),
        ),
    )

    private val m7l3 = ctLesson(
        M7, 3, "Sauvegarder un patch", listOf(SAVE), 10,
        objective = "Garder tes sons avec un nom et une catégorie.",
        explanation = "La protection est activée par défaut : « Protection is On! » s'affiche. La désactiver : SYSTEM SETUP, page 1, champ 4 " +
            "(encodeur 4), EXIT. Puis SAVE : encodeur 1 = emplacement (8 banques × 128), encodeur 5 = banque, encodeur 2 = nom (16 " +
            "caractères), encodeur 3 = catégorie, encodeur 4 = traitement des macros, couleur des LED (32 couleurs). SAVE pour confirmer. " +
            "Penser à réactiver Protect ensuite.",
        controls = listOf(
            cmd("SYSTEM SETUP > Protect", "Autoriser la sauvegarde.", SAVE),
            cmd("SAVE", "Page de sauvegarde.", SAVE),
            cmd("Encodeurs 1–5", "Emplacement, nom, catégorie, macros, banque.", SAVE),
        ),
        steps = listOf(
            "SYSTEM SETUP : Protect sur Off, EXIT.",
            "SAVE : choisis un emplacement libre.",
            "Nomme le patch et choisis sa catégorie (Bass, Lead…).",
            "SAVE pour confirmer, puis remets Protect sur On.",
        ),
        expected = "Ton patch est rangé sous son nom et sa catégorie.",
        exerciseTitle = "Banque de live",
        exercise = listOf("Sauvegarde tes trois meilleurs sons à la suite dans une banque libre."),
        success = "Tu les retrouves par catégorie dans BROWSE.",
        mistakes = listOf("S'étonner du message « Protection is On! » : il faut d'abord désactiver Protect."),
        quiz = listOf(
            qz("Protect par défaut ?", listOf("Activé", "Désactivé"), 0, "Empêche la sauvegarde.", SAVE),
            qz("Longueur du nom ?", listOf("16 caractères", "8"), 0, "Encodeur 2.", SAVE),
            qz("Mémoire de patchs ?", listOf("8 banques × 128", "4 × 64"), 0, "A à H.", SAVE),
            qz("Couleurs de LED ?", listOf("32", "8"), 0, "Molette et LED.", SAVE),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(M5, 5, "Modulation", "Enveloppes, LFO, matrice de modulation.",
            "Faire bouger le son.", "Modulations synchronisées pour des sons vivants.", listOf(m5l1, m5l2, m5l3)),
        CourseModule(M6, 6, "Jeu : macros, arpégiateur, voix", "Macros, arpégiateur, polyphonie et unisson.",
            "Jouer l'Hydrasynth en live.", "Macros et arpèges pour la scène.", listOf(m6l1, m6l2, m6l3)),
        CourseModule(M7, 7, "Effets et sauvegarde", "Pre-FX, Post-FX, délai, réverb, Freeze, sauvegarde.",
            "Finaliser et garder tes sons.", "Effets et réverb figée pour les breaks.", listOf(m7l1, m7l2, m7l3)),
    )
}
