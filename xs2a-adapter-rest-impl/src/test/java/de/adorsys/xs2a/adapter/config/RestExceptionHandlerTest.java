package de.adorsys.xs2a.adapter.config;

import de.adorsys.xs2a.adapter.api.model.ErrorResponse;
import de.adorsys.xs2a.adapter.api.model.MessageCode;
import de.adorsys.xs2a.adapter.api.model.TppMessage;
import de.adorsys.xs2a.adapter.api.model.TppMessageCategory;
import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.validation.RequestValidationException;
import de.adorsys.xs2a.adapter.validation.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static de.adorsys.xs2a.adapter.service.ResponseHeaders.emptyResponseHeaders;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

class RestExceptionHandlerTest {

    private static final String OAUTH_ERROR_RESPONSE = "{\n" +
        "  \"timestamp\": \"2019-10-17T13:12:15.171+0000\",\n" +
        "  \"status\": 400,\n" +
        "  \"error\": \"Bad Request\",\n" +
        "  \"message\": \"Missing request header 'code' for method parameter of type String\",\n" +
        "  \"path\": \"/oauth/token\"\n" +
        "}";

    private RestExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new RestExceptionHandler(new HeadersMapper());
    }

    @Test
    void handleErrorResponseExceptionReturnsOriginalMessageWhenXs2aErrorResponseFieldsAreAbsent() {
        ErrorResponseException ex =
            new ErrorResponseException(400, emptyResponseHeaders(), new ErrorResponse(), OAUTH_ERROR_RESPONSE);
        RestExceptionHandler exceptionHandler = this.exceptionHandler;
        ResponseEntity responseEntity = exceptionHandler.handle(ex);

        assertThat(responseEntity.getBody()).isEqualTo(OAUTH_ERROR_RESPONSE);
    }

    @Test
    void requestValidationException() {
        RequestValidationException requestValidationException =
            new RequestValidationException(singletonList(new ValidationError(ValidationError.Code.REQUIRED,
                RequestHeaders.TPP_REDIRECT_URI,
                "...")));

        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handle(requestValidationException);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getHeaders().get("X-GTW-Error-Origination")).isEqualTo(singletonList("ADAPTER"));
        assertThat(responseEntity.getBody().getTppMessages()).hasSize(1);
        TppMessage tppMessage = responseEntity.getBody().getTppMessages().get(0);
        assertThat(tppMessage.getCategory()).isEqualTo(TppMessageCategory.ERROR);
        assertThat(tppMessage.getCode()).isEqualTo(MessageCode.FORMAT_ERROR);
        assertThat(tppMessage.getPath()).isEqualTo(requestValidationException.getValidationErrors().get(0).getPath());
        assertThat(tppMessage.getText()).isEqualTo(requestValidationException.getValidationErrors().get(0).getMessage());
    }
}
