package de.adorsys.xs2a.adapter.service.exception;

public class AdapterMappingNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Adapter mapping with id [%s] is not found";

    public AdapterMappingNotFoundException(String aspspId) {
        super(String.format(MESSAGE, aspspId));
    }
}
