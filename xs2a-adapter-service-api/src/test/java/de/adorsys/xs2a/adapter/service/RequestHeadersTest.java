package de.adorsys.xs2a.adapter.service;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

class RequestHeadersTest {

    @Test
    void getIsCaseAgnostic() {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(singletonMap("X-gtw-ASPSP-id", "1"));
        Optional<String> aspspId = requestHeaders.get(RequestHeaders.X_GTW_ASPSP_ID);
        assertThat(aspspId).get().isEqualTo("1");
    }

    @Test
    void getUnknownHeaderReturnsEmpty() {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(emptyMap());
        Optional<String> headerValue = requestHeaders.get("asdfasdf");
        assertThat(headerValue).isEmpty();
    }
}
