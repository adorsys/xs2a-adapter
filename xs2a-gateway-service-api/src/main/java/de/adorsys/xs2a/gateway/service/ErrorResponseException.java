package de.adorsys.xs2a.gateway.service;

public class ErrorResponseException extends RuntimeException{
    private final int statusCode;
    private final ErrorResponse errorResponse;

    public ErrorResponseException(int statusCode, ErrorResponse errorResponse) {
        this.statusCode = statusCode;
        this.errorResponse = errorResponse;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
