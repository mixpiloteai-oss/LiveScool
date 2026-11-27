package com.livemachine.academy.data.commerce

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.android.billingclient.api.queryProductDetails
import com.android.billingclient.api.queryPurchasesAsync
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/** Offre affichée (prix fourni par Google Play, dans la devise de l'utilisateur). */
data class PremiumOffer(val basePlanId: String, val price: String, val offerToken: String, val details: ProductDetails)

sealed interface BillingEvent {
    data object Idle : BillingEvent
    data object Working : BillingEvent
    data class Message(val text: String, val error: Boolean = false) : BillingEvent
}

/**
 * Google Play Billing. Ce gestionnaire n'accorde JAMAIS Premium lui-même : chaque achat est envoyé au
 * serveur, qui le vérifie auprès de Google Play, en accuse réception et renvoie un jeton de droits signé.
 * [enabled] = false (par défaut) : aucun achat réel possible tant que les tests Google Play ne sont pas faits.
 */
class BillingManager(
    context: Context,
    private val entitlements: EntitlementRepository,
    private val installIds: InstallIdRepository,
    val enabled: Boolean,
) : PurchasesUpdatedListener {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val client: BillingClient = BillingClient.newBuilder(context.applicationContext)
        .setListener(this)
        .enablePendingPurchases(PendingPurchasesParams.newBuilder().enableOneTimeProducts().build())
        .build()

    private val _offers = MutableStateFlow<List<PremiumOffer>>(emptyList())
    val offers: StateFlow<List<PremiumOffer>> = _offers.asStateFlow()
    private val _event = MutableStateFlow<BillingEvent>(BillingEvent.Idle)
    val event: StateFlow<BillingEvent> = _event.asStateFlow()

    private suspend fun connect(): Boolean {
        if (client.isReady) return true
        return suspendCancellableCoroutine { cont ->
            client.startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(result: BillingResult) {
                    if (cont.isActive) cont.resume(result.responseCode == BillingClient.BillingResponseCode.OK)
                }

                override fun onBillingServiceDisconnected() {
                    if (cont.isActive) cont.resume(false)
                }
            })
        }
    }

    /** Charge les offres mensuelle et annuelle du produit d'abonnement. */
    suspend fun loadOffers(products: Products) {
        if (!enabled || !connect()) return
        val params = QueryProductDetailsParams.newBuilder().setProductList(
            listOf(
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(products.subscriptionId)
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build(),
            ),
        ).build()
        val result = client.queryProductDetails(params)
        val details = result.productDetailsList?.firstOrNull() ?: return
        _offers.value = details.subscriptionOfferDetails.orEmpty()
            .filter { it.basePlanId == products.monthlyBasePlan || it.basePlanId == products.annualBasePlan }
            .filter { it.offerId == null } // offre de base (sans promotion)
            .map { o -> PremiumOffer(o.basePlanId, o.pricingPhases.pricingPhaseList.lastOrNull()?.formattedPrice.orEmpty(), o.offerToken, details) }
    }

    fun purchase(activity: Activity, offer: PremiumOffer) {
        if (!enabled) return
        scope.launch {
            if (!connect()) {
                _event.value = BillingEvent.Message("Google Play indisponible.", error = true)
                return@launch
            }
            val params = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(
                    listOf(BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(offer.details).setOfferToken(offer.offerToken).build()),
                )
                .setObfuscatedAccountId(installIds.get())
                .build()
            client.launchBillingFlow(activity, params)
        }
    }

    /** Restauration : retrouve les abonnements du compte Google et les fait revérifier par le serveur. */
    fun restore() {
        if (!enabled) return
        scope.launch {
            _event.value = BillingEvent.Working
            if (!connect()) {
                _event.value = BillingEvent.Message("Google Play indisponible.", error = true)
                return@launch
            }
            val purchases = client.queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build())
                .purchasesList.filter { it.purchaseState == Purchase.PurchaseState.PURCHASED }
            if (purchases.isEmpty()) {
                entitlements.refresh()
                _event.value = BillingEvent.Message("Aucun abonnement trouvé sur ce compte Google.")
                return@launch
            }
            handle(purchases)
        }
    }

    override fun onPurchasesUpdated(result: BillingResult, purchases: MutableList<Purchase>?) {
        when (result.responseCode) {
            BillingClient.BillingResponseCode.OK -> scope.launch { handle(purchases.orEmpty()) }
            BillingClient.BillingResponseCode.USER_CANCELED -> _event.value = BillingEvent.Message("Achat annulé.")
            BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED -> restore()
            else -> _event.value = BillingEvent.Message("Achat impossible (${result.debugMessage}).", error = true)
        }
    }

    private suspend fun handle(purchases: List<Purchase>) {
        _event.value = BillingEvent.Working
        if (purchases.any { it.purchaseState == Purchase.PurchaseState.PENDING }) {
            _event.value = BillingEvent.Message("Paiement en attente : Premium sera activé une fois le paiement confirmé.")
            return
        }
        var ok = false
        var lastError: String? = null
        for (p in purchases.filter { it.purchaseState == Purchase.PurchaseState.PURCHASED }) {
            when (val r = entitlements.verifyPurchase(p.purchaseToken)) {
                is com.livemachine.academy.domain.result.AppResult.Success -> ok = true
                is com.livemachine.academy.domain.result.AppResult.Failure -> lastError = r.error.toString()
            }
        }
        _event.value = if (ok) BillingEvent.Message("Abonnement vérifié par le serveur.")
        else BillingEvent.Message("Vérification de l'achat impossible pour le moment : réessaie avec « Restaurer mes achats ». ($lastError)", error = true)
    }
}
