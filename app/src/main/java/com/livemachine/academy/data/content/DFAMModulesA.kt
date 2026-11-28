package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** DFAM — modules 1 à 3 : prise en main, oscillateurs, modulation de hauteur. */
internal object DFAMModulesA {

    // Références du manuel (pages imprimées = pages du PDF)
    private val DEBALLAGE = dfam("Unpacking and Inspection", "6")
    private val SETUP = dfam("Setup and Connections", "6")
    private val SIGNAL = dfam("DFAM Overview, Signal Flow", "7")
    private val DEFAUT = dfam("Exploring the DFAM, Default Settings", "8")
    private val TOUR_1 = dfam("Exploring the DFAM", "8–9")
    private val TOUR_2 = dfam("Exploring the DFAM", "10–11")
    private val FREQ = dfam("VCO 1 Frequency / VCO 2 Frequency", "13")
    private val WAVE = dfam("VCO 1 Wave / VCO 2 Wave", "13")
    private val SYNC = dfam("Hard Sync", "14")
    private val FM = dfam("1→2 FM Amount", "14")
    private val PITCH_MOD = dfam("Seq Pitch Mod", "15")
    private val VCO_DECAY = dfam("VCO Decay", "15")
    private val VCO_EG = dfam("VCO 1 EG Amount / VCO 2 EG Amount", "16")
    private val SEQ_PITCH = dfam("Analog 8-Step Sequencer, Pitch", "22")

    // ---------------------------------------------------------------------
    // Module 1 — Prise en main
    // ---------------------------------------------------------------------
    private const val M1 = "dfam-m1"

    private val m1l1 = ctLesson(
        M1, 1, "Brancher et laisser chauffer", listOf(DEBALLAGE, SETUP), 10,
        objective = "Alimenter le DFAM, le relier à une écoute sans risque et lui laisser le temps de se stabiliser.",
        explanation = "Le DFAM n'a pas d'interrupteur : dès que l'adaptateur secteur fourni est branché à la prise 12 V DC de l'arrière, " +
            "il est allumé. C'est un instrument entièrement analogique : il faut quelques minutes de préchauffage pour que l'accord " +
            "des oscillateurs se stabilise (plus longtemps s'il a passé la nuit au froid). La sortie AUDIO OUT, à l'arrière, accepte un " +
            "câble jack 6,35 mm mono (TS) vers une enceinte amplifiée ou une table de mixage, ou un casque. Avant de brancher, " +
            "le bouton VOLUME doit être tout à gauche.",
        controls = listOf(
            cmd("Prise d'alimentation 12 V DC", "Adaptateur fourni ; pas d'interrupteur.", SETUP),
            cmd("AUDIO OUT", "Sortie audio mono, aussi utilisable au casque.", SETUP),
            cmd("VOLUME", "À zéro avant de brancher.", SETUP),
        ),
        steps = listOf(
            "Tourne le bouton VOLUME complètement à gauche.",
            "Relie AUDIO OUT à ton enceinte amplifiée ou à ta table avec un câble jack 6,35 mm mono (TS), ou branche un casque.",
            "Branche l'adaptateur fourni sur la prise 12 V DC, puis sur le secteur : le DFAM est allumé.",
            "Laisse-le chauffer quelques minutes avant de régler la hauteur des oscillateurs.",
        ),
        expected = "Le DFAM est alimenté, relié à ton écoute, volume encore à zéro.",
        exerciseTitle = "Installation propre",
        exercise = listOf(
            "Installe le DFAM à côté de ta machine principale avec le bon câble de sortie.",
            "Pendant le préchauffage, vérifie que les cinq câbles de patch fournis sont rangés hors de la patchbay.",
        ),
        success = "Le DFAM est prêt, sans câble TRS sur la sortie ligne et sans câble de patch branché.",
        mistakes = listOf(
            "Utiliser un câble TRS (symétrique) vers une table : le manuel prévient que le signal devient très faible (annulation de phase).",
            "Chercher un interrupteur : il n'y en a pas, le DFAM est allumé dès qu'il est alimenté.",
        ),
        quiz = listOf(
            qz("Comment allume-t-on le DFAM ?", listOf("Avec un interrupteur à l'arrière", "En branchant l'alimentation", "Avec RUN / STOP"), 1,
                "Il n'y a pas d'interrupteur : une fois l'alimentation branchée, le DFAM est allumé.", SETUP),
            qz("Pourquoi attendre quelques minutes avant de jouer ?", listOf("Pour charger une mémoire", "Pour que l'accord analogique se stabilise", "Pour une mise à jour"), 1,
                "Instrument analogique : l'accord des oscillateurs se stabilise après un préchauffage.", SETUP),
            qz("Quel câble pour la sortie ligne vers une table ?", listOf("Jack 6,35 mm mono (TS)", "Jack 6,35 mm symétrique (TRS)", "Câble MIDI"), 0,
                "Un câble TS ; un TRS en sortie ligne donne un signal très faible.", SETUP),
            qz("Où doit être le VOLUME avant de brancher la sortie ?", listOf("Au maximum", "Au centre", "Tout à gauche"), 2,
                "Le manuel demande de brancher avec le VOLUME tourné complètement à gauche.", SETUP),
        ),
    )

