package com.livemachine.academy.data.content

import com.livemachine.academy.data.model.MachineCategory
import com.livemachine.academy.data.model.MachineCategory.DRUM_MACHINE
import com.livemachine.academy.data.model.MachineCategory.GROOVEBOX
import com.livemachine.academy.data.model.MachineCategory.HYBRID
import com.livemachine.academy.data.model.MachineCategory.SAMPLER
import com.livemachine.academy.data.model.MachineCategory.SEQUENCER
import com.livemachine.academy.data.model.MachineCategory.SYNTH
import com.livemachine.academy.data.model.MachineProfile
import com.livemachine.academy.data.model.ManualSpec

/**
 * Les 18 machines du catalogue hors Electribe et Circuit Tracks : fiche de présentation générale,
 * puis cours dès que le manuel officiel est intégré (Digitakt II).
 * Aucune manipulation, page ou commande n'est enseignée tant que le PDF officiel du modèle exact
 * (voir [ManualSpec.fileName]) n'est pas déposé dans docs/manual/ et le cours rédigé à partir de lui.
 * Pour ajouter un cours : remplir `modules` (préfixe d'ids = progressPrefix) en citant ce manuel.
 */
object CatalogMachines {

    private const val PENDING_NOTE =
        "Fiche de présentation générale. Aucune manipulation n'est enseignée tant que le manuel officiel " +
            "de ce modèle exact n'est pas intégré : pas de commande ni de page citée sans vérification."

    private fun pending(
        id: String,
        prefix: String,
        manufacturer: String,
        name: String,
        category: MachineCategory,
        kind: String,
        shortDescription: String,
        pedagogy: String,
        liveUse: String,
        styles: List<String>,
        manual: ManualSpec,
    ) = MachineProfile(
        id = id,
        name = name,
        manufacturer = manufacturer,
        shortDescription = shortDescription,
        note = PENDING_NOTE,
        sourcesLabel = "Manuel attendu : ${manual.title} — fichier docs/manual/${manual.fileName}",
        modules = emptyList(),
        progressPrefix = prefix,
        category = category,
        kind = kind,
        styles = styles,
        pedagogy = pedagogy,
        liveUse = liveUse,
        manuals = listOf(manual),
    )

