package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.CourseModule

/** SP-404MKII — modules 1 à 4 : prise en main, jeu des échantillons, effets, échantillonnage. */
internal object SP404MkIIModulesA {

    // Références du manuel de référence (pages imprimées = pages du PDF)
    private val DONNEES = sp4("Ce que vous devez savoir à propos de cette unité", "4–5")
    private val CONNEXIONS = sp4("Connexion d'un équipement externe", "6")
    private val ALIM = sp4("À propos de l'alimentation", "8")
    private val MISE_EN_ROUTE = sp4("Mise sous/hors tension", "10")
    private val MODE_SAMPLE = sp4("Lecture des échantillons, Sélection d'une banque", "11–12")
    private val BANK_VOLUME = sp4("Réglage du volume de toutes les banques", "12")
    private val GATE = sp4("Lecture uniquement lorsqu'un pad est enfoncé (GATE)", "14")
    private val LOOP = sp4("Lecture répétée d'échantillons (LOOP)", "15")
    private val REVERSE = sp4("Lecture d'un échantillon en arrière (REVERSE)", "16")
    private val ROLL = sp4("Lecture des échantillons par étapes (ROLL)", "17")
    private val FIXE = sp4("Réglage d'un volume d'échantillon fixe (VÉLOCITÉ FIXE)", "19")
    private val PAD_MUTE = sp4("Mise en sourdine (PAD MUTE)", "22")
    private val MUTE_GROUP = sp4("Empêcher les échantillons de jouer en même temps (GROUPE MUET)", "24")
    private val STOP = sp4("Arrêt de la lecture (STOP), Pause (PAUSE)", "25")
    private val BUS = sp4("Utilisation des effets, BUS FX", "27–28")
    private val EFFET_BOUTONS = sp4("Ajout d'effets à un échantillon", "28")
    private val ENVOI = sp4("Décider des effets à utiliser pour chaque échantillon, MFX", "29")
    private val EDIT_EFFET = sp4("Modification des effets, Verrouillage", "31")
    private val CAPTURE = sp4("Activation/désactivation des effets à la synchronisation souhaitée", "32")
    private val BUS_MUTE = sp4("Sortie temporaire uniquement du son d'effet (BUS MUTE)", "33")
    private val REGLAGE_REC = sp4("Configuration des paramètres d'échantillonnage", "35–36")
    private val ECHANTILLONNER = sp4("Échantillonnage, Ajout d'un décompte", "37")
    private val RESAMPLE = sp4("Rééchantillonnage d'un échantillon", "40")
    private val SKIP_BACK = sp4("Échantillonnage par saut (SKIP BACK)", "41")

    // ---------------------------------------------------------------------
    // Module 1 — Prise en main
    // ---------------------------------------------------------------------
    private const val M1 = "sp4-m1"

    private val m1l1 = ctLesson(
        M1, 1, "Brancher, alimenter, allumer", listOf(CONNEXIONS, ALIM, MISE_EN_ROUTE), 15,
        objective = "Relier le SP-404MKII à ton écoute et l'allumer dans le bon ordre.",
        explanation = "Volume baissé et appareils éteints avant de brancher. LINE OUT (arrière) vers l'ampli ou les enceintes ; PHONES pour " +
            "un casque ; LINE IN pour un synthé ou une boîte à rythmes ; INPUT (avant) pour un micro ou une guitare, avec le commutateur " +
            "MIC/GUITAR vers la droite pour un instrument à haute impédance. Alimentation : adaptateur fourni sur DC IN, ou port USB (5 V, " +
            "1,5 A minimum), ou piles ; s'il y en a plusieurs, l'ordre de priorité est DC IN, USB, piles. Allumage : POWER sur ON, puis " +
            "les appareils connectés, puis l'ampli ; volume réglé avec la molette VOLUME. Extinction dans l'ordre inverse.",
        controls = listOf(
            cmd("LINE OUT", "Sortie vers l'ampli.", CONNEXIONS),
            cmd("LINE IN, INPUT, MIC/GUITAR", "Entrées.", CONNEXIONS),
            cmd("DC IN, POWER", "Alimentation et interrupteur.", ALIM),
            cmd("VOLUME", "Volume général.", MISE_EN_ROUTE),
        ),
        steps = listOf(
            "Volume au minimum, relie LINE OUT à ton ampli ou tes enceintes.",
            "Branche l'adaptateur fourni sur DC IN.",
            "Mets POWER sur ON, puis allume l'ampli.",
            "Monte doucement VOLUME en jouant un pad.",
        ),
        expected = "Le SP-404MKII s'allume, les pads sont orange et le son sort sur tes enceintes.",
        exerciseTitle = "Ordre d'allumage",
        exercise = listOf(
            "Éteins tout, puis rallume dans l'ordre du manuel.",
            "Éteins ensuite dans l'ordre inverse : volume, ampli, POWER.",
        ),
        success = "Aucun bruit fort à l'allumage ni à l'extinction.",
        mistakes = listOf(
            "Allumer l'ampli avant le SP-404MKII : le manuel fait l'inverse.",
            "Brancher une guitare sur INPUT sans basculer MIC/GUITAR vers la droite.",
        ),
        quiz = listOf(
            qz("Où brancher les enceintes ?", listOf("LINE OUT", "LINE IN"), 0,
                "LINE OUT, sur le panneau arrière.", CONNEXIONS),
            qz("Priorité des sources d'alimentation ?", listOf("DC IN, USB, piles", "Piles, USB, DC IN"), 0,
                "Si plusieurs sources sont présentes.", ALIM),
            qz("Que doit fournir un port USB d'alimentation ?", listOf("5 V et au moins 1,5 A", "12 V"), 0,
                "Sinon « Switch to batteries » s'affiche.", MISE_EN_ROUTE),
            qz("Que faut-il allumer en dernier ?", listOf("L'ampli / les enceintes", "Le SP-404MKII"), 0,
                "SP-404MKII, appareils connectés, puis ampli.", MISE_EN_ROUTE),
        ),
    )

