package com.example.bank.transaction.sample.service.impl;

import com.example.bank.transaction.sample.enums.Currency;
import com.example.bank.transaction.sample.exception.InsufficientAccountBalanceException;
import com.example.bank.transaction.sample.exception.InvalidCreditAmountException;
import com.example.bank.transaction.sample.exception.InvalidDebitAmountException;
import com.example.bank.transaction.sample.exception.UserNotFoundException;
import com.example.bank.transaction.sample.model.Account;
import com.example.bank.transaction.sample.model.Credit;
import com.example.bank.transaction.sample.model.Debit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class TransactionServiceImplTest {

    @MockBean
    private TransactionServiceImpl transactionService;

    @Test
    public void creditAmount_UserNotFoundException() {
        var username = "ABC";
        var credit = new Credit(BigDecimal.valueOf(100), Currency.USD);
        try {
            given(transactionService.creditAmount(username, credit))
                    .willThrow(new UserNotFoundException(username.concat(" not found...")));

            transactionService.creditAmount(username, credit);
        } catch (UserNotFoundException ex) {
            assertTrue(ex instanceof UserNotFoundException);
            assertTrue(ex.getMessage().equals(username.concat(" not found...")));
        }
    }

    @Test
    public void creditAmount_InvalidCreditAmountException() {
        var username = "windows-7";
        var credit = new Credit(BigDecimal.valueOf(-100), Currency.USD);
        try {
            given(transactionService.creditAmount(username, credit))
                    .willThrow(new InvalidCreditAmountException("Credit Amount Must be Positive"));

            transactionService.creditAmount(username, credit);
        } catch (InvalidCreditAmountException ex) {
            assertTrue(ex instanceof InvalidCreditAmountException);
            assertTrue(ex.getMessage().equals("Credit Amount Must be Positive"));
        }
    }

    @Test
    public void creditAmount_Success() {
        var username = "windows-7";
        var credit = new Credit(BigDecimal.valueOf(100), Currency.USD);
        var account = new Account(username, Currency.USD, BigDecimal.valueOf(7100));
        var completableFutureAccount = CompletableFuture.completedFuture(account);

        given(transactionService.creditAmount(username, credit))
                .willReturn(completableFutureAccount);
        transactionService.creditAmount(username, credit)
                .thenAccept(acc -> {
                    assertTrue(acc.getUsername().equals(account.getUsername()));
                    assertTrue(acc.getCurrency().equals(account.getCurrency()));
                    assertTrue(acc.getBalance().equals(account.getBalance()));
                });
    }

    @Test
    public void debitAmount_UserNotFoundException() {
        var username = "ABC";
        var debit = new Debit(BigDecimal.valueOf(100), Currency.USD);
        try {
            given(transactionService.debitAmount(username, debit))
                    .willThrow(new UserNotFoundException(username.concat(" not found...")));

            transactionService.debitAmount(username, debit);
        } catch (UserNotFoundException ex) {
            assertTrue(ex instanceof UserNotFoundException);
            assertTrue(ex.getMessage().equals(username.concat(" not found...")));
        }
    }

    @Test
    public void debitAmount_InvalidCreditAmountException() {
        var username = "windows-7";
        var debit = new Credit(BigDecimal.valueOf(-100), Currency.USD);
        try {
            given(transactionService.creditAmount(username, debit))
                    .willThrow(new InvalidDebitAmountException("Debit Amount Must be Positive"));

            transactionService.creditAmount(username, debit);
        } catch (InvalidDebitAmountException ex) {
            assertTrue(ex instanceof InvalidDebitAmountException);
            assertTrue(ex.getMessage().equals("Debit Amount Must be Positive"));
        }
    }

    @Test
    public void debitAmount_InsufficientAccountBalanceException() {
        var username = "windows-7";
        var debit = new Debit(BigDecimal.valueOf(7001), Currency.USD);
        try {
            given(transactionService.debitAmount(username, debit))
                    .willThrow(new InsufficientAccountBalanceException("Account Balance is Insufficient"));

            transactionService.debitAmount(username, debit);
        } catch (InsufficientAccountBalanceException ex) {
            assertTrue(ex instanceof InsufficientAccountBalanceException);
            assertTrue(ex.getMessage().equals("Account Balance is Insufficient"));
        }
    }

    @Test
    public void debitAmount_Success() {
        var username = "windows-7";
        var debit = new Debit(BigDecimal.valueOf(100), Currency.USD);
        var account = new Account(username, Currency.USD, BigDecimal.valueOf(6900));
        var completableFutureAccount = CompletableFuture.completedFuture(account);

        given(transactionService.debitAmount(username, debit))
                .willReturn(completableFutureAccount);
        transactionService.debitAmount(username, debit)
                .thenAccept(acc -> {
                    assertTrue(acc.getUsername().equals(account.getUsername()));
                    assertTrue(acc.getCurrency().equals(account.getCurrency()));
                    assertTrue(acc.getBalance().equals(account.getBalance()));
                });
    }
}