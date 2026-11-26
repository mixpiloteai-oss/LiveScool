package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** Circuit Tracks — modules 10 à 12 : MIDI, construction d'un morceau, live. */
internal object CircuitTracksModulesD {

    private val ARRIERE = ct("Présentation du matériel › Vue arrière", "19")
    private val MIDI_INTRO = ct("Les pistes MIDI › Introduction", "58")
    private val MODELES = ct("Les pistes MIDI › Choisir les modèles", "58–59")
    private val SORTIE_MIDI = ct("Les pistes MIDI › Connecter l'appareil via la sortie MIDI", "60")
    private val SETUP = ct("Annexe › La vue Setup", "102–103")
    private val CANAUX = ct("Annexe › La vue Setup › Les canaux MIDI", "103")
    private val RX_TX = ct("Annexe › La vue Setup › Entrée/sortie MIDI", "104")
    private val HORLOGE = ct("Annexe › La vue Setup › Le réglage de l'horloge", "105")
    private val SETUP_AVANCE = ct("Annexe › La vue Setup avancée", "106")
    private val PATCH_EXT = ct("Les synthétiseurs › Choisir un patch externe", "35")
    private val CLAVIER_EXT = ct("Les synthétiseurs › Enregistrer à partir d'un contrôleur externe", "38–39")
    private val TEMPO = ct("Le tempo et le swing › Le tempo", "85")
    private val HORLOGE_EXT = ct("Le tempo et le swing › L'horloge externe", "85–86")
    private val SYNC_OUT = ct("Le tempo et le swing › La sortie synchronisation analogique", "87")
    private val MIXER = ct("Mélangeur", "88")
    private val VUE_PATTERNS = ct("Les patterns › La vue Patterns", "73–74")
    private val SUPPR_PATTERNS = ct("Les patterns › Supprimer des patterns", "74")
    private val DUPL_PATTERNS = ct("Les patterns › Dupliquer des patterns", "75")
    private val CHAINES = ct("Les patterns › Le chaînage des patterns", "76–79")
    private val TRANSPOSER = ct("Les patterns › Transposer des patterns à l'octave", "79")
    private val VERROU = ct("Les patterns › Verrouiller la vue", "80")
    private val SCENES = ct("Les patterns › Scènes", "81–83")
    private val CHAINE_SCENES = ct("Les patterns › Chaîner les scènes", "83–84")
    private val FILE_SCENES = ct("Les patterns › Mettre des scènes en file d'attente", "84")
    private val CHARGER = ct("Principes élémentaires › Le chargement et la sauvegarde", "23")
    private val CHANGER_PROJET = ct("Projets › Changer de projet", "95")
    private val COULEURS = ct("Projets › Changer les couleurs des projets", "96")
    private val MUTE_JEU = ct("Mélangeur › Jouer quand le son des pistes est coupé", "89")
    private val TAP = ct("Le tempo et le swing › Battue du tempo", "86")
    private val SWING = ct("Le tempo et le swing › Le swing", "86–87")
    private val FILTRE = ct("Le potentiomètre de filtre", "94")
    private val PACKS = ct("Les packs", "97–98")
    private val MICROSD = ct("Les packs › Utiliser des cartes microSD", "99–100")
    private val BRIGHTNESS = ct("Annexe › La vue Setup › Brightness", "103")
    private val SAVE_LOCK = ct("Annexe › La fonction Save Lock et chargement des projets", "107")
    private val ALIM = ct("Introduction › Alimentation", "11")

    // ---------------------------------------------------------------------
    // Module 10 — MIDI et contrôle d'autres machines
    // ---------------------------------------------------------------------
    private const val M10 = "ct-m10"

