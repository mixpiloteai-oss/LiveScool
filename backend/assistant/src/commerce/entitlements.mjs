import { createPrivateKey, createPublicKey, generateKeyPairSync, sign } from 'node:crypto';
import { describe, isPremium, toRecord } from './subscription.mjs';

export const INSTALL_ID = /^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/;

/**
 * Jeton de droits : `base64url(json).base64url(signature ECDSA P-256 SHA-256, DER)`.
 * Signé par le serveur (clé privée secrète), vérifié par l'app (clé publique, non secrète) :
 * l'app ne peut pas se déclarer Premium elle-même.
 */
export function signToken(payload, privateKey) {
  const body = Buffer.from(JSON.stringify(payload)).toString('base64url');
  const sig = sign('sha256', Buffer.from(body), { key: privateKey, dsaEncoding: 'der' }).toString('base64url');
  return `${body}.${sig}`;
}

export function loadSigningKey(pem) {
  return createPrivateKey(pem);
}

/** Paire de clés de développement/test. En production : LMA_ENTITLEMENT_PRIVATE_KEY (PEM). */
export function generateSigningKey() {
  const { privateKey } = generateKeyPairSync('ec', { namedCurve: 'P-256' });
  return privateKey;
}

export function publicKeyBase64(privateKey) {
  return createPublicKey(privateKey).export({ type: 'spki', format: 'der' }).toString('base64');
}

const dayKey = (t) => new Date(t).toISOString().slice(0, 10);
const monthKey = (t) => new Date(t).toISOString().slice(0, 7);

/**
 * Droits, quotas et abonnements. Le plan d'une installation vient toujours de l'abonnement
 * vérifié auprès de Google Play et enregistré côté serveur, jamais d'une valeur envoyée par l'app.
 */
export function createCommerce({ store, config, play, signingKey, now = () => Date.now(), log = console }) {
  const products = config.products;

  function recordOf(installId) {
    const token = store.tokenOfInstall(installId);
    return token ? store.getSubscription(token) : null;
  }

  function planOf(installId) {
    return isPremium(recordOf(installId), now()) ? 'premium' : 'free';
  }

  const QUOTAS = {
    ai: { limitKey: 'aiQuestionsPerDay', period: 'jour', key: (id, t) => `ai:${id}:${dayKey(t)}` },
    audio: { limitKey: 'audioAnalysesPerMonth', period: 'mois', key: (id, t) => `audio:${id}:${monthKey(t)}` },
  };

  function usage(installId) {
    const plan = planOf(installId);
    const out = {};
    for (const [kind, q] of Object.entries(QUOTAS)) {
      const limit = config[plan][q.limitKey];
      const used = store.usage(q.key(installId, now()));
      out[kind] = { limit, used, remaining: Math.max(0, limit - used), period: q.period };
    }
    return out;
  }

  return {
    planOf,

    /** Réserve une utilisation ; { ok:false } si la limite du plan est atteinte. */
    consume(installId, kind) {
      const q = QUOTAS[kind];
      const plan = planOf(installId);
      const limit = config[plan][q.limitKey];
      const key = q.key(installId, now());
      const used = store.usage(key);
      if (config.enforceQuotas && used >= limit) return { ok: false, plan, limit, used, period: q.period };
      store.addUsage(key, 1);
      return { ok: true, plan, limit, used: used + 1, period: q.period, refund: () => store.addUsage(key, -1) };
    },

    /** Droits signés + configuration + usage, pour l'app. */
    entitlement(installId) {
      const record = recordOf(installId);
      const premium = isPremium(record, now());
      const ttl = config.entitlementTtlHours * 3_600_000;
      const expiresAt = premium ? Math.min(record.expiryMs, now() + ttl) : now() + ttl;
      const payload = {
        v: 1,
        installId,
        plan: premium ? 'premium' : 'free',
        issuedAt: now(),
        expiresAt,
        freeModulesPerMachine: config.freeModulesPerMachine,
        test: Boolean(record?.testPurchase || play.name === 'fake'),
      };
      return {
        token: signToken(payload, signingKey),
        plan: payload.plan,
        limits: { free: config.free, premium: config.premium, freeModulesPerMachine: config.freeModulesPerMachine },
        usage: usage(installId),
        subscription: describe(record, now()),
        products,
      };
    },

    /** Vérifie un achat auprès de Google Play, l'enregistre, en accuse réception et le lie à l'installation. */
    async verifyPurchase(installId, purchaseToken) {
      const google = await play.getSubscription(purchaseToken);
      if (!google) return { ok: false, code: 'purchase_not_found', message: 'Achat introuvable chez Google Play.' };
      const record = toRecord(purchaseToken, installId, google, products);
      if (record.productId !== products.subscriptionId) {
        return { ok: false, code: 'unknown_product', message: 'Produit inconnu.' };
      }
      if (!record.acknowledged && isPremium(record, now())) {
        try {
          await play.acknowledge(record.productId, purchaseToken);
          record.acknowledged = true;
        } catch (e) {
          log.warn?.(`billing: accusé de réception à refaire (${e.message})`);
        }
      }
      // Un renouvellement après changement d'offre remplace l'ancien jeton.
      if (record.linkedPurchaseToken) {
        const old = store.getSubscription(record.linkedPurchaseToken);
        if (old) store.putSubscription({ ...old, state: 'SUBSCRIPTION_STATE_EXPIRED', replacedBy: purchaseToken });
      }
      store.putSubscription(record);
      store.linkInstall(installId, purchaseToken);
      return { ok: true, entitlement: this.entitlement(installId) };
    },

    /** Notification Google Play (RTDN) : on relit l'abonnement au lieu de croire le message. */
    async onNotification(purchaseToken) {
      const known = store.getSubscription(purchaseToken);
      const google = await play.getSubscription(purchaseToken);
      if (!google) {
        if (known) store.putSubscription({ ...known, state: 'SUBSCRIPTION_STATE_EXPIRED' });
        return;
      }
      const record = toRecord(purchaseToken, known?.installId ?? null, google, products);
      store.putSubscription(record);
    },
  };
}
