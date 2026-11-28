package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** DFAM — modules 4 à 6 : mélangeur et filtre, VCA et séquenceur, patchbay et synchronisation. */
internal object DFAMModulesB {

    // Références du manuel (pages imprimées = pages du PDF)
    private val TOUR_FILTRE = dfam("Exploring the DFAM", "11–12")
    private val MIXER = dfam("Mixer", "16–17")
    private val VCF_MODE = dfam("Voltage Controlled Filter, VCF switch", "17")
    private val CUTOFF = dfam("Cutoff, Resonance", "18")
    private val VCF_DECAY = dfam("VCF Decay, VCF EG Amount", "19")
    private val VCF_NEG = dfam("VCF EG Amount (−), Noise / VCF Mod", "20")
    private val VCA_EG = dfam("Voltage Controlled Amplifier, VCA EG", "20")
    private val VCA_DECAY = dfam("VCA Decay, Volume", "21")
    private val SEQ_STEPS = dfam("Analog 8-Step Sequencer, Step Controls, Run / Stop", "22")
    private val SEQ_TEMPO = dfam("Tempo, Advance, Trigger", "23")
    private val PATCH = dfam("Patchbay", "24")
    private val PATCH_IO = dfam("Patchbay, Velocity, VCA Decay, Ext Audio", "25")
    private val PATCH_CV = dfam("Patchbay Overview, VCF Decay, Noise Level, VCO Decay", "26")
    private val PATCH_VCO = dfam("Patchbay Overview, VCF Mod, VCO 1 CV", "27")
    private val PATCH_TEMPO = dfam("Patchbay Overview, Tempo", "28")
    private val PATCH_CLOCK = dfam("Run / Stop, Adv / Clock, Trigger, Velocity, Pitch", "29")
    private val SYNC_DFAM = dfam("Syncing Multiple DFAMs", "30")
    private val SYNC_M32 = dfam("Syncing DFAM with a Mother-32", "31")

    // ---------------------------------------------------------------------
    // Module 4 — Mélangeur et filtre
    // ---------------------------------------------------------------------
    private const val M4 = "dfam-m4"

    private val m4l1 = ctLesson(
        M4, 1, "Le mélangeur et le bruit blanc", listOf(MIXER, TOUR_FILTRE), 15,
        objective = "Doser les trois sources et utiliser le bruit pour l'attaque, les caisses claires et les charleys.",
        explanation = "Le mélangeur a trois boutons : VCO 1 LEVEL, NOISE / EXT LEVEL et VCO 2 LEVEL. Le bruit blanc sert aux caisses " +
            "claires et charleys, et ajoute profondeur et attaque aux sons accordés. Le DFAM étant analogique, les niveaux du " +
            "mélangeur changent le son : niveaux bas = plus de marge et de dynamique ; niveaux hauts = son plus agressif, saturé. " +
            "Un signal branché sur l'entrée EXT AUDIO de la patchbay remplace le bruit ; son niveau se règle alors avec NOISE / EXT " +
            "LEVEL (entrée prévue pour des niveaux Eurorack : un lecteur MP3 doit être amplifié avant).",
        controls = listOf(
            cmd("VCO 1 LEVEL", "Niveau du VCO 1.", MIXER),
            cmd("NOISE / EXT LEVEL", "Niveau du bruit blanc ou de l'entrée externe.", MIXER),
            cmd("VCO 2 LEVEL", "Niveau du VCO 2.", MIXER),
        ),
        steps = listOf(
            "Depuis ton son FM, monte NOISE / EXT LEVEL à 13 h : l'attaque devient plus naturelle.",
            "Baisse puis monte VCO 1 LEVEL et VCO 2 LEVEL : écoute la saturation arriver aux niveaux hauts.",
            "Coupe les deux VCO et garde seulement le bruit pour entendre un son de charley ou de caisse claire.",
        ),
        expected = "Tu sais doser les sources et entends quand le mélangeur sature.",
        exerciseTitle = "Kick propre, kick sale",
        exercise = listOf(
            "Règle un kick avec les niveaux bas, puis le même avec les niveaux hauts.",
            "Garde la version sale pour un passage hardtek, la propre pour une intro.",
        ),
        success = "Tu choisis consciemment la saturation du mélangeur.",
        style = "Hardtek : un mélangeur poussé donne le grain sale des kicks de free party.",
        mistakes = listOf(
            "Brancher un téléphone directement dans EXT AUDIO : le niveau est trop faible, il faut l'amplifier.",
            "Tout mettre au maximum par réflexe : on perd la dynamique.",
        ),
        quiz = listOf(
            qz("Combien de voies a le mélangeur ?", listOf("Deux", "Trois", "Huit"), 1,
                "VCO 1, bruit / externe et VCO 2.", MIXER),
            qz("Que donnent des niveaux de mélangeur élevés ?", listOf("Un son plus agressif, saturé", "Un son plus propre"), 0,
                "Des niveaux hauts donnent un son plus agressif et écrêté.", MIXER),
            qz("Que se passe-t-il quand on branche EXT AUDIO ?", listOf("Le VCO 1 est coupé", "Le signal externe remplace le bruit", "Le tempo change"), 1,
                "L'entrée EXT AUDIO remplace le générateur de bruit.", MIXER),
            qz("À quoi sert le bruit blanc ?", listOf("Caisses claires, charleys, attaque", "Accorder le DFAM", "Synchroniser"), 0,
                "Il sert aux caisses claires et charleys, et ajoute de l'attaque.", MIXER),
        ),
    )

