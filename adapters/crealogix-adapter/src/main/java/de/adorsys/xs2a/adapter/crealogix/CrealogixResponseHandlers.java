package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.model.ErrorResponse;
import de.adorsys.xs2a.adapter.api.model.HrefType;
import de.adorsys.xs2a.adapter.api.model.TppMessage;
import de.adorsys.xs2a.adapter.api.model.TppMessageCategory;
import de.adorsys.xs2a.adapter.impl.http.ResponseHandlers;

import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;

public class CrealogixResponseHandlers {

    private static final String PRE_AUTH_ERROR_MESSAGE = "embedded pre-authorisation needed";
    public static final ErrorResponse CREALOGIX_ERROR_RESPONSE_INSTANCE = getErrorResponse();

    public static <T> HttpClient.ResponseHandler<T> crealogixResponseHandler(Class<T> tClass) {
        return (statusCode, responseBody, responseHeaders) -> {
            if (statusCode == 401 || statusCode == 403) {
                throw new ErrorResponseException(401,
                    ResponseHeaders.emptyResponseHeaders(),
                    CREALOGIX_ERROR_RESPONSE_INSTANCE,
                    PRE_AUTH_ERROR_MESSAGE);
            }

            return ResponseHandlers.jsonResponseHandler(tClass).apply(statusCode, responseBody, responseHeaders);
        };
    }

    private static ErrorResponse getErrorResponse() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setLinks(singletonMap("embeddedPreAuth", getHrefType()));
        errorResponse.setTppMessages(singletonList(getTppMessage()));
        return errorResponse;
    }

    private static HrefType getHrefType() {
        HrefType hrefType = new HrefType();
        hrefType.setHref("/v1/embedded-pre-auth/token");
        return hrefType;
    }

    private static TppMessage getTppMessage() {
        TppMessage tppMessage = new TppMessage();
        tppMessage.setCategory(TppMessageCategory.ERROR);
        tppMessage.setText(PRE_AUTH_ERROR_MESSAGE);
        return tppMessage;
    }
}
