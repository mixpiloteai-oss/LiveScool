package com.livemachine.academy.ui.screens.lesson

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.toRoute
import com.livemachine.academy.data.model.ContentStatus
import com.livemachine.academy.data.model.Lesson
import com.livemachine.academy.data.repository.CourseRepository
import com.livemachine.academy.data.repository.ProgressRepository
import com.livemachine.academy.ui.appViewModelFactory
import com.livemachine.academy.ui.components.AppTopBar
import com.livemachine.academy.ui.components.AwaitingManualNotice
import com.livemachine.academy.ui.components.EmptyState
import com.livemachine.academy.ui.components.Eyebrow
import com.livemachine.academy.ui.components.ManualRefLine
import com.livemachine.academy.ui.components.ScreenPadding
import com.livemachine.academy.ui.components.SectionCard
import com.livemachine.academy.ui.components.StatusBadge
import com.livemachine.academy.ui.navigation.LessonRoute
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LessonViewModel(
    savedStateHandle: SavedStateHandle,
    course: CourseRepository,
    private val progressRepository: ProgressRepository,
) : ViewModel() {
    val lesson: Lesson? = course.lesson(savedStateHandle.toRoute<LessonRoute>().lessonId)

    val isCompleted: StateFlow<Boolean> = progressRepository.progress
        .map { lesson != null && it.isLessonDone(lesson.id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)

    fun toggleCompleted() {
        val id = lesson?.id ?: return
        viewModelScope.launch { progressRepository.setLessonCompleted(id, !isCompleted.value) }
    }

    companion object {
        val Factory = appViewModelFactory { c, handle ->
            LessonViewModel(handle, c.courseRepository, c.progressRepository)
        }
    }
}

@Composable
fun LessonScreen(
    onBack: () -> Unit,
    onOpenQuiz: (String) -> Unit,
    viewModel: LessonViewModel = viewModel(factory = LessonViewModel.Factory),
) {
    val lesson = viewModel.lesson
    val completed by viewModel.isCompleted.collectAsStateWithLifecycle()

    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = "Leçon", onBack = onBack)
        if (lesson == null) {
            EmptyState("Leçon introuvable", "Cette leçon n'existe pas.")
            return@Column
        }
        LazyColumn(contentPadding = ScreenPadding, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                Column {
                    Text(lesson.title, style = MaterialTheme.typography.headlineSmall)
                    Spacer(Modifier.height(8.dp))
                    StatusBadge(lesson.status)
                    lesson.manualRefs.forEach { Spacer(Modifier.height(4.dp)); ManualRefLine(it) }
                }
            }
            if (lesson.status == ContentStatus.AWAITING_MANUAL) {
                item { AwaitingManualNotice() }
            }
            item { TextBlock("1 · Objectif", lesson.objective) }
            item { TextBlock("2 · Explication simple", lesson.explanation) }
            item {
                ListBlock(
                    "3 · Commandes utilisées",
                    lesson.controls.map { c -> "${c.name} — ${c.role}" + (c.manualRef?.let { " (${it.doc.label.lowercase()} p. ${it.pages})" } ?: "") },
                    numbered = false,
                )
            }
            item { ListBlock("4 · Sur ta machine, étape par étape", lesson.steps, numbered = true) }
            item { TextBlock("5 · Résultat attendu", lesson.expectedResult) }
            item {
                val ex = lesson.exercise
                ListBlock("6 · Exercice", ex?.let { listOf(it.title) + it.instructions + "Réussi si : ${it.successCriteria}" } ?: emptyList(), numbered = false)
            }
            item { ListBlock("7 · Erreurs fréquentes", lesson.commonMistakes, numbered = false) }
            item {
                SectionCard {
                    Eyebrow("8 · Quiz")
                    Spacer(Modifier.height(8.dp))
                    if (lesson.quiz.isEmpty()) {
                        PendingText()
                    } else {
                        Text("${lesson.quiz.size} questions pour valider la leçon.", style = MaterialTheme.typography.bodyMedium)
                        Spacer(Modifier.height(10.dp))
                        OutlinedButton(onClick = { onOpenQuiz(lesson.id) }) {
                            Icon(Icons.Outlined.Quiz, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Lancer le quiz")
                        }
                    }
                }
            }
            item {
                Button(onClick = viewModel::toggleCompleted, modifier = Modifier.fillMaxWidth()) {
                    if (completed) {
                        Icon(Icons.Filled.CheckCircle, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                    }
                    Text(if (completed) "Leçon terminée (annuler)" else "J'ai fait la leçon sur ma machine")
                }
            }
        }
    }
}

@Composable
private fun PendingText() {
    Text(
        "À rédiger depuis le manuel officiel.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
private fun TextBlock(title: String, body: String?) {
    SectionCard {
        Eyebrow(title)
        Spacer(Modifier.height(8.dp))
        if (body.isNullOrBlank()) PendingText() else Text(body, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun ListBlock(title: String, lines: List<String>, numbered: Boolean) {
    SectionCard {
        Eyebrow(title)
        Spacer(Modifier.height(8.dp))
        if (lines.isEmpty()) {
            PendingText()
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                lines.forEachIndexed { index, line ->
                    Row {
                        Text(
                            if (numbered) "${index + 1}." else "•",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.width(28.dp),
                        )
                        Text(line, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}
