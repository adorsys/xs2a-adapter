package de.adorsys.xs2a.adapter.adorsys.service.api;

import de.adorsys.xs2a.adapter.adorsys.service.api.model.AuthorisationServerMetaData;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.ResponseHandlers;

public class Oauth2Api {
    private final HttpClient httpClient;

    public Oauth2Api(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String getAuthorisationUri(String scaOAuthUrl) {
        return getAuthorisationServerMetaData(scaOAuthUrl)
                   .getAuthorisationEndpoint();
    }

    public String getTokenUri(String scaOAuthUrl) {
        return getAuthorisationServerMetaData(scaOAuthUrl)
                   .getTokenEndpoint();
    }

    private AuthorisationServerMetaData getAuthorisationServerMetaData(String scaOAuthUrl) {
        return httpClient.get(scaOAuthUrl)
                   .send(ResponseHandlers.jsonResponseHandler(AuthorisationServerMetaData.class))
                   .getBody();
    }
}
