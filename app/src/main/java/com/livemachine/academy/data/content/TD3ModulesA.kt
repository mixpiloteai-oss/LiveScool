package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** TD-3 — modules 1 à 3 : prise en main, le son acid, écrire un motif. */
internal object TD3ModulesA {

    // Références du Quick Start Guide (pages imprimées)
    private val ARRIERE = td3("Face arrière", "25")
    private val CONFIG = td3("Configuration matérielle, temps de chauffe", "43")
    private val ACCORDAGE = td3("Accordage", "43")
    private val HORLOGE = td3("Réglage de la synchronisation et de l'horloge", "42–43")
    private val CONNEXIONS = td3("Panneau de connexions", "24, 43")
    private val SYNTH = td3("Section du synthétiseur", "24")
    private val CHEMIN = td3("Le chemin du signal", "42–43")
    private val DISTO = td3("Section Distortion", "24")
    private val GENERAL = td3("Réglages généraux", "24")
    private val MOTIF = td3("Écriture d'un motif", "64")
    private val PITCH = td3("Écrire les notes en PITCH MODE, vérification", "64")
    private val TIME = td3("Écriture du rythme en TIME MODE, méthode alternative", "64–65")
    private val TIME_EN = td3("Writing the timing in TIME MODE", "56–57")

    // ---------------------------------------------------------------------
    // Module 1 — Prise en main
    // ---------------------------------------------------------------------
    private const val M1 = "td3-m1"

    private val m1l1 = ctLesson(
        M1, 1, "Brancher, allumer, laisser chauffer", listOf(ARRIERE, CONFIG), 15,
        objective = "Installer la TD-3 sans risque et la laisser se stabiliser.",
        explanation = "Toutes les connexions se font appareil éteint, volume au minimum. OUTPUT (jack 6,35 mm) vers une entrée ligne ; PHONES " +
            "(minijack stéréo) pour le casque. Seul l'adaptateur 9 V fourni doit être utilisé (prise DC INPUT). La TD-3 s'allume avant " +
            "les amplis et s'éteint après eux, pour éviter les claquements. Le guide recommande au moins 15 minutes de chauffe avant " +
            "d'enregistrer ou de jouer en live (plus si elle a eu froid). En USB, elle est reconnue comme appareil MIDI sans pilote.",
        controls = listOf(
            cmd("OUTPUT, PHONES", "Sortie ligne et casque.", ARRIERE),
            cmd("DC INPUT, POWER", "Adaptateur 9 V fourni, interrupteur.", ARRIERE),
            cmd("VOLUME", "Niveau de la sortie et du casque.", ARRIERE),
        ),
        steps = listOf(
            "TD-3 éteinte, volume au minimum, relie OUTPUT à ta table.",
            "Branche l'adaptateur 9 V fourni.",
            "Allume la TD-3, puis l'ampli.",
            "Attends 15 minutes avant de l'accorder ou de jouer.",
        ),
        expected = "La TD-3 est allumée, chaude et stable.",
        exerciseTitle = "Routine d'installation",
        exercise = listOf("Allume la TD-3 en arrivant et prépare le reste pendant les 15 minutes de chauffe."),
        success = "La TD-3 est prête en même temps que le reste du set.",
        mistakes = listOf(
            "Jouer tout de suite : les circuits analogiques ne sont pas encore accordés.",
            "Allumer l'ampli avant la TD-3 : risque de claquement.",
        ),
        quiz = listOf(
            qz("Temps de chauffe recommandé ?", listOf("Au moins 15 minutes", "Aucun"), 0, "Plus si elle a eu froid.", CONFIG),
            qz("Quelle alimentation ?", listOf("L'adaptateur 9 V fourni", "Un 12 V quelconque"), 0, "Uniquement celui fourni.", ARRIERE),
            qz("Qu'allume-t-on en premier ?", listOf("La TD-3", "L'ampli"), 0, "Elle s'éteint aussi en dernier.", CONFIG),
            qz("Pilote USB nécessaire ?", listOf("Non", "Oui"), 0, "Appareil USB MIDI reconnu nativement.", CONFIG),
        ),
    )

