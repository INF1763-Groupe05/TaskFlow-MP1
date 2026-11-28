# Diagnostic et refactoring — TaskFlow



Dans le cadre du mini-projet 2, une analyse du code source de TaskFlow a été réalisée afin d'identifier les mauvaises odeurs susceptibles de compliquer la maintenance, la compréhension et l'évolution de l'application.

L'objectif est d'améliorer la structure interne du code sans modifier son comportement observable.

- Version analysée : v1.0.0
- Commit de référence : 789d125

##  Filet de sécurité

Avant toute modification du code source, un filet de sécurité a été mis en place dans le cadre de l'Issue A1.

Ce dispositif comprend huit scénarios rejouables couvrant les fonctionnalités principales de TaskFlow :

- Ajout d'une tâche.
- Affichage des tâches.
- Modification de la priorité.
- Gestion des échéances.
- Modification du statut.
- Sauvegarde des données.
- Rechargement des données.
- Gestion des erreurs.

Les sorties de référence sont conservées dans `verification/references/`.

La commande suivante permet de vérifier l'absence de modification du comportement :

`bash verification/rejouer.sh`

Les scénarios doivent être exécutés après chaque étape de refactoring.

## 3. Diagnostic des mauvaises odeurs

| ID | Type de mauvaise odeur | Emplacement | Pourquoi c'est gênant | Refactoring prévu |
|---|---|---|---|---|
| R1 | Mysterious Name — Nom peu explicite | GestionnaireTaches.java, méthode `suivant()`, lignes 73-75 | Le nom ne précise pas que la méthode calcule le prochain identifiant disponible. | Rename : renommer la méthode en `calculerIdentifiantSuivant()`. |
| R2 | Duplicated Code — Code dupliqué | GestionnaireTaches.java, méthodes `marquerTacheTerminee()`, `modifierPrioriteTache()` et `modifierEcheanceTache()`, lignes 128-132, 151-155 et 181-185 | La validation de l'identifiant est répétée dans plusieurs méthodes. Une modification de cette règle nécessite plusieurs interventions. | Extract Method : créer une méthode commune `verifierIdentifiant()`. |
| R3 | Magic Number — Nombre magique | GestionnaireTaches.java, méthodes `creerTache()`, `ajouterTache()` et `suivant()`, lignes 20-22, 46-48 et 74 | La valeur 0 représente un identifiant indisponible sans que cette signification soit explicitement nommée. | Extract Constant : introduire la constante `IDENTIFIANT_INDISPONIBLE`. |
| R4 | Tight Coupling — Couplage fort | GestionnaireTaches.java, méthodes `afficherTaches()`, `afficherPriorite()` et `afficherStatut()`, lignes 93-120 | La logique métier est directement liée à l'affichage dans la console. Cette dépendance complique la maintenance et l'évolution de l'interface utilisateur. | Extract Class et Move Method : créer une classe `AffichageTaches` responsable de la présentation. |
| R5 | Long Method — Méthode trop longue | Main.java, méthode `main()`, lignes 14-80 | La méthode réalise le chargement initial, la gestion du menu, le traitement des commandes et la gestion des erreurs. | Extract Method : extraire le chargement initial et la gestion des commandes dans des méthodes distinctes. |
| R6 | Long Method — Méthode trop longue | SauvegardeTaches.java, méthode `sauvegarder()`, lignes 38-73 | La méthode regroupe la validation, la sérialisation, l'écriture et le déplacement du fichier temporaire. | Extract Method : séparer la sérialisation et les opérations d'écriture et de déplacement du fichier. |
| R7 | Duplicated Code — Code dupliqué | ModeleTache.java, constructeurs, lignes 24-49 | L'initialisation des attributs est répétée dans les deux constructeurs. | Constructor Delegation : utiliser `this()` pour appeler le constructeur principal. |
| R8 | Magic String — Chaîne magique | SauvegardeTaches.java, méthodes `sauvegarder()` et `lireTache()`, lignes 59 et 125 | La chaîne "-" représente une échéance absente à plusieurs endroits. Une modification incohérente pourrait compromettre la compatibilité des données. | Extract Constant : introduire la constante `ECHEANCE_ABSENTE`. |

## 4. Synthèse du diagnostic

L'analyse a permis d'identifier huit mauvaises odeurs appartenant à six catégories :

- Mysterious Name.
- Duplicated Code.
- Magic Number.
- Magic String.
- Tight Coupling.
- Long Method.

Les refactorings proposés couvrent les techniques suivantes :

- Rename.
- Extract Method.
- Extract Constant.
- Extract Class.
- Move Method.
- Constructor Delegation.

## 5. Stratégie de réalisation

Chaque refactoring sera réalisé indépendamment en suivant les étapes suivantes :



1. Réalisation du refactoring par petits pas.
2. Exécution des scénarios de non-régression après chaque modification.


Aucune sortie de référence ne doit être modifiée pendant un refactoring.


## 6. Refactorings réalisés : avant et après

### 6.1. R1 — Renommage d'une méthode (Rename)

**Fichier :** `GestionnaireTaches.java`

**Avant :**

```java
private static int suivant(int id) {
    return id == Integer.MAX_VALUE ? 0 : id + 1;
}
```

**Après :**

```java
private static int calculerIdentifiantSuivant(int id) {
    return id == Integer.MAX_VALUE ? 0 : id + 1;
}
```

**Amélioration apportée :**

Le nouveau nom décrit clairement le rôle de la méthode. Les deux appels de la méthode ont également été mis à jour. Cette modification facilite la compréhension et la maintenance du code sans modifier sa logique.

**Traçabilité :**

- Issue principale : [#37 — Réaliser les refactorings du code](https://github.com/INF1763-Groupe05/TaskFlow-MP1/issues/37)
- Branche : `refactor/R1-renommage-suivant`
- Pull Request : lien à compléter.
- Résultats des vérifications : à confirmer.

### 6.2. R2 — Suppression du code dupliqué (Extract Method)

**Fichier :** `GestionnaireTaches.java`

**Avant :**

Le bloc de validation suivant était répété dans trois méthodes :

```java
if (id <= 0) {
    throw new IllegalArgumentException(
        "L'identifiant doit être positif."
    );
}
```

**Après :**

Une méthode commune a été créée :

```java
private void verifierIdentifiant(int id) {
    if (id <= 0) {
        throw new IllegalArgumentException(
            "L'identifiant doit être positif."
        );
    }
}
```

Les trois méthodes utilisent désormais l'appel suivant :

```java
verifierIdentifiant(id);
```

**Méthodes concernées :**

- `marquerTacheTerminee()`
- `modifierPrioriteTache()`
- `modifierEcheanceTache()`

**Amélioration apportée :**

La validation de l'identifiant est centralisée dans une seule méthode. Cette modification réduit la duplication du code et facilite la maintenance. Toute modification future de cette règle de validation pourra être effectuée à un seul endroit.

**Traçabilité :**

- Issue principale : [#37 — Réaliser les refactorings du code](https://github.com/INF1763-Groupe05/TaskFlow-MP1/issues/37)
- Branche : `refactor/R2-validation-identifiant`
- Pull Request : lien à compléter.
- Résultats des vérifications finales : à confirmer.