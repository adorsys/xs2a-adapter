package de.adorsys.xs2a.adapter.http;

import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.service.model.Amount;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class ResponseHandlersTest {

    @Test
    public void jsonResponseHandlerParsesOnSuccessfulResponse() {
        Amount amount = ResponseHandlers.jsonResponseHandler(Amount.class).apply(200,
            new ByteArrayInputStream("{\"amount\":\"10\", \"currency\":\"EUR\"}" .getBytes()),
            ResponseHeaders.emptyResponseHeaders());

        assertThat(amount.getAmount()).isEqualTo("10");
        assertThat(amount.getCurrency()).isEqualTo("EUR");
    }

    @Test
    public void jsonResponseHandlerThrowsOnErrorResponseAndExceptionMessageContainsResponseBody() {
        try {
            ResponseHandlers.jsonResponseHandler(Amount.class).apply(400,
                new ByteArrayInputStream("{}" .getBytes()), // fails if response body is not json
                ResponseHeaders.emptyResponseHeaders());
            fail();
        } catch (ErrorResponseException e) {
            assertThat(e.getMessage()).isEqualTo("{}");
        }
    }

    @Test
    public void stringResponseHandlerReturnsResponseBodyAsStringOnSuccessfulResponse() {
        String response = ResponseHandlers.stringResponseHandler().apply(200,
            new ByteArrayInputStream("<response>" .getBytes()),
            ResponseHeaders.emptyResponseHeaders());

        assertThat(response).isEqualTo("<response>");
    }

    @Test
    public void stringResponseHandlerThrowsOnErrorResponse() {
        try {
            ResponseHandlers.stringResponseHandler().apply(400,
                new ByteArrayInputStream("{}" .getBytes()),
                ResponseHeaders.emptyResponseHeaders());
            fail();
        } catch (ErrorResponseException e) {
            assertThat(e.getMessage()).isEqualTo("{}");
        }
    }
}
