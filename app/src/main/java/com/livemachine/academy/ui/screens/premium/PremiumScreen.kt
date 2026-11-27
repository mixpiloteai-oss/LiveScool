package com.livemachine.academy.ui.screens.premium

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.livemachine.academy.BuildConfig
import com.livemachine.academy.data.commerce.BillingEvent
import com.livemachine.academy.data.commerce.BillingManager
import com.livemachine.academy.data.commerce.EntitlementRepository
import com.livemachine.academy.data.commerce.EntitlementResponse
import com.livemachine.academy.data.commerce.PremiumOffer
import com.livemachine.academy.data.commerce.Products
import com.livemachine.academy.domain.commerce.Availability
import com.livemachine.academy.domain.commerce.Entitlements
import com.livemachine.academy.domain.commerce.Feature
import com.livemachine.academy.domain.commerce.Plan
import com.livemachine.academy.domain.commerce.PremiumTexts
import com.livemachine.academy.ui.appViewModelFactory
import com.livemachine.academy.ui.components.AppTopBar
import com.livemachine.academy.ui.components.Eyebrow
import com.livemachine.academy.ui.components.SectionCard
import com.livemachine.academy.ui.theme.Acid
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PremiumViewModel(
    private val repository: EntitlementRepository,
    private val billing: BillingManager,
    val configured: Boolean,
) : ViewModel() {
    val entitlements: StateFlow<Entitlements> = repository.entitlements
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Entitlements.Free)
    val details: StateFlow<EntitlementResponse?> = repository.details
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
    val offers: StateFlow<List<PremiumOffer>> = billing.offers
    val event: StateFlow<BillingEvent> = billing.event
    val billingEnabled: Boolean get() = billing.enabled

    private val _refreshing = MutableStateFlow(false)
    val refreshing: StateFlow<Boolean> = _refreshing.asStateFlow()

    init {
        refresh()
    }

    /** Relit les droits auprès du serveur, puis les offres Google Play. */
    fun refresh() {
        viewModelScope.launch {
            _refreshing.value = true
            repository.refresh()
            _refreshing.value = false
            billing.loadOffers(repository.details.first()?.products ?: Products())
        }
    }

    fun buy(activity: Activity, offer: PremiumOffer) = billing.purchase(activity, offer)
    fun restore() = billing.restore()

    companion object {
        val Factory = appViewModelFactory { c, _ -> PremiumViewModel(c.entitlementRepository, c.billingManager, c.assistantConfigured) }
    }
}

