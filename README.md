## Format et emplacement du fichier de sauvegarde

L'application TaskFlow utilise un fichier local au format `TASKFLOW_V1` pour conserver les tâches.

La première ligne du fichier contient `TASKFLOW_V1`. Chaque ligne suivante représente une tâche avec cinq champs séparés par des tabulations :

1. Identifiant de la tâche
2. Titre encodé en Base64 (UTF-8)
3. Priorité (`BASSE`, `NORMALE` ou `ELEVEE`)
4. Échéance au format `AAAA-MM-JJ`, ou `-` si aucune date n'est définie
5. Statut (`A_FAIRE` ou `TERMINEE`)

Le format permet de conserver les caractères spéciaux et les tâches sans échéance.

L'emplacement du fichier est défini par le chemin `Path` transmis au constructeur de `SauvegardeTaches`. La sauvegarde crée le dossier de destination si nécessaire.


## Fonctionnalités réalisées : US-04, US-05 et US-06

### US-04 — Charger les tâches au démarrage

L'application charge automatiquement les tâches enregistrées au démarrage. Les informations conservées comprennent l'identifiant, le titre, la priorité, l'échéance et le statut de chaque tâche.

Si le fichier de sauvegarde n'existe pas, l'application démarre avec une liste de tâches vide. Aucun chargement manuel n'est nécessaire.

### US-05 — Gérer les fichiers invalides ou illisibles

L'application vérifie la validité du fichier de sauvegarde pendant le chargement. Si le fichier est invalide ou illisible, une erreur est signalée et le démarrage est interrompu proprement.

Le chargement est effectué de manière à ne pas remplacer les tâches en mémoire par des données partiellement chargées. Un fichier invalide n'est pas automatiquement remplacé par une sauvegarde vide.

### US-06 — Afficher le menu principal

L'application affiche un menu principal permettant à l'utilisateur de choisir les opérations disponibles. Une option permet de quitter l'application.

Les saisies invalides sont signalées à l'utilisateur sans provoquer de fermeture brutale. Après une opération, l'utilisateur peut revenir au menu principal.

### Vérification

Les fonctionnalités de chargement et de sauvegarde sont vérifiées avec les tests automatisés du projet. Le menu principal est également vérifié par des essais manuels : navigation entre les options, saisies invalides, retour au menu et sortie de l'application.
