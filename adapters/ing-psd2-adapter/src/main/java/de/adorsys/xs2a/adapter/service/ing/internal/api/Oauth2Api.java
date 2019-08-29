package de.adorsys.xs2a.adapter.service.ing.internal.api;

import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.TokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ObjectParser;
import de.adorsys.xs2a.adapter.service.ing.internal.api.model.*;

import java.io.IOException;

public class Oauth2Api {
    private static final String TOKEN_ENDPOINT = "/oauth2/token";
    private static final String AUTHORIZATION_ENDPOINT = "/oauth2/authorization-server-url";

    private final Host host;
    private final HttpTransport transport;
    private final JacksonFactory jsonFactory = new JacksonFactory();
    private final ObjectParser parser;

    public Oauth2Api(Host host,
                     HttpTransport transport,
                     ObjectParser parser) {
        this.host = host;
        this.transport = transport;
        this.parser = parser;
    }

    public ApplicationTokenResponse  getApplicationToken(HttpExecuteInterceptor clientAuthentication) throws IOException {
        // When using eIDAS certificates supporting PSD2 the scope parameter is not required.
        // The scopes will be derived automatically from the PSD2 roles in the certificate.
        // When using eIDAS certificates supporting PSD2, the response will contain the client ID of your application,
        // this client ID has to be used in the rest of the session when the client ID or key ID is required.
        return new TokenRequest(transport, jsonFactory, getUrl(TOKEN_ENDPOINT), "client_credentials")
            .setClientAuthentication(clientAuthentication)
            .executeUnparsed()
            .parseAs(ApplicationTokenResponse.class);
    }

    private GenericUrl getUrl(String endpoint) {
        return new GenericUrl(host.toString() + endpoint);
    }

    public TokenResponse getCustomerToken(String authorizationCode,
                                          HttpExecuteInterceptor clientAuthentication) throws IOException {

        return new AuthorizationCodeTokenRequest(transport, jsonFactory, getUrl(TOKEN_ENDPOINT), authorizationCode)
            .setClientAuthentication(clientAuthentication)
            .execute();
    }

    public AuthorizationURLResponse getAuthorizationUrl(HttpExecuteInterceptor clientAuthentication) throws IOException {
        return transport.createRequestFactory()
            .buildGetRequest(getUrl(AUTHORIZATION_ENDPOINT))
            .setInterceptor(clientAuthentication)
            .setParser(parser)
            .execute()
            .parseAs(AuthorizationURLResponse.class);
    }
}
