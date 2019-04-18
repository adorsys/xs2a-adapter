package de.adorsys.xs2a.gateway.service.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.adorsys.xs2a.gateway.service.AccountReference;
import de.adorsys.xs2a.gateway.service.Links;

import java.util.List;

public class TransactionsReport {
    private static final String RESPONSE_TYPE_JSON = "application/json";
    private static final String RESPONSE_TYPE_XML = "application/xml";
    private static final String RESPONSE_TYPE_TEXT = "text/plain";

    private AccountReference accountReference;
    private AccountReport transactions;
    private List<Balance> balances;
    @JsonProperty("_links")
    private Links links;
    // TODO change it when other response type is supported
    private String responseContentType = RESPONSE_TYPE_JSON;

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

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public String getResponseContentType() {
        return responseContentType;
    }

    public void setResponseContentType(String responseContentType) {
        this.responseContentType = responseContentType;
    }
}
