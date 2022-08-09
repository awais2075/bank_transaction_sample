package com.example.bank.transaction.sample.exception;

import java.util.function.Supplier;

public class UserNotFoundException extends RuntimeException implements Supplier<UserNotFoundException> {
    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public UserNotFoundException get() {
        return this;
    }
}
