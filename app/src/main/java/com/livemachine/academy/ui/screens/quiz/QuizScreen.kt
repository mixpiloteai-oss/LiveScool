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
import com.livemachine.academy.data.model.Lesson
import com.livemachine.academy.data.repository.CourseRepository
import com.livemachine.academy.data.repository.ProgressRepository
import com.livemachine.academy.ui.appViewModelFactory
import com.livemachine.academy.ui.components.AppTopBar
import com.livemachine.academy.ui.components.EmptyState
import com.livemachine.academy.ui.components.Eyebrow
import com.livemachine.academy.ui.components.ManualRefLine
import com.livemachine.academy.ui.components.ProgressLine
import com.livemachine.academy.ui.components.SectionCard
import com.livemachine.academy.ui.navigation.QuizRoute
import com.livemachine.academy.ui.theme.Acid
import com.livemachine.academy.ui.theme.ErrorRed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizViewModel(
    savedStateHandle: SavedStateHandle,
    course: CourseRepository,
    private val progressRepository: ProgressRepository,
) : ViewModel() {
    val lesson: Lesson? = course.lesson(savedStateHandle.toRoute<QuizRoute>().lessonId)

    private val _session = MutableStateFlow(QuizSession(lesson?.quiz.orEmpty()))
    val session: StateFlow<QuizSession> = _session.asStateFlow()

    fun select(option: Int) = _session.update { it.select(option) }
    fun validate() = _session.update { it.validate() }
    fun restart() = _session.update { it.restart() }

    fun next() {
        _session.update { it.next() }
        val s = _session.value
        val id = lesson?.id
        if (s.isFinished && id != null && s.questions.isNotEmpty()) {
            viewModelScope.launch { progressRepository.recordQuizScore(id, s.score) }
        }
    }

    companion object {
        val Factory = appViewModelFactory { c, handle -> QuizViewModel(handle, c.courseRepository, c.progressRepository) }
    }
}

@Composable
fun QuizScreen(
    onBack: () -> Unit,
    viewModel: QuizViewModel = viewModel(factory = QuizViewModel.Factory),
) {
    val session by viewModel.session.collectAsStateWithLifecycle()

    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = "Quiz", onBack = onBack)
        if (session.questions.isEmpty()) {
            EmptyState("Pas encore de quiz", "Les questions seront écrites à partir du manuel officiel.")
            return@Column
        }
        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ProgressLine(session.index.toFloat() / session.questions.size)
            val q = session.current
            if (q == null) {
                SectionCard {
                    Eyebrow("Résultat")
                    Spacer(Modifier.height(8.dp))
                    Text("${session.correctCount} / ${session.questions.size}", style = MaterialTheme.typography.displaySmall)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        if (session.score.ratio >= 0.8f) "Bravo, passe à la pratique sur ta machine."
                        else "Relis la leçon et refais les étapes sur la machine avant de réessayer.",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                OutlinedButton(onClick = viewModel::restart, modifier = Modifier.fillMaxWidth()) { Text("Recommencer") }
                Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Retour à la leçon") }
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
