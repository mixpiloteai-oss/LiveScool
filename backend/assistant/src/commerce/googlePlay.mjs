import { createSign } from 'node:crypto';

/**
 * Client minimal de l'API Google Play Developer (sans dépendance) : compte de service (JSON, secret serveur),
 * jeton OAuth2 signé RS256, lecture d'un abonnement (subscriptionsv2) et accusé de réception.
 */
export function createGooglePlayClient({ serviceAccount, packageName, fetchImpl = fetch, now = () => Date.now() }) {
  if (!serviceAccount?.client_email || !serviceAccount?.private_key) throw new Error('Compte de service Google Play incomplet.');
  let cached = null;

  async function accessToken() {
    if (cached && cached.expiresAt > now() + 60_000) return cached.token;
    const iat = Math.floor(now() / 1000);
    const b64 = (o) => Buffer.from(JSON.stringify(o)).toString('base64url');
    const unsigned = `${b64({ alg: 'RS256', typ: 'JWT' })}.${b64({
      iss: serviceAccount.client_email,
      scope: 'https://www.googleapis.com/auth/androidpublisher',
      aud: 'https://oauth2.googleapis.com/token',
      iat,
      exp: iat + 3600,
    })}`;
    const signature = createSign('RSA-SHA256').update(unsigned).sign(serviceAccount.private_key).toString('base64url');
    const res = await fetchImpl('https://oauth2.googleapis.com/token', {
      method: 'POST',
      headers: { 'content-type': 'application/x-www-form-urlencoded' },
      body: new URLSearchParams({ grant_type: 'urn:ietf:params:oauth:grant-type:jwt-bearer', assertion: `${unsigned}.${signature}` }),
    });
    if (!res.ok) throw new Error(`OAuth Google ${res.status}`);
    const json = await res.json();
    cached = { token: json.access_token, expiresAt: now() + (json.expires_in ?? 3600) * 1000 };
    return cached.token;
  }

  const base = `https://androidpublisher.googleapis.com/androidpublisher/v3/applications/${encodeURIComponent(packageName)}`;

  return {
    name: 'google',
    async getSubscription(purchaseToken) {
      const res = await fetchImpl(`${base}/purchases/subscriptionsv2/tokens/${encodeURIComponent(purchaseToken)}`, {
        headers: { authorization: `Bearer ${await accessToken()}` },
      });
      if (res.status === 404 || res.status === 410) return null; // jeton inconnu ou trop ancien
      if (!res.ok) throw new Error(`Google Play ${res.status}`);
      return res.json();
    },
    async acknowledge(productId, purchaseToken) {
      const res = await fetchImpl(
        `${base}/purchases/subscriptions/${encodeURIComponent(productId)}/tokens/${encodeURIComponent(purchaseToken)}:acknowledge`,
        { method: 'POST', headers: { authorization: `Bearer ${await accessToken()}`, 'content-type': 'application/json' }, body: '{}' },
      );
      if (!res.ok) throw new Error(`Accusé de réception Google Play ${res.status}`);
    },
  };
}

/**
 * Faux Google Play pour les tests et la recette interne (BILLING_MODE=fake). Ne JAMAIS l'utiliser en production :
 * tout jeton « test-… » connu donnerait Premium. Les droits émis portent alors test=true.
 */
export function createFakePlayClient(subscriptions = {}) {
  const acknowledged = [];
  return {
    name: 'fake',
    acknowledged,
    subscriptions,
    async getSubscription(token) {
      return subscriptions[token] ?? null;
    },
    async acknowledge(productId, token) {
      acknowledged.push(token);
      if (subscriptions[token]) subscriptions[token].acknowledgementState = 'ACKNOWLEDGEMENT_STATE_ACKNOWLEDGED';
    },
  };
}
