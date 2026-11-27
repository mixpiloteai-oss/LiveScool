import { test } from 'node:test';
import assert from 'node:assert/strict';
import { createServer } from 'node:http';
import { createPublicKey, verify } from 'node:crypto';
import { mkdtempSync, rmSync } from 'node:fs';
import { tmpdir } from 'node:os';
import { join } from 'node:path';
import { createHandler } from '../src/app.mjs';
import { DEFAULT_PLAN_CONFIG, loadPlanConfig } from '../src/commerce/config.mjs';
import { createStore } from '../src/commerce/store.mjs';
import { createCommerce, generateSigningKey } from '../src/commerce/entitlements.mjs';
import { createFakePlayClient } from '../src/commerce/googlePlay.mjs';
import { isPremium, toRecord } from '../src/commerce/subscription.mjs';
import { encodeWav } from '../src/audio/wav.mjs';
import { synth } from './synth.mjs';

const DAY = 86_400_000;
const T0 = Date.parse('2026-12-01T10:00:00Z');
const A = '11111111-1111-4111-8111-111111111111';
const B = '22222222-2222-4222-9222-222222222222';
const products = DEFAULT_PLAN_CONFIG.products;

const sub = (state, expiry, extra = {}) => ({
  subscriptionState: state,
  acknowledgementState: 'ACKNOWLEDGEMENT_STATE_PENDING',
  lineItems: [{ productId: 'lma_premium', expiryTime: new Date(expiry).toISOString(), autoRenewingPlan: { autoRenewEnabled: state === 'SUBSCRIPTION_STATE_ACTIVE' }, offerDetails: { basePlanId: 'mensuel' } }],
  testPurchase: {},
  ...extra,
});

test('configuration : défauts, surcharge partielle, valeurs invalides refusées', () => {
  assert.deepEqual(loadPlanConfig({}), { ...DEFAULT_PLAN_CONFIG });
  const c = loadPlanConfig({ LMA_PLAN_CONFIG: '{"free":{"aiQuestionsPerDay":2},"freeModulesPerMachine":4,"inconnu":1}' });
  assert.equal(c.free.aiQuestionsPerDay, 2);
  assert.equal(c.free.audioAnalysesPerMonth, DEFAULT_PLAN_CONFIG.free.audioAnalysesPerMonth);
  assert.equal(c.freeModulesPerMachine, 4);
  assert.equal(c.inconnu, undefined);
  assert.throws(() => loadPlanConfig({ LMA_PLAN_CONFIG: '{"premium":{"aiQuestionsPerDay":-1}}' }));
  assert.throws(() => loadPlanConfig({ LMA_PLAN_CONFIG: 'pas du json' }));
});

test('états Google Play → Premium seulement si actif, en grâce ou annulé avant échéance', () => {
  const r = (state, expiry) => toRecord('t', A, sub(state, expiry), products);
  assert.equal(isPremium(r('SUBSCRIPTION_STATE_ACTIVE', T0 + DAY), T0), true);
  assert.equal(isPremium(r('SUBSCRIPTION_STATE_IN_GRACE_PERIOD', T0 + DAY), T0), true);
  assert.equal(isPremium(r('SUBSCRIPTION_STATE_CANCELED', T0 + DAY), T0), true);
  assert.equal(isPremium(r('SUBSCRIPTION_STATE_CANCELED', T0 - 1), T0), false);
  assert.equal(isPremium(r('SUBSCRIPTION_STATE_ACTIVE', T0 - 1), T0), false); // date dépassée
  for (const s of ['SUBSCRIPTION_STATE_ON_HOLD', 'SUBSCRIPTION_STATE_PAUSED', 'SUBSCRIPTION_STATE_EXPIRED', 'SUBSCRIPTION_STATE_PENDING']) {
    assert.equal(isPremium(r(s, T0 + DAY), T0), false, s);
  }
  assert.equal(r('SUBSCRIPTION_STATE_ACTIVE', T0 + DAY).autoRenew, true);
  assert.equal(r('SUBSCRIPTION_STATE_CANCELED', T0 + DAY).autoRenew, false);
});

