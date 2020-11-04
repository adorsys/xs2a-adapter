/*
 * Copyright 2018-2020 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.EmbeddedPreAuthorisationService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.EmbeddedPreAuthorisationRequest;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.impl.http.JacksonObjectMapper;
import de.adorsys.xs2a.adapter.impl.http.JsonMapper;
import de.adorsys.xs2a.adapter.impl.security.AccessTokenException;

import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static de.adorsys.xs2a.adapter.api.config.AdapterConfig.readProperty;

public class CrealogixEmbeddedPreAuthorisationService implements EmbeddedPreAuthorisationService {
    public static final String TOKEN_CONSUMER_KEY_PROPERTY = ".token.consumer_key";
    public static final String TOKEN_CONSUMER_SECRET_PROPERTY = ".token.consumer_secret";
    private static final String CREDENTIALS_JSON_BODY = "{\"username\":\"%s\",\"password\":\"%s\"}";
    private Aspsp aspsp;

    private static final String TOKEN_URL = "/token";
    private static Map<String, String> tppHeaders = new HashMap<>();
    private HttpClient httpClient;
    private JsonMapper jsonMapper;

    public CrealogixEmbeddedPreAuthorisationService(CrealogixClient crealogixClient, Aspsp aspsp, HttpClient httpClient) {
        this.aspsp = aspsp;
        this.httpClient = httpClient;

        jsonMapper = new JacksonObjectMapper();

        String prefix = crealogixClient.getPrefix();
        String consumerKey = readProperty(prefix + TOKEN_CONSUMER_KEY_PROPERTY, "");
        String consumerSecret = readProperty(prefix + TOKEN_CONSUMER_SECRET_PROPERTY, "");

        if (consumerKey.isEmpty() || consumerSecret.isEmpty()) {
            throw new AccessTokenException("Consumer key or secret are not provided");
        }

        tppHeaders.put(RequestHeaders.AUTHORIZATION, "Basic " + buildBasicAuthorization(consumerKey, consumerSecret));
        tppHeaders.put(RequestHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
    }

    @Override
    public TokenResponse getToken(EmbeddedPreAuthorisationRequest request) {
        String tppToken = retrieveTppToken();
        String psd2AuthorisationToken = retrievePsd2AuthorisationToken(request.getUsername(), request.getPassword(), tppToken);
        TokenResponse tokenResponse = new TokenResponse();
        String encodedCrealogixToken = new CrealogixAuthorisationToken(tppToken, psd2AuthorisationToken).encode();
        tokenResponse.setAccessToken(encodedCrealogixToken);
        return tokenResponse;
    }

    private String retrieveTppToken() {
        Response<TokenResponse> response = httpClient.post(adjustIdpUrl(aspsp.getIdpUrl()) + TOKEN_URL)
                                               .urlEncodedBody(Collections.singletonMap("grant_type", "client_credentials"))
                                               .headers(tppHeaders)
                                               .send(responseHandler());
        return response.getBody().getAccessToken();
    }

    private String retrievePsd2AuthorisationToken(String username, String password, String tppToken) {

        Map<String, String> headers = new HashMap<>(2);
        headers.put(RequestHeaders.CONTENT_TYPE, "application/json");
        headers.put(RequestHeaders.AUTHORIZATION, tppToken);

        Response<TokenResponse> response = httpClient.post(adjustIdpUrl(aspsp.getIdpUrl()) + "/pre-auth/1.0.6/psd2-auth/v1/auth/token")
                                               .jsonBody(String.format(CREDENTIALS_JSON_BODY, username, password))
                                               .headers(headers)
                                               .send(responseHandler());
        return response.getBody().getAccessToken();
    }

    private HttpClient.ResponseHandler<TokenResponse> responseHandler() {
        return (statusCode, responseBody, responseHeaders) -> {
            if (statusCode == 200) {
                return jsonMapper.readValue(responseBody, TokenResponse.class);
            }
            throw new AccessTokenException("Can't retrieve access token by provided credentials");
        };
    }

    private static String buildBasicAuthorization(String key, String secret) {
        String credentials = key + ":" + secret;
        return new String(Base64.getEncoder().encode(credentials.getBytes()));
    }

    private static String adjustIdpUrl(String idpUrl) {
        return idpUrl.endsWith("/") ? idpUrl.substring(0, idpUrl.length() - 1) : idpUrl;
    }
}
