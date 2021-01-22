package de.adorsys.xs2a.adapter.ing.model;

public class IngAccountLinks {
    private IngHrefType balances;

    private IngHrefType transactions;

    public IngHrefType getBalances() {
        return balances;
    }

    public void setBalances(IngHrefType balances) {
        this.balances = balances;
    }

    public IngHrefType getTransactions() {
        return transactions;
    }

    public void setTransactions(IngHrefType transactions) {
        this.transactions = transactions;
    }
}
