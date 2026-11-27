package com.livemachine.academy.ui.screens.audio

import android.Manifest
import android.media.MediaPlayer
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.FileOpen
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.livemachine.academy.data.assistant.MachineInfo
import com.livemachine.academy.data.audio.AudioAnalysisRequest
import com.livemachine.academy.data.audio.AudioAnalysisResult
import com.livemachine.academy.data.audio.AudioClip
import com.livemachine.academy.data.audio.AudioDecoder
import com.livemachine.academy.data.audio.AudioPayload
import com.livemachine.academy.data.audio.AudioRecorder
import com.livemachine.academy.data.audio.TempAudioStore
import com.livemachine.academy.data.audio.UnsupportedAudioException
import com.livemachine.academy.data.audio.WavCodec
import com.livemachine.academy.data.model.MachineProfile
import com.livemachine.academy.data.repository.CourseRepository
import com.livemachine.academy.data.repository.ProgressRepository
import com.livemachine.academy.data.repository.SettingsRepository
import com.livemachine.academy.data.services.AudioAnalyzer
import com.livemachine.academy.domain.assistant.AssistantContextBuilder
import com.livemachine.academy.domain.audio.AudioClipRules
import com.livemachine.academy.domain.result.AppError
import com.livemachine.academy.domain.result.AppResult
import com.livemachine.academy.ui.appViewModelFactory
import com.livemachine.academy.ui.components.AppTopBar
import com.livemachine.academy.ui.components.EmptyState
import com.livemachine.academy.ui.components.Eyebrow
import com.livemachine.academy.ui.components.SectionCard
import com.livemachine.academy.ui.screens.assistant.AssistantViewModel
import java.io.File
import java.util.Base64
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

val AUDIO_STYLES = listOf("hardtek", "tribe", "tribecore", "acidcore", "mentalcore", "autre")
val AUDIO_GOALS = listOf("live", "création", "entraînement")

data class AudioUiState(
    val recording: Boolean = false,
    val recordedSeconds: Int = 0,
    val preparing: Boolean = false,
    val clip: AudioClip? = null,
    val clipFile: File? = null,
    val style: String = "hardtek",
    val goal: String = "live",
    val consent: Boolean = false,
    val sending: Boolean = false,
    val result: AudioAnalysisResult? = null,
    val error: String? = null,
    val info: String? = null,
    val quotaReached: Boolean = false,
)

class AudioAnalysisViewModel(
    private val course: CourseRepository,
    settingsRepository: SettingsRepository,
    private val progressRepository: ProgressRepository,
    private val decoder: AudioDecoder,
    private val recorder: AudioRecorder,
    private val store: TempAudioStore,
    private val analyzer: AudioAnalyzer,
    val configured: Boolean,
) : ViewModel() {
    val machine: StateFlow<MachineProfile> = settingsRepository.settings
        .map { course.machine(it.selectedMachineId) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, course.machine(null))

    private val _state = MutableStateFlow(AudioUiState())
    val state: StateFlow<AudioUiState> = _state.asStateFlow()
    private var recordJob: Job? = null

    fun hasMicPermission() = recorder.hasPermission()

    /** Démarré uniquement par le bouton « Enregistrer » (jamais automatiquement). */
    fun startRecording() {
        if (_state.value.recording || _state.value.sending) return
        discard()
        _state.update { it.copy(recording = true, recordedSeconds = 0, error = null, info = null) }
        recordJob = viewModelScope.launch {
            try {
                val clip = recorder.record { sec -> _state.update { it.copy(recordedSeconds = sec) } }
                setClip(clip)
            } catch (e: SecurityException) {
                _state.update { it.copy(error = "Autorise le micro pour enregistrer un extrait.") }
            } catch (e: UnsupportedAudioException) {
                _state.update { it.copy(error = e.message) }
            } finally {
                _state.update { it.copy(recording = false) }
            }
        }
    }

    fun stopRecording() = recorder.stop()

    fun import(uri: Uri, name: String) {
        if (_state.value.recording || _state.value.sending) return
        discard()
        _state.update { it.copy(preparing = true, error = null, info = null) }
        viewModelScope.launch {
            try {
                setClip(decoder.decode(uri, name))
            } catch (e: UnsupportedAudioException) {
                _state.update { it.copy(error = "${e.message} Essaie un MP3, M4A, WAV, FLAC ou OGG.") }
            } catch (e: Exception) {
                _state.update { it.copy(error = "Lecture du fichier impossible.") }
            } finally {
                _state.update { it.copy(preparing = false) }
            }
        }
    }

    private fun setClip(clip: AudioClip) {
        val invalid = AudioClipRules.validate(clip.samples.size)
        if (invalid != null) {
            _state.update { it.copy(error = invalid) }
            return
        }
        val file = store.write(clip)
        _state.update {
            it.copy(
                clip = clip, clipFile = file, consent = false, result = null,
                info = if (clip.truncated) "Seules les ${AudioClipRules.MAX_SECONDS} premières secondes sont gardées." else null,
            )
        }
    }

    fun setStyle(style: String) = _state.update { it.copy(style = style) }
    fun setGoal(goal: String) = _state.update { it.copy(goal = goal) }
    fun setConsent(consent: Boolean) = _state.update { it.copy(consent = consent) }

    /** Supprime l'extrait et le fichier temporaire. */
    fun discard() {
        store.clear()
        _state.update { it.copy(clip = null, clipFile = null, consent = false, info = null) }
    }

    /** Envoi volontaire, après consentement explicite. */
    fun send() {
        val s = _state.value
        val clip = s.clip ?: return
        if (!configured || !AudioClipRules.canSend(clip.samples.size, s.consent, s.sending)) return
        _state.update { it.copy(sending = true, error = null, result = null) }
        viewModelScope.launch {
            val machine = machine.value
            val progress = progressRepository.progressOf(machine.id).first()
            val request = AudioAnalysisRequest(
                machine = MachineInfo(machine.id, machine.fullName),
                style = s.style,
                goal = s.goal,
                level = AssistantContextBuilder.level(machine, progress),
                context = contextLessons(machine),
                audio = AudioPayload("wav", Base64.getEncoder().encodeToString(WavCodec.encodePcm16Mono(clip.samples, clip.sampleRate))),
            )
            when (val result = analyzer.analyze(request)) {
                is AppResult.Success -> {
                    // Analyse terminée : l'extrait et son fichier temporaire sont supprimés.
                    discard()
                    _state.update { it.copy(sending = false, result = result.value) }
                }
                is AppResult.Failure -> _state.update {
                    it.copy(sending = false, error = AssistantViewModel.messageFor(result.error), quotaReached = result.error is AppError.QuotaExceeded)
                }
            }
        }
    }

    /** Leçons utiles aux conseils (breaks, transitions, kick, basse…), avec leurs pages du manuel. */
    fun contextLessons(machine: MachineProfile) = AssistantContextBuilder
        .relevantLessons(machine, "break transition kick basse filtre mute pattern scène tempo swing sidechain macro")
        .take(3)
        .map(AssistantContextBuilder::snippet)

    override fun onCleared() {
        recorder.stop()
        recordJob?.cancel()
        store.clear()
    }

    companion object {
        val Factory = appViewModelFactory { c, _ ->
            AudioAnalysisViewModel(
                c.courseRepository, c.settingsRepository, c.progressRepository, c.audioDecoder, c.audioRecorder,
                c.tempAudioStore, c.audioAnalyzer, c.assistantConfigured,
            )
        }
    }
}

