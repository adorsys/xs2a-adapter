/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.api.validation;

import java.io.Serializable;
import java.util.Objects;

public class ValidationError implements Serializable {
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
