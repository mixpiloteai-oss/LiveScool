package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.ContentStatus
import com.livemachine.academy.data.model.Exercise
import com.livemachine.academy.data.model.Lesson
import com.livemachine.academy.data.model.MachineControl
import com.livemachine.academy.data.model.ManualDoc.PARAMETER_GUIDE
import com.livemachine.academy.data.model.ManualRef
import com.livemachine.academy.data.model.QuizQuestion

/**
 * Module 2 — Parts et samples.
 *
 * Source : manuel d'utilisation electribe/electribe sampler (Korg, 2015), partie française.
 * L'import de samples depuis la carte SD n'est pas enseigné (déjà connu) ; seule la
 * conservation des samples utilisateur est rappelée, car elle est propre à la machine.
 */
object Module2Content {

    private const val M = "m2"

    private val PARTIE = ManualRef("Pour produire du son › 4. Sélection d'une partie", "27")
    private val MOTIF = ManualRef("Pour produire du son › 3. Lecture d'un motif", "27")
    private val OSC = ManualRef("Description par section › Section d'édition › Oscillator", "23")
    private val AMP = ManualRef("Description par section › Section d'édition › Amp/EG", "23")
    private val SECTION_PARTIE = ManualRef("Description par section › Section d'édition de partie", "24")
    private val SAMPLER = ManualRef("Fonctions de l'electribe sampler", "34")
    private val CHANGEMENTS = ManualRef("Fonctions de l'electribe sampler › 2. Changements apportés à des fonctions de jeu", "34")
    private val ASSIGNER = ManualRef("Enregistrement et édition d'échantillons › Assigner l'échantillon à une partie", "35")
    private val ECHANTILLONNAGE = ManualRef("Enregistrement et édition d'échantillons › 1. Échantillonnage", "35")
    private val REECHANTILLONNAGE = ManualRef("Enregistrement et édition d'échantillons › 2. Rééchantillonnage", "35–36")
    private val EDITION = ManualRef("Enregistrement et édition d'échantillons › 3. Édition d'un échantillon", "36")
    private val PG_TIME_SLICE = ManualRef("5. SAMPLE EDIT › About time slice", "6", PARAMETER_GUIDE)
    private val CONSERVER = ManualRef("Enregistrement et édition d'échantillons › 4. Importer/exporter des échantillons", "36")
    private val CLAVIER = ManualRef("Production de musique › 1. Jouer avec les pads › Mode de clavier", "28")
    private val ACCORD = ManualRef("Production de musique › 1. Jouer avec les pads › Mode d'accord", "28")
    private val MUTE = ManualRef("Edition d'un motif › 1. Etouffement d'une partie", "32")
    private val ERASE = ManualRef("Edition d'un motif › 2. Effacement d'une partie", "32")
    private val COPY = ManualRef("Edition d'un motif › 3. Copie d'une partie", "32")
    private val MENUS = ManualRef("Autres réglages › Méthode d'affichage de chaque menu de réglage", "32")
    private val SPECS = ManualRef("Fiche technique", "38")

    private fun q(id: String, prompt: String, options: List<String>, correct: Int, explanation: String, ref: ManualRef) =
        QuizQuestion(id, prompt, options, correct, explanation, ref)