    private val m1l2 = ctLesson(
        M1, 2, "Échantillons, motifs, banques et projets", listOf(DONNEES), 10,
        objective = "Comprendre comment le SP-404MKII range ses données.",
        explanation = "Un échantillon, c'est un son enregistré plus ses réglages (boucle, envoi vers les effets). Il est attribué à un pad " +
            "[1] à [16]. 16 échantillons forment une banque ; il y a 10 banques (A à J). Un motif (pattern) enregistre l'ordre dans lequel " +
            "les échantillons sont joués ; 16 motifs forment une banque, 10 banques de motifs. Les 10 banques d'échantillons et les 10 " +
            "banques de motifs forment un projet ; l'appareil stocke 16 projets.",
        controls = listOf(
            cmd("Pads [1] à [16]", "Un échantillon ou un motif par pad.", DONNEES),
            cmd("Banques A à J", "10 banques de 16.", DONNEES),
        ),
        steps = listOf(
            "Compte : combien d'échantillons par banque ? (16)",
            "Combien de banques d'échantillons ? (10, de A à J)",
            "Combien de projets peut-on stocker ? (16)",
        ),
        expected = "Tu sais où se trouve chaque son et chaque motif.",
        exerciseTitle = "Plan de ton projet",
        exercise = listOf(
            "Décide d'une banque par type de son : A kicks et caisses, B percussions, C basses, D boucles.",
        ),
        success = "Ton plan de banques est écrit avant de commencer à échantillonner.",
        mistakes = listOf(
            "Confondre banque d'échantillons et banque de motifs : il y a 10 banques de chaque.",
        ),
        quiz = listOf(
            qz("Échantillons par banque ?", listOf("8", "16"), 1,
                "Une banque = 16 échantillons.", DONNEES),
            qz("Nombre de banques d'échantillons ?", listOf("10 (A à J)", "4"), 0,
                "De A à J.", DONNEES),
            qz("Qu'est-ce qu'un motif ?", listOf("L'ordre de lecture des échantillons", "Un effet"), 0,
                "Il enregistre l'ordre dans lequel les échantillons sont lus.", DONNEES),
            qz("Nombre de projets ?", listOf("16", "99"), 0,
                "Le SP-404MKII stocke 16 projets.", DONNEES),
        ),
    )

