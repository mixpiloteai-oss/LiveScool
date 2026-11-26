# Live Machine Academy — règles du projet

## Objectif
App Android native (APK, hors ligne) pour apprendre de A à Z la **Korg electribe sampler** rose (electribe2s, « Electribe Sampler 2 ») et la **Novation Circuit Tracks**. L'utilisateur lit sur son téléphone puis pratique sur la vraie machine. Styles : mentalcore, tribe, tribeCore, hardtek, acidcore. Utilisateur déjà à l'aise avec Ableton et l'import de samples depuis SD (ne pas en faire de cours).

## Interdits
Pas de simulateur, pas de WebView, pas de site web, pas de permission réseau.

## Stack / architecture
Kotlin, Jetpack Compose, Material 3 (thème sombre unique, magenta), MVVM + StateFlow, Navigation Compose type-safe, DataStore (progression, paramètres), DI manuelle (`di/AppContainer`). Package `com.livemachine.academy`. minSdk 26, compileSdk 35, AGP 8.9.1, Kotlin 2.1.20, Gradle 8.14.3.
- `data/model` : `Lesson` (8 rubriques), `ManualRef(chapter, pages, doc)`, `ContentStatus`.
- `data/content` : `ModuleNContent.kt` (1 à 9), `CourseContent`, `MachineContent`, `GlossaryContent`.
- `data/content/MachineCatalog.kt` : liste des machines ; `CircuitTracksContent.kt` (fiche, photo, défis, glossaire) + `CircuitTracksModulesA..D.kt` (12 modules) + `CircuitTracksDsl.kt` (`ct()`, `ctLesson()`).
- `ui/screens` : Accueil, Parcours, Machine, Modules, Module, Leçon, Quiz, Exercices, Progression, Glossaire, Paramètres.

## Sources (obligatoires)
- Manuel d'utilisation electribe/electribe sampler (Korg, 2015), **partie française p. 20–38** → `ManualDoc.OWNERS_MANUAL`.
- electribe sampler Parameter Guide (Korg, 2016, anglais) **p. 3–19** → `ManualDoc.PARAMETER_GUIDE`.
- Novation Circuit Tracks — Guide d'utilisation **Version 2, FR, p. 6–108** → `ManualDoc.CIRCUIT_TRACKS_GUIDE` (aucun n° de firmware dans le guide ; samples inversés cités p. 22/25 sans manipulation détaillée).
- PDF dans `docs/manual/` : ignorés par git, **ne jamais les committer**. Absents en début de session : les redemander si une leçon doit être modifiée.
- Divergences signalées dans les leçons : sauvegarde Global (manuel p. 32 vs guide p. 7) ; AUTO POWER OFF « Off » (manuel) / « Disable » (guide).
- Non documenté, à faire vérifier sur la machine : moment du changement de pattern en lecture (7.1), sauvegarde des Part Mute (7.2).

## Fait (v1.3.0)
- 11 écrans + Défis + Photo ; 9 modules × 6 leçons = 54 leçons `VERIFIED`, pages citées.
- Leçon : étapes cochables, exercice validable, quiz (80 %). Quiz final par module (10 q.), révision des erreurs (`wrongQuestions`).
- Déblocage : module N ouvert si N-1 validé (leçons faites OU quiz final ≥ 80 %), mode libre dans Paramètres. Compétence = leçons + quiz final. Règles pures dans `domain/ProgressRules.kt`.
- 8 défis par style (`ChallengeContent`), débloqués par module.
- Photo : l'utilisateur photographie SA machine, repère les commandes (`MachineContent.identifiableControls`), puis quiz « Où est… ? ». Aucune image Korg ni schéma inventé.
- Tests : `CourseContentTest`, `QuizSessionTest`, `ProgressRulesTest`. CI `android.yml` (tests, lint, APK debug + release) ; `screenshots.yml` (émulateur debug/release, options `upgrade_from` = tag pour tester la mise à jour, `script_mode` = smoke ; commite `docs/screenshots/`) ; `release.yml` (vérifie et publie les APK dans une Release GitHub).
- Multi-machine : `MachineProfile` + `MachineCatalog` (Electribe `electribe-sampler`, Circuit Tracks `circuit-tracks`, ids futurs préfixés `ct-`). Machine choisie à l'accueil (`selectedMachineId`, Electribe par défaut) ; parcours, exercices, défis, glossaire, révision, photo et fiche filtrés par machine. Progression : ids inchangés, aucune migration. Photo Electribe : anciennes clés/fichier conservés.
- Circuit Tracks : 12 modules × 3 leçons = 36 leçons `VERIFIED` (ids `ct-mN-lK`), 12 questions par module, fiche (5 sections), 24 commandes photo, 4 défis, 9 termes de glossaire. Test `CircuitTracksContentTest`.
- Release v1.2.0 publiée (GitHub Releases). Mise à jour v1.1.2 → v1.2.0 testée sur émulateur : progression conservée. Sur le Samsung (v1.1.2, ancienne clé) : désinstallation unique nécessaire.
- APK release v1.1.2 installé et fonctionnel sur le Samsung de l'utilisateur. Texte « Très grande » vérifié sur émulateur.
- Signature : `app/debug.keystore` versionnée (non secrète, signature stable entre runners) ; `LMA_KEYSTORE_*` prioritaire. Les APK ≤ 1.1.2 avaient une clé par runner → désinstallation unique nécessaire. Version lue via `BuildConfig`.

## Prochaines étapes
1. Retours utilisateur sur la Circuit Tracks (firmware de sa machine, samples inversés, leçons à approfondir).
2. Purger de l'historique git le PDF Circuit Tracks committé par l'upload GitHub (commit 2d71b2d), sur accord de l'utilisateur.
3. Retours utilisateur Electribe : points 7.1 / 7.2, écran Photo.
4. Clé release secrète de l'utilisateur si publication.

## Règles
- N'inventer aucune fonction : toute info machine cite `ManualRef` ; `VERIFIED` seulement si vérifié dans le document.
- Compilation impossible en local (SDK bloqué) : pousser et vérifier le run GitHub Actions.
- Branche de travail : `ccr-da6a4977-k0rjt6` (seule branche, pas de PR possible).
- Incrémenter `versionCode`/`versionName` dans `app/build.gradle.kts` à chaque version.
- Répondre en français, de façon concise.
