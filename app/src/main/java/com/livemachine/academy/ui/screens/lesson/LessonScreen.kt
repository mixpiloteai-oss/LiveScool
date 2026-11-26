package com.livemachine.academy.ui.screens.lesson

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import com.livemachine.academy.data.model.UserProgress
import com.livemachine.academy.data.repository.CourseRepository
import com.livemachine.academy.data.repository.ProgressRepository
import com.livemachine.academy.domain.ProgressRules
import com.livemachine.academy.ui.appViewModelFactory
import com.livemachine.academy.ui.components.AppTopBar
import com.livemachine.academy.ui.components.AwaitingManualNotice
import com.livemachine.academy.ui.components.EmptyState
import com.livemachine.academy.ui.components.Eyebrow
import com.livemachine.academy.ui.components.ManualRefLine
import com.livemachine.academy.ui.components.ProgressLine
import com.livemachine.academy.ui.components.ScreenPadding
import com.livemachine.academy.ui.components.SectionCard
import com.livemachine.academy.ui.components.StatusBadge
import com.livemachine.academy.ui.navigation.LessonRoute
import com.livemachine.academy.ui.theme.Acid
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LessonViewModel(
    savedStateHandle: SavedStateHandle,
    course: CourseRepository,
    private val progressRepository: ProgressRepository,
) : ViewModel() {
    val lesson: Lesson? = course.lesson(savedStateHandle.toRoute<LessonRoute>().lessonId)

    val progress: StateFlow<UserProgress> = progressRepository.progress
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UserProgress())

    fun toggleCompleted() {
        val id = lesson?.id ?: return
        viewModelScope.launch { progressRepository.setLessonCompleted(id, !progress.value.isLessonDone(id)) }
    }

    fun toggleStep(index: Int) {
        val id = lesson?.id ?: return
        viewModelScope.launch { progressRepository.setStepCompleted(id, index, !progress.value.isStepDone(id, index)) }
    }

    fun toggleExercise() {
        val id = lesson?.exercise?.id ?: return
        viewModelScope.launch { progressRepository.setExerciseCompleted(id, id !in progress.value.completedExercises) }
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
    val progress by viewModel.progress.collectAsStateWithLifecycle()

    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = "Leçon", onBack = onBack)
        if (lesson == null) {
            EmptyState("Leçon introuvable", "Cette leçon n'existe pas.")
            return@Column
        }
        val completed = progress.isLessonDone(lesson.id)
        val stepsDone = lesson.steps.indices.count { progress.isStepDone(lesson.id, it) }
        val exerciseDone = lesson.exercise?.let { it.id in progress.completedExercises } == true
        val bestScore = progress.bestQuizScores[lesson.id]
        val toReview = lesson.quiz.count { it.id in progress.wrongQuestions }

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
                )
            }
            item {
                SectionCard {
                    Eyebrow("4 · Sur ta machine, étape par étape")
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Coche chaque étape une fois faite sur ta vraie Electribe.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(Modifier.height(8.dp))
                    if (lesson.steps.isEmpty()) PendingText()
                    lesson.steps.forEachIndexed { index, step ->
                        val done = progress.isStepDone(lesson.id, index)
                        Row(
                            Modifier.fillMaxWidth().clickable { viewModel.toggleStep(index) }.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.Top,
                        ) {
                            Checkbox(checked = done, onCheckedChange = { viewModel.toggleStep(index) })
                            Text(
                                "${index + 1}. $step",
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (done) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(top = 10.dp),
                            )
                        }
                    }
                    if (lesson.steps.isNotEmpty()) {
                        Spacer(Modifier.height(8.dp))
                        ProgressLine(stepsDone.toFloat() / lesson.steps.size)
                        Spacer(Modifier.height(4.dp))
                        Text("$stepsDone / ${lesson.steps.size} étapes faites", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
            item { TextBlock("5 · Résultat attendu", lesson.expectedResult) }
            item {
                val ex = lesson.exercise
                SectionCard {
                    Eyebrow("6 · Exercice")
                    Spacer(Modifier.height(8.dp))
                    if (ex == null) {
                        PendingText()
                    } else {
                        Text(ex.title, style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(6.dp))
                        ex.instructions.forEach { Text("• $it", style = MaterialTheme.typography.bodyLarge) }
                        Spacer(Modifier.height(8.dp))
                        Text("Réussi si : ${ex.successCriteria}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                        ex.styleHint?.let {
                            Spacer(Modifier.height(6.dp))
                            Text(it, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                        }
                        Spacer(Modifier.height(10.dp))
                        if (exerciseDone) {
                            OutlinedButton(onClick = viewModel::toggleExercise) {
                                Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = Acid)
                                Spacer(Modifier.width(8.dp))
                                Text("Exercice validé (annuler)")
                            }
                        } else {
                            Button(onClick = viewModel::toggleExercise) { Text("J'ai réussi l'exercice") }
                        }
                    }
                }
            }
            item { ListBlock("7 · Erreurs fréquentes", lesson.commonMistakes) }
            item {
                SectionCard {
                    Eyebrow("8 · Quiz")
                    Spacer(Modifier.height(8.dp))
                    if (lesson.quiz.isEmpty()) {
                        PendingText()
                    } else {
                        Text("${lesson.quiz.size} questions, réussite à 80 %.", style = MaterialTheme.typography.bodyMedium)
                        bestScore?.let {
                            val ok = it.ratio >= ProgressRules.PASS_RATIO
                            Text(
                                "Meilleur score : ${it.correct}/${it.total}" + if (ok) " · réussi" else "",
                                style = MaterialTheme.typography.labelLarge,
                                color = if (ok) Acid else MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        if (toReview > 0) {
                            Text(
                                "$toReview question(s) de cette leçon à réviser.",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.secondary,
                            )
                        }
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
private fun ListBlock(title: String, lines: List<String>) {
    SectionCard {
        Eyebrow(title)
        Spacer(Modifier.height(8.dp))
        if (lines.isEmpty()) {
            PendingText()
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                lines.forEach { line ->
                    Row {
                        Text("•", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.width(28.dp))
                        Text(line, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}
