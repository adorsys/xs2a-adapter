package de.adorsys.xs2a.adapter.registry.exception;

import java.io.IOException;
import java.util.Objects;

public class RegistryIOException extends RuntimeException {

    public RegistryIOException(IOException cause) {
        super(Objects.requireNonNull(cause));
    }

    public RegistryIOException(String s) {
        super(s);
    }
}
