package de.adorsys.xs2a.adapter.service.psd2;

import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.psd2.model.*;

import java.util.Map;

public interface Psd2PaymentInitiationService {
    Response<PaymentInitiationRequestResponse> initiatePayment(PaymentService paymentService,
                                                               PaymentProduct paymentProduct,
                                                               Map<String, String> queryParameters,
                                                               Map<String, String> headers,
                                                               PaymentInitiation body);

    Response<PaymentInitiationRequestResponse> initiatePayment(PaymentService paymentService,
                                                               PaymentProduct paymentProduct,
                                                               Map<String, String> queryParameters,
                                                               Map<String, String> headers,
                                                               String body);

    Response<Object> getPaymentInformation(PaymentService paymentService,
                                           PaymentProduct paymentProduct,
                                           String paymentId,
                                           Map<String, String> queryParameters,
                                           Map<String, String> headers);

    Response<Object> getPaymentInitiationStatus(PaymentService paymentService,
                                                PaymentProduct paymentProduct,
                                                String paymentId,
                                                Map<String, String> queryParameters,
                                                Map<String, String> headers);

    Response<Authorisations> getPaymentInitiationAuthorisation(PaymentService paymentService,
                                                               PaymentProduct paymentProduct,
                                                               String paymentId,
                                                               Map<String, String> queryParameters,
                                                               Map<String, String> headers);

    Response<StartScaProcessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                PaymentProduct paymentProduct,
                                                                String paymentId,
                                                                Map<String, String> queryParameters,
                                                                Map<String, String> headers,
                                                                UpdateAuthorisation body);

    Response<ScaStatusResponse> getPaymentInitiationScaStatus(PaymentService paymentService,
                                                              PaymentProduct paymentProduct,
                                                              String paymentId,
                                                              String authorisationId,
                                                              Map<String, String> queryParameters,
                                                              Map<String, String> headers);

    Response<UpdateAuthorisationResponse> updatePaymentPsuData(PaymentService paymentService,
                                                               PaymentProduct paymentProduct,
                                                               String paymentId,
                                                               String authorisationId,
                                                               Map<String, String> queryParameters,
                                                               Map<String, String> headers,
                                                               UpdateAuthorisation body);
}
