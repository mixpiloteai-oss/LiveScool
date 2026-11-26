package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.ContentStatus
import com.livemachine.academy.data.model.Exercise
import com.livemachine.academy.data.model.Lesson
import com.livemachine.academy.data.model.MachineControl
import com.livemachine.academy.data.model.ManualDoc.PARAMETER_GUIDE
import com.livemachine.academy.data.model.ManualRef
import com.livemachine.academy.data.model.QuizQuestion

/**
 * Module 9 — Préparer un set.
 *
 * Sources : manuel d'utilisation (partie française) et electribe sampler Parameter Guide.
 * La carte SD et l'export vers Ableton Live sont supposés connus : seules les fonctions
 * de sauvegarde et de restauration propres à la machine sont détaillées.
 */
object Module9Content {

    private const val M = "m9"

    // Manuel d'utilisation
    private val OM_CONNEXIONS = ManualRef("Introduction › 2. Connexions", "25")
    private val OM_PILES = ManualRef("Introduction › 3. Connexion de l'adaptateur secteur", "25–26")
    private val OM_CARTE = ManualRef("Introduction › 4. Insérer et retirer une carte de mémoire", "26")
    private val OM_REGLAGES = ManualRef("Autres réglages › 2. à 7. (piles, économie d'énergie, MIDI, synchronisation)", "32–33")
    private val OM_CARTE_UTIL = ManualRef("Autres réglages › 8. Utilisation d'une carte de mémoire", "33")
    private val OM_FACTORY = ManualRef("Autres réglages › 10. Formatage des données utilisateur", "34")
    private val OM_IMPORT_EXPORT = ManualRef("Enregistrement et édition d'échantillons › 4. Importer/exporter des échantillons", "36")
    private val OM_DEPANNAGE = ManualRef("Appendice › 1. Guide de dépannage", "37")
    private val OM_ERREURS = ManualRef("Appendice › 2. Messages d'erreur", "37")
    private val OM_SPECS = ManualRef("Fiche technique", "38")

    // Guide des paramètres
    private val PG_CLOCK = ManualRef("6. GLOBAL PARAMETERS › CLOCK MODE, GLOBAL MIDI CH", "7", PARAMETER_GUIDE)
    private val PG_SYNC = ManualRef("6. GLOBAL PARAMETERS › SYNC POLARITY, SYNC UNIT", "8", PARAMETER_GUIDE)
    private val PG_POWER = ManualRef("6. GLOBAL PARAMETERS › BATTERY TYPE, POWER SAVE MODE", "8", PARAMETER_GUIDE)
    private val PG_CHAIN_MODE = ManualRef("6. GLOBAL PARAMETERS › CHAIN MODE", "9", PARAMETER_GUIDE)
    private val PG_DATA_PATTERN = ManualRef("7. DATA UTILITY › EXPORT/IMPORT PATTERN", "9", PARAMETER_GUIDE)
    private val PG_DATA_SAMPLE = ManualRef("7. DATA UTILITY › EXPORT ALL SAMPLE, CARD FORMAT, SOFTWARE UPDATE", "10", PARAMETER_GUIDE)
    private val PG_LAST_STEP = ManualRef("2. PART PARAMETERS › LAST STEP", "4", PARAMETER_GUIDE)
    private val PG_EVENT = ManualRef("8. EVENT REC/PLAY", "10", PARAMETER_GUIDE)
    private val PG_TEMPO_LOCK = ManualRef("6. GLOBAL PARAMETERS › TEMPO LOCK", "8", PARAMETER_GUIDE)
    private val PG_SHORTCUTS = ManualRef("9. Appendix › Shortcut list", "11", PARAMETER_GUIDE)

    private fun q(id: String, prompt: String, options: List<String>, correct: Int, explanation: String, ref: ManualRef) =
        QuizQuestion(id, prompt, options, correct, explanation, ref)

