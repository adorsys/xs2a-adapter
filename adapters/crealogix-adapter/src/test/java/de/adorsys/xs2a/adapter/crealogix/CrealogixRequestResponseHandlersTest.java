package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.api.exception.PreAuthorisationException;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.impl.security.AccessTokenException;
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
                .isInstanceOf(PreAuthorisationException.class)
                .hasMessage(CrealogixRequestResponseHandlers.REQUEST_ERROR_MESSAGE)
                .matches(er -> ((PreAuthorisationException) er)
                    .getErrorResponse()
                    .getLinks()
                    .get("embeddedPreAuth")
                    .getHref()
                    .equals("/v1/embedded-pre-auth/token"));
    }

    @Test
    void crealogixRequestHandler_throwsAccessTokenException() {
        RequestHeaders flawedHeaders = getHeadersWithAuthorization("foo");

        assertThatThrownBy(() ->
            requestResponseHandlers.crealogixRequestHandler(flawedHeaders))
                .isInstanceOf(AccessTokenException.class);
    }

    @Test
    void crealogixRequestHandler() {
        RequestHeaders actualHeaders = requestResponseHandlers.crealogixRequestHandler(getHeadersWithAuthorization());

        assertThat(actualHeaders.toMap())
            .hasSize(2)
            .contains(entry(RequestHeaders.AUTHORIZATION, "Bearer " + TPP_TOKEN),
                entry(RequestHeaders.PSD2_AUTHORIZATION, "Bearer " + PSD2_AUTHORIZATION_TOKEN));
    }
}
