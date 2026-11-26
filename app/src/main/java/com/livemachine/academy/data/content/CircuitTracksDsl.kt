package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.ContentStatus
import com.livemachine.academy.data.model.Exercise
import com.livemachine.academy.data.model.Lesson
import com.livemachine.academy.data.model.MachineControl
import com.livemachine.academy.data.model.ManualDoc
import com.livemachine.academy.data.model.ManualRef
import com.livemachine.academy.data.model.QuizQuestion

/**
 * Outils d'écriture du parcours Circuit Tracks.
 * Toutes les références pointent vers le Guide d'utilisation Novation Circuit Tracks, Version 2 (FR) ;
 * les numéros de page sont ceux imprimés (identiques à ceux du PDF).
 */
internal fun ct(chapter: String, pages: String) = ManualRef(chapter, pages, ManualDoc.CIRCUIT_TRACKS_GUIDE)

internal fun cmd(name: String, role: String, ref: ManualRef) = MachineControl(name, role, ref)

internal class CtQuestion(
    val prompt: String,
    val options: List<String>,
    val correct: Int,
    val explanation: String,
    val ref: ManualRef,
)

internal fun qz(prompt: String, options: List<String>, correct: Int, explanation: String, ref: ManualRef) =
    CtQuestion(prompt, options, correct, explanation, ref)

/** Leçon vérifiée : ids `<module>-l<ordre>`, `-ex` pour l'exercice, `-q<n>` pour les questions. */
internal fun ctLesson(
    moduleId: String,
    order: Int,
    title: String,
    refs: List<ManualRef>,
    minutes: Int,
    objective: String,
    explanation: String,
    controls: List<MachineControl>,
    steps: List<String>,
    expected: String,
    exerciseTitle: String,
    exercise: List<String>,
    success: String,
    mistakes: List<String>,
    quiz: List<CtQuestion>,
    style: String? = null,
): Lesson {
    val id = "$moduleId-l$order"
    return Lesson(
        id = id,
        moduleId = moduleId,
        order = order,
        title = title,
        status = ContentStatus.VERIFIED,
        manualRefs = refs,
        durationMinutes = minutes,
        objective = objective,
        explanation = explanation,
        controls = controls,
        steps = steps,
        expectedResult = expected,
        exercise = Exercise("$id-ex", id, exerciseTitle, exercise, success, style),
        commonMistakes = mistakes,
        quiz = quiz.mapIndexed { i, q ->
            QuizQuestion("$id-q${i + 1}", q.prompt, q.options, q.correct, q.explanation, q.ref)
        },
    )
}