@Composable
fun AudioAnalysisScreen(
    onBack: () -> Unit,
    onOpenPremium: () -> Unit = {},
    viewModel: AudioAnalysisViewModel = viewModel(factory = AudioAnalysisViewModel.Factory),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val machine by viewModel.machine.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val pickFile = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) viewModel.import(uri, displayName(context, uri))
    }
    var micDenied by remember { mutableStateOf(false) }
    val askMic = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        micDenied = !granted
        if (granted) viewModel.startRecording()
    }

    // Lecteur de l'extrait (écoute avant envoi)
    var player by remember { mutableStateOf<MediaPlayer?>(null) }
    var playing by remember { mutableStateOf(false) }
    fun stopPlayer() {
        player?.release()
        player = null
        playing = false
    }
    DisposableEffect(state.clipFile) { onDispose { stopPlayer() } }

    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = "Analyse audio", onBack = onBack)
        if (!viewModel.configured) {
            EmptyState(
                "Analyse non configurée",
                "Cette version de l'app n'a pas l'adresse du serveur d'analyse. Le reste fonctionne normalement, hors ligne.",
            )
            return@Column
        }
        Column(
            Modifier.verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                "Importe un morceau ou enregistre un extrait de ta ${machine.name} (5 à ${AudioClipRules.MAX_SECONDS} s). " +
                    "Rien n'est envoyé tant que tu n'as pas validé l'envoi.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            SectionCard {
                Eyebrow("1 · Ton extrait")
                Spacer(Modifier.height(8.dp))
                when {
                    state.recording -> {
                        Text("Enregistrement… ${state.recordedSeconds} s / ${AudioClipRules.MAX_SECONDS} s", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(8.dp))
                        Button(
                            onClick = viewModel::stopRecording,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        ) {
                            Icon(Icons.Outlined.Stop, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Arrêter")
                        }
                    }
                    state.preparing -> Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp)
                        Spacer(Modifier.width(10.dp))
                        Text("Préparation du fichier…")
                    }
                    else -> {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(onClick = { stopPlayer(); pickFile.launch("audio/*") }, enabled = !state.sending) {
                                Icon(Icons.Outlined.FileOpen, contentDescription = null)
                                Spacer(Modifier.width(6.dp))
                                Text("Importer")
                            }
                            OutlinedButton(
                                onClick = {
                                    stopPlayer()
                                    if (viewModel.hasMicPermission()) viewModel.startRecording() else askMic.launch(Manifest.permission.RECORD_AUDIO)
                                },
                                enabled = !state.sending,
                            ) {
                                Icon(Icons.Outlined.Mic, contentDescription = null)
                                Spacer(Modifier.width(6.dp))
                                Text("Enregistrer")
                            }
                        }
                        if (micDenied) {
                            Spacer(Modifier.height(6.dp))
                            Text("Micro refusé : tu peux toujours importer un fichier.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
                state.clip?.let { clip ->
                    Spacer(Modifier.height(10.dp))
                    Text(
                        "${clip.source} · ${AudioClipRules.durationSeconds(clip.samples.size).toInt()} s",
                        style = MaterialTheme.typography.labelLarge,
                    )
                    state.info?.let { Text(it, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                    Spacer(Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(onClick = {
                            if (playing) {
                                stopPlayer()
                            } else {
                                state.clipFile?.let { file ->
                                    stopPlayer()
                                    player = runCatching {
                                        MediaPlayer().apply {
                                            setDataSource(file.absolutePath)
                                            setOnCompletionListener { playing = false }
                                            prepare()
                                            start()
                                        }
                                    }.getOrNull()
                                    playing = player != null
                                }
                            }
                        }) {
                            Icon(if (playing) Icons.Outlined.Pause else Icons.Outlined.PlayArrow, contentDescription = null)
                            Spacer(Modifier.width(6.dp))
                            Text(if (playing) "Pause" else "Écouter")
                        }
                        OutlinedButton(onClick = { stopPlayer(); viewModel.discard() }, enabled = !state.sending) {
                            Icon(Icons.Outlined.Delete, contentDescription = null)
                            Spacer(Modifier.width(6.dp))
                            Text("Supprimer")
                        }
                    }
                }
            }

            SectionCard {
                Eyebrow("2 · Pour des conseils adaptés")
                Spacer(Modifier.height(8.dp))
                Text("Style", style = MaterialTheme.typography.labelLarge)
                ChipRow(AUDIO_STYLES, state.style, viewModel::setStyle)
                Spacer(Modifier.height(6.dp))
                Text("Objectif", style = MaterialTheme.typography.labelLarge)
                ChipRow(AUDIO_GOALS, state.goal, viewModel::setGoal)
                Spacer(Modifier.height(6.dp))
                Text("Machine : ${machine.fullName} · niveau calculé d'après ta progression.", style = MaterialTheme.typography.bodySmall)
            }

            state.clip?.let { clip ->
                SectionCard {
                    Eyebrow("3 · Données envoyées")
                    Spacer(Modifier.height(8.dp))
                    AudioClipRules.payloadDescription(
                        clip.samples.size, machine.fullName, state.style, state.goal, "calculé d'après ta progression", viewModel.contextLessons(machine).size,
                    ).forEach { Text("• $it", style = MaterialTheme.typography.bodyMedium) }
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Le serveur mesure l'extrait en mémoire puis l'oublie : il n'est ni conservé ni transmis à l'IA, " +
                            "qui ne reçoit que les mesures. Le fichier temporaire du téléphone est supprimé après l'analyse.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = state.consent, onCheckedChange = viewModel::setConsent, enabled = !state.sending)
                        Text("J'accepte d'envoyer cet extrait pour analyse.", style = MaterialTheme.typography.bodyMedium)
                    }
                    Button(
                        onClick = { stopPlayer(); viewModel.send() },
                        enabled = AudioClipRules.canSend(clip.samples.size, state.consent, state.sending),
                        modifier = Modifier.fillMaxWidth(),
                    ) { Text("Envoyer pour analyse") }
                }
            }

            if (state.sending) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp)
                    Spacer(Modifier.width(10.dp))
                    Text("Analyse en cours…", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            state.error?.let {
                SectionCard {
                    Text(it, color = MaterialTheme.colorScheme.error)
                    if (state.quotaReached) OutlinedButton(onClick = onOpenPremium) { Text("Voir Premium") }
                }
            }
            state.result?.let { ResultCards(it) }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ChipRow(options: List<String>, selected: String, onSelect: (String) -> Unit) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        options.forEach { o -> FilterChip(selected = o == selected, onClick = { onSelect(o) }, label = { Text(o) }) }
    }
}

@Composable
private fun ResultCards(result: AudioAnalysisResult) {
    SectionCard {
        Eyebrow("Mesures détectées par l'outil")
        Spacer(Modifier.height(8.dp))
        result.summary.forEach { m ->
            Text(
                m.label + (if (m.kind == "estimation") " (estimation)" else ""),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
            )
            Text(m.value, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(6.dp))
        }
        Text(
            "Non mesuré : tonalité, swing fin, identification des sons, ressenti. Ces points demandent ton écoute.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
    SectionCard {
        Eyebrow("Analyse et conseils (IA)")
        Spacer(Modifier.height(8.dp))
        Text(result.advice ?: result.adviceError ?: "Pas de conseils pour cet extrait.", style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(6.dp))
        Text(
            "L'IA n'a pas écouté ton morceau : elle s'appuie sur les mesures ci-dessus. Les conseils sont des suggestions.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

private fun displayName(context: android.content.Context, uri: Uri): String =
    runCatching {
        context.contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)?.use { c ->
            if (c.moveToFirst()) c.getString(0) else null
        }
    }.getOrNull() ?: "Fichier importé"
