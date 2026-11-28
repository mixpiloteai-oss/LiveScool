package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** Hydrasynth — modules 1 à 4 : prise en main, pads, oscillateurs et mixer, filtres et ampli. */
internal object HydrasynthModulesA {

    // Références du manuel (pages imprimées = pages du PDF)
    private val BRANCHER = hyd("Plug It In", "12")
    private val PATCHES = hyd("Make Some Noise!, Select Patches", "13")
    private val NAV = hyd("Main Systems, Patch selection", "18")
    private val CONCEPTS = hyd("General Concepts", "17")
    private val INIT_RANDOM = hyd("HOME, INIT, RANDOM buttons", "19–20")
    private val PAGES = hyd("EXIT, PAGE Up / Down buttons", "20–21")
    private val SHIFT_PADS = hyd("SHIFT + Pad Functions, Octave Transpose", "26")
    private val PAD_KEY = hyd("Changing the Pad Key, Pad response options", "27")
    private val PAD_MODES = hyd("Pad Modes", "28")
    private val MODULES = hyd("Understanding the Modules", "32")
    private val SINGLE = hyd("Oscillators 1 and 2, Single mode", "35")
    private val WAVESCAN = hyd("WaveScan mode, Oscillator Bit Reduction", "36")
    private val MIXER = hyd("The Mixer Module", "43")
    private val ROUTAGE = hyd("Pan positions, Filter Routing, Filter Configuration", "44")
    private val FILTRE1 = hyd("Filter 1 types and parameters", "45–46")
    private val FILTRE2 = hyd("Filter 2", "48")
    private val AMP = hyd("The Amp Module", "49")

    // ---------------------------------------------------------------------
    // Module 1 — Prise en main
    // ---------------------------------------------------------------------
    private const val M1 = "hyd-m1"

    private val m1l1 = ctLesson(
        M1, 1, "Brancher l'Hydrasynth", listOf(BRANCHER), 10,
        objective = "Relier l'Hydrasynth Desktop à ton système sans risque.",
        explanation = "N'utiliser que l'alimentation fournie (12 V DC, au moins 1,5 A), interrupteur sur OFF pendant le branchement. Baisser " +
            "les enceintes ou couper les voies de la table, relier les sorties jack 6,35 mm arrière à la table ou à l'interface, puis " +
            "allumer. Le Master Volume est un réglage analogique (il ne transmet pas sa position en MIDI, mais réagit au CC 7). Sur le " +
            "Desktop, il règle aussi le casque ; brancher le casque après l'allumage. L'USB est classe MIDI sans pilote, mais ne transporte " +
            "pas d'audio.",
        controls = listOf(
            cmd("Alimentation 12 V DC ≥ 1,5 A", "Seulement celle fournie.", BRANCHER),
            cmd("Sorties arrière", "Vers la table ou l'interface.", BRANCHER),
            cmd("Master Volume", "Sortie et casque (Desktop).", BRANCHER),
            cmd("USB, MIDI In/Out/Thru", "MIDI uniquement.", BRANCHER),
        ),
        steps = listOf(
            "Interrupteur sur OFF, branche l'alimentation fournie.",
            "Baisse ta table, relie les sorties gauche et droite.",
            "Allume l'Hydrasynth, puis monte le Master Volume.",
            "Branche le casque seulement après l'allumage.",
        ),
        expected = "L'Hydrasynth sonne sur ta table sans bruit à l'allumage.",
        exerciseTitle = "Installation propre",
        exercise = listOf("Branche-le en stéréo sur deux voies de ta table, panoramiques gauche et droite."),
        success = "Le son est en stéréo et sans claquement.",
        mistakes = listOf("Compter sur l'USB pour l'audio : il ne transporte que du MIDI."),
        quiz = listOf(
            qz("Alimentation ?", listOf("12 V DC, au moins 1,5 A, celle fournie", "9 V quelconque"), 0, "Seulement celle fournie.", BRANCHER),
            qz("L'USB transporte-t-il l'audio ?", listOf("Non", "Oui"), 0, "MIDI seulement.", BRANCHER),
            qz("Casque sur le Desktop : volume ?", listOf("Master Volume", "Bouton séparé"), 0, "Le clavier a un réglage séparé.", BRANCHER),
            qz("Le Master Volume répond-il au CC 7 ?", listOf("Oui", "Non"), 0, "Mais ne transmet pas sa position.", BRANCHER),
        ),
    )

