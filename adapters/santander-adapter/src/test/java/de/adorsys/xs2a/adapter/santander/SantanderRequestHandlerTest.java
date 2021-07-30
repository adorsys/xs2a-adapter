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
