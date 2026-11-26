package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule
import com.livemachine.academy.data.model.GlossaryCategory.MACHINE
import com.livemachine.academy.data.model.GlossaryTerm
import com.livemachine.academy.data.model.IdentifiableControl
import com.livemachine.academy.data.model.MusicStyle
import com.livemachine.academy.data.model.StyleChallenge

/**
 * Novation Circuit Tracks.
 *
 * Source unique : Guide d'utilisation Circuit Tracks, Version 2 (Novation, FR), p. 6–108.
 * Aucune fonction n'est décrite sans sa page. Identifiants préfixés « ct- ».
 */
object CircuitTracksContent {
    const val shortDescription =
        "Groovebox autonome de Novation : 2 synthés, 4 pistes Drum à samples, 2 pistes MIDI, sur batterie."
    const val note =
        "Parcours rédigé d'après le Guide d'utilisation Circuit Tracks, Version 2 (Novation, en français). " +
            "Ce guide ne donne pas de numéro de firmware : si une fonction diffère sur ta machine, mets-la à jour avec " +
            "Novation Components (Annexe, p. 102). Ne pas confondre avec la Circuit Rhythm ou la Circuit d'origine."
    const val sourcesLabel = "Novation Circuit Tracks — Guide d'utilisation, Version 2 (FR), p. 6–108."

    val modules: List<CourseModule> =
        CircuitTracksModulesA.modules + CircuitTracksModulesB.modules +
            CircuitTracksModulesC.modules + CircuitTracksModulesD.modules

    private val DESSUS = ct("Présentation du matériel › Vue du dessus", "16–18")
    private val ARRIERE = ct("Présentation du matériel › Vue arrière", "19")

    val sections: List<MachineSection> = listOf(
        MachineSection(
            "Grille et commandes de son",
            "La grille de 32 pads change de rôle selon la vue ; Macros, Master Filter et Master Volume façonnent le son.",
            listOf(
                cmd("Grille de 32 pads RVB", "Jeu, pas du séquenceur et réglages selon la vue.", DESSUS),
                cmd("Macros 1 à 8", "Encodeurs dont la fonction dépend de la vue.", DESSUS),
                cmd("Master Filter", "Passe-bas / passe-haut global, toujours actif.", DESSUS),
                cmd("Master Volume", "Niveau des sorties audio.", DESSUS),
            ),
            DESSUS,
        ),
        MachineSection(
            "Pistes et édition des pas",
            "Huit touches de pistes et les touches Step pour éditer chaque pas.",
            listOf(
                cmd("Synth 1-2, MIDI 1-2, Drum 1-4", "Choix de la piste ; ouvre sa vue Note.", DESSUS),
                cmd("Note, Velocity, Gate", "Vues d'édition des pas.", DESSUS),
                cmd("Pattern Settings (Probability en Shift)", "Longueur, sens, vitesse ; probabilité.", DESSUS),
                cmd("Step Page (1-16 / 17-32)", "Patterns de 16 ou 32 pas.", DESSUS),
            ),
            DESSUS,
        ),
        MachineSection(
            "Vues de jeu",
            "Gammes, sons, patterns, mixage et effets.",
            listOf(
                cmd("Scales", "Gamme et fondamentale des synthés et pistes MIDI.", DESSUS),
                cmd("Preset", "Patches, samples ou modèles MIDI.", DESSUS),
                cmd("Patterns", "Huit patterns par piste, chaînes.", DESSUS),
                cmd("Mixer", "Niveaux, mutes, panoramique, Scènes.", DESSUS),
                cmd("FX", "Réverbe, délai ; sidechain en Shift.", DESSUS),
                cmd("Touches fléchées", "Octave ou page selon la vue.", DESSUS),
            ),
            DESSUS,
        ),
        MachineSection(
            "Transport, édition, mémoire",
            "Lecture, enregistrement, tempo, copier/effacer, projets.",
            listOf(
                cmd("Record, Play", "Enregistrement et lecture.", DESSUS),
                cmd("Tempo/Swing", "Tempo (Macro 1), swing (Macro 2).", DESSUS),
                cmd("Clear, Duplicate", "Effacer ; copier/coller.", DESSUS),
                cmd("Save, Projects", "Sauvegarder ; ouvrir un projet.", DESSUS),
                cmd("Shift", "Fonctions secondaires.", DESSUS),
            ),
            DESSUS,
        ),
        MachineSection(
            "Panneau arrière",
            "Audio, MIDI, synchronisation, alimentation et carte microSD.",
            listOf(
                cmd("Outputs L/Mono et R, casque", "Sorties audio.", ARRIERE),
                cmd("Inputs 1 et 2", "Entrées audio ligne, mixées et traitées.", ARRIERE),
                cmd("MIDI In, Out, Thru", "Prises DIN 5 broches.", ARRIERE),
                cmd("Sync", "Horloge analogique 5 V.", ARRIERE),
                cmd("USB-C", "Alimentation, charge, MIDI, firmware (pas d'audio).", ARRIERE),
                cmd("microSD", "Packs de projets.", ARRIERE),
                cmd("Bouton d'alimentation", "Tenir environ une seconde.", ARRIERE),
            ),
            ARRIERE,
        ),
    )

