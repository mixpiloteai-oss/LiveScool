package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule
import com.livemachine.academy.data.model.Lesson

/**
 * Programme du cours, hors ligne.
 *
 * Source unique : le manuel officiel (voir [MachineContent.manualSource]).
 * Un module dont les leçons ne sont pas encore rédigées a une liste vide : on
 * n'écrit rien sur la machine sans l'avoir lu dans le manuel, page à l'appui.
 */
object CourseContent {

    val modules: List<CourseModule> = listOf(
        CourseModule(
            id = "m1",
            number = 1,
            title = "Interface et navigation",
            summary = "Se repérer sur la façade et dans les menus.",
            goal = "Retrouver sans hésiter chaque zone de la façade et naviguer dans l'écran.",
            styleFocus = "Base indispensable : en live, chaque seconde perdue à chercher un bouton casse l'énergie.",
            lessons = Module1Content.lessons,
        ),
        CourseModule(
            id = "m2",
            number = 2,
            title = "Parts et samples",
            summary = "Assigner, régler, éditer et créer les samples de chaque part.",
            goal = "Construire un kit cohérent, régler la lecture de chaque sample et créer tes propres samples par rééchantillonnage.",
            styleFocus = "Kick saturé, basse roulante, percus tribales : le kit fait le style.",
            lessons = Module2Content.lessons,
        ),
        CourseModule(
            id = "m3",
            number = 3,
            title = "Paramètres sonores",
            summary = "Oscillateur, hauteur, enveloppe, polyphonie et dynamique de chaque part.",
            goal = "Transformer un oscillateur ou un sample brut en son prêt pour le mix.",
            styleFocus = "Kicks courts et punchy en hardtek, sons plus longs et hypnotiques en tribe.",
            lessons = Module3Content.lessons,
        ),
        CourseModule(
            id = "m4",
            number = 4,
            title = "Séquenceur et patterns",
            summary = "Programmer pas à pas et en temps réel, éditer chaque pas, groove.",
            goal = "Programmer un groove complet, note par note, avec ses accents et son placement.",
            styleFocus = "Kick régulier, basse en contretemps, percussions en polyrythmie.",
            lessons = Module4Content.lessons,
        ),
        CourseModule(
            id = "m5",
            number = 5,
            title = "Filtres et effets",
            summary = "Les trois filtres du sampler, l'effet d'insertion et l'effet maître.",
            goal = "Utiliser filtre et effets pour donner du caractère et créer de la tension.",
            styleFocus = "Acidcore et mentalcore : filtre résonant, distorsion, montées de tension.",
            lessons = Module5Content.lessons,
        ),
        CourseModule(
            id = "m6",
            number = 6,
            title = "Motion Sequence et modulation",
            summary = "LFO et enveloppes de modulation, enregistrement des mouvements de commandes.",
            goal = "Faire évoluer un son automatiquement au fil du pattern, jusqu'à construire une montée.",
            styleFocus = "Lignes acides qui bougent toutes seules, montées mentales, textures tribales.",
            lessons = Module6Content.lessons,
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
