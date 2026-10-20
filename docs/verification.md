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
