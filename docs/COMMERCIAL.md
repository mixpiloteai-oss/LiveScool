# Passage en produit commercial — audit et plan

## Audit (v1.4.0)
**Solide, à garder** : contenu séparé de l'UI par machine (`MachineProfile`) ; progression locale par machine, ids immuables (base saine pour une synchro) ; règles pures testées (`ProgressRules`) ; R8 + réduction des ressources en release ; aucune dépendance réseau ; CI tests/lint/APK.

**Bloquant pour Google Play**
1. **Format** : ~~AAB~~ fait — `bundleRelease` dans la CI, AAB en artefact de « Publier l'APK ».
2. **Signature** : clé d'envoi secrète + Play App Signing. La clé `debug.keystore` versionnée ne doit jamais servir à Play.
3. **Marques et contenus** : « Korg », « electribe », « Novation », « Circuit Tracks » sont des marques ; leçons reformulées et pages citées, mais un avis juridique est nécessaire avant de vendre (usage nominatif, mention de non-affiliation déjà présente).
4. **Règle « aucune permission réseau »** : incompatible avec compte, synchro, IA, abonnements. À lever par décision explicite, fonction par fonction.
5. **Politique de confidentialité** + formulaire **Sécurité des données** Play (photo de la machine : stockée en local seulement aujourd'hui).

**À améliorer** : `allowBackup` sans règles explicites (photo et progression sauvegardées par Android) ; pas de couche d'erreurs pour des appels distants ; textes Photo encore propres à l'Electribe (« Où est Write ? »).

## Structure ajoutée (inactive, sans dépendance ni réseau)
| Besoin | Contrat | Implémentation actuelle |
|---|---|---|
| Gratuit / Premium | `domain/commerce/Features.kt` : `Feature`, `Plan`, `Entitlements`, `FeatureGate` | tout le contenu actuel `FREE` ; Premium = `PLANNED` |
| Abonnements | Play Billing + `/v1/billing/verify` + jeton signé | **Actif côté code, paiements OFF** (v1.8.0) |
| Compte facultatif | `data/account/AccountRepository` | `GuestAccountRepository` (invité) |
| Synchro progression | `data/sync/ProgressSync` (par machine) | `LocalOnlyProgressSync` |
| Assistant IA | `MusicAssistant` | **MVP actif** : `HttpAssistantClient` → `backend/assistant` (v1.6.0) |
| Analyse audio | `AudioAnalyzer` | **MVP actif** : `HttpAudioAnalysisClient` → `/v1/audio-analysis` (v1.7.0) |
| Erreurs / réseau | `domain/result/AppResult`, `AppError` (`Offline`, `Unauthorized`, `RequiresPremium`, `NotAvailable`, `Unexpected`) | `appResultOf {}` |

Branchement unique : `di/AppContainer`. Paramètres affiche l'offre (« Gratuite ») et les fonctions prévues. Test : `CommerceTest`.

## Étapes suivantes (une décision à la fois)
1. Clé d'envoi secrète (secrets `LMA_*`) : l'AAB devient publiable.
2. Politique de confidentialité, fiche Play, avis juridique marques.
3. Compte : Credential Manager (Google), seule fonction réseau → première permission `INTERNET`.
4. ~~Abonnements~~ fait en v1.8.0 : reste la création du produit dans la Play Console et les tests avec licences de test.
5. Synchro, assistant en Premium si souhaité, analyse audio (traitement local de préférence, envoi seulement avec accord).
