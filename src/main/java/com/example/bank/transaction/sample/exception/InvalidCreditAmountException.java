package com.example.bank.transaction.sample.exception;

public class InvalidCreditAmountException extends RuntimeException{
    public InvalidCreditAmountException(String message) {
        super(message);
    }
}
