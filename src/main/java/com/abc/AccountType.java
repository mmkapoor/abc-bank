package com.abc;

import com.abc.calculator.Calculator;
import com.abc.calculator.CheckingAccountCalculator;
import com.abc.calculator.MaxiSavingAccountCalculator;
import com.abc.calculator.SavingAccountCalculator;

/**
 * AccountType will host AccountType related data.
 */
public enum AccountType {
    CHECKING("Checking", "Checking Account", new CheckingAccountCalculator()),
    SAVINGS("Savings", "Savings Account", new SavingAccountCalculator()),
    MAXI_SAVINGS("MaximumSavings", "Maxi Savings Account", new MaxiSavingAccountCalculator());

    private String accountType;
    private String description;
    private Calculator calculator;

    AccountType(String accountType, String description, Calculator calculator) {
        this.accountType = accountType;
        this.description = description;
        this.calculator = calculator;
    }

    public String getDescription() {
        return description;
    }

    public Calculator getCalculator() {
        return calculator;
    }
}
