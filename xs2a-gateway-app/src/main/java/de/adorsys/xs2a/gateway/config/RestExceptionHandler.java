package de.adorsys.xs2a.gateway.config;

import de.adorsys.xs2a.gateway.mapper.HeadersMapper;
import de.adorsys.xs2a.gateway.model.shared.TppMessageCategory;
import de.adorsys.xs2a.gateway.service.ErrorResponse;
import de.adorsys.xs2a.gateway.service.MessageErrorCode;
import de.adorsys.xs2a.gateway.service.TppMessage;
import de.adorsys.xs2a.gateway.service.exception.ErrorResponseException;
import de.adorsys.xs2a.gateway.signing.exception.HttpRequestSigningException;
import de.adorsys.xs2a.gateway.service.exception.NotAcceptableException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.Optional;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private final HeadersMapper headersMapper;

    public RestExceptionHandler(HeadersMapper headersMapper) {
        this.headersMapper = headersMapper;
    }

    @ExceptionHandler
    ResponseEntity handle(ErrorResponseException exception) {
        Optional<ErrorResponse> errorResponse = exception.getErrorResponse();
        HttpHeaders responseHeaders = headersMapper.toHttpHeaders(exception.getResponseHeaders());

        return errorResponse
                       .map(response -> new ResponseEntity<>(response, responseHeaders, HttpStatus.valueOf(exception.getStatusCode())))
                       .orElseGet(() -> new ResponseEntity<>(responseHeaders, HttpStatus.valueOf(exception.getStatusCode())));
    }

    @ExceptionHandler
    ResponseEntity handle(NotAcceptableException exception) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .build();
    }

    @ExceptionHandler
    ResponseEntity handle(HttpRequestSigningException exception) {
        ErrorResponse errorResponse = new ErrorResponse();

        TppMessage tppMessage = new TppMessage();
        tppMessage.setCategory(TppMessageCategory.ERROR.name());
        tppMessage.setCode(MessageErrorCode.INTERNAL_SERVER_ERROR.name());
        tppMessage.setText("Exception during the request signing process");

        errorResponse.setTppMessages(Collections.singletonList(tppMessage));

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
