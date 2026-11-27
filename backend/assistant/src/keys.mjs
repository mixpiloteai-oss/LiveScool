// Génère la paire de clés des jetons de droits : la privée reste sur le serveur, la publique va dans l'app.
import { generateSigningKey, publicKeyBase64 } from './commerce/entitlements.mjs';

const key = generateSigningKey();
console.log('LMA_ENTITLEMENT_PRIVATE_KEY (secret serveur) :\n' + key.export({ type: 'pkcs8', format: 'pem' }));
console.log('LMA_ENTITLEMENT_PUBLIC_KEY (app, non secrète) :\n' + publicKeyBase64(key));
