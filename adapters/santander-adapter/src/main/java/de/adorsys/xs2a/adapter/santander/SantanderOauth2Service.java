package de.adorsys.xs2a.adapter.santander;

import de.adorsys.xs2a.adapter.impl.adapter.BaseOauth2Service;
import de.adorsys.xs2a.adapter.impl.adapter.CertificateSubjectClientIdOauth2Service;
import de.adorsys.xs2a.adapter.impl.adapter.PkceOauth2Service;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.impl.http.UriBuilder;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.PkceOauth2Extension;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import de.adorsys.xs2a.adapter.validation.ValidationError;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static de.adorsys.xs2a.adapter.validation.Validation.requireValid;

class SantanderOauth2Service implements Oauth2Service, PkceOauth2Extension {

    private final Oauth2Service oauth2Service;

    SantanderOauth2Service(Oauth2Service oauth2Service) {
        this.oauth2Service = oauth2Service;
    }

    public static SantanderOauth2Service create(Aspsp aspsp, HttpClient httpClient, Pkcs12KeyStore keyStore) {
        return new SantanderOauth2Service(
            new CertificateSubjectClientIdOauth2Service(
                new PkceOauth2Service(new BaseOauth2Service(aspsp, httpClient)),
                keyStore));
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {
        requireValid(validateGetAuthorizationRequestUri(headers, parameters));

        return UriBuilder.fromUri(oauth2Service.getAuthorizationRequestUri(headers, parameters))
            .queryParam("scope", scope(parameters))
            .build();
    }

    private String scope(Parameters parameters) {
        if (parameters.getPaymentId() != null) {
            return "PIS:" + parameters.getPaymentId();
        }
        if (parameters.getConsentId() != null) {
            return "AIS:" + parameters.getConsentId();
        }
        throw new IllegalStateException(); // request validation doesn't permit this case to occur
    }

    @Override
    public List<ValidationError> validateGetAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) {
        List<ValidationError> validationErrors = new ArrayList<>();
        if (parameters.getScaOAuthLink() == null) {
            validationErrors.add(ValidationError.required(Parameters.SCA_OAUTH_LINK));
        }
        if (parameters.getPaymentId() == null && parameters.getConsentId() == null) {
            validationErrors.add(ValidationError.required(Parameters.CONSENT_ID + " or " + Parameters.PAYMENT_ID));
        }
        return validationErrors;
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) throws IOException {
        return oauth2Service.getToken(headers, parameters);
    }
}
