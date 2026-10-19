TaskFlow — Gestionnaire de tâches

1. Présentation du projet

TaskFlow est une application en ligne de commande développée en Java dans le cadre du cours INF1763. Elle permet de créer, consulter et gérer des tâches, ainsi que de sauvegarder et de recharger les données.

2. Fonctionnalités

Les fonctionnalités prévues dans le projet sont les suivantes :

Création d'une tâche avec un titre, un identifiant, une priorité, une échéance facultative et un statut.

Ajout d'une nouvelle tâche.

Affichage de la liste des tâches.

Modification de la priorité d'une tâche.

Marquage d'une tâche comme terminée.

Gestion des échéances.

Attribution d'identifiants uniques.

Sauvegarde des tâches dans un fichier local.

Rechargement automatique des tâches au démarrage.

Gestion des fichiers de données absents, invalides ou illisibles.

Les fonctionnalités réellement disponibles dépendent de la version intégrée à la branche principale.

3. Prérequis

Avant de compiler et d'exécuter le projet, les outils suivants sont nécessaires :

JDK 

Git : pour cloner le dépôt et gérer les versions.

Terminal ou éditeur de code : par exemple Visual Studio Code ou IntelliJ IDEA.

4. Installation, compilation et exécution

Cloner le dépôt

git clone https://github.com/INF1763-Groupe05/TaskFlow-MP1.git
cd taskflow

5. Exemple d'utilisation

L'exemple suivant illustre le fonctionnement attendu de l'application.

Ajouter une tâche

L'utilisateur sélectionne l'option d'ajout et saisit les informations suivantes :

Titre : Vérifier la sauvegarde du serveur
Priorité : Élevée
Échéance : 2026-10-25

L'application crée la tâche avec un identifiant unique et le statut initial À faire.

Afficher les tâches

La liste peut être présentée sous la forme suivante :

===== LISTE DES TÂCHES =====

ID : 1
Titre : Vérifier la sauvegarde du serveur
Priorité : Élevée
Échéance : 2026-10-25
Statut : À faire
----------------------------

ID : 2
Titre : Mettre à jour le guide utilisateur
Priorité : Normale
Échéance : Aucune
Statut : À faire
----------------------------

Cet exemple illustre le résultat attendu ; il ne constitue pas une preuve que toutes les fonctionnalités sont déjà intégrées.
7. Sauvegarde et rechargement

Sauvegarde

La sauvegarde permet d'enregistrer les tâches dans le fichier de données local. Elle doit conserver les informations des tâches afin de pouvoir les retrouver lors d'une exécution ultérieure.

Le comportement attendu est le suivant :

L'utilisateur demande l'enregistrement des tâches.

L'application écrit les données dans le fichier prévu.

Un message indique si l'opération a réussi ou échoué.

Rechargement

Au démarrage, l'application doit tenter de récupérer les tâches précédemment enregistrées.

Si le fichier existe et contient des données valides, les tâches sont rechargées.

Si le fichier est absent, l'application doit pouvoir démarrer avec une liste vide.

Si le fichier est invalide ou illisible, l'application doit gérer l'erreur avec un message explicite et un comportement défini par l'équipe.

Les identifiants déjà utilisés doivent être préservés afin d'éviter les conflits lors de la création de nouvelles tâches.

Le déclenchement exact de la sauvegarde (commande explicite, automatique ou les deux) et la stratégie adoptée en cas de fichier invalide doivent être précisés selon le code final.

8. Limites et anomalies connues

Cette section doit être mise à jour au fur et à mesure des tests et de l'intégration.

À ce stade, les points suivants restent à confirmer sur la version finale :

Compatibilité avec la version exacte du JDK utilisée.

Comportement lorsque le fichier de données est absent, mal formé ou inaccessible.

Préservation des identifiants après un redémarrage.

Gestion des dates invalides et des titres vides.

Comportement lorsque la sauvegarde échoue.

Fonctionnalités qui ne sont pas encore intégrées ou testées.

Aucune anomalie ne doit être considérée comme corrigée ou confirmée sans vérification dans l'application.

9. Organisation du développement

Le projet utilise Git et GitHub pour faciliter le travail en équipe.

Issues : suivi des fonctionnalités et des tâches.

Branches : développement séparé des fonctionnalités.

Commits : historique des modifications.

Pull Requests : proposition et révision des changements avant fusion dans main.
