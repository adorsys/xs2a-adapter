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

package de.adorsys.xs2a.adapter.santander;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.exception.RequestAuthorizationValidationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class SantanderRequestHandlerTest {

    private final SantanderRequestHandler requestHandler = new SantanderRequestHandler();

    @Test
    void validateRequest_throwException() {
        RequestHeaders requestHeaders = RequestHeaders.empty();

        Assertions.assertThatThrownBy(() -> requestHandler.validateRequest(requestHeaders))
            .hasMessageContaining(SantanderRequestHandler.REQUEST_ERROR_MESSAGE)
            .isExactlyInstanceOf(RequestAuthorizationValidationException.class);
    }

    @Test
    void validateRequest_noException() {
        Map<String, String> headers = Map.of(RequestHeaders.AUTHORIZATION, "Bearer foo");
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        Assertions.assertThatCode(() -> requestHandler.validateRequest(requestHeaders))
            .doesNotThrowAnyException();
    }
}