    private val m10l1 = ctLesson(
        M10, 1, "Séquencer une machine externe avec les pistes MIDI", listOf(MIDI_INTRO, MODELES, SORTIE_MIDI), 20,
        objective = "Faire jouer un synthé ou une boîte à rythmes externe depuis MIDI 1 ou MIDI 2, et piloter ses paramètres avec les Macros.",
        explanation = "Une piste MIDI se programme comme un synthé (notes, vélocité, gate, probabilité…) mais envoie ses notes en MIDI. " +
            "Ses Macros envoient des CC selon le modèle choisi dans la vue Preset (8 modèles). Les modèles par défaut sont identiques : " +
            "CC 1, 2, 5, 11, 12, 13, 71 et 74 (par exemple 71 résonance, 74 fréquence du filtre sur beaucoup de synthés). " +
            "On les modifie dans Components. Le guide conseille de ramener l'audio de la machine externe dans les entrées de la Circuit Tracks : " +
            "la piste MIDI règle alors son niveau, son panoramique et ses envois d'effets.",
        controls = listOf(
            cmd("MIDI 1 / MIDI 2", "Pistes qui envoient notes et CC.", MIDI_INTRO),
            cmd("Preset (piste MIDI)", "Vue des 8 modèles (rangée du haut).", MODELES),
            cmd("Macros 1 à 8 (piste MIDI)", "Envoient les CC du modèle.", MODELES),
            cmd("Prise MIDI Out (DIN)", "Vers l'entrée MIDI de la machine externe.", SORTIE_MIDI),
            cmd("Entrées 1 et 2 (arrière)", "Retour audio de la machine externe.", ARRIERE),
        ),
        steps = listOf(
            "Relie MIDI Out de la Circuit Tracks à MIDI In de ta machine externe.",
            "Règle la machine externe pour recevoir sur le canal 3 (canal par défaut de MIDI 1).",
            "Branche sa sortie audio sur l'entrée 1 de la Circuit Tracks.",
            "Sélectionne MIDI 1, joue quelques notes, puis enregistre une boucle avec Record.",
            "Tourne la Macro 8 (CC 74) : si ta machine suit ce CC, son filtre bouge.",
            "Ouvre le Mixer et règle le niveau de l'entrée 1 avec la Macro 3.",
        ),
        expected = "La machine externe joue la boucle de MIDI 1, et son son passe par le Mixer de la Circuit Tracks.",
        exerciseTitle = "Ta seconde machine",
        exercise = listOf(
            "Programme une ligne de 16 pas sur MIDI 1 pour une machine externe.",
            "Automatise un CC (Macro 7 ou 8) sur la seconde moitié du pattern.",
        ),
        success = "La machine externe joue en rythme, et le CC automatisé change son son.",
        mistakes = listOf(
            "La machine externe ne réagit pas : vérifie dans la vue Setup que les pads Tx de la rangée du bas sont allumés (p. 60).",
            "Le canal ne correspond pas : MIDI 1 envoie sur le canal 3 et MIDI 2 sur le canal 4 par défaut (p. 103).",
            "Les CC ne collent pas à ta machine : adapte le modèle dans Components (p. 59).",
        ),
        quiz = listOf(
            qz("Quel CC envoie la Macro 8 avec un modèle par défaut ?",
                listOf("CC 1", "CC 71", "CC 74"), 2,
                "Macro 7 = CC 71 (résonance), Macro 8 = CC 74 (fréquence du filtre).", MODELES),
            qz("Combien de modèles MIDI propose une piste MIDI ?",
                listOf("4", "8", "16"), 1,
                "Huit modèles, sur la rangée du haut de la vue Preset.", MODELES),
            qz("Où modifie-t-on les CC d'un modèle ?",
                listOf("Dans la vue Setup", "Dans Novation Components", "Dans la vue Scales"), 1,
                "Le Components Editor définit CC, plages et polarité.", MODELES),
            qz("Changer de modèle efface-t-il l'automation déjà enregistrée ?",
                listOf("Oui", "Non, elle s'applique au nouveau CC de la Macro", "Seulement sur MIDI 2"), 1,
                "L'automation reste et pilote le nouveau message assigné à la Macro.", MODELES),
        ),
    )

    private val m10l2 = ctLesson(
        M10, 2, "Canaux, vue Setup et contrôleur externe", listOf(SETUP, CANAUX, RX_TX, CLAVIER_EXT, PATCH_EXT, SETUP_AVANCE), 20,
        objective = "Régler les canaux MIDI, filtrer ce qui entre et sort, et jouer les synthés depuis un clavier.",
        explanation = "La vue Setup s'ouvre avec Shift + Save et se ferme avec Play, qui enregistre les réglages. " +
            "Canaux par défaut : Synth 1 = 1, Synth 2 = 2, MIDI 1 = 3, MIDI 2 = 4, les quatre Drum = 10 ; le canal 16 est réservé au projet. " +
            "Deux pistes ne peuvent pas avoir le même canal. Pads 25 à 32 : réception (Rx) et transmission (Tx) des notes, des CC, " +
            "des Program Change et de l'horloge, tout est actif par défaut. Un clavier MIDI joue Synth 1 sur le canal 1 et Synth 2 sur le canal 2, " +
            "dans la gamme choisie. La vue Setup avancée (Shift tenu à l'allumage) permet de faire du MIDI Thru une seconde sortie MIDI.",
        controls = listOf(
            cmd("Shift + Save", "Ouvre la vue Setup ; Play la quitte et enregistre.", SETUP),
            cmd("Touche de piste + rangées 1-2 (vue Setup)", "Choisit le canal MIDI de la piste.", CANAUX),
            cmd("Pads 25 à 32 (vue Setup)", "Rx/Tx des notes, CC, Program Change et horloge.", RX_TX),
            cmd("Shift à l'allumage puis Duplicate", "MIDI Thru en seconde sortie MIDI (vert) ou Thru normal.", SETUP_AVANCE),
        ),
        steps = listOf(
            "Shift + Save : repère les canaux de Synth 1 (violet) et Synth 2 (vert pâle).",
            "Appuie sur MIDI 2 et choisis le canal 5 sur les rangées du haut.",
            "Repère les pads 25 à 32 et vérifie qu'ils sont tous allumés vivement.",
            "Appuie sur Play pour sauvegarder et sortir.",
            "Branche un clavier MIDI sur MIDI In, joue sur le canal 1 : c'est Synth 1 qui sonne.",
            "Envoie un Program Change depuis le clavier sur le canal 2 pour changer le patch de Synth 2.",
        ),
        expected = "Les canaux correspondent à ton installation et ton clavier joue les synthés internes.",
        exerciseTitle = "Plan MIDI du set",
        exercise = listOf(
            "Écris le canal de chaque piste et de chaque machine externe de ton set.",
            "Règle-les dans la vue Setup et vérifie chaque liaison en jouant une note.",
        ),
        success = "Chaque appareil répond à sa piste, sans note jouée en double.",
        mistakes = listOf(
            "Quitter la vue Setup autrement que par Play : c'est Play qui enregistre et ferme (p. 103).",
            "Mettre deux pistes sur le même canal : ce n'est pas possible (p. 103).",
            "Attendre des fausses notes d'un clavier : la machine cale les notes sur la gamme ; choisis Chromatique pour toutes les notes (p. 39).",
        ),
        quiz = listOf(
            qz("Comment ouvrir la vue Setup ?",
                listOf("Shift + Save", "Shift + Projects", "Shift + FX"), 0,
                "On en sort avec Play.", SETUP),
            qz("Sur quel canal MIDI sont les pistes Drum par défaut ?",
                listOf("4", "10", "16"), 1,
                "Les quatre pistes Drum partagent le canal 10.", CANAUX),
            qz("Quel canal est réservé au projet ?",
                listOf("Le canal 1", "Le canal 10", "Le canal 16"), 2,
                "Les pistes peuvent utiliser les canaux 1 à 15.", CANAUX),
            qz("Quels types de données se règlent en Rx/Tx dans la vue Setup ?",
                listOf("Notes, CC, Program Change, horloge", "Audio et MIDI", "Seulement les notes"), 0,
                "Quatre paires de pads, 25 à 32.", RX_TX),
        ),
    )

