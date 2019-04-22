package de.adorsys.xs2a.gateway.service.exception;

import de.adorsys.xs2a.gateway.service.ErrorResponse;

import java.util.Map;
import java.util.Optional;

// Following error schemas don't have the body:
// Error406_NG_PIS
// Error408_NG_PIS
// Error415_NG_PIS
// Error429_NG_PIS
// Error500_NG_PIS
// Error503_NG_PIS
public class ErrorResponseException extends RuntimeException{
    private final int statusCode;
    private final ErrorResponse errorResponse;
    private final Map<String, String> responseHeaders;

    public ErrorResponseException(int statusCode, Map<String, String> responseHeaders, ErrorResponse errorResponse) {
        this.statusCode = statusCode;
        this.responseHeaders = responseHeaders;
        this.errorResponse = errorResponse;
    }

    public ErrorResponseException(int statusCode, Map<String, String> responseHeaders) {
        this(statusCode, responseHeaders, null);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Optional<ErrorResponse> getErrorResponse() {
        return Optional.ofNullable(errorResponse);
    }

    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }
}