    // ---------------------------------------------------------------------
    // 9.1 Sauvegarder tout son travail
    // ---------------------------------------------------------------------
    private val lesson1 = Lesson(
        id = "$M-l1",
        moduleId = M,
        order = 1,
        title = "Sauvegarder tout son travail",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(PG_DATA_PATTERN, PG_DATA_SAMPLE, OM_IMPORT_EXPORT, OM_CARTE_UTIL),
        durationMinutes = 15,
        objective = "Faire une copie de sécurité complète de tes patterns, de tes réglages et de tes samples avant un set.",
        explanation = "Deux exports couvrent tout. EXPORT ALL PATTERN (menu DATA UTILITY) écrit tous les patterns et les " +
            "paramètres Global dans un seul fichier (electribe_sampler_allpattern.e2sallpat) ; IMPORT ALL PATTERN le " +
            "recharge en remplaçant tous les patterns. EXPORT ALL SAMPLE écrit tous les samples, d'usine et utilisateur, " +
            "dans le fichier e2sSample.all, qui est rechargé automatiquement au démarrage suivant et remplace alors tous " +
            "les samples de la machine. Il n'y a qu'un seul e2sSample.all : avec une autre carte, ou si le fichier est " +
            "supprimé, tes samples utilisateur ne sont pas chargés. Exporté après des suppressions, le fichier est " +
            "défragmenté, ce qui peut rendre du temps d'échantillonnage. Pour un seul pattern : EXPORT PATTERN, qui exporte " +
            "même les modifications pas encore sauvegardées avec Write ; un fichier du même nom est écrasé. La carte doit " +
            "avoir été formatée sur la machine.",
        controls = listOf(
            MachineControl("EXPORT ALL PATTERN / IMPORT ALL PATTERN", "Tous les patterns + paramètres Global, dans un fichier.", PG_DATA_PATTERN),
            MachineControl("EXPORT ALL SAMPLE", "Tous les samples, rechargés automatiquement au démarrage.", PG_DATA_SAMPLE),
            MachineControl("EXPORT PATTERN", "Le pattern sélectionné, modifications non sauvegardées comprises.", PG_DATA_PATTERN),
        ),
        steps = listOf(
            "Machine éteinte, insère ta carte de set (formatée sur la machine).",
            "Allume, ouvre DATA UTILITY (Menu/Enter, puis « DATA UTILITY »).",
            "Lance EXPORT ALL PATTERN.",
            "Lance EXPORT ALL SAMPLE.",
            "Copie les fichiers de la carte sur ton ordinateur, dans un dossier daté.",
            "Éteins puis rallume avec la même carte : vérifie que tes samples utilisateur sont bien là.",
        ),
        expectedResult = "Deux fichiers de sauvegarde à jour sur la carte et sur ton ordinateur, et des samples rechargés au démarrage.",
        exercise = Exercise(
            id = "$M-l1-ex",
            lessonId = "$M-l1",
            title = "Le test de restauration",
            instructions = listOf(
                "Fais les deux exports complets.",
                "Modifie volontairement un pattern sans importance et sauvegarde-le avec Write.",
                "Lance IMPORT ALL PATTERN et vérifie que le pattern a retrouvé son état de la sauvegarde.",
            ),
            successCriteria = "Tu as restauré un pattern depuis ta sauvegarde : tu sais que ta copie de sécurité fonctionne.",
            styleHint = "Free party ou club, une sauvegarde testée vaut mieux que dix sauvegardes jamais relues.",
        ),
        commonMistakes = listOf(
            "Lancer IMPORT ALL PATTERN sans sauvegarde récente : tous les patterns de la machine sont remplacés (manuel p. 36, guide p. 9).",
            "Démarrer avec une autre carte : les samples utilisateur ne sont pas chargés (guide p. 10).",
            "Démarrer avec la mauvaise carte qui contient un ancien e2sSample.all : il remplace tous les samples au démarrage (manuel p. 36).",
            "Utiliser une carte formatée sur l'ordinateur : il faut la reformater sur la machine (manuel p. 37).",
        ),
        quiz = listOf(
            q("$M-l1-q1", "Que contient le fichier créé par EXPORT ALL PATTERN ?",
                listOf("Tous les patterns et les paramètres Global", "Seulement le pattern actif", "Tous les samples"), 0,
                "EXPORT ALL PATTERN exporte tous les patterns et les paramètres Global dans un seul fichier.", PG_DATA_PATTERN),
            q("$M-l1-q2", "Quand le fichier e2sSample.all est-il rechargé ?",
                listOf("Jamais automatiquement", "Automatiquement au démarrage suivant", "À chaque changement de pattern"), 1,
                "Le fichier exporté est chargé automatiquement à la mise sous tension suivante.", PG_DATA_SAMPLE),
            q("$M-l1-q3", "EXPORT PATTERN exporte-t-il les modifications pas encore sauvegardées ?",
                listOf("Oui, l'état modifié est exporté", "Non, seulement l'état sauvegardé", "Il refuse d'exporter"), 0,
                "Si le pattern est en cours d'édition, sa forme modifiée est exportée même sans Write.", PG_DATA_PATTERN),
            q("$M-l1-q4", "Que fait IMPORT ALL PATTERN ?",
                listOf("Il ajoute des patterns", "Il remplace tous les patterns", "Il importe des samples"), 1,
                "Cette opération remplace tous les motifs.", OM_IMPORT_EXPORT),
        ),
    )

