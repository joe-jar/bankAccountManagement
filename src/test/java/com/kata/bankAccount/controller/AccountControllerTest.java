package com.kata.bankAccount.controller;

import com.kata.bankAccount.dto.AccountDTO;
import com.kata.bankAccount.exception.BalanceNotSufficientException;
import com.kata.bankAccount.exception.InvalideAmountException;
import com.kata.bankAccount.exception.NoSuchAccountException;
import com.kata.bankAccount.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    // ===== OK CASES =====

    @Test
    void getAccountStatement_WithValidId_ShouldReturnAccount() throws Exception {
        AccountDTO account = new AccountDTO(1L, 1500.0);

        when(accountService.getAccountStatement(1L)).thenReturn(account);

        mockMvc.perform(get("/api/accounts/1/statement"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.balance").value(1500.0));
    }

    @Test
    void deposit_WithValidAmount_ShouldReturnUpdatedAccount() throws Exception {
        AccountDTO updatedAccount = new AccountDTO(1L, 2000.0);

        when(accountService.deposit(1L, 500.0)).thenReturn(updatedAccount);

        mockMvc.perform(post("/api/accounts/1/deposit")
                        .param("amount", "500"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.balance").value(2000.0));
    }

    @Test
    void withdraw_WithSufficientBalance_ShouldReturnUpdatedAccount() throws Exception {
        AccountDTO updatedAccount = new AccountDTO(1L, 500.0);

        when(accountService.withdraw(1L, 500.0)).thenReturn(updatedAccount);

        mockMvc.perform(post("/api/accounts/1/withdraw")
                        .param("amount", "500"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.balance").value(500.0));
    }

    // ===== KO CASES =====

    @Test
    void withdraw_WithInsufficientBalance_ShouldReturnBadRequest() throws Exception {
        when(accountService.withdraw(1L, 2000.0))
                .thenThrow(new BalanceNotSufficientException("Insufficient balance"));

        mockMvc.perform(post("/api/accounts/1/withdraw")
                        .param("amount", "2000"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details").value("Insufficient balance"))
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @Test
    void deposit_WithInvalidAmount_ShouldReturnBadRequest() throws Exception {
        when(accountService.deposit(1L, -100.0))
                .thenThrow(new InvalideAmountException("Invalid deposit amount"));

        mockMvc.perform(post("/api/accounts/1/deposit")
                        .param("amount", "-100"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details").value("Invalid deposit amount"))
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @Test
    void getAccountStatement_WithNonExistingId_ShouldReturnNotFound() throws Exception {
        when(accountService.getAccountStatement(999L))
                .thenThrow(new NoSuchAccountException("Account not found"));

        mockMvc.perform(get("/api/accounts/999/statement"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.details").value("Account not found"))
                .andExpect(jsonPath("$.statusCode").value(404));
    }

}
