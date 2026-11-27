package com.livemachine.academy.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.livemachine.academy.BuildConfig
import com.livemachine.academy.data.commerce.EntitlementRepository
import com.livemachine.academy.data.content.MachineCatalog
import com.livemachine.academy.domain.commerce.Availability
import com.livemachine.academy.domain.commerce.Entitlements
import com.livemachine.academy.domain.commerce.Feature
import com.livemachine.academy.domain.commerce.Plan
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
    entitlementRepository: EntitlementRepository,
) : ViewModel() {
    val entitlements: StateFlow<Entitlements> = entitlementRepository.entitlements
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Entitlements.Free)

    val settings: StateFlow<AppSettings> = settingsRepository.settings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AppSettings())

    fun setTextSize(size: TextSize) {
        viewModelScope.launch { settingsRepository.setTextSize(size) }
    }

    fun setKeepScreenOn(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setKeepScreenOn(enabled) }
    }

    fun setUnlockAll(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setUnlockAllModules(enabled) }
    }

    /** Efface la progression de la machine choisie, celle des autres machines est conservée. */
    fun resetProgress() {
        viewModelScope.launch { progressRepository.reset(settings.value.selectedMachineId) }
    }

    companion object {
        val Factory = appViewModelFactory { c, _ -> SettingsViewModel(c.settingsRepository, c.progressRepository, c.entitlementRepository) }
    }
}

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory),
) {
    val settings by viewModel.settings.collectAsStateWithLifecycle()
    val entitlements by viewModel.entitlements.collectAsStateWithLifecycle()
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
                    Spacer(Modifier.width(16.dp))
                    Switch(checked = settings.keepScreenOn, onCheckedChange = viewModel::setKeepScreenOn)
                }
            }
            SectionCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Mode libre", style = MaterialTheme.typography.titleMedium)
                        Text(
                            "Tous les modules et défis débloqués, sans valider les modules précédents.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    Spacer(Modifier.width(16.dp))
                    Switch(checked = settings.unlockAllModules, onCheckedChange = viewModel::setUnlockAll)
                }
            }
            SectionCard {
                Eyebrow("Ton offre")
                Spacer(Modifier.height(8.dp))
                Text(if (entitlements.plan == Plan.PREMIUM) "Premium" else "Gratuite", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(4.dp))
                Text(
                    "Tout le contenu actuel est inclus, hors ligne. Prévu plus tard : " +
                        Feature.entries.filter { it.availability == Availability.PLANNED }.joinToString(", ") { it.label.lowercase() } + ".",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            SectionCard {
                Eyebrow("Progression")
                Spacer(Modifier.height(8.dp))
                Text(
                    "Chaque machine a sa propre sauvegarde. Seule celle de la ${MachineCatalog.byId(settings.selectedMachineId).name} sera effacée.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(Modifier.height(8.dp))
                OutlinedButton(onClick = { confirmReset = true }) { Text("Réinitialiser la ${MachineCatalog.byId(settings.selectedMachineId).name}") }
            }
            SectionCard {
                Eyebrow("À propos")
                Spacer(Modifier.height(8.dp))
                Text("Live Machine Academy · version ${BuildConfig.VERSION_NAME}", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(6.dp))
                Text(
                    "Application hors ligne. Sources pédagogiques : " + MachineCatalog.all.joinToString(" ") { "${it.fullName} : ${it.sourcesLabel}" } + " " +
                        "Chaque leçon cite le chapitre et les pages du manuel. Application non officielle, non affiliée à Korg ni à Novation.",
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
            text = { Text("Leçons, étapes, exercices, défis, ateliers, scores et erreurs à réviser de la ${MachineCatalog.byId(settings.selectedMachineId).name} seront effacés.") },
            confirmButton = {
                TextButton(onClick = { viewModel.resetProgress(); confirmReset = false }) { Text("Effacer") }
            },
            dismissButton = { TextButton(onClick = { confirmReset = false }) { Text("Annuler") } },
        )
    }
}