    private val m1l2 = ctLesson(
        M1, 2, "Accorder la TD-3", listOf(ACCORDAGE), 10,
        objective = "Accorder la TD-3 avec les autres instruments.",
        explanation = "Volume au minimum, MODE sur PATTERN PLAY, puis PITCH MODE maintenu + bouton PATTERN 6 (note LA). Chaque appui sur " +
            "WRITE/NEXT fait entendre cette note ; on règle le volume, puis TUNE jusqu'à 220 Hz exactement (ou pour s'accorder avec les " +
            "autres instruments). Dans ce mode, les notes sont transposées d'une octave vers le haut. Pour une autre note de référence : " +
            "PITCH MODE maintenu + la note voulue.",
        controls = listOf(
            cmd("MODE > PATTERN PLAY", "Position pour l'accordage.", ACCORDAGE),
            cmd("PITCH MODE + PATTERN 6", "Note LA de référence.", ACCORDAGE),
            cmd("WRITE/NEXT, TUNE", "Jouer la note, accorder.", ACCORDAGE),
        ),
        steps = listOf(
            "VOLUME au minimum, MODE sur PATTERN PLAY.",
            "Maintiens PITCH MODE et appuie sur PATTERN 6.",
            "Appuie sur WRITE/NEXT et monte le volume.",
            "Règle TUNE avec un accordeur jusqu'à 220 Hz.",
        ),
        expected = "La TD-3 joue un LA juste.",
        exerciseTitle = "Accord avec le set",
        exercise = listOf("Accorde la TD-3 sur une note jouée par une autre machine de ton set."),
        success = "Aucun battement entre les deux machines.",
        mistakes = listOf("Accorder avant la chauffe : l'accord bouge ensuite."),
        quiz = listOf(
            qz("Note de référence ?", listOf("LA (pad PATTERN 6)", "DO (PATTERN 1)"), 0, "PITCH MODE + PATTERN 6.", ACCORDAGE),
            qz("Fréquence visée ?", listOf("220 Hz", "440 Hz"), 0, "Les notes sont transposées d'une octave plus haut dans ce mode.", ACCORDAGE),
            qz("Bouton qui joue la note ?", listOf("WRITE/NEXT", "START/STOP"), 0, "À chaque appui.", ACCORDAGE),
            qz("Position du MODE ?", listOf("PATTERN PLAY", "TRACK WRITE"), 0, "Pour l'accordage.", ACCORDAGE),
        ),
    )

