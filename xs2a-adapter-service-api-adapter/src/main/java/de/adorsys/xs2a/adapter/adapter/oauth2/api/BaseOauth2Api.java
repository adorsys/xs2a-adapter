package de.adorsys.xs2a.adapter.adapter.oauth2.api;

import de.adorsys.xs2a.adapter.adapter.oauth2.api.model.AuthorisationServerMetaData;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.ResponseHandlers;
import de.adorsys.xs2a.adapter.service.oauth.Oauth2Api;

public class BaseOauth2Api<T extends AuthorisationServerMetaData> implements Oauth2Api {
    private final HttpClient httpClient;
    private final Class<T> metaDataModelClass;

    public BaseOauth2Api(HttpClient httpClient, Class<T> metaDataModelClass) {
        this.httpClient = httpClient;
        this.metaDataModelClass = metaDataModelClass;
    }

    @Override
    public String getAuthorisationUri(String scaOAuthUrl) {
        return getAuthorisationServerMetaData(scaOAuthUrl)
                   .getAuthorisationEndpoint();
    }

    @Override
    public String getTokenUri(String scaOAuthUrl) {
        return getAuthorisationServerMetaData(scaOAuthUrl)
                   .getTokenEndpoint();
    }

    private T getAuthorisationServerMetaData(String scaOAuthUrl) {
        return httpClient.get(scaOAuthUrl)
                   .send(ResponseHandlers.jsonResponseHandler(metaDataModelClass))
                   .getBody();
    }
}
