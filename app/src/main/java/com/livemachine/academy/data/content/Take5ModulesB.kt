package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** Take 5 — modules 5 à 7 : LFO et modulation, jeu et effets, arpégiateur et séquenceur. */
internal object Take5ModulesB {

    // Références du guide (pages imprimées ; page du PDF = page imprimée + 9)
    private val LFO = tk5("Low Frequency Oscillators", "30–31")
    private val LFO_PARAM = tk5("Global LFO Versus Per-Voice LFO, LFO Parameters", "31–33")
    private val LFO_PLUS = tk5("Additional LFO Parameters", "33–34")
    private val MOD = tk5("Modulation", "34–36")
    private val MOD_EX = tk5("Modulation Examples", "36–37")
    private val BRASS = tk5("Creating Synth Brass", "60–61")
    private val UNISON = tk5("Unison, Using Chord Memory", "38–39")
    private val FX = tk5("Effects", "40–41")
    private val VINTAGE = tk5("Vintage Knob, Overdrive", "42")
    private val GLIDE = tk5("Glide, Glide Modes", "51–52")
    private val WHEELS = tk5("Pitch and Mod Wheels", "53")
    private val ARP = tk5("Arpeggiator", "43–44")
    private val ARP_PARAM = tk5("Arpeggiator Parameters", "46")
    private val CLOCK = tk5("Clock Parameters", "47")
    private val SEQ = tk5("Step Sequencer", "48–49")
    private val HOLD = tk5("Transpose, Hold", "50")
    private val CLOCK_MIDI = tk5("Global Settings, Clock Mode", "13–14")
    private val ARP_MIDI = tk5("MIDI Note Output from the Arpeggiator", "45")
    private val DEPANNAGE = tk5("Troubleshooting", "69–70")

    // ---------------------------------------------------------------------
    // Module 5 — LFO et modulation
    // ---------------------------------------------------------------------
    private const val M5 = "tk5-m5"

    private val m5l1 = ctLesson(
        M5, 1, "Les deux LFO", listOf(LFO, LFO_PARAM, LFO_PLUS), 15,
        objective = "Créer vibrato, wah-wah ou trémolo avec les LFO et comprendre global / par voix.",
        explanation = "Formes : triangle (bipolaire), dent de scie, dent de scie inversée, carré, S&H (aléatoire). Frequency va de 0,022 Hz " +
            "à 500 Hz ; Clk Sync cale le LFO sur l'horloge (arpégiateur, séquenceur, certains délais) ; Amount dose la modulation vers " +
            "la destination choisie avec Dest. LFO 1 est global : la même modulation pour toutes les voix. LFO 2 est par voix : chaque " +
            "note a son propre cycle. Raccourci : maintenir dest du LFO et bouger la commande de destination. Dans le menu Program : " +
            "Reset (redémarrage à chaque note) et Slew (adoucissement).",
        controls = listOf(
            cmd("Freq, Amount", "Vitesse et profondeur.", LFO_PARAM),
            cmd("Shape", "Triangle, dent de scie, inversée, carré, S&H.", LFO_PARAM),
            cmd("Clk Sync, Dest", "Synchronisation et destination.", LFO_PARAM),
            cmd("Program > LFO Reset / Slew", "Redémarrage et adoucissement.", LFO_PLUS),
        ),
        steps = listOf(
            "Sur LFO 1, choisis le triangle, une vitesse moyenne et maintiens dest en tournant Cutoff.",
            "Monte Amount : le filtre fait wah-wah.",
            "Passe sur S&H : la coupure saute au hasard.",
            "Active Clk Sync pour caler le LFO sur le tempo.",
        ),
        expected = "Le filtre bouge en rythme avec le LFO.",
        exerciseTitle = "Filtre aléatoire calé",
        exercise = listOf(
            "LFO en S&H vers Cutoff, Clk Sync actif.",
            "Joue une basse tenue sur huit mesures.",
        ),
        success = "La basse change de couleur à chaque temps, en rythme.",
        style = "Mentalcore : S&H synchronisé sur le filtre pour des basses instables.",
        mistakes = listOf(
            "Confondre LFO 1 et LFO 2 sur un accord : seul LFO 2 donne un cycle différent par note.",
            "Oublier Clk Sync : le LFO dérive par rapport au tempo.",
        ),
        quiz = listOf(
            qz("Quel LFO est par voix ?", listOf("LFO 1", "LFO 2"), 1,
                "LFO 1 est global, LFO 2 est polyphonique.", LFO_PARAM),
            qz("Quelle est la plage de Frequency ?", listOf("0,022 Hz à 500 Hz", "1 à 20 Hz"), 0,
                "De 0,022 Hz à 500 Hz.", LFO_PARAM),
            qz("Quelle forme donne des valeurs aléatoires ?", listOf("Triangle", "S&H"), 1,
                "Sample & Hold = aléatoire.", LFO),
            qz("Raccourci pour choisir une destination ?", listOf("Maintenir dest et bouger la commande", "Maintenir write"), 0,
                "dest + mouvement de la commande voulue.", LFO_PLUS),
        ),
    )

