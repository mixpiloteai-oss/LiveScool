package com.livemachine.academy

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.livemachine.academy.data.account.AccountState
import com.livemachine.academy.data.account.GuestAccountRepository
import com.livemachine.academy.data.assistant.MachineInfo
import com.livemachine.academy.data.audio.AudioAnalysisRequest
import com.livemachine.academy.data.audio.AudioPayload
import com.livemachine.academy.data.commerce.EntitlementVerifier
import com.livemachine.academy.data.commerce.FreeEntitlementRepository
import com.livemachine.academy.data.commerce.InstallIdRepository
import com.livemachine.academy.data.commerce.PlanLimits
import com.livemachine.academy.data.commerce.Products
import com.livemachine.academy.data.commerce.QuotaSet
import com.livemachine.academy.data.commerce.RemoteEntitlementRepository
import com.livemachine.academy.data.commerce.SubscriptionInfo
import com.livemachine.academy.data.content.MachineCatalog
import com.livemachine.academy.data.services.UnavailableAudioAnalyzer
import com.livemachine.academy.data.sync.LocalOnlyProgressSync
import com.livemachine.academy.domain.commerce.Access
import com.livemachine.academy.domain.commerce.Availability
import com.livemachine.academy.domain.commerce.CommerceRules
import com.livemachine.academy.domain.commerce.Entitlements
import com.livemachine.academy.domain.commerce.Feature
import com.livemachine.academy.domain.commerce.FeatureGate
import com.livemachine.academy.domain.commerce.Plan
import com.livemachine.academy.domain.commerce.PremiumTexts
import com.livemachine.academy.domain.result.AppError
import com.livemachine.academy.domain.result.AppResult
import com.livemachine.academy.domain.result.appResultOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import java.nio.file.Files
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.Signature
import java.security.spec.ECGenParameterSpec
import java.util.Base64
import java.util.TimeZone

class CommerceTest {
    private val now = 1_800_000_000_000L
    private val install = "11111111-1111-4111-8111-111111111111"
    private val keys = KeyPairGenerator.getInstance("EC").apply { initialize(ECGenParameterSpec("secp256r1")) }.generateKeyPair()
    private val publicKey = Base64.getEncoder().encodeToString(keys.public.encoded)

    /** Jeton au même format que le serveur (base64url(json).base64url(signature DER)). */
    private fun token(plan: String, installId: String = install, expiresAt: Long = now + 3_600_000, key: PrivateKey = keys.private, freeModules: Int = 3): String {
        val json = """{"v":1,"installId":"$installId","plan":"$plan","issuedAt":${now - 1000},"expiresAt":$expiresAt,"freeModulesPerMachine":$freeModules,"test":true}"""
        val body = Base64.getUrlEncoder().withoutPadding().encodeToString(json.toByteArray())
        val sig = Signature.getInstance("SHA256withECDSA").run {
            initSign(key)
            update(body.toByteArray())
            sign()
        }
        return body + "." + Base64.getUrlEncoder().withoutPadding().encodeToString(sig)
    }

    // --- Catalogue gratuit / Premium -------------------------------------------------

    @Test
    fun `offre gratuite et Premium separees`() {
        val free = Feature.entries.filter { it.requiredPlan == Plan.FREE && it.availability == Availability.AVAILABLE }
        val premium = Feature.entries.filter { it.requiredPlan == Plan.PREMIUM && it.availability == Availability.AVAILABLE }
        assertTrue(free.containsAll(listOf(Feature.COURSES_INTRO, Feature.QUIZZES, Feature.LOCAL_PROGRESS, Feature.AI_ASSISTANT, Feature.AUDIO_ANALYSIS)))
        assertTrue(premium.containsAll(listOf(Feature.COURSES_ALL, Feature.ADVANCED_CHALLENGES, Feature.AI_ASSISTANT_PLUS, Feature.AUDIO_ANALYSIS_PLUS, Feature.PERSONALIZED_ADVICE)))
        free.forEach { assertEquals(it.name, Access.Granted, FeatureGate.access(it, Entitlements.Free)) }
        premium.forEach { assertEquals(it.name, Access.RequiresPremium, FeatureGate.access(it, Entitlements.Free)) }
    }

