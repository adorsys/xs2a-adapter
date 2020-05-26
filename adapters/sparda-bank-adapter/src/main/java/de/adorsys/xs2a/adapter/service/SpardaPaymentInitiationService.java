package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.adapter.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.adapter.StandardPaymentProduct;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.PaymentInitiationRequestResponse;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.paymentInitiationResponseHandler;
import static java.util.function.Function.identity;

public class SpardaPaymentInitiationService extends BasePaymentInitiationService {

    public SpardaPaymentInitiationService(Aspsp aspsp,
                                          HttpClient httpClient,
                                          LinksRewriter linksRewriter) {
        super(aspsp, httpClient, linksRewriter);
    }

    @Override
    public Response<PaymentInitiationRequestResponse> initiateSinglePayment(String paymentProduct,
                                                                            RequestHeaders requestHeaders,
                                                                            RequestParams requestParams,
                                                                            Object body) {
        return initiateSinglePayment(StandardPaymentProduct.fromSlug(paymentProduct),
            body,
            requestHeaders,
            requestParams,
            identity(),
            paymentInitiationResponseHandler(getIdpUri(), PaymentInitiationRequestResponse.class));
    }
}
