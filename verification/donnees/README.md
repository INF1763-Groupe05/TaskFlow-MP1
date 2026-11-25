# État initial des données

Chaque scénario de vérification est exécuté dans un répertoire temporaire isolé.

Initialement, le fichier `data/taches.txt` est absent. L'application démarre donc avec une liste de tâches vide.

Le scénario `06-rechargement.txt` constitue une exception : il est exécuté dans le même répertoire temporaire que le scénario `06-sauvegarde.txt` afin de vérifier la persistance des données.

Cette stratégie garantit la reproductibilité des scénarios et évite de modifier les données réelles de l'utilisateur.