package com.livemachine.academy.data.commerce

import com.livemachine.academy.domain.commerce.Entitlements
import com.livemachine.academy.domain.commerce.Plan
import kotlinx.serialization.json.Json
import java.security.KeyFactory
import java.security.Signature
import java.security.spec.X509EncodedKeySpec
import java.util.Base64

/**
 * Vérifie un jeton de droits signé par le serveur (ECDSA P-256 / SHA-256) avec la clé PUBLIQUE de l'app.
 * Un jeton modifié, d'une autre installation ou expiré est refusé : l'utilisateur reste en offre gratuite.
 */
object EntitlementVerifier {
    private val json = Json { ignoreUnknownKeys = true }
    private const val CLOCK_SKEW_MS = 5 * 60_000L

    fun verify(token: String, publicKeyBase64: String, installId: String, now: Long): Entitlements? {
        if (publicKeyBase64.isBlank()) return null
        val parts = token.split('.')
        if (parts.size != 2) return null
        return runCatching {
            val key = KeyFactory.getInstance("EC").generatePublic(X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyBase64)))
            val ok = Signature.getInstance("SHA256withECDSA").run {
                initVerify(key)
                update(parts[0].toByteArray(Charsets.US_ASCII))
                verify(Base64.getUrlDecoder().decode(parts[1]))
            }
            if (!ok) return@runCatching null
            val payload = json.decodeFromString(EntitlementPayload.serializer(), String(Base64.getUrlDecoder().decode(parts[0]), Charsets.UTF_8))
            if (payload.installId != installId || payload.expiresAt <= now || payload.issuedAt > now + CLOCK_SKEW_MS) return@runCatching null
            Entitlements(
                plan = if (payload.plan == "premium") Plan.PREMIUM else Plan.FREE,
                expiresAt = payload.expiresAt,
                freeModulesPerMachine = payload.freeModulesPerMachine.coerceAtLeast(1),
                test = payload.test,
                verified = true,
            )
        }.getOrNull()
    }
}
