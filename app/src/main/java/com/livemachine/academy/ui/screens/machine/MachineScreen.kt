package com.livemachine.academy.ui.screens.machine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.Alignment
import com.livemachine.academy.ui.components.MachineIllustration
import com.livemachine.academy.ui.components.CourseStatusPill
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.livemachine.academy.data.content.MachineCatalog
import com.livemachine.academy.data.model.MachineProfile
import com.livemachine.academy.data.repository.CourseRepository
import com.livemachine.academy.data.repository.MachinePhotoRepository
import com.livemachine.academy.data.repository.MachinePhotoState
import com.livemachine.academy.data.repository.SettingsRepository
import com.livemachine.academy.ui.appViewModelFactory
import com.livemachine.academy.ui.components.AppTopBar
import com.livemachine.academy.ui.components.AwaitingManualNotice
import com.livemachine.academy.ui.components.Eyebrow
import com.livemachine.academy.ui.components.HeroBanner
import com.livemachine.academy.ui.components.ManualRefLine
import com.livemachine.academy.ui.components.ScreenPadding
import com.livemachine.academy.ui.components.SectionCard
import com.livemachine.academy.ui.components.UserMachinePhoto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.io.File

data class MachineSheetState(
    val machine: MachineProfile = MachineCatalog.electribe,
    val photo: MachinePhotoState = MachinePhotoState(),
    val photoFile: File? = null,
)

/** Fiche de la machine choisie à l'accueil. */
class MachineViewModel(
    course: CourseRepository,
    settingsRepository: SettingsRepository,
    photoRepository: MachinePhotoRepository,
) : ViewModel() {
    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<MachineSheetState> = settingsRepository.settings
        .flatMapLatest { settings ->
            val machine = course.machine(settings.selectedMachineId)
            photoRepository.state(machine.id).map { MachineSheetState(machine, it, photoRepository.photoFile(machine.id)) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), MachineSheetState())

    companion object {
        val Factory = appViewModelFactory { c, _ -> MachineViewModel(c.courseRepository, c.settingsRepository, c.photoRepository) }
    }
}

/** Fiche machine (pas de simulateur : uniquement des repères pour la vraie machine). */
@Composable
fun MachineScreen(
    onOpenSettings: () -> Unit,
    onOpenPhoto: (String) -> Unit,
    onStartLearning: () -> Unit,
    onOpenCatalog: () -> Unit = {},
    viewModel: MachineViewModel = viewModel(factory = MachineViewModel.Factory),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val machine = state.machine

    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = "La machine", onOpenSettings = onOpenSettings)
        LazyColumn(contentPadding = ScreenPadding, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                HeroBanner {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        MachineIllustration(machine, size = 64.dp)
                        Spacer(Modifier.width(14.dp))
                        Column(Modifier.weight(1f)) {
                            Eyebrow(machine.manufacturer, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Text(machine.name, style = MaterialTheme.typography.headlineSmall)
                            Text("${machine.kind} · ${machine.category.label}", style = MaterialTheme.typography.labelMedium)
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    CourseStatusPill(machine)
                    Spacer(Modifier.height(12.dp))
                    state.photoFile?.let { file ->
                        UserMachinePhoto(file, state.photo.hasPhoto, state.photo.version, onAddPhoto = { onOpenPhoto(machine.id) })
                    }
                    Spacer(Modifier.height(12.dp))
                    Text(machine.shortDescription, style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(16.dp))
                    if (machine.hasCourse) {
                        Button(onClick = onStartLearning, modifier = Modifier.fillMaxWidth()) {
                            Text("Commencer l'apprentissage")
                            Spacer(Modifier.width(8.dp))
                            Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null)
                        }
                    }
                    OutlinedButton(onClick = onOpenCatalog, modifier = Modifier.fillMaxWidth()) {
                        Text("Changer de machine")
                    }
                }
            }
            if (machine.pedagogy.isNotBlank()) {
                item {
                    SectionCard {
                        Text("Présentation", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(8.dp))
                        InfoLine("Intérêt pédagogique", machine.pedagogy)
                        InfoLine("En live", machine.liveUse)
                        if (machine.styles.isNotEmpty()) InfoLine("Styles", machine.styles.joinToString(" · "))
                    }
                }
            }
            item {
                SectionCard {
                    Text("Manuel officiel", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        if (machine.hasCourse) "Cours rédigé et vérifié à partir de :" else "${machine.courseStatusLabel}. Document attendu :",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    machine.manuals.forEach { manual ->
                        Spacer(Modifier.height(6.dp))
                        Text(manual.title.replaceFirstChar { it.uppercase() }, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                        Text("docs/manual/${manual.fileName}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
            item {
                SectionCard {
                    Text(machine.note, style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.height(8.dp))
                    Text(machine.sourcesLabel, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            item {
                SectionCard(onClick = { onOpenPhoto(machine.id) }) {
                    Text(
                        if (machine.identifiableControls.isEmpty()) "Ajouter ou changer la photo de ta machine"
                        else "Identifier les commandes sur ta photo",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        if (machine.identifiableControls.isEmpty()) "Ta photo sert d'image sur cette fiche."
                        else "Repère les commandes sur une photo de ta propre machine, puis entraîne-toi à les retrouver.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            if (machine.sections.isEmpty()) {
                item { AwaitingManualNotice(message = "La description de la façade et les cours seront rédigés uniquement à partir du manuel officiel de ce modèle exact.") }
            }
            items(machine.sections, key = { it.title }) { section ->
                SectionCard {
                    Text(section.title, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(4.dp))
                    Text(section.summary, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.height(10.dp))
                    section.controls.forEachIndexed { index, control ->
                        if (index > 0) HorizontalDivider(Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.outlineVariant)
                        Text(control.name, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                        Text(control.role, style = MaterialTheme.typography.bodyMedium)
                    }
                    Spacer(Modifier.height(10.dp))
                    ManualRefLine(section.manualRef)
                }
            }
        }
    }
}

@Composable
private fun InfoLine(label: String, text: String) {
    if (text.isBlank()) return
    Text(label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
    Text(text, style = MaterialTheme.typography.bodyMedium)
    Spacer(Modifier.height(8.dp))
}
