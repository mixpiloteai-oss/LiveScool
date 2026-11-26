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
                Text(
                    "De la découverte de la façade jusqu'au set live. Suis les modules dans l'ordre : " +
                        "chacun s'appuie sur le précédent.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            items(state.modules, key = { it.module.id }) { item ->
                ModuleRow(item, highlighted = item.module.id == currentModuleId) { onOpenModule(item.module.id) }
            }
            item { TextButton(onClick = onOpenModules) { Text("Voir le catalogue des modules") } }
        }
    }
}
