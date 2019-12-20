package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.adapter.BaseOauth2Service;
import de.adorsys.xs2a.adapter.adapter.PkceOauth2Service;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.UriBuilder;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;

import java.io.IOException;
import java.net.URI;
import java.security.KeyStoreException;
import java.util.Map;

public class SparkasseOauth2Service implements Oauth2Service, PkceOauth2Extension {

    private final Oauth2Service oauth2Service;
    private final Pkcs12KeyStore keyStore;

    private SparkasseOauth2Service(Oauth2Service oauth2Service, Pkcs12KeyStore keyStore) {
        this.oauth2Service = oauth2Service;
        this.keyStore = keyStore;
    }

    public static SparkasseOauth2Service create(Aspsp aspsp, HttpClient httpClient, Pkcs12KeyStore keyStore) {
        return new SparkasseOauth2Service(new PkceOauth2Service(new BaseOauth2Service(aspsp, httpClient)), keyStore);
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {

        return UriBuilder.fromUri(oauth2Service.getAuthorizationRequestUri(headers, parameters))
            .queryParam("responseType", "code")
            .queryParam("clientId", clientId())
            .queryParam("scope", scope(parameters))
            .build();
    }

    private String scope(Parameters parameters) {
        if (parameters.getConsentId() != null) {
            return "AIS: " + parameters.getConsentId();
        }
        return null;
    }

    private String clientId() {
        try {
            return keyStore.getOrganizationIdentifier();
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) {
        throw new UnsupportedOperationException();
    }
}
