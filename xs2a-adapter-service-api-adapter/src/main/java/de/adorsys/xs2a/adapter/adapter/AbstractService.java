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

package de.adorsys.xs2a.adapter.adapter;

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.JsonMapper;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.ErrorResponse;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.SinglePaymentInitiationBody;
import de.adorsys.xs2a.adapter.service.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.service.exception.NotAcceptableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractService {
    private static final Logger log = LoggerFactory.getLogger(AbstractService.class);

    protected static final Pattern CHARSET_PATTERN = Pattern.compile("charset=([^;]+)");
    protected static final String AUTHORISATIONS = "authorisations";
    protected static final String STATUS = "status";
    protected static final String CONTENT_TYPE_HEADER = "Content-Type";
    protected static final String APPLICATION_JSON = "application/json";
    protected static final String ACCEPT_HEADER = "Accept";
    protected final JsonMapper jsonMapper = new JsonMapper();
    protected HttpClient httpClient = HttpClient.newHttpClient();

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    protected Map<String, String> addConsentIdHeader(Map<String, String> map) {
        return map;
    }

    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        return map;
    }

    protected Map<String, String> populatePutHeaders(Map<String, String> map) {
        return map;
    }

    protected Map<String, String> populateGetHeaders(Map<String, String> map) {
        return map;
    }

    <T> HttpClient.ResponseHandler<T> jsonResponseHandler(Class<T> klass) {
        return (statusCode, responseBody, responseHeaders) -> {
            String contentType = responseHeaders.getHeader(CONTENT_TYPE_HEADER);

            if (!contentType.startsWith(APPLICATION_JSON)) {
                NotAcceptableException notAcceptableException = new NotAcceptableException(buildNotAcceptableExceptionMessage(contentType, APPLICATION_JSON));
                log.error(notAcceptableException.getMessage(), notAcceptableException);
                throw notAcceptableException;
            }
            if (statusCode == 200 || statusCode == 201) {
                return jsonMapper.readValue(responseBody, klass);
            }

            ErrorResponseException errorResponseException = responseException(statusCode, new PushbackInputStream(responseBody), responseHeaders);
            log.error(errorResponseException.getMessage(), errorResponseException);
            throw errorResponseException;
        };
    }

    /**
     * Should be used, when it is not clear, what content type will be used in bank response.
     */
    HttpClient.ResponseHandler<String> stringResponseHandler() {
        return (statusCode, responseBody, responseHeaders) -> {
            if (statusCode == 200) {
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = responseBody.read(buffer)) != -1) {
                        baos.write(buffer, 0, length);
                    }

                    Matcher matcher = CHARSET_PATTERN.matcher(responseHeaders.getHeader(CONTENT_TYPE_HEADER));

                    String charset = StandardCharsets.UTF_8.name();

                    if (matcher.find()) {
                        charset = matcher.group(1);
                    }

                    log.info("{} charset is used for response body parsing", charset);
                    return baos.toString(charset);
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                    throw new UncheckedIOException(e);
                }
            }

            ErrorResponseException errorResponseException = responseException(statusCode, new PushbackInputStream(responseBody), responseHeaders);
            log.error(errorResponseException.getMessage(), errorResponseException);
            throw errorResponseException;
        };
    }

    protected ErrorResponseException responseException(int statusCode, PushbackInputStream responseBody, ResponseHeaders responseHeaders) {
        if (isEmpty(responseBody)) {
            return new ErrorResponseException(statusCode, responseHeaders);
        }
        return new ErrorResponseException(statusCode, responseHeaders, jsonMapper.readValue(responseBody, ErrorResponse.class));
    }

    private boolean isEmpty(PushbackInputStream responseBody) {
        try {
            int nextByte = responseBody.read();
            if (nextByte == -1) {
                return true;
            }
            responseBody.unread(nextByte);
            return false;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new UncheckedIOException(e);
        }
    }

    protected static String buildUri(String uri, RequestParams requestParams) {
        if (requestParams == null) {
            return uri;
        }

        Map<String, String> requestParamsMap = requestParams.toMap();

        return StringUri.withQuery(uri, requestParamsMap);
    }

    private String buildNotAcceptableExceptionMessage(String actualContentType, String expectedContentType) {
        return String.format("Content type %s is not acceptable, has to start with %s", actualContentType, expectedContentType);
    }

    protected Class<?> getSinglePaymentInitiationBodyClass() {
        return SinglePaymentInitiationBody.class;
    }
}
