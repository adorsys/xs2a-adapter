package de.adorsys.xs2a.gateway.config;

import de.adorsys.xs2a.gateway.mapper.HeadersMapper;
import de.adorsys.xs2a.gateway.model.shared.TppMessageCategory;
import de.adorsys.xs2a.gateway.service.ErrorResponse;
import de.adorsys.xs2a.gateway.service.TppMessage;
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
    private static final String ERROR_SIDE_HEADER_NAME = "Error-Side";

    private final HeadersMapper headersMapper;

    public RestExceptionHandler(HeadersMapper headersMapper) {
        this.headersMapper = headersMapper;
    }

    @ExceptionHandler
    ResponseEntity handle(ErrorResponseException exception) {
        Optional<ErrorResponse> errorResponse = exception.getErrorResponse();
        HttpHeaders responseHeaders = addErrorSideHeader(
                headersMapper.toHttpHeaders(exception.getResponseHeaders()),
                ErrorSide.BANK
        );

        return errorResponse
                       .map(response -> new ResponseEntity<>(response, responseHeaders, HttpStatus.valueOf(exception.getStatusCode())))
                       .orElseGet(() -> new ResponseEntity<>(responseHeaders, HttpStatus.valueOf(exception.getStatusCode())));
    }

    @ExceptionHandler
    ResponseEntity handle(NotAcceptableException exception) {
        return ResponseEntity
                       .status(HttpStatus.NOT_ACCEPTABLE)
                       .headers(addErrorSideHeader(new HttpHeaders(), ErrorSide.ADAPTER))
                       .build();
    }

    @ExceptionHandler
    ResponseEntity handle(HttpRequestSigningException exception) {
        String errorText = "Exception during the request signing process";
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = buildErrorResponse(TppMessageCategory.ERROR.name(), httpStatus.name(), errorText);
        HttpHeaders headers = addErrorSideHeader(new HttpHeaders(), ErrorSide.ADAPTER);
        return new ResponseEntity<>(errorResponse, headers, httpStatus);
    }

    @ExceptionHandler
    ResponseEntity handle(UncheckedIOException exception) {
        String errorText = "Exception during the IO process";
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = buildErrorResponse(TppMessageCategory.ERROR.name(), httpStatus.name(), errorText);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler
    ResponseEntity handle(UnsupportedOperationException exception) {
        String errorText = "This endpoint is not supported yet";
        HttpStatus httpStatus = HttpStatus.NOT_IMPLEMENTED;
        ErrorResponse errorResponse = buildErrorResponse(TppMessageCategory.ERROR.name(), httpStatus.name(), errorText);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler
    ResponseEntity handle(Exception exception) {
        String errorText = "Server error";
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = buildErrorResponse(TppMessageCategory.ERROR.name(), httpStatus.name(), errorText);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    private ErrorResponse buildErrorResponse(String category, String code, String text) {
        ErrorResponse errorResponse = new ErrorResponse();

        TppMessage tppMessage = new TppMessage();
        tppMessage.setCategory(category);
        tppMessage.setCode(code);
        tppMessage.setText(text);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTppMessages(Collections.singletonList(tppMessage));
        return errorResponse;
    }

    private HttpHeaders addErrorSideHeader(HttpHeaders httpHeaders, ErrorSide errorSide) {
        httpHeaders.add(ERROR_SIDE_HEADER_NAME, errorSide.name());
        return httpHeaders;
    }

    private enum ErrorSide {
        BANK,
        ADAPTER
    }
}
