package de.adorsys.xs2a.adapter.impl.oauth2.api;

import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.oauth.Oauth2Api;
import de.adorsys.xs2a.adapter.impl.http.ResponseHandlers;
import de.adorsys.xs2a.adapter.impl.oauth2.api.model.AuthorisationServerMetaData;

public class BaseOauth2Api<T extends AuthorisationServerMetaData> implements Oauth2Api {

    private final HttpClient httpClient;
    private final Class<T> metaDataModelClass;
    private final ResponseHandlers responseHandlers;

    public BaseOauth2Api(HttpClient httpClient, Class<T> metaDataModelClass) {
        this(httpClient, metaDataModelClass, null);
    }

    public BaseOauth2Api(HttpClient httpClient, Class<T> metaDataModelClass, HttpLogSanitizer logSanitizer) {
        this.httpClient = httpClient;
        this.metaDataModelClass = metaDataModelClass;
        this.responseHandlers = new ResponseHandlers(logSanitizer);
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
                   .send(responseHandlers.jsonResponseHandler(metaDataModelClass))
                   .getBody();
    }
}