    /** Cours rédigé d'après le manuel officiel (v2.1.0). */
    val digitaktII = pending(
        "elektron-digitakt-ii", "dt2-", "Elektron", "Digitakt II", SAMPLER, "Sampler et séquenceur",
        "Boîte à rythmes à samples et séquenceur Elektron : 16 pistes audio ou MIDI, échantillonnage stéréo, 128 pas.",
        "Apprendre le séquençage pas à pas façon Elektron : variations par pas (parameter locks) et conditions de déclenchement.",
        "Patterns enchaînés et variations jouées en direct, idéal comme cœur rythmique d'un set.",
        listOf("Techno", "Hardtek", "TribeCore"),
        ManualSpec("elektron-digitakt-ii.pdf", "Digitakt II User Manual, OS 1.17 (Elektron, anglais)"),
    ).copy(
        note = DigitaktIIContent.note,
        sourcesLabel = DigitaktIIContent.sourcesLabel,
        modules = DigitaktIIContent.modules,
        sections = DigitaktIIContent.sections,
    )
    /** Cours rédigé d'après le manuel officiel (v2.4.0). */
    val octatrackMkII = pending(
        "elektron-octatrack-mkii", "ot2-", "Elektron", "Octatrack MKII", SAMPLER, "Sampler de performance",
        "Sampler de performance : 8 pistes audio et 8 pistes MIDI, échantillonnage en direct, scènes et crossfader.",
        "Comprendre le live sampling, les scènes et le crossfader : une autre façon de construire un set.",
        "Transitions par crossfader, rééchantillonnage en direct, boucles Pickup, pilotage d'autres machines.",
        listOf("Free party", "Techno", "Mentalcore"),
        ManualSpec("elektron-octatrack-mkii.pdf", "Octatrack MKII User Manual, OS 1.40C (Elektron, anglais)"),
    ).copy(
        note = OctatrackMkIIContent.note,
        sourcesLabel = OctatrackMkIIContent.sourcesLabel,
        modules = OctatrackMkIIContent.modules,
        sections = OctatrackMkIIContent.sections,
    )
    val mpcOnePlus = pending(
        "akai-mpc-one-plus", "mpc1p-", "Akai Professional", "MPC One+", SAMPLER, "Station MPC autonome",
        "Station de production MPC autonome : pads, échantillonnage, découpe et séquençage.",
        "Découvrir la méthode MPC : sampler, découper, jouer aux pads et arranger sans ordinateur.",
        "Jeu aux pads, déclenchement de séquences et de samples en direct.",
        listOf("House", "Électro", "Tribe"),
        ManualSpec("akai-mpc-one-plus.pdf", "guide d'utilisation officiel de la MPC One+ (Akai Professional)"),
    )
    /** Cours rédigé d'après le manuel de référence (v2.8.0). */
    val sp404MkII = pending(
        "roland-sp-404mkii", "sp4-", "Roland", "SP-404MKII", SAMPLER, "Sampler à effets",
        "Sampler compact à pads, orienté effets et jeu immédiat.",
        "Travailler le jeu en direct et les effets sur des samples, sans séquençage complexe.",
        "Déclenchement de samples, effets en temps réel, rééchantillonnage.",
        listOf("Électro", "Breaks", "Tribe"),
        ManualSpec("roland-sp-404mkii.pdf", "Manuel de référence SP-404MKII (Roland, français, logiciel 2.00+)"),
    ).copy(
        note = SP404MkIIContent.note,
        sourcesLabel = SP404MkIIContent.sourcesLabel,
        modules = SP404MkIIContent.modules,
        sections = SP404MkIIContent.sections,
    )
    val tr8s = pending(
        "roland-tr-8s", "tr8s-", "Roland", "TR-8S", DRUM_MACHINE, "Boîte à rythmes",
        "Boîte à rythmes Roland héritière des TR-808 et TR-909, avec import de samples.",
        "Construire des rythmes au séquenceur pas à pas et sculpter les kicks et percussions.",
        "Jeu instrument par instrument, variations et effets en direct.",
        listOf("Hardtek", "Techno", "House"),
        ManualSpec("roland-tr-8s.pdf", "manuel de référence officiel de la TR-8S (Roland)"),
    )
    /** Cours rédigé d'après le manuel officiel (v2.2.0). */
    val drumBruteImpact = pending(
        "arturia-drumbrute-impact", "dbi-", "Arturia", "DrumBrute Impact", DRUM_MACHINE, "Boîte à rythmes analogique",
        "Boîte à rythmes analogique : dix instruments sur huit pads, séquenceur 64 pas, effets Color et distorsion du mix.",
        "Comprendre la synthèse de batterie analogique, les accents, les effets Color et la polyrythmie.",
        "Bande tactile Looper/Roller, Mute/Solo et distorsion pour des rythmes bruts joués en direct.",
        listOf("Hardtek", "TribeCore", "Mentalcore"),
        ManualSpec("arturia-drumbrute-impact.pdf", "Manuel utilisateur DrumBrute Impact v1.0 (Arturia, français)"),
    ).copy(
        note = DrumBruteImpactContent.note,
        sourcesLabel = DrumBruteImpactContent.sourcesLabel,
        modules = DrumBruteImpactContent.modules,
        sections = DrumBruteImpactContent.sections,
    )
    /** Cours rédigé d'après le manuel officiel (v2.5.0). */
    val dfam = pending(
        "moog-dfam", "dfam-", "Moog", "DFAM", DRUM_MACHINE, "Percussion semi-modulaire",
        "Synthétiseur de percussion analogique semi-modulaire avec séquenceur 8 pas.",
        "Apprendre la synthèse percussive et le patch par câbles sur un instrument simple.",
        "Boucles percussives évolutives, synchronisées avec le reste du set.",
        listOf("Techno", "Industriel", "Mentalcore"),
        ManualSpec("moog-dfam.pdf", "DFAM Manual (Moog, 2017, anglais)"),
    ).copy(
        note = DFAMContent.note,
        sourcesLabel = DFAMContent.sourcesLabel,
        modules = DFAMContent.modules,
        sections = DFAMContent.sections,
    )
    val td3 = pending(
        "behringer-td-3", "td3-", "Behringer", "TD-3", SYNTH, "Synthé basse acid",
        "Synthétiseur basse analogique inspiré de la TB-303, avec séquenceur à accent et slide.",
        "Maîtriser la ligne acid : séquenceur, accent, slide, filtre résonant.",
        "Lignes acid jouées en direct sur le filtre et la résonance.",
        listOf("Acidcore", "Acid techno", "Tribe"),
        ManualSpec("behringer-td-3.pdf", "guide d'utilisation officiel de la TD-3 (Behringer)"),
    )
    /** Cours rédigé d'après le manuel officiel (v2.3.0). */
    val miniBrute2S = pending(
        "arturia-minibrute-2s", "mb2s-", "Arturia", "MiniBrute 2S", SYNTH, "Synthé analogique semi-modulaire",
        "Synthétiseur analogique monophonique semi-modulaire : deux VCO, filtre multimode, patchbay 48 points, séquenceur et arpégiateur.",
        "Comprendre la synthèse soustractive analogique, la modulation par câbles et le séquençage d'une ligne.",
        "Basses et leads agressifs pilotés par le séquenceur, filtre et Brute Factor joués en direct.",
        listOf("Mentalcore", "Hardtek", "Acidcore", "Techno"),
        ManualSpec("arturia-minibrute-2s.pdf", "Manuel utilisateur MiniBrute 2S v1.0 (Arturia, français)"),
    ).copy(
        note = MiniBrute2SContent.note,
        sourcesLabel = MiniBrute2SContent.sourcesLabel,
        modules = MiniBrute2SContent.modules,
        sections = MiniBrute2SContent.sections,
    )
    val hydrasynthDesktop = pending(
        "asm-hydrasynth-desktop", "hyd-", "ASM", "Hydrasynth Desktop", SYNTH, "Synthé numérique à tables d'ondes",
        "Synthétiseur numérique polyphonique à tables d'ondes, aux modulations très étendues.",
        "Explorer la synthèse par tables d'ondes et la modulation avancée.",
        "Leads, textures et nappes évolutives.",
        listOf("Mentalcore", "Techno", "Électro"),
        ManualSpec("asm-hydrasynth-desktop.pdf", "manuel d'utilisation officiel de l'Hydrasynth Desktop (ASM)"),
    )
    /** Cours rédigé d'après le guide officiel (v2.6.0). */
    val take5 = pending(
        "sequential-take-5", "tk5-", "Sequential", "Take 5", SYNTH, "Synthé analogique polyphonique",
        "Synthétiseur analogique polyphonique compact à 5 voix.",
        "Travailler les accords, nappes et stabs en synthèse soustractive polyphonique.",
        "Accords et stabs joués au clavier ou pilotés par un séquenceur.",
        listOf("House", "Techno", "Trance"),
        ManualSpec("sequential-take-5.pdf", "Take 5 User's Guide v2.0 (Sequential, mai 2025, anglais)"),
    ).copy(
        note = Take5Content.note,
        sourcesLabel = Take5Content.sourcesLabel,
        modules = Take5Content.modules,
        sections = Take5Content.sections,
    )
    val zeroCoast = pending(
        "makenoise-0-coast", "0c-", "Make Noise", "0-Coast", SYNTH, "Synthé « west coast »",
        "Synthétiseur monophonique semi-modulaire inspiré de la synthèse « west coast ».",
        "Découvrir une autre approche de la synthèse que la soustractive classique.",
        "Sons évolutifs et percussifs, patchés en direct.",
        listOf("Techno expérimentale", "Électro"),
        ManualSpec("makenoise-0-coast.pdf", "manuel officiel du 0-Coast (Make Noise)"),
    )
    /** Cours rédigé d'après le guide officiel (v2.7.0). */
    val deluge = pending(
        "synthstrom-deluge", "dlg-", "Synthstrom Audible", "Deluge", GROOVEBOX, "Groovebox hybride",
        "Groovebox portable : synthés, samples et séquenceur sur une grille de pads lumineux.",
        "Composer et arranger sur grille : de la boucle au morceau complet sur une seule machine.",
        "Lancement de clips et de sections, live complet autonome.",
        listOf("Tribe", "Techno", "Breaks"),
        ManualSpec("synthstrom-deluge.pdf", "Deluge Official Guidebook OS 4.1.0 (Synthstrom Audible, anglais)"),
    ).copy(
        note = DelugeContent.note,
        sourcesLabel = DelugeContent.sourcesLabel,
        modules = DelugeContent.modules,
        sections = DelugeContent.sections,
    )
    val trackerPlus = pending(
        "polyend-tracker-plus", "trk-", "Polyend", "Tracker+", HYBRID, "Tracker hybride",
        "Séquenceur tracker portable avec sampler et moteurs de synthèse.",
        "Apprendre le séquençage en colonnes (tracker), précis et rapide pour les rythmes complexes.",
        "Patterns et morceaux joués en direct, idéal pour les rythmes rapides.",
        listOf("TribeCore", "Breakcore", "Électro"),
        ManualSpec("polyend-tracker-plus.pdf", "manuel officiel du Tracker+ (Polyend)"),
    )
    val op1Field = pending(
        "teenage-engineering-op-1-field", "op1f-", "Teenage Engineering", "OP-1 field", HYBRID, "Synthé, sampler et enregistreur",
        "Synthétiseur, sampler et enregistreur multipiste portable.",
        "Découvrir une méthode de production « à la bande » et des synthèses variées.",
        "Boucles enregistrées et transformées en direct.",
        listOf("Électro", "Expérimental"),
        ManualSpec("teenage-engineering-op-1-field.pdf", "guide officiel de l'OP-1 field (Teenage Engineering)"),
    )
    val seqtrak = pending(
        "yamaha-seqtrak", "sqt-", "Yamaha", "SEQTRAK", GROOVEBOX, "Groovebox portable",
        "Groovebox portable : batterie, synthés, sampler et séquenceur dans un seul appareil.",
        "Aborder la synthèse FM et la production complète sur un appareil compact.",
        "Patterns et scènes enchaînés en direct.",
        listOf("House", "Électro", "Techno"),
        ManualSpec("yamaha-seqtrak.pdf", "manuel de référence officiel du SEQTRAK (Yamaha)"),
    )
    val pyramidMk3 = pending(
        "squarp-pyramid-mk3", "pyr-", "Squarp Instruments", "Pyramid MK3", SEQUENCER, "Séquenceur MIDI/CV",
        "Séquenceur matériel multipiste qui pilote synthés et boîtes à rythmes en MIDI et CV.",
        "Organiser tout un set autour d'un séquenceur central : pistes, polymétrie, effets MIDI.",
        "Pilotage de plusieurs machines à la fois, enchaînement des séquences.",
        listOf("Tous styles", "Techno", "Tribe"),
        ManualSpec("squarp-pyramid-mk3.pdf", "manuel officiel du Pyramid MK3 (Squarp Instruments)"),
    )
    val torsoT1 = pending(
        "torso-t-1", "t1-", "Torso Electronics", "T-1", SEQUENCER, "Séquenceur algorithmique",
        "Séquenceur algorithmique MIDI multipiste.",
        "Comprendre les rythmes euclidiens et les variations génératives.",
        "Rythmes et mélodies qui évoluent en direct, en pilotant d'autres machines.",
        listOf("Techno", "Tribe", "Expérimental"),
        ManualSpec("torso-t-1.pdf", "manuel officiel du T-1 (Torso Electronics)"),
    )

    /** Ordre d'affichage du catalogue, après l'Electribe et la Circuit Tracks. */
    val all = listOf(
        digitaktII, octatrackMkII, mpcOnePlus, sp404MkII, tr8s, drumBruteImpact, dfam,
        td3, miniBrute2S, hydrasynthDesktop, take5, zeroCoast,
        deluge, seqtrak, trackerPlus, op1Field, pyramidMk3, torsoT1,
    )
}
