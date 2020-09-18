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

package de.adorsys.xs2a.adapter.impl.http.wiremock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;

public class WiremockStubDifferenceDetectingInterceptor implements Request.Builder.Interceptor {
    private static final Logger log = LoggerFactory.getLogger(WiremockStubDifferenceDetectingInterceptor.class);
    private static final String EQUAL_TO = "equalTo";
    private static final String REQUEST = "request";
    private static final String RESPONSE = "response";
    private static final String HEADERS = "headers";
    private static final String BODY = "body";
    private static final String BODY_FILE_NAME = "bodyFileName";
    private static final String BODY_PATTERNS = "bodyPatterns";
    private static final String CONTENT_TYPE = "Content-Type";
    private final Aspsp aspsp;
    private final ObjectMapper objectMapper;

    public WiremockStubDifferenceDetectingInterceptor(Aspsp aspsp) {
        this.aspsp = aspsp;
        objectMapper = new ObjectMapper();
    }

    @Override
    public Request.Builder apply(Request.Builder builder) {
        return builder;
    }

    @Override
    public <T> Response<T> postHandle(Request.Builder builder, Response<T> response) {
        try {
            WiremockFileResolver fileResolver = WiremockFileResolver.resolve(URI.create(builder.uri()).getPath(), builder.method(), builder.body());
            String fileName = buildStubFilePath(aspsp.getName(), fileResolver.getFileName());
            Map<String, Object> jsonFile = readStubFile(fileName);
            List<String> changes = new ArrayList<>();
            getStubRequestHeaders(jsonFile)
                .flatMap(headers -> analyzeRequestHeaders(builder, headers))
                .ifPresent(changes::add);
            getRequestBody(jsonFile)
                .flatMap(body -> analyzeRequestBody(builder, body))
                .ifPresent(changes::add);
            getResponseBody(jsonFile, aspsp.getName())
                .flatMap(body -> analyzeResponseBody(response, body))
                .ifPresent(changes::add);

            if (!changes.isEmpty()) {
                String headerValue = aspsp.getName() + ":" + fileResolver.name() + ":" + String.join(",", changes);
                Map<String, String> headersMap = response.getHeaders().getHeadersMap();
                headersMap.put(ResponseHeaders.X_ASPSP_CHANGES_DETECTED, headerValue);
                return new Response<>(response.getStatusCode(), response.getBody(), ResponseHeaders.fromMap(headersMap));
            }
            return response;

        } catch (IllegalStateException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error("Can't find the difference with wiremock stub", e);
        }

        return response;
    }

    private <T> Optional<String> analyzeResponseBody(Response<T> response, String responseBody) {
        try {
            String body = getResponseBody(response);
            return analyzePayloadStructure(responseBody, body, PayloadType.RESPONSE);
        } catch (JsonProcessingException e) {
            log.error("Can't retrieve response body", e);
        }
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    private Optional<String> analyzePayloadStructure(String payloadBody, String body, PayloadType type) {
        String payloadType = type.name().toLowerCase();
        try {
            Map<String, Object> stubBody = objectMapper.readValue(payloadBody, Map.class);
            Map<String, Object> currentBody = objectMapper.readValue(body, Map.class);
            Map<String, Object> stubMap = FlatMapUtils.flatten(stubBody);
            Map<String, Object> currentBodyMap = FlatMapUtils.flatten(currentBody);
            if (!currentBodyMap.keySet().containsAll(stubMap.keySet())) {
                log.warn("{} stub {} body is different from the aspsp {}", aspsp.getName(), payloadType, payloadType);
                return Optional.of(payloadType + "-payload");
            }
        } catch (JsonProcessingException e) {
            log.error("Can't get differences for the {} or wiremock stub body", payloadType, e);
        }
        return Optional.empty();
    }

    private enum PayloadType {
        REQUEST, RESPONSE;
    }

    private <T> String getResponseBody(Response<T> response) throws JsonProcessingException {
        String body;
        T bodyObject = response.getBody();
        if (bodyObject instanceof String) {
            body = (String) bodyObject;
        } else {
            body = objectMapper.writeValueAsString(bodyObject);
        }
        return body;
    }

    @SuppressWarnings("unchecked")
    private Optional<String> getResponseBody(Map<String, Object> jsonFile, String adapterName) {
        Map<String, Object> response = (Map<String, Object>) jsonFile.get(RESPONSE);
        if (response.containsKey(BODY)) {
            return Optional.ofNullable((String) response.get(BODY));
        }
        if (response.containsKey(BODY_FILE_NAME)) {
            try {
                String fileName = (String) response.get(BODY_FILE_NAME);
                String filePath = adapterName + "/__files/" + fileName;
                InputStream is = getClass().getResourceAsStream("/" + filePath);
                return Optional.ofNullable(IOUtils.toString(is));
            } catch (IOException e) {
                log.error("Can't get stub response file", e);
            }
        }
        return Optional.empty();
    }

    private Optional<String> analyzeRequestBody(Request.Builder builder, String requestBody) {
        return analyzePayloadStructure(requestBody, builder.body(), PayloadType.REQUEST);
    }

    private Optional<String> analyzeRequestHeaders(Request.Builder builder, Map<String, Object> requestHeaders) {
        if (requestHeaders != null && !builder.headers().keySet().containsAll(requestHeaders.keySet())) {
            log.warn("{} stub headers are different from the request", aspsp.getName());
            return Optional.of("request-headers");
        }
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    private Optional<String> getRequestBody(Map<String, Object> jsonFile) {
        Map<String, Object> request = (Map<String, Object>) jsonFile.get(REQUEST);
        Optional<Map<String, Object>> headers = getStubRequestHeaders(jsonFile);
        if (!request.containsKey(BODY_PATTERNS) || !headers.isPresent() || !isJsonRequestBody(headers.get())) {
            return Optional.empty();
        }
        List<Map<String, Object>> bodyMap = (List<Map<String, Object>>) (request).get(BODY_PATTERNS);
        return Optional.ofNullable((String) bodyMap.get(0).get("equalToJson"));
    }

    @SuppressWarnings("unchecked")
    private Optional<Map<String, Object>> getStubRequestHeaders(Map<String, Object> jsonFile) {
        Map<String, Object> request = (Map<String, Object>) jsonFile.get(REQUEST);
        if (!request.containsKey(HEADERS)) {
            return Optional.empty();
        }
        Map<String, Object> headers = (Map<String, Object>) request.get(HEADERS);
        //todo: parse equalsTo and matches
        return Optional.ofNullable(headers);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> readStubFile(String fileName) throws IOException {
        InputStream is = getClass().getResourceAsStream("/" + fileName);
        return objectMapper.readValue(is, Map.class);
    }

    private String buildStubFilePath(String adapterName, String fileName) {
        return adapterName + "/mappings/" + fileName;
    }

    @SuppressWarnings("unchecked")
    private boolean isJsonRequestBody(Map<String, Object> headers) {
        Map<String, String> contentType = (Map<String, String>) headers.getOrDefault(CONTENT_TYPE, Collections.emptyMap());
        return contentType.getOrDefault(EQUAL_TO, "").startsWith("application/json");
    }
}
