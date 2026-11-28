package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** MiniBrute 2S — modules 1 à 4 : prise en main, oscillateurs, filtre, enveloppe AD, LFO et saturation. */
internal object MiniBrute2SModulesA {

    // Références du manuel (pages imprimées ; page du PDF = page imprimée + 6)
    private val CONNEXIONS = mb2s("2.4 Connecter le MiniBrute 2S", "7")
    private val PRECHAUFFE = mb2s("2.5 Préchauffage et accord général", "9")
    private val ARRIERE = mb2s("4.6 Le panneau arrière", "27")
    private val PATCH_BASE = mb2s("3.1 Créer votre premier son : le patch de base", "10–11")
    private val FONCTIONS = mb2s("4.1–4.2 Fonctions principales, bouton Shift", "22")
    private val OCTAVE = mb2s("4.5.1 Les pads Octave & Transpose", "25–26")
    private val SOUS_OSC = mb2s("3.2 Ajouter un second oscillateur", "11")
    private val VCO1 = mb2s("5.2 VCO 1", "31–33")
    private val VCO2 = mb2s("5.3 VCO 2", "34–35")
    private val MIXER = mb2s("5.6.1–5.6.6 Osc Mixer", "42–43")
    private val FILTRE = mb2s("5.4.1–5.4.3 Filter : Mode, Cutoff, Resonance", "36–37")
    private val FILTRE_FM = mb2s("5.4.4 Le potentiomètre FM (ADSR)", "37")
    private val ADSR = mb2s("3.5.2 L'enveloppe ADSR", "16")
    private val ADSR_CURSEURS = mb2s("5.6.7 Les curseurs ADSR Envelope", "44")
    private val RM = mb2s("5.4.5 RM (Resonance Modulation)", "38")
    private val ATT1 = mb2s("5.4.6 Att 1 > Cutoff", "39")
    private val PRESSION = mb2s("3.3 Balayer le filtre avec un pad", "12")
    private val AD = mb2s("3.5.1 L'enveloppe AD", "15")
    private val AD_CURSEURS = mb2s("5.6.8 Les curseurs AD Envelope", "44")
    private val AD_SWITCH = mb2s("5.6.9 Les interrupteurs AD Envelope", "45")
    private val LFO = mb2s("5.1 Les LFO", "28–30")
    private val AMP = mb2s("5.5.1–5.5.3 Brute Factor, Master Volume, Global Tune", "40")
    private val ATT2 = mb2s("5.5.4 Att 2 > Amp", "41")

    // ---------------------------------------------------------------------
    // Module 1 — Prise en main
    // ---------------------------------------------------------------------
    private const val M1 = "mb2s-m1"

    private val m1l1 = ctLesson(
        M1, 1, "Brancher, préchauffer, accorder", listOf(CONNEXIONS, PRECHAUFFE, ARRIERE), 15,
        objective = "Brancher le MiniBrute 2S sans risque, le laisser chauffer et l'accorder avec les autres instruments.",
        explanation = "Éteins tout avant de brancher, mets les niveaux à zéro, allume l'amplification en dernier. L'adaptateur fourni " +
            "(12 V DC, 2 A) est le seul à utiliser. Le son sort par la prise Master (jack 6,35 mm) et le casque. Comme tout synthé " +
            "analogique, il faut 5 à 10 minutes de préchauffage pour que les oscillateurs soient stables ; ensuite on l'accorde avec un " +
            "accordeur externe et le potentiomètre Global Tune.",
        controls = listOf(
            cmd("Prise Master et casque", "Sorties audio.", ARRIERE),
            cmd("Alimentation DC", "Uniquement l'adaptateur fourni (12 V, 2 A).", ARRIERE),
            cmd("MIDI In/Out, USB", "Notes, contrôle et horloge.", ARRIERE),
            cmd("Global Tune", "Accord général une fois préchauffé.", PRECHAUFFE),
        ),
        steps = listOf(
            "Tout éteint, relie la prise Master à ta table ou à ton ampli, puis branche l'adaptateur fourni.",
            "Mets les volumes à zéro, allume le MiniBrute 2S, puis l'amplification en dernier.",
            "Attends 5 à 10 minutes de préchauffage.",
            "Joue une note et règle Global Tune avec un accordeur externe.",
        ),
        expected = "Le synthé sonne juste par rapport à l'accordeur.",
        exerciseTitle = "Accord avant la session",
        exercise = listOf(
            "Allume le MiniBrute 2S, prépare tes autres machines pendant le préchauffage.",
            "Accorde-le après 10 minutes et vérifie avec une autre machine.",
        ),
        success = "Le MiniBrute 2S et l'autre machine jouent la même note sans battement.",
        mistakes = listOf(
            "Accorder tout de suite après l'allumage : l'accord dérive pendant le préchauffage.",
            "Brancher les câbles appareils allumés : le manuel demande de tout éteindre d'abord.",
        ),
        quiz = listOf(
            qz("Combien de temps de préchauffage faut-il prévoir ?", listOf("Aucun", "5 à 10 minutes", "1 heure"), 1,
                "Environ cinq à dix minutes, plus si la pièce est froide.", PRECHAUFFE),
            qz("Quel potentiomètre sert à accorder l'instrument ?", listOf("Fine Tune", "Global Tune", "Tune VCO 2"), 1,
                "Global Tune accorde le MiniBrute 2S une fois préchauffé.", PRECHAUFFE),
            qz("Quelle alimentation utiliser ?", listOf("N'importe quel 12 V", "Uniquement l'adaptateur fourni", "L'USB"), 1,
                "Le manuel demande de brancher uniquement l'adaptateur fourni (12 V DC, 2 A).", ARRIERE),
            qz("Que faut-il allumer en dernier ?", listOf("Le synthé", "L'amplificateur ou le système d'écoute", "L'ordinateur"), 1,
                "On allume l'amplification en dernier, puis on monte les volumes.", CONNEXIONS),
        ),
    )

