package de.adorsys.xs2a.adapter.adorsys.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.adorsys.xs2a.adapter.api.model.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AdorsysTransactionsResponse200Json {
    private AccountReference account;
    private AdorsysAccountReport transactions;
    private List<Balance> balances;

    @JsonProperty("_links")
    private Map<String, HrefType> links;

    public AccountReference getAccount() {
        return account;
    }

    public void setAccount(AccountReference account) {
        this.account = account;
    }

    public AdorsysAccountReport getTransactions() {
        return transactions;
    }

    public void setTransactions(AdorsysAccountReport transactions) {
        this.transactions = transactions;
    }

    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }

    public Map<String, HrefType> getLinks() {
        return links;
    }

    public void setLinks(Map<String, HrefType> links) {
        this.links = links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdorsysTransactionsResponse200Json that = (AdorsysTransactionsResponse200Json) o;
        return Objects.equals(account, that.account) &&
            Objects.equals(transactions, that.transactions) &&
            Objects.equals(balances, that.balances) &&
            Objects.equals(links, that.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account,
            transactions,
            balances,
            links);
    }
}
