package com.abc.domain;

import com.abc.AccountType;
import com.abc.DateProvider;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Account {

    private final AccountType accountType;
    private final List<Transaction> transactions;
    private final DateProvider dateProvider;

    public Account(AccountType accountType) {
        this(accountType, new DateProvider());
    }

    public Account(AccountType accountType, DateProvider dateProvider) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.dateProvider = dateProvider;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "amount must be greater than zero");
        } else {
            synchronized (transactions) {
                transactions.add(new Transaction(amount, dateProvider.now()));
            }
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "amount must be greater than zero");
        } else {
            synchronized (transactions) {
                transactions.add(new Transaction(-amount, dateProvider.now()));
            }
        }
    }

    public double interestEarned() {
        double amount = sumTransactions();
        return getAccountType().getCalculator().interestEarned(amount, getDaysToLastWithdrawl());
    }

    public double sumTransactions() {
        return checkIfTransactionsExist();
    }

    private double checkIfTransactionsExist() {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.getAmount();
        return amount;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    /**
     * Returns the number of days since last withdrawl. If there is no withdrawl, return -1.
     *
     * @return
     */
    public Long getDaysToLastWithdrawl() {
        List<Transaction> transactionList = getWithDrawlTransactions();
        if (!transactionList.isEmpty()) {
            Collections.sort(transactionList, new Comparator<Transaction>() {
                @Override
                public int compare(Transaction o1, Transaction o2) {
                    return o1.getTransactionDate().compareTo(o2.getTransactionDate());
                }
            });

            Date today = dateProvider.now();
            Date datelastWithDrawl = transactionList.get(transactionList.size() - 1).getTransactionDate();
            long diffInMilliSec = today.getTime() - datelastWithDrawl.getTime();
            TimeUnit timeUnit = TimeUnit.DAYS;
            return timeUnit.convert(diffInMilliSec, TimeUnit.MILLISECONDS);
        }
        return null;
    }

    private List<Transaction> getWithDrawlTransactions() {
        List<Transaction> withDrawlTransactions = new ArrayList<Transaction>(getTransactions());
        Iterator<Transaction> transactionIterator = withDrawlTransactions.iterator();
        while (transactionIterator.hasNext()) {
            Transaction t = transactionIterator.next();
            if (t.getAmount() >= 0) {
                transactionIterator.remove();
            }
        }
        return withDrawlTransactions;
    }
}
