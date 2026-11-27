package com.livemachine.academy.ui.screens.legal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.toRoute
import com.livemachine.academy.data.assistant.AssistantHistoryRepository
import com.livemachine.academy.data.commerce.EntitlementRepository
import com.livemachine.academy.data.commerce.InstallIdRepository
import com.livemachine.academy.data.content.LegalTexts
import com.livemachine.academy.ui.appViewModelFactory
import com.livemachine.academy.ui.components.AppTopBar
import com.livemachine.academy.ui.components.Eyebrow
import com.livemachine.academy.ui.components.SectionCard
import com.livemachine.academy.ui.navigation.LegalRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LegalViewModel(
    savedStateHandle: SavedStateHandle,
    private val history: AssistantHistoryRepository,
    private val installIds: InstallIdRepository,
    private val entitlements: EntitlementRepository,
) : ViewModel() {
    val privacy: Boolean = savedStateHandle.toRoute<LegalRoute>().kind == LegalRoute.PRIVACY
    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()

    fun clearAssistantHistory() {
        viewModelScope.launch {
            history.clearAll()
            _message.value = "Historique de l'assistant effacé pour toutes les machines."
        }
    }

    fun resetInstallId() {
        viewModelScope.launch {
            installIds.reset()
            entitlements.clearLocal()
            _message.value = "Nouvel identifiant d'installation créé. Un abonnement se récupère avec « Restaurer mes achats »."
        }
    }

    companion object {
        val Factory = appViewModelFactory { c, handle ->
            LegalViewModel(handle, c.assistantHistoryRepository, c.installIdRepository, c.entitlementRepository)
        }
    }
}

/** Politique de confidentialité ou conditions d'utilisation, affichées en texte natif (pas de WebView). */
@Composable
fun LegalScreen(
    onBack: () -> Unit,
    viewModel: LegalViewModel = viewModel(factory = LegalViewModel.Factory),
) {
    val message by viewModel.message.collectAsStateWithLifecycle()
    var confirmReset by rememberSaveable { mutableStateOf(false) }
    val sections = if (viewModel.privacy) LegalTexts.privacy else LegalTexts.terms

    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = if (viewModel.privacy) "Confidentialité" else "Conditions d'utilisation", onBack = onBack)
        Column(
            Modifier.verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            if (viewModel.privacy) {
                SectionCard {
                    Eyebrow("Tes données sur ce téléphone")
                    Spacer(Modifier.height(8.dp))
                    OutlinedButton(onClick = viewModel::clearAssistantHistory) { Text("Effacer l'historique de l'assistant") }
                    OutlinedButton(onClick = { confirmReset = true }) { Text("Réinitialiser l'identifiant d'installation") }
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Progression : Paramètres › Réinitialiser. Photo de la machine : écran Photo › Supprimer.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    message?.let {
                        Spacer(Modifier.height(6.dp))
                        Text(it, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
            Text("Dernière mise à jour : ${LegalTexts.UPDATED}", style = MaterialTheme.typography.labelMedium)
            sections.forEach { s ->
                SectionCard {
                    Eyebrow(s.title)
                    Spacer(Modifier.height(6.dp))
                    Text(s.text, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }

    if (confirmReset) {
        AlertDialog(
            onDismissRequest = { confirmReset = false },
            title = { Text("Nouvel identifiant ?") },
            text = { Text("Tes compteurs d'utilisation en ligne repartent de zéro sous un nouvel identifiant. Un abonnement Premium se récupère ensuite avec « Restaurer mes achats ».") },
            confirmButton = { TextButton(onClick = { viewModel.resetInstallId(); confirmReset = false }) { Text("Réinitialiser") } },
            dismissButton = { TextButton(onClick = { confirmReset = false }) { Text("Annuler") } },
        )
    }
}
