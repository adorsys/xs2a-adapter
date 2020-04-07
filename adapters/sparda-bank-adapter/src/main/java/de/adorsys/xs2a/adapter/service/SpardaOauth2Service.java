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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SpardaOauth2Service extends AbstractService implements Oauth2Service, PkceOauth2Extension {

    private static final String MISSING_REQUIRED_PARAMETER_ERROR_MESSAGE = "Missing required parameter";

    private final Aspsp aspsp;
    private final Oauth2Service oauth2Service;
    private final String clientId;
    private final String authorizationEndpoint;
    private final String tokenEndpoint;

    private SpardaOauth2Service(Aspsp aspsp,
                                HttpClient httpClient,
                                Oauth2Service oauth2Service,
                                String clientId) {
        super(httpClient);
        this.aspsp = aspsp;
        this.oauth2Service = oauth2Service;
        this.clientId = clientId;
        String[] idpHosts = Objects.requireNonNull(aspsp.getIdpUrl()).trim().split("\\s+");
        if (idpHosts.length != 2) {
            throw new IllegalArgumentException("IDP must consist of two hosts separated by a whitespace");
        }
        authorizationEndpoint = StringUri.fromElements(idpHosts[0], "/oauth2/authorize");
        tokenEndpoint = StringUri.fromElements(idpHosts[1], "/oauth2/token");
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
        parameters.setAuthorizationEndpoint(authorizationEndpoint);
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
        if (StringUtils.isBlank(parameters.getRedirectUri())) {
            return Collections.singletonList(new ValidationError(ValidationError.Code.REQUIRED,
                Parameters.REDIRECT_URI,
                MISSING_REQUIRED_PARAMETER_ERROR_MESSAGE));
        }
        return Collections.emptyList();
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) throws IOException {
        requireValid(validateGetToken(headers, parameters));
        parameters.setClientId(clientId);
        parameters.setTokenEndpoint(tokenEndpoint);
        return oauth2Service.getToken(headers, parameters);
    }

    @Override
    public List<ValidationError> validateGetToken(Map<String, String> headers, Parameters parameters) {
        if (parameters.getAuthorizationCode() != null && StringUtils.isBlank(parameters.getRedirectUri())) {
            return Collections.singletonList(new ValidationError(ValidationError.Code.REQUIRED,
                Parameters.REDIRECT_URI,
                MISSING_REQUIRED_PARAMETER_ERROR_MESSAGE));
        }
        return Collections.emptyList();
    }

    @Override
    public byte[] octetSequence() {
        return SpardaPkceOauth2Service.OCTET_SEQUENCE;
    }
}
