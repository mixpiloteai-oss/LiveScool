package com.livemachine.academy

import com.livemachine.academy.data.account.AccountState
import com.livemachine.academy.data.account.GuestAccountRepository
import com.livemachine.academy.data.assistant.AssistantRequest
import com.livemachine.academy.data.assistant.MachineInfo
import com.livemachine.academy.data.commerce.FreeEntitlementRepository
import com.livemachine.academy.data.services.UnavailableAudioAnalyzer
import com.livemachine.academy.data.services.UnavailableMusicAssistant
import com.livemachine.academy.data.sync.LocalOnlyProgressSync
import com.livemachine.academy.domain.commerce.Access
import com.livemachine.academy.domain.commerce.Availability
import com.livemachine.academy.domain.commerce.Entitlements
import com.livemachine.academy.domain.commerce.Feature
import com.livemachine.academy.domain.commerce.FeatureGate
import com.livemachine.academy.domain.commerce.Plan
import com.livemachine.academy.domain.result.AppError
import com.livemachine.academy.domain.result.AppResult
import com.livemachine.academy.domain.result.appResultOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CommerceTest {

    @Test
    fun `tout le contenu actuel reste gratuit`() {
        Feature.entries.filter { it.availability == Availability.AVAILABLE }.forEach {
            assertEquals(it.name, Plan.FREE, it.requiredPlan)
            assertEquals(it.name, Access.Granted, FeatureGate.access(it, Entitlements.Free))
        }
    }

    @Test
    fun `les fonctions prevues ne sont pas activees meme en Premium`() {
        val planned = Feature.entries.filter { it.availability == Availability.PLANNED }
        assertTrue(planned.containsAll(listOf(Feature.CLOUD_SYNC, Feature.AUDIO_ANALYSIS, Feature.ACCOUNT)))
        planned.forEach {
            assertEquals(Access.NotYetAvailable, FeatureGate.access(it, Entitlements.Free))
            assertEquals(Access.NotYetAvailable, FeatureGate.access(it, Entitlements(Plan.PREMIUM)))
        }
    }

    @Test
    fun `les fonctions payantes sont separees et exigent Premium`() {
        val premium = Feature.entries.filter { it.requiredPlan == Plan.PREMIUM }
        assertEquals(setOf(Feature.CLOUD_SYNC, Feature.AUDIO_ANALYSIS), premium.toSet())
        // MVP : assistant disponible et gratuit
        assertEquals(Access.Granted, FeatureGate.access(Feature.AI_ASSISTANT, Entitlements.Free))
    }

    @Test
    fun `implementations actuelles locales et inactives`() = runBlocking {
        assertEquals(Entitlements.Free, FreeEntitlementRepository().entitlements.first())
        assertEquals(AccountState.Guest, GuestAccountRepository().state.first())
        assertEquals(AppResult.Failure(AppError.NotAvailable), LocalOnlyProgressSync().pull("circuit-tracks"))
        assertEquals(
            AppResult.Failure(AppError.NotAvailable),
            UnavailableMusicAssistant().ask(
                AssistantRequest(MachineInfo("circuit-tracks", "Novation Circuit Tracks"), null, "débutant", emptyList(), emptyList(), "?"),
            ),
        )
        assertEquals(AppResult.Failure(AppError.NotAvailable), UnavailableAudioAnalyzer().analyze("x.wav"))
    }

    @Test
    fun `les exceptions deviennent des erreurs typees`() = runBlocking {
        val r = appResultOf<Int> { error("boom") }
        assertEquals(AppResult.Failure(AppError.Unexpected("boom")), r)
        assertEquals(AppResult.Success(2), appResultOf { 1 + 1 })
    }
}
