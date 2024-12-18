package com.kata.bankAccount.service;

import com.kata.bankAccount.dto.OperationDTO;

import java.util.List;


public interface OperationService {
    List<OperationDTO> getAllAccountOperations(Long id);
}