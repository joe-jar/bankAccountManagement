package com.kata.bankAccount.service;

import com.kata.bankAccount.dto.AccountDTO;

public interface AccountService {

     AccountDTO deposit(Long id, Double amount);

    AccountDTO withdraw(Long id, Double amount);

    AccountDTO getAccountStatement(Long id);
}