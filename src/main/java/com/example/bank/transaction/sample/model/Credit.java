package com.example.bank.transaction.sample.model;

import com.example.bank.transaction.sample.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Credit {

    private BigDecimal amount;
    private Currency currency;
}
