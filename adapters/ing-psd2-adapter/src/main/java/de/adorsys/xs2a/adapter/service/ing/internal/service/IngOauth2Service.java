package de.adorsys.xs2a.adapter.service.ing.internal.service;

import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.ing.internal.api.ClientAuthentication;
import de.adorsys.xs2a.adapter.service.ing.internal.api.ClientAuthenticationFactory;
import de.adorsys.xs2a.adapter.service.ing.internal.api.Oauth2Api;
import de.adorsys.xs2a.adapter.service.ing.internal.api.model.ApplicationTokenResponse;
import de.adorsys.xs2a.adapter.service.ing.internal.api.model.AuthorizationURLResponse;
import de.adorsys.xs2a.adapter.service.ing.internal.api.model.TokenResponse;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

public class IngOauth2Service {

    private final Oauth2Api oauth2Api;
    private final ClientAuthenticationFactory clientAuthenticationFactory;

    private ApplicationTokenResponse applicationToken;

    public IngOauth2Service(Oauth2Api oauth2Api,
                            ClientAuthenticationFactory clientAuthenticationFactory) {
        this.oauth2Api = oauth2Api;
        this.clientAuthenticationFactory = clientAuthenticationFactory;
    }

    public URI getAuthorizationRequestUri(String state, URI oauthRedirectUri)  {
        ClientAuthentication clientAuthentication =
            clientAuthenticationFactory.newClientAuthentication(getApplicationToken());
        AuthorizationURLResponse authorizationUrlResponse = oauth2Api.getAuthorizationUrl(clientAuthentication)
            .getBody();

        Map<String, Object> queryParams = new LinkedHashMap<>();
        queryParams.put("client_id", getClientId());
        queryParams.put("state", state);
        queryParams.put("redirect_uri", oauthRedirectUri);

        return URI.create(StringUri.withQuery(authorizationUrlResponse.getLocation(), queryParams));
    }

    private ApplicationTokenResponse getApplicationToken() {
        if (applicationToken != null) {
            return applicationToken;
        }

        ClientAuthentication clientAuthentication =
            clientAuthenticationFactory.newClientAuthenticationForApplicationToken();
        applicationToken = oauth2Api.getApplicationToken(clientAuthentication)
            .getBody();
        return applicationToken;
    }

    private String getClientId() {
        return getApplicationToken().getClientId();
    }

    public TokenResponse getToken(String authorizationCode) {
        ClientAuthentication clientAuthentication =
            clientAuthenticationFactory.newClientAuthentication(getApplicationToken());
        return oauth2Api.getCustomerToken(authorizationCode, clientAuthentication)
            .getBody();
    }

    public ClientAuthentication getClientAuthentication(String accessToken) {
        return clientAuthenticationFactory.newClientAuthentication(getApplicationToken(), accessToken);
    }
}
