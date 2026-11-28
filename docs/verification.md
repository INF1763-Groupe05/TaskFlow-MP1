## Issue #19 - Tests unitaires

Fichier teste : TestFonctionnalites.java

| Test | Resultat attendu | Resultat observe | Conclusion |
|---|---|---|---|
| Modifier la priorité | La priorité est modifiée | OK | Réussi |
| Priorité null refusée | La priorité invalide est refusée | OK | Réussi |
| Priorité : identifiant inexistant | Retourne false | OK | Réussi |
| Terminer une tâche | Le statut devient TERMINEE | OK | Réussi |
| Terminer une seule tâche | L'autre tâche reste A_FAIRE | OK | Réussi |
| Tâche déjà terminée | Le statut reste TERMINEE | OK | Réussi |
| Terminer une tâche inexistante | Retourne false | OK | Réussi |
| Priorité sans modifier les autres champs | Les autres champs sont conservés | OK | Réussi |

**Résultat final : 8 tests réussis sur 8.**


---

## MP2 — Volet A : Filet de sécurité

### Objectif

Mettre en place des scénarios rejouables permettant de vérifier que les opérations de refactoring ne modifient pas le comportement initial de TaskFlow.

**Version de référence :** v1.0.0

**Commit de référence :** 789d125

### Scénarios de non-régression

| Scénario | Données utilisées | Résultat attendu | Résultat observé | Conclusion |
|---|---|---|---|---|
| 01-ajout | Titre, priorité élevée, échéance | Création correcte de la tâche | Sortie identique à la référence | Réussi |
| 02-liste | Deux tâches | Affichage des deux tâches | Sortie identique à la référence | Réussi |
| 03-priorite | Tâche ID 1, priorité basse puis élevée | Modification de la priorité | Sortie identique à la référence | Réussi |
| 04-echeance | Tâche ID 1, modification de date puis suppression | Modification et suppression de l'échéance | Sortie identique à la référence | Réussi |
| 05-statut | Tâche ID 1 | Passage au statut Terminée | Sortie identique à la référence | Réussi |
| 06-sauvegarde | Deux tâches dont une terminée | Sauvegarde des tâches | Sortie identique à la référence | Réussi |
| 06-rechargement | Données précédemment sauvegardées et nouvelle tâche | Rechargement des tâches et conservation des identifiants existants | Sortie identique à la référence | Réussi |
| 07-erreur | Saisies invalides, titre vide, date invalide, identifiant inexistant | Affichage des messages d'erreur | Sortie identique à la référence | Réussi |

### État initial des données

Chaque scénario est exécuté dans un répertoire temporaire isolé, sans fichier de données initial.

Le scénario de rechargement utilise les données produites par le scénario de sauvegarde.

Les dates utilisées sont fixes afin de garantir la reproductibilité des vérifications.

### Organisation des fichiers

- `verification/scenarios/` : fichiers de saisies.
- `verification/references/` : sorties de référence générées avec la version v1.0.0.
- `verification/donnees/` : documentation de l'état initial des données.
- `verification/rejouer.sh` : script de vérification automatique.

### Exécution des vérifications

Depuis la racine du projet, exécuter dans Git Bash :

`bash verification/rejouer.sh`

Le script compile le programme, exécute les scénarios et compare les sorties obtenues aux références à l'aide de la commande `diff`.

### Résultat final

- Nombre de scénarios exécutés : 8
- Nombre de scénarios réussis : 8
- Nombre de scénarios échoués : 0

Aucune différence de comportement n'a été détectée par rapport aux sorties de référence.

Les scénarios devront être rejoués après chaque étape de refactoring. Les fichiers de référence ne doivent pas être modifiés lors d'un refactoring.

---

## MP2 — Volet D3 : Vérifications finales

### Objectif

Vérifier le bon fonctionnement de TaskFlow après l'intégration des refactorings, de Maven, de l'export Excel et de la journalisation.

### Environnement de vérification

- Système d'exploitation : Windows.
- Terminal : Git Bash / PowerShell.
- Version cible : v1.1.0.
- Version du JDK : à confirmer.
- Commit final : à compléter.

### Préparation de l'environnement

Un nouveau clone du dépôt a été créé afin de réaliser les vérifications dans un environnement indépendant du dossier de développement.

```bash
git clone https://github.com/INF1763-Groupe05/TaskFlow-MP1.git TaskFlow-verification-D3
cd TaskFlow-verification-D3
```

### Tableau des vérifications finales

| N° | Vérification | Résultat attendu | Résultat observé | Conclusion |
|---|---|---|---|---|
| 1 | Non-régression | Les huit scénarios produisent les sorties attendues | À compléter | À vérifier |
| 2 | Compatibilité des anciennes données | Les données de la version v1.0.0 sont correctement chargées | À compléter | À vérifier |
| 3 | Construction depuis un clone neuf | La commande Maven génère le JAR sans erreur | Maven non encore disponible dans le clone | En attente |
| 4 | Exécution du JAR de la Release | Le JAR fonctionne avec Java uniquement, sans Maven ni code source | Release non encore publiée | En attente |
| 5 | Export Excel nominal | Le fichier Excel contient les tâches attendues | À compléter | À vérifier |
| 6 | Export Excel sans tâches | L'export d'une liste vide est correctement géré | À compléter | À vérifier |
| 7 | Échec de l'export Excel | L'erreur est correctement gérée | À compléter | À vérifier |
| 8 | Journalisation | Les événements et les erreurs sont enregistrés | À compléter | À vérifier |
| 9 | Message Log4j | Le problème de journalisation identifié est résolu | À compléter | À vérifier |

### Commandes de vérification

**Scénarios de non-régression :**

```bash
bash verification/rejouer.sh
```

**Construction du projet :**

```bash
./mvnw clean package
```

**Exécution du JAR :**

```bash
java -jar target/<nom-du-jar>.jar
```

### Bilan final

Le clonage du dépôt a été réalisé avec succès.

Les autres vérifications seront finalisées après l'intégration des fonctionnalités du mini-projet 2 et la publication de la Release v1.1.0.

La validation finale du projet reste en attente.