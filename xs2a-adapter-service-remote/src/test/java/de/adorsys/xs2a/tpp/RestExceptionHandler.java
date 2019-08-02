package de.adorsys.xs2a.tpp;

import de.adorsys.xs2a.adapter.api.remote.Xs2aAdapterClientParseException;
import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.model.TppMessageCategoryTO;
import de.adorsys.xs2a.adapter.service.ErrorResponse;
import de.adorsys.xs2a.adapter.service.TppMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String ERROR_ORIGINATION_HEADER_NAME = "X-GTW-Error-Origination";

    private final HeadersMapper headersMapper;

    public RestExceptionHandler(HeadersMapper headersMapper) {
        this.headersMapper = headersMapper;
    }


    @ExceptionHandler
    ResponseEntity handle(Xs2aAdapterClientParseException exception) {
        return badRequestHandler(exception);
    }

    private ResponseEntity badRequestHandler(RuntimeException exception) {
        logger.error(exception.getMessage(), exception);
        String errorText = exception.getMessage();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = buildErrorResponse(TppMessageCategoryTO.ERROR.name(), httpStatus.name(), errorText);
        HttpHeaders headers = addErrorOriginationHeader(new HttpHeaders(), ErrorOrigination.ADAPTER);
        return new ResponseEntity<>(errorResponse, headers, httpStatus);
    }

    @ExceptionHandler
    ResponseEntity handle(Exception exception) {
        logger.error(exception.getMessage(), exception);
        String errorText = "Server error";
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = buildErrorResponse(TppMessageCategoryTO.ERROR.name(), httpStatus.name(), errorText);
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
