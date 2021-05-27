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
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.EmbeddedPreAuthorisationRequest;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.crealogix.model.CrealogixValidationResponse;
import de.adorsys.xs2a.adapter.impl.http.JacksonObjectMapper;
import de.adorsys.xs2a.adapter.impl.http.JsonMapper;
import de.adorsys.xs2a.adapter.impl.security.AccessTokenException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static de.adorsys.xs2a.adapter.api.config.AdapterConfig.readProperty;

public class CrealogixEmbeddedPreAuthorisationService implements EmbeddedPreAuthorisationService {
    private final Logger logger = LoggerFactory.getLogger(CrealogixEmbeddedPreAuthorisationService.class);
    private final CrealogixMapper mapper = Mappers.getMapper(CrealogixMapper.class);

    public static final String PSD2_TOKEN_URL = ".psd2_token.url";
    private static final String CREDENTIALS_JSON_BODY = "{\"username\":\"%s\",\"password\":\"%s\"}";
    private final Aspsp aspsp;

    private final HttpClient httpClient;
    private final HttpLogSanitizer logSanitizer;
    private final JsonMapper jsonMapper;
    private final String psd2TokenUrl;

    public CrealogixEmbeddedPreAuthorisationService(CrealogixClient crealogixClient,
                                                    Aspsp aspsp,
                                                    HttpClientFactory httpClientFactory) {
        this.aspsp = aspsp;
        this.httpClient = httpClientFactory.getHttpClient(aspsp.getAdapterId());
        this.logSanitizer = httpClientFactory.getHttpClientConfig().getLogSanitizer();

        jsonMapper = new JacksonObjectMapper();

        String prefix = crealogixClient.getPrefix();
        psd2TokenUrl = readProperty(prefix + PSD2_TOKEN_URL, "");

        if (StringUtils.isEmpty(psd2TokenUrl)) {
            throw new AccessTokenException("PSD2 Token URL is not provided");
        }
    }

    @Override
    public TokenResponse getToken(EmbeddedPreAuthorisationRequest request, RequestHeaders requestHeaders) {
        String psd2AuthorisationToken = retrievePsd2AuthorisationToken(request.getUsername(), request.getPassword());
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(psd2AuthorisationToken);
        return tokenResponse;
    }

    private String retrievePsd2AuthorisationToken(String username, String password) {

        Map<String, String> headers = new HashMap<>(2);
        headers.put(RequestHeaders.CONTENT_TYPE, "application/json");
        Response<TokenResponse> response = httpClient.post(adjustIdpUrl(aspsp.getIdpUrl()) + psd2TokenUrl)
            .jsonBody(String.format(CREDENTIALS_JSON_BODY, username, password))
            .headers(headers)
            .send(responseHandler(CrealogixValidationResponse.class))
            .map(mapper::toTokenResponse);
        return response.getBody().getAccessToken();
    }

    <T> HttpClient.ResponseHandler<T> responseHandler(Class<T> tClass) {
        return (statusCode, responseBody, responseHeaders) -> {
            if (isSuccess(statusCode)) {
                return jsonMapper.readValue(responseBody, tClass);
            }
            String sanitizedResponse = logSanitizer.sanitize(toString(responseBody));
            logger.error("Failed to retrieve Token. Status code: {}\nBank response: {}", statusCode, sanitizedResponse);
            throw new AccessTokenException("Can't retrieve access token by provided credentials");
        };
    }

    private String toString(InputStream inputStream) {
        try {
            return IOUtils.toString(inputStream, Charset.defaultCharset());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private boolean isSuccess(int statusCode) {
        return Status.Family.SUCCESSFUL.equals(Status.Family.familyOf(statusCode));
    }

    private static String adjustIdpUrl(String idpUrl) {
        return idpUrl.endsWith("/") ? idpUrl.substring(0, idpUrl.length() - 1) : idpUrl;
    }
}
