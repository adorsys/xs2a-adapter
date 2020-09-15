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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class WiremockStubDifferenceDetectingInterceptor implements Request.Builder.Interceptor {
    private static final Logger log = LoggerFactory.getLogger(WiremockStubDifferenceDetectingInterceptor.class);
    private static final String EQUAL_TO = "equalTo";
    private static final String REQUEST = "request";
    private static final String HEADERS = "headers";
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
            WiremockFileResolver fileResolver = WiremockFileResolver.resolve(builder.uri(), builder.method(), builder.body());
            String fileName = buildStubFilePath(aspsp.getName(), fileResolver.getFileName());
            Map<String, Object> jsonFile = readStubFile(fileName);
            List<String> changes = new ArrayList<>();
            getStubRequestUrl(jsonFile)
                .flatMap(url -> analyzeRequestUrl(fileResolver, builder, url))
                .ifPresent(changes::add);
            getStubRequestHeaders(jsonFile)
                .flatMap(headers -> analyzeRequestHeaders(fileResolver, builder, headers))
                .ifPresent(changes::add);
            getRequestBody(jsonFile)
                .flatMap(body -> analyzeRequestBody(fileResolver, builder, body))
                .ifPresent(changes::add);

            String headerValue = String.join(",", changes);
            Map<String, String> headersMap = response.getHeaders().getHeadersMap();
            headersMap.put(ResponseHeaders.X_ASPSP_CHANGES_DETECTED, headerValue);

            return new Response<>(response.getStatusCode(), response.getBody(), ResponseHeaders.fromMap(headersMap));

        } catch (Exception e) {
            log.error("Can't find the difference with wiremock stub", e);
        }

        return response;
    }

    @SuppressWarnings("unchecked")
    private Optional<String> analyzeRequestBody(WiremockFileResolver resolver, Request.Builder builder, String requestBody) {
        try {
            Map<String, Object> stubRequestBody = objectMapper.readValue(requestBody, Map.class);
            Map<String, Object> currentRequestBody = objectMapper.readValue(builder.body(), Map.class);
            Map<String, Object> stubMap = FlatMapUtils.flatten(stubRequestBody);
            Map<String, Object> requestMap = FlatMapUtils.flatten(currentRequestBody);
            if (!requestMap.keySet().containsAll(stubMap.keySet())) {
                log.warn("{} stub request body is different from the request", aspsp.getName());
                String changes = aspsp.getName() + ":" + resolver.name() + ":request-payload";
                return Optional.of(changes);
            }
        } catch (JsonProcessingException e) {
            log.error("Can't get differences for the request or wiremock stub body", e);
        }
        return Optional.empty();
    }

    private Optional<String> analyzeRequestHeaders(WiremockFileResolver resolver, Request.Builder builder, Map<String, Object> requestHeaders) {
        if (requestHeaders != null && !builder.headers().keySet().containsAll(requestHeaders.keySet())) {
            log.warn("{} stub headers are different from the request", aspsp.getName());
            String changes = aspsp.getName() + ":" + resolver.name() + ":request-headers";
            return Optional.of(changes);
        }
        return Optional.empty();
    }

    private Optional<String> analyzeRequestUrl(WiremockFileResolver resolver, Request.Builder builder, String requestUrl) {
        String url = builder.uri().replace("://", "");
        url = url.substring(url.indexOf("/"));
        if (requestUrl.isEmpty() || !requestUrl.startsWith(url)) {
            log.warn("{} stub URL is different from the request URL", aspsp.getName());
            String changes = aspsp.getName() + ":" + resolver.name() + ":request-url";
            return Optional.of(changes);
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
    private Optional<String> getStubRequestUrl(Map<String, Object> jsonFile) {
        return Optional.ofNullable((String) ((Map<String, Object>) jsonFile.get(REQUEST)).get("url"));
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