    private val m10l3 = ctLesson(
        M10, 3, "Horloge, synchronisation et entrées audio", listOf(TEMPO, HORLOGE_EXT, HORLOGE, SYNC_OUT, ARRIERE, MIXER), 20,
        objective = "Rendre la Circuit Tracks maître ou esclave du tempo et synchroniser une machine analogique.",
        explanation = "Le tempo interne va de 40 à 240 BPM. Avec la réception d'horloge active (mode AUTO), une horloge MIDI externe valide, " +
            "par USB ou MIDI In, prend la main : « SYN » s'affiche quand tu tournes la Macro 1 dans la vue Tempo. Elle peut aller de 30 à 300 BPM, " +
            "valeurs décimales comprises. Si elle disparaît, la lecture s'arrête. Avec la transmission active, la Circuit Tracks envoie son horloge " +
            "sur USB et MIDI Out. La prise Sync envoie une impulsion analogique 5 V, 2 par noire par défaut, réglable (1, 2, 4, 8 ou 24) ; " +
            "le swing n'y est pas appliqué. L'USB ne transporte pas d'audio : les machines externes reviennent par les entrées 1 et 2.",
        controls = listOf(
            cmd("Tempo/Swing + Macro 1", "Tempo interne ; « SYN » en horloge externe.", TEMPO),
            cmd("Pads 31-32 (vue Setup)", "Réception / transmission de l'horloge MIDI.", HORLOGE),
            cmd("Pads 17 à 21 (vue Setup)", "Cadence de la sortie Sync : 1, 2, 4, 8, 24 ppqn.", HORLOGE),
            cmd("Prise Sync (mini-jack)", "Horloge analogique 5 V.", ARRIERE),
            cmd("Entrées 1 et 2", "Audio de niveau ligne mixé avec les pistes internes.", ARRIERE),
        ),
        steps = listOf(
            "Vue Tempo : règle 150 BPM avec la Macro 1.",
            "Relie MIDI Out à une machine externe réglée en horloge externe : elle suit la Circuit Tracks.",
            "Pour une machine analogique, branche la prise Sync et choisis sa cadence dans la vue Setup (pads 17 à 21).",
            "Inverse les rôles : une autre machine envoie son horloge, tourne la Macro 1 et vérifie l'affichage « SYN ».",
            "Débranche l'horloge externe : la lecture s'arrête, Play relance au tempo du projet.",
        ),
        expected = "Les deux machines restent calées, dans un sens puis dans l'autre.",
        exerciseTitle = "Maître et esclave",
        exercise = listOf(
            "Fais tourner la Circuit Tracks en maître pendant 2 minutes avec une machine externe.",
            "Puis en esclave : vérifie « SYN » et qu'aucun décalage n'apparaît.",
        ),
        success = "Aucune dérive de tempo entre les machines dans les deux configurations.",
        mistakes = listOf(
            "Tourner la Macro 1 en esclave pour changer le tempo : sans effet tant que l'horloge externe est là (p. 85).",
            "S'étonner de l'arrêt quand l'horloge externe est débranchée : c'est le comportement prévu (p. 86).",
            "Compter sur le swing pour la sortie Sync : il n'y est pas appliqué (p. 105).",
        ),
        quiz = listOf(
            qz("Quelle plage de tempo propose l'horloge interne ?",
                listOf("30 à 300 BPM", "40 à 240 BPM", "60 à 200 BPM"), 1,
                "De 40 à 240 BPM, valeurs entières.", TEMPO),
            qz("Que signifie « SYN » affiché sur la grille ?",
                listOf("La synchronisation analogique est coupée", "Une horloge MIDI externe pilote le tempo", "Le Sidechain est actif"), 1,
                "Il apparaît quand tu tournes la Macro 1 en horloge externe.", HORLOGE_EXT),
            qz("Quelle est la cadence par défaut de la sortie Sync ?",
                listOf("1 ppqn", "2 ppqn", "24 ppqn"), 1,
                "Deux impulsions par noire.", SYNC_OUT),
            qz("Le port USB transmet-il l'audio ?",
                listOf("Oui", "Non", "Seulement en stéréo"), 1,
                "Il sert à l'alimentation, au MIDI et au firmware.", ARRIERE),
        ),
    )

