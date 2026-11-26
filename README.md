# Live Machine Academy

App Android native et hors ligne pour apprendre la Korg electribe sampler, leçon par leçon, sur la vraie machine.
Détails du projet : [PROJECT_RULES.md](PROJECT_RULES.md).

## Installer sur un téléphone (Samsung ou autre Android 8+)
1. Sur le téléphone, ouvrir la page **Releases** du dépôt GitHub et télécharger `LiveMachineAcademy-<version>-debug.apk`
   (ou `-release.apk`, plus léger et optimisé).
2. Ouvrir le fichier téléchargé. Samsung demande d'autoriser l'installation : **Paramètres → Applications → Accès spécial →
   Installer applis inconnues → Internet Samsung / Chrome / Mes fichiers → Autoriser**.
3. Installer, puis ouvrir « Live Machine Academy ». Aucune connexion n'est nécessaire ensuite.

Depuis un ordinateur avec le téléphone en USB (débogage USB activé) : `adb install LiveMachineAcademy-<version>-debug.apk`.

## Compiler
JDK 17 + SDK Android (compileSdk 35) :
```bash
./gradlew testDebugUnitTest assembleDebug     # app/build/outputs/apk/debug/app-debug.apk
./gradlew assembleRelease                     # app/build/outputs/apk/release/app-release.apk
```
Sur GitHub Actions : chaque push teste et compile (`android.yml`) ; le workflow **« Publier l'APK »** (`release.yml`, lancé
à la demande) vérifie les APK et les publie dans une Release.

## Release signée avec ta propre clé
Sans clé, la release est signée avec la clé debug : installable, mais pas publiable sur le Play Store.
1. Créer une clé (une seule fois, à conserver précieusement) :
   ```bash
   keytool -genkeypair -v -keystore lma-release.jks -alias lma -keyalg RSA -keysize 4096 -validity 10000
   ```
2. En local :
   ```bash
   export LMA_KEYSTORE_PATH=$PWD/lma-release.jks LMA_KEYSTORE_PASSWORD=... LMA_KEY_ALIAS=lma LMA_KEY_PASSWORD=...
   ./gradlew assembleRelease
   ```
3. Sur GitHub : ajouter les secrets `LMA_KEYSTORE_BASE64` (sortie de `base64 -w0 lma-release.jks`), `LMA_KEYSTORE_PASSWORD`,
   `LMA_KEY_ALIAS`, `LMA_KEY_PASSWORD`, puis lancer « Publier l'APK ».

Ne jamais committer le fichier `.jks` ni ses mots de passe. Une app signée avec une autre clé ne peut pas mettre à jour
celle déjà installée : il faut alors la désinstaller d'abord.
