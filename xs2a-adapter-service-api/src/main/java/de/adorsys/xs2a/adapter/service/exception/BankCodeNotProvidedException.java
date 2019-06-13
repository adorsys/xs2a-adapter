package de.adorsys.xs2a.adapter.service.exception;

public class BankCodeNotProvidedException extends RuntimeException {
    private static final String MESSAGE = "Bank code is not provided within the request";

    public BankCodeNotProvidedException() {
        super(MESSAGE);
    }
}
