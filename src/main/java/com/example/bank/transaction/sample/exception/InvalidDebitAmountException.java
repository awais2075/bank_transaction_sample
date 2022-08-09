package com.example.bank.transaction.sample.exception;

public class InvalidDebitAmountException extends RuntimeException{
    public InvalidDebitAmountException(String message) {
        super(message);
    }
}
