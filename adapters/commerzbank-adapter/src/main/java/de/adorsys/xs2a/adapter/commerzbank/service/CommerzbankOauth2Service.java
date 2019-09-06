package de.adorsys.xs2a.adapter.commerzbank.service;

import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import de.adorsys.xs2a.adapter.commerzbank.service.mapper.TokenResponseMapper;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class CommerzbankOauth2Service implements Oauth2Service {

    private final TokenResponseMapper tokenResponseMapper = Mappers.getMapper(TokenResponseMapper.class);
    private final String baseUrl;

    public CommerzbankOauth2Service(String baseUrl) {

        this.baseUrl = baseUrl;
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers,
                                          String state,
                                          URI redirectUri) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers,
                                  String authorizationCode,
                                  URI redirectUri,
                                  String clientId) throws IOException {
        return tokenResponseMapper.map(new AuthorizationCodeTokenRequest(new NetHttpTransport(), new JacksonFactory(),
                new GenericUrl(StringUri.fromElements(baseUrl, "/v1/token")), authorizationCode)
                .setRedirectUri(redirectUri.toString())
                .set("client_id", clientId)
                .set("code_verifier", "sha256")
                .execute());
    }
}
