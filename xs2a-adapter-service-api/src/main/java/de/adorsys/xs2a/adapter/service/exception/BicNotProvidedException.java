package de.adorsys.xs2a.adapter.service.exception;

public class BicNotProvidedException extends RuntimeException {
    private static final String MESSAGE = "BIC is not provided within the request";

    public BicNotProvidedException() {
        super(MESSAGE);
    }
}
