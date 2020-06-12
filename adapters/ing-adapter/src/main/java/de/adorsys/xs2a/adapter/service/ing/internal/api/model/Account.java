package de.adorsys.xs2a.adapter.service.ing.internal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Account {
    private UUID resourceId;

    private String iban;

    private String name;

    private String currency;

    @JsonProperty("_links")
    private AccountLinks links;

    public UUID getResourceId() {
        return resourceId;
    }

    public void setResourceId(UUID resourceId) {
        this.resourceId = resourceId;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public AccountLinks getLinks() {
        return links;
    }

    public void setLinks(AccountLinks links) {
        this.links = links;
    }
}

