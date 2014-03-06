package com.abc.calculator;

/**
 * Calculator interface. Should only contain calculation related functions
 */
public interface Calculator {
    /**
     * calculate and return the interest earned on a given amount
     *
     * @param amount
     * @param daysSinceLastWithDrawl
     * @return
     */
    double interestEarned(double amount, Long daysSinceLastWithDrawl);
}
