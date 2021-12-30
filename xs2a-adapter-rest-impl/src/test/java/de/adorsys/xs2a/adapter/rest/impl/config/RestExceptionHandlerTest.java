package de.adorsys.xs2a.adapter.rest.impl.config;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.exception.AccessTokenException;
import de.adorsys.xs2a.adapter.api.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.api.exception.RequestAuthorizationValidationException;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.api.validation.RequestValidationException;
import de.adorsys.xs2a.adapter.api.validation.ValidationError;
import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static de.adorsys.xs2a.adapter.api.ResponseHeaders.emptyResponseHeaders;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

class RestExceptionHandlerTest {

    private static final String OAUTH_ERROR_RESPONSE = "{\n" +
        "  \"timestamp\": \"2019-10-17T13:12:15.171+0000\",\n" +
        "  \"status\": 400,\n" +
        "  \"error\": \"Bad Request\",\n" +
        "  \"message\": \"Missing request header 'code' for method parameter of type String\",\n" +
        "  \"path\": \"/oauth/token\"\n" +
        "}";
    private static final String X_GTW_ERROR_ORIGINATION = "X-GTW-Error-Origination";
    private static final String EMBEDDED_PRE_AUTH = "embeddedPreAuth";
    private static final String PRE_AUTH_TOKEN_URI = "/v1/embedded-pre-auth/token";
    public static final String ADAPTER = "ADAPTER";

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
        ResponseEntity<?> responseEntity = exceptionHandler.handle(ex);

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
        assertThat(responseEntity.getHeaders().get(X_GTW_ERROR_ORIGINATION)).isEqualTo(singletonList(ADAPTER));
        assertThat(responseEntity.getBody().getTppMessages()).hasSize(1);
        TppMessage tppMessage = responseEntity.getBody().getTppMessages().get(0);
        assertThat(tppMessage.getCategory()).isEqualTo(TppMessageCategory.ERROR);
        assertThat(tppMessage.getCode()).isEqualTo(MessageCode.FORMAT_ERROR.toString());
        assertThat(tppMessage.getPath()).isEqualTo(requestValidationException.getValidationErrors().get(0).getPath());
        assertThat(tppMessage.getText()).isEqualTo(requestValidationException.getValidationErrors().get(0).getMessage());
    }

    @Test
    void preAuthorisationException() {
        RequestAuthorizationValidationException requestAuthorizationValidationException
            = new RequestAuthorizationValidationException(getErrorResponse(), "test error");

        ResponseEntity<?> actualResponse = exceptionHandler.handle(requestAuthorizationValidationException);

        assertThat(actualResponse)
            .isNotNull()
            .matches(res -> res.getStatusCodeValue() == 401)
            .matches(res -> {
                HttpHeaders headers = res.getHeaders();
                List<String> originator = headers.get(X_GTW_ERROR_ORIGINATION);
                if (originator != null) {
                    return originator.contains(ADAPTER);
                }
                return false;
            })
            .extracting(ResponseEntity::getBody)
            .isInstanceOf(ErrorResponse.class)
            .matches(body -> ((ErrorResponse) body)
                .getLinks()
                .get(EMBEDDED_PRE_AUTH)
                .getHref()
                .equals(PRE_AUTH_TOKEN_URI));
    }

    @Test
    void accessTokenException_internalError() {
        var actualResponse = exceptionHandler.handle(new AccessTokenException("foo"));

        assertThat(actualResponse)
            .isNotNull()
            .matches(resp -> HttpStatus.INTERNAL_SERVER_ERROR == resp.getStatusCode())
            .matches(resp -> {
                var headers = resp.getHeaders();
                assertThat(headers).isNotEmpty();
                return headers.containsValue(List.of("ADAPTER"));
            })
            .extracting(ResponseEntity::getBody)
            .extracting(ErrorResponse::getTppMessages, InstanceOfAssertFactories.list(TppMessage.class))
            .hasSize(1)
            .element(0)
            .matches(tppMessage -> "Server error".equals(tppMessage.getText()));
    }

    @Test
    void accessTokenException_bankError() {
        final String originalMessage = "bank message";
        var actualResponse
            = exceptionHandler.handle(new AccessTokenException("foo", 400, originalMessage, true));

        assertThat(actualResponse)
            .isNotNull()
            .matches(resp -> HttpStatus.BAD_REQUEST == resp.getStatusCode())
            .matches(resp -> {
                var headers = resp.getHeaders();
                assertThat(headers).isNotEmpty();
                return headers.containsValue(List.of("BANK"));
            })
            .extracting(ResponseEntity::getBody)
            .extracting(ErrorResponse::getTppMessages, InstanceOfAssertFactories.list(TppMessage.class))
            .hasSize(1)
            .element(0)
            .matches(tppMessage -> originalMessage.equals(tppMessage.getText()));
    }

    private ErrorResponse getErrorResponse() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setLinks(singletonMap(EMBEDDED_PRE_AUTH, getHrefType()));
        return errorResponse;
    }

    private HrefType getHrefType() {
        HrefType hrefType = new HrefType();
        hrefType.setHref(PRE_AUTH_TOKEN_URI);
        return hrefType;
    }
}
