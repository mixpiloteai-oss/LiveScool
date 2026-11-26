package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.ContentStatus
import com.livemachine.academy.data.model.Exercise
import com.livemachine.academy.data.model.Lesson
import com.livemachine.academy.data.model.MachineControl
import com.livemachine.academy.data.model.ManualDoc.PARAMETER_GUIDE
import com.livemachine.academy.data.model.ManualRef
import com.livemachine.academy.data.model.QuizQuestion

/**
 * Module 3 — Paramètres sonores : oscillateur, hauteur, enveloppe, polyphonie, dynamique.
 *
 * Sources : manuel d'utilisation (partie française) et « electribe sampler Parameter Guide »
 * (Korg, 2016, en anglais). Les noms de paramètres restent en anglais, comme à l'écran.
 * Filtres et effets : Module 5. Modulation : Module 6.
 */
object Module3Content {

    private const val M = "m3"

    // Manuel d'utilisation
    private val OM_OSC = ManualRef("Description par section › Section d'édition › Oscillator", "23")
    private val OM_AMP = ManualRef("Description par section › Section d'édition › Amp/EG", "23")
    private val OM_CHANGEMENTS = ManualRef("Fonctions de l'electribe sampler › 2. Changements apportés à des fonctions de jeu", "34")
    private val OM_SPECS = ManualRef("Fiche technique", "38")

    // Guide des paramètres
    private val PG_PATTERN = ManualRef("1. PATTERN PARAMETERS", "3", PARAMETER_GUIDE)
    private val PG_PART = ManualRef("2. PART PARAMETERS", "4", PARAMETER_GUIDE)
    private val PG_PART_UTILITY = ManualRef("4. PART UTILITY", "5", PARAMETER_GUIDE)
    private val PG_SAMPLE_TUNE = ManualRef("5. SAMPLE EDIT › SAMPLE TUNE", "6", PARAMETER_GUIDE)
    private val PG_VELOCITY_CURVE = ManualRef("6. GLOBAL PARAMETERS › VELOCITY CURVE", "7", PARAMETER_GUIDE)
    private val PG_KNOB_MODE = ManualRef("6. GLOBAL PARAMETERS › KNOB MODE", "8", PARAMETER_GUIDE)
    private val PG_INIT = ManualRef("7. DATA UTILITY › INITIALIZE PATTERN", "9", PARAMETER_GUIDE)
    private val PG_SHORTCUTS = ManualRef("9. Appendix › Shortcut list", "11", PARAMETER_GUIDE)
    private val PG_OSC_LIST = ManualRef("9. Appendix › OSC Type List", "12–14", PARAMETER_GUIDE)

    private fun q(id: String, prompt: String, options: List<String>, correct: Int, explanation: String, ref: ManualRef) =
        QuizQuestion(id, prompt, options, correct, explanation, ref)

