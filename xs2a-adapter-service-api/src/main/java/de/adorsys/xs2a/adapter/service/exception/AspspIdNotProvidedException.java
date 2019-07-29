package de.adorsys.xs2a.adapter.service.exception;

public class AspspIdNotProvidedException extends RuntimeException {
    private static final String MESSAGE = "ASPSP ID is not provided within the request";

    public AspspIdNotProvidedException() {
        super(MESSAGE);
    }
}
