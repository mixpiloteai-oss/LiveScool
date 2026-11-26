package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.ManualDoc.PARAMETER_GUIDE
import com.livemachine.academy.data.model.ManualRef
import com.livemachine.academy.data.model.MusicStyle
import com.livemachine.academy.data.model.StyleChallenge

/**
 * Défis par style. Chaque défi n'utilise que des fonctions vues dans les leçons,
 * avec leurs pages dans le manuel ou le guide. Les tempos sont laissés au choix :
 * les documents Korg ne parlent pas de styles musicaux.
 */
object ChallengeContent {

    private val OM_PAS_A_PAS = ManualRef("Enregistrement de données de jeu › 2. Edition pas à pas", "30")
    private val OM_IFX = ManualRef("Description par section › Section d'édition › Insert Fx", "23–24")
    private val OM_FILTER = ManualRef("Description par section › Section d'édition › Filter", "23")
    private val OM_MOTION = ManualRef("Enregistrement de données de jeu › 3. Motion Seq", "30–31")
    private val OM_MUTE = ManualRef("Edition d'un motif › 1. Etouffement d'une partie", "32")
    private val OM_STEP_JUMP = ManualRef("Description par section › Section du mode de pad › Step Jump", "24")
    private val OM_MODULATION = ManualRef("Description par section › Section d'édition › Modulation", "23")
    private val PG_IFX_LIST = ManualRef("9. Appendix › IFX Type List", "17", PARAMETER_GUIDE)
    private val PG_PATTERN = ManualRef("1. PATTERN PARAMETERS", "3", PARAMETER_GUIDE)
    private val PG_PART = ManualRef("2. PART PARAMETERS", "4", PARAMETER_GUIDE)
    private val PG_STEP_EDIT = ManualRef("3. STEP EDIT", "5", PARAMETER_GUIDE)
    private val PG_SHORTCUTS = ManualRef("9. Appendix › Shortcut list", "11", PARAMETER_GUIDE)
    private val PG_MOD_LIST = ManualRef("9. Appendix › Modulation Type List", "15–16", PARAMETER_GUIDE)
    private val PG_MFX_LIST = ManualRef("9. Appendix › MFX Type List", "17", PARAMETER_GUIDE)

