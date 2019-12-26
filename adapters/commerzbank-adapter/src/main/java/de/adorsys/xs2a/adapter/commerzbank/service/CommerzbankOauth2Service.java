package de.adorsys.xs2a.adapter.commerzbank.service;

import de.adorsys.xs2a.adapter.adapter.BaseOauth2Service;
import de.adorsys.xs2a.adapter.adapter.CertificateSubjectClientIdOauth2Service;
import de.adorsys.xs2a.adapter.adapter.PkceOauth2Service;
import de.adorsys.xs2a.adapter.adapter.mapper.TokenResponseMapper;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.http.UriBuilder;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.PkceOauth2Extension;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @see <a href="https://psd2.developer.commerzbank.com/content/howto/ais-manage-consents">OAuth2 authorisation</a>
 * @see <a href="https://psd2.developer.commerzbank.com/content/howto/sandbox">2 - Authorize the payment</a>
 */
public class CommerzbankOauth2Service implements Oauth2Service, PkceOauth2Extension {

    private final TokenResponseMapper tokenResponseMapper = Mappers.getMapper(TokenResponseMapper.class);
    private final Oauth2Service oauth2Service;

    private CommerzbankOauth2Service(Oauth2Service oauth2Service) {
        this.oauth2Service = oauth2Service;
    }

    public static CommerzbankOauth2Service create(Aspsp aspsp, HttpClient httpClient, Pkcs12KeyStore keyStore) {
        String baseUrl = aspsp.getIdpUrl() != null ? aspsp.getIdpUrl() : aspsp.getUrl();
        String authorizationEndpoint = StringUri.fromElements(baseUrl, "/authorize");
        String tokenEndpoint = StringUri.fromElements(baseUrl, "/v1/token");
        BaseOauth2Service baseOauth2Service =
            new BaseOauth2Service(aspsp, httpClient, authorizationEndpoint, tokenEndpoint);
        CertificateSubjectClientIdOauth2Service clientIdOauth2Service =
            new CertificateSubjectClientIdOauth2Service(baseOauth2Service, keyStore);
        return new CommerzbankOauth2Service(new PkceOauth2Service(clientIdOauth2Service));
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {

        URI url = oauth2Service.getAuthorizationRequestUri(headers, parameters);
        return UriBuilder.fromUri(appendAuthorisationIdToPath(parameters, url))
            .queryParam(Parameters.SCOPE, scope(parameters))
            .build();
    }

    private String scope(Parameters parameters) {
        if (parameters.getConsentId() != null) {
            return "AIS:" + parameters.getConsentId();
        }
        return null;
    }

    private URI appendAuthorisationIdToPath(Parameters parameters, URI url) {
        try {
            return new URI(url.getScheme(), url.getUserInfo(), url.getHost(), url.getPort(),
                    url.getPath() + "/" + parameters.removeAuthorisationId(), url.getQuery(), url.getFragment());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) throws IOException {
        return oauth2Service.getToken(headers, parameters);
    }
}