    // ---------------------------------------------------------------------
    // 2.1 Assigner un sample à une part
    // ---------------------------------------------------------------------
    private val lesson1 = Lesson(
        id = "$M-l1",
        moduleId = M,
        order = 1,
        title = "Assigner un sample à une part",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(PARTIE, ASSIGNER, SAMPLER, OSC),
        durationMinutes = 15,
        objective = "Choisir une part et lui donner le sample voulu, en tenant compte de la lecture stéréo ou mono.",
        explanation = "Chaque pattern a 16 parts. Une part porte son son, ses rythmes ou phrases, l'état de ses effets et " +
            "ses données de jeu. Son son vient de l'oscillateur : les samples d'usine (batteries, boucles de batterie, " +
            "phrases, hits) et tes samples utilisateur font partie des choix de l'oscillateur. On sélectionne la part, " +
            "puis on choisit le sample avec le paramètre Oscillator. Point clé : un sample stéréo est lu en stéréo sur " +
            "une part de numéro impair, et en mono sur une part de numéro pair.",
        controls = listOf(
            MachineControl("<Part, Part>", "Sélectionnent la part ; son pad s'allume en bleu.", PARTIE),
            MachineControl("Trigger + pads", "Écouter le son de chaque part.", PARTIE),
            MachineControl("Commande Oscillator", "Choisit la forme d'onde ou le sample de la part.", OSC),
        ),
        steps = listOf(
            "Choisis un pattern sur l'écran de sélection, sans le lancer.",
            "Avec <Part, Part>, sélectionne la part 1 : son pad s'allume en bleu.",
            "Allume Trigger et tape le pad de la part 1 pour entendre son son actuel.",
            "Tourne la commande Oscillator pour parcourir les sons, en tapant le pad entre deux crans.",
            "Arrête-toi sur un sample qui te plaît.",
            "Fais pareil avec la part 2. Si tu as un sample stéréo, compare-le sur la part 1 (impaire) et sur la part 2 (paire).",
            "Sauvegarde le pattern avec Write pour garder tes choix (leçon 1.6).",
        ),
        expectedResult = "Les parts sélectionnées jouent les samples que tu as choisis ; un sample stéréo sonne large sur une part impaire et mono sur une part paire.",
        exercise = Exercise(
            id = "$M-l1-ex",
            lessonId = "$M-l1",
            title = "Ton premier kit tekno",
            instructions = listOf(
                "Sur une copie de pattern, assigne un kick à une part, une basse à une autre, puis une caisse claire, un hi-hat et une percussion.",
                "Place tes samples stéréo (nappes, boucles, hits) sur des parts impaires.",
                "Écoute chaque part avec Trigger, puis sauvegarde avec Write.",
            ),
            successCriteria = "5 parts avec les sons voulus, les samples stéréo sur des parts impaires, pattern sauvegardé.",
            styleHint = "Hardtek et tribe : mets le kick et la basse en mono (le centre du mix) et garde les parts impaires pour ce qui doit rester large.",
        ),
        commonMistakes = listOf(
            "Mettre un sample stéréo sur une part paire et s'étonner qu'il sonne mono : il n'est lu en stéréo que sur une part impaire (p. 35).",
            "Tourner Oscillator alors que la mauvaise part est sélectionnée : vérifie quel pad est allumé en bleu (p. 27).",
            "Changer de pattern sans sauvegarder : les choix de samples sont perdus (p. 31).",
        ),
        quiz = listOf(
            q("$M-l1-q1", "Combien de parts compte un pattern ?",
                listOf("8", "16", "24"), 1, "Un pattern est constitué de 16 parts et d'un effet maître.", MOTIF),
            q("$M-l1-q2", "Comment choisir le sample d'une part ?",
                listOf("Avec le paramètre Oscillator, après avoir sélectionné la part", "Avec la commande Pan", "Dans le menu Global"),
                0, "On sélectionne une partie, puis on choisit l'échantillon voulu avec le paramètre Oscillator.", ASSIGNER),
            q("$M-l1-q3", "Un sample stéréo assigné à la part 4 est lu…",
                listOf("En stéréo", "En mono", "Il ne peut pas être assigné"),
                1, "Les échantillons stéréo sont lus en stéréo sur une partie impaire, en mono sur une partie paire.", ASSIGNER),
            q("$M-l1-q4", "Quels types de samples d'usine le manuel mentionne-t-il ?",
                listOf("Uniquement des sons de synthé", "Batteries, boucles de batterie, phrases et hits", "Uniquement des voix"),
                1, "L'instrument est fourni avec des sons de batterie, boucles de batterie, phrases et accents (hits).", SAMPLER),
        ),
    )

