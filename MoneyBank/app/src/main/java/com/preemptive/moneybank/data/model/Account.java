package com.preemptive.moneybank.data.model;

import java.util.Objects;

public class Account {

    private final String accountNumber;
    private final double balance;

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

}
