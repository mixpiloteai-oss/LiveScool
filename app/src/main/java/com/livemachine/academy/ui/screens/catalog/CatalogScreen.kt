package com.livemachine.academy.ui.screens.catalog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.livemachine.academy.data.content.MachineCatalog
import com.livemachine.academy.data.model.MachineCategory
import com.livemachine.academy.data.model.MachineProfile
import com.livemachine.academy.data.repository.CourseRepository
import com.livemachine.academy.data.repository.SettingsRepository
import com.livemachine.academy.ui.appViewModelFactory
import com.livemachine.academy.ui.components.AppTopBar
import com.livemachine.academy.ui.components.CourseStatusPill
import com.livemachine.academy.ui.components.Eyebrow
import com.livemachine.academy.ui.components.MachineIllustration
import com.livemachine.academy.ui.components.ScreenPadding
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/** Catalogue des machines : choisir la machine dont on suit le parcours. */
class CatalogViewModel(
    course: CourseRepository,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {
    val machines: List<MachineProfile> = course.machines()

    val selectedId: StateFlow<String> = settingsRepository.settings
        .map { it.selectedMachineId }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), MachineCatalog.ELECTRIBE_ID)

    fun select(machineId: String, then: () -> Unit) {
        viewModelScope.launch {
            settingsRepository.setSelectedMachine(machineId)
            then()
        }
    }

    companion object {
        val Factory = appViewModelFactory { c, _ -> CatalogViewModel(c.courseRepository, c.settingsRepository) }
    }
}

@Composable
fun CatalogScreen(
    onBack: () -> Unit,
    onSelected: () -> Unit,
    viewModel: CatalogViewModel = viewModel(factory = CatalogViewModel.Factory),
) {
    val selectedId by viewModel.selectedId.collectAsStateWithLifecycle()
    var filter by rememberSaveable { mutableStateOf<MachineCategory?>(null) }
    val machines = viewModel.machines
    val shown = machines.filter { filter == null || it.category == filter }

    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = "Catalogue des machines", onBack = onBack)
        LazyColumn(contentPadding = ScreenPadding, verticalArrangement = Arrangement.spacedBy(10.dp)) {
            item {
                Text(
                    "${machines.size} machines · ${machines.count { it.hasCourse }} avec cours",
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Choisis ta machine : chaque machine a son parcours et sa progression. " +
                        "Les cours sont rédigés uniquement à partir du manuel officiel du modèle exact.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item { CategoryChip("Toutes (${machines.size})", filter == null) { filter = null } }
                    items(MachineCategory.entries.filter { c -> machines.any { it.category == c } }) { c ->
                        CategoryChip("${c.label} (${machines.count { it.category == c }})", filter == c) {
                            filter = if (filter == c) null else c
                        }
                    }
                }
            }
            items(shown, key = { it.id }) { machine ->
                MachineCard(machine, selected = machine.id == selectedId) { viewModel.select(machine.id, onSelected) }
            }
        }
    }
}

@Composable
private fun CategoryChip(label: String, selected: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
    )
}

@Composable
private fun MachineCard(machine: MachineProfile, selected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerHigh,
        border = BorderStroke(if (selected) 2.dp else 1.dp, if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            MachineIllustration(machine, size = 76.dp)
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Eyebrow(machine.manufacturer, color = MaterialTheme.colorScheme.secondary)
                Text(machine.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    machine.typeLabel,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                if (machine.styles.isNotEmpty()) {
                    Text(
                        machine.styles.joinToString(" · "),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Spacer(Modifier.height(6.dp))
                CourseStatusPill(machine)
            }
            if (selected) {
                Spacer(Modifier.width(8.dp))
                Icon(Icons.Outlined.CheckCircle, contentDescription = "Machine choisie", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
            }
        }
    }
}