    // ---------------------------------------------------------------------
    // 2.2 Régler la lecture d'un sample
    // ---------------------------------------------------------------------
    private val lesson2 = Lesson(
        id = "$M-l2",
        moduleId = M,
        order = 2,
        title = "Régler la lecture d'un sample",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(CHANGEMENTS, OSC, AMP),
        durationMinutes = 15,
        objective = "Régler le départ, le sens, la hauteur, la durée et le niveau de lecture d'un sample sur une part.",
        explanation = "Sur l'electribe sampler, certaines commandes changent de rôle quand un sample est assigné à " +
            "l'oscillateur. La commande Edit (section Oscillator) règle le point de départ de lecture et peut inverser " +
            "le sens de lecture. Pitch règle la hauteur. Decay/Release règle le Gate Time, c'est-à-dire la durée de " +
            "lecture, à condition que Amp EG soit désactivé ; réglée sur 127, elle fait jouer le sample jusqu'au bout, " +
            "quelle que soit sa longueur. Avec Amp EG activé, le volume suit l'enveloppe réglée par Attack et " +
            "Decay/Release. Level et Pan règlent le volume et la position stéréo de la part.",
        controls = listOf(
            MachineControl("Edit (Oscillator)", "Avec un sample : point de départ de lecture et sens de lecture.", CHANGEMENTS),
            MachineControl("Pitch/Glide", "Hauteur du son.", OSC),
            MachineControl("Amp EG (bouton)", "Active (allumé) ou coupe l'enveloppe d'amplitude.", AMP),
            MachineControl("Decay/Release", "Amp EG coupé : Gate Time (durée de lecture) ; 127 = sample joué jusqu'au bout.", CHANGEMENTS),
            MachineControl("Attack", "Vitesse d'attaque, quand Amp EG est actif.", AMP),
            MachineControl("Level / Pan", "Volume et position stéréo de la part.", AMP),
        ),
        steps = listOf(
            "Sélectionne une part qui joue un sample, et allume Trigger pour la jouer au pad.",
            "Tourne Edit (section Oscillator) en tapant le pad : écoute le point de départ bouger, puis cherche le réglage qui inverse la lecture.",
            "Tourne Pitch pour monter et descendre la hauteur.",
            "Éteins le bouton Amp EG. Tourne Decay/Release : la durée de lecture change. Monte-la à 127 : le sample joue jusqu'au bout.",
            "Rallume Amp EG et joue avec Attack et Decay/Release : le volume suit maintenant l'enveloppe.",
            "Règle Level et Pan pour placer la part dans le mix, puis sauvegarde avec Write.",
        ),
        expectedResult = "Tu sais raccourcir ou laisser filer un sample, le jouer à l'envers, changer sa hauteur, et tu entends la différence entre Amp EG actif et coupé.",
        exercise = Exercise(
            id = "$M-l2-ex",
            lessonId = "$M-l2",
            title = "Kick court, nappe longue",
            instructions = listOf(
                "Sur la part du kick : Amp EG coupé, raccourcis-le avec Decay/Release jusqu'à ce qu'il claque sec.",
                "Sur une part avec un son long (nappe, phrase) : Amp EG coupé, Decay/Release à 127 pour qu'il joue entièrement.",
                "Sur un troisième sample, essaie la lecture inversée avec Edit.",
            ),
            successCriteria = "Un kick sec, un son long joué jusqu'au bout et un sample inversé, sauvegardés dans le pattern.",
            styleHint = "Hardtek : kick court et punchy. Tribe : sons longs et hypnotiques. Un sample inversé fait une bonne montée avant un drop.",
        ),
        commonMistakes = listOf(
            "Tourner Decay/Release pour régler le Gate Time alors que Amp EG est allumé : ce réglage du Gate Time ne marche que Amp EG coupé (p. 34).",
            "Croire que Edit a le même rôle qu'avec une forme d'onde : avec un sample, il règle le départ et le sens de lecture (p. 34).",
            "S'étonner qu'un sample avec un point de départ de boucle (LOOP START POINT) se comporte comme avec Amp EG coupé : c'est prévu (p. 34).",
        ),
        quiz = listOf(
            q("$M-l2-q1", "Avec un sample, que règle la commande Edit de la section Oscillator ?",
                listOf("Le point de départ de lecture et le sens de lecture", "La résonance", "Le tempo"),
                0, "Quand un échantillon est affecté à l'oscillateur, Edit ajuste le point de départ et change le sens de lecture.", CHANGEMENTS),
            q("$M-l2-q2", "À quelle condition Decay/Release règle-t-il le Gate Time d'un sample ?",
                listOf("Amp EG activé", "Amp EG désactivé", "Trigger allumé"),
                1, "Decay/Release ajuste le Gate Time à condition que Amp EG soit désactivé.", CHANGEMENTS),
            q("$M-l2-q3", "Que se passe-t-il avec Decay/Release réglé sur 127 (Amp EG coupé) ?",
                listOf("Le sample est muet", "Le sample joue jusqu'au bout, quelle que soit sa longueur", "Le sample joue à l'envers"),
                1, "Sur 127, l'échantillon est reproduit jusqu'à la fin, indépendamment de sa longueur.", CHANGEMENTS),
            q("$M-l2-q4", "Quand Amp EG est coupé, comment est géré le volume ?",
                listOf("Selon Attack et Decay/Release", "Avec une enveloppe d'orgue fixe, sans tenir compte des réglages d'enveloppe", "Par le pad tactile"),
                1, "Amp EG coupé, les réglages d'enveloppe ne sont pas pris en compte (enveloppe d'orgue fixe).", AMP),
        ),
    )

