package com.example.bank.transaction.sample.service;

import com.example.bank.transaction.sample.model.Account;
import com.example.bank.transaction.sample.model.Credit;
import com.example.bank.transaction.sample.model.Debit;

import java.util.concurrent.CompletableFuture;

public interface TransactionService {
    CompletableFuture<Account> creditAmount(String username, Credit credit);
    CompletableFuture<Account> debitAmount(String username, Debit debit);
}
