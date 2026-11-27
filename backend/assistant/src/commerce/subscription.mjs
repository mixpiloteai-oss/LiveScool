// Interprétation d'un abonnement Google Play (API purchases.subscriptionsv2) en droits Premium.

/** États Google Play qui donnent accès à Premium jusqu'à la date d'expiration. */
const PREMIUM_STATES = new Set([
  'SUBSCRIPTION_STATE_ACTIVE',
  'SUBSCRIPTION_STATE_IN_GRACE_PERIOD', // paiement en échec mais encore dans le délai de grâce
  'SUBSCRIPTION_STATE_CANCELED', // annulé : reste actif jusqu'à la fin de la période payée
]);

const LABELS = {
  SUBSCRIPTION_STATE_ACTIVE: 'actif',
  SUBSCRIPTION_STATE_IN_GRACE_PERIOD: 'paiement en attente (période de grâce)',
  SUBSCRIPTION_STATE_CANCELED: 'annulé, actif jusqu\'à l\'échéance',
  SUBSCRIPTION_STATE_ON_HOLD: 'suspendu (paiement refusé)',
  SUBSCRIPTION_STATE_PAUSED: 'en pause',
  SUBSCRIPTION_STATE_EXPIRED: 'expiré',
  SUBSCRIPTION_STATE_PENDING: 'achat en attente',
  SUBSCRIPTION_STATE_PENDING_PURCHASE_CANCELED: 'achat en attente annulé',
};

/** Convertit la réponse Google en enregistrement interne. */
export function toRecord(purchaseToken, installId, google, products) {
  const items = Array.isArray(google.lineItems) ? google.lineItems : [];
  const item = items.find((i) => i.productId === products.subscriptionId) ?? items[0] ?? {};
  const expiryMs = item.expiryTime ? Date.parse(item.expiryTime) : 0;
  return {
    purchaseToken,
    installId,
    productId: item.productId ?? null,
    basePlanId: item.offerDetails?.basePlanId ?? null,
    state: google.subscriptionState ?? 'SUBSCRIPTION_STATE_UNSPECIFIED',
    expiryMs: Number.isFinite(expiryMs) ? expiryMs : 0,
    autoRenew: Boolean(item.autoRenewingPlan?.autoRenewEnabled),
    acknowledged: google.acknowledgementState === 'ACKNOWLEDGEMENT_STATE_ACKNOWLEDGED',
    linkedPurchaseToken: google.linkedPurchaseToken ?? null,
    testPurchase: Boolean(google.testPurchase),
    verifiedAt: Date.now(),
  };
}

/** Premium seulement si l'état le permet ET si la date d'expiration n'est pas passée. */
export function isPremium(record, now = Date.now()) {
  return Boolean(record && record.productId && PREMIUM_STATES.has(record.state) && record.expiryMs > now);
}

export function describe(record, now = Date.now()) {
  if (!record) return null;
  return {
    productId: record.productId,
    basePlanId: record.basePlanId,
    state: LABELS[record.state] ?? 'inconnu',
    active: isPremium(record, now),
    autoRenew: record.autoRenew,
    expiryMs: record.expiryMs,
    test: record.testPurchase,
  };
}
