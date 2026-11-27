package com.livemachine.academy.ui.screens.assistant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.livemachine.academy.data.assistant.AssistantHistoryRepository
import com.livemachine.academy.data.assistant.ChatMessage
import com.livemachine.academy.data.commerce.EntitlementRepository
import com.livemachine.academy.data.model.Lesson
import com.livemachine.academy.data.model.MachineProfile
import com.livemachine.academy.data.repository.CourseRepository
import com.livemachine.academy.data.repository.ProgressRepository
import com.livemachine.academy.data.repository.SettingsRepository
import com.livemachine.academy.data.services.MusicAssistant
import com.livemachine.academy.domain.assistant.AssistantContextBuilder
import com.livemachine.academy.domain.commerce.Entitlements
import com.livemachine.academy.domain.commerce.Feature
import com.livemachine.academy.domain.commerce.FeatureGate
import com.livemachine.academy.domain.result.AppError
import com.livemachine.academy.domain.result.AppResult
import com.livemachine.academy.ui.appViewModelFactory
import com.livemachine.academy.ui.components.AppTopBar
import com.livemachine.academy.ui.components.EmptyState
import com.livemachine.academy.ui.components.SectionCard
import com.livemachine.academy.ui.navigation.AssistantRoute
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AssistantUiState(
    val input: String = "",
    val sending: Boolean = false,
    val error: String? = null,
    /** Dernière question restée sans réponse (pour « Réessayer »). */
    val pending: String? = null,
)

