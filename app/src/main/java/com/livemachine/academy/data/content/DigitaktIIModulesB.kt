package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** Digitakt II — modules 6 à 9 : variations, son, effets et sampling, live et sauvegarde. */
internal object DigitaktIIModulesB {

    private val PLOCKS = dt("10.8.1 Parameter locks", "47")
    private val PRESET_LOCKS = dt("10.8.2 Preset locks", "48")
    private val CONDITIONS = dt("10.7.3 Trig conditions and conditional locks", "48–49")
    private val TRIG_PAGE1 = dt("11.2 Trig page 1", "53–54")
    private val TRIG_PAGE2 = dt("11.3 Trig page 2", "54–55")
    private val MICRO = dt("10.4 Micro timing / 10.5 Retrigs", "45")
    private val FILL = dt("10.8.4 Fill mode", "49")
    private val MACHINES = dt("Appendix A.1–A.2.1 Machines, Oneshot", "93–94")
    private val PLAYBACK = dt("13.4 Sample playback", "70")
    private val SRC = dt("11.4 SRC page", "55")
    private val FLTR = dt("11.5–11.6 FLTR pages 1 and 2", "55–56")
    private val MULTIMODE = dt("Appendix A.3.1 Multi-mode filter", "104–105")
    private val AMP = dt("11.7 Amp page", "56–57")
    private val FX = dt("11.8 FX page", "57–58")
    private val LFO = dt("11.9 MOD page 1", "58–59")
    private val SEND_FX = dt("12.1–12.2 Send FX, Delay", "61–62")
    private val REVERB_CHORUS = dt("12.3–12.4 Reverb, Chorus", "62–63")
    private val COMP = dt("12.5 Compressor", "63–64")
    private val MIXER = dt("12.6–12.8 Internal mixer, FX mixer", "65–66")
    private val SAMPLING_MENU = dt("13.1 Sampling menu", "68–69")
    private val SAMPLING = dt("13.2 Sampling audio", "69")
    private val QUICK_SAMPLING = dt("7.5 Sampling from the external inputs", "23–24")
    private val CHAINS = dt("10.1.3 Chains", "40–41")
    private val SONG = dt("10.9 Song mode", "50–51")
    private val CONTROL_ALL = dt("6.2.2 Control all", "19")
    private val TEMP_SAVE = dt("10.8.6 Temporary save and reload", "49–50")
    private val PERFORM = dt("10.10 Perform kit mode", "52")
    private val PROJECT = dt("14.1 Project", "75")
    private val PATTERN_MENU = dt("14.3 Pattern menu", "76–77")
    private val BACKUP = dt("6.9 Backing up the Digitakt II", "20–21")
    private val FACADE = dt("3.1 Front panel", "12–13")
    private val DONNEES = dt("5.2.1 Project", "16")

    // ---------------------------------------------------------------------
    // Module 6 — Variations : locks, conditions, micro timing
    // ---------------------------------------------------------------------
    private const val M6 = "dt2-m6"

    private val m6l1 = ctLesson(
        M6, 1, "Parameter locks : un réglage par pas", listOf(PLOCKS, PRESET_LOCKS), 20,
        objective = "Donner à certains pas un son différent (hauteur, filtre, sample…) sans changer le reste de la piste.",
        explanation = "En GRID RECORDING, maintiens le [TRIG] d'un trig déjà posé et tourne un encodeur : ce pas garde sa propre " +
            "valeur, affichée en inversé ; le [TRIG] clignote (rouge pour une note trig, jaune pour un lock trig). Maintenir un trig " +
            "et appuyer sur l'encodeur retire ce lock. Jusqu'à 80 paramètres différents peuvent être verrouillés dans un pattern. " +
            "Avec des presets ajoutés au pool du projet, maintenir une note trig et tourner LEVEL/DATA change même le preset du pas (preset lock).",
        controls = listOf(
            cmd("[TRIG] maintenu + encodeur", "Verrouille la valeur pour ce pas.", PLOCKS),
            cmd("[TRIG] maintenu + appui encodeur", "Retire le lock de ce paramètre.", PLOCKS),
            cmd("[TRIG] maintenu + LEVEL/DATA", "Preset lock (presets du pool).", PRESET_LOCKS),
        ),
        steps = listOf(
            "En GRID RECORDING, choisis la piste du charley qui joue sur chaque contretemps.",
            "Maintiens [TRIG 4] et baisse le paramètre de volume ou le filtre : ce charley seul change.",
            "Maintiens [TRIG 12] et monte la hauteur (NOTE ou TUNE).",
            "Lance la lecture et écoute la variation.",
            "Retire un des locks en maintenant le trig et en appuyant sur l'encodeur.",
        ),
        expected = "Les pas verrouillés sonnent différemment, les autres restent identiques.",
        exerciseTitle = "Charley vivant",
        exercise = listOf(
            "Sur un charley de 16 pas, verrouille une valeur de filtre différente sur quatre pas.",
            "Ajoute un lock trig (jaune) sur un pas vide pour changer un réglage sans jouer de note.",
        ),
        success = "Le charley respire et les pas verrouillés se reconnaissent à l'écoute.",
        style = "Mentalcore : des locks de hauteur et de filtre sur chaque pas rendent les lignes plus agressives.",
        mistakes = listOf(
            "Tourner l'encodeur sans maintenir le trig : c'est toute la piste qui change.",
            "Vouloir un preset lock avec un preset du +Drive : seuls les presets du pool sont possibles.",
        ),
        quiz = listOf(
            qz("Comment créer un parameter lock en GRID RECORDING ?",
                listOf("Maintenir le [TRIG] du pas et tourner un encodeur", "Appuyer sur [FUNC] + [YES]", "Tourner LEVEL/DATA"), 0,
                "On maintient le trig puis on tourne l'encodeur du paramètre à verrouiller.", PLOCKS),
            qz("Combien de paramètres différents peut-on verrouiller dans un pattern ?", listOf("16", "80", "128"), 1,
                "Jusqu'à 80 paramètres ; un paramètre compte une fois quel que soit le nombre de pas.", PLOCKS),
            qz("Comment retirer un seul lock ?",
                listOf("Maintenir le trig et appuyer sur l'encodeur du paramètre", "Supprimer le pattern", "[FUNC] + [PLAY]"), 0,
                "Maintenir le trig et appuyer sur l'encodeur verrouillé supprime ce lock.", PLOCKS),
            qz("D'où viennent les presets utilisables en preset lock ?", listOf("Du pool du projet", "Du +Drive directement"), 0,
                "Seuls les presets ajoutés au pool peuvent être utilisés en preset lock.", PRESET_LOCKS),
        ),
    )

