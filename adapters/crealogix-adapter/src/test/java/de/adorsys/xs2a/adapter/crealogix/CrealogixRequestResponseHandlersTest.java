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
import de.adorsys.xs2a.adapter.api.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.api.exception.RequestAuthorizationValidationException;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.exception.AccessTokenException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class CrealogixRequestResponseHandlersTest extends CrealogixTestHelper {

    private static final CrealogixRequestResponseHandlers requestResponseHandlers = new CrealogixRequestResponseHandlers(null);

    @ParameterizedTest
    @ValueSource(ints = {401, 403})
    void crealogixResponseHandler(int status) {
        HttpClient.ResponseHandler<Object> responseHandler
            = requestResponseHandlers.crealogixResponseHandler(Object.class);

        assertThatThrownBy(() ->
            responseHandler.apply(status, null, null))
                .isInstanceOf(ErrorResponseException.class)
                .hasMessage(CrealogixRequestResponseHandlers.RESPONSE_ERROR_MESSAGE)
                .matches(er -> ((ErrorResponseException) er)
                    .getErrorResponse()
                    .orElseGet(() -> fail("There should be ErrorResponse"))
                    .getLinks()
                    .get("embeddedPreAuth")
                    .getHref()
                    .equals("/v1/embedded-pre-auth/token"));
    }

    @Test
    void crealogixRequestHandler_throwsPreAuthorisationException() {
        RequestHeaders emptyHeaders = RequestHeaders.empty();

        assertThatThrownBy(() ->
            requestResponseHandlers.crealogixRequestHandler(emptyHeaders))
                .isInstanceOf(RequestAuthorizationValidationException.class)
                .hasMessageContaining(CrealogixRequestResponseHandlers.REQUEST_ERROR_MESSAGE)
                .matches(er -> ((RequestAuthorizationValidationException) er)
                    .getErrorResponse()
                    .getLinks()
                    .get("embeddedPreAuth")
                    .getHref()
                    .equals("/v1/embedded-pre-auth/token"));
    }

    @Test
    void crealogixRequestHandler_throwsAccessTokenException_wrongAuthorizationType() {
        RequestHeaders flawedHeaders = getHeadersWithAuthorization("foo");

        assertThatThrownBy(() ->
            requestResponseHandlers.crealogixRequestHandler(flawedHeaders))
                .isInstanceOf(AccessTokenException.class)
                .hasMessage(CrealogixRequestResponseHandlers.INVALID_CREDENTIALS_TYPE_MESSAGE);
    }

    @Test
    void crealogixRequestHandler() {
        RequestHeaders actualHeaders = requestResponseHandlers.crealogixRequestHandler(getHeadersWithAuthorization());

        assertThat(actualHeaders.toMap())
            .hasSize(1)
            .contains(entry(RequestHeaders.PSD2_AUTHORIZATION, "Bearer " + TOKEN));
    }
}
