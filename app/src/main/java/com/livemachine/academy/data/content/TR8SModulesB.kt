package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** TR-8S — modules 4 à 6 : sons et kits, effets et transitions, tempo et organisation. */
internal object TR8SModulesB {

    // Références du mode d'emploi (pages imprimées = pages du PDF)
    private val SECTION_INST = tr8s("Section de modification INST", "5")
    private val CTRL_SELECT = tr8s("Affectation de paramètres aux boutons [CTRL] (CTRL SELECT)", "15")
    private val KIT = tr8s("Sélection de kits (KIT), KIT Edit", "14")
    private val INST = tr8s("Sélection du son d'un instrument (INST)", "15")
    private val INST_EDIT = tr8s("INST Edit, panoramique, gain, LFO", "16")
    private val MOTION = tr8s("Enregistrement/lecture de MOTION", "11")
    private val MOTION_CLEAR = tr8s("Suppression de MOTION", "19")
    private val REVERB_DELAY = tr8s("Réglage de la réverbération ou du retard", "10")
    private val REVERB_DELAY_DETAIL = tr8s("Paramètres détaillés de réverbération et de retard", "14")
    private val MASTER = tr8s("Effet Master (MASTER FX)", "10, 15")
    private val FILL = tr8s("Sélection d'un motif de transition, Manual Trigger, Auto Fill In", "10")
    private val SCATTER = tr8s("À propos des transitions, Scatter", "9")
    private val PTN_SETTING = tr8s("Modification des réglages du motif (Pattern Setting)", "13")
    private val TEMPO = tr8s("Réglage du tempo, Tap Tempo, Nudge", "20")
    private val COPIE = tr8s("Copie d'un motif, suppression, motif aléatoire", "10")
    private val COPY = tr8s("Copie d'un motif ou d'un kit (COPY)", "19")
    private val SYNC = tr8s("Synchronisation/enregistrement avec d'autres appareils", "21")
    private val SORTIES = tr8s("Face arrière, ASSIGNABLE OUT/TRIGGER OUT", "7")

    // ---------------------------------------------------------------------
    // Module 4 — Sons et kits
    // ---------------------------------------------------------------------
    private const val M4 = "tr8s-m4"

    private val m4l1 = ctLesson(
        M4, 1, "TUNE, DECAY, CTRL et fader", listOf(SECTION_INST, CTRL_SELECT), 15,
        objective = "Sculpter chaque instrument directement sur la façade.",
        explanation = "Chaque instrument a TUNE (accordage), DECAY (longueur), CTRL et un fader de niveau. Le rôle de CTRL se choisit avec " +
            "CTRL SELECT : l'allumer, choisir le paramètre avec VALUE (le même pour tous les CTRL), puis l'éteindre. Pour un rôle différent " +
            "par instrument : CTRL SELECT maintenu + bouton [BD]–[RC], puis VALUE (enregistré dans le réglage « User »). Selon le son, " +
            "CTRL peut n'avoir aucun effet.",
        controls = listOf(
            cmd("TUNE, DECAY", "Accordage et longueur.", SECTION_INST),
            cmd("CTRL, fader", "Paramètre choisi et volume.", SECTION_INST),
            cmd("CTRL SELECT", "Rôle des boutons CTRL.", CTRL_SELECT),
        ),
        steps = listOf(
            "Pendant la lecture, baisse le TUNE du BD et raccourcis son DECAY.",
            "Allume CTRL SELECT, choisis un paramètre, éteins.",
            "Tourne le CTRL du BD et écoute.",
        ),
        expected = "Un kick plus grave et plus court.",
        exerciseTitle = "Kick hardtek",
        exercise = listOf("Règle un kick court et percutant, puis sauvegarde le kit."),
        success = "Le kick claque sans traîner.",
        style = "Hardtek : un kick court et percutant.",
        mistakes = listOf("Oublier de sauvegarder le kit : le réglage est perdu à l'extinction."),
        quiz = listOf(
            qz("DECAY règle…", listOf("La longueur", "La hauteur"), 0, "TUNE règle la hauteur.", SECTION_INST),
            qz("Choisir le rôle des CTRL ?", listOf("CTRL SELECT", "SHIFT + TUNE"), 0, "Puis VALUE.", CTRL_SELECT),
            qz("Rôle différent par instrument ?", listOf("CTRL SELECT maintenu + instrument", "MUTE + CTRL"), 0, "Enregistré dans « User ».", CTRL_SELECT),
            qz("CTRL a-t-il toujours un effet ?", listOf("Pas forcément, selon le son", "Toujours"), 0, "Selon le son.", SECTION_INST),
        ),
    )

