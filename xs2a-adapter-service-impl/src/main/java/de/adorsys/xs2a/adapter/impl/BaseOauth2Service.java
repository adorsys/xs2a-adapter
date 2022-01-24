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

package de.adorsys.xs2a.adapter.impl;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.impl.http.ResponseHandlers;
import de.adorsys.xs2a.adapter.impl.http.UriBuilder;
import de.adorsys.xs2a.adapter.impl.oauth2.api.model.AuthorisationServerMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class BaseOauth2Service implements Oauth2Service {
    private static final Logger logger = LoggerFactory.getLogger(BaseOauth2Service.class);

    private final HttpClient httpClient;
    private final Aspsp aspsp;
    private final ResponseHandlers responseHandlers;

    public BaseOauth2Service(Aspsp aspsp, HttpClient httpClient) {
        this(aspsp, httpClient, null);
    }

    public BaseOauth2Service(Aspsp aspsp, HttpClient httpClient, HttpLogSanitizer logSanitizer) {
        this.httpClient = httpClient;
        this.aspsp = aspsp;
        this.responseHandlers = new ResponseHandlers(logSanitizer);
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {

        return UriBuilder.fromUri(getAuthorizationEndpoint(parameters))
            .queryParam(Parameters.RESPONSE_TYPE, ResponseType.CODE.toString())
            .queryParam(Parameters.STATE, parameters.getState())
            .queryParam(Parameters.REDIRECT_URI, parameters.getRedirectUri())
            .queryParam(Parameters.SCOPE, parameters.getScope())
            .build();
    }

    private String getAuthorizationEndpoint(Parameters parameters) {
        String authorizationEndpoint = parameters.getAuthorizationEndpoint();
        if (authorizationEndpoint != null) {
            logger.debug("Get Authorisation Request URI: resolved on the adapter side");
            return authorizationEndpoint;
        }
        AuthorisationServerMetaData metadata = getAuthorizationServerMetadata(parameters);
        return metadata.getAuthorisationEndpoint();
    }

    private AuthorisationServerMetaData getAuthorizationServerMetadata(Parameters parameters) {
        String scaOAuthLink = parameters.removeScaOAuthLink();
        String metadataUri = scaOAuthLink != null ? scaOAuthLink : aspsp.getIdpUrl();
        return httpClient.get(metadataUri)
            .send(responseHandlers.jsonResponseHandler(AuthorisationServerMetaData.class))
            .getBody();
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) throws IOException {

        Response<TokenResponse> response = httpClient.post(getTokenEndpoint(parameters))
            .urlEncodedBody(parameters.asMap())
            .send(responseHandlers.jsonResponseHandler(TokenResponse.class));
        return response.getBody();
    }

    private String getTokenEndpoint(Parameters parameters) {
        String tokenEndpoint = parameters.removeTokenEndpoint();
        if (tokenEndpoint != null) {
            return tokenEndpoint;
        }
        return getAuthorizationServerMetadata(parameters).getTokenEndpoint();
    }
}
