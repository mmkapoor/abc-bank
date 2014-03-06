package com.abc.calculator;

/**
 * Calculator for Savings Account
 */
public class SavingAccountCalculator implements Calculator {

    private static final double INTEREST_RATE_TIER1 = 0.001;
    private static final double INTEREST_RATE_TIER2 = 0.002;

    @Override
    public double interestEarned(double amount, Long daysSinceLastWithDrawl) {
        double interest;
        if (amount <= 1000)
            interest = amount * INTEREST_RATE_TIER1;
        else
            interest = 1 + (amount - 1000) * INTEREST_RATE_TIER2;
        return interest;
    }
}
