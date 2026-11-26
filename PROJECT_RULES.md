# Live Machine Academy — règles du projet

## Objectif
App Android native (APK, hors ligne) pour apprendre de A à Z la **Korg electribe sampler** rose (electribe2s, « Electribe Sampler 2 »). L'utilisateur lit sur son téléphone puis pratique sur la vraie machine. Styles : mentalcore, tribe, tribeCore, hardtek, acidcore. Utilisateur déjà à l'aise avec Ableton et l'import de samples depuis SD (ne pas en faire de cours).

## Interdits
Pas de simulateur, pas de WebView, pas de site web, pas de permission réseau.

## Stack / architecture
Kotlin, Jetpack Compose, Material 3 (thème sombre unique, magenta), MVVM + StateFlow, Navigation Compose type-safe, DataStore (progression, paramètres), DI manuelle (`di/AppContainer`). Package `com.livemachine.academy`. minSdk 26, compileSdk 35, AGP 8.9.1, Kotlin 2.1.20, Gradle 8.14.3.
- `data/model` : `Lesson` (8 rubriques), `ManualRef(chapter, pages, doc)`, `ContentStatus`.
- `data/content` : `ModuleNContent.kt` (1 à 9), `CourseContent`, `MachineContent`, `GlossaryContent`.
- `ui/screens` : Accueil, Parcours, Machine, Modules, Module, Leçon, Quiz, Exercices, Progression, Glossaire, Paramètres.

## Sources (obligatoires)
- Manuel d'utilisation electribe/electribe sampler (Korg, 2015), **partie française p. 20–38** → `ManualDoc.OWNERS_MANUAL`.
- electribe sampler Parameter Guide (Korg, 2016, anglais) **p. 3–19** → `ManualDoc.PARAMETER_GUIDE`.
- PDF dans `docs/manual/` : ignorés par git, **ne jamais les committer**. Absents en début de session : les redemander si une leçon doit être modifiée.
- Divergences signalées dans les leçons : sauvegarde Global (manuel p. 32 vs guide p. 7) ; AUTO POWER OFF « Off » (manuel) / « Disable » (guide).
- Non documenté, à faire vérifier sur la machine : moment du changement de pattern en lecture (7.1), sauvegarde des Part Mute (7.2).

## Fait (v1.0.0)
- 11 écrans, progression, quiz, paramètres (taille du texte, écran allumé).
- 9 modules × 6 leçons = 54 leçons `VERIFIED`, chacune citant ses pages.
- Tests (`CourseContentTest`, `QuizSessionTest`) : leçons complètes, pages valides par document.
- CI `.github/workflows/android.yml` (tests + APK debug en artefact) ; `screenshots.yml` (émulateur à la demande → `docs/screenshots/`).

## Prochaines étapes
1. Recueillir les retours de l'utilisateur (téléphone + vraie machine) et corriger.
2. Vérifier les tailles de texte « Grande » / « Très grande » (captures).
3. Clé de signature release (actuellement clé debug).

## Règles
- N'inventer aucune fonction : toute info machine cite `ManualRef` ; `VERIFIED` seulement si vérifié dans le document.
- Compilation impossible en local (SDK bloqué) : pousser et vérifier le run GitHub Actions.
- Branche de travail : `ccr-da6a4977-k0rjt6` (seule branche, pas de PR possible).
- Incrémenter `versionCode`/`versionName` (build.gradle.kts + SettingsScreen) à chaque version.
- Répondre en français, de façon concise.
