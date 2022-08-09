package com.example.bank.transaction.sample.exception;

public class InsufficientAccountBalanceException extends RuntimeException{
    public InsufficientAccountBalanceException(String message) {
        super(message);
    }
}
