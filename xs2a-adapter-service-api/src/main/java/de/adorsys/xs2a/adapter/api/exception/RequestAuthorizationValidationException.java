package de.adorsys.xs2a.adapter.api.exception;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.model.ErrorResponse;
import de.adorsys.xs2a.adapter.api.validation.RequestValidationException;
import de.adorsys.xs2a.adapter.api.validation.ValidationError;

import java.util.Collections;

public class RequestAuthorizationValidationException extends RequestValidationException {
    private final transient ErrorResponse errorResponse;

    public RequestAuthorizationValidationException(ErrorResponse errorResponse, String message) {
        super(Collections.singletonList(new ValidationError(ValidationError.Code.REQUIRED, RequestHeaders.AUTHORIZATION, message)));
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
