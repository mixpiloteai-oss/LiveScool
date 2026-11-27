import { readFileSync, renameSync, writeFileSync } from 'node:fs';

/**
 * Stockage du serveur : abonnements vérifiés (par jeton d'achat), lien installation → jeton, compteurs d'usage.
 * Mémoire par défaut ; avec `file`, sauvegarde JSON atomique (suffisant pour un MVP mono-instance ;
 * remplacer par une base de données pour plusieurs instances). Aucun contenu audio ou texte n'y est stocké.
 */
export function createStore({ file = null } = {}) {
  let data = { subscriptions: {}, installs: {}, usage: {} };
  if (file) {
    try {
      data = { ...data, ...JSON.parse(readFileSync(file, 'utf8')) };
    } catch {
      // fichier absent : départ à vide
    }
  }
  const save = () => {
    if (!file) return;
    writeFileSync(`${file}.tmp`, JSON.stringify(data));
    renameSync(`${file}.tmp`, file);
  };
  return {
    getSubscription: (token) => data.subscriptions[token] ?? null,
    putSubscription(record) {
      data.subscriptions[record.purchaseToken] = record;
      save();
    },
    tokenOfInstall: (installId) => data.installs[installId] ?? null,
    linkInstall(installId, token) {
      data.installs[installId] = token;
      save();
    },
    usage: (key) => data.usage[key] ?? 0,
    addUsage(key, delta) {
      data.usage[key] = Math.max(0, (data.usage[key] ?? 0) + delta);
      save();
      return data.usage[key];
    },
  };
}
