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
import de.adorsys.xs2a.adapter.api.model.ErrorResponse;
import de.adorsys.xs2a.adapter.api.model.MessageCode;
import de.adorsys.xs2a.adapter.api.model.TppMessage;
import de.adorsys.xs2a.adapter.api.model.TppMessageCategory;

import java.util.Map;

import static java.util.Collections.singletonList;

public class SantanderRequestHandler {
    public static final String REQUEST_ERROR_MESSAGE = "AUTHORIZATION header is missing";

    public void validateRequest(RequestHeaders requestHeaders) {
        Map<String, String> headers = requestHeaders.toMap();
        if (!headers.containsKey(RequestHeaders.AUTHORIZATION)) {
            throw new RequestAuthorizationValidationException(
                getErrorResponse(),
                REQUEST_ERROR_MESSAGE);
        }
    }

    private static ErrorResponse getErrorResponse() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTppMessages(singletonList(getTppMessage()));
        return errorResponse;
    }

    private static TppMessage getTppMessage() {
        TppMessage tppMessage = new TppMessage();
        tppMessage.setCategory(TppMessageCategory.ERROR);
        tppMessage.setCode(MessageCode.TOKEN_UNKNOWN.toString());
        tppMessage.setText(REQUEST_ERROR_MESSAGE);
        return tppMessage;
    }
}
