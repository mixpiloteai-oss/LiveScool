#!/usr/bin/env bash
# Parcourt les écrans de l'app dans l'émulateur et enregistre une capture de chacun.
# Utilisé par .github/workflows/screenshots.yml (émulateur déjà démarré, APK déjà installé).
set -u
OUT="docs/screenshots"
PKG="com.livemachine.academy"
mkdir -p "$OUT"
FAILED=0

shot() {
  sleep 2
  adb exec-out screencap -p > "$OUT/$1.png"
  echo "capture : $1"
}

# Affiche les coordonnées du centre du premier élément dont le texte contient $1 (vide sinon).
coords_of() {
  adb shell uiautomator dump /sdcard/ui.xml >/dev/null 2>&1
  adb pull /sdcard/ui.xml /tmp/ui.xml >/dev/null 2>&1
  python3 - "$1" <<'PY2'
import re, sys, xml.etree.ElementTree as ET
target = sys.argv[1]
try:
    root = ET.parse('/tmp/ui.xml').getroot()
except Exception:
    sys.exit(0)
for n in root.iter('node'):
    if target in (n.get('text') or '') or target in (n.get('content-desc') or ''):
        x1, y1, x2, y2 = map(int, re.findall(r'\d+', n.get('bounds')))
        print((x1 + x2) // 2, (y1 + y2) // 2)
        break
PY2
}

# Touche le premier élément dont le texte (ou la description) contient $1.
tap_text() {
  local text="$1" coords=""
  for _ in 1 2 3 4 5; do
    adb shell uiautomator dump /sdcard/ui.xml >/dev/null 2>&1
    adb pull /sdcard/ui.xml /tmp/ui.xml >/dev/null 2>&1
    coords=$(python3 - "$text" <<'PY'
import re, sys, xml.etree.ElementTree as ET
target = sys.argv[1]
try:
    root = ET.parse('/tmp/ui.xml').getroot()
except Exception:
    sys.exit(0)
for n in root.iter('node'):
    if target in (n.get('text') or '') or target in (n.get('content-desc') or ''):
        x1, y1, x2, y2 = map(int, re.findall(r'\d+', n.get('bounds')))
        print((x1 + x2) // 2, (y1 + y2) // 2)
        break
PY
)
    if [ -n "$coords" ]; then
      adb shell input tap $coords
      sleep 2
      return 0
    fi
    sleep 2
  done
  echo "ÉLÉMENT INTROUVABLE : $text"
  FAILED=1
  return 1
}

# Fait défiler jusqu'à voir $1, puis le touche.
scroll_to_and_tap() {
  scroll_to "$1" && tap_text "$1"
}

swipe_up() {
  local size w h
  size=$(adb shell wm size | grep -o '[0-9]*x[0-9]*' | tail -1)
  w=${size%x*}; h=${size#*x}
  adb shell input swipe $((w / 2)) $((h * 3 / 4)) $((w / 2)) $((h / 4)) 400
  sleep 1
}

back() { adb shell input keyevent KEYCODE_BACK; sleep 2; }

# Relance l'app depuis l'accueil (la progression, stockée sur l'appareil, est conservée).
restart() { adb shell am start -S -n "$PKG/.MainActivity" >/dev/null; sleep 6; }

scroll_to() {
  for _ in $(seq 1 12); do
    [ -n "$(coords_of "$1")" ] && return 0
    swipe_up
  done
  echo "ÉLÉMENT INTROUVABLE APRÈS DÉFILEMENT : $1"; FAILED=1; return 1
}

rm -f "$OUT"/*.png
restart
shot 01-accueil
tap_text "Parcours" && shot 02-parcours-verrous

# Leçon : étape cochée, exercice validé, quiz avec une erreur volontaire
tap_text "Interface et navigation" && shot 03-module-1
tap_text "Vue d'ensemble de la façade" && shot 04-lecon
scroll_to_and_tap "Machine éteinte devant toi" && shot 05-etape-cochee
scroll_to_and_tap "J'ai réussi l'exercice" && shot 06-exercice-valide
scroll_to_and_tap "Lancer le quiz" && shot 07-quiz
tap_text "Régler le volume du casque" && tap_text "Valider" && shot 08-quiz-erreur

# Quiz final du module 1
restart
tap_text "Parcours" && tap_text "Interface et navigation"
scroll_to "Lancer le quiz final" && shot 09-module-quiz-final
tap_text "Lancer le quiz final" && shot 10-quiz-final

# Révision des erreurs (la question ratée doit y être)
restart
shot 11-accueil-revision
tap_text "Réviser mes erreurs" && shot 12-revision

# Défis par style
restart
scroll_to_and_tap "Défis par style" && shot 13-defis

# Photo de la machine
restart
tap_text "Machine" && scroll_to_and_tap "Identifier les commandes" && shot 14-photo

# Progression
restart
tap_text "Progrès" && shot 15-progression
swipe_up; shot 16-progression-suite
tap_text "Paramètres" && shot 17-parametres

ls -1 "$OUT"
exit $FAILED
