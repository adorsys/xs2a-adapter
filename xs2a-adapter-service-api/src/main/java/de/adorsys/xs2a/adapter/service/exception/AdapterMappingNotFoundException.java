package de.adorsys.xs2a.adapter.service.exception;

public class AdapterMappingNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Adapter mapping with bic [%s] is not found";

    public AdapterMappingNotFoundException(String adpaterId) {
        super(String.format(MESSAGE, adpaterId));
    }
}
