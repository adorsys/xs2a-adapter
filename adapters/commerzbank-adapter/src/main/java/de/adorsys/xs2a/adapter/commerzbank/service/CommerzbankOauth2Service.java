package de.adorsys.xs2a.adapter.commerzbank.service;

import de.adorsys.xs2a.adapter.adapter.AbstractService;
import de.adorsys.xs2a.adapter.adapter.mapper.TokenResponseMapper;
import de.adorsys.xs2a.adapter.adapter.model.OauthToken;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import org.mapstruct.factory.Mappers;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.jsonResponseHandler;

public class CommerzbankOauth2Service extends AbstractService implements Oauth2Service {

    private final TokenResponseMapper tokenResponseMapper = Mappers.getMapper(TokenResponseMapper.class);
    private final String baseUrl;

    public CommerzbankOauth2Service(String baseUrl, HttpClient httpClient) {
        super(httpClient);
        this.baseUrl = baseUrl;
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers,
                                          String state,
                                          URI redirectUri) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers,
                                  String authorizationCode,
                                  URI redirectUri,
                                  String clientId) {
        String url = StringUri.fromElements(baseUrl, "/v1/token");

        Map<String, String> params = new HashMap<>();
        params.put("redirect_uri", redirectUri.toString());
        params.put("client_id", clientId);
        params.put("grant_type", "authorization_code");
        params.put("code", authorizationCode);
        params.put("code_verifier", "sha256");

        Response<OauthToken> response = httpClient.post(url)
            .urlEncodedBody(params)
            .send(jsonResponseHandler(OauthToken.class));
        return tokenResponseMapper.map(response.getBody());
    }
}
