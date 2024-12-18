package com.kata.bankAccount.util;

import com.kata.bankAccount.exception.InvalideAmountException;

public class ValidationUtils {

    private ValidationUtils() {
        // Prevent instantiation
    }

    public static void validateAmount(Double amount) {
        if (amount == null || amount <= 0) {
            throw new InvalideAmountException("Amount must be positive.");
        }
    }

}
