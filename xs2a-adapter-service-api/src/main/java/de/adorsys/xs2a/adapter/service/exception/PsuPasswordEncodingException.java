package de.adorsys.xs2a.adapter.service.exception;

public class PsuPasswordEncodingException extends RuntimeException {

    public PsuPasswordEncodingException(String message) {
        super(message);
    }

    public PsuPasswordEncodingException(String message, Throwable cause) {
        super(message, cause);
    }
}
