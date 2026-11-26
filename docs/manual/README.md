# Manuel officiel

Source unique du contenu pédagogique : le **manuel officiel Korg de l'Electribe Sampler**
(Owner's Manual / Manuel d'utilisation), dans la version correspondant à ta machine.

Fichier utilisé : `ER2ER2S_OM_EFGS2.pdf` — manuel d'utilisation electribe/electribe sampler
(Korg, 2015), en 4 langues. **Partie française : pages 20 à 38.** Les numéros de page cités dans
l'application sont ceux imprimés dans le manuel, identiques aux pages du PDF.

Le manuel n'est ni dans le dépôt ni dans l'APK (document Korg protégé) : garde ta copie en local, dans ce dossier (les `*.pdf` y sont ignorés par git).

Deuxième source : `39d238e0f840ee25e68f1618517b4731.pdf` — **electribe sampler Parameter Guide**
(Korg, 2016, en anglais, 20 pages). Pages citées : 3 à 19, identiques aux pages du PDF. Dans l'app,
ses références portent la mention « Guide des paramètres » (`ManualDoc.PARAMETER_GUIDE`).

Points où les deux documents divergent (signalés dans les leçons) :
- réglages Global : perdus sans Write selon le manuel (p. 32), sauvegardés à l'extinction selon le guide (p. 7) ;
- AUTO POWER OFF : valeur « Off » dans le manuel (p. 33), « Disable » dans le guide (p. 8).

Règles de rédaction :
- chaque leçon, commande et question de quiz cite son chapitre et ses pages (`ManualRef`) ;
- aucune fonction absente du manuel ;
- une leçon ne passe en `VERIFIED` qu'après relecture page par page (contrôlé par `CourseContentTest`).
