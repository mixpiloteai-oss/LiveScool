package com.livemachine.academy.ui.screens.exercises

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.livemachine.academy.ui.components.AppTopBar
import com.livemachine.academy.ui.components.AwaitingManualNotice
import com.livemachine.academy.ui.components.ScreenPadding
import com.livemachine.academy.ui.components.SectionCard
import com.livemachine.academy.ui.screens.OverviewViewModel

/** Tous les exercices pratiques, à faire sur la vraie machine. */
@Composable
fun ExercisesScreen(
    onBack: () -> Unit,
    onOpenLesson: (String) -> Unit,
    viewModel: OverviewViewModel = viewModel(factory = OverviewViewModel.Factory),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = "Exercices", onBack = onBack)
        LazyColumn(contentPadding = ScreenPadding, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                Text(
                    "Chaque exercice se fait sur ta vraie Electribe. Coche-le quand tu as obtenu le résultat demandé.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            if (state.exercises.isEmpty()) {
                item {
                    AwaitingManualNotice(
                        message = "Les exercices arriveront avec les leçons, rédigées à partir du manuel officiel.",
                    )
                }
            }
            items(state.exercises, key = { it.id }) { ex ->
                val done = ex.id in state.progress.completedExercises
                SectionCard {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = done, onCheckedChange = { viewModel.toggleExercise(ex.id) })
                        Column(Modifier.weight(1f)) {
                            Text(ex.title, style = MaterialTheme.typography.titleMedium)
                            Text(
                                state.lessonTitles[ex.lessonId].orEmpty(),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                    ex.styleHint?.let {
                        Spacer(Modifier.height(8.dp))
                        Text(it, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                    }
                    TextButton(onClick = { onOpenLesson(ex.lessonId) }) { Text("Ouvrir la leçon") }
                }
            }
        }
    }
}
