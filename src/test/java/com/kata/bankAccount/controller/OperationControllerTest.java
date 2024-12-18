package com.kata.bankAccount.controller;

import com.kata.bankAccount.dto.OperationDTO;
import com.kata.bankAccount.enums.OperationType;
import com.kata.bankAccount.exception.NoOperationsForAccountException;
import com.kata.bankAccount.service.OperationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OperationController.class)
class OperationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OperationService operationService;

    // ===== OK CASES =====

    @Test
    void getOperations_WithValidAccountId_ShouldReturnOperationList() throws Exception {
        // Setup
        OperationDTO operation1 = OperationDTO.builder()
                .id(1L)
                .operationType(OperationType.DEPOSIT)
                .amount(500.0)
                .PostOperationBalance(400.0)
                .date(LocalDateTime.now())
                .build();

        OperationDTO operation2 = OperationDTO.builder()
                .id(2L)
                .operationType(OperationType.WITHDRAWAL)
                .PostOperationBalance(400.0)
                .amount(200.0)
                .date(LocalDateTime.now())
                .build();


        when(operationService.getAllAccountOperations(1L))
                .thenReturn(Arrays.asList(operation1, operation2));

        // Test & Assert
        mockMvc.perform(get("/api/operations/1/history")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].operationType").value(OperationType.DEPOSIT.toString()))
                .andExpect(jsonPath("$[0].amount").value(500.0))
                .andExpect(jsonPath("$[0].postOperationBalance").value(400.0))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].operationType").value(OperationType.WITHDRAWAL.toString()))
                .andExpect(jsonPath("$[1].amount").value(200.0))
                .andExpect(jsonPath("$[0].postOperationBalance").value(400.0));

    }

    @Test
    void getOperations_WithNoOperations_ShouldReturnEmptyList() throws Exception {
        // Setup
        when(operationService.getAllAccountOperations(1L))
                .thenReturn(Collections.emptyList());

        // Test and Asset
        mockMvc.perform(get("/api/operations/1/history")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    // ===== KO CASES =====

    @Test
    void getOperations_WithInvalidAccountId_ShouldReturnNotFound() throws Exception {
        // Setup
        when(operationService.getAllAccountOperations(999L))
                .thenThrow(new NoOperationsForAccountException("No operations found for account with ID 999"));

        // Test & Assert
        mockMvc.perform(get("/api/operations/999/history")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.details").value("No operations found for account with ID 999"))
                .andExpect(jsonPath("$.statusCode").value(404));
    }
}
    