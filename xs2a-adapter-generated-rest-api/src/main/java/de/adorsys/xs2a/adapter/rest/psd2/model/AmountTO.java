package de.adorsys.xs2a.adapter.rest.psd2.model;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public class AmountTO {
    private String currency;

    private String amount;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
