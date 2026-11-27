// Offres et limites, configurables côté serveur (variable LMA_PLAN_CONFIG, JSON partiel fusionné avec les défauts).

export const DEFAULT_PLAN_CONFIG = Object.freeze({
  /** Modules ouverts sans abonnement, pour chaque machine (les premiers du parcours). */
  freeModulesPerMachine: 3,
  free: { aiQuestionsPerDay: 5, audioAnalysesPerMonth: 3 },
  premium: { aiQuestionsPerDay: 60, audioAnalysesPerMonth: 40 },
  /** Produit d'abonnement Google Play et ses offres de base (mensuelle, annuelle). */
  products: { subscriptionId: 'lma_premium', monthlyBasePlan: 'mensuel', annualBasePlan: 'annuel' },
  /** Validité maximale d'un jeton de droits avant nouvelle vérification (hors ligne compris). */
  entitlementTtlHours: 72,
  /** false : les quotas ne sont pas appliqués (phase de test). */
  enforceQuotas: true,
});

const isObj = (v) => v && typeof v === 'object' && !Array.isArray(v);

function merge(base, extra) {
  const out = { ...base };
  for (const [k, v] of Object.entries(extra ?? {})) {
    if (!(k in base)) continue; // seules les clés connues sont prises en compte
    out[k] = isObj(base[k]) && isObj(v) ? merge(base[k], v) : v;
  }
  return out;
}

export function loadPlanConfig(env = process.env) {
  let extra = {};
  if (env.LMA_PLAN_CONFIG) {
    try {
      extra = JSON.parse(env.LMA_PLAN_CONFIG);
    } catch {
      throw new Error('LMA_PLAN_CONFIG doit être du JSON.');
    }
  }
  const cfg = merge(DEFAULT_PLAN_CONFIG, extra);
  for (const plan of ['free', 'premium']) {
    for (const [k, v] of Object.entries(cfg[plan])) {
      if (!Number.isInteger(v) || v < 0) throw new Error(`Limite invalide ${plan}.${k}`);
    }
  }
  if (!Number.isInteger(cfg.freeModulesPerMachine) || cfg.freeModulesPerMachine < 1) throw new Error('freeModulesPerMachine invalide');
  return cfg;
}
