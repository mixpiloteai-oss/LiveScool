package com.livemachine.academy.ui.screens.modules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.toRoute
import com.livemachine.academy.data.model.CourseModule
import com.livemachine.academy.data.model.UserProgress
import com.livemachine.academy.data.repository.CourseRepository
import com.livemachine.academy.data.repository.ProgressRepository
import com.livemachine.academy.data.repository.SettingsRepository
import com.livemachine.academy.data.commerce.EntitlementRepository
import com.livemachine.academy.domain.ProgressRules
import com.livemachine.academy.domain.commerce.CommerceRules
import com.livemachine.academy.ui.appViewModelFactory
import com.livemachine.academy.ui.components.AppTopBar
import com.livemachine.academy.ui.components.AwaitingManualNotice
import com.livemachine.academy.ui.components.EmptyState
import com.livemachine.academy.ui.components.Eyebrow
import com.livemachine.academy.ui.components.HeroBanner
import com.livemachine.academy.ui.components.ScreenPadding
import com.livemachine.academy.ui.components.SectionCard
import com.livemachine.academy.ui.components.StatusBadge
import com.livemachine.academy.ui.navigation.ModuleRoute
import com.livemachine.academy.ui.theme.Acid
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class ModuleDetailState(
    val progress: UserProgress = UserProgress(),
    val unlocked: Boolean = true,
    val premiumLocked: Boolean = false,
    val previousTitle: String? = null,
)

class ModuleDetailViewModel(
    savedStateHandle: SavedStateHandle,
    course: CourseRepository,
    progressRepository: ProgressRepository,
    settingsRepository: SettingsRepository,
    entitlementRepository: EntitlementRepository,
    commerceEnforced: Boolean,
) : ViewModel() {
    val module: CourseModule? = course.module(savedStateHandle.toRoute<ModuleRoute>().moduleId)

    val state: StateFlow<ModuleDetailState> =
        combine(progressRepository.progress, settingsRepository.settings, entitlementRepository.entitlements) { progress, settings, entitlements ->
            val modules = module?.let { course.machineOfModule(it.id)?.modules }.orEmpty()
            val index = modules.indexOfFirst { it.id == module?.id }
            val included = CommerceRules.isModuleIncluded(index, entitlements, commerceEnforced)
            ModuleDetailState(
                progress = progress,
                unlocked = included && ProgressRules.isUnlocked(modules, index, progress, settings.unlockAllModules),
                premiumLocked = !included,
                previousTitle = modules.getOrNull(index - 1)?.let { "Module ${it.number} · ${it.title}" },
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ModuleDetailState())

    companion object {
        val Factory = appViewModelFactory { c, handle ->
            ModuleDetailViewModel(handle, c.courseRepository, c.progressRepository, c.settingsRepository, c.entitlementRepository, c.commerceEnforced)
        }
    }
}

@Composable
fun ModuleDetailScreen(
    onBack: () -> Unit,
    onOpenLesson: (String) -> Unit,
    onOpenFinalQuiz: (String) -> Unit,
    viewModel: ModuleDetailViewModel = viewModel(factory = ModuleDetailViewModel.Factory),
) {
    val module = viewModel.module
    val state by viewModel.state.collectAsStateWithLifecycle()
    val progress = state.progress

    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = module?.let { "Module ${it.number}" } ?: "Module", onBack = onBack)
        if (module == null) {
            EmptyState("Module introuvable", "Ce module n'existe pas.")
            return@Column
        }
        val finalScore = progress.bestQuizScores[ProgressRules.finalQuizId(module.id)]
        val competence = ProgressRules.competenceAcquired(module, progress)
        val lessonsDone = ProgressRules.lessonsDone(module, progress)
        val finalPassed = ProgressRules.finalPassed(module, progress)

        LazyColumn(contentPadding = ScreenPadding, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                HeroBanner {
                    Eyebrow("Module ${module.number}", color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Spacer(Modifier.height(4.dp))
                    Text(module.title, style = MaterialTheme.typography.headlineSmall)
                    Spacer(Modifier.height(10.dp))
                    Text("Objectif : ${module.goal}", style = MaterialTheme.typography.bodyMedium)
                }
            }
            if (!state.unlocked) {
                item {
                    SectionCard {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Outlined.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                            Spacer(Modifier.width(12.dp))
                            Text(
                                if (state.premiumLocked) {
                                    "Module Premium : il fait partie de l'abonnement (Paramètres › Ton offre)."
                                } else {
                                    "Module verrouillé. Valide d'abord ${state.previousTitle ?: "le module précédent"} : " +
                                        "termine ses leçons ou réussis son quiz final. (Mode libre dans les Paramètres.)"
                                },
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
            }
            item {
                SectionCard {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.WorkspacePremium,
                            contentDescription = null,
                            tint = if (competence) Acid else MaterialTheme.colorScheme.outline,
                        )
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Eyebrow(if (competence) "Compétence acquise" else "Compétence visée", color = if (competence) Acid else MaterialTheme.colorScheme.secondary)
                            Text(module.goal, style = MaterialTheme.typography.bodyMedium)
                            Text(
                                "Leçons : ${if (lessonsDone) "toutes faites" else "à terminer"} · Quiz final : ${if (finalPassed) "réussi" else "à réussir"}",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            }
            item {
                SectionCard {
                    Eyebrow("Pour tes styles", color = MaterialTheme.colorScheme.secondary)
                    Spacer(Modifier.height(6.dp))
                    Text(module.styleFocus, style = MaterialTheme.typography.bodyMedium)
                }
            }
            item { Text("Leçons", style = MaterialTheme.typography.titleLarge) }
            if (module.lessons.isEmpty()) {
                item {
                    AwaitingManualNotice(
                        message = "Les leçons de ce module seront définies et rédigées à partir du manuel officiel.",
                    )
                }
            }
            items(module.lessons, key = { it.id }) { lesson ->
                val done = progress.isLessonDone(lesson.id)
                val click: (() -> Unit)? = if (state.unlocked) ({ onOpenLesson(lesson.id) }) else null
                SectionCard(onClick = click) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            when {
                                !state.unlocked -> Icons.Outlined.Lock
                                done -> Icons.Filled.CheckCircle
                                else -> Icons.Outlined.RadioButtonUnchecked
                            },
                            contentDescription = if (done) "Terminée" else "À faire",
                            tint = if (done) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outline,
                        )
                        Spacer(Modifier.width(14.dp))
                        Column(Modifier.weight(1f)) {
                            Text("${module.number}.${lesson.order}  ${lesson.title}", style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(6.dp))
                            StatusBadge(lesson.status)
                        }
                    }
                }
            }
            if (module.lessons.isNotEmpty()) {
                item {
                    SectionCard {
                        Eyebrow("Quiz final du module")
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "${ProgressRules.FINAL_QUIZ_SIZE} questions tirées de toutes les leçons du module. " +
                                "Réussi à 80 % : il valide le module et débloque le suivant.",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        finalScore?.let {
                            Text(
                                "Meilleur score : ${it.correct}/${it.total}" + if (finalPassed) " · réussi" else "",
                                style = MaterialTheme.typography.labelLarge,
                                color = if (finalPassed) Acid else MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        Spacer(Modifier.height(10.dp))
                        Button(onClick = { onOpenFinalQuiz(module.id) }, enabled = state.unlocked) {
                            Text("Lancer le quiz final")
                        }
                    }
                }
            }
        }
    }
}
