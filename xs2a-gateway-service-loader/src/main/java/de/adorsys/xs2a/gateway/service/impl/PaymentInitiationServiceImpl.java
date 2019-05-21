package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.service.*;
import de.adorsys.xs2a.gateway.service.model.*;
import de.adorsys.xs2a.gateway.service.exception.BankNotSupportedException;
import de.adorsys.xs2a.gateway.service.provider.PaymentInitiationServiceProvider;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

public class PaymentInitiationServiceImpl implements PaymentInitiationService {

    @Override
    public GeneralResponse<PaymentInitiationRequestResponse> initiateSinglePayment(String paymentProduct, Object body, RequestHeaders requestHeaders) {
        return getPaymentService(requestHeaders).initiateSinglePayment(paymentProduct, body, requestHeaders);
    }

    @Override
    public GeneralResponse<SinglePaymentInitiationInformationWithStatusResponse> getSinglePaymentInformation(String paymentProduct,
                                                                                            String paymentId,
                                                                                            RequestHeaders requestHeaders) {
        return getPaymentService(requestHeaders).getSinglePaymentInformation(paymentProduct, paymentId, requestHeaders);
    }

    @Override
    public GeneralResponse<PaymentInitiationScaStatusResponse> getPaymentInitiationScaStatus(String paymentService,
                                                                            String paymentProduct,
                                                                            String paymentId,
                                                                            String authorisationId,
                                                                            RequestHeaders requestHeaders) {
        return getPaymentService(requestHeaders).getPaymentInitiationScaStatus(paymentService, paymentProduct, paymentId,
                                                                        authorisationId, requestHeaders);
    }

    @Override
    public GeneralResponse<PaymentInitiationStatus> getSinglePaymentInitiationStatus(String paymentProduct,
                                                                    String paymentId,
                                                                    RequestHeaders requestHeaders) {
        return getPaymentService(requestHeaders).getSinglePaymentInitiationStatus(paymentProduct, paymentId, requestHeaders);
    }

    @Override
    public GeneralResponse<String> getSinglePaymentInitiationStatusAsString(String paymentProduct,
                                                                            String paymentId,
                                                                            RequestHeaders requestHeaders) {
        return getPaymentService(requestHeaders).getSinglePaymentInitiationStatusAsString(paymentProduct, paymentId, requestHeaders);
    }

    @Override
    public GeneralResponse<PaymentInitiationAuthorisationResponse> getPaymentInitiationAuthorisation(String paymentService,
                                                                                    String paymentProduct,
                                                                                    String paymentId,
                                                                                    RequestHeaders requestHeaders) {
        return getPaymentService(requestHeaders).getPaymentInitiationAuthorisation(paymentService, paymentProduct,
                                                                            paymentId, requestHeaders);
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startSinglePaymentAuthorisation(String paymentProduct,
                                                                                    String paymentId,
                                                                                    RequestHeaders requestHeaders,
                                                                                    UpdatePsuAuthentication updatePsuAuthentication) {
        return getPaymentService(requestHeaders).startSinglePaymentAuthorisation(paymentProduct, paymentId,
                requestHeaders, updatePsuAuthentication);
    }

    @Override
    public GeneralResponse<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders, SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return getPaymentService(requestHeaders).updateConsentsPsuData(paymentService, paymentProduct, paymentId, authorisationId, requestHeaders, selectPsuAuthenticationMethod);
    }

    @Override
    public GeneralResponse<ScaStatusResponse> updateConsentsPsuData(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders, TransactionAuthorisation transactionAuthorisation) {
        return getPaymentService(requestHeaders).updateConsentsPsuData(paymentService, paymentProduct, paymentId, authorisationId, requestHeaders, transactionAuthorisation);
    }

    @Override
    public GeneralResponse<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        return getPaymentService(requestHeaders).updateConsentsPsuData(paymentService, paymentProduct, paymentId, authorisationId, requestHeaders, updatePsuAuthentication);
    }

    private PaymentInitiationService getPaymentService(RequestHeaders requestHeaders) {
        String bankCode = requestHeaders.removeBankCode();
        ServiceLoader<PaymentInitiationServiceProvider> loader =
                ServiceLoader.load(PaymentInitiationServiceProvider.class);
        return StreamSupport.stream(loader.spliterator(), false)
                       .filter(pis -> pis.getBankCodes().contains(bankCode))
                       .findFirst().orElseThrow(() -> new BankNotSupportedException(bankCode))
                       .getPaymentInitiationService();
    }

}
