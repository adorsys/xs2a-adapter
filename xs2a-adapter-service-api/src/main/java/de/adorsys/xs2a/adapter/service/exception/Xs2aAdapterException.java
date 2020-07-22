package de.adorsys.xs2a.adapter.service.exception;

public class Xs2aAdapterException extends RuntimeException {
    public Xs2aAdapterException(Throwable cause) {
        super(cause);
    }

    public Xs2aAdapterException(String message, Throwable cause) {
        super(message, cause);
    }
}