    private val m1l2 = ctLesson(
        M1, 2, "Choisir les patchs", listOf(PATCHES, NAV), 10,
        objective = "Parcourir les patchs, les banques et tes favoris.",
        explanation = "HOME ramène à la page principale. La grande molette Patch (ou les flèches) avance d'un patch ; SHIFT + molette saute de " +
            "banque ; SHIFT + flèches avance de 10 patchs. BROWSE ouvre le navigateur (tri par numéro, nom ou catégorie, comparaison, " +
            "favoris) ; SHIFT + BROWSE ouvre les favoris (jusqu'à 32 patchs, en 4 banques). Sur le Desktop, octave : SHIFT + pads [Oct −] / " +
            "[Oct +] ; les deux ensemble remettent au centre.",
        controls = listOf(
            cmd("Molette Patch, flèches", "Patch suivant / précédent.", NAV),
            cmd("SHIFT + molette", "Changer de banque.", PATCHES),
            cmd("BROWSE, SHIFT + BROWSE", "Navigateur, favoris.", NAV),
            cmd("SHIFT + [Oct −] / [Oct +]", "Octave des pads.", PATCHES),
        ),
        steps = listOf(
            "Appuie sur HOME, tourne la molette Patch.",
            "SHIFT + molette pour changer de banque.",
            "Ouvre BROWSE et trie par catégorie.",
            "Monte d'une octave avec SHIFT + [Oct +], puis reviens au centre.",
        ),
        expected = "Tu trouves vite un patch par catégorie.",
        exerciseTitle = "Tes favoris",
        exercise = listOf("Mets cinq patchs utiles en live (basse, lead, nappe, FX, arpège) dans tes favoris."),
        success = "SHIFT + BROWSE te donne ces cinq sons d'un coup.",
        mistakes = listOf("Oublier HOME : la molette ne change pas de patch depuis une page d'édition."),
        quiz = listOf(
            qz("Changer de banque ?", listOf("SHIFT + molette Patch", "PAGE haut"), 0, "Saut de banque.", PATCHES),
            qz("Nombre de favoris ?", listOf("32", "8"), 0, "En 4 banques.", NAV),
            qz("Avancer de 10 patchs ?", listOf("SHIFT + flèches", "INIT + flèches"), 0, "Saut de 10.", NAV),
            qz("Remettre l'octave au centre (Desktop) ?", listOf("SHIFT + les deux pads Oct", "HOME"), 0, "Les deux ensemble.", PATCHES),
        ),
    )

