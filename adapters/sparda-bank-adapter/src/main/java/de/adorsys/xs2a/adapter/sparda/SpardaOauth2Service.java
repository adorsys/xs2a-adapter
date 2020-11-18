package de.adorsys.xs2a.adapter.sparda;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.PkceOauth2Extension;
import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.Scope;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.api.validation.ValidationError;
import de.adorsys.xs2a.adapter.impl.BaseOauth2Service;
import de.adorsys.xs2a.adapter.impl.CertificateSubjectClientIdOauth2Service;
import de.adorsys.xs2a.adapter.impl.PkceOauth2Service;
import de.adorsys.xs2a.adapter.impl.http.StringUri;
import de.adorsys.xs2a.adapter.impl.http.UriBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.util.*;

import static de.adorsys.xs2a.adapter.api.validation.Validation.requireValid;

public class SpardaOauth2Service implements Oauth2Service, PkceOauth2Extension {

    private static final EnumMap<Scope, Scope> SCOPE_MAPPING = initiateScopeMapping();
    protected static final String UNSUPPORTED_SCOPE_VALUE_ERROR_MESSAGE = "Scope value [%s] is not supported";

    private final Aspsp aspsp;
    private final Oauth2Service oauth2Service;
    private final String clientId;
    private final String authorizationEndpoint;
    private final String tokenEndpoint;

    private SpardaOauth2Service(Aspsp aspsp,
                                Oauth2Service oauth2Service,
                                String clientId) {
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
                                             String clientId,
                                             HttpLogSanitizer logSanitizer) {
        BaseOauth2Service baseOauth2Service = new BaseOauth2Service(aspsp, httpClient, logSanitizer);
        CertificateSubjectClientIdOauth2Service clientIdOauth2Service =
            new CertificateSubjectClientIdOauth2Service(baseOauth2Service, keyStore);
        PkceOauth2Service pkceOauth2Service = new SpardaPkceOauth2Service(clientIdOauth2Service);
        return new SpardaOauth2Service(aspsp, pkceOauth2Service, clientId);
    }

    private static EnumMap<Scope, Scope> initiateScopeMapping() {
        EnumMap<Scope, Scope> scopeMapping = new EnumMap<>(Scope.class);
        scopeMapping.put(Scope.AIS, Scope.AIS);
        scopeMapping.put(Scope.AIS_BALANCES, Scope.AIS);
        scopeMapping.put(Scope.AIS_TRANSACTIONS, Scope.AIS);
        scopeMapping.put(Scope.PIS, Scope.PIS);
        return scopeMapping;
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {
        requireValid(validateGetAuthorizationRequestUri(headers, parameters));
        parameters.setAuthorizationEndpoint(authorizationEndpoint);
        parameters.setClientId(clientId);
        parameters.setScope(mapScope(parameters.getScope()));

        return UriBuilder.fromUri(oauth2Service.getAuthorizationRequestUri(headers, parameters))
            .queryParam(Parameters.BIC, aspsp.getBic())
            .build();
    }

    private String mapScope(String scope) {
        return SCOPE_MAPPING.get(Scope.fromValue(scope)).getValue();
    }

    @Override
    public List<ValidationError> validateGetAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) {
        List<ValidationError> validationErrors = new ArrayList<>();

        if (StringUtils.isBlank(parameters.getRedirectUri())) {
            validationErrors.add(ValidationError.required(Parameters.REDIRECT_URI));
        }

        String scope = parameters.getScope();
        if (StringUtils.isBlank(scope)) {
            validationErrors.add(ValidationError.required(Parameters.SCOPE));
        } else if (!Scope.contains(scope)) {
            validationErrors.add(new ValidationError(ValidationError.Code.NOT_SUPPORTED,
                Parameters.SCOPE,
                String.format(UNSUPPORTED_SCOPE_VALUE_ERROR_MESSAGE, scope)));
        }

        return validationErrors;
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
            return Collections.singletonList(ValidationError.required(Parameters.REDIRECT_URI));
        }
        return Collections.emptyList();
    }

    @Override
    public byte[] octetSequence() {
        return SpardaPkceOauth2Service.OCTET_SEQUENCE;
    }
}
