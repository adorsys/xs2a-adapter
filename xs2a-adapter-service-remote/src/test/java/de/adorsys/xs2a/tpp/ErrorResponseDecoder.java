package de.adorsys.xs2a.tpp;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.service.ErrorResponse;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.exception.ErrorResponseException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class ErrorResponseDecoder implements ErrorDecoder {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        ResponseHeaders responseHeaders = ResponseHeaders.fromMap(
            response.headers().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().iterator().next()))
        );

        ErrorResponse errorResponse = null;
        try {
            errorResponse = objectMapper.readValue(response.body().toString(), ErrorResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ErrorResponseException(response.status(), responseHeaders, errorResponse);
    }
}
