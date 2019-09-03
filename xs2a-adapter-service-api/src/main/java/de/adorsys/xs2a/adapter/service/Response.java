package de.adorsys.xs2a.adapter.service;

public class Response<T> {
    private final int statusCode;
    private final T responseBody;
    private final ResponseHeaders headers;

    public Response(int statusCode, T responseBody, ResponseHeaders headers) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.headers = headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public T getResponseBody() {
        return responseBody;
    }

    public ResponseHeaders getHeaders() {
        return headers;
    }
}
