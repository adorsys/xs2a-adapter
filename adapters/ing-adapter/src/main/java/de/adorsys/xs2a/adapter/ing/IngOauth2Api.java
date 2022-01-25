/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.impl.http.ResponseHandlers;
import de.adorsys.xs2a.adapter.impl.http.StringUri;
import de.adorsys.xs2a.adapter.ing.model.IngApplicationTokenResponse;
import de.adorsys.xs2a.adapter.ing.model.IngAuthorizationURLResponse;
import de.adorsys.xs2a.adapter.ing.model.IngTokenResponse;

import java.util.List;

import static java.util.Collections.singletonMap;

public class IngOauth2Api {
    private static final String TOKEN_ENDPOINT = "/oauth2/token";
    private static final String AUTHORIZATION_ENDPOINT = "/oauth2/authorization-server-url";

    private final String baseUri;
    private final HttpClient httpClient;
    private final ResponseHandlers handlers;

    public IngOauth2Api(String baseUri, HttpClient httpClient, HttpLogSanitizer logSanitizer) {
        this.baseUri = baseUri;
        this.httpClient = httpClient;
        this.handlers = new ResponseHandlers(logSanitizer);
    }

    public Response<IngApplicationTokenResponse> getApplicationToken(List<Interceptor> interceptors) {
        // When using eIDAS certificates supporting PSD2 the scope parameter is not required.
        // The scopes will be derived automatically from the PSD2 roles in the certificate.
        // When using eIDAS certificates supporting PSD2, the response will contain the client ID of your application,
        // this client ID has to be used in the rest of the session when the client ID or key ID is required.
        return httpClient.post(baseUri + TOKEN_ENDPOINT)
                   .urlEncodedBody(singletonMap("grant_type", "client_credentials"))
                   .send(handlers.jsonResponseHandler(IngApplicationTokenResponse.class), interceptors);
    }

    public Response<IngTokenResponse> getCustomerToken(Oauth2Service.Parameters parameters,
                                                       List<Interceptor> interceptors) {

        return httpClient.post(baseUri + TOKEN_ENDPOINT)
                   .urlEncodedBody(parameters.asMap())
                   .send(handlers.jsonResponseHandler(IngTokenResponse.class), interceptors);
    }

    public Response<IngAuthorizationURLResponse> getAuthorizationUrl(List<Interceptor> interceptors,
                                                                     String scope,
                                                                     String redirectUri) {
        Oauth2Service.Parameters queryParameters = new Oauth2Service.Parameters();
        queryParameters.setScope(scope);
        queryParameters.setRedirectUri(redirectUri);
        String uri = StringUri.withQuery(baseUri + AUTHORIZATION_ENDPOINT, queryParameters.asMap());
        return httpClient.get(uri)
                   .send(handlers.jsonResponseHandler(IngAuthorizationURLResponse.class), interceptors);
    }
}
