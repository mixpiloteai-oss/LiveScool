package com.livemachine.academy.data.commerce

import kotlinx.serialization.Serializable

/** Réponse de GET /v1/entitlement et POST /v1/billing/verify. Seul [token] (signé) décide du plan. */
@Serializable
data class EntitlementResponse(
    val token: String,
    val plan: String = "free",
    val limits: PlanLimits? = null,
    val usage: Usage? = null,
    val subscription: SubscriptionInfo? = null,
    val products: Products? = null,
)

@Serializable data class QuotaSet(val aiQuestionsPerDay: Int = 0, val audioAnalysesPerMonth: Int = 0)
@Serializable data class PlanLimits(val free: QuotaSet = QuotaSet(), val premium: QuotaSet = QuotaSet(), val freeModulesPerMachine: Int = 3)
@Serializable data class QuotaUsage(val limit: Int = 0, val used: Int = 0, val remaining: Int = 0, val period: String = "")
@Serializable data class Usage(val ai: QuotaUsage? = null, val audio: QuotaUsage? = null)

@Serializable
data class SubscriptionInfo(
    val productId: String? = null,
    val basePlanId: String? = null,
    val state: String = "",
    val active: Boolean = false,
    val autoRenew: Boolean = false,
    val expiryMs: Long = 0,
    val test: Boolean = false,
)

@Serializable
data class Products(
    val subscriptionId: String = "lma_premium",
    val monthlyBasePlan: String = "mensuel",
    val annualBasePlan: String = "annuel",
)

/** Contenu signé du jeton de droits (voir backend/assistant/src/commerce/entitlements.mjs). */
@Serializable
data class EntitlementPayload(
    val v: Int = 1,
    val installId: String,
    val plan: String,
    val issuedAt: Long,
    val expiresAt: Long,
    val freeModulesPerMachine: Int = 3,
    val test: Boolean = false,
)
