import { LIMITS, buildConversation, parseRequest } from './prompt.mjs';
import { ProviderError } from './providers/provider.mjs';
import { AudioFormatError, decodeWav } from './audio/wav.mjs';
import { AUDIO_LIMITS, analyzeAudio, summarize } from './audio/analyze.mjs';
import { GOALS, STYLES, buildAudioConversation } from './audio/advice.mjs';

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
 * - GET /health.
 * Aucun contenu (texte ou audio) n'est journalisé ni écrit sur disque : l'audio n'existe qu'en mémoire
 * le temps de la requête.
 */
export function createHandler({
  provider,
  rateLimiter = createRateLimiter(),
  audioRateLimiter = createRateLimiter({ max: 6 }),
  log = console,
}) {
  return async (req, res) => {
    const send = (status, payload) => {
      res.writeHead(status, { 'content-type': 'application/json; charset=utf-8', 'cache-control': 'no-store' });
      res.end(JSON.stringify(payload));
    };
    const error = (status, code, message) => send(status, { error: { code, message } });

    if (req.method === 'GET' && req.url === '/health') return send(200, { ok: true, provider: provider.name });
    const route = req.url === '/v1/assistant' ? 'assistant' : req.url === '/v1/audio-analysis' ? 'audio' : null;
    if (!route) return error(404, 'not_found', 'Ressource inconnue.');
    if (req.method !== 'POST') return error(405, 'method_not_allowed', 'Utiliser POST.');

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

    if (route === 'audio') return handleAudio(body, provider, send, error, log);

    const parsed = parseRequest(body);
    if (!parsed.ok) return error(400, parsed.code, parsed.message);
    try {
      const result = await provider.complete(buildConversation(parsed.value));
      return send(200, { reply: result.text, truncated: result.truncated });
    } catch (e) {
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

async function handleAudio(body, provider, send, error, log) {
  const meta = parseAudioMeta(body);
  if (!meta.ok) return error(meta.code === 'unsupported_format' ? 415 : 400, meta.code ?? 'invalid_request', meta.message);
  let audio;
  try {
    audio = decodeWav(Buffer.from(body.audio.base64, 'base64'));
  } catch (e) {
    if (e instanceof AudioFormatError) return error(415, e.code, e.message);
    return error(400, 'invalid_request', 'Audio illisible.');
  } finally {
    body.audio = null; // l'audio encodé n'est plus référencé
  }
  if (audio.durationSec < AUDIO_LIMITS.minSeconds) {
    return error(400, 'audio_too_short', `Extrait trop court (minimum ${AUDIO_LIMITS.minSeconds} s).`);
  }
  if (audio.durationSec > AUDIO_LIMITS.maxSeconds + 1) {
    return error(413, 'audio_too_long', `Extrait trop long (maximum ${AUDIO_LIMITS.maxSeconds} s).`);
  }
  const measures = analyzeAudio(audio);
  audio = null; // les échantillons ne sont plus utilisés
  const summary = summarize(measures);
  if (measures.silent) return send(200, { measures, summary, advice: null, adviceError: 'Extrait silencieux : pas de conseils.' });
  try {
    const result = await provider.complete(buildAudioConversation({ ...meta.value, measures, summary }));
    return send(200, { measures, summary, advice: result.text, adviceError: null });
  } catch (e) {
    // Les mesures restent utiles même si l'IA est indisponible.
    log.warn?.(`audio: conseils indisponibles (${e?.code ?? e?.name})`);
    return send(200, { measures, summary, advice: null, adviceError: 'Conseils IA indisponibles pour le moment ; les mesures ci-dessus restent valables.' });
  }
}
