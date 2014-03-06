package com.abc.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    public static final String NEW_LINE = "\n";
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = Collections.synchronizedList(new ArrayList<Account>());
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        String statement = "Statement for " + name + NEW_LINE;
        double total = 0.0;
        for (Account a : accounts) {
            statement += NEW_LINE + statementForAccount(a) + NEW_LINE;
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        StringBuffer buffer = new StringBuffer();

        buffer.append(a.getAccountType().getDescription());
        buffer.append(NEW_LINE);
        double total = populateTransactions(a, buffer);
        buffer.append("Total ");
        buffer.append(toDollars(total));
        return buffer.toString();
    }

    // Now total up all the transactions
    private double populateTransactions(Account a, StringBuffer buffer) {
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
            buffer.append("  ");
            buffer.append(t.getAmount() < 0 ? "withdrawal" : "deposit");
            buffer.append(" ");
            buffer.append(toDollars(t.getAmount()));
            buffer.append(NEW_LINE);
            total += t.getAmount();
        }
        return total;
    }

    private String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }
}
