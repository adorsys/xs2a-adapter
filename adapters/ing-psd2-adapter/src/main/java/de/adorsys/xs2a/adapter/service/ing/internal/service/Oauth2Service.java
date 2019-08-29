package de.adorsys.xs2a.adapter.service.ing.internal.service;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.TokenResponse;
import de.adorsys.xs2a.adapter.service.ing.internal.api.ApplicationTokenResponse;
import de.adorsys.xs2a.adapter.service.ing.internal.api.ClientAuthentication;
import de.adorsys.xs2a.adapter.service.ing.internal.api.ClientAuthenticationFactory;
import de.adorsys.xs2a.adapter.service.ing.internal.api.Oauth2Api;
import de.adorsys.xs2a.adapter.service.ing.internal.api.model.AuthorizationURLResponse;

import java.io.IOException;
import java.net.URI;

public class Oauth2Service {

    private final Oauth2Api oauth2Api;
    private final ClientAuthenticationFactory clientAuthenticationFactory;

    private ApplicationTokenResponse applicationToken;

    public Oauth2Service(Oauth2Api oauth2Api,
                         ClientAuthenticationFactory clientAuthenticationFactory) {
        this.oauth2Api = oauth2Api;
        this.clientAuthenticationFactory = clientAuthenticationFactory;
    }

    public URI getAuthorizationRequestUri(String state, URI oauthRedirectUri) throws IOException {
        ClientAuthentication clientAuthentication = clientAuthenticationFactory.newClientAuthentication(getApplicationToken());
        AuthorizationURLResponse authorizationUrlResponse = oauth2Api.getAuthorizationUrl(clientAuthentication);

        AuthorizationCodeRequestUrl authorizationUrl = new AuthorizationCodeRequestUrl(authorizationUrlResponse.getLocation(), getClientId())
            .setState(state)
            .setRedirectUri(oauthRedirectUri.toString());
        return authorizationUrl.toURI();
    }

    private ApplicationTokenResponse getApplicationToken() throws IOException {
        if (applicationToken != null) {
            return applicationToken;
        }

        ClientAuthentication clientAuthentication = clientAuthenticationFactory.newClientAuthenticationForApplicationToken();
        applicationToken = oauth2Api.getApplicationToken(clientAuthentication);
        return applicationToken;
    }

    private String getClientId() throws IOException {
        return getApplicationToken().getClientId();
    }

    public TokenResponse getToken(String authorizationCode) throws IOException {
        ClientAuthentication clientAuthentication = clientAuthenticationFactory.newClientAuthentication(getApplicationToken());
        return oauth2Api.getCustomerToken(authorizationCode, clientAuthentication);
    }

    public ClientAuthentication getClientAuthentication(String accessToken) throws IOException {
        return clientAuthenticationFactory.newClientAuthentication(getApplicationToken(), accessToken);
    }
}
