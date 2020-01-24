package de.adorsys.xs2a.adapter.service.exception;

public class PasswordEncodingException extends RuntimeException {

    public PasswordEncodingException(String message) {
        super(message);
    }

    public PasswordEncodingException(String message, Throwable cause) {
        super(message, cause);
    }
}
