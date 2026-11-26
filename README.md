# Live Machine Academy (nom provisoire)

Application Android native pour apprendre de A à Z la **Korg Electribe Sampler 2 (rose)**.
On lit la leçon sur le téléphone, puis on la refait sur la vraie machine. Pas de simulateur,
pas de WebView, fonctionne hors ligne.

Styles visés : mentalcore, tribe, tribeCore, hardtek, acidcore.

## État actuel (v0.5.0)

- Architecture, navigation et les 11 écrans de base en place.
- **Module 1 (Interface et navigation) rédigé** : 6 leçons complètes, avec exercices et quiz, d'après
  le manuel officiel (p. 22–34). Fiche Machine et glossaire de la machine, avec leurs pages.
- **Module 2 (Parts et samples) rédigé** : 6 leçons (assigner, régler la lecture, SAMPLE EDIT, clavier,
  copier/couper/effacer, rééchantillonnage), d'après le manuel (p. 23–38).
- **Module 3 (Paramètres sonores) rédigé** : 6 leçons (pattern de travail et KNOB MODE, oscillateurs,
  hauteur et glide, enveloppe, polyphonie et priorité, dynamique), d'après le Guide des paramètres.
- **Module 4 (Séquenceur et patterns) rédigé** : 6 leçons (pas à pas, temps réel, longueur/beat/Last Step,
  STEP EDIT, swing et groove, gammes et transposition).
- Modules 5 à 9 : définis, leçons à rédiger depuis le manuel (voir `docs/manual/README.md`).
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
