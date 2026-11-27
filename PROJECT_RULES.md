# Live Machine Academy — règles

## Objectif
App Android native, APK installable, 100 % hors ligne : apprendre une vraie machine leçon par leçon (lire sur le téléphone, pratiquer sur la machine). Styles : hardtek, tribe, tribeCore, acidcore, mentalcore. Pas de cours Ableton ni import SD (déjà maîtrisés).

## Interdits
Simulateur, WebView, site web. Permission réseau : interdite tant que l'utilisateur n'a pas validé une fonction en ligne (voir `docs/COMMERCIAL.md`).

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

## Commercial (structure inactive)
`domain/commerce` (`Feature`, `Plan`, `FeatureGate`), `domain/result` (`AppResult`), `data/commerce|account|sync|services` : implémentations locales (gratuit, invité, pas de synchro, IA/audio indisponibles), branchées dans `AppContainer`. Contenu actuel 100 % gratuit ; Premium prévu : synchro, IA, analyse audio. Pas de chatbot ni paiement. Audit et étapes : `docs/COMMERCIAL.md`.

## Fait (v1.5.0)
- Electribe : 9 modules × 6 leçons `VERIFIED`, 8 défis par style.
- Circuit Tracks : 12 modules × 3 leçons `VERIFIED` (12 q./module), fiche 5 sections, 24 commandes photo, 4 défis, 7 ateliers (pattern, variation, automation, macros, 2 transitions, séquence live ; étapes cochables + validation), 9 termes.
- Leçon : 8 rubriques, étapes cochables, exercice validé à la main, quiz corrigé (80 %), photo ciblée sur la leçon. Quiz final 10 q., révision des erreurs.
- Photo : l'utilisateur photographie SA machine ; aucune image constructeur.
- Tests : `CourseContentTest`, `CircuitTracksContentTest`, `MultiMachineTest`, `ProgressRepositoryTest`, `CommerceTest`, `QuizSessionTest`, `ProgressRulesTest`.
- CI : `android.yml` (tests, lint, APK debug + release) ; `screenshots.yml` (émulateur ; `variant`, `upgrade_from`, `script_mode=smoke` ; commite `docs/screenshots/`) ; `release.yml` (vérifie, refuse INTERNET, publie). Release publiée : v1.4.0.
- Signature : `app/debug.keystore` partagée non secrète ; `LMA_KEYSTORE_*` prioritaire. APK ≤ 1.1.2 : désinstallation unique.

## Prochaine tâche
Décisions Play (`docs/COMMERCIAL.md`) : AAB + clé d'envoi secrète, confidentialité, avis marques. Puis retours Circuit Tracks, Electribe 7.1/7.2, texte Photo (« Write »).

## Règles de travail
- Aucune fonction inventée : toute info machine cite sa `ManualRef` ; `VERIFIED` seulement si vérifié.
- Compilation locale impossible : pousser et vérifier GitHub Actions.
- Branche unique `ccr-da6a4977-k0rjt6`, pas de PR.
- Incrémenter `versionCode`/`versionName` à chaque version.
- Français, concis ; mettre ce fichier à jour en fin de tâche.
