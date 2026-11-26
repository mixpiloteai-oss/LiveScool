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
  for _ in $(seq 1 12); do
    adb shell uiautomator dump /sdcard/ui.xml >/dev/null 2>&1
    adb pull /sdcard/ui.xml /tmp/ui.xml >/dev/null 2>&1
    if grep -q "$1" /tmp/ui.xml; then
      tap_text "$1"
      return $?
    fi
    swipe_up
  done
  echo "ÉLÉMENT INTROUVABLE APRÈS DÉFILEMENT : $1"
  FAILED=1
  return 1
}

swipe_up() {
  local size w h
  size=$(adb shell wm size | grep -o '[0-9]*x[0-9]*' | tail -1)
  w=${size%x*}; h=${size#*x}
  adb shell input swipe $((w / 2)) $((h * 3 / 4)) $((w / 2)) $((h / 4)) 400
  sleep 1
}

back() { adb shell input keyevent KEYCODE_BACK; sleep 2; }

adb shell am start -n "$PKG/.MainActivity" >/dev/null
sleep 8

shot 01-accueil
tap_text "Parcours" && shot 02-parcours
tap_text "Interface et navigation" && shot 03-module-1
tap_text "Vue d'ensemble de la façade" && shot 04-lecon-haut
swipe_up; shot 05-lecon-commandes
swipe_up; swipe_up; shot 06-lecon-etapes
scroll_to_and_tap "Lancer le quiz" && shot 07-quiz
tap_text "niveau du signal reçu sur la prise Audio In" && tap_text "Valider" && shot 08-quiz-correction
back; back; back
tap_text "Machine" && shot 09-machine
tap_text "Progrès" && shot 10-progression
tap_text "Glossaire" && shot 11-glossaire
tap_text "Paramètres" && shot 12-parametres
back
tap_text "Accueil" && tap_text "Exercices" && shot 13-exercices

ls -1 "$OUT"
exit $FAILED
