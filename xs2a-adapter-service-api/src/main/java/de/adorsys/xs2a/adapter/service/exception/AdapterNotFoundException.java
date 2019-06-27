package de.adorsys.xs2a.adapter.service.exception;

public class AdapterNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Adapter with id [%s] is not found";

    public AdapterNotFoundException(String adpaterId) {
        super(String.format(MESSAGE, adpaterId));
    }
}
