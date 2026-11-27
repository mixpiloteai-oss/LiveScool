package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** Digitakt II — modules 1 à 5 : prise en main, jeu, sons, programmation, longueur et Euclidien. */
internal object DigitaktIIModulesA {

    // Références du manuel (pages imprimées = pages du PDF)
    private val FACADE = dt("3.1 Front panel", "12–13")
    private val ARRIERE = dt("3.2 Rear connectors / 3.3 Setting up", "14")
    private val DONNEES = dt("5. Data structure", "16–17")
    private val CONTENU = dt("9. Patterns, kits, presets and samples", "29")
    private val ECRAN = dt("6. Interacting / 6.1 Screen navigation / 6.2 Parameter editing", "18")
    private val FUNC = dt("6.2.1–6.4 Value jump, Control all, [FUNC], Copy-clear-paste", "19")
    private val QUICK_PTN = dt("7.1 Playing the factory presets", "22")
    private val PTN_OPS = dt("10.1.1–10.1.2 Selecting bank and pattern, Pattern control", "40")
    private val TEMPO = dt("7.3 Tempo and metronome", "22–23")
    private val KEYBOARD = dt("8.5.1 Keyboard mode", "25")
    private val KB_SETUP = dt("8.5.2 Keyboard setup menu", "26")
    private val MUTE = dt("8.5.3 Mute mode", "26–27")
    private val TRIG_MODES = dt("8.5.4 Trig mode", "27–28")
    private val BROWSER = dt("9.2 Preset browser / 9.3.1 Load (preset)", "30")
    private val PRESET_SAVE = dt("9.3.2 Save (preset)", "31")
    private val KIT_SAVE = dt("9.3.5–9.3.6 Load and save (kit)", "33–34")
    private val PRESET_EDIT = dt("9.5–9.6 Editing and saving a preset", "35–36")
    private val ASSIGN = dt("9.7 Assigning a sample", "36")
    private val SAMPLES_LOAD = dt("13.6 Samples menu / 13.6.1 Loading a sample", "70–71")
    private val RELOAD_PRESET = dt("11.1 Editing the track parameters", "53")
    private val TRIGS = dt("10.2 Editing a pattern / 10.2.1 Trig types", "41")
    private val GRID = dt("10.2.2 Grid recording mode", "42")
    private val LIVE = dt("10.2.3 Live recording mode", "42–43")
    private val STEP = dt("10.2.4 Step recording mode", "43–44")
    private val QUANTIZE = dt("10.6 Quantize menu", "45")
    private val PAGE_SETUP = dt("10.7 Page setup menu / 10.7.1 Per pattern mode", "45–46")
    private val PER_TRACK = dt("10.7.2 Per track mode", "46–47")
    private val EUCLID = dt("10.3 Sequencer menu (Euclidean mode)", "44–45")

    // ---------------------------------------------------------------------
    // Module 1 — Prise en main
    // ---------------------------------------------------------------------
    private const val M1 = "dt2-m1"

    private val m1l1 = ctLesson(
        M1, 1, "La façade et les connexions", listOf(FACADE, ARRIERE), 15,
        objective = "Situer les touches, les encodeurs et les prises de la Digitakt II, et la brancher correctement.",
        explanation = "Au centre, l'écran et les huit encodeurs DATA ENTRY A–H qui règlent les paramètres affichés. À droite, " +
            "[YES] et [NO] pour valider ou revenir, les flèches et LEVEL/DATA. En bas, les 16 touches [TRIG] : pas du séquenceur, " +
            "pistes, patterns ou clavier selon le mode. Beaucoup de touches ont une seconde fonction, écrite en orange sur la " +
            "façade : on l'obtient en maintenant [FUNC]. À l'arrière : sorties OUT L/R, casque, entrées IN L/R, MIDI IN/OUT/THRU et USB.",
        controls = listOf(
            cmd("MAIN VOLUME", "Volume des sorties principales et du casque.", FACADE),
            cmd("DATA ENTRY A–H", "Règlent les paramètres ; appuyer en tournant change la valeur plus vite.", FACADE),
            cmd("[FUNC]", "Maintenir pour obtenir la fonction orange d'une autre touche.", FACADE),
            cmd("[TRIG 1–16]", "Pas du séquenceur, sélection de piste/pattern, clavier.", FACADE),
            cmd("OUT L/R, HEADPHONES, IN L/R", "Sorties principales, casque, entrées audio pour sampler.", ARRIERE),
        ),
        steps = listOf(
            "Machine éteinte, branche l'adaptateur secteur sur l'entrée DC In.",
            "Relie OUT L/R à ta table de mixage ou à tes enceintes, ou branche un casque sur HEADPHONES.",
            "Allume tous les appareils, puis la Digitakt II avec l'interrupteur POWER à l'arrière.",
            "Repère sur la façade [FUNC], [YES], [NO], les flèches, LEVEL/DATA et MAIN VOLUME.",
            "Repère les touches dont le second libellé est écrit en orange : ce sont les fonctions [FUNC].",
        ),
        expected = "La Digitakt II démarre et l'écran affiche le pattern actif ; tu sais où se trouvent les commandes principales.",
        exerciseTitle = "Carte de la façade",
        exercise = listOf(
            "Sans regarder le manuel, nomme à voix haute la fonction orange de [SETTINGS], [TEMPO] et [TRK].",
            "Vérifie sur la façade : sauvegarde du projet, tap tempo, mode MUTE.",
            "Montre les prises IN L/R et dis à quoi elles servent.",
        ),
        success = "Tu retrouves les trois fonctions orange et le rôle des entrées IN L/R.",
        mistakes = listOf(
            "Brancher les câbles machine allumée : le manuel demande d'éteindre les appareils avant de les connecter.",
            "Chercher une fonction orange sans maintenir [FUNC].",
        ),
        quiz = listOf(
            qz("Comment accéder à la fonction écrite en orange sous une touche ?",
                listOf("Appuyer deux fois sur la touche", "Maintenir [FUNC] et appuyer sur la touche", "Maintenir [YES]"), 1,
                "Les fonctions secondaires s'obtiennent en maintenant [FUNC] puis en appuyant brièvement sur l'autre touche.", FACADE),
            qz("Que règle MAIN VOLUME ?",
                listOf("Le volume de la piste active", "Le volume des sorties principales et du casque", "Le niveau d'entrée"), 1,
                "MAIN VOLUME règle les sorties principales et la sortie casque.", FACADE),
            qz("À quoi servent les entrées IN L/R ?",
                listOf("À brancher le casque", "À échantillonner ou traiter une source audio externe", "À recevoir l'horloge MIDI"), 1,
                "IN L/R reçoivent une source externe pour l'échantillonner ou la traiter.", ARRIERE),
            qz("Que fait un appui sur un encodeur DATA ENTRY pendant qu'on le tourne ?",
                listOf("Remet la valeur à zéro", "Change la valeur par grands pas", "Verrouille la valeur"), 1,
                "Appuyer en tournant fait varier la valeur plus rapidement.", FACADE),
        ),
    )

