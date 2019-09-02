package de.adorsys.xs2a.adapter.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class CommerzbankTransactionsReport {
    private AccountReference accountReference;
    private AccountReport transactions;
    private List<CommerzbankBalance> balances;
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

    public List<CommerzbankBalance> getBalances() {
        return balances;
    }

    public void setBalances(List<CommerzbankBalance> balances) {
        this.balances = balances;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }
}
