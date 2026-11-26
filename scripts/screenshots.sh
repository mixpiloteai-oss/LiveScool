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
  # La lecture de l'écran échoue parfois (« could not get idle state ») : on réessaie.
  local out
  for _ in 1 2 3; do
    adb shell rm -f /sdcard/ui.xml >/dev/null 2>&1
    out=$(adb shell uiautomator dump /sdcard/ui.xml 2>&1)
    echo "$out" | grep -q "dumped" && break
    echo "$(date +%T) dump : $out" >> "$OUT/diagnostic.txt"
    sleep 2
  done
  rm -f /tmp/ui.xml
  adb pull /sdcard/ui.xml /tmp/ui.xml >/dev/null 2>&1
  python3 - "$1" "${2:-}" <<'PY2'
import re, sys, xml.etree.ElementTree as ET
target = sys.argv[1]
exact = sys.argv[2] == "exact"
try:
    root = ET.parse('/tmp/ui.xml').getroot()
except Exception:
    sys.exit(0)
for n in root.iter('node'):
    text, desc = n.get('text') or '', n.get('content-desc') or ''
    if (text == target or desc == target) if exact else (target in text or target in desc):
        x1, y1, x2, y2 = map(int, re.findall(r'\d+', n.get('bounds')))
        print((x1 + x2) // 2, (y1 + y2) // 2)
        break
PY2
}

