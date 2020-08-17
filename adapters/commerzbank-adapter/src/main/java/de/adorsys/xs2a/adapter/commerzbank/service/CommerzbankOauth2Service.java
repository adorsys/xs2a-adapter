package de.adorsys.xs2a.adapter.commerzbank.service;

import de.adorsys.xs2a.adapter.impl.adapter.*;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.impl.http.StringUri;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.PkceOauth2Extension;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import static de.adorsys.xs2a.adapter.validation.Validation.requireValid;

/**
 * @see <a href="https://psd2.developer.commerzbank.com/content/howto/ais-manage-consents">OAuth2 authorisation</a>
 * @see <a href="https://psd2.developer.commerzbank.com/content/howto/sandbox">2 - Authorize the payment</a>
 */
public class CommerzbankOauth2Service extends Oauth2ServiceDecorator implements PkceOauth2Extension {
    private static final String AIS_SCOPE_PREFIX = "AIS:";
    private static final String PIS_SCOPE_PREFIX = "PIS:";

    private final String baseUrl;

    private CommerzbankOauth2Service(Oauth2Service oauth2Service, String baseUrl) {
        super(oauth2Service);
        this.baseUrl = baseUrl;
    }

    public static CommerzbankOauth2Service create(Aspsp aspsp, HttpClient httpClient, Pkcs12KeyStore keyStore) {
        String baseUrl = aspsp.getIdpUrl() != null ? aspsp.getIdpUrl() : aspsp.getUrl();
        BaseOauth2Service baseOauth2Service = new BaseOauth2Service(aspsp, httpClient);
        CertificateSubjectClientIdOauth2Service clientIdOauth2Service =
            new CertificateSubjectClientIdOauth2Service(baseOauth2Service, keyStore);
        PkceOauth2Service pkceOauth2Service = new PkceOauth2Service(clientIdOauth2Service);
        ScopeWithResourceIdOauth2Service scopeOauth2Service =
            new ScopeWithResourceIdOauth2Service(pkceOauth2Service, AIS_SCOPE_PREFIX, PIS_SCOPE_PREFIX);
        return new CommerzbankOauth2Service(scopeOauth2Service, baseUrl);
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {
        requireValid(validateGetAuthorizationRequestUri(headers, parameters));
        parameters.setAuthorizationEndpoint(parameters.removeScaOAuthLink());
        return oauth2Service.getAuthorizationRequestUri(headers, parameters);
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) throws IOException {
        parameters.removeScaOAuthLink();
        parameters.setTokenEndpoint(StringUri.fromElements(baseUrl, "/v1/token"));
        return oauth2Service.getToken(headers, parameters);
    }
}
