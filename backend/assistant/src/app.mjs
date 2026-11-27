import { LIMITS, buildConversation, parseRequest } from './prompt.mjs';
import { ProviderError } from './providers/provider.mjs';
import { AudioFormatError, decodeWav } from './audio/wav.mjs';
import { AUDIO_LIMITS, analyzeAudio, summarize } from './audio/analyze.mjs';
import { GOALS, STYLES, buildAudioConversation } from './audio/advice.mjs';
import { INSTALL_ID } from './commerce/entitlements.mjs';

export const AUDIO_BODY_MAX_BYTES = 6 * 1024 * 1024;

/** Limite simple par adresse IP (mémoire du processus). À remplacer par un compte/jeton quand il existera. */
export function createRateLimiter({ max = 20, windowMs = 10 * 60 * 1000, now = () => Date.now() } = {}) {
  const hits = new Map();
  return (key) => {
    const t = now();
    const recent = (hits.get(key) ?? []).filter((x) => t - x < windowMs);
    if (recent.length >= max) { hits.set(key, recent); return false; }
    recent.push(t);
    hits.set(key, recent);
    return true;
  };
}

/**
 * Gestionnaire HTTP (node:http) :
 * - POST /v1/assistant : question à l'assistant ;
 * - POST /v1/audio-analysis : mesures d'un extrait WAV + conseils IA ;
 * - GET /v1/entitlement : droits signés, limites et usage de l'installation ;
 * - POST /v1/billing/verify : vérification d'un achat Google Play ;
 * - POST /v1/billing/rtdn?secret=… : notifications Google Play (Pub/Sub push) ;
 * - GET /health.
 * Avec `commerce`, chaque appel IA exige l'en-tête X-LMA-Install et consomme le quota du plan vérifié côté serveur.
 * Aucun contenu (texte ou audio) n'est journalisé ni écrit sur disque : l'audio n'existe qu'en mémoire
 * le temps de la requête.
 */
export function createHandler({
  provider,
  rateLimiter = createRateLimiter(),
  audioRateLimiter = createRateLimiter({ max: 6 }),
  commerce = null,
  rtdnSecret = null,
  log = console,
}) {
  return async (req, res) => {
    const send = (status, payload) => {
      res.writeHead(status, { 'content-type': 'application/json; charset=utf-8', 'cache-control': 'no-store' });
      res.end(JSON.stringify(payload));
    };
    const error = (status, code, message) => send(status, { error: { code, message } });

    const url = new URL(req.url, 'http://local');
    const path = url.pathname;
    const installId = String(req.headers['x-lma-install'] ?? '').toLowerCase();
    const validInstall = INSTALL_ID.test(installId);

    if (req.method === 'GET' && path === '/health') return send(200, { ok: true, provider: provider.name });
    if (req.method === 'GET' && path === '/v1/entitlement') {
      if (!commerce) return error(503, 'commerce_disabled', 'Offres non configurées sur ce serveur.');
      if (!validInstall) return error(400, 'install_required', 'Identifiant d\'installation manquant.');
      return send(200, commerce.entitlement(installId));
    }
    const routes = { '/v1/assistant': 'assistant', '/v1/audio-analysis': 'audio', '/v1/billing/verify': 'verify', '/v1/billing/rtdn': 'rtdn' };
    const route = routes[path] ?? null;
    if (!route) return error(404, 'not_found', 'Ressource inconnue.');
    if (req.method !== 'POST') return error(405, 'method_not_allowed', 'Utiliser POST.');
    if ((route === 'verify' || route === 'rtdn') && !commerce) return error(503, 'commerce_disabled', 'Offres non configurées sur ce serveur.');
    if (route === 'rtdn' && (!rtdnSecret || url.searchParams.get('secret') !== rtdnSecret)) return error(403, 'forbidden', 'Accès refusé.');
    if (commerce && route !== 'rtdn' && !validInstall) return error(400, 'install_required', 'Identifiant d\'installation manquant.');

    const ip = String(req.headers['x-forwarded-for'] ?? req.socket.remoteAddress ?? '').split(',')[0].trim();
    const limiter = route === 'audio' ? audioRateLimiter : rateLimiter;
    if (!limiter(ip)) return error(429, 'rate_limited', 'Trop de demandes : réessaie dans quelques minutes.');

    const maxBytes = route === 'audio' ? AUDIO_BODY_MAX_BYTES : LIMITS.bodyMaxBytes;
    const chunks = [];
    let size = 0;
    try {
      for await (const chunk of req) {
        size += chunk.length;
        if (size > maxBytes) return error(413, 'payload_too_large', 'Requête trop volumineuse.');
        chunks.push(chunk);
      }
    } catch {
      return error(400, 'invalid_request', 'Lecture de la requête impossible.');
    }
    let body;
    try { body = JSON.parse(Buffer.concat(chunks).toString('utf8')); } catch { return error(400, 'invalid_request', 'JSON invalide.'); }
    chunks.length = 0;

    if (route === 'rtdn') {
      // Pub/Sub : { message: { data: base64(JSON { subscriptionNotification: { purchaseToken } }) } }
      try {
        const data = JSON.parse(Buffer.from(body?.message?.data ?? '', 'base64').toString('utf8') || '{}');
        const token = data?.subscriptionNotification?.purchaseToken;
        if (token) await commerce.onNotification(token);
      } catch (e) {
        log.warn?.(`rtdn: ${e.message}`);
      }
      return send(200, { ok: true }); // toujours 200 pour que Pub/Sub ne réessaie pas indéfiniment
    }
    if (route === 'verify') {
      const purchaseToken = typeof body?.purchaseToken === 'string' ? body.purchaseToken.slice(0, 4096) : '';
      if (!purchaseToken) return error(400, 'invalid_request', 'Jeton d\'achat manquant.');
      try {
        const result = await commerce.verifyPurchase(installId, purchaseToken);
        if (!result.ok) return error(422, result.code, result.message);
        return send(200, result.entitlement);
      } catch (e) {
        log.warn?.(`billing: vérification impossible (${e.message})`);
        return error(502, 'play_unavailable', 'Vérification Google Play momentanément impossible, réessaie.');
      }
    }

    // Quota du plan vérifié côté serveur (jamais d'après ce que dit l'app)
    let quota = null;
    if (commerce) {
      quota = commerce.consume(installId, route === 'audio' ? 'audio' : 'ai');
      if (!quota.ok) {
        const what = route === 'audio' ? 'analyses audio' : 'questions à l\'assistant';
        return send(429, {
          error: {
            code: 'quota_exceeded',
            message: `Limite atteinte : ${quota.limit} ${what} par ${quota.period} avec l'offre ${quota.plan === 'premium' ? 'Premium' : 'gratuite'}.`,
          },
          quota: { plan: quota.plan, limit: quota.limit, used: quota.used, period: quota.period },
        });
      }
    }

    if (route === 'audio') {
      const ok = await handleAudio(body, provider, send, error, log, quota?.plan ?? 'free');
      if (!ok) quota?.refund?.();
      return;
    }

    const parsed = parseRequest(body);
    if (!parsed.ok) {
      quota?.refund?.();
      return error(400, parsed.code, parsed.message);
    }
    try {
      const result = await provider.complete(buildConversation(parsed.value));
      return send(200, { reply: result.text, truncated: result.truncated });
    } catch (e) {
      quota?.refund?.(); // une erreur ne consomme pas de quota
      return providerFailure(e, error, log);
    }
  };
}

