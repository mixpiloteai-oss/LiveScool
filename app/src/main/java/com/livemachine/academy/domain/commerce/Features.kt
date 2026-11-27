package com.livemachine.academy.domain.commerce

/** Offre de l'utilisateur. Seule l'offre gratuite existe pour l'instant (aucun paiement). */
enum class Plan { FREE, PREMIUM }

/** Où en est une fonctionnalité dans le produit. */
enum class Availability {
    /** Disponible dans l'application actuelle. */
    AVAILABLE,

    /** Prévue : la structure existe, aucune implémentation n'est active. */
    PLANNED,
}

/**
 * Catalogue des fonctionnalités et de leur offre. Tout le contenu actuel reste gratuit ;
 * les fonctions Premium sont seulement déclarées, elles ne sont pas activées.
 */
enum class Feature(val label: String, val requiredPlan: Plan, val availability: Availability) {
    // Existant, gratuit
    COURSES("Parcours Electribe et Circuit Tracks", Plan.FREE, Availability.AVAILABLE),
    QUIZZES("Quiz, quiz finaux et révision", Plan.FREE, Availability.AVAILABLE),
    EXERCISES("Exercices, défis et ateliers", Plan.FREE, Availability.AVAILABLE),
    PHOTO_TRAINING("Repérage sur photo", Plan.FREE, Availability.AVAILABLE),
    LOCAL_PROGRESS("Progression sur le téléphone", Plan.FREE, Availability.AVAILABLE),

    // Prévu
    ACCOUNT("Compte utilisateur facultatif", Plan.FREE, Availability.PLANNED),
    CLOUD_SYNC("Synchronisation du profil et de la progression", Plan.PREMIUM, Availability.PLANNED),
    AI_ASSISTANT("Assistant musical IA", Plan.PREMIUM, Availability.PLANNED),
    AUDIO_ANALYSIS("Analyse audio", Plan.PREMIUM, Availability.PLANNED),
}

/** Droits de l'utilisateur, issus plus tard d'un abonnement vérifié. */
data class Entitlements(val plan: Plan = Plan.FREE) {
    companion object {
        val Free = Entitlements(Plan.FREE)
    }
}

/** Résultat d'un contrôle d'accès. */
sealed interface Access {
    data object Granted : Access
    data object RequiresPremium : Access
    data object NotYetAvailable : Access
}

/** Règle unique d'accès aux fonctionnalités : pure, testable, sans effet de bord. */
object FeatureGate {
    fun access(feature: Feature, entitlements: Entitlements): Access = when {
        feature.availability == Availability.PLANNED -> Access.NotYetAvailable
        feature.requiredPlan == Plan.PREMIUM && entitlements.plan != Plan.PREMIUM -> Access.RequiresPremium
        else -> Access.Granted
    }

    fun isGranted(feature: Feature, entitlements: Entitlements) = access(feature, entitlements) == Access.Granted
}