    private val m6l2 = ctLesson(
        M6, 2, "Probabilité et trig conditions", listOf(CONDITIONS, TRIG_PAGE1), 20,
        objective = "Faire jouer certains trigs seulement de temps en temps, selon une règle ou au hasard.",
        explanation = "Sur la page [TRIG PARAMETERS], PROB fixe la probabilité qu'un trig joue (100 % par défaut) ; verrouillée sur " +
            "un pas, elle ne concerne que lui. COND (encodeur H) applique une condition à un trig : A:B (joue au passage A sur un " +
            "cycle de B, par exemple 1:2 = un tour sur deux), 1ST (premier passage), LST (dernier passage avant un changement), " +
            "PRE (si la condition précédente de la piste était vraie), NEI (selon la piste voisine). Chaque condition existe aussi en " +
            "version inversée, marquée d'un trait sur l'écran.",
        controls = listOf(
            cmd("[TRIG PARAMETERS]", "Page TRIG : NOTE, VEL, LEN, PROB, FILL, COND.", TRIG_PAGE1),
            cmd("PROB", "Probabilité de jeu du trig.", TRIG_PAGE1),
            cmd("COND (encodeur H)", "Trig condition du pas maintenu.", CONDITIONS),
        ),
        steps = listOf(
            "En GRID RECORDING, pose un trig de caisse claire sur le pas 16.",
            "Maintiens [TRIG 16], ouvre [TRIG PARAMETERS] et règle COND sur 1:4 avec l'encodeur H.",
            "Lance la lecture : la caisse claire ne joue qu'un tour sur quatre.",
            "Sur un charley, verrouille PROB à 50 % sur quelques pas.",
            "Écoute plusieurs tours : ces charleys apparaissent au hasard.",
        ),
        expected = "Le pattern varie d'un tour à l'autre sans changer de pattern.",
        exerciseTitle = "Pattern qui ne se répète pas",
        exercise = listOf(
            "Ajoute un roulement en fin de pattern avec COND 4:4.",
            "Mets deux percussions en probabilité 30 %.",
            "Écoute huit tours et note quand chaque élément apparaît.",
        ),
        success = "Le roulement tombe tous les quatre tours et les percussions varient.",
        style = "Tribecore : les conditions A:B évitent la monotonie sur les longues boucles.",
        mistakes = listOf(
            "Régler PROB sans maintenir le trig : toute la piste devient aléatoire.",
            "Croire que la condition FILL marche seule : il faut activer le mode FILL.",
        ),
        quiz = listOf(
            qz("Avec la condition 1:2, quand le trig joue-t-il ?",
                listOf("À chaque tour", "Au 1er, 3e, 5e tour…", "Une seule fois"), 1,
                "1:2 joue au premier passage puis tous les deux passages.", CONDITIONS),
            qz("Quel encodeur règle COND sur la page TRIG ?", listOf("A", "E", "H"), 2,
                "La trig condition se choisit avec l'encodeur H.", CONDITIONS),
            qz("Que signifie LST ?",
                listOf("Joue au dernier passage avant de changer de pattern", "Joue au dernier pas", "Joue en dernier dans la chaîne de son"), 0,
                "LST : le trig joue la dernière fois que le pattern passe avant un changement.", CONDITIONS),
            qz("Quelle est la valeur par défaut de PROB ?", listOf("50 %", "100 %", "0 %"), 1,
                "Par défaut tous les trigs jouent : 100 %.", TRIG_PAGE1),
        ),
    )

