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

package de.adorsys.xs2a.adapter.api.exception;

public class AccessTokenException extends RuntimeException {
    private final Integer originalStatusCode;
    private final String originalMessage;
    private final boolean bankOriginator;

    public AccessTokenException(String message) {
        this(message, null, null, false);
    }

    public AccessTokenException(String message,
                                Integer originalStatusCode,
                                String originalMessage,
                                Boolean bankOriginator) {
        super(message);
        this.originalStatusCode = originalStatusCode;
        this.originalMessage = originalMessage;
        this.bankOriginator = bankOriginator;
    }

    public Integer getOriginalStatusCode() {
        return originalStatusCode;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }

    public boolean isBankOriginator() {
        return bankOriginator;
    }
}
