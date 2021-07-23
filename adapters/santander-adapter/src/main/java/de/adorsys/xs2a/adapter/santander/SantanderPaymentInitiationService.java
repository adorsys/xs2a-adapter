package de.adorsys.xs2a.adapter.santander;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.api.validation.ValidationError;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;

import java.util.List;
import java.util.Map;

public class SantanderPaymentInitiationService extends BasePaymentInitiationService {
    private final SantanderOauth2Service oauth2Service;

    private final SantanderRequestHandler requestHandler = new SantanderRequestHandler();

    public SantanderPaymentInitiationService(Aspsp aspsp,
                                             HttpClientFactory httpClientFactory,
                                             LinksRewriter linksRewriter,
                                             SantanderOauth2Service oauth2Service) {
        super(aspsp,
            SantanderHttpClientFactory.getHttpClient(aspsp.getAdapterId(), httpClientFactory),
            linksRewriter,
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
        this.oauth2Service = oauth2Service;
    }

    @Override
    public Response<PaymentInitationRequestResponse201> initiatePayment(PaymentService paymentService,
                                                                        PaymentProduct paymentProduct,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams,
                                                                        Object body) {
        Map<String, String> updatedHeaders = addBearerHeader(requestHeaders.toMap());

        return super.initiatePayment(paymentService, paymentProduct, RequestHeaders.fromMap(updatedHeaders), requestParams, body);
    }

    @Override
    public Response<PaymentInitiationStatusResponse200Json> getPaymentInitiationStatus(PaymentService paymentService,
                                                                                       PaymentProduct paymentProduct,
                                                                                       String paymentId,
                                                                                       RequestHeaders requestHeaders,
                                                                                       RequestParams requestParams) {
        requestHandler.validateRequest(requestHeaders);

        return super.getPaymentInitiationStatus(paymentService, paymentProduct, paymentId, requestHeaders, requestParams);
    }

    @Override
    public List<ValidationError> validateGetPaymentInitiationStatus(PaymentService paymentService,
                                                                    PaymentProduct paymentProduct,
                                                                    String paymentId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {
        Map<String, String> headers = requestHeaders.toMap();

        if (!headers.containsKey(RequestHeaders.PSU_IP_ADDRESS)) {
            return List.of(ValidationError.required(RequestHeaders.PSU_IP_ADDRESS));
        }
        return List.of();
    }

    @Override
    public Response<PaymentInitiationWithStatusResponse> getSinglePaymentInformation(PaymentProduct paymentProduct,
                                                                                     String paymentId,
                                                                                     RequestHeaders requestHeaders,
                                                                                     RequestParams requestParams) {
        requestHandler.validateRequest(requestHeaders);

        return super.getSinglePaymentInformation(paymentProduct, paymentId, requestHeaders, requestParams);
    }

    @Override
    public Response<PeriodicPaymentInitiationWithStatusResponse> getPeriodicPaymentInformation(PaymentProduct paymentProduct,
                                                                                               String paymentId,
                                                                                               RequestHeaders requestHeaders,
                                                                                               RequestParams requestParams) {
        requestHandler.validateRequest(requestHeaders);

        return super.getPeriodicPaymentInformation(paymentProduct, paymentId, requestHeaders, requestParams);
    }

    @Override
    public Response<Authorisations> getPaymentInitiationAuthorisation(PaymentService paymentService,
                                                                      PaymentProduct paymentProduct,
                                                                      String paymentId,
                                                                      RequestHeaders requestHeaders,
                                                                      RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<StartScaprocessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                       PaymentProduct paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<StartScaprocessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                       PaymentProduct paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams,
                                                                       UpdatePsuAuthentication updatePsuAuthentication) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                          PaymentProduct paymentProduct,
                                                                          String paymentId,
                                                                          String authorisationId,
                                                                          RequestHeaders requestHeaders,
                                                                          RequestParams requestParams,
                                                                          UpdatePsuAuthentication updatePsuAuthentication) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                                PaymentProduct paymentProduct,
                                                                                String paymentId,
                                                                                String authorisationId,
                                                                                RequestHeaders requestHeaders,
                                                                                RequestParams requestParams,
                                                                                SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<ScaStatusResponse> updatePaymentPsuData(PaymentService paymentService,
                                                            PaymentProduct paymentProduct,
                                                            String paymentId,
                                                            String authorisationId,
                                                            RequestHeaders requestHeaders,
                                                            RequestParams requestParams,
                                                            TransactionAuthorisation transactionAuthorisation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<ScaStatusResponse> getPaymentInitiationScaStatus(PaymentService paymentService,
                                                                     PaymentProduct paymentProduct,
                                                                     String paymentId,
                                                                     String authorisationId,
                                                                     RequestHeaders requestHeaders,
                                                                     RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }



    private Map<String, String> addBearerHeader(Map<String, String> headers) {
        headers.put("Authorization", "Bearer " + oauth2Service.getClientCredentialsAccessToken());
        return headers;
    }
}
