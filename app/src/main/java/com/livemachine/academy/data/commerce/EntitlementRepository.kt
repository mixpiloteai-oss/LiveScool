package com.livemachine.academy.data.commerce

import com.livemachine.academy.domain.commerce.Entitlements
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Source des droits de l'utilisateur. Plus tard : abonnement vérifié (Google Play Billing
 * + vérification serveur). Aujourd'hui : toujours l'offre gratuite, aucun paiement.
 */
interface EntitlementRepository {
    val entitlements: Flow<Entitlements>
}

class FreeEntitlementRepository : EntitlementRepository {
    override val entitlements: Flow<Entitlements> = flowOf(Entitlements.Free)
}
