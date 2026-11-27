# Backend de l'assistant IA

Petit serveur Node (sans framework) entre l'app Android et le fournisseur IA. **La clé API reste ici** : l'app
ne contient aucun secret, seulement l'adresse publique de ce serveur.

- `POST /v1/assistant` : `{ machine:{id,name}, lesson?:{id,title}, level, context:[{title,text,ref}], history:[{role,text}], message }`
  → `{ reply, truncated }` ou `{ error:{code,message} }` (400, 413, 422, 429, 502, 500).
- `POST /v1/audio-analysis` : `{ machine, style, goal, level, context, audio:{format:'wav', base64} }` → `{ measures, summary,
  advice, adviceError }`. WAV PCM/flottant, 5–75 s, 6 Mo, 6 analyses / 10 min par IP. L'audio est décodé et mesuré
  **en mémoire** (aucun fichier écrit), puis oublié ; l'IA ne reçoit que les mesures. Autre format → 415.
- Offres (avec `LMA_ENTITLEMENT_PRIVATE_KEY`) : `GET /v1/entitlement`, `POST /v1/billing/verify {purchaseToken}`,
  `POST /v1/billing/rtdn?secret=RTDN_SECRET` (Pub/Sub). En-tête `X-LMA-Install` (UUID) obligatoire ; quotas du plan
  vérifié côté serveur (`LMA_PLAN_CONFIG`), 429 `quota_exceeded`. Premium = abonnement Google Play vérifié (API
  subscriptionsv2, compte de service `GOOGLE_SERVICE_ACCOUNT_JSON`, `PLAY_PACKAGE_NAME`), jamais une valeur de l'app.
  `npm run keys` génère la paire de clés (privée : serveur ; publique : `LMA_ENTITLEMENT_PUBLIC_KEY` de l'app).
- `GET /health`.
- Limites : message 500 caractères, 6 messages d'historique, 5 extraits de contexte de 700 caractères, 24 Ko,
  20 questions / 10 min par IP. Aucun contenu de conversation n'est journalisé.
- Fournisseur interchangeable : `src/providers/` (`ASSISTANT_PROVIDER`, défaut `anthropic`). Ajouter un fichier et
  une entrée dans `createProvider` suffit ; ni l'app ni le contrat HTTP ne changent.

```bash
npm ci && npm test
npm run analyze -- morceau.wav      # mesures locales d'un ou plusieurs WAV
ANTHROPIC_API_KEY=... npm start        # PORT=8080 ; ASSISTANT_MODEL, ASSISTANT_EFFORT facultatifs
```
Déployer derrière HTTPS (Cloud Run, Fly.io, Render…), puis compiler l'app avec
`LMA_ASSISTANT_URL=https://…` (variable d'environnement ou propriété Gradle `lmaAssistantUrl`).
