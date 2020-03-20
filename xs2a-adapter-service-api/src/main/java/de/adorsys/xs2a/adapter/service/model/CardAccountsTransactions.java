package de.adorsys.xs2a.adapter.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class CardAccountsTransactions {
    private AccountReference cardAccount;

    private CardAccountReport cardTransactions;

    private List<Balance> balances;

    @JsonProperty("_links")
    private Map<String, Link> links;

    public AccountReference getCardAccount() {
        return cardAccount;
    }

    public void setCardAccount(AccountReference cardAccount) {
        this.cardAccount = cardAccount;
    }

    public CardAccountReport getCardTransactions() {
        return cardTransactions;
    }

    public void setCardTransactions(CardAccountReport cardTransactions) {
        this.cardTransactions = cardTransactions;
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
}