    // ---------------------------------------------------------------------
    // 2.3 Éditer un sample (SAMPLE EDIT)
    // ---------------------------------------------------------------------
    private val lesson3 = Lesson(
        id = "$M-l3",
        moduleId = M,
        order = 3,
        title = "Éditer un sample",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(EDITION, CONSERVER, SAMPLER, PG_TIME_SLICE),
        durationMinutes = 20,
        objective = "Utiliser le menu SAMPLE EDIT pour recadrer, accorder, découper, renommer ou régler le niveau d'un sample.",
        explanation = "SAMPLE EDIT s'ouvre depuis le menu d'édition. On y choisit un sample par son numéro avec la " +
            "commande de valeur, puis la fonction avec < et > : RENAME, START POINT, END POINT, LOOP START POINT " +
            "(la lecture boucle de ce point jusqu'au END POINT), SAMPLE TUNE (hauteur), TIME SLICE (découpe en tranches " +
            "rythmiques, sur un sample mono uniquement), CLEAR SLICE (annule la découpe), PLAY LEVEL (volume de lecture) et DELETE SAMPLE. Ces " +
            "fonctions marchent aussi sur les samples d'usine. Pendant l'édition, on écoute avec un pad ou le pad " +
            "tactile en Touch Scale ; Filter, Modulation, Amp/EG et Insert FX sont indisponibles. Write sauvegarde. " +
            "Attention : les samples utilisateur sont perdus à l'extinction s'ils ne sont pas exportés sur carte " +
            "(EXPORT SAMPLE ou EXPORT ALL SAMPLE, menu DATA UTILITY).",
        controls = listOf(
            MachineControl("Menu/Enter puis « SAMPLE EDIT »", "Ouvre l'édition de sample depuis le menu d'édition.", EDITION),
            MachineControl("Commande de valeur", "Choisit le numéro du sample, puis règle la fonction choisie.", EDITION),
            MachineControl("< et >", "Choisissent la fonction d'édition.", EDITION),
            MachineControl("Pads / pad tactile (Touch Scale)", "Écouter le sample pendant l'édition.", EDITION),
            MachineControl("Write", "Sauvegarde les changements.", EDITION),
        ),
        steps = listOf(
            "Depuis l'écran de sélection de pattern, appuie sur Menu/Enter.",
            "Avec < et >, choisis « SAMPLE EDIT », puis appuie sur Menu/Enter.",
            "Avec la commande de valeur, choisis le numéro du sample à modifier.",
            "Avec < et >, va sur START POINT et règle-le en écoutant au pad ; fais de même avec END POINT.",
            "Va sur SAMPLE TUNE et accorde le sample.",
            "Va sur PLAY LEVEL et règle le volume de lecture.",
            "Appuie sur Write pour sauvegarder. Si c'est un sample utilisateur, exporte-le ensuite sur carte avant d'éteindre.",
        ),
        expectedResult = "Le sample démarre et s'arrête où tu veux, à la bonne hauteur et au bon niveau, et la modification est sauvegardée.",
        exercise = Exercise(
            id = "$M-l3-ex",
            lessonId = "$M-l3",
            title = "Recadrer et trancher une boucle",
            instructions = listOf(
                "Choisis une boucle de batterie mono dans SAMPLE EDIT : TIME SLICE ne fonctionne pas sur un sample stéréo.",
                "Recadre-la avec START POINT et END POINT pour qu'elle commence pile sur le premier coup.",
                "Essaie TIME SLICE pour la découper, écoute au pad, puis annule avec CLEAR SLICE si le résultat ne te plaît pas.",
                "Renomme-la avec RENAME et sauvegarde avec Write.",
            ),
            successCriteria = "La boucle démarre sur le premier coup, porte ton nom, et tu as entendu le résultat de TIME SLICE.",
            styleHint = "Une boucle tribale bien recadrée sert de base percussive ; découpée, elle donne des variations pour les breaks.",
        ),
        commonMistakes = listOf(
            "Éteindre la machine sans exporter ses samples utilisateur : ils sont perdus, même après Write (p. 36).",
            "Chercher à filtrer ou ajouter un effet pendant l'édition : Filter, Modulation, Amp/EG et Insert FX sont indisponibles en SAMPLE EDIT (p. 36).",
            "Lancer TIME SLICE sur un sample stéréo : impossible, il faut un sample mono. Une fois découpé, le sample ne se rejoue plus en entier jusqu'à CLEAR SLICE (guide p. 6).",
            "Effacer un sample d'usine avec DELETE SAMPLE sans le vouloir : il ne peut plus être choisi avec la commande Oscillator (p. 36).",
        ),
        quiz = listOf(
            q("$M-l3-q1", "Que fait LOOP START POINT ?",
                listOf("Il efface la boucle", "La lecture boucle de ce point jusqu'au END POINT", "Il règle le tempo de la boucle"),
                1, "La lecture est mise en boucle à partir du point défini, jusqu'au point de fin (END POINT).", EDITION),
            q("$M-l3-q2", "Quelle fonction découpe un sample en tranches rythmiques ?",
                listOf("TIME SLICE", "SAMPLE TUNE", "RENAME"), 0, "TIME SLICE découpe l'échantillon ; CLEAR SLICE annule.", EDITION),
            q("$M-l3-q3", "Pendant l'édition d'un sample, quelles sections sont indisponibles ?",
                listOf("Aucune", "Filter, Modulation, Amp/EG et Insert FX", "Seulement le transport"),
                1, "Pendant l'édition d'un échantillon, Filter, Modulation, Amp/EG et Insert FX ne sont pas disponibles.", EDITION),
            q("$M-l3-q4", "Un sample utilisateur sauvegardé avec Write est-il conservé à l'extinction ?",
                listOf("Oui", "Non, il faut l'exporter sur carte (EXPORT SAMPLE ou EXPORT ALL SAMPLE)", "Seulement s'il est court"),
                1, "Même sauvegardés avec Write, les échantillons utilisateur sont perdus à la mise hors tension s'ils ne sont pas exportés.", EDITION),
        ),
    )

