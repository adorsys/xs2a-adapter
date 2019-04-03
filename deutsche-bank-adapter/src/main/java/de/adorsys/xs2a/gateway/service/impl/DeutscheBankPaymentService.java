package de.adorsys.xs2a.gateway.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.gateway.service.*;
import de.adorsys.xs2a.gateway.service.impl.mapper.PaymentMapper;
import de.adorsys.xs2a.gateway.service.impl.model.PaymentInitiationResponse;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class DeutscheBankPaymentService implements PaymentService {

    private static final String DATE_HEADER_NAME = "Date";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PaymentMapper paymentMapper = Mappers.getMapper(PaymentMapper.class);
    {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public PaymentInitiationRequestResponse initiatePayment(String paymentService, String paymentProduct, Object body, PaymentInitiationHeaders headers) {
        if (!paymentService.equalsIgnoreCase("payments")) {
            throw new UnsupportedOperationException(paymentService);
        }
        return initiatePayment(paymentProduct, headers, objectMapper.convertValue(body, SinglePaymentInitiationBody.class));
    }

    private PaymentInitiationRequestResponse initiatePayment(String paymentProduct, PaymentInitiationHeaders headers, SinglePaymentInitiationBody body) {
        if (!paymentProduct.equalsIgnoreCase("sepa-credit-transfers")) {
            throw new UnsupportedOperationException(paymentProduct);
        }

        String uri = "https://simulator-xs2a.db.com/pis/DE/SB-DB/v1/payments/sepa-credit-transfers";
        Map<String, String> headersMap = headers.toMap();
        headersMap.put(DATE_HEADER_NAME, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        headersMap.put("Content-Type", "application/json");
        PaymentInitiationResponse response = httpClient.post(uri, writeValueAsString(body), headersMap, statusCode -> {
            switch (statusCode) {
                case 201:
                    return responseBody -> readValue(responseBody, PaymentInitiationResponse.class);
                case 401:
                case 400:
                    return responseBody -> {
                        throw new ErrorResponseException(statusCode, readValue(responseBody, ErrorResponse.class));
                    };
                default:
                    throw new UnexpectedResponseStatusCodeException();
            }
        });
        return paymentMapper.toBerlinGroupPaymentInitiationResponse(response);
    }

    private String writeValueAsString(SinglePaymentInitiationBody body) {
        try {
            return objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    private <T> T readValue(InputStream inputStream, Class<T> klass) {
        try {
            return objectMapper.readValue(inputStream, klass);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public PaymentInformationResponse getPaymentInformation(String paymentService, String paymentProduct, String paymentId, PaymentInformationHeaders headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PaymentInitiationScaStatusResponse getPaymentInitiationScaStatus(String paymentService, String paymentProduct, String paymentId, String authorisationId, PaymentInitiationScaStatusHeaders headers) {
        throw new UnsupportedOperationException();
    }
}
