package de.adorsys.xs2a.adapter.validation;

import java.util.List;

public class Validation {

    private Validation() {
    }

    public static void requireValid(List<ValidationError> validationErrors) {
        if (!validationErrors.isEmpty()) {
            throw new RequestValidationException(validationErrors);
        }
    }
}
