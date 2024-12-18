package com.kata.bankAccount.service;

import com.kata.bankAccount.dto.OperationDTO;
import com.kata.bankAccount.enums.OperationType;
import com.kata.bankAccount.exception.NoOperationsForAccountException;
import com.kata.bankAccount.model.Operation;
import com.kata.bankAccount.repository.OperationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OperationServiceImplTest {

    @InjectMocks
    private OperationServiceImpl operationService;

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private ModelMapper modelMapper;

    private List<Operation> operations;
    private OperationDTO operationDTO;
    private Operation operation;


    @BeforeEach
    void setup() {
        operationDTO = OperationDTO.builder()
                .id(1L)
                .amount(200.0)
                .operationType(OperationType.DEPOSIT)
                .build();

        operation = Operation.builder()
                .id(1L)
                .amount(200.0)
                .operationType(OperationType.DEPOSIT)
                .build();

        operations = Arrays.asList(operation);
    }

    @Test
    void testGetAllAccountOperations_success() {
        // Setup
        Long accountId = 1L;
        when(operationRepository.findByAccountId(accountId)).thenReturn(operations);
        when(modelMapper.map(any(Operation.class), eq(OperationDTO.class))).thenReturn(operationDTO);

        // Test
        List<OperationDTO> result = operationService.getAllAccountOperations(accountId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(200.0, result.get(0).getAmount());
        assertEquals(OperationType.DEPOSIT, result.get(0).getOperationType());
        verify(operationRepository, times(1)).findByAccountId(accountId);
        verify(modelMapper, times(1)).map(any(Operation.class), eq(OperationDTO.class));
    }

    @Test
    void testGetAllAccountOperations_noOperations_throwsException() {
        // Setup
        Long accountId = 1L;
        when(operationRepository.findByAccountId(accountId)).thenReturn(Arrays.asList());

        // Test & Assert
        NoOperationsForAccountException exception = assertThrows(NoOperationsForAccountException.class,
                () -> operationService.getAllAccountOperations(accountId));
        assertEquals("No operations found for account ID: 1", exception.getMessage());
        verify(operationRepository, times(1)).findByAccountId(accountId);
    }

    @Test
    void testGetAllAccountOperations_accountNotFound_throwsException() {
        // Setup
        Long accountId = 1L;
        when(operationRepository.findByAccountId(accountId)).thenReturn(null);

        // Test & Assert
        NoOperationsForAccountException exception = assertThrows(NoOperationsForAccountException.class,
                () -> operationService.getAllAccountOperations(accountId));
        assertEquals("No operations found for account ID: 1", exception.getMessage());
        verify(operationRepository, times(1)).findByAccountId(accountId);
    }
}