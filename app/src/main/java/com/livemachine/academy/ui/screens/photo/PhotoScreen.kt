package com.livemachine.academy.ui.screens.photo

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.livemachine.academy.data.model.IdentifiableControl
import com.livemachine.academy.data.model.MachineProfile
import com.livemachine.academy.data.repository.CourseRepository
import com.livemachine.academy.ui.navigation.PhotoRoute
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.livemachine.academy.data.repository.MachinePhotoRepository
import com.livemachine.academy.data.repository.MachinePhotoState
import com.livemachine.academy.data.repository.PhotoMark
import com.livemachine.academy.domain.PhotoRules
import com.livemachine.academy.ui.appViewModelFactory
import com.livemachine.academy.ui.components.AppTopBar
import com.livemachine.academy.ui.components.Eyebrow
import com.livemachine.academy.ui.components.ManualRefLine
import com.livemachine.academy.ui.components.SectionCard
import com.livemachine.academy.ui.theme.Acid
import com.livemachine.academy.ui.theme.ErrorRed
import com.livemachine.academy.ui.theme.Magenta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

enum class PhotoMode { MARK, QUIZ }

data class QuizAttempt(val target: IdentifiableControl, val tap: PhotoMark? = null, val hit: Boolean = false)

class PhotoViewModel(
    savedStateHandle: SavedStateHandle,
    course: CourseRepository,
    private val repository: MachinePhotoRepository,
) : ViewModel() {
    val machine: MachineProfile = course.machine(savedStateHandle.toRoute<PhotoRoute>().machineId)
    private val machineId = machine.id
    val controls = machine.identifiableControls
    val photoFile: File get() = repository.photoFile(machineId)

    val photo: StateFlow<MachinePhotoState> = repository.state(machineId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), MachinePhotoState())

    private val skipped = MutableStateFlow<Set<String>>(emptySet())
    val skippedIds: StateFlow<Set<String>> = skipped.asStateFlow()

    val mode = MutableStateFlow(PhotoMode.MARK)
    private val _attempt = MutableStateFlow<QuizAttempt?>(null)
    val attempt: StateFlow<QuizAttempt?> = _attempt.asStateFlow()
    val score = MutableStateFlow(0 to 0)

    /** Prochaine commande à repérer : la première ni repérée ni passée. */
    fun nextToMark(state: MachinePhotoState, skippedIds: Set<String>): IdentifiableControl? =
        controls.firstOrNull { it.id !in state.marks && it.id !in skippedIds }

    fun importPhoto(uri: Uri) {
        viewModelScope.launch {
            repository.importPhoto(machineId, uri)
            skipped.value = emptySet()
            mode.value = PhotoMode.MARK
        }
    }

    fun placeMark(control: IdentifiableControl, x: Float, y: Float) {
        viewModelScope.launch { repository.setMark(machineId, control.id, PhotoMark(x, y)) }
    }

    fun skip(control: IdentifiableControl) {
        skipped.value = skipped.value + control.id
    }

    fun restartMarking() {
        skipped.value = emptySet()
        viewModelScope.launch { controls.forEach { repository.setMark(machineId, it.id, null) } }
    }

    fun startQuiz() {
        mode.value = PhotoMode.QUIZ
        score.value = 0 to 0
        newQuestion()
    }

    fun newQuestion() {
        val marked = controls.filter { it.id in photo.value.marks }
        val previous = _attempt.value?.target?.id
        val pool = marked.filter { it.id != previous }.ifEmpty { marked }
        _attempt.value = pool.randomOrNull()?.let { QuizAttempt(it) }
    }

    fun answer(x: Float, y: Float) {
        val current = _attempt.value ?: return
        if (current.tap != null) return
        val target = photo.value.marks[current.target.id] ?: return
        val hit = PhotoRules.isHit(x, y, target.x, target.y)
        _attempt.value = current.copy(tap = PhotoMark(x, y), hit = hit)
        val (ok, total) = score.value
        score.value = (if (hit) ok + 1 else ok) to total + 1
    }

    fun deletePhoto() {
        viewModelScope.launch { repository.deletePhoto(machineId) }
        mode.value = PhotoMode.MARK
        _attempt.value = null
    }

    companion object {
        const val MIN_MARKS_FOR_QUIZ = 4
        val Factory = appViewModelFactory { c, handle -> PhotoViewModel(handle, c.courseRepository, c.photoRepository) }
    }
}

