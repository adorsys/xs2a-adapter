package de.adorsys.xs2a.tpp.controller;

import de.adorsys.xs2a.adapter.api.Oauth2Api;
import de.adorsys.xs2a.adapter.model.HrefTypeTO;
import de.adorsys.xs2a.adapter.model.TokenResponseTO;
import de.adorsys.xs2a.adapter.remote.api.Oauth2Client;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class Oauth2Controller implements Oauth2Api {

    private final Oauth2Client client;

    public Oauth2Controller(Oauth2Client client) {
        this.client = client;
    }

    @Override
    public HrefTypeTO getAuthorizationUrl(Map<String, String> headers, Map<String, String> parameters) throws IOException {
        return client.getAuthorizationUrl(headers, parameters);
    }

    @Override
    public TokenResponseTO getToken(Map<String, String> headers, Map<String, String> parameters) throws IOException {
        return client.getToken(headers, parameters);
    }
}