    private val m10 = CourseModule(
        id = M10, number = 10, title = "MIDI et contrôle d'autres machines",
        summary = "Pistes MIDI, modèles CC, canaux, horloge, Sync, entrées audio.",
        goal = "Faire de la Circuit Tracks le centre d'un set multi-machines synchronisé.",
        styleFocus = "Piloter une seconde machine (basse acide, boîte à rythmes) au même tempo et dans le même mix.",
        lessons = listOf(m10l1, m10l2, m10l3),
    )

    // ---------------------------------------------------------------------
    // Module 11 — Construction d'un morceau
    // ---------------------------------------------------------------------
    private const val M11 = "ct-m11"

    private val m11l1 = ctLesson(
        M11, 1, "Huit patterns par piste", listOf(VUE_PATTERNS, SUPPR_PATTERNS, DUPL_PATTERNS), 15,
        objective = "Créer des variations d'un pattern et passer de l'une à l'autre.",
        explanation = "La vue Patterns montre une colonne par piste et ses patterns : 1 à 4 sur la page 1, 5 à 8 sur la page 2 (touches fléchées). " +
            "Le pattern qui joue clignote. Touche un autre pad pour le choisir : en lecture, il attend la fin du pattern en cours ; " +
            "avec Shift, il démarre tout de suite au même pas pour garder le timing. Duplicate copie un pattern dans un autre emplacement, " +
            "y compris d'un synthé vers une piste MIDI, mais jamais entre une piste Drum et une piste de synthé ou MIDI. Clear + pad vide un pattern.",
        controls = listOf(
            cmd("Patterns", "Vue des patterns de chaque piste.", VUE_PATTERNS),
            cmd("Pad de pattern / Shift + pad", "Change de pattern en fin de boucle / immédiatement.", VUE_PATTERNS),
            cmd("Duplicate + source + destination", "Copie un pattern.", DUPL_PATTERNS),
            cmd("Clear + pad de pattern", "Vide un pattern.", SUPPR_PATTERNS),
        ),
        steps = listOf(
            "Ouvre Patterns : repère la colonne de chaque piste.",
            "Tiens Duplicate, touche le pattern 1 de Drum 1 puis son pattern 2.",
            "Sélectionne le pattern 2 de Drum 1 et modifie-le (kick plus chargé).",
            "En lecture, alterne entre 1 et 2 : le changement arrive en fin de boucle.",
            "Refais-le en tenant Shift : le changement est immédiat, sans décalage.",
        ),
        expected = "Deux versions du kick que tu enchaînes à volonté, en douceur ou instantanément.",
        exerciseTitle = "Variations",
        exercise = listOf(
            "Crée 3 variations de la basse sur Synth 1 (patterns 1, 2, 3) par copie puis modification.",
            "Change de variation en lecture sans arrêter le kick.",
        ),
        success = "Trois variations audibles, enchaînées sans trou.",
        mistakes = listOf(
            "Copier un pattern de batterie vers un synthé : c'est impossible, Drum ne se copie qu'entre pistes Drum (p. 75).",
            "Croire qu'un pattern appartient à toutes les pistes : chaque colonne est une piste (p. 73).",
            "Oublier la page 2 : les patterns 5 à 8 sont derrière les touches fléchées (p. 73).",
        ),
        quiz = listOf(
            qz("Où sont les patterns 5 à 8 dans la vue Patterns ?",
                listOf("Sur la page 2", "Sur les rangées du bas", "Dans la vue Mixer"), 0,
                "Les touches fléchées changent de page.", VUE_PATTERNS),
            qz("Que fait Shift + pad de pattern en lecture ?",
                listOf("Attend la fin de la boucle", "Démarre tout de suite au même pas", "Efface le pattern"), 1,
                "La position de lecture est conservée pour garder le timing.", VUE_PATTERNS),
            qz("Peut-on copier un pattern de Synth 1 vers MIDI 2 ?",
                listOf("Oui", "Non", "Seulement à l'arrêt"), 0,
                "Synthé et MIDI s'échangent ; Drum seulement vers Drum.", DUPL_PATTERNS),
            qz("Comment vider un pattern ?",
                listOf("Clear + pad du pattern", "Shift + pad", "Duplicate deux fois"), 0,
                "Clear et le pad s'allument en rouge pour confirmer.", SUPPR_PATTERNS),
        ),
    )

