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