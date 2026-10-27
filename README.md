# TaskFlow — Gestionnaire de tâches

## Présentation du projet

**TaskFlow** est une application en ligne de commande développée dans le cadre du cours. Elle permet de gérer une liste de tâches et de faciliter le suivi de leur avancement.

Le projet est réalisé en équipe de cinq personnes.


## Fonctionnalités

Le projet prévoit les fonctionnalités suivantes :

- Création et ajout de tâches.
- Affichage de la liste des tâches.
- Modification de la priorité d’une tâche.
- Marquage d’une tâche comme terminée.
- Gestion des échéances.
- Attribution d’un identifiant unique à chaque tâche.
- Sauvegarde des tâches dans un fichier.
- Chargement des tâches au démarrage de l’application.
- Validation des données saisies.
- Gestion des erreurs liées aux fichiers.
- Tests unitaires.



## Prérequis


- Java Development Kit (JDK) :(version la plus récente prefèrée)
- Un terminal ou une invite de commandes.
- Git pour récupérer le dépôt et contribuer au projet.

## Compilation et exécution

Les commandes exactes dépendent de l’organisation des fichiers Java et du nom du package principal.

Si le projet utilise la structure `src/main/java/taskflow/` et une classe principale `Main` dans le package `taskflow`, les commandes sont :

**1. Compiler le projet**

```bash
javac -d out src/main/java/taskflow/*.java
```

**2. Exécuter l’application**

```bash
java -cp out taskflow.Main
```


## Exemple d’utilisation

### Ajouter une tâche

L’utilisateur sélectionne l’option d’ajout dans le menu et saisit les informations demandées.

```text
Titre : Vérifier la sauvegarde du serveur
Priorité : Élevée
Échéance : 2026-10-25
```

### Afficher la liste des tâches

Exemple de présentation dans le terminal :

```text
========== TASKFLOW ==========

ID   TÂCHE                              PRIORITÉ    STATUT
------------------------------------------------------------
1    Vérifier la sauvegarde du serveur  Élevée      À faire
2    Mettre à jour le guide utilisateur Normale     À faire
3    Tester la validation des données   Faible      Terminée

------------------------------------------------------------
Nombre total de tâches : 3
```



## Sauvegarde et chargement des données

L’application prévoit de sauvegarder les tâches dans un fichier afin de conserver les données entre deux exécutions.

| Élément | Description |
|---|---|
| Fichier de sauvegarde | À préciser |
| Emplacement du fichier | À préciser |
| Format des données | À préciser |
| Chargement au démarrage | Prévu dans le projet |
| Gestion des erreurs de lecture | Prévue dans le projet |

## Tests

Des tests unitaires sont prévus pour vérifier le bon fonctionnement des fonctionnalités, notamment la gestion des tâches, la validation des données et la persistance des informations.


## Technologies utilisées

- **Langage :** Java
- **Gestion de versions :** GitHub
- **Tests :** tests unitaires Java


