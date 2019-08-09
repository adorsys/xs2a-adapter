package de.adorsys.xs2a.adapter.registry.exception;

import de.adorsys.xs2a.adapter.service.exception.AspspRegistrationException;

import java.io.IOException;
import java.util.Objects;

public class RegistryIOException extends AspspRegistrationException {

    public RegistryIOException(IOException cause) {
        super(Objects.requireNonNull(cause));
    }
}
