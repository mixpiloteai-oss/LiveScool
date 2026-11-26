# Architecture

Application Android native, 100 % hors ligne (aucune permission réseau, aucune WebView).

- **Kotlin + Jetpack Compose + Material 3**, thème sombre unique, accent magenta (`ui/theme`).
- **MVVM** : un ViewModel par écran (ou `OverviewViewModel` partagé), état exposé en `StateFlow`.
- **Navigation Compose type-safe** (`ui/navigation/Routes.kt`, routes `@Serializable`).
- **DataStore Preferences** pour la progression et les paramètres (`data/repository`).
  Room n'est pas nécessaire tant que le contenu est embarqué dans le code ; à reconsidérer
  si on ajoute des notes personnelles ou un historique détaillé.
- **Injection manuelle** via `di/AppContainer` + `ui/AppViewModelFactory`.

```
data/model        Modèles : CourseModule, Lesson, ManualRef, QuizQuestion, Exercise, GlossaryTerm…
data/content      Contenu embarqué : programme, glossaire, fiche machine
data/repository   CourseRepository (lecture), ProgressRepository, SettingsRepository (DataStore)
ui/navigation     Routes + NavHost + barre de navigation
ui/components     Composants communs (cartes, badges, bannière, avertissement « manuel »)
ui/screens        Accueil, Parcours, Machine, Modules, Module, Leçon, Quiz, Exercices,
                  Progression, Glossaire, Paramètres
```

## Structure d'une leçon

`Lesson` impose les 8 rubriques : objectif, explication simple, commandes, étapes sur la vraie machine,
résultat attendu, exercice, erreurs fréquentes, quiz — plus `manualRefs` (chapitre + pages) et un
`ContentStatus` (`AWAITING_MANUAL` → `DRAFT` → `VERIFIED`).

## Navigation

Barre du bas : Accueil · Parcours · Machine · Progrès · Glossaire.
Paramètres depuis la barre du haut ; Modules et Exercices depuis l'accueil ;
Module → Leçon → Quiz en profondeur.
