package com.abc;

import com.abc.domain.Transaction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    DateProvider dateProvider = new DateProvider();

    @Test
    public void createTransaction() {
        Transaction t = new Transaction(5, dateProvider.now());
        assertTrue(t instanceof Transaction);
    }

    @Test
    public void createTransactionCheckAmount() {
        double amount = 10;
        Transaction t = new Transaction(amount, dateProvider.now());
        assertTrue(t instanceof Transaction);
        assertEquals(amount, t.getAmount(), 0);
    }
}
