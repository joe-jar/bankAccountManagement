package com.kata.bankAccount.service;

import com.kata.bankAccount.dto.OperationDTO;
import com.kata.bankAccount.exception.NoOperationsForAccountException;
import com.kata.bankAccount.model.Operation;
import com.kata.bankAccount.repository.OperationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OperationServiceImpl implements OperationService {

    private final ModelMapper modelMapper;
    private final OperationRepository operationRepository;

    /**
     * Constructor-based dependency injection for ModelMapper and OperationRepository.
     *
     * @param modelMapper the mapper to convert between entities and DTOs
     * @param operationRepository the repository to handle database operations for account operations
     */
    @Autowired
    public OperationServiceImpl(ModelMapper modelMapper, OperationRepository operationRepository) {
        this.modelMapper = modelMapper;
        this.operationRepository = operationRepository;
    }

    /**
     * Retrieve all operations for a specific account.
     *
     * @param id the ID of the account
     * @return a list of OperationDTOs representing the account's operation history
     */
    @Override
    public List<OperationDTO> getAllAccountOperations(Long id) {
        // Fetch all operations for the given account ID
        List<Operation> accountOperations = operationRepository.findByAccountId(id);

        // Check if the list of operations is empty, and throw an exception if no operations are found
        Optional.ofNullable(accountOperations)
                .filter(operations -> !operations.isEmpty()) // Ensure the list is not empty
                .orElseThrow(() -> new NoOperationsForAccountException("No operations found for account ID: " + id));

        // Convert the list of Operation entities to OperationDTOs and return the result
        return accountOperations.stream()
                .map(operation -> modelMapper.map(operation, OperationDTO.class)) // Map each operation to a DTO
                .collect(Collectors.toList()); // Collect the DTOs into a list
    }
}
