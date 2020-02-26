package de.adorsys.xs2a.adapter.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TransactionsReport {
    private AccountReference accountReference;
    private AccountReport transactions;
    private List<Balance> balances;
    @JsonProperty("_links")
    private Map<String, Link> links;

    public AccountReference getAccountReference() {
        return accountReference;
    }

    public void setAccountReference(AccountReference accountReference) {
        this.accountReference = accountReference;
    }

    public AccountReport getTransactions() {
        return transactions;
    }

    public void setTransactions(AccountReport transactions) {
        this.transactions = transactions;
    }

    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionsReport that = (TransactionsReport) o;
        return Objects.equals(accountReference, that.accountReference) &&
                   Objects.equals(transactions, that.transactions) &&
                   Objects.equals(balances, that.balances) &&
                   Objects.equals(links, that.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountReference, transactions, balances, links);
    }

    @Override
    public String toString() {
        return "TransactionsReport{" +
                   "accountReference=" + accountReference +
                   ", transactions=" + transactions +
                   ", balances=" + balances +
                   ", links=" + links +
                   '}';
    }
}
