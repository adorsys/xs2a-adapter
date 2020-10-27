package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.exception.ErrorResponseException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

class CrealogixResponseHandlersTest {

    @ParameterizedTest
    @ValueSource(ints = {401, 403})
    void crealogixResponseHandler(int status) {
        assertThatThrownBy(() ->
            CrealogixResponseHandlers.crealogixResponseHandler(Object.class)
            .apply(status, null, null))
                .isInstanceOf(ErrorResponseException.class)
                .hasMessage(CrealogixResponseHandlers.PRE_AUTH_ERROR_MESSAGE)
                .matches(er -> ((ErrorResponseException) er)
                    .getErrorResponse()
                    .orElseGet(() -> fail("There should be ErrorResponse"))
                    .getLinks()
                    .get("embeddedPreAuth")
                    .getHref()
                    .equals("/v1/embedded-pre-auth/token"));
    }
}
