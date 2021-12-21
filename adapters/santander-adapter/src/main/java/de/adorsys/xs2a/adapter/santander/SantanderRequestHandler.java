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
