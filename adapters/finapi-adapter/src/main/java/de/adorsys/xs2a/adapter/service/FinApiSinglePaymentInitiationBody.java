package de.adorsys.xs2a.adapter.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FinApiSinglePaymentInitiationBody extends SinglePaymentInitiationBody {
    @JsonProperty("@type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
