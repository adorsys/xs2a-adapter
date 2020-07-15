package de.adorsys.xs2a.adapter.ing.internal.api.model;

public class AccountLinks {
    private HrefType balances;

    private HrefType transactions;

    public HrefType getBalances() {
        return balances;
    }

    public void setBalances(HrefType balances) {
        this.balances = balances;
    }

    public HrefType getTransactions() {
        return transactions;
    }

    public void setTransactions(HrefType transactions) {
        this.transactions = transactions;
    }
}
