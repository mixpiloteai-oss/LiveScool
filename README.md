# Live Machine Academy (nom provisoire)

Application Android native pour apprendre de A à Z la **Korg Electribe Sampler 2 (rose)**.
On lit la leçon sur le téléphone, puis on la refait sur la vraie machine. Pas de simulateur,
pas de WebView, fonctionne hors ligne.

Styles visés : mentalcore, tribe, tribeCore, hardtek, acidcore.

## État actuel (v0.1.0)

- Architecture, navigation et les 11 écrans de base en place.
- Les 9 modules sont définis. **Aucun contenu technique sur la machine n'est encore rédigé** :
  il viendra uniquement du manuel officiel (voir `docs/manual/README.md`).
- Progression (leçons, exercices, meilleurs scores de quiz) et paramètres enregistrés sur l'appareil.

## Compiler

Il faut le JDK 17 et le SDK Android (compileSdk 35).

```bash
./gradlew testDebugUnitTest assembleDebug
# APK : app/build/outputs/apk/debug/app-debug.apk
```

Chaque push lance aussi la compilation sur GitHub Actions (`.github/workflows/android.yml`) :
l'APK se télécharge depuis l'onglet **Actions**, dans les artefacts du run.

## Installer sur Android

Copie l'APK sur le téléphone, ouvre-le et autorise l'installation depuis cette source.
Ou bien : `adb install app-debug.apk`.

Voir aussi `docs/ARCHITECTURE.md`.
