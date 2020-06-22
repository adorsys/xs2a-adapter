package de.adorsys.xs2a.adapter.service.exception;

import de.adorsys.xs2a.adapter.api.model.ErrorResponse;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;

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
