package de.adorsys.xs2a.adapter.config;

import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.service.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.service.model.ErrorResponse;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static de.adorsys.xs2a.adapter.service.ResponseHeaders.emptyResponseHeaders;
import static org.assertj.core.api.Assertions.assertThat;

public class RestExceptionHandlerTest {

    private static final String OAUTH_ERROR_RESPONSE = "{\n" +
        "  \"timestamp\": \"2019-10-17T13:12:15.171+0000\",\n" +
        "  \"status\": 400,\n" +
        "  \"error\": \"Bad Request\",\n" +
        "  \"message\": \"Missing request header 'code' for method parameter of type String\",\n" +
        "  \"path\": \"/oauth/token\"\n" +
        "}";

    @Test
    public void handleErrorResponseExceptionReturnsOriginalMessageWhenXs2aErrorResponseFieldsAreAbsent() {
        ErrorResponseException ex =
            new ErrorResponseException(400, emptyResponseHeaders(), new ErrorResponse(), OAUTH_ERROR_RESPONSE);
        RestExceptionHandler exceptionHandler = new RestExceptionHandler(new HeadersMapper());
        ResponseEntity responseEntity = exceptionHandler.handle(ex);

        assertThat(responseEntity.getBody()).isEqualTo(OAUTH_ERROR_RESPONSE);
    }
}
