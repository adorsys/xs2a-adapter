package de.adorsys.xs2a.adapter.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class CardAccountDetails {
    private String resourceId;

    private String maskedPan;

    private String currency;

    private String ownerName;

    private String name;

    private String displayName;

    private String product;

    private Boolean debitAccounting;

    private AccountStatus status;

    private Usage usage;

    private String details;

    private Amount creditLimit;

    private List<Balance> balances;

    @JsonProperty("_links")
    private Map<String, HrefType> links;

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

    public Boolean getDebitAccounting() {
        return debitAccounting;
    }

    public void setDebitAccounting(Boolean debitAccounting) {
        this.debitAccounting = debitAccounting;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
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
        CardAccountDetails that = (CardAccountDetails) o;
        return Objects.equals(resourceId, that.resourceId) &&
            Objects.equals(maskedPan, that.maskedPan) &&
            Objects.equals(currency, that.currency) &&
            Objects.equals(ownerName, that.ownerName) &&
            Objects.equals(name, that.name) &&
            Objects.equals(displayName, that.displayName) &&
            Objects.equals(product, that.product) &&
            Objects.equals(debitAccounting, that.debitAccounting) &&
            Objects.equals(status, that.status) &&
            Objects.equals(usage, that.usage) &&
            Objects.equals(details, that.details) &&
            Objects.equals(creditLimit, that.creditLimit) &&
            Objects.equals(balances, that.balances) &&
            Objects.equals(links, that.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceId,
            maskedPan,
            currency,
            ownerName,
            name,
            displayName,
            product,
            debitAccounting,
            status,
            usage,
            details,
            creditLimit,
            balances,
            links);
    }

    public enum Usage {
        PRIV("PRIV"),

        ORGA("ORGA");

        private String value;

        Usage(String value) {
            this.value = value;
        }

        @JsonCreator
        public static Usage fromValue(String value) {
            for (Usage e : Usage.values()) {
                if (e.value.equals(value)) {
                    return e;
                }
            }
            return null;
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }
}
