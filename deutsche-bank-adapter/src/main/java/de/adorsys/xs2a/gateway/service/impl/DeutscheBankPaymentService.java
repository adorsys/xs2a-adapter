package de.adorsys.xs2a.gateway.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.gateway.service.*;
import de.adorsys.xs2a.gateway.service.exception.ErrorResponseException;
import de.adorsys.xs2a.gateway.service.impl.mapper.PaymentMapper;
import de.adorsys.xs2a.gateway.service.impl.model.DeutscheBankPaymentInitiationResponse;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class DeutscheBankPaymentService implements PaymentService {

    private static final String DATE_HEADER_NAME = "Date";
    private static final String PAYMENTS_SEPA_CREDIT_TRANSFERS_URI = "https://simulator-xs2a.db.com/pis/DE/SB-DB/v1/payments/sepa-credit-transfers";
    private static final String SLASH_SEPARATOR = "/";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PaymentMapper paymentMapper = Mappers.getMapper(PaymentMapper.class);
    {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public PaymentInitiationRequestResponse initiateSinglePayment(String paymentProduct, Object body, Headers headers) {
        requireSepaCreditTransfer(paymentProduct);

        Map<String, String> headersMap = headers.toMap();
        headersMap.put(DATE_HEADER_NAME, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        headersMap.put("Content-Type", "application/json");

        String bodyString = writeValueAsString(objectMapper.convertValue(body, SinglePaymentInitiationBody.class));

        DeutscheBankPaymentInitiationResponse response = httpClient.post(PAYMENTS_SEPA_CREDIT_TRANSFERS_URI, bodyString, headersMap, (statusCode, responseBody) -> {
            switch (statusCode) {
                case 201:
                    return readValue(responseBody, DeutscheBankPaymentInitiationResponse.class);
                case 401:
                case 400:
                    throw new ErrorResponseException(statusCode, readValue(responseBody, ErrorResponse.class));
                default:
                    throw new UnexpectedResponseStatusCodeException();
            }
        });
        return paymentMapper.toPaymentInitiationRequestResponse(response);
    }

    private void requireSepaCreditTransfer(String paymentProduct) {
        if (!paymentProduct.equalsIgnoreCase("sepa-credit-transfers")) {
            throw new UnsupportedOperationException(paymentProduct);
        }
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
    public SinglePaymentInitiationInformationWithStatusResponse getSinglePaymentInformation(String paymentProduct, String paymentId, Headers headers) {
        requireSepaCreditTransfer(paymentProduct);

        String uri = PAYMENTS_SEPA_CREDIT_TRANSFERS_URI + SLASH_SEPARATOR + paymentId;

        Map<String, String> headersMap = headers.toMap();
        headersMap.put(DATE_HEADER_NAME, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        headersMap.put("Accept", "application/json");

        return httpClient.get(uri, headersMap, (statusCode, responseBody) -> {
                    switch (statusCode) {
                        case 200:
                            return readValue(responseBody, SinglePaymentInitiationInformationWithStatusResponse.class);
                        case 400:
                        case 401:
                        case 403:
                        case 404:
                        case 405:
                            throw new ErrorResponseException(statusCode, readValue(responseBody, ErrorResponse.class));
                        case 406:
                        case 408:
                        case 415:
                        case 429:
                        case 500:
                        case 503:
                            throw new ErrorResponseException(statusCode);
                        default:
                            throw new UnexpectedResponseStatusCodeException();
                    }
                }
        );
    }

    @Override
    public PaymentInitiationScaStatusResponse getPaymentInitiationScaStatus(String paymentService, String paymentProduct, String paymentId, String authorisationId, Headers headers) {
        throw new UnsupportedOperationException();
    }
}