@Composable
fun PhotoScreen(
    onBack: () -> Unit,
    viewModel: PhotoViewModel = viewModel(factory = PhotoViewModel.Factory),
) {
    val photo by viewModel.photo.collectAsStateWithLifecycle()
    val skipped by viewModel.skippedIds.collectAsStateWithLifecycle()
    val mode by viewModel.mode.collectAsStateWithLifecycle()
    val attempt by viewModel.attempt.collectAsStateWithLifecycle()
    val score by viewModel.score.collectAsStateWithLifecycle()

    val picker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) viewModel.importPhoto(uri)
    }
    val pick = { picker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }

    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    LaunchedEffect(photo.version, photo.hasPhoto) {
        bitmap = if (photo.hasPhoto) withContext(Dispatchers.IO) {
            BitmapFactory.decodeFile(viewModel.photoFile.absolutePath)?.asImageBitmap()
        } else null
    }

    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = "Photo · ${viewModel.machine.name}", onBack = onBack)
        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            val image = bitmap
            if (!photo.hasPhoto || image == null) {
                SectionCard {
                    Eyebrow("Identifier les commandes sur ta photo")
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Prends ta propre ${viewModel.machine.name} en photo, vue de dessus, puis choisis-la ici. " +
                            "Tu repères d'abord chaque commande en la touchant sur la photo ; l'app t'interroge ensuite " +
                            "(« Où est Write ? ») en comparant avec tes repères. La photo reste sur ton téléphone.",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "La photo sert aussi d'image sur la fiche de la machine. L'app n'affiche aucune image du fabricant : seulement ta photo.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(Modifier.height(10.dp))
                    Button(onClick = pick) { Text("Choisir une photo de ma machine") }
                }
                return@Column
            }

            if (viewModel.controls.isEmpty()) {
                Image(
                    image,
                    contentDescription = "Photo de ta machine",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxWidth().aspectRatio(image.width.toFloat() / image.height),
                )
                SectionCard {
                    Text(
                        "Photo enregistrée : elle s'affiche sur la fiche de la machine. Le repérage des commandes " +
                            "arrivera avec le parcours, rédigé à partir du manuel officiel.",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Row {
                    TextButton(onClick = pick) { Text("Changer de photo") }
                    TextButton(onClick = viewModel::deletePhoto) { Text("Supprimer", color = Color(0xFFFF8A8A)) }
                }
                return@Column
            }

            val toMark = viewModel.nextToMark(photo, skipped)
            val markedCount = photo.marks.size
            val canQuiz = markedCount >= PhotoViewModel.MIN_MARKS_FOR_QUIZ
            val current = attempt

            // Consigne au-dessus de la photo
            SectionCard {
                if (mode == PhotoMode.MARK) {
                    Eyebrow("Repérage · $markedCount / ${viewModel.controls.size}")
                    Spacer(Modifier.height(6.dp))
                    if (toMark != null) {
                        Text("Touche « ${toMark.name} » sur ta photo", style = MaterialTheme.typography.titleMedium)
                        Text(toMark.hint, style = MaterialTheme.typography.bodyMedium)
                        ManualRefLine(toMark.manualRef)
                    } else {
                        Text("Repérage terminé. Lance le quiz pour t'entraîner.", style = MaterialTheme.typography.titleMedium)
                    }
                } else if (current != null) {
                    Eyebrow("Quiz · ${score.first} / ${score.second}")
                    Spacer(Modifier.height(6.dp))
                    Text("Où est « ${current.target.name} » ?", style = MaterialTheme.typography.titleMedium)
                    if (current.tap != null) {
                        Text(
                            if (current.hit) "Bien vu." else "Non : la bonne position est entourée en vert.",
                            color = if (current.hit) Acid else ErrorRed,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Text(current.target.hint, style = MaterialTheme.typography.bodyMedium)
                        ManualRefLine(current.target.manualRef)
                    }
                }
            }

            // Photo + repères
            Box(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(image.width.toFloat() / image.height)
                    .pointerInput(mode, toMark?.id, current) {
                        detectTapGestures { offset ->
                            val x = offset.x / size.width
                            val y = offset.y / size.height
                            if (mode == PhotoMode.MARK && toMark != null) viewModel.placeMark(toMark, x, y)
                            if (mode == PhotoMode.QUIZ) viewModel.answer(x, y)
                        }
                    },
            ) {
                Image(image, contentDescription = "Photo de ta machine", contentScale = ContentScale.FillBounds, modifier = Modifier.fillMaxSize())
                Canvas(Modifier.fillMaxSize()) {
                    val radius = PhotoRules.HIT_TOLERANCE * size.width
                    fun at(m: PhotoMark) = Offset(m.x * size.width, m.y * size.height)
                    if (mode == PhotoMode.MARK) {
                        photo.marks.values.forEach { drawCircle(Magenta, 8.dp.toPx(), at(it)) }
                    } else if (current?.tap != null) {
                        photo.marks[current.target.id]?.let { drawCircle(Acid, radius, at(it), style = Stroke(4.dp.toPx())) }
                        drawCircle(if (current.hit) Acid else ErrorRed, 8.dp.toPx(), at(current.tap))
                    }
                }
            }

            // Actions
            if (mode == PhotoMode.MARK) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (toMark != null) OutlinedButton(onClick = { viewModel.skip(toMark) }) { Text("Passer") }
                    Button(onClick = viewModel::startQuiz, enabled = canQuiz) { Text("Lancer le quiz") }
                }
                if (!canQuiz) {
                    Text(
                        "Repère au moins ${PhotoViewModel.MIN_MARKS_FOR_QUIZ} commandes pour lancer le quiz.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            } else {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = viewModel::newQuestion, enabled = current?.tap != null) { Text("Question suivante") }
                    OutlinedButton(onClick = { viewModel.mode.value = PhotoMode.MARK }) { Text("Repérage") }
                }
            }
            Row {
                TextButton(onClick = viewModel::restartMarking) { Text("Refaire le repérage") }
                Spacer(Modifier.width(4.dp))
                TextButton(onClick = pick) { Text("Changer de photo") }
                Spacer(Modifier.width(4.dp))
                TextButton(onClick = viewModel::deletePhoto) { Text("Supprimer", color = Color(0xFFFF8A8A)) }
            }
        }
    }
}
