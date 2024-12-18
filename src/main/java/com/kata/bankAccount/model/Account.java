package com.kata.bankAccount.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double balance = 0.0;

    //An Operation should never exist without an associated Account. Combining cascade = ALL and orphanRemoval = true enforces this constraint.
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
     private List<Operation> operations;
}