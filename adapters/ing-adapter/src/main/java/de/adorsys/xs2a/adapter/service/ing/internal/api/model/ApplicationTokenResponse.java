package de.adorsys.xs2a.adapter.service.ing.internal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationTokenResponse extends TokenResponse {
    @JsonProperty("client_id")
    private String clientId;

    public final String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
