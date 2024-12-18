package com.kata.bankAccount.model;

import com.kata.bankAccount.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //LocalDateTime: Ideal for most bank transactions that don't need time zone management.
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    private Double amount;

    private Double PostOperationBalance;

    //fetch = FetchType.LAZY (default): This optimizes performance by loading the transactions only when explicitly accessed, reducing unnecessary database queries.
    //Non-null constraint on transaction.account: Ensures every transaction must be tied to an account, adhering to business logic where every transaction requires an associated account.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
