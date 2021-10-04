package de.adorsys.xs2a.adapter.commerzbank.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.adorsys.xs2a.adapter.api.model.AccountReference;
import de.adorsys.xs2a.adapter.api.model.HrefType;

import java.util.List;
import java.util.Map;

public class CommerzbankTransactionsReport {
    private AccountReference account;
    private CommerzbankAccountReport transactions;
    private List<CommerzbankBalance> balances;
    @JsonProperty("_links")
    private Map<String, HrefType> links;

    public AccountReference getAccount() {
        return account;
    }

    public void setAccount(AccountReference account) {
        this.account = account;
    }

    public CommerzbankAccountReport getTransactions() {
        return transactions;
    }

    public void setTransactions(CommerzbankAccountReport transactions) {
        this.transactions = transactions;
    }

    public List<CommerzbankBalance> getBalances() {
        return balances;
    }

    public void setBalances(List<CommerzbankBalance> balances) {
        this.balances = balances;
    }

    public Map<String, HrefType> getLinks() {
        return links;
    }

    public void setLinks(Map<String, HrefType> links) {
        this.links = links;
    }
}
