# Live Machine Academy

App Android hors ligne pour apprendre la Korg electribe sampler, leçon par leçon, sur la vraie machine.
Détails du projet : [PROJECT_RULES.md](PROJECT_RULES.md).

## Compiler
JDK 17 + SDK Android (compileSdk 35) :
```bash
./gradlew testDebugUnitTest assembleDebug   # APK : app/build/outputs/apk/debug/app-debug.apk
```
Chaque push compile aussi sur GitHub Actions (onglet Actions → artefact `live-machine-academy-debug-apk`).

## Installer
Copier l'APK sur le téléphone et l'ouvrir (autoriser l'installation depuis cette source), ou `adb install app-debug.apk`.
