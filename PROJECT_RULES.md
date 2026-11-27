# Live Machine Academy — règles

## Objectif
App Android native, APK installable, 100 % hors ligne : apprendre une vraie machine leçon par leçon (lire sur le téléphone, pratiquer sur la machine). Styles : hardtek, tribe, tribeCore, acidcore, mentalcore. Pas de cours Ableton ni import SD (déjà maîtrisés).

## Interdits
Simulateur, WebView, site web, secret dans l'app. Réseau : seulement l'assistant IA et l'analyse audio (permissions `INTERNET`, `RECORD_AUDIO` demandée au clic « Enregistrer ») ; tout le reste hors ligne. Toute autre permission fait échouer `release.yml`. Jamais d'enregistrement ni d'envoi audio sans action explicite.

## Machines (`data/content/MachineCatalog.kt`)
| Machine | id | Préfixe ids | Contenu |
|---|---|---|---|
| Korg Electribe Sampler 2 (rose) | `electribe-sampler` (défaut) | aucun (`m1`, `m1-l1`) | `CourseContent`, `Module1..9Content`, `MachineContent`, `ChallengeContent` |
| Novation Circuit Tracks | `circuit-tracks` | `ct-` | `CircuitTracksContent`, `CircuitTracksModulesA..D`, `CircuitTracksWorkshops`, `CircuitTracksDsl` (`ct()`, `ctLesson()`) |

Chaque `MachineProfile` a son parcours indépendant (modules, leçons, fiche, commandes photo, défis, ateliers, glossaire). Machine choisie à l'accueil (`selectedMachineId`), écrans filtrés par machine.

