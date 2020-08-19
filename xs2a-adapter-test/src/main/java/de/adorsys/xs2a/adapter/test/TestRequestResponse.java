package de.adorsys.xs2a.adapter.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.impl.http.JacksonObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TestRequestResponse {
    private static final ObjectMapper objectMapper = new JacksonObjectMapper().copyObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    protected static final String REQUEST = "request";
    protected static final String RESPONSE = "response";
    protected static final String BODY = "body";
    protected static final String HEADERS = "headers";
    protected static final String PARAMS = "params";
    private final JsonNode jsonNode;

    public TestRequestResponse(String relativePath) throws IOException {
        jsonNode = objectMapper.readTree(new File("src/test/resources/" + relativePath));
    }

    public RequestHeaders requestHeaders() {
        return RequestHeaders.fromMap(objectMapper.convertValue(jsonNode.path(REQUEST).path(HEADERS).require(),
            new TypeReference<Map<String, String>>() {
            }));
    }

    public RequestParams requestParams() {
        return RequestParams.fromMap(objectMapper.convertValue(jsonNode.path(REQUEST).path(PARAMS).require(),
            new TypeReference<Map<String, String>>() {
            }));
    }

    public <T> T requestBody(Class<T> klass) {
        return objectMapper.convertValue(jsonNode.path(REQUEST).path(BODY).require(), klass);
    }

    public <T> T responseBody(Class<T> klass) {
        return objectMapper.convertValue(jsonNode.path(RESPONSE).path(BODY).require(), klass);
    }

    public String responseBody() {
        return jsonNode.path(RESPONSE).path(BODY).require().asText();
    }
}
