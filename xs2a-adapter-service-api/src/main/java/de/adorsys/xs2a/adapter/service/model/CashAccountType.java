package de.adorsys.xs2a.adapter.service.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum CashAccountType {
    // Account used to post debits and credits when no specific account has been nominated
    CACC("Current"),
    // Account used for the payment of cash
    CASH("CashPayment"),
    // Account used for charges if different from the account for payment
    CHAR("Charges"),
    // Account used for payment of income if different from the current cash account
    CISH("CashIncome"),
    // Account used for commission if different from the account for payment
    COMM("Commission"),
    // Account used to post settlement debit and credit entries
    // on behalf of a designated Clearing Participant
    CPAC("ClearingParticipantSettlementAccount"),
    // Account used for savings with special interest and withdrawal terms
    LLSV("LimitedLiquiditySavingsAccount"),
    // Account used for loans
    LOAN("Loan"),
    // Account used for a marginal lending facility
    MGLD("Marginal Lending"),
    // Account used for money markets if different from the cash account
    MOMA("Money Market"),
    // Account used for non-resident external
    NREX("NonResidentExternal"),
    // Account is used for overdrafts
    ODFT("Overdraft"),
    // Account used for overnight deposits
    ONDP("OverNightDeposit"),
    // Account not otherwise specified
    OTHR("OtherAccount"),
    // Account used to post debit and credit entries, as a result of transactions
    // cleared and settled through a specific clearing and settlement system
    SACC("Settlement"),
    // Accounts used for salary payments
    SLRY("Salary"),
    // Account used for savings
    SVGS("Savings"),
    // Account used for taxes if different from the account for payment
    TAXE("Tax"),
    // A transacting account is the most basic type of bank account that you can get.
    // The main difference between transaction and cheque accounts is that you
    // usually do not get a cheque book with your transacting account and neither
    // are you offered an overdraft facility
    TRAN("TransactingAccount"),
    // Account used for trading if different from the current cash account
    TRAS("Cash Trading");

    private final static Map<String, CashAccountType> container = new HashMap<>();

    static {
        for (CashAccountType cashAccountType : values()) {
            container.put(cashAccountType.getValue(), cashAccountType);
        }
    }

    private String value;

    CashAccountType(String value) {
        this.value = value;
    }

    public static Optional<CashAccountType> getByValue(String name) {
        return Optional.ofNullable(container.get(name));
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
