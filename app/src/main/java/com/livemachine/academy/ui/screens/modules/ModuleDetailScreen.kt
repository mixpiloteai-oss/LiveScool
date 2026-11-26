package com.livemachine.academy.ui.screens.modules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.toRoute
import com.livemachine.academy.data.model.CourseModule
import com.livemachine.academy.data.model.UserProgress
import com.livemachine.academy.data.repository.CourseRepository
import com.livemachine.academy.data.repository.ProgressRepository
import com.livemachine.academy.ui.appViewModelFactory
import com.livemachine.academy.ui.components.AppTopBar
import com.livemachine.academy.ui.components.AwaitingManualNotice
import com.livemachine.academy.ui.components.EmptyState
import com.livemachine.academy.ui.components.Eyebrow
import com.livemachine.academy.ui.components.HeroBanner
import com.livemachine.academy.ui.components.ScreenPadding
import com.livemachine.academy.ui.components.SectionCard
import com.livemachine.academy.ui.components.StatusBadge
import com.livemachine.academy.ui.navigation.ModuleRoute
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ModuleDetailViewModel(
    savedStateHandle: SavedStateHandle,
    course: CourseRepository,
    progressRepository: ProgressRepository,
) : ViewModel() {
    val module: CourseModule? = course.module(savedStateHandle.toRoute<ModuleRoute>().moduleId)

    val progress: StateFlow<UserProgress> = progressRepository.progress
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UserProgress())

    companion object {
        val Factory = appViewModelFactory { c, handle ->
            ModuleDetailViewModel(handle, c.courseRepository, c.progressRepository)
        }
    }
}

@Composable
fun ModuleDetailScreen(
    onBack: () -> Unit,
    onOpenLesson: (String) -> Unit,
    viewModel: ModuleDetailViewModel = viewModel(factory = ModuleDetailViewModel.Factory),
) {
    val module = viewModel.module
    val progress by viewModel.progress.collectAsStateWithLifecycle()

    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = module?.let { "Module ${it.number}" } ?: "Module", onBack = onBack)
        if (module == null) {
            EmptyState("Module introuvable", "Ce module n'existe pas.")
            return@Column
        }
        LazyColumn(contentPadding = ScreenPadding, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                HeroBanner {
                    Eyebrow("Module ${module.number}", color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Spacer(Modifier.height(4.dp))
                    Text(module.title, style = MaterialTheme.typography.headlineSmall)
                    Spacer(Modifier.height(10.dp))
                    Text("Objectif : ${module.goal}", style = MaterialTheme.typography.bodyMedium)
                }
            }
            item {
                SectionCard {
                    Eyebrow("Pour tes styles", color = MaterialTheme.colorScheme.secondary)
                    Spacer(Modifier.height(6.dp))
                    Text(module.styleFocus, style = MaterialTheme.typography.bodyMedium)
                }
            }
            item { Text("Leçons", style = MaterialTheme.typography.titleLarge) }
            if (module.lessons.isEmpty()) {
                item {
                    AwaitingManualNotice(
                        message = "Les leçons de ce module seront définies et rédigées à partir du manuel " +
                            "officiel, chapitre par chapitre.",
                    )
                }
            }
            items(module.lessons, key = { it.id }) { lesson ->
                val done = progress.isLessonDone(lesson.id)
                SectionCard(onClick = { onOpenLesson(lesson.id) }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            if (done) Icons.Filled.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                            contentDescription = if (done) "Terminée" else "À faire",
                            tint = if (done) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outline,
                        )
                        Spacer(Modifier.width(14.dp))
                        Column(Modifier.weight(1f)) {
                            Text("${module.number}.${lesson.order}  ${lesson.title}", style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(6.dp))
                            StatusBadge(lesson.status)
                        }
                    }
                }
            }
        }
    }
}