    private val m5l2 = ctLesson(
        M5, 2, "La matrice de modulation", listOf(MOD), 15,
        objective = "Relier n'importe quelle source à n'importe quelle destination.",
        explanation = "Le principe : choisir une source, une destination, puis une quantité (positive ou négative). Il y a 19 sources " +
            "et 54 destinations. Dans l'écran, select parcourt les emplacements de modulation et les champs, value règle source, " +
            "quantité et destination. Raccourci : maintenir src assign et bouger la commande source (molette Mod par exemple), puis " +
            "maintenir dest assign et bouger la destination. Toutes les commandes de façade ne peuvent pas servir. Les sources " +
            "mono (LFO 1, molettes) agissent pareil sur toutes les voix ; les sources poly (vélocité, LFO 2, enveloppes) par voix.",
        controls = listOf(
            cmd("source", "Ouvre la matrice dans l'écran.", MOD),
            cmd("src assign / dest assign", "Raccourcis d'affectation.", MOD),
            cmd("select / value", "Emplacement, source, quantité, destination.", MOD),
        ),
        steps = listOf(
            "Appuie sur source, choisis l'emplacement Mod 1 avec select.",
            "Avec value, choisis la source mod wheel.",
            "Choisis la destination cutoff et règle la quantité.",
            "Tiens une note et bouge la molette Mod.",
        ),
        expected = "La molette Mod ouvre (ou ferme, si négatif) le filtre.",
        exerciseTitle = "Ta molette de live",
        exercise = listOf(
            "Affecte la molette Mod à Cutoff avec src assign / dest assign.",
            "Ajoute un deuxième emplacement : molette Mod vers Overdrive.",
        ),
        success = "Un seul geste ouvre et salit le son.",
        style = "Hardtek : une molette qui ouvre et sature pour les montées.",
        mistakes = listOf(
            "Laisser la quantité à 0 : la modulation n'a aucun effet.",
            "Essayer une commande de façade non affectable : toutes ne le sont pas.",
        ),
        quiz = listOf(
            qz("Combien de sources et de destinations ?", listOf("19 sources, 54 destinations", "8 et 8"), 0,
                "19 sources et 54 destinations.", MOD),
            qz("La quantité peut-elle être négative ?", listOf("Oui", "Non"), 0,
                "Elle peut généralement être positive ou négative.", MOD),
            qz("La vélocité est une source…", listOf("Mono", "Poly"), 1,
                "La vélocité agit voix par voix.", MOD),
            qz("Que fait src assign ?", listOf("Choisit la source en bougeant la commande", "Sauvegarde"), 0,
                "On maintient src assign et on bouge la source.", MOD),
        ),
    )

