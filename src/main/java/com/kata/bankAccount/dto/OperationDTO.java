package com.kata.bankAccount.dto;

import com.kata.bankAccount.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OperationDTO {

private Long id;

private LocalDateTime date;

private OperationType operationType;

private Double amount;

private Double PostOperationBalance;

private AccountDTO account;
}
