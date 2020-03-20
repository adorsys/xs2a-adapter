package de.adorsys.xs2a.adapter.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;
import java.util.Map;

public class CardAccountDetails {
    private String resourceId;

    private String maskedPan;

    private String currency;

    private String ownerName;

    private String name;

    private String displayName;

    private String product;

    private AccountStatus status;

    private UsageTO usage;

    private String details;

    private Amount creditLimit;

    private List<Balance> balances;

    @JsonProperty("_links")
    private Map<String, Link> links;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getMaskedPan() {
        return maskedPan;
    }

    public void setMaskedPan(String maskedPan) {
        this.maskedPan = maskedPan;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public UsageTO getUsage() {
        return usage;
    }

    public void setUsage(UsageTO usage) {
        this.usage = usage;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Amount getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Amount creditLimit) {
        this.creditLimit = creditLimit;
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

    public enum UsageTO {
        PRIV("PRIV"),

        ORGA("ORGA");

        private String value;

        UsageTO(String value) {
            this.value = value;
        }

        @JsonCreator
        public static UsageTO fromValue(String value) {
            for (UsageTO e : UsageTO.values()) {
                if (e.value.equals(value)) {
                    return e;
                }
            }
            throw new IllegalArgumentException(value);
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }
}