    private val m5l3 = ctLesson(
        M5, 3, "Recettes : vibrato et « pitch blip »", listOf(MOD_EX, BRASS), 20,
        objective = "Reproduire deux modulations classiques du guide.",
        explanation = "Vibrato à la molette : LFO 1 en triangle, vitesse moyenne, destination osc all fine, Amount à 0 ; dans la " +
            "matrice, source mod wheel, quantité 30, destination lfo 1 amt. La molette dose alors le vibrato (ou la pression, si on " +
            "la choisit comme source). Cuivres synthé : Basic Program, Osc 2 au maximum et légèrement désaccordé, Cutoff vers 9 h, " +
            "Resonance vers 10 h, Amount de l'enveloppe 1 vers 15 h, Attack et Decay 10 h, Sustain 11 h, Release 10 h, Velocity " +
            "active. Pour le « pitch blip » : emplacement Mod 3, source env 1, quantité +5, destination osc2 fine.",
        controls = listOf(
            cmd("LFO 1 → osc all fine", "Vibrato sur les deux oscillateurs.", MOD_EX),
            cmd("Matrice : mod wheel → lfo 1 amt", "La molette dose le vibrato.", MOD_EX),
            cmd("Matrice : env 1 → osc2 fine", "Petit saut de hauteur à l'attaque.", BRASS),
            cmd("Vintage", "Variations entre les voix.", BRASS),
        ),
        steps = listOf(
            "Règle LFO 1 : triangle, vitesse moyenne, destination osc all fine, Amount à 0.",
            "Dans la matrice, mod wheel → lfo 1 amt, quantité 30 ; tiens une note et monte la molette.",
            "Construis les cuivres du guide à partir du Basic Program.",
            "Ajoute Mod 3 : env 1 → osc2 fine, quantité +5, et joue dans les aigus.",
        ),
        expected = "Un vibrato dosé à la molette et des cuivres avec un petit saut à l'attaque.",
        exerciseTitle = "Stab cuivré",
        exercise = listOf(
            "Raccourcis la release des cuivres pour en faire un stab.",
            "Monte un peu Vintage pour un son plus vivant.",
        ),
        success = "Le stab claque avec un léger saut de hauteur.",
        mistakes = listOf(
            "Laisser l'Amount du LFO 1 au-dessus de 0 : le vibrato est permanent.",
            "Mettre la quantité du pitch blip trop haute : le saut devient caricatural.",
        ),
        quiz = listOf(
            qz("Quelle destination pour le vibrato ?", listOf("osc all fine", "cutoff"), 0,
                "LFO 1 vers osc all fine.", MOD_EX),
            qz("Que pilote la molette dans la recette ?", listOf("lfo 1 amt", "La reverb"), 0,
                "mod wheel → lfo 1 amt, quantité 30.", MOD_EX),
            qz("Quelle quantité pour le pitch blip ?", listOf("+5", "+100"), 0,
                "env 1 → osc2 fine, quantité +5.", BRASS),
            qz("Quelle source peut remplacer la molette ?", listOf("La pression (aftertouch)", "Le tempo"), 0,
                "Le guide propose la pression comme alternative.", MOD_EX),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 6 — Jeu et effets
    // ---------------------------------------------------------------------
    private const val M6 = "tk5-m6"

    private val m6l1 = ctLesson(
        M6, 1, "Unisson et mémoire d'accord", listOf(UNISON), 15,
        objective = "Empiler les voix sur une note et jouer des accords d'un seul doigt.",
        explanation = "En unisson, le Take 5 devient monophonique : une note, jouée par jusqu'à 5 voix. On maintient unison et on choisit " +
            "le nombre de voix avec value. Unison Detune (menu Program, 0 à 7) les désaccorde. Mémoire d'accord : tenir un accord " +
            "(5 notes maximum) et appuyer sur unison ; une seule touche rejoue alors l'accord transposé. Il est sauvegardé avec le " +
            "programme. Pour l'effacer : unisson coupé, tenir une seule note, appuyer sur unison, sauvegarder.",
        controls = listOf(
            cmd("unison + value", "Nombre de voix empilées.", UNISON),
            cmd("Program > Unison Detune", "Désaccord de 0 à 7.", UNISON),
            cmd("Accord tenu + unison", "Mémoire d'accord.", UNISON),
        ),
        steps = listOf(
            "Maintiens unison et choisis 5 voix avec value, puis relâche.",
            "Monte Unison Detune dans le menu Program.",
            "Coupe l'unisson, tiens un accord mineur et appuie sur unison.",
            "Joue une note par temps : l'accord suit.",
        ),
        expected = "Un accord complet sort de chaque touche.",
        exerciseTitle = "Stabs d'un doigt",
        exercise = listOf(
            "Mémorise un accord de trois notes et joue une ligne de stabs d'une main.",
            "Sauvegarde le programme pour garder l'accord.",
        ),
        success = "Les stabs suivent ta ligne, accord compris.",
        style = "Tribe : stabs d'accord en mémoire joués d'une main.",
        mistakes = listOf(
            "Oublier de sauvegarder : la mémoire d'accord n'est gardée qu'avec le programme.",
            "Tenir plus de 5 notes : la mémoire en garde 5 au maximum.",
        ),
        quiz = listOf(
            qz("Combien de voix au maximum en unisson ?", listOf("2", "5", "8"), 1,
                "Jusqu'à cinq voix sur une note.", UNISON),
            qz("Plage d'Unison Detune ?", listOf("0 à 7", "0 à 127"), 0,
                "0 = minimum, 7 = maximum.", UNISON),
            qz("Comment mémoriser un accord ?", listOf("Tenir l'accord et appuyer sur unison", "Maintenir write"), 0,
                "L'accord tenu est mémorisé à l'appui sur unison.", UNISON),
            qz("Comment effacer la mémoire d'accord ?", listOf("Unisson coupé, une note tenue, unison, sauvegarder", "Éteindre"), 0,
                "C'est la procédure du guide.", UNISON),
        ),
    )

    private val m6l2 = ctLesson(
        M6, 2, "Effets et reverb", listOf(FX), 15,
        objective = "Ajouter un effet et la reverb, mémorisés avec le programme.",
        explanation = "Deux effets par programme. Effet 1 au choix : délai stéréo, BBD, deux délais à bande, chorus, flanger, phaser, " +
            "filtre passe-haut, distorsion, modulation en anneau, cabine rotative, Lo-Fi. Effet 2 : reverb. Pour l'effet 1 : bouton " +
            "on/off, type pour choisir, depth/mix pour doser, time et feedback/misc pour régler (leur rôle change selon l'effet). Reverb : " +
            "on/off, mix, decay (longueur), pre-delay, tone et damping (plus de damping = reverb plus sombre).",
        controls = listOf(
            cmd("effect on/off, type", "Active et choisit l'effet 1.", FX),
            cmd("time, depth/mix, feedback/misc", "Réglages de l'effet 1.", FX),
            cmd("reverb on/off, mix, decay, pre-delay, tone, damping", "Reverb.", FX),
        ),
        steps = listOf(
            "Allume l'effet, tourne type jusqu'à « bbd ».",
            "Monte depth/mix, puis règle time et feedback.",
            "Allume la reverb, monte mix et decay.",
            "Monte damping pour assombrir la reverb, puis sauvegarde.",
        ),
        expected = "Le son a un écho et une reverb dosés, sauvegardés avec le programme.",
        exerciseTitle = "Écho de fin de phrase",
        exercise = listOf(
            "Sur un lead, règle un délai avec un feedback assez long.",
            "Monte depth/mix seulement sur la dernière note de la phrase.",
        ),
        success = "L'écho prolonge la fin de phrase sans brouiller le reste.",
        style = "Mentalcore : délai et reverb sur les passages planants.",
        mistakes = listOf(
            "Oublier depth/mix à zéro : l'effet est actif mais inaudible.",
            "Croire que time règle toujours un temps : sur le chorus, c'est la vitesse.",
        ),
        quiz = listOf(
            qz("Combien d'effets par programme ?", listOf("Un", "Deux (effet 1 + reverb)"), 1,
                "Un effet au choix et une reverb.", FX),
            qz("Quel bouton dose l'effet 1 ?", listOf("depth/mix", "type"), 0,
                "depth/mix mélange le signal traité.", FX),
            qz("Plus de damping sur la reverb…", listOf("Reverb plus sombre", "Reverb plus brillante"), 0,
                "Le damping absorbe les aigus.", FX),
            qz("Les effets sont-ils sauvegardés avec le programme ?", listOf("Oui", "Non"), 0,
                "Les effets font partie du programme.", FX),
        ),
    )

    private val m6l3 = ctLesson(
        M6, 3, "Vintage, Overdrive, Glide et molettes", listOf(VINTAGE, GLIDE, WHEELS), 15,
        objective = "Donner du caractère au son et l'expressivité du jeu.",
        explanation = "Vintage ajoute des variations d'une voix à l'autre, comme les synthés anciens. Overdrive, séparé des effets, ajoute " +
            "harmoniques et agressivité. Glide fait glisser la hauteur d'une note à l'autre : bouton glide allumé et rate au-dessus de 0. " +
            "Le mode (menu Program) par défaut est Fixed Rate ; Fixed Rate A et Fixed Time ne sont disponibles qu'en unisson. Molette " +
            "Pitch : jusqu'à 12 demi-tons vers le haut et 24 vers le bas (réglages pitch bend up / down du menu Program).",
        controls = listOf(
            cmd("Vintage", "Variations entre les voix.", VINTAGE),
            cmd("Overdrive", "Saturation.", VINTAGE),
            cmd("glide, rate", "Glissement entre les notes.", GLIDE),
            cmd("Molettes Pitch et Mod", "Jeu expressif.", WHEELS),
        ),
        steps = listOf(
            "Sur une nappe, monte Vintage et écoute les voix devenir moins identiques.",
            "Monte Overdrive sur un lead.",
            "Active glide, monte rate et joue des notes éloignées.",
            "Règle pitch bend up à 2 demi-tons et fais un bend.",
        ),
        expected = "Le son est plus vivant, sature à la demande et glisse entre les notes.",
        exerciseTitle = "Lead qui glisse",
        exercise = listOf(
            "En unisson, active glide et choisis un mode à glissement legato (Fixed Rate A).",
            "Joue une phrase en liant certaines notes seulement.",
        ),
        success = "Seules les notes liées glissent.",
        style = "Acidcore : glissements sur une ligne lead ou basse.",
        mistakes = listOf(
            "Allumer glide avec rate à 0 : aucun effet.",
            "Chercher Fixed Rate A hors unisson : il n'est disponible qu'en unisson.",
        ),
        quiz = listOf(
            qz("Que fait Vintage ?", listOf("Variations d'une voix à l'autre", "Ajoute une reverb"), 0,
                "Il introduit des variations entre les voix.", VINTAGE),
            qz("Glide allumé, rate à 0…", listOf("Pas de glissement", "Glissement maximal"), 0,
                "Il faut aussi régler la vitesse.", GLIDE),
            qz("Mode de glide par défaut ?", listOf("Fixed Rate", "Fixed Time A"), 0,
                "Fixed Rate est le mode par défaut.", GLIDE),
            qz("Bend maximal vers le bas ?", listOf("12 demi-tons", "24 demi-tons"), 1,
                "12 vers le haut, 24 vers le bas.", WHEELS),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 7 — Arpégiateur, séquenceur et synchronisation
    // ---------------------------------------------------------------------
    private const val M7 = "tk5-m7"

    private val m7l1 = ctLesson(
        M7, 1, "Arpégiateur et horloge", listOf(ARP, ARP_PARAM, CLOCK, HOLD), 15,
        objective = "Faire tourner un arpège calé au tempo et le maintenir avec Hold.",
        explanation = "Allumer l'arpégiateur et tenir des notes ; hold les maintient. Modes : Up, Down, Up + Down, Random, Assign (ordre " +
            "de frappe). Octave 1, 2 ou 3 ; Repeat fait rejouer chaque note de une à trois fois de plus. BPM : 30 à 250 ; Clock Divide " +
            "choisit la valeur de note (de 1/2 à 1/64, triolets compris) ; tap tempo : 4 appuis. Arp Relatch (menu Program) fait " +
            "redémarrer l'arpège avec les nouvelles notes. L'arpégiateur marche mieux sur des sons à attaque rapide.",
        controls = listOf(
            cmd("Arpeggiator on/off, mode, octave, repeat", "Réglages de l'arpège.", ARP_PARAM),
            cmd("hold", "Maintient les notes.", HOLD),
            cmd("bpm, clk divide, tap tempo", "Horloge.", CLOCK),
        ),
        steps = listOf(
            "Allume l'arpégiateur, tiens un accord.",
            "Appuie sur hold et relâche : l'arpège continue.",
            "Choisis Up + Down, Octave 2.",
            "Tape 4 fois sur tap tempo, puis règle clk divide sur 1/16.",
        ),
        expected = "Un arpège en doubles croches tourne au tempo choisi.",
        exerciseTitle = "Arpège tribecore",
        exercise = listOf(
            "Tempo vers 180 BPM, mode Random, Octave 3.",
            "Ouvre et ferme Cutoff pendant que l'arpège tourne.",
        ),
        success = "L'arpège reste calé pendant que tu joues le filtre.",
        style = "Tribecore : arpèges rapides et aléatoires joués au filtre.",
        mistakes = listOf(
            "Utiliser un son à attaque lente : l'arpège devient flou.",
            "Oublier hold : l'arpège s'arrête dès qu'on relâche.",
        ),
        quiz = listOf(
            qz("Plage du BPM ?", listOf("30 à 250", "60 à 180"), 0,
                "BPM de 30 à 250.", CLOCK),
            qz("Combien d'appuis pour tap tempo ?", listOf("2", "4"), 1,
                "Quatre appuis fixent le tempo.", CLOCK),
            qz("Mode Assign…", listOf("Joue dans l'ordre de frappe", "Joue au hasard"), 0,
                "Ordre dans lequel les touches ont été pressées.", ARP_PARAM),
            qz("Repeat sur 3…", listOf("Chaque note joue quatre fois", "Trois octaves"), 0,
                "Réglé sur 3, chaque note joue quatre fois.", ARP_PARAM),
        ),
    )

    private val m7l2 = ctLesson(
        M7, 2, "Le séquenceur pas à pas", listOf(SEQ), 20,
        objective = "Enregistrer une séquence de notes et d'accords et la transposer en jouant.",
        explanation = "Jusqu'à 64 pas, avec un accord de 5 notes maximum par pas ; il enregistre notes et vélocité, pas le temps. record, " +
            "puis jouer les notes. Silence : program settings (un appui par pas). Liaison : jouer une note puis write autant de fois " +
            "que de pas à prolonger. play pour écouter, bpm ou tap tempo pour la vitesse. La séquence est sauvegardée avec le " +
            "programme. Transposition : pendant la lecture, maintenir record et jouer une touche (do central = référence). Laisser " +
            "des voix libres pour jouer par-dessus.",
        controls = listOf(
            cmd("record", "Enregistrement pas à pas.", SEQ),
            cmd("program settings / write", "Silence / liaison pendant l'enregistrement.", SEQ),
            cmd("play", "Lecture.", SEQ),
            cmd("record + touche", "Transposition en lecture.", SEQ),
        ),
        steps = listOf(
            "Appuie sur record et joue 8 notes d'une ligne de basse.",
            "Ajoute un silence avec program settings et une liaison avec write.",
            "Appuie sur play et ajuste bpm.",
            "Pendant la lecture, maintiens record et joue une touche au-dessus du do central.",
            "Sauvegarde le programme pour garder la séquence.",
        ),
        expected = "Ta séquence tourne et se transpose depuis le clavier.",
        exerciseTitle = "Basse transposée",
        exercise = listOf(
            "Enregistre une basse de 16 pas avec silences et liaisons.",
            "Transpose-la sur quatre notes différentes pendant 16 mesures.",
        ),
        success = "La progression se joue sans arrêter la séquence.",
        style = "Hardtek : une ligne de basse transposée pour faire avancer le morceau.",
        mistakes = listOf(
            "Croire que le séquenceur enregistre le rythme joué : il enregistre seulement notes et vélocité.",
            "Remplir chaque pas d'accords de 5 notes : plus de voix pour jouer par-dessus.",
        ),
        quiz = listOf(
            qz("Longueur maximale ?", listOf("16 pas", "64 pas"), 1,
                "Jusqu'à 64 pas.", SEQ),
            qz("Comment ajouter un silence ?", listOf("program settings", "hold"), 0,
                "Un appui sur program settings par pas de silence.", SEQ),
            qz("Comment transposer en lecture ?", listOf("Maintenir record et jouer une touche", "Tourner Octave"), 0,
                "Do central = référence.", SEQ),
            qz("Où est sauvegardée la séquence ?", listOf("Avec le programme", "Dans le menu Global"), 0,
                "La sauvegarde du programme garde la séquence.", SEQ),
        ),
    )

    private val m7l3 = ctLesson(
        M7, 3, "Synchronisation MIDI et dépannage", listOf(CLOCK_MIDI, ARP_MIDI, DEPANNAGE), 15,
        objective = "Caler le Take 5 sur ton set et résoudre les problèmes courants.",
        explanation = "Clock Mode (menu Global) : Off, Out (envoie), Out no S/S (sans Start/Stop), In (reçoit), In Thru (reçoit et " +
            "renvoie), In no S/S. Clock Cable In / Out choisit MIDI ou USB. En In ou In Thru sans horloge reçue, arpégiateur et " +
            "séquenceur ne tournent pas. MIDI Arp Notes envoie les notes de l'arpégiateur en MIDI. Dépannage : pas de son → Basic " +
            "Program, volume, câbles L/R, Local Control sur On ; séquenceur arrêté → Clock Mode ; son étrange → Scale en tempérament " +
            "égal, molette Mod ; faux → Master Coarse/Fine à 0 ou recalibrer ; comportement erratique → boucle MIDI.",
        controls = listOf(
            cmd("Global > Clock Mode", "Envoi ou réception de l'horloge MIDI.", CLOCK_MIDI),
            cmd("Global > Clock Cable In / Out", "Port MIDI ou USB.", CLOCK_MIDI),
            cmd("Global > MIDI Arp Notes", "Notes de l'arpégiateur en MIDI.", ARP_MIDI),
            cmd("Global > Local Control", "Doit être sur On pour jouer.", DEPANNAGE),
        ),
        steps = listOf(
            "Relie la sortie MIDI de ta machine maîtresse à MIDI In du Take 5.",
            "Dans Global, règle Clock Mode sur In et Clock Cable In sur MIDI.",
            "Lance la maîtresse : arpégiateur et séquenceur suivent son tempo.",
            "Coupe la maîtresse : vérifie que le Take 5 s'arrête, puis repasse Clock Mode sur Out pour jouer seul.",
        ),
        expected = "Le Take 5 suit le tempo de ta machine principale.",
        exerciseTitle = "Check-list de dépannage",
        exercise = listOf(
            "Mets volontairement Clock Mode sur In sans horloge, constate l'arrêt du séquenceur.",
            "Retrouve la cause avec la liste de dépannage.",
        ),
        success = "Tu corriges le problème en moins d'une minute.",
        mistakes = listOf(
            "Laisser Clock Mode sur In en jouant seul : arpégiateur et séquenceur ne démarrent pas.",
            "Créer une boucle MIDI Thru : le guide en fait la cause n° 1 des comportements erratiques.",
        ),
        quiz = listOf(
            qz("Clock Mode sur In sans horloge reçue…", listOf("Arpégiateur et séquenceur ne tournent pas", "Tempo par défaut"), 0,
                "Ils ont besoin de l'horloge reçue.", CLOCK_MIDI),
            qz("Plus de son : premier réflexe du guide ?", listOf("Rappeler le Basic Program", "Recalibrer"), 0,
                "Global > Basic Program > Write.", DEPANNAGE),
            qz("Take 5 erratique : cause habituelle ?", listOf("Boucle MIDI", "Résonance trop haute"), 0,
                "Presque toujours une boucle MIDI.", DEPANNAGE),
            qz("Que fait MIDI Arp Notes sur On ?", listOf("Envoie les notes de l'arpège en MIDI", "Coupe l'arpège"), 0,
                "Pour piloter d'autres machines.", ARP_MIDI),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(
            M5, 5, "LFO et modulation",
            "LFO global et par voix, matrice de modulation, recettes du guide.",
            "Faire bouger le son tout seul ou à la molette.",
            "Les modulations synchronisées font vivre les longues boucles.",
            listOf(m5l1, m5l2, m5l3),
        ),
        CourseModule(
            M6, 6, "Jeu et effets",
            "Unisson, mémoire d'accord, effets, Vintage, Overdrive, Glide, molettes.",
            "Jouer le Take 5 de façon expressive.",
            "Stabs d'un doigt et leads saturés pour la scène.",
            listOf(m6l1, m6l2, m6l3),
        ),
        CourseModule(
            M7, 7, "Arpégiateur, séquenceur et synchro",
            "Arpégiateur, horloge, séquenceur 64 pas, horloge MIDI, dépannage.",
            "Faire tourner le Take 5 calé sur ton set.",
            "Arpèges et basses séquencées tiennent un morceau entier.",
            listOf(m7l1, m7l2, m7l3),
        ),
    )
}