    private val m11l2 = ctLesson(
        M11, 2, "Chaîner les patterns, transposer, verrouiller la vue", listOf(CHAINES, TRANSPOSER, VERROU), 20,
        objective = "Allonger une piste avec une chaîne de patterns et éditer un pattern sans couper la lecture.",
        explanation = "Pour chaîner, tiens le pattern de départ et touche le dernier : tous ceux entre les deux jouent à la suite, en boucle. " +
            "Les patterns doivent être consécutifs (1-2-3, pas 1-2-6). Chaque piste a sa chaîne : la plus longue fixe la durée totale, " +
            "jusqu'à 256 pas (8 × 32). Shift + touche fléchée transpose d'une octave le pattern d'un synthé ou d'une piste MIDI. " +
            "Shift + Patterns verrouille la vue : tu édites le pattern affiché pendant que la chaîne continue de jouer.",
        controls = listOf(
            cmd("Tenir un pattern + toucher le dernier", "Crée une chaîne de patterns consécutifs.", CHAINES),
            cmd("Shift + touche fléchée", "Transpose le pattern d'une octave.", TRANSPOSER),
            cmd("Shift + Patterns", "Verrouille / déverrouille la vue des pas.", VERROU),
        ),
        steps = listOf(
            "Synth 1 avec des patterns 1 à 4 différents : tiens le 1 et touche le 4.",
            "Drum 1 avec seulement le pattern 1 : il se répète 4 fois pendant la chaîne de la basse.",
            "Shift + Patterns pour verrouiller, puis Shift + pad du pattern 3 pour l'afficher et le modifier pendant la lecture.",
            "Shift + touche fléchée du haut sur le pattern 4 : il monte d'une octave.",
            "Shift + Patterns de nouveau pour déverrouiller.",
        ),
        expected = "Une basse de 4 patterns qui tourne sous un kick d'un seul pattern, éditée sans arrêt.",
        exerciseTitle = "Phrase de 64 pas",
        exercise = listOf(
            "Construis une chaîne de 4 patterns de 16 pas sur la basse et une chaîne de 2 sur les percussions.",
            "Transpose le dernier pattern de la basse d'une octave pour marquer la fin de phrase.",
        ),
        success = "La phrase de 64 pas se répète, et la fin se distingue nettement.",
        style = "Hardtek : 3 patterns de basse identiques et un 4e transposé pour relancer.",
        mistakes = listOf(
            "Chaîner des patterns non consécutifs : c'est impossible dans la vue Patterns, passe par les Scènes (p. 76).",
            "Oublier que Play relance la chaîne depuis son début ; Shift + Play reprend où elle s'était arrêtée (p. 79).",
            "Oublier de déverrouiller la vue : son état n'est pas mémorisé, mais il reste actif jusqu'à l'extinction (p. 80).",
        ),
        quiz = listOf(
            qz("Peut-on chaîner les patterns 1, 2 et 6 ?",
                listOf("Oui", "Non, ils doivent être consécutifs", "Seulement sur une piste Drum"), 1,
                "Les Scènes permettent de contourner cette limite.", CHAINES),
            qz("Quelle est la longueur maximale d'une chaîne de patterns ?",
                listOf("128 pas", "256 pas", "512 pas"), 1,
                "8 patterns de 32 pas.", CHAINES),
            qz("Comment transposer d'une octave tout le pattern d'un synthé ?",
                listOf("Shift + touche fléchée", "Scales + pad", "Duplicate + touche fléchée"), 0,
                "Seule la piste sélectionnée est transposée.", TRANSPOSER),
            qz("À quoi sert Shift + Patterns ?",
                listOf("À effacer les patterns", "À verrouiller la vue des pas", "À chaîner les scènes"), 1,
                "La vue reste figée sur un pattern pendant que la lecture continue.", VERROU),
        ),
    )