    @Test
    fun `Premium exige un jeton verifie et pas seulement une valeur locale`() {
        val unverified = Entitlements(Plan.PREMIUM, expiresAt = Long.MAX_VALUE, verified = false)
        assertFalse(unverified.isPremium)
        assertEquals(Access.RequiresPremium, FeatureGate.access(Feature.COURSES_ALL, unverified))
        val verified = EntitlementVerifier.verify(token("premium"), publicKey, install, now)!!
        assertTrue(verified.isPremium)
        assertEquals(Access.Granted, FeatureGate.access(Feature.COURSES_ALL, verified))
        Feature.entries.filter { it.availability == Availability.PLANNED }.forEach {
            assertEquals(Access.NotYetAvailable, FeatureGate.access(it, verified))
        }
    }

    // --- Jeton de droits ----------------------------------------------------------------

    @Test
    fun `jeton refuse s il est falsifie expire ou d une autre installation`() {
        assertNotNull(EntitlementVerifier.verify(token("premium"), publicKey, install, now))
        assertNull(EntitlementVerifier.verify(token("premium", expiresAt = now - 1), publicKey, install, now))
        assertNull(EntitlementVerifier.verify(token("premium", installId = "22222222-2222-4222-9222-222222222222"), publicKey, install, now))
        val other = KeyPairGenerator.getInstance("EC").apply { initialize(ECGenParameterSpec("secp256r1")) }.generateKeyPair()
        assertNull(EntitlementVerifier.verify(token("premium", key = other.private), publicKey, install, now))
        val (body, sig) = token("free").split('.')
        val forgedBody = Base64.getUrlEncoder().withoutPadding().encodeToString(
            String(Base64.getUrlDecoder().decode(body)).replace("\"free\"", "\"premium\"").toByteArray(),
        )
        assertNull(EntitlementVerifier.verify("$forgedBody.$sig", publicKey, install, now))
        assertNull(EntitlementVerifier.verify("n'importe quoi", publicKey, install, now))
        assertNull(EntitlementVerifier.verify(token("premium"), "", install, now))
        assertEquals(5, EntitlementVerifier.verify(token("free", freeModules = 5), publicKey, install, now)!!.freeModulesPerMachine)
    }

    // --- Verrous de contenu --------------------------------------------------------------

    @Test
    fun `modules gratuits puis Premium seulement quand les paiements sont actives`() {
        val free = Entitlements.Free
        val premium = EntitlementVerifier.verify(token("premium"), publicKey, install, now)!!
        // Paiements désactivés (build actuel) : tout reste ouvert
        (0 until 12).forEach { assertTrue(CommerceRules.isModuleIncluded(it, free, enforced = false)) }
        // Paiements activés : 3 premiers modules gratuits, le reste Premium
        assertTrue(CommerceRules.isModuleIncluded(2, free, enforced = true))
        assertFalse(CommerceRules.isModuleIncluded(3, free, enforced = true))
        assertTrue(CommerceRules.isModuleIncluded(11, premium, enforced = true))
        // Défis : selon leur module requis
        val ids = MachineCatalog.circuitTracks.modules.map { it.id }
        assertTrue(CommerceRules.isModuleIdIncluded("ct-m2", ids, free, enforced = true))
        assertFalse(CommerceRules.isModuleIdIncluded("ct-m9", ids, free, enforced = true))
        assertTrue(CommerceRules.isModuleIdIncluded("ct-m9", ids, premium, enforced = true))
    }

    // --- Dépôt des droits face au serveur -------------------------------------------------