# Ferme les dialogues système « … isn't responding » (fréquents juste après le démarrage de l'émulateur).
dismiss_system_dialogs() {
  if [ -n "$(coords_of "isn't responding")" ]; then
    local wait; wait=$(coords_of "Wait")
    [ -n "$wait" ] && adb shell input tap $wait && echo "Dialogue système fermé" && sleep 2
  fi
}

# Touche l'élément dont le texte est exactement $1 (onglets de la barre du bas).
tap_tab() {
  local coords=""
  for _ in 1 2 3 4 5; do
    dismiss_system_dialogs
    coords=$(coords_of "$1" exact)
    if [ -n "$coords" ]; then adb shell input tap $coords; sleep 2; return 0; fi
    sleep 2
  done
  echo "ONGLET INTROUVABLE : $1"; adb exec-out screencap -p > "$OUT/echec-$(date +%H%M%S).png"; { echo "$(date +%T) introuvable"; grep -o 'text="[^"]*"' /tmp/ui.xml | head -15; } >> "$OUT/diagnostic.txt"; FAILED=1; return 1
}

# Touche le premier élément dont le texte (ou la description) contient $1.
tap_text() {
  local text="$1" coords=""
  for _ in 1 2 3 4 5; do
    dismiss_system_dialogs
    coords=$(coords_of "$text")
    if [ -n "$coords" ]; then
      adb shell input tap $coords
      sleep 2
      return 0
    fi
    sleep 2
  done
  echo "ÉLÉMENT INTROUVABLE : $text"; adb exec-out screencap -p > "$OUT/echec-$(date +%H%M%S).png"
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

# Relance l'app depuis l'accueil (la progression, stockée sur l'appareil, est conservée)
# et attend qu'elle soit au premier plan.
restart() {
  adb shell am start -S -W -n "$PKG/.MainActivity" >/dev/null
  for _ in $(seq 1 20); do
    adb shell dumpsys activity activities | grep -q "mResumedActivity.*$PKG" && break
    sleep 1
  done
  sleep 4
  dismiss_system_dialogs
}

# Diagnostic : activité au premier plan et plantages éventuels.
diagnose() {
  mkdir -p "$OUT"
  { echo "== $1"; adb shell dumpsys activity activities | grep -E "mResumedActivity|topResumedActivity"; adb logcat -d -b crash | tail -40; } >> "$OUT/diagnostic.txt"
}

scroll_to() {
  for _ in $(seq 1 12); do
    dismiss_system_dialogs
    [ -n "$(coords_of "$1")" ] && return 0
    swipe_up
  done
  echo "ÉLÉMENT INTROUVABLE APRÈS DÉFILEMENT : $1"; adb exec-out screencap -p > "$OUT/echec-$(date +%H%M%S).png"; { echo "$(date +%T) introuvable"; grep -o 'text="[^"]*"' /tmp/ui.xml | head -15; } >> "$OUT/diagnostic.txt"; FAILED=1; return 1
}

# --- Diagnostic rapide : contenu complet de l'écran Parcours ---
if [ "${1:-}" = "smoke" ]; then
  restart
  shot s1-accueil
  tap_tab "Parcours"
  shot s2-parcours
  coords_of "rien" >/dev/null
  { echo "== smoke : écran Parcours, tous les noeuds"; grep -o '<node [^>]*>' /tmp/ui.xml | grep -o 'text="[^"]*"\|content-desc="[^"]*"\|clickable="true"\|bounds="[^"]*"' | paste -sd' ' | sed 's/bounds=/\n/g'; } >> "$OUT/diagnostic.txt"
  tap_text "Interface et navigation" && shot s3-module-1
  exit 0
fi

# --- Test de mise à jour : progression créée dans l'ancienne version ---
if [ "${1:-}" = "seed" ]; then
  restart
  shot 00-ancienne-version-demarrage
  diagnose "ancienne version au démarrage"
  tap_tab "Parcours" && tap_text "Interface et navigation" && tap_text "Vue d'ensemble de la façade"
  scroll_to_and_tap "Machine éteinte devant toi"
  scroll_to_and_tap "J'ai réussi l'exercice"
  scroll_to_and_tap "J'ai fait la leçon sur ma machine"
  scroll_to_and_tap "Lancer le quiz"
  tap_text "Régler le volume du casque" && tap_text "Valider"
  restart
  tap_tab "Progrès" && shot 00a-progression-avant-mise-a-jour
  exit $FAILED
fi
if [ "${1:-}" = "verify-upgrade" ]; then
  restart
  diagnose "nouvelle version après mise à jour"
  tap_tab "Progrès" && shot 00b-progression-apres-mise-a-jour
  for attendu in "1/54" "Réviser mes erreurs (1)"; do
    if [ -z "$(coords_of "$attendu")" ]; then echo "PROGRESSION PERDUE : « $attendu » absent"; FAILED=1; else echo "OK après mise à jour : $attendu"; fi
  done
  exit $FAILED
fi

restart
shot 01-accueil
tap_tab "Parcours" && shot 02-parcours-verrous

# Leçon : étape cochée, exercice validé, quiz avec une erreur volontaire
tap_text "Interface et navigation" && shot 03-module-1
tap_text "Vue d'ensemble de la façade" && shot 04-lecon
scroll_to_and_tap "Machine éteinte devant toi" && shot 05-etape-cochee
scroll_to_and_tap "J'ai réussi l'exercice" && shot 06-exercice-valide
scroll_to_and_tap "Lancer le quiz" && shot 07-quiz
tap_text "Régler le volume du casque" && tap_text "Valider" && shot 08-quiz-erreur

# Quiz final du module 1
restart
tap_tab "Parcours" && tap_text "Interface et navigation"
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
scroll_to_and_tap "Fiche de ta Electribe" && scroll_to_and_tap "Identifier les commandes" && shot 14-photo

# Progression
restart
tap_tab "Progrès" && shot 15-progression
swipe_up; shot 16-progression-suite
tap_text "Paramètres" && shot 17-parametres

# Autres écrans : glossaire (avec recherche), exercices, catalogue des modules
restart
tap_tab "Glossaire" && tap_text "Rechercher un terme" && adb shell input text "filtre" && sleep 2 && shot 18-glossaire-recherche
restart
scroll_to_and_tap "À faire sur la vraie machine" && shot 19-exercices
restart
tap_text "Accès direct à chaque module" && shot 20-modules

# Deuxième machine : Novation Circuit Tracks, puis retour à l'Electribe
restart
tap_text "Circuit Tracks" && shot 23-accueil-circuit-tracks
tap_tab "Machine" && shot 24-fiche-circuit-tracks
tap_text "Commencer l'apprentissage" && shot 25-parcours-circuit-tracks
tap_text "Interface et navigation" && shot 27-module-circuit-tracks
tap_text "La façade et le principe des vues" && shot 28-lecon-circuit-tracks
scroll_to_and_tap "Repérer sur ma photo" && shot 29-photo-lecon-circuit-tracks
restart
scroll_to_and_tap "Ateliers et défis" && shot 30-ateliers-circuit-tracks
restart
tap_text "Electribe Sampler 2" && shot 26-retour-electribe

# Taille de texte « Très grande »
restart
tap_tab "Progrès" && tap_text "Paramètres" && tap_text "Très grande"
restart
shot 21-texte-xl-accueil
tap_text "Continuer" && shot 22-texte-xl-lecon

ls -1 "$OUT"
exit $FAILED
