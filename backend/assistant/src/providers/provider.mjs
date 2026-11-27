// Contrat commun des fournisseurs IA. Pour en changer : ajouter un fichier ici et une entrée dans
// createProvider ; l'application Android et le contrat HTTP ne changent pas.
//
// provider.complete({ system: string, messages: [{ role: 'user'|'assistant', text }] })
//   -> Promise<{ text: string, truncated: boolean }>   ou rejette une ProviderError.

export class ProviderError extends Error {
  constructor(code, message, retryable) {
    super(message);
    this.code = code;
    this.retryable = retryable;
  }
}

export async function createProvider(name, env) {
  switch (name) {
    case 'anthropic': {
      const { createAnthropicProvider } = await import('./anthropic.mjs');
      return createAnthropicProvider({ model: env.ASSISTANT_MODEL || undefined, effort: env.ASSISTANT_EFFORT || undefined });
    }
    default:
      throw new Error(`Fournisseur IA inconnu : ${name}`);
  }
}
