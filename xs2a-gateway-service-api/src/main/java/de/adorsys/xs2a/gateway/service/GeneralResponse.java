package de.adorsys.xs2a.gateway.service;

import java.util.Map;

public class GeneralResponse<T> {
    private final int statusCode;
    private final T responseBody;
    private final Map<String, String> responseHeaders;

    public GeneralResponse(int statusCode, T responseBody, Map<String, String> responseHeaders) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.responseHeaders = responseHeaders;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public T getResponseBody() {
        return responseBody;
    }

    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }
}
