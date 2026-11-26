package com.livemachine.academy.ui.screens.challenges

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.livemachine.academy.data.model.CreativeWorkshop
import com.livemachine.academy.data.model.MusicStyle
import com.livemachine.academy.data.model.UserProgress
import com.livemachine.academy.ui.components.AppTopBar
import com.livemachine.academy.ui.components.Eyebrow
import com.livemachine.academy.ui.components.ManualRefLine
import com.livemachine.academy.ui.components.SectionCard
import com.livemachine.academy.ui.screens.OverviewViewModel
import com.livemachine.academy.ui.theme.Acid

@Composable
fun ChallengesScreen(
    onBack: () -> Unit,
    viewModel: OverviewViewModel = viewModel(factory = OverviewViewModel.Factory),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var style by rememberSaveable { mutableStateOf<MusicStyle?>(null) }
    val shown = state.challenges.filter { style == null || it.style == style }

    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = if (state.workshops.isEmpty()) "Défis par style" else "Ateliers et défis", onBack = onBack)
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Text(
                    "À réaliser sur ta machine. Chaque défi s'ouvre quand le module indiqué est validé ; valide-le toi-même quand le critère est atteint.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            if (state.workshops.isNotEmpty()) {
                item { Text("Ateliers de création", style = MaterialTheme.typography.titleLarge) }
                items(state.workshops, key = { it.id }) { w ->
                    WorkshopCard(
                        workshop = w,
                        progress = state.progress,
                        open = state.unlockAll || state.isModuleValidated(w.requiredModuleId),
                        moduleLabel = state.modules.firstOrNull { it.module.id == w.requiredModuleId }?.module
                            ?.let { "Module ${it.number} (${it.title})" } ?: "",
                        onToggleStep = { viewModel.toggleWorkshopStep(w.id, it) },
                        onValidate = { viewModel.toggleChallenge(w.id) },
                    )
                }
                item { Text("Défis par style", style = MaterialTheme.typography.titleLarge) }
            }
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item { FilterChip(selected = style == null, onClick = { style = null }, label = { Text("Tous") }) }
                    items(MusicStyle.entries) { s ->
                        FilterChip(selected = style == s, onClick = { style = if (style == s) null else s }, label = { Text(s.label) })
                    }
                }
            }
            if (state.challenges.isEmpty()) {
                item {
                    Text(
                        "Pas encore de défis pour la ${state.machine.name} : ils arriveront avec son parcours.",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            items(shown, key = { it.id }) { ch ->
                val module = state.modules.firstOrNull { it.module.id == ch.requiredModuleId }?.module
                val open = state.unlockAll || state.isModuleValidated(ch.requiredModuleId)
                val done = ch.id in state.progress.completedChallenges
                SectionCard {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Eyebrow(ch.style.label, color = MaterialTheme.colorScheme.secondary)
                            Text(ch.title, style = MaterialTheme.typography.titleMedium)
                        }
                        when {
                            done -> Icon(Icons.Filled.CheckCircle, contentDescription = "Validé", tint = Acid)
                            !open -> Icon(Icons.Outlined.Lock, contentDescription = "Verrouillé", tint = MaterialTheme.colorScheme.outline)
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(ch.goal, style = MaterialTheme.typography.bodyMedium)
                    if (!open) {
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "Se débloque quand le Module ${module?.number ?: "?"} (${module?.title ?: ""}) est validé.",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    } else {
                        Spacer(Modifier.height(8.dp))
                        ch.steps.forEachIndexed { i, step -> Text("${i + 1}. $step", style = MaterialTheme.typography.bodyMedium) }
                        Spacer(Modifier.height(6.dp))
                        Text("Réussi si : ${ch.successCriteria}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(6.dp))
                        ch.manualRefs.forEach { ManualRefLine(it) }
                        Spacer(Modifier.height(10.dp))
                        if (done) {
                            OutlinedButton(onClick = { viewModel.toggleChallenge(ch.id) }) { Text("Défi validé (annuler)") }
                        } else {
                            Button(onClick = { viewModel.toggleChallenge(ch.id) }) { Text("J'ai réussi le défi") }
                        }
                    }
                }
            }
            item { Spacer(Modifier.width(1.dp)) }
        }
    }
}

/** Atelier de création : étapes à cocher sur la machine, puis validation manuelle. */
@Composable
private fun WorkshopCard(
    workshop: CreativeWorkshop,
    progress: UserProgress,
    open: Boolean,
    moduleLabel: String,
    onToggleStep: (Int) -> Unit,
    onValidate: () -> Unit,
) {
    val done = workshop.id in progress.completedChallenges
    val stepsDone = workshop.steps.indices.count { progress.isStepDone(workshop.id, it) }
    SectionCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Eyebrow(workshop.kind.label, color = MaterialTheme.colorScheme.secondary)
                Text(workshop.title, style = MaterialTheme.typography.titleMedium)
            }
            when {
                done -> Icon(Icons.Filled.CheckCircle, contentDescription = "Validé", tint = Acid)
                !open -> Icon(Icons.Outlined.Lock, contentDescription = "Verrouillé", tint = MaterialTheme.colorScheme.outline)
            }
        }
        Spacer(Modifier.height(6.dp))
        Text(workshop.goal, style = MaterialTheme.typography.bodyMedium)
        if (!open) {
            Spacer(Modifier.height(6.dp))
            Text(
                "Se débloque quand le $moduleLabel est validé.",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            return@SectionCard
        }
        Spacer(Modifier.height(8.dp))
        Text("Étapes sur la machine : $stepsDone/${workshop.steps.size}", style = MaterialTheme.typography.labelLarge)
        workshop.steps.forEachIndexed { i, step ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = progress.isStepDone(workshop.id, i), onCheckedChange = { onToggleStep(i) })
                Text("${i + 1}. $step", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
            }
        }
        Spacer(Modifier.height(6.dp))
        Text("Réussi si : ${workshop.successCriteria}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(6.dp))
        workshop.manualRefs.forEach { ManualRefLine(it) }
        Spacer(Modifier.height(10.dp))
        when {
            done -> OutlinedButton(onClick = onValidate) { Text("Atelier validé (annuler)") }
            stepsDone < workshop.steps.size -> OutlinedButton(onClick = {}, enabled = false) { Text("Coche toutes les étapes pour valider") }
            else -> Button(onClick = onValidate) { Text("J'ai réussi l'atelier") }
        }
    }
}
