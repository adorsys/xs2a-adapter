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

    public static ValidationError required(String path) {
        return new ValidationError(ValidationError.Code.REQUIRED, path, "Missing required parameter");
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationError that = (ValidationError) o;
        return code == that.code &&
            Objects.equals(path, that.path) &&
            Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, path, message);
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
