package com.livemachine.academy.ui.navigation

import kotlinx.serialization.Serializable

@Serializable data object HomeRoute
@Serializable data object MachineRoute
@Serializable data object PathRoute
@Serializable data object ModulesRoute
@Serializable data class ModuleRoute(val moduleId: String)
@Serializable data class LessonRoute(val lessonId: String)
@Serializable data class QuizRoute(val lessonId: String)
@Serializable data object ExercisesRoute
@Serializable data object ProgressRoute
@Serializable data object GlossaryRoute
@Serializable data object SettingsRoute
