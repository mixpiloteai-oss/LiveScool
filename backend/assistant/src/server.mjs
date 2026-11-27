import { createServer } from 'node:http';
import { createDailyBudget, createHandler } from './app.mjs';
import { createProvider } from './providers/provider.mjs';
import { loadPlanConfig } from './commerce/config.mjs';
import { createStore } from './commerce/store.mjs';
import { createCommerce, loadSigningKey } from './commerce/entitlements.mjs';
import { createFakePlayClient, createGooglePlayClient } from './commerce/googlePlay.mjs';

// Variables (toutes côté serveur, jamais dans l'app) :
//   ANTHROPIC_API_KEY, ASSISTANT_PROVIDER, ASSISTANT_MODEL, ASSISTANT_EFFORT, PORT
//   LMA_ENTITLEMENT_PRIVATE_KEY (PEM EC P-256) : active offres et quotas ; la clé publique va dans l'app
//   LMA_PLAN_CONFIG (JSON partiel) : limites gratuites/Premium, modules gratuits, produits
//   LMA_DATA_FILE : fichier JSON des abonnements et compteurs (sinon mémoire)
//   BILLING_MODE = google | fake (fake : recette interne uniquement, jamais en production)
//   PLAY_PACKAGE_NAME, GOOGLE_SERVICE_ACCOUNT_JSON (compte de service Play Console), RTDN_SECRET
const env = process.env;
const provider = await createProvider(env.ASSISTANT_PROVIDER || 'anthropic', env);

const planConfig = loadPlanConfig(env);
let commerce = null;
if (env.LMA_ENTITLEMENT_PRIVATE_KEY) {
  let play;
  if (env.BILLING_MODE === 'fake') {
    console.warn('BILLING_MODE=fake : achats simulés, NE PAS utiliser en production.');
    play = createFakePlayClient();
  } else if (env.GOOGLE_SERVICE_ACCOUNT_JSON && env.PLAY_PACKAGE_NAME) {
    play = createGooglePlayClient({ serviceAccount: JSON.parse(env.GOOGLE_SERVICE_ACCOUNT_JSON), packageName: env.PLAY_PACKAGE_NAME });
  } else {
    play = { name: 'none', getSubscription: async () => { throw new Error('Google Play non configuré'); }, acknowledge: async () => {} };
  }
  commerce = createCommerce({
    store: createStore({ file: env.LMA_DATA_FILE || null }),
    config: planConfig,
    play,
    signingKey: loadSigningKey(env.LMA_ENTITLEMENT_PRIVATE_KEY),
  });
}

const port = Number(env.PORT || 8080);
createServer(createHandler({ provider, commerce, rtdnSecret: env.RTDN_SECRET || null, dailyBudget: createDailyBudget(planConfig.dailyBudget) })).listen(port, () => {
  console.log(`Live Machine Academy : port ${port}, IA ${provider.name}, offres ${commerce ? 'actives' : 'désactivées'}`);
});
