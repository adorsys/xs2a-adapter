package de.adorsys.xs2a.adapter.consors;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.PaymentInitationRequestResponse201;
import de.adorsys.xs2a.adapter.api.model.PaymentProduct;
import de.adorsys.xs2a.adapter.api.model.PaymentService;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;

import java.util.Map;

import static de.adorsys.xs2a.adapter.api.RequestHeaders.PSU_ID;

public class ConsorsPaymentInitiationService extends BasePaymentInitiationService {

    public ConsorsPaymentInitiationService(Aspsp aspsp,
                                           HttpClient httpClient,
                                           LinksRewriter linksRewriter) {
        super(aspsp, httpClient, linksRewriter);
    }

    @Override
    public Response<PaymentInitationRequestResponse201> initiatePayment(PaymentService paymentService,
                                                                        PaymentProduct paymentProduct,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams,
                                                                        Object body) {
        Map<String, String> checkedHeader = removePsuIdHeader(requestHeaders.toMap());

        return super.initiatePayment(paymentService, paymentProduct, RequestHeaders.fromMap(checkedHeader), requestParams, body);
    }

    private Map<String, String> removePsuIdHeader(Map<String, String> headers) {
        headers.remove(PSU_ID);
        return headers;
    }
}
