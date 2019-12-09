package de.adorsys.xs2a.adapter.adorsys.service.api;

import de.adorsys.xs2a.adapter.adorsys.service.api.model.AdorsysAuthorisationServerMetaData;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.ResponseHandlers;

public class AdorsysOauth2Api {
    private final HttpClient httpClient;

    public AdorsysOauth2Api(HttpClient httpClient) {
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

    private AdorsysAuthorisationServerMetaData getAuthorisationServerMetaData(String scaOAuthUrl) {
        return httpClient.get(scaOAuthUrl)
                   .send(ResponseHandlers.jsonResponseHandler(AdorsysAuthorisationServerMetaData.class))
                   .getBody();
    }
}
