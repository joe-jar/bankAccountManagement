
# Bank Account Management

## Description
A Java-based project simulating basic banking operations, including access to account statements, deposits, withdrawals, and transaction history. Designed to display SOLID principles and implement clean, maintainable code.

The project is developed with Spring Boot and H2 Database (an embedded in-memory database).

Upon running the application, the account table in the database is populated (via execution of `src/main/resources/import.sql`), so we can test the endpoints right away.

## Features
- Deposit and withdraw money
- View current account statement
- View transaction history

## Technologies
- Java 21
- Spring Boot 3.3.5

## Setup Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/joe-jar/bankAccountManagement.git
   cd bankAccountManagement
   mvn clean install
   ```

2. **Configure the Application Settings**:
   The file `src/main/resources/application.properties` contains H2 database configuration (including credentials), application name, and port (8080).

3. **Build the Project with Maven**:
   From the root directory of the project, run the following command to download dependencies and build the project:
   ```bash
   mvn clean install
   ```

## Running the application
   ```bash
   mvn spring-boot:run
   ```

## Unit Tests
Unit tests are written following TDD, covering all valid and invalid use cases. To run the tests, use the following command:
   ```bash
   mvn test
   ```

## Endpoints

For all use cases, use `1` or `2` as the account ID (these are the identifiers of the accounts persisted upon running the app).

### Get Account Statement
- **URL**: `/api/accounts/{id}/statement`
- **Method**: `GET`
- **Description**: Retrieves the account statement for a specific account using its ID.

### Deposit Money
- **URL**: `/api/accounts/{id}/deposit?amount={amount}`
- **Method**: `POST`
- **Description**: Deposits a specified amount into the account identified by `id`.

### Withdraw Money
- **URL**: `/api/accounts/{id}/withdraw?amount={amount}`
- **Method**: `POST`
- **Description**: Withdraws a specified amount from the account identified by `id`.

### Get Operations History
- **URL**: `/api/operations/{id}/history`
- **Method**: `GET`
- **Description**: Retrieves the operation history for a specific account using its ID.