    private val m11l3 = ctLesson(
        M11, 3, "Scènes et arrangement", listOf(SCENES, CHAINE_SCENES, FILE_SCENES), 20,
        objective = "Mémoriser les parties d'un morceau dans des Scènes et les enchaîner en arrangement complet.",
        explanation = "Une Scène mémorise, pour toutes les pistes, les patterns et chaînes sélectionnés. Les 16 Scènes sont les deux rangées du bas de la vue Mixer. " +
            "Pour en créer une : choisis les patterns dans la vue Patterns, ouvre le Mixer, tiens Shift et touche un pad de Scène. " +
            "Un pad blanc vif contient une Scène. Tenir une Scène et toucher une autre crée une chaîne de Scènes : c'est l'arrangement. " +
            "Les Scènes permettent aussi de jouer des patterns non consécutifs. Elles sont sauvegardées avec le projet (Save deux fois).",
        controls = listOf(
            cmd("Mixer, rangées 3-4", "Les 16 Scènes.", SCENES),
            cmd("Shift + pad de Scène", "Mémorise les patterns sélectionnés dans la Scène.", SCENES),
            cmd("Tenir une Scène + toucher la dernière", "Chaîne de Scènes.", CHAINE_SCENES),
            cmd("Clear / Duplicate + pad de Scène", "Efface / copie une Scène.", FILE_SCENES),
        ),
        steps = listOf(
            "Prépare : intro (kick seul, pattern 1), partie A (patterns 2), break (pas de kick, patterns 3).",
            "Vue Patterns : sélectionne les patterns de l'intro, puis Mixer, Shift + Scène 1.",
            "Fais de même pour la partie A en Scène 2 et le break en Scène 3.",
            "Tiens la Scène 1 et touche la Scène 3 : les trois parties s'enchaînent.",
            "Save deux fois pour garder les Scènes avec le projet.",
        ),
        expected = "Un morceau intro / partie A / break qui se joue tout seul, en boucle.",
        exerciseTitle = "Le morceau complet",
        exercise = listOf(
            "Construis 5 Scènes : intro, montée, drop, break, final.",
            "Chaîne-les et écoute le morceau entier sans toucher la machine.",
        ),
        success = "Les 5 parties s'enchaînent dans l'ordre et en rythme.",
        style = "Tribe : longues Scènes hypnotiques et un break sans kick avant le retour.",
        mistakes = listOf(
            "Toucher un pad de Scène sans Shift en voulant enregistrer : ça la sélectionne seulement (p. 81–82).",
            "Oublier de sauvegarder : les Scènes ne restent qu'avec Save pressé deux fois (p. 83).",
            "Effacer une Scène et la croire vide : elle revient au pattern 1 de toutes les pistes (p. 84).",
        ),
        quiz = listOf(
            qz("Combien de Scènes propose un projet ?",
                listOf("8", "16", "32"), 1,
                "16 Scènes, sur les deux rangées du bas de la vue Mixer.", SCENES),
            qz("Comment mémoriser une Scène ?",
                listOf("Shift + pad de Scène dans la vue Mixer", "Save + pad de Scène", "Duplicate + pad de Scène"), 0,
                "Le pad s'allume en doré vif pendant l'enregistrement.", SCENES),
            qz("Une Scène en file d'attente démarre quand ?",
                listOf("Immédiatement", "À la fin du pattern de la piste Drum 1", "Au prochain Play"), 1,
                "Elle démarre en rythme, depuis son point de départ.", FILE_SCENES),
            qz("Comment jouer les patterns 1, 2, 5 et 6 dans l'ordre ?",
                listOf("Chaîner 1 à 6", "Deux Scènes consécutives (1-2 puis 5-6) chaînées", "C'est impossible"), 1,
                "Les Scènes contournent la règle des patterns consécutifs.", CHAINE_SCENES),
        ),
    )

    private val m11 = CourseModule(
        id = M11, number = 11, title = "Construction d'un morceau",
        summary = "Patterns, chaînes, transposition, Scènes, arrangement.",
        goal = "Passer d'une boucle à un morceau structuré, prêt à être joué.",
        styleFocus = "Intro, montée, drop et break : la structure qui fait danser en free party.",
        lessons = listOf(m11l1, m11l2, m11l3),
    )

    // ---------------------------------------------------------------------
    // Module 12 — Jeu live et transitions
    // ---------------------------------------------------------------------
    private const val M12 = "ct-m12"

    private val m12l1 = ctLesson(
        M12, 1, "Transitions : file d'attente ou changement immédiat", listOf(VUE_PATTERNS, FILE_SCENES, CHARGER, CHANGER_PROJET), 20,
        objective = "Choisir, pour chaque transition, entre un changement calé en fin de boucle et un changement immédiat.",
        explanation = "Patterns, Scènes et projets suivent la même logique : par défaut, ce que tu choisis attend son tour (le pad clignote) ; " +
            "avec Shift, le changement est immédiat. Pour les patterns et les projets, Shift garde la position de lecture. " +
            "Une Scène en attente démarre à la fin du pattern de Drum 1. Un projet choisi en lecture attend la fin du pattern en cours " +
            "et garde le tempo actuel : tu peux enchaîner plusieurs projets comme des morceaux d'un set.",
        controls = listOf(
            cmd("Pad de pattern / Shift + pad", "File d'attente / immédiat, même pas.", VUE_PATTERNS),
            cmd("Pad de Scène (vue Mixer)", "Scène en file d'attente, démarre en fin de pattern de Drum 1.", FILE_SCENES),
            cmd("Pad de projet / Shift + pad", "Projet en fin de pattern / immédiat au même pas.", CHANGER_PROJET),
        ),
        steps = listOf(
            "Lance un projet avec plusieurs Scènes.",
            "Touche une autre Scène : son pad clignote en vert, elle démarre en fin de pattern de Drum 1.",
            "Ouvre la vue Patterns et fais un changement immédiat de basse avec Shift.",
            "Ouvre Projects et choisis un autre projet : il attend la fin du pattern et garde le tempo.",
            "Recommence avec Shift : le nouveau projet démarre tout de suite, au même pas.",
        ),
        expected = "Tu sais prévoir à quel moment chaque changement va tomber.",
        exerciseTitle = "Trois transitions",
        exercise = listOf(
            "Enchaîne deux projets : une fois en file d'attente, une fois avec Shift.",
            "Fais un changement de Scène en file d'attente et un changement de pattern immédiat sur la batterie.",
        ),
        success = "Aucune transition ne tombe hors du temps.",
        mistakes = listOf(
            "Utiliser Shift sur un projet dont les patterns ont d'autres longueurs : le résultat peut surprendre, essaie avant le live (p. 95).",
            "Croire qu'un projet en attente attend la fin de toute la chaîne : il attend seulement la fin du pattern en cours (p. 95).",
            "Oublier qu'un projet lancé à l'arrêt reprend son propre tempo (p. 23).",
        ),
        quiz = listOf(
            qz("Quand démarre un projet choisi pendant la lecture, sans Shift ?",
                listOf("Immédiatement", "À la fin du pattern en cours", "À la fin de la chaîne de Scènes"), 1,
                "Seul le pattern en cours est terminé, pas la Scène ni la chaîne.", CHANGER_PROJET),
            qz("Que fait Shift en choisissant un projet en lecture ?",
                listOf("Le lance tout de suite au même pas", "Le sauvegarde", "Le met en file d'attente"), 0,
                "Il démarre sans attendre, au même pas de la chaîne.", CHANGER_PROJET),
            qz("À quel tempo joue un projet choisi pendant la lecture ?",
                listOf("Son tempo sauvegardé", "Le tempo actuel", "120 BPM"), 1,
                "Le tempo reste constant d'un projet à l'autre.", CHARGER),
            qz("De quelle couleur clignote le pad d'une Scène en file d'attente ?",
                listOf("Rouge", "Vert", "Blanc"), 1,
                "Elle démarre à la fin du pattern de Drum 1.", FILE_SCENES),
        ),
    )

