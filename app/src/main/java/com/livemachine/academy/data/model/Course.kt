package com.livemachine.academy.data.model

/**
 * Niveau de fiabilité d'un contenu pédagogique.
 *
 * Règle du projet : un contenu ne passe en [VERIFIED] que s'il a été vérifié
 * dans le manuel officiel de l'Electribe Sampler et qu'il cite ses pages.
 */
enum class ContentStatus {
    /** Le manuel officiel n'a pas encore été fourni : rien n'est rédigé. */
    AWAITING_MANUAL,

    /** Rédigé, mais les références au manuel ne sont pas encore vérifiées. */
    DRAFT,

    /** Vérifié dans le manuel officiel, avec ses pages. */
    VERIFIED,
}

/** Documents officiels Korg utilisés comme sources, avec leurs pages valides. */
enum class ManualDoc(val label: String, val validPages: IntRange) {
    /** Manuel d'utilisation electribe/electribe sampler (2015), partie française. */
    OWNERS_MANUAL("Manuel", 20..38),

    /** electribe sampler Parameter Guide (2016), en anglais. */
    PARAMETER_GUIDE("Guide des paramètres", 3..19),
}

/** Référence vers un document officiel (chapitre + pages). */
data class ManualRef(
    val chapter: String,
    val pages: String,
    val doc: ManualDoc = ManualDoc.OWNERS_MANUAL,
)

/** Une commande physique de la machine (bouton, potentiomètre, combinaison). */
data class MachineControl(
    val name: String,
    val role: String,
    val manualRef: ManualRef? = null,
)

data class Exercise(
    val id: String,
    val lessonId: String,
    val title: String,
    val instructions: List<String>,
    val successCriteria: String,
    /** Lien éventuel avec un style visé (mentalcore, tribe, hardtek…). */
    val styleHint: String? = null,
)

data class QuizQuestion(
    val id: String,
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val manualRef: ManualRef? = null,
) {
    init {
        require(options.size >= 2) { "Une question doit proposer au moins deux réponses." }
        require(correctIndex in options.indices) { "Index de bonne réponse invalide." }
    }
}

/**
 * Une leçon suit toujours la même structure :
 * objectif, explication simple, commandes, étapes sur la vraie machine,
 * résultat attendu, exercice, erreurs fréquentes, quiz.
 */
data class Lesson(
    val id: String,
    val moduleId: String,
    val order: Int,
    val title: String,
    val status: ContentStatus = ContentStatus.AWAITING_MANUAL,
    val manualRefs: List<ManualRef> = emptyList(),
    val durationMinutes: Int? = null,
    val objective: String? = null,
    val explanation: String? = null,
    val controls: List<MachineControl> = emptyList(),
    val steps: List<String> = emptyList(),
    val expectedResult: String? = null,
    val exercise: Exercise? = null,
    val commonMistakes: List<String> = emptyList(),
    val quiz: List<QuizQuestion> = emptyList(),
)

data class CourseModule(
    val id: String,
    val number: Int,
    val title: String,
    val summary: String,
    /** Ce que l'utilisateur saura faire sur sa machine à la fin du module. */
    val goal: String,
    /** En quoi le module sert les styles visés. */
    val styleFocus: String,
    val lessons: List<Lesson>,
)

data class GlossaryTerm(
    val term: String,
    val definition: String,
    val category: GlossaryCategory,
    val manualRef: ManualRef? = null,
)

enum class GlossaryCategory(val label: String) {
    MACHINE("Machine"),
    SOUND("Son & synthèse"),
    SEQUENCING("Séquence"),
    LIVE("Live"),
    STYLE("Styles"),
}
