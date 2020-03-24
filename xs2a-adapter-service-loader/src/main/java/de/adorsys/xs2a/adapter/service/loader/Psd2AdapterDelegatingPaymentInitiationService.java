package de.adorsys.xs2a.adapter.service.loader;

import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.psd2.Psd2PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.psd2.model.*;

import java.util.Map;

public class Psd2AdapterDelegatingPaymentInitiationService implements Psd2PaymentInitiationService {

    private final Psd2AdapterServiceLoader adapterServiceLoader;

    public Psd2AdapterDelegatingPaymentInitiationService(Psd2AdapterServiceLoader adapterServiceLoader) {
        this.adapterServiceLoader = adapterServiceLoader;
    }

    @Override
    public Response<PaymentInitiationRequestResponse> initiatePayment(PaymentService paymentService,
                                                                      PaymentProduct paymentProduct,
                                                                      Map<String, String> queryParameters,
                                                                      Map<String, String> headers,
                                                                      PaymentInitiation body) {
        return adapterServiceLoader.getPsd2PaymentInitiationService(RequestHeaders.fromMap(headers))
            .initiatePayment(paymentService, paymentProduct, queryParameters, headers, body);
    }

    @Override
    public Response<PaymentInitiationRequestResponse> initiatePayment(PaymentService paymentService,
                                                                      PaymentProduct paymentProduct,
                                                                      Map<String, String> queryParameters,
                                                                      Map<String, String> headers,
                                                                      String body) {
        return adapterServiceLoader.getPsd2PaymentInitiationService(RequestHeaders.fromMap(headers))
            .initiatePayment(paymentService, paymentProduct, queryParameters, headers, body);
    }

    @Override
    public Response<Object> getPaymentInformation(PaymentService paymentService,
                                                  PaymentProduct paymentProduct,
                                                  String paymentId,
                                                  Map<String, String> queryParameters,
                                                  Map<String, String> headers) {
        return adapterServiceLoader.getPsd2PaymentInitiationService(RequestHeaders.fromMap(headers))
            .getPaymentInformation(paymentService, paymentProduct, paymentId, queryParameters, headers);
    }

    @Override
    public Response<Object> getPaymentInitiationStatus(PaymentService paymentService,
                                                       PaymentProduct paymentProduct,
                                                       String paymentId,
                                                       Map<String, String> queryParameters,
                                                       Map<String, String> headers) {
        return adapterServiceLoader.getPsd2PaymentInitiationService(RequestHeaders.fromMap(headers))
            .getPaymentInitiationStatus(paymentService, paymentProduct, paymentId, queryParameters, headers);
    }

    @Override
    public Response<Authorisations> getPaymentInitiationAuthorisation(PaymentService paymentService,
                                                                      PaymentProduct paymentProduct,
                                                                      String paymentId,
                                                                      Map<String, String> queryParameters,
                                                                      Map<String, String> headers) {
        return adapterServiceLoader.getPsd2PaymentInitiationService(RequestHeaders.fromMap(headers))
            .getPaymentInitiationAuthorisation(paymentService, paymentProduct, paymentId, queryParameters, headers);
    }

    @Override
    public Response<StartScaProcessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                       PaymentProduct paymentProduct,
                                                                       String paymentId,
                                                                       Map<String, String> queryParameters,
                                                                       Map<String, String> headers,
                                                                       UpdateAuthorisation body) {
        return adapterServiceLoader.getPsd2PaymentInitiationService(RequestHeaders.fromMap(headers))
            .startPaymentAuthorisation(paymentService, paymentProduct, paymentId, queryParameters, headers, body);
    }

    @Override
    public Response<ScaStatusResponse> getPaymentInitiationScaStatus(PaymentService paymentService,
                                                                     PaymentProduct paymentProduct,
                                                                     String paymentId,
                                                                     String authorisationId,
                                                                     Map<String, String> queryParameters,
                                                                     Map<String, String> headers) {
        return adapterServiceLoader.getPsd2PaymentInitiationService(RequestHeaders.fromMap(headers))
            .getPaymentInitiationScaStatus(paymentService,
                paymentProduct,
                paymentId,
                authorisationId,
                queryParameters,
                headers);
    }

    @Override
    public Response<UpdateAuthorisationResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                      PaymentProduct paymentProduct,
                                                                      String paymentId,
                                                                      String authorisationId,
                                                                      Map<String, String> queryParameters,
                                                                      Map<String, String> headers,
                                                                      UpdateAuthorisation body) {
        return adapterServiceLoader.getPsd2PaymentInitiationService(RequestHeaders.fromMap(headers))
            .updatePaymentPsuData(paymentService,
                paymentProduct,
                paymentId,
                authorisationId,
                queryParameters,
                headers,
                body);
    }
}