    private val m1l2 = ctLesson(
        M1, 2, "Le patch de base", listOf(PATCH_BASE), 15,
        objective = "Partir d'un réglage neutre connu, à partir duquel toutes les leçons suivantes travaillent.",
        explanation = "Le MiniBrute 2S n'a pas de mémoire de sons : tout se règle sur la façade. Le « patch de base » met potentiomètres " +
            "au minimum, curseurs en bas, FM 1 et RM au centre, les trois Tune au centre. Ensuite : LFO 1 et 2 sur Free, Rate au " +
            "centre ; VCO 2 sur Sine et Fine ; filtre en mode LP ; enveloppe AD sur Gate et Once ; curseur de l'onde carrée au maximum ; " +
            "Cutoff au maximum ; Sync sur Int ; aucun câble dans la patchbay. On obtient une grosse onde carrée.",
        controls = listOf(
            cmd("Curseurs Osc Mixer", "Niveau de chaque forme d'onde.", PATCH_BASE),
            cmd("Mode du filtre", "LP pour le patch de base.", PATCH_BASE),
            cmd("Interrupteurs AD (Gate, Once)", "Comportement de l'enveloppe d'amplitude.", PATCH_BASE),
            cmd("Bouton Sync", "Int pour l'horloge interne.", PATCH_BASE),
        ),
        steps = listOf(
            "Mets tous les potentiomètres au minimum et tous les curseurs en bas ; FM 1, RM et les trois Tune au centre.",
            "Règle LFO 1 et 2 sur Free avec Rate au centre, VCO 2 sur Sine et Fine.",
            "Mets le filtre en LP et l'enveloppe AD sur Gate et Once.",
            "Monte le curseur de l'onde carrée au maximum et le Cutoff au maximum.",
            "Règle Sync sur Int, vérifie qu'aucun câble n'est branché, puis joue un pad.",
        ),
        expected = "Chaque pad joue une onde carrée franche et stable.",
        exerciseTitle = "Patch de base les yeux fermés",
        exercise = listOf(
            "Fais n'importe quel réglage, puis reviens au patch de base sans regarder le manuel.",
            "Vérifie ensuite chaque point avec la page 10.",
        ),
        success = "Tu retrouves le patch de base en moins de deux minutes.",
        mistakes = listOf(
            "Oublier un câble dans la patchbay : il modifie le son de base.",
            "Laisser un curseur d'onde levé en plus de l'onde carrée.",
        ),
        quiz = listOf(
            qz("Le MiniBrute 2S a-t-il des presets mémorisés ?", listOf("Oui, 64", "Non, tout se règle sur la façade"), 1,
                "Il n'a pas de présélections : la création sonore est entièrement sur la façade.", PATCH_BASE),
            qz("Dans le patch de base, quel mode de filtre ?", listOf("LP", "HP", "Notch"), 0,
                "Le patch de base met le filtre en LP (passe-bas).", PATCH_BASE),
            qz("Quelle forme d'onde est au maximum dans le patch de base ?", listOf("Dent de scie", "Carrée", "Bruit"), 1,
                "On monte le curseur de l'onde carrée au maximum.", PATCH_BASE),
            qz("Où placer FM 1 et RM ?", listOf("Au minimum", "Au centre (12 h)", "Au maximum"), 1,
                "Les contrôles bipolaires FM 1 et RM sont mis en position centrale.", PATCH_BASE),
        ),
    )

