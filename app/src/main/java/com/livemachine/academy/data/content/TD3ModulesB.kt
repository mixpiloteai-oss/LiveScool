package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** TD-3 — modules 4 et 5 : accent, slide et jeu des motifs ; pistes. */
internal object TD3ModulesB {

    // Références du Quick Start Guide (pages imprimées)
    private val ACCENT_SLIDE = td3("Ajout ou suppression d'ACCENT et SLIDE", "65")
    private val ACCENT_SLIDE_EN = td3("Adding or deleting ACCENT and SLIDE", "57–58")
    private val SLIDE_FIN = td3("Slide sur la dernière note", "65")
    private val JOUER = td3("Jouer un motif", "65")
    private val JOUER_EN = td3("Playing a pattern", "58")
    private val GROUPES = td3("TRACK/PATTERN GROUP", "24")
    private val PISTES = td3("Des pistes, insertion de motifs dans une piste", "66–67")
    private val PISTES_EN = td3("Tracks", "58–59")
    private val EDIT_PISTE = td3("Lecture, suppression, insertion, remplacement", "67")

    // ---------------------------------------------------------------------
    // Module 4 — Accent, slide et jeu
    // ---------------------------------------------------------------------
    private const val M4 = "td3-m4"

    private val m4l1 = ctLesson(
        M4, 1, "Ajouter accents et slides", listOf(ACCENT_SLIDE, ACCENT_SLIDE_EN), 15,
        objective = "Placer les accents et les glissements qui font la ligne acid.",
        explanation = "En PATTERN WRITE, PITCH MODE allumé, appuyer sur WRITE/NEXT pour avancer note par note. Sur la note voulue, garder " +
            "WRITE/NEXT enfoncé (ne pas relâcher), puis appuyer sur ACCENT et/ou SLIDE ; relâcher WRITE/NEXT. Si la note les avait déjà, " +
            "ils sont retirés. Si on a dépassé la note : BACK d'abord, puis WRITE/NEXT maintenu. Le bouton ACCENT du synthé ne change que " +
            "les notes accentuées.",
        controls = listOf(
            cmd("WRITE/NEXT maintenu", "Note à modifier.", ACCENT_SLIDE),
            cmd("ACCENT / SLIDE", "Ajouter ou retirer.", ACCENT_SLIDE_EN),
            cmd("BACK", "Revenir à une note dépassée.", ACCENT_SLIDE),
        ),
        steps = listOf(
            "PATTERN WRITE, PITCH MODE, avance avec WRITE/NEXT jusqu'à la note 3.",
            "Garde WRITE/NEXT enfoncé et appuie sur ACCENT, relâche.",
            "Sur la note 6, ajoute un SLIDE de la même façon.",
            "Écoute en mode normal (FUNCTION puis START/STOP).",
        ),
        expected = "La note 3 est accentuée et la note 6 glisse vers la suivante.",
        exerciseTitle = "Ligne accentuée",
        exercise = listOf("Ajoute 3 accents et 2 slides à ta ligne, puis joue ACCENT et CUTOFF pendant la lecture."),
        success = "La ligne « parle » grâce aux accents et glissements.",
        style = "Acidcore : accents et slides, la signature de la ligne acid.",
        mistakes = listOf("Relâcher WRITE/NEXT avant ACCENT : la note avance sans accent."),
        quiz = listOf(
            qz("Comment cibler une note ?", listOf("Garder WRITE/NEXT enfoncé sur elle", "TAP"), 0, "Puis ACCENT/SLIDE.", ACCENT_SLIDE),
            qz("ACCENT sur une note déjà accentuée…", listOf("Retire l'accent", "Double l'accent"), 0, "Bascule.", ACCENT_SLIDE_EN),
            qz("Note dépassée ?", listOf("BACK puis WRITE/NEXT maintenu", "CLEAR"), 0, "Retour à cette note.", ACCENT_SLIDE),
            qz("Le bouton ACCENT du synthé agit sur…", listOf("Les notes accentuées", "Toutes"), 0, "Seulement celles-ci.", ACCENT_SLIDE_EN),
        ),
    )

