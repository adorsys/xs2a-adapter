package de.adorsys.xs2a.adapter.http;

import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.service.exception.OAuthException;
import de.adorsys.xs2a.adapter.service.model.Amount;
import de.adorsys.xs2a.adapter.service.model.ConsentCreationResponse;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class ResponseHandlersTest {
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String SCA_OAUTH_URL = "https://example.com";

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
    public void jsonResponseHandlerThrowsErrorOnUnsupportedFormatBody() {
        try {
            ResponseHandlers.jsonResponseHandler(Amount.class).apply(400,
                new ByteArrayInputStream("<response>" .getBytes()),
                ResponseHeaders.emptyResponseHeaders());
            fail();
        } catch (ErrorResponseException e) {
            assertThat(e.getMessage()).isEqualTo("<response>");
        }
    }

    @Test
    public void jsonResponseHandlerThrowsErrorOnUnsupportedContentType() {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE_HEADER, "application/xml");

        try {
            ResponseHandlers.jsonResponseHandler(Amount.class).apply(400,
                new ByteArrayInputStream("<response>" .getBytes()),
                ResponseHeaders.fromMap(headers));
            fail();
        } catch (ErrorResponseException e) {
            assertThat(e.getMessage()).isEqualTo("<response>");
        }
    }

    @Test
    public void jsonResponseHandlerThrowsErrorInJsonFormat() {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE_HEADER, "application/json");

        try {
            ResponseHandlers.jsonResponseHandler(Amount.class).apply(400,
                new ByteArrayInputStream("{}" .getBytes()),
                ResponseHeaders.fromMap(headers));
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

    @Test
    public void byteArrayResponseHandlerThrowsOnErrorResponse() {
        try {
            ResponseHandlers.byteArrayResponseHandler().apply(400,
                new ByteArrayInputStream("{}" .getBytes()),
                ResponseHeaders.emptyResponseHeaders());
            fail();
        } catch (ErrorResponseException e) {
            assertThat(e.getMessage()).isEqualTo("{}");
        }
    }


    @Test
    public void byteArrayResponseHandlerReturnsResponseBodyAsByteArrayOnSuccessfulResponse() {
        byte[] response = ResponseHandlers.byteArrayResponseHandler().apply(200,
            new ByteArrayInputStream("<response>" .getBytes()),
            ResponseHeaders.emptyResponseHeaders());

        assertThat(response).isEqualTo("<response>".getBytes());
    }

    @Test
    public void consentCreationResponseHandler403JsonResponse() {
        try {
            ResponseHandlers.consentCreationResponseHandler(SCA_OAUTH_URL, ConsentCreationResponse.class)
                .apply(403,
                    new ByteArrayInputStream("{}".getBytes()),
                    ResponseHeaders.emptyResponseHeaders());
            fail();
        } catch (OAuthException e) {
            assertThat(e.getMessage()).isEqualTo("{}");
            assertThat(e.getErrorResponse().getLinks().getScaOAuth().getHref()).isEqualTo(SCA_OAUTH_URL);
        }
    }

    @Test
    public void consentCreationResponseHandler403UnsupportedFormatBody() {
        try {
            ResponseHandlers.consentCreationResponseHandler(SCA_OAUTH_URL, ConsentCreationResponse.class)
                .apply(403,
                    new ByteArrayInputStream("<response>".getBytes()),
                    ResponseHeaders.emptyResponseHeaders());
            fail();
        } catch (OAuthException e) {
            assertThat(e.getMessage()).isEqualTo("<response>");
            assertThat(e.getErrorResponse().getLinks().getScaOAuth().getHref()).isEqualTo(SCA_OAUTH_URL);
        }
    }

    @Test
    public void consentCreationResponseHandler403UnsupportedContentType() {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE_HEADER, "application/xml");

        try {
            ResponseHandlers.consentCreationResponseHandler(SCA_OAUTH_URL, ConsentCreationResponse.class)
                .apply(403,
                    new ByteArrayInputStream("<response>".getBytes()),
                    ResponseHeaders.fromMap(headers));
            fail();
        } catch (OAuthException e) {
            assertThat(e.getMessage()).isEqualTo("<response>");
            assertThat(e.getErrorResponse().getLinks().getScaOAuth().getHref()).isEqualTo(SCA_OAUTH_URL);
        }
    }

    @Test
    public void consentCreationResponseHandler403InJsonFormat() {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE_HEADER, "application/json");

        try {
            ResponseHandlers.consentCreationResponseHandler(SCA_OAUTH_URL, ConsentCreationResponse.class)
                .apply(403,
                    new ByteArrayInputStream("{}".getBytes()),
                    ResponseHeaders.fromMap(headers));
            fail();
        } catch (OAuthException e) {
            assertThat(e.getMessage()).isEqualTo("{}");
            assertThat(e.getErrorResponse().getLinks().getScaOAuth().getHref()).isEqualTo(SCA_OAUTH_URL);
        }
    }

    @Test
    public void consentCreationResponseHandlerParsesOnSuccessfulResponse() {
        Amount amount = ResponseHandlers.jsonResponseHandler(Amount.class).apply(200,
            new ByteArrayInputStream("{\"amount\":\"10\", \"currency\":\"EUR\"}" .getBytes()),
            ResponseHeaders.emptyResponseHeaders());

        assertThat(amount.getAmount()).isEqualTo("10");
        assertThat(amount.getCurrency()).isEqualTo("EUR");
    }

    @Test
    public void consentCreationResponseHandlerThrowsOnErrorResponseAndExceptionMessageContainsResponseBody() {
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
    public void consentCreationResponseHandlerThrowsErrorOnUnsupportedFormatBody() {
        try {
            ResponseHandlers.jsonResponseHandler(Amount.class).apply(400,
                new ByteArrayInputStream("<response>" .getBytes()),
                ResponseHeaders.emptyResponseHeaders());
            fail();
        } catch (ErrorResponseException e) {
            assertThat(e.getMessage()).isEqualTo("<response>");
        }
    }

    @Test
    public void consentCreationResponseHandlerThrowsErrorOnUnsupportedContentType() {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE_HEADER, "application/xml");

        try {
            ResponseHandlers.jsonResponseHandler(Amount.class).apply(400,
                new ByteArrayInputStream("<response>" .getBytes()),
                ResponseHeaders.fromMap(headers));
            fail();
        } catch (ErrorResponseException e) {
            assertThat(e.getMessage()).isEqualTo("<response>");
        }
    }

    @Test
    public void consentCreationResponseHandlerThrowsErrorInJsonFormat() {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE_HEADER, "application/json");

        try {
            ResponseHandlers.jsonResponseHandler(Amount.class).apply(400,
                new ByteArrayInputStream("{}" .getBytes()),
                ResponseHeaders.fromMap(headers));
            fail();
        } catch (ErrorResponseException e) {
            assertThat(e.getMessage()).isEqualTo("{}");
        }
    }
}
