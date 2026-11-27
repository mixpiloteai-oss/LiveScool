package com.livemachine.academy.ui.navigation

import kotlinx.serialization.Serializable

@Serializable data object HomeRoute
@Serializable data object MachineRoute
@Serializable data object PathRoute
@Serializable data object ModulesRoute
@Serializable data class ModuleRoute(val moduleId: String)
@Serializable data class LessonRoute(val lessonId: String)

/** [kind] : [QuizKind.LESSON] (id = leçon), [QuizKind.FINAL] (id = module), [QuizKind.REVIEW] (id ignoré). */
@Serializable data class QuizRoute(val kind: String, val id: String)

object QuizKind {
    const val LESSON = "lesson"
    const val FINAL = "final"
    const val REVIEW = "review"
}

@Serializable data object ExercisesRoute
@Serializable data object ChallengesRoute
/** [focus] : ids de commandes séparés par des virgules (vide = toutes les commandes de la machine). */
@Serializable data class PhotoRoute(val machineId: String, val focus: String = "")
/** [lessonId] : leçon d'où l'assistant est ouvert (vide = contexte de la machine choisie). */
@Serializable data class AssistantRoute(val lessonId: String = "")
@Serializable data object AudioAnalysisRoute
@Serializable data object PremiumRoute

/** [kind] : [PRIVACY] (politique de confidentialité + contrôle des données) ou [TERMS] (conditions d'utilisation). */
@Serializable data class LegalRoute(val kind: String) {
    companion object {
        const val PRIVACY = "privacy"
        const val TERMS = "terms"
    }
}
@Serializable data object ProgressRoute
@Serializable data object GlossaryRoute
@Serializable data object SettingsRoute