    val challenges: List<StyleChallenge> = listOf(
        StyleChallenge(
            id = "c-hardtek-1",
            style = MusicStyle.HARDTEK,
            title = "Kick saturé, basse qui roule",
            requiredModuleId = "m5",
            goal = "Un groove hardtek de base : kick saturé sur les temps, basse en doubles croches entre les kicks.",
            steps = listOf(
                "Kick sur les pas 1, 5, 9, 13 en mode Sequencer.",
                "Effet d'insertion Distortion ou Overdrive sur le kick, IFX On allumé, dosé avec Edit.",
                "Basse sur les pas entre les kicks, notes courtes (GATE TIME bas dans STEP EDIT).",
                "HPF sur les charleys pour laisser le bas au kick et à la basse.",
            ),
            successCriteria = "Le kick reste lisible malgré la saturation et la basse ne masque jamais le kick.",
            manualRefs = listOf(OM_PAS_A_PAS, OM_IFX, PG_IFX_LIST, PG_STEP_EDIT),
        ),
        StyleChallenge(
            id = "c-hardtek-2",
            style = MusicStyle.HARDTEK,
            title = "Le drop aux mutes",
            requiredModuleId = "m8",
            goal = "Faire monter la tension puis tout relâcher d'un geste.",
            steps = listOf(
                "Lance ton pattern hardtek, Part Mute allumé.",
                "Coupe le kick et la basse pendant 8 tours, garde charleys et percussions.",
                "Au début d'une phrase, rétablis tout d'un coup avec Shift + Part Mute.",
            ),
            successCriteria = "Le retour du kick tombe pile au début de la phrase.",
            manualRefs = listOf(OM_MUTE, PG_SHORTCUTS),
        ),
        StyleChallenge(
            id = "c-tribe-1",
            style = MusicStyle.TRIBE,
            title = "Transe polyrythmique",
            requiredModuleId = "m4",
            goal = "Des percussions qui se décalent tout seules autour d'un kick droit.",
            steps = listOf(
                "Kick droit sur 16 pas.",
                "Deux percussions, l'une en LAST STEP 12, l'autre en LAST STEP 10.",
                "Un GROOVE TYPE sur chaque percussion, avec un GROOVE DEPTH modéré.",
            ),
            successCriteria = "Le motif évolue sur au moins 8 tours sans devenir confus.",
            manualRefs = listOf(PG_PART, PG_SHORTCUTS),
        ),
        StyleChallenge(
            id = "c-tribe-2",
            style = MusicStyle.TRIBE,
            title = "Percussions qui respirent",
            requiredModuleId = "m6",
            goal = "Faire vivre les percussions avec la modulation et la vélocité.",
            steps = listOf(
                "Sur une percussion, un type de modulation « … Pan » avec Speed lente.",
                "Sur une autre, TRG. PAD VELOCITY sur On et des accents joués en temps réel.",
                "Sur la basse, un type « … Filter » à faible Depth.",
            ),
            successCriteria = "Le groove bouge en continu sans que tu touches à rien après l'enregistrement.",
            manualRefs = listOf(OM_MODULATION, PG_MOD_LIST, PG_PART),
        ),
        StyleChallenge(
            id = "c-acid-1",
            style = MusicStyle.ACIDCORE,
            title = "Ligne acide automatique",
            requiredModuleId = "m6",
            goal = "Une ligne acide dont le filtre évolue tout seul.",
            steps = listOf(
                "Basse SAW ou PULSE, VOICE ASSIGN Mono1, quelques pas liés par TIE.",
                "LPF allumé, coupure basse, Resonance haute, EG Int marqué.",
                "MOTION SEQ sur Smooth : enregistre une ouverture puis une fermeture de Filter sur un tour.",
            ),
            successCriteria = "La ligne évolue à chaque tour et les pas liés glissent sans réouverture du filtre.",
            manualRefs = listOf(OM_FILTER, OM_MOTION, PG_PART, PG_STEP_EDIT),
        ),
        StyleChallenge(
            id = "c-acid-2",
            style = MusicStyle.ACIDCORE,
            title = "Acid qui change de tonalité",
            requiredModuleId = "m4",
            goal = "Transposer la ligne acide en direct sans fausse note.",
            steps = listOf(
                "SCALE MODE sur On avant d'enregistrer la basse.",
                "Enregistre la ligne en mode Keyboard dans une SCALE choisie.",
                "Pendant la lecture, change KEY (Shift + pad 4) tous les 4 tours.",
            ),
            successCriteria = "La ligne suit chaque changement de tonique sans sortir de la gamme.",
            manualRefs = listOf(PG_PATTERN, PG_PART, PG_SHORTCUTS),
        ),
        StyleChallenge(
            id = "c-mental-1",
            style = MusicStyle.MENTALCORE,
            title = "Montée de 4 mesures",
            requiredModuleId = "m6",
            goal = "Un pattern de montée long qui fait grimper la tension tout seul.",
            steps = listOf(
                "Copie de ton pattern principal en LENGTH 4.",
                "Motion en Smooth : la coupure s'ouvre sur les 4 mesures.",
                "Modulation « … Filter » dont tu enregistres la Depth qui monte.",
            ),
            successCriteria = "La tension monte sans interruption jusqu'au retour du pattern principal.",
            manualRefs = listOf(PG_PATTERN, OM_MOTION, OM_MODULATION),
        ),
        StyleChallenge(
            id = "c-mental-2",
            style = MusicStyle.MENTALCORE,
            title = "Rupture de séquence",
            requiredModuleId = "m8",
            goal = "Casser la boucle avec le Step Jump et un effet maître de séquence.",
            steps = listOf(
                "Step Jump allumé : fais bégayer les derniers pas d'une phrase.",
                "Effet maître Seq Reverse ou Odd Stepper sur tout le pattern pendant 2 tours.",
                "Reviens à la lecture normale au début de la phrase suivante.",
            ),
            successCriteria = "La rupture dure le temps voulu et la reprise tombe juste.",
            manualRefs = listOf(OM_STEP_JUMP, PG_MFX_LIST),
        ),
    )
}
