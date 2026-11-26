package com.livemachine.academy.ui.screens.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.livemachine.academy.ui.components.AppTopBar
import com.livemachine.academy.ui.components.Eyebrow
import com.livemachine.academy.ui.components.HeroBanner
import com.livemachine.academy.ui.components.ProgressLine
import com.livemachine.academy.ui.components.ScreenPadding
import com.livemachine.academy.ui.components.SectionCard
import com.livemachine.academy.ui.screens.OverviewViewModel
import com.livemachine.academy.ui.screens.modules.ModuleRow

@Composable
fun ProgressScreen(
    onOpenModule: (String) -> Unit,
    onOpenExercises: () -> Unit,
    onOpenSettings: () -> Unit,
    viewModel: OverviewViewModel = viewModel(factory = OverviewViewModel.Factory),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val quizzes = state.progress.bestQuizScores

    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = "Progression", onOpenSettings = onOpenSettings)
        LazyColumn(contentPadding = ScreenPadding, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                HeroBanner {
                    Eyebrow("Global", color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Text("${(state.fraction * 100).toInt()} %", style = MaterialTheme.typography.displaySmall)
                    Spacer(Modifier.height(8.dp))
                    ProgressLine(state.fraction)
                    Spacer(Modifier.height(12.dp))
                    Row {
                        Stat("Leçons", "${state.completedLessons}/${state.totalLessons}", Modifier.weight(1f))
                        Stat("Exercices", "${state.progress.completedExercises.size}/${state.exercises.size}", Modifier.weight(1f))
                        Stat("Quiz réussis", "${quizzes.values.count { it.ratio >= 0.8f }}", Modifier.weight(1f))
                    }
                }
            }
            item {
                SectionCard(onClick = onOpenExercises) {
                    Text("Voir les exercices pratiques", style = MaterialTheme.typography.titleMedium)
                }
            }
            item { Text("Par module", style = MaterialTheme.typography.titleLarge) }
            items(state.modules, key = { it.module.id }) { item ->
                ModuleRow(item) { onOpenModule(item.module.id) }
            }
            if (quizzes.isNotEmpty()) {
                item { Text("Meilleurs scores aux quiz", style = MaterialTheme.typography.titleLarge) }
                items(quizzes.entries.toList(), key = { it.key }) { (lessonId, score) ->
                    SectionCard {
                        Row {
                            Text(state.lessonTitles[lessonId] ?: lessonId, Modifier.weight(1f))
                            Spacer(Modifier.width(8.dp))
                            Text("${score.correct}/${score.total}", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Stat(label: String, value: String, modifier: Modifier) {
    Column(modifier) {
        Text(value, style = MaterialTheme.typography.titleLarge)
        Text(label, style = MaterialTheme.typography.labelMedium)
    }
}
