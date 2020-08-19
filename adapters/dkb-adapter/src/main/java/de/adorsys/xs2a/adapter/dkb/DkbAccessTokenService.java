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

package de.adorsys.xs2a.adapter.dkb;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.impl.http.JacksonObjectMapper;
import de.adorsys.xs2a.adapter.impl.http.JsonMapper;
import de.adorsys.xs2a.adapter.impl.security.AccessTokenException;
import de.adorsys.xs2a.adapter.impl.security.AccessTokenService;
import de.adorsys.xs2a.adapter.service.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static de.adorsys.xs2a.adapter.service.config.AdapterConfig.readProperty;

public class DkbAccessTokenService implements AccessTokenService {
    private static final Logger logger = LoggerFactory.getLogger(DkbAccessTokenService.class);

    public static final String DKB_TOKEN_CONSUMER_KEY_PROPERTY = "dkb.token.consumer_key";
    public static final String DKB_TOKEN_CONSUMER_SECRET_PROPERTY = "dkb.token.consumer_secret";
    private static final String DKB_TOKEN_URL_PROPERTY = "dkb.token.url";
    private static final String DKB_TOKEN_SECONDS_BEFORE_TOKEN_EXPIRATION_PROPERTY = "dkb.token.seconds_before_token_expiration";

    private static final String DEFAULT_SECONDS_BEFORE_TOKEN_EXPIRATION = "60";
    private static final String DEFAULT_TOKEN_URL = "https://api.dkb.de/token";

    private static DkbAccessTokenService instance;
    private static Map<String, String> headers;
    private static String tokenUrl;
    private static int secondsBeforeTokenExpiration;

    private HttpClient httpClient;

    private AccessToken accessToken;

    private JsonMapper jsonMapper;


    private DkbAccessTokenService() {
        jsonMapper = new JacksonObjectMapper();

        String consumerKey = readProperty(DKB_TOKEN_CONSUMER_KEY_PROPERTY, "");
        String consumerSecret = readProperty(DKB_TOKEN_CONSUMER_SECRET_PROPERTY, "");

        if (consumerKey.isEmpty() || consumerSecret.isEmpty()) {
            String message = "Consumer key or secret are not provided";
            logger.error(message);
            throw new AccessTokenException(message);
        }

        headers = new HashMap<>();
        headers.put("Authorization", "Basic " + buildBasicAuthorization(consumerKey, consumerSecret));

        tokenUrl = readProperty(DKB_TOKEN_URL_PROPERTY, DEFAULT_TOKEN_URL);
        logger.debug("Token url is {}", tokenUrl);

        secondsBeforeTokenExpiration = Integer.parseInt(readProperty(
            DKB_TOKEN_SECONDS_BEFORE_TOKEN_EXPIRATION_PROPERTY,
            DEFAULT_SECONDS_BEFORE_TOKEN_EXPIRATION
        ));
        logger.debug("Seconds before token expiration is {}", secondsBeforeTokenExpiration);
    }

    public static synchronized DkbAccessTokenService getInstance() {
        if (instance == null) {
            instance = new DkbAccessTokenService();
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
        // {"access_token":"9c60cc0a-7b38-3a2a-9b43-2b7d9d957838","scope":"am_application_scope default","token_type":"Bearer","expires_in":3600}
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("expires_in")
        private int expiresIn;

        // DO NOT remove default constructor. It needs for ObjectMapper
        TokenResponse() {
        }

        TokenResponse(String accessToken, int expiresIn) {
            this.accessToken = accessToken;
            this.expiresIn = expiresIn;
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