    private val m1l2 = ctLesson(
        M1, 2, "Le trajet du signal et les réglages par défaut", listOf(SIGNAL, DEFAUT), 15,
        objective = "Comprendre le chemin du son dans le DFAM et savoir revenir à un point de départ connu.",
        explanation = "Le DFAM n'a aucune mémoire : on part toujours d'un réglage de façade. Le son vient de deux oscillateurs (VCO 1, " +
            "VCO 2) et d'un générateur de bruit blanc ; un mélangeur à trois voies les envoie dans un filtre ladder 4 pôles (passe-bas " +
            "ou passe-haut), puis dans l'amplificateur (VCA) et le VOLUME. Trois enveloppes (VCO, VCF, VCA) n'ont qu'un réglage de " +
            "decay ; le séquenceur 8 pas déclenche les trois et fixe leur amplitude par la vélocité de chaque pas. La patchbay " +
            "compte 24 points (15 entrées, 9 sorties). Le manuel dessine, page 8, les « réglages par défaut » : c'est le point de " +
            "départ de la visite guidée et le réglage à retrouver quand le DFAM ne sonne plus.",
        controls = listOf(
            cmd("Sources : VCO 1, VCO 2, bruit", "Matière sonore.", SIGNAL),
            cmd("Mélangeur, filtre, VCA", "Dosage, timbre, volume.", SIGNAL),
            cmd("Séquenceur 8 pas", "Déclenche les enveloppes, hauteur et vélocité par pas.", SIGNAL),
            cmd("Schéma « Default Settings »", "Réglage de départ dessiné dans le manuel.", DEFAUT),
        ),
        steps = listOf(
            "Ouvre le manuel page 7 et suis du doigt le trajet : VCO 1, VCO 2, bruit → mélangeur → filtre → VCA → sortie.",
            "Débranche tous les câbles de la patchbay.",
            "Reproduis sur ton DFAM chaque bouton et chaque sélecteur du schéma de la page 8.",
            "Vérifie le schéma une seconde fois, potentiomètre par potentiomètre.",
        ),
        expected = "Ton DFAM est exactement dans la position du schéma de la page 8, sans câble.",
        exerciseTitle = "Retour au point de départ",
        exercise = listOf(
            "Dérègle volontairement cinq boutons au hasard.",
            "Reviens aux réglages par défaut en t'aidant du schéma.",
        ),
        success = "Tu retrouves les réglages par défaut en moins de trois minutes.",
        mistakes = listOf(
            "Oublier un câble dans la patchbay : il change le comportement du réglage de départ.",
            "Croire que le DFAM garde un son en mémoire : il n'a pas de presets internes.",
        ),
        quiz = listOf(
            qz("Combien de sources sonores le DFAM possède-t-il ?", listOf("Une", "Deux VCO et un bruit blanc", "Huit"), 1,
                "Deux oscillateurs et un générateur de bruit blanc.", SIGNAL),
            qz("Quel est l'ordre du trajet ?", listOf("Filtre → mélangeur → VCO", "Sources → mélangeur → filtre → VCA", "VCA → filtre → sources"), 1,
                "Les sources passent par le mélangeur, le filtre puis le VCA.", SIGNAL),
            qz("Combien d'enveloppes le DFAM possède-t-il ?", listOf("Une", "Trois (VCO, VCF, VCA)", "Huit"), 1,
                "Trois enveloppes : hauteur (VCO), filtre (VCF) et amplitude (VCA).", SIGNAL),
            qz("Où trouver le réglage de départ ?", listOf("Dans un menu", "Sur le schéma « Default Settings » du manuel", "En maintenant TRIGGER"), 1,
                "Les réglages par défaut sont dessinés au début de la visite guidée (p. 8).", DEFAUT),
        ),
    )

