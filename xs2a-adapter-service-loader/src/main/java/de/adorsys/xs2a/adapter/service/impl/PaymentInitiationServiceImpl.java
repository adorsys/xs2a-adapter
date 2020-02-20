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
    public Response<PaymentInitiationRequestResponse> initiateSinglePayment(String paymentProduct,
                                                                            RequestHeaders requestHeaders,
                                                                            RequestParams requestParams,
                                                                            Object body) {
        return getPaymentInitiationService(requestHeaders)
                   .initiateSinglePayment(paymentProduct, requestHeaders, requestParams, body);
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
    public Response<PaymentInitiationStatus> getSinglePaymentInitiationStatus(String paymentProduct,
                                                                              String paymentId,
                                                                              RequestHeaders requestHeaders,
                                                                              RequestParams requestParams) {
        return getPaymentInitiationService(requestHeaders)
                   .getSinglePaymentInitiationStatus(paymentProduct, paymentId, requestHeaders, requestParams);
    }

    @Override
    public Response<String> getSinglePaymentInitiationStatusAsString(String paymentProduct,
                                                                     String paymentId,
                                                                     RequestHeaders requestHeaders,
                                                                     RequestParams requestParams) {
        return getPaymentInitiationService(requestHeaders)
                   .getSinglePaymentInitiationStatusAsString(paymentProduct, paymentId, requestHeaders, requestParams);
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
    public Response<StartScaProcessResponse> startSinglePaymentAuthorisation(String paymentProduct,
                                                                             String paymentId,
                                                                             RequestHeaders requestHeaders,
                                                                             RequestParams requestParams,
                                                                             UpdatePsuAuthentication updatePsuAuthentication) {
        return getPaymentInitiationService(requestHeaders)
            .startSinglePaymentAuthorisation(paymentProduct,
                paymentId,
                requestHeaders,
                requestParams,
                updatePsuAuthentication);
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
