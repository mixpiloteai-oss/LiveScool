# Backend de l'assistant IA

Petit serveur Node (sans framework) entre l'app Android et le fournisseur IA. **La clé API reste ici** : l'app
ne contient aucun secret, seulement l'adresse publique de ce serveur.

- `POST /v1/assistant` : `{ machine:{id,name}, lesson?:{id,title}, level, context:[{title,text,ref}], history:[{role,text}], message }`
  → `{ reply, truncated }` ou `{ error:{code,message} }` (400, 413, 422, 429, 502, 500).
- `GET /health`.
- Limites : message 500 caractères, 6 messages d'historique, 5 extraits de contexte de 700 caractères, 24 Ko,
  20 questions / 10 min par IP. Aucun contenu de conversation n'est journalisé.
- Fournisseur interchangeable : `src/providers/` (`ASSISTANT_PROVIDER`, défaut `anthropic`). Ajouter un fichier et
  une entrée dans `createProvider` suffit ; ni l'app ni le contrat HTTP ne changent.

```bash
npm ci && npm test
ANTHROPIC_API_KEY=... npm start        # PORT=8080 ; ASSISTANT_MODEL, ASSISTANT_EFFORT facultatifs
```
Déployer derrière HTTPS (Cloud Run, Fly.io, Render…), puis compiler l'app avec
`LMA_ASSISTANT_URL=https://…` (variable d'environnement ou propriété Gradle `lmaAssistantUrl`).
