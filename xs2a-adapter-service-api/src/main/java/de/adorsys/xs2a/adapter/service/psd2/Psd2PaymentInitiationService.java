package de.adorsys.xs2a.adapter.service.psd2;

import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.psd2.model.*;

import java.io.IOException;
import java.util.Map;

public interface Psd2PaymentInitiationService {
    Response<PaymentInitiationRequestResponse> initiatePayment(PaymentService paymentService,
                                                               PaymentProduct paymentProduct,
                                                               Map<String, String> queryParameters,
                                                               Map<String, String> headers,
                                                               PaymentInitiation body) throws IOException;

    Response<PaymentInitiationRequestResponse> initiatePayment(PaymentService paymentService,
                                                               PaymentProduct paymentProduct,
                                                               Map<String, String> queryParameters,
                                                               Map<String, String> headers,
                                                               String body) throws IOException;

    Response<Object> getPaymentInformation(PaymentService paymentService,
                                           PaymentProduct paymentProduct,
                                           String paymentId,
                                           Map<String, String> queryParameters,
                                           Map<String, String> headers) throws IOException;

    Response<Object> getPaymentInitiationStatus(PaymentService paymentService,
                                                PaymentProduct paymentProduct,
                                                String paymentId,
                                                Map<String, String> queryParameters,
                                                Map<String, String> headers) throws IOException;

    Response<Authorisations> getPaymentInitiationAuthorisation(PaymentService paymentService,
                                                               PaymentProduct paymentProduct,
                                                               String paymentId,
                                                               Map<String, String> queryParameters,
                                                               Map<String, String> headers) throws IOException;

    Response<StartScaProcessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                PaymentProduct paymentProduct,
                                                                String paymentId,
                                                                Map<String, String> queryParameters,
                                                                Map<String, String> headers,
                                                                UpdateAuthorisation body) throws IOException;

    Response<ScaStatusResponse> getPaymentInitiationScaStatus(PaymentService paymentService,
                                                              PaymentProduct paymentProduct,
                                                              String paymentId,
                                                              String authorisationId,
                                                              Map<String, String> queryParameters,
                                                              Map<String, String> headers) throws IOException;

    Response<UpdateAuthorisationResponse> updatePaymentPsuData(PaymentService paymentService,
                                                               PaymentProduct paymentProduct,
                                                               String paymentId,
                                                               String authorisationId,
                                                               Map<String, String> queryParameters,
                                                               Map<String, String> headers,
                                                               UpdateAuthorisation body) throws IOException;
}
