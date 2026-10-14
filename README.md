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