    /** Commandes à repérer sur la photo de l'utilisateur (guide p. 16–19). */
    val identifiableControls: List<IdentifiableControl> = listOf(
        IdentifiableControl("ct-grid", "Grille de 32 pads", "Jeu et affichage selon la vue.", DESSUS),
        IdentifiableControl("ct-filter", "Master Filter", "Grand potentiomètre cranté au centre.", DESSUS),
        IdentifiableControl("ct-macros", "Macros 1 à 8", "Huit encodeurs avec LED.", DESSUS),
        IdentifiableControl("ct-volume", "Master Volume", "Niveau global.", DESSUS),
        IdentifiableControl("ct-tracks", "Touches de pistes", "Synth, MIDI, Drum.", DESSUS),
        IdentifiableControl("ct-note", "Note", "Vue Note de la piste.", DESSUS),
        IdentifiableControl("ct-velocity", "Velocity", "Vue vélocité.", DESSUS),
        IdentifiableControl("ct-gate", "Gate", "Vue durée des notes.", DESSUS),
        IdentifiableControl("ct-settings", "Pattern Settings", "Longueur, sens, vitesse.", DESSUS),
        IdentifiableControl("ct-steppage", "Step Page", "Pas 1-16 / 17-32.", DESSUS),
        IdentifiableControl("ct-scales", "Scales", "Gammes.", DESSUS),
        IdentifiableControl("ct-patterns", "Patterns", "Vue des patterns.", DESSUS),
        IdentifiableControl("ct-mixer", "Mixer", "Mixage et Scènes.", DESSUS),
        IdentifiableControl("ct-fx", "FX", "Réverbe et délai.", DESSUS),
        IdentifiableControl("ct-preset", "Preset", "Sons de la piste.", DESSUS),
        IdentifiableControl("ct-arrows", "Touches fléchées", "Octave ou page.", DESSUS),
        IdentifiableControl("ct-rec", "Record", "Enregistrement.", DESSUS),
        IdentifiableControl("ct-play", "Play", "Lecture.", DESSUS),
        IdentifiableControl("ct-tempo", "Tempo/Swing", "Tempo et swing.", DESSUS),
        IdentifiableControl("ct-clear", "Clear", "Effacer.", DESSUS),
        IdentifiableControl("ct-duplicate", "Duplicate", "Copier/coller.", DESSUS),
        IdentifiableControl("ct-save", "Save", "Sauvegarder.", DESSUS),
        IdentifiableControl("ct-projects", "Projects", "Ouvrir un projet.", DESSUS),
        IdentifiableControl("ct-shift", "Shift", "Fonctions secondaires.", DESSUS),
    )

    private val MACROS_DRUM = ct("Les rythmes › Utiliser les macros pour créer des rythmes", "64")
    private val SIDECHAIN = ct("Les sidechains", "93–94")
    private val PROBA = ct("Les synthétiseurs › Les probabilités", "48–49")
    private val PARAM = ct("Les paramètres des patterns", "54–57")
    private val MICRO_DRUM = ct("Les rythmes › Éditer les micro-pas", "67–69")
    private val MACROS = ct("Les synthétiseurs › Parcourir les macros", "35")
    private val AUTO = ct("Les synthétiseurs › Enregistrer les mouvements des potentiomètres", "37–38")
    private val GAMMES = ct("Les synthétiseurs › Scales", "31–32")
    private val MIXER = ct("Mélangeur", "88–89")
    private val FILTRE = ct("Le potentiomètre de filtre", "94")
    private val SCENES = ct("Les patterns › Scènes", "81–84")

