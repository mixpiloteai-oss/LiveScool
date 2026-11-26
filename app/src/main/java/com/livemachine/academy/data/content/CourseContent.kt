package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule
import com.livemachine.academy.data.model.Lesson

/**
 * Programme du cours, hors ligne.
 *
 * IMPORTANT : aucune fonction de la machine n'est décrite ici tant que le manuel
 * officiel n'a pas été fourni et lu. Les leçons sont des emplacements prévus
 * (statut AWAITING_MANUAL) : leur contenu, leurs commandes et leurs pages seront
 * rédigés uniquement à partir du manuel.
 */
object CourseContent {

    private fun plannedLessons(moduleId: String, vararg titles: String): List<Lesson> =
        titles.mapIndexed { index, title ->
            Lesson(
                id = "$moduleId-l${index + 1}",
                moduleId = moduleId,
                order = index + 1,
                title = title,
            )
        }

    val modules: List<CourseModule> = listOf(
        CourseModule(
            id = "m1",
            number = 1,
            title = "Interface et navigation",
            summary = "Se repérer sur la façade et dans les menus.",
            goal = "Retrouver sans hésiter chaque zone de la façade et naviguer dans l'écran.",
            styleFocus = "Base indispensable : en live, chaque seconde perdue à chercher un bouton casse l'énergie.",
            lessons = plannedLessons(
                "m1",
                "Vue d'ensemble de la façade",
                "Allumer la machine et lire l'écran",
                "Lancer, arrêter et choisir un pattern",
                "Régler le tempo",
                "Sauvegarder son travail",
            ),
        ),
        CourseModule(
            id = "m2",
            number = 2,
            title = "Parts et samples",
            summary = "Assigner les samples aux parts et les jouer.",
            goal = "Construire un kit cohérent à partir de tes samples déjà importés.",
            styleFocus = "Kick saturé, basse roulante, percus tribales : le kit fait le style.",
            lessons = emptyList(),
        ),
        CourseModule(
            id = "m3",
            number = 3,
            title = "Paramètres sonores",
            summary = "Façonner chaque sample : hauteur, enveloppe, niveau…",
            goal = "Transformer un sample brut en son prêt pour le mix.",
            styleFocus = "Kicks courts et punchy en hardtek, sons plus longs et hypnotiques en tribe.",
            lessons = emptyList(),
        ),
        CourseModule(
            id = "m4",
            number = 4,
            title = "Séquenceur et patterns",
            summary = "Programmer des rythmes pas à pas et en temps réel.",
            goal = "Programmer un groove complet sur 16 pas et plus.",
            styleFocus = "Kick régulier, basse en contretemps, roulements de caisse.",
            lessons = emptyList(),
        ),
        CourseModule(
            id = "m5",
            number = 5,
            title = "Filtres et effets",
            summary = "Sculpter le son avec le filtre et les effets.",
            goal = "Utiliser filtre et effets pour donner du caractère et créer de la tension.",
            styleFocus = "Acidcore et mentalcore : filtre résonant, distorsion, montées de tension.",
            lessons = emptyList(),
        ),
        CourseModule(
            id = "m6",
            number = 6,
            title = "Motion Sequence et modulation",
            summary = "Enregistrer et animer les mouvements des paramètres.",
            goal = "Faire évoluer un son automatiquement au fil du pattern.",
            styleFocus = "Lignes acides qui bougent toutes seules, effets rythmiques.",
            lessons = emptyList(),
        ),
        CourseModule(
            id = "m7",
            number = 7,
            title = "Variations et construction",
            summary = "Décliner un pattern en variations et bâtir un morceau.",
            goal = "Construire intro, montée, break et drop à partir de quelques patterns.",
            styleFocus = "Tribecore et hardtek : longues montées et retours de kick percutants.",
            lessons = emptyList(),
        ),
        CourseModule(
            id = "m8",
            number = 8,
            title = "Live et transitions",
            summary = "Jouer en direct, enchaîner les patterns, gérer les mutes.",
            goal = "Enchaîner les patterns sans coupure et jouer avec les parts en direct.",
            styleFocus = "Enchaînements fluides et ruptures franches, à la manière d'un live tekno.",
            lessons = emptyList(),
        ),
        CourseModule(
            id = "m9",
            number = 9,
            title = "Préparer un set",
            summary = "Organiser patterns et sauvegardes pour la scène.",
            goal = "Arriver devant le public avec un set organisé et sauvegardé.",
            styleFocus = "Un set cohérent en tempo et en énergie, de la tribe au mentalcore.",
            lessons = emptyList(),
        ),
    )

    val lessons: List<Lesson> get() = modules.flatMap { it.lessons }
}
