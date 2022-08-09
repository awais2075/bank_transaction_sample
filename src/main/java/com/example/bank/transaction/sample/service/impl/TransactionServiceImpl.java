package com.example.bank.transaction.sample.service.impl;

import com.example.bank.transaction.sample.enums.Currency;
import com.example.bank.transaction.sample.exception.InsufficientAccountBalanceException;
import com.example.bank.transaction.sample.exception.InvalidCreditAmountException;
import com.example.bank.transaction.sample.exception.InvalidDebitAmountException;
import com.example.bank.transaction.sample.exception.UserNotFoundException;
import com.example.bank.transaction.sample.model.Account;
import com.example.bank.transaction.sample.model.Credit;
import com.example.bank.transaction.sample.model.Debit;
import com.example.bank.transaction.sample.service.TransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Override
    public CompletableFuture<Account> creditAmount(String username, Credit credit) {
        return CompletableFuture.supplyAsync(() -> getAccount(username))
                .thenApply(account -> {
                    if (isValidCreditAmount(credit.getAmount())) {
                        var exchangeRate = getExchangeRate(credit.getCurrency(), account.getCurrency());
                        var creditAmount = credit.getAmount().multiply(exchangeRate);
                        var newAccountBalance = account.getBalance().add(creditAmount);
                        account.setBalance(newAccountBalance);
                        return account;
                    }
                    throw new InvalidCreditAmountException("Credit Amount Must be Positive");
                });
    }

    @Override
    public CompletableFuture<Account> debitAmount(String username, Debit debit) {
        return CompletableFuture.supplyAsync(() -> getAccount(username))
                .thenApply(account -> {
                    if(isValidDebitAmount(debit.getAmount())) {
                        var exchangeRate = getExchangeRate(debit.getCurrency(), account.getCurrency());
                        var debitAmount = debit.getAmount().multiply(exchangeRate);
                        var newAccountBalance = account.getBalance().subtract(debitAmount);
                        if(newAccountBalance.compareTo(BigDecimal.ZERO) < 0) {
                            throw new InsufficientAccountBalanceException("Account Balance is Insufficient");
                        }
                        account.setBalance(newAccountBalance);
                    }
                    throw new InvalidDebitAmountException("Debit Amount Must be Positive");
                });
    }

    public List<Account> accounts() {
        return List.of(
                new Account("windows-7", Currency.USD, BigDecimal.valueOf(7000)),
                new Account("windows-8", Currency.USD, BigDecimal.valueOf(8000)),
                new Account("windows-10", Currency.USD, BigDecimal.valueOf(10000)),
                new Account("ubuntu-18.04", Currency.EUR, BigDecimal.valueOf(18400))
        );
    }

    private BigDecimal getExchangeRate(Currency fromCurrency, Currency toCurrency) {
        return BigDecimal.ONE;
    }

    private Account getAccount(String username) {
        return accounts().stream().filter(account -> account.getUsername().equals(username)).findFirst()
                .orElseThrow(new UserNotFoundException(username.concat(" not found...")));
    }

    private boolean isValidCreditAmount(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    private boolean isValidDebitAmount(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }
}
