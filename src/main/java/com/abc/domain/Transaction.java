package com.abc.domain;

import java.util.Date;

public class Transaction {

    private final double amount;
    private final Date transactionDate;

    public Transaction(double amount, Date date) {
        this.amount = amount;
        this.transactionDate = date;
    }

    public final double getAmount() {
        return amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }
}
