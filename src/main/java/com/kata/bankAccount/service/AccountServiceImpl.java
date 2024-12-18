package com.kata.bankAccount.service;

import com.kata.bankAccount.dto.AccountDTO;
import com.kata.bankAccount.enums.OperationType;
import com.kata.bankAccount.exception.BalanceNotSufficientException;
import com.kata.bankAccount.exception.NoSuchAccountException;
import com.kata.bankAccount.model.Account;
import com.kata.bankAccount.model.Operation;
import com.kata.bankAccount.repository.AccountRepository;
import com.kata.bankAccount.util.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class AccountServiceImpl implements AccountService {

    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;

    /**
     * Constructor-based dependency injection for ModelMapper and AccountRepository.
     *
     * @param modelMapper the mapper to convert between entities and DTOs
     * @param accountRepository the repository to handle database operations for accounts
     */
    @Autowired
    public AccountServiceImpl(ModelMapper modelMapper, AccountRepository accountRepository) {
        this.modelMapper = modelMapper;
        this.accountRepository = accountRepository;
    }

    /**
     * Deposit a specific amount into an account
     * with a serializable isolation transaction in case of concurrent transactions.
     *
     * @param id the account ID
     * @param amount the amount to deposit
     * @return an updated AccountDTO reflecting the new balance
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE) // Ensures serialized transactions for data consistency
    public AccountDTO deposit(Long id, Double amount) {
        ValidationUtils.validateAmount(amount); // Validate that the amount is positive
        Account account = findAccount(id); // Fetch the account or throw an exception if not found
        account.setBalance(account.getBalance() + amount); // Update the account balance
        addOperation(account, OperationType.DEPOSIT, amount); // Log the deposit operation
        return modelMapper.map(accountRepository.save(account), AccountDTO.class); // Save and map to DTO
    }

    /**
     * Withdraw a specific amount from an account,
     * with a serializable isolation transaction in case of concurrent transactions
     * ensuring sufficient balance.
     *
     * @param id the account ID
     * @param amount the amount to withdraw
     * @return an updated AccountDTO reflecting the new balance
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public AccountDTO withdraw(Long id, Double amount) {
        ValidationUtils.validateAmount(amount); // Validate that the amount is positive
        Account account = findAccount(id); // Fetch the account or throw an exception if not found
        if (account.getBalance() < amount) { // Check for sufficient funds
            throw new BalanceNotSufficientException("Insufficient funds.");
        }
        account.setBalance(account.getBalance() - amount); // Update the account balance
        addOperation(account, OperationType.WITHDRAWAL, amount); // Log the withdrawal operation
        return modelMapper.map(accountRepository.save(account), AccountDTO.class); // Save and map to DTO
    }

    /**
     * Retrieve the statement for a specific account.
     *
     * @param id the account ID
     * @return the account's details as an AccountDTO
     */
    @Override
    @Transactional
    public AccountDTO getAccountStatement(Long id) {
        return modelMapper.map(findAccount(id), AccountDTO.class); // Fetch and map account details
    }

    /**
     *  method to find an account by ID or throw an exception if not found.
     *
     * @param id the account ID
     * @return the Account entity
     */
    private Account findAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NoSuchAccountException("No such account with ID: " + id));
    }

    /**
     *  method to log operations (deposit or withdrawal) for an account.
     *
     * @param account the account where the operation is performed
     * @param type the type of operation (DEPOSIT or WITHDRAWAL)
     * @param amount the amount involved in the operation
     */
    private void addOperation(Account account, OperationType type, Double amount) {
        if (account.getOperations() == null) { // Initialize operations list if null
            account.setOperations(new ArrayList<>());
        }
        // Create a new operation object
        Operation operation = Operation.builder()
                .operationType(type)
                .amount(amount)
                .PostOperationBalance(account.getBalance())
                .account(account)
                .date(LocalDateTime.now())
                .build();
        account.getOperations().add(operation);
    }
}