    private val m4l2 = ctLesson(
        M4, 2, "Kits, sons d'instruments, pan et gain", listOf(KIT, INST, INST_EDIT), 15,
        objective = "Choisir un kit et changer le son d'un instrument.",
        explanation = "KIT allumé, VALUE choisit le kit, KIT éteint pour sortir. INST : l'instrument choisi s'allume ; VALUE choisit son son " +
            "(SHIFT + VALUE change de catégorie) ; INST pour sortir. SHIFT + INST ouvre INST Edit : Pan (L127–CENTER–R127), Gain (−INF, " +
            "−40,0 dB à +40,0 dB)… SHIFT + KIT ouvre KIT Edit (niveau, réverbération, retard, Master FX, EXT IN, LFO, sorties, mute, " +
            "CTRL). COPY + KIT copie un kit.",
        controls = listOf(
            cmd("KIT + VALUE", "Choisir un kit.", KIT),
            cmd("INST + VALUE (SHIFT = catégorie)", "Choisir un son.", INST),
            cmd("SHIFT + INST", "INST Edit (Pan, Gain…).", INST_EDIT),
            cmd("SHIFT + KIT", "KIT Edit.", KIT),
        ),
        steps = listOf(
            "KIT, choisis un autre kit avec VALUE, KIT.",
            "Sélectionne le BD, INST, choisis un autre kick, INST.",
            "SHIFT + INST > Pan : mets un charley à L30.",
        ),
        expected = "Un kit modifié avec un charley décalé à gauche.",
        exerciseTitle = "Kit maison",
        exercise = listOf("Construis ton kit : change BD, SD et deux percussions, puis sauvegarde-le."),
        success = "Ton kit se rappelle par son numéro.",
        mistakes = listOf("Changer de kit en croyant garder ses sons : chaque kit a ses 11 sons."),
        quiz = listOf(
            qz("Changer de catégorie de sons ?", listOf("SHIFT + VALUE", "KIT + VALUE"), 0, "Dans l'écran INST.", INST),
            qz("Plage du Pan ?", listOf("L127 à R127", "L64 à R64"), 0, "Avec CENTER au milieu.", INST_EDIT),
            qz("Ouvrir INST Edit ?", listOf("SHIFT + INST", "INST seul"), 0, "INST seul choisit le son.", INST_EDIT),
            qz("Copier un kit ?", listOf("COPY + KIT", "WRITE + COPY"), 0, "Puis source, destination, OK.", INST_EDIT),
        ),
    )