    private val m1l3 = ctLesson(
        M1, 3, "Premier rythme : la visite guidée", listOf(TOUR_1, TOUR_2), 20,
        objective = "Faire jouer le séquenceur et transformer une note de basse en coup de grosse caisse.",
        explanation = "Depuis les réglages par défaut, RUN / STOP lance le séquenceur. On descend VCO 1 FREQUENCY vers 10–11 h pour " +
            "obtenir une note grave qui se répète. VCO 1 EG AMOUNT vers 15 h ajoute un saut de hauteur à chaque note : c'est le " +
            "« punch ». VCO DECAY règle la vitesse de ce saut (9 h dans la visite). Les huit potentiomètres VELOCITY changent volume " +
            "et impact de chaque pas : au maximum, tous les coups sont identiques ; en les variant, on crée le rythme.",
        controls = listOf(
            cmd("RUN / STOP", "Lance ou arrête le séquenceur.", TOUR_1),
            cmd("VCO 1 FREQUENCY", "Hauteur de départ du VCO 1.", TOUR_1),
            cmd("VCO 1 EG AMOUNT", "Saut de hauteur à chaque note.", TOUR_1),
            cmd("VCO DECAY", "Durée du saut de hauteur.", TOUR_1),
            cmd("VELOCITY 1–8", "Volume et impact de chaque pas.", TOUR_1),
        ),
        steps = listOf(
            "Pars des réglages par défaut et appuie sur RUN / STOP.",
            "Tourne lentement VCO 1 FREQUENCY vers la gauche jusqu'à une note grave (entre 10 et 11 h).",
            "Monte VCO 1 EG AMOUNT à 15 h, puis règle VCO DECAY sur 9 h.",
            "Mets les huit VELOCITY au maximum : tous les coups sont pareils.",
            "Baisse certaines VELOCITY pour créer un rythme.",
        ),
        expected = "Une grosse caisse synthétique joue un rythme de huit pas, avec des coups forts et des coups faibles.",
        exerciseTitle = "Kick hardtek",
        exercise = listOf(
            "Règle les VELOCITY pour que les pas 1 et 5 soient au maximum et les autres plus bas.",
            "Ajuste VCO 1 EG AMOUNT entre le centre et 15 h jusqu'à un kick qui claque sans sonner « jouet ».",
        ),
        success = "Les pas 1 et 5 dominent et le kick garde du punch.",
        style = "Hardtek",
        mistakes = listOf(
            "Pousser VCO 1 EG AMOUNT trop loin : le manuel conseille de revenir vers le centre si le son a trop de punch.",
            "Mettre une VELOCITY au minimum en pensant baisser un peu : au minimum, le pas ne sonne plus.",
        ),
        quiz = listOf(
            qz("Quel bouton lance le séquenceur ?", listOf("TRIGGER", "RUN / STOP", "ADVANCE"), 1,
                "RUN / STOP démarre et arrête le séquenceur.", TOUR_1),
            qz("Qu'apporte VCO 1 EG AMOUNT tourné vers 15 h ?", listOf("Du bruit", "Un saut de hauteur à chaque note (punch)", "Un filtre plus ouvert"), 1,
                "Il applique l'enveloppe à la hauteur du VCO 1 : un saut à chaque note.", TOUR_1),
            qz("Que se passe-t-il avec les huit VELOCITY au maximum ?", listOf("Chaque coup sonne pareil", "Le tempo double", "Le son s'arrête"), 0,
                "Tous les pas ont alors la même intensité.", TOUR_1),
            qz("Quel réglage de VCO DECAY la visite propose-t-elle ?", listOf("7 h", "9 h", "17 h"), 1,
                "Après essai, la visite demande de régler VCO DECAY sur 9 h.", TOUR_1),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 2 — Les oscillateurs
    // ---------------------------------------------------------------------
    private const val M2 = "dfam-m2"

    private val m2l1 = ctLesson(
        M2, 1, "Fréquence et formes d'onde", listOf(FREQ, WAVE), 15,
        objective = "Régler la hauteur des deux oscillateurs et choisir entre triangle et carré.",
        explanation = "VCO 1 FREQUENCY et VCO 2 FREQUENCY fixent la hauteur de départ de chaque oscillateur sur dix octaves. Le manuel " +
            "conseille de partir des deux boutons au centre, qui correspond au centre des potentiomètres PITCH du séquenceur. Chaque " +
            "VCO a un sélecteur WAVE : TRIANGLE, doux et rond (toms, marimbas, sons de boîte à rythmes analogique) ; SQUARE (carré), " +
            "plus riche et agressif (basses profondes, sons métalliques en FM). Le carré paraît plus fort que le triangle : c'est normal. " +
            "Si le DFAM ne sonne plus, la hauteur peut être sortie de l'audible : vérifier la patchbay, remettre FREQUENCY au centre, " +
            "puis les EG AMOUNT au centre, puis SEQ PITCH MOD sur OFF, et enfin revenir aux réglages par défaut.",
        controls = listOf(
            cmd("VCO 1 FREQUENCY / VCO 2 FREQUENCY", "Hauteur de départ, dix octaves.", FREQ),
            cmd("VCO 1 WAVE / VCO 2 WAVE", "Triangle ou carré.", WAVE),
            cmd("VCO 2 LEVEL", "Fait entendre le VCO 2 dans le mélangeur.", TOUR_2),
        ),
        steps = listOf(
            "Depuis ton premier rythme, passe VCO 1 WAVE sur SQUARE et écoute la différence.",
            "Monte VCO 2 LEVEL au maximum.",
            "Tourne VCO 2 FREQUENCY jusqu'à ce qu'il sonne accordé avec le VCO 1.",
            "Reviens sur TRIANGLE pour le VCO 1 et compare les deux formes.",
        ),
        expected = "Les deux oscillateurs sonnent ensemble, accordés ; tu entends la différence triangle / carré.",
        exerciseTitle = "Deux couleurs de kick",
        exercise = listOf(
            "Fais un kick rond en triangle, puis un kick plus dur en carré, au même réglage.",
            "Garde celui qui convient le mieux à un morceau tribe.",
        ),
        success = "Tu sais dire quelle forme d'onde est la plus agressive et pourquoi.",
        style = "Tribe",
        mistakes = listOf(
            "Croire que le carré est « trop fort » par défaut : sa richesse harmonique le fait paraître plus fort, c'est normal.",
            "Chercher une panne quand le son disparaît : souvent la hauteur est sortie de l'audible (voir la procédure du manuel).",
        ),
        quiz = listOf(
            qz("Sur combien d'octaves s'étend VCO FREQUENCY ?", listOf("Une", "Dix", "Cent"), 1,
                "Les boutons FREQUENCY couvrent dix octaves.", FREQ),
            qz("Quelle forme d'onde est la plus douce ?", listOf("Triangle", "Carré"), 0,
                "Le triangle est lisse et « chaud » ; le carré est plus riche et agressif.", WAVE),
            qz("Plus de son : que vérifier d'abord ?", listOf("Le firmware", "La patchbay", "La carte SD"), 1,
                "Un mauvais câblage est la cause la plus fréquente ; ensuite FREQUENCY au centre, etc.", FREQ),
            qz("Quelle position conseille le manuel pour commencer ?", listOf("Les deux FREQUENCY au centre", "Au minimum", "Au maximum"), 0,
                "Le centre de FREQUENCY correspond au centre des PITCH du séquenceur.", FREQ),
        ),
    )

    private val m2l2 = ctLesson(
        M2, 2, "Hard Sync", listOf(SYNC, TOUR_2), 15,
        objective = "Utiliser la synchronisation forcée pour des timbres métalliques tout en gardant la hauteur du VCO 1.",
        explanation = "HARD SYNC sur ON force le VCO 2 à repartir au début de son cycle en même temps que le VCO 1. Sa forme d'onde " +
            "devient plus complexe : sons tranchants, métalliques, proches d'un flanger, et la hauteur du VCO 2 reste calée sur celle " +
            "du VCO 1. En tournant VCO 2 FREQUENCY, on change donc le timbre et non la note. Attention : si le VCO 2 est réglé plus " +
            "bas que le VCO 1, il n'a pas le temps de finir son cycle et ne sort presque plus de son (surtout en carré).",
        controls = listOf(
            cmd("HARD SYNC", "ON : le VCO 2 suit la phase du VCO 1.", SYNC),
            cmd("VCO 2 FREQUENCY", "Avec sync, change le timbre.", SYNC),
        ),
        steps = listOf(
            "VCO 2 LEVEL au maximum, VCO 2 accordé avec le VCO 1.",
            "Mets HARD SYNC sur ON.",
            "Tourne VCO 2 FREQUENCY d'un bout à l'autre plusieurs fois : la hauteur reste liée au VCO 1, le timbre change.",
            "Arrête-toi sur un timbre qui te plaît.",
        ),
        expected = "Un son métallique dont la note ne change pas quand tu tournes VCO 2 FREQUENCY.",
        exerciseTitle = "Balayage sync en live",
        exercise = listOf(
            "Pendant que le séquenceur tourne, balaie lentement VCO 2 FREQUENCY sur huit mesures.",
            "Termine sur un réglage plus haut que le VCO 1 pour ne pas perdre le son.",
        ),
        success = "Le balayage est audible sans trou de son.",
        style = "Mentalcore",
        mistakes = listOf(
            "Régler le VCO 2 sous le VCO 1 avec sync : le VCO 2 sort peu ou pas de son.",
            "Oublier de monter VCO 2 LEVEL : la sync ne s'entend pas.",
        ),
        quiz = listOf(
            qz("Que fait HARD SYNC sur ON ?", listOf("Synchronise le tempo", "Force la phase du VCO 2 sur celle du VCO 1", "Coupe le VCO 1"), 1,
                "Le VCO 2 repart en même temps que le VCO 1.", SYNC),
            qz("Avec sync, tourner VCO 2 FREQUENCY change surtout…", listOf("La note", "Le timbre", "Le tempo"), 1,
                "La hauteur reste liée au VCO 1 ; le timbre change.", SYNC),
            qz("Pourquoi le son peut-il disparaître avec sync ?", listOf("VCO 2 plus bas que VCO 1", "VOLUME au maximum", "Triangle choisi"), 0,
                "Sous la fréquence du VCO 1, le VCO 2 ne finit pas son cycle.", SYNC),
            qz("Quel type de son le Hard Sync produit-il ?", listOf("Doux et rond", "Tranchant, métallique", "Bruit blanc"), 1,
                "Le manuel parle de sons tranchants, métalliques, proches d'un flanger.", SYNC),
        ),
    )

    private val m2l3 = ctLesson(
        M2, 3, "La modulation de fréquence 1→2", listOf(FM, TOUR_2), 15,
        objective = "Créer des percussions complexes en modulant la fréquence du VCO 2 par le VCO 1.",
        explanation = "Le bouton 1→2 FM AMOUNT fait varier très vite la hauteur du VCO 2 au rythme du VCO 1 : de nouvelles harmoniques " +
            "apparaissent. Plus on tourne à droite, plus la modulation est forte. Le résultat dépend des hauteurs des deux VCO : " +
            "complexe, élastique, proche d'une cloche ou agressif. La FM s'entend beaucoup mieux avec HARD SYNC sur OFF.",
        controls = listOf(
            cmd("1→2 FM AMOUNT", "Quantité de modulation du VCO 2 par le VCO 1.", FM),
            cmd("VCO 1 / VCO 2 FREQUENCY", "Leur rapport change le caractère de la FM.", FM),
            cmd("HARD SYNC", "Sur OFF, la FM est plus nette.", TOUR_2),
        ),
        steps = listOf(
            "Mets HARD SYNC sur OFF et VCO 2 LEVEL au maximum.",
            "Monte doucement 1→2 FM AMOUNT jusqu'à 12 h en écoutant.",
            "Tourne VCO 2 FREQUENCY, puis VCO 1 FREQUENCY : le caractère de la FM change.",
            "Garde le son qui te plaît pour la suite.",
        ),
        expected = "Un son percussif métallique ou « cloche » produit par la FM.",
        exerciseTitle = "Cloche tribe",
        exercise = listOf(
            "Fabrique un son de cloche avec la FM et le VCO 2 en carré.",
            "Fais-le jouer seulement sur deux pas en baissant les autres VELOCITY au minimum.",
        ),
        success = "Une cloche métallique ponctue la boucle sur deux pas.",
        style = "Tribe",
        mistakes = listOf(
            "Tester la FM avec HARD SYNC sur ON : elle est beaucoup moins audible.",
            "Monter FM AMOUNT d'un coup : on passe à côté des réglages intermédiaires les plus musicaux.",
        ),
        quiz = listOf(
            qz("Qui module qui avec 1→2 FM AMOUNT ?", listOf("Le VCO 2 module le VCO 1", "Le VCO 1 module le VCO 2", "Le bruit module le VCA"), 1,
                "Le VCO 1 module la fréquence du VCO 2.", FM),
            qz("Quand la FM est-elle la plus audible ?", listOf("HARD SYNC sur OFF", "HARD SYNC sur ON"), 0,
                "Le manuel précise que la FM est bien plus nette avec HARD SYNC sur OFF.", TOUR_2),
            qz("De quoi dépend le caractère de la FM ?", listOf("Du tempo", "Des hauteurs des deux oscillateurs", "Du VOLUME"), 1,
                "Les sons de FM dépendent fortement des hauteurs du VCO 1 et du VCO 2.", FM),
            qz("Que crée la FM ?", listOf("De nouvelles harmoniques", "Un écho", "Un arrêt du séquenceur"), 0,
                "Moduler très vite une hauteur crée des harmoniques absentes des deux signaux d'origine.", FM),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 3 — Modulation de hauteur
    // ---------------------------------------------------------------------
    private const val M3 = "dfam-m3"

    private val m3l1 = ctLesson(
        M3, 1, "SEQ PITCH MOD : quels oscillateurs suivent le séquenceur", listOf(PITCH_MOD, SEQ_PITCH), 15,
        objective = "Choisir quels oscillateurs reçoivent la hauteur programmée par les potentiomètres PITCH.",
        explanation = "Le sélecteur à trois positions SEQ PITCH MOD décide où vont les PITCH 1–8. VCO 1&2 : les deux oscillateurs " +
            "suivent la mélodie (basses et leads, VCO accordés à l'unisson). OFF : aucun oscillateur ne suit, utile pour les sons " +
            "fixes et non accordés comme les charleys ; la tension reste disponible à la sortie PITCH de la patchbay. VCO 2 : seul le " +
            "VCO 2 suit, pratique avec la FM ou la sync pour faire bouger le timbre d'une percussion fixe. Un PITCH à droite du centre " +
            "monte la note du pas, à gauche il la descend ; la plage est très large (environ dix octaves).",
        controls = listOf(
            cmd("SEQ PITCH MOD (VCO 1&2 / OFF / VCO 2)", "Destination des PITCH 1–8.", PITCH_MOD),
            cmd("PITCH 1–8", "Hauteur ajoutée ou retirée pour chaque pas.", SEQ_PITCH),
        ),
        steps = listOf(
            "Mets SEQ PITCH MOD sur VCO 1&2 : de petites variations de hauteur apparaissent.",
            "Fais de très petits mouvements sur les huit PITCH.",
            "Passe sur VCO 2 avec la FM active : seul le timbre bouge.",
            "Passe sur OFF : la hauteur ne bouge plus du tout.",
        ),
        expected = "Tu entends clairement la différence entre les trois positions.",
        exerciseTitle = "Basse séquencée",
        exercise = listOf(
            "En VCO 1&2, programme une petite ligne de basse sur les huit pas.",
            "Reste dans de petits mouvements de PITCH : la plage est très large.",
        ),
        success = "Une ligne de basse de huit notes tourne sans sauts d'octave involontaires.",
        style = "Acidcore",
        mistakes = listOf(
            "Tourner les PITCH à grands gestes : leur plage d'environ dix octaves rend les petits réglages essentiels.",
            "Croire que les PITCH ne servent plus en OFF : la tension reste sur la sortie PITCH de la patchbay.",
        ),
        quiz = listOf(
            qz("Quelle position pour une ligne de basse sur les deux VCO ?", listOf("OFF", "VCO 1&2", "VCO 2"), 1,
                "VCO 1&2 envoie les PITCH aux deux oscillateurs.", PITCH_MOD),
            qz("Quelle position pour un charley de hauteur fixe ?", listOf("OFF", "VCO 1&2", "VCO 2"), 0,
                "OFF convient aux sons non accordés comme les charleys.", PITCH_MOD),
            qz("Un PITCH tourné à gauche du centre…", listOf("Monte la note", "Descend la note du pas", "Coupe le pas"), 1,
                "À gauche du centre, la valeur est retirée de VCO FREQUENCY.", SEQ_PITCH),
            qz("En OFF, où reste disponible la tension des PITCH ?", listOf("Nulle part", "Sortie PITCH de la patchbay", "Sortie AUDIO OUT"), 1,
                "La tension des PITCH reste à la sortie PITCH.", PITCH_MOD),
        ),
    )

    private val m3l2 = ctLesson(
        M3, 2, "VCO DECAY : la vitesse du saut de hauteur", listOf(VCO_DECAY), 15,
        objective = "Régler la durée de l'enveloppe de hauteur pour passer d'un clic sec à un « boom » long.",
        explanation = "L'enveloppe VCO module la hauteur des oscillateurs ; VCO DECAY règle sa durée. À 7 h (minimum) : effet presque " +
            "immédiat, très sec, pour l'attaque. Entre 9 et 10 h : le punch d'une peau de batterie frappée ; vers 10 h, l'attaque " +
            "devient plus élastique. À 12 h et au-delà : « booms » exagérés ou sons spatiaux, avec des séquences lentes. Si les " +
            "VCO EG AMOUNT sont au centre, VCO DECAY semble sans effet. Les VELOCITY 1–8 modifient aussi l'amplitude et la durée " +
            "de cette enveloppe à chaque pas.",
        controls = listOf(
            cmd("VCO DECAY", "Durée de l'enveloppe de hauteur.", VCO_DECAY),
            cmd("VCO 1 EG AMOUNT", "Doit être hors du centre pour entendre VCO DECAY.", VCO_DECAY),
            cmd("VELOCITY 1–8", "Varient amplitude et durée de l'enveloppe par pas.", VCO_DECAY),
        ),
        steps = listOf(
            "VCO 1 EG AMOUNT vers 15 h.",
            "VCO DECAY à 7 h : écoute le clic sec.",
            "Monte à 9–10 h : le punch d'une batterie.",
            "Passe à 12 h et ralentis le tempo : un « boom » long.",
        ),
        expected = "Tu sais placer VCO DECAY selon le son voulu : clic, punch ou boom.",
        exerciseTitle = "Trois kicks",
        exercise = listOf(
            "Note trois positions de VCO DECAY : clic, punch, boom.",
            "Choisis celle qui colle à un tempo rapide de tribecore.",
        ),
        success = "Tu passes d'un kick à l'autre en un seul geste.",
        style = "TribeCore",
        mistakes = listOf(
            "Tourner VCO DECAY avec les EG AMOUNT au centre : rien ne change, c'est normal.",
            "Oublier que des VELOCITY fortes allongent un peu l'enveloppe.",
        ),
        quiz = listOf(
            qz("VCO DECAY règle…", listOf("La durée de l'enveloppe de hauteur", "Le volume", "Le tempo"), 0,
                "C'est le temps de decay de l'enveloppe dédiée à la hauteur.", VCO_DECAY),
            qz("Quelle zone pour le punch d'une batterie ?", listOf("7 h", "9–10 h", "17 h"), 1,
                "Le manuel associe 9–10 h au punch d'une peau frappée.", VCO_DECAY),
            qz("Pourquoi VCO DECAY peut-il sembler inactif ?", listOf("VCO EG AMOUNT au centre", "VOLUME bas", "HARD SYNC sur OFF"), 0,
                "Au centre, les EG AMOUNT n'appliquent aucune modulation de hauteur.", VCO_DECAY),
            qz("Quelle commande agit aussi sur l'enveloppe à chaque pas ?", listOf("TEMPO", "VELOCITY 1–8", "RESONANCE"), 1,
                "Les VELOCITY règlent l'amplitude et la durée de l'enveloppe VCO.", VCO_DECAY),
        ),
    )

    private val m3l3 = ctLesson(
        M3, 3, "VCO EG AMOUNT : montée ou chute de hauteur", listOf(VCO_EG), 15,
        objective = "Utiliser les commandes bipolaires pour des sauts de hauteur vers le haut ou vers le bas.",
        explanation = "VCO 1 EG AMOUNT et VCO 2 EG AMOUNT dosent l'effet de l'enveloppe VCO sur chaque oscillateur. Comme toutes les " +
            "commandes AMOUNT du DFAM, ils sont bipolaires. Au centre : pas de modulation, hauteur stable. À droite : sauts vers le " +
            "haut à chaque note, sons plus durs et agressifs. À gauche : chutes de hauteur, sons « boing », proches des jeux vidéo. " +
            "VCO DECAY règle la vitesse du retour à la note. Petites valeurs = percussions naturelles ; grandes valeurs = sons extrêmes.",
        controls = listOf(
            cmd("VCO 1 EG AMOUNT", "Enveloppe vers la hauteur du VCO 1, bipolaire.", VCO_EG),
            cmd("VCO 2 EG AMOUNT", "Enveloppe vers la hauteur du VCO 2, bipolaire.", VCO_EG),
        ),
        steps = listOf(
            "Mets VCO 1 EG AMOUNT au centre : la hauteur est stable.",
            "Tourne à droite : chaque note démarre plus haut.",
            "Tourne à gauche du centre : chaque note plonge, son « boing ».",
            "Règle VCO 2 EG AMOUNT dans le sens opposé et écoute les deux ensemble.",
        ),
        expected = "Tu entends la différence entre modulation positive, nulle et négative.",
        exerciseTitle = "Tom qui tombe",
        exercise = listOf(
            "Fabrique un tom en triangle avec un EG AMOUNT positif modéré.",
            "Compare avec la même valeur en négatif et garde le plus musical.",
        ),
        success = "Tu sais créer un tom naturel avec une petite valeur positive.",
        mistakes = listOf(
            "Croire que le centre est le minimum : c'est l'absence de modulation, la gauche est négative.",
            "Utiliser de grandes valeurs pour un son naturel : le manuel conseille des valeurs faibles.",
        ),
        quiz = listOf(
            qz("Que signifie « bipolaire » ?", listOf("Deux sorties", "Des valeurs positives et négatives", "Deux couleurs"), 1,
                "Les AMOUNT ont des valeurs positives (+) et négatives (−).", VCO_EG),
            qz("Au centre, VCO EG AMOUNT…", listOf("N'applique aucune enveloppe", "Applique le maximum", "Coupe le son"), 0,
                "Au centre, la hauteur reste stable.", VCO_EG),
            qz("À gauche du centre, on obtient…", listOf("Des sauts vers le haut", "Des chutes de hauteur", "Plus de bruit"), 1,
                "La modulation négative donne des chutes de hauteur.", VCO_EG),
            qz("Pour une percussion naturelle, il faut…", listOf("De petites valeurs", "Le maximum"), 0,
                "De petites valeurs donnent des percussions plus naturelles.", VCO_EG),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(
            M1, 1, "Prise en main",
            "Brancher, comprendre le trajet du signal, réglages par défaut, premier rythme.",
            "Faire jouer un premier rythme depuis un réglage connu.",
            "Un kick analogique qui sonne bien porte déjà une boucle hardtek.",
            listOf(m1l1, m1l2, m1l3),
        ),
        CourseModule(
            M2, 2, "Les oscillateurs",
            "Fréquence, formes d'onde, Hard Sync et FM.",
            "Créer des timbres variés avec les deux VCO.",
            "Sync et FM donnent les sons métalliques de la tribe et de la mentalcore.",
            listOf(m2l1, m2l2, m2l3),
        ),
        CourseModule(
            M3, 3, "La modulation de hauteur",
            "SEQ PITCH MOD, VCO DECAY et VCO EG AMOUNT.",
            "Sculpter l'attaque et la mélodie de chaque coup.",
            "Le saut de hauteur fait la différence entre un son « jouet » et un kick qui frappe.",
            listOf(m3l1, m3l2, m3l3),
        ),
    )
}
