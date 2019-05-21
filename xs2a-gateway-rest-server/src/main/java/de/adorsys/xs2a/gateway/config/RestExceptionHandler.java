package de.adorsys.xs2a.gateway.config;

import de.adorsys.xs2a.gateway.mapper.HeadersMapper;
import de.adorsys.xs2a.gateway.model.shared.TppMessageCategory;
import de.adorsys.xs2a.gateway.service.ErrorResponse;
import de.adorsys.xs2a.gateway.service.RequestHeaders;
import de.adorsys.xs2a.gateway.service.TppMessage;
import de.adorsys.xs2a.gateway.service.exception.BankCodeNotProvidedException;
import de.adorsys.xs2a.gateway.service.exception.BankNotSupportedException;
import de.adorsys.xs2a.gateway.service.exception.ErrorResponseException;
import de.adorsys.xs2a.gateway.service.exception.NotAcceptableException;
import de.adorsys.xs2a.gateway.signing.exception.HttpRequestSigningException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.Optional;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String ERROR_ORIGINATION_HEADER_NAME = "X-GTW-Error-Origination";

    private final HeadersMapper headersMapper;

    public RestExceptionHandler(HeadersMapper headersMapper) {
        this.headersMapper = headersMapper;
    }

    @ExceptionHandler
    ResponseEntity handle(ErrorResponseException exception) {
        logger.error(exception.getMessage(), exception);
        Optional<ErrorResponse> errorResponse = exception.getErrorResponse();
        HttpHeaders responseHeaders = addErrorOriginationHeader(
                headersMapper.toHttpHeaders(exception.getResponseHeaders()),
                ErrorOrigination.BANK
        );

        return errorResponse
                       .map(response -> new ResponseEntity<>(response, responseHeaders, HttpStatus.valueOf(exception.getStatusCode())))
                       .orElseGet(() -> new ResponseEntity<>(responseHeaders, HttpStatus.valueOf(exception.getStatusCode())));
    }

    @ExceptionHandler
    ResponseEntity handle(NotAcceptableException exception) {
        return ResponseEntity
                       .status(HttpStatus.NOT_ACCEPTABLE)
                       .headers(addErrorOriginationHeader(new HttpHeaders(), ErrorOrigination.ADAPTER))
                       .build();
    }

    @ExceptionHandler
    ResponseEntity handle(HttpRequestSigningException exception) {
        logger.error(exception.getMessage(), exception);
        String errorText = "Exception during the request signing process";
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = buildErrorResponse(TppMessageCategory.ERROR.name(), httpStatus.name(), errorText);
        HttpHeaders headers = addErrorOriginationHeader(new HttpHeaders(), ErrorOrigination.ADAPTER);
        return new ResponseEntity<>(errorResponse, headers, httpStatus);
    }

    @ExceptionHandler
    ResponseEntity handle(UncheckedIOException exception) {
        logger.error(exception.getMessage(), exception);
        String errorText = "Exception during the IO process";
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = buildErrorResponse(TppMessageCategory.ERROR.name(), httpStatus.name(), errorText);
        HttpHeaders headers = addErrorOriginationHeader(new HttpHeaders(), ErrorOrigination.ADAPTER);
        return new ResponseEntity<>(errorResponse, headers, httpStatus);
    }

    @ExceptionHandler
    ResponseEntity handle(UnsupportedOperationException exception) {
        logger.error(exception.getMessage(), exception);
        String errorText = "This endpoint is not supported yet";
        HttpStatus httpStatus = HttpStatus.NOT_IMPLEMENTED;
        ErrorResponse errorResponse = buildErrorResponse(TppMessageCategory.ERROR.name(), httpStatus.name(), errorText);
        HttpHeaders headers = addErrorOriginationHeader(new HttpHeaders(), ErrorOrigination.ADAPTER);
        return new ResponseEntity<>(errorResponse, headers, httpStatus);
    }

    @ExceptionHandler
    ResponseEntity handle(BankCodeNotProvidedException exception) {
        logger.error(exception.getMessage(), exception);
        String errorText = String.format("%s header is not provided within the request", RequestHeaders.X_GTW_BANK_CODE);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = buildErrorResponse(TppMessageCategory.ERROR.name(), httpStatus.name(), errorText);
        HttpHeaders headers = addErrorOriginationHeader(new HttpHeaders(), ErrorOrigination.ADAPTER);
        return new ResponseEntity<>(errorResponse, headers, httpStatus);
    }

    @ExceptionHandler
    ResponseEntity handle(BankNotSupportedException exception) {
        String errorText = exception.getMessage();
        logger.error(errorText, exception);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = buildErrorResponse(TppMessageCategory.ERROR.name(), httpStatus.name(), errorText);
        HttpHeaders headers = addErrorOriginationHeader(new HttpHeaders(), ErrorOrigination.ADAPTER);
        return new ResponseEntity<>(errorResponse, headers, httpStatus);
    }

    @ExceptionHandler
    ResponseEntity handle(Exception exception) {
        logger.error(exception.getMessage(), exception);
        String errorText = "Server error";
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = buildErrorResponse(TppMessageCategory.ERROR.name(), httpStatus.name(), errorText);
        HttpHeaders headers = addErrorOriginationHeader(new HttpHeaders(), ErrorOrigination.ADAPTER);
        return new ResponseEntity<>(errorResponse, headers, httpStatus);
    }

    private ErrorResponse buildErrorResponse(String category, String code, String text) {
        TppMessage tppMessage = new TppMessage();
        tppMessage.setCategory(category);
        tppMessage.setCode(code);
        tppMessage.setText(text);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTppMessages(Collections.singletonList(tppMessage));
        return errorResponse;
    }

    private HttpHeaders addErrorOriginationHeader(HttpHeaders httpHeaders, ErrorOrigination errorOrigination) {
        httpHeaders.add(ERROR_ORIGINATION_HEADER_NAME, errorOrigination.name());
        return httpHeaders;
    }

    private enum ErrorOrigination {
        BANK,
        ADAPTER
    }
}