## Sources obligatoires (`ManualRef(chapter, pages, doc)`)
- `OWNERS_MANUAL` : manuel electribe/electribe sampler (Korg 2015), FR p. 20–38.
- `PARAMETER_GUIDE` : electribe sampler Parameter Guide (Korg 2016, EN) p. 3–19.
- `CIRCUIT_TRACKS_GUIDE` : Circuit Tracks User Guide V2 (Novation, FR) p. 6–108, sans n° de firmware.
- PDF dans `docs/manual/`, ignorés par git, jamais committés (upload GitHub = retirer et purger l'historique). Absents : les redemander avant de modifier une leçon.
- Signalés dans les leçons : sauvegarde Global (manuel p. 32 / guide p. 7), AUTO POWER OFF « Off »/« Disable », Electribe 7.1/7.2 non documentés, Circuit Tracks samples inversés (p. 22/25) sans manipulation.

## Stack
Kotlin 2.1.20, Compose + Material 3 (sombre, magenta), MVVM + StateFlow, Navigation type-safe, DataStore, DI manuelle (`di/AppContainer`). Package `com.livemachine.academy`, minSdk 26, compileSdk 35, AGP 8.9.1, Gradle 8.14.3.

## Progression séparée par machine
- Un fichier DataStore par machine : `progress` (Electribe, historique), `progress_<id>`. `ProgressRepository` route chaque id (`MachineCatalog.machineOfProgressId`) ; `migrateLegacy()` ; `reset(machineId)`.
- Ids immuables : leçon `<m>-l<n>`, exercice `-ex`, question `-q<n>`, étape `<id>#<i>`, quiz final `final-<module>`.
- Module N ouvert si N-1 validé (leçons ou quiz final ≥ 80 %) ; mode libre dans Paramètres. Règles : `domain/ProgressRules.kt`.

## Assistant IA (MVP v1.6.0)
- App : `ui/screens/assistant` (chat, historique local par machine `assistant_history`, chargement, erreurs, 500 car.), `domain/assistant/AssistantContextBuilder` (leçon ouverte + 3 leçons proches par mots-clés, extraits ≤ 650 car. avec `ManualRef`, 6 messages d'historique, niveau), `data/assistant/HttpAssistantClient` (HttpURLConnection, `AppError`). Entrées : accueil, bouton de chaque leçon.
- Backend : `backend/assistant` (Node, SDK Anthropic, `claude-opus-5-5`, effort medium, repli serveur), contrat `POST /v1/assistant`, règles « contexte uniquement, citer, ne rien inventer, idées = Suggestion », limites + 20 req./10 min/IP. Fournisseur interchangeable (`src/providers`). Tests `node --test`, CI `backend.yml`.
- URL du backend : `LMA_ASSISTANT_URL` (variable de dépôt GitHub / propriété `lmaAssistantUrl`) ; vide = « Assistant non configuré ». Clé API seulement sur le serveur.
- Gratuit pendant le MVP (`Feature.AI_ASSISTANT`, `Plan.FREE`).

## Analyse audio IA (MVP v1.7.0)
- App : `ui/screens/audio` (importer via sélecteur système, enregistrer au micro ≤ 60 s, écouter, choisir style/objectif, voir les données envoyées, case de consentement, envoyer) ; `data/audio` (`AudioDecoder` MediaCodec → mono 22 050 Hz, `AudioRecorder`, `WavCodec`, `TempAudioStore` cache supprimé après analyse/abandon/démarrage, `HttpAudioAnalysisClient`) ; règles pures `domain/audio/AudioClipRules` (5–60 s).
- Backend `POST /v1/audio-analysis` (WAV base64 ≤ 6 Mo, 5–75 s, 6 req./10 min/IP) : `src/audio/wav.mjs` (décodage en mémoire, aucun fichier), `analyze.mjs` (niveaux, bandes, tempo par autocorrélation + affinage, stabilité, kick, graves entre kicks, énergie, breaks, sections ; liste « non mesuré »), `advice.mjs` (l'IA reçoit seulement les mesures : Mesures / Ce que ça suggère / Conseils / Non mesuré). Mesures renvoyées même si l'IA échoue. `npm run analyze -- f.wav`.
- Gratuit pendant le MVP (`Feature.AUDIO_ANALYSIS`). Pas d'écoute en temps réel.

## Commercial (structure inactive)
`domain/commerce` (`Feature`, `Plan`, `FeatureGate`), `domain/result` (`AppResult`), `data/commerce|account|sync|services` : implémentations locales (gratuit, invité, pas de synchro, IA/audio indisponibles), branchées dans `AppContainer`. Contenu actuel 100 % gratuit ; Premium prévu : synchro. Pas de chatbot ni paiement. Audit et étapes : `docs/COMMERCIAL.md`.

## Fait (v1.7.0)
- Electribe : 9 modules × 6 leçons `VERIFIED`, 8 défis par style.
- Circuit Tracks : 12 modules × 3 leçons `VERIFIED` (12 q./module), fiche 5 sections, 24 commandes photo, 4 défis, 7 ateliers (pattern, variation, automation, macros, 2 transitions, séquence live ; étapes cochables + validation), 9 termes.
- Leçon : 8 rubriques, étapes cochables, exercice validé à la main, quiz corrigé (80 %), photo ciblée sur la leçon. Quiz final 10 q., révision des erreurs.
- Photo : l'utilisateur photographie SA machine ; aucune image constructeur.
- Tests : `CourseContentTest`, `CircuitTracksContentTest`, `MultiMachineTest`, `ProgressRepositoryTest`, `CommerceTest`, `AssistantTest`, `AudioAnalysisTest`, `QuizSessionTest`, `ProgressRulesTest`.
- CI : `android.yml` (tests, lint, APK debug + release) ; `screenshots.yml` (émulateur ; `variant`, `upgrade_from`, `script_mode=smoke` ; commite `docs/screenshots/`) ; `release.yml` (vérifie, refuse INTERNET, publie les APK ; AAB Play en artefact, alerte si signé avec la clé partagée). Release publiée : v1.4.0.
- Signature : `app/debug.keystore` partagée non secrète ; `LMA_KEYSTORE_*` prioritaire. APK ≤ 1.1.2 : désinstallation unique.

## Prochaine tâche
Déployer le backend (HTTPS) et définir `LMA_ASSISTANT_URL`, puis décisions Play (`docs/COMMERCIAL.md`) : clé d'envoi secrète (secrets `LMA_*`), confidentialité, avis marques. Puis retours Circuit Tracks, Electribe 7.1/7.2, texte Photo (« Write »).

## Règles de travail
- Aucune fonction inventée : toute info machine cite sa `ManualRef` ; `VERIFIED` seulement si vérifié.
- Compilation locale impossible : pousser et vérifier GitHub Actions.
- Branche unique `ccr-da6a4977-k0rjt6`, pas de PR.
- Incrémenter `versionCode`/`versionName` à chaque version.
- Français, concis ; mettre ce fichier à jour en fin de tâche.
