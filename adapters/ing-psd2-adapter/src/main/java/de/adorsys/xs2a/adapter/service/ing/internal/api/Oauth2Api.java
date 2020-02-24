package de.adorsys.xs2a.adapter.service.ing.internal.api;

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.http.UriBuilder;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ing.internal.api.model.ApplicationTokenResponse;
import de.adorsys.xs2a.adapter.service.ing.internal.api.model.AuthorizationURLResponse;
import de.adorsys.xs2a.adapter.service.ing.internal.api.model.TokenResponse;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.jsonResponseHandler;
import static java.util.Collections.singletonMap;

public class Oauth2Api {
    private static final String TOKEN_ENDPOINT = "/oauth2/token";
    private static final String AUTHORIZATION_ENDPOINT = "/oauth2/authorization-server-url";

    private final String baseUri;
    private final HttpClient httpClient;

    public Oauth2Api(String baseUri, HttpClient httpClient) {
        this.baseUri = baseUri;
        this.httpClient = httpClient;
    }

    public Response<ApplicationTokenResponse> getApplicationToken(Request.Builder.Interceptor clientAuthentication) {
        // When using eIDAS certificates supporting PSD2 the scope parameter is not required.
        // The scopes will be derived automatically from the PSD2 roles in the certificate.
        // When using eIDAS certificates supporting PSD2, the response will contain the client ID of your application,
        // this client ID has to be used in the rest of the session when the client ID or key ID is required.
        return httpClient.post(baseUri + TOKEN_ENDPOINT)
            .urlEncodedBody(singletonMap("grant_type", "client_credentials"))
            .send(clientAuthentication, jsonResponseHandler(ApplicationTokenResponse.class));
    }

    public Response<TokenResponse> getCustomerToken(Oauth2Service.Parameters parameters,
                                                    Request.Builder.Interceptor clientAuthentication) {

        return httpClient.post(baseUri + TOKEN_ENDPOINT)
            .urlEncodedBody(parameters.asMap())
            .send(clientAuthentication, jsonResponseHandler(TokenResponse.class));
    }

    public Response<AuthorizationURLResponse> getAuthorizationUrl(Request.Builder.Interceptor clientAuthentication, String scope) {
        String uri = StringUri.fromElements(baseUri + AUTHORIZATION_ENDPOINT + "?scope=" + scope);
        return httpClient.get(uri)
            .send(clientAuthentication, jsonResponseHandler(AuthorizationURLResponse.class));
    }
}
