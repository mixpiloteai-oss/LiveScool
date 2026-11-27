import Anthropic from '@anthropic-ai/sdk';
import { ProviderError } from './provider.mjs';

/** Fournisseur Claude (Anthropic). Clé lue côté serveur uniquement (ANTHROPIC_API_KEY). */
export function createAnthropicProvider({ model = 'claude-opus-5-5', effort = 'medium', client = new Anthropic() } = {}) {
  return {
    name: 'anthropic',
    async complete({ system, messages }) {
      let response;
      try {
        response = await client.beta.messages.create({
          model,
          max_tokens: 8000,
          // Effort explicite (défaut du modèle : medium) ; réflexion adaptative active par défaut sur ce modèle.
          output_config: { effort },
          // Règles fixes en premier, marquées pour le cache de prompt.
          system: [{ type: 'text', text: system, cache_control: { type: 'ephemeral' } }],
          messages: messages.map((m) => ({ role: m.role, content: m.text })),
          // Si le modèle décline, l'API rejoue la requête sur un modèle de repli adapté.
          betas: ['server-side-fallback-2026-07-01'],
          fallbacks: 'default',
        });
      } catch (error) {
        if (error instanceof Anthropic.RateLimitError) throw new ProviderError('rate_limited', 'Fournisseur IA saturé.', true);
        if (error instanceof Anthropic.AuthenticationError) throw new ProviderError('provider_auth', 'Clé du fournisseur invalide.', false);
        if (error instanceof Anthropic.BadRequestError) throw new ProviderError('provider_rejected', 'Requête refusée par le fournisseur.', false);
        if (error instanceof Anthropic.APIConnectionError) throw new ProviderError('provider_unreachable', 'Fournisseur IA injoignable.', true);
        if (error instanceof Anthropic.APIError) throw new ProviderError('provider_error', `Erreur du fournisseur (${error.status}).`, true);
        throw error;
      }
      if (response.stop_reason === 'refusal') throw new ProviderError('refused', 'Question refusée par le modèle.', false);
      const text = response.content.filter((b) => b.type === 'text').map((b) => b.text).join('').trim();
      if (!text) throw new ProviderError('empty_reply', 'Réponse vide.', true);
      return { text, truncated: response.stop_reason === 'max_tokens' };
    },
  };
}
