
# Bank Account Management

## Description

Un projet en Java simulant des opérations bancaires de base, y compris l'accès aux relevés de compte, les dépôts, les retraits et l'historique des transactions. Conçu pour illustrer les principes SOLID et mettre en œuvre un code propre et maintenable.

Le projet est développé avec Spring Boot et H2 Database (une base de données embarquée en mémoire).

Lors de l'exécution de l'application, la table des comptes dans la base de données est remplie (via l'exécution de src/main/resources/import.sql), ce qui permet de tester immédiatement les endpoints.


## Fonctionnalités
- Déposer et retirer de l'argent
- Consulter le relevé actuel du compte
- Consulter l'historique des transactions

## Technologies
- Java 21
- Spring Boot 3.3.5

## Instructions d'installation
1. Clonez le dépôt :
   ```bash
   git clone https://github.com/joe-jar/bankAccountManagement.git
   cd bankAccountManagement
   mvn clean install
   ```

2. **Configurer les paramètres de l'application** :
   Le fichier `src/main/resources/application.properties` contient la configuration de la base de données H2 (y compris les credentials), le nom de l'application et le port (8080).

3. **Construire le projet avec Maven** :
   Depuis le répertoire racine du projet, exécutez la commande suivante pour inclure les dépendances et construire le projet :
   ```bash
   mvn clean install
   ```

## Exécution de l'application
   ```bash
   mvn spring-boot:run
   ```

## Tests unitaires
Les tests unitaires sont écrits en suivant le TDD, couvrant tous les cas ok et ko. Pour exécuter les tests, utilisez la commande suivante :
   ```bash
   mvn test
   ```

## Endpoints

Pour tous les cas d'utilisation, utilisez `1` ou `2` comme ID de compte (ce sont les identifiants des comptes persistés lors de l'exécution de l'application).

### Obtenir le relevé de compte
- **URL** : `/api/accounts/{id}/statement`
- **Méthode** : `GET`
- **Description** : Récupère le relevé de compte pour un compte spécifique en utilisant son ID.

### Déposer de l'argent
- **URL** : `/api/accounts/{id}/deposit?amount={amount}`
- **Méthode** : `POST`
- **Description** : Dépose un montant spécifié sur le compte identifié par `id`.

### Retirer de l'argent
- **URL** : `/api/accounts/{id}/withdraw?amount={amount}`
- **Méthode** : `POST`
- **Description** : Retire un montant spécifié du compte identifié par `id`.

### Obtenir l'historique des opérations
- **URL** : `/api/operations/{id}/history`
- **Méthode** : `GET`
- **Description** : Récupère l'historique des opérations pour un compte spécifique en utilisant son ID.