    private val m4l2 = ctLesson(
        M4, 2, "Jouer, enchaîner et transposer les motifs", listOf(JOUER, JOUER_EN, GROUPES), 15,
        objective = "Lancer les motifs en live et les transposer.",
        explanation = "Pour jouer, utiliser PATTERN PLAY (évite d'effacer un motif par erreur avec CLEAR). Groupe I–IV, FUNCTION (LED NORMAL " +
            "MODE), PATTERN 1–8 (clignote), A ou B, START/STOP. Pendant la lecture, un autre motif choisi démarre à la fin du motif en cours. " +
            "Maintenir un bouton PATTERN et en appuyer un autre sélectionne ces deux motifs et ceux entre eux, joués dans l'ordre. PITCH MODE " +
            "maintenu + une touche transpose le motif au redémarrage.",
        controls = listOf(
            cmd("MODE > PATTERN PLAY", "Jouer sans risque d'effacer.", JOUER),
            cmd("PATTERN 1–8 + A / B", "Choisir.", JOUER_EN),
            cmd("Deux PATTERN ensemble", "Plage de motifs.", JOUER),
            cmd("PITCH MODE + touche", "Transposer.", JOUER),
        ),
        steps = listOf(
            "MODE sur PATTERN PLAY, groupe I, FUNCTION.",
            "PATTERN 1, A, START/STOP.",
            "Pendant la lecture, PATTERN 2 : il démarre à la fin du 1.",
            "Maintiens PITCH MODE et appuie sur une touche pour transposer.",
        ),
        expected = "Tu enchaînes et transposes tes motifs en direct.",
        exerciseTitle = "Progression acid",
        exercise = listOf("Joue 1A et transpose-le sur 4 notes différentes toutes les 4 mesures."),
        success = "La progression se fait sans arrêter la lecture.",
        style = "Acidcore : une seule ligne transposée suffit à faire une progression.",
        mistakes = listOf("Jouer en PATTERN WRITE : un appui sur CLEAR peut effacer le motif."),
        quiz = listOf(
            qz("Mode conseillé pour jouer ?", listOf("PATTERN PLAY", "PATTERN WRITE"), 0, "Évite les effacements.", JOUER),
            qz("Nouveau motif pendant la lecture…", listOf("Démarre à la fin du motif en cours", "Coupe tout"), 0, "Enchaînement.", JOUER),
            qz("Transposer ?", listOf("PITCH MODE + touche", "TRANSPOSE UP"), 0, "Au redémarrage du motif.", JOUER),
            qz("Deux PATTERN maintenus…", listOf("Jouent la plage dans l'ordre", "S'effacent"), 0, "Avec ceux entre eux.", JOUER_EN),
        ),
    )

