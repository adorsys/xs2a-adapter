package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.api.exception.PreAuthorisationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

class CrealogixResponseHandlersTest {

    private static final CrealogixRequestResponseHandlers requestResponseHandlers = new CrealogixRequestResponseHandlers(null);

    @ParameterizedTest
    @ValueSource(ints = {401, 403})
    void crealogixResponseHandler(int status) {
        assertThatThrownBy(() ->
            requestResponseHandlers.crealogixResponseHandler(Object.class)
            .apply(status, null, null))
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
    void crealogixRequestHandler() {
        assertThatThrownBy(() ->
            requestResponseHandlers.crealogixRequestHandler(RequestHeaders.empty()))
                .isInstanceOf(PreAuthorisationException.class)
                .hasMessage(CrealogixRequestResponseHandlers.REQUEST_ERROR_MESSAGE)
                .matches(er -> ((PreAuthorisationException) er)
                        .getErrorResponse()
                        .getLinks()
                        .get("embeddedPreAuth")
                        .getHref()
                        .equals("/v1/embedded-pre-auth/token"));
    }
}
