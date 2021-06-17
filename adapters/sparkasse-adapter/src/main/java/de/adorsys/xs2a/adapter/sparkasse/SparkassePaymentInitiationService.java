package de.adorsys.xs2a.adapter.sparkasse;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.sparkasse.model.SparkassePaymentInitationRequestResponse201;
import de.adorsys.xs2a.adapter.sparkasse.model.SparkasseSelectPsuAuthenticationMethodResponse;
import de.adorsys.xs2a.adapter.sparkasse.model.SparkasseStartScaprocessResponse;
import de.adorsys.xs2a.adapter.sparkasse.model.SparkasseUpdatePsuAuthenticationResponse;
import org.mapstruct.factory.Mappers;

public class SparkassePaymentInitiationService extends BasePaymentInitiationService {

    private final SparkasseMapper sparkasseMapper = Mappers.getMapper(SparkasseMapper.class);

    public SparkassePaymentInitiationService(Aspsp aspsp,
                                             HttpClientFactory httpClientFactory,
                                             LinksRewriter linksRewriter) {
        super(aspsp,
            httpClientFactory.getHttpClient(aspsp.getAdapterId()),
            linksRewriter,
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
    }

    @Override
    public Response<PaymentInitationRequestResponse201> initiatePayment(PaymentService paymentService,
                                                                        PaymentProduct paymentProduct,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams,
                                                                        Object body) {
        if (paymentService == PaymentService.PAYMENTS && isXml(paymentProduct) && body instanceof String) {
            String xml = (String) body;
            body = resolveReqdExctnDt(xml);
        }

        return super.initiatePayment(paymentService,
            paymentProduct,
            requestHeaders,
            requestParams,
            body,
            SparkassePaymentInitationRequestResponse201.class,
            sparkasseMapper::toPaymentInitationRequestResponse201);
    }

    @Override
    public Response<StartScaprocessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                       PaymentProduct paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams) {
        return super.startPaymentAuthorisation(paymentService,
            paymentProduct,
            paymentId,
            requestHeaders,
            requestParams,
            SparkasseStartScaprocessResponse.class,
            sparkasseMapper::toStartScaprocessResponse);
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                          PaymentProduct paymentProduct,
                                                                          String paymentId,
                                                                          String authorisationId,
                                                                          RequestHeaders requestHeaders,
                                                                          RequestParams requestParams,
                                                                          UpdatePsuAuthentication updatePsuAuthentication) {
        return super.updatePaymentPsuData(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            updatePsuAuthentication,
            SparkasseUpdatePsuAuthenticationResponse.class,
            sparkasseMapper::toUpdatePsuAuthenticationResponse);
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                                PaymentProduct paymentProduct,
                                                                                String paymentId,
                                                                                String authorisationId,
                                                                                RequestHeaders requestHeaders,
                                                                                RequestParams requestParams,
                                                                                SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return super.updatePaymentPsuData(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            selectPsuAuthenticationMethod,
            SparkasseSelectPsuAuthenticationMethodResponse.class,
            sparkasseMapper::toSelectPsuAuthenticationMethodResponse);
    }

    private String resolveReqdExctnDt(String body) {
        return body.replaceAll("<ReqdExctnDt>.+</ReqdExctnDt>", "<ReqdExctnDt>1999-01-01</ReqdExctnDt>");
    }
}
