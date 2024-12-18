package com.kata.bankAccount.controller;

import com.kata.bankAccount.dto.OperationDTO;
import com.kata.bankAccount.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/operations")
public class OperationController {

    private final OperationService operationService;

    // Constructor injection is used to provide the OperationService dependency.
    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    /**
     * Retrieves the transaction history for a specific account.
     *
     * @param id the ID of the account whose operations are to be retrieved
     * @return a ResponseEntity containing a list of OperationDTO objects representing the account's transaction history
     */
    @GetMapping("/{id}/history")
    public ResponseEntity<List<OperationDTO>> getAccountOperations(@PathVariable Long id) {
        List<OperationDTO> operations = operationService.getAllAccountOperations(id); // Fetch all operations for the account
        return ResponseEntity.ok(operations); // Return 200 OK with the list of operations
    }
}
