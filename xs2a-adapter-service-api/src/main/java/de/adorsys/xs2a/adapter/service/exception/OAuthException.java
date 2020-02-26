package de.adorsys.xs2a.adapter.service.exception;

import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.model.ErrorResponse;

public class OAuthException extends RuntimeException {
    private final transient ResponseHeaders responseHeaders;
    private final transient ErrorResponse errorResponse;

    public OAuthException(ResponseHeaders responseHeaders, ErrorResponse errorResponse, String response) {
        super(response);
        this.responseHeaders = responseHeaders;
        this.errorResponse = errorResponse;
    }

    public ResponseHeaders getResponseHeaders() {
        return responseHeaders;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
