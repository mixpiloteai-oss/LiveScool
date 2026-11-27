import { createServer } from 'node:http';
import { createHandler } from './app.mjs';
import { createProvider } from './providers/provider.mjs';

// Variables : ANTHROPIC_API_KEY (secret, jamais dans l'app), ASSISTANT_PROVIDER (défaut anthropic),
// ASSISTANT_MODEL, ASSISTANT_EFFORT, PORT. Déployer derrière HTTPS (Cloud Run, Fly, Render…).
const provider = await createProvider(process.env.ASSISTANT_PROVIDER || 'anthropic', process.env);
const port = Number(process.env.PORT || 8080);
createServer(createHandler({ provider })).listen(port, () => {
  console.log(`Assistant Live Machine Academy sur le port ${port} (${provider.name})`);
});
