package de.adorsys.xs2a.adapter.service.ing.internal.api;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.util.Key;

public class ApplicationTokenResponse extends TokenResponse {
    @Key("client_id")
    private String clientId;

    public final String getClientId() {
        return clientId;
    }
}
