package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.api.exception.PreAuthorisationException;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.model.ErrorResponse;
import de.adorsys.xs2a.adapter.api.model.HrefType;
import de.adorsys.xs2a.adapter.api.model.TppMessage;
import de.adorsys.xs2a.adapter.api.model.TppMessageCategory;
import de.adorsys.xs2a.adapter.impl.http.ResponseHandlers;

import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;

public class CrealogixRequestResponseHandlers {

    public static final String REQUEST_ERROR_MESSAGE = "authorization header is missing, embedded pre-authorization may be needed";
    public static final String RESPONSE_ERROR_MESSAGE = "embedded pre-authorisation needed";
    private static final ErrorResponse CREALOGIX_ERROR_RESPONSE_INSTANCE = getErrorResponse();

    private final ResponseHandlers responseHandlers;

    public CrealogixRequestResponseHandlers(HttpLogSanitizer logSanitizer) {
        this.responseHandlers = new ResponseHandlers(logSanitizer);
    }

    public void crealogixRequestHandler(RequestHeaders requestHeaders) {
        if (!requestHeaders.get(RequestHeaders.AUTHORIZATION).isPresent()) {
            throw new PreAuthorisationException(
                    CREALOGIX_ERROR_RESPONSE_INSTANCE,
                    REQUEST_ERROR_MESSAGE);
        }
    }

    public <T> HttpClient.ResponseHandler<T> crealogixResponseHandler(Class<T> tClass) {
        return (statusCode, responseBody, responseHeaders) -> {
            if (statusCode == 401 || statusCode == 403) {
                throw new ErrorResponseException(
                    401,
                    ResponseHeaders.emptyResponseHeaders(),
                    CREALOGIX_ERROR_RESPONSE_INSTANCE,
                    RESPONSE_ERROR_MESSAGE);
            }

            return responseHandlers.jsonResponseHandler(tClass).apply(statusCode, responseBody, responseHeaders);
        };
    }

    public <T> HttpClient.ResponseHandler<T> jsonResponseHandler(Class<T> tClass) {
        return responseHandlers.jsonResponseHandler(tClass);
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
        tppMessage.setText(RESPONSE_ERROR_MESSAGE);
        return tppMessage;
    }
}
