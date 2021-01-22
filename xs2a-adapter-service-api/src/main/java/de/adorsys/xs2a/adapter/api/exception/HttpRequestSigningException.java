package de.adorsys.xs2a.adapter.api.exception;

public class HttpRequestSigningException extends RuntimeException {

    public HttpRequestSigningException(String message) {
        super(message);
    }

    public HttpRequestSigningException(Throwable cause) {
        super(cause);
    }
}