    // ---------------------------------------------------------------------
    // 2.4 Jouer une part au clavier et en accords
    // ---------------------------------------------------------------------
    private val lesson4 = Lesson(
        id = "$M-l4",
        moduleId = M,
        order = 4,
        title = "Jouer une part au clavier",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(CLAVIER, ACCORD),
        durationMinutes = 15,
        objective = "Jouer une part en notes avec les pads (mode Keyboard), changer d'octave, et jouer des accords (mode Chord).",
        explanation = "En mode Keyboard, les pads jouent la part sélectionnée comme un clavier. Les notes dépendent des " +
            "paramètres Key (tonique) et Scale (gamme) du menu d'édition ; par exemple Key = C et Scale = Ionian donne " +
            "les notes de Do majeur. Le bouton Step 1 descend le clavier d'une octave, le bouton Step 4 le monte ; le " +
            "bouton Step allumé indique le décalage. Ce qui se passe quand tu appuies sur plusieurs pads à la fois " +
            "dépend de Voice Assign. En mode Chord, les pads jouent des accords : choisis une part dont Voice Assign " +
            "est sur « Poly 1 » ou « Poly 2 » ; Chord Set change les variations d'accord.",
        controls = listOf(
            MachineControl("Keyboard", "Les pads jouent la part sélectionnée comme un clavier.", CLAVIER),
            MachineControl("<Part, Part>", "Choisit la part jouée au clavier.", CLAVIER),
            MachineControl("Step 1 / Step 4", "Clavier une octave plus bas / plus haut.", CLAVIER),
            MachineControl("Key, Scale (menu d'édition)", "Tonique et gamme du clavier.", CLAVIER),
            MachineControl("Chord", "Les pads jouent des accords (part en Poly 1 ou Poly 2).", ACCORD),
            MachineControl("Chord Set (menu d'édition)", "Change les variations d'accord.", ACCORD),
        ),
        steps = listOf(
            "Appuie sur Keyboard.",
            "Avec <Part, Part>, choisis une part qui joue une basse ou un son mélodique.",
            "Joue les pads comme un clavier.",
            "Appuie sur Step 1 pour descendre d'une octave, sur Step 4 pour monter ; regarde quel bouton Step est allumé.",
            "Dans le menu d'édition, change Key et Scale et écoute la différence.",
            "Appuie sur Chord et joue des accords sur une part réglée en Poly 1 ou Poly 2.",
        ),
        expectedResult = "Tu joues une ligne de notes dans la gamme choisie, sur plusieurs octaves, et des accords sur une part polyphonique.",
        exercise = Exercise(
            id = "$M-l4-ex",
            lessonId = "$M-l4",
            title = "Trouver une ligne de basse",
            instructions = listOf(
                "Choisis une part avec un son de basse et passe en Keyboard.",
                "Règle Key et Scale sur une gamme sombre de ton choix.",
                "Descends d'une ou deux octaves avec Step 1 et cherche une ligne de 4 notes qui tourne en boucle.",
            ),
            successCriteria = "Tu rejoues ta ligne de 4 notes trois fois de suite sans te tromper.",
            styleHint = "Acidcore et mentalcore : des lignes courtes et répétitives dans le grave. Le Module 4 les programmera dans le séquenceur.",
        ),
        commonMistakes = listOf(
            "Ne pas avoir sélectionné la bonne part avant de jouer au clavier : c'est la part choisie avec <Part, Part> qui joue (p. 28).",
            "Jouer des accords sur une part monophonique : le mode Chord demande une part en Poly 1 ou Poly 2 (p. 28).",
            "Croire que les pads sont cassés quand rien ne sort : vérifie le mode de pad (Keyboard allumé ?) (p. 37).",
        ),
        quiz = listOf(
            q("$M-l4-q1", "En mode Keyboard, quel bouton descend le clavier d'une octave ?",
                listOf("Step 1", "Step 4", "Exit"), 0, "Step 1 décale le clavier d'une octave vers le bas, Step 4 vers le haut.", CLAVIER),
            q("$M-l4-q2", "Quels paramètres définissent les notes jouées par le clavier ?",
                listOf("Level et Pan", "Key et Scale", "BPM et Swing"), 1, "Key (tonique) et Scale (gamme) du menu d'édition définissent le son produit.", CLAVIER),
            q("$M-l4-q3", "Pour jouer des accords, la part doit être réglée en…",
                listOf("Mono 2", "Poly 1 ou Poly 2", "Trigger Hold"), 1, "Choisissez une partie dont Voice Assign est réglé sur Poly 1 ou Poly 2.", ACCORD),
            q("$M-l4-q4", "Qu'est-ce qui détermine le son produit quand tu appuies sur plusieurs pads à la fois en mode Keyboard ?",
                listOf("Voice Assign", "Le contraste de l'écran", "Le métronome"), 0, "Le son produit avec plusieurs pads dépend des réglages de Voice Assign.", CLAVIER),
        ),
    )

