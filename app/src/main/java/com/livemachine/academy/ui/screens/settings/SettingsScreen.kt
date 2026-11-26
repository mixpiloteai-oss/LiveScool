package com.livemachine.academy.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.livemachine.academy.data.content.MachineContent
import com.livemachine.academy.data.model.AppSettings
import com.livemachine.academy.data.model.TextSize
import com.livemachine.academy.data.repository.ProgressRepository
import com.livemachine.academy.data.repository.SettingsRepository
import com.livemachine.academy.ui.appViewModelFactory
import com.livemachine.academy.ui.components.AppTopBar
import com.livemachine.academy.ui.components.Eyebrow
import com.livemachine.academy.ui.components.SectionCard
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val progressRepository: ProgressRepository,
) : ViewModel() {
    val settings: StateFlow<AppSettings> = settingsRepository.settings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AppSettings())

    fun setTextSize(size: TextSize) {
        viewModelScope.launch { settingsRepository.setTextSize(size) }
    }

    fun setKeepScreenOn(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setKeepScreenOn(enabled) }
    }

    fun resetProgress() {
        viewModelScope.launch { progressRepository.reset() }
    }

    companion object {
        val Factory = appViewModelFactory { c, _ -> SettingsViewModel(c.settingsRepository, c.progressRepository) }
    }
}

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory),
) {
    val settings by viewModel.settings.collectAsStateWithLifecycle()
    var confirmReset by rememberSaveable { mutableStateOf(false) }

    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = "Paramètres", onBack = onBack)
        Column(
            Modifier.verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            SectionCard {
                Eyebrow("Taille du texte")
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextSize.entries.forEach { size ->
                        FilterChip(
                            selected = settings.textSize == size,
                            onClick = { viewModel.setTextSize(size) },
                            label = { Text(size.label) },
                        )
                    }
                }
            }
            SectionCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Garder l'écran allumé", style = MaterialTheme.typography.titleMedium)
                        Text(
                            "Pratique quand tu suis une leçon les mains sur la machine.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    Switch(checked = settings.keepScreenOn, onCheckedChange = viewModel::setKeepScreenOn)
                }
            }
            SectionCard {
                Eyebrow("Progression")
                Spacer(Modifier.height(8.dp))
                OutlinedButton(onClick = { confirmReset = true }) { Text("Réinitialiser ma progression") }
            }
            SectionCard {
                Eyebrow("À propos")
                Spacer(Modifier.height(8.dp))
                Text("Live Machine Academy · version 0.8.0", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(6.dp))
                Text(
                    "Application hors ligne. Source pédagogique unique : " + MachineContent.manualSource + " " +
                        "Chaque leçon cite le chapitre et les pages du manuel. Application non officielle, non affiliée à Korg.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }

    if (confirmReset) {
        AlertDialog(
            onDismissRequest = { confirmReset = false },
            title = { Text("Réinitialiser ?") },
            text = { Text("Les leçons, exercices et scores de quiz seront effacés.") },
            confirmButton = {
                TextButton(onClick = { viewModel.resetProgress(); confirmReset = false }) { Text("Effacer") }
            },
            dismissButton = { TextButton(onClick = { confirmReset = false }) { Text("Annuler") } },
        )
    }
}