    private val m1l2 = ctLesson(
        M1, 2, "Projet, patterns, kits, presets et samples", listOf(DONNEES, CONTENU, ECRAN, PTN_OPS), 15,
        objective = "Comprendre comment la Digitakt II range ton travail, pour savoir ce qui est modifié et ce qui est sauvegardé.",
        explanation = "Un projet contient 128 patterns (8 banques A–H de 16) et jusqu'à 16 songs. Chaque pattern contient un kit, " +
            "les trigs et parameter locks de ses 16 pistes, le tempo, la longueur et le swing. Un kit rassemble 16 presets, un par " +
            "piste. Un preset, c'est un sample plus les réglages SRC, FLTR, AMP, FX et MOD. Chaque piste peut être audio ou MIDI. " +
            "Point clé : un preset ou un kit chargé dans un pattern devient une copie indépendante ; le modifier ne change pas l'original du +Drive.",
        controls = listOf(
            cmd("[PTN]", "Choisit la banque et le pattern.", FACADE),
            cmd("[PRESET/KIT]", "Ouvre le menu des presets et des kits.", FACADE),
            cmd("[SETTINGS]", "Gestion des projets ; [FUNC] + [SETTINGS] sauvegarde le projet.", FACADE),
            cmd("[TRK] + [TRIG 1–16]", "Sélectionne une des 16 pistes.", FACADE),
        ),
        steps = listOf(
            "Appuie sur [PTN] : les [TRIG] blancs montrent les patterns qui contiennent des données, le rouge le pattern actif.",
            "Appuie sur [NO] pour sortir.",
            "Maintiens [TRK] et appuie sur [TRIG 1] : la piste 1 devient la piste active (son numéro s'affiche à l'écran).",
            "Regarde le type de piste à l'écran : SMP pour une piste audio, MID pour une piste MIDI.",
            "Ouvre [PRESET/KIT] pour voir le menu, puis ressors avec [NO].",
        ),
        expected = "Tu sais distinguer pattern, kit, preset et sample, et tu sais où chacun se choisit.",
        exerciseTitle = "Ranger un morceau",
        exercise = listOf(
            "Écris sur papier ce que contient un pattern, un kit et un preset.",
            "Explique pourquoi modifier le kick d'un pattern ne modifie pas le preset d'origine du +Drive.",
            "Vérifie ta réponse avec la page 29 du manuel.",
        ),
        success = "Ta description correspond au manuel : le preset chargé est une copie qui appartient au pattern.",
        mistakes = listOf(
            "Croire qu'éditer un preset dans un pattern modifie la bibliothèque : il faut le sauvegarder dans le +Drive pour cela.",
            "Confondre kit (16 presets) et pattern (kit + séquence + tempo).",
        ),
        quiz = listOf(
            qz("Combien de patterns contient un projet ?", listOf("16", "64", "128"), 2,
                "8 banques de 16 patterns, soit 128 patterns par projet.", DONNEES),
            qz("Qu'est-ce qu'un kit ?", listOf("Un groupe de 16 presets, un par piste", "Un sample", "Une suite de patterns"), 0,
                "Un kit est une collection de 16 presets, un pour chaque piste du pattern.", DONNEES),
            qz("Que contient un preset audio ?",
                listOf("Uniquement un sample", "Un sample et les réglages SRC, FLTR, AMP, FX et MOD", "Les trigs de la piste"), 1,
                "Le preset combine le sample et tous les réglages des pages de paramètres.", DONNEES),
            qz("Tu modifies un preset chargé dans un pattern. Que devient le preset du +Drive ?",
                listOf("Il est modifié aussi", "Il ne change pas : le pattern contient une copie", "Il est effacé"), 1,
                "Un preset importé devient une copie indépendante qui fait partie du pattern.", CONTENU),
        ),
    )