    val challenges: List<StyleChallenge> = listOf(
        StyleChallenge(
            id = "ct-c-hardtek-1", style = MusicStyle.HARDTEK, title = "Kick saturé et basse qui pompe",
            requiredModuleId = "ct-m9",
            goal = "Un groove hardtek : kick court et saturé, basse qui s'efface à chaque kick.",
            steps = listOf(
                "Kick 4/4 sur Drum 1 ; Macro 4 pour un déclin court, Macro 6 pour la distorsion.",
                "Basse roulante sur Synth 1, notes courtes entre les kicks.",
                "Shift + FX : sidechain de Synth 1 déclenché par Drum 1.",
                "Mixer : kick et basse au centre, charleys légèrement décalés.",
            ),
            successCriteria = "Le kick reste net malgré la saturation et la basse ne le masque jamais.",
            manualRefs = listOf(MACROS_DRUM, SIDECHAIN, MIXER),
        ),
        StyleChallenge(
            id = "ct-c-tribe-1", style = MusicStyle.TRIBE, title = "Percussions hypnotiques",
            requiredModuleId = "ct-m6",
            goal = "Une boucle tribale qui évolue seule grâce à la polyrythmie et à la probabilité.",
            steps = listOf(
                "Kick 16 pas sur Drum 1.",
                "Percussions sur Drum 4 en 12 pas (Pattern Settings), avec un roulement en micro-pas.",
                "Probabilité de 50 à 75 % sur la moitié des percussions.",
                "Charley sur Drum 3 en 7 pas.",
            ),
            successCriteria = "Pendant 2 minutes, la boucle ne sonne jamais deux fois exactement pareil, le kick reste fixe.",
            manualRefs = listOf(PARAM, PROBA, MICRO_DRUM),
        ),
        StyleChallenge(
            id = "ct-c-acid-1", style = MusicStyle.ACIDCORE, title = "La basse acide automatisée",
            requiredModuleId = "ct-m8",
            goal = "Une basse qui crie : filtre et résonance automatisés sur 32 pas.",
            steps = listOf(
                "Basse de 32 pas sur Synth 1, gamme mineure ou phrygienne.",
                "Record : ouvre la Macro 5 et la Macro 6 sur la seconde moitié du pattern.",
                "Ajoute une résonance plus forte sur le dernier pas en automation par pas.",
            ),
            successCriteria = "Le filtre s'ouvre seul à chaque tour et le dernier pas « crie » plus que les autres.",
            manualRefs = listOf(GAMMES, MACROS, AUTO),
        ),
        StyleChallenge(
            id = "ct-c-mental-1", style = MusicStyle.MENTALCORE, title = "Montée et drop en Scènes",
            requiredModuleId = "ct-m12",
            goal = "Un enchaînement montée / drop joué en live avec les Scènes, les mutes et le filtre.",
            steps = listOf(
                "Scène 1 : montée sans kick, lead aigu ; Scène 2 : drop, kick et basse complets.",
                "En lecture, filtre en passe-haut pendant la montée.",
                "Mets la Scène 2 en file d'attente et recentre le filtre juste avant qu'elle démarre.",
            ),
            successCriteria = "Le drop tombe exactement sur le temps, sans silence ni décalage.",
            manualRefs = listOf(SCENES, FILTRE, MIXER),
        ),
    )

    val glossary: List<GlossaryTerm> = listOf(
        GlossaryTerm("Vue (Circuit Tracks)", "Une façon d'utiliser la grille de 32 pads, ouverte par une touche : Note, Velocity, Gate, Mixer, FX…", MACHINE, ct("Présentation du matériel › Glossaire", "15")),
        GlossaryTerm("Projet (Circuit Tracks)", "Tout ce qu'il faut pour rejouer un morceau : patterns, sons, automation. 64 emplacements.", MACHINE, ct("Présentation du matériel › Glossaire", "13")),
        GlossaryTerm("Pack", "L'ensemble des 64 projets, patches et samples ; jusqu'à 32 Packs avec une carte microSD.", MACHINE, ct("Les packs", "97")),
        GlossaryTerm("Scène", "Mémoire des patterns de toutes les pistes, déclenchée d'un pad ; les Scènes se chaînent en arrangement.", MACHINE, ct("Les patterns › Scènes", "81")),
        GlossaryTerm("Micro-pas", "Un sixième de pas : sert à décaler des notes ou à faire des roulements.", MACHINE, ct("Présentation du matériel › Glossaire", "12")),
        GlossaryTerm("Sample Flip", "Faire jouer d'autres samples que le sample actif sur certains pas d'une piste Drum.", MACHINE, ct("Les rythmes › La fonction Sample Flip", "63")),
        GlossaryTerm("Mutate", "Shift + Duplicate : redistribue au hasard les notes du pattern en cours sur d'autres pas.", MACHINE, ct("Les synthétiseurs › La fonction Mutate", "57")),
        GlossaryTerm("Modèle MIDI", "Réglage d'une piste MIDI qui définit les CC envoyés par les Macros.", MACHINE, ct("Les pistes MIDI › Choisir les modèles", "58")),
        GlossaryTerm("Save Lock", "Désactive la sauvegarde pour protéger un set ; Shift + Save à l'allumage.", MACHINE, ct("Annexe › La fonction Save Lock", "107")),
    ).map { it.copy(machineId = MachineCatalog.CIRCUIT_TRACKS_ID) }
}
