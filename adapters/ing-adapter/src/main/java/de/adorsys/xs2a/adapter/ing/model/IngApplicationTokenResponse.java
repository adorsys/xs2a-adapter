package de.adorsys.xs2a.adapter.ing.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IngApplicationTokenResponse extends IngTokenResponse {
    @JsonProperty("client_id")
    private String clientId;

    public final String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