    private val m6l3 = ctLesson(
        M6, 3, "Micro timing, retrigs et mode FILL", listOf(MICRO, TRIG_PAGE2, FILL), 20,
        objective = "Décaler légèrement un trig, créer des roulements par retrig et déclencher une variation FILL en direct.",
        explanation = "En GRID RECORDING, maintenir un [TRIG] et appuyer sur [LEFT]/[RIGHT] ouvre le micro timing : le trig est avancé " +
            "ou retardé. Les retrigs se règlent en appuyant deux fois sur [TRIG PARAMETERS] : RTRG active, RATE choisit la vitesse " +
            "(1/16 = un coup par pas, 1/32 = deux, 1/12 pour des triolets), VFAD fait monter ou descendre la vélocité. Le mode FILL : " +
            "[PAGE] maintenu pendant la lecture (hors GRID RECORDING) ou [YES] + [PAGE] pour un tour ; les trigs réglés FILL ON " +
            "ne jouent qu'en mode FILL.",
        controls = listOf(
            cmd("[TRIG] maintenu + [LEFT]/[RIGHT]", "Micro timing du trig.", MICRO),
            cmd("[TRIG PARAMETERS] × 2", "Page des retrigs : RTRG, VFAD, LEN, RATE.", TRIG_PAGE2),
            cmd("[PAGE] maintenu / [YES] + [PAGE]", "Mode FILL tant que tenu / pour un tour.", FILL),
        ),
        steps = listOf(
            "En GRID RECORDING, maintiens un trig de charley et appuie sur [RIGHT] pour le retarder un peu.",
            "Sur la caisse claire, maintiens le dernier trig, appuie deux fois sur [TRIG PARAMETERS] et active RTRG.",
            "Règle RATE sur 1/32 puis VFAD vers une valeur positive : le roulement monte en vélocité.",
            "Pose un trig de crash, maintiens-le et règle FILL sur ON (page TRIG).",
            "Sors du GRID RECORDING, lance la lecture et maintiens [PAGE] : le crash joue tant que tu maintiens.",
        ),
        expected = "Le charley groove, la caisse claire roule et le crash n'apparaît qu'en mode FILL.",
        exerciseTitle = "Montée avant le drop",
        exercise = listOf(
            "Programme un roulement de caisse claire en retrig qui monte en vélocité.",
            "Mets-le en FILL ON pour qu'il ne joue que lorsque tu maintiens [PAGE].",
            "Déclenche-le en direct juste avant de changer de pattern.",
        ),
        success = "Le roulement n'arrive que sur ton geste et mène proprement au pattern suivant.",
        style = "Hardtek : le roulement en retrig avant le retour du kick est un classique.",
        mistakes = listOf(
            "Chercher les retrigs sur une piste MIDI : ils n'existent que sur les pistes audio.",
            "Maintenir [PAGE] en GRID RECORDING : là, [PAGE] change de page au lieu d'activer FILL.",
        ),
        quiz = listOf(
            qz("Comment ajouter du micro timing à un trig ?",
                listOf("Maintenir le [TRIG] et appuyer sur [LEFT]/[RIGHT]", "[FUNC] + [TEMPO]", "[PAGE] + [TRIG]"), 0,
                "En GRID RECORDING, trig maintenu + flèches ouvre le micro timing.", MICRO),
            qz("Quel RATE de retrig donne deux coups par pas ?", listOf("1/16", "1/32", "1/8"), 1,
                "1/16 = un coup par pas, 1/32 = deux coups par pas.", TRIG_PAGE2),
            qz("Que fait [YES] + [PAGE] pendant la lecture ?",
                listOf("Sauve le pattern", "Active le mode FILL pour un tour", "Change de page"), 1,
                "FILL devient actif au prochain bouclage et dure un tour de pattern.", FILL),
            qz("Un trig réglé FILL ON joue…", listOf("Toujours", "Seulement en mode FILL", "Jamais"), 1,
                "FILL ON : le trig ne joue que lorsque le mode FILL est actif.", TRIG_PAGE1),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 7 — Façonner le son
    // ---------------------------------------------------------------------
    private const val M7 = "dt2-m7"

    private val m7l1 = ctLesson(
        M7, 1, "Source : machine, hauteur et portion du sample", listOf(MACHINES, PLAYBACK, SRC), 20,
        objective = "Choisir la machine de lecture et régler la hauteur, le sens de lecture et la partie jouée du sample.",
        explanation = "[FUNC] + [SRC] ouvre le menu MACHINE : on y choisit la machine source de la piste (ONESHOT par défaut, " +
            "mais aussi WERP, STRETCH, REPITCH, SLICE, GRID ou MIDI). Avec ONESHOT, la page [SRC] règle TUNE (± 5 octaves), PLAY " +
            "(FORWARD, REVERSE, FORWARD LOOP, REVERSE LOOP), SAMP, STRT (début), LEN (longueur jouée) et LOOP (point de boucle). " +
            "Un second appui sur [SRC] montre la forme d'onde avec ces repères.",
        controls = listOf(
            cmd("[FUNC] + [SRC]", "Menu MACHINE : choix de la machine source.", MACHINES),
            cmd("TUNE", "Hauteur du sample, ± 5 octaves.", MACHINES),
            cmd("PLAY", "Sens et boucle de lecture.", PLAYBACK),
            cmd("STRT / LEN / LOOP", "Début, longueur et point de boucle.", PLAYBACK),
        ),
        steps = listOf(
            "Choisis une piste avec un sample long (nappe ou boucle) et appuie sur [SRC].",
            "Monte TUNE d'une octave en maintenant [FUNC] pendant que tu tournes.",
            "Règle PLAY sur REVERSE et écoute.",
            "Avance STRT pour démarrer plus loin dans le sample, puis raccourcis LEN.",
            "Appuie une seconde fois sur [SRC] pour voir la forme d'onde et les repères.",
        ),
        expected = "Le sample joue à une autre hauteur, à l'envers puis seulement sur la partie choisie.",
        exerciseTitle = "Un sample, trois sons",
        exercise = listOf(
            "À partir d'un seul sample, crée trois versions sur trois pistes : grave, à l'envers, et un court extrait.",
            "Programme-les dans un même pattern.",
        ),
        success = "On reconnaît les trois versions du même sample dans le pattern.",
        mistakes = listOf(
            "Chercher TUNE sur une piste MIDI : ses pages SRC sont différentes (canal, program…).",
            "Mettre FORWARD LOOP sans régler LEN sur la page TRIG : la durée de la boucle dépend de LEN et de l'enveloppe AMP.",
        ),
        quiz = listOf(
            qz("Comment ouvrir le menu MACHINE ?", listOf("[FUNC] + [SRC]", "[SRC] × 2", "[FUNC] + [FLTR]"), 0,
                "La fonction orange de [SRC] ouvre le menu MACHINE.", MACHINES),
            qz("Quelle est la machine source par défaut ?", listOf("ONESHOT", "GRID", "WERP"), 0,
                "ONESHOT est la machine par défaut : elle lit le sample en avant, à l'envers ou en boucle.", MACHINES),
            qz("Quelle est la plage de TUNE ?", listOf("± 1 octave", "± 2 octaves", "± 5 octaves"), 2,
                "TUNE couvre ± 5 octaves ; 0 garde la hauteur d'origine.", MACHINES),
            qz("Que définissent STRT + LEN ?", listOf("Le volume", "La portion du sample jouée", "La vitesse du séquenceur"), 1,
                "Start + Length fixent le début et la fin de la portion jouée.", PLAYBACK),
        ),
    )

    private val m7l2 = ctLesson(
        M7, 2, "Filtre et enveloppe d'amplitude", listOf(FLTR, MULTIMODE, AMP), 20,
        objective = "Sculpter le timbre avec le filtre et la forme du son avec l'enveloppe d'amplitude.",
        explanation = "[FLTR] page 1, avec le filtre MULTI-MODE : FREQ (fréquence de coupure), RESO (résonance), TYPE " +
            "(passe-bas → passe-bande → passe-haut), ENV (profondeur de l'enveloppe de filtre) et ATK, DEC, SUS, REL. La page 2 " +
            "ajoute le filtre BASE/WIDTH. [AMP] règle l'enveloppe de volume : ATK, HOLD, DEC (mode AHD) ou SUS/REL (mode ADSR), " +
            "ainsi que PAN et VOL, qui contrairement au niveau de piste peut être verrouillé par pas.",
        controls = listOf(
            cmd("[FLTR]", "Filtre : FREQ, RESO, TYPE, ENV, enveloppe.", MULTIMODE),
            cmd("BASE / WIDTH (page 2)", "Filtre passe-haut + passe-bas en série.", FLTR),
            cmd("[AMP]", "Enveloppe : ATK, HOLD, DEC, MODE AHD/ADSR, PAN, VOL.", AMP),
        ),
        steps = listOf(
            "Choisis une piste de basse et appuie sur [FLTR].",
            "Baisse FREQ, monte RESO et écoute.",
            "Monte ENV et raccourcis DEC (filtre) : chaque note « claque » davantage.",
            "Appuie sur [AMP] et raccourcis DEC : la note devient plus courte.",
            "Essaie PAN pour placer le son à gauche ou à droite.",
        ),
        expected = "La basse passe d'un son sourd à un son mordant, et sa durée change avec l'enveloppe.",
        exerciseTitle = "Basse acide",
        exercise = listOf(
            "Avec un sample de basse, règle FREQ bas, RESO haut et une enveloppe de filtre courte.",
            "Verrouille FREQ sur quelques pas pour faire « parler » la ligne.",
        ),
        success = "La ligne évolue pas à pas comme une basse acid.",
        style = "Acidcore : résonance haute et enveloppe de filtre courte, puis locks sur FREQ.",
        mistakes = listOf(
            "Monter RESO trop haut avec un volume élevé : le son peut devenir très fort.",
            "Chercher SUS et REL en mode AHD : ils n'existent qu'en mode ADSR.",
        ),
        quiz = listOf(
            qz("Que fait TYPE sur le filtre MULTI-MODE ?",
                listOf("Change de sample", "Passe du passe-bas au passe-bande puis au passe-haut", "Règle la résonance"), 1,
                "TYPE fait évoluer le filtre de passe-bas à passe-bande à passe-haut.", MULTIMODE),
            qz("Quel paramètre règle la profondeur de l'enveloppe de filtre ?", listOf("ENV", "WIDTH", "FREQ"), 0,
                "ENV règle la modulation de la coupure par l'enveloppe ; il est bipolaire.", MULTIMODE),
            qz("Avec BASE à 0, le filtre BASE/WIDTH agit comme…", listOf("Un passe-haut", "Un passe-bas réglé par WIDTH", "Un écho"), 1,
                "BASE à 0 : il se comporte en passe-bas dont WIDTH règle la plage.", FLTR),
            qz("Quelle différence entre VOL (page AMP) et le niveau de piste ?",
                listOf("Aucune", "VOL peut être verrouillé par pas", "VOL agit sur toutes les pistes"), 1,
                "Contrairement au niveau de piste, VOL accepte les parameter locks.", AMP),
        ),
    )

    private val m7l3 = ctLesson(
        M7, 3, "Effets de piste et LFO", listOf(FX, LFO), 20,
        objective = "Salir un son avec les effets de piste et l'animer avec un LFO.",
        explanation = "[FX] contient BR (réduction de bits, de 16 à 1), OVER (overdrive), SRR (réduction de fréquence " +
            "d'échantillonnage) et les envois DEL, REV et CHR vers les effets partagés. [MOD] règle le LFO 1 (trois LFO sur une " +
            "piste audio, deux sur une piste MIDI) : SPD (vitesse, 8, 16 ou 32 pour suivre le tempo), MULT, DEST (paramètre modulé), " +
            "WAVE (forme), MODE (FRE libre, TRG relancé à chaque note, ONE comme une enveloppe…) et DEP (profondeur).",
        controls = listOf(
            cmd("[FX]", "BR, OVER, SRR, envois DEL/REV/CHR.", FX),
            cmd("[MOD]", "LFO : SPD, MULT, FADE, DEST, WAVE, SPH, MODE, DEP.", LFO),
        ),
        steps = listOf(
            "Sur une piste de percussion, appuie sur [FX] et monte OVER, puis SRR : le son se durcit.",
            "Appuie sur [MOD], choisis DEST = coupure du filtre et valide avec [YES].",
            "Règle WAVE sur triangle, SPD sur 16 et monte DEP.",
            "Passe MODE sur TRG pour que le LFO reparte à chaque note.",
            "Remets les effets à zéro avec encodeur + [NO] si le son devient trop dur.",
        ),
        expected = "Le son se sature, puis le filtre bouge en rythme avec le LFO.",
        exerciseTitle = "Lead saturé qui bouge",
        exercise = listOf(
            "Sur un son de lead, ajoute OVER et un peu de BR.",
            "Module la coupure du filtre avec un LFO carré synchronisé (SPD 32).",
        ),
        success = "Le lead est plus agressif et s'ouvre/se ferme en rythme.",
        style = "Mentalcore : saturation et LFO rapides sur les leads.",
        mistakes = listOf(
            "Oublier de valider DEST avec [YES] : la destination n'est pas appliquée.",
            "Laisser DEP à 0.00 : le LFO tourne sans aucun effet.",
        ),
        quiz = listOf(
            qz("Que fait BR ?", listOf("Réduction de bits", "Réverbération", "Basse"), 0,
                "BR (Bit Reduction) va de 16 bits à 1 bit.", FX),
            qz("Combien de LFO a une piste audio ?", listOf("1", "2", "3"), 2,
                "Trois LFO sur une piste audio, deux sur une piste MIDI.", LFO),
            qz("Quel MODE relance le LFO à chaque note ?", listOf("FRE", "TRG", "HLD"), 1,
                "TRG redémarre le LFO quand une note est déclenchée.", LFO),
            qz("Quelle valeur de DEP correspond à aucune modulation ?", listOf("0.00", "64", "127"), 0,
                "DEP est bipolaire ; 0.00 au centre = pas de modulation.", LFO),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 8 — Effets, mixage et sampling
    // ---------------------------------------------------------------------
    private const val M8 = "dt2-m8"

    private val m8l1 = ctLesson(
        M8, 1, "Delay, réverbération et chorus", listOf(SEND_FX, REVERB_CHORUS, FX), 20,
        objective = "Régler les trois effets partagés du pattern et y envoyer certaines pistes.",
        explanation = "Chorus, delay et reverb sont des effets d'envoi réglés au niveau du pattern : toutes les pistes partagent les " +
            "mêmes réglages, mais chacune a ses niveaux d'envoi (DEL, REV, CHR sur sa page [FX]). [FUNC] + [FX] ouvre les pages SEND " +
            "FX ; [UP]/[DOWN] passent de DELAY à REVERB puis CHORUS. Delay : TIME (relatif au tempo), X (ping-pong), FDBK " +
            "(répétitions ; attention, une valeur haute peut devenir très forte), HPF/LPF. Reverb : PRE, DEC (taille), FREQ/GAIN.",
        controls = listOf(
            cmd("[FUNC] + [FX]", "Pages SEND FX : delay, reverb, chorus.", SEND_FX),
            cmd("DEL / REV / CHR (page [FX])", "Envois de la piste vers les effets.", FX),
            cmd("TIME / FDBK / X", "Temps, répétitions et ping-pong du delay.", SEND_FX),
        ),
        steps = listOf(
            "Choisis la piste de caisse claire, appuie sur [FX] et monte DEL.",
            "Ouvre [FUNC] + [FX] : page DELAY. Règle TIME sur 24 (croche pointée) et un FDBK moyen.",
            "Active X (ping-pong) et écoute les répétitions passer de gauche à droite.",
            "Descends avec [DOWN] sur REVERB et règle DEC.",
            "Sur la piste, ajoute un peu de REV.",
        ),
        expected = "La caisse claire résonne en rythme et dans l'espace, sans toucher aux autres pistes.",
        exerciseTitle = "Écho de transition",
        exercise = listOf(
            "Verrouille un envoi DEL très haut sur le dernier trig de caisse claire du pattern.",
            "Monte FDBK pour que l'écho se prolonge sur le pattern suivant.",
        ),
        success = "Seul le dernier coup part en écho et fait la transition.",
        style = "Tribe : l'écho sur la dernière frappe sert souvent de transition.",
        mistakes = listOf(
            "Monter FDBK au maximum : le manuel prévient que le signal peut devenir très fort.",
            "Chercher l'envoi dans [FUNC] + [FX] : l'envoi de chaque piste est sur sa page [FX].",
        ),
        quiz = listOf(
            qz("Les réglages du delay sont-ils propres à chaque piste ?",
                listOf("Oui", "Non : partagés par le pattern, seuls les envois sont par piste"), 1,
                "Les effets d'envoi sont réglés au niveau du pattern ; chaque piste règle son envoi.", SEND_FX),
            qz("Comment ouvrir les pages SEND FX ?", listOf("[FUNC] + [FX]", "[FX] × 2", "[FUNC] + [MOD]"), 0,
                "[FUNC] + [FX] ouvre delay, reverb et chorus.", SEND_FX),
            qz("Que fait X sur le delay ?", listOf("Inverse le son", "Fait alterner l'écho entre gauche et droite", "Coupe le delay"), 1,
                "X (ping-pong) fait alterner les répétitions dans le champ stéréo.", SEND_FX),
            qz("Que règle DEC sur la reverb ?", listOf("La taille de l'espace", "Le pré-délai", "Le volume d'envoi"), 0,
                "DEC règle la durée de la queue de réverbération, donc la taille de l'espace.", REVERB_CHORUS),
        ),
    )

    private val m8l2 = ctLesson(
        M8, 2, "Compresseur, sidechain et mixeur", listOf(COMP, MIXER), 20,
        objective = "Équilibrer les pistes et faire « pomper » le mix sur le kick.",
        explanation = "[FUNC] + [MOD] ouvre les pages MIXER. Page 1 : le compresseur (THR seuil, ATK, REL, MUP gain de " +
            "compensation, RAT de 1.50 à 20.00, SCS source de sidechain, SCF filtre de sidechain, mix sec/compressé). Avec SCS sur " +
            "TRK1 (le kick) et SCF en passe-bas, le mix baisse à chaque kick : effet de pompe. Pages 2 et 3 : niveaux des pistes " +
            "1–8 et 9–16. Page FX MIXER : volume des effets et MOVD (overdrive général).",
        controls = listOf(
            cmd("[FUNC] + [MOD]", "Pages MIXER : compresseur, niveaux, FX mixer, entrées.", COMP),
            cmd("THR / RAT / MUP", "Seuil, taux et gain du compresseur.", COMP),
            cmd("SCS / SCF", "Source et filtre du sidechain.", COMP),
            cmd("MOVD", "Overdrive général du kit.", MIXER),
        ),
        steps = listOf(
            "Mets le kick sur la piste 1 et lance un pattern complet.",
            "Ouvre [FUNC] + [MOD] : page compresseur. Règle SCS sur TRK1.",
            "Baisse THR et monte RAT jusqu'à entendre le mix baisser à chaque kick.",
            "Règle SCF vers le passe-bas pour accentuer la pompe.",
            "Descends avec [DOWN] vers les niveaux des pistes et équilibre-les.",
        ),
        expected = "Le mix respire au rythme du kick et les niveaux sont équilibrés.",
        exerciseTitle = "Mix qui pompe",
        exercise = listOf(
            "Règle un sidechain sur le kick assez marqué pour l'entendre.",
            "Compense la perte de volume avec MUP.",
            "Ajoute un peu de MOVD et vérifie que rien ne sature trop.",
        ),
        success = "Le kick ressort nettement et le reste du mix pompe sans saturer.",
        style = "Hardtek : le kick doit dominer ; un sidechain léger aide les nappes à laisser la place.",
        mistakes = listOf(
            "Oublier MUP : la compression baisse le volume général.",
            "Choisir COMP MIX ou NOT COMP sans régler le routage dans [FUNC] + [FLTR] › KIT.",
        ),
        quiz = listOf(
            qz("Comment ouvrir les pages MIXER ?", listOf("[FUNC] + [MOD]", "[MOD] × 2", "[FUNC] + [AMP]"), 0,
                "La fonction orange de [MOD] ouvre le mixeur et le compresseur.", COMP),
            qz("Quel réglage fait réagir le compresseur au kick de la piste 1 ?", listOf("SCS = TRK1", "RAT = 20", "THR au maximum"), 0,
                "SCS choisit la source du sidechain ; TRK1 = le son de la piste 1.", COMP),
            qz("SCF en passe-bas donne plutôt…", listOf("Moins de pompe", "Une pompe caractéristique", "Aucune compression"), 1,
                "En passe-bas, le compresseur réagit surtout aux basses : effet de pompe.", COMP),
            qz("Que règle MOVD ?", listOf("L'overdrive général du kit", "Le volume du delay", "La réverbération"), 0,
                "MOVD (Master Overdrive) sur la page FX MIXER.", MIXER),
        ),
    )

    private val m8l3 = ctLesson(
        M8, 3, "Échantillonner une source", listOf(SAMPLING_MENU, SAMPLING, QUICK_SAMPLING), 25,
        objective = "Enregistrer un son externe ou interne, le couper proprement et l'assigner à une piste.",
        explanation = "[SAMPLING] ouvre le menu : SRC (MAIN, USB, TRK1–16 ou IN), CHAN (stéréo ou mono), THRES (seuil de " +
            "déclenchement), MON (écoute avant ou après le mixeur), R.LEN (durée en pas, ou MAX), R.STRT et ARM. Avec ARM sur ON, " +
            "l'enregistrement démarre quand le signal dépasse le seuil ; RECORD NOW démarre tout de suite. Durée maximale : 66 secondes. " +
            "Le sample est normalisé, puis on le coupe avec TRIM START/END (encodeurs E et H), on l'écoute avec [FUNC] + [YES], " +
            "on le nomme et on l'assigne à une piste.",
        controls = listOf(
            cmd("[SAMPLING]", "Menu d'échantillonnage.", SAMPLING_MENU),
            cmd("SRC / THRES / R.LEN / ARM", "Source, seuil, durée et armement.", SAMPLING_MENU),
            cmd("Encodeurs E et H", "TRIM START et TRIM END.", SAMPLING),
            cmd("[FUNC] + [YES]", "Écoute du sample avant de le sauver.", SAMPLING),
        ),
        steps = listOf(
            "Branche une source sur IN L/R (téléphone, synthé, autre machine).",
            "Appuie sur [SAMPLING], règle SRC sur IN avec l'encodeur A.",
            "Joue la source et règle son volume au plus fort sans saturer, en surveillant l'indicateur.",
            "Règle THRES (encodeur D) juste au-dessus du bruit de fond, puis R.LEN.",
            "Mets ARM sur ON et joue le son : l'enregistrement démarre au-dessus du seuil.",
            "Coupe le début et la fin avec les encodeurs E et H, écoute avec [FUNC] + [YES], puis [YES], nomme et assigne à une piste.",
        ),
        expected = "Un nouveau sample propre est enregistré et joue sur la piste choisie.",
        exerciseTitle = "Ton propre kick",
        exercise = listOf(
            "Échantillonne un son percussif externe (frappe sur une table, autre machine).",
            "Coupe-le au plus près de l'attaque.",
            "Assigne-le à une piste et programme-le dans un pattern.",
        ),
        success = "Le son démarre sans silence au début et sonne en rythme.",
        mistakes = listOf(
            "Seuil trop bas : le bruit de fond déclenche l'enregistrement.",
            "Croire que les effets sont enregistrés : l'enregistrement est toujours le signal sec, sans les effets.",
        ),
        quiz = listOf(
            qz("Quelle est la durée maximale d'un échantillonnage ?", listOf("30 s", "66 s", "5 min"), 1,
                "66 secondes, toujours disponibles même si la mémoire du projet est pleine.", SAMPLING_MENU),
            qz("Que fait ARM sur ON ?",
                listOf("Démarre l'enregistrement au-dessus du seuil", "Efface le sample", "Coupe l'entrée"), 0,
                "Armé, le sampler démarre quand le signal dépasse THRES.", SAMPLING_MENU),
            qz("Les effets de la Digitakt II sont-ils enregistrés dans le sample ?", listOf("Oui", "Non, le signal enregistré est sec"), 1,
                "Le manuel précise que l'audio enregistré est toujours le signal d'entrée sec.", SAMPLING_MENU),
            qz("Quels encodeurs coupent le début et la fin du sample ?", listOf("A et B", "E et H", "C et D"), 1,
                "TRIM START et TRIM END se règlent avec les encodeurs E et H.", SAMPLING),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 9 — Live et sauvegarde
    // ---------------------------------------------------------------------
    private const val M9 = "dt2-m9"

    private val m9l1 = ctLesson(
        M9, 1, "Chaînes et mode SONG", listOf(CHAINS, SONG), 20,
        objective = "Préparer l'ordre des patterns avec une chaîne, puis construire un morceau complet en mode SONG.",
        explanation = "Une chaîne (jusqu'à 64 patterns) se crée avec [PTN] puis [FUNC] + [YES], en touchant les patterns dans " +
            "l'ordre ; elle boucle à la fin, mais elle est perdue à l'extinction ou quand on choisit un autre pattern. Un song (16 par " +
            "projet, 99 lignes) est sauvé avec le projet : [SONG] + [TRIG] le choisit, [FUNC] + [SONG] ouvre l'édition. Chaque ligne a " +
            "un pattern, un nombre de répétitions, une longueur, un tempo et des mutes ; la ligne END choisit LOOP ou STOP.",
        controls = listOf(
            cmd("[PTN] puis [FUNC] + [YES]", "Créateur de chaîne.", CHAINS),
            cmd("[SONG] + [TRIG 1–16]", "Choisit un song et entre en mode SONG.", SONG),
            cmd("[FUNC] + [SONG]", "Écran d'édition du song.", SONG),
            cmd("[FUNC] + [DOWN] / [UP]", "Ajoute / supprime une ligne.", SONG),
        ),
        steps = listOf(
            "Appuie sur [PTN] puis [FUNC] + [YES], touche A01, A01, A02, A03, puis [YES] et [PLAY] : la chaîne joue.",
            "Appuie sur [SONG] puis [TRIG 1], puis [FUNC] + [SONG].",
            "Choisis CREATE ROWS FROM CHAIN pour transformer ta chaîne en lignes de song.",
            "Règle sur une ligne le nombre de répétitions et mute une piste.",
            "Lance avec [PLAY] ; [STOP] deux fois ramène au début.",
        ),
        expected = "Les patterns s'enchaînent dans l'ordre prévu et le song garde cet ordre.",
        exerciseTitle = "Structure d'un morceau",
        exercise = listOf(
            "Construis un song : intro (2 fois), partie principale (4 fois), break avec kick muté (2 fois), retour (4 fois).",
            "Règle la ligne END sur STOP.",
        ),
        success = "Le morceau se déroule seul et s'arrête à la fin.",
        style = "Hardtek / tribe : intro, montée, break sans kick puis retour, c'est la forme classique.",
        mistakes = listOf(
            "Compter sur une chaîne après extinction : elle n'est pas sauvegardée, contrairement aux songs.",
            "Oublier de sauvegarder le projet : les songs sont gardés avec le projet.",
        ),
        quiz = listOf(
            qz("Une chaîne est-elle conservée après extinction ?", listOf("Oui", "Non"), 1,
                "Les chaînes ne peuvent pas être sauvegardées et se perdent à l'extinction.", CHAINS),
            qz("Combien de lignes au maximum dans un song ?", listOf("16", "64", "99"), 2,
                "Un song peut avoir jusqu'à 99 lignes, et un projet 16 songs.", SONG),
            qz("Comment ouvrir l'écran d'édition du song ?", listOf("[FUNC] + [SONG]", "[SONG] + [YES]", "[PTN] + [SONG]"), 0,
                "[FUNC] + [SONG] ouvre le SONG EDIT.", SONG),
            qz("À quoi sert la ligne END ?", listOf("À choisir LOOP ou STOP à la fin", "À couper le son", "À changer de projet"), 0,
                "END décide si le song recommence (LOOP) ou s'arrête (STOP).", SONG),
        ),
    )

    private val m9l2 = ctLesson(
        M9, 2, "Jouer en direct sans tout casser", listOf(CONTROL_ALL, TEMP_SAVE, PERFORM), 20,
        objective = "Modifier tout le pattern en direct et pouvoir revenir en arrière instantanément.",
        explanation = "CONTROL ALL : maintenir [TRK] en tournant un encodeur modifie ce paramètre sur toutes les pistes audio ; " +
            "[NO] avant de relâcher [TRK] annule. [FUNC] + [YES] fait une sauvegarde temporaire du pattern, [FUNC] + [NO] le " +
            "recharge depuis ce point : idéal pour improviser. PERFORM KIT ([FUNC] + [PRESET/KIT]) garde tes réglages du kit quand " +
            "tu changes de pattern ; [PRESET/KIT] + [NO] recharge le kit du pattern. Rien de tout cela n'est sauvegardé définitivement.",
        controls = listOf(
            cmd("[TRK] maintenu + encodeur", "CONTROL ALL sur toutes les pistes audio.", CONTROL_ALL),
            cmd("[FUNC] + [YES] / [FUNC] + [NO]", "Sauvegarde / rechargement temporaire du pattern.", TEMP_SAVE),
            cmd("[FUNC] + [PRESET/KIT]", "Mode PERFORM KIT marche/arrêt.", PERFORM),
        ),
        steps = listOf(
            "Lance un pattern et fais une sauvegarde temporaire avec [FUNC] + [YES].",
            "Maintiens [TRK], ouvre [FLTR] et baisse FREQ : toutes les pistes audio se ferment.",
            "Relâche [TRK] pour garder le changement.",
            "Ajoute des trigs, change des sons, puis recharge avec [FUNC] + [NO] : tout revient au point de sauvegarde.",
            "Active PERFORM KIT avec [FUNC] + [PRESET/KIT] (« P » clignote) et change de pattern : tes réglages restent.",
        ),
        expected = "Tu peux déformer tout le pattern en direct puis revenir exactement au point de départ.",
        exerciseTitle = "Montée filtrée",
        exercise = listOf(
            "Fais une sauvegarde temporaire.",
            "Sur 8 mesures, ferme progressivement le filtre de toutes les pistes avec CONTROL ALL.",
            "Au moment du drop, recharge le pattern avec [FUNC] + [NO].",
        ),
        success = "Le drop revient d'un coup au pattern d'origine.",
        style = "Hardtek / mentalcore : filtre fermé puis retour brutal, effet garanti en live.",
        mistakes = listOf(
            "Croire que la sauvegarde temporaire est définitive : elle est perdue si tu charges un autre projet.",
            "Utiliser CONTROL ALL sur des pistes MIDI : il ne s'applique qu'aux pistes audio.",
        ),
        quiz = listOf(
            qz("Comment modifier un paramètre sur toutes les pistes audio ?",
                listOf("Maintenir [TRK] en tournant l'encodeur", "Maintenir [FUNC]", "Maintenir [PAGE]"), 0,
                "C'est la fonction CONTROL ALL.", CONTROL_ALL),
            qz("Comment annuler un CONTROL ALL avant de le valider ?", listOf("[NO] avant de relâcher [TRK]", "[STOP]", "[YES]"), 0,
                "Appuyer sur [NO] avant de relâcher [TRK] annule les changements.", CONTROL_ALL),
            qz("Que fait [FUNC] + [NO] hors STEP RECORDING ?",
                listOf("Recharge le pattern depuis la dernière sauvegarde temporaire", "Efface le pattern", "Coupe le son"), 0,
                "C'est le rechargement temporaire du pattern.", TEMP_SAVE),
            qz("En PERFORM KIT, que devient le kit quand tu changes de pattern ?",
                listOf("Il est rechargé", "Tes réglages modifiés sont conservés"), 1,
                "En PERFORM KIT, le kit n'est pas rechargé au changement de pattern.", PERFORM),
        ),
    )

    private val m9l3 = ctLesson(
        M9, 3, "Sauvegarder le projet et faire une copie", listOf(PROJECT, PATTERN_MENU, BACKUP, FACADE, DONNEES), 15,
        objective = "Ne jamais perdre ton travail : sauver le projet, les patterns, et faire une copie sur ordinateur.",
        explanation = "La machine redémarre toujours sur le projet actif. [FUNC] + [SETTINGS] sauvegarde et écrase le projet " +
            "courant ; [SETTINGS] › PROJECT › SAVE PROJECT AS l'enregistre ailleurs. Charger un autre projet remplace le projet actif : " +
            "sauve avant. Dans [SETTINGS] › PATTERN, SAVE TO PROJ enregistre le pattern (en entier, kit ou séquence) et RELOAD FROM PROJ " +
            "revient à la version enregistrée. Pour une copie de sécurité, l'application gratuite Elektron Transfer copie projets, " +
            "presets et samples sur l'ordinateur via USB.",
        controls = listOf(
            cmd("[FUNC] + [SETTINGS]", "Sauvegarde le projet courant.", PROJECT),
            cmd("[SETTINGS] › PROJECT", "Charger, enregistrer sous, gérer les projets.", PROJECT),
            cmd("[SETTINGS] › PATTERN", "SAVE TO PROJ / RELOAD FROM PROJ.", PATTERN_MENU),
            cmd("USB + Elektron Transfer", "Copie de sécurité sur ordinateur.", BACKUP),
        ),
        steps = listOf(
            "Appuie sur [FUNC] + [SETTINGS] pour sauver le projet en cours.",
            "Ouvre [SETTINGS] › PROJECT › SAVE PROJECT AS et enregistre une copie dans un autre emplacement.",
            "Dans [SETTINGS] › PATTERN › SAVE TO PROJ, enregistre WHOLE PATTERN.",
            "Modifie le pattern, puis reviens à la version enregistrée avec RELOAD FROM PROJ › WHOLE PATTERN.",
            "Si tu as un ordinateur : relie la machine en USB et copie ton projet avec Elektron Transfer.",
        ),
        expected = "Ton projet est enregistré, une copie existe, et tu sais revenir à la version sauvegardée d'un pattern.",
        exerciseTitle = "Routine de fin de session",
        exercise = listOf(
            "À la fin de ta session, sauve le projet avec [FUNC] + [SETTINGS].",
            "Éteins puis rallume : vérifie que tout est là.",
            "Une fois par semaine, fais une copie avec Elektron Transfer.",
        ),
        success = "Après redémarrage, le projet et les patterns sont identiques.",
        mistakes = listOf(
            "Charger un autre projet sans sauver : le projet actif est remplacé.",
            "Utiliser SAVE TO PROJ sur un pattern avant d'avoir sauvegardé le projet une première fois : le manuel l'exige.",
        ),
        quiz = listOf(
            qz("Comment sauvegarder rapidement le projet courant ?", listOf("[FUNC] + [SETTINGS]", "[FUNC] + [YES]", "[STOP] + [STOP]"), 0,
                "[FUNC] + [SETTINGS] sauvegarde et écrase le projet courant.", PROJECT),
            qz("Que fait RELOAD FROM PROJ › WHOLE PATTERN ?",
                listOf("Revient à la version enregistrée du pattern", "Efface le projet", "Charge un kit d'usine"), 0,
                "Il recharge la séquence et le kit enregistrés du pattern.", PATTERN_MENU),
            qz("Que se passe-t-il si tu charges un autre projet ?",
                listOf("Les deux projets se mélangent", "Il remplace le projet actif", "Rien"), 1,
                "Charger un projet remplace le projet actif : sauvegarde avant.", PROJECT),
            qz("Quelle application sert aux copies de sécurité ?", listOf("Overbridge", "Elektron Transfer", "Novation Components"), 1,
                "Le manuel recommande l'application gratuite Elektron Transfer.", BACKUP),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(
            M6, 6, "Variations : locks et conditions",
            "Parameter locks, probabilité, trig conditions, micro timing, retrigs et FILL.",
            "Faire varier un pattern sans en changer, et déclencher des variations en direct.",
            "Les variations évitent la monotonie des longues boucles hardtek et tribecore.",
            listOf(m6l1, m6l2, m6l3),
        ),
        CourseModule(
            M7, 7, "Façonner le son",
            "Machines sources, filtre, enveloppe, effets de piste et LFO.",
            "Transformer un sample en basse acide, lead saturé ou percussion sale.",
            "Acidcore et mentalcore demandent des sons sculptés : filtre résonant, saturation, LFO.",
            listOf(m7l1, m7l2, m7l3),
        ),
        CourseModule(
            M8, 8, "Effets, mixage et sampling",
            "Delay, reverb, chorus, compresseur, mixeur et échantillonnage.",
            "Mixer le pattern, créer une pompe sur le kick et échantillonner tes propres sons.",
            "Le sidechain et l'écho de transition sont des outils clés en free party.",
            listOf(m8l1, m8l2, m8l3),
        ),
        CourseModule(
            M9, 9, "Live et sauvegarde",
            "Chaînes, songs, CONTROL ALL, sauvegarde temporaire, PERFORM KIT, projets.",
            "Jouer un set structuré, improviser sans risque et ne rien perdre.",
            "Un live hardtek ou tribe repose sur des enchaînements sûrs et des retours instantanés.",
            listOf(m9l1, m9l2, m9l3),
        ),
    )
}
