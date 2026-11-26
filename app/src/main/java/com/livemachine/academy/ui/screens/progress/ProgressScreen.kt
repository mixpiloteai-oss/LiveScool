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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.livemachine.academy.domain.ProgressRules
import com.livemachine.academy.ui.components.AppTopBar
import com.livemachine.academy.ui.components.Eyebrow
import com.livemachine.academy.ui.components.HeroBanner
import com.livemachine.academy.ui.components.ProgressLine
import com.livemachine.academy.ui.components.ScreenPadding
import com.livemachine.academy.ui.components.SectionCard
import com.livemachine.academy.ui.screens.OverviewViewModel
import com.livemachine.academy.ui.screens.modules.ModuleRow
import com.livemachine.academy.ui.theme.Acid

@Composable
fun ProgressScreen(
    onOpenModule: (String) -> Unit,
    onOpenExercises: () -> Unit,
    onOpenChallenges: () -> Unit,
    onOpenReview: () -> Unit,
    onOpenSettings: () -> Unit,
    viewModel: OverviewViewModel = viewModel(factory = OverviewViewModel.Factory),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val quizzes = state.progress.bestQuizScores
    val moduleNames = state.modules.associate { ProgressRules.finalQuizId(it.module.id) to "Quiz final · Module ${it.module.number}" }

    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = "Progression", onOpenSettings = onOpenSettings)
        LazyColumn(contentPadding = ScreenPadding, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                HeroBanner {
                    Eyebrow(state.machine.fullName, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Text("${(state.fraction * 100).toInt()} %", style = MaterialTheme.typography.displaySmall)
                    Spacer(Modifier.height(8.dp))
                    ProgressLine(state.fraction)
                    Spacer(Modifier.height(12.dp))
                    Row {
                        Stat("Leçons", "${state.completedLessons}/${state.totalLessons}", Modifier.weight(1f))
                        Stat("Exercices", "${state.progress.completedExercises.size}/${state.exercises.size}", Modifier.weight(1f))
                    }
                    Spacer(Modifier.height(8.dp))
                    Row {
                        Stat("Compétences", "${state.modules.count { it.competenceAcquired }}/${state.modules.size}", Modifier.weight(1f))
                        Stat("Défis", "${state.challenges.count { it.id in state.progress.completedChallenges }}/${state.challenges.size}", Modifier.weight(1f))
                    }
                    if (state.workshops.isNotEmpty()) {
                        Spacer(Modifier.height(8.dp))
                        Row {
                            Stat("Ateliers", "${state.workshops.count { it.id in state.progress.completedChallenges }}/${state.workshops.size}", Modifier.weight(1f))
                            Spacer(Modifier.weight(1f))
                        }
                    }
                }
            }
            item {
                SectionCard {
                    Eyebrow("Points à réviser", color = MaterialTheme.colorScheme.secondary)
                    Spacer(Modifier.height(6.dp))
                    if (state.reviewCount == 0) {
                        Text("Aucune erreur en attente. Les questions ratées dans les quiz arriveront ici.", style = MaterialTheme.typography.bodyMedium)
                    } else {
                        state.reviewItems.forEach {
                            Text("• ${it.lessonTitle} : ${it.count} question(s)", style = MaterialTheme.typography.bodyMedium)
                        }
                        Spacer(Modifier.height(10.dp))
                        Button(onClick = onOpenReview) { Text("Réviser mes erreurs (${state.reviewCount})") }
                    }
                }
            }
            item {
                Row {
                    SectionCard(Modifier.weight(1f), onClick = onOpenExercises) {
                        Text("Exercices", style = MaterialTheme.typography.titleMedium)
                    }
                    Spacer(Modifier.width(12.dp))
                    SectionCard(Modifier.weight(1f), onClick = onOpenChallenges) {
                        Text(if (state.workshops.isEmpty()) "Défis par style" else "Ateliers et défis", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
            item { Text("Compétences", style = MaterialTheme.typography.titleLarge) }
            items(state.modules, key = { "c-" + it.module.id }) { item ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.WorkspacePremium,
                        contentDescription = null,
                        tint = if (item.competenceAcquired) Acid else MaterialTheme.colorScheme.outline,
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Module ${item.module.number} · ${item.module.title}", style = MaterialTheme.typography.labelLarge)
                        Text(
                            item.module.goal,
                            style = MaterialTheme.typography.bodySmall,
                            color = if (item.competenceAcquired) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
            item { Text("Par module", style = MaterialTheme.typography.titleLarge) }
            items(state.modules, key = { it.module.id }) { item ->
                ModuleRow(item) { onOpenModule(item.module.id) }
            }
            if (quizzes.isNotEmpty()) {
                item { Text("Meilleurs scores aux quiz", style = MaterialTheme.typography.titleLarge) }
                items(quizzes.entries.sortedBy { it.key }, key = { "s-" + it.key }) { (quizId, score) ->
                    SectionCard {
                        Row {
                            Text(moduleNames[quizId] ?: state.lessonTitles[quizId] ?: quizId, Modifier.weight(1f))
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "${score.correct}/${score.total}",
                                color = if (score.ratio >= ProgressRules.PASS_RATIO) Acid else MaterialTheme.colorScheme.primary,
                            )
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