class AssistantViewModel(
    savedStateHandle: SavedStateHandle,
    private val course: CourseRepository,
    settingsRepository: SettingsRepository,
    private val progressRepository: ProgressRepository,
    private val history: AssistantHistoryRepository,
    private val assistant: MusicAssistant,
    entitlementRepository: EntitlementRepository,
    val configured: Boolean,
) : ViewModel() {
    /** Leçon d'où l'assistant a été ouvert (vide depuis l'accueil). */
    val lesson: Lesson? = savedStateHandle.toRoute<AssistantRoute>().lessonId.takeIf { it.isNotBlank() }?.let(course::lesson)

    val machine: StateFlow<MachineProfile> = settingsRepository.settings
        .map { s -> lesson?.let { course.machineOfModule(it.moduleId) } ?: course.machine(s.selectedMachineId) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, lesson?.let { course.machineOfModule(it.moduleId) } ?: course.machine(null))

    @OptIn(ExperimentalCoroutinesApi::class)
    val messages: StateFlow<List<ChatMessage>> = machine.flatMapLatest { history.history(it.id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val entitlements: StateFlow<Entitlements> = entitlementRepository.entitlements
        .stateIn(viewModelScope, SharingStarted.Eagerly, Entitlements.Free)

    private val _state = MutableStateFlow(AssistantUiState())
    val state: StateFlow<AssistantUiState> = _state.asStateFlow()

    val allowed: Boolean get() = FeatureGate.isGranted(Feature.AI_ASSISTANT, entitlements.value)

    fun onInput(text: String) = _state.update { it.copy(input = text.take(AssistantContextBuilder.MESSAGE_MAX_CHARS), error = null) }

    fun send(text: String = _state.value.input) {
        val question = text.trim()
        if (question.isEmpty() || _state.value.sending || !configured || !allowed) return
        _state.update { it.copy(input = "", sending = true, error = null, pending = null) }
        viewModelScope.launch {
            val machine = machine.value
            val previous = messages.value
            history.append(machine.id, ChatMessage(ChatMessage.USER, question, System.currentTimeMillis()))
            ask(machine, previous, question)
        }
    }

    /** Renvoie la dernière question sans la dupliquer dans l'historique. */
    fun retry() {
        val question = _state.value.pending ?: return
        if (_state.value.sending) return
        _state.update { it.copy(sending = true, error = null, pending = null) }
        viewModelScope.launch {
            val previous = messages.value.let { if (it.lastOrNull()?.text == question) it.dropLast(1) else it }
            ask(machine.value, previous, question)
        }
    }

    fun clearHistory() {
        viewModelScope.launch { history.clear(machine.value.id) }
        _state.update { it.copy(error = null, pending = null) }
    }

    private suspend fun ask(machine: MachineProfile, previous: List<ChatMessage>, question: String) {
        val progress = progressRepository.progressOf(machine.id).first()
        val request = AssistantContextBuilder.build(machine, lesson, previous, question, progress)
        when (val result = assistant.ask(request)) {
            is AppResult.Success -> {
                history.append(machine.id, ChatMessage(ChatMessage.ASSISTANT, result.value, System.currentTimeMillis()))
                _state.update { it.copy(sending = false) }
            }
            is AppResult.Failure -> _state.update { it.copy(sending = false, error = messageFor(result.error), pending = question) }
        }
    }

    companion object {
        fun messageFor(error: AppError): String = when (error) {
            AppError.Offline -> "Pas de connexion. Vérifie le réseau puis réessaie."
            AppError.RateLimited -> "Trop de questions d'un coup : réessaie dans quelques minutes."
            AppError.NotAvailable -> "L'assistant n'est pas disponible dans cette version."
            AppError.RequiresPremium -> "L'assistant fait partie de l'offre Premium."
            AppError.Unauthorized -> "Accès refusé par le serveur."
            is AppError.Rejected -> error.message
            is AppError.Unexpected -> "L'assistant n'a pas pu répondre (${error.message})."
        }

        val Factory = appViewModelFactory { c, handle ->
            AssistantViewModel(
                handle, c.courseRepository, c.settingsRepository, c.progressRepository,
                c.assistantHistoryRepository, c.musicAssistant, c.entitlementRepository, c.assistantConfigured,
            )
        }
    }
}

private val SUGGESTIONS_LESSON = listOf("Explique-moi cette leçon simplement", "Donne-moi un exercice à mon niveau", "Quelles erreurs éviter ?")
private val SUGGESTIONS = listOf(
    "Une idée de break pour mon live",
    "Comment préparer une transition ?",
    "Donne-moi un exercice à mon niveau",
    "Une idée de pattern hardtek",
)

@Composable
fun AssistantScreen(
    onBack: () -> Unit,
    viewModel: AssistantViewModel = viewModel(factory = AssistantViewModel.Factory),
) {
    val machine by viewModel.machine.collectAsStateWithLifecycle()
    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val itemCount = messages.size + (if (state.sending || state.error != null) 1 else 0)
    LaunchedEffect(itemCount) { if (itemCount > 0) listState.animateScrollToItem(itemCount) }

    Column(Modifier.fillMaxSize().imePadding()) {
        AppTopBar(title = "Assistant · ${machine.name}", onBack = onBack)
        if (!viewModel.configured) {
            EmptyState(
                "Assistant non configuré",
                "Cette version de l'app n'a pas l'adresse du serveur de l'assistant. Le reste de l'application fonctionne normalement, hors ligne.",
            )
            return@Column
        }
        if (!viewModel.allowed) {
            EmptyState("Assistant Premium", "L'assistant fait partie de l'offre Premium.")
            return@Column
        }
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            item {
                SectionCard {
                    Text(
                        viewModel.lesson?.let { "Contexte : leçon « ${it.title} »." } ?: "Contexte : ta ${machine.name}.",
                        style = MaterialTheme.typography.labelLarge,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Réponses générées par IA. Les infos sur la machine viennent des leçons et du manuel cité ; " +
                            "les idées créatives sont des suggestions. Vérifie toujours sur ta machine.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            items(messages.size) { i -> Bubble(messages[i]) }
            if (state.sending) item { Typing() }
            state.error?.let { error ->
                item {
                    SectionCard {
                        Text(error, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.error)
                        if (state.pending != null) {
                            Spacer(Modifier.height(8.dp))
                            OutlinedButton(onClick = viewModel::retry) { Text("Réessayer") }
                        }
                    }
                }
            }
        }
        if (messages.isEmpty() && !state.sending) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(if (viewModel.lesson != null) SUGGESTIONS_LESSON else SUGGESTIONS) { s ->
                    AssistChip(onClick = { viewModel.send(s) }, label = { Text(s) })
                }
            }
        }
        Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = state.input,
                onValueChange = viewModel::onInput,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Pose ta question…") },
                supportingText = { Text("${state.input.length} / ${AssistantContextBuilder.MESSAGE_MAX_CHARS}") },
                maxLines = 4,
                enabled = !state.sending,
            )
            Spacer(Modifier.width(8.dp))
            FilledIconButton(
                onClick = { viewModel.send() },
                enabled = state.input.isNotBlank() && !state.sending,
            ) { Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Envoyer") }
        }
        if (messages.isNotEmpty()) {
            TextButton(onClick = viewModel::clearHistory, modifier = Modifier.padding(start = 8.dp)) { Text("Effacer l'historique") }
        }
    }
}

@Composable
private fun Bubble(message: ChatMessage) {
    val mine = message.role == ChatMessage.USER
    Box(Modifier.fillMaxWidth(), contentAlignment = if (mine) Alignment.CenterEnd else Alignment.CenterStart) {
        Text(
            message.text,
            style = MaterialTheme.typography.bodyLarge,
            color = if (mine) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .widthIn(max = 320.dp)
                .background(
                    if (mine) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerHigh,
                    RoundedCornerShape(18.dp),
                )
                .padding(horizontal = 14.dp, vertical = 10.dp),
        )
    }
}

@Composable
private fun Typing() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp)
        Spacer(Modifier.width(10.dp))
        Text("L'assistant réfléchit…", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
