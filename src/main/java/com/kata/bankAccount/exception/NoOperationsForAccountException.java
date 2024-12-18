package com.kata.bankAccount.exception;

public class NoOperationsForAccountException extends RuntimeException {
    public NoOperationsForAccountException(String message) {
        super(message);
    }
}