    // ---------------------------------------------------------------------
    // 3.1 Préparer un pattern de travail
    // ---------------------------------------------------------------------
    private val lesson1 = Lesson(
        id = "$M-l1",
        moduleId = M,
        order = 1,
        title = "Préparer un pattern de travail",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(PG_INIT, PG_KNOB_MODE, PG_SHORTCUTS),
        durationMinutes = 10,
        objective = "Partir d'un pattern vierge pour travailler le son, et comprendre pourquoi une commande ne change parfois pas la valeur.",
        explanation = "INITIALIZE PATTERN (menu DATA UTILITY) remet à zéro le pattern sélectionné : le son et la séquence " +
            "de chaque part (motion sequence comprise), ainsi que le tempo, la longueur et le beat. C'est la page blanche " +
            "idéale pour comprendre chaque paramètre sans le son d'un pattern d'usine. Le réglage Global KNOB MODE décide " +
            "de ce qui se passe quand la position d'une commande ne correspond pas à la valeur réelle : « Jump », la " +
            "valeur saute à la position de la commande (pratique pour éditer) ; « Catch », rien ne change tant que la " +
            "commande n'a pas rejoint la valeur réelle (pratique en live, évite les sauts de son) ; « Value Scale », la " +
            "valeur bouge de façon relative dans le sens où tu tournes. Si une commande semble ne rien faire, tu es " +
            "probablement en « Catch ». Depuis le système 2.00, Shift + < annule la dernière opération.",
        controls = listOf(
            MachineControl("INITIALIZE PATTERN (DATA UTILITY)", "Réinitialise toutes les données du pattern sélectionné.", PG_INIT),
            MachineControl("KNOB MODE (menu Global)", "Jump, Catch ou Value Scale.", PG_KNOB_MODE),
            MachineControl("Shift + <", "Annule la dernière opération (système 2.00 et plus).", PG_SHORTCUTS),
        ),
        steps = listOf(
            "Choisis un numéro de pattern que tu acceptes d'effacer.",
            "Menu/Enter, puis avec < ou > affiche « DATA UTILITY » et appuie sur Menu/Enter.",
            "Choisis INITIALIZE PATTERN et exécute-le avec Menu/Enter, comme les autres fonctions de DATA UTILITY (manuel p. 33).",
            "Sauvegarde ce pattern vierge avec Write : ce sera ton atelier son pour tout le module.",
            "Dans le menu Global, va sur KNOB MODE et choisis « Jump » pour la durée du module.",
            "Tourne une commande de la section d'édition : la valeur suit immédiatement la position de la commande.",
        ),
        expectedResult = "Tu as un pattern vierge sauvegardé et des commandes qui réagissent tout de suite.",
        exercise = Exercise(
            id = "$M-l1-ex",
            lessonId = "$M-l1",
            title = "Jump contre Catch",
            instructions = listOf(
                "En « Jump », règle Level d'une part au minimum, change de part puis reviens : tourne Level et écoute le saut.",
                "Passe KNOB MODE sur « Catch » et refais la même chose : la valeur ne bouge qu'une fois la commande arrivée à la valeur réelle.",
                "Choisis le mode que tu utiliseras en live et note-le.",
            ),
            successCriteria = "Tu sais décrire à l'oreille la différence entre Jump et Catch, et tu as choisi ton mode pour le live.",
            styleHint = "En live, « Catch » évite qu'un filtre ou un volume saute brutalement quand tu changes de part ou de pattern.",
        ),
        commonMistakes = listOf(
            "Lancer INITIALIZE PATTERN sur un pattern que tu voulais garder : tout son contenu est réinitialisé (guide p. 9).",
            "Croire qu'une commande est en panne : en « Catch », la valeur ne change qu'une fois la commande arrivée à la valeur réelle (guide p. 8).",
            "Compter sur Shift + < sans vérifier ta version de système : l'annulation demande le système 2.00 ou plus (guide p. 11).",
        ),
        quiz = listOf(
            q("$M-l1-q1", "Que réinitialise INITIALIZE PATTERN ?",
                listOf("Seulement le tempo", "Le son et la séquence de chaque part, la motion sequence, le tempo, la longueur et le beat", "Tous les patterns de la machine"),
                1, "Toutes les données du pattern sélectionné sont remises à l'état initial.", PG_INIT),
            q("$M-l1-q2", "En KNOB MODE « Catch », que se passe-t-il quand tu tournes une commande ?",
                listOf("La valeur saute à la position de la commande", "La valeur ne change qu'une fois la commande arrivée à la valeur réelle", "La commande est désactivée"),
                1, "Catch évite les changements de son brusques : utile en live.", PG_KNOB_MODE),
            q("$M-l1-q3", "Quel mode KNOB MODE est conseillé pour éditer ?",
                listOf("Jump", "Catch", "Value Scale"), 0, "Avec Jump, le résultat de la commande s'entend immédiatement.", PG_KNOB_MODE),
            q("$M-l1-q4", "Quel raccourci annule la dernière opération (système 2.00 et plus) ?",
                listOf("Shift + <", "Shift + Exit", "Shift + Write"), 0, "Shift + < annule la dernière opération.", PG_SHORTCUTS),
        ),
    )

