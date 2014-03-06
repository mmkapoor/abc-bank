package com.abc.calculator;

/**
 * Calculator for Checking Account
 */
public class CheckingAccountCalculator implements Calculator {

    private static final double INTEREST_RATE = 0.001;

    @Override
    public double interestEarned(double amount, Long daysSinceLastWithDrawl) {
        return amount * INTEREST_RATE;
    }
}
