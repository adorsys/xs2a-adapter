package de.adorsys.xs2a.adapter.fiducia;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.fiducia.mapper.FiduciaMapper;
import de.adorsys.xs2a.adapter.fiducia.model.*;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class FiduciaPaymentInitiationService extends BasePaymentInitiationService {

    private final FiduciaMapper mapper = Mappers.getMapper(FiduciaMapper.class);

    public FiduciaPaymentInitiationService(Aspsp aspsp,
                                           HttpClientFactory httpClientFactory,
                                           List<Interceptor> interceptors,
                                           LinksRewriter linksRewriter) {
        super(aspsp,
            httpClientFactory.getHttpClient(aspsp.getAdapterId()),
            interceptors,
            linksRewriter,
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
    }

    @Override
    public Response<PaymentInitationRequestResponse201> initiatePayment(PaymentService paymentService,
                                                                        PaymentProduct paymentProduct,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams,
                                                                        Object body) {
        if (body instanceof PeriodicPaymentInitiationJson) {
            body = mapper.toFiduciaPeriodicPaymentInitiationJson((PeriodicPaymentInitiationJson) body);
        }
        return initiatePayment(paymentService,
                               paymentProduct,
                               requestHeaders,
                               requestParams,
                               body,
                               FiduciaPaymentInitationRequestResponse201.class,
                               mapper::toPaymentInitationRequestResponse201);
    }

    @Override
    protected Class<?> getPaymentInitiationBodyClass(PaymentService paymentService) {
        if (paymentService == PaymentService.PERIODIC_PAYMENTS) {
            return FiduciaPeriodicPaymentInitiationJson.class;
        }
        return super.getPaymentInitiationBodyClass(paymentService);
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                                PaymentProduct paymentProduct,
                                                                                String paymentId,
                                                                                String authorisationId,
                                                                                RequestHeaders requestHeaders,
                                                                                RequestParams requestParams,
                                                                                SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return updatePaymentPsuData(paymentService,
                                    paymentProduct,
                                    paymentId,
                                    authorisationId,
                                    requestHeaders,
                                    requestParams,
                                    selectPsuAuthenticationMethod,
                                    FiduciaSelectPsuAuthenticationMethodResponse.class,
                                    mapper::toSelectPsuAuthenticationMethodResponse);
    }

    @Override
    public Response<StartScaprocessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                       PaymentProduct paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams,
                                                                       UpdatePsuAuthentication updatePsuAuthentication) {
        return super.startPaymentAuthorisation(paymentService,
                                               paymentProduct,
                                               paymentId,
                                               requestHeaders,
                                               requestParams,
                                               updatePsuAuthentication,
                                               FiduciaStartScaProcessResponse.class,
                                               mapper::toStartScaProcessResponse);
    }

    @Override
    public Response<StartScaprocessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                       PaymentProduct paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams) {
        return startPaymentAuthorisation(paymentService,
                                         paymentProduct,
                                         paymentId,
                                         requestHeaders,
                                         requestParams,
                                         FiduciaStartScaProcessResponse.class,
                                         mapper::toStartScaProcessResponse);
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                          PaymentProduct paymentProduct,
                                                                          String paymentId,
                                                                          String authorisationId,
                                                                          RequestHeaders requestHeaders,
                                                                          RequestParams requestParams,
                                                                          UpdatePsuAuthentication updatePsuAuthentication) {
        return updatePaymentPsuData(paymentService,
                                    paymentProduct,
                                    paymentId,
                                    authorisationId,
                                    requestHeaders,
                                    requestParams,
                                    updatePsuAuthentication,
                                    FiduciaUpdatePsuAuthenticationResponse.class,
                                    mapper::toUpdatePsuAuthenticationResponse);
    }

    @Override
    public Response<PeriodicPaymentInitiationWithStatusResponse> getPeriodicPaymentInformation(PaymentProduct paymentProduct,
                                                                                               String paymentId,
                                                                                               RequestHeaders requestHeaders,
                                                                                               RequestParams requestParams) {
        return getPeriodicPaymentInformation(paymentProduct,
                                             paymentId,
                                             requestHeaders,
                                             requestParams,
                                             FiduciaPeriodicPaymentInitiationWithStatusResponse.class,
                                             mapper::toPeriodicPaymentInitiationWithStatusResponse);
    }

    @Override
    public Response<PeriodicPaymentInitiationMultipartBody> getPeriodicPain001PaymentInformation(PaymentProduct paymentProduct,
                                                                                                 String paymentId,
                                                                                                 RequestHeaders requestHeaders,
                                                                                                 RequestParams requestParams) {
        return getPeriodicPain001PaymentInformation(paymentProduct,
                                                    paymentId,
                                                    requestHeaders,
                                                    requestParams,
                                                    FiduciaPeriodicPaymentInitiationMultipartBody.class,
                                                    mapper::toPeriodicPaymentInitiationMultipartBody);
    }
}
