package de.adorsys.xs2a.adapter.service.ing.internal.api.model;

public class AccountReferenceIban {
    private String iban;

    private String currency;

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
