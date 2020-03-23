package de.adorsys.xs2a.adapter.service.psd2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class CardAccountsTransactionsResponse {
    private AccountReference cardAccount;

    private CardAccountReport cardTransactions;

    private List<Balance> balances;

    @JsonProperty("_links")
    private Map<String, HrefType> links;

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

    public Map<String, HrefType> getLinks() {
        return links;
    }

    public void setLinks(Map<String, HrefType> links) {
        this.links = links;
    }
}