    @Test
    fun `droits du serveur verifies et conserves pour une utilisation hors ligne limitee`() = runBlocking {
        val dir: File = Files.createTempDirectory("ent").toFile()
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        try {
            val installIds = InstallIdRepository(PreferenceDataStoreFactory.create(scope = scope) { File(dir, "s.preferences_pb") })
            val id = installIds.get()
            assertEquals(id, installIds.get()) // stable
            assertTrue(Regex("^[0-9a-f-]{36}$").matches(id))
            var clock = now
            val ent = PreferenceDataStoreFactory.create(scope = scope) { File(dir, "e.preferences_pb") }
            val body = """{"token":"${token("premium", installId = id, expiresAt = now + 72 * 3_600_000L)}","plan":"premium",
                "usage":{"ai":{"limit":60,"used":2,"remaining":58,"period":"jour"}},
                "subscription":{"productId":"lma_premium","state":"actif","active":true,"autoRenew":true,"expiryMs":${now + 30L * 86_400_000}}}"""
            withHttpServer(200, body) { url, _ ->
                val repo = RemoteEntitlementRepository(ent, installIds, url, publicKey) { clock }
                assertFalse(runBlocking { repo.entitlements.first() }.isPremium)
                assertEquals(AppResult.Success(Unit), runBlocking { repo.refresh() })
                assertTrue(runBlocking { repo.entitlements.first() }.isPremium)
                assertEquals(2, runBlocking { repo.details.first() }!!.usage!!.ai!!.used)
                // Hors ligne au-delà de l'expiration du jeton : retour à l'offre gratuite
                clock = now + 73 * 3_600_000L
                assertFalse(runBlocking { repo.entitlements.first() }.isPremium)
            }
            // Jeton signé par une autre clé : refusé et non enregistré
            val other = KeyPairGenerator.getInstance("EC").apply { initialize(ECGenParameterSpec("secp256r1")) }.generateKeyPair()
            withHttpServer(200, """{"token":"${token("premium", installId = id, key = other.private)}"}""") { url, _ ->
                val repo = RemoteEntitlementRepository(PreferenceDataStoreFactory.create(scope = scope) { File(dir, "e2.preferences_pb") }, installIds, url, publicKey) { now }
                assertTrue(runBlocking { repo.refresh() } is AppResult.Failure)
                assertFalse(runBlocking { repo.entitlements.first() }.isPremium)
            }
            // Achat refusé par le serveur (non trouvé chez Google)
            withHttpServer(422, """{"error":{"code":"purchase_not_found","message":"Achat introuvable chez Google Play."}}""") { url, received ->
                val repo = RemoteEntitlementRepository(PreferenceDataStoreFactory.create(scope = scope) { File(dir, "e3.preferences_pb") }, installIds, url, publicKey) { now }
                assertEquals(AppResult.Failure(AppError.Rejected("Achat introuvable chez Google Play.")), runBlocking { repo.verifyPurchase("tok") })
                assertEquals("""{"purchaseToken":"tok"}""", received())
            }
        } finally {
            scope.cancel()
            dir.deleteRecursively()
        }
    }

    // --- Textes de l'écran Premium ---------------------------------------------------------

    @Test
    fun `textes de l abonnement et des offres`() {
        val utc = TimeZone.getTimeZone("UTC")
        val expiry = 1_798_761_600_000L // 1er janvier 2027
        assertEquals("Abonnement actif · renouvellement automatique le 1 janvier 2027",
            PremiumTexts.subscriptionStatus(SubscriptionInfo(state = "actif", active = true, autoRenew = true, expiryMs = expiry), utc))
        assertEquals("Abonnement annulé, actif jusqu'à l'échéance · accès Premium jusqu'au 1 janvier 2027",
            PremiumTexts.subscriptionStatus(SubscriptionInfo(state = "annulé, actif jusqu'à l'échéance", active = true, autoRenew = false, expiryMs = expiry), utc))
        assertEquals("Abonnement expiré · offre gratuite", PremiumTexts.subscriptionStatus(SubscriptionInfo(state = "expiré"), utc))
        val limits = PlanLimits(QuotaSet(5, 3), QuotaSet(60, 40), 3)
        assertTrue(PremiumTexts.freeLines(limits).contains("Assistant IA : 5 questions par jour"))
        assertTrue(PremiumTexts.premiumLines(limits).contains("Analyse audio : 40 par mois"))
        assertEquals("Mensuel · 4,99 € / mois", PremiumTexts.offerLabel("mensuel", "4,99 €", Products()))
        assertEquals("Annuel · 39,99 € / an", PremiumTexts.offerLabel("annuel", "39,99 €", Products()))
    }

    // --- Implémentations locales -----------------------------------------------------------

    @Test
    fun `implementations locales sans serveur`() = runBlocking {
        assertEquals(Entitlements.Free, FreeEntitlementRepository().entitlements.first())
        assertEquals(AppResult.Failure(AppError.NotAvailable), FreeEntitlementRepository().verifyPurchase("x"))
        assertEquals(AccountState.Guest, GuestAccountRepository().state.first())
        assertEquals(AppResult.Failure(AppError.NotAvailable), LocalOnlyProgressSync().pull("circuit-tracks"))
        assertEquals(
            AppResult.Failure(AppError.NotAvailable),
            UnavailableAudioAnalyzer().analyze(
                AudioAnalysisRequest(MachineInfo("circuit-tracks", "Novation Circuit Tracks"), "hardtek", "live", "débutant", emptyList(), AudioPayload("wav", "")),
            ),
        )
        val r = appResultOf<Int> { error("boom") }
        assertEquals(AppResult.Failure(AppError.Unexpected("boom")), r)
    }
}
