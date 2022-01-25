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

package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.api.exception.RequestAuthorizationValidationException;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.http.ResponseHandlers;
import de.adorsys.xs2a.adapter.api.exception.AccessTokenException;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;

public class CrealogixRequestResponseHandlers {

    public static final String REQUEST_ERROR_MESSAGE = "authorization header is missing, embedded pre-authorization may be needed";
    public static final String RESPONSE_ERROR_MESSAGE = "embedded pre-authorisation needed";
    public static final String INVALID_CREDENTIALS_TYPE_MESSAGE = "Authorization header credentials type is invalid. 'Bearer' type is expected";
    private static final String BEARER = "Bearer ";

    private final ResponseHandlers responseHandlers;

    public CrealogixRequestResponseHandlers(HttpLogSanitizer logSanitizer) {
        this.responseHandlers = new ResponseHandlers(logSanitizer);
    }

    public RequestHeaders crealogixRequestHandler(RequestHeaders requestHeaders) {
        return Stream.of(requestHeaders)
            .map(RequestHeaders::toMap)
            .map(this::checkAuthorizationHeader)
            .map(this::validateCredentialsType)
            .map(this::mapToPsd2Authorisation)
            .map(RequestHeaders::fromMap)
            .findFirst() // always should be one object, if empty
            .orElse(requestHeaders); // return original headers, which shouldn't be the case
    }

    private Map<String, String> mapToPsd2Authorisation(Map<String, String> requestHeaders) {
        String token = requestHeaders.remove(RequestHeaders.AUTHORIZATION);
        requestHeaders.put(RequestHeaders.PSD2_AUTHORIZATION, token);

        return requestHeaders;
    }

    private Map<String, String> validateCredentialsType(Map<String, String> requestHeaders) {
        String token = requestHeaders.get(RequestHeaders.AUTHORIZATION);

        if (!token.startsWith(BEARER)) {
            throw new AccessTokenException(INVALID_CREDENTIALS_TYPE_MESSAGE);
        }

        return requestHeaders;
    }

    private Map<String, String> checkAuthorizationHeader(Map<String, String> requestHeaders) {
        if (!requestHeaders.containsKey(RequestHeaders.AUTHORIZATION)) {
            throw new RequestAuthorizationValidationException(
                getErrorResponse(REQUEST_ERROR_MESSAGE),
                REQUEST_ERROR_MESSAGE);
        }
        return requestHeaders;
    }

    public <T> HttpClient.ResponseHandler<T> crealogixResponseHandler(Class<T> tClass) {
        return (statusCode, responseBody, responseHeaders) -> {
            if (statusCode == 401 || statusCode == 403) {
                throw new ErrorResponseException(
                    401,
                    ResponseHeaders.emptyResponseHeaders(),
                    getErrorResponse(),
                    RESPONSE_ERROR_MESSAGE);
            }

            return responseHandlers.jsonResponseHandler(tClass).apply(statusCode, responseBody, responseHeaders);
        };
    }

    public <T> HttpClient.ResponseHandler<T> jsonResponseHandler(Class<T> tClass) {
        return responseHandlers.jsonResponseHandler(tClass);
    }

    private static ErrorResponse getErrorResponse(String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setLinks(singletonMap("embeddedPreAuth", getHrefType()));
        errorResponse.setTppMessages(singletonList(getTppMessage(message)));
        return errorResponse;
    }

    private static ErrorResponse getErrorResponse() {
        return getErrorResponse(RESPONSE_ERROR_MESSAGE);
    }

    private static HrefType getHrefType() {
        HrefType hrefType = new HrefType();
        hrefType.setHref("/v1/embedded-pre-auth/token");
        return hrefType;
    }

    private static TppMessage getTppMessage(String message) {
        TppMessage tppMessage = new TppMessage();
        tppMessage.setCategory(TppMessageCategory.ERROR);
        tppMessage.setCode(MessageCode.TOKEN_UNKNOWN.toString());
        tppMessage.setText(message);
        return tppMessage;
    }
}