    private val m1l3 = ctLesson(
        M1, 3, "Horloge et connexions", listOf(HORLOGE, CONNEXIONS), 15,
        objective = "Choisir la source d'horloge et connaître le panneau de minijacks.",
        explanation = "FUNCTION, aucune séquence en lecture, puis BACK + WRITE/NEXT ensemble : les LED 1 à 8 s'allument. Boutons 1 à 4 = source " +
            "d'horloge INT, MIDI, USB ou TRIG ; boutons 5 à 8 = résolution 1 par noire, 2, 24 ou 48 pulsations par noire (24 ou 48 " +
            "recommandées). 3 secondes sans appui = sauvegarde. Sans horloge externe, la source doit être INT. Panneau minijack : FILTER IN " +
            "(source externe dans le filtre), SYNC IN (horloge et start/stop), CV OUT (1 V/octave), GATE OUT ; ne pas dépasser les tensions " +
            "indiquées.",
        controls = listOf(
            cmd("FUNCTION puis BACK + WRITE/NEXT", "Réglage de synchronisation.", HORLOGE),
            cmd("Boutons 1–4 / 5–8", "Source / résolution.", HORLOGE),
            cmd("FILTER IN, SYNC IN, CV OUT, GATE OUT", "Minijacks.", CONNEXIONS),
        ),
        steps = listOf(
            "Arrête la lecture, appuie sur FUNCTION.",
            "Appuie ensemble sur BACK et WRITE/NEXT.",
            "Choisis MIDI (bouton 2) et 24 pulsations (bouton 7).",
            "Attends 3 secondes, puis vérifie en recommençant.",
        ),
        expected = "La TD-3 suit l'horloge MIDI de ta machine maîtresse.",
        exerciseTitle = "TD-3 esclave",
        exercise = listOf("Synchronise la TD-3 sur ta boîte à rythmes en MIDI, puis remets INT pour jouer seule."),
        success = "Elle suit le tempo, puis rejoue seule en INT.",
        style = "Acidcore : la ligne de TD-3 calée sur la boîte à rythmes.",
        mistakes = listOf("Laisser la source sur MIDI sans horloge : le séquenceur ne suit plus rien."),
        quiz = listOf(
            qz("Source d'horloge sans machine externe ?", listOf("INT", "TRIG"), 0, "Toujours INT sans horloge externe.", HORLOGE),
            qz("Entrer dans le réglage ?", listOf("FUNCTION, puis BACK + WRITE/NEXT", "SHIFT + TEMPO"), 0, "Aucune séquence en lecture.", HORLOGE),
            qz("Résolutions recommandées ?", listOf("24 ou 48 pulsations par noire", "1 par noire"), 0, "Remarque du guide.", HORLOGE),
            qz("CV OUT envoie…", listOf("1 V/octave", "De l'audio"), 0, "Tension de contrôle.", CONNEXIONS),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 2 — Le son acid
    // ---------------------------------------------------------------------
    private const val M2 = "td3-m2"

    private val m2l1 = ctLesson(
        M2, 1, "Oscillateur et filtre", listOf(SYNTH, CHEMIN), 15,
        objective = "Comprendre le trajet du son et jouer avec le filtre.",
        explanation = "Le son part du VCO : WAVEFORM choisit dent de scie inversée ou pulsation, TUNE règle la hauteur (environ une octave " +
            "au-dessus ou au-dessous du centre). Il passe ensuite dans le filtre passe-bas (VCF) : CUTOFF atténue les fréquences au-dessus " +
            "de la coupure, RESONANCE amplifie le signal autour de la coupure ; poussée vers le maximum, elle fait auto-osciller le filtre. " +
            "Puis le VCA, la distorsion (si active) et VOLUME.",
        controls = listOf(
            cmd("WAVEFORM", "Dent de scie inversée ou pulsation.", SYNTH),
            cmd("TUNE", "Hauteur du VCO.", SYNTH),
            cmd("CUTOFF, RESONANCE", "Filtre passe-bas.", SYNTH),
        ),
        steps = listOf(
            "Lance un motif, écoute les deux formes d'onde avec WAVEFORM.",
            "Ferme CUTOFF puis rouvre-le.",
            "Monte RESONANCE et balaie CUTOFF.",
        ),
        expected = "Le son « crie » quand la résonance est haute et que tu balaies le filtre.",
        exerciseTitle = "Balayage acid",
        exercise = listOf("Sur une boucle, balaie CUTOFF lentement sur 16 mesures avec une résonance haute."),
        success = "Le balayage fait monter la tension sans couper le son.",
        style = "Acidcore : résonance haute et filtre joué à la main.",
        mistakes = listOf("Résonance au maximum sans le vouloir : le filtre auto-oscille."),
        quiz = listOf(
            qz("Formes d'onde ?", listOf("Dent de scie inversée ou pulsation", "Sinus, triangle"), 0, "Sélecteur WAVEFORM.", SYNTH),
            qz("Type de filtre ?", listOf("Passe-bas", "Passe-haut"), 0, "Il atténue au-dessus de la coupure.", SYNTH),
            qz("Résonance vers le maximum ?", listOf("Auto-oscillation", "Silence"), 0, "Effets très intéressants.", CHEMIN),
            qz("Plage de TUNE ?", listOf("Environ ±1 octave", "±12 octaves"), 0, "À partir du centre.", SYNTH),
        ),
    )

    private val m2l2 = ctLesson(
        M2, 2, "Enveloppe, decay et accent", listOf(SYNTH, CHEMIN), 15,
        objective = "Faire « claquer » chaque note avec l'enveloppe du filtre.",
        explanation = "ENVELOPE règle combien l'enveloppe module la coupure du filtre ; DECAY le temps de retour au minimum. Les deux sont liés : " +
            "plus ENVELOPE est haut, plus DECAY s'entend. ACCENT règle l'intensité de l'accent, mais seulement sur les notes programmées " +
            "avec un accent.",
        controls = listOf(
            cmd("ENVELOPE", "Profondeur de modulation du filtre.", SYNTH),
            cmd("DECAY", "Durée de l'enveloppe.", SYNTH),
            cmd("ACCENT", "Intensité des notes accentuées.", SYNTH),
        ),
        steps = listOf(
            "CUTOFF assez bas, monte ENVELOPE.",
            "Tourne DECAY : notes courtes ou longues.",
            "Monte ACCENT : seules les notes accentuées changent.",
        ),
        expected = "Des notes qui claquent et des accents marqués.",
        exerciseTitle = "Ligne qui claque",
        exercise = listOf("Règle ENVELOPE haut et DECAY court, puis monte ACCENT pendant 8 mesures."),
        success = "Les accents ressortent de plus en plus.",
        style = "Acidcore : decay court et accents forts.",
        mistakes = listOf("Chercher l'effet d'ACCENT sur un motif sans accent : il n'agit que sur les notes accentuées."),
        quiz = listOf(
            qz("ENVELOPE agit sur…", listOf("La coupure du filtre", "Le tempo"), 0, "Modulation par l'enveloppe.", SYNTH),
            qz("DECAY s'entend mieux quand…", listOf("ENVELOPE est haut", "VOLUME est bas"), 0, "Réglages interdépendants.", CHEMIN),
            qz("ACCENT agit sur…", listOf("Les notes accentuées seulement", "Toutes les notes"), 0, "Notes programmées avec accent.", SYNTH),
            qz("DECAY règle…", listOf("Le temps de retour au minimum", "La hauteur"), 0, "Temps de décroissance.", SYNTH),
        ),
    )

    private val m2l3 = ctLesson(
        M2, 3, "La distorsion", listOf(DISTO, GENERAL), 10,
        objective = "Salir la ligne de basse avec la distorsion intégrée.",
        explanation = "La distorsion se trouve juste avant le volume et la sortie finale. Elle n'agit que si DISTORTION est sur ON. DRIVE règle " +
            "la quantité, TONE la tonalité, LEVEL le niveau de la distorsion. En OFF, ces trois boutons n'ont aucun effet. TEMPO règle la " +
            "vitesse de lecture.",
        controls = listOf(
            cmd("DISTORTION ON/OFF", "Activer.", DISTO),
            cmd("DRIVE, TONE, LEVEL", "Quantité, couleur, niveau.", DISTO),
            cmd("TEMPO", "Vitesse.", GENERAL),
        ),
        steps = listOf(
            "Mets DISTORTION sur ON.",
            "Monte DRIVE, ajuste TONE.",
            "Équilibre LEVEL avec le son sans distorsion (bascule ON/OFF pour comparer).",
        ),
        expected = "Une ligne sale au même volume que sans distorsion.",
        exerciseTitle = "Montée saturée",
        exercise = listOf("Monte DRIVE progressivement pendant une montée, puis coupe la distorsion au break."),
        success = "Le contraste propre / sale marque le break.",
        style = "Mentalcore / acidcore : basse saturée pour les passages durs.",
        mistakes = listOf("Régler DRIVE avec DISTORTION sur OFF : rien ne change."),
        quiz = listOf(
            qz("Où est la distorsion dans le trajet ?", listOf("Avant le volume final", "Avant le VCO"), 0, "Juste avant la sortie.", DISTO),
            qz("DISTORTION sur OFF…", listOf("DRIVE, TONE, LEVEL sans effet", "Son coupé"), 0, "Ils n'agissent plus.", DISTO),
            qz("TONE règle…", listOf("La tonalité", "Le tempo"), 0, "Couleur de la distorsion.", DISTO),
            qz("LEVEL règle…", listOf("Le niveau de la distorsion", "Le volume du casque"), 0, "Section Distortion.", DISTO),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 3 — Écrire un motif
    // ---------------------------------------------------------------------
    private const val M3 = "td3-m3"

    private val m3l1 = ctLesson(
        M3, 1, "Préparer un motif", listOf(MOTIF, GENERAL), 15,
        objective = "Comprendre l'organisation des motifs et en préparer un vide.",
        explanation = "4 groupes de motifs (I à IV), chacun avec jusqu'à 8 motifs A et 8 motifs B ; un motif a jusqu'à 16 pas (double croche " +
            "par défaut). On écrit d'abord les notes (PITCH MODE), puis le rythme (TIME MODE), tout en PATTERN WRITE. Exemple du guide : " +
            "MODE sur PATTERN WRITE, groupe I (position 1 ou 2), FUNCTION (LED NORMAL MODE), PATTERN 1 (note DO), PATTERN A. Pour vider " +
            "1A : PATTERN 1 maintenu + CLEAR. Nombre de pas : FUNCTION maintenu + STEP autant de fois que de pas (sinon 16).",
        controls = listOf(
            cmd("MODE > PATTERN WRITE", "Écriture.", MOTIF),
            cmd("PATTERN GROUP I–IV", "Groupe.", GENERAL),
            cmd("PATTERN 1–8 + A / B", "Motif.", MOTIF),
            cmd("FUNCTION + STEP", "Nombre de pas.", MOTIF),
        ),
        steps = listOf(
            "MODE sur PATTERN WRITE, groupe I.",
            "FUNCTION, puis PATTERN 1 et PATTERN A.",
            "Vide le motif avec PATTERN 1 maintenu + CLEAR.",
            "FUNCTION maintenu + STEP 16 fois pour 16 pas.",
        ),
        expected = "Le motif 1A est vide et prêt, avec 16 pas.",
        exerciseTitle = "Plan de motifs",
        exercise = listOf("Décide : groupe I pour tes lignes acides, A pour la base, B pour les variations."),
        success = "Tu sais où écrire chaque ligne.",
        mistakes = listOf("Écrire en PATTERN PLAY : l'écriture se fait en PATTERN WRITE."),
        quiz = listOf(
            qz("Groupes de motifs ?", listOf("4 (I à IV)", "8"), 0, "8 motifs A et 8 B chacun.", MOTIF),
            qz("Pas maximum par motif ?", listOf("16", "64"), 0, "Double croche par défaut.", MOTIF),
            qz("Ordre d'écriture ?", listOf("Notes (PITCH) puis rythme (TIME)", "L'inverse"), 0, "En PATTERN WRITE.", MOTIF),
            qz("Fixer le nombre de pas ?", listOf("FUNCTION + STEP", "TAP"), 0, "Sinon 16 pas.", MOTIF),
        ),
    )

    private val m3l2 = ctLesson(
        M3, 2, "Entrer les notes (PITCH MODE)", listOf(PITCH), 15,
        objective = "Écrire la suite de notes d'une ligne de basse.",
        explanation = "PITCH MODE allumé : chaque touche du clavier de 13 notes ajoute une note. Pour changer d'octave, maintenir TRANSPOSE " +
            "UP ou DOWN et appuyer sur la note. Vérifier : PITCH MODE revient au début, WRITE/NEXT joue les notes une à une. Corriger : " +
            "BACK revient à la note qui vient de jouer, puis on appuie sur la nouvelle note. FUNCTION quitte PITCH MODE.",
        controls = listOf(
            cmd("PITCH MODE", "Entrée des notes.", PITCH),
            cmd("Clavier 13 notes", "Notes.", PITCH),
            cmd("TRANSPOSE UP / DOWN + note", "Octave.", PITCH),
            cmd("WRITE/NEXT, BACK", "Écouter, corriger.", PITCH),
        ),
        steps = listOf(
            "PITCH MODE, entre 8 notes (dont deux à l'octave avec TRANSPOSE UP).",
            "PITCH MODE pour revenir au début, puis WRITE/NEXT note par note.",
            "Corrige une note avec BACK puis la nouvelle note.",
            "FUNCTION pour sortir.",
        ),
        expected = "Les notes de ta ligne sont entrées, sans rythme encore.",
        exerciseTitle = "Ligne de 16 notes",
        exercise = listOf("Entre 16 notes en alternant notes graves et aiguës (TRANSPOSE)."),
        success = "La suite de notes est correcte à la vérification.",
        style = "Acidcore : sauts d'octave pour une ligne qui rebondit.",
        mistakes = listOf("Appuyer sur TRANSPOSE après la note : il faut le maintenir avant."),
        quiz = listOf(
            qz("Changer l'octave d'une note ?", listOf("TRANSPOSE maintenu + note", "SHIFT + note"), 0, "UP ou DOWN.", PITCH),
            qz("Écouter les notes une à une ?", listOf("WRITE/NEXT", "TAP"), 0, "Après PITCH MODE.", PITCH),
            qz("Revenir à la note précédente ?", listOf("BACK", "CLEAR"), 0, "Puis nouvelle note.", PITCH),
            qz("Quitter PITCH MODE ?", listOf("FUNCTION", "START/STOP"), 0, "Retour au mode normal.", PITCH),
        ),
    )

    private val m3l3 = ctLesson(
        M3, 3, "Le rythme (TIME MODE)", listOf(TIME, TIME_EN), 20,
        objective = "Donner à chaque pas une note, une liaison ou un silence.",
        explanation = "TIME MODE allumé (aucun son) : pour chaque pas, NOTE, TIE (liaison) ou REST (silence). Une fois le nombre de pas atteint, " +
            "on sort automatiquement. Appuyer seulement sur NOTE donne un arpège simple. Vérifier : TIME MODE puis WRITE/NEXT pas à pas " +
            "(les LED montrent note, liaison ou silence). Méthode alternative : FUNCTION puis START/STOP, CLEAR (métronome), puis taper le " +
            "rythme sur TAP (maintenir TAP = tenue). FUNCTION puis START/STOP pour écouter, TEMPO pour la vitesse.",
        controls = listOf(
            cmd("TIME MODE", "Entrée du rythme.", TIME),
            cmd("NOTE, TIE, REST", "Note, liaison, silence.", TIME_EN),
            cmd("TAP", "Rythme tapé.", TIME),
        ),
        steps = listOf(
            "TIME MODE, puis entre : NOTE, REST, REST, NOTE, TIE, NOTE, NOTE, TIE…",
            "FUNCTION puis START/STOP pour écouter.",
            "Vérifie avec TIME MODE et WRITE/NEXT.",
        ),
        expected = "La ligne joue avec silences et liaisons.",
        exerciseTitle = "Groove acid",
        exercise = listOf("Refais le rythme avec la méthode TAP jusqu'à ce que le groove te plaise."),
        success = "Une ligne qui groove, avec des silences.",
        style = "Acidcore : les silences et liaisons font le groove de la ligne.",
        mistakes = listOf("S'attendre à entendre le son en TIME MODE : il est muet."),
        quiz = listOf(
            qz("Entend-on le son en TIME MODE ?", listOf("Non", "Oui"), 0, "Aucun son.", TIME),
            qz("Les trois choix par pas ?", listOf("NOTE, TIE, REST", "ACCENT, SLIDE, STEP"), 0, "Note, liaison, silence.", TIME_EN),
            qz("Seulement NOTE à chaque pas donne…", listOf("Un arpège simple", "Un silence"), 0, "Remarque du guide.", TIME),
            qz("Méthode alternative ?", listOf("Taper sur TAP", "Tourner TUNE"), 0, "Après CLEAR (métronome).", TIME),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(M1, 1, "Prise en main", "Branchements, chauffe, accordage, horloge, connexions.",
            "Installer et accorder la TD-3.", "Une TD-3 chaude et accordée = une ligne acid juste.", listOf(m1l1, m1l2, m1l3)),
        CourseModule(M2, 2, "Le son acid", "VCO, filtre, enveloppe, accent, distorsion.",
            "Façonner le son de basse acid.", "Résonance, decay court et distorsion : la base de l'acidcore.", listOf(m2l1, m2l2, m2l3)),
        CourseModule(M3, 3, "Écrire un motif", "Organisation, notes en PITCH MODE, rythme en TIME MODE.",
            "Programmer une ligne de basse.", "Les silences et les octaves font rebondir la ligne.", listOf(m3l1, m3l2, m3l3)),
    )
}
