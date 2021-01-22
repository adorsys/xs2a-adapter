package de.adorsys.xs2a.adapter.ing.model;

public class IngTransactionsResponse {
    private IngAccountReferenceIban account;

    private IngTransactions transactions;

    public IngAccountReferenceIban getAccount() {
        return account;
    }

    public void setAccount(IngAccountReferenceIban account) {
        this.account = account;
    }

    public IngTransactions getTransactions() {
        return transactions;
    }

    public void setTransactions(IngTransactions transactions) {
        this.transactions = transactions;
    }
}
