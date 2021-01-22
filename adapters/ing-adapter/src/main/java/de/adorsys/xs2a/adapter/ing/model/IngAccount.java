package de.adorsys.xs2a.adapter.ing.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class IngAccount {
    private UUID resourceId;

    private String iban;

    private String name;

    private String currency;

    private String product;

    @JsonProperty("_links")
    private IngAccountLinks links;

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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public IngAccountLinks getLinks() {
        return links;
    }

    public void setLinks(IngAccountLinks links) {
        this.links = links;
    }
}

