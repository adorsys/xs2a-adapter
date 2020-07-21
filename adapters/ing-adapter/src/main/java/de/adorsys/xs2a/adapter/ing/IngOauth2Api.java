package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.ing.model.IngApplicationTokenResponse;
import de.adorsys.xs2a.adapter.ing.model.IngAuthorizationURLResponse;
import de.adorsys.xs2a.adapter.ing.model.IngTokenResponse;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Response;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.jsonResponseHandler;
import static java.util.Collections.singletonMap;

public class IngOauth2Api {
    private static final String TOKEN_ENDPOINT = "/oauth2/token";
    private static final String AUTHORIZATION_ENDPOINT = "/oauth2/authorization-server-url";

    private final String baseUri;
    private final HttpClient httpClient;

    public IngOauth2Api(String baseUri, HttpClient httpClient) {
        this.baseUri = baseUri;
        this.httpClient = httpClient;
    }

    public Response<IngApplicationTokenResponse> getApplicationToken(Request.Builder.Interceptor clientAuthentication) {
        // When using eIDAS certificates supporting PSD2 the scope parameter is not required.
        // The scopes will be derived automatically from the PSD2 roles in the certificate.
        // When using eIDAS certificates supporting PSD2, the response will contain the client ID of your application,
        // this client ID has to be used in the rest of the session when the client ID or key ID is required.
        return httpClient.post(baseUri + TOKEN_ENDPOINT)
            .urlEncodedBody(singletonMap("grant_type", "client_credentials"))
            .send(clientAuthentication, jsonResponseHandler(IngApplicationTokenResponse.class));
    }

    public Response<IngTokenResponse> getCustomerToken(Oauth2Service.Parameters parameters,
                                                       Request.Builder.Interceptor clientAuthentication) {

        return httpClient.post(baseUri + TOKEN_ENDPOINT)
            .urlEncodedBody(parameters.asMap())
            .send(clientAuthentication, jsonResponseHandler(IngTokenResponse.class));
    }

    public Response<IngAuthorizationURLResponse> getAuthorizationUrl(Request.Builder.Interceptor clientAuthentication,
                                                                     String scope,
                                                                     String redirectUri) {
        Oauth2Service.Parameters queryParameters = new Oauth2Service.Parameters();
        queryParameters.setScope(scope);
        queryParameters.setRedirectUri(redirectUri);
        String uri = StringUri.withQuery(baseUri + AUTHORIZATION_ENDPOINT, queryParameters.asMap());
        return httpClient.get(uri)
            .send(clientAuthentication, jsonResponseHandler(IngAuthorizationURLResponse.class));
    }
}
