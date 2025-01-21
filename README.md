# Application de Gestion des Notes

## Description

Ce projet est la version java de l'application de gestion des notes de [ItsAlexousd](https://github.com/ItsAlexousd/iut-laval-grades-api). \
Cette application est conçue pour gérer les notes des étudiants. Elle permet de stocker, récupérer et manipuler les informations relatives aux étudiants, aux cours et aux notes. L'application génère également des relevés de notes au format PDF.

## Prérequis

- Java 21
- Maven
- Docker (pour les tests d'intégration)

## Fonctionnalités

- Gestion des étudiants : ajout, modification, suppression et récupération des informations des étudiants.
- Gestion des cours : ajout, modification, suppression et récupération des informations des cours.
- Gestion des notes : ajout, modification, suppression et récupération des notes des étudiants.
- Génération de relevés de notes au format PDF.
- Statistiques globales et par cours.

## Structure du Projet

Le projet est structuré en plusieurs packages :

- `com.course.backend.model` : contient les classes de modèle représentant les entités de l'application.
- `com.course.backend.repository` : contient les interfaces de repository pour accéder aux données.
- `com.course.backend.service` : contient les services pour la logique métier.
- `com.course.backend.controller` : contient les contrôleurs REST pour exposer les API.
- `com.course.backend.dto` : contient les interfaces pour les objets de transfert de données.
- `com.course.backend.request` : contient les classes de requête pour les API.
- `com.course.backend.response` : contient les classes de réponse pour les API.

## Tests

L'application est couverte par des tests unitaires et des tests d'intégration. Les tests sont situés dans le répertoire `src/test/java/com/course/backend`. Les principaux tests incluent :

- Tests unitaires pour les services (`service`).
- Tests d'intégration pour les contrôleurs (`controller`).
- Tests d'intégration pour les repositories (`repository`).

Les tests sont exécutés automatiquement lors de chaque build grâce à GitHub Actions. Cependant les test d'intégration ne s'exécutent pas dans les github-actions que ce soit self-hoted ou avec ubuntu-latest et ubuntu-18.04.

## GitHub Actions

Le projet utilise GitHub Actions pour automatiser les processus de build, de test et de déploiement. Deux workflows principaux sont définis :

- `test.yml` : Ce workflow est déclenché sur chaque pull request vers la branche `main`. Il effectue les actions suivantes :
  - Vérifie le code source.
  - Configure Docker Buildx.
  - Construit l'image Docker.

- `deploy.yml` : Ce workflow est déclenché sur chaque push vers la branche `main`. Il effectue les actions suivantes :
  - Vérifie le code source.
  - Configure Docker Buildx.
  - Se connecte à Docker Hub.
  - Construit et pousse l'image Docker vers le registre Docker.

Ces workflows sont exécutés automatiquement lors de chaque push ou pull request vers la branche `main` sur un self-hosted runner.

## Documentation

### Javadoc

La documentation Javadoc est générée pour toutes les classes et méthodes publiques de l'application. Vous pouvez y accéder en ouvrant le fichier `index.html` situé dans le répertoire `target/site/apidocs`.

### Swagger

L'application utilise Swagger pour documenter et tester les API REST. Vous pouvez accéder à l'interface Swagger à l'URL suivante : `[URL]/swagger-ui.html`

### Couverture de Code avec JaCoCo

L'application utilise JaCoCo pour mesurer la couverture de code des tests. Le rapport de couverture est généré dans le répertoire `target/site/jacoco` après l'exécution des tests. Vous pouvez ouvrir le fichier `index.html` pour voir le rapport détaillé.


## Configuration

Pour configurer l'application, modifiez les fichiers de configuration situés dans le répertoire `src/main/resources` ainsi que certaines dépendances dans le `pom.xml`.

## Installation et Exécution

1. Clonez le dépôt :
   ```bash
   git clone https://github.com/votre-utilisateur/votre-repo.git
   cd votre-repo
    ```
   
2. Compilez le projet :
    ```bash
    mvn clean -U install
    ```

3. Exécutez l'application :
   ```bash
   mvn spring-boot:run
   ```

## Accessibilité à l'application

L'application est accessible à l'URL suivante : https://api.lilian-daurat.com/, tous les endpoints sont accessibles y compris Swagger à l'URL suivante : https://api.lilian-daurat.com/swagger-ui.html. \
L'image docker est disponible sur l'URL suivante : registry.lilian-daurat.com/cours/r5-08-back
