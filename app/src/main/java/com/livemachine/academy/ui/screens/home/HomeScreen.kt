package com.livemachine.academy.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
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
import androidx.compose.material.icons.outlined.AutoAwesome
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
    onOpenAssistant: () -> Unit = {},
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
                Eyebrow("Ta machine", color = MaterialTheme.colorScheme.secondary)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    state.machines.forEach { m ->
                        val selected = m.id == state.machine.id
                        Surface(
                            onClick = { viewModel.selectMachine(m.id) },
                            shape = RoundedCornerShape(16.dp),
                            color = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerHigh,
                            border = BorderStroke(2.dp, if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant),
                            modifier = Modifier.weight(1f),
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text(m.manufacturer, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                                Text(m.name, style = MaterialTheme.typography.titleMedium)
                                Text(
                                    if (m.hasCourse) "${m.lessons.size} leçons" else "Parcours à venir",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                }
            }
            item {
                val machine = state.machine
                HeroBanner {
                    Eyebrow(machine.fullName, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Spacer(Modifier.height(6.dp))
                    Text("Apprends ici, joue sur ta machine.", style = MaterialTheme.typography.headlineSmall)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        if (machine.hasCourse) "Lis la leçon sur ton téléphone, puis refais chaque étape sur ta vraie ${machine.name}."
                        else "Le parcours de la ${machine.name} sera rédigé à partir de son manuel officiel.",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    if (machine.hasCourse) {
                        Spacer(Modifier.height(16.dp))
                        ProgressLine(state.fraction)
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "${state.completedLessons} / ${state.totalLessons} leçons terminées",
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    val next = state.nextLesson
                    Button(onClick = {
                        when {
                            next != null -> onOpenLesson(next.id)
                            machine.hasCourse -> onOpenPath()
                            else -> onOpenMachine()
                        }
                    }) {
                        Text(
                            when {
                                next != null -> "Continuer : ${next.title}"
                                machine.hasCourse -> "Voir le parcours"
                                else -> "Voir la fiche machine"
                            },
                        )
                        Spacer(Modifier.width(8.dp))
                        Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null)
                    }
                }
            }
            item { Shortcut(Icons.Outlined.Route, "Parcours", if (state.modules.isEmpty()) "Parcours à venir" else "Les ${state.modules.size} modules dans l'ordre conseillé", onOpenPath) }
            item { Shortcut(Icons.Outlined.GridView, "Modules", "Accès direct à chaque module", onOpenModules) }
            if (state.reviewCount > 0) {
                item { Shortcut(Icons.Outlined.Replay, "Réviser mes erreurs", "${state.reviewCount} question(s) à revoir", onOpenReview) }
            }
            item { Shortcut(Icons.Outlined.AutoAwesome, "Assistant IA", "Questions sur ta ${state.machine.name}, idées de live", onOpenAssistant) }
            item { Shortcut(Icons.Outlined.FitnessCenter, "Exercices", "À faire sur la vraie machine", onOpenExercises) }
            if (state.workshops.isNotEmpty()) {
                item { Shortcut(Icons.Outlined.EmojiEvents, "Ateliers et défis", "Création guidée et défis par style", onOpenChallenges) }
            } else if (state.challenges.isNotEmpty()) {
                item { Shortcut(Icons.Outlined.EmojiEvents, "Défis par style", "Hardtek, tribe, acidcore, mentalcore", onOpenChallenges) }
            }
            item { Shortcut(Icons.Outlined.Tune, "La machine", "Fiche de ta ${state.machine.name}", onOpenMachine) }
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
