#!/usr/bin/env python3
"""Source unique des textes légaux : génère LegalTexts.kt (écrans de l'app) et docs/legal/*.md (à héberger).
Usage : python3 scripts/legal_texts.py   (depuis la racine du dépôt)"""
import json, pathlib

UPDATED = "27 novembre 2026"
EDITOR = "[À COMPLÉTER : nom ou raison sociale de l'éditeur, adresse]"
CONTACT = "[À COMPLÉTER : adresse e-mail de contact]"

PRIVACY = [
    ("Qui est responsable ?", f"L'éditeur de Live Machine Academy : {EDITOR}. Contact pour toute question ou demande sur tes données : {CONTACT}."),
    ("Ce qui reste sur ton téléphone", "Ta progression (leçons, quiz, exercices, défis), tes réglages, la photo de ta machine et tes repères, l'historique de l'assistant IA, un identifiant d'installation aléatoire et le dernier jeton de droits Premium. Ces données ne quittent pas le téléphone, sauf la sauvegarde Android de ton compte Google si tu l'as activée (le jeton de droits et les fichiers audio temporaires en sont exclus)."),
    ("Les cours fonctionnent hors ligne", "Les leçons, quiz, exercices, défis et le repérage photo n'utilisent pas Internet. L'app ne contient ni publicité, ni outil de mesure d'audience, ni géolocalisation."),
    ("Assistant IA", "Quand tu poses une question, l'app envoie à notre serveur : ta question (500 caractères maximum), tes 6 derniers messages, la machine choisie, la leçon ouverte, de courts extraits des leçons et ton niveau calculé, avec l'identifiant d'installation. Le serveur transmet ce texte au fournisseur d'IA (Anthropic) pour produire la réponse. Le serveur n'enregistre pas le contenu des conversations."),
    ("Analyse audio et microphone", "Le micro n'est utilisé que lorsque tu appuies sur « Enregistrer », avec l'autorisation Android demandée à ce moment-là. Rien n'est envoyé tant que tu n'as pas coché le consentement et appuyé sur « Envoyer pour analyse ». L'extrait (WAV mono, 60 s maximum) est analysé en mémoire par notre serveur puis oublié : il n'est ni stocké ni transmis au fournisseur d'IA, qui reçoit uniquement les mesures (tempo, niveaux, structure). Le fichier temporaire du téléphone est supprimé après l'analyse, à l'abandon et au redémarrage de l'app."),
    ("Abonnement Premium", "Le paiement est traité par Google Play ; nous ne voyons jamais tes données bancaires. Pour vérifier un achat, l'app envoie à notre serveur le jeton d'achat Google Play et l'identifiant d'installation. Le serveur conserve l'état de l'abonnement (produit, date d'échéance, renouvellement) et des compteurs d'utilisation (questions IA par jour, analyses par mois)."),
    ("Données techniques", "Comme tout service en ligne, le serveur voit l'adresse IP des requêtes ; elle sert seulement à limiter les abus (nombre de requêtes) et n'est pas conservée au-delà de quelques minutes en mémoire."),
    ("Durée de conservation", "Compteurs d'utilisation : jusqu'à la fin de la période concernée (jour ou mois), puis remplacés. État d'abonnement : pendant l'abonnement puis 12 mois maximum pour la gestion des litiges. Aucune donnée audio ni aucun contenu de conversation n'est conservé par le serveur."),
    ("Tes droits", f"Tu peux à tout moment effacer l'historique de l'assistant, ta photo et ta progression depuis l'app, et réinitialiser ton identifiant d'installation (Paramètres › Confidentialité). Conformément au RGPD, tu peux demander l'accès, la rectification ou la suppression des données liées à ton identifiant d'installation en écrivant à {CONTACT}. Tu peux aussi saisir la CNIL."),
    ("Sous-traitants", "Hébergeur du serveur : [À COMPLÉTER]. Fournisseur d'IA : Anthropic (traitement du texte des questions et des mesures audio). Paiements : Google Play."),
]

