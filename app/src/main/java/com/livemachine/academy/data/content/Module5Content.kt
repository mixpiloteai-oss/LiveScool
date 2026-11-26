package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.ContentStatus
import com.livemachine.academy.data.model.Exercise
import com.livemachine.academy.data.model.Lesson
import com.livemachine.academy.data.model.MachineControl
import com.livemachine.academy.data.model.ManualDoc.PARAMETER_GUIDE
import com.livemachine.academy.data.model.ManualRef
import com.livemachine.academy.data.model.QuizQuestion

/**
 * Module 5 — Filtres et effets.
 *
 * Sources : manuel d'utilisation (partie française) et electribe sampler Parameter Guide.
 * Les documents donnent la liste des types d'effets mais ne décrivent pas chaque type :
 * les leçons font écouter et comparer les types, sans leur prêter de comportement non documenté.
 */
object Module5Content {

    private const val M = "m5"

    // Manuel d'utilisation
    private val OM_FILTER = ManualRef("Description par section › Section d'édition › Filter", "23")
    private val OM_SAMPLER_FILTRES = ManualRef("Fonctions de l'electribe sampler › Boutons Filter LPF/HPF/BPF", "34")
    private val OM_IFX = ManualRef("Description par section › Section d'édition › Insert Fx", "23–24")
    private val OM_MFX_SEND = ManualRef("Description par section › Section d'édition › Amp/EG › MFX Send", "23")
    private val OM_PAD_TACTILE = ManualRef("Description par section › Section du pad tactile", "22")
    private val OM_EFFETS_MAITRES = ManualRef("Production de musique › 4. Utilisation des effets maîtres", "29")
    private val OM_MOTIF = ManualRef("Pour produire du son › 3. Lecture d'un motif", "27")
    private val OM_SPECS = ManualRef("Fiche technique", "38")

    // Guide des paramètres
    private val PG_FILTER_LIST = ManualRef("9. Appendix › Filter Type List", "16", PARAMETER_GUIDE)
    private val PG_IFX_LIST = ManualRef("9. Appendix › IFX Type List", "17", PARAMETER_GUIDE)
    private val PG_MFX_LIST = ManualRef("9. Appendix › MFX Type List", "17", PARAMETER_GUIDE)
    private val PG_PATTERN = ManualRef("1. PATTERN PARAMETERS", "3", PARAMETER_GUIDE)
    private val PG_PART = ManualRef("2. PART PARAMETERS › VOICE ASSIGN", "4", PARAMETER_GUIDE)
    private val PG_SHORTCUTS = ManualRef("9. Appendix › Shortcut list", "11", PARAMETER_GUIDE)
    private val PG_STEP_EDIT = ManualRef("3. STEP EDIT", "5", PARAMETER_GUIDE)

    private fun q(id: String, prompt: String, options: List<String>, correct: Int, explanation: String, ref: ManualRef) =
        QuizQuestion(id, prompt, options, correct, explanation, ref)

