package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.ContentStatus
import com.livemachine.academy.data.model.Exercise
import com.livemachine.academy.data.model.Lesson
import com.livemachine.academy.data.model.MachineControl
import com.livemachine.academy.data.model.ManualDoc.PARAMETER_GUIDE
import com.livemachine.academy.data.model.ManualRef
import com.livemachine.academy.data.model.QuizQuestion

/**
 * Module 6 — Motion Sequence et modulation.
 *
 * Sources : manuel d'utilisation (partie française) et electribe sampler Parameter Guide.
 * La Modulation Type List indique pour chaque type les colonnes « BPM Sync » et « Key Sync »
 * sans les définir : les leçons le signalent et font comparer à l'oreille.
 */
object Module6Content {

    private const val M = "m6"

    // Manuel d'utilisation
    private val OM_MODULATION = ManualRef("Description par section › Section d'édition › Modulation", "23")
    private val OM_MOTION = ManualRef("Enregistrement de données de jeu › 3. Enregistrer les manipulations (‘Motion Seq’)", "30–31")
    private val OM_DEPANNAGE = ManualRef("Appendice › Guide de dépannage et messages d'erreur", "37")
    private val OM_EFFETS_MAITRES = ManualRef("Production de musique › 4. Utilisation des effets maîtres", "29")

    // Guide des paramètres
    private val PG_MOD_LIST = ManualRef("9. Appendix › Modulation Type List", "15–16", PARAMETER_GUIDE)
    private val PG_SHORTCUTS = ManualRef("9. Appendix › Shortcut list", "11", PARAMETER_GUIDE)
    private val PG_PART = ManualRef("2. PART PARAMETERS › MOTION SEQ", "4", PARAMETER_GUIDE)
    private val PG_PATTERN = ManualRef("1. PATTERN PARAMETERS › Clear MFX Motion", "3", PARAMETER_GUIDE)
    private val PG_STEP_EDIT = ManualRef("3. STEP EDIT", "5", PARAMETER_GUIDE)
    private val PG_PART_UTILITY = ManualRef("4. PART UTILITY", "5", PARAMETER_GUIDE)
    private val PG_MFX_LIST = ManualRef("9. Appendix › MFX Type List", "17", PARAMETER_GUIDE)

    private fun q(id: String, prompt: String, options: List<String>, correct: Int, explanation: String, ref: ManualRef) =
        QuizQuestion(id, prompt, options, correct, explanation, ref)

