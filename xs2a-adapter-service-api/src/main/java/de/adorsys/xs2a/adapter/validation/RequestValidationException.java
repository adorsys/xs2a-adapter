package de.adorsys.xs2a.adapter.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RequestValidationException extends RuntimeException {
    private final List<ValidationError> validationErrors;

    public RequestValidationException(List<ValidationError> validationErrors) {
        super(validationErrors.toString());
        this.validationErrors = Collections.unmodifiableList(new ArrayList<>(validationErrors));
    }

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }

    @Override
    public String toString() {
        return validationErrors.toString();
    }
}
