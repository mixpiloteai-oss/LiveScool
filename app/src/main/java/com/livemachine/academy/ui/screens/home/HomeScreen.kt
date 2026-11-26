package com.livemachine.academy.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Route
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.livemachine.academy.data.content.MachineContent
import com.livemachine.academy.ui.components.AppTopBar
import com.livemachine.academy.ui.components.Eyebrow
import com.livemachine.academy.ui.components.HeroBanner
import com.livemachine.academy.ui.components.ProgressLine
import com.livemachine.academy.ui.components.ScreenPadding
import com.livemachine.academy.ui.components.SectionCard
import com.livemachine.academy.ui.screens.OverviewViewModel

@Composable
fun HomeScreen(
    onOpenLesson: (String) -> Unit,
    onOpenPath: () -> Unit,
    onOpenModules: () -> Unit,
    onOpenExercises: () -> Unit,
    onOpenMachine: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenChallenges: () -> Unit,
    onOpenReview: () -> Unit,
    viewModel: OverviewViewModel = viewModel(factory = OverviewViewModel.Factory),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = "Live Machine Academy", onOpenSettings = onOpenSettings)
        LazyColumn(
            contentPadding = ScreenPadding,
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            item {
                HeroBanner {
                    Eyebrow(MachineContent.modelLabel, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Spacer(Modifier.height(6.dp))
                    Text("Apprends ici, joue sur ta machine.", style = MaterialTheme.typography.headlineSmall)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Lis la leçon sur ton téléphone, puis refais chaque étape sur ta vraie Electribe.",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(Modifier.height(16.dp))
                    ProgressLine(state.fraction)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "${state.completedLessons} / ${state.totalLessons} leçons terminées",
                        style = MaterialTheme.typography.labelMedium,
                    )
                    Spacer(Modifier.height(16.dp))
                    val next = state.nextLesson
                    Button(onClick = { if (next != null) onOpenLesson(next.id) else onOpenPath() }) {
                        Text(if (next != null) "Continuer : ${next.title}" else "Voir le parcours")
                        Spacer(Modifier.width(8.dp))
                        Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null)
                    }
                }
            }
            item { Shortcut(Icons.Outlined.Route, "Parcours", "Les 9 modules dans l'ordre conseillé", onOpenPath) }
            item { Shortcut(Icons.Outlined.GridView, "Modules", "Accès direct à chaque module", onOpenModules) }
            if (state.reviewCount > 0) {
                item { Shortcut(Icons.Outlined.Replay, "Réviser mes erreurs", "${state.reviewCount} question(s) à revoir", onOpenReview) }
            }
            item { Shortcut(Icons.Outlined.FitnessCenter, "Exercices", "À faire sur la vraie machine", onOpenExercises) }
            item { Shortcut(Icons.Outlined.EmojiEvents, "Défis par style", "Hardtek, tribe, acidcore, mentalcore", onOpenChallenges) }
            item { Shortcut(Icons.Outlined.Tune, "La machine", "Fiche de ton Electribe Sampler", onOpenMachine) }
            item {
                SectionCard {
                    Eyebrow("Styles visés", color = MaterialTheme.colorScheme.secondary)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Mentalcore · Tribe · TribeCore · Hardtek · Acidcore",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
        }
    }
}

@Composable
private fun Shortcut(icon: ImageVector, title: String, subtitle: String, onClick: () -> Unit) {
    SectionCard(onClick = onClick) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium)
                Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