    private val m1l3 = ctLesson(
        M1, 3, "Pages, INIT et RANDOM", listOf(CONCEPTS, INIT_RANDOM, PAGES), 15,
        objective = "Éditer un module et repartir de zéro.",
        explanation = "Les boutons d'accès (lettres orange) ouvrent la page d'un module dans l'écran droit ; les boutons de fonction (lettres " +
            "blanches) agissent tout de suite. Autour de l'écran, 8 encodeurs sans fin et 8 boutons. PAGE haut / bas (allumés s'il y a " +
            "d'autres pages), EXIT revient en arrière ou annule. INIT maintenu + bouton d'un paramètre le remet par défaut ; INIT + bouton " +
            "d'accès puis INIT réinitialise un module ; INIT deux fois efface tout le patch (init). RANDOM fonctionne pareil ; RANDOM deux " +
            "fois génère un patch aléatoire. SHIFT + HOME : All Notes Off.",
        controls = listOf(
            cmd("Boutons d'accès (orange)", "Ouvrir un module.", CONCEPTS),
            cmd("PAGE haut / bas, EXIT", "Pages, retour.", PAGES),
            cmd("INIT, INIT ×2", "Réinitialiser paramètre, module, patch.", INIT_RANDOM),
            cmd("RANDOM, RANDOM ×2", "Randomiser.", INIT_RANDOM),
        ),
        steps = listOf(
            "Appuie sur [OSC 1] : sa page s'affiche.",
            "Change la Wave, puis remets-la avec INIT + son bouton.",
            "Appuie deux fois sur INIT : patch vierge.",
            "Essaie RANDOM deux fois, puis EXIT.",
        ),
        expected = "Tu sais éditer, annuler et repartir d'un patch vierge.",
        exerciseTitle = "Patch du hasard",
        exercise = listOf("Génère trois patchs aléatoires et garde le plus intéressant."),
        success = "Un son de départ inattendu à travailler.",
        mistakes = listOf("Faire INIT deux fois sans sauvegarder : le patch en cours est effacé."),
        quiz = listOf(
            qz("Patch vierge ?", listOf("INIT deux fois", "RANDOM deux fois"), 0, "Efface le tampon d'édition.", INIT_RANDOM),
            qz("Note bloquée ?", listOf("SHIFT + HOME", "EXIT"), 0, "All Notes Off.", INIT_RANDOM),
            qz("Boutons orange = …", listOf("Accès à une page", "Action immédiate"), 0, "Les blancs agissent.", CONCEPTS),
            qz("PAGE haut / bas allumés = …", listOf("D'autres pages existent", "Erreur"), 0, "Dans cette direction.", PAGES),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 2 — Les pads du Desktop
    // ---------------------------------------------------------------------
    private const val M2 = "hyd-m2"

    private val m2l1 = ctLesson(
        M2, 1, "Gamme et octave des pads", listOf(SHIFT_PADS), 10,
        objective = "Faire jouer aux pads une gamme précise.",
        explanation = "SHIFT + pads 1–16 (deux premières rangées) choisit la gamme des pads (16 gammes). SHIFT + pad 17 / 18 transpose d'une " +
            "octave (jusqu'à 4 de chaque côté) ; les deux ensemble remettent au centre. SHIFT + pad 19 / 20 change le mode des pads, " +
            "SHIFT + pad 21 [KEY] la tonalité. Maintenir SHIFT affiche gamme et octave. SAVE + SYSTEM SETUP sauvegarde gamme, mode et " +
            "tonalité ; le décalage d'octave n'est pas gardé à l'extinction.",
        controls = listOf(
            cmd("SHIFT + pads 1–16", "Gamme des pads.", SHIFT_PADS),
            cmd("SHIFT + pad 17 / 18", "Octave −/+.", SHIFT_PADS),
            cmd("SHIFT + pad 19 / 20", "Mode des pads.", SHIFT_PADS),
            cmd("SAVE + SYSTEM SETUP", "Garder les réglages des pads.", SHIFT_PADS),
        ),
        steps = listOf(
            "SHIFT + pad 4 : Min Pentatonic.",
            "Joue : toutes les notes sont dans la gamme.",
            "SHIFT + pad 17 pour descendre d'une octave.",
            "SAVE + SYSTEM SETUP pour garder la gamme.",
        ),
        expected = "Les pads ne jouent que des notes de la gamme choisie.",
        exerciseTitle = "Basse en mineur",
        exercise = listOf("Choisis une gamme mineure et improvise une basse une octave plus bas."),
        success = "Aucune fausse note en improvisant.",
        style = "Mentalcore : gammes mineures pour des leads tendus.",
        mistakes = listOf("Croire que l'octave est sauvegardée : elle revient au centre à l'extinction."),
        quiz = listOf(
            qz("Nombre de gammes par SHIFT + pads ?", listOf("16", "4"), 0, "Plus dans le module Voice.", SHIFT_PADS),
            qz("Transposition maximale ?", listOf("4 octaves de chaque côté", "1 octave"), 0, "SHIFT + pad 17 / 18.", SHIFT_PADS),
            qz("Garder gamme et mode ?", listOf("SAVE + SYSTEM SETUP", "SAVE seul"), 0, "Réglages des pads.", SHIFT_PADS),
            qz("La gamme « Minor » des pads correspond à…", listOf("Aeolian", "Dorian"), 0, "Nom de la Voice Scale.", SHIFT_PADS),
        ),
    )

    private val m2l2 = ctLesson(
        M2, 2, "Tonalité, latch et sensibilité", listOf(PAD_KEY), 10,
        objective = "Choisir la note de départ et maintenir les notes.",
        explanation = "SHIFT maintenu, pad [KEY] : les deux rangées du bas montrent une octave chromatique (jaune = tonique actuelle). Un pad " +
            "choisit la nouvelle tonique (ex. pad 10 = C#) ; c'est un réglage global, qui ne transpose pas le MIDI reçu. LATCH maintient " +
            "les notes jusqu'à la suivante ; SHIFT + LATCH active le Sustain sans pédale. La sensibilité à la vélocité et à la pression se " +
            "règle dans System Setup.",
        controls = listOf(
            cmd("SHIFT + [KEY] + pad", "Tonalité des pads.", PAD_KEY),
            cmd("LATCH, SHIFT + LATCH", "Maintien, sustain.", PAD_KEY),
        ),
        steps = listOf(
            "SHIFT + [KEY], puis un pad pour choisir la tonique de ton morceau.",
            "Relâche SHIFT : le pad 1 joue cette tonique.",
            "Appuie sur LATCH et joue un accord : il reste tenu.",
        ),
        expected = "Les pads jouent dans la tonalité de ton morceau.",
        exerciseTitle = "Nappe tenue",
        exercise = listOf("Tiens un accord avec LATCH et tourne les filtres des deux mains."),
        success = "La nappe évolue sans tenir les pads.",
        mistakes = listOf("Attendre que la tonalité des pads transpose le MIDI entrant : ce n'est pas le cas."),
        quiz = listOf(
            qz("Tonalité par défaut ?", listOf("C", "A"), 0, "Modifiable avec [KEY].", PAD_KEY),
            qz("Pad jaune = …", listOf("Tonique actuelle", "Note hors gamme"), 0, "Dans l'écran [KEY].", PAD_KEY),
            qz("Sustain sans pédale ?", listOf("SHIFT + LATCH", "LATCH seul"), 0, "Active/désactive.", PAD_KEY),
            qz("La tonalité des pads est-elle globale ?", listOf("Oui", "Non, par patch"), 0, "Pour tous les presets.", PAD_KEY),
        ),
    )

    private val m2l3 = ctLesson(
        M2, 3, "Les modes des pads", listOf(PAD_MODES), 15,
        objective = "Choisir la disposition des notes sur les 24 pads.",
        explanation = "Chromatic : du pad 1 au pad 24 par demi-tons. Fretboard : colonnes en quartes (comme une basse ; pads 1, 9, 17 = C, F, " +
            "Bb). Octave : toutes les notes de la gamme à la suite (le plus de notes possible). Octave Row : chaque rangée est une octave, " +
            "tonique au début et à la fin ; disponible seulement si la gamme a moins de 8 notes. Les pads jaunes jouent la tonique. En " +
            "Chromatic ou Fretboard, la gamme des pads est ignorée.",
        controls = listOf(
            cmd("SHIFT + pad 19 / 20", "Mode précédent / suivant.", PAD_MODES),
        ),
        steps = listOf(
            "INIT deux fois, gamme Chromatic (SHIFT + pad 1).",
            "Fais défiler les modes avec SHIFT + pad 19.",
            "Choisis Major (SHIFT + pad 2) puis passe en Octave Row.",
        ),
        expected = "Tu sais quel mode convient pour jouer une basse ou des accords.",
        exerciseTitle = "Mode de jeu",
        exercise = listOf("Joue la même mélodie en Fretboard puis en Octave et garde ton préféré."),
        success = "Tu joues plus vite dans le mode choisi.",
        mistakes = listOf("Chercher Octave Row avec une gamme de 8 notes ou plus : il n'est pas disponible."),
        quiz = listOf(
            qz("Mode en quartes ?", listOf("Fretboard", "Octave"), 0, "Comme une basse.", PAD_MODES),
            qz("Octave Row disponible si…", listOf("Gamme de moins de 8 notes", "Toujours"), 0, "Sinon passage en Octave.", PAD_MODES),
            qz("Pads jaunes = …", listOf("Tonique", "Notes interdites"), 0, "Avec quelques exceptions.", PAD_MODES),
            qz("Gamme ignorée en…", listOf("Chromatic et Fretboard", "Octave"), 0, "Ces modes l'ignorent.", PAD_MODES),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 3 — Oscillateurs et mixer
    // ---------------------------------------------------------------------
    private const val M3 = "hyd-m3"

    private val m3l1 = ctLesson(
        M3, 1, "Les oscillateurs en mode Single", listOf(MODULES, SINGLE), 15,
        objective = "Choisir une forme d'onde et accorder les oscillateurs.",
        explanation = "Le signal va de gauche à droite : OSC > MIXER > FILTER > AMP > FX. 3 oscillateurs par voix ; 1 et 2 ont deux modes " +
            "(Single, WaveScan), le 3 seulement Single. En Single : Wave (219 formes d'onde), Semi (±36 demi-tons, SHIFT = sauts de 12), " +
            "Cents (±50), BitRedux, Keytrack (0 à 200 %, 100 % par défaut, 0 = hauteur fixe). Le mode se change avec l'encodeur 1.",
        controls = listOf(
            cmd("OSC 1 / OSC 2 / OSC 3", "Page de l'oscillateur.", SINGLE),
            cmd("Wave, Semi, Cents", "Forme d'onde et accord.", SINGLE),
            cmd("Keytrack", "Suivi du clavier.", SINGLE),
        ),
        steps = listOf(
            "INIT deux fois, puis [OSC 1].",
            "Change la Wave avec l'encodeur 2.",
            "Monte l'OSC 2 de 7 demi-tons (Semi) et écoute la quinte.",
            "Mets le Keytrack de l'OSC 3 à 0 : il joue toujours la même note.",
        ),
        expected = "Deux oscillateurs en quinte et un troisième fixe.",
        exerciseTitle = "Stab en quinte",
        exercise = listOf("Construis un stab avec OSC 1 et OSC 2 à la quinte."),
        success = "Un stab large et plein.",
        style = "Tribe : stabs en quinte sur les contretemps.",
        mistakes = listOf("Keytrack à 0 sans le vouloir : la note ne suit plus les pads."),
        quiz = listOf(
            qz("Nombre de formes d'onde ?", listOf("219", "16"), 0, "Waveform List.", SINGLE),
            qz("Plage de Semi ?", listOf("±36 demi-tons", "±12"), 0, "SHIFT = sauts de 12.", SINGLE),
            qz("Keytrack à 0 % ?", listOf("Hauteur fixe", "Deux octaves"), 0, "200 % = deux octaves par octave jouée.", SINGLE),
            qz("Oscillateur sans WaveScan ?", listOf("OSC 3", "OSC 1"), 0, "Seulement Single.", SINGLE),
        ),
    )

    private val m3l2 = ctLesson(
        M3, 2, "WaveScan et réduction de bits", listOf(WAVESCAN), 15,
        objective = "Faire évoluer le timbre en parcourant plusieurs formes d'onde.",
        explanation = "En WaveScan, une Wavelist contient jusqu'à 8 formes d'onde (WAV 1 à 8). Le paramètre WaveScan (1,0 à 8,0) choisit la " +
            "position ; une valeur intermédiaire (1,5) mélange deux formes. On le module avec un LFO via la matrice. Dans Wavelist Edit, " +
            "le bouton d'un WAV le fait écouter seul ; SHIFT + encodeur change ce WAV et les suivants. BitRedux réduit la résolution de " +
            "l'oscillateur (jusqu'à 2 bits) : son dégradé, « pixelisé ».",
        controls = listOf(
            cmd("Wavelist Edit (bouton 2)", "Choisir les 8 formes d'onde.", WAVESCAN),
            cmd("WaveScan", "Position 1,0 à 8,0.", WAVESCAN),
            cmd("BitRedux", "Dégradation numérique.", WAVESCAN),
        ),
        steps = listOf(
            "[OSC 1], passe en WaveScan (encodeur 1).",
            "Ouvre Wavelist Edit et remplis 4 WAV.",
            "Tourne WaveScan en tenant une note : le timbre change.",
            "Monte BitRedux sur une note grave.",
        ),
        expected = "Un timbre qui glisse d'une forme d'onde à l'autre.",
        exerciseTitle = "Basse qui mute",
        exercise = listOf("Crée une basse dont la WaveScan est balayée par un LFO lent."),
        success = "La basse change de couleur sans changer de note.",
        style = "Mentalcore : timbres qui mutent et sons dégradés.",
        mistakes = listOf("Mettre WAV 1 sur OFF : impossible, il doit contenir une forme d'onde."),
        quiz = listOf(
            qz("Formes d'onde dans une Wavelist ?", listOf("Jusqu'à 8", "219"), 0, "WAV 1 à 8.", WAVESCAN),
            qz("WaveScan = 2,5 ?", listOf("Mélange WAV 2 et WAV 3", "WAV 5"), 0, "Mi-chemin.", WAVESCAN),
            qz("Résolution minimale de BitRedux ?", listOf("2 bits", "8 bits"), 0, "Son très dégradé.", WAVESCAN),
            qz("Écouter un WAV seul ?", listOf("Son bouton dans Wavelist Edit", "SHIFT + HOME"), 0, "Audition isolée.", WAVESCAN),
        ),
    )

    private val m3l3 = ctLesson(
        M3, 3, "Le mixer", listOf(MIXER, ROUTAGE), 15,
        objective = "Doser les sources et les envoyer vers les filtres.",
        explanation = "[MIXER] page 1 : niveaux (0 à 128) des OSC 1–3 (en haut), Ring et Noise (en bas) ; bouton 8 = Solo, puis le bouton " +
            "d'une source pour l'écouter seule. Page 2 : panoramique des OSC 1–3 (haut) et répartition entre les filtres (bas : 100:0 = " +
            "Filtre 1 seul, 0:100 = Filtre 2 seul). Page 3 : idem pour Ring et Noise, et configuration des filtres Series (le 1 passe dans " +
            "le 2) ou Parallel. SHIFT + encodeur = réglage fin.",
        controls = listOf(
            cmd("MIXER page 1", "Niveaux, Solo.", MIXER),
            cmd("MIXER page 2–3", "Pan, routage vers les filtres.", ROUTAGE),
            cmd("Series / Parallel", "Configuration des filtres.", ROUTAGE),
        ),
        steps = listOf(
            "[MIXER], monte OSC 2 et un peu de Noise.",
            "Active Solo (bouton 8) et écoute chaque source.",
            "Page 2 : écarte OSC 1 et OSC 2 en stéréo.",
            "Page 3 : passe les filtres en Parallel.",
        ),
        expected = "Les sources sont équilibrées et réparties dans l'espace.",
        exerciseTitle = "Lead large",
        exercise = listOf("Écarte deux oscillateurs à gauche et à droite et envoie-les chacun vers un filtre différent."),
        success = "Le lead est large et chaque côté a sa couleur.",
        mistakes = listOf("Oublier de couper Solo : les autres sources restent muettes."),
        quiz = listOf(
            qz("100:0 dans le routage = …", listOf("Filtre 1 seulement", "Filtre 2 seulement"), 0, "0:100 = Filtre 2.", ROUTAGE),
            qz("Activer Solo ?", listOf("Bouton 8 page 1", "SHIFT + MIXER"), 0, "Puis choisir la source.", MIXER),
            qz("En Series…", listOf("Le Filtre 1 passe dans le Filtre 2", "Les filtres sont séparés"), 0, "Parallel = séparés.", ROUTAGE),
            qz("Réglage fin ?", listOf("SHIFT + encodeur", "INIT + encodeur"), 0, "Pour tous les niveaux.", MIXER),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 4 — Filtres et ampli
    // ---------------------------------------------------------------------
    private const val M4 = "hyd-m4"

    private val m4l1 = ctLesson(
        M4, 1, "Le Filtre 1", listOf(FILTRE1), 15,
        objective = "Choisir un type de filtre et régler coupure et résonance.",
        explanation = "Le Filtre 1 propose 16 modèles : ladder 12/24 dB compensés ou non, LP Gate, MS20 LP/HP, 3-Ler LP/BP/HP, Stn12 " +
            "LP/BP/HP, LP 1 Pole (6 dB), LP 8 Pole (48 dB), Vowel. Non compensé : les graves baissent quand la résonance monte ; compensé : " +
            "ils restent. Paramètres : Cutoff, Resonance, ENV 1 amount, LFO 1 amount (±64, liaisons pré-câblées), Vel Env, Keytrack. Les " +
            "boutons Filter du panneau règlent cutoff, résonance et drive du Filtre 1.",
        controls = listOf(
            cmd("FILTER 1 > Type", "16 modèles.", FILTRE1),
            cmd("Cutoff, Resonance", "Coupure, résonance.", FILTRE1),
            cmd("ENV 1 amount, LFO 1 amount", "Modulations pré-câblées.", FILTRE1),
            cmd("Vel Env, Keytrack", "Vélocité, suivi du clavier.", FILTRE1),
        ),
        steps = listOf(
            "[FILTER 1], choisis LP Ldr24.",
            "Ferme la coupure, monte la résonance.",
            "Passe en LP Fat24 (compensé) : les graves restent.",
            "Monte ENV 1 amount pour un filtre qui claque.",
        ),
        expected = "Tu entends la différence compensé / non compensé.",
        exerciseTitle = "Basse acid",
        exercise = listOf("LP 24 dB, résonance haute, ENV 1 amount positif, joue la coupure en direct."),
        success = "La basse crie à l'ouverture du filtre.",
        style = "Acidcore : filtre résonant joué à la main.",
        mistakes = listOf("Choisir un filtre non compensé et perdre les graves avec beaucoup de résonance."),
        quiz = listOf(
            qz("Modèles du Filtre 1 ?", listOf("16", "4"), 0, "Dont Vowel.", FILTRE1),
            qz("Filtre compensé ?", listOf("Garde les graves avec la résonance", "Coupe les graves"), 0, "Non compensé : graves réduits.", FILTRE1),
            qz("Pente de LP 8 Pole ?", listOf("48 dB", "12 dB"), 0, "Très raide.", FILTRE1),
            qz("Modulation pré-câblée du Filtre 1 ?", listOf("ENV 1 et LFO 1", "ENV 2 et LFO 2"), 0, "ENV 2 / LFO 2 vont à l'ampli.", FILTRE1),
        ),
    )

    private val m4l2 = ctLesson(
        M4, 2, "Le Filtre 2 et son morph", listOf(FILTRE2, ROUTAGE), 10,
        objective = "Utiliser le filtre multimode et le morph.",
        explanation = "Le Filtre 2 est un 2 pôles à variables d'état, en deux types : LP-BP-HP ou LP-NO-HP. Morph passe en continu de passe-bas " +
            "(0) à passe-bande ou réjection (64) puis passe-haut (128) ; plus la résonance est haute, plus la différence s'entend. Mêmes " +
            "paramètres que le Filtre 1 sinon. Sur le panneau, quand le Filtre 2 est choisi, le 3e bouton fait le morph (au lieu du drive).",
        controls = listOf(
            cmd("FILTER 2 > Type", "LP-BP-HP ou LP-NO-HP.", FILTRE2),
            cmd("Morph", "0 = LP, 64 = BP/NO, 128 = HP.", FILTRE2),
        ),
        steps = listOf(
            "[FILTER 2], type LP-BP-HP.",
            "Tourne Morph de 0 à 128 en tenant une note.",
            "Passe en LP-NO-HP, monte la résonance et recommence.",
        ),
        expected = "Le son passe de sombre à fin en un seul geste.",
        exerciseTitle = "Transition au morph",
        exercise = listOf("Fais une montée en passant le Filtre 2 de LP à HP sur 8 mesures."),
        success = "La montée fait disparaître les graves progressivement.",
        style = "Hardtek / tribe : passe-haut avant le retour du kick.",
        mistakes = listOf("Chercher le drive sur le panneau avec le Filtre 2 sélectionné : le bouton fait le morph."),
        quiz = listOf(
            qz("Morph à 64 en LP-BP-HP ?", listOf("Passe-bande", "Passe-haut"), 0, "BP ou NO au milieu.", FILTRE2),
            qz("Morph à 128 ?", listOf("Passe-haut", "Passe-bas"), 0, "Extrême HP.", FILTRE2),
            qz("Types du Filtre 2 ?", listOf("LP-BP-HP et LP-NO-HP", "16 modèles"), 0, "Deux types.", FILTRE2),
            qz("Où régler Series / Parallel ?", listOf("Mixer page 3 ou Filtre 1 page 2", "Filtre 2"), 0, "Paramètre lié.", ROUTAGE),
        ),
    )

    private val m4l3 = ctLesson(
        M4, 3, "Le module Amp", listOf(AMP), 10,
        objective = "Régler le niveau, la vélocité et le trémolo de l'ampli.",
        explanation = "Le module Amp a trois paramètres : LFO 2 amount (±64, trémolo ; négatif = phase inversée), Velocity (±64, 0 = pas de " +
            "réponse ; négatif = jouer fort baisse le volume) et Amp Level (0 à 128), le plafond avant les effets. LFO 2 et Velocity agissent " +
            "sous ce plafond, ce qui évite la saturation des effets. ENV 2 règle le volume de chaque note via Amp Level.",
        controls = listOf(
            cmd("LFO 2 amount", "Trémolo.", AMP),
            cmd("Velocity", "Réponse à la vélocité.", AMP),
            cmd("Amp Level", "Niveau avant effets.", AMP),
        ),
        steps = listOf(
            "[AMP], monte LFO 2 amount : trémolo.",
            "Mets Velocity à 0 : tous les coups au même volume.",
            "Baisse Amp Level si les effets saturent.",
        ),
        expected = "Un niveau stable avant les effets.",
        exerciseTitle = "Trémolo calé",
        exercise = listOf("Règle un trémolo sur une nappe avec LFO 2 synchronisé au tempo."),
        success = "La nappe pulse en rythme.",
        style = "Tribe : une nappe qui pulse au tempo.",
        mistakes = listOf("Amp Level trop haut : les effets peuvent saturer."),
        quiz = listOf(
            qz("Paramètres du module Amp ?", listOf("3", "8"), 0, "LFO 2 amount, Velocity, Amp Level.", AMP),
            qz("Velocity à 0 ?", listOf("Aucune réponse à la vélocité", "Silence"), 0, "Volume constant.", AMP),
            qz("Amp Level est…", listOf("Le plafond avant les effets", "Le volume du casque"), 0, "Évite l'écrêtage.", AMP),
            qz("LFO 2 amount négatif ?", listOf("Phase du LFO inversée", "Arrêt du LFO"), 0, "Saw Up devient Saw Down.", AMP),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(M1, 1, "Prise en main", "Branchements, patchs, navigation, INIT et RANDOM.",
            "Installer l'Hydrasynth et s'y repérer.", "Des favoris rangés = des sons trouvés vite en live.", listOf(m1l1, m1l2, m1l3)),
        CourseModule(M2, 2, "Les pads du Desktop", "Gammes, octave, tonalité, latch, modes des pads.",
            "Jouer juste sur les pads.", "Gammes tendues et notes tenues pour la mentalcore.", listOf(m2l1, m2l2, m2l3)),
        CourseModule(M3, 3, "Oscillateurs et mixer", "Single, WaveScan, BitRedux, niveaux, pan, routage.",
            "Fabriquer la matière sonore.", "Timbres qui mutent et sons dégradés.", listOf(m3l1, m3l2, m3l3)),
        CourseModule(M4, 4, "Filtres et ampli", "Filtre 1, Filtre 2, morph, module Amp.",
            "Sculpter et équilibrer le son.", "Filtre résonant et morph pour les montées.", listOf(m4l1, m4l2, m4l3)),
    )
}
