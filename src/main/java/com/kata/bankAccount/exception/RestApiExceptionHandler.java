package com.kata.bankAccount.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestApiExceptionHandler {

    // Handle BalanceNotSufficientException
    @ExceptionHandler(BalanceNotSufficientException.class)
    public ResponseEntity<Map<String, Object>> handleBalanceNotSufficient(BalanceNotSufficientException ex) {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // Handle NegativeAmountException
    @ExceptionHandler(InvalideAmountException.class)
    public ResponseEntity<Map<String, Object>> handleNegativeAmount(InvalideAmountException ex) {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // Handle NoOperationsForAccountException
    @ExceptionHandler(NoOperationsForAccountException.class)
    public ResponseEntity<Map<String, Object>> handleNoOperations(NoOperationsForAccountException ex) {
        return generateErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // Handle NoSuchAccountException
    @ExceptionHandler(NoSuchAccountException.class)
    public ResponseEntity<Map<String, Object>> handleNoSuchAccount(NoSuchAccountException ex) {
        return generateErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // Utility method for creating error responses
    private ResponseEntity<Map<String, Object>> generateErrorResponse(HttpStatus status, String errorMessage) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("time", LocalDateTime.now());
        responseBody.put("statusCode", status.value());
        responseBody.put("errorType", status.getReasonPhrase());
        responseBody.put("details", errorMessage);

        return new ResponseEntity<>(responseBody, status);
    }
}
