package de.adorsys.xs2a.adapter.api.exception;

import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.model.ErrorResponse;

public class PreAuthorisationException extends RuntimeException {
    private final transient ResponseHeaders responseHeaders;
    private final transient ErrorResponse errorResponse;

    public PreAuthorisationException(ResponseHeaders responseHeaders, ErrorResponse errorResponse, String message) {
        super(message);
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
