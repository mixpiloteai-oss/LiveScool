package com.livemachine.academy.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.livemachine.academy.data.model.CourseModule
import com.livemachine.academy.data.model.Exercise
import com.livemachine.academy.data.model.Lesson
import com.livemachine.academy.data.model.QuizScore
import com.livemachine.academy.data.model.StyleChallenge
import com.livemachine.academy.data.model.UserProgress
import com.livemachine.academy.data.repository.CourseRepository
import com.livemachine.academy.data.repository.ProgressRepository
import com.livemachine.academy.data.repository.SettingsRepository
import com.livemachine.academy.domain.ProgressRules
import com.livemachine.academy.ui.appViewModelFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ModuleProgress(
    val module: CourseModule,
    val completed: Int,
    val unlocked: Boolean = true,
    val validated: Boolean = false,
    val competenceAcquired: Boolean = false,
    val finalScore: QuizScore? = null,
) {
    val total: Int get() = module.lessons.size
    val fraction: Float get() = if (total == 0) 0f else completed.toFloat() / total
    val isDone: Boolean get() = total > 0 && completed == total
}

data class ReviewItem(val lessonId: String, val lessonTitle: String, val count: Int)

data class OverviewUiState(
    val modules: List<ModuleProgress> = emptyList(),
    val nextLesson: Lesson? = null,
    val exercises: List<Exercise> = emptyList(),
    val challenges: List<StyleChallenge> = emptyList(),
    val progress: UserProgress = UserProgress(),
    val lessonTitles: Map<String, String> = emptyMap(),
    val reviewItems: List<ReviewItem> = emptyList(),
    val unlockAll: Boolean = false,
) {
    val totalLessons: Int get() = modules.sumOf { it.total }
    val completedLessons: Int get() = modules.sumOf { it.completed }
    val fraction: Float get() = if (totalLessons == 0) 0f else completedLessons.toFloat() / totalLessons
    val reviewCount: Int get() = reviewItems.sumOf { it.count }
    fun isModuleUnlocked(moduleId: String) = modules.firstOrNull { it.module.id == moduleId }?.unlocked ?: false
    fun isModuleValidated(moduleId: String) = modules.firstOrNull { it.module.id == moduleId }?.validated ?: false
}

/** Vue d'ensemble du cours + progression, partagée par plusieurs écrans. */
class OverviewViewModel(
    private val course: CourseRepository,
    private val progressRepository: ProgressRepository,
    settingsRepository: SettingsRepository,
) : ViewModel() {

    val uiState: StateFlow<OverviewUiState> = combine(progressRepository.progress, settingsRepository.settings) { progress, settings ->
        val modules = course.modules()
        OverviewUiState(
            modules = modules.mapIndexed { index, module ->
                ModuleProgress(
                    module = module,
                    completed = module.lessons.count { progress.isLessonDone(it.id) },
                    unlocked = ProgressRules.isUnlocked(modules, index, progress, settings.unlockAllModules),
                    validated = ProgressRules.isValidated(module, progress),
                    competenceAcquired = ProgressRules.competenceAcquired(module, progress),
                    finalScore = progress.bestQuizScores[ProgressRules.finalQuizId(module.id)],
                )
            },
            nextLesson = course.nextLesson(progress.completedLessons),
            exercises = course.exercises(),
            challenges = course.challenges(),
            progress = progress,
            lessonTitles = course.lessons().associate { it.id to it.title },
            reviewItems = ProgressRules.reviewQuestions(modules, progress)
                .groupBy { q -> course.lessonOfQuestion(q.id) }
                .mapNotNull { (lesson, qs) -> lesson?.let { ReviewItem(it.id, it.title, qs.size) } },
            unlockAll = settings.unlockAllModules,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), OverviewUiState())

    fun toggleExercise(exerciseId: String) {
        viewModelScope.launch {
            val done = exerciseId in uiState.value.progress.completedExercises
            progressRepository.setExerciseCompleted(exerciseId, !done)
        }
    }

    fun toggleChallenge(challengeId: String) {
        viewModelScope.launch {
            val done = challengeId in uiState.value.progress.completedChallenges
            progressRepository.setChallengeCompleted(challengeId, !done)
        }
    }

    companion object {
        val Factory = appViewModelFactory { c, _ ->
            OverviewViewModel(c.courseRepository, c.progressRepository, c.settingsRepository)
        }
    }
}
