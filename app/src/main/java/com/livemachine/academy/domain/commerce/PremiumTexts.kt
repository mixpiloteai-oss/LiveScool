package com.livemachine.academy.domain.commerce

import com.livemachine.academy.data.commerce.PlanLimits
import com.livemachine.academy.data.commerce.Products
import com.livemachine.academy.data.commerce.SubscriptionInfo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/** Textes de l'écran Premium (purs, testés). Les limites affichées viennent du serveur. */
object PremiumTexts {
    private fun date(ms: Long, zone: TimeZone = TimeZone.getDefault()): String =
        SimpleDateFormat("d MMMM yyyy", Locale.FRANCE).apply { timeZone = zone }.format(Date(ms))

    fun subscriptionStatus(sub: SubscriptionInfo, zone: TimeZone = TimeZone.getDefault()): String = when {
        sub.active && sub.autoRenew -> "Abonnement ${sub.state} · renouvellement automatique le ${date(sub.expiryMs, zone)}"
        sub.active -> "Abonnement ${sub.state} · accès Premium jusqu'au ${date(sub.expiryMs, zone)}"
        else -> "Abonnement ${sub.state} · offre gratuite"
    }

    fun freeLines(limits: PlanLimits?): List<String> {
        val l = limits ?: PlanLimits(freeModulesPerMachine = CommerceRules.DEFAULT_FREE_MODULES)
        return listOf(
            "Les ${l.freeModulesPerMachine} premiers modules de chaque machine",
            "Quiz, quiz finaux, révision et progression",
            "Exercices des leçons et repérage photo",
            if (limits != null) "Assistant IA : ${l.free.aiQuestionsPerDay} questions par jour" else "Assistant IA : quota quotidien",
            if (limits != null) "Analyse audio : ${l.free.audioAnalysesPerMonth} par mois" else "Analyse audio : quota mensuel",
        )
    }

    fun premiumLines(limits: PlanLimits?): List<String> = listOf(
        "Tous les cours de toutes les machines disponibles",
        "Défis et ateliers avancés",
        if (limits != null) "Assistant IA : ${limits.premium.aiQuestionsPerDay} questions par jour" else "Assistant IA : quota supérieur",
        if (limits != null) "Analyse audio : ${limits.premium.audioAnalysesPerMonth} par mois" else "Analyses audio plus nombreuses",
        "Conseils personnalisés détaillés avec plan d'entraînement",
    )

    fun offerLabel(basePlanId: String, price: String, products: Products): String = when (basePlanId) {
        products.monthlyBasePlan -> "Mensuel · $price / mois"
        products.annualBasePlan -> "Annuel · $price / an"
        else -> "$basePlanId · $price"
    }
}
