package de.adorsys.xs2a.adapter.commerzbank.service;

import de.adorsys.xs2a.adapter.adapter.AbstractService;
import de.adorsys.xs2a.adapter.adapter.mapper.TokenResponseMapper;
import de.adorsys.xs2a.adapter.adapter.model.OauthToken;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import org.mapstruct.factory.Mappers;

import java.net.URI;
import java.util.Map;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.jsonResponseHandler;

public class CommerzbankOauth2Service extends AbstractService implements Oauth2Service {

    private final TokenResponseMapper tokenResponseMapper = Mappers.getMapper(TokenResponseMapper.class);
    private final Aspsp aspsp;

    public CommerzbankOauth2Service(Aspsp aspsp, HttpClient httpClient) {
        super(httpClient);
        this.aspsp = aspsp;
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) {
        String url = StringUri.fromElements(getBaseUrl(), "/v1/token");

        parameters.setCodeVerifier("sha256");

        Response<OauthToken> response = httpClient.post(url)
            .urlEncodedBody(parameters.asMap())
            .send(jsonResponseHandler(OauthToken.class));
        return tokenResponseMapper.map(response.getBody());
    }

    private String getBaseUrl() {
        return aspsp.getIdpUrl() != null ? aspsp.getIdpUrl() : aspsp.getUrl();
    }
}
