package de.adorsys.xs2a.adapter.validation;

import java.util.Objects;

public class ValidationError {
    private final Code code;
    private final String path;
    private final String message;

    public enum Code {
        NOT_SUPPORTED,
        REQUIRED
    }

    public ValidationError(Code code, String path, String message) {
        this.code = Objects.requireNonNull(code);
        this.path = Objects.requireNonNull(path);
        this.message = Objects.requireNonNull(message);
    }

    public Code getCode() {
        return code;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ValidationError{" +
            "code=" + code +
            ", path='" + path + '\'' +
            ", message='" + message + '\'' +
            '}';
    }
}
