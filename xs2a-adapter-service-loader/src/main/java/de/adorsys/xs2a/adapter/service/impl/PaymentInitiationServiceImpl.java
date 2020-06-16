package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.loader.AdapterServiceLoader;

public class PaymentInitiationServiceImpl implements PaymentInitiationService {
    private final AdapterServiceLoader adapterServiceLoader;

    public PaymentInitiationServiceImpl(AdapterServiceLoader adapterServiceLoader) {
        this.adapterServiceLoader = adapterServiceLoader;
    }

    @Override
    public Response<PaymentInitationRequestResponse201> initiateSinglePayment(String paymentProduct,
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
    public Response<PaymentInitiationWithStatusResponse> getSinglePaymentInformation(String paymentProduct,
                                                                                     String paymentId,
                                                                                     RequestHeaders requestHeaders,
                                                                                     RequestParams requestParams) {
        return getPaymentInitiationService(requestHeaders)
                   .getSinglePaymentInformation(paymentProduct, paymentId, requestHeaders, requestParams);
    }

    @Override
    public Response<String> getSinglePaymentInformationAsString(String paymentProduct,
                                                                String paymentId,
                                                                RequestHeaders requestHeaders,
                                                                RequestParams requestParams) {
        return getPaymentInitiationService(requestHeaders)
            .getSinglePaymentInformationAsString(paymentProduct, paymentId, requestHeaders, requestParams);
    }

    @Override
    public Response<ScaStatusResponse> getPaymentInitiationScaStatus(String paymentService,
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
    public Response<PaymentInitiationStatusResponse200Json> getSinglePaymentInitiationStatus(String paymentProduct,
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
    public Response<Authorisations> getPaymentInitiationAuthorisation(String paymentService,
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
    public Response<StartScaprocessResponse> startSinglePaymentAuthorisation(String paymentProduct,
                                                                             String paymentId,
                                                                             RequestHeaders requestHeaders,
                                                                             RequestParams requestParams) {
        return getPaymentInitiationService(requestHeaders)
            .startSinglePaymentAuthorisation(paymentProduct, paymentId, requestHeaders, requestParams);
    }

    @Override
    public Response<StartScaprocessResponse> startSinglePaymentAuthorisation(String paymentProduct,
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