function providerFailure(e, error, log) {
  if (e instanceof ProviderError) {
    log.warn?.(`assistant: ${e.code}`);
    const status = e.code === 'rate_limited' ? 429 : e.code === 'refused' ? 422 : 502;
    return error(status, e.code, e.retryable ? 'Assistant momentanément indisponible, réessaie.' : e.message);
  }
  log.error?.('assistant: erreur inattendue', e?.name);
  return error(500, 'internal', 'Erreur interne.');
}

/** Valide la requête d'analyse audio (sans l'audio). */
export function parseAudioMeta(body) {
  if (!body || typeof body !== 'object') return { ok: false, message: 'Requête illisible.' };
  const machine = body.machine ?? {};
  if (typeof machine.id !== 'string' || typeof machine.name !== 'string') return { ok: false, message: 'Machine manquante.' };
  if (body.audio?.format !== 'wav' || typeof body.audio?.base64 !== 'string') {
    return { ok: false, code: 'unsupported_format', message: 'Audio manquant ou format non pris en charge (WAV attendu).' };
  }
  const style = STYLES.includes(body.style) ? body.style : 'autre';
  const goal = GOALS.includes(body.goal) ? body.goal : 'création';
  const level = ['débutant', 'intermédiaire', 'avancé'].includes(body.level) ? body.level : 'débutant';
  const context = (Array.isArray(body.context) ? body.context : [])
    .filter((c) => c && typeof c.text === 'string')
    .slice(0, 4)
    .map((c) => ({ title: String(c.title ?? '').slice(0, 120), text: c.text.slice(0, 700), ref: String(c.ref ?? '').slice(0, 160) }));
  return {
    ok: true,
    value: { machine: { id: machine.id.slice(0, 60), name: machine.name.slice(0, 80) }, style, goal, level, context },
  };
}

/** Retourne true si des mesures ont été produites (le quota est alors consommé). */
async function handleAudio(body, provider, send, error, log, plan) {
  const meta = parseAudioMeta(body);
  if (!meta.ok) {
    error(meta.code === 'unsupported_format' ? 415 : 400, meta.code ?? 'invalid_request', meta.message);
    return false;
  }
  let audio;
  try {
    audio = decodeWav(Buffer.from(body.audio.base64, 'base64'));
  } catch (e) {
    if (e instanceof AudioFormatError) error(415, e.code, e.message);
    else error(400, 'invalid_request', 'Audio illisible.');
    return false;
  } finally {
    body.audio = null; // l'audio encodé n'est plus référencé
  }
  if (audio.durationSec < AUDIO_LIMITS.minSeconds) {
    error(400, 'audio_too_short', `Extrait trop court (minimum ${AUDIO_LIMITS.minSeconds} s).`);
    return false;
  }
  if (audio.durationSec > AUDIO_LIMITS.maxSeconds + 1) {
    error(413, 'audio_too_long', `Extrait trop long (maximum ${AUDIO_LIMITS.maxSeconds} s).`);
    return false;
  }
  const measures = analyzeAudio(audio);
  audio = null; // les échantillons ne sont plus utilisés
  const summary = summarize(measures);
  if (measures.silent) {
    send(200, { measures, summary, advice: null, adviceError: 'Extrait silencieux : pas de conseils.' });
    return false;
  }
  try {
    const result = await provider.complete(buildAudioConversation({ ...meta.value, measures, summary, plan }));
    send(200, { measures, summary, advice: result.text, adviceError: null, plan });
    return true;
  } catch (e) {
    // Les mesures restent utiles même si l'IA est indisponible.
    log.warn?.(`audio: conseils indisponibles (${e?.code ?? e?.name})`);
    send(200, { measures, summary, advice: null, adviceError: 'Conseils IA indisponibles pour le moment ; les mesures ci-dessus restent valables.' });
    return true;
  }
}
