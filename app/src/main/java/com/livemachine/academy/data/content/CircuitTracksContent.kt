package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/**
 * Novation Circuit Tracks.
 *
 * Aucun contenu technique tant que le manuel officiel Novation n'a pas été fourni :
 * règle du projet, on n'invente aucune fonction. Identifiants futurs préfixés « ct- ».
 */
object CircuitTracksContent {
    const val shortDescription =
        "Groovebox autonome de Novation, à apprendre sur la vraie machine comme l'Electribe."
    const val note =
        "Le parcours de la Circuit Tracks sera rédigé uniquement à partir de son manuel officiel Novation, " +
            "pages citées, comme pour l'Electribe. Aucune fonction n'est décrite avant cette étape."
    const val sourcesLabel = "Manuel officiel Novation Circuit Tracks : à fournir."

    val modules: List<CourseModule> = emptyList()
}
