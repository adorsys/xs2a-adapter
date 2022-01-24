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

import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.model.ErrorResponse;

public class OAuthException extends RuntimeException {
    private final transient ResponseHeaders responseHeaders;
    private final transient ErrorResponse errorResponse;

    public OAuthException(ResponseHeaders responseHeaders, ErrorResponse errorResponse, String response) {
        super(response);
        this.responseHeaders = responseHeaders;
        this.errorResponse = errorResponse;
    }

    public ResponseHeaders getResponseHeaders() {
        return responseHeaders;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
