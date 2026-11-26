package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CreativeWorkshop
import com.livemachine.academy.data.model.WorkshopKind

/**
 * Ateliers de création sur la Circuit Tracks. Chaque étape n'utilise que des fonctions
 * décrites dans le Guide d'utilisation V2, pages citées. Les tempos et styles sont des choix
 * musicaux laissés à l'utilisateur, le guide ne parle pas de styles.
 */
object CircuitTracksWorkshops {

    private val ZERO = ct("Principes élémentaires › Démarrer de zéro", "25–27")
    private val RYTHMES = ct("Les rythmes › Jouer des rythmes", "61–62")
    private val VELOCITE = ct("Les rythmes › Vélocité", "69–71")
    private val GATE = ct("Les synthétiseurs › Gate", "46–47")
    private val REC = ct("Les synthétiseurs › Enregistrer un pattern de synthé en temps réel", "36–37")
    private val DUPL_PATTERNS = ct("Les patterns › Dupliquer des patterns", "75")
    private val VUE_PATTERNS = ct("Les patterns › La vue Patterns", "73–74")
    private val PROBA = ct("Les synthétiseurs › Les probabilités", "48–49")
    private val MUTATE = ct("Les synthétiseurs › La fonction Mutate", "57")
    private val FLIP = ct("Les rythmes › La fonction Sample Flip", "63–64")
    private val AUTO_SYNTH = ct("Les synthétiseurs › Enregistrer les mouvements des potentiomètres", "37–38")
    private val AUTO_DRUM = ct("Les rythmes › Enregistrer les mouvements des potentiomètres", "71–72")
    private val MACROS = ct("Les synthétiseurs › Parcourir les macros", "35")
    private val MACROS_DRUM = ct("Les rythmes › Utiliser les macros pour créer des rythmes", "64")
    private val MIXER = ct("Mélangeur", "88–89")
    private val FILTRE = ct("Le potentiomètre de filtre", "94")
    private val FX = ct("La section d'effets", "90–92")
    private val CHAINES = ct("Les patterns › Le chaînage des patterns", "76–79")
    private val SCENES = ct("Les patterns › Scènes", "81–84")
    private val PROJETS = ct("Projets › Changer de projet", "95")
    private val SAVE = ct("Principes élémentaires › Le chargement et la sauvegarde", "23–24")