    // ---------------------------------------------------------------------
    // 9.2 Organiser le set
    // ---------------------------------------------------------------------
    private val lesson2 = Lesson(
        id = "$M-l2",
        moduleId = M,
        order = 2,
        title = "Organiser le set",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(PG_CHAIN_MODE, PG_TEMPO_LOCK, PG_SHORTCUTS),
        durationMinutes = 20,
        objective = "Ranger les morceaux du set pour les retrouver sans réfléchir, dans un ordre d'énergie cohérent.",
        explanation = "Un set se prépare comme une carte. Chaque morceau a sa dizaine de patterns (leçon 7.1), ses sections " +
            "dans une rangée de Pattern Set (7.5), et un nom clair. Côté énergie, classe les morceaux par tempo et par " +
            "intensité, par exemple de la tribe vers le mentalcore. Deux réglages Global à décider avant le set : TEMPO " +
            "LOCK (On si tu veux garder un tempo unique, Off pour que chaque pattern impose le sien) et CHAIN MODE. Avec " +
            "CHAIN MODE sur On, les patterns qui ont un CHAIN TO s'enchaînent tout seuls : pratique pour un morceau en " +
            "pilote automatique, piégeux si tu veux tout lancer à la main. Mets-le sur Off si tu joues tout au Pattern Set.",
        controls = listOf(
            MachineControl("CHAIN MODE (menu Global)", "On : les chaînes des patterns s'exécutent ; Off : elles sont ignorées.", PG_CHAIN_MODE),
            MachineControl("TEMPO LOCK (menu Global)", "Tempo unique ou tempo de chaque pattern.", PG_TEMPO_LOCK),
        ),
        steps = listOf(
            "Liste les morceaux du set et leur BPM.",
            "Classe-les dans l'ordre d'énergie voulu.",
            "Attribue à chacun une dizaine de patterns et vérifie les noms.",
            "Range leurs sections dans les groupes de Pattern Set, un morceau par rangée de pads.",
            "Décide CHAIN MODE et TEMPO LOCK pour ce set, et note-les sur ta check-list (leçon 8.1).",
            "Fais un export complet (leçon 9.1).",
        ),
        expectedResult = "Une feuille de set (ordre, BPM, dizaines, pads) qui correspond exactement à ce qui est dans la machine.",
        exercise = Exercise(
            id = "$M-l2-ex",
            lessonId = "$M-l2",
            title = "La feuille de set",
            instructions = listOf(
                "Prépare un set de 30 minutes : au moins 4 morceaux.",
                "Écris la feuille : ordre, style, BPM, dizaine de patterns, groupe et pads de Pattern Set, transition prévue.",
                "Vérifie chaque ligne sur la machine.",
            ),
            successCriteria = "Chaque ligne de la feuille se retrouve sur la machine en moins de 10 secondes.",
            styleHint = "Monter progressivement en BPM et en intensité (tribe → hardtek → tribecore → mentalcore) garde le dancefloor avec toi.",
        ),
        commonMistakes = listOf(
            "Laisser CHAIN MODE sur On avec d'anciennes chaînes : un pattern part tout seul vers un autre en plein set (guide p. 3 et 9).",
            "Changer le BPM d'un morceau en répétition sans mettre à jour la feuille de set.",
            "Oublier d'exporter après la dernière répétition (leçon 9.1).",
        ),
        quiz = listOf(
            q("$M-l2-q1", "CHAIN MODE est sur Off : les CHAIN TO des patterns s'exécutent-ils ?",
                listOf("Oui", "Non, ils n'ont aucun effet", "Seulement pour le premier pattern"), 1,
                "Si CHAIN MODE est sur Off, CHAIN TO et CHAIN REPEAT de chaque pattern n'ont aucun effet.", PG_CHAIN_MODE),
            q("$M-l2-q2", "TEMPO LOCK sur On : que se passe-t-il en lançant un morceau d'un autre BPM ?",
                listOf("Il joue à son propre BPM", "Il joue au tempo actuel, verrouillé", "La machine s'arrête"), 1,
                "Le tempo actuel est verrouillé même si le nouveau pattern a un tempo différent.", PG_TEMPO_LOCK),
            q("$M-l2-q3", "Pourquoi ranger chaque morceau dans sa propre dizaine de patterns ?",
                listOf("Parce que Shift + commande de valeur saute de 10 en 10", "Parce que la machine l'impose", "Pour économiser la mémoire"), 0,
                "Shift + Value sélectionne le numéro de pattern par pas de 10 : on retrouve chaque morceau très vite.", PG_SHORTCUTS),
        ),
    )