    private val m1l3 = ctLesson(
        M1, 3, "Pads, octaves et fonctions Shift", listOf(FONCTIONS, OCTAVE), 15,
        objective = "Jouer sur les pads, changer d'octave et comprendre les inscriptions bleues de la façade.",
        explanation = "Les pads forment un clavier d'une octave, sensible à la vélocité et à la pression (aftertouch) qui peuvent " +
            "moduler le son. Les pads Octave − et Octave + décalent les pads d'octave (jusqu'à trois fois de chaque côté) ; appuyer " +
            "sur les deux ramène au centre. Le changement s'applique au prochain pad joué. Le bouton Shift, entouré de bleu, donne " +
            "accès aux fonctions écrites en bleu dans la partie séquenceur (pas celles écrites sous les potentiomètres du synthé ou " +
            "dans la patchbay).",
        controls = listOf(
            cmd("Pads", "Clavier d'une octave, vélocité et pression.", FONCTIONS),
            cmd("Octave − / Octave +", "Décalent les pads d'octave ; les deux ensemble = centre.", OCTAVE),
            cmd("Shift", "Fonctions secondaires écrites en bleu (partie séquenceur).", FONCTIONS),
        ),
        steps = listOf(
            "Joue une mélodie sur les pads en appuyant plus ou moins fort.",
            "Appuie sur Octave − puis joue : les notes sont plus graves, le pad clignote.",
            "Appuie encore pour descendre plus bas, puis appuie sur les deux pads Octave pour revenir au centre.",
            "Repère sur la façade trois inscriptions bleues de la partie séquenceur.",
        ),
        expected = "Tu joues sur plusieurs octaves et tu sais revenir au centre.",
        exerciseTitle = "Basse et lead",
        exercise = listOf(
            "Joue une ligne de basse deux octaves plus bas, puis la même ligne deux octaves plus haut.",
            "Reviens au centre d'un seul geste.",
        ),
        success = "La même ligne sonne en basse puis en lead, et tu reviens au centre.",
        mistakes = listOf(
            "Attendre un changement immédiat après Octave : il s'applique au prochain pad joué.",
            "Chercher une fonction Shift sous les potentiomètres du synthé : Shift ne concerne que la partie séquenceur.",
        ),
        quiz = listOf(
            qz("Comment revenir à l'octave centrale ?", listOf("Appuyer sur les deux pads Octave", "Shift + Octave", "Stop"), 0,
                "Appuyer simultanément sur Octave − et Octave + ramène au centre.", OCTAVE),
            qz("Que signifie une inscription bleue dans la partie séquenceur ?",
                listOf("Une fonction accessible avec Shift", "Un connecteur", "Un réglage d'usine"), 0,
                "Shift + la touche ou le pad active la fonction écrite en bleu.", FONCTIONS),
            qz("Les pads sont-ils sensibles à la pression ?", listOf("Oui", "Non"), 0,
                "La pression produit un signal qui peut moduler le vibrato, le filtre, etc.", FONCTIONS),
            qz("Combien de fois peut-on décaler les pads vers le haut ?", listOf("1", "3", "6"), 1,
                "Les pads peuvent être décalés trois fois vers le haut (et trois vers le bas).", OCTAVE),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 2 — Oscillateurs et mélangeur
    // ---------------------------------------------------------------------
    private const val M2 = "mb2s-m2"

    private val m2l1 = ctLesson(
        M2, 1, "Le VCO 1 et ses trois formes d'onde", listOf(VCO1, MIXER), 20,
        objective = "Utiliser les trois formes d'onde du VCO 1 et leurs transformations : Ultrasaw, largeur d'impulsion, Metalizer.",
        explanation = "Le VCO 1 fournit dent de scie, carrée et triangle, mélangées par leurs curseurs dans l'Osc Mixer. Ultrasaw Amt " +
            "épaissit la dent de scie (deux copies déphasées, animées par le LFO 2 par défaut). Pulse Width rétrécit l'onde carrée, " +
            "PWM la fait varier avec le LFO 1. Metalizer « plie » le triangle en ondes riches et métalliques ; Metal Mod règle sa " +
            "modulation, par la vélocité par défaut. Fine Tune ajuste la hauteur du VCO 1, Glide fait glisser d'une note à l'autre.",
        controls = listOf(
            cmd("Curseurs dent de scie / carrée / triangle", "Niveau de chaque forme du VCO 1.", MIXER),
            cmd("Ultrasaw Amt", "Épaissit la dent de scie.", VCO1),
            cmd("Pulse Width / PWM", "Largeur de l'onde carrée, fixe ou modulée.", VCO1),
            cmd("Metalizer / Metal Mod", "Plie le triangle ; modulation du pliage.", VCO1),
            cmd("Glide", "Portamento, jusqu'à 3 s.", VCO1),
        ),
        steps = listOf(
            "Depuis le patch de base, baisse l'onde carrée et monte la dent de scie.",
            "Tourne Ultrasaw Amt : le son s'élargit.",
            "Reviens à l'onde carrée et tourne Pulse Width, puis PWM.",
            "Passe au triangle et tourne Metalizer doucement.",
            "Monte Glide et joue deux notes éloignées.",
        ),
        expected = "Tu entends une dent de scie épaisse, une impulsion fine qui bouge et un triangle métallique.",
        exerciseTitle = "Trois sons de base",
        exercise = listOf(
            "Crée une basse en dent de scie + Ultrasaw, un son « acide » en impulsion fine et un son métallique au Metalizer.",
            "Note les réglages de chacun.",
        ),
        success = "Tu retrouves chaque son à partir de tes notes.",
        style = "Mentalcore : le Metalizer donne des sons agressifs et métalliques.",
        mistakes = listOf(
            "Tourner Pulse Width en écoutant la dent de scie : il n'agit que sur l'onde carrée.",
            "Mettre Metalizer au maximum puis s'étonner que Metal Mod n'ait plus d'effet : ils partagent la même plage.",
        ),
        quiz = listOf(
            qz("Sur quelle onde agit Ultrasaw Amt ?", listOf("Carrée", "Dent de scie", "Triangle"), 1,
                "Ultrasaw n'affecte que la dent de scie du VCO 1.", VCO1),
            qz("Quelle source module PWM par défaut ?", listOf("LFO 1", "LFO 2", "VCO 2"), 0,
                "La largeur d'impulsion est modulée par le LFO 1 (pré-câblage).", VCO1),
            qz("Que fait Metalizer ?",
                listOf("Plie le triangle en ondes riches en harmoniques", "Ajoute un écho", "Change l'octave"), 0,
                "Il replie les pics du triangle pour créer des ondes complexes.", VCO1),
            qz("Quel est le temps maximal du Glide ?", listOf("0,5 s", "3 s", "14 s"), 1,
                "Au maximum, il faut trois secondes pour glisser d'une note à l'autre.", VCO1),
        ),
    )

    private val m2l2 = ctLesson(
        M2, 2, "Le VCO 2 : sous-oscillateur, LFO et FM", listOf(VCO2, SOUS_OSC, VCO1), 20,
        objective = "Utiliser le second oscillateur pour épaissir le son, moduler le VCO 1 ou servir de LFO.",
        explanation = "Le VCO 2 suit la hauteur du VCO 1 par défaut. Son interrupteur Wave choisit sinus, dent de scie ou carrée ; " +
            "Range choisit Fine (± une octave environ), All (toute la plage) ou LFO (oscillateur lent). Tune règle sa hauteur dans " +
            "cette plage, et son niveau se règle avec le curseur Osc 2. Le potentiomètre FM du VCO 1 module la hauteur du VCO 1 par " +
            "le VCO 2 : vibrato avec Range sur LFO, son métallique ou percussif sur Fine ou All.",
        controls = listOf(
            cmd("Tune (VCO 2)", "Hauteur dans la plage choisie.", VCO2),
            cmd("Wave", "Sinus, dent de scie, carrée.", VCO2),
            cmd("Range", "Fine, All ou LFO.", VCO2),
            cmd("Curseur Osc 2", "Niveau du VCO 2.", VCO2),
            cmd("FM (VCO 1)", "Modulation du VCO 1 par le VCO 2.", VCO1),
        ),
        steps = listOf(
            "Depuis le patch de base, appuie sur Octave + et maintiens un pad.",
            "Monte le curseur Osc 2, puis baisse Tune (VCO 2) jusqu'à une octave plus grave : c'est un sous-oscillateur.",
            "Mets Range sur LFO et monte doucement FM du VCO 1 : vibrato.",
            "Remets Range sur Fine et monte FM : le son devient métallique.",
        ),
        expected = "Le son s'épaissit avec le sous-oscillateur, puis vibre ou se métallise avec la FM.",
        exerciseTitle = "Basse lourde",
        exercise = listOf(
            "Construis une basse avec le VCO 1 en dent de scie et le VCO 2 une octave en dessous.",
            "Ajoute un soupçon de FM pour la salir.",
        ),
        success = "La basse est plus grave et plus large qu'avec un seul oscillateur.",
        style = "Hardtek : une basse sous-oscillée et légèrement salie remplit l'espace sous le kick.",
        mistakes = listOf(
            "Oublier de monter le curseur Osc 2 : le VCO 2 ne s'entend pas.",
            "Monter FM au maximum en Fine : le son devient vite très bruyant.",
        ),
        quiz = listOf(
            qz("Quelle position de Range transforme le VCO 2 en oscillateur lent ?", listOf("Fine", "All", "LFO"), 2,
                "Sur LFO, le VCO 2 sert de LFO supplémentaire.", VCO2),
            qz("Que suit le VCO 2 par défaut ?", listOf("La hauteur du VCO 1", "Le LFO 1", "Rien"), 0,
                "Par défaut la hauteur du VCO 2 suit celle du VCO 1.", VCO2),
            qz("Que module le potentiomètre FM du VCO 1 par défaut ?",
                listOf("La hauteur du VCO 1 par le VCO 2", "Le filtre", "Le volume"), 0,
                "La source FM par défaut est le VCO 2 (inscription bleue).", VCO1),
            qz("Quelles formes propose le VCO 2 ?", listOf("Sinus, dent de scie, carrée", "Triangle seulement", "Bruit"), 0,
                "L'interrupteur Wave choisit sinus, dent de scie ou carrée.", VCO2),
        ),
    )

    private val m2l3 = ctLesson(
        M2, 3, "Le mélangeur : bruit et entrée externe", listOf(MIXER), 15,
        objective = "Doser le bruit blanc et une source externe dans le mélangeur.",
        explanation = "L'Osc Mixer règle aussi le curseur Noise (bruit blanc vers le filtre) et le curseur Ext (une source audio " +
            "branchée sur la prise Ext de la partie EXT IN de la patchbay). Un peu de bruit avec le triangle donne un souffle de flûte ; " +
            "le bruit seul sert au vent, à la pluie ou aux cymbales. Une source externe peut ainsi passer par le filtre et l'amplificateur.",
        controls = listOf(
            cmd("Noise", "Bruit blanc vers le filtre.", MIXER),
            cmd("Ext", "Niveau de l'audio externe (prise Ext de la patchbay).", MIXER),
            cmd("Osc 2", "Niveau du VCO 2.", MIXER),
        ),
        steps = listOf(
            "Depuis le patch de base, baisse l'onde carrée et monte Noise à mi-course.",
            "Joue un pad et baisse le Cutoff : le bruit devient un souffle.",
            "Mélange un peu de bruit avec le triangle et écoute le souffle.",
            "Si tu as une source ligne, branche-la sur la prise Ext de la patchbay et monte le curseur Ext.",
        ),
        expected = "Le bruit et la source externe passent par le filtre comme les oscillateurs.",
        exerciseTitle = "Effet de vent",
        exercise = listOf(
            "Avec le bruit seul, fais un vent qui monte et descend en bougeant le Cutoff.",
        ),
        success = "Le souffle se ferme et s'ouvre de manière progressive.",
        mistakes = listOf(
            "Brancher un micro directement sur Ext : il faut un préamplificateur pour un niveau ligne.",
            "Oublier que les sorties de la patchbay ne passent pas par les curseurs du mélangeur.",
        ),
        quiz = listOf(
            qz("Où va le bruit réglé par le curseur Noise ?", listOf("Vers le filtre", "Directement à la sortie", "Vers le LFO"), 0,
                "Le curseur Noise envoie du bruit blanc au filtre.", MIXER),
            qz("Que contrôle le curseur Ext ?",
                listOf("Une source audio branchée sur la prise Ext de la patchbay", "Le casque", "L'USB"), 0,
                "Ext règle la source externe branchée sur EXT IN.", MIXER),
            qz("Que donne un peu de bruit mélangé au triangle ?", listOf("Un son de flûte", "Un kick", "Un écho"), 0,
                "Le manuel cite un son de flûte crédible.", MIXER),
            qz("Que contrôle le curseur Osc 2 ?", listOf("Le niveau du VCO 2", "La hauteur du VCO 2", "Le LFO 2"), 0,
                "Il règle le niveau de sortie du second oscillateur.", MIXER),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 3 — Le filtre
    // ---------------------------------------------------------------------
    private const val M3 = "mb2s-m3"

    private val m3l1 = ctLesson(
        M3, 1, "Modes, coupure et résonance", listOf(FILTRE), 20,
        objective = "Choisir le mode du filtre et sculpter le timbre avec Cutoff et Resonance.",
        explanation = "Le filtre Steiner-Parker propose LP (passe-bas, -12 dB/oct), BP (passe-bande), HP (passe-haut) et Notch " +
            "(coupe-bande). LP donne des sons pleins et ronds ; BP et HP plus fins ; Notch modulé ressemble à un phaser. Cutoff fixe " +
            "la fréquence de coupure ; Resonance accentue les harmoniques autour de la coupure et, dans son dernier quart, fait " +
            "osciller le filtre (environ 180 Hz à 8 kHz). Attention aux niveaux : un filtre résonant peut devenir très fort, surtout au casque.",
        controls = listOf(
            cmd("Mode", "LP, BP, HP, Notch.", FILTRE),
            cmd("Cutoff", "Fréquence de coupure.", FILTRE),
            cmd("Resonance", "Pic de résonance, auto-oscillation dans le dernier quart.", FILTRE),
        ),
        steps = listOf(
            "Mets les curseurs de l'Osc Mixer à 0 et Noise à mi-course.",
            "Mets les curseurs des deux enveloppes à 0 et le filtre en LP.",
            "Joue et maintiens une note, puis tourne Cutoff.",
            "Essaie BP, HP et Notch avec la même méthode.",
            "Repasse sur un oscillateur, monte Resonance progressivement en surveillant le volume.",
        ),
        expected = "Tu entends clairement quelles fréquences chaque mode laisse passer, et le sifflement de la résonance.",
        exerciseTitle = "Basse acide au filtre",
        exercise = listOf(
            "Dent de scie, filtre LP, Cutoff bas et Resonance aux trois quarts.",
            "Joue une ligne de basse en ouvrant et fermant le Cutoff à la main.",
        ),
        success = "La basse « parle » quand tu bouges le Cutoff.",
        style = "Acidcore : coupure basse et résonance forte, balayées à la main.",
        mistakes = listOf(
            "Monter Resonance au maximum avec le volume fort : risque pour les oreilles et les enceintes.",
            "Chercher des basses en mode HP : il coupe justement les graves.",
        ),
        quiz = listOf(
            qz("Quel mode donne les sons les plus pleins et ronds ?", listOf("LP", "HP", "BP"), 0,
                "Le passe-bas LP est le plus utilisé pour des sons pleins et gras.", FILTRE),
            qz("Quand le filtre commence-t-il à osciller seul ?",
                listOf("Dès que Resonance dépasse 0", "Dans le dernier quart de Resonance", "Jamais"), 1,
                "Il commence à osciller quand Resonance atteint son dernier quart.", FILTRE),
            qz("Quel mode modulé rappelle une pédale Phaser ?", listOf("Notch", "LP", "HP"), 0,
                "Modulé par un LFO, le mode Notch ressemble à un phaser.", FILTRE),
            qz("Quelle est la pente du filtre en mode LP ?", listOf("-6 dB/oct", "-12 dB/oct", "-24 dB/oct"), 1,
                "-12 dB/octave en LP et HP, -6 dB/octave en BP et Notch.", FILTRE),
        ),
    )

    private val m3l2 = ctLesson(
        M3, 2, "L'enveloppe ADSR du filtre", listOf(ADSR, FILTRE_FM, ADSR_CURSEURS), 20,
        objective = "Faire évoluer le timbre de chaque note avec l'enveloppe ADSR et le potentiomètre FM du filtre.",
        explanation = "L'enveloppe ADSR est pré-câblée sur le filtre. Attack (0,5 ms à 4 s) monte jusqu'au maximum, Decay descend vers " +
            "le niveau de Sustain (tenu tant que le pad est enfoncé), Release ramène à zéro au relâchement. Le potentiomètre FM du " +
            "filtre règle la quantité et le sens : au centre (12 h) pas de modulation, au-dessus enveloppe positive, en dessous " +
            "enveloppe inversée. Si l'effet ne s'entend pas, baisse le Cutoff (enveloppe positive) ou monte-le (négative).",
        controls = listOf(
            cmd("Curseurs A, D, S, R", "Forme de l'enveloppe du filtre.", ADSR_CURSEURS),
            cmd("FM (Filter)", "Quantité et sens de l'enveloppe sur la coupure.", FILTRE_FM),
        ),
        steps = listOf(
            "Dent de scie, filtre LP, Cutoff assez bas.",
            "Mets Attack à 0, Decay au tiers, Sustain à 0, Release court.",
            "Tourne FM du filtre au-dessus du centre et joue : chaque note s'ouvre puis se referme.",
            "Allonge Attack : l'ouverture devient progressive.",
            "Passe FM sous le centre et monte le Cutoff : l'enveloppe ferme le filtre au lieu de l'ouvrir.",
        ),
        expected = "Chaque note a un « wow » ou un « pluck » selon les réglages.",
        exerciseTitle = "Pluck et nappe",
        exercise = listOf(
            "Fais un son court et claquant (Attack 0, Decay court, Sustain 0).",
            "Puis une nappe qui s'ouvre lentement (Attack long, Sustain haut).",
        ),
        success = "Les deux sons viennent du même oscillateur mais sont totalement différents.",
        style = "Acidcore : Decay court et FM positif pour le « claquement » de chaque note.",
        mistakes = listOf(
            "Laisser FM au centre : l'enveloppe n'agit pas.",
            "Garder le Cutoff au maximum avec une enveloppe positive : il n'y a plus de place pour l'ouvrir.",
        ),
        quiz = listOf(
            qz("À quoi est pré-câblée l'enveloppe ADSR ?", listOf("Au filtre", "À l'amplificateur", "Au VCO 2"), 0,
                "L'ADSR est dédiée au filtre (inscription ADSR sous FM).", FILTRE_FM),
            qz("Que se passe-t-il avec FM du filtre au centre ?", listOf("Modulation maximale", "Aucune modulation", "Enveloppe inversée"), 1,
                "À 12 h, aucune modulation d'enveloppe.", FILTRE_FM),
            qz("Quelle phase est tenue tant que le pad est enfoncé ?", listOf("Attack", "Sustain", "Release"), 1,
                "Le Sustain reste à son niveau tant que le pad est maintenu.", ADSR),
            qz("Tu n'entends pas une enveloppe positive. Que faire ?",
                listOf("Baisser le Cutoff", "Monter le Cutoff", "Couper le son"), 0,
                "Le manuel conseille de diminuer la coupure pour entendre une enveloppe positive.", FILTRE_FM),
        ),
    )

    private val m3l3 = ctLesson(
        M3, 3, "Moduler la résonance et jouer le filtre à la pression", listOf(RM, ATT1, PRESSION), 20,
        objective = "Animer la résonance avec le LFO 1 et ouvrir le filtre en appuyant plus fort sur les pads.",
        explanation = "RM (Resonance Modulation) est pré-câblé sur le LFO 1 et bipolaire : au centre, rien ; d'un côté ou de l'autre, " +
            "la résonance monte et descend. Att 1 > Cutoff est pré-câblé sur la pression des pads : plus tu appuies sur un pad déjà " +
            "enfoncé, plus le filtre s'ouvre. Ces deux pré-câblages peuvent être remplacés par d'autres sources via la patchbay.",
        controls = listOf(
            cmd("RM", "Modulation de la résonance (LFO 1 par défaut), bipolaire.", RM),
            cmd("Att 1 > Cutoff", "Quantité de pression envoyée à la coupure.", ATT1),
        ),
        steps = listOf(
            "Bruit seul, filtre LP, Cutoff à 10 h, Resonance au centre.",
            "LFO 1 en dent de scie, Rate à 10 h ; joue et maintiens une note.",
            "Tourne RM vers 3 h : « chirp » descendant ; vers 9 h : « chirp » montant.",
            "Reviens à une onde, Cutoff au minimum, Att 1 > Cutoff au maximum, VCO 2 en dent de scie.",
            "Maintiens un pad et appuie de plus en plus fort : le filtre s'ouvre.",
        ),
        expected = "La résonance pulse avec le LFO et le filtre suit la pression de ton doigt.",
        exerciseTitle = "Lead expressif",
        exercise = listOf(
            "Joue une mélodie en ouvrant le filtre uniquement par la pression des pads.",
        ),
        success = "Les notes s'ouvrent seulement quand tu appuies plus fort.",
        style = "Mentalcore : la pression sur les pads rend les leads vivants et agressifs.",
        mistakes = listOf(
            "Laisser RM au centre et croire qu'il ne marche pas : au centre il n'y a aucune modulation.",
            "Garder le Cutoff haut : la pression n'a plus d'effet audible.",
        ),
        quiz = listOf(
            qz("Quelle source module RM par défaut ?", listOf("LFO 1", "LFO 2", "Pression"), 0,
                "RM est pré-câblé sur le LFO 1.", RM),
            qz("Que fait RM au centre ?", listOf("Rien", "Maximum de modulation", "Coupe le filtre"), 0,
                "RM est bipolaire : au centre aucune modulation.", RM),
            qz("Quelle source contrôle Att 1 > Cutoff par défaut ?", listOf("La pression des pads", "La vélocité", "Le LFO 2"), 0,
                "Il est pré-câblé sur la sensibilité à la pression.", ATT1),
            qz("Pour entendre le filtre s'ouvrir à la pression, le Cutoff doit être…", listOf("Bas", "Au maximum"), 0,
                "L'exemple du manuel règle le Cutoff au minimum et Att > Cutoff au maximum.", PRESSION),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 4 — Amplitude, LFO et saturation
    // ---------------------------------------------------------------------
    private const val M4 = "mb2s-m4"

    private val m4l1 = ctLesson(
        M4, 1, "L'enveloppe AD d'amplitude", listOf(AD, AD_CURSEURS, AD_SWITCH), 20,
        objective = "Contrôler comment le son apparaît et disparaît, et faire boucler l'enveloppe.",
        explanation = "L'enveloppe AD règle le volume : Attack et Decay de 1 ms à 14 s. L'interrupteur Gate/Trig : en Gate, le son " +
            "reste au maximum tant que la note est tenue puis descend au relâchement ; en Trig, il monte puis redescend sans tenir. " +
            "Once/Loop : en Loop, l'enveloppe recommence en boucle (en Gate jusqu'au relâchement, en Trig indéfiniment).",
        controls = listOf(
            cmd("Curseurs Attack / Decay (AD)", "Montée et descente du volume.", AD_CURSEURS),
            cmd("Gate / Trig", "Tenue ou cycle complet à chaque note.", AD_SWITCH),
            cmd("Once / Loop", "Une fois ou en boucle.", AD_SWITCH),
        ),
        steps = listOf(
            "Depuis le patch de base, monte Attack au centre et joue : le son monte lentement.",
            "Monte Decay : le son s'éteint progressivement au relâchement.",
            "Passe sur Trig avec Attack court et Decay moyen : chaque note est un coup.",
            "Passe sur Loop : le volume pulse tant que la note est tenue (Gate) ou en continu (Trig).",
        ),
        expected = "Tu obtiens des sons tenus, des coups percussifs et des pulsations en boucle.",
        exerciseTitle = "Pulsation rythmique",
        exercise = listOf(
            "Avec Trig + Loop et un Decay court, fais une pulsation régulière sur une note tenue.",
            "Ajuste Attack et Decay pour la caler à l'oreille sur un tempo.",
        ),
        success = "La note tenue pulse comme un motif rythmique.",
        style = "Tribe : une boucle AD sur une basse donne une pulsation hypnotique.",
        mistakes = listOf(
            "Monter Attack au maximum et croire que le synthé ne marche plus : la montée peut durer 14 s.",
            "Confondre l'enveloppe AD (volume) et l'ADSR (filtre).",
        ),
        quiz = listOf(
            qz("Que contrôle l'enveloppe AD par défaut ?", listOf("Le volume", "Le filtre", "Le VCO 2"), 0,
                "L'enveloppe AD contrôle l'amplitude.", AD),
            qz("Quelle est la durée maximale d'Attack de l'enveloppe AD ?", listOf("4 s", "14 s", "1 s"), 1,
                "Attack et Decay vont de 1 ms à 14 s.", AD_CURSEURS),
            qz("En Trig + Loop, l'enveloppe…",
                listOf("Boucle indéfiniment", "Ne joue qu'une fois", "Ne démarre pas"), 0,
                "Trig + Loop boucle indéfiniment et se relance à chaque note.", AD_SWITCH),
            qz("En Gate, que se passe-t-il quand la note est tenue ?",
                listOf("Le son reste au maximum", "Le son s'arrête", "L'enveloppe boucle toujours"), 0,
                "En Gate (Once), l'amplificateur reste au maximum jusqu'au relâchement.", AD_SWITCH),
        ),
    )

    private val m4l2 = ctLesson(
        M4, 2, "Les deux LFO", listOf(LFO), 15,
        objective = "Régler la forme et la vitesse des LFO, libres ou synchronisés au tempo.",
        explanation = "Deux LFO identiques : Wave choisit sinus, triangle, dent de scie, carrée, aléatoire par paliers (Sample & Hold) " +
            "ou aléatoire lissée. Rate va d'un cycle toutes les 16 s à 100 Hz ; la LED rouge clignote à la vitesse. L'interrupteur Sync : " +
            "Free (vitesse libre) ou Seq (vitesse calée sur le tempo, neuf valeurs de 8 mesures à la triple croche). Par défaut, " +
            "le LFO 1 module PWM et RM, le LFO 2 anime l'Ultrasaw.",
        controls = listOf(
            cmd("Wave", "Forme du LFO.", LFO),
            cmd("Rate + LED", "Vitesse, 0,0625 Hz à 100 Hz.", LFO),
            cmd("Sync (Free / Seq)", "Vitesse libre ou calée sur le tempo.", LFO),
        ),
        steps = listOf(
            "Onde carrée, monte PWM : le LFO 1 fait bouger la largeur d'impulsion.",
            "Change la forme du LFO 1 avec Wave et écoute la différence.",
            "Passe Sync sur Seq et tourne Rate : la vitesse saute de valeur en valeur, calée sur le tempo.",
            "Dent de scie + Ultrasaw, puis fais varier Rate du LFO 2 : du battement lent au son « supersaw ».",
        ),
        expected = "Les modulations suivent la forme et la vitesse choisies, libres ou en rythme.",
        exerciseTitle = "Modulation en rythme",
        exercise = listOf(
            "Synchronise le LFO 1 (Sync sur Seq) et règle une vitesse à la noire.",
            "Utilise-le sur PWM et vérifie qu'il reste en place avec le séquenceur.",
        ),
        success = "La modulation tombe toujours au même endroit de la mesure.",
        mistakes = listOf(
            "Tourner Rate en mode Seq en s'attendant à un réglage continu : il saute entre neuf valeurs.",
            "Chercher l'effet du LFO 2 sans Ultrasaw : il l'anime par défaut.",
        ),
        quiz = listOf(
            qz("Combien de vitesses possibles en mode Sync ?", listOf("4", "9", "16"), 1,
                "Neuf valeurs, de 8 mesures à la triple croche.", LFO),
            qz("Quelle forme s'appelle aussi Sample & Hold ?", listOf("Aléatoire par paliers", "Triangle", "Aléatoire lissée"), 0,
                "L'aléatoire par palier est aussi appelée Sample & Hold.", LFO),
            qz("Quelle est la vitesse maximale d'un LFO ?", listOf("10 Hz", "100 Hz", "1 kHz"), 1,
                "Rate va jusqu'à 100 Hz.", LFO),
            qz("Que fait Sync sur Free ?", listOf("La vitesse dépend uniquement de Rate", "Le LFO est coupé"), 0,
                "En Free, seul le potentiomètre Rate fixe la vitesse.", LFO),
        ),
    )

    private val m4l3 = ctLesson(
        M4, 3, "Brute Factor, volume et sons tenus", listOf(AMP, ATT2), 15,
        objective = "Saturer le son avec le Brute Factor et créer des sons tenus avec Att 2 > Amp.",
        explanation = "Brute Factor recrée en interne une boucle de rétroaction : au minimum il est coupé ; un peu donne une " +
            "distorsion douce, beaucoup une saturation dure ; au-delà de 75 %, le son devient presque incontrôlable. Il modifie " +
            "fortement le filtre. Master Volume règle les sorties Master et casque. Att 2 > Amp, normalement au minimum, garde " +
            "l'amplificateur ouvert à un certain niveau : pratique pour régler un son les mains libres ou faire un drone.",
        controls = listOf(
            cmd("Brute Factor", "Saturation par rétroaction interne.", AMP),
            cmd("Master Volume", "Niveau des sorties Master et casque.", AMP),
            cmd("Att 2 > Amp", "Niveau minimal de l'amplificateur.", ATT2),
        ),
        steps = listOf(
            "Joue une basse en dent de scie, monte Brute Factor progressivement.",
            "Écoute la distorsion douce puis dure ; ne dépasse 75 % que volume bas.",
            "Remets Brute Factor bas, monte Att 2 > Amp : le son reste audible sans toucher de pad.",
            "Règle le filtre les deux mains libres, puis remets Att 2 > Amp au minimum.",
        ),
        expected = "La basse devient saturée, et le son tenu permet de régler à deux mains.",
        exerciseTitle = "Drone saturé",
        exercise = listOf(
            "Avec Att 2 > Amp ouvert, crée un drone et fais-le évoluer au Cutoff et au Brute Factor.",
            "Coupe-le en ramenant Att 2 > Amp au minimum.",
        ),
        success = "Le drone évolue sans toucher les pads et se coupe proprement.",
        style = "Hardtek / mentalcore : le Brute Factor donne le grain sale des basses de free party.",
        mistakes = listOf(
            "Monter Brute Factor au-delà de 75 % avec le volume fort.",
            "Oublier Att 2 > Amp ouvert : le son ne s'arrête plus au relâchement.",
        ),
        quiz = listOf(
            qz("Brute Factor au minimum, c'est…", listOf("Désactivé", "Saturation maximale"), 0,
                "La position par défaut, au minimum, désactive le Brute Factor.", AMP),
            qz("À partir de quel réglage le Brute Factor devient-il presque incontrôlable ?", listOf("25 %", "50 %", "75 %"), 2,
                "Au-dessus de 75 % de sa plage, les sons de rétroaction deviennent délirants.", AMP),
            qz("À quoi sert Att 2 > Amp au-dessus du minimum ?",
                listOf("Garder l'amplificateur ouvert à un niveau donné", "Accorder", "Changer de mode"), 0,
                "Il maintient un niveau minimal : son tenu, drone.", ATT2),
            qz("Que règle Master Volume ?", listOf("Les sorties Master et casque", "Le niveau du VCO 1", "Le LFO"), 0,
                "Il agit uniquement sur les sorties du panneau arrière.", AMP),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(
            M1, 1, "Prise en main",
            "Branchements, préchauffage, patch de base, pads et octaves.",
            "Brancher, accorder et repartir d'un son neutre connu.",
            "Un synthé sans mémoire : savoir revenir au patch de base est la base du live.",
            listOf(m1l1, m1l2, m1l3),
        ),
        CourseModule(
            M2, 2, "Oscillateurs et mélangeur",
            "VCO 1, VCO 2, FM, bruit et entrée externe.",
            "Construire basses, leads et sons métalliques avec les deux oscillateurs.",
            "Ultrasaw, Metalizer et sous-oscillateur donnent les basses et leads des styles visés.",
            listOf(m2l1, m2l2, m2l3),
        ),
        CourseModule(
            M3, 3, "Le filtre",
            "Modes, coupure, résonance, enveloppe ADSR, RM et pression.",
            "Sculpter et animer le timbre au filtre.",
            "L'acidcore repose sur le filtre résonant balayé.",
            listOf(m3l1, m3l2, m3l3),
        ),
        CourseModule(
            M4, 4, "Amplitude, LFO et saturation",
            "Enveloppe AD, LFO, Brute Factor et sons tenus.",
            "Donner forme, mouvement et grain au son.",
            "Le Brute Factor et les LFO synchronisés servent la hardtek et la mentalcore.",
            listOf(m4l1, m4l2, m4l3),
        ),
    )
}
