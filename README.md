# Bank Account Management

## Description
A Java-based project simulating basic banking operations, including access to account statement, deposits, withdrawals, and transaction history. Designed to practice object-oriented solid principles and implement clean, maintainable code.

the project is developped with Spring boot,H2 Database an embedded in-memory database.

upon running the application the account table in the database is filled ( execution src/main/resources/import.sql) so we can test the endpoints right away 
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
   ```bash
   cd bankAccountManagement
   ```bash
   mvn clean install



## Endpoints

for all the use cases use  1 or 2 as id (the identifiers of the the accounts persisted upon running the app)
### Get Account Statement
- **URL**: `/api/accounts/{id}/statement`
- **Method**: `GET`
- **Description**: Retrieves the account statement for a specific account using its ID.

### Deposit Money
- **URL**: `/api/accounts/{id}/deposit`
- **Method**: `POST`
- **Description**: Deposits a specified amount into the account identified by `id`.

### Withdraw Money
- **URL**: `/api/accounts/{id}/withdraw`
- **Method**: `POST`
- **Description**: Withdraws a specified amount from the account identified by `id`.

### Get Operations History
- **URL**: `/api/operations/{id}/history`
- **Method**: `GET`
- **Description**: Retrieves the operation history for a specific account using its ID.