function setup({ config = {}, providerFails = false } = {}) {
  let now = T0;
  const play = createFakePlayClient({
    'tok-actif': sub('SUBSCRIPTION_STATE_ACTIVE', T0 + 30 * DAY),
    'tok-annuel': sub('SUBSCRIPTION_STATE_ACTIVE', T0 + 365 * DAY),
    'tok-attente': sub('SUBSCRIPTION_STATE_PENDING', T0 + 30 * DAY),
    'tok-autre-produit': { ...sub('SUBSCRIPTION_STATE_ACTIVE', T0 + DAY), lineItems: [{ productId: 'autre', expiryTime: new Date(T0 + DAY).toISOString() }] },
  });
  const signingKey = generateSigningKey();
  const cfg = loadPlanConfig({ LMA_PLAN_CONFIG: JSON.stringify({ free: { aiQuestionsPerDay: 2, audioAnalysesPerMonth: 1 }, premium: { aiQuestionsPerDay: 4, audioAnalysesPerMonth: 3 }, ...config }) });
  const commerce = createCommerce({ store: createStore(), config: cfg, play, signingKey, now: () => now, log: {} });
  const provider = { name: 'fake', complete: async () => { if (providerFails) throw new Error('panne'); return { text: 'ok', truncated: false }; } };
  return { play, commerce, provider, signingKey, advance: (ms) => { now += ms; }, setNow: (t) => { now = t; } };
}

async function withServer(env, fn) {
  const server = createServer(createHandler({ provider: env.provider, commerce: env.commerce, rtdnSecret: 'secret-rtdn', log: {} }));
  await new Promise((r) => server.listen(0, r));
  const base = `http://127.0.0.1:${server.address().port}`;
  const call = (path, { install, body, method } = {}) => fetch(base + path, {
    method: method ?? (body ? 'POST' : 'GET'),
    headers: { 'content-type': 'application/json', ...(install ? { 'x-lma-install': install } : {}) },
    body: body ? JSON.stringify(body) : undefined,
  });
  try { return await fn(call); } finally { server.close(); }
}

const ask = { machine: { id: 'circuit-tracks', name: 'Circuit Tracks' }, level: 'débutant', context: [], history: [], message: 'Une idée de break ?' };

function verifyToken(token, signingKey) {
  const [body, sig] = token.split('.');
  const ok = verify('sha256', Buffer.from(body), { key: createPublicKey(signingKey), dsaEncoding: 'der' }, Buffer.from(sig, 'base64url'));
  return ok ? JSON.parse(Buffer.from(body, 'base64url').toString()) : null;
}

test('achat : vérification Google, accusé de réception, jeton signé Premium', async () => {
  const env = setup();
  await withServer(env, async (call) => {
    let r = await call('/v1/entitlement', { install: A });
    let e = await r.json();
    assert.equal(e.plan, 'free');
    assert.equal(verifyToken(e.token, env.signingKey).plan, 'free');

    r = await call('/v1/billing/verify', { install: A, body: { purchaseToken: 'tok-actif' } });
    assert.equal(r.status, 200);
    e = await r.json();
    assert.equal(e.plan, 'premium');
    assert.equal(e.subscription.active, true);
    assert.equal(e.subscription.autoRenew, true);
    assert.deepEqual(env.play.acknowledged, ['tok-actif']);
    const payload = verifyToken(e.token, env.signingKey);
    assert.equal(payload.plan, 'premium');
    assert.equal(payload.installId, A);
    assert.equal(payload.expiresAt, T0 + 72 * 3_600_000); // jamais au-delà de 72 h sans nouvelle vérification
    assert.equal(payload.test, true);
    // Jeton falsifié : signature invalide
    const [, sig] = e.token.split('.');
    const forged = `${Buffer.from(JSON.stringify({ ...payload, installId: B })).toString('base64url')}.${sig}`;
    assert.equal(verifyToken(forged, env.signingKey), null);
  });
});

