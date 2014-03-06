package com.abc.calculator;

/**
 * Calculator for Maxi Savings Account
 */
public class MaxiSavingAccountCalculator implements Calculator {

    public static final double INTEREST_RATE_TIER1 = 0.05;
    public static final double INTEREST_RATE_TIER2 = 0.001;

    @Override
    public double interestEarned(double amount, Long daysSinceLastWithDrawl) {
        if (daysSinceLastWithDrawl != null && daysSinceLastWithDrawl < 10)
            return amount * INTEREST_RATE_TIER2;
        return amount * INTEREST_RATE_TIER1;
    }
}
