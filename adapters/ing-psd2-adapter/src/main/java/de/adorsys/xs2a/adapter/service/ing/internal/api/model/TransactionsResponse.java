package de.adorsys.xs2a.adapter.service.ing.internal.api.model;

public class TransactionsResponse {
    private AccountReferenceIban account;

    private Transactions transactions;

    public AccountReferenceIban getAccount() {
        return account;
    }

    public void setAccount(AccountReferenceIban account) {
        this.account = account;
    }

    public Transactions getTransactions() {
        return transactions;
    }

    public void setTransactions(Transactions transactions) {
        this.transactions = transactions;
    }
}
