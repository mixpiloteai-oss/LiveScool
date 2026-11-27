import { LIMITS, buildConversation, parseRequest } from './prompt.mjs';
import { ProviderError } from './providers/provider.mjs';

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

/** Gestionnaire HTTP (node:http) : POST /v1/assistant, GET /health. Aucun contenu de conversation n'est journalisé. */
export function createHandler({ provider, rateLimiter = createRateLimiter(), log = console }) {
  return async (req, res) => {
    const send = (status, payload) => {
      res.writeHead(status, { 'content-type': 'application/json; charset=utf-8', 'cache-control': 'no-store' });
      res.end(JSON.stringify(payload));
    };
    const error = (status, code, message) => send(status, { error: { code, message } });

    if (req.method === 'GET' && req.url === '/health') return send(200, { ok: true, provider: provider.name });
    if (req.url !== '/v1/assistant') return error(404, 'not_found', 'Ressource inconnue.');
    if (req.method !== 'POST') return error(405, 'method_not_allowed', 'Utiliser POST.');

    const ip = String(req.headers['x-forwarded-for'] ?? req.socket.remoteAddress ?? '').split(',')[0].trim();
    if (!rateLimiter(ip)) return error(429, 'rate_limited', 'Trop de questions : réessaie dans quelques minutes.');

    let raw = '';
    let size = 0;
    try {
      for await (const chunk of req) {
        size += chunk.length;
        if (size > LIMITS.bodyMaxBytes) return error(413, 'payload_too_large', 'Requête trop volumineuse.');
        raw += chunk;
      }
    } catch {
      return error(400, 'invalid_request', 'Lecture de la requête impossible.');
    }
    let body;
    try { body = JSON.parse(raw); } catch { return error(400, 'invalid_request', 'JSON invalide.'); }

    const parsed = parseRequest(body);
    if (!parsed.ok) return error(400, parsed.code, parsed.message);

    try {
      const result = await provider.complete(buildConversation(parsed.value));
      return send(200, { reply: result.text, truncated: result.truncated });
    } catch (e) {
      if (e instanceof ProviderError) {
        log.warn?.(`assistant: ${e.code}`);
        const status = e.code === 'rate_limited' ? 429 : e.code === 'refused' ? 422 : 502;
        return error(status, e.code, e.retryable ? 'Assistant momentanément indisponible, réessaie.' : e.message);
      }
      log.error?.('assistant: erreur inattendue', e?.name);
      return error(500, 'internal', 'Erreur interne.');
    }
  };
}