    // ---------------------------------------------------------------------
    // 2.5 Copier, couper, effacer des parts
    // ---------------------------------------------------------------------
    private val lesson5 = Lesson(
        id = "$M-l5",
        moduleId = M,
        order = 5,
        title = "Copier, couper et effacer des parts",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(COPY, MUTE, ERASE, MENUS, SECTION_PARTIE),
        durationMinutes = 15,
        objective = "Dupliquer une part pour gagner du temps, couper une part sans l'effacer, et effacer ses notes.",
        explanation = "Part Copy copie tout le contenu d'une part vers une autre : utile pour décliner un son (deux kicks, " +
            "deux percussions). Elle se trouve dans PART UTILITY, dans le menu d'édition. Part Mute coupe le son d'une " +
            "part sans rien effacer : bouton Part Mute allumé, une pression sur le pad coupe la part (pad éteint), une " +
            "nouvelle pression la rétablit. Part Erase efface les notes : bouton Part Erase allumé, pendant la lecture, " +
            "maintiens le pad de la part. Pour effacer toute la séquence d'une part, CLEAR SEQUENCE (PART UTILITY).",
        controls = listOf(
            MachineControl("PART COPY (PART UTILITY)", "Copie tout le contenu de la part sélectionnée vers le pad de destination.", COPY),
            MachineControl("Part Mute", "Allumé : un pad coupe ou rétablit sa part.", MUTE),
            MachineControl("Part Erase", "Allumé, pendant la lecture : maintenir un pad efface les notes de sa part.", ERASE),
            MachineControl("CLEAR SEQUENCE (PART UTILITY)", "Efface toutes les données de séquence de la part sélectionnée.", ERASE),
        ),
        steps = listOf(
            "Avec <Part, Part>, sélectionne la part à copier.",
            "Menu/Enter, puis avec < ou > affiche « PART UTILITY » et appuie sur Menu/Enter.",
            "Choisis « PART COPY », appuie sur Menu/Enter, puis sur le pad de la part de destination.",
            "Lance le pattern. Allume Part Mute et tape le pad d'une part pour la couper, puis retape-le pour la rétablir.",
            "Allume Part Erase et, pendant la lecture, maintiens le pad d'une part dont tu veux effacer les notes.",
            "Sauvegarde avec Write si tu veux garder le résultat.",
        ),
        expectedResult = "Tu as une copie exacte d'une part, tu coupes et rétablis une part en lecture, et tu as effacé les notes d'une part.",
        exercise = Exercise(
            id = "$M-l5-ex",
            lessonId = "$M-l5",
            title = "Doubler ses percussions",
            instructions = listOf(
                "Copie ta part de percussion vers une part libre avec PART COPY.",
                "Sur la copie, change le sample ou le Pitch pour obtenir une variante (leçons 2.1 et 2.2).",
                "Pattern lancé, alterne les deux parts avec Part Mute.",
            ),
            successCriteria = "Deux parts de percussion différentes, que tu alternes en rythme avec Part Mute.",
            styleHint = "Tribe : alterner deux percussions proches avec Part Mute crée du mouvement sans changer de pattern.",
        ),
        commonMistakes = listOf(
            "Utiliser Part Erase pour couper une part : il efface ses notes. Pour couper sans perdre, c'est Part Mute (p. 32).",
            "Lancer PART COPY avec la mauvaise part sélectionnée : la source est la part choisie avec <Part, Part> (p. 32).",
            "Oublier que Part Copy remplace tout le contenu de la part de destination (p. 32).",
        ),
        quiz = listOf(
            q("$M-l5-q1", "Où se trouve la fonction PART COPY ?",
                listOf("Dans PART UTILITY, menu d'édition", "Dans le menu Global", "Sur le bouton Tap"), 0,
                "On sélectionne PART COPY sous PART UTILITY dans le menu d'édition.", COPY),
            q("$M-l5-q2", "Bouton Part Mute allumé, que fait une pression sur le pad d'une part ?",
                listOf("Elle efface la part", "Elle coupe son son (pad éteint) ; une nouvelle pression le rétablit", "Elle copie la part"), 1,
                "Le pad s'éteint et la part est coupée ; une nouvelle pression annule la coupure.", MUTE),
            q("$M-l5-q3", "Comment effacer les notes d'une part avec Part Erase ?",
                listOf("Une pression rapide machine arrêtée", "Pendant la lecture, maintenir le pad de la part", "Appuyer deux fois sur Exit"), 1,
                "Pendant la lecture, maintenir la pression sur le pad efface les données de note.", ERASE),
            q("$M-l5-q4", "Quelle commande efface toutes les données de séquence d'une part ?",
                listOf("CLEAR SEQUENCE", "DELETE SAMPLE", "FORMAT SD CARD"), 0,
                "CLEAR SEQUENCE, sous PART UTILITY, efface toutes les données de séquence de la part.", ERASE),
        ),
    )