@Composable
fun PremiumScreen(
    onBack: () -> Unit,
    viewModel: PremiumViewModel = viewModel(factory = PremiumViewModel.Factory),
) {
    val entitlements by viewModel.entitlements.collectAsStateWithLifecycle()
    val details by viewModel.details.collectAsStateWithLifecycle()
    val offers by viewModel.offers.collectAsStateWithLifecycle()
    val event by viewModel.event.collectAsStateWithLifecycle()
    val refreshing by viewModel.refreshing.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = "Premium", onBack = onBack)
        Column(
            Modifier.verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            SectionCard {
                Eyebrow("Ton offre")
                Spacer(Modifier.height(6.dp))
                Text(
                    if (entitlements.isPremium) "Premium" else "Gratuite",
                    style = MaterialTheme.typography.headlineSmall,
                    color = if (entitlements.isPremium) Acid else MaterialTheme.colorScheme.onSurface,
                )
                if (entitlements.test) Text("Mode test (achats de test Google Play)", style = MaterialTheme.typography.labelMedium)
                details?.subscription?.let { sub ->
                    Spacer(Modifier.height(4.dp))
                    Text(PremiumTexts.subscriptionStatus(sub), style = MaterialTheme.typography.bodyMedium)
                }
                details?.usage?.let { usage ->
                    Spacer(Modifier.height(8.dp))
                    usage.ai?.let { Text("Questions à l'assistant : ${it.used} / ${it.limit} par ${it.period}", style = MaterialTheme.typography.bodyMedium) }
                    usage.audio?.let { Text("Analyses audio : ${it.used} / ${it.limit} par ${it.period}", style = MaterialTheme.typography.bodyMedium) }
                }
                if (!viewModel.configured) {
                    Spacer(Modifier.height(6.dp))
                    Text("Serveur non configuré dans cette version : offre gratuite, sans quotas en ligne.", style = MaterialTheme.typography.bodySmall)
                }
                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextButton(onClick = viewModel::refresh, enabled = viewModel.configured && !refreshing) { Text("Actualiser") }
                    if (refreshing) CircularProgressIndicator(Modifier.size(16.dp), strokeWidth = 2.dp)
                }
            }

            SectionCard {
                Eyebrow("Gratuit ou Premium")
                Spacer(Modifier.height(8.dp))
                val limits = details?.limits
                Text("Gratuit", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                PremiumTexts.freeLines(limits).forEach { Text("• $it", style = MaterialTheme.typography.bodyMedium) }
                Spacer(Modifier.height(8.dp))
                Text("Premium", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = Acid)
                PremiumTexts.premiumLines(limits).forEach { Text("• $it", style = MaterialTheme.typography.bodyMedium) }
                val planned = Feature.entries.filter { it.availability == Availability.PLANNED && it.requiredPlan == Plan.PREMIUM }
                if (planned.isNotEmpty()) {
                    Text("Bientôt : " + planned.joinToString { it.label.lowercase() }, style = MaterialTheme.typography.bodySmall)
                }
            }

            SectionCard {
                Eyebrow("Abonnement")
                Spacer(Modifier.height(8.dp))
                if (!viewModel.billingEnabled) {
                    Text(
                        "Paiements désactivés dans cette version. Les abonnements mensuel et annuel seront ouverts " +
                            "après les tests Google Play (achats de test). Aucun paiement réel n'est possible ici.",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                } else {
                    if (offers.isEmpty()) Text("Chargement des offres Google Play…", style = MaterialTheme.typography.bodyMedium)
                    offers.sortedBy { it.basePlanId != (details?.products?.monthlyBasePlan ?: "mensuel") }.forEach { offer ->
                        Button(
                            onClick = { context.findActivity()?.let { viewModel.buy(it, offer) } },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = event !is BillingEvent.Working,
                        ) { Text(PremiumTexts.offerLabel(offer.basePlanId, offer.price, details?.products ?: Products())) }
                        Spacer(Modifier.height(6.dp))
                    }
                    OutlinedButton(onClick = viewModel::restore, enabled = event !is BillingEvent.Working, modifier = Modifier.fillMaxWidth()) {
                        Text("Restaurer mes achats")
                    }
                    if (details?.subscription != null) {
                        TextButton(onClick = { context.openPlaySubscriptions(details?.products?.subscriptionId ?: "lma_premium") }) {
                            Text("Gérer ou résilier sur Google Play")
                        }
                    }
                }
                when (val e = event) {
                    BillingEvent.Working -> Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator(Modifier.size(16.dp), strokeWidth = 2.dp)
                        Spacer(Modifier.width(8.dp))
                        Text("Vérification auprès du serveur…")
                    }
                    is BillingEvent.Message -> Text(
                        e.text,
                        color = if (e.error) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    BillingEvent.Idle -> Unit
                }
                Spacer(Modifier.height(6.dp))
                Text(
                    "Premium est accordé uniquement après vérification de l'achat par notre serveur auprès de Google Play. " +
                        "Résiliation à tout moment dans Google Play : l'accès reste actif jusqu'à la fin de la période payée.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

private fun Context.findActivity(): Activity? {
    var c: Context? = this
    while (c is ContextWrapper) {
        if (c is Activity) return c
        c = c.baseContext
    }
    return null
}

/** Page de gestion des abonnements du Play Store (application Play ou navigateur, pas de WebView). */
private fun Context.openPlaySubscriptions(subscriptionId: String) {
    val uri = Uri.parse("https://play.google.com/store/account/subscriptions?sku=$subscriptionId&package=${BuildConfig.APPLICATION_ID}")
    runCatching { startActivity(Intent(Intent.ACTION_VIEW, uri).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) }
}