    private val m12l2 = ctLesson(
        M12, 2, "Jouer en live : mutes, pads libres, filtre, tempo et swing", listOf(MIXER, MUTE_JEU, FILTRE, TAP, SWING), 20,
        objective = "Faire vivre un morceau en direct avec les mutes, le Master Filter, le tap tempo et le swing.",
        explanation = "Dans la vue Mixer, les mutes de la rangée 1 coupent et relancent les pistes : c'est l'outil principal des breaks. " +
            "Une piste mutée n'est plus jouée par le séquenceur, mais ses pads de pas restent jouables à la main : tu peux faire des accords " +
            "ou des frappes en direct (pour la batterie, depuis la vue Velocity ou Gate). Le Master Filter prépare les retours de kick. " +
            "Shift + Tempo/Swing tapé au moins trois fois cale le tempo (moyenne des cinq dernières frappes). " +
            "Dans la vue Tempo, la Macro 2 règle le swing, de 20 à 80 (50 = droit).",
        controls = listOf(
            cmd("Mixer, rangée 1", "Mutes des pistes.", MIXER),
            cmd("Pads de pas d'une piste mutée", "Jouent ses notes à la main.", MUTE_JEU),
            cmd("Master Filter", "Passe-haut / passe-bas sur tout le son.", FILTRE),
            cmd("Shift + Tempo/Swing (tapé)", "Tap tempo, au moins 3 frappes.", TAP),
            cmd("Tempo/Swing + Macro 2", "Swing de 20 à 80, 50 par défaut.", SWING),
        ),
        steps = listOf(
            "Lance ton morceau, ouvre le Mixer.",
            "Coupe le kick et la basse pendant 8 temps, filtre en passe-haut, puis relance tout sur le temps en recentrant le filtre.",
            "Mute Synth 2, ouvre sa vue Note et joue ses pas à la main par-dessus la boucle.",
            "Shift + Tempo/Swing tapé en rythme sur un morceau que tu écoutes : le tempo s'aligne.",
            "Vue Tempo : monte la Macro 2 vers 60 pour donner du swing aux charleys, puis reviens à 50.",
        ),
        expected = "Un break et un retour propres, une partie jouée à la main et un tempo calé au tap.",
        exerciseTitle = "5 minutes de live",
        exercise = listOf(
            "Joue ton morceau 5 minutes sans l'arrêter.",
            "Fais au moins deux breaks aux mutes, un retour au filtre et une partie jouée à la main sur une piste mutée.",
        ),
        success = "5 minutes sans silence involontaire et des retours toujours sur le temps.",
        style = "Hardtek / tribe : breaks sans kick et retours au filtre, l'énergie de la free party.",
        mistakes = listOf(
            "Taper le tempo sans Shift : la touche Tempo/Swing seule ouvre la vue Tempo (p. 86).",
            "Taper seulement deux fois : il faut au moins trois frappes (p. 86).",
            "Muter une piste Drum et chercher ses pads de pas en vue Note : passe par Velocity ou Gate (p. 89).",
        ),
        quiz = listOf(
            qz("Combien de frappes faut-il au minimum pour le tap tempo ?",
                listOf("2", "3", "5"), 1,
                "Au moins trois ; le tempo est la moyenne des cinq dernières.", TAP),
            qz("Quelle valeur de swing correspond à un rythme droit ?",
                listOf("20", "50", "80"), 1,
                "50 par défaut ; plage de 20 à 80.", SWING),
            qz("Sur une piste mutée, que font les pads de pas ?",
                listOf("Rien", "Ils jouent les notes à la main", "Ils effacent les pas"), 1,
                "Le séquenceur ne les joue plus, mais tu peux les déclencher.", MUTE_JEU),
            qz("Quelle Macro règle le swing dans la vue Tempo ?",
                listOf("Macro 1", "Macro 2", "Macro 5"), 1,
                "Macro 1 tempo, Macro 2 swing, Macro 5 volume du métronome.", SWING),
        ),
    )

