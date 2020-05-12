package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.adapter.BaseOauth2Service;
import de.adorsys.xs2a.adapter.adapter.CertificateSubjectClientIdOauth2Service;
import de.adorsys.xs2a.adapter.adapter.PkceOauth2Service;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.UriBuilder;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class SparkasseOauth2Service implements Oauth2Service, PkceOauth2Extension {

    private final Oauth2Service oauth2Service;

    private SparkasseOauth2Service(Oauth2Service oauth2Service) {
        this.oauth2Service = oauth2Service;
    }

    public static SparkasseOauth2Service create(Aspsp aspsp, HttpClient httpClient, Pkcs12KeyStore keyStore) {
        BaseOauth2Service baseOauth2Service = new BaseOauth2Service(aspsp, httpClient);
        CertificateSubjectClientIdOauth2Service clientIdOauth2Service =
            new CertificateSubjectClientIdOauth2Service(baseOauth2Service, keyStore);
        PkceOauth2Service pkceOauth2Service = new PkceOauth2Service(clientIdOauth2Service);
        return new SparkasseOauth2Service(pkceOauth2Service);
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {

        return UriBuilder.fromUri(oauth2Service.getAuthorizationRequestUri(headers, parameters))
            .renameQueryParam(Parameters.RESPONSE_TYPE, "responseType")
            .renameQueryParam(Parameters.CLIENT_ID, "clientId")
            .queryParam("scope", scope(parameters))
            .build();
    }

    private String scope(Parameters parameters) {
        if (parameters.getConsentId() != null) {
            return "AIS: " + parameters.getConsentId();
        } else if (parameters.getPaymentId() != null) {
            return "PIS: " + parameters.getPaymentId();
        }
        return null;
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) throws IOException {
        return oauth2Service.getToken(headers, parameters);
    }
}
