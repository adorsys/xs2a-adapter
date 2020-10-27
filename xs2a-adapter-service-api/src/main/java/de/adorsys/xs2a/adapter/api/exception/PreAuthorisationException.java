package de.adorsys.xs2a.adapter.api.exception;

import de.adorsys.xs2a.adapter.api.model.ErrorResponse;

public class PreAuthorisationException extends RuntimeException {
    private final transient ErrorResponse errorResponse;

    public PreAuthorisationException(ErrorResponse errorResponse, String message) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