TERMS = [
    ("Objet", "Live Machine Academy est une application d'apprentissage qui t'aide à utiliser de vraies machines de musique (Korg Electribe Sampler 2, Novation Circuit Tracks). Elle ne simule aucune machine."),
    ("Marques", "Korg, electribe, Novation et Circuit Tracks sont des marques de leurs propriétaires respectifs. Live Machine Academy est une application indépendante, non affiliée à Korg ni à Novation. Les leçons sont rédigées avec nos propres mots à partir des manuels officiels, dont les pages sont citées."),
    ("Offres gratuite et Premium", "L'offre gratuite donne accès aux premiers modules de chaque machine, aux quiz, à la progression et à un nombre limité de questions IA et d'analyses audio. L'abonnement Premium (mensuel ou annuel, via Google Play) ouvre tous les cours, les défis avancés, des quotas supérieurs et des conseils personnalisés. Les limites en vigueur sont affichées dans l'écran Premium et peuvent évoluer."),
    ("Abonnement, renouvellement et résiliation", "L'abonnement se renouvelle automatiquement à chaque échéance jusqu'à sa résiliation dans Google Play (Paramètres de Google Play › Abonnements). Après résiliation, Premium reste actif jusqu'à la fin de la période payée. Les remboursements suivent les règles de Google Play."),
    ("Assistant IA et analyse audio", "Les réponses et conseils sont générés par une intelligence artificielle à partir des leçons et de mesures automatiques ; ils peuvent contenir des erreurs. Vérifie toujours sur ta machine et dans le manuel officiel. Les idées créatives sont des suggestions. N'envoie pas de contenu dont tu n'as pas les droits."),
    ("Usage acceptable", "Il est interdit de contourner les quotas, d'automatiser des requêtes, de revendre l'accès ou de tenter d'accéder aux données d'autres utilisateurs. En cas d'abus, l'accès aux fonctions en ligne peut être limité."),
    ("Responsabilité", "L'application est fournie pour l'apprentissage. L'éditeur ne peut être tenu responsable d'une mauvaise manipulation de ton matériel ; suis les consignes de sécurité des fabricants."),
    ("Contact et droit applicable", f"Éditeur : {EDITOR}. Contact : {CONTACT}. Droit français ; en cas de litige, recherche d'une solution amiable avant toute action."),
]

root = pathlib.Path(__file__).resolve().parent.parent

def md(title, sections):
    out = [f"# {title}", "", f"Dernière mise à jour : {UPDATED}", ""]
    for h, t in sections:
        out += [f"## {h}", "", t, ""]
    return "\n".join(out)

(root / "docs/legal/politique-confidentialite.md").write_text(md("Politique de confidentialité — Live Machine Academy", PRIVACY), encoding="utf-8")
(root / "docs/legal/conditions-utilisation.md").write_text(md("Conditions d'utilisation — Live Machine Academy", TERMS), encoding="utf-8")

def kt(sections):
    return ",\n".join(f"        LegalSection({json.dumps(h, ensure_ascii=False)}, {json.dumps(t, ensure_ascii=False)})" for h, t in sections)

kotlin = f'''package com.livemachine.academy.data.content

// Fichier généré par scripts/legal_texts.py : modifier le script, pas ce fichier.

data class LegalSection(val title: String, val text: String)

object LegalTexts {{
    const val UPDATED = "{UPDATED}"

    val privacy: List<LegalSection> = listOf(
{kt(PRIVACY)},
    )

    val terms: List<LegalSection> = listOf(
{kt(TERMS)},
    )
}}
'''
kotlin = kotlin.replace("$", "\\$")
(root / "app/src/main/java/com/livemachine/academy/data/content/LegalTexts.kt").write_text(kotlin, encoding="utf-8")
print("ok")
