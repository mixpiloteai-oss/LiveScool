import { test } from 'node:test';
import assert from 'node:assert/strict';
import { createServer } from 'node:http';
import { createHandler, createRateLimiter } from '../src/app.mjs';
import { LIMITS, SYSTEM_RULES, buildConversation, parseRequest } from '../src/prompt.mjs';
import { ProviderError, createProvider } from '../src/providers/provider.mjs';
import { createAnthropicProvider } from '../src/providers/anthropic.mjs';

const valid = {
  machine: { id: 'circuit-tracks', name: 'Circuit Tracks' },
  lesson: { id: 'ct-m6-l2', title: 'Probabilité et Mutate' },
  level: 'intermédiaire',
  context: [{ title: 'Probabilité et Mutate', text: 'Shift + Pattern Settings ouvre la vue Probability.', ref: 'Guide Circuit Tracks p. 48–49' }],
  history: [{ role: 'user', text: 'Salut' }, { role: 'assistant', text: 'Bonjour !' }],
  message: 'Comment rendre mes charleys moins répétitifs ?',
};

async function withServer(provider, fn, limiter) {
  const server = createServer(createHandler({ provider, rateLimiter: limiter, log: {} }));
  await new Promise((r) => server.listen(0, r));
  const url = `http://127.0.0.1:${server.address().port}`;
  try { return await fn(url); } finally { server.close(); }
}
const post = (url, body) => fetch(`${url}/v1/assistant`, { method: 'POST', headers: { 'content-type': 'application/json' }, body: typeof body === 'string' ? body : JSON.stringify(body) });

