package de.adorsys.xs2a.adapter.api.http;

public interface HttpLogSanitizer {
    String sanitize(String data);
    String sanitizeHeader(String name, String value);
    String sanitizeRequestBody(Object entity, String contentType);
    String sanitizeResponseBody(Object responseBody, String contentType);
}
