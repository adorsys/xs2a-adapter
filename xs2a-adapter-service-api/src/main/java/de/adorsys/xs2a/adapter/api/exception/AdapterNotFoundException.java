package de.adorsys.xs2a.adapter.api.exception;

public class AdapterNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Adapter with id [%s] is not found";

    public AdapterNotFoundException(String adapterId) {
        super(String.format(MESSAGE, adapterId));
    }
}