    // ---------------------------------------------------------------------
    // 3.2 Choisir l'oscillateur
    // ---------------------------------------------------------------------
    private val lesson2 = Lesson(
        id = "$M-l2",
        moduleId = M,
        order = 2,
        title = "Choisir l'oscillateur",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(PG_OSC_LIST, PG_SHORTCUTS, OM_OSC, OM_CHANGEMENTS),
        durationMinutes = 15,
        objective = "Naviguer rapidement dans les 421 oscillateurs d'usine et savoir ce que fait la commande Edit selon le type.",
        explanation = "La commande Oscillator choisit la source sonore de la part. La liste d'usine compte 421 types, rangés " +
            "par catégories : Analog (1 à 16 : SAW, PULSE, TRIANGLE, SINE, les variantes UNI- et SYNC-, et quatre bruits), " +
            "Audio In (Audio In Mn et Audio In St), puis les samples : Kick, Snare, Clap, HiHat, Cymbal, Hits, Shots, " +
            "Voice, SE, FX, Tom, Phrase, Loop et PCM. Shift + Oscillator saute d'une catégorie à l'autre : c'est le moyen " +
            "le plus rapide de trouver un kick ou une forme d'onde. La commande Edit modifie la nature du son, et son effet " +
            "dépend du type choisi ; avec un sample, elle règle le point de départ et le sens de lecture (leçon 2.2).",
        controls = listOf(
            MachineControl("Oscillator", "Choisit le type d'oscillateur de la part.", OM_OSC),
            MachineControl("Shift + Oscillator", "Saute de catégorie en catégorie.", PG_SHORTCUTS),
            MachineControl("Edit (Oscillator)", "Modifie la nature du son ; son effet dépend du type.", OM_OSC),
        ),
        steps = listOf(
            "Dans ton pattern vierge, sélectionne la part 1 et allume Trigger.",
            "Maintiens Shift et tourne Oscillator : l'écran passe d'une catégorie à l'autre. Arrête-toi sur Kick.",
            "Relâche Shift et tourne Oscillator pour parcourir les kicks, en tapant le pad à chaque cran.",
            "Sélectionne la part 2, saute à la catégorie Analog avec Shift + Oscillator et choisis SAW.",
            "Passe en mode Keyboard et joue quelques notes, puis tourne Edit pour entendre comment il transforme le son.",
            "Essaie aussi PULSE et une variante UNI- ou SYNC-, en tournant Edit à chaque fois. Sauvegarde avec Write.",
        ),
        expectedResult = "Tu trouves en quelques secondes la catégorie voulue, et tu as entendu l'effet de Edit sur plusieurs formes d'onde Analog.",
        exercise = Exercise(
            id = "$M-l2-ex",
            lessonId = "$M-l2",
            title = "Le kit en 2 minutes",
            instructions = listOf(
                "Chronomètre-toi. Avec Shift + Oscillator, assigne sur 4 parts un Kick, une Snare ou un Clap, un HiHat et une forme d'onde Analog pour la basse.",
                "Pour chaque part, garde le premier son qui fonctionne : le but est la vitesse.",
            ),
            successCriteria = "4 parts prêtes en moins de 2 minutes, sans parcourir la liste cran par cran.",
            styleHint = "Acidcore : une SAW ou une PULSE fait une bonne base de ligne acide, que tu travailleras au filtre au Module 5.",
        ),
        commonMistakes = listOf(
            "Parcourir les 421 types cran par cran : Shift + Oscillator saute de catégorie en catégorie (guide p. 11).",
            "S'attendre à ce que Edit fasse la même chose sur tous les types : son effet dépend du type d'oscillateur (manuel p. 23).",
            "Oublier que Edit, sur un sample, règle le départ et le sens de lecture (manuel p. 34).",
        ),
        quiz = listOf(
            q("$M-l2-q1", "Combien de types d'oscillateur d'usine la liste compte-t-elle ?",
                listOf("250", "421", "499"), 1, "La liste OSC Type List va du n° 1 au n° 421.", PG_OSC_LIST),
            q("$M-l2-q2", "Comment sauter directement d'une catégorie d'oscillateur à la suivante ?",
                listOf("Shift + Oscillator", "Shift + Edit", "Menu/Enter"), 0, "Shift + Oscillator : sélection par saut de catégorie.", PG_SHORTCUTS),
            q("$M-l2-q3", "Dans quelle catégorie trouve-t-on SAW, PULSE, TRIANGLE et SINE ?",
                listOf("PCM", "Analog", "Shots"), 1, "Ce sont les types 1 à 4 de la catégorie Analog.", PG_OSC_LIST),
            q("$M-l2-q4", "Que fait la commande Edit de la section Oscillator ?",
                listOf("Elle règle le volume", "Elle modifie la nature du son, selon le type d'oscillateur", "Elle choisit la part"),
                1, "L'effet de la commande Edit dépend du type de forme d'onde actif.", OM_OSC),
        ),
    )