    // ---------------------------------------------------------------------
    // 9.3 Alimentation et autonomie
    // ---------------------------------------------------------------------
    private val lesson3 = Lesson(
        id = "$M-l3",
        moduleId = M,
        order = 3,
        title = "Alimentation et autonomie",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_PILES, OM_REGLAGES, PG_POWER, OM_SPECS),
        durationMinutes = 10,
        objective = "Choisir entre secteur et piles, et régler la machine pour tenir tout le set.",
        explanation = "Sur secteur, n'utilise que l'adaptateur fourni (DC 9V). Sur piles : six piles AA, alcalines ou " +
            "nickel-métal-hydrure (Ni-MH). Règle BATTERY TYPE (« Alkali » ou « Ni-MH ») pour que l'indicateur de charge " +
            "soit juste ; l'icône de pile vide signale qu'il faut les changer. L'autonomie est d'environ 5 heures avec " +
            "des piles Ni-MH et le mode d'économie d'énergie actif. POWER SAVE MODE : « Auto » (par défaut) l'active sur " +
            "piles et le coupe sur secteur, « Enable » toujours, « Disable » jamais ; il baisse l'éclairage de l'écran et des " +
            "diodes, et l'écran peut alors sembler scintiller selon l'éclairage ambiant.",
        controls = listOf(
            MachineControl("Prise DC 9V", "Adaptateur fourni uniquement.", OM_PILES),
            MachineControl("BATTERY TYPE (menu Global)", "Alkali ou Ni-MH.", PG_POWER),
            MachineControl("POWER SAVE MODE (menu Global)", "Disable, Auto ou Enable.", PG_POWER),
        ),
        steps = listOf(
            "Décide si ton set se joue sur secteur ou sur piles.",
            "Sur piles : mets six piles AA neuves ou chargées, toutes du même type.",
            "Dans le menu Global, règle BATTERY TYPE sur le type utilisé.",
            "Règle POWER SAVE MODE (Auto convient dans la plupart des cas).",
            "Prévois des piles de rechange, et ne change les piles qu'après avoir mis la machine hors tension si l'adaptateur est branché.",
            "Sauvegarde les réglages Global avec Write.",
        ),
        expectedResult = "La machine est alimentée de façon fiable pour la durée du set, et l'indicateur de charge est juste.",
        exercise = Exercise(
            id = "$M-l3-ex",
            lessonId = "$M-l3",
            title = "Le test d'autonomie",
            instructions = listOf(
                "Avec des piles chargées et POWER SAVE sur Auto, fais tourner ton set en boucle (CHAIN) en notant l'heure.",
                "Note au bout de combien de temps l'icône de pile vide apparaît.",
            ),
            successCriteria = "Tu connais l'autonomie réelle de ta machine avec tes piles, et tu sais combien de jeux de rechange prévoir.",
            styleHint = "Free party en extérieur : l'autonomie réelle décide de la durée possible de ton set sans groupe électrogène.",
        ),
        commonMistakes = listOf(
            "Utiliser un autre adaptateur secteur que celui fourni : risque de dysfonctionnement (manuel p. 26).",
            "Laisser BATTERY TYPE sur le mauvais type : l'indicateur de charge n'est pas fiable (manuel p. 25–26).",
            "Laisser des piles dans la machine pendant un long rangement : risque de fuite (manuel p. 26).",
        ),
        quiz = listOf(
            q("$M-l3-q1", "Quelle autonomie le manuel indique-t-il ?",
                listOf("Environ 2 heures", "Environ 5 heures (piles Ni-MH, économie d'énergie active)", "Environ 12 heures"), 1,
                "Autonomie des piles : 5 heures environ, avec des piles Ni-MH et le mode d'économie d'énergie actif.", OM_SPECS),
            q("$M-l3-q2", "Que fait POWER SAVE MODE sur « Auto » ?",
                listOf("Il est actif sur piles et coupé sur secteur", "Il est toujours actif", "Il éteint la machine"), 0,
                "Auto active l'économie d'énergie sur piles et la désactive avec l'adaptateur.", PG_POWER),
            q("$M-l3-q3", "Combien de piles AA faut-il ?",
                listOf("4", "6", "8"), 1, "Six piles de type AA.", OM_PILES),
        ),
    )

    // ---------------------------------------------------------------------
    // 9.4 Brancher et synchroniser
    // ---------------------------------------------------------------------
    private val lesson4 = Lesson(
        id = "$M-l4",
        moduleId = M,
        order = 4,
        title = "Brancher et synchroniser",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_CONNEXIONS, PG_CLOCK, PG_SYNC, PG_LAST_STEP, PG_EVENT),
        durationMinutes = 15,
        objective = "Brancher la machine sur la sono et la synchroniser avec d'autres machines.",
        explanation = "Sorties : Audio Out L/R (jacks 6,35 mm) ; si seule la prise L est branchée, la sortie passe en mono. La " +
            "prise casque est un mini-jack stéréo. Toujours brancher machines éteintes. Synchronisation : CLOCK MODE " +
            "(menu Global) choisit l'horloge. « Internal » : la machine est maître. « Auto » : elle suit une horloge MIDI " +
            "reçue (USB ou MIDI IN) ou un signal Sync In, sinon elle reste en interne ; priorité USB, puis MIDI, puis Sync. " +
            "« External USB », « External MIDI », « External Sync » forcent la source. GLOBAL MIDI CH règle le canal MIDI. " +
            "Sync Out envoie une impulsion à chaque pas, pour la série volca ou des séquenceurs analogiques ; SYNC POLARITY " +
            "et SYNC UNIT (1 ou 2 pas) ajustent ce signal. Limites : le LAST STEP ne se synchronise pas avec un séquenceur " +
            "externe, et l'Event Recorder demande CLOCK MODE sur « Internal ».",
        controls = listOf(
            MachineControl("Audio Out L/R", "Sorties ; L seule = mono.", OM_CONNEXIONS),
            MachineControl("CLOCK MODE", "Internal, Auto, External USB / MIDI / Sync.", PG_CLOCK),
            MachineControl("GLOBAL MIDI CH", "Canal MIDI, 01 à 16.", PG_CLOCK),
            MachineControl("SYNC POLARITY / SYNC UNIT", "Polarité et cycle du signal Sync.", PG_SYNC),
        ),
        steps = listOf(
            "Machines éteintes, branche Audio Out L et R sur la console (ou L seule si l'entrée est mono).",
            "Si une autre machine doit suivre l'electribe : relie Sync Out ou MIDI OUT, et laisse CLOCK MODE sur « Internal ».",
            "Si l'electribe doit suivre une autre machine : relie son horloge à MIDI IN, USB ou Sync In, et règle CLOCK MODE en conséquence.",
            "Règle GLOBAL MIDI CH pour qu'il corresponde à l'autre appareil.",
            "Lance la lecture et vérifie que les deux machines restent calées.",
            "Sauvegarde les réglages Global avec Write.",
        ),
        expectedResult = "La machine sonne correctement sur la sono, et reste calée avec l'autre machine.",
        exercise = Exercise(
            id = "$M-l4-ex",
            lessonId = "$M-l4",
            title = "Sur la sono du club",
            instructions = listOf(
                "Prépare la liste des câbles pour ton set : sorties, casque, éventuelle synchro.",
                "Écris les réglages CLOCK MODE et GLOBAL MIDI CH de ton setup, et ceux à remettre si tu joues seul.",
            ),
            successCriteria = "Liste de câbles et réglages de synchro prêts, pour le jeu seul et en duo.",
            styleHint = "En sound system, demande toujours si l'entrée de la console est stéréo ou mono avant de brancher.",
        ),
        commonMistakes = listOf(
            "Brancher machines allumées : risque de dysfonctionnement ou d'endommager les enceintes (manuel p. 25).",
            "Rester en synchro externe et ne plus pouvoir lancer l'Event Recorder : il demande CLOCK MODE sur Internal (guide p. 10).",
            "Compter sur les LAST STEP différents en synchro avec un séquenceur externe : ils ne se synchronisent pas (guide p. 4).",
            "Ne pas avoir le même canal MIDI des deux côtés : pas de pilotage MIDI (manuel p. 37).",
        ),
        quiz = listOf(
            q("$M-l4-q1", "Une seule prise Audio Out L est branchée. Comment sort le son ?",
                listOf("En stéréo", "En mono", "Il ne sort pas"), 1, "Avec seulement la prise L branchée, le signal stéréo devient mono.", OM_CONNEXIONS),
            q("$M-l4-q2", "Avec CLOCK MODE sur « Auto », quelle source est prioritaire ?",
                listOf("Sync In", "External USB", "External MIDI"), 1, "L'ordre de priorité est External USB, External MIDI, puis External Sync.", PG_CLOCK),
            q("$M-l4-q3", "Quel CLOCK MODE choisir quand l'electribe est maître ?",
                listOf("Internal", "External Sync", "External MIDI"), 0, "Internal : l'horloge interne de la machine sert de référence.", PG_CLOCK),
            q("$M-l4-q4", "À quoi sert la prise Sync Out ?",
                listOf("À la sortie casque", "À synchroniser la série volca ou des séquenceurs analogiques", "À l'alimentation"), 1,
                "Elle produit une impulsion à chaque pas, pour synchroniser la série volca ou d'autres séquenceurs analogiques.", OM_CONNEXIONS),
        ),
    )

    // ---------------------------------------------------------------------
    // 9.5 Dépanner sur scène
    // ---------------------------------------------------------------------
    private val lesson5 = Lesson(
        id = "$M-l5",
        moduleId = M,
        order = 5,
        title = "Dépanner sur scène",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(OM_DEPANNAGE, OM_ERREURS, OM_CARTE),
        durationMinutes = 15,
        objective = "Réagir vite aux problèmes courants et comprendre les messages d'erreur.",
        explanation = "Les réflexes du guide de dépannage. Pas d'affichage : adaptateur bien branché ? Piles épuisées ? Pas de " +
            "son : bonne prise ? Ampli ou console allumés et réglés ? Volume de l'electribe assez haut ? Le son ne s'arrête " +
            "pas : un pattern joue en boucle, appuie sur Stop. Les pads ne sonnent pas : la part a-t-elle été sauvegardée ? " +
            "Le bon mode de pad est-il allumé ? Une motion sequence est-elle active ? Messages d'erreur (Exit ferme la " +
            "fenêtre) : « Card Error » carte endommagée (sauvegarde ses fichiers, puis formate-la sur la machine) ; " +
            "« Protected » carte protégée en écriture ; « Card Full » carte pleine ; « Event Over » trop d'événements à " +
            "sauvegarder ; « File Error » erreur de lecture ou d'écriture d'un fichier ; « Memory Full » mémoire de la " +
            "machine insuffisante pour charger ou copier ; « Motion Seq Full » plus de 24 motion sequences.",
        controls = listOf(
            MachineControl("Exit", "Ferme la fenêtre du message d'erreur.", OM_ERREURS),
            MachineControl("Volume", "Premier réflexe quand il n'y a pas de son.", OM_DEPANNAGE),
            MachineControl("Stop", "Arrête un pattern qui tourne en boucle.", OM_DEPANNAGE),
        ),
        steps = listOf(
            "Lis la liste des problèmes ci-dessus et repère ceux qui te sont déjà arrivés.",
            "Simule « pas de son » : baisse Volume à zéro, puis retrouve la cause en suivant la check-list.",
            "Simule « pads muets » : passe en mode Sequencer et tape un pad en croyant jouer, puis retrouve la cause.",
            "Mets ta carte en protection d'écriture et lance un export : lis le message, ferme-le avec Exit, retire la protection.",
            "Note sur ta feuille de set les trois vérifications à faire en cas de silence.",
        ),
        expectedResult = "Tu sais diagnostiquer les pannes courantes en quelques secondes et tu reconnais chaque message d'erreur.",
        exercise = Exercise(
            id = "$M-l5-ex",
            lessonId = "$M-l5",
            title = "Le silence de 10 secondes",
            instructions = listOf(
                "Demande à quelqu'un de créer un problème en secret (Volume baissé, mauvais mode de pad, part coupée, câble débranché).",
                "Trouve la cause en moins de 10 secondes.",
                "Recommence avec trois problèmes différents.",
            ),
            successCriteria = "Trois pannes trouvées, chacune en moins de 10 secondes.",
            styleHint = "En live, un silence se rattrape ; un silence de 30 secondes, beaucoup moins.",
        ),
        commonMistakes = listOf(
            "Utiliser une carte SDXC : non compatible ; prends une SD de 512 Mo à 2 Go ou une SDHC de 32 Go maximum (manuel p. 26 et 37).",
            "Insérer ou retirer la carte machine allumée pour régler un problème : risque de perte de données (manuel p. 26).",
            "Formater une carte endommagée sans avoir copié ses fichiers avant (manuel p. 37).",
        ),
        quiz = listOf(
            q("$M-l5-q1", "Comment fermer la fenêtre d'un message d'erreur ?",
                listOf("Bouton Exit", "Bouton Write", "Éteindre la machine"), 0, "Le bouton Exit referme la fenêtre du message.", OM_ERREURS),
            q("$M-l5-q2", "Que signifie « Protected » ?",
                listOf("La carte est protégée en écriture", "Le pattern est verrouillé", "La mémoire est pleine"), 0,
                "Vous essayez de sauvegarder sur une carte protégée.", OM_ERREURS),
            q("$M-l5-q3", "Que signifie « Memory Full » ?",
                listOf("La carte SD est pleine", "La mémoire de la machine est insuffisante pour charger ou copier", "Trop de motion sequences"), 1,
                "La capacité de la mémoire de l'instrument ne suffit pas pour charger ou copier les données de la carte.", OM_ERREURS),
            q("$M-l5-q4", "Les cartes SDXC sont-elles compatibles ?",
                listOf("Oui", "Non", "Seulement de 64 Go"), 1, "L'instrument n'est pas compatible avec les cartes SDXC.", OM_CARTE),
        ),
    )

    // ---------------------------------------------------------------------
    // 9.6 La répétition générale
    // ---------------------------------------------------------------------
    private val lesson6 = Lesson(
        id = "$M-l6",
        moduleId = M,
        order = 6,
        title = "La répétition générale",
        status = ContentStatus.VERIFIED,
        manualRefs = listOf(PG_DATA_SAMPLE, OM_FACTORY, PG_EVENT),
        durationMinutes = 45,
        objective = "Jouer le set en entier dans les conditions du live, l'enregistrer, et vérifier une dernière fois la machine.",
        explanation = "La répétition générale rassemble tout le parcours : check-list Global (8.1), sauvegardes (9.1), feuille " +
            "de set (9.2), alimentation (9.3), branchements (9.4). On joue le set de bout en bout, sans s'arrêter, en " +
            "l'enregistrant avec l'Event Recorder (8.6). Deux points d'entretien : SOFTWARE UPDATE (DATA UTILITY) met à jour " +
            "le système à partir d'un fichier du site Korg copié sur la carte ; certaines fonctions, comme l'annulation " +
            "Shift + <, demandent le système 2.00 ou plus. FACTORY RESET remet tous les réglages d'usine et efface les " +
            "patterns créés : ne le lance jamais sans sauvegarde complète.",
        controls = listOf(
            MachineControl("SOFTWARE UPDATE (DATA UTILITY)", "Mise à jour du système depuis la carte.", PG_DATA_SAMPLE),
            MachineControl("FACTORY RESET", "Retour aux réglages d'usine ; efface les patterns utilisateur.", OM_FACTORY),
            MachineControl("Event Recorder", "Enregistrer la répétition.", PG_EVENT),
        ),
        steps = listOf(
            "Fais un export complet (9.1) et vérifie ta check-list Global (8.1).",
            "Installe-toi comme en live : même alimentation, mêmes câbles, casque.",
            "Lance l'Event Recorder et joue le set en entier sans t'arrêter, même en cas d'erreur.",
            "Réécoute l'enregistrement et note les moments faibles.",
            "Corrige les patterns concernés, sauvegarde, puis refais un export complet.",
            "La veille du live, ne change plus rien : seulement la check-list et la sauvegarde.",
        ),
        expectedResult = "Un set joué d'un bout à l'autre, enregistré, corrigé, et une sauvegarde finale à jour.",
        exercise = Exercise(
            id = "$M-l6-ex",
            lessonId = "$M-l6",
            title = "Le set de fin de parcours",
            instructions = listOf(
                "Joue un set d'au moins 30 minutes, dans tes styles, avec au moins trois transitions différentes.",
                "Enregistre-le et réécoute-le en entier.",
                "Liste ce que tu referais pareil et ce que tu changerais.",
            ),
            successCriteria = "Un set complet joué sans arrêt, enregistré, avec un bilan écrit : tu es prêt pour le public.",
            styleHint = "Mentalcore, tribe, tribecore, hardtek ou acidcore : ton set doit raconter une montée, pas juste empiler des morceaux.",
        ),
        commonMistakes = listOf(
            "Lancer FACTORY RESET pour « repartir propre » sans sauvegarde : tous tes patterns sont effacés (manuel p. 34).",
            "Mettre à jour le système la veille d'un live : fais-le bien avant, puis vérifie ton set.",
            "S'arrêter à chaque erreur pendant la répétition : en live, on continue ; entraîne-toi à rattraper.",
        ),
        quiz = listOf(
            q("$M-l6-q1", "Que fait FACTORY RESET ?",
                listOf("Il exporte les patterns", "Il rétablit les réglages d'usine et efface les patterns créés", "Il met à jour le système"), 1,
                "Le formatage efface tous les motifs créés et rétablit les réglages d'usine.", OM_FACTORY),
            q("$M-l6-q2", "Comment met-on à jour le système de la machine ?",
                listOf("Par le pad tactile", "SOFTWARE UPDATE, depuis un fichier du site Korg copié sur la carte", "Par MIDI uniquement"), 1,
                "On copie le fichier de mise à jour sur la carte puis on exécute SOFTWARE UPDATE.", PG_DATA_SAMPLE),
            q("$M-l6-q3", "Pourquoi enregistrer la répétition avec l'Event Recorder ?",
                listOf("Pour sauvegarder les patterns", "Pour réécouter le set et repérer les moments faibles", "Pour libérer de la mémoire"), 1,
                "L'Event Recorder enregistre ta performance, que tu peux réécouter avec l'Event Player.", PG_EVENT),
        ),
    )

    val lessons: List<Lesson> = listOf(lesson1, lesson2, lesson3, lesson4, lesson5, lesson6)
}
