package com.kata.bankAccount.service;


import com.kata.bankAccount.dto.AccountDTO;
import com.kata.bankAccount.enums.OperationType;
import com.kata.bankAccount.exception.BalanceNotSufficientException;
import com.kata.bankAccount.exception.InvalideAmountException;
import com.kata.bankAccount.exception.NoSuchAccountException;
import com.kata.bankAccount.model.Account;
import com.kata.bankAccount.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ModelMapper modelMapper;

    private Account account;
    private AccountDTO accountDTO;

    @BeforeEach
    void setup() {
        // Setup a sample account
         account = Account.builder()
                .id(1L)
                .balance(1000.0)
                .operations(new ArrayList<>())
                .build();

         accountDTO = AccountDTO.builder()
                .id(1L)
                .balance(1000.0)
                .build();
    }

    @Test
    void testDeposit_success() {
        // Setup
        Double depositAmount = 200.0;
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);
        when(modelMapper.map(account, AccountDTO.class)).thenReturn(accountDTO);

        // Test
        AccountDTO updatedAccount = accountService.deposit(1L, depositAmount);

        // Assert
        assertNotNull(updatedAccount);
        assertEquals(1200.0, account.getBalance());
        verify(accountRepository, times(1)).save(account);
        assertEquals(1, account.getOperations().size());
        assertEquals(OperationType.DEPOSIT, account.getOperations().getFirst().getOperationType());
        assertEquals(account.getBalance(), account.getOperations().getFirst().getPostOperationBalance());

    }

    @Test
    void testDeposit_invalidAmount_throwsException() {
        // Setup
        Double invalidAmount = -50.0;

        // Test & Assert
        assertThrows(InvalideAmountException.class, () -> accountService.deposit(1L, invalidAmount));
        verify(accountRepository, never()).save(any());
    }

    @Test
    void testDeposit_accountNotFound_throwsException() {
        // Setup
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        // Test & Assert
        assertThrows(NoSuchAccountException.class, () -> accountService.deposit(1L, 100.0));
        verify(accountRepository, never()).save(any());
    }

    @Test
    void testWithdraw_success() {
        // Setup
        Double withdrawAmount = 200.0;
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);
        when(modelMapper.map(account, AccountDTO.class)).thenReturn(accountDTO);

        // Test
        AccountDTO updatedAccount = accountService.withdraw(1L, withdrawAmount);

        // Assert
        assertNotNull(updatedAccount);
        assertEquals(800.0, account.getBalance());
        verify(accountRepository, times(1)).save(account);
        assertEquals(1, account.getOperations().size());
        assertEquals(OperationType.WITHDRAWAL, account.getOperations().getFirst().getOperationType());
        assertEquals(account.getBalance(), account.getOperations().getFirst().getPostOperationBalance());
    }

    @Test
    void testWithdraw_insufficientBalance_throwsException() {
        // Setup
        Double withdrawAmount = 1500.0;
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        // Test & Assert
        assertThrows(BalanceNotSufficientException.class, () -> accountService.withdraw(1L, withdrawAmount));
        verify(accountRepository, never()).save(any());
    }

    @Test
    void testWithdraw_accountNotFound_throwsException() {
        // Setup
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        // Test & Assert
        assertThrows(NoSuchAccountException.class, () -> accountService.withdraw(1L, 100.0));
        verify(accountRepository, never()).save(any());
    }

    @Test
    void testGetAccountStatement_success() {
        // Setup
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(modelMapper.map(account, AccountDTO.class)).thenReturn(accountDTO);

        // Test
        AccountDTO result = accountService.getAccountStatement(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAccountStatement_accountNotFound_throwsException() {
        // Setup
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        // Test & Assert
        assertThrows(NoSuchAccountException.class, () -> accountService.getAccountStatement(1L));
        verify(accountRepository, times(1)).findById(1L);
    }
}