    // ---------------------------------------------------------------------
    // 3.3 Hauteur et glide
    // ---------------------------------------------------------------------
    private val lesson3 = Lesson(
        id = "$M-l3",
        moduleId = M,
        order = 3,
        title = "Hauteur et glide",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_OSC, PG_SHORTCUTS, PG_SAMPLE_TUNE),
        durationMinutes = 10,
        objective = "Régler la hauteur d'une part, ajouter du glide entre les notes, et accorder un sample à la source.",
        explanation = "La commande Pitch/Glide règle la hauteur de la part. Pour les sons de synthé, on obtient un glide " +
            "(transition douce d'une note à la suivante) en tournant cette commande avec Shift enfoncé ; Shift + Pitch/Glide " +
            "affiche aussi la page Glide du menu d'édition. Pour corriger durablement la hauteur d'un sample lui-même, " +
            "SAMPLE TUNE (dans SAMPLE EDIT) va de -63 à +63.",
        controls = listOf(
            MachineControl("Pitch/Glide", "Hauteur de la part.", OM_OSC),
            MachineControl("Shift + Pitch/Glide", "Règle le glide ; affiche la page Glide du menu d'édition.", PG_SHORTCUTS),
            MachineControl("SAMPLE TUNE (SAMPLE EDIT)", "Accord du sample, de -63 à +63.", PG_SAMPLE_TUNE),
        ),
        steps = listOf(
            "Sélectionne ta part de basse (forme d'onde Analog) et passe en mode Keyboard.",
            "Joue deux notes alternées et tourne Pitch/Glide : toute la part monte ou descend.",
            "Maintiens Shift et tourne Pitch/Glide : l'écran affiche la page Glide.",
            "Augmente le glide et rejoue tes deux notes : le son glisse de l'une à l'autre.",
            "Pour un kick sample, essaie Pitch pour l'accorder sur ta basse.",
            "Sauvegarde avec Write.",
        ),
        expectedResult = "La basse glisse entre les notes, et le kick est accordé à l'oreille avec la basse.",
        exercise = Exercise(
            id = "$M-l3-ex",
            lessonId = "$M-l3",
            title = "Accorder kick et basse",
            instructions = listOf(
                "Joue la basse en Keyboard sur la note de base de ta gamme.",
                "Tape le kick en alternance et ajuste son Pitch jusqu'à ce que les deux sonnent ensemble sans frotter.",
                "Ajoute un peu de glide à la basse pour une ligne plus « liquide ».",
            ),
            successCriteria = "Kick et basse sonnent juste ensemble, et la basse glisse sans perdre son attaque.",
            styleHint = "Hardtek et tribe : un kick accordé sur la basse donne un bas du spectre plus massif.",
        ),
        commonMistakes = listOf(
            "Chercher le glide sans Shift : sans Shift, Pitch/Glide ne règle que la hauteur (manuel p. 23).",
            "Confondre Pitch (réglage de la part) et SAMPLE TUNE (réglage du sample lui-même, dans SAMPLE EDIT) (guide p. 6).",
        ),
        quiz = listOf(
            q("$M-l3-q1", "Comment régler le glide ?",
                listOf("Tourner Pitch/Glide avec Shift enfoncé", "Tourner Edit", "Appuyer sur Tap"), 0,
                "On obtient le glide en tournant Pitch/Glide tout en maintenant Shift.", OM_OSC),
            q("$M-l3-q2", "Quel est le réglage de SAMPLE TUNE ?",
                listOf("0 à 127", "-63 à +63", "-12 à +12"), 1, "SAMPLE TUNE va de -63 à +63.", PG_SAMPLE_TUNE),
            q("$M-l3-q3", "Que fait Shift + Pitch/Glide ?",
                listOf("Affiche la page Glide du menu d'édition", "Remet la hauteur à zéro", "Affiche le BPM"), 0,
                "Le raccourci affiche la page Glide du menu d'édition.", PG_SHORTCUTS),
        ),
    )

    // ---------------------------------------------------------------------
    // 3.4 Enveloppe et niveaux
    // ---------------------------------------------------------------------
    private val lesson4 = Lesson(
        id = "$M-l4",
        moduleId = M,
        order = 4,
        title = "Enveloppe et niveaux",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_AMP, OM_CHANGEMENTS, PG_PATTERN),
        durationMinutes = 15,
        objective = "Sculpter l'attaque et la chute d'un son avec l'enveloppe Amp/EG, puis équilibrer les parts et le pattern.",
        explanation = "Le bouton Amp EG active l'enveloppe d'amplitude. Allumé, le volume suit Attack (vitesse d'attaque) et " +
            "Decay/Release (durée de chute). Éteint, l'enveloppe n'est pas prise en compte : enveloppe d'orgue fixe, et sur " +
            "un sample Decay/Release règle alors le Gate Time (leçon 2.2). Level règle le volume de la part, Pan sa place " +
            "dans l'image stéréo. PATTERN LEVEL (0 à 127, paramètre de pattern) règle le volume du pattern entier.",
        controls = listOf(
            MachineControl("Amp EG", "Active (allumé) ou coupe l'enveloppe d'amplitude.", OM_AMP),
            MachineControl("Attack", "Vitesse d'attaque du son.", OM_AMP),
            MachineControl("Decay/Release", "Durée de chute du son (Gate Time d'un sample si Amp EG est coupé).", OM_AMP),
            MachineControl("Level / Pan", "Volume et position stéréo de la part.", OM_AMP),
            MachineControl("PATTERN LEVEL", "Volume de tout le pattern, de 0 à 127.", PG_PATTERN),
        ),
        steps = listOf(
            "Sélectionne ta part de basse Analog et allume Amp EG.",
            "Mets Attack au minimum et Decay/Release court : la basse devient percussive.",
            "Monte Attack : l'attaque s'adoucit. Monte Decay/Release : le son dure plus longtemps.",
            "Éteins Amp EG et compare : le son suit maintenant une enveloppe fixe.",
            "Équilibre tes parts avec Level, et écarte légèrement les percussions avec Pan.",
            "Ajuste PATTERN LEVEL dans le menu d'édition, puis sauvegarde avec Write.",
        ),
        expectedResult = "Tu sais rendre un son percussif ou tenu, et ton pattern est équilibré, le kick et la basse au centre.",
        exercise = Exercise(
            id = "$M-l4-ex",
            lessonId = "$M-l4",
            title = "Trois basses, une forme d'onde",
            instructions = listOf(
                "Avec la même forme d'onde, crée trois réglages d'enveloppe : très court, moyen, long.",
                "Copie la part (leçon 2.5) pour garder chaque version sur une part différente.",
                "Écoute les trois versions sur le même kick et garde celle qui sert le mieux ton style.",
            ),
            successCriteria = "Trois versions nettement différentes à l'oreille, et une choisie pour ton style.",
            styleHint = "Hardtek : basse courte qui laisse respirer le kick. Tribe : plus de chute pour une basse qui roule.",
        ),
        commonMistakes = listOf(
            "Tourner Attack ou Decay/Release avec Amp EG éteint et ne rien entendre changer sur un son de synthé : l'enveloppe n'est pas prise en compte (manuel p. 23).",
            "Monter tous les Level au maximum : équilibre plutôt les parts entre elles, puis ajuste PATTERN LEVEL (guide p. 3).",
            "Écarter le kick ou la basse avec Pan : garde le bas du spectre au centre.",
        ),
        quiz = listOf(
            q("$M-l4-q1", "Amp EG éteint, quel est le comportement du volume ?",
                listOf("Il suit Attack et Decay/Release", "Enveloppe d'orgue fixe, les réglages d'enveloppe ne sont pas pris en compte", "Le son est coupé"),
                1, "Quand la fonction est coupée, les réglages d'enveloppe ne sont pas pris en compte.", OM_AMP),
            q("$M-l4-q2", "Que règle Decay/Release, Amp EG allumé ?",
                listOf("La durée de chute du son", "La hauteur", "Le panoramique"), 0, "Decay/Release définit la durée de chute du son.", OM_AMP),
            q("$M-l4-q3", "Quelle est la plage de PATTERN LEVEL ?",
                listOf("0 à 127", "1 à 16", "-50 à +50"), 0, "PATTERN LEVEL règle le volume du pattern entier, de 0 à 127.", PG_PATTERN),
            q("$M-l4-q4", "Quelle commande place une part dans l'image stéréo ?",
                listOf("Level", "Pan", "Edit"), 1, "Pan définit la position de la part dans l'image stéréo.", OM_AMP),
        ),
    )

    // ---------------------------------------------------------------------
    // 3.5 Polyphonie, priorité et alternance
    // ---------------------------------------------------------------------
    private val lesson5 = Lesson(
        id = "$M-l5",
        moduleId = M,
        order = 5,
        title = "Polyphonie, priorité et alternance",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(PG_PART, PG_PATTERN, OM_SPECS),
        durationMinutes = 15,
        objective = "Choisir la polyphonie d'une part, protéger les parts importantes quand les voix manquent, et faire alterner deux charleys.",
        explanation = "VOICE ASSIGN (paramètre de part) règle la polyphonie. Mono1 : monophonique ; si tu gardes le premier " +
            "pad enfoncé, les notes suivantes ne relancent pas l'enveloppe (jeu legato), sauf avec un sample one-shot. " +
            "Mono2 : monophonique, l'enveloppe repart à chaque pad. Poly1 et Poly2 : accords jusqu'à 4 voix, qui partagent " +
            "enveloppe, filtre, ampli et effet d'insertion ; en Poly1 l'enveloppe ne repart que quand tu as relâché tous " +
            "les pads (sauf sample one-shot), en Poly2 elle repart à chaque note. Un pattern joue au maximum 24 voix : " +
            "quand beaucoup de notes se chevauchent, une note en cours peut être coupée. PART PRIORITY sur « High » rend " +
            "cela moins probable pour une part, mais l'effet diminue si plusieurs parts sont en High. Enfin, ALTERNATE " +
            "13-14 et ALTERNATE 15-16 (paramètres de pattern) empêchent deux parts de jouer en même temps : par exemple " +
            "charley fermé sur la part 13 et ouvert sur la part 14.",
        controls = listOf(
            MachineControl("VOICE ASSIGN", "Mono1, Mono2, Poly1 ou Poly2.", PG_PART),
            MachineControl("PART PRIORITY", "Normal ou High : protège les notes de la part.", PG_PART),
            MachineControl("ALTERNATE 13-14 / 15-16", "Off ou On : les deux parts ne jouent jamais ensemble.", PG_PATTERN),
        ),
        steps = listOf(
            "Sélectionne ta part de basse. Dans le menu d'édition, va sur VOICE ASSIGN.",
            "Essaie Mono2 : joue deux pads l'un après l'autre en gardant le premier enfoncé ; l'attaque repart à chaque note.",
            "Passe en Mono1 et refais le même geste : la deuxième note s'enchaîne sans nouvelle attaque.",
            "Sélectionne la part du kick et règle PART PRIORITY sur High.",
            "Mets un charley fermé sur la part 13 et un charley ouvert sur la part 14 (leçon 3.2), puis règle ALTERNATE 13-14 sur On.",
            "Joue les deux pads : l'ouvert est coupé dès que le fermé joue. Sauvegarde avec Write.",
        ),
        expectedResult = "La basse joue legato ou relancée selon ton choix, le kick est protégé, et les deux charleys ne se chevauchent jamais.",
        exercise = Exercise(
            id = "$M-l5-ex",
            lessonId = "$M-l5",
            title = "Basse legato, charleys naturels",
            instructions = listOf(
                "Joue la même phrase de basse en Mono1 puis en Mono2 et choisis celle qui convient à ton style.",
                "Programme sur les parts 13 et 14 des charleys fermés et ouverts, avec ALTERNATE 13-14 sur On.",
                "Mets PART PRIORITY sur High pour une seule part : celle que tu ne veux jamais perdre.",
            ),
            successCriteria = "Une seule part en High, des charleys qui se coupent naturellement, et un choix argumenté entre Mono1 et Mono2.",
            styleHint = "Acidcore : Mono1 et le glide (leçon 3.3) donnent des lignes qui glissent ; Mono2 donne des basses tekno plus martelées.",
        ),
        commonMistakes = listOf(
            "Mettre plusieurs parts en PART PRIORITY High : l'effet diminue pour toutes (guide p. 4).",
            "Attendre de vraies voix indépendantes en Poly : les 4 voix partagent enveloppe, filtre, ampli et effet d'insertion (guide p. 4).",
            "Mettre les charleys sur d'autres parts que 13-14 ou 15-16 : l'alternance n'existe que pour ces deux paires (guide p. 3).",
        ),
        quiz = listOf(
            q("$M-l5-q1", "Quel réglage de VOICE ASSIGN permet un jeu legato (pas de nouvelle attaque si le premier pad reste enfoncé) ?",
                listOf("Mono1", "Mono2", "Poly2"), 0, "En Mono1, les notes suivantes ne relancent pas l'enveloppe tant que le premier pad est tenu.", PG_PART),
            q("$M-l5-q2", "Combien de voix au maximum une part en Poly1 ou Poly2 peut-elle jouer ?",
                listOf("2", "4", "24"), 1, "Un maximum de quatre voix peut sonner.", PG_PART),
            q("$M-l5-q3", "À quoi sert ALTERNATE 13-14 ?",
                listOf("À échanger les parts 13 et 14", "À empêcher les parts 13 et 14 de jouer en même temps", "À doubler le volume"),
                1, "Par exemple charley fermé sur 13 et ouvert sur 14 : ils ne jouent jamais simultanément.", PG_PATTERN),
            q("$M-l5-q4", "Pourquoi éviter de mettre PART PRIORITY sur High pour beaucoup de parts ?",
                listOf("Ça coupe le son", "L'effet du réglage diminue", "Ça bloque le tempo"), 1,
                "L'efficacité diminue si plusieurs parts sont réglées sur High.", PG_PART),
            q("$M-l5-q5", "Combien de voix au maximum un pattern entier peut-il jouer ?",
                listOf("16", "24", "64"), 1, "24 voix maximum pour un pattern entier.", OM_SPECS),
        ),
    )

    // ---------------------------------------------------------------------
    // 3.6 Dynamique et copie de son
    // ---------------------------------------------------------------------
    private val lesson6 = Lesson(
        id = "$M-l6",
        moduleId = M,
        order = 6,
        title = "Dynamique et copie de son",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(PG_PART, PG_VELOCITY_CURVE, PG_PART_UTILITY, PG_SHORTCUTS),
        durationMinutes = 10,
        objective = "Régler la réponse des pads à la force de frappe, et réutiliser un son réglé sur une autre part.",
        explanation = "TRG. PAD VELOCITY (paramètre de part, Off/On) active la sensibilité des pads à la force de frappe ; " +
            "Shift + pad 11 affiche directement cette page. VELOCITY CURVE (menu Global) règle la réponse : « Heavy » " +
            "demande de frapper fort pour obtenir l'effet, « Normal », « Light » donne l'effet sans frapper fort, et " +
            "« Const96 » fixe toujours la vélocité à 96. Pour réutiliser un son, COPY PART SOUND (PART UTILITY) copie " +
            "uniquement le son de la part sélectionnée vers une autre, sans ses pas ni sa motion sequence. COPY PART, " +
            "elle, copie le son et la séquence, motion sequence comprise.",
        controls = listOf(
            MachineControl("TRG. PAD VELOCITY", "Sensibilité des pads à la force de frappe (Off/On).", PG_PART),
            MachineControl("Shift + pad 11", "Affiche la page TRG. PAD VELOCITY.", PG_SHORTCUTS),
            MachineControl("VELOCITY CURVE (menu Global)", "Heavy, Normal, Light ou Const96.", PG_VELOCITY_CURVE),
            MachineControl("COPY PART SOUND (PART UTILITY)", "Copie seulement le son vers une autre part.", PG_PART_UTILITY),
        ),
        steps = listOf(
            "Sélectionne une part de percussion, maintiens Shift et appuie sur le pad 11 : la page TRG. PAD VELOCITY s'affiche.",
            "Règle-la sur On, allume Trigger et frappe le pad doucement puis fort : écoute la différence.",
            "Dans le menu Global, essaie VELOCITY CURVE sur Heavy puis sur Light, en frappant pareil.",
            "Sélectionne ta part de basse réglée aux leçons précédentes.",
            "Dans PART UTILITY, choisis COPY PART SOUND et copie le son vers une part libre.",
            "Vérifie que la nouvelle part a le même son mais aucune note. Sauvegarde avec Write.",
        ),
        expectedResult = "Les pads répondent à ta force de frappe comme tu le souhaites, et tu as dupliqué un son sans dupliquer sa séquence.",
        exercise = Exercise(
            id = "$M-l6-ex",
            lessonId = "$M-l6",
            title = "Percussions vivantes",
            instructions = listOf(
                "Active TRG. PAD VELOCITY sur une part de percussion.",
                "Joue un rythme de 16 coups en alternant coups forts et coups légers.",
                "Si tu n'arrives pas à faire entendre la différence, change VELOCITY CURVE.",
            ),
            successCriteria = "Ton rythme fait entendre clairement les accents, et tu as choisi une VELOCITY CURVE qui te convient.",
            styleHint = "Tribe : des accents marqués sur les percussions donnent le côté hypnotique et vivant.",
        ),
        commonMistakes = listOf(
            "Frapper plus fort sans résultat parce que TRG. PAD VELOCITY est sur Off (guide p. 4).",
            "Laisser VELOCITY CURVE sur Const96 et s'étonner que tous les coups sonnent pareil (guide p. 7).",
            "Utiliser COPY PART au lieu de COPY PART SOUND et écraser la séquence de la part de destination (guide p. 5).",
        ),
        quiz = listOf(
            q("$M-l6-q1", "Que fait VELOCITY CURVE réglé sur Const96 ?",
                listOf("La vélocité vaut toujours 96", "Les pads sont muets", "La réponse est très légère"), 0,
                "Avec Const96, la valeur de vélocité est toujours 96.", PG_VELOCITY_CURVE),
            q("$M-l6-q2", "Quelle courbe donne l'effet sans avoir à frapper fort ?",
                listOf("Heavy", "Light", "Normal"), 1, "Light permet d'obtenir l'effet sans frapper fort.", PG_VELOCITY_CURVE),
            q("$M-l6-q3", "Que copie COPY PART SOUND ?",
                listOf("Le son et la séquence", "Uniquement le son", "Uniquement la motion sequence"), 1,
                "Seul le son est copié ; les pas et la motion sequence ne le sont pas.", PG_PART_UTILITY),
            q("$M-l6-q4", "Quel raccourci affiche la page TRG. PAD VELOCITY ?",
                listOf("Shift + pad 11", "Shift + pad 1", "Shift + Tap"), 0, "Shift + pad 11 affiche la page TRG. PAD VELOCITY.", PG_SHORTCUTS),
        ),
    )

    val lessons: List<Lesson> = listOf(lesson1, lesson2, lesson3, lesson4, lesson5, lesson6)
}