test('achats refusés : en attente, autre produit, jeton inconnu, Google indisponible', async () => {
  const env = setup();
  await withServer(env, async (call) => {
    let r = await call('/v1/billing/verify', { install: A, body: { purchaseToken: 'tok-attente' } });
    assert.equal((await r.json()).plan, 'free'); // achat en attente : pas Premium, pas d'accusé
    assert.deepEqual(env.play.acknowledged, []);
    r = await call('/v1/billing/verify', { install: A, body: { purchaseToken: 'tok-autre-produit' } });
    assert.equal((await r.json()).error.code, 'unknown_product');
    r = await call('/v1/billing/verify', { install: A, body: { purchaseToken: 'inconnu' } });
    assert.equal((await r.json()).error.code, 'purchase_not_found');
    env.play.getSubscription = async () => { throw new Error('réseau'); };
    r = await call('/v1/billing/verify', { install: A, body: { purchaseToken: 'tok-actif' } });
    assert.equal(r.status, 502);
  });
});

test('restauration sur une nouvelle installation ; Premium jamais déclaré par l\'app', async () => {
  const env = setup();
  await withServer(env, async (call) => {
    await call('/v1/billing/verify', { install: A, body: { purchaseToken: 'tok-annuel' } });
    let e = await (await call('/v1/entitlement', { install: B })).json();
    assert.equal(e.plan, 'free');
    // L'app ne peut pas forcer Premium : un champ « plan » envoyé est ignoré
    await call('/v1/assistant', { install: B, body: { ...ask, plan: 'premium' } });
    assert.equal(env.commerce.planOf(B), 'free');
    // Restauration (jeton retrouvé par Google Play Billing sur le même compte)
    e = await (await call('/v1/billing/verify', { install: B, body: { purchaseToken: 'tok-annuel' } })).json();
    assert.equal(e.plan, 'premium');
    assert.equal(e.subscription.expiryMs, T0 + 365 * DAY);
  });
});

test('expiration, annulation, suspension et renouvellement', async () => {
  const env = setup();
  await withServer(env, async (call) => {
    await call('/v1/billing/verify', { install: A, body: { purchaseToken: 'tok-actif' } });
    assert.equal(env.commerce.planOf(A), 'premium');

    // Annulation : Premium jusqu'à l'échéance, plus de renouvellement automatique
    env.play.subscriptions['tok-actif'] = sub('SUBSCRIPTION_STATE_CANCELED', T0 + 30 * DAY);
    const notify = (token) => call('/v1/billing/rtdn?secret=secret-rtdn', {
      body: { message: { data: Buffer.from(JSON.stringify({ subscriptionNotification: { purchaseToken: token, notificationType: 3 } })).toString('base64') } },
    });
    assert.equal((await notify('tok-actif')).status, 200);
    let e = await (await call('/v1/entitlement', { install: A })).json();
    assert.equal(e.plan, 'premium');
    assert.equal(e.subscription.autoRenew, false);
    assert.match(e.subscription.state, /annulé/);

    // Échéance dépassée : retour à l'offre gratuite, même sans notification
    env.advance(31 * DAY);
    e = await (await call('/v1/entitlement', { install: A })).json();
    assert.equal(e.plan, 'free');

    // Suspension (paiement refusé) notifiée
    env.setNow(T0);
    env.play.subscriptions['tok-actif'] = sub('SUBSCRIPTION_STATE_ON_HOLD', T0 + 30 * DAY);
    await notify('tok-actif');
    assert.equal(env.commerce.planOf(A), 'free');

    // Renouvellement avec nouveau jeton (changement d'offre) : l'ancien est remplacé
    env.play.subscriptions['tok-nouveau'] = sub('SUBSCRIPTION_STATE_ACTIVE', T0 + 365 * DAY, { linkedPurchaseToken: 'tok-actif' });
    e = await (await call('/v1/billing/verify', { install: A, body: { purchaseToken: 'tok-nouveau' } })).json();
    assert.equal(e.plan, 'premium');

    // Notification sans le bon secret refusée
    assert.equal((await call('/v1/billing/rtdn?secret=faux', { body: {} })).status, 403);
  });
});