    // ---------------------------------------------------------------------
    // 5.1 Les trois filtres du sampler
    // ---------------------------------------------------------------------
    private val lesson1 = Lesson(
        id = "$M-l1",
        moduleId = M,
        order = 1,
        title = "Les trois filtres du sampler",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_FILTER, OM_SAMPLER_FILTRES, PG_FILTER_LIST),
        durationMinutes = 10,
        objective = "Activer et couper les filtres passe-bas, passe-haut et passe-bande d'une part, et entendre ce que fait chacun.",
        explanation = "Le filtre modifie le timbre en atténuant ou en accentuant certaines parties du spectre de l'oscillateur. " +
            "La section Filter a trois boutons : LPF (passe-bas), HPF (passe-haut) et BPF (passe-bande). Sur l'electribe, " +
            "le manuel explique qu'on appuie plusieurs fois pour changer de type de filtre. Sur l'electribe sampler, c'est " +
            "différent : chaque bouton active ou coupe son filtre « electribe » (electribe LPF, electribe HPF, electribe BPF), " +
            "et les trois s'activent individuellement. Grossièrement : le passe-bas garde le grave, le passe-haut garde " +
            "l'aigu, le passe-bande garde une zone autour de la fréquence de coupure.",
        controls = listOf(
            MachineControl("LPF", "Active ou coupe l'electribe LPF (passe-bas).", OM_SAMPLER_FILTRES),
            MachineControl("HPF", "Active ou coupe l'electribe HPF (passe-haut).", OM_SAMPLER_FILTRES),
            MachineControl("BPF", "Active ou coupe l'electribe BPF (passe-bande).", OM_SAMPLER_FILTRES),
            MachineControl("Filter (cutoff)", "Fréquence de coupure du filtre.", OM_FILTER),
        ),
        steps = listOf(
            "Lance un pattern, sélectionne ta part de basse (forme d'onde SAW, leçon 3.2).",
            "Appuie sur LPF pour l'activer, puis tourne la commande Filter dans un sens puis dans l'autre : le son s'assombrit ou s'éclaircit.",
            "Coupe LPF et active HPF : tourne Filter, le grave disparaît progressivement.",
            "Coupe HPF et active BPF : tourne Filter, seule une zone du son reste.",
            "Essaie d'activer deux filtres à la fois et écoute le résultat.",
            "Garde le réglage qui te plaît et sauvegarde avec Write.",
        ),
        expectedResult = "Tu reconnais à l'oreille le passe-bas, le passe-haut et le passe-bande sur la même part.",
        exercise = Exercise(
            id = "$M-l1-ex",
            lessonId = "$M-l1",
            title = "Nettoyer le bas du spectre",
            instructions = listOf(
                "Sur tes parts de charley et de percussion, active HPF et monte Filter jusqu'à ce que leur grave disparaisse sans les rendre maigres.",
                "Laisse le kick et la basse sans HPF.",
                "Compare le mix avant et après.",
            ),
            successCriteria = "Le kick et la basse ressortent mieux, sans que les percussions perdent leur caractère.",
            styleHint = "Hardtek : un bas du spectre réservé au kick et à la basse, c'est la base d'un mix qui tape en sound system.",
        ),
        commonMistakes = listOf(
            "Appuyer plusieurs fois sur LPF en cherchant d'autres types : sur le sampler, chaque bouton active ou coupe seulement son filtre electribe (manuel p. 34, guide p. 16).",
            "Se fier aux explications du manuel sur les types de filtres : elles concernent l'electribe, pas le sampler (manuel p. 23 et 34).",
            "Tourner Filter sans filtre actif et ne rien entendre changer : allume d'abord LPF, HPF ou BPF.",
        ),
        quiz = listOf(
            q("$M-l1-q1", "Sur l'electribe sampler, que fait une pression sur le bouton LPF ?",
                listOf("Elle fait défiler plusieurs types de passe-bas", "Elle active ou coupe l'electribe LPF", "Elle règle la résonance"), 1,
                "Sur le sampler, LPF, HPF et BPF s'activent ou se coupent individuellement.", OM_SAMPLER_FILTRES),
            q("$M-l1-q2", "Quels choix la Filter Type List donne-t-elle pour le HPF ?",
                listOf("OFF et electribe HPF", "6 types de HPF", "HPF 12 dB et 24 dB"), 0,
                "Pour chaque filtre, la liste donne OFF ou le filtre electribe.", PG_FILTER_LIST),
            q("$M-l1-q3", "Que règle la commande Filter ?",
                listOf("La résonance", "La fréquence de coupure", "Le volume"), 1, "La commande Filter règle la fréquence de coupure du filtre.", OM_FILTER),
        ),
    )

    // ---------------------------------------------------------------------
    // 5.2 Coupure, résonance et EG Int
    // ---------------------------------------------------------------------
    private val lesson2 = Lesson(
        id = "$M-l2",
        moduleId = M,
        order = 2,
        title = "Coupure, résonance et EG Int",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_FILTER),
        durationMinutes = 10,
        objective = "Faire « chanter » un filtre avec la résonance, et le faire bouger à chaque note avec l'enveloppe.",
        explanation = "Trois commandes travaillent ensemble. Filter règle la fréquence de coupure. Resonance accentue la zone " +
            "autour de cette fréquence : poussée, elle donne le son nasal et sifflant typique des lignes acides. EG Int " +
            "règle l'intensité avec laquelle l'enveloppe de la section Amp/EG fait bouger la fréquence de coupure : à " +
            "chaque note, le filtre s'ouvre puis se referme suivant cette enveloppe, réglée avec Attack et Decay/Release.",
        controls = listOf(
            MachineControl("Filter", "Fréquence de coupure.", OM_FILTER),
            MachineControl("Resonance", "Résonance du filtre.", OM_FILTER),
            MachineControl("EG Int", "Intensité du mouvement de la coupure produit par l'enveloppe Amp/EG.", OM_FILTER),
            MachineControl("Attack / Decay/Release", "Forme de l'enveloppe qui pilote aussi la coupure via EG Int.", OM_FILTER),
        ),
        steps = listOf(
            "Sur ta part de basse, active LPF et baisse Filter assez bas.",
            "Monte Resonance progressivement : le son devient plus nasal autour de la coupure.",
            "Balaye lentement Filter avec la résonance haute : écoute le filtre « chanter ».",
            "Laisse Filter assez bas et monte EG Int : chaque note ouvre le filtre puis le referme.",
            "Raccourcis Decay/Release : l'ouverture devient un « clic » bref ; allonge-le : elle devient un « wouah ».",
            "Sauvegarde avec Write.",
        ),
        expectedResult = "Chaque note de basse a une attaque de filtre audible, plus ou moins brève selon Decay/Release.",
        exercise = Exercise(
            id = "$M-l2-ex",
            lessonId = "$M-l2",
            title = "Trois couleurs de filtre",
            instructions = listOf(
                "Trouve un réglage « sourd » (coupure basse, peu de résonance, EG Int faible).",
                "Un réglage « acide » (résonance haute, EG Int marqué, Decay/Release court).",
                "Un réglage « ouvert » (coupure haute). Note les positions des commandes pour chacun.",
            ),
            successCriteria = "Tu passes d'un réglage à l'autre en moins de 10 secondes, de mémoire.",
            styleHint = "Acidcore et mentalcore : le réglage « acide » est ta base ; tu l'animeras ensuite au Module 6.",
        ),
        commonMistakes = listOf(
            "Monter EG Int avec une coupure déjà ouverte : l'enveloppe a peu de place pour bouger ; baisse d'abord Filter.",
            "Pousser la résonance à fond sans baisser le Level de la part : le son peut devenir agressif dans le mix.",
            "Oublier que l'enveloppe qui pilote le filtre est celle de la section Amp/EG : Attack et Decay/Release changent aussi le mouvement du filtre (manuel p. 23).",
        ),
        quiz = listOf(
            q("$M-l2-q1", "Que règle EG Int ?",
                listOf("Le volume de l'enveloppe", "L'intensité du changement de fréquence de coupure produit par l'enveloppe", "La vitesse du LFO"), 1,
                "EG Int règle l'intensité avec laquelle l'enveloppe de la section Amp/EG fait varier la coupure.", OM_FILTER),
            q("$M-l2-q2", "Quelle enveloppe EG Int utilise-t-il ?",
                listOf("Celle de la section Amp/EG", "Une enveloppe propre au filtre", "Celle de l'effet maître"), 0,
                "L'enveloppe est celle définie dans la section Amp/EG.", OM_FILTER),
            q("$M-l2-q3", "Quelle commande règle la résonance ?",
                listOf("Resonance", "Edit", "Depth"), 0, "La commande Resonance règle la résonance du filtre.", OM_FILTER),
        ),
    )

    // ---------------------------------------------------------------------
    // 5.3 Atelier : la ligne acide
    // ---------------------------------------------------------------------
    private val lesson3 = Lesson(
        id = "$M-l3",
        moduleId = M,
        order = 3,
        title = "Atelier : la ligne acide",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_FILTER, PG_STEP_EDIT, PG_PART),
        durationMinutes = 20,
        objective = "Combiner séquence, filtre et enveloppe pour construire une ligne de basse acide complète.",
        explanation = "Une ligne acide vient autant de la séquence que du filtre. Côté séquence (Module 4) : des notes courtes " +
            "et d'autres liées par TIE (pas de relance de l'enveloppe si la note suivante est la même), des vélocités " +
            "différentes, et VOICE ASSIGN en Mono1 pour un jeu legato. Côté son : LPF actif, coupure basse, résonance " +
            "haute, EG Int marqué et Decay/Release court. Les accents viennent du mélange : un pas lié ne relance pas " +
            "l'enveloppe, donc pas de nouvelle ouverture du filtre, alors qu'un pas séparé la relance.",
        controls = listOf(
            MachineControl("LPF + Filter + Resonance", "Le cœur du son acide.", OM_FILTER),
            MachineControl("EG Int + Decay/Release", "L'ouverture du filtre à chaque note.", OM_FILTER),
            MachineControl("GATE TIME / TIE (STEP EDIT)", "Notes courtes ou liées.", PG_STEP_EDIT),
            MachineControl("VOICE ASSIGN : Mono1", "Jeu legato.", PG_PART),
        ),
        steps = listOf(
            "Pars de ta ligne de basse de la leçon 4.4, sur une forme d'onde SAW ou PULSE.",
            "Mets VOICE ASSIGN sur Mono1.",
            "Active LPF, coupure basse, Resonance haute, EG Int marqué, Decay/Release court.",
            "Dans STEP EDIT, mets GATE TIME sur TIE sur deux pas consécutifs qui portent la même note.",
            "Écoute : les pas liés glissent sans réouverture du filtre, les autres « claquent ».",
            "Pendant la lecture, tourne lentement Filter et Resonance à la main. Sauvegarde avec Write.",
        ),
        expectedResult = "Une ligne acide vivante, avec des notes qui claquent, d'autres qui glissent, et un filtre que tu fais évoluer à la main.",
        exercise = Exercise(
            id = "$M-l3-ex",
            lessonId = "$M-l3",
            title = "Ta première acid line",
            instructions = listOf(
                "16 pas, au moins 3 notes différentes, 2 liaisons TIE, des vélocités variées.",
                "Règle le filtre pour un son acide, puis joue la coupure à la main pendant 8 tours, en montée.",
                "Ajoute un peu de glide (leçon 3.3) si ça sert la ligne.",
            ),
            successCriteria = "La ligne tient seule sur 8 tours, et la montée de filtre crée une tension audible.",
            styleHint = "Acidcore : c'est la signature du style. Mentalcore : même principe, à tempo plus rapide et avec des montées plus longues.",
        ),
        commonMistakes = listOf(
            "Mettre TIE entre deux notes différentes en espérant une liaison sans relance : TIE ne l'évite que si la note suivante est la même (guide p. 5).",
            "Laisser VOICE ASSIGN en Poly : la ligne perd son jeu monophonique (guide p. 4).",
            "Tout régler au maximum : une ligne acide vit de contrastes entre pas forts et pas doux.",
        ),
        quiz = listOf(
            q("$M-l3-q1", "Pourquoi un pas lié par TIE n'ouvre-t-il pas à nouveau le filtre ?",
                listOf("Parce que TIE coupe le filtre", "Parce que l'enveloppe n'est pas relancée si la note suivante est la même", "Parce que TIE baisse la vélocité"), 1,
                "Avec TIE, oscillateur, enveloppe et modulation ne sont pas relancés si le pas suivant a la même note.", PG_STEP_EDIT),
            q("$M-l3-q2", "Quel réglage de VOICE ASSIGN convient au jeu legato ?",
                listOf("Mono1", "Poly1", "Poly2"), 0, "En Mono1, les notes suivantes ne relancent pas l'enveloppe tant que le premier pad est tenu.", PG_PART),
            q("$M-l3-q3", "Quel filtre utilise-t-on le plus souvent pour une ligne acide ?",
                listOf("LPF (passe-bas)", "HPF (passe-haut)", "Aucun"), 0, "Le passe-bas avec résonance haute est la base de la ligne acide.", OM_FILTER),
        ),
    )

    // ---------------------------------------------------------------------
    // 5.4 L'effet d'insertion
    // ---------------------------------------------------------------------
    private val lesson4 = Lesson(
        id = "$M-l4",
        moduleId = M,
        order = 4,
        title = "L'effet d'insertion",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_IFX, PG_IFX_LIST, PG_PART, OM_SPECS),
        durationMinutes = 15,
        objective = "Choisir et doser l'effet d'insertion d'une part, et le couper ou l'activer à la volée.",
        explanation = "Chaque part a son effet d'insertion (Insert FX). La commande Insert Fx choisit le type, la commande Edit " +
            "de la section règle son intensité (son rôle exact dépend du type), et le bouton IFX On l'active (allumé) ou le " +
            "coupe. La liste compte 38 types : Punch, Overdrive, Distortion, Decimator, Bit Crusher, Ring Modulator, " +
            "Sustainer, Limiter, quatre EQ (Low, Mid, High, Radio), Exciter, quatre filtres (Low Pass, High Pass, Band Plus, " +
            "Talk), des délais (Delay 1/4, 3/16, 1/8, 1/16, Roller 1/32, One Delay, Short Delay, Ring Delay 1 et 2), puis " +
            "Chorus, quatre flangers, trois phasers, Tremolo, Off Beater, Pumper, Repeater et Slicer. Deux points à retenir : en Poly1 et Poly2, les voix d'une part partagent le même effet d'insertion ; et " +
            "le nombre de voix disponibles dans un pattern (24 au maximum) dépend notamment de l'effet d'insertion choisi.",
        controls = listOf(
            MachineControl("Insert Fx", "Choisit le type d'effet d'insertion de la part.", OM_IFX),
            MachineControl("Edit (Insert Fx)", "Intensité de l'effet ; son rôle varie selon le type.", OM_IFX),
            MachineControl("IFX On", "Active (allumé) ou coupe l'effet d'insertion.", OM_IFX),
        ),
        steps = listOf(
            "Sélectionne la part du kick et tourne Insert Fx jusqu'à Punch, puis allume IFX On.",
            "Tourne Edit : écoute comment l'effet change avec son intensité.",
            "Passe à Overdrive puis à Distortion, en réajustant Edit à chaque fois.",
            "Sur une percussion, essaie un délai (Delay 1/8 par exemple).",
            "Pendant la lecture, coupe et rallume IFX On pour comparer.",
            "Garde un effet par part et sauvegarde avec Write.",
        ),
        expectedResult = "Ton kick est plus présent ou saturé, une percussion a un écho, et tu sais couper l'effet à la volée.",
        exercise = Exercise(
            id = "$M-l4-ex",
            lessonId = "$M-l4",
            title = "Le kick saturé",
            instructions = listOf(
                "Essaie sur ton kick les types Punch, Overdrive, Distortion et Bit Crusher, au même Level.",
                "Pour chacun, cherche le meilleur réglage de Edit, puis classe-les de « propre » à « destroy ».",
                "Garde celui qui sert ton style.",
            ),
            successCriteria = "Un classement des 4 types à l'oreille, et un kick saturé qui reste lisible.",
            styleHint = "Hardtek et tribecore : le kick saturé est la signature. Monte Edit jusqu'à ce que le kick soit gras, puis reviens un peu en arrière.",
        ),
        commonMistakes = listOf(
            "Tourner Insert Fx et Edit sans allumer IFX On : l'effet n'est pas actif (manuel p. 24).",
            "Mettre des effets lourds sur beaucoup de parts : le nombre de voix disponibles dépend de l'effet d'insertion choisi (manuel p. 38).",
            "Attendre un effet différent par voix sur une part Poly : toutes ses voix partagent le même effet d'insertion (guide p. 4).",
        ),
        quiz = listOf(
            q("$M-l4-q1", "Combien de types d'effet d'insertion la liste compte-t-elle ?",
                listOf("26", "32", "38"), 2, "L'IFX Type List va de 1 Punch à 38 Slicer.", PG_IFX_LIST),
            q("$M-l4-q2", "Quel bouton active ou coupe l'effet d'insertion ?",
                listOf("MFX Send", "IFX On", "Master Fx"), 1, "IFX On active ou coupe l'effet d'insertion.", OM_IFX),
            q("$M-l4-q3", "Que règle la commande Edit de la section Insert Fx ?",
                listOf("Le type d'effet", "L'intensité de l'effet, selon le type", "Le tempo"), 1,
                "Edit règle l'intensité de l'effet d'insertion ; son action varie selon le type.", OM_IFX),
            q("$M-l4-q4", "Qu'est-ce qui influence le nombre de voix disponibles dans un pattern ?",
                listOf("Le contraste de l'écran", "Le type d'oscillateur, de filtre ou d'effet d'insertion choisi", "Le numéro du pattern"), 1,
                "Le nombre de sons simultanés dépend du type d'oscillateur, de filtre ou de l'effet d'insertion.", OM_SPECS),
        ),
    )

    // ---------------------------------------------------------------------
    // 5.5 L'effet maître au pad tactile
    // ---------------------------------------------------------------------
    private val lesson5 = Lesson(
        id = "$M-l5",
        moduleId = M,
        order = 5,
        title = "L'effet maître au pad tactile",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_EFFETS_MAITRES, OM_PAD_TACTILE, OM_MFX_SEND, PG_PATTERN, PG_MFX_LIST, PG_SHORTCUTS, OM_MOTIF),
        durationMinutes = 15,
        objective = "Envoyer des parts dans l'effet maître et le jouer au pad tactile, avec ou sans maintien.",
        explanation = "Chaque pattern a un seul effet maître, commun à toutes les parts. Son type se choisit avec MFX TYPE " +
            "(01 à 32) ; Shift + Master Fx affiche directement cette page. Pour chaque part, le bouton MFX Send décide si " +
            "elle passe dans l'effet maître (allumé) ou non. Ensuite : Master Fx allumé, tu joues l'effet en touchant le pad " +
            "tactile. MFX Hold garde l'état de l'effet obtenu au pad tactile sans que tu gardes le doigt dessus ; son état " +
            "peut être mémorisé dans le pattern. La liste des 32 types comprend des délais (Mod, Tape, High Pass), des " +
            "réverbes (Hall, Room, Wet), Looper, Pitch Looper, Step Shifter, Slicer, Jag Filter, Grain Shifter, Vinyl " +
            "Break, quatre types qui pilotent le séquenceur (Seq Reverse, Seq Doubler, Odd Stepper, Even Stepper), des " +
            "filtres, Touch Wah, Tube EQ, Decimator, Distortion, Compressor, Limiter, Chorus, des flangers, des phasers et " +
            "Auto Pan.",
        controls = listOf(
            MachineControl("MFX TYPE (Shift + Master Fx)", "Type d'effet maître du pattern, 01 à 32.", PG_SHORTCUTS),
            MachineControl("MFX Send", "Allumé : la part passe dans l'effet maître.", OM_MFX_SEND),
            MachineControl("Master Fx", "Active la commande de l'effet maître au pad tactile.", OM_PAD_TACTILE),
            MachineControl("Pad tactile", "Joue l'effet maître.", OM_EFFETS_MAITRES),
            MachineControl("MFX Hold", "Garde l'état de l'effet sans le doigt sur le pad.", OM_PAD_TACTILE),
        ),
        steps = listOf(
            "Maintiens Shift et appuie sur Master Fx : la page MFX TYPE s'affiche. Choisis un délai (Tape Delay par exemple).",
            "Sélectionne une percussion et allume son MFX Send. Laisse le kick et la basse éteints.",
            "Lance la lecture et appuie sur Master Fx.",
            "Touche le pad tactile du bout des doigts et déplace-toi : l'effet se joue sur la percussion.",
            "Allume MFX Hold, place ton doigt, puis lève-le : l'effet reste dans cet état.",
            "Essaie deux autres types d'effet maître et sauvegarde avec Write.",
        ),
        expectedResult = "L'effet maître agit seulement sur les parts envoyées, tu le joues au pad tactile, et MFX Hold le fige.",
        exercise = Exercise(
            id = "$M-l5-ex",
            lessonId = "$M-l5",
            title = "Carte des effets maîtres",
            instructions = listOf(
                "Envoie toutes les parts sauf le kick dans l'effet maître.",
                "Parcours au moins 8 types d'effet maître et, pour chacun, joue le pad tactile dans ses quatre coins.",
                "Note pour chacun : « montée », « break », « texture » ou « inutile pour moi ».",
            ),
            successCriteria = "8 types classés selon l'usage que tu en ferais en live.",
            styleHint = "Mentalcore et tribecore : garder le kick hors de l'effet maître permet des montées folles sans perdre le pied.",
        ),
        commonMistakes = listOf(
            "Toucher le pad tactile sans que Master Fx soit allumé : il ne pilote pas l'effet maître (manuel p. 22).",
            "Oublier MFX Send : une part non envoyée n'est pas touchée par l'effet maître (manuel p. 29).",
            "Croire qu'on peut avoir un effet maître différent par part : il n'y en a qu'un par pattern (manuel p. 27).",
            "Appuyer sur le pad tactile avec un objet pointu : uniquement le bout des doigts (manuel p. 29).",
        ),
        quiz = listOf(
            q("$M-l5-q1", "Combien d'effets maîtres un pattern contient-il ?",
                listOf("Un", "Un par part", "Quatre"), 0, "Un motif est constitué de 16 parties et d'un effet maître.", OM_MOTIF),
            q("$M-l5-q2", "À quoi sert le bouton MFX Send d'une part ?",
                listOf("Il choisit le type d'effet", "Il décide si la part passe dans l'effet maître", "Il coupe la part"), 1,
                "MFX Send définit si le signal d'une partie est envoyé ou non à l'effet maître.", OM_MFX_SEND),
            q("$M-l5-q3", "Quel raccourci affiche la page MFX TYPE ?",
                listOf("Shift + Master Fx", "Shift + MFX Hold", "Shift + IFX On"), 0, "Shift + Master Fx affiche la page MFX TYPE.", PG_SHORTCUTS),
            q("$M-l5-q4", "Combien de types d'effet maître la liste compte-t-elle ?",
                listOf("26", "32", "50"), 1, "MFX TYPE va de 01 à 32.", PG_PATTERN),
        ),
    )

    // ---------------------------------------------------------------------
    // 5.6 Effets maîtres de séquence et mémorisation
    // ---------------------------------------------------------------------
    private val lesson6 = Lesson(
        id = "$M-l6",
        moduleId = M,
        order = 6,
        title = "Effets maîtres de séquence",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(PG_MFX_LIST, PG_PATTERN, PG_SHORTCUTS, OM_PAD_TACTILE),
        durationMinutes = 10,
        objective = "Utiliser les effets maîtres qui agissent sur le séquenceur, et connaître leurs limites.",
        explanation = "Quatre types d'effet maître pilotent le séquenceur : Seq Reverse, Seq Doubler, Odd Stepper et Even " +
            "Stepper. Deux limites : la motion sequence n'est pas disponible avec eux, et ils peuvent changer l'effet du " +
            "SWING (ce sont parfois les pas impairs qui sont décalés au lieu des pas pairs). Comme pour les autres types, " +
            "MFX Hold permet de figer l'état de l'effet, et cet état peut être mémorisé dans le pattern. Si tu as enregistré " +
            "des mouvements d'effet maître dans le pattern (Module 6), CLEAR MFX MOTION les efface ; Shift + pad 3 affiche " +
            "cette page.",
        controls = listOf(
            MachineControl("Seq Reverse, Seq Doubler, Odd Stepper, Even Stepper", "Effets maîtres qui pilotent le séquenceur (types 14 à 17).", PG_MFX_LIST),
            MachineControl("MFX Hold", "Fige l'état de l'effet ; état mémorisable dans le pattern.", OM_PAD_TACTILE),
            MachineControl("CLEAR MFX MOTION (Shift + pad 3)", "Efface la motion sequence de l'effet maître.", PG_SHORTCUTS),
        ),
        steps = listOf(
            "Envoie toutes les parts dans l'effet maître (MFX Send allumé), y compris le kick.",
            "Shift + Master Fx : choisis Seq Reverse. Lance la lecture, allume Master Fx et touche le pad tactile.",
            "Essaie Seq Doubler, Odd Stepper et Even Stepper de la même façon.",
            "Avec l'un d'eux, allume MFX Hold pour figer l'effet pendant quelques tours, puis éteins-le.",
            "Mets un peu de SWING (leçon 4.5) et écoute s'il se comporte différemment avec ces types.",
            "Sauvegarde avec Write si tu veux garder l'état de MFX Hold dans le pattern.",
        ),
        expectedResult = "Tu as entendu les quatre effets de séquence sur tout le pattern et tu sais les figer avec MFX Hold.",
        exercise = Exercise(
            id = "$M-l6-ex",
            lessonId = "$M-l6",
            title = "Transformer le groove sans le reprogrammer",
            instructions = listOf(
                "Sur ton pattern complet, utilise un effet de séquence pendant 4 tours, puis relâche le pad tactile.",
                "Trouve lequel des quatre types crée la meilleure rupture avant un retour du kick.",
            ),
            successCriteria = "Une rupture de 4 tours avec un effet de séquence, suivie d'un retour net au groove normal.",
            styleHint = "Tribecore et hardtek : une rupture courte avant le retour du kick relance l'énergie du dancefloor.",
        ),
        commonMistakes = listOf(
            "Chercher à enregistrer une motion sequence avec Seq Reverse, Seq Doubler, Odd Stepper ou Even Stepper : elle n'est pas disponible avec ces types (guide p. 17).",
            "S'étonner que le swing change avec ces effets : ils peuvent faire décaler les pas impairs (guide p. 3).",
            "Laisser MFX Hold allumé sans s'en rendre compte : l'effet reste actif même sans le doigt sur le pad.",
        ),
        quiz = listOf(
            q("$M-l6-q1", "Avec quels effets maîtres la motion sequence n'est-elle pas disponible ?",
                listOf("Hall Reverb et Room Reverb", "Seq Reverse, Seq Doubler, Odd Stepper et Even Stepper", "Tous les délais"), 1,
                "La liste les marque d'un astérisque : motion sequence non disponible.", PG_MFX_LIST),
            q("$M-l6-q2", "Quel raccourci affiche la page CLEAR MFX MOTION ?",
                listOf("Shift + pad 3", "Shift + pad 12", "Shift + Master Fx"), 0, "Shift + pad 3 affiche la page CLEAR MFX MOTION.", PG_SHORTCUTS),
            q("$M-l6-q3", "L'état de MFX Hold peut-il être mémorisé dans un pattern ?",
                listOf("Oui", "Non", "Seulement en Global"), 0, "Le statut ON/OFF de MFX Hold peut être mémorisé dans un motif.", OM_PAD_TACTILE),
        ),
    )

    val lessons: List<Lesson> = listOf(lesson1, lesson2, lesson3, lesson4, lesson5, lesson6)
}
