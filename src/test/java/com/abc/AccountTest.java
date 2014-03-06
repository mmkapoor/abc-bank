package com.abc;

import com.abc.domain.Account;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class AccountTest {

    public static final double DELTA = 1e-15;
    private DateProvider dateProvider = new DateProvider();

    @Test
    public void testDeposit() {
        Account account = new Account(AccountType.CHECKING, dateProvider);
        account.deposit(100);
        assertEquals(100, account.sumTransactions(), DELTA);
    }

    @Test
    public void testWithDraw() {
        Account account = new Account(AccountType.CHECKING, dateProvider);
        account.deposit(100);
        account.withdraw(50);
        assertEquals(50, account.sumTransactions(), DELTA);
    }

    @Test
    public void testWithDrawWithoutDeposit() {
        Account account = new Account(AccountType.CHECKING, dateProvider);
        account.withdraw(50);
        assertEquals(-50, account.sumTransactions(), DELTA);
    }

    @Test
    public void testCheckSavingInterestWithLessThan1000() {
        Account account = new Account(AccountType.SAVINGS, dateProvider);
        account.deposit(500);
        assertEquals(0.5, account.interestEarned(), DELTA);
    }

    @Test
    public void testCheckSavingInterestWithMoreThan1000() {
        Account account = new Account(AccountType.SAVINGS, dateProvider);
        account.deposit(1200);
        assertEquals(1.4, account.interestEarned(), DELTA);
    }

    @Test
    public void testCheckSavingInterestWith1000() {
        Account account = new Account(AccountType.SAVINGS, dateProvider);
        account.deposit(1000);
        assertEquals(1, account.interestEarned(), DELTA);
    }

    @Test
    public void testCheckDefaultInterest() {
        Account account = new Account(AccountType.CHECKING, dateProvider);
        account.deposit(800);
        assertEquals(0.8, account.interestEarned(), DELTA);
    }

    @Test
    public void testCheckMaxiSavingInterestWithdrawl() {
        Account account = new Account(AccountType.MAXI_SAVINGS, dateProvider);
        account.deposit(1000);
        account.withdraw(500);
        assertEquals(0.5, account.interestEarned(), DELTA);
    }

    @Test
    public void testCheckMaxiSavingInterestNoWithdrawl() {
        Account account = new Account(AccountType.MAXI_SAVINGS, dateProvider);
        account.deposit(1000);
        assertEquals(50, account.interestEarned(), DELTA);
    }

    @Test
    public void testTime() {
        MyDateProvider myDateProvider = new MyDateProvider();
        Account account = new MyAccount(AccountType.MAXI_SAVINGS, myDateProvider);
        account.deposit(200);
        account.withdraw(50);
        account.deposit(50);
        account.withdraw(50);

        Date datelastWithDrawl = new Date(114, 1, 18);
        long diffInMilliSec = myDateProvider.now().getTime() - datelastWithDrawl.getTime();
        TimeUnit timeUnit = TimeUnit.DAYS;
        long expectedValue = timeUnit.convert(diffInMilliSec, TimeUnit.MILLISECONDS);

        assertEquals(Long.valueOf(expectedValue), account.getDaysToLastWithdrawl());
    }

    private class MyDateProvider extends DateProvider {
        private int counter = 0;

        @Override
        public Date now() {
            Date now = super.now();
            switch (counter) {
                case 0:
                    now = new Date(114, 1, 1);
                    break;
                case 1:
                    now = new Date(114, 1, 15);
                    break;
                case 2:
                    now = new Date(114, 1, 17);
                    break;
                case 3:
                    now = new Date(114, 1, 18);
                    break;
            }
            counter++;
            return now;
        }
    }

    private class MyAccount extends Account {
        private MyAccount(AccountType accountType, DateProvider myDateProvider) {
            super(accountType, myDateProvider);
        }
    }
}
