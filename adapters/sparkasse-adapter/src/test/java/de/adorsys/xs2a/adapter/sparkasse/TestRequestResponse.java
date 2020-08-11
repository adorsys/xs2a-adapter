package de.adorsys.xs2a.adapter.sparkasse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.http.JacksonObjectMapper;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TestRequestResponse {
    private static final ObjectMapper objectMapper = new JacksonObjectMapper().copyObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    private final JsonNode jsonNode;

    public TestRequestResponse(String relativePath) throws IOException {
        jsonNode = objectMapper.readTree(new File("src/test/resources/" + relativePath));
    }

    RequestHeaders requestHeaders() {
        return RequestHeaders.fromMap(objectMapper.convertValue(jsonNode.path("request").path("headers").require(),
            new TypeReference<Map<String, String>>() {
            }));
    }

    RequestParams requestParams() {
        return RequestParams.fromMap(objectMapper.convertValue(jsonNode.path("request").path("params").require(),
            new TypeReference<Map<String, String>>() {
            }));
    }

    <T> T requestBody(Class<T> klass) {
        return objectMapper.convertValue(jsonNode.path("request").path("body").require(), klass);
    }

    <T> T responseBody(Class<T> klass) {
        return objectMapper.convertValue(jsonNode.path("response").path("body").require(), klass);
    }

    String responseBody() {
        return jsonNode.path("response").path("body").require().asText();
    }
}
