package de.adorsys.xs2a.gateway.config;

import de.adorsys.xs2a.gateway.service.ErrorResponse;
import de.adorsys.xs2a.gateway.service.exception.ErrorResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    ResponseEntity<Object> handle(ErrorResponseException exception) {
        Optional<ErrorResponse> errorResponse = exception.getErrorResponse();
        if (errorResponse.isPresent()) {
            return new ResponseEntity<>(errorResponse.get(), HttpStatus.valueOf(exception.getStatusCode()));
        }
        return new ResponseEntity<>(HttpStatus.valueOf(exception.getStatusCode()));
    }
}
