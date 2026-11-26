package com.livemachine.academy.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.livemachine.academy.data.model.CourseModule
import com.livemachine.academy.data.model.Exercise
import com.livemachine.academy.data.model.Lesson
import com.livemachine.academy.data.model.UserProgress
import com.livemachine.academy.data.repository.CourseRepository
import com.livemachine.academy.data.repository.ProgressRepository
import com.livemachine.academy.ui.appViewModelFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ModuleProgress(val module: CourseModule, val completed: Int) {
    val total: Int get() = module.lessons.size
    val fraction: Float get() = if (total == 0) 0f else completed.toFloat() / total
    val isDone: Boolean get() = total > 0 && completed == total
}

data class OverviewUiState(
    val modules: List<ModuleProgress> = emptyList(),
    val nextLesson: Lesson? = null,
    val exercises: List<Exercise> = emptyList(),
    val progress: UserProgress = UserProgress(),
    val lessonTitles: Map<String, String> = emptyMap(),
) {
    val totalLessons: Int get() = modules.sumOf { it.total }
    val completedLessons: Int get() = modules.sumOf { it.completed }
    val fraction: Float get() = if (totalLessons == 0) 0f else completedLessons.toFloat() / totalLessons
}

/** Vue d'ensemble du cours + progression, partagée par plusieurs écrans. */
class OverviewViewModel(
    private val course: CourseRepository,
    private val progressRepository: ProgressRepository,
) : ViewModel() {

    val uiState: StateFlow<OverviewUiState> = progressRepository.progress
        .map { progress ->
            OverviewUiState(
                modules = course.modules().map { module ->
                    ModuleProgress(module, module.lessons.count { progress.isLessonDone(it.id) })
                },
                nextLesson = course.nextLesson(progress.completedLessons),
                exercises = course.exercises(),
                progress = progress,
                lessonTitles = course.lessons().associate { it.id to it.title },
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), OverviewUiState())

    fun toggleExercise(exerciseId: String) {
        viewModelScope.launch {
            val done = exerciseId in uiState.value.progress.completedExercises
            progressRepository.setExerciseCompleted(exerciseId, !done)
        }
    }

    companion object {
        val Factory = appViewModelFactory { c, _ -> OverviewViewModel(c.courseRepository, c.progressRepository) }
    }
}
