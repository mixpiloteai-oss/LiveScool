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
import com.livemachine.academy.data.model.MusicStyle
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
        AppTopBar(title = "Défis par style", onBack = onBack)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        ) {
            item { FilterChip(selected = style == null, onClick = { style = null }, label = { Text("Tous") }) }
            items(MusicStyle.entries) { s ->
                FilterChip(selected = style == s, onClick = { style = if (style == s) null else s }, label = { Text(s.label) })
            }
        }
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
