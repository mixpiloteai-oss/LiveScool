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
    // Offre gratuite
    COURSES_INTRO("Premiers modules de chaque machine", Plan.FREE, Availability.AVAILABLE),
    QUIZZES("Quiz, quiz finaux et révision des erreurs", Plan.FREE, Availability.AVAILABLE),
    LOCAL_PROGRESS("Progression sauvegardée sur le téléphone", Plan.FREE, Availability.AVAILABLE),
    EXERCISES("Exercices des leçons et repérage photo", Plan.FREE, Availability.AVAILABLE),
    AI_ASSISTANT("Assistant IA (quota quotidien)", Plan.FREE, Availability.AVAILABLE),
    AUDIO_ANALYSIS("Analyses audio (quota mensuel)", Plan.FREE, Availability.AVAILABLE),

    // Premium
    COURSES_ALL("Tous les cours de toutes les machines", Plan.PREMIUM, Availability.AVAILABLE),
    ADVANCED_CHALLENGES("Défis et ateliers avancés", Plan.PREMIUM, Availability.AVAILABLE),
    AI_ASSISTANT_PLUS("Assistant IA avec quota supérieur", Plan.PREMIUM, Availability.AVAILABLE),
    AUDIO_ANALYSIS_PLUS("Analyses audio plus nombreuses", Plan.PREMIUM, Availability.AVAILABLE),
    PERSONALIZED_ADVICE("Conseils personnalisés détaillés et plan d'entraînement", Plan.PREMIUM, Availability.AVAILABLE),

    // Prévu
    ACCOUNT("Compte utilisateur facultatif", Plan.FREE, Availability.PLANNED),
    CLOUD_SYNC("Synchronisation du profil et de la progression", Plan.PREMIUM, Availability.PLANNED),
}

/**
 * Droits de l'installation. Ne vient JAMAIS d'une valeur locale seule : [verified] n'est vrai que si
 * le jeton signé par le serveur a été vérifié (signature, installation, date d'expiration).
 */
data class Entitlements(
    val plan: Plan = Plan.FREE,
    val expiresAt: Long = 0L,
    val freeModulesPerMachine: Int = CommerceRules.DEFAULT_FREE_MODULES,
    val test: Boolean = false,
    val verified: Boolean = false,
) {
    val isPremium: Boolean get() = plan == Plan.PREMIUM && verified

    companion object {
        val Free = Entitlements(Plan.FREE)
    }
}

/** Règles de l'offre gratuite, pures et testées. */
object CommerceRules {
    const val DEFAULT_FREE_MODULES = 3

    /**
     * Un module est inclus si l'offre n'est pas appliquée dans ce build ([enforced] = paiements activés),
     * si l'utilisateur est Premium (vérifié), ou s'il fait partie des premiers modules gratuits.
     */
    fun isModuleIncluded(moduleIndex: Int, entitlements: Entitlements, enforced: Boolean): Boolean =
        !enforced || entitlements.isPremium || moduleIndex < entitlements.freeModulesPerMachine

    /** Défis et ateliers : inclus si leur module requis est inclus (les plus avancés sont Premium). */
    fun isModuleIdIncluded(moduleId: String, moduleIds: List<String>, entitlements: Entitlements, enforced: Boolean): Boolean {
        val index = moduleIds.indexOf(moduleId)
        return index < 0 || isModuleIncluded(index, entitlements, enforced)
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
        feature.requiredPlan == Plan.PREMIUM && !entitlements.isPremium -> Access.RequiresPremium
        else -> Access.Granted
    }

    fun isGranted(feature: Feature, entitlements: Entitlements) = access(feature, entitlements) == Access.Granted
}
