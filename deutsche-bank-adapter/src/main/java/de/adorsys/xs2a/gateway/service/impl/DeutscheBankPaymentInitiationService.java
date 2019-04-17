package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.service.*;
import de.adorsys.xs2a.gateway.service.impl.mapper.PaymentMapper;
import de.adorsys.xs2a.gateway.service.impl.model.DeutscheBankPaymentInitiationResponse;
import org.mapstruct.factory.Mappers;

import java.util.Map;

public class DeutscheBankPaymentInitiationService extends AbstractDeutscheBankService implements PaymentInitiationService {

    private static final String PAYMENTS_SEPA_CREDIT_TRANSFERS_URI = PIS_URI + "payments/sepa-credit-transfers";

    private final PaymentMapper paymentMapper = Mappers.getMapper(PaymentMapper.class);

    @Override
    public PaymentInitiationRequestResponse initiateSinglePayment(String paymentProduct, Object body, Headers headers) {
        requireSepaCreditTransfer(paymentProduct);

        Map<String, String> headersMap = headers.toMap();
        addDBSpecificPostHeaders(headersMap);
        String bodyString = jsonMapper.writeValueAsString(jsonMapper.convertValue(body, SinglePaymentInitiationBody.class));

        DeutscheBankPaymentInitiationResponse response = httpClient.post(
                PAYMENTS_SEPA_CREDIT_TRANSFERS_URI,
                bodyString,
                headersMap,
                responseHandler(DeutscheBankPaymentInitiationResponse.class)
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
        return httpClient.get(uri, headersMap, responseHandler(SinglePaymentInitiationInformationWithStatusResponse.class));
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

        return httpClient.get(uri, headersMap, responseHandler(PaymentInitiationStatus.class));
    }

    @Override
    public PaymentInitiationAuthorisationResponse getPaymentInitiationAuthorisation(String paymentService, String paymentProduct, String paymentId, Headers headers) {
        requireSepaCreditTransfer(paymentProduct);

        Map<String, String> headersMap = headers.toMap();
        addDBSpecificGetHeaders(headersMap);

        // TODO should be updated after Deutsche bank provides the implementation for this endpoint
        throw new UnsupportedOperationException();
    }
}