    private val m4l2 = ctLesson(
        M4, 2, "Filtre : mode, coupure, résonance", listOf(VCF_MODE, CUTOFF), 15,
        objective = "Choisir passe-bas ou passe-haut et régler la coupure et la résonance.",
        explanation = "Le filtre ladder 4 pôles (24 dB/octave) fonctionne en LP (passe-bas, sons épais ou sombres) ou HP (passe-haut, " +
            "sons fins, brillants, claquants), choisi par le sélecteur VCF. CUTOFF règle la fréquence de coupure, de 20 Hz à 20 kHz. " +
            "RESONANCE crée un pic à la fréquence de coupure et peut atténuer les basses. En LP, au-delà de 15 h, le filtre " +
            "auto-oscille et produit une sinusoïde. En HP, la résonance n'a pas la même échelle. Si le son disparaît : CUTOFF et " +
            "VCF EG AMOUNT au centre, puis VCF sur LP, puis réglages par défaut.",
        controls = listOf(
            cmd("VCF (LP / HP)", "Mode du filtre.", VCF_MODE),
            cmd("CUTOFF", "Fréquence de coupure, 20 Hz à 20 kHz.", CUTOFF),
            cmd("RESONANCE", "Pic à la coupure ; auto-oscillation en LP au-delà de 15 h.", CUTOFF),
        ),
        steps = listOf(
            "Mets VCF sur LP et CUTOFF à 11 h : le son devient sombre et plat.",
            "Ouvre CUTOFF vers la droite : le son s'éclaircit.",
            "Passe en HP et balaie CUTOFF : les graves disparaissent.",
            "Reviens en LP et monte un peu RESONANCE : le pic apparaît et les graves s'allègent.",
        ),
        expected = "Tu entends la différence LP / HP et l'effet de la résonance.",
        exerciseTitle = "Montée au filtre",
        exercise = listOf(
            "En HP, ferme les graves pendant quatre mesures, puis repasse en LP ouvert pour le retour.",
            "Garde la résonance sous 15 h pour éviter l'auto-oscillation.",
        ),
        success = "Le retour du kick en LP fait l'effet d'une relance.",
        style = "Hardtek / tribe : passe-haut avant le retour du kick, la transition classique en free party.",
        mistakes = listOf(
            "Pousser la résonance en LP au-delà de 15 h sans le vouloir : le filtre siffle (auto-oscillation).",
            "Oublier qu'une forte résonance allège les basses du kick.",
        ),
        quiz = listOf(
            qz("Quel mode garde les graves ?", listOf("LP", "HP"), 0,
                "Le passe-bas laisse passer les basses fréquences.", VCF_MODE),
            qz("Quelle est la pente du filtre ?", listOf("12 dB/octave", "24 dB/octave", "6 dB/octave"), 1,
                "Ladder 4 pôles : 24 dB par octave.", CUTOFF),
            qz("En LP, que fait RESONANCE au-delà de 15 h ?", listOf("Rien", "Le filtre auto-oscille", "Le DFAM s'arrête"), 1,
                "Le filtre produit une sinusoïde utilisable comme source.", CUTOFF),
            qz("Que peut faire une résonance élevée sur les graves ?", listOf("Les atténuer", "Les doubler"), 0,
                "Le manuel indique que les basses peuvent être atténuées.", CUTOFF),
        ),
    )