    private val m12l3 = ctLesson(
        M12, 3, "Préparer et protéger son set", listOf(COULEURS, SAVE_LOCK, PACKS, MICROSD, BRIGHTNESS, ALIM), 20,
        objective = "Organiser tes projets pour la scène, protéger la sauvegarde et emporter plusieurs sets sur microSD.",
        explanation = "Pendant une sauvegarde, après le premier Save, la Macro 1 fait défiler 14 couleurs pour le pad du projet : pratique pour repérer " +
            "tes morceaux en live. Save Lock (Shift + Save tenus à l'allumage) désactive la sauvegarde pour ne rien écraser par erreur ; " +
            "il reste actif jusqu'à ce que tu le désactives de la même façon. Un Pack contient les 64 projets, les patches et les samples. " +
            "Avec une carte microSD, 31 Packs de plus sont disponibles : vue Packs (Shift + Projects), choisis un Pack puis Play pour le charger. " +
            "Ne retire jamais la carte pendant une sauvegarde ou un chargement. Pad 24 de la vue Setup : luminosité réduite.",
        controls = listOf(
            cmd("Save puis Macro 1", "Choix parmi 14 couleurs pour le pad du projet.", COULEURS),
            cmd("Shift + Save à l'allumage", "Save Lock marche/arrêt.", SAVE_LOCK),
            cmd("Shift + Projects", "Vue Packs (carte microSD insérée).", PACKS),
            cmd("Duplicate (vue Packs)", "Copie le Pack actuel dans un emplacement libre.", PACKS),
            cmd("Pad 24 (vue Setup)", "Luminosité des pads réduite d'environ 50 %.", BRIGHTNESS),
        ),
        steps = listOf(
            "Range ton set : un projet par morceau, dans l'ordre de jeu, sur la même page.",
            "Pour chaque morceau : Save, tourne la Macro 1 pour une couleur (par exemple une couleur par style), puis Save.",
            "Éteins, rallume en tenant Shift + Save : Save reste éteint, le set est protégé.",
            "Avec une carte microSD : Shift + Projects, Duplicate pour copier le Pack actuel comme sauvegarde.",
            "Si tu joues sur batterie ou dans le noir, baisse la luminosité dans la vue Setup (pad 24).",
        ),
        expected = "Un set coloré dans l'ordre, protégé contre l'écrasement, avec une copie de sécurité.",
        exerciseTitle = "Check-list avant scène",
        exercise = listOf(
            "Batterie chargée (environ 4 heures d'autonomie) ou adaptateur prévu.",
            "Projets dans l'ordre et colorés, Save Lock actif, copie du Pack sur microSD.",
            "Joue le premier et le dernier morceau pour vérifier les transitions.",
        ),
        success = "Toute la check-list cochée la veille du live.",
        mistakes = listOf(
            "Retirer la microSD pendant une sauvegarde, un chargement ou une copie de Pack : tout le travail sauvegardé peut être perdu (p. 99).",
            "Changer de carte microSD sans éteindre la machine : comportement indéterminé (p. 100).",
            "Oublier de désactiver Save Lock en studio : Save reste éteint tant qu'il est actif (p. 107).",
            "Projet qui ne se charge plus après une coupure : allume avec Shift + Clear pour démarrer sur un projet vierge (p. 107).",
        ),
        quiz = listOf(
            qz("Combien de couleurs peut-on donner à un pad de projet ?",
                listOf("8", "14", "16"), 1,
                "14 couleurs, avec la Macro 1 pendant la sauvegarde.", COULEURS),
            qz("Comment activer Save Lock ?",
                listOf("Shift + Save pendant la mise sous tension", "Shift + Save en lecture", "Dans Components"), 0,
                "On le désactive de la même façon.", SAVE_LOCK),
            qz("Combien de Packs une carte microSD ajoute-t-elle ?",
                listOf("16", "31", "64"), 1,
                "31 Packs sur la carte, plus le Pack interne : 32 au total.", MICROSD),
            qz("Comment charger un Pack choisi dans la vue Packs ?",
                listOf("Appuyer sur Play", "Appuyer sur Save", "Tenir Shift"), 0,
                "Le pad du Pack clignote, puis Play lance le chargement.", PACKS),
        ),
    )

    private val m12 = CourseModule(
        id = M12, number = 12, title = "Jeu live et transitions",
        summary = "Transitions, mutes, jeu à la main, tap tempo, swing, préparation du set.",
        goal = "Jouer un set complet sur la Circuit Tracks, sans arrêt et sans risque.",
        styleFocus = "Enchaînements en rythme, breaks et retours : la Circuit Tracks comme vraie machine de live.",
        lessons = listOf(m12l1, m12l2, m12l3),
    )

    val modules: List<CourseModule> = listOf(m10, m11, m12)
}
