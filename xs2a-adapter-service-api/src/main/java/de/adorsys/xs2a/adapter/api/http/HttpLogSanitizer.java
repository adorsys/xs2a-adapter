package de.adorsys.xs2a.adapter.api.http;

/**
 * Service that masks all sensitive data that may appear in logs, e.g. IBAN, PSU-ID, ConsentId, Location, etc.
 */
public interface HttpLogSanitizer {

    String sanitize(String data);
    String sanitizeHeader(String name, String value);
    String sanitizeRequestBody(Object httpEntity, String contentType);
    String sanitizeResponseBody(Object responseBody, String contentType);
}