    private val m4l3 = ctLesson(
        M4, 3, "Mouvement du filtre : enveloppe et bruit", listOf(VCF_DECAY, VCF_NEG), 20,
        objective = "Faire bouger le filtre à chaque note avec l'enveloppe VCF et le salir avec le bruit.",
        explanation = "VCF EG AMOUNT (bipolaire) dose l'enveloppe du filtre : à droite, la coupure monte à chaque note (sons articulés, " +
            "agressifs) ; à gauche, elle descend (sons « qui éclosent », « boing ») ; au centre, rien. VCF DECAY règle sa durée " +
            "(10 ms à 10 s) : 7 h = petit clic, 10 h = claquement sec, 12 h = balayage naturel pour kicks et toms, au-delà = sons " +
            "longs et agressifs. Les VELOCITY agissent aussi sur cette enveloppe. NOISE / VCF MOD module la coupure avec le bruit : " +
            "son sale, distordu, lo-fi, ou grésillement des cymbales.",
        controls = listOf(
            cmd("VCF EG AMOUNT", "Quantité d'enveloppe sur la coupure, bipolaire.", VCF_DECAY),
            cmd("VCF DECAY", "Durée de l'enveloppe du filtre.", VCF_DECAY),
            cmd("NOISE / VCF MOD", "Bruit vers la coupure (ou signal de l'entrée VCF MOD).", VCF_NEG),
        ),
        steps = listOf(
            "CUTOFF à 11 h, monte lentement VCF EG AMOUNT jusqu'à 15 h.",
            "Tourne VCF DECAY : écoute l'articulation de chaque pas, puis règle-le à 10 h.",
            "Monte NOISE / VCF MOD au maximum : le son devient sale et lo-fi.",
            "Redescends NOISE / VCF MOD à 9 h.",
        ),
        expected = "Chaque coup a une attaque de filtre marquée, avec un léger grain de bruit.",
        exerciseTitle = "Snare sale",
        exercise = listOf(
            "Fais une caisse claire avec le bruit, VCF en HP, VCF DECAY vers 10 h.",
            "Ajoute NOISE / VCF MOD jusqu'à un son agressif mais lisible.",
        ),
        success = "Une caisse claire sèche et sale claque sur les temps voulus.",
        style = "Mentalcore : bruit sur le filtre pour des percussions sales et distordues.",
        mistakes = listOf(
            "Tourner VCF DECAY avec VCF EG AMOUNT au centre : sans effet, c'est normal.",
            "Laisser NOISE / VCF MOD au maximum par défaut : le son perd sa définition.",
        ),
        quiz = listOf(
            qz("VCF EG AMOUNT à gauche du centre…", listOf("Monte la coupure", "Descend la coupure à chaque note", "Coupe le son"), 1,
                "La modulation négative fait baisser la fréquence de coupure.", VCF_NEG),
            qz("Quelle position de VCF DECAY pour un claquement sec ?", listOf("10 h", "17 h"), 0,
                "Le manuel associe 10 h à un claquement sec de l'attaque.", VCF_DECAY),
            qz("Quelle est la plage de VCF DECAY ?", listOf("10 ms à 10 s", "1 s à 1 min"), 0,
                "De 10 millisecondes à 10 secondes.", VCF_DECAY),
            qz("Que produit NOISE / VCF MOD ?", listOf("Un son sale, distordu, lo-fi", "Un écho"), 0,
                "Le bruit qui module la coupure salit le son.", VCF_NEG),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 5 — Amplificateur et séquenceur
    // ---------------------------------------------------------------------
    private const val M5 = "dfam-m5"

    private val m5l1 = ctLesson(
        M5, 1, "VCA : attaque et longueur du son", listOf(VCA_EG, VCA_DECAY), 15,
        objective = "Régler la longueur de chaque coup et adoucir l'attaque quand il le faut.",
        explanation = "Le VCA règle le volume, piloté par son enveloppe et par les VELOCITY. Le sélecteur VCA EG choisit l'attaque : " +
            "FAST (1 ms) pour les sons qui claquent (kicks, charleys) ; SLOW (100 ms) pour les attaques douces (mailloches, " +
            "balais, gongs), et sur une basse séquencée il imite un léger glide. VCA DECAY règle la longueur : 7 h = presque rien " +
            "(charleys très serrés), 9 h = coups brefs, 10 h = coups courts et lourds, 11 h = le son résonne, 12 h = longue tenue, " +
            "au-delà = sons qui se fondent, presque en drone. VOLUME règle la sortie et le casque.",
        controls = listOf(
            cmd("VCA EG (FAST / SLOW)", "Attaque de 1 ms ou 100 ms.", VCA_EG),
            cmd("VCA DECAY", "Longueur du son.", VCA_DECAY),
            cmd("VOLUME", "Niveau de sortie.", VCA_DECAY),
        ),
        steps = listOf(
            "VCA EG sur FAST, VCA DECAY à 10 h : le son se resserre.",
            "Passe VCA DECAY à 13 h : les queues de son passent.",
            "Mets VCA EG sur SLOW et VCA DECAY entre 9 et 10 h : l'attaque s'adoucit.",
            "Reviens sur FAST pour le kick.",
        ),
        expected = "Tu sais raccourcir, allonger et adoucir chaque coup.",
        exerciseTitle = "Charley serré",
        exercise = listOf(
            "Avec le bruit seul et le filtre en HP, règle VCA DECAY entre 7 et 9 h pour un charley.",
            "Compare FAST et SLOW sur ce charley.",
        ),
        success = "Un charley court et net en FAST.",
        style = "Hardtek : charley serré entre les kicks.",
        mistakes = listOf(
            "Mettre VCA DECAY au minimum et croire que le DFAM est en panne : à 7 h, il sort très peu de son.",
            "Utiliser SLOW sur un kick : l'attaque perd son impact.",
        ),
        quiz = listOf(
            qz("Quelle attaque donne VCA EG sur FAST ?", listOf("1 ms", "100 ms", "1 s"), 0,
                "FAST = 1 ms, SLOW = 100 ms.", VCA_EG),
            qz("Pour une attaque douce de type balai ?", listOf("FAST", "SLOW"), 1,
                "SLOW (100 ms) adoucit l'attaque.", VCA_EG),
            qz("Quel réglage de VCA DECAY pour des charleys très serrés ?", listOf("Vers 7 h", "Au-delà de 12 h"), 0,
                "Le minimum convient aux sons très courts comme les charleys serrés.", VCA_DECAY),
            qz("Que peut imiter SLOW sur une basse séquencée ?", listOf("Un glide léger", "Un écho"), 0,
                "Le manuel indique qu'il peut imiter subtilement un glide.", VCA_EG),
        ),
    )

    private val m5l2 = ctLesson(
        M5, 2, "Les huit pas : PITCH et VELOCITY", listOf(SEQ_STEPS), 15,
        objective = "Programmer hauteur et intensité de chacun des huit pas.",
        explanation = "Chaque pas a un potentiomètre PITCH, un potentiomètre VELOCITY et une LED qui montre le pas actif. PITCH s'ajoute " +
            "à VCO FREQUENCY (à droite du centre) ou s'en retranche (à gauche), à condition que SEQ PITCH MOD soit sur VCO 1&2 ou " +
            "VCO 2 ; sa plage est d'environ dix octaves (±5 V). VELOCITY fixe l'amplitude des trois enveloppes (VCO, VCF, VCA) : " +
            "faible = son doux et peu marqué, fort = son fort et percutant. Au minimum, le pas ne sonne pas : c'est ainsi qu'on " +
            "fait des silences. Les VELOCITY fortes allongent légèrement les decays.",
        controls = listOf(
            cmd("PITCH 1–8", "Hauteur de chaque pas.", SEQ_STEPS),
            cmd("VELOCITY 1–8", "Intensité des trois enveloppes ; au minimum = silence.", SEQ_STEPS),
            cmd("LED de pas", "Pas en cours.", SEQ_STEPS),
        ),
        steps = listOf(
            "Mets les huit VELOCITY au minimum : plus aucun son.",
            "Monte VELOCITY 1 et 5 au maximum : deux coups par cycle.",
            "Ajoute VELOCITY 3 et 7 à mi-course : des coups plus faibles.",
            "En VCO 1&2, monte légèrement PITCH 7 : ce coup sonne plus aigu.",
        ),
        expected = "Un motif de huit pas avec silences, accents et une note différente.",
        exerciseTitle = "Motif tribe en huit pas",
        exercise = listOf(
            "Programme un motif avec deux accents, deux coups faibles et quatre silences.",
            "Varie légèrement les PITCH des coups faibles pour un effet de toms.",
        ),
        success = "Le motif tourne avec des accents clairs et des toms qui varient.",
        style = "Tribe : des toms à hauteurs différentes donnent un groove hypnotique.",
        mistakes = listOf(
            "Programmer les PITCH avec SEQ PITCH MOD sur OFF : la hauteur ne change pas.",
            "Oublier que VELOCITY agit aussi sur le filtre et la hauteur, pas seulement le volume.",
        ),
        quiz = listOf(
            qz("Comment faire un silence sur un pas ?", listOf("VELOCITY au minimum", "PITCH au minimum", "TRIGGER"), 0,
                "Au minimum, le pas ne sonne pas.", SEQ_STEPS),
            qz("Sur quoi agit VELOCITY ?", listOf("Seulement le VCA", "Les trois enveloppes (VCO, VCF, VCA)", "Le tempo"), 1,
                "La vélocité règle l'amplitude des trois enveloppes.", SEQ_STEPS),
            qz("Quelle condition pour que PITCH change la note ?", listOf("SEQ PITCH MOD sur VCO 1&2 ou VCO 2", "HARD SYNC sur ON"), 0,
                "Sur OFF, les PITCH n'agissent pas sur les oscillateurs.", SEQ_STEPS),
            qz("Quelle est la plage des PITCH ?", listOf("Une octave", "Environ dix octaves (±5 V)"), 1,
                "Environ dix octaves, ±5 V.", SEQ_STEPS),
        ),
    )

    private val m5l3 = ctLesson(
        M5, 3, "RUN / STOP, TEMPO, ADVANCE et TRIGGER", listOf(SEQ_STEPS, SEQ_TEMPO), 15,
        objective = "Contrôler la lecture et régler un pas précis à l'arrêt.",
        explanation = "RUN / STOP lance la lecture depuis le pas courant (LED rouge) au tempo réglé, et l'arrête si elle tourne ; une LED " +
            "à côté du bouton indique si le séquenceur tourne. TEMPO va de 0,7 Hz à 700 Hz, soit environ 10 à 10 000 BPM si un pas " +
            "vaut une double croche. À l'arrêt, ADVANCE passe au pas suivant sans jouer les notes, et TRIGGER joue le pas courant sans " +
            "avancer : idéal pour régler un pas précis en l'écoutant autant de fois que nécessaire.",
        controls = listOf(
            cmd("RUN / STOP", "Lecture depuis le pas courant, ou arrêt.", SEQ_STEPS),
            cmd("TEMPO", "Vitesse : environ 10 à 10 000 BPM.", SEQ_TEMPO),
            cmd("ADVANCE", "À l'arrêt : pas suivant, sans jouer.", SEQ_TEMPO),
            cmd("TRIGGER", "À l'arrêt : joue le pas courant sans avancer.", SEQ_TEMPO),
        ),
        steps = listOf(
            "Arrête le séquenceur avec RUN / STOP.",
            "Appuie sur ADVANCE jusqu'au pas 5.",
            "Appuie plusieurs fois sur TRIGGER en réglant PITCH 5 et VELOCITY 5.",
            "Relance avec RUN / STOP et ajuste TEMPO.",
        ),
        expected = "Le pas 5 est réglé précisément, puis la boucle repart.",
        exerciseTitle = "Réglage pas à pas",
        exercise = listOf(
            "Règle chacun des huit pas à l'arrêt avec ADVANCE et TRIGGER.",
            "Relance et monte TEMPO jusqu'à une vitesse tribecore.",
        ),
        success = "Chaque pas sonne comme prévu une fois la boucle relancée.",
        style = "TribeCore : tempo élevé, chaque pas réglé à l'oreille.",
        mistakes = listOf(
            "Utiliser ADVANCE et TRIGGER pendant la lecture : ils sont prévus séquenceur arrêté.",
            "Croire que RUN repart du pas 1 : il repart du pas courant.",
        ),
        quiz = listOf(
            qz("D'où repart la lecture après RUN / STOP ?", listOf("Du pas 1", "Du pas courant"), 1,
                "La lecture reprend au pas courant, indiqué par la LED rouge.", SEQ_STEPS),
            qz("Que fait TRIGGER à l'arrêt ?", listOf("Joue le pas courant sans avancer", "Avance d'un pas"), 0,
                "TRIGGER rejoue le pas courant ; ADVANCE avance.", SEQ_TEMPO),
            qz("ADVANCE joue-t-il les notes des pas traversés ?", listOf("Oui", "Non"), 1,
                "Il passe au pas suivant sans déclencher les notes.", SEQ_TEMPO),
            qz("Quelle plage de tempo indique le manuel ?", listOf("60 à 200 BPM", "Environ 10 à 10 000 BPM"), 1,
                "0,7 Hz à 700 Hz, soit environ 10 à 10 000 BPM en doubles croches.", SEQ_TEMPO),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 6 — Patchbay et synchronisation
    // ---------------------------------------------------------------------
    private const val M6 = "dfam-m6"

    private val m6l1 = ctLesson(
        M6, 1, "Lire la patchbay", listOf(PATCH, PATCH_IO), 15,
        objective = "Distinguer entrées et sorties et comprendre les liaisons pré-câblées.",
        explanation = "La patchbay compte 24 prises 3,5 mm : 15 entrées écrites en texte normal et 9 sorties écrites en lettres " +
            "inversées. On utilise uniquement des câbles 3,5 mm (cinq sont fournis). On peut dédoubler une sortie (multiple, câble en Y, " +
            "fiches empilables), mais il ne faut brancher qu'une seule sortie sur une entrée CV. Certaines entrées sont pré-câblées " +
            "(« normalled ») : TRIGGER reçoit l'horloge du séquenceur, VELOCITY reçoit les potentiomètres VELOCITY 1–8 ; brancher " +
            "un câble remplace ce lien. La sortie VCA est la sortie audio principale, au niveau Eurorack.",
        controls = listOf(
            cmd("15 entrées (texte normal)", "TRIGGER, VCA CV, VELOCITY, VCA DECAY, EXT AUDIO, VCF DECAY, NOISE LEVEL, VCO DECAY, VCF MOD, VCO 1 CV, 1→2 FM AMT, VCO 2 CV, TEMPO, RUN / STOP, ADV / CLOCK.", PATCH),
            cmd("9 sorties (lettres inversées)", "VCA, VCA EG, VCF EG, VCO EG, VCO 1, VCO 2, TRIGGER, VELOCITY, PITCH.", PATCH),
            cmd("Sortie VCA", "Sortie audio principale de la patchbay.", PATCH_IO),
        ),
        steps = listOf(
            "Repère les neuf sorties : leurs noms sont en lettres inversées.",
            "Repère TRIGGER et VELOCITY parmi les entrées : elles sont pré-câblées au séquenceur.",
            "Vérifie qu'aucun câble n'est branché avant de commencer un patch.",
        ),
        expected = "Tu reconnais une entrée d'une sortie au premier coup d'œil.",
        exerciseTitle = "Carte de la patchbay",
        exercise = listOf(
            "Sans regarder le manuel, nomme trois sorties et trois entrées de ta patchbay.",
            "Vérifie ensuite avec la page 24.",
        ),
        success = "Aucune erreur entre entrées et sorties.",
        mistakes = listOf(
            "Brancher deux sorties sur une même entrée CV : le manuel l'interdit (surtension).",
            "Utiliser des câbles 6,35 mm ou TRS de casque : la patchbay est en 3,5 mm.",
        ),
        quiz = listOf(
            qz("Combien de prises compte la patchbay ?", listOf("12", "24", "48"), 1,
                "24 prises : 15 entrées et 9 sorties.", PATCH),
            qz("Comment reconnaître une sortie ?", listOf("Lettres inversées", "Couleur bleue", "Plus grosse prise"), 0,
                "Les sorties sont identifiées par des lettres inversées.", PATCH),
            qz("Combien de sorties sur une même entrée CV ?", listOf("Une seule", "Deux maximum", "Autant qu'on veut"), 0,
                "Une seule, pour éviter les surtensions.", PATCH),
            qz("À quoi est pré-câblée l'entrée VELOCITY ?", listOf("Aux potentiomètres VELOCITY 1–8", "Au VCO 2", "Au bruit"), 0,
                "Elle est reliée aux VELOCITY du séquenceur tant qu'aucun câble n'est branché.", PATCH_IO),
        ),
    )

    private val m6l2 = ctLesson(
        M6, 2, "Premiers patchs de modulation", listOf(PATCH_IO, PATCH_CV, PATCH_VCO, PATCH_TEMPO), 20,
        objective = "Utiliser les tensions du séquenceur et des oscillateurs pour moduler d'autres paramètres.",
        explanation = "Les entrées VCA DECAY, VCF DECAY et VCO DECAY s'ajoutent à leur bouton : le manuel conseille de mettre ce bouton " +
            "au centre pour profiter de toute la plage. Patch 1 : sortie PITCH → entrée VCA DECAY, les PITCH 1–8 règlent alors la " +
            "longueur de chaque pas (idéal pour les charleys, SEQ PITCH MOD sur OFF). Patch 2 : sortie VCO 2 → entrée VCF MOD avec " +
            "NOISE / VCF MOD au maximum, la hauteur du VCO 2 module le filtre. Patch 3 : sortie PITCH ou VELOCITY → entrée TEMPO, " +
            "chaque pas a sa propre durée (délicat à régler).",
        controls = listOf(
            cmd("Sortie PITCH", "Tension des PITCH 1–8 (−5 à +5 V).", PATCH_CLOCK),
            cmd("Entrée VCA DECAY", "S'ajoute au bouton VCA DECAY.", PATCH_IO),
            cmd("Entrée VCF MOD", "Remplace le bruit comme source de modulation du filtre.", PATCH_VCO),
            cmd("Entrée TEMPO", "S'ajoute au bouton TEMPO.", PATCH_TEMPO),
        ),
        steps = listOf(
            "Mets SEQ PITCH MOD sur OFF et VCA DECAY au centre.",
            "Relie la sortie PITCH à l'entrée VCA DECAY, puis tourne les PITCH 1–8 : chaque pas a sa longueur.",
            "Débranche, puis relie la sortie VCO 2 à l'entrée VCF MOD avec NOISE / VCF MOD au maximum.",
            "Tourne VCO 2 FREQUENCY et écoute le filtre changer.",
        ),
        expected = "Tu entends la longueur des pas varier, puis le filtre modulé par le VCO 2.",
        exerciseTitle = "Charleys vivants",
        exercise = listOf(
            "Avec le patch PITCH → VCA DECAY, fais un motif de charleys ouverts et fermés.",
            "Ajoute des variations de VELOCITY pour humaniser le motif.",
        ),
        success = "Les charleys alternent ouverts et fermés sans toucher VCA DECAY.",
        style = "Tribe : charleys qui varient à chaque pas pour un groove vivant.",
        mistakes = listOf(
            "Laisser VCA DECAY au minimum avec le patch : la tension externe a moins de plage.",
            "Brancher VCF MOD en laissant NOISE / VCF MOD bas : la modulation est faible.",
        ),
        quiz = listOf(
            qz("Où mettre le bouton VCA DECAY avant de brancher son entrée ?", listOf("Au minimum", "Au centre", "Au maximum"), 1,
                "Le centre offre la plus grande plage à la tension externe.", PATCH_IO),
            qz("Brancher l'entrée VCF MOD…", listOf("Remplace le bruit comme source de modulation du filtre", "Coupe le filtre"), 0,
                "Le signal branché remplace le bruit ; NOISE / VCF MOD en dose la quantité.", PATCH_VCO),
            qz("Que donne PITCH → VCA DECAY ?", listOf("Une longueur différente par pas", "Un tempo double"), 0,
                "Les PITCH 1–8 règlent alors la durée de chaque pas.", dfam("VCA Decay (tip)", "21")),
            qz("Que donne PITCH ou VELOCITY → TEMPO ?", listOf("Une durée propre à chaque pas", "Un arrêt du séquenceur"), 0,
                "Les potentiomètres déterminent la longueur de chaque pas.", dfam("Tempo (tip)", "23")),
        ),
    )

    private val m6l3 = ctLesson(
        M6, 3, "Synchroniser le DFAM", listOf(PATCH_CLOCK, SYNC_DFAM, SYNC_M32), 15,
        objective = "Faire suivre une horloge externe au DFAM ou s'en servir comme horloge.",
        explanation = "L'entrée ADV / CLOCK fait avancer le séquenceur d'un pas à chaque front montant d'horloge ; le bouton TEMPO est " +
            "alors ignoré. Les signaux qui varient lentement peuvent être ignorés. La sortie TRIGGER envoie une impulsion de 5 V " +
            "(environ 1 ms) à chaque note : elle peut servir d'horloge pour d'autres instruments. Pour deux DFAM : TRIGGER de l'unité " +
            "principale → ADV / CLOCK de la secondaire ; appuyer d'abord sur RUN / STOP de la secondaire, puis sur celui de la " +
            "principale. Avec un Mother-32 : sa sortie ASSIGN (réglée pour envoyer l'horloge) → ADV / CLOCK, RUN / STOP du DFAM " +
            "d'abord, puis lecture sur le Mother-32. L'entrée RUN / STOP démarre (+5 V) et arrête (0 V) le séquenceur, mais ne " +
            "synchronise pas l'horloge interne.",
        controls = listOf(
            cmd("Entrée ADV / CLOCK", "Avance d'un pas par impulsion ; TEMPO ignoré.", PATCH_CLOCK),
            cmd("Sortie TRIGGER", "Impulsion à chaque note, horloge pour d'autres machines.", PATCH_CLOCK),
            cmd("Entrée RUN / STOP", "Lecture tant que +5 V est présent.", PATCH_CLOCK),
        ),
        steps = listOf(
            "Relie la sortie horloge de ta machine maîtresse (ou TRIGGER d'un autre DFAM) à l'entrée ADV / CLOCK.",
            "Appuie d'abord sur RUN / STOP du DFAM qui reçoit l'horloge.",
            "Lance ensuite la machine maîtresse : le DFAM avance au rythme reçu.",
            "Tourne TEMPO : il n'a plus d'effet tant que l'horloge externe pilote.",
        ),
        expected = "Le DFAM joue calé sur la machine maîtresse.",
        exerciseTitle = "DFAM esclave",
        exercise = listOf(
            "Synchronise le DFAM sur une machine qui sort une horloge analogique (impulsions).",
            "Lance et arrête la maîtresse trois fois en vérifiant que le DFAM reste en place.",
        ),
        success = "Le DFAM reste calé à chaque relance.",
        style = "Hardtek / tribe : DFAM calé sur la boîte à rythmes principale pour des percussions en plus.",
        mistakes = listOf(
            "Lancer la maîtresse avant d'armer le DFAM : il rate les premières impulsions.",
            "Envoyer un signal lent ou une modulation sur ADV / CLOCK : il peut être ignoré.",
        ),
        quiz = listOf(
            qz("Que fait le DFAM à chaque front montant sur ADV / CLOCK ?", listOf("Avance d'un pas", "Revient au pas 1"), 0,
                "Le séquenceur avance d'un pas ; TEMPO est ignoré.", PATCH_CLOCK),
            qz("Quelle sortie relier pour piloter un second DFAM ?", listOf("TRIGGER", "VCA", "PITCH"), 0,
                "TRIGGER de l'unité principale vers ADV / CLOCK de la secondaire.", SYNC_DFAM),
            qz("Quel DFAM lancer en premier ?", listOf("La principale", "La secondaire (qui reçoit l'horloge)"), 1,
                "On appuie d'abord sur RUN / STOP de l'unité secondaire.", SYNC_DFAM),
            qz("L'entrée RUN / STOP synchronise-t-elle l'horloge interne ?", listOf("Oui", "Non"), 1,
                "Elle démarre et arrête la lecture, mais l'horloge interne n'est pas synchronisée.", PATCH_CLOCK),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(
            M4, 4, "Mélangeur et filtre",
            "Mélangeur, bruit, filtre LP / HP, résonance, enveloppe du filtre.",
            "Donner au son sa couleur et son attaque.",
            "Le filtre fait passer d'un kick rond à une caisse claire sale.",
            listOf(m4l1, m4l2, m4l3),
        ),
        CourseModule(
            M5, 5, "Amplificateur et séquenceur",
            "VCA, PITCH et VELOCITY par pas, transport, ADVANCE et TRIGGER.",
            "Programmer et régler un motif de huit pas.",
            "Accents et silences font le groove tribe et hardtek.",
            listOf(m5l1, m5l2, m5l3),
        ),
        CourseModule(
            M6, 6, "Patchbay et synchronisation",
            "Entrées, sorties, pré-câblage, patchs de modulation, horloge externe.",
            "Patcher des modulations et caler le DFAM sur ton set.",
            "Un DFAM synchronisé ajoute une couche de percussions vivantes au live.",
            listOf(m6l1, m6l2, m6l3),
        ),
    )
}
