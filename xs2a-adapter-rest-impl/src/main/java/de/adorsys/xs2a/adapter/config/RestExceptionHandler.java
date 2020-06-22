package de.adorsys.xs2a.adapter.config;

import de.adorsys.xs2a.adapter.api.model.ErrorResponse;
import de.adorsys.xs2a.adapter.api.model.MessageCode;
import de.adorsys.xs2a.adapter.api.model.TppMessage;
import de.adorsys.xs2a.adapter.api.model.TppMessageCategory;
import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.service.exception.*;
import de.adorsys.xs2a.adapter.validation.RequestValidationException;
import de.adorsys.xs2a.adapter.validation.ValidationError;
import org.slf4j.MDC;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String ERROR_ORIGINATION_HEADER_NAME = "X-GTW-Error-Origination";

    private final HeadersMapper headersMapper;

    public RestExceptionHandler(HeadersMapper headersMapper) {
        this.headersMapper = headersMapper;
    }

    @ExceptionHandler
    ResponseEntity handle(ErrorResponseException exception) {
        logError(exception);
        HttpHeaders responseHeaders = addErrorOriginationHeader(
                headersMapper.toHttpHeaders(exception.getResponseHeaders()),
                ErrorOrigination.BANK
        );
        HttpStatus status = HttpStatus.valueOf(exception.getStatusCode());

        return exception.getErrorResponse()
                       .map(response -> {
                           String originalResponse = exception.getMessage();
                           if (response.getTppMessages() == null && response.getLinks() == null
                               && originalResponse != null && !originalResponse.isEmpty()) {
                               return new ResponseEntity<>(originalResponse, responseHeaders, status);
                           }
                           return new ResponseEntity<>(response, responseHeaders, status);
                       })
                       .orElseGet(() -> new ResponseEntity<>(responseHeaders, status));
    }

    @ExceptionHandler
    ResponseEntity handle(OAuthException exception) {
        logError(exception);

        HttpHeaders responseHeaders = addErrorOriginationHeader(
            headersMapper.toHttpHeaders(exception.getResponseHeaders()),
            ErrorOrigination.BANK
        );

        String originalResponse = exception.getMessage();
        ErrorResponse errorResponse = exception.getErrorResponse();

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity
                                                 .status(HttpStatus.FORBIDDEN)
                                                 .headers(responseHeaders);

        if (errorResponse != null && (errorResponse.getTppMessages() != null || errorResponse.getLinks() != null)) {
            return responseBuilder
                       .body(errorResponse);
        }

        if (originalResponse != null && !originalResponse.trim().isEmpty()) {
            return responseBuilder
                       .body(originalResponse);
        }

        return responseBuilder
                   .build();
    }

    @ExceptionHandler
    ResponseEntity handle(NotAcceptableException exception) {
        logError(exception);
        return ResponseEntity
                       .status(HttpStatus.NOT_ACCEPTABLE)
                       .headers(addErrorOriginationHeader(new HttpHeaders(), ErrorOrigination.BANK))
                       .build();
    }

    @ExceptionHandler
    ResponseEntity handle(HttpRequestSigningException exception) {
        logError(exception);
        ErrorResponse errorResponse = buildErrorResponse("Exception during the request signing process");
        HttpHeaders headers = addErrorOriginationHeader(new HttpHeaders(), ErrorOrigination.ADAPTER);
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    ResponseEntity handle(UncheckedSSLHandshakeException exception) {
        logError(exception);
        ErrorResponse errorResponse = buildErrorResponse("Exception during the SSL handshake process");
        HttpHeaders headers = addErrorOriginationHeader(new HttpHeaders(), ErrorOrigination.BANK);
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    ResponseEntity handle(UncheckedIOException exception) {
        logError(exception);
        ErrorResponse errorResponse = buildErrorResponse("Exception during the IO process");
        HttpHeaders headers = addErrorOriginationHeader(new HttpHeaders(), ErrorOrigination.ADAPTER);
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    ResponseEntity handle(UnsupportedOperationException exception) {
        logError(exception);
        ErrorResponse errorResponse = buildErrorResponse("This endpoint is not supported yet");
        HttpHeaders headers = addErrorOriginationHeader(new HttpHeaders(), ErrorOrigination.ADAPTER);
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler
    ResponseEntity<ErrorResponse> handle(RequestValidationException exception) {
        logError(exception);
        ErrorResponse errorResponse = new ErrorResponse();
        ArrayList<TppMessage> tppMessages = new ArrayList<>();
        for (ValidationError validationError : exception.getValidationErrors()) {
            TppMessage tppMessage = new TppMessage();
            tppMessage.setCategory(TppMessageCategory.ERROR);
            tppMessage.setCode(MessageCode.FORMAT_ERROR);
            tppMessage.setPath(validationError.getPath());
            tppMessage.setText(validationError.getMessage());
            tppMessages.add(tppMessage);
        }
        errorResponse.setTppMessages(tppMessages);
        HttpHeaders headers = addErrorOriginationHeader(new HttpHeaders(), ErrorOrigination.ADAPTER);
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
        BadRequestException.class,
        AspspRegistrationException.class,
        AspspRegistrationNotFoundException.class,
        AdapterNotFoundException.class,
        IbanException.class
    })
    ResponseEntity<Object> handleAsBadRequest(Exception exception) {
        logError(exception);
        return handleAsBadRequest(exception.getMessage());
    }

    private ResponseEntity<Object> handleAsBadRequest(String errorText) {
        ErrorResponse errorResponse = buildErrorResponse(errorText);
        HttpHeaders headers = addErrorOriginationHeader(new HttpHeaders(), ErrorOrigination.ADAPTER);
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {

        logError(ex);
        return handleAsBadRequest("Required parameter '" + ex.getParameterName() + "' is missing");
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                        HttpHeaders headers,
                                                        HttpStatus status,
                                                        WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            logError(ex);
            MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) ex;
            String errorMessage = "Illegal value '" + exception.getValue() + "' for parameter '" + exception.getName() + "'";
            Class<?> parameterType = exception.getParameter().getParameterType();
            if (parameterType.isEnum()) {
                errorMessage += ", allowed values: " + Arrays.stream(parameterType.getEnumConstants())
                    .map(Objects::toString)
                    .collect(Collectors.joining(", "));
            }

            return handleAsBadRequest(errorMessage);
        }
        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @ExceptionHandler
    ResponseEntity handle(PsuPasswordEncodingException exception) {
        logError(exception);
        ErrorResponse errorResponse = buildErrorResponse("Exception during PSU password encryption");
        HttpHeaders headers = addErrorOriginationHeader(new HttpHeaders(), ErrorOrigination.ADAPTER);
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    ResponseEntity handle(Exception exception) {
        logError(exception);
        ErrorResponse errorResponse = buildErrorResponse("Server error");
        HttpHeaders headers = addErrorOriginationHeader(new HttpHeaders(), ErrorOrigination.ADAPTER);
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse buildErrorResponse(String text) {
        TppMessage tppMessage = new TppMessage();
        tppMessage.setCategory(TppMessageCategory.ERROR);
        tppMessage.setText(text);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTppMessages(Collections.singletonList(tppMessage));
        return errorResponse;
    }

    private HttpHeaders addErrorOriginationHeader(HttpHeaders httpHeaders, ErrorOrigination errorOrigination) {
        httpHeaders.add(ERROR_ORIGINATION_HEADER_NAME, errorOrigination.name());
        return httpHeaders;
    }

    private void logError(Exception exception) {
        String errorMessage = exception.getMessage() == null ? "" : exception.getMessage();
        logger.error(errorMessage, exception);

        MDC.put("errorMessage", errorMessage);
    }

    private enum ErrorOrigination {
        BANK,
        ADAPTER
    }
}
