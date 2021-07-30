package de.adorsys.xs2a.adapter.santander;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.PkceOauth2Extension;
import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.config.AdapterConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.api.validation.ValidationError;
import de.adorsys.xs2a.adapter.impl.BaseOauth2Service;
import de.adorsys.xs2a.adapter.impl.PkceOauth2Service;
import de.adorsys.xs2a.adapter.impl.http.ResponseHandlers;
import de.adorsys.xs2a.adapter.impl.http.UriBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static de.adorsys.xs2a.adapter.api.validation.Validation.requireValid;

class SantanderOauth2Service implements Oauth2Service, PkceOauth2Extension {

    private static final String SANTANDER_TOKEN_URL_PROPERTY = "santander.token.url";
    private static final String TOKEN_URL = AdapterConfig.readProperty(SANTANDER_TOKEN_URL_PROPERTY, "/v1/oauth_matls/token");
    private static final String GRANT_TYPE_VALUE = "client_credentials";
    private static String baseUrl;
    private final Oauth2Service oauth2Service;
    private static HttpClient httpClient;

    private static ResponseHandlers responseHandlers;

    SantanderOauth2Service(Oauth2Service oauth2Service) {
        this.oauth2Service = oauth2Service;
    }

    public static SantanderOauth2Service create(Aspsp aspsp, HttpClientFactory httpClientFactory) {
        baseUrl = aspsp.getUrl();
        httpClient = SantanderHttpClientFactory.getHttpClient(aspsp.getAdapterId(), httpClientFactory);
        HttpLogSanitizer logSanitizer = httpClientFactory.getHttpClientConfig().getLogSanitizer();
        Pkcs12KeyStore keyStore = httpClientFactory.getHttpClientConfig().getKeyStore();
        responseHandlers = new ResponseHandlers(logSanitizer);

        return new SantanderOauth2Service(
            new SantanderCertificateSubjectClientIdOauth2Service(
                new PkceOauth2Service(new BaseOauth2Service(aspsp, httpClient, logSanitizer)),
                keyStore));
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {
        requireValid(validateGetAuthorizationRequestUri(headers, parameters));

        fixScaOAuthLink(parameters);

        return UriBuilder.fromUri(oauth2Service.getAuthorizationRequestUri(headers, parameters))
            .queryParam("scope", scope(parameters))
            .build();
    }

    private void fixScaOAuthLink(Parameters parameters) {
        String scaOAuthLink = parameters.getScaOAuthLink();
        scaOAuthLink = scaOAuthLink.toLowerCase();

        if (scaOAuthLink.startsWith("https://")) {
            return; // link in an expected format
        }

        parameters.setScaOAuthLink(baseUrl + scaOAuthLink);
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
        parameters.setTokenEndpoint(baseUrl + TOKEN_URL);
        return oauth2Service.getToken(headers, parameters);
    }

    public String getClientCredentialsAccessToken() {
        return httpClient.post(baseUrl + TOKEN_URL)
            .urlEncodedBody(Map.of(Parameters.GRANT_TYPE, GRANT_TYPE_VALUE))
            .send(responseHandlers.jsonResponseHandler(TokenResponse.class))
            .getBody()
            .getAccessToken();
    }
}
