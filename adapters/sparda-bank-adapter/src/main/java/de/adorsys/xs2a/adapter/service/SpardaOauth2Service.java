package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.adapter.AbstractService;
import de.adorsys.xs2a.adapter.adapter.BaseOauth2Service;
import de.adorsys.xs2a.adapter.adapter.CertificateSubjectClientIdOauth2Service;
import de.adorsys.xs2a.adapter.adapter.PkceOauth2Service;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.http.UriBuilder;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import de.adorsys.xs2a.adapter.validation.ValidationError;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SpardaOauth2Service extends AbstractService implements Oauth2Service, PkceOauth2Extension {

    private static final String AUTHORISATION_REQUEST_URI_SUFFIX = "/authorize";
    private static final String TOKEN_REQUEST_URI_SUFFIX = "/token";
    private static final String SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE
        = "SCA OAuth link is missing or has a wrong format: " +
              "it has to be either provided as a request parameter or preconfigured for the current ASPSP";
    private static final String MISSING_REQUIRED_PARAMETER_ERROR_MESSAGE = "Missing required parameter";

    private final Aspsp aspsp;
    private final Oauth2Service oauth2Service;
    private final String clientId;

    private SpardaOauth2Service(Aspsp aspsp,
                                HttpClient httpClient,
                                Oauth2Service oauth2Service,
                                String clientId) {
        super(httpClient);
        this.aspsp = aspsp;
        this.oauth2Service = oauth2Service;
        this.clientId = clientId;
    }

    public static SpardaOauth2Service create(Aspsp aspsp,
                                             HttpClient httpClient,
                                             Pkcs12KeyStore keyStore,
                                             String clientId) {
        BaseOauth2Service baseOauth2Service = new BaseOauth2Service(aspsp, httpClient);
        CertificateSubjectClientIdOauth2Service clientIdOauth2Service =
            new CertificateSubjectClientIdOauth2Service(baseOauth2Service, keyStore);
        PkceOauth2Service pkceOauth2Service = new SpardaPkceOauth2Service(clientIdOauth2Service);
        return new SpardaOauth2Service(aspsp, httpClient, pkceOauth2Service, clientId);
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {
        requireValid(validateGetAuthorizationRequestUri(headers, parameters));
        String scaOAuthUrl = getScaOAuthUrl(parameters);
        String authorisationRequestUri = StringUri.fromElements(scaOAuthUrl, AUTHORISATION_REQUEST_URI_SUFFIX);
        parameters.setAuthorizationEndpoint(authorisationRequestUri);
        parameters.setClientId(clientId);
        if (StringUtils.isBlank(parameters.getScope())) {
            parameters.setScope("ais");
        }

        return UriBuilder.fromUri(oauth2Service.getAuthorizationRequestUri(headers, parameters))
            .queryParam(Parameters.BIC, aspsp.getBic())
            .build();
    }

    @Override
    public List<ValidationError> validateGetAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) {
        List<ValidationError> validationErrors = new ArrayList<>();
        String scaOAuthUrl = getScaOAuthUrl(parameters);
        if (StringUtils.isBlank(scaOAuthUrl)) {
            validationErrors.add(new ValidationError(ValidationError.Code.REQUIRED,
                Parameters.SCA_OAUTH_LINK,
                SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE));
        }
        if (StringUtils.isBlank(parameters.getRedirectUri())) {
            validationErrors.add(new ValidationError(ValidationError.Code.REQUIRED,
                Parameters.REDIRECT_URI,
                MISSING_REQUIRED_PARAMETER_ERROR_MESSAGE));
        }
        return Collections.unmodifiableList(validationErrors);
    }

    private String getScaOAuthUrl(Parameters parameters) {
        String baseScaOAuthUrl = parameters.getScaOAuthLink();

        if (StringUtils.isBlank(baseScaOAuthUrl)) {
            baseScaOAuthUrl = aspsp.getIdpUrl();
        }

        return baseScaOAuthUrl;
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) throws IOException {
        requireValid(validateGetToken(headers, parameters));
        String scaOAuthUrl = getScaOAuthUrl(parameters);
        parameters.removeScaOAuthLink();
        String tokenEndpoint = StringUri.fromElements(scaOAuthUrl, TOKEN_REQUEST_URI_SUFFIX);
        parameters.setClientId(clientId);
        parameters.setTokenEndpoint(tokenEndpoint);
        return oauth2Service.getToken(headers, parameters);
    }

    @Override
    public List<ValidationError> validateGetToken(Map<String, String> headers, Parameters parameters) {
        List<ValidationError> validationErrors = new ArrayList<>();
        String scaOAuthUrl = getScaOAuthUrl(parameters);
        if (StringUtils.isBlank(scaOAuthUrl)) {
            validationErrors.add(new ValidationError(ValidationError.Code.REQUIRED,
                Parameters.SCA_OAUTH_LINK,
                SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE));
        }
        if (parameters.getAuthorizationCode() != null && StringUtils.isBlank(parameters.getRedirectUri())) {
            validationErrors.add(new ValidationError(ValidationError.Code.REQUIRED,
                Parameters.REDIRECT_URI,
                MISSING_REQUIRED_PARAMETER_ERROR_MESSAGE));
        }
        return Collections.unmodifiableList(validationErrors);
    }

    @Override
    public byte[] octetSequence() {
        return SpardaPkceOauth2Service.OCTET_SEQUENCE;
    }
}
