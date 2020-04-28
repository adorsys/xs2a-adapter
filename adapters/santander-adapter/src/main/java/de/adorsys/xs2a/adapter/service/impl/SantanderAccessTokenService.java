/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
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

package de.adorsys.xs2a.adapter.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.JsonMapper;
import de.adorsys.xs2a.adapter.security.AccessTokenException;
import de.adorsys.xs2a.adapter.security.AccessTokenService;
import de.adorsys.xs2a.adapter.service.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static de.adorsys.xs2a.adapter.service.config.AdapterConfig.readProperty;

public class SantanderAccessTokenService implements AccessTokenService {
    private static final Logger logger = LoggerFactory.getLogger(SantanderAccessTokenService.class);

    public static final String SANTANDER_TOKEN_CONSUMER_KEY_PROPERTY = "santander.token.consumer_key";
    private static final String SANTANDER_TOKEN_CONSUMER_SECRET_PROPERTY = "santander.token.consumer_secret";
    private static final String SANTANDER_TOKEN_URL_PROPERTY = "santander.token.url";
    private static final String SANTANDER_TOKEN_SECONDS_BEFORE_TOKEN_EXPIRATION_PROPERTY = "santander.token.seconds_before_token_expiration";

    private static final String DEFAULT_SECONDS_BEFORE_TOKEN_EXPIRATION = "60";
    private static final String DEFAULT_TOKEN_URL = "https://apigateway-sandbox.api.santander.de/scb-openapis/sx/oauthsos/password/token";

    private static SantanderAccessTokenService instance;
    private static Map<String, String> headers;
    private static String tokenUrl;
    private static int secondsBeforeTokenExpiration;

    private HttpClient httpClient;
    private AccessToken accessToken;
    private JsonMapper jsonMapper;

    private SantanderAccessTokenService() {
        jsonMapper = new JsonMapper();

        String consumerKey = readProperty(SANTANDER_TOKEN_CONSUMER_KEY_PROPERTY, "");
        String consumerSecret = readProperty(SANTANDER_TOKEN_CONSUMER_SECRET_PROPERTY, "");

        if (consumerKey.isEmpty() || consumerSecret.isEmpty()) {
            String message = "Consumer key or secret are not provided";
            logger.error(message);
            throw new AccessTokenException(message);
        }

        headers = new HashMap<>();
        headers.put("Authorization", "Basic " + buildBasicAuthorization(consumerKey, consumerSecret));

        tokenUrl = readProperty(SANTANDER_TOKEN_URL_PROPERTY, DEFAULT_TOKEN_URL);
        logger.debug("Token url is {}", tokenUrl);

        secondsBeforeTokenExpiration = Integer.parseInt(readProperty(
            SANTANDER_TOKEN_SECONDS_BEFORE_TOKEN_EXPIRATION_PROPERTY,
            DEFAULT_SECONDS_BEFORE_TOKEN_EXPIRATION
        ));
        logger.debug("Seconds before token expiration is {}", secondsBeforeTokenExpiration);
    }

    public static synchronized SantanderAccessTokenService getInstance() {
        if (instance == null) {
            instance = new SantanderAccessTokenService();
        }
        return instance;
    }

    @Override
    public String retrieveToken() {
        if (isNotValid()) {
            logger.debug("Token is not valid");
            Response<TokenResponse> response = httpClient.post(tokenUrl)
                .urlEncodedBody(Collections.singletonMap("grant_type", "client_credentials"))
                .headers(headers)
                .send(responseHandler());
            logger.debug("New token is retrieved");
            accessToken = new AccessToken(response.getBody());
        }
        return accessToken.token;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    private boolean isNotValid() {
        if (accessToken == null || accessToken.token == null || accessToken.validity == null) {
            return true;
        }
        LocalDateTime expirationDate = LocalDateTime.now().plusSeconds(secondsBeforeTokenExpiration);
        return accessToken.validity.isBefore(expirationDate);
    }

    private HttpClient.ResponseHandler<TokenResponse> responseHandler() {
        return (statusCode, responseBody, responseHeaders) -> {
            if (statusCode == 200) {
                return jsonMapper.readValue(responseBody, TokenResponse.class);
            }
            String message = "Can't retrieve access token by provided credentials";
            logger.error(message);
            throw new AccessTokenException(message);
        };
    }

    private static String buildBasicAuthorization(String key, String secret) {
        String credentials = key + ":" + secret;
        return new String(Base64.getEncoder().encode(credentials.getBytes()));
    }

    static class TokenResponse {
        //  {
        //      "access_token": "716039d4-bebf-4188-824d-1fecec377164",
        //      "token_type": "bearer",
        //      "expires_in": 599,
        //      "scope": "payments.write periodicpayments.read payment.read fundsconfimations.read consents.write consents.read accounts.read payments.read periodicpayments.write"
        //  }
        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("token_type")
        private String tokenType;

        @JsonProperty("expires_in")
        private int expiresIn;

        private String scope;

        // DO NOT remove default constructor. It needs for ObjectMapper
        TokenResponse() {
        }

        TokenResponse(String accessToken, String tokenType, int expiresIn, String scope) {
            this.accessToken = accessToken;
            this.tokenType = tokenType;
            this.expiresIn = expiresIn;
            this.scope = scope;
        }
    }

    private static class AccessToken {
        private String token;
        private LocalDateTime validity;

        AccessToken(TokenResponse response) {
            token = response.accessToken;
            validity = LocalDateTime.now().plusSeconds(response.expiresIn);
        }
    }
}
