package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.loader.AdapterServiceLoader;
import de.adorsys.xs2a.adapter.service.model.*;

public class PaymentInitiationServiceImpl implements PaymentInitiationService {
    private final AdapterServiceLoader adapterServiceLoader;

    public PaymentInitiationServiceImpl(AdapterServiceLoader adapterServiceLoader) {
        this.adapterServiceLoader = adapterServiceLoader;
    }

    @Override
    public Response<PaymentInitiationRequestResponse> initiatePayment(String paymentService,
                                                                      String paymentProduct,
                                                                      RequestHeaders requestHeaders,
                                                                      RequestParams requestParams,
                                                                      Object body) {
        return getPaymentInitiationService(requestHeaders)
                   .initiatePayment(paymentService, paymentProduct, requestHeaders, requestParams, body);
    }

    private PaymentInitiationService getPaymentInitiationService(RequestHeaders requestHeaders) {
        return adapterServiceLoader.getPaymentInitiationService(requestHeaders);
    }

    @Override
    public Response<SinglePaymentInitiationInformationWithStatusResponse> getSinglePaymentInformation(String paymentProduct,
                                                                                                      String paymentId,
                                                                                                      RequestHeaders requestHeaders,
                                                                                                      RequestParams requestParams) {
        return getPaymentInitiationService(requestHeaders)
                   .getSinglePaymentInformation(paymentProduct, paymentId, requestHeaders, requestParams);
    }

    @Override
    public Response<PeriodicPaymentInitiationInformationWithStatusResponse> getPeriodicPaymentInformation(String paymentProduct,
                                                                                                          String paymentId,
                                                                                                          RequestHeaders requestHeaders,
                                                                                                          RequestParams requestParams) {
        return getPaymentInitiationService(requestHeaders)
                   .getPeriodicPaymentInformation(paymentProduct, paymentId, requestHeaders, requestParams);
    }

    @Override
    public Response<String> getPaymentInformationAsString(String paymentService,
                                                          String paymentProduct,
                                                          String paymentId,
                                                          RequestHeaders requestHeaders,
                                                          RequestParams requestParams) {
        return getPaymentInitiationService(requestHeaders)
                   .getPaymentInformationAsString(paymentService, paymentProduct, paymentId, requestHeaders, requestParams);
    }

    @Override
    public Response<PaymentInitiationScaStatusResponse> getPaymentInitiationScaStatus(String paymentService,
                                                                                      String paymentProduct,
                                                                                      String paymentId,
                                                                                      String authorisationId,
                                                                                      RequestHeaders requestHeaders,
                                                                                      RequestParams requestParams) {
        return getPaymentInitiationService(requestHeaders)
            .getPaymentInitiationScaStatus(paymentService,
                paymentProduct,
                paymentId,
                authorisationId,
                requestHeaders,
                requestParams);
    }

    @Override
    public Response<PaymentInitiationStatus> getPaymentInitiationStatus(String paymentService, String paymentProduct, String paymentId, RequestHeaders requestHeaders, RequestParams requestParams) {
        return getPaymentInitiationService(requestHeaders)
                   .getPaymentInitiationStatus(paymentService, paymentProduct, paymentId, requestHeaders, requestParams);
    }

    @Override
    public Response<String> getPaymentInitiationStatusAsString(String paymentService,
                                                               String paymentProduct,
                                                               String paymentId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        return getPaymentInitiationService(requestHeaders)
                   .getPaymentInitiationStatusAsString(paymentService, paymentProduct, paymentId, requestHeaders,
                       requestParams);
    }

    @Override
    public Response<PaymentInitiationAuthorisationResponse> getPaymentInitiationAuthorisation(String paymentService,
                                                                                              String paymentProduct,
                                                                                              String paymentId,
                                                                                              RequestHeaders requestHeaders,
                                                                                              RequestParams requestParams) {
        return getPaymentInitiationService(requestHeaders)
            .getPaymentInitiationAuthorisation(paymentService,
                paymentProduct,
                paymentId,
                requestHeaders,
                requestParams);
    }

    @Override
    public Response<StartScaProcessResponse> startPaymentAuthorisation(String paymentService,
                                                                       String paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams) {
        return getPaymentInitiationService(requestHeaders)
                   .startPaymentAuthorisation(paymentService, paymentProduct, paymentId, requestHeaders, requestParams);
    }

    @Override
    public Response<StartScaProcessResponse> startPaymentAuthorisation(String paymentService,
                                                                       String paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams,
                                                                       UpdatePsuAuthentication updatePsuAuthentication) {
        return getPaymentInitiationService(requestHeaders)
                   .startPaymentAuthorisation(paymentService, paymentProduct, paymentId, requestHeaders,
                       requestParams, updatePsuAuthentication);
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(String paymentService,
                                                                                String paymentProduct,
                                                                                String paymentId,
                                                                                String authorisationId,
                                                                                RequestHeaders requestHeaders,
                                                                                RequestParams requestParams,
                                                                                SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return getPaymentInitiationService(requestHeaders)
            .updatePaymentPsuData(paymentService,
                paymentProduct,
                paymentId,
                authorisationId,
                requestHeaders,
                requestParams,
                selectPsuAuthenticationMethod);
    }

    @Override
    public Response<ScaStatusResponse> updatePaymentPsuData(String paymentService,
                                                            String paymentProduct,
                                                            String paymentId,
                                                            String authorisationId,
                                                            RequestHeaders requestHeaders,
                                                            RequestParams requestParams,
                                                            TransactionAuthorisation transactionAuthorisation) {
        return getPaymentInitiationService(requestHeaders)
            .updatePaymentPsuData(paymentService,
                paymentProduct,
                paymentId,
                authorisationId,
                requestHeaders,
                requestParams,
                transactionAuthorisation);
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(String paymentService,
                                                                          String paymentProduct,
                                                                          String paymentId,
                                                                          String authorisationId,
                                                                          RequestHeaders requestHeaders,
                                                                          RequestParams requestParams,
                                                                          UpdatePsuAuthentication updatePsuAuthentication) {
        return getPaymentInitiationService(requestHeaders)
            .updatePaymentPsuData(paymentService,
                paymentProduct,
                paymentId,
                authorisationId,
                requestHeaders,
                requestParams,
                updatePsuAuthentication);
    }
}