    // ---------------------------------------------------------------------
    // 6.1 La section Modulation
    // ---------------------------------------------------------------------
    private val lesson1 = Lesson(
        id = "$M-l1",
        moduleId = M,
        order = 1,
        title = "La section Modulation",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_MODULATION, PG_MOD_LIST),
        durationMinutes = 15,
        objective = "Faire bouger un paramètre automatiquement avec la section Modulation : choisir le type, régler l'intensité et la vitesse.",
        explanation = "La section Modulation produit des changements périodiques du son, avec un LFO ou une enveloppe. Trois " +
            "commandes : Modulation choisit le type, Depth règle l'intensité, Speed la vitesse. Chacun des 72 types combine " +
            "une source et une destination, et son nom les indique : par exemple « LFOTri Filter » est un LFO triangle qui " +
            "agit sur la coupure du filtre. Les destinations possibles sont six : Filter (coupure), Pitch (hauteur), OSC " +
            "(commande Edit de l'oscillateur), Level (volume), Pan et IFX (commande Edit de l'effet d'insertion).",
        controls = listOf(
            MachineControl("Modulation", "Type de modulation (source + destination), 72 choix.", OM_MODULATION),
            MachineControl("Depth", "Intensité de la modulation.", OM_MODULATION),
            MachineControl("Speed", "Vitesse de la modulation.", OM_MODULATION),
        ),
        steps = listOf(
            "Sélectionne ta basse acide du Module 5 et lance la lecture.",
            "Tourne Modulation jusqu'à « LFOTri Filter ».",
            "Monte Depth : la coupure du filtre se met à monter et descendre toute seule.",
            "Tourne Speed : le mouvement accélère ou ralentit.",
            "Essaie « LFOTri Pan » sur un charley : le son se balade dans l'image stéréo.",
            "Garde le réglage qui te plaît et sauvegarde avec Write.",
        ),
        expectedResult = "Le filtre de ta basse bouge en cycle sans que tu touches à rien, à la vitesse et avec l'amplitude choisies.",
        exercise = Exercise(
            id = "$M-l1-ex",
            lessonId = "$M-l1",
            title = "Une destination par part",
            instructions = listOf(
                "Basse : un type « … Filter ».",
                "Charley : un type « … Pan ».",
                "Percussion : un type « … Pitch » avec peu de Depth.",
                "Règle Speed et Depth pour que les trois mouvements restent musicaux ensemble.",
            ),
            successCriteria = "Trois parts modulées vers trois destinations différentes, sans que le groove devienne confus.",
            styleHint = "Tribe : des mouvements lents et continus sur le filtre et le panoramique entretiennent la transe.",
        ),
        commonMistakes = listOf(
            "Choisir un type et laisser Depth à zéro : aucun mouvement audible.",
            "Moduler la coupure d'un filtre non activé : allume d'abord LPF, HPF ou BPF (Module 5).",
            "Confondre la destination « OSC » avec la hauteur : « OSC » vise la commande Edit de l'oscillateur, « Pitch » vise la hauteur (guide p. 15).",
        ),
        quiz = listOf(
            q("$M-l1-q1", "Que règle la commande Depth de la section Modulation ?",
                listOf("La vitesse", "L'intensité", "Le type"), 1, "Depth règle l'intensité de modulation.", OM_MODULATION),
            q("$M-l1-q2", "Combien de types de modulation la liste compte-t-elle ?",
                listOf("32", "38", "72"), 2, "La Modulation Type List va de 1 EG+ Filter à 72 Random IFX.", PG_MOD_LIST),
            q("$M-l1-q3", "Que fait le type « LFOTri Filter » ?",
                listOf("Un LFO triangle agit sur la coupure du filtre", "Une enveloppe agit sur la hauteur", "Un LFO agit sur le panoramique"), 0,
                "Source : LFO (triangle) ; destination : Filter Cutoff.", PG_MOD_LIST),
            q("$M-l1-q4", "Quelle destination vise un type dont le nom finit par « OSC » ?",
                listOf("La hauteur", "La commande Edit de l'oscillateur", "Le volume"), 1, "Destination : Oscillator Edit.", PG_MOD_LIST),
        ),
    )

    // ---------------------------------------------------------------------
    // 6.2 Sources de modulation
    // ---------------------------------------------------------------------
    private val lesson2 = Lesson(
        id = "$M-l2",
        moduleId = M,
        order = 2,
        title = "Les sources de modulation",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(PG_MOD_LIST, PG_SHORTCUTS),
        durationMinutes = 15,
        objective = "Choisir la bonne source (enveloppe, LFO, aléatoire) pour une destination donnée, et changer de forme sans perdre la destination.",
        explanation = "Les sources de la liste : une enveloppe AD positive (types « EG+ ») ou négative (« EG- ») ; des LFO " +
            "triangle (« LFOTri », « LFOTriB »), dent de scie montante (« SawUpB ») ou descendante (« SawDwnB »), carré " +
            "montant (« SquUpB ») ou descendant (« SquDwnB ») ; un LFO sample & hold (« S&HBPM ») et un LFO aléatoire " +
            "(« Random »). Pour chaque type, la liste indique aussi deux propriétés, « BPM Sync » et « Key Sync » : par " +
            "exemple « LFOTriB » les a toutes les deux, « LFOTri » aucune, « EG+ BPM » seulement BPM Sync. Le guide ne " +
            "les détaille pas : compare à l'oreille, surtout en changeant le tempo. Raccourci très utile : Shift + " +
            "Modulation change la forme d'onde en gardant la même destination.",
        controls = listOf(
            MachineControl("Modulation", "Parcourt les 72 types.", PG_MOD_LIST),
            MachineControl("Shift + Modulation", "Change la forme d'onde en gardant la destination.", PG_SHORTCUTS),
        ),
        steps = listOf(
            "Sur ta basse, choisis « EG+ Filter » et monte Depth : le filtre s'ouvre à chaque note.",
            "Passe à « EG- Filter » : le mouvement va dans l'autre sens.",
            "Choisis « LFOTri Filter », puis maintiens Shift et tourne Modulation : la forme change, la destination reste Filter.",
            "Arrête-toi sur « SquUpB Filter », puis sur « S&HBPM Filter » et « Random Filter » : écoute les différences.",
            "Compare « LFOTri Filter » et « LFOTriB Filter » en changeant le BPM : note ce qui change.",
            "Garde ton préféré et sauvegarde avec Write.",
        ),
        expectedResult = "Tu reconnais à l'oreille enveloppe, LFO régulier, sample & hold et aléatoire sur une même destination.",
        exercise = Exercise(
            id = "$M-l2-ex",
            lessonId = "$M-l2",
            title = "Même destination, cinq formes",
            instructions = listOf(
                "Sur la destination Filter, essaie cinq sources avec Shift + Modulation sans changer Depth ni Speed.",
                "Pour chacune, écris un mot qui décrit le mouvement (respire, hache, gargouille…).",
                "Change le tempo de 20 BPM et note quelles sources suivent le tempo.",
            ),
            successCriteria = "Cinq sources décrites, et une liste de celles qui suivent le changement de tempo.",
            styleHint = "Mentalcore : le sample & hold sur le filtre donne des bouillonnements nerveux ; le random, des textures plus imprévisibles.",
        ),
        commonMistakes = listOf(
            "Tourner Modulation sans Shift en cherchant une autre forme : tu changes aussi de destination (guide p. 11).",
            "Croire que tous les types suivent le tempo : la liste indique BPM Sync seulement pour certains (guide p. 15–16).",
            "Confondre EG+ et EG- : ce sont deux enveloppes de sens opposés (positive et négative) (guide p. 15).",
        ),
        quiz = listOf(
            q("$M-l2-q1", "Que fait Shift + Modulation ?",
                listOf("Il remet la modulation à zéro", "Il change la forme d'onde en gardant la destination", "Il affiche le BPM"), 1,
                "Shift + Modulation change la forme d'onde en conservant la destination.", PG_SHORTCUTS),
            q("$M-l2-q2", "Quelle source utilisent les types « EG- » ?",
                listOf("Une enveloppe AD négative", "Un LFO carré", "Un LFO aléatoire"), 0, "EG- : AD Envelope (negative).", PG_MOD_LIST),
            q("$M-l2-q3", "Quelles propriétés la liste indique-t-elle pour « LFOTriB Filter » ?",
                listOf("Aucune", "BPM Sync et Key Sync", "Seulement Key Sync"), 1, "LFOTriB est marqué BPM Sync et Key Sync.", PG_MOD_LIST),
            q("$M-l2-q4", "Quelle source utilisent les types « S&HBPM » ?",
                listOf("Un LFO sample & hold", "Une enveloppe positive", "Un LFO triangle"), 0, "S&HBPM : LFO (sample & hold).", PG_MOD_LIST),
        ),
    )

    // ---------------------------------------------------------------------
    // 6.3 Enregistrer une motion sequence
    // ---------------------------------------------------------------------
    private val lesson3 = Lesson(
        id = "$M-l3",
        moduleId = M,
        order = 3,
        title = "Enregistrer une motion sequence",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_MOTION, PG_PART, PG_SHORTCUTS),
        durationMinutes = 15,
        objective = "Enregistrer tes mouvements de commandes sur une part pour qu'ils rejouent à chaque tour.",
        explanation = "La motion sequence enregistre les mouvements de commandes et de boutons d'une part, puis les rejoue. On " +
            "peut enregistrer : OSC Edit et Pitch ; Filter Cutoff, Resonance, EG Int ; Modulation Speed et Depth ; Amp/EG " +
            "Level, Pan, Attack, Decay/Release ; IFX Edit, IFX ON, MFX Send ; Master Fx On, Master Fx X et Master Fx Y. Le " +
            "paramètre de part MOTION SEQ (Shift + pad 10) choisit le mode : « Off » désactive la motion enregistrée, « Smooth » " +
            "donne des mouvements fluides, « Trigger Hold » tient la valeur à partir du moment où la part joue. Pour " +
            "enregistrer : Rec allumé, puis Play/Pause ; Rec clignote pendant l'enregistrement de la motion. Après un tour " +
            "complet du pattern, l'enregistrement s'arrête seul et la motion est rejouée. Pour continuer, rappuie sur Rec.",
        controls = listOf(
            MachineControl("MOTION SEQ (Shift + pad 10)", "Off, Smooth ou Trigger Hold, par part.", PG_PART),
            MachineControl("Rec puis Play/Pause", "Lance l'enregistrement de la motion ; Rec clignote.", OM_MOTION),
            MachineControl("Commandes enregistrables", "Edit, Pitch, Filter, Resonance, EG Int, Speed, Depth, Level, Pan, Attack, Decay/Release, IFX…", OM_MOTION),
        ),
        steps = listOf(
            "Pattern arrêté, sélectionne ta basse acide.",
            "Shift + pad 10 : règle MOTION SEQ sur « Smooth ».",
            "Appuie sur Rec (il s'allume), puis sur Play/Pause.",
            "Pendant que le pattern joue, tourne lentement Filter du bas vers le haut sur tout le tour. Rec clignote.",
            "Après un tour, l'enregistrement s'arrête : écoute ton mouvement rejoué automatiquement.",
            "Passe MOTION SEQ sur « Trigger Hold » et écoute la différence, puis choisis ton mode et sauvegarde avec Write.",
        ),
        expectedResult = "La coupure du filtre refait ton mouvement à chaque tour du pattern, sans que tu touches la commande.",
        exercise = Exercise(
            id = "$M-l3-ex",
            lessonId = "$M-l3",
            title = "Le filtre qui respire",
            instructions = listOf(
                "Enregistre en Smooth une ouverture puis une fermeture du filtre sur un tour.",
                "Sur une autre part, enregistre en Trigger Hold des positions de Pitch différentes selon les notes.",
                "Compare les deux modes et note quand utiliser chacun.",
            ),
            successCriteria = "Deux parts avec motion sequence, une en Smooth et une en Trigger Hold, et une règle personnelle pour choisir.",
            styleHint = "Acidcore : la motion du filtre en Smooth sur la basse, c'est la ligne acide qui évolue toute seule pendant que tu joues autre chose.",
        ),
        commonMistakes = listOf(
            "Laisser MOTION SEQ sur « Off » : la motion enregistrée est désactivée (guide p. 4).",
            "Continuer à tourner après un tour en croyant enregistrer : l'enregistrement s'arrête seul après un cycle ; rappuie sur Rec (manuel p. 31).",
            "Ne plus entendre les pads réagir comme prévu : vérifie si une motion sequence est active sur la part (manuel p. 37).",
        ),
        quiz = listOf(
            q("$M-l3-q1", "Que fait le mode MOTION SEQ « Trigger Hold » ?",
                listOf("Il désactive la motion", "Il tient les valeurs à partir du moment où la part joue", "Il lisse les mouvements"), 1,
                "Trigger Hold : les valeurs sont tenues à partir du moment où la part est jouée.", PG_PART),
            q("$M-l3-q2", "Que se passe-t-il après un tour complet du pattern pendant l'enregistrement d'une motion ?",
                listOf("Il continue sans fin", "Il s'arrête et la motion enregistrée est rejouée", "Le pattern s'arrête"), 1,
                "Après un cycle, l'enregistrement s'arrête et la lecture de la motion commence.", OM_MOTION),
            q("$M-l3-q3", "Laquelle de ces commandes peut être enregistrée en motion sequence ?",
                listOf("Tap", "Filter Cutoff", "Write"), 1, "Filter Cutoff, Resonance et EG Int font partie de la liste.", OM_MOTION),
            q("$M-l3-q4", "Quel raccourci affiche la page MOTION SEQ ?",
                listOf("Shift + pad 10", "Shift + pad 15", "Shift + Rec"), 0, "Shift + pad 10 affiche la page MOTION SEQ.", PG_SHORTCUTS),
        ),
    )

    // ---------------------------------------------------------------------
    // 6.4 Compléter, effacer et éditer une motion
    // ---------------------------------------------------------------------
    private val lesson4 = Lesson(
        id = "$M-l4",
        moduleId = M,
        order = 4,
        title = "Compléter, effacer et éditer une motion",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_MOTION, PG_PART_UTILITY, PG_STEP_EDIT, PG_SHORTCUTS, OM_DEPANNAGE),
        durationMinutes = 15,
        objective = "Ajouter une commande à une motion existante, corriger une valeur pas par pas, et effacer une motion ratée.",
        explanation = "Pour ajouter des mouvements, sélectionne la part et relance l'enregistrement de la même façon (Rec puis " +
            "Play/Pause). Pour effacer toute la motion d'une part : CLEAR MOTION dans PART UTILITY (Shift + pad 15). Dans " +
            "STEP EDIT, la motion fait partie des données de chaque pas ; le guide détaille par exemple OSC EDIT MOTION " +
            "(Off ou 000 à 127), la valeur de la commande Edit de l'oscillateur enregistrée sur ce pas. Limites : un pattern " +
            "peut contenir jusqu'à 24 motion sequences (message « Motion Seq Full » au-delà) ; COPY PART copie aussi la " +
            "motion, sauf si la copie ferait dépasser 24 ; COPY PART SOUND ne la copie pas.",
        controls = listOf(
            MachineControl("Rec puis Play/Pause", "Ajoute des mouvements à la motion de la part.", OM_MOTION),
            MachineControl("CLEAR MOTION (Shift + pad 15)", "Efface la motion de la part sélectionnée.", PG_SHORTCUTS),
            MachineControl("OSC EDIT MOTION (STEP EDIT)", "Valeur d'Edit enregistrée sur un pas, Off ou 000 à 127.", PG_STEP_EDIT),
        ),
        steps = listOf(
            "Sur ta basse qui a déjà une motion de Filter, appuie sur Rec puis Play/Pause.",
            "Pendant ce tour, tourne Resonance : la motion de Filter est conservée, la résonance s'ajoute.",
            "Ouvre STEP EDIT (Shift + pad 12), choisis un pas et regarde OSC EDIT MOTION ; essaie de changer sa valeur.",
            "Sur une autre part, enregistre volontairement une motion ratée.",
            "Shift + pad 15 : CLEAR MOTION, et exécute-le sur cette part.",
            "Sauvegarde avec Write.",
        ),
        expectedResult = "Ta basse a une motion de filtre et de résonance, une valeur corrigée pas par pas, et l'autre part est revenue sans motion.",
        exercise = Exercise(
            id = "$M-l4-ex",
            lessonId = "$M-l4",
            title = "Motion en couches",
            instructions = listOf(
                "Sur une part, enregistre trois passages : Filter, puis Resonance, puis Decay/Release.",
                "Corrige dans STEP EDIT une valeur de motion qui ne te plaît pas.",
                "Si une couche est ratée, efface tout avec CLEAR MOTION et recommence plus vite.",
            ),
            successCriteria = "Une part avec trois commandes animées qui sonnent ensemble, enregistrées en trois passages.",
            styleHint = "Hardtek : une motion sur Decay/Release du kick ou de la basse fait varier le punch au fil du pattern.",
        ),
        commonMistakes = listOf(
            "Utiliser CLEAR MOTION en voulant effacer les notes : il n'efface que la motion ; pour les notes c'est CLEAR SEQUENCE (guide p. 5).",
            "Atteindre « Motion Seq Full » : 24 motion sequences au maximum, efface celles qui ne servent pas (manuel p. 37).",
            "Copier une part avec COPY PART SOUND en espérant garder sa motion : seul le son est copié (guide p. 5).",
        ),
        quiz = listOf(
            q("$M-l4-q1", "Combien de motion sequences l'instrument peut-il enregistrer au maximum ?",
                listOf("16", "24", "64"), 1, "Jusqu'à 24 ; au-delà, le message « Motion Seq Full » apparaît.", OM_DEPANNAGE),
            q("$M-l4-q2", "Quelle fonction efface la motion de la part sélectionnée ?",
                listOf("CLEAR SEQUENCE", "CLEAR MOTION", "DELETE SAMPLE"), 1, "CLEAR MOTION supprime les données de motion de la part.", PG_PART_UTILITY),
            q("$M-l4-q3", "COPY PART copie-t-il la motion sequence ?",
                listOf("Oui, sauf si cela ferait dépasser 24 motion sequences", "Jamais", "Seulement en Smooth"), 0,
                "COPY PART copie son et séquence, motion comprise, sauf au-delà de 24 motion sequences.", PG_PART_UTILITY),
            q("$M-l4-q4", "Quelle est la plage de OSC EDIT MOTION ?",
                listOf("Off ou 000 à 127", "-63 à +63", "1 à 16"), 0, "OSC EDIT MOTION : Off, 000 à 127.", PG_STEP_EDIT),
        ),
    )

    // ---------------------------------------------------------------------
    // 6.5 La motion de l'effet maître
    // ---------------------------------------------------------------------
    private val lesson5 = Lesson(
        id = "$M-l5",
        moduleId = M,
        order = 5,
        title = "La motion de l'effet maître",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_MOTION, PG_PART, PG_PATTERN, PG_MFX_LIST, OM_EFFETS_MAITRES),
        durationMinutes = 10,
        objective = "Enregistrer tes gestes sur le pad tactile pour que l'effet maître se rejoue tout seul.",
        explanation = "La motion sequence enregistre aussi l'effet maître : Master Fx On, et les positions X et Y du pad " +
            "tactile. La motion de l'effet maître utilise toujours le mode Smooth. On l'enregistre comme une motion de part " +
            "(Rec puis Play/Pause), en jouant le pad tactile avec Master Fx allumé. Pour l'effacer sans toucher aux motions " +
            "des parts : CLEAR MFX MOTION (Shift + pad 3). Rappel : avec Seq Reverse, Seq Doubler, Odd Stepper et Even " +
            "Stepper, la motion sequence n'est pas disponible.",
        controls = listOf(
            MachineControl("Master Fx + pad tactile", "Gestes enregistrables (On, X, Y).", OM_MOTION),
            MachineControl("Rec puis Play/Pause", "Lance l'enregistrement.", OM_MOTION),
            MachineControl("CLEAR MFX MOTION (Shift + pad 3)", "Efface seulement la motion de l'effet maître.", PG_PATTERN),
        ),
        steps = listOf(
            "Choisis un effet maître qui n'est pas un effet de séquence (un délai, par exemple), avec les parts voulues en MFX Send.",
            "Allume Master Fx.",
            "Appuie sur Rec, puis sur Play/Pause.",
            "Pendant le tour, fais glisser ton doigt sur le pad tactile, puis lève-le avant la fin.",
            "Écoute : ton geste est rejoué à chaque tour.",
            "S'il ne te plaît pas, Shift + pad 3 : CLEAR MFX MOTION, puis recommence. Sauvegarde avec Write.",
        ),
        expectedResult = "L'effet maître se déclenche et bouge tout seul au même moment de chaque tour.",
        exercise = Exercise(
            id = "$M-l5-ex",
            lessonId = "$M-l5",
            title = "L'écho de fin de phrase",
            instructions = listOf(
                "Avec un délai en effet maître, enregistre un geste uniquement sur la fin du pattern.",
                "Vérifie que le début du pattern reste sec.",
            ),
            successCriteria = "Un écho automatique sur la fin de chaque tour, le début restant propre.",
            styleHint = "Tribe : un écho qui revient régulièrement en fin de phrase marque les cycles et aide le public à sentir la structure.",
        ),
        commonMistakes = listOf(
            "Enregistrer une motion avec un effet de séquence (Seq Reverse, etc.) : elle n'est pas disponible (guide p. 17).",
            "Utiliser CLEAR MOTION pour effacer l'effet maître : il efface la motion d'une part ; pour l'effet maître, c'est CLEAR MFX MOTION (guide p. 3).",
            "Chercher un mode Trigger Hold pour l'effet maître : sa motion utilise toujours Smooth (guide p. 4).",
        ),
        quiz = listOf(
            q("$M-l5-q1", "Quelles données de l'effet maître la motion sequence enregistre-t-elle ?",
                listOf("Master Fx On, Master Fx X, Master Fx Y", "Seulement le type d'effet", "Le BPM"), 0,
                "Master Fx On, Master Fx X et Master Fx Y font partie des données enregistrables.", OM_MOTION),
            q("$M-l5-q2", "Quel mode utilise la motion de l'effet maître ?",
                listOf("Trigger Hold", "Smooth", "Off"), 1, "Les motion sequences de l'effet maître utilisent Smooth.", PG_PART),
            q("$M-l5-q3", "Comment effacer seulement la motion de l'effet maître ?",
                listOf("CLEAR MFX MOTION", "CLEAR MOTION", "INITIALIZE PATTERN"), 0, "CLEAR MFX MOTION efface la motion de l'effet maître du pattern.", PG_PATTERN),
        ),
    )

    // ---------------------------------------------------------------------
    // 6.6 Atelier : une montée automatisée
    // ---------------------------------------------------------------------
    private val lesson6 = Lesson(
        id = "$M-l6",
        moduleId = M,
        order = 6,
        title = "Atelier : une montée automatisée",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_MOTION, OM_MODULATION, PG_MOD_LIST, PG_PART),
        durationMinutes = 20,
        objective = "Construire un pattern de montée qui fait grimper la tension tout seul, en combinant modulation et motion sequence.",
        explanation = "Une montée efficace combine plusieurs mouvements qui vont dans le même sens. La motion sequence permet " +
            "d'enregistrer un geste unique sur tout le pattern (la coupure qui s'ouvre, la Depth d'une modulation qui monte, " +
            "un niveau qui grimpe). La section Modulation ajoute des mouvements cycliques, et Speed et Depth sont elles-mêmes " +
            "enregistrables en motion. En pratique : un pattern de montée plus long (LENGTH 2 à 4, leçon 4.3) donne plus " +
            "de temps au geste enregistré.",
        controls = listOf(
            MachineControl("Motion sequence (Smooth)", "Le geste d'ensemble de la montée.", PG_PART),
            MachineControl("Modulation, Depth, Speed", "Mouvements cycliques, eux-mêmes enregistrables.", OM_MOTION),
        ),
        steps = listOf(
            "Copie ton pattern principal vers un emplacement libre (Shift + Write, leçon 1.6) : ce sera la montée.",
            "Allonge-le si tu veux (LENGTH 2 ou plus).",
            "Sur la basse : mets un type « … Filter » avec peu de Depth.",
            "Enregistre en Smooth une motion où Filter s'ouvre progressivement et où Depth monte en même temps.",
            "Sur une percussion : enregistre une motion où Level monte du silence au plein volume.",
            "Écoute la montée en entier, ajuste, puis sauvegarde avec Write.",
        ),
        expectedResult = "Un pattern qui part calme et finit tendu, sans aucune intervention de ta part pendant la lecture.",
        exercise = Exercise(
            id = "$M-l6-ex",
            lessonId = "$M-l6",
            title = "La montée mentale",
            instructions = listOf(
                "Construis une montée d'au moins 2 mesures avec au moins trois commandes animées.",
                "Enchaîne-la à la main avec ton pattern principal (changement de pattern pendant la lecture, leçon 1.4).",
                "Le retour au pattern principal doit faire l'effet d'un drop.",
            ),
            successCriteria = "La montée crée une tension nette et le retour au pattern principal fait l'effet d'un drop.",
            styleHint = "Mentalcore : les montées longues et obsédantes sont au cœur du style ; n'hésite pas à aller jusqu'à 4 mesures.",
        ),
        commonMistakes = listOf(
            "Animer trop de commandes en même temps dès le début : une montée doit commencer sobre.",
            "Oublier que la motion rejoue à chaque tour : une montée dans un pattern qui boucle redescend au début du tour suivant ; enchaîne vers le pattern suivant au bon moment.",
            "Dépasser 24 motion sequences dans le pattern (manuel p. 37).",
        ),
        quiz = listOf(
            q("$M-l6-q1", "Les commandes Speed et Depth de la section Modulation peuvent-elles être enregistrées en motion ?",
                listOf("Oui", "Non", "Seulement Depth"), 0, "Modulation Speed et Depth font partie des données enregistrables.", OM_MOTION),
            q("$M-l6-q2", "Quel mode MOTION SEQ donne des mouvements fluides, adaptés à une montée ?",
                listOf("Smooth", "Trigger Hold", "Off"), 0, "Smooth : mouvements fluides, changement progressif du son.", PG_PART),
            q("$M-l6-q3", "Pourquoi utiliser un pattern de montée plus long (LENGTH 2 à 4) ?",
                listOf("Pour avoir plus de voix", "Pour que le geste enregistré dure plus longtemps avant que le pattern boucle", "Pour changer de tempo"), 1,
                "La motion suit le pattern : plus il est long, plus le geste peut durer.", OM_MOTION),
        ),
    )

    val lessons: List<Lesson> = listOf(lesson1, lesson2, lesson3, lesson4, lesson5, lesson6)
}
