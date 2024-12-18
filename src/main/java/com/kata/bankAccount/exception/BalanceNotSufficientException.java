package com.kata.bankAccount.exception;

public class BalanceNotSufficientException extends RuntimeException {

    public BalanceNotSufficientException(String message) {
        super(message);
    }
}
