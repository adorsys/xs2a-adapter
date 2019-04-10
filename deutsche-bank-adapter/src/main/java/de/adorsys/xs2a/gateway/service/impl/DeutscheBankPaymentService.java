package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.service.*;
import de.adorsys.xs2a.gateway.service.impl.mapper.PaymentMapper;
import de.adorsys.xs2a.gateway.service.impl.model.DeutscheBankPaymentInitiationResponse;
import org.mapstruct.factory.Mappers;

import java.util.Map;

public class DeutscheBankPaymentService extends AbstractDeutscheBankService implements PaymentService {

    private static final String PAYMENTS_SEPA_CREDIT_TRANSFERS_URI = BASE_DB_URI + "payments/sepa-credit-transfers";

    private final PaymentMapper paymentMapper = Mappers.getMapper(PaymentMapper.class);

    @Override
    public PaymentInitiationRequestResponse initiateSinglePayment(String paymentProduct, Object body, Headers headers) {
        requireSepaCreditTransfer(paymentProduct);

        Map<String, String> headersMap = headers.toMap();
        addDBSpecificPostHeaders(headersMap);
        String bodyString = writeValueAsString(objectMapper.convertValue(body, SinglePaymentInitiationBody.class));

        DeutscheBankPaymentInitiationResponse response = httpClient.post(
                PAYMENTS_SEPA_CREDIT_TRANSFERS_URI,
                bodyString,
                headersMap,
                postResponseHandler(DeutscheBankPaymentInitiationResponse.class)
        );
        return paymentMapper.toPaymentInitiationRequestResponse(response);
    }

    private void requireSepaCreditTransfer(String paymentProduct) {
        if (!paymentProduct.equalsIgnoreCase("sepa-credit-transfers")) {
            throw new UnsupportedOperationException(paymentProduct);
        }
    }

    @Override
    public SinglePaymentInitiationInformationWithStatusResponse getSinglePaymentInformation(
            String paymentProduct, String paymentId, Headers headers) {
        requireSepaCreditTransfer(paymentProduct);

        String uri = PAYMENTS_SEPA_CREDIT_TRANSFERS_URI + SLASH_SEPARATOR + paymentId;

        Map<String, String> headersMap = headers.toMap();
        addDBSpecificGetHeaders(headersMap);
        return httpClient.get(uri, headersMap,
                              getResponseHandler(SinglePaymentInitiationInformationWithStatusResponse.class));
    }

    @Override
    public PaymentInitiationScaStatusResponse getPaymentInitiationScaStatus(
            String paymentService, String paymentProduct, String paymentId, String authorisationId, Headers headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PaymentInitiationStatus getSinglePaymentInitiationStatus(String paymentProduct, String paymentId,
                                                                    Headers headers) {
        requireSepaCreditTransfer(paymentProduct);
        String uri = PAYMENTS_SEPA_CREDIT_TRANSFERS_URI + SLASH_SEPARATOR + paymentId + "/status";
        Map<String, String> headersMap = headers.toMap();
        addDBSpecificGetHeaders(headersMap);

        return httpClient.get(uri, headersMap, getResponseHandler(PaymentInitiationStatus.class));
    }
}