    // ---------------------------------------------------------------------
    // 2.6 Rééchantillonner et échantillonner
    // ---------------------------------------------------------------------
    private val lesson6 = Lesson(
        id = "$M-l6",
        moduleId = M,
        order = 6,
        title = "Rééchantillonner et échantillonner",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(REECHANTILLONNAGE, ECHANTILLONNAGE, SAMPLER, EDITION),
        durationMinutes = 20,
        objective = "Créer un nouveau sample à partir du son de la machine (rééchantillonnage) ou d'une source branchée sur Audio In.",
        explanation = "Le rééchantillonnage enregistre la sortie de la machine : la lecture d'un pattern, ou le son d'une part. " +
            "Shift + Rec ouvre le réglage : choisis Stereo ou Mono (« Sampling mode »), puis avec > la source " +
            "« Resampling », puis avec > vérifie la durée disponible. Play/Pause enregistre la lecture du pattern ; ou bien " +
            "l'enregistrement démarre dès que tu touches un pad ou le pad tactile, pour une part seule. Stop arrête. " +
            "« New Sample » s'affiche : Play/Pause pour écouter, Write pour le garder comme sample utilisateur, Exit " +
            "pour l'abandonner. Le volume d'un sample rééchantillonné est rehaussé (PLAY LEVEL sur +12 dB) : en cas de " +
            "distorsion, mets PLAY LEVEL sur « Normal ». Pour une source externe (prise Audio In), on choisit la source " +
            "« Audio In ». La machine stocke jusqu'à 499 samples utilisateur, pour environ 270 secondes au total (en mono).",
        controls = listOf(
            MachineControl("Shift + Rec", "Ouvre le mode d'échantillonnage (Exit pour annuler).", REECHANTILLONNAGE),
            MachineControl("Commande de valeur", "Choisit Stereo/Mono, puis la source (Resampling ou Audio In).", REECHANTILLONNAGE),
            MachineControl("Bouton >", "Passe au choix de la source, puis à la durée disponible.", REECHANTILLONNAGE),
            MachineControl("Play/Pause / pads", "Démarre l'enregistrement (pattern / part seule).", REECHANTILLONNAGE),
            MachineControl("Stop", "Arrête l'enregistrement.", REECHANTILLONNAGE),
            MachineControl("Write / Exit", "Garde le nouveau sample / l'abandonne.", REECHANTILLONNAGE),
            MachineControl("Input Level", "Niveau d'Audio In : l'indicateur CLIP ne doit pas s'allumer.", ECHANTILLONNAGE),
        ),
        steps = listOf(
            "Choisis le pattern à rééchantillonner.",
            "Maintiens Shift et appuie sur Rec : Rec reste allumé et Play/Pause clignote.",
            "Avec la commande de valeur, choisis Stereo ou Mono.",
            "Appuie sur > et choisis « Resampling » avec la commande de valeur.",
            "Appuie sur > pour lire la durée d'enregistrement disponible.",
            "Appuie sur Play/Pause pour enregistrer la lecture du pattern, puis sur Stop pour arrêter.",
            "« New Sample » s'affiche : écoute avec Play/Pause, puis Write pour le garder ou Exit pour l'abandonner.",
            "Assigne le nouveau sample à une part (leçon 2.1). Exporte-le sur carte avant d'éteindre.",
        ),
        expectedResult = "Un nouveau sample utilisateur contient le son de ton pattern, et tu peux le jouer sur une part comme n'importe quel sample.",
        exercise = Exercise(
            id = "$M-l6-ex",
            lessonId = "$M-l6",
            title = "Figer un groove",
            instructions = listOf(
                "Rééchantillonne un pattern qui joue kick + basse.",
                "Assigne le résultat à une part impaire si tu l'as enregistré en stéréo.",
                "Recadre-le dans SAMPLE EDIT (START POINT / END POINT) pour qu'il boucle proprement.",
                "S'il sature, mets PLAY LEVEL sur « Normal ».",
            ),
            successCriteria = "Le groove figé joue sur une seule part, sans saturation, et libère les autres parts pour de nouveaux sons.",
            styleHint = "Hardtek et tribecore : figer kick + basse en un seul sample libère des parts et des voix pour les percussions et les effets.",
        ),
        commonMistakes = listOf(
            "Éteindre sans exporter : le nouveau sample est perdu à l'extinction, même après Write (p. 36).",
            "Laisser saturer un sample rééchantillonné : son PLAY LEVEL est rehaussé automatiquement ; mets-le sur « Normal » (p. 36).",
            "En stéréo, lire la durée disponible telle quelle : elle est donnée pour du mono, divise-la par deux (p. 35).",
            "Échantillonner Audio In avec l'indicateur CLIP allumé : baisse Input Level ou le niveau de la source (p. 35).",
        ),
        quiz = listOf(
            q("$M-l6-q1", "Quelle combinaison ouvre le mode d'échantillonnage ?",
                listOf("Shift + Write", "Shift + Rec", "Shift + Tap"), 1, "Maintenir Shift et appuyer sur le bouton d'enregistrement.", REECHANTILLONNAGE),
            q("$M-l6-q2", "Quelle source choisir pour enregistrer le son de la machine elle-même ?",
                listOf("Audio In", "Resampling", "USB"), 1, "On choisit « Resampling » avec la commande de valeur.", REECHANTILLONNAGE),
            q("$M-l6-q3", "Un sample rééchantillonné sature. Que faire ?",
                listOf("Régler PLAY LEVEL sur « Normal »", "Changer de pattern", "Baisser le tempo"), 0,
                "Le niveau est rehaussé (+12 dB) ; en cas de distorsion, réglez le niveau de lecture sur Normal.", REECHANTILLONNAGE),
            q("$M-l6-q4", "Combien de samples utilisateur la machine peut-elle stocker ?",
                listOf("Jusqu'à 250", "Jusqu'à 499, pour environ 270 secondes au total (mono)", "Sans limite"), 1,
                "Jusqu'à 499 échantillons utilisateur, pour une durée totale d'environ 270 secondes en mono.", SAMPLER),
            q("$M-l6-q5", "Combien de voix au maximum une part peut-elle jouer en même temps ?",
                listOf("1", "Jusqu'à 4", "24"), 1, "Jusqu'à 4 voix par partie, 24 voix maximum pour un pattern entier.", SPECS),
        ),
    )

    val lessons: List<Lesson> = listOf(lesson1, lesson2, lesson3, lesson4, lesson5, lesson6)
}
