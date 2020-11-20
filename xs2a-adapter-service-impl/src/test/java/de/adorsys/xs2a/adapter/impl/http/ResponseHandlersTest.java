package de.adorsys.xs2a.adapter.impl.http;

import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.api.exception.OAuthException;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.model.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class ResponseHandlersTest {
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String SCA_OAUTH_URL = "https://example.com";
    private final ResponseHandlers responseHandlers = new ResponseHandlers();

    @Test
    void jsonResponseHandlerParsesOnSuccessfulResponse() {
        Amount amount = responseHandlers.jsonResponseHandler(Amount.class).apply(200,
            new ByteArrayInputStream("{\"amount\":\"10\", \"currency\":\"EUR\"}".getBytes()),
            ResponseHeaders.emptyResponseHeaders());

        assertThat(amount.getAmount()).isEqualTo("10");
        assertThat(amount.getCurrency()).isEqualTo("EUR");
    }

    @Test
    void jsonResponseHandlerThrowsOnErrorResponseAndExceptionMessageContainsResponseBody() {
        HttpClient.ResponseHandler<Amount> jsonResponseHandler = responseHandlers.jsonResponseHandler(Amount.class);
        ByteArrayInputStream responseBody = new ByteArrayInputStream("{}".getBytes());
        ResponseHeaders responseHeaders = ResponseHeaders.emptyResponseHeaders();
        try {
            jsonResponseHandler.apply(400, responseBody, responseHeaders);
            fail();
        } catch (ErrorResponseException e) {
            assertThat(e.getMessage()).isEqualTo("{}");
        }
    }

    @Test
    void jsonResponseHandlerThrowsErrorOnUnsupportedFormatBody() {
        HttpClient.ResponseHandler<Amount> jsonResponseHandler = responseHandlers.jsonResponseHandler(Amount.class);
        ByteArrayInputStream responseBody = new ByteArrayInputStream("<response>".getBytes());
        ResponseHeaders responseHeaders = ResponseHeaders.emptyResponseHeaders();
        try {
            jsonResponseHandler.apply(400, responseBody, responseHeaders);
            fail();
        } catch (ErrorResponseException e) {
            assertThat(e.getMessage()).isEqualTo("<response>");
        }
    }

    @Test
    void jsonResponseHandlerThrowsErrorOnUnsupportedContentType() {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE_HEADER, "application/xml");
        HttpClient.ResponseHandler<Amount> jsonResponseHandler = responseHandlers.jsonResponseHandler(Amount.class);
        ByteArrayInputStream responseBody = new ByteArrayInputStream("<response>".getBytes());
        ResponseHeaders responseHeaders = ResponseHeaders.fromMap(headers);

        try {
            jsonResponseHandler.apply(400, responseBody, responseHeaders);
            fail();
        } catch (ErrorResponseException e) {
            assertThat(e.getMessage()).isEqualTo("<response>");
        }
    }

    @Test
    void jsonResponseHandlerThrowsErrorInJsonFormat() {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE_HEADER, "application/json");
        HttpClient.ResponseHandler<Amount> jsonResponseHandler = responseHandlers.jsonResponseHandler(Amount.class);
        ByteArrayInputStream responseBody = new ByteArrayInputStream("{}".getBytes());
        ResponseHeaders responseHeaders = ResponseHeaders.fromMap(headers);

        try {
            jsonResponseHandler.apply(400, responseBody, responseHeaders);
            fail();
        } catch (ErrorResponseException e) {
            assertThat(e.getMessage()).isEqualTo("{}");
        }
    }

    @Test
    void stringResponseHandlerReturnsResponseBodyAsStringOnSuccessfulResponse() {
        String response = responseHandlers.stringResponseHandler().apply(200,
            new ByteArrayInputStream("<response>".getBytes()),
            ResponseHeaders.emptyResponseHeaders());

        assertThat(response).isEqualTo("<response>");
    }

    @Test
    void stringResponseHandlerThrowsOnErrorResponse() {
        HttpClient.ResponseHandler<String> stringResponseHandler = responseHandlers.stringResponseHandler();
        ByteArrayInputStream responseBody = new ByteArrayInputStream("{}".getBytes());
        ResponseHeaders responseHeaders = ResponseHeaders.emptyResponseHeaders();

        try {
            stringResponseHandler.apply(400, responseBody, responseHeaders);
            fail();
        } catch (ErrorResponseException e) {
            assertThat(e.getMessage()).isEqualTo("{}");
        }
    }

    @Test
    void byteArrayResponseHandlerThrowsOnErrorResponse() {
        HttpClient.ResponseHandler<byte[]> responseHandler = responseHandlers.byteArrayResponseHandler();
        ByteArrayInputStream responseBody = new ByteArrayInputStream("{}".getBytes());
        ResponseHeaders responseHeaders = ResponseHeaders.emptyResponseHeaders();

        try {
            responseHandler.apply(400, responseBody, responseHeaders);
            fail();
        } catch (ErrorResponseException e) {
            assertThat(e.getMessage()).isEqualTo("{}");
        }
    }


    @Test
    void byteArrayResponseHandlerReturnsResponseBodyAsByteArrayOnSuccessfulResponse() {
        byte[] response = responseHandlers.byteArrayResponseHandler().apply(200,
            new ByteArrayInputStream("<response>".getBytes()),
            ResponseHeaders.emptyResponseHeaders());

        assertThat(response).isEqualTo("<response>".getBytes());
    }

    @Test
    void consentCreationResponseHandler403JsonResponse() {
        HttpClient.ResponseHandler<ConsentsResponse201> consentCreationResponseHandler =
            responseHandlers.consentCreationResponseHandler(SCA_OAUTH_URL, ConsentsResponse201.class);
        ByteArrayInputStream responseBody = new ByteArrayInputStream("{}".getBytes());
        ResponseHeaders responseHeaders = ResponseHeaders.emptyResponseHeaders();

        try {
            consentCreationResponseHandler.apply(403, responseBody, responseHeaders);
            fail();
        } catch (OAuthException e) {
            assertThat(e.getMessage()).isEqualTo("{}");
            assertThat(e.getErrorResponse().getLinks().get("scaOAuth").getHref()).isEqualTo(SCA_OAUTH_URL);
        }
    }

    @Test
    void consentCreationResponseHandler403UnsupportedFormatBody() {
        HttpClient.ResponseHandler<ConsentsResponse201> consentCreationResponseHandler =
            responseHandlers.consentCreationResponseHandler(SCA_OAUTH_URL, ConsentsResponse201.class);
        ByteArrayInputStream responseBody = new ByteArrayInputStream("<response>".getBytes());
        ResponseHeaders responseHeaders = ResponseHeaders.emptyResponseHeaders();

        try {
            consentCreationResponseHandler.apply(403, responseBody, responseHeaders);
            fail();
        } catch (OAuthException e) {
            assertThat(e.getMessage()).isEqualTo("<response>");
            assertThat(e.getErrorResponse().getLinks().get("scaOAuth").getHref()).isEqualTo(SCA_OAUTH_URL);
        }
    }

    @Test
    void consentCreationResponseHandler403UnsupportedContentType() {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE_HEADER, "application/xml");
        HttpClient.ResponseHandler<ConsentsResponse201> consentCreationResponseHandler =
            responseHandlers.consentCreationResponseHandler(SCA_OAUTH_URL, ConsentsResponse201.class);
        ByteArrayInputStream responseBody = new ByteArrayInputStream("<response>".getBytes());
        ResponseHeaders responseHeaders = ResponseHeaders.fromMap(headers);

        try {
            consentCreationResponseHandler.apply(403, responseBody, responseHeaders);
            fail();
        } catch (OAuthException e) {
            assertThat(e.getMessage()).isEqualTo("<response>");
            assertThat(e.getErrorResponse().getLinks().get("scaOAuth").getHref()).isEqualTo(SCA_OAUTH_URL);
        }
    }

    @Test
    void consentCreationResponseHandler403InJsonFormat() {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE_HEADER, "application/json");
        HttpClient.ResponseHandler<ConsentsResponse201> consentCreationResponseHandler =
            responseHandlers.consentCreationResponseHandler(SCA_OAUTH_URL, ConsentsResponse201.class);
        ByteArrayInputStream responseBody = new ByteArrayInputStream("{}".getBytes());
        ResponseHeaders responseHeaders = ResponseHeaders.fromMap(headers);

        try {
            consentCreationResponseHandler.apply(403, responseBody, responseHeaders);
            fail();
        } catch (OAuthException e) {
            assertThat(e.getMessage()).isEqualTo("{}");
            assertThat(e.getErrorResponse().getLinks().get("scaOAuth").getHref()).isEqualTo(SCA_OAUTH_URL);
        }
    }

    @Test
    void consentCreationResponseHandlerParsesOnSuccessfulResponse() {
        Amount amount = responseHandlers.jsonResponseHandler(Amount.class).apply(200,
            new ByteArrayInputStream("{\"amount\":\"10\", \"currency\":\"EUR\"}".getBytes()),
            ResponseHeaders.emptyResponseHeaders());

        assertThat(amount.getAmount()).isEqualTo("10");
        assertThat(amount.getCurrency()).isEqualTo("EUR");
    }

    @Test
    void consentCreationResponseHandlerThrowsOnErrorResponseAndExceptionMessageContainsResponseBody() {
        HttpClient.ResponseHandler<Amount> jsonResponseHandler = responseHandlers.jsonResponseHandler(Amount.class);
        ByteArrayInputStream responseBody = new ByteArrayInputStream("{}".getBytes());
        ResponseHeaders responseHeaders = ResponseHeaders.emptyResponseHeaders();

        try {
            jsonResponseHandler.apply(400, responseBody, responseHeaders);
            fail();
        } catch (ErrorResponseException e) {
            assertThat(e.getMessage()).isEqualTo("{}");
        }
    }

    @Test
    void consentCreationResponseHandlerThrowsErrorOnUnsupportedFormatBody() {
        HttpClient.ResponseHandler<Amount> jsonResponseHandler = responseHandlers.jsonResponseHandler(Amount.class);
        ByteArrayInputStream responseBody = new ByteArrayInputStream("<response>".getBytes());
        ResponseHeaders responseHeaders = ResponseHeaders.emptyResponseHeaders();

        try {
            jsonResponseHandler.apply(400, responseBody, responseHeaders);
            fail();
        } catch (ErrorResponseException e) {
            assertThat(e.getMessage()).isEqualTo("<response>");
        }
    }

    @Test
    void consentCreationResponseHandlerThrowsErrorOnUnsupportedContentType() {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE_HEADER, "application/xml");
        HttpClient.ResponseHandler<Amount> jsonResponseHandler = responseHandlers.jsonResponseHandler(Amount.class);
        ByteArrayInputStream responseBody = new ByteArrayInputStream("<response>".getBytes());
        ResponseHeaders responseHeaders = ResponseHeaders.fromMap(headers);

        try {
            jsonResponseHandler.apply(400, responseBody, responseHeaders);
            fail();
        } catch (ErrorResponseException e) {
            assertThat(e.getMessage()).isEqualTo("<response>");
        }
    }

    @Test
    void consentCreationResponseHandlerThrowsErrorInJsonFormat() {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE_HEADER, "application/json");
        HttpClient.ResponseHandler<Amount> jsonResponseHandler = responseHandlers.jsonResponseHandler(Amount.class);
        ByteArrayInputStream responseBody = new ByteArrayInputStream("{}".getBytes());
        ResponseHeaders responseHeaders = ResponseHeaders.fromMap(headers);

        try {
            jsonResponseHandler.apply(400, responseBody, responseHeaders);
            fail();
        } catch (ErrorResponseException e) {
            assertThat(e.getMessage()).isEqualTo("{}");
        }
    }

    @Test
    void multipartFormDataResponseHandlerThrowsWhenContentTypeNotSpecified() {
        HttpClient.ResponseHandler<PeriodicPaymentInitiationMultipartBody> multipartFormDataResponseHandler =
            responseHandlers.multipartFormDataResponseHandler(PeriodicPaymentInitiationMultipartBody.class);
        ResponseHeaders responseHeaders = ResponseHeaders.emptyResponseHeaders();

        HttpClientException exception = assertThrows(HttpClientException.class,
            () -> multipartFormDataResponseHandler.apply(200, null, responseHeaders));
        assertThat(exception.getMessage()).isEqualTo("Unexpected content type: null");
    }

    @Test
    void multipartFormDataResponseHandlerThrowsWhenContentTypeSpecifiedIsNotMultipartFormData() {
        HttpClient.ResponseHandler<PeriodicPaymentInitiationMultipartBody> multipartFormDataResponseHandler =
            responseHandlers.multipartFormDataResponseHandler(PeriodicPaymentInitiationMultipartBody.class);
        ResponseHeaders responseHeaders = ResponseHeaders.fromMap(singletonMap("Content-Type", "application/xml"));

        HttpClientException exception = assertThrows(HttpClientException.class,
            () -> multipartFormDataResponseHandler.apply(200, null, responseHeaders));
        assertThat(exception.getMessage()).isEqualTo("Unexpected content type: application/xml");
    }

    @Test
    void multipartFormDataResponseHandlerThrowsWhenBoundaryNotSpecified() {
        HttpClient.ResponseHandler<PeriodicPaymentInitiationMultipartBody> multipartFormDataResponseHandler =
            responseHandlers.multipartFormDataResponseHandler(PeriodicPaymentInitiationMultipartBody.class);
        ResponseHeaders responseHeaders = ResponseHeaders.fromMap(singletonMap("Content-Type", "multipart/form-data"));

        HttpClientException exception = assertThrows(HttpClientException.class,
            () -> multipartFormDataResponseHandler.apply(200, null, responseHeaders));
        assertThat(exception.getMessage()).contains("boundary");
    }

    @Test
    void multipartFormDataResponseHandlerThrowsWhenPartNameDoesNotMatchAnyObjectProperties() {
        HttpClient.ResponseHandler<PeriodicPaymentInitiationMultipartBody> multipartFormDataResponseHandler =
            responseHandlers.multipartFormDataResponseHandler(PeriodicPaymentInitiationMultipartBody.class);
        String body = "--wYxajuhgWlVdGgMi-GoYM7orJaBzQ0z6JffqaC\r\n" +
            "Content-Disposition: form-data; name=\"unexpected_part_name\"\r\n" +
            "Content-Type: application/xml\r\n" +
            "\r\n" +
            "<Document></Document>\r\n" +
            "--wYxajuhgWlVdGgMi-GoYM7orJaBzQ0z6JffqaC--\r\n";
        Map<String, String> headers = singletonMap("Content-Type",
            "multipart/form-data; boundary=wYxajuhgWlVdGgMi-GoYM7orJaBzQ0z6JffqaC;charset=UTF-8");
        ByteArrayInputStream responseBody = new ByteArrayInputStream(body.getBytes());
        ResponseHeaders responseHeaders = ResponseHeaders.fromMap(headers);

        assertThrows(RuntimeException.class,
            () -> multipartFormDataResponseHandler.apply(200, responseBody, responseHeaders));
    }

    @Test
    void multipartFormDataResponseHandlerCanDeserializePeriodicPaymentInitiationMultipartBody() {
        String responseBody = "--wYxajuhgWlVdGgMi-GoYM7orJaBzQ0z6JffqaC\r\n" +
            "Content-Disposition: form-data; name=\"xml_sct\"\r\n" +
            "Content-Type: application/xml\r\n" +
            "\r\n" +
            "<Document></Document>\r\n" +
            "--wYxajuhgWlVdGgMi-GoYM7orJaBzQ0z6JffqaC\r\n" +
            "Content-Disposition: form-data; name=\"json_standingorderType\"\r\n" +
            "Content-Type: application/json\r\n" +
            "\r\n" +
            "{\"startDate\":\"2018-03-01\",\"executionRule\":\"following\",\"frequency\":\"Monthly\"}\r\n" +
            "--wYxajuhgWlVdGgMi-GoYM7orJaBzQ0z6JffqaC--\r\n";
        Map<String, String> headers = singletonMap("Content-Type",
            "multipart/form-data; boundary=wYxajuhgWlVdGgMi-GoYM7orJaBzQ0z6JffqaC;charset=UTF-8");
        PeriodicPaymentInitiationMultipartBody expected = new PeriodicPaymentInitiationMultipartBody();
        expected.setXml_sct("<Document></Document>");
        PeriodicPaymentInitiationXmlPart2StandingorderTypeJson json = new PeriodicPaymentInitiationXmlPart2StandingorderTypeJson();
        json.setStartDate(LocalDate.of(2018, 3, 1));
        json.setExecutionRule(ExecutionRule.FOLLOWING);
        json.setFrequency(FrequencyCode.MONTHLY);
        expected.setJson_standingorderType(json);

        PeriodicPaymentInitiationMultipartBody actual =
            responseHandlers.multipartFormDataResponseHandler(PeriodicPaymentInitiationMultipartBody.class)
                .apply(200, new ByteArrayInputStream(responseBody.getBytes()), ResponseHeaders.fromMap(headers));

        assertThat(actual).isEqualTo(expected);
    }
}
