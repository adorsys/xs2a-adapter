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
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class WiremockStubDifferenceDetectingInterceptor implements Interceptor {
    private static final Logger log = LoggerFactory.getLogger(WiremockStubDifferenceDetectingInterceptor.class);
    private static final String EQUAL_TO = "equalTo";
    private static final String HEADERS = "headers";
    private static final String BODY = "body";
    private static final String BODY_FILE_NAME = "bodyFileName";
    private static final String BODY_PATTERNS = "bodyPatterns";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String MAPPINGS_DIR = File.separator + "mappings" + File.separator;
    private static final String MAPPINGS_DIR_PATH = "%s" + MAPPINGS_DIR;
    private static final String APPLICATION_JSON = "application/json";
    private final Aspsp aspsp;
    private final ObjectMapper objectMapper;

    public WiremockStubDifferenceDetectingInterceptor(Aspsp aspsp) {
        this.aspsp = aspsp;
        objectMapper = new ObjectMapper();
    }

    @Override
    public Request.Builder preHandle(Request.Builder builder) {
        return builder;
    }

    @Override
    public <T> Response<T> postHandle(Request.Builder builder, Response<T> response) {
        try {
            WiremockFileType wiremockFileType = WiremockFileType.resolve(URI.create(builder.uri()).getPath(), builder.method(), builder.body());
            String fileName = buildStubFilePath(aspsp.getAdapterId(), wiremockFileType.getFileName());
            Map<String, Object> jsonFile = readStubFile(fileName);
            List<String> changes = new ArrayList<>();
            getHeaders(jsonFile, PayloadType.REQUEST)
                .flatMap(headers -> analyzeRequestHeaders(builder, headers))
                .ifPresent(changes::add);
            getRequestBody(jsonFile)
                .flatMap(body -> analyzeRequestBody(builder, body))
                .ifPresent(changes::add);
            getResponseBody(jsonFile, aspsp.getAdapterId())
                .flatMap(body -> analyzeResponseBody(response, body))
                .ifPresent(changes::add);

            Map<String, String> headersMap = response.getHeaders().getHeadersMap();

            if (!changes.isEmpty()) {
                String headerValue = String.join(",", changes);
                headerValue = aspsp.getAdapterId() + ":" + wiremockFileType.name() + ":" + headerValue;
                headersMap.put(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED, headerValue);
            }

            return new Response<>(response.getStatusCode(), response.getBody(), ResponseHeaders.fromMap(headersMap));

        } catch (IllegalStateException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error("Can't find the difference with wiremock stub", e);
        }

        return response;
    }

    private <T> Optional<String> analyzeResponseBody(Response<T> response,
                                                     String responseBody) {
        try {
            String body = getResponseBody(response);
            return analyzePayloadStructure(responseBody, body, PayloadType.RESPONSE);
        } catch (JsonProcessingException e) {
            log.error("Can't retrieve response body", e);
        }
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    private Optional<String> analyzePayloadStructure(String payloadBody, String body, PayloadType payloadType) {
        try {
            Map<String, Object> stubBody = objectMapper.readValue(payloadBody, Map.class);
            Map<String, Object> currentBody
                = isEmpty(body) ? Collections.EMPTY_MAP : objectMapper.readValue(body, Map.class);
            Map<String, Object> stubMap = FlatMapUtils.flatten(stubBody);
            Map<String, Object> currentBodyMap = FlatMapUtils.flatten(currentBody);
            if (!currentBodyMap.keySet().containsAll(stubMap.keySet())) {
                StringBuilder differences = new StringBuilder("\nDifferences:");
                for (String key : stubMap.keySet()) {
                    if (!currentBodyMap.containsKey(key)) {
                        differences.append("\n").append(key).append(" field is absent");
                    }
                }
                log.warn("{} stub {} body is different from the aspsp {}.{}", aspsp.getAdapterId(), payloadType.value, payloadType.value, differences);
                return Optional.of(payloadType.value + "-payload");
            }
        } catch (JsonProcessingException e) {
            log.error("Can't get differences for the {} or wiremock stub body", payloadType.value, e);
        }
        return Optional.empty();
    }

    private enum PayloadType {
        REQUEST("request"),
        RESPONSE("response");

        public final String value;

        PayloadType(String value) {
            this.value = value;
        }
    }

    private <T> String getResponseBody(Response<T> response) throws JsonProcessingException {
        String body = null;
        T bodyObject = response.getBody();
        if (bodyObject instanceof String) {
            body = (String) bodyObject;
        } else {
            body = objectMapper.writeValueAsString(bodyObject);
        }
        return body;
    }

    @SuppressWarnings("unchecked")
    private Optional<String> getResponseBody(Map<String, Object> jsonFile, String adapterId) {
        Map<String, Object> response = (Map<String, Object>) jsonFile.get(PayloadType.RESPONSE.value);
        Optional<Map<String, Object>> headers = getHeaders(jsonFile, PayloadType.RESPONSE);
        if ((!response.containsKey(BODY) && !response.containsKey(BODY_FILE_NAME)) || !headers.isPresent() || !isJsonBody(headers.get(), PayloadType.RESPONSE)) {
            return Optional.empty();
        }

        if (response.containsKey(BODY)) {
            return Optional.ofNullable((String) response.get(BODY));
        }
        if (response.containsKey(BODY_FILE_NAME)) {
            try {
                String fileName = (String) response.get(BODY_FILE_NAME);
                String filePath = adapterId + File.separator + "__files" + File.separator + fileName;
                InputStream is = getClass().getResourceAsStream(File.separator + filePath);
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
            log.warn("{} stub headers are different from the request", aspsp.getAdapterId());
            return Optional.of("request-headers");
        }
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    private Optional<String> getRequestBody(Map<String, Object> jsonFile) {
        Map<String, Object> request = (Map<String, Object>) jsonFile.get(PayloadType.REQUEST.value);
        Optional<Map<String, Object>> headers = getHeaders(jsonFile, PayloadType.REQUEST);
        if (!request.containsKey(BODY_PATTERNS) || !headers.isPresent() || !isJsonBody(headers.get(), PayloadType.REQUEST)) {
            return Optional.empty();
        }
        List<Map<String, Object>> bodyMap = (List<Map<String, Object>>) (request).get(BODY_PATTERNS);
        return Optional.ofNullable((String) bodyMap.get(0).get("equalToJson"));
    }

    @SuppressWarnings("unchecked")
    private Optional<Map<String, Object>> getHeaders(Map<String, Object> jsonFile, PayloadType payloadType) {
        Map<String, Object> request = (Map<String, Object>) jsonFile.get(payloadType.value);
        return Optional.ofNullable((Map<String, Object>) request.get(HEADERS));
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> readStubFile(String fileName) throws IOException {
        InputStream is = getClass().getResourceAsStream(File.separator + fileName);
        return objectMapper.readValue(is, Map.class);
    }

    private String buildStubFilePath(String adapterName, String fileName) {
        return String.format(MAPPINGS_DIR_PATH, adapterName) + fileName;
    }

    @SuppressWarnings("unchecked")
    private boolean isJsonBody(Map<String, Object> headers, PayloadType payloadType) {
        if (payloadType == PayloadType.REQUEST) {
            Map<String, String> contentType = (Map<String, String>) headers.getOrDefault(CONTENT_TYPE, Collections.emptyMap());
            return contentType.getOrDefault(EQUAL_TO, "").startsWith(APPLICATION_JSON);
        }
        return headers.getOrDefault(CONTENT_TYPE.toLowerCase(), "").toString().startsWith(APPLICATION_JSON);
    }

    public static boolean isWiremockSupported(String adapterName) {
        Path path = Paths.get(String.format(MAPPINGS_DIR_PATH, adapterName));
        return Files.exists(path);
    }
}
