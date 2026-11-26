package com.livemachine.academy.ui.screens.quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.toRoute
import com.livemachine.academy.data.repository.CourseRepository
import com.livemachine.academy.data.repository.ProgressRepository
import com.livemachine.academy.domain.ProgressRules
import com.livemachine.academy.ui.appViewModelFactory
import com.livemachine.academy.ui.components.AppTopBar
import com.livemachine.academy.ui.components.EmptyState
import com.livemachine.academy.ui.components.Eyebrow
import com.livemachine.academy.ui.components.ManualRefLine
import com.livemachine.academy.ui.components.ProgressLine
import com.livemachine.academy.ui.components.SectionCard
import com.livemachine.academy.ui.navigation.QuizKind
import com.livemachine.academy.ui.navigation.QuizRoute
import com.livemachine.academy.ui.theme.Acid
import com.livemachine.academy.ui.theme.ErrorRed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class QuizUiState(
    val title: String = "Quiz",
    val loading: Boolean = true,
    val session: QuizSession = QuizSession(emptyList()),
    val emptyMessage: String = "Pas de question pour ce quiz.",
)

class QuizViewModel(
    savedStateHandle: SavedStateHandle,
    private val course: CourseRepository,
    private val progressRepository: ProgressRepository,
) : ViewModel() {
    private val route = savedStateHandle.toRoute<QuizRoute>()

    /** Identifiant sous lequel le meilleur score est enregistré (aucun pour la révision). */
    private val scoreId: String? = when (route.kind) {
        QuizKind.LESSON -> route.id
        QuizKind.FINAL -> ProgressRules.finalQuizId(route.id)
        else -> null
    }

    private val _state = MutableStateFlow(QuizUiState())
    val state: StateFlow<QuizUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = when (route.kind) {
                QuizKind.LESSON -> {
                    val lesson = course.lesson(route.id)
                    QuizUiState(title = "Quiz", loading = false, session = QuizSession(lesson?.quiz.orEmpty()))
                }
                QuizKind.FINAL -> {
                    val module = course.module(route.id)
                    QuizUiState(
                        title = module?.let { "Quiz final · Module ${it.number}" } ?: "Quiz final",
                        loading = false,
                        session = QuizSession(module?.let { ProgressRules.finalQuizQuestions(it) }.orEmpty()),
                    )
                }
                else -> {
                    val progress = progressRepository.progress.first()
                    QuizUiState(
                        title = "Révision des erreurs",
                        loading = false,
                        session = QuizSession(ProgressRules.reviewQuestions(course.modules(), progress).take(15)),
                        emptyMessage = "Aucune erreur à réviser. Les questions ratées dans les quiz apparaîtront ici.",
                    )
                }
            }
        }
    }

    fun select(option: Int) = _state.update { it.copy(session = it.session.select(option)) }

    fun validate() {
        val before = _state.value.session
        val after = before.validate()
        if (after === before) return
        _state.update { it.copy(session = after) }
        val question = after.current ?: return
        viewModelScope.launch { progressRepository.recordAnswer(question.id, after.isCurrentCorrect) }
    }

    fun next() {
        _state.update { it.copy(session = it.session.next()) }
        val s = _state.value.session
        val id = scoreId
        if (s.isFinished && id != null && s.questions.isNotEmpty()) {
            viewModelScope.launch { progressRepository.recordQuizScore(id, s.score) }
        }
    }

    fun restart() = _state.update { it.copy(session = it.session.restart()) }

    companion object {
        val Factory = appViewModelFactory { c, handle -> QuizViewModel(handle, c.courseRepository, c.progressRepository) }
    }
}

@Composable
fun QuizScreen(
    onBack: () -> Unit,
    viewModel: QuizViewModel = viewModel(factory = QuizViewModel.Factory),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val session = state.session

    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = state.title, onBack = onBack)
        if (state.loading) return@Column
        if (session.questions.isEmpty()) {
            EmptyState("Rien à faire ici", state.emptyMessage)
            return@Column
        }
        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ProgressLine(session.index.toFloat() / session.questions.size)
            val q = session.current
            if (q == null) {
                val passed = session.score.ratio >= ProgressRules.PASS_RATIO
                SectionCard {
                    Eyebrow(if (passed) "Réussi" else "À retravailler", color = if (passed) Acid else ErrorRed)
                    Spacer(Modifier.height(8.dp))
                    Text("${session.correctCount} / ${session.questions.size}", style = MaterialTheme.typography.displaySmall)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        if (passed) "Bravo. Les questions ratées restent dans « Révision des erreurs »."
                        else "Il faut 80 % pour réussir. Relis la leçon, refais les étapes sur la machine, puis réessaie. " +
                            "Tes erreurs sont dans « Révision des erreurs ».",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                OutlinedButton(onClick = viewModel::restart, modifier = Modifier.fillMaxWidth()) { Text("Recommencer") }
                Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Terminer") }
                return@Column
            }
            Eyebrow("Question ${session.index + 1} / ${session.questions.size}")
            Text(q.prompt, style = MaterialTheme.typography.titleLarge)
            q.options.forEachIndexed { i, option ->
                val border = when {
                    session.validated && i == q.correctIndex -> Acid
                    session.validated && i == session.selected -> ErrorRed
                    i == session.selected -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.outlineVariant
                }
                Surface(
                    onClick = { viewModel.select(i) },
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                    border = BorderStroke(2.dp, border),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(option, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(16.dp))
                }
            }
            if (session.validated) {
                SectionCard {
                    Eyebrow(
                        if (session.isCurrentCorrect) "Bonne réponse" else "Pas tout à fait",
                        color = if (session.isCurrentCorrect) Acid else ErrorRed,
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(q.explanation, style = MaterialTheme.typography.bodyMedium)
                    q.manualRef?.let { Spacer(Modifier.height(6.dp)); ManualRefLine(it) }
                }
                Button(onClick = viewModel::next, modifier = Modifier.fillMaxWidth()) {
                    Text(if (session.index + 1 < session.questions.size) "Question suivante" else "Voir le résultat")
                }
            } else {
                Button(
                    onClick = viewModel::validate,
                    enabled = session.selected != null,
                    modifier = Modifier.fillMaxWidth(),
                ) { Text("Valider") }
            }
        }
    }
}
