package de.adorsys.xs2a.gateway.service;

public class GeneralResponse<T> {
    private final int statusCode;
    private final T responseBody;
    private final ResponseHeaders responseHeaders;

    public GeneralResponse(int statusCode, T responseBody, ResponseHeaders responseHeaders) {
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

    public ResponseHeaders getResponseHeaders() {
        return responseHeaders;
    }
}