    private val m4l3 = ctLesson(
        M4, 3, "LFO et MOTION", listOf(INST_EDIT, MOTION, MOTION_CLEAR), 15,
        objective = "Faire bouger un son automatiquement.",
        explanation = "LFO : dans INST Edit, « LFO », choisir le paramètre à moduler, puis « LFO Depth » (−128 à +127). La forme, la vitesse et " +
            "la synchronisation au tempo du LFO se règlent dans KIT Edit. MOTION : MOTION [REC] allumé enregistre les mouvements de TUNE, " +
            "DECAY et CTRL dans les pas (ainsi que REVERB LEVEL, DELAY et MASTER FX) ; MOTION [ON] les rejoue. Maintenir MOTION [REC] " +
            "enregistre seulement pendant l'appui. MOTION [ON] + CLEAR efface tout le MOTION de la variation.",
        controls = listOf(
            cmd("INST Edit > LFO", "Paramètre modulé et profondeur.", INST_EDIT),
            cmd("MOTION [REC] / [ON]", "Enregistrer / rejouer les mouvements.", MOTION),
            cmd("MOTION [ON] + CLEAR", "Effacer le MOTION.", MOTION_CLEAR),
        ),
        steps = listOf(
            "Lance le motif, allume MOTION [REC], tourne le DECAY du charley sur une mesure.",
            "Éteins MOTION [REC], allume MOTION [ON] : le mouvement est rejoué.",
            "Efface avec MOTION [ON] + CLEAR.",
        ),
        expected = "Le charley s'ouvre et se ferme tout seul.",
        exerciseTitle = "Charley vivant",
        exercise = listOf("Enregistre un mouvement de DECAY sur deux mesures et garde-le."),
        success = "Le motif évolue sans tes mains.",
        style = "Tribe : un charley qui s'ouvre progressivement.",
        mistakes = listOf("Laisser MOTION [REC] allumé : chaque mouvement suivant s'enregistre."),
        quiz = listOf(
            qz("Plage de LFO Depth ?", listOf("−128 à +127", "0 à 100"), 0, "Profondeur de modification.", INST_EDIT),
            qz("Forme et vitesse du LFO ?", listOf("Dans KIT Edit", "Dans INST"), 0, "Waveform, Rate, TempoSync.", INST_EDIT),
            qz("Rejouer le MOTION ?", listOf("MOTION [ON]", "MOTION [REC]"), 0, "[REC] enregistre.", MOTION),
            qz("Effacer tout le MOTION ?", listOf("MOTION [ON] + CLEAR", "SHIFT + CLEAR"), 0, "Pour la variation lue.", MOTION_CLEAR),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 5 — Effets et transitions
    // ---------------------------------------------------------------------
    private const val M5 = "tr8s-m5"

    private val m5l1 = ctLesson(
        M5, 1, "Réverbération et retard", listOf(REVERB_DELAY, REVERB_DELAY_DETAIL), 10,
        objective = "Doser réverbération et retard en direct.",
        explanation = "REVERB [LEVEL] : volume de la réverbération ; KIT + REVERB [LEVEL] : sa durée. DELAY [LEVEL] : volume du retard, " +
            "[TIME] : durée, [FEEDBACK] : répétitions. Les réglages détaillés (type, pré-délai, filtres, synchronisation du retard au " +
            "tempo avec TempoSync) sont dans KIT Edit.",
        controls = listOf(
            cmd("REVERB [LEVEL], KIT + REVERB [LEVEL]", "Volume et durée.", REVERB_DELAY),
            cmd("DELAY [LEVEL] / [TIME] / [FEEDBACK]", "Retard.", REVERB_DELAY),
            cmd("KIT Edit > DELAY > TempoSync", "Retard calé au tempo.", REVERB_DELAY_DETAIL),
        ),
        steps = listOf(
            "Monte DELAY [LEVEL] et règle [TIME].",
            "Augmente [FEEDBACK] pour plus de répétitions.",
            "Ajoute un peu de REVERB [LEVEL] et allonge avec KIT + REVERB [LEVEL].",
        ),
        expected = "Des répétitions et un espace autour du rythme.",
        exerciseTitle = "Écho de transition",
        exercise = listOf("Monte le feedback juste avant un break, redescends au retour."),
        success = "L'écho fait la transition.",
        style = "Tribe : l'écho sur la dernière frappe sert de transition.",
        mistakes = listOf("Feedback trop haut trop longtemps : le retard sature le mix."),
        quiz = listOf(
            qz("Durée de la réverbération ?", listOf("KIT + REVERB [LEVEL]", "DELAY [TIME]"), 0, "REVERB [LEVEL] seul = volume.", REVERB_DELAY),
            qz("Nombre de répétitions du retard ?", listOf("DELAY [FEEDBACK]", "DELAY [LEVEL]"), 0, "Son retourné à l'entrée.", REVERB_DELAY),
            qz("Caler le retard sur le tempo ?", listOf("TempoSync dans KIT Edit", "SHUFFLE"), 0, "Paramètre détaillé.", REVERB_DELAY_DETAIL),
            qz("Durée du retard ?", listOf("DELAY [TIME]", "TUNE"), 0, "Longueur du retard.", REVERB_DELAY),
        ),
    )

    private val m5l2 = ctLesson(
        M5, 2, "L'effet Master", listOf(MASTER), 10,
        objective = "Traiter tout le kit avec un effet général.",
        explanation = "MASTER FX [ON] allume l'effet, MASTER FX [CTRL] règle sa profondeur. SHIFT + MASTER FX [ON] ouvre le choix du type " +
            "d'effet (paramètres différents selon le type ; détail dans le Manuel de référence), KIT pour sortir. L'effet Master agit sur " +
            "la sortie MIX OUT.",
        controls = listOf(
            cmd("MASTER FX [ON]", "Activer.", MASTER),
            cmd("MASTER FX [CTRL]", "Profondeur.", MASTER),
            cmd("SHIFT + MASTER FX [ON]", "Type d'effet.", MASTER),
        ),
        steps = listOf(
            "Allume MASTER FX [ON] et tourne [CTRL].",
            "SHIFT + MASTER FX [ON], essaie un autre type, KIT pour sortir.",
            "Joue [CTRL] sur 4 mesures avant un retour.",
        ),
        expected = "Tout le rythme est transformé par l'effet.",
        exerciseTitle = "Montée à l'effet Master",
        exercise = listOf("Monte progressivement [CTRL] sur 8 mesures puis coupe [ON] au retour."),
        success = "Le retour sec fait l'effet d'une relance.",
        style = "Hardtek : effet général monté puis coupé net au retour.",
        mistakes = listOf("Chercher l'effet Master sur ASSIGNABLE OUT : il s'applique à MIX OUT."),
        quiz = listOf(
            qz("Profondeur de l'effet Master ?", listOf("MASTER FX [CTRL]", "VOLUME"), 0, "Après [ON].", MASTER),
            qz("Choisir le type ?", listOf("SHIFT + MASTER FX [ON]", "KIT + CTRL"), 0, "Puis KIT pour sortir.", MASTER),
            qz("Sur quelle sortie agit-il ?", listOf("MIX OUT", "ASSIGNABLE OUT"), 0, "Comme indiqué pour EXT IN.", SYNC),
            qz("Couper l'effet ?", listOf("MASTER FX [ON]", "CLEAR"), 0, "Il s'éteint.", MASTER),
        ),
    )

    private val m5l3 = ctLesson(
        M5, 3, "Transitions et Scatter", listOf(FILL, SCATTER), 15,
        objective = "Insérer des transitions à la main ou automatiquement.",
        explanation = "Chaque motif a deux variations de transition (FILL IN) ; une variation A–H ou l'effet Scatter peuvent aussi servir. " +
            "Choix : AUTO FILL IN [ON] maintenu + pad [14]–[16] ou bouton [A]–[H]. MANUAL TRIG insère une transition au moment voulu. " +
            "AUTO FILL IN [ON] allumé + bouton [AUTO FILL IN] : transition automatique toutes les 32, 16, 12, 8, 4 ou 2 mesures. Scatter " +
            "échange des pas, change le sens ou la durée des notes (à partir du 2e cycle) ; en mode SCATTER, MANUAL TRIG maintenu + VALUE " +
            "règle sa profondeur.",
        controls = listOf(
            cmd("AUTO FILL IN [ON] + pad [14]–[16] / [A]–[H]", "Choisir la transition.", FILL),
            cmd("MANUAL TRIG", "Transition manuelle.", FILL),
            cmd("[AUTO FILL IN]", "Intervalle automatique.", FILL),
        ),
        steps = listOf(
            "Choisis une transition avec AUTO FILL IN [ON] maintenu + pad [14].",
            "Appuie sur MANUAL TRIG avant la fin d'une phrase.",
            "Règle [AUTO FILL IN] sur 8 et allume [ON] : une transition toutes les 8 mesures.",
        ),
        expected = "Des transitions régulières sans y penser.",
        exerciseTitle = "Phrase de 8",
        exercise = listOf("Fais tourner un motif avec une transition automatique toutes les 8 mesures et une manuelle au break."),
        success = "Le morceau respire par phrases.",
        style = "Hardtek / tribe : une relance à la fin de chaque phrase.",
        mistakes = listOf("Attendre le Scatter dès le premier cycle : il agit à partir du deuxième."),
        quiz = listOf(
            qz("Transitions par motif ?", listOf("2", "8"), 0, "Plus variations A–H ou Scatter.", SCATTER),
            qz("Insérer une transition à la main ?", listOf("MANUAL TRIG", "TR-REC"), 0, "Au moment voulu.", FILL),
            qz("Intervalles automatiques ?", listOf("32, 16, 12, 8, 4, 2 mesures", "1 à 64"), 0, "Avec [AUTO FILL IN].", FILL),
            qz("Profondeur du Scatter ?", listOf("MANUAL TRIG maintenu + VALUE", "SHIFT + CTRL"), 0, "Si le type est SCATTER.", FILL),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 6 — Tempo, organisation et synchronisation
    // ---------------------------------------------------------------------
    private const val M6 = "tr8s-m6"

    private val m6l1 = ctLesson(
        M6, 1, "Tempo, shuffle, tap et nudge", listOf(TEMPO, PTN_SETTING), 10,
        objective = "Régler le tempo et le groove de chaque motif.",
        explanation = "TEMPO règle le tempo ; SHIFT + TEMPO par pas de 0,1. Tap : SHIFT + ENTER, trois fois ou plus. SHUFFLE règle le swing. " +
            "Nudge : SHIFT + COPY avance, SHIFT + UTILITY recule la lecture (pour se recaler sur un autre son). MUTE + UTILITY mémorise " +
            "un tempo, MUTE + COPY le rappelle (non sauvegardé). SHIFT + PTN SELECT ouvre PTN SETTING : kit lié, tempo, gamme, shuffle, " +
            "Flam Spacing, Scatter, nom du motif.",
        controls = listOf(
            cmd("TEMPO, SHIFT + TEMPO", "Tempo, pas de 0,1.", TEMPO),
            cmd("SHIFT + ENTER", "Tap tempo.", TEMPO),
            cmd("SHIFT + COPY / UTILITY", "Nudge.", TEMPO),
            cmd("SHIFT + PTN SELECT", "Réglages du motif.", PTN_SETTING),
        ),
        steps = listOf(
            "Règle 165,0 BPM avec TEMPO puis SHIFT + TEMPO.",
            "Tape le tempo d'un morceau avec SHIFT + ENTER.",
            "Mémorise avec MUTE + UTILITY, change, rappelle avec MUTE + COPY.",
            "SHIFT + PTN SELECT : donne un tempo au motif.",
        ),
        expected = "Chaque motif a son tempo et tu sais te recaler.",
        exerciseTitle = "Recalage",
        exercise = listOf("Lance un morceau sur une autre source, cale le tempo au tap et recale avec le nudge."),
        success = "Les deux rythmes restent ensemble.",
        style = "Hardtek / tribe : entre 150 et 180 BPM.",
        mistakes = listOf("Croire que le tempo mémorisé est sauvegardé : il ne l'est pas."),
        quiz = listOf(
            qz("Tap tempo ?", listOf("SHIFT + ENTER, 3 fois ou plus", "TEMPO enfoncé"), 0, "Moyenne des appuis.", TEMPO),
            qz("Avancer la lecture ?", listOf("SHIFT + COPY", "SHIFT + UTILITY"), 0, "UTILITY recule.", TEMPO),
            qz("Réglage fin du tempo ?", listOf("SHIFT + TEMPO (0,1)", "VALUE"), 0, "Par pas de 0,1.", TEMPO),
            qz("Tempo propre à un motif ?", listOf("PTN SETTING", "KIT Edit"), 0, "SHIFT + PTN SELECT.", PTN_SETTING),
        ),
    )

    private val m6l2 = ctLesson(
        M6, 2, "Copier, effacer, motif aléatoire", listOf(COPIE, COPY), 10,
        objective = "Organiser tes motifs et partir d'une idée aléatoire.",
        explanation = "COPY ouvre la copie : Pattern, Variation, Track, Kit ou Inst, puis source, destination, OK. Raccourcis : COPY + PTN " +
            "SELECT (motif), COPY + KIT, COPY + INST, COPY + bouton d'instrument (piste), COPY + [A]–[H] (variation). Supprimer un motif : " +
            "PTN SELECT, puis CLEAR maintenu + pad. Motif aléatoire : PTN SELECT maintenu + SAMPLE, puis TR-REC pour le garder comme motif " +
            "actuel (WRITE pour le sauvegarder).",
        controls = listOf(
            cmd("COPY (+ PTN SELECT / KIT / [A]–[H])", "Copier.", COPY),
            cmd("PTN SELECT, CLEAR + pad", "Supprimer un motif.", COPIE),
            cmd("PTN SELECT + SAMPLE", "Motif aléatoire.", COPIE),
        ),
        steps = listOf(
            "COPY + [A] : copie la variation A vers B.",
            "Modifie B pour une variation.",
            "PTN SELECT + SAMPLE sur un motif vide, TR-REC pour le garder, WRITE.",
        ),
        expected = "Une variation dérivée de A et un motif aléatoire sauvegardé.",
        exerciseTitle = "Idées aléatoires",
        exercise = listOf("Génère trois motifs aléatoires et garde le meilleur après l'avoir retouché."),
        success = "Un nouveau motif utilisable.",
        mistakes = listOf("Générer un motif aléatoire sur un motif que tu voulais garder : il est modifié."),
        quiz = listOf(
            qz("Copier une variation ?", listOf("COPY + [A]–[H]", "SHIFT + [A]"), 0, "Puis destination.", COPY),
            qz("Motif aléatoire ?", listOf("PTN SELECT + SAMPLE", "TR-REC + SAMPLE"), 0, "TR-REC pour le garder.", COPIE),
            qz("Supprimer un motif ?", listOf("PTN SELECT, CLEAR + pad", "DELETE"), 0, "Le pad indique le motif.", COPIE),
            qz("Motif aléatoire sauvegardé automatiquement ?", listOf("Non, il faut WRITE", "Oui"), 0, "Opération WRITE.", COPIE),
        ),
    )

    private val m6l3 = ctLesson(
        M6, 3, "Synchronisation et sorties séparées", listOf(SYNC, SORTIES), 15,
        objective = "Caler la TR-8S sur ton set et envoyer des instruments sur des sorties séparées.",
        explanation = "La TR-8S reçoit l'horloge MIDI pour suivre le tempo, et les messages Start/Stop pour démarrer et s'arrêter (MIDI ou " +
            "USB, pilote Roland nécessaire pour l'ordinateur). ASSIGNABLE OUT/TRIGGER OUT 1 à 6 : en ASSIGNABLE OUT, le son ne sort plus " +
            "par MIX OUT ; en TRIGGER OUT, il sort aussi par MIX OUT. Choix dans UTILITY > ASSIGN OUT 1–6 ; affectation d'un instrument " +
            "dans KIT Edit > OUTPUT (MIX, ASSIGN 1–6 mono, ASSIGN A–C stéréo). EXT IN reçoit une source (sidechain, envois d'effet).",
        controls = listOf(
            cmd("MIDI IN / USB", "Horloge et Start/Stop.", SYNC),
            cmd("UTILITY > ASSIGN OUT 1–6", "Sortie ou déclencheur.", SYNC),
            cmd("KIT Edit > OUTPUT", "Destination de chaque instrument.", SYNC),
        ),
        steps = listOf(
            "Relie la sortie MIDI de ta machine maîtresse à MIDI IN de la TR-8S.",
            "Lance la maîtresse : la TR-8S suit tempo et Start/Stop.",
            "Dans KIT Edit > OUTPUT, envoie le BD sur ASSIGN 1 et relie-le à une voie de ta table.",
        ),
        expected = "La TR-8S suit ton set et le kick a sa propre voie.",
        exerciseTitle = "Kick séparé",
        exercise = listOf("Mixe le kick sur sa voie (égaliseur, compression) pendant que le reste sort par MIX OUT."),
        success = "Le kick est réglé à part du reste.",
        style = "Hardtek : le kick traité seul pour dominer le mix.",
        mistakes = listOf("Chercher le kick dans MIX OUT après l'avoir envoyé en ASSIGNABLE OUT : il n'y est plus."),
        quiz = listOf(
            qz("Instrument en ASSIGNABLE OUT…", listOf("Ne sort plus par MIX OUT", "Sort aussi par MIX OUT"), 0, "TRIGGER OUT sort aussi par MIX OUT.", SORTIES),
            qz("Que reçoit la TR-8S pour se synchroniser ?", listOf("Horloge MIDI et Start/Stop", "Audio"), 0, "F8, FA, FC.", SYNC),
            qz("Affecter un instrument à une sortie ?", listOf("KIT Edit > OUTPUT", "INST"), 0, "MIX, ASSIGN 1–6, A–C.", SYNC),
            qz("Pilote nécessaire pour l'ordinateur ?", listOf("Oui, pilote USB Roland", "Non"), 0, "À installer avant de brancher.", SYNC),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(M4, 4, "Sons et kits", "TUNE, DECAY, CTRL, kits, sons, pan, gain, LFO, MOTION.",
            "Sculpter les instruments.", "Un kick court et des charleys vivants pour la hardtek.", listOf(m4l1, m4l2, m4l3)),
        CourseModule(M5, 5, "Effets et transitions", "Réverbération, retard, effet Master, transitions, Scatter.",
            "Faire vivre le rythme en direct.", "Transitions et effets marquent les fins de phrase.", listOf(m5l1, m5l2, m5l3)),
        CourseModule(M6, 6, "Tempo, organisation et synchro", "Tempo, shuffle, nudge, copie, motif aléatoire, MIDI, sorties.",
            "Intégrer la TR-8S dans ton set.", "Synchronisée et mixée à part, la TR-8S tient le kick du live.", listOf(m6l1, m6l2, m6l3)),
    )
}