    val workshops: List<CreativeWorkshop> = listOf(
        CreativeWorkshop(
            id = "ct-w-pattern",
            kind = WorkshopKind.PATTERN,
            title = "Un groove complet sur les huit pistes",
            requiredModuleId = "ct-m5",
            goal = "Programmer un pattern de 16 pas : batterie sur quatre pistes, basse sur Synth 1, accords sur Synth 2.",
            steps = listOf(
                "Projects : choisis un emplacement vide.",
                "Drum 1 : kick sur 1, 5, 9, 13. Drum 2 : snare sur 5 et 13.",
                "Drum 3 : charleys ; en vue Velocity, baisse les pas pairs pour créer des accents.",
                "Drum 4 : trois percussions à contretemps.",
                "Synth 1 : Record, joue une basse de notes courtes, puis Record ; ajuste les durées dans la vue Gate.",
                "Synth 2 : ajoute un accord en tenant un pas et en touchant ses notes (sans Record).",
                "Save : sauvegarde dans l'emplacement choisi (vue Projects, Save puis pad).",
            ),
            successCriteria = "Le pattern tourne en boucle avec ses accents, et il revient intact après rechargement du projet.",
            manualRefs = listOf(ZERO, RYTHMES, VELOCITE, REC, GATE, SAVE),
        ),
        CreativeWorkshop(
            id = "ct-w-variation",
            kind = WorkshopKind.VARIATION,
            title = "Trois variations d'un même pattern",
            requiredModuleId = "ct-m6",
            goal = "Créer les patterns 2 et 3 de la batterie à partir du pattern 1, puis passer de l'un à l'autre en lecture.",
            steps = listOf(
                "Sauvegarde d'abord le projet : Mutate ne s'annule pas.",
                "Vue Patterns : Duplicate, pattern 1 de Drum 3 puis pattern 2 ; refais-le vers le pattern 3.",
                "Pattern 2 : mets plusieurs charleys à 50 % dans la vue Probability.",
                "Pattern 3 : Sample Flip sur deux pas (tenir le pas, toucher un autre sample), puis Shift + Duplicate (Mutate).",
                "Fais de même pour Drum 4 si tu veux.",
                "En lecture, passe du pattern 1 au 2 puis au 3 : chaque changement arrive en fin de boucle.",
            ),
            successCriteria = "Trois variations reconnaissables, enchaînées sans trou ni décalage.",
            manualRefs = listOf(DUPL_PATTERNS, PROBA, FLIP, MUTATE, VUE_PATTERNS),
        ),
        CreativeWorkshop(
            id = "ct-w-automation",
            kind = WorkshopKind.AUTOMATION,
            title = "Refaire une automation de filtre",
            requiredModuleId = "ct-m7",
            goal = "Remplacer une automation existante par une meilleure, puis ajouter un réglage sur un seul pas.",
            steps = listOf(
                "Sur une basse de Synth 1, enregistre une ouverture de la Macro 5 en Record pendant la lecture.",
                "Écoute : si le mouvement ne te plaît pas, tiens Clear et tourne la Macro 5 d'au moins 20 %.",
                "Réenregistre-la plus lentement, sur toute la boucle, et sors de Record avant que la boucle reparte.",
                "Arrête la lecture, Record, tiens le dernier pas et monte la Macro 6 : seul ce pas devient plus résonant.",
                "Drum 2 : dans la vue Gate, Record, tiens le pas 13 et tourne la Macro 2 pour changer son pitch.",
            ),
            successCriteria = "La nouvelle ouverture de filtre remplace l'ancienne, et seuls les pas choisis ont leur réglage particulier.",
            manualRefs = listOf(AUTO_SYNTH, AUTO_DRUM),
        ),
        CreativeWorkshop(
            id = "ct-w-macros",
            kind = WorkshopKind.MACROS,
            title = "Sculpter kick et basse aux Macros",
            requiredModuleId = "ct-m8",
            goal = "Transformer le son d'un kick et d'une basse en direct, uniquement avec les Macros.",
            steps = listOf(
                "Drum 1 : Macro 2 (pitch) plus bas, Macro 4 (déclin) plus court, Macro 6 (distorsion) plus haut, Macro 8 (EQ) pour garder le kick lisible.",
                "Synth 1 : fais le tour des huit Macros et repère les deux qui changent le plus le patch.",
                "Tourne ensemble la Macro 5 et la Macro 6 sur une boucle.",
                "Change de patch et refais le tour : note les différences.",
                "Sauvegarde la version que tu préfères.",
            ),
            successCriteria = "Un kick et une basse au caractère nettement différent du son de départ, obtenus sans changer de sample ni de patch.",
            manualRefs = listOf(MACROS_DRUM, MACROS),
        ),
        CreativeWorkshop(
            id = "ct-w-transition",
            kind = WorkshopKind.TRANSITION,
            title = "Break, filtre et retour sur le temps",
            requiredModuleId = "ct-m9",
            goal = "Préparer une transition : break aux mutes, montée au filtre, puis nouveau pattern qui démarre en rythme.",
            steps = listOf(
                "Prépare un pattern 2 plus chargé pour la batterie (vue Patterns, Duplicate puis modification).",
                "Mixer : coupe Drum 1 et Synth 1 avec la rangée 1 pendant une boucle.",
                "Tourne le Master Filter vers la droite (passe-haut) pendant la montée.",
                "Vue Patterns : choisis le pattern 2 de la batterie ; il attend la fin de la boucle.",
                "Juste avant le changement, réactive les mutes et ramène le filtre au cran central.",
                "Ajoute un envoi de réverbe sur la snare pendant le break (vue FX), puis retire-le.",
            ),
            successCriteria = "Le retour du kick et le nouveau pattern tombent exactement sur le premier temps.",
            manualRefs = listOf(MIXER, FILTRE, VUE_PATTERNS, FX),
        ),
        CreativeWorkshop(
            id = "ct-w-projets",
            kind = WorkshopKind.TRANSITION,
            title = "Enchaîner deux morceaux",
            requiredModuleId = "ct-m12",
            goal = "Passer d'un projet à un autre sans arrêter la musique ni changer de tempo.",
            steps = listOf(
                "Prépare deux projets sur la même page de la vue Projects.",
                "Lance le premier, ouvre Projects et touche le second : il clignote et démarre à la fin du pattern en cours.",
                "Vérifie que le tempo du premier morceau est conservé.",
                "Recommence en tenant Shift : le second projet démarre tout de suite, au même pas.",
                "Choisis la méthode qui sonne le mieux pour ton set.",
            ),
            successCriteria = "Les deux morceaux s'enchaînent sans silence ni rupture de tempo.",
            manualRefs = listOf(PROJETS, SAVE),
        ),
        CreativeWorkshop(
            id = "ct-w-live",
            kind = WorkshopKind.LIVE_SEQUENCE,
            title = "Une séquence live en Scènes",
            requiredModuleId = "ct-m11",
            goal = "Construire un morceau de quatre parties en Scènes, les chaîner, puis le jouer en live.",
            steps = listOf(
                "Prépare les patterns : intro (kick seul), partie A, break (sans kick), partie B (basse transposée, Shift + touche fléchée).",
                "Pour chaque partie : sélectionne ses patterns dans la vue Patterns, puis Mixer, Shift + pad de Scène (Scènes 1 à 4).",
                "Tiens la Scène 1 et touche la Scène 4 : les quatre parties s'enchaînent.",
                "Pour une partie plus longue, chaîne des patterns consécutifs sur une piste (tenir le premier, toucher le dernier).",
                "Save deux fois pour garder les Scènes avec le projet.",
                "Joue la séquence en ajoutant des mutes et des coups de filtre en direct.",
            ),
            successCriteria = "Les quatre parties s'enchaînent seules dans l'ordre et tu peux jouer par-dessus sans arrêter la lecture.",
            manualRefs = listOf(SCENES, CHAINES, MIXER, FILTRE),
        ),
    )
}