    private val m1l3 = ctLesson(
        M1, 3, "Jouer les pads et changer de banque", listOf(MODE_SAMPLE, BANK_VOLUME), 10,
        objective = "Jouer les échantillons d'usine et passer d'une banque à l'autre.",
        explanation = "À l'allumage, les pads sont orange : mode échantillonnage, un pad joue un échantillon. Sinon, appuyer plusieurs fois sur " +
            "EXIT (si les pads clignotent en bleu, EXIT puis PATTERN SELECT). L'écran affiche projet, banque et échantillon, bus d'effet, " +
            "état de la vélocité fixe, alimentation et tempo de l'échantillon. Les boutons Bank [A/F] à [E/J] changent de banque ; un " +
            "nouvel appui bascule entre A et F (les banques F à J font clignoter le bouton). SHIFT + bouton de banque ouvre BANK VOLUME " +
            "(VALUE pour régler, EXIT pour sortir).",
        controls = listOf(
            cmd("Pads [1] à [16]", "Jouer.", MODE_SAMPLE),
            cmd("Bank [A/F] à [E/J]", "Banques ; second appui = F à J.", MODE_SAMPLE),
            cmd("SHIFT + Bank", "Volume de la banque.", BANK_VOLUME),
        ),
        steps = listOf(
            "Joue les 16 pads de la banque A.",
            "Appuie sur Bank [A/F] une seconde fois : banque F, le bouton clignote.",
            "SHIFT + Bank [A/F], tourne VALUE pour baisser la banque, puis EXIT.",
        ),
        expected = "Tu passes d'une banque à l'autre et équilibres leurs volumes.",
        exerciseTitle = "Tour des banques",
        exercise = listOf(
            "Écoute les 10 banques et note ce qu'elles contiennent.",
            "Égalise le volume de deux banques trop différentes.",
        ),
        success = "Tu sais dans quelle banque se trouve chaque type de son.",
        mistakes = listOf(
            "Chercher les banques F à J ailleurs : elles s'obtiennent par un second appui sur le même bouton.",
        ),
        quiz = listOf(
            qz("Couleur des pads en mode échantillonnage ?", listOf("Orange", "Violet"), 0,
                "Les pads s'allument en orange.", MODE_SAMPLE),
            qz("Comment aller en banque F ?", listOf("Second appui sur Bank [A/F]", "SHIFT + F"), 0,
                "Le bouton bascule entre A et F.", MODE_SAMPLE),
            qz("Régler le volume d'une banque ?", listOf("SHIFT + bouton de banque, puis VALUE", "VOLUME"), 0,
                "Écran BANK VOLUME.", BANK_VOLUME),
            qz("Bouton de banque qui clignote = …", listOf("Banque F à J", "Erreur"), 0,
                "A à E allumé, F à J clignotant.", MODE_SAMPLE),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 2 — Jouer les échantillons
    // ---------------------------------------------------------------------
    private const val M2 = "sp4-m2"

    private val m2l1 = ctLesson(
        M2, 1, "GATE et LOOP", listOf(GATE, LOOP), 15,
        objective = "Choisir si un échantillon joue tant que le pad est tenu, ou en boucle.",
        explanation = "GATE allumé : l'échantillon ne joue que pendant l'appui. GATE éteint : il démarre à chaque appui. SHIFT + GATE applique " +
            "le réglage à toute la banque. LOOP allumé : un appui lance la boucle, l'appui suivant l'arrête. REVERSE en plus = boucle à " +
            "l'envers ; SHIFT + LOOP (LOOP clignote) = aller-retour. HOLD + pad relance une boucle depuis le début. Pour arrêter des " +
            "échantillons sans gate : EXIT + pads ; pour garder joués ceux avec gate : HOLD + pads.",
        controls = listOf(
            cmd("GATE", "Lecture pendant l'appui.", GATE),
            cmd("LOOP", "Lecture en boucle.", LOOP),
            cmd("SHIFT + LOOP", "Boucle aller-retour.", LOOP),
            cmd("HOLD + pad", "Relancer / maintenir.", GATE),
        ),
        steps = listOf(
            "Choisis une boucle de batterie, allume LOOP et appuie sur son pad : elle tourne.",
            "Appuie à nouveau sur le pad : elle s'arrête.",
            "Sur un son court, allume GATE : il ne joue que pendant l'appui.",
            "Essaie SHIFT + LOOP pour une boucle aller-retour.",
        ),
        expected = "Une boucle qui tourne et un son qui ne joue que sous le doigt.",
        exerciseTitle = "Boucle et coups",
        exercise = listOf(
            "Lance une boucle de batterie en LOOP.",
            "Par-dessus, joue des coups de percussion en GATE.",
        ),
        success = "La boucle tourne pendant que tu joues les coups.",
        style = "Tribe : une boucle qui tourne et des percussions jouées à la main.",
        mistakes = listOf(
            "Appuyer deux fois sur un pad en LOOP en voulant relancer : le second appui l'arrête (utilise HOLD + pad).",
        ),
        quiz = listOf(
            qz("GATE allumé…", listOf("Joue tant que le pad est enfoncé", "Joue en boucle"), 0,
                "Gate replay.", GATE),
            qz("LOOP allumé, second appui sur le pad…", listOf("Arrête la boucle", "Relance du début"), 0,
                "Lecture et arrêt alternés.", LOOP),
            qz("Boucle aller-retour ?", listOf("SHIFT + LOOP", "SHIFT + GATE"), 0,
                "Le bouton LOOP clignote.", LOOP),
            qz("Relancer une boucle du début sans l'arrêter ?", listOf("HOLD + pad", "EXIT"), 0,
                "Retrigger.", LOOP),
        ),
    )

    private val m2l2 = ctLesson(
        M2, 2, "REVERSE et ROLL", listOf(REVERSE, ROLL), 15,
        objective = "Jouer un échantillon à l'envers et en roulement rythmé.",
        explanation = "REVERSE : choisir le pad puis allumer REVERSE ; un appui pendant la lecture inverse le son en cours. ROLL : maintenir " +
            "ROLL et appuyer sur un pad le répète à intervalle régulier (jusqu'à 4 échantillons en même temps). Pendant la lecture, ROLL " +
            "active le roulement au relâchement du bouton ; un nouvel appui l'arrête. SHIFT + ROLL fait défiler l'intervalle : 1/4, 1/8, " +
            "1/16, 1/32, 1/64.",
        controls = listOf(
            cmd("REVERSE", "Lecture inversée.", REVERSE),
            cmd("ROLL + pad", "Roulement.", ROLL),
            cmd("SHIFT + ROLL", "Intervalle 1/4 à 1/64.", ROLL),
        ),
        steps = listOf(
            "Sélectionne un pad de caisse claire et allume REVERSE : écoute à l'envers.",
            "Éteins REVERSE, règle ROLL sur 1/16 avec SHIFT + ROLL.",
            "Maintiens ROLL et appuie sur la caisse claire : roulement.",
            "Passe à 1/32 pour un roulement plus serré.",
        ),
        expected = "Un roulement en doubles croches, puis plus serré.",
        exerciseTitle = "Roulement de relance",
        exercise = listOf(
            "Pendant une boucle, fais un roulement de caisse claire sur la dernière mesure.",
            "Accélère de 1/16 à 1/32 au milieu.",
        ),
        success = "Le roulement relance la boucle au bon moment.",
        style = "Tribecore : roulements qui accélèrent avant le retour.",
        mistakes = listOf(
            "Croire que ROLL démarre à l'appui pendant la lecture : il démarre au relâchement du bouton.",
        ),
        quiz = listOf(
            qz("Intervalles de ROLL ?", listOf("1/4, 1/8, 1/16, 1/32, 1/64", "1/2 seulement"), 0,
                "SHIFT + ROLL les fait défiler.", ROLL),
            qz("Combien d'échantillons en roulement à la fois ?", listOf("Jusqu'à 4", "16"), 0,
                "Jusqu'à quatre.", ROLL),
            qz("REVERSE pendant la lecture…", listOf("Inverse le son en cours", "Arrête tout"), 0,
                "L'échantillon est lu en sens inverse.", REVERSE),
            qz("Pendant la lecture, le roulement commence…", listOf("Au relâchement de ROLL", "À l'appui de ROLL"), 0,
                "Il commence quand on retire le doigt.", ROLL),
        ),
    )

    private val m2l3 = ctLesson(
        M2, 3, "Vélocité fixe, mute, groupes et arrêt", listOf(FIXE, PAD_MUTE, MUTE_GROUP, STOP), 15,
        objective = "Maîtriser le volume des coups, couper des pads et tout arrêter.",
        explanation = "SHIFT + pad [1] : FIXED VELOCITY ON (vélocité fixée à 127) ; refaire pour OFF. PAD MUTE : SHIFT + REVERSE + pad coupe " +
            "un échantillon (pad rouge) ; même geste pour rétablir. MUTE GROUP : SHIFT + pad [8], VALUE pour choisir le groupe (A à J), " +
            "pads à inclure, EXIT ; dans un groupe, seul le dernier pad joué sonne (charley ouvert/fermé). STOP : quatre appuis rapides " +
            "sur EXIT, ou SHIFT + EXIT. PAUSE : SHIFT + HOLD, refaire pour reprendre.",
        controls = listOf(
            cmd("SHIFT + pad [1]", "Vélocité fixe.", FIXE),
            cmd("SHIFT + REVERSE + pad", "Couper un pad.", PAD_MUTE),
            cmd("SHIFT + pad [8]", "Groupes de mute.", MUTE_GROUP),
            cmd("EXIT ×4 / SHIFT + HOLD", "Stop / pause.", STOP),
        ),
        steps = listOf(
            "Active FIXED VELOCITY avec SHIFT + pad [1].",
            "Mets charley fermé et ouvert dans le groupe A (SHIFT + pad [8]).",
            "Coupe une boucle avec SHIFT + REVERSE + pad, puis rétablis-la.",
            "Arrête tout avec quatre appuis rapides sur EXIT.",
        ),
        expected = "Les charleys se coupent entre eux et tu arrêtes tout d'un geste.",
        exerciseTitle = "Break d'un geste",
        exercise = listOf(
            "Pendant une boucle, coupe le pad de kick (PAD MUTE) sur 4 mesures, puis rétablis-le.",
        ),
        success = "Le kick revient pile sur le temps.",
        style = "Hardtek : couper le kick puis le relancer, le geste de base du live.",
        mistakes = listOf(
            "Oublier que FIXED VELOCITY rend tous les coups identiques : tu perds les nuances de frappe.",
        ),
        quiz = listOf(
            qz("Vélocité fixée à… ?", listOf("127", "64"), 0,
                "FIXED VELOCITY ON = 127.", FIXE),
            qz("Couper un pad ?", listOf("SHIFT + REVERSE + pad", "EXIT + pad"), 0,
                "Le pad s'allume en rouge.", PAD_MUTE),
            qz("Dans un groupe de mute…", listOf("Seul le dernier pad joué sonne", "Tous sonnent"), 0,
                "Les sons du groupe ne se superposent pas.", MUTE_GROUP),
            qz("Tout arrêter ?", listOf("Quatre appuis rapides sur EXIT", "POWER"), 0,
                "Ou SHIFT + EXIT.", STOP),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 3 — Les effets
    // ---------------------------------------------------------------------
    private const val M3 = "sp4-m3"

    private val m3l1 = ctLesson(
        M3, 1, "Boutons d'effet et molettes CTRL", listOf(EFFET_BOUTONS, EDIT_EFFET), 15,
        objective = "Activer un effet et le régler en jouant.",
        explanation = "Boutons d'effet : FILTER+DRIVE, RESONATOR, DELAY, ISOLATOR, DJFX LOOPER, MFX. On en allume un, on joue des pads, et " +
            "CTRL 1 à 3 règlent ses paramètres principaux (pour FILTER+DRIVE : cutoff, résonance, drive). En maintenant VALUE enfoncé et " +
            "en tournant les CTRL, on règle les sous-paramètres. L'écran d'édition disparaît après quelques secondes ; REMAIN + bouton " +
            "d'effet le verrouille (EXIT ou REMAIN pour sortir).",
        controls = listOf(
            cmd("FILTER+DRIVE … MFX", "Boutons d'effet.", EFFET_BOUTONS),
            cmd("CTRL 1, 2, 3", "Paramètres principaux.", EFFET_BOUTONS),
            cmd("VALUE enfoncé + CTRL", "Sous-paramètres.", EDIT_EFFET),
            cmd("REMAIN + bouton d'effet", "Verrouiller l'écran.", EDIT_EFFET),
        ),
        steps = listOf(
            "Lance une boucle et allume FILTER+DRIVE.",
            "Ferme le filtre avec CTRL 1, monte la résonance avec CTRL 2.",
            "Ajoute du drive avec CTRL 3.",
            "Verrouille l'écran avec REMAIN + FILTER+DRIVE, puis sors avec EXIT.",
        ),
        expected = "La boucle est filtrée et saturée à ta main.",
        exerciseTitle = "Filtre de break",
        exercise = listOf(
            "Ferme le filtre sur 4 mesures puis rouvre-le d'un coup.",
        ),
        success = "Le retour du filtre ouvert relance la boucle.",
        style = "Hardtek / tribe : filtre fermé puis retour brutal.",
        mistakes = listOf(
            "Chercher les sous-paramètres sans maintenir VALUE.",
        ),
        quiz = listOf(
            qz("Paramètres de FILTER+DRIVE sur CTRL 1 à 3 ?", listOf("Cutoff, résonance, drive", "Temps, feedback, mix"), 0,
                "Affichés à l'écran.", EFFET_BOUTONS),
            qz("Sous-paramètres ?", listOf("VALUE enfoncé + CTRL", "SHIFT + pad"), 0,
                "Maintenir VALUE et tourner.", EDIT_EFFET),
            qz("Garder l'écran d'édition ?", listOf("REMAIN + bouton d'effet", "HOLD"), 0,
                "Il ne revient plus à l'écran précédent.", EDIT_EFFET),
            qz("Sortir du verrouillage ?", listOf("EXIT ou REMAIN", "POWER"), 0,
                "Pour terminer le réglage.", EDIT_EFFET),
        ),
    )

    private val m3l2 = ctLesson(
        M3, 2, "BUS 1, BUS 2 et envoi des pads", listOf(BUS, ENVOI), 15,
        objective = "Choisir quels pads passent dans quel effet.",
        explanation = "Les effets sont placés sur des bus. BUS 1 et BUS 2 traitent les échantillons choisis ; BUS 3 et 4 traitent surtout la " +
            "sortie générale. BUS FX allumé orange = BUS 1, clignotant = BUS 2 ; on appuie ensuite sur un bouton d'effet pour l'affecter. " +
            "REMAIN + pad envoie ce pad vers le bus choisi ; la couleur du pad montre son bus : orange = BUS 1, vert = BUS 2, blanc = sans " +
            "effet (DRY). MFX : maintenir MFX et tourner VALUE ou CTRL 3 pour choisir l'effet, puis relâcher.",
        controls = listOf(
            cmd("BUS FX", "Choisir BUS 1 / BUS 2.", BUS),
            cmd("REMAIN + pad", "Envoyer un pad vers le bus.", ENVOI),
            cmd("MFX + VALUE / CTRL 3", "Choisir un MFX.", ENVOI),
        ),
        steps = listOf(
            "Appuie sur BUS FX jusqu'à BUS 2 (clignotant), puis sur DELAY.",
            "REMAIN + pad de caisse claire : il passe vert.",
            "Joue : seule la caisse claire a du délai.",
            "Avec REMAIN, appuie plusieurs fois sur le kick pour qu'il passe blanc (sans effet).",
        ),
        expected = "Le délai touche la caisse claire mais pas le kick.",
        exerciseTitle = "Kick sec, percussions traitées",
        exercise = listOf(
            "Mets le kick en blanc (sans effet), les percussions sur BUS 1 avec un filtre.",
        ),
        success = "Le filtre agit sur les percussions sans toucher au kick.",
        style = "Hardtek : garder le kick sec et traiter le reste.",
        mistakes = listOf(
            "Oublier que BUS 1 et BUS 2 reviennent à leurs réglages par défaut à l'extinction.",
        ),
        quiz = listOf(
            qz("Pad vert = …", listOf("BUS 2", "BUS 1"), 0,
                "Orange = BUS 1, vert = BUS 2, blanc = sans effet.", ENVOI),
            qz("BUS FX clignotant = …", listOf("BUS 2", "BUS 1"), 0,
                "Allumé = BUS 1.", BUS),
            qz("Envoyer un pad vers un bus ?", listOf("REMAIN + pad", "SHIFT + pad"), 0,
                "Le pad change de couleur.", ENVOI),
            qz("Rôle principal de BUS 3 et 4 ?", listOf("Effets sur la sortie générale", "Échantillonner"), 0,
                "Ils traitent surtout la sortie globale.", BUS),
        ),
    )

    private val m3l3 = ctLesson(
        M3, 3, "Effet sous le doigt et BUS MUTE", listOf(CAPTURE, BUS_MUTE, EDIT_EFFET), 15,
        objective = "Jouer un effet en rythme et ne laisser que les queues d'effet.",
        explanation = "Capture d'effet : en maintenant VALUE enfoncé, appuyer sur un bouton d'effet ; l'effet n'agit que pendant l'appui. " +
            "BUS MUTE : SHIFT + BUS FX coupe le son envoyé aux bus (échantillons et entrée) et ne laisse que l'effet, comme les queues de " +
            "réverbération ou de délai, pour créer une pause ; refaire SHIFT + BUS FX pour rétablir. Les paramètres principaux des BUS 1 " +
            "et 2 ne sont pas sauvegardés à l'extinction ; ceux des BUS 3 et 4 le sont.",
        controls = listOf(
            cmd("VALUE + bouton d'effet", "Effet pendant l'appui.", CAPTURE),
            cmd("SHIFT + BUS FX", "BUS MUTE.", BUS_MUTE),
        ),
        steps = listOf(
            "Mets un délai sur le bus de ta boucle.",
            "Maintiens VALUE et appuie en rythme sur DELAY : le délai n'agit que sous le doigt.",
            "Active BUS MUTE avec SHIFT + BUS FX : seules les répétitions restent.",
            "Rétablis avec SHIFT + BUS FX.",
        ),
        expected = "Une pause où seules les répétitions du délai sonnent.",
        exerciseTitle = "Coupure d'écho",
        exercise = listOf(
            "Juste avant un break, active BUS MUTE pendant une mesure.",
        ),
        success = "L'écho remplit le silence et la boucle revient proprement.",
        style = "Tribe : l'écho sur la dernière frappe sert de transition.",
        mistakes = listOf(
            "Compter retrouver ses réglages BUS 1/2 après extinction : ils ne sont pas enregistrés.",
        ),
        quiz = listOf(
            qz("Effet seulement pendant l'appui ?", listOf("VALUE + bouton d'effet", "REMAIN + bouton"), 0,
                "Capture d'effet.", CAPTURE),
            qz("BUS MUTE ?", listOf("SHIFT + BUS FX", "SHIFT + EXIT"), 0,
                "Ne laisse que le son d'effet.", BUS_MUTE),
            qz("Paramètres BUS 1/2 à l'extinction ?", listOf("Remis par défaut", "Sauvegardés"), 0,
                "Seuls INPUT FX et BUS 3/4 sont conservés.", EDIT_EFFET),
            qz("À quoi sert BUS MUTE ?", listOf("Créer une pause avec les queues d'effet", "Accorder"), 0,
                "Réverbération ou délai seuls.", BUS_MUTE),
        ),
    )

    // ---------------------------------------------------------------------
    // Module 4 — Échantillonner
    // ---------------------------------------------------------------------
    private const val M4 = "sp4-m4"

    private val m4l1 = ctLesson(
        M4, 1, "Enregistrer un échantillon", listOf(REGLAGE_REC, ECHANTILLONNER), 20,
        objective = "Échantillonner un synthé ou un micro sur un pad.",
        explanation = "REC met l'appareil en attente ; RECORD SETTING ouvre les réglages : CTRL 1 tempo (40,0 à 200,0 BPM), CTRL 2 routage " +
            "(Mix = son de l'appareil + entrée ; ExtIn = entrée seule), CTRL 3 niveau (0–127), SHIFT + CTRL 3 panoramique. Les pads vides " +
            "clignotent rouge : on en choisit un, puis REC lance l'enregistrement ; un nouvel appui sur le pad ou sur REC l'arrête (EXIT " +
            "annule). Décompte : SHIFT + pad [10] (1 mesure, 2 mesures, attente du signal, désactivé). Métronome : SHIFT + pad [9].",
        controls = listOf(
            cmd("REC", "Attente d'échantillonnage.", ECHANTILLONNER),
            cmd("RECORD SETTING", "Tempo, routage, niveau.", REGLAGE_REC),
            cmd("SHIFT + pad [10]", "Décompte.", ECHANTILLONNER),
            cmd("SHIFT + pad [9]", "Métronome.", ECHANTILLONNER),
        ),
        steps = listOf(
            "Branche un synthé sur LINE IN et appuie sur REC.",
            "RECORD SETTING : routage ExtIn, règle le niveau avec CTRL 3.",
            "Choisis un pad rouge clignotant, appuie sur REC et joue.",
            "Arrête en appuyant sur le pad.",
        ),
        expected = "Ton son est enregistré sur un pad et se rejoue.",
        exerciseTitle = "Stab de synthé",
        exercise = listOf(
            "Échantillonne un accord court de ton synthé.",
            "Rejoue-le en rythme sur les pads.",
        ),
        success = "Le stab sonne net, sans souffle au début.",
        mistakes = listOf(
            "Laisser le routage sur Mix en voulant seulement l'entrée : le son de l'appareil est enregistré aussi.",
            "Appuyer sur EXIT pour finir : EXIT annule l'enregistrement.",
        ),
        quiz = listOf(
            qz("Routage pour l'entrée seule ?", listOf("ExtIn", "Mix"), 0,
                "Mix inclut le son de l'appareil.", REGLAGE_REC),
            qz("Pads vides en attente d'enregistrement ?", listOf("Clignotent rouge", "Bleu fixe"), 0,
                "On choisit l'un d'eux.", ECHANTILLONNER),
            qz("Arrêter l'enregistrement ?", listOf("Le pad ou REC", "EXIT"), 0,
                "EXIT annule.", ECHANTILLONNER),
            qz("Décompte ?", listOf("SHIFT + pad [10]", "SHIFT + pad [1]"), 0,
                "1 ou 2 mesures, attente ou désactivé.", ECHANTILLONNER),
        ),
    )

    private val m4l2 = ctLesson(
        M4, 2, "Rééchantillonner", listOf(RESAMPLE), 15,
        objective = "Figer un son avec ses effets ou plusieurs pads joués ensemble.",
        explanation = "RESAMPLE, puis RECORD SETTING : routage sur Mix (ExtIn n'enregistre que l'entrée externe). EXIT ou RESAMPLE pour " +
            "revenir. Choisir un pad vide rouge (« Press Pad to START ») ; l'enregistrement démarre dès qu'on joue un échantillon. On peut " +
            "jouer plusieurs pads et des effets. Fin : le pad d'enregistrement ou REC. Avec PATTERN SELECT allumé, on rééchantillonne un " +
            "motif de la même façon.",
        controls = listOf(
            cmd("RESAMPLE", "Rééchantillonnage.", RESAMPLE),
            cmd("RECORD SETTING > CTRL 2", "Routage Mix.", RESAMPLE),
        ),
        steps = listOf(
            "Mets un FILTER+DRIVE sur une boucle.",
            "RESAMPLE, RECORD SETTING, routage Mix, puis EXIT.",
            "Choisis un pad vide, puis joue la boucle en bougeant le filtre.",
            "Arrête avec REC : la boucle filtrée est sur le nouveau pad.",
        ),
        expected = "Un nouvel échantillon qui contient l'effet.",
        exerciseTitle = "Boucle détruite",
        exercise = listOf(
            "Rééchantillonne une boucle avec un effet fort, puis rééchantillonne le résultat avec un autre effet.",
        ),
        success = "Une boucle méconnaissable, prête à jouer.",
        style = "Mentalcore : rééchantillonner plusieurs fois pour des sons dégradés.",
        mistakes = listOf(
            "Routage ExtIn en rééchantillonnage : le son de l'appareil n'est pas enregistré.",
        ),
        quiz = listOf(
            qz("Routage pour rééchantillonner l'appareil ?", listOf("Mix", "ExtIn"), 0,
                "ExtIn ne prend que l'entrée externe.", RESAMPLE),
            qz("Quand commence l'enregistrement ?", listOf("Dès qu'on joue un échantillon", "Après 4 mesures"), 0,
                "« Press Pad to START ».", RESAMPLE),
            qz("Rééchantillonner un motif ?", listOf("Avec PATTERN SELECT allumé", "Impossible"), 0,
                "Même méthode.", RESAMPLE),
            qz("Arrêter ?", listOf("Pad d'enregistrement ou REC", "SHIFT + HOLD"), 0,
                "L'échantillon est enregistré dans le pad.", RESAMPLE),
        ),
    )

    private val m4l3 = ctLesson(
        M4, 3, "Échantillonner après coup (SKIP BACK)", listOf(SKIP_BACK), 10,
        objective = "Récupérer une bonne phrase jouée sans avoir lancé l'enregistrement.",
        explanation = "Le SP-404MKII garde en mémoire les 25 dernières secondes (par défaut ; jusqu'à 40 s avec le réglage SBS Time sur long). " +
            "Quand il détecte un son, MARK clignote. Appuyer sur MARK affiche la forme d'onde (« SKIP BACK… »), puis REC (« Select Pad to " +
            "Save ») et choisir un pad vide. EXIT ou l'extinction efface cette mémoire. Si MARK ne clignote pas, rien n'a été enregistré.",
        controls = listOf(
            cmd("MARK", "Afficher la mémoire de saut.", SKIP_BACK),
            cmd("REC + pad", "Sauvegarder sur un pad.", SKIP_BACK),
        ),
        steps = listOf(
            "Joue une phrase sur les pads ou ton synthé.",
            "Quand MARK clignote, appuie sur MARK.",
            "Appuie sur REC, puis sur un pad vide rouge.",
        ),
        expected = "La phrase que tu viens de jouer est sur un pad.",
        exerciseTitle = "Impro sauvée",
        exercise = listOf(
            "Improvise 20 secondes, puis sauvegarde le passage avec SKIP BACK.",
        ),
        success = "Ton improvisation est rejouable depuis un pad.",
        mistakes = listOf(
            "Appuyer sur EXIT avant d'avoir choisi un pad : la mémoire est perdue.",
        ),
        quiz = listOf(
            qz("Durée par défaut de la mémoire ?", listOf("25 secondes", "5 minutes"), 0,
                "Jusqu'à 40 s avec SBS Time long.", SKIP_BACK),
            qz("Signe qu'il y a quelque chose à sauver ?", listOf("MARK clignote", "REC clignote"), 0,
                "Sinon « No SKIP BACK Trigger Data ».", SKIP_BACK),
            qz("Ordre des boutons ?", listOf("MARK, REC, pad", "REC, MARK, pad"), 0,
                "MARK puis REC puis pad.", SKIP_BACK),
            qz("Que fait EXIT avant de choisir un pad ?", listOf("Efface la mémoire", "Sauvegarde"), 0,
                "Le son est perdu.", SKIP_BACK),
        ),
    )

    val modules: List<CourseModule> = listOf(
        CourseModule(
            M1, 1, "Prise en main",
            "Branchements, alimentation, organisation des données, pads et banques.",
            "Installer le SP-404MKII et jouer ses banques.",
            "Des banques rangées = des sons trouvés vite en live.",
            listOf(m1l1, m1l2, m1l3),
        ),
        CourseModule(
            M2, 2, "Jouer les échantillons",
            "GATE, LOOP, REVERSE, ROLL, vélocité fixe, mute, groupes, stop.",
            "Jouer les pads comme un instrument.",
            "Roulements et coupures de kick font le live tribe et hardtek.",
            listOf(m2l1, m2l2, m2l3),
        ),
        CourseModule(
            M3, 3, "Les effets",
            "Boutons d'effet, CTRL, bus, envoi des pads, capture, BUS MUTE.",
            "Traiter le son en direct.",
            "Filtre, délai et queues d'effet pour les transitions.",
            listOf(m3l1, m3l2, m3l3),
        ),
        CourseModule(
            M4, 4, "Échantillonner",
            "Enregistrement, rééchantillonnage, SKIP BACK.",
            "Créer tes propres échantillons.",
            "Rééchantillonner et dégrader les sons, l'esprit free party.",
            listOf(m4l1, m4l2, m4l3),
        ),
    )
}
