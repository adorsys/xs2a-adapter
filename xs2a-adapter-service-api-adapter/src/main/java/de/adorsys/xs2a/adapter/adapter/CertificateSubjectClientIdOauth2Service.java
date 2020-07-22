package de.adorsys.xs2a.adapter.adapter;

import de.adorsys.xs2a.adapter.http.UriBuilder;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.exception.Xs2aAdapterException;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;

import java.io.IOException;
import java.net.URI;
import java.security.KeyStoreException;
import java.util.Map;

/**
 * Sets client_id parameter to organization identifier from eIDAS certificate.
 */
public class CertificateSubjectClientIdOauth2Service implements Oauth2Service {
    private final Oauth2Service oauth2Service;
    private final Pkcs12KeyStore keyStore;

    public CertificateSubjectClientIdOauth2Service(Oauth2Service oauth2Service, Pkcs12KeyStore keyStore) {
        this.oauth2Service = oauth2Service;
        this.keyStore = keyStore;
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {
        return UriBuilder.fromUri(oauth2Service.getAuthorizationRequestUri(headers, parameters))
            .queryParam(Parameters.CLIENT_ID, clientId(parameters))
            .build();
    }

    private String clientId(Parameters parameters) {
        String clientId = parameters.getClientId();
        if (clientId != null) {
            return clientId;
        }
        try {
            return keyStore.getOrganizationIdentifier();
        } catch (KeyStoreException e) {
            throw new Xs2aAdapterException(e);
        }
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) throws IOException {
        parameters.setClientId(clientId(parameters));
        return oauth2Service.getToken(headers, parameters);
    }
}