    private val m4l3 = ctLesson(
        M4, 3, "Slide en fin de motif", listOf(SLIDE_FIN, ACCENT_SLIDE_EN), 10,
        objective = "Utiliser un slide sur la dernière note pour lier les motifs.",
        explanation = "Si la dernière note d'un motif a un slide : en TRACK PLAY (motif placé dans une piste), elle glisse vers le motif " +
            "suivant de la piste ; en PATTERN WRITE/PLAY, avec un seul motif, elle glisse vers le début du même motif ; avec deux motifs ou " +
            "plus, vers le motif suivant.",
        controls = listOf(
            cmd("SLIDE sur la dernière note", "Liaison entre motifs.", SLIDE_FIN),
        ),
        steps = listOf(
            "Ajoute un SLIDE sur la dernière note de 1A.",
            "Joue 1A seul : la fin glisse vers son début.",
            "Joue 1A puis 2A : la fin de 1A glisse vers 2A.",
        ),
        expected = "Les motifs s'enchaînent sans coupure.",
        exerciseTitle = "Boucle sans couture",
        exercise = listOf("Crée deux motifs qui se lient par un slide final."),
        success = "On n'entend pas où finit un motif.",
        style = "Acidcore : des lignes qui glissent d'un motif à l'autre.",
        mistakes = listOf("Oublier que le slide final dépend du mode et du nombre de motifs."),
        quiz = listOf(
            qz("Un seul motif avec slide final…", listOf("Glisse vers son propre début", "S'arrête"), 0, "En PATTERN WRITE/PLAY.", SLIDE_FIN),
            qz("Deux motifs ou plus…", listOf("Glisse vers le suivant", "Glisse vers le premier"), 0, "Vers le motif suivant.", SLIDE_FIN),
            qz("En TRACK PLAY…", listOf("Vers le motif suivant de la piste", "Vers rien"), 0, "Quand le motif est dans une piste.", SLIDE_FIN),
            qz("Où place-t-on ce slide ?", listOf("Sur la dernière note", "Sur la première"), 0, "Dernière note du motif.", SLIDE_FIN),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 5 — Les pistes
    // ---------------------------------------------------------------------
    private const val M5 = "td3-m5"

    private val m5l1 = ctLesson(
        M5, 1, "Écrire une piste", listOf(PISTES, PISTES_EN, GROUPES), 20,
        objective = "Assembler tes motifs en un morceau (piste).",
        explanation = "MODE sur TRACK WRITE, TRACK sur 1 à 7 (cela fixe le groupe utilisable : pistes 1–2 = groupe I, 3–4 = II, 5–6 = III, " +
            "7 = IV). CLEAR revient au début de la piste. START/STOP : la piste ne s'écrit que pendant la lecture. Choisir un motif (ex. 2 " +
            "puis A pour 2A), WRITE/NEXT l'ajoute. On continue ainsi ; PITCH MODE + touche ajoute une transposition. Au dernier motif : " +
            "CLEAR, puis WRITE/NEXT, puis START/STOP.",
        controls = listOf(
            cmd("MODE > TRACK WRITE", "Écriture de piste.", PISTES),
            cmd("TRACK 1–7", "Piste (et groupe de motifs).", GROUPES),
            cmd("WRITE/NEXT", "Ajouter le motif.", PISTES_EN),
            cmd("CLEAR (au dernier motif)", "Marquer la fin.", PISTES_EN),
        ),
        steps = listOf(
            "MODE sur TRACK WRITE, TRACK sur 1, CLEAR.",
            "START/STOP, puis 1 et A, WRITE/NEXT.",
            "Ajoute 1A, 1A, 2A de la même façon (une transposition sur le 3e avec PITCH MODE + touche).",
            "Au dernier : CLEAR, WRITE/NEXT, START/STOP.",
        ),
        expected = "Une piste qui enchaîne tes motifs.",
        exerciseTitle = "Morceau acid",
        exercise = listOf("Écris une piste de 8 motifs : intro, ligne, ligne transposée, break, retour."),
        success = "La piste joue le morceau en entier.",
        style = "Acidcore : la même ligne transposée structure tout le morceau.",
        mistakes = listOf("Écrire à l'arrêt : la piste ne s'écrit que pendant la lecture."),
        quiz = listOf(
            qz("Groupe utilisable par la piste 3 ?", listOf("II", "I"), 0, "Pistes 3–4 = groupe II.", GROUPES),
            qz("Piste écrite quand ?", listOf("Pendant la lecture", "À l'arrêt"), 0, "Uniquement en lecture.", PISTES_EN),
            qz("Ajouter un motif ?", listOf("WRITE/NEXT", "TAP"), 0, "Après l'avoir choisi.", PISTES_EN),
            qz("Signaler le dernier motif ?", listOf("CLEAR puis WRITE/NEXT", "BACK"), 0, "Puis START/STOP.", PISTES_EN),
        ),
    )

    private val m5l2 = ctLesson(
        M5, 2, "Lire et modifier une piste", listOf(EDIT_PISTE, PISTES_EN), 15,
        objective = "Jouer une piste, retirer et insérer des motifs.",
        explanation = "Lire : MODE sur TRACK PLAY, choisir la piste, CLEAR (retour au début), START/STOP. Supprimer le 3e motif : TRACK WRITE, " +
            "piste, FUNCTION maintenu + PATTERN 3, puis FUNCTION maintenu + DEL (DO dièse) ; revenir en TRACK PLAY, CLEAR, START/STOP. " +
            "Insérer avant le 5e : TRACK WRITE, FUNCTION + PATTERN 5, FUNCTION + INS (RÉ dièse) ; START/STOP joue le motif ajouté, choisir le " +
            "motif voulu (1–8, A/B), TAP pour enregistrer, START/STOP pour arrêter.",
        controls = listOf(
            cmd("MODE > TRACK PLAY, CLEAR, START/STOP", "Lire depuis le début.", EDIT_PISTE),
            cmd("FUNCTION + PATTERN n", "Cibler le n-ième motif.", PISTES_EN),
            cmd("FUNCTION + DEL / INS", "Supprimer / insérer.", PISTES_EN),
            cmd("TAP", "Enregistrer la modification.", PISTES_EN),
        ),
        steps = listOf(
            "Lis ta piste en TRACK PLAY après CLEAR.",
            "En TRACK WRITE, supprime le 3e motif (FUNCTION + PATTERN 3, FUNCTION + DEL).",
            "Insère un motif avant le 5e (FUNCTION + PATTERN 5, FUNCTION + INS), choisis-le, TAP.",
            "Réécoute depuis le début.",
        ),
        expected = "La piste est modifiée sans la réécrire.",
        exerciseTitle = "Break inséré",
        exercise = listOf("Insère un motif de break au milieu de ta piste."),
        success = "Le break tombe au bon endroit.",
        mistakes = listOf("Oublier CLEAR avant de relire : la piste ne repart pas du début."),
        quiz = listOf(
            qz("Touche DEL ?", listOf("DO dièse", "RÉ dièse"), 0, "INS = RÉ dièse.", PISTES_EN),
            qz("Cibler le 5e motif ?", listOf("FUNCTION + PATTERN 5", "PATTERN 5 seul"), 0, "En TRACK WRITE.", PISTES_EN),
            qz("Enregistrer un motif inséré ?", listOf("TAP", "CLEAR"), 0, "Après l'avoir choisi.", PISTES_EN),
            qz("Revenir au début d'une piste ?", listOf("CLEAR", "BACK"), 0, "Avant START/STOP.", EDIT_PISTE),
        ),
    )

    private val m5l3 = ctLesson(
        M5, 3, "Remplacer une piste", listOf(EDIT_PISTE, PISTES_EN), 10,
        objective = "Réécrire une piste existante proprement.",
        explanation = "On peut écrire une nouvelle piste par-dessus une ancienne sans supprimer ses motifs un à un : quand la nouvelle piste " +
            "(même plus courte) joue son dernier motif, elle revient automatiquement au début. On réécrit donc simplement la piste comme " +
            "à la leçon précédente, en marquant bien le dernier motif avec CLEAR.",
        controls = listOf(
            cmd("TRACK WRITE + écriture", "Nouvelle piste par-dessus.", PISTES_EN),
            cmd("CLEAR au dernier motif", "Fin de la nouvelle piste.", PISTES_EN),
        ),
        steps = listOf(
            "Choisis une piste existante en TRACK WRITE.",
            "Écris une version plus courte en terminant par CLEAR, WRITE/NEXT, START/STOP.",
            "Lis-la en TRACK PLAY : elle boucle sur la nouvelle longueur.",
        ),
        expected = "La nouvelle piste remplace l'ancienne.",
        exerciseTitle = "Version courte",
        exercise = listOf("Fais une version courte de ton morceau pour un enchaînement rapide en live."),
        success = "La version courte boucle correctement.",
        mistakes = listOf("Oublier de marquer le dernier motif : la fin de la piste reste incertaine."),
        quiz = listOf(
            qz("Faut-il vider la piste avant de la réécrire ?", listOf("Non", "Oui"), 0, "La nouvelle revient au début après son dernier motif.", PISTES_EN),
            qz("Une piste plus courte écrite par-dessus…", listOf("Boucle sur sa longueur", "Garde la fin de l'ancienne"), 0, "Retour automatique au début.", PISTES_EN),
            qz("Combien de pistes ?", listOf("7", "16"), 0, "Commutateur TRACK 1–7.", GROUPES),
            qz("Mode pour écrire ?", listOf("TRACK WRITE", "PATTERN PLAY"), 0, "Écriture de piste.", PISTES_EN),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(M4, 4, "Accent, slide et jeu", "Accents, slides, enchaînement et transposition des motifs.",
            "Donner vie à la ligne et la jouer en live.", "Accents, slides et transpositions : le cœur de l'acidcore.", listOf(m4l1, m4l2, m4l3)),
        CourseModule(M5, 5, "Les pistes", "Écrire, lire, modifier et remplacer une piste.",
            "Construire un morceau complet.", "Une piste prête = une ligne acid qui tient tout le morceau.", listOf(m5l1, m5l2, m5l3)),
    )
}
