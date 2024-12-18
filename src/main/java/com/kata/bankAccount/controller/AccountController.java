package com.kata.bankAccount.controller;

import com.kata.bankAccount.dto.AccountDTO;
import com.kata.bankAccount.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    // Constructor injection is used to provide the AccountService dependency.
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Retrieves the account statement for a specific account.
     *
     * @param id the ID of the account whose statement is to be retrieved
     * @return a ResponseEntity containing the AccountDTO for the requested account
     */
    @GetMapping("/{id}/statement")
    public ResponseEntity<AccountDTO> getAccountStatement(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountStatement(id));
    }

    /**
     * Deposits a specified amount into an account.
     *
     * @param id the ID of the account to deposit into
     * @param amount the amount to deposit (optional)
     * @return a ResponseEntity containing the updated AccountDTO
     */
    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountDTO> deposit(@PathVariable Long id, @RequestParam(required = false) Double amount) {
        return ResponseEntity.ok(accountService.deposit(id, amount));
    }

    /**
     * Withdraws a specified amount from an account.
     *
     * @param id the ID of the account to withdraw from
     * @param amount the amount to withdraw (optional)
     * @return a ResponseEntity containing the updated AccountDTO
     */
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<AccountDTO> withdraw(@PathVariable Long id, @RequestParam(required = false) Double amount) {
        return ResponseEntity.ok(accountService.withdraw(id, amount));
    }

}
