# Live Machine Academy — règles

## Objectif
App Android native, APK installable, 100 % hors ligne : apprendre une vraie machine leçon par leçon (lire sur le téléphone, pratiquer sur la machine). Styles : hardtek, tribe, tribeCore, acidcore, mentalcore. Pas de cours Ableton ni import SD (déjà maîtrisés).

## Interdits
Simulateur, WebView, site web, secret dans l'app. Réseau : seulement l'assistant IA et l'analyse audio (permissions `INTERNET`, `RECORD_AUDIO` demandée au clic « Enregistrer », `BILLING` et `ACCESS_NETWORK_STATE` de Play Billing) ; tout le reste hors ligne. Toute autre permission fait échouer `release.yml`. Jamais d'enregistrement ni d'envoi audio sans action explicite.

## Machines (`data/content/MachineCatalog.kt`, 20 machines, v2.0.0)
| Machine | id | Préfixe ids | Contenu |
|---|---|---|---|
| Korg Electribe Sampler 2 (rose) | `electribe-sampler` (défaut) | aucun (`m1`, `m1-l1`) | `CourseContent`, `Module1..9Content`, `MachineContent`, `ChallengeContent` |
| Novation Circuit Tracks | `circuit-tracks` | `ct-` | `CircuitTracksContent`, `CircuitTracksModulesA..D`, `CircuitTracksWorkshops`, `CircuitTracksDsl` (`ct()`, `ctLesson()`) |
| Elektron Digitakt II | `elektron-digitakt-ii` | `dt2-` | `DigitaktIIContent`, `DigitaktIIModulesA..B` (`dt()` + `ctLesson()`), profil dans `CatalogMachines.digitaktII` |
| Arturia DrumBrute Impact | `arturia-drumbrute-impact` | `dbi-` | `DrumBruteImpactContent`, `DrumBruteImpactModulesA..B` (`dbi()` + `ctLesson()`), profil `CatalogMachines.drumBruteImpact` |
| Arturia MiniBrute 2S | `arturia-minibrute-2s` | `mb2s-` | `MiniBrute2SContent`, `MiniBrute2SModulesA..B` (`mb2s()` + `ctLesson()`), profil `CatalogMachines.miniBrute2S` |
| Elektron Octatrack MKII | `elektron-octatrack-mkii` | `ot2-` | `OctatrackMkIIContent`, `OctatrackMkIIModulesA..B` (`ot()` + `ctLesson()`), profil `CatalogMachines.octatrackMkII` |
| Moog DFAM | `moog-dfam` | `dfam-` | `DFAMContent`, `DFAMModulesA..B` (`dfam()` + `ctLesson()`), profil `CatalogMachines.dfam` |
| Sequential Take 5 | `sequential-take-5` | `tk5-` | `Take5Content`, `Take5ModulesA..B` (`tk5()` + `ctLesson()`), profil `CatalogMachines.take5` |
| 12 autres (liste `docs/MACHINES.md`) | ex. `roland-tr-8s` | ex. `tr8s-` (unique, jamais préfixe d'un autre) | `CatalogMachines` : fiche seule, « Cours en attente du manuel officiel » |

- `MachineProfile` : `category` (`MachineCategory` : groovebox, sampler, boîte à rythmes, synthé, séquenceur, hybride), `kind`, `styles`, `pedagogy`, `liveUse`, `manuals` (`ManualSpec(fileName, title)` = PDF du modèle exact dans `docs/manual/`), `courseStatusLabel`.
- UI : écran `ui/screens/catalog` (filtres par catégorie, sélection → retour), carte « Ta machine » + « Changer de machine » à l'accueil, fiche avec présentation et manuel attendu. Illustration générée `ui/components/MachineArt.kt` (dégradé + icône de catégorie, aucune image constructeur) ; vraie image = photo de l'utilisateur.
- Progression : un DataStore `progress_<id>` par machine, créé automatiquement pour chaque entrée de `MachineCatalog.all`.
- Ajouter un cours : PDF nommé selon `docs/manual/README.md` → `modules` dans le profil (ids préfixés), sections/commandes citant `ManualRef` (nouveau `ManualDoc` par manuel). Tests : `CatalogTest` (20 machines, fiches complètes, aucun cours sans manuel, README à jour).

## Sources obligatoires (`ManualRef(chapter, pages, doc)`)
- `OWNERS_MANUAL` : manuel electribe/electribe sampler (Korg 2015), FR p. 20–38.
- `PARAMETER_GUIDE` : electribe sampler Parameter Guide (Korg 2016, EN) p. 3–19.
- `CIRCUIT_TRACKS_GUIDE` : Circuit Tracks User Guide V2 (Novation, FR) p. 6–108, sans n° de firmware.
- `DIGITAKT_II_MANUAL` : Digitakt II User Manual OS 1.17 (Elektron, EN) p. 10–118 (pages imprimées = PDF).
- `DRUMBRUTE_IMPACT_MANUAL` : Manuel utilisateur DrumBrute Impact v1.0 (Arturia 2018, FR), pages imprimées 4–108 (PDF = page + 6). Réglages MIDI Control Center non enseignés.
- `MINIBRUTE_2S_MANUAL` : Manuel utilisateur MiniBrute 2S v1.0 (Arturia, FR), pages imprimées 6–170 (PDF = page + 6). Réglages MIDI Control Center non enseignés.
- `OCTATRACK_MKII_MANUAL` : Octatrack MKII User Manual OS 1.40C (Elektron, EN) p. 10–148 (pages imprimées = PDF). Pistes MIDI et éditeur audio non couverts.
- `DFAM_MANUAL` : DFAM Manual (Moog 2017, EN) p. 6–41 (pages imprimées = PDF). Montage Eurorack et réglages des presets (schémas) non recopiés.
- `TAKE5_GUIDE` : Take 5 User's Guide v2.0 (Sequential, mai 2025, EN), pages imprimées 1–76 (PDF = page + 9). Version clavier ; Take 5 Module et SysEx non enseignés.
- PDF dans `docs/manual/` (et `/*.pdf`), ignorés par git, jamais committés. Upload GitHub = copier en local puis purger l'historique (avec accord ; fait le 27/11/2026 pour 17 PDF). Le conteneur est éphémère : PDF absents = les redemander avant de modifier une leçon.
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

## Offres gratuite / Premium (v1.8.0)
- Serveur (`backend/assistant/src/commerce`) : `config.mjs` (limites dans `LMA_PLAN_CONFIG` : modules gratuits/machine 3, IA 5/j vs 60/j, audio 3/mois vs 40/mois, produit `lma_premium` + offres `mensuel`/`annuel`, `enforceQuotas`), `subscription.mjs` (états Google → Premium si actif, grâce ou annulé avant échéance), `googlePlay.mjs` (API subscriptionsv2 + acknowledge, compte de service ; faux client `BILLING_MODE=fake` pour tests), `entitlements.mjs` (quotas par installation, jeton de droits signé ECDSA P-256, TTL ≤ 72 h), `store.mjs` (mémoire ou `LMA_DATA_FILE`). Routes `GET /v1/entitlement`, `POST /v1/billing/verify`, `POST /v1/billing/rtdn?secret=`. En-tête `X-LMA-Install` obligatoire, 429 `quota_exceeded`. `npm run keys`.
- App : `InstallIdRepository` (UUID), `EntitlementVerifier` (clé publique `LMA_ENTITLEMENT_PUBLIC_KEY`), `RemoteEntitlementRepository` (jeton revérifié à chaque lecture), `BillingManager` (Play Billing 7.1.1 : offres, achat, restauration ; n'accorde jamais Premium lui-même), `domain/commerce` (`Feature`, `Entitlements.isPremium` = vérifié, `CommerceRules`, `PremiumTexts`), écran `ui/screens/premium` (offre, usage, comparatif, abonnement, restaurer, gérer sur Google Play). Verrous : modules, leçons, défis/ateliers avancés.
- Paiements réels OFF : `LMA_BILLING_ENABLED`/`lmaBillingEnabled` (défaut false) ; verrous appliqués seulement si ON (`commerceEnforced`). Activer uniquement pour les tests Google Play (licences de test), puis production.
- Prévu : compte, synchro (`data/account`, `data/sync`). Audit : `docs/COMMERCIAL.md`.

## Publication (v1.9.0)
- Fiche Play, icône 512, image 1024×500, formulaire sécurité des données : `docs/play/`. Textes légaux : source `scripts/legal_texts.py` → `docs/legal/*.md` (à héberger) + `LegalTexts.kt` (écrans Confidentialité et Conditions, Paramètres). Champs `[À COMPLÉTER]` : éditeur, contact, hébergeur.
- Sécurité : trafic en clair interdit, jeton de droits exclu des sauvegardes Android (`res/xml`), effacement historique IA + réinitialisation de l'identifiant (écran Confidentialité), plafond global IA/jour (`dailyBudget`, 503 `capacity_reached`), `release.yml` refuse tout secret dans APK/AAB et ne publie une Release GitHub que si `publish=true`.

## Fait (v1.9.0)
- Electribe : 9 modules × 6 leçons `VERIFIED`, 8 défis par style.
- Circuit Tracks : 12 modules × 3 leçons `VERIFIED` (12 q./module), fiche 5 sections, 24 commandes photo, 4 défis, 7 ateliers (pattern, variation, automation, macros, 2 transitions, séquence live ; étapes cochables + validation), 9 termes.
- Leçon : 8 rubriques, étapes cochables, exercice validé à la main, quiz corrigé (80 %), photo ciblée sur la leçon. Quiz final 10 q., révision des erreurs.
- Photo : l'utilisateur photographie SA machine ; aucune image constructeur.
- Tests : `CourseContentTest`, `CircuitTracksContentTest`, `MultiMachineTest`, `ProgressRepositoryTest`, `CommerceTest` (offres, jetons, verrous, droits), `AssistantTest`, `AudioAnalysisTest`, `QuizSessionTest`, `ProgressRulesTest`.
- CI : `android.yml` (tests, lint, APK debug + release) ; `screenshots.yml` (émulateur ; `variant`, `upgrade_from`, `script_mode=smoke` ; commite `docs/screenshots/`) ; `release.yml` (vérifie, refuse INTERNET, publie les APK ; AAB Play en artefact, alerte si signé avec la clé partagée). Release publiée : v1.4.0. v1.9.0 : APK + AAB en artefacts (run 45958441944), non publiés.
- Signature : `app/debug.keystore` partagée non secrète ; `LMA_KEYSTORE_*` prioritaire. APK ≤ 1.1.2 : désinstallation unique.

## Déploiement backend
- Cloud Run `livescool` (europe-west1), URL `https://livescool-234303726601.europe-west1.run.app`, Buildpacks, contexte `/backend/assistant`, branche `^ccr-da6a4977-k0rjt6$` : chaque push redéploie. Secret `ANTHROPIC_API_KEY` (rôle « Accesseur de secrets » accordé à `234303726601-compute@developer.gserviceaccount.com`), `PLAY_PACKAGE_NAME`. `/health` → `configured` ; clé absente = 503 `provider_not_configured`.
- Assistant validé sur téléphone. Variable de dépôt `LMA_ASSISTANT_URL` définie ; APK de test : run « Publier l'APK » 45993696327.

## Priorité actuelle : 20 machines
- Google Play EN PAUSE : ni Play Console, ni Billing, ni publication. Code abonnements/compte/paiements conservé tel quel. Reprise Premium : clés serveur via Cloud Shell (rédigé) ; `release.yml` transmet `LMA_ENTITLEMENT_PUBLIC_KEY`/`LMA_BILLING_ENABLED`.
- Fait : v2.0.0 catalogue des 20 machines ; v2.1.0 cours Digitakt II (9 modules × 3 leçons `VERIFIED`, 4 q./leçon, fiche 4 sections ; pas encore de commandes photo, défis ni ateliers). Tests `DigitaktIIContentTest`.
- v2.2.0 cours DrumBrute Impact (7 modules × 3 leçons `VERIFIED`, 4 q./leçon, fiche 4 sections). Tests `DrumBruteImpactContentTest`.
- v2.3.0 cours MiniBrute 2S (7 modules × 3 leçons `VERIFIED`, 4 q./leçon, fiche 4 sections). Tests `MiniBrute2SContentTest`.
- v2.4.0 cours Octatrack MKII (7 modules × 3 leçons `VERIFIED`, 4 q./leçon, fiche 4 sections). Tests `OctatrackMkIIContentTest`.
- v2.5.0 cours Moog DFAM (6 modules × 3 leçons `VERIFIED`, 4 q./leçon, fiche 4 sections). Tests `DFAMContentTest`.
- v2.6.0 cours Sequential Take 5 (7 modules × 3 leçons `VERIFIED`, 4 q./leçon, fiche 4 sections). Tests `Take5ContentTest`.
- Manuels reçus, cours à rédiger : Deluge (OS 4.1), SP-404MKII (PDF de référence sans texte extractible : à lire en images ou fournir une version texte). Détail `docs/manual/README.md`.
- Manuels manquants (10) : MPC One+, TR-8S, TD-3 (reçu RD-6 par erreur), Hydrasynth Desktop, 0-Coast, SEQTRAK, Tracker+, OP-1 field, Pyramid MK3, T-1.

## Prochaine tâche
Cours suivant parmi les manuels reçus : Deluge, puis SP-404MKII ; même format que la Digitakt II. Puis commandes photo / défis Digitakt II, retours Circuit Tracks, Electribe 7.1/7.2, texte Photo (« Write »).

## Règles de travail
- Aucune fonction inventée : toute info machine cite sa `ManualRef` ; `VERIFIED` seulement si vérifié.
- Compilation locale impossible : pousser et vérifier GitHub Actions.
- Branche unique `ccr-da6a4977-k0rjt6`, pas de PR.
- Incrémenter `versionCode`/`versionName` à chaque version.
- Français, concis ; mettre ce fichier à jour en fin de tâche.
