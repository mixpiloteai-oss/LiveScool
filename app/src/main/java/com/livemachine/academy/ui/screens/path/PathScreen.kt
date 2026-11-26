package com.livemachine.academy.ui.screens.path

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.livemachine.academy.ui.components.AppTopBar
import com.livemachine.academy.ui.components.AwaitingManualNotice
import com.livemachine.academy.ui.components.Eyebrow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import com.livemachine.academy.ui.components.ScreenPadding
import com.livemachine.academy.ui.screens.OverviewViewModel
import com.livemachine.academy.ui.screens.modules.ModuleRow

/** Le parcours : les modules dans l'ordre conseillé, le module en cours mis en avant. */
@Composable
fun PathScreen(
    onOpenModule: (String) -> Unit,
    onOpenModules: () -> Unit,
    onOpenSettings: () -> Unit,
    viewModel: OverviewViewModel = viewModel(factory = OverviewViewModel.Factory),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val currentModuleId = state.nextLesson?.moduleId

    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = "Parcours", onOpenSettings = onOpenSettings)
        LazyColumn(contentPadding = ScreenPadding, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                Eyebrow(state.machine.fullName, color = MaterialTheme.colorScheme.secondary)
                Spacer(Modifier.height(4.dp))
                Text(
                    if (state.machine.hasCourse) "Suis les modules dans l'ordre : chacun s'appuie sur le précédent."
                    else state.machine.note,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            if (state.modules.isEmpty()) {
                item {
                    AwaitingManualNotice(
                        message = "Aucun module pour l'instant : le parcours sera construit à partir du manuel officiel " +
                            "de la ${state.machine.name}, pages citées.",
                    )
                }
            }
            items(state.modules, key = { it.module.id }) { item ->
                ModuleRow(item, highlighted = item.module.id == currentModuleId) { onOpenModule(item.module.id) }
            }
            if (state.modules.isNotEmpty()) {
                item { TextButton(onClick = onOpenModules) { Text("Voir le catalogue des modules") } }
            }
        }
    }
}
