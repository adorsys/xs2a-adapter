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

import java.util.Optional;

// Following error schemas don't have the body:
// Error406_NG_PIS
// Error408_NG_PIS
// Error415_NG_PIS
// Error429_NG_PIS
// Error500_NG_PIS
// Error503_NG_PIS
public class ErrorResponseException extends RuntimeException {
    private final int statusCode;
    private final transient ResponseHeaders responseHeaders;
    private final transient ErrorResponse errorResponse;

    public ErrorResponseException(int statusCode,
                                  ResponseHeaders responseHeaders,
                                  ErrorResponse errorResponse,
                                  String response) {
        super(response);
        this.statusCode = statusCode;
        this.responseHeaders = responseHeaders;
        this.errorResponse = errorResponse;
    }

    public ErrorResponseException(int statusCode, ResponseHeaders responseHeaders, ErrorResponse errorResponse) {
        this(statusCode, responseHeaders, errorResponse, null);
    }

    public ErrorResponseException(int statusCode, ResponseHeaders responseHeaders) {
        this(statusCode, responseHeaders, null);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Optional<ErrorResponse> getErrorResponse() {
        return Optional.ofNullable(errorResponse);
    }

    public ResponseHeaders getResponseHeaders() {
        return responseHeaders;
    }
}
