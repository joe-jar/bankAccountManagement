package com.kata.bankAccount.exception;

public class NoSuchAccountException extends RuntimeException {
    public NoSuchAccountException(String message) {
        super(message);
    }
}
