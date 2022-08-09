package com.example.bank.transaction.sample.model;

import com.example.bank.transaction.sample.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class Account {
    private String username;
    private Currency currency;
    private BigDecimal balance;
}