    private val m1l3 = ctLesson(
        M1, 3, "Naviguer et régler les paramètres", listOf(ECRAN, FUNC), 15,
        objective = "Se déplacer dans les menus et modifier, réinitialiser et copier des réglages sans se perdre.",
        explanation = "Les flèches naviguent, [YES] valide ou entre dans un sous-menu, [NO] revient en arrière jusqu'à l'écran " +
            "principal. Les paramètres affichés sont placés comme les encodeurs A–H. Encodeur + [NO] remet un paramètre à sa valeur " +
            "par défaut ; maintenir une touche [PARAMETER] montre toutes les valeurs de la page. Copier, coller et effacer se font " +
            "avec [FUNC] + [RECORD], [STOP] et [PLAY] ; refaire la même combinaison annule un collage ou un effacement.",
        controls = listOf(
            cmd("Flèches, [YES], [NO]", "Naviguer, valider, revenir en arrière.", ECRAN),
            cmd("LEVEL/DATA", "Fait défiler les listes rapidement ; règle le niveau de la piste active.", ECRAN),
            cmd("Encodeur + [NO]", "Remet le paramètre à sa valeur par défaut.", ECRAN),
            cmd("[FUNC] + [RECORD] / [STOP] / [PLAY]", "Copier / coller / effacer.", FUNC),
        ),
        steps = listOf(
            "Lance un pattern avec [PLAY], puis choisis une piste avec [TRK] + [TRIG].",
            "Appuie sur [FLTR] et tourne l'encodeur du paramètre FREQ : écoute le filtre.",
            "Maintiens l'encodeur enfoncé puis appuie sur [NO] : le paramètre revient à sa valeur par défaut.",
            "Maintiens [FLTR] pour voir toutes les valeurs de la page.",
            "Ouvre un menu avec [SETTINGS], descends avec les flèches, puis reviens avec [NO] jusqu'à l'écran principal.",
        ),
        expected = "Tu modifies un paramètre et tu le remets à zéro sans chercher ; tu sais toujours revenir à l'écran principal.",
        exerciseTitle = "Régler puis revenir",
        exercise = listOf(
            "Sur une piste qui joue, change trois paramètres de la page FLTR.",
            "Remets-les un par un à leur valeur par défaut avec encodeur + [NO].",
            "Recommence en appuyant sur l'encodeur en le tournant pour aller plus vite.",
        ),
        success = "Les trois paramètres reviennent à leur valeur par défaut et le son redevient celui de départ.",
        mistakes = listOf(
            "Rester bloqué dans un menu : [NO] revient en arrière un pas à la fois.",
            "Oublier que le presse-papiers ne garde qu'un élément : une nouvelle copie remplace la précédente.",
        ),
        quiz = listOf(
            qz("Comment remettre un paramètre à sa valeur par défaut ?",
                listOf("Encodeur + [NO]", "Encodeur + [YES]", "[FUNC] + [PLAY]"), 0,
                "Appuyer sur l'encodeur et sur [NO] remet le paramètre par défaut.", ECRAN),
            qz("Comment voir toutes les valeurs d'une page de paramètres ?",
                listOf("Appuyer sur [SETTINGS]", "Maintenir la touche [PARAMETER] de la page", "Appuyer sur [PAGE]"), 1,
                "Maintenir une touche [PARAMETER] affiche toutes les valeurs de la page.", ECRAN),
            qz("Quelle combinaison colle l'élément copié ?",
                listOf("[FUNC] + [RECORD]", "[FUNC] + [STOP]", "[FUNC] + [PLAY]"), 1,
                "[FUNC] + [RECORD] copie, [FUNC] + [STOP] colle, [FUNC] + [PLAY] efface.", FUNC),
            qz("Comment annuler un collage ?",
                listOf("Éteindre la machine", "Refaire la même combinaison", "Appuyer sur [TEMPO]"), 1,
                "Coller et effacer s'annulent en répétant la même combinaison.", FUNC),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 2 — Jouer et écouter
    // ---------------------------------------------------------------------
    private const val M2 = "dt2-m2"

    private val m2l1 = ctLesson(
        M2, 1, "Lancer et enchaîner les patterns", listOf(QUICK_PTN, PTN_OPS), 15,
        objective = "Écouter les patterns d'usine, en préparer un autre pendant la lecture et maîtriser lecture, pause et arrêt.",
        explanation = "[PTN] puis [LEFT]/[RIGHT] choisit la banque, puis [TRIG 1–16] le pattern. Pendant la lecture, le pattern " +
            "choisi clignote en haut à gauche de l'écran et démarre quand le pattern en cours arrive à sa fin. [PLAY] lance, un " +
            "second [PLAY] met en pause. [STOP] arrête, mais le delay continue de s'éteindre ; [STOP] + [STOP] coupe aussi les effets " +
            "avec un court fondu.",
        controls = listOf(
            cmd("[PTN] + [LEFT]/[RIGHT] + [TRIG]", "Choisir banque puis pattern.", PTN_OPS),
            cmd("[PLAY]", "Lecture ; second appui = pause.", PTN_OPS),
            cmd("[STOP]", "Arrêt ; [STOP] + [STOP] coupe aussi les effets.", PTN_OPS),
        ),
        steps = listOf(
            "Appuie sur [PTN], choisis la banque A avec [LEFT]/[RIGHT], puis [TRIG 1] : pattern A01.",
            "Appuie sur [PLAY] et écoute.",
            "Appuie sur [PTN] puis [TRIG 2] : A02 clignote et démarre à la fin de A01.",
            "Appuie sur [PLAY] pour mettre en pause, puis encore pour reprendre.",
            "Appuie sur [STOP], puis rapidement [STOP] + [STOP] pour couper aussi les répétitions du delay.",
        ),
        expected = "Les patterns s'enchaînent sans coupure, à la fin de la mesure en cours.",
        exerciseTitle = "Enchaîner quatre patterns",
        exercise = listOf(
            "Lance A01 puis enchaîne A02, A03 et A04 en les préparant chacun avant la fin du précédent.",
            "Termine en arrêtant tout avec [STOP] + [STOP].",
        ),
        success = "Chaque changement tombe à la fin du pattern précédent, sans silence.",
        mistakes = listOf(
            "Attendre la fin du pattern pour appuyer : prépare le suivant pendant la lecture, il attend son tour.",
            "S'étonner que le delay continue après [STOP] : c'est normal, [STOP] + [STOP] le coupe.",
        ),
        quiz = listOf(
            qz("Tu choisis un nouveau pattern pendant la lecture. Quand démarre-t-il ?",
                listOf("Immédiatement", "Quand le pattern en cours a joué son dernier pas", "Après [STOP]"), 1,
                "Le nouveau pattern clignote puis démarre à la fin du pattern en cours.", PTN_OPS),
            qz("Que fait un second appui sur [PLAY] pendant la lecture ?", listOf("Arrête tout", "Met en pause", "Recommence au début"), 1,
                "[PLAY] pendant la lecture met en pause ; un nouvel appui reprend.", PTN_OPS),
            qz("Comment arrêter la lecture et couper aussi la traîne des effets ?",
                listOf("[STOP] + [STOP] rapidement", "[NO] + [STOP]", "[FUNC] + [PLAY]"), 0,
                "Après l'arrêt, [STOP] + [STOP] coupe toutes les pistes avec un court fondu des effets.", PTN_OPS),
            qz("Que signifie un [TRIG] blanc dans la sélection des patterns ?",
                listOf("Pattern vide", "Pattern qui contient des données", "Pattern en lecture"), 1,
                "Blanc : pattern avec données ; rouge : pattern actif ; éteint : vide.", PTN_OPS),
        ),
    )

    private val m2l2 = ctLesson(
        M2, 2, "Tempo, swing et métronome", listOf(TEMPO), 15,
        objective = "Régler le tempo, le swing et le métronome, taper le tempo et caler la machine à l'oreille.",
        explanation = "[TEMPO] ouvre le menu : l'encodeur A (ou LEVEL/DATA) règle le BPM, l'encodeur B les décimales, l'encodeur D " +
            "le swing (de 51 à 80 %, 50 % = pas de swing). Le tempo peut être commun au projet ou propre à chaque pattern ([FUNC] + " +
            "encodeur E). Sur l'écran principal, maintenir [LEFT] ou [RIGHT] pousse le tempo de 10 % le temps de l'appui. " +
            "[FUNC] + [TEMPO] tapé en rythme calcule le tempo après quatre frappes. [TEMPO] + [YES] active le métronome.",
        controls = listOf(
            cmd("[TEMPO]", "Menu tempo, swing et métronome.", TEMPO),
            cmd("[FUNC] + [TEMPO]", "Tap tempo (moyenne après 4 frappes).", TEMPO),
            cmd("[LEFT] / [RIGHT] maintenus", "Pousser le tempo de 10 % temporairement.", TEMPO),
            cmd("[TEMPO] + [YES]", "Métronome marche/arrêt.", TEMPO),
        ),
        steps = listOf(
            "Appuie sur [TEMPO] et règle 150 BPM avec l'encodeur A.",
            "Règle le swing avec l'encodeur D à 56 % et écoute la différence, puis reviens à 50 %.",
            "Ressors, lance un pattern, maintiens [RIGHT] puis relâche : le tempo accélère puis revient.",
            "Maintiens [FUNC] et tape [TEMPO] en rythme au moins quatre fois.",
            "Active le métronome avec [TEMPO] + [YES], puis désactive-le.",
        ),
        expected = "Le tempo change, le swing décale les doubles croches et le métronome se coupe et s'allume à la demande.",
        exerciseTitle = "Caler sur un morceau",
        exercise = listOf(
            "Lance un morceau sur ton téléphone ou une autre machine.",
            "Tape le tempo avec [FUNC] + [TEMPO], puis corrige les dérives en poussant avec [LEFT]/[RIGHT].",
            "Garde les deux en place pendant 30 secondes.",
        ),
        success = "Les kicks des deux sources restent ensemble 30 secondes.",
        style = "Hardtek / tribe : travaille entre 150 et 180 BPM, swing à 50 %.",
        mistakes = listOf(
            "Changer le tempo d'un pattern en croyant changer celui du projet : vérifie le mode avec [FUNC] + encodeur E.",
            "Oublier que le coup de pouce [LEFT]/[RIGHT] n'est que temporaire.",
        ),
        quiz = listOf(
            qz("Quel encodeur règle le swing dans le menu TEMPO ?", listOf("A", "B", "D"), 2,
                "L'encodeur D règle le swing, de 51 à 80 %.", TEMPO),
            qz("Combien de frappes faut-il pour que le tap tempo calcule un tempo ?", listOf("2", "4", "8"), 1,
                "Après quatre frappes consécutives, la moyenne est calculée.", TEMPO),
            qz("Que fait [LEFT] ou [RIGHT] maintenu sur l'écran principal ?",
                listOf("Change de pattern", "Pousse le tempo de 10 % tant que la touche est tenue", "Change de piste"), 1,
                "Le tempo est poussé de 10 % vers le bas ou le haut, puis revient au relâchement.", TEMPO),
            qz("Quelle valeur de swing correspond à un espacement égal ?", listOf("50 %", "60 %", "80 %"), 0,
                "Par défaut le swing vaut 50 %, soit un espacement égal.", TEMPO),
        ),
    )

    private val m2l3 = ctLesson(
        M2, 3, "Clavier, mutes et modes des touches", listOf(KEYBOARD, KB_SETUP, MUTE, TRIG_MODES), 20,
        objective = "Jouer une piste en notes, couper et relancer des pistes en direct, et connaître les modes des touches [TRIG].",
        explanation = "[KEYBOARD] transforme les [TRIG] en clavier pour la piste active ; [KEYBOARD] + [UP]/[DOWN] change d'octave, " +
            "[FUNC] + [KEYBOARD] ouvre le réglage de gamme (KB SCALE), de fondamentale et de KB FOLD. Le mode MUTE s'ouvre avec " +
            "[FUNC] + [TRK] : chaque [TRIG] coupe ou relance sa piste. GLOBAL MUTE (vert) vaut pour tous les patterns, PATTERN MUTE " +
            "(magenta, [FUNC] + double [TRK]) pour le pattern actif. En maintenant [FUNC] dans le mode MUTE, on prépare plusieurs " +
            "mutes qui s'appliquent au relâchement. [FUNC] + [UP]/[DOWN] choisit le mode des touches : TRACKS, VELOCITIES, RETRIGS, SLICES, PRESET POOL.",
        controls = listOf(
            cmd("[KEYBOARD]", "Mode clavier pour la piste active.", KEYBOARD),
            cmd("[FUNC] + [KEYBOARD]", "Gamme, fondamentale, KB FOLD.", KB_SETUP),
            cmd("[FUNC] + [TRK]", "Mode MUTE (GLOBAL ; double [TRK] = PATTERN).", MUTE),
            cmd("[FUNC] + [UP]/[DOWN]", "Choisit le mode des touches [TRIG].", TRIG_MODES),
        ),
        steps = listOf(
            "Choisis une piste mélodique avec [TRK] + [TRIG], puis appuie sur [KEYBOARD] et joue les touches allumées.",
            "Ouvre [FUNC] + [KEYBOARD], choisis une gamme avec KB SCALE et une fondamentale, puis sors avec [NO].",
            "Lance un pattern, entre en MUTE avec [FUNC] + [TRK] et coupe deux pistes.",
            "Maintiens [FUNC], touche trois [TRIG], puis relâche [FUNC] : les trois changements tombent ensemble.",
            "Sors du mode MUTE avec [FUNC] + [TRK].",
        ),
        expected = "La piste se joue en notes dans la gamme choisie et les pistes se coupent seules ou en groupe.",
        exerciseTitle = "Break par les mutes",
        exercise = listOf(
            "Sur un pattern complet, prépare la coupure du kick et de la basse en maintenant [FUNC].",
            "Relâche [FUNC] au début d'une mesure : break.",
            "Prépare leur retour de la même façon et relance-les sur le premier temps.",
        ),
        success = "Coupure et retour tombent en même temps et sur le temps.",
        style = "Tribe / hardtek : le retour du kick après un break doit tomber pile sur le 1.",
        mistakes = listOf(
            "Confondre GLOBAL MUTE (vert, tous les patterns) et PATTERN MUTE (magenta, pattern actif).",
            "Chercher à jouer une touche éteinte en mode clavier : seules les touches allumées jouent.",
        ),
        quiz = listOf(
            qz("Comment entrer en mode MUTE ?", listOf("[FUNC] + [TRK]", "[TRK] + [PLAY]", "[FUNC] + [PTN]"), 0,
                "[FUNC] + [TRK] ouvre le mode MUTE.", MUTE),
            qz("En GLOBAL MUTE, les pistes coupées le sont…",
                listOf("Dans le pattern actif seulement", "Dans tous les patterns", "Jusqu'au prochain [STOP]"), 1,
                "GLOBAL MUTE coupe les pistes dans tous les patterns ; ses réglages sont sauvés avec le projet.", MUTE),
            qz("Comment préparer plusieurs mutes pour qu'ils tombent en même temps ?",
                listOf("Maintenir [FUNC] en touchant les [TRIG], puis relâcher [FUNC]", "Appuyer sur [YES]", "Maintenir [PAGE]"), 0,
                "Les pistes préparées changent d'état au relâchement de [FUNC].", MUTE),
            qz("Quel mode des touches joue la piste active avec 16 vélocités différentes ?",
                listOf("TRACKS", "VELOCITIES", "SLICES"), 1,
                "En mode VELOCITIES, [TRIG 1] = vélocité 8 … [TRIG 16] = vélocité 127.", TRIG_MODES),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 3 — Sons : presets, samples, kits
    // ---------------------------------------------------------------------
    private const val M3 = "dt2-m3"

    private val m3l1 = ctLesson(
        M3, 1, "Charger un preset sur une piste", listOf(BROWSER, CONTENU), 15,
        objective = "Trouver, écouter puis charger un preset sur la piste de ton choix.",
        explanation = "[FUNC] + LEVEL/DATA tourné ouvre le PRESET BROWSER (ou [PRESET/KIT] puis LOAD). La liste montre les presets " +
            "du +Drive ; [LEFT] ouvre le menu de tri (banque, ordre, tags, recherche). Le [TRIG] de la piste active ou [FUNC] + [YES] " +
            "fait écouter le preset surligné ; [YES] le charge. Le preset chargé devient une copie qui appartient au pattern.",
        controls = listOf(
            cmd("[FUNC] + LEVEL/DATA", "Ouvre le PRESET BROWSER.", BROWSER),
            cmd("[PRESET/KIT] › LOAD", "Autre accès au chargement de preset.", BROWSER),
            cmd("[LEFT] dans la liste", "Menu de tri : banque, ordre, tags, recherche.", BROWSER),
            cmd("[YES]", "Charge le preset surligné sur la piste active.", BROWSER),
        ),
        steps = listOf(
            "Choisis la piste à remplir avec [TRK] + [TRIG].",
            "Maintiens [FUNC] et tourne LEVEL/DATA : la liste des presets s'ouvre.",
            "Parcours la liste et écoute avec le [TRIG] de la piste active.",
            "Appuie sur [LEFT], choisis FILTER et coche un tag pour réduire la liste, puis [NO].",
            "Appuie sur [YES] pour charger le preset choisi.",
        ),
        expected = "La piste joue le nouveau son dans le pattern.",
        exerciseTitle = "Changer de kick",
        exercise = listOf(
            "Sur un pattern qui joue, écoute au moins cinq presets de kick sans les charger.",
            "Charge celui qui convient le mieux.",
            "Compare avant/après en rechargeant le pattern si besoin.",
        ),
        success = "Le nouveau kick est chargé et le pattern continue de jouer.",
        style = "Hardtek : cherche un kick court et percutant.",
        mistakes = listOf(
            "Charger sans écouter : le [TRIG] de la piste active permet de prévisualiser.",
            "Charger sur la mauvaise piste : vérifie la piste active avant [YES].",
        ),
        quiz = listOf(
            qz("Comment ouvrir rapidement le PRESET BROWSER ?",
                listOf("[FUNC] + tourner LEVEL/DATA", "[TRK] + [YES]", "[SETTINGS]"), 0,
                "Maintenir [FUNC] et tourner LEVEL/DATA ouvre le navigateur de presets.", BROWSER),
            qz("Comment écouter un preset avant de le charger ?",
                listOf("Appuyer sur le [TRIG] de la piste active", "Appuyer sur [PLAY]", "Maintenir [NO]"), 0,
                "Le [TRIG] de la piste active (ou [FUNC] + [YES]) fait entendre le preset surligné.", BROWSER),
            qz("Que permet [LEFT] dans la liste des presets ?",
                listOf("Supprimer le preset", "Ouvrir le menu de tri et de recherche", "Changer de pattern"), 1,
                "[LEFT] ouvre le menu de tri : banque, ordre, filtres par tags, recherche.", BROWSER),
            qz("Après chargement, modifier le preset change-t-il la bibliothèque ?",
                listOf("Oui", "Non, le pattern contient une copie"), 1,
                "Le preset chargé devient une copie qui fait partie du pattern.", CONTENU),
        ),
    )

    private val m3l2 = ctLesson(
        M3, 2, "Charger des samples dans le projet et les assigner", listOf(SAMPLES_LOAD, ASSIGN), 20,
        objective = "Charger des samples du +Drive dans le projet, puis en placer un sur une piste.",
        explanation = "Le +Drive contient tous les samples (dossier FACTORY protégé, INCOMING, RECORDED). Pour être utilisé, un " +
            "sample doit d'abord être chargé dans la mémoire du projet : jusqu'à 1016 emplacements et 400 Mo. On ouvre le menu " +
            "SAMPLES avec [FUNC] + [SAMPLING]. Ensuite, sur la page [SRC], l'encodeur D (SAMP) choisit le sample de la piste ; " +
            "la méthode rapide ([FUNC] + [YES] dans la liste) charge un sample du +Drive directement sur la piste et dans le projet.",
        controls = listOf(
            cmd("[FUNC] + [SAMPLING]", "Ouvre le menu SAMPLES (+Drive ou mémoire du projet).", SAMPLES_LOAD),
            cmd("[RIGHT] › LOAD TO PROJECT", "Charge les samples cochés dans le projet.", SAMPLES_LOAD),
            cmd("[SRC] + encodeur D", "Choisit le sample de la piste dans la liste du projet.", ASSIGN),
            cmd("[FUNC] + [YES] dans la liste", "Écoute (environ 10 s) ou méthode d'assignation rapide.", ASSIGN),
        ),
        steps = listOf(
            "Appuie sur [FUNC] + [SAMPLING], sélectionne +DRIVE et [YES], puis ouvre FACTORY.",
            "Écoute un sample avec [FUNC] + [YES], puis coche-le avec [YES] ; coches-en deux ou trois.",
            "Appuie sur [RIGHT], choisis LOAD TO PROJECT et valide avec [YES].",
            "Choisis une piste audio, appuie sur [SRC] et tourne l'encodeur D : la liste des samples du projet s'ouvre.",
            "Écoute avec le [TRIG] de la piste, puis valide avec [YES].",
        ),
        expected = "Les samples apparaissent dans la mémoire du projet et la piste joue le sample choisi.",
        exerciseTitle = "Kit maison",
        exercise = listOf(
            "Charge dans le projet un kick, une caisse claire et un charley du dossier FACTORY.",
            "Assigne-les aux pistes 1, 2 et 3 avec l'encodeur D de la page SRC.",
            "Écoute-les avec [TRIG 1], [TRIG 2] et [TRIG 3].",
        ),
        success = "Chaque piste joue le sample voulu.",
        mistakes = listOf(
            "Chercher un sample du +Drive dans la liste SAMP alors qu'il n'est pas chargé dans le projet.",
            "Écouter avec une piste MIDI active : la prévisualisation se fait avec la voix de la piste audio active.",
        ),
        quiz = listOf(
            qz("Où doit se trouver un sample pour être choisi avec SAMP ?",
                listOf("N'importe où dans le +Drive", "Chargé dans la mémoire du projet", "Sur une clé USB"), 1,
                "Il faut charger le sample du +Drive vers le projet avant de l'assigner.", ASSIGN),
            qz("Combien d'emplacements de samples a un projet ?", listOf("128", "1016", "2048"), 1,
                "Un projet dispose de 1016 emplacements de samples (400 Mo au total).", SAMPLES_LOAD),
            qz("Quel encodeur choisit le sample sur la page SRC ?", listOf("A", "D", "H"), 1,
                "Tourner l'encodeur D (SAMP) ouvre la liste des samples du projet.", ASSIGN),
            qz("Comment ouvrir le menu SAMPLES ?", listOf("[SAMPLING]", "[FUNC] + [SAMPLING]", "[FUNC] + [SRC]"), 1,
                "[SAMPLING] ouvre l'échantillonnage ; [FUNC] + [SAMPLING] ouvre la gestion des samples.", SAMPLES_LOAD),
        ),
    )

    private val m3l3 = ctLesson(
        M3, 3, "Sauvegarder un preset et un kit", listOf(PRESET_EDIT, PRESET_SAVE, KIT_SAVE, RELOAD_PRESET), 15,
        objective = "Garder tes sons dans la bibliothèque, les copier entre pistes et revenir à la dernière version sauvegardée.",
        explanation = "Un son retouché n'existe que dans le pattern. Pour le réutiliser ailleurs : [PRESET/KIT] › PRESET SAVE, " +
            "choisir un emplacement, le nommer puis ajouter des tags et valider <SAVE>. Le kit complet (16 presets) se sauve avec " +
            "KIT SAVE. Pour copier un preset vers une autre piste : [TRK] + [TRIG] + [RECORD], puis [TRK] + [TRIG] + [STOP] sur la " +
            "piste cible. [TRK] + [TRIG] + [NO] recharge le preset dans son dernier état sauvegardé.",
        controls = listOf(
            cmd("[PRESET/KIT] › PRESET SAVE", "Sauve le preset de la piste active dans le +Drive.", PRESET_SAVE),
            cmd("[PRESET/KIT] › KIT SAVE", "Sauve le kit du pattern actif.", KIT_SAVE),
            cmd("[TRK] + [TRIG] + [RECORD] / [STOP]", "Copier puis coller un preset entre pistes.", PRESET_EDIT),
            cmd("[TRK] + [TRIG] + [NO]", "Recharge le preset dans son dernier état sauvegardé.", RELOAD_PRESET),
        ),
        steps = listOf(
            "Retouche le son d'une piste (par exemple le filtre).",
            "Ouvre [PRESET/KIT], choisis PRESET SAVE, sélectionne un emplacement vide et appuie sur [YES].",
            "Nomme le preset sur l'écran de nommage et valide avec [YES].",
            "Ajoute un tag, sélectionne <SAVE> et appuie sur [YES].",
            "Copie ce preset sur une autre piste avec [TRK] + [TRIG] + [RECORD], puis [TRK] + [TRIG cible] + [STOP].",
        ),
        expected = "Ton preset apparaît dans la liste du +Drive et la piste cible joue le même son.",
        exerciseTitle = "Ma banque de sons",
        exercise = listOf(
            "Crée deux variantes de ton kick et sauve-les sous deux noms différents.",
            "Sauve le kit du pattern avec KIT SAVE.",
            "Retouche le kick, puis reviens au son sauvegardé avec [TRK] + [TRIG] + [NO].",
        ),
        success = "Les deux presets et le kit apparaissent dans les listes, et le rechargement rend le son sauvegardé.",
        mistakes = listOf(
            "Croire que retoucher un son suffit à le garder pour d'autres patterns : il faut le sauver dans le +Drive.",
            "Écraser un preset existant : la machine demande confirmation si l'emplacement est occupé.",
        ),
        quiz = listOf(
            qz("Où se sauve un preset avec PRESET SAVE ?", listOf("Dans le +Drive", "Dans le pattern seulement", "Sur l'ordinateur"), 0,
                "PRESET SAVE enregistre le preset de la piste active dans la bibliothèque du +Drive.", PRESET_EDIT),
            qz("Comment copier un preset vers une autre piste ?",
                listOf("[TRK] + [TRIG] + [RECORD], puis [TRK] + [TRIG] + [STOP]", "[FUNC] + [YES]", "[PTN] + [STOP]"), 0,
                "Le preset complet se copie avec [TRK] + [TRIG] + [RECORD] et se colle avec [TRK] + [TRIG] + [STOP].", PRESET_EDIT),
            qz("Que fait [TRK] + [TRIG] + [NO] ?",
                listOf("Supprime la piste", "Recharge le preset dans son dernier état sauvegardé", "Coupe la piste"), 1,
                "Cette combinaison recharge le preset tel qu'il était sauvegardé.", RELOAD_PRESET),
            qz("Que contient un kit sauvegardé ?", listOf("Un sample", "Les 16 presets du pattern", "La séquence"), 1,
                "Un kit est la collection des 16 presets du pattern.", KIT_SAVE),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 4 — Programmer un pattern
    // ---------------------------------------------------------------------
    private const val M4 = "dt2-m4"

    private val m4l1 = ctLesson(
        M4, 1, "Programmer au pas (GRID RECORDING)", listOf(TRIGS, GRID), 20,
        objective = "Poser et retirer des trigs pas à pas pour construire un rythme.",
        explanation = "[RECORD] active le GRID RECORDING (touche rouge). Chaque [TRIG] pose une note trig (rouge) sur la piste " +
            "active ; [FUNC] + [TRIG] pose un lock trig (jaune), qui ne joue pas de note mais porte des réglages. Un appui bref sur un " +
            "trig le retire ; un appui un peu plus long le prépare à l'édition. [TRIG] + [YES] fait écouter un trig. En GRID, " +
            "[FUNC] + [LEFT]/[RIGHT] décale tous les trigs de la piste.",
        controls = listOf(
            cmd("[RECORD]", "Active / quitte le GRID RECORDING.", GRID),
            cmd("[TRK] + [TRIG]", "Choisit la piste à programmer.", GRID),
            cmd("[TRIG 1–16]", "Pose ou retire une note trig.", GRID),
            cmd("[FUNC] + [TRIG]", "Pose un lock trig (jaune).", TRIGS),
        ),
        steps = listOf(
            "Appuie sur [RECORD] : la touche s'allume en rouge.",
            "Choisis la piste du kick avec [TRK] + [TRIG].",
            "Pose des trigs sur [TRIG 1], [TRIG 5], [TRIG 9] et [TRIG 13].",
            "Choisis la piste du charley et pose des trigs sur les pas pairs.",
            "Appuie sur [PLAY], écoute, puis retire un trig par un appui bref.",
            "Appuie sur [RECORD] pour sortir du mode.",
        ),
        expected = "Un rythme régulier tourne en boucle sur 16 pas.",
        exerciseTitle = "Rythme hardtek de base",
        exercise = listOf(
            "Kick sur les 4 temps, caisse claire sur les pas 5 et 13, charley sur les contretemps.",
            "Décale ensuite tout le charley d'un pas avec [FUNC] + [LEFT]/[RIGHT] et écoute la différence.",
        ),
        success = "Le rythme tourne et le décalage déplace bien toute la piste.",
        style = "Hardtek : kick régulier sur chaque temps, très présent.",
        mistakes = listOf(
            "Maintenir un trig trop longtemps en voulant le retirer : il passe en édition au lieu de s'effacer.",
            "Poser les notes sur la mauvaise piste : la piste active est le [TRIG] rouge quand tu maintiens [TRK].",
        ),
        quiz = listOf(
            qz("Quelle couleur indique une note trig ?", listOf("Rouge", "Jaune", "Vert"), 0,
                "Note trigs en rouge, lock trigs en jaune.", TRIGS),
            qz("Comment poser un lock trig ?", listOf("[FUNC] + [TRIG]", "[YES] + [TRIG]", "[TRIG] maintenu"), 0,
                "En GRID RECORDING, [FUNC] + [TRIG] ajoute un lock trig.", GRID),
            qz("Que joue un lock trig ?", listOf("Une note", "Aucune note : il porte des réglages", "Un accord"), 1,
                "Les lock trigs déclenchent des parameter locks sans jouer de note.", TRIGS),
            qz("Comment décaler tous les trigs d'une piste ?",
                listOf("[FUNC] + [LEFT]/[RIGHT] en GRID RECORDING", "[PAGE] + [LEFT]", "[TRK] + [RIGHT]"), 0,
                "En GRID RECORDING, [FUNC] + flèches décale tous les trigs de la piste.", GRID),
        ),
    )

    private val m4l2 = ctLesson(
        M4, 2, "Enregistrer en direct (LIVE RECORDING)", listOf(LIVE, QUANTIZE), 20,
        objective = "Jouer un rythme en temps réel pendant que le séquenceur tourne, puis le recaler.",
        explanation = "[RECORD] + [PLAY] lance le LIVE RECORDING : [RECORD] clignote et tout ce que tu joues sur les [TRIG] est " +
            "enregistré. Appuyer deux fois sur [PLAY] en maintenant [RECORD] active ou coupe la quantification automatique. Les " +
            "encodeurs tournés pendant l'enregistrement créent des parameter locks. [NO] + [TRIG] efface en rythme les trigs d'une " +
            "piste tant que les touches sont tenues. [PLAY] sort de l'enregistrement sans arrêter la lecture. Le menu QUANTIZE " +
            "([FUNC] + [TRIG PARAMETERS]) recale après coup les trigs joués hors grille.",
        controls = listOf(
            cmd("[RECORD] + [PLAY]", "Lance le LIVE RECORDING (double [PLAY] = quantification).", LIVE),
            cmd("[NO] + [TRIG]", "Efface en rythme les trigs de la piste.", dt("10.8.1 Parameter locks", "47")),
            cmd("[FUNC] + [TRIG PARAMETERS]", "Menu QUANTIZE (TRACK, PATTERN).", QUANTIZE),
        ),
        steps = listOf(
            "Choisis une piste de percussion avec [TRK] + [TRIG].",
            "Maintiens [RECORD] et appuie sur [PLAY] : l'enregistrement démarre.",
            "Joue la touche [TRIG] de la piste en rythme pendant deux tours.",
            "Appuie sur [PLAY] pour arrêter d'enregistrer en gardant la lecture.",
            "Ouvre [FUNC] + [TRIG PARAMETERS] et monte la valeur TRACK pour recaler les trigs.",
        ),
        expected = "Les frappes jouées sont dans le pattern, puis alignées après quantification.",
        exerciseTitle = "Percussions jouées",
        exercise = listOf(
            "Enregistre en direct un rythme de percussions sur une piste libre.",
            "Efface une partie ratée avec [NO] + [TRIG] pendant la lecture.",
            "Rejoue cette partie, puis quantifie la piste.",
        ),
        success = "La piste garde ton jeu, sans les frappes ratées, et tombe en place après quantification.",
        style = "Tribe : des percussions jouées à la main donnent du mouvement au pattern.",
        mistakes = listOf(
            "Arrêter avec [STOP] alors qu'on voulait continuer à écouter : [PLAY] sort de l'enregistrement sans arrêter.",
            "Tourner un encodeur sans le vouloir pendant l'enregistrement : cela crée des parameter locks.",
        ),
        quiz = listOf(
            qz("Comment lancer le LIVE RECORDING ?", listOf("[RECORD] + [PLAY]", "[RECORD] + [STOP]", "[FUNC] + [RECORD]"), 0,
                "Maintenir [RECORD] puis appuyer sur [PLAY].", LIVE),
            qz("Que se passe-t-il si tu tournes un encodeur pendant le LIVE RECORDING ?",
                listOf("Rien n'est enregistré", "Des parameter locks sont enregistrés", "Le pattern change"), 1,
                "Les changements de paramètres sont enregistrés comme parameter locks.", LIVE),
            qz("Comment quitter l'enregistrement sans arrêter la lecture ?", listOf("[PLAY]", "[STOP]", "[NO]"), 0,
                "[PLAY] quitte le LIVE RECORDING en laissant tourner le séquenceur.", LIVE),
            qz("À quoi sert le menu QUANTIZE ?",
                listOf("Changer la longueur", "Recaler les trigs hors grille", "Transposer"), 1,
                "La quantification agit sur les trigs décalés ou joués hors grille.", QUANTIZE),
        ),
    )

    private val m4l3 = ctLesson(
        M4, 3, "Saisir note par note (STEP RECORDING)", listOf(STEP), 20,
        objective = "Entrer une ligne de notes pas à pas, la machine avançant d'un pas à chaque note.",
        explanation = "[RECORD] + [STOP] active le STEP RECORDING ([RECORD] double-clignote). On choisit le pas de départ avec un " +
            "[TRIG] ou les flèches. [FUNC] + [TRIG] ajoute une note sur la piste correspondante et avance d'un pas. En mode clavier, " +
            "maintenir [KEYBOARD] et toucher une note l'ajoute avec sa hauteur. [NO] retire un trig ou laisse un silence. En mode " +
            "JUMP (double [STOP] en tenant [RECORD]), le paramètre LEN fixe la durée de la note et l'avance.",
        controls = listOf(
            cmd("[RECORD] + [STOP]", "Active le STEP RECORDING (double [STOP] : STANDARD / JUMP).", STEP),
            cmd("[FUNC] + [TRIG]", "Ajoute une note sur la piste et avance d'un pas.", STEP),
            cmd("[KEYBOARD] maintenu + [TRIG]", "Ajoute une note à la hauteur jouée.", STEP),
            cmd("[NO]", "Retire le trig ou laisse un silence, puis avance.", STEP),
        ),
        steps = listOf(
            "Choisis une piste mélodique avec [TRK] + [TRIG].",
            "Appuie sur [RECORD] + [STOP] : le mode pas à pas s'active.",
            "Choisis le pas 1 avec [TRIG 1].",
            "Active [KEYBOARD], puis maintiens-le et touche une note : elle est posée et le pas avance.",
            "Pose quelques notes, appuie sur [NO] pour laisser des silences, puis écoute avec [PLAY].",
            "Sors avec [RECORD].",
        ),
        expected = "Une ligne mélodique est posée pas à pas, avec des silences aux bons endroits.",
        exerciseTitle = "Ligne de basse",
        exercise = listOf(
            "Écris une ligne de basse de 16 pas avec au moins quatre silences.",
            "Corrige une note en revenant sur son pas avec les flèches.",
        ),
        success = "La ligne joue en boucle avec les notes et les silences prévus.",
        style = "Acidcore : une basse en doubles croches avec quelques silences donne vite du groove.",
        mistakes = listOf(
            "Oublier de maintenir [KEYBOARD] pour poser une note à sa hauteur.",
            "En STEP RECORDING, [FUNC] + [NO] et [FUNC] + [YES] ne rechargent ni ne sauvent le pattern : c'est voulu.",
        ),
        quiz = listOf(
            qz("Comment activer le STEP RECORDING ?", listOf("[RECORD] + [STOP]", "[RECORD] + [PLAY]", "[FUNC] + [STOP]"), 0,
                "Maintenir [RECORD] puis appuyer sur [STOP].", STEP),
            qz("Que fait [NO] en STEP RECORDING ?",
                listOf("Quitte le mode", "Retire le trig ou laisse un silence, puis avance", "Efface le pattern"), 1,
                "[NO] supprime ou ajoute un silence au pas actif et avance au pas suivant.", STEP),
            qz("En mode JUMP, quel paramètre décide de la durée et de l'avance ?", listOf("VEL", "LEN", "PROB"), 1,
                "En JUMP, LEN fixe la longueur de la note et l'avance du pas actif.", STEP),
            qz("Que fait [FUNC] + [TRIG 1–16] en STEP RECORDING ?",
                listOf("Change de pattern", "Ajoute une note sur la piste correspondante et avance", "Coupe la piste"), 1,
                "La note est ajoutée sur la piste correspondante et le pas actif avance.", STEP),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 5 — Longueur, pages et séquenceur euclidien
    // ---------------------------------------------------------------------
    private const val M5 = "dt2-m5"

    private val m5l1 = ctLesson(
        M5, 1, "Longueur et vitesse du pattern", listOf(PAGE_SETUP, GRID), 15,
        objective = "Allonger un pattern sur plusieurs pages et changer sa vitesse de lecture.",
        explanation = "Le séquenceur compte jusqu'à 128 pas, soit 8 pages de 16. [FUNC] + [PAGE] ouvre PAGE SETUP. En mode PER " +
            "PATTERN, toutes les pistes partagent LENGTH et SPEED (1/8X à 2X). Quand on allonge un pattern, ses trigs sont recopiés " +
            "sur les nouvelles pages. En GRID RECORDING, [PAGE] passe d'une page à l'autre ; les LEDs <PATTERN PAGE> montrent la page " +
            "éditée et celle qui joue. 2X donne une résolution en triples croches, 3/4X aide à jouer des triolets.",
        controls = listOf(
            cmd("[FUNC] + [PAGE]", "Ouvre PAGE SETUP.", PAGE_SETUP),
            cmd("[PAGE]", "Change la page éditée en GRID RECORDING.", GRID),
            cmd("LEDs <PATTERN PAGE>", "Nombre de pages et page en cours.", FACADE),
        ),
        steps = listOf(
            "Ouvre [FUNC] + [PAGE] et règle LENGTH sur 32 pas.",
            "Ferme le menu, passe en GRID RECORDING avec [RECORD].",
            "Appuie sur [PAGE] pour aller en page 2 : elle reprend la page 1.",
            "Modifie quelques trigs de la page 2 pour créer une variation.",
            "Rouvre PAGE SETUP et essaie SPEED 2X, puis reviens à 1X.",
        ),
        expected = "Le pattern dure deux mesures, la seconde avec une variation, et la vitesse change le débit des pas.",
        exerciseTitle = "Question-réponse sur deux pages",
        exercise = listOf(
            "Fais un pattern de 32 pas où la page 2 ajoute un roulement de caisse claire à la fin.",
            "Vérifie avec les LEDs <PATTERN PAGE> quelle page joue.",
        ),
        success = "Le roulement n'arrive qu'une mesure sur deux.",
        mistakes = listOf(
            "Éditer la page 1 en croyant éditer la page 2 : vérifie la LED allumée en plein.",
            "Oublier que l'allongement recopie les trigs existants.",
        ),
        quiz = listOf(
            qz("Combien de pas au maximum dans un pattern ?", listOf("64", "128", "256"), 1,
                "Jusqu'à 128 pas répartis sur 8 pages de 16.", PAGE_SETUP),
            qz("Que se passe-t-il quand tu allonges un pattern de 2 à 4 pages ?",
                listOf("Les nouvelles pages sont vides", "Elles copient les pages existantes", "Le tempo double"), 1,
                "Les pages ajoutées sont des copies des premières pages.", PAGE_SETUP),
            qz("Quel réglage SPEED aide à jouer des triolets avec d'autres machines au même tempo ?",
                listOf("2X", "3/4X", "1/8X"), 1,
                "Le manuel conseille 3/4X pour jouer des triolets.", PAGE_SETUP),
            qz("Comment ouvrir PAGE SETUP ?", listOf("[FUNC] + [PAGE]", "[PAGE] + [YES]", "[FUNC] + [TEMPO]"), 0,
                "[FUNC] + [PAGE] ouvre le menu de longueur et de vitesse.", PAGE_SETUP),
        ),
    )

    private val m5l2 = ctLesson(
        M5, 2, "Longueurs par piste et polyrythmie", listOf(PER_TRACK), 20,
        objective = "Donner une longueur différente à chaque piste pour créer des motifs qui se décalent.",
        explanation = "Dans PAGE SETUP, [FUNC] + [YES] passe en mode PER TRACK : chaque piste a sa longueur et sa vitesse (colonne " +
            "TRACK). La colonne PATTERN règle CHANGE (quand un pattern en attente démarre) et RESET (après combien de pas toutes les " +
            "pistes repartent du début ; INF = jamais). Attention : avec RESET sur INF et sans CHANGE, le pattern suivant ne démarre jamais.",
        controls = listOf(
            cmd("[FUNC] + [YES] dans PAGE SETUP", "Bascule PER PATTERN / PER TRACK.", PER_TRACK),
            cmd("LENGTH / SPEED (colonne TRACK)", "Longueur et vitesse de la piste active.", PER_TRACK),
            cmd("CHANGE / RESET (colonne PATTERN)", "Changement de pattern et remise à zéro.", PER_TRACK),
        ),
        steps = listOf(
            "Ouvre [FUNC] + [PAGE], puis [FUNC] + [YES] pour passer en PER TRACK.",
            "Laisse le kick sur 16 pas.",
            "Choisis la piste du charley et règle sa longueur sur 12 pas.",
            "Choisis une percussion et règle 7 pas.",
            "Lance la lecture et écoute les motifs se décaler.",
        ),
        expected = "Le kick reste fixe pendant que les autres pistes tournent sur des cycles différents.",
        exerciseTitle = "Groove qui évolue",
        exercise = listOf(
            "Crée trois pistes de 16, 12 et 5 pas.",
            "Règle RESET pour que tout reparte ensemble toutes les 4 mesures (64 pas).",
        ),
        success = "Le motif évolue puis se recale régulièrement.",
        style = "Tribe / tribecore : les percussions décalées donnent un effet hypnotique.",
        mistakes = listOf(
            "Mettre RESET sur INF sans régler CHANGE : le pattern suivant ne démarre jamais.",
            "Régler la longueur de la mauvaise piste : la colonne TRACK agit sur la piste active.",
        ),
        quiz = listOf(
            qz("Comment passer en mode PER TRACK ?", listOf("[FUNC] + [YES] dans PAGE SETUP", "[TRK] + [PAGE]", "[FUNC] + [TRK]"), 0,
                "Dans PAGE SETUP, [FUNC] + [YES] bascule entre les deux modes.", PER_TRACK),
            qz("Que règle RESET ?",
                listOf("Le volume", "Après combien de pas toutes les pistes repartent du début", "La vitesse"), 1,
                "RESET fixe le nombre de pas avant que toutes les pistes recommencent au premier pas.", PER_TRACK),
            qz("RESET sur INF et CHANGE non réglé : que devient le pattern en attente ?",
                listOf("Il démarre à la fin de la mesure", "Il ne démarre jamais", "Il démarre tout de suite"), 1,
                "Sans CHANGE, un pattern qui boucle à l'infini ne laisse jamais jouer le pattern suivant.", PER_TRACK),
            qz("La colonne TRACK agit sur…", listOf("Toutes les pistes", "La piste active", "Le pattern suivant"), 1,
                "Les réglages TRACK ne concernent que la piste active.", PER_TRACK),
        ),
    )

    private val m5l3 = ctLesson(
        M5, 3, "Le séquenceur euclidien", listOf(EUCLID), 20,
        objective = "Générer des rythmes répartis automatiquement avec deux générateurs d'impulsions.",
        explanation = "[FUNC] + [AMP] ouvre le menu SEQUENCER. EUC active le mode euclidien pour la piste : [RECORD] et les trigs " +
            "générés passent en bleu. PL1 et PL2 donnent le nombre d'impulsions des deux générateurs, réparties aussi régulièrement " +
            "que possible. R01, R02 et TRO décalent les impulsions. OP combine les deux générateurs : OR, XOR, AND ou SUB. Les trigs " +
            "posés à la main sont masqués pendant ce mode ; [FUNC] en désactivant EUC convertit la séquence en trigs normaux.",
        controls = listOf(
            cmd("[FUNC] + [AMP]", "Ouvre le menu SEQUENCER.", EUCLID),
            cmd("EUC", "Active/désactive le mode euclidien.", EUCLID),
            cmd("PL1 / PL2", "Nombre d'impulsions des générateurs 1 et 2.", EUCLID),
            cmd("R01 / R02 / TRO / OP", "Rotations et combinaison des deux générateurs.", EUCLID),
        ),
        steps = listOf(
            "Choisis une piste de percussion et ouvre [FUNC] + [AMP].",
            "Active EUC, règle PL1 sur 5 : cinq trigs se répartissent sur la piste.",
            "Tourne R01 pour décaler le motif et écoute.",
            "Règle PL2 sur 3 et essaie les opérateurs OR, XOR, AND et SUB.",
            "Pour garder le résultat en trigs normaux, maintiens [FUNC] en désactivant EUC.",
        ),
        expected = "La piste joue un rythme régulier qui change selon le nombre d'impulsions, la rotation et l'opérateur.",
        exerciseTitle = "Percussions euclidiennes",
        exercise = listOf(
            "Sur deux pistes de percussions, crée deux rythmes euclidiens différents (par exemple 3 et 5 impulsions).",
            "Décale-les avec les rotations jusqu'à ce qu'ils se répondent.",
        ),
        success = "Les deux pistes forment un motif qui se complète sans se marcher dessus.",
        style = "Tribe : les rythmes euclidiens donnent vite des percussions de type tribal.",
        mistakes = listOf(
            "Chercher à poser des notes à la main en mode euclidien : c'est impossible, seuls les parameter locks restent possibles.",
            "Perdre ses trigs manuels : désactiver EUC avec [FUNC] les remplace par la séquence euclidienne.",
        ),
        quiz = listOf(
            qz("Comment ouvrir le menu SEQUENCER ?", listOf("[FUNC] + [AMP]", "[FUNC] + [PAGE]", "[SRC] + [YES]"), 0,
                "La fonction orange de [AMP] ouvre le menu SEQUENCER (mode euclidien).", EUCLID),
            qz("Que règle PL1 ?", listOf("La vitesse", "Le nombre d'impulsions du générateur 1", "La rotation de la piste"), 1,
                "PL1 et PL2 fixent le nombre d'impulsions de chaque générateur.", EUCLID),
            qz("Avec l'opérateur AND, quels trigs restent ?",
                listOf("Tous les trigs", "Seulement ceux que les deux générateurs placent sur le même pas", "Aucun"), 1,
                "AND ne garde que les trigs communs aux deux générateurs.", EUCLID),
            qz("Comment convertir la séquence euclidienne en trigs normaux ?",
                listOf("Maintenir [FUNC] en désactivant EUC", "Appuyer sur [RECORD]", "Sauver le projet"), 0,
                "Désactiver EUC en maintenant [FUNC] transforme la séquence en trigs normaux.", EUCLID),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(
            M1, 1, "Prise en main",
            "Façade, connexions, organisation des données et navigation.",
            "Brancher la Digitakt II, comprendre projet/pattern/kit/preset et régler un paramètre sans se perdre.",
            "Base de tous les styles : savoir où sont les choses avant de jouer.",
            listOf(m1l1, m1l2, m1l3),
        ),
        CourseModule(
            M2, 2, "Jouer et écouter",
            "Patterns, tempo, swing, clavier et mutes.",
            "Enchaîner des patterns, caler le tempo et construire un break avec les mutes.",
            "Les mutes et les enchaînements sont le cœur du live hardtek et tribe.",
            listOf(m2l1, m2l2, m2l3),
        ),
        CourseModule(
            M3, 3, "Sons : presets, samples et kits",
            "Charger, assigner et sauvegarder les sons.",
            "Construire et sauver ton propre kit.",
            "Un kit personnel donne une identité sonore à tes sets.",
            listOf(m3l1, m3l2, m3l3),
        ),
        CourseModule(
            M4, 4, "Programmer un pattern",
            "GRID, LIVE et STEP RECORDING.",
            "Programmer un rythme et une ligne de basse avec les trois méthodes.",
            "Kick, caisse claire et basse : la base d'un morceau hardtek ou acidcore.",
            listOf(m4l1, m4l2, m4l3),
        ),
        CourseModule(
            M5, 5, "Longueur, pages et euclidien",
            "Pages, vitesse, longueurs par piste et générateurs euclidiens.",
            "Créer des patterns longs, des polyrythmies et des rythmes euclidiens.",
            "Les cycles qui se décalent donnent le côté hypnotique de la tribe.",
            listOf(m5l1, m5l2, m5l3),
        ),
    )
}