test('règles : manuel uniquement, pas d\'invention, suggestions signalées, français', () => {
  assert.match(SYSTEM_RULES, /UNIQUEMENT les extraits « CONTEXTE »/);
  assert.match(SYSTEM_RULES, /N'invente jamais/);
  assert.match(SYSTEM_RULES, /« Suggestion »/);
  assert.match(SYSTEM_RULES, /français/);
});

test('validation et limites', () => {
  assert.equal(parseRequest({ ...valid, message: '' }).code, 'invalid_request');
  assert.equal(parseRequest({ ...valid, message: 'x'.repeat(LIMITS.messageMaxChars + 1) }).code, 'message_too_long');
  assert.equal(parseRequest({ ...valid, machine: undefined }).code, 'invalid_request');
  const many = parseRequest({
    ...valid,
    context: Array.from({ length: 20 }, (_, i) => ({ title: `t${i}`, text: 'y'.repeat(5000), ref: 'p. 1' })),
    history: Array.from({ length: 30 }, (_, i) => ({ role: i % 2 ? 'assistant' : 'user', text: 'z'.repeat(5000) })),
  }).value;
  assert.equal(many.context.length, LIMITS.contextMaxItems);
  assert.ok(many.context.every((c) => c.text.length <= LIMITS.contextItemMaxChars));
  assert.equal(many.history.length, LIMITS.historyMaxMessages);
  assert.ok(many.history.every((h) => h.text.length <= LIMITS.historyItemMaxChars));
});

test('conversation : alternance, contexte et machine dans le dernier message', () => {
  const conv = buildConversation(parseRequest(valid).value);
  assert.deepEqual(conv.messages.map((m) => m.role), ['user', 'assistant', 'user']);
  const last = conv.messages.at(-1).text;
  assert.match(last, /Machine sélectionnée : Circuit Tracks/);
  assert.match(last, /Leçon ouverte : Probabilité et Mutate/);
  assert.match(last, /Guide Circuit Tracks p\. 48–49/);
  assert.match(last, /QUESTION : Comment rendre/);
  const noCtx = buildConversation(parseRequest({ ...valid, context: [], history: [{ role: 'assistant', text: 'x' }] }).value);
  assert.deepEqual(noCtx.messages.map((m) => m.role), ['user']);
  assert.match(noCtx.messages[0].text, /aucun extrait pertinent/);
});

test('HTTP : réponse, erreurs et limite de débit', async () => {
  let seen;
  const provider = { name: 'fake', complete: async (c) => { seen = c; return { text: 'Réponse', truncated: false }; } };
  await withServer(provider, async (url) => {
    const ok = await post(url, valid);
    assert.equal(ok.status, 200);
    assert.deepEqual(await ok.json(), { reply: 'Réponse', truncated: false });
    assert.equal(seen.system, SYSTEM_RULES);
    assert.equal((await post(url, '{pas du json')).status, 400);
    const long = await post(url, { ...valid, message: 'x'.repeat(600) });
    assert.equal(long.status, 400);
    assert.equal((await long.json()).error.code, 'message_too_long');
    assert.equal((await post(url, 'x'.repeat(LIMITS.bodyMaxBytes + 10))).status, 413);
    assert.equal((await fetch(`${url}/v1/assistant`)).status, 405);
    assert.equal((await fetch(`${url}/health`)).status, 200);
  });
  await withServer(provider, async (url) => {
    assert.equal((await post(url, valid)).status, 200);
    const limited = await post(url, valid);
    assert.equal(limited.status, 429);
    assert.equal((await limited.json()).error.code, 'rate_limited');
  }, createRateLimiter({ max: 1 }));
});

test('HTTP : erreurs du fournisseur traduites', async () => {
  const failing = (code, retryable) => ({ name: 'fake', complete: async () => { throw new ProviderError(code, 'détail', retryable); } });
  await withServer(failing('rate_limited', true), async (url) => assert.equal((await post(url, valid)).status, 429));
  await withServer(failing('refused', false), async (url) => assert.equal((await post(url, valid)).status, 422));
  await withServer(failing('provider_unreachable', true), async (url) => {
    const r = await post(url, valid);
    assert.equal(r.status, 502);
    assert.equal((await r.json()).error.code, 'provider_unreachable');
  });
  await withServer({ name: 'fake', complete: async () => { throw new Error('boom'); } }, async (url) => assert.equal((await post(url, valid)).status, 500));
});

test('fournisseur Anthropic : requête et lecture de la réponse (client simulé)', async () => {
  let params;
  const client = { beta: { messages: { create: async (p) => { params = p; return { stop_reason: 'end_turn', content: [{ type: 'thinking', thinking: '' }, { type: 'text', text: 'Bonjour' }] }; } } } };
  const provider = createAnthropicProvider({ client });
  const out = await provider.complete({ system: 'S', messages: [{ role: 'user', text: 'Q' }] });
  assert.deepEqual(out, { text: 'Bonjour', truncated: false });
  assert.equal(params.model, 'claude-opus-5-5');
  assert.deepEqual(params.messages, [{ role: 'user', content: 'Q' }]);
  assert.equal(params.system[0].cache_control.type, 'ephemeral');

  const refusing = createAnthropicProvider({ client: { beta: { messages: { create: async () => ({ stop_reason: 'refusal', content: [] }) } } } });
  await assert.rejects(refusing.complete({ system: 'S', messages: [] }), (e) => e.code === 'refused');
});

test('fournisseur inconnu refusé', async () => {
  await assert.rejects(createProvider('inconnu', {}), /Fournisseur IA inconnu/);
});

test('clé API absente : 503 provider_not_configured et health le signale', async () => {
  const provider = createAnthropicProvider({ apiKey: '' });
  assert.equal(provider.configured, false);
  await withServer(provider, async (url) => {
    const health = await (await fetch(`${url}/health`)).json();
    assert.equal(health.configured, false);
    const res = await post(url, valid);
    assert.equal(res.status, 503);
    assert.equal((await res.json()).error.code, 'provider_not_configured');
  });
});

test('erreur inattendue du SDK : 502 et non une erreur interne', async () => {
  const client = { beta: { messages: { create: async () => { throw new TypeError('boom'); } } } };
  await withServer(createAnthropicProvider({ client }), async (url) => {
    const res = await post(url, valid);
    assert.equal(res.status, 502);
    assert.equal((await res.json()).error.code, 'provider_failure');
  });
});