test('quotas : limites gratuites et Premium, remise à zéro, pas de quota consommé sur erreur', async () => {
  const env = setup();
  await withServer(env, async (call) => {
    assert.equal((await call('/v1/assistant', { body: ask })).status, 400); // installation obligatoire
    assert.equal((await call('/v1/assistant', { install: A, body: ask })).status, 200);
    assert.equal((await call('/v1/assistant', { install: A, body: ask })).status, 200);
    let r = await call('/v1/assistant', { install: A, body: ask });
    assert.equal(r.status, 429);
    const j = await r.json();
    assert.equal(j.error.code, 'quota_exceeded');
    assert.match(j.error.message, /2 questions à l'assistant par jour avec l'offre gratuite/);
    // Premium : quota supérieur
    await call('/v1/billing/verify', { install: A, body: { purchaseToken: 'tok-actif' } });
    assert.equal((await call('/v1/assistant', { install: A, body: ask })).status, 200);
    assert.equal((await call('/v1/assistant', { install: A, body: ask })).status, 200);
    assert.equal((await call('/v1/assistant', { install: A, body: ask })).status, 429);
    const usage = (await (await call('/v1/entitlement', { install: A })).json()).usage.ai;
    assert.deepEqual(usage, { limit: 4, used: 4, remaining: 0, period: 'jour' });
    // Le lendemain, le compteur repart à zéro
    env.advance(DAY);
    assert.equal((await call('/v1/assistant', { install: A, body: ask })).status, 200);
    // Audio : 1 analyse par mois en gratuit ; une requête invalide ne consomme rien
    const wav = encodeWav(synth({ bpm: 140, seconds: 8 }), 22050).toString('base64');
    const audio = { machine: ask.machine, style: 'tribe', goal: 'live', level: 'débutant', context: [], audio: { format: 'wav', base64: wav } };
    assert.equal((await call('/v1/audio-analysis', { install: B, body: { ...audio, audio: { format: 'wav', base64: 'AAAA' } } })).status, 415);
    assert.equal((await call('/v1/audio-analysis', { install: B, body: audio })).status, 200);
    assert.equal((await call('/v1/audio-analysis', { install: B, body: audio })).status, 429);
  });
  const failing = setup({ providerFails: true });
  await withServer(failing, async (call) => {
    for (let i = 0; i < 3; i++) assert.equal((await call('/v1/assistant', { install: A, body: ask })).status, 500);
    assert.equal((await (await call('/v1/entitlement', { install: A })).json()).usage.ai.used, 0);
  });
});

test('quotas désactivables par configuration', async () => {
  const env = setup({ config: { enforceQuotas: false } });
  await withServer(env, async (call) => {
    for (let i = 0; i < 5; i++) assert.equal((await call('/v1/assistant', { install: A, body: ask })).status, 200);
  });
});

test('stockage fichier : abonnements et compteurs conservés au redémarrage', () => {
  const dir = mkdtempSync(join(tmpdir(), 'lma-'));
  try {
    const file = join(dir, 'data.json');
    const s1 = createStore({ file });
    s1.putSubscription({ purchaseToken: 'x', installId: A, state: 'SUBSCRIPTION_STATE_ACTIVE' });
    s1.linkInstall(A, 'x');
    s1.addUsage('ai:a', 3);
    const s2 = createStore({ file });
    assert.equal(s2.getSubscription('x').installId, A);
    assert.equal(s2.tokenOfInstall(A), 'x');
    assert.equal(s2.usage('ai:a'), 3);
  } finally {
    rmSync(dir, { recursive: true, force: true });
  }
});
