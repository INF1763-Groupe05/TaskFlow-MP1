# Construction et dépendances — TaskFlow

## 1. Introduction

Dans le cadre du mini-projet 2, Maven est utilisé pour automatiser la construction du projet, gérer les dépendances et produire un JAR exécutable.

## 2. Structure du projet Maven

Cette section présentera l'organisation des fichiers et des répertoires après la migration vers Maven.

## 3. Configuration du fichier pom.xml

Cette section décrira les différentes parties du fichier pom.xml et leur rôle dans la construction du projet.

## 4. Gestion des dépendances

Les dépendances utilisées, leurs versions et la justification de leur choix seront documentées après leur intégration.

## 5. Arbre des dépendances

La commande suivante permet d'afficher l'arbre des dépendances :

```bash
./mvnw dependency:tree
```

Résultat : à compléter.

## 6. Construction du projet

La construction du projet s'effectue avec le Maven Wrapper :

```bash
./mvnw clean package
```

## 7. Production du JAR exécutable

Le plugin Maven utilisé, sa configuration et son intégration dans le cycle de vie Maven seront documentés après la configuration finale.

## 8. Problème Log4j

- Description du problème : à compléter.
- Cause du problème : à compléter.
- Solution retenue : à compléter.
- Résultat de la vérification : à compléter.