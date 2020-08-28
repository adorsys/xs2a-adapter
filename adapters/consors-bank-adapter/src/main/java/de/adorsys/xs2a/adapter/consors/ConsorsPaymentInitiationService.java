package de.adorsys.xs2a.adapter.consors;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.PaymentInitationRequestResponse201;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.function.Function;

import static de.adorsys.xs2a.adapter.api.RequestHeaders.PSU_ID;

public class ConsorsPaymentInitiationService extends BasePaymentInitiationService {

    public ConsorsPaymentInitiationService(Aspsp aspsp,
                                           HttpClient httpClient,
                                           LinksRewriter linksRewriter) {
        super(aspsp, httpClient, linksRewriter);
    }

    @Override
    protected <T> Response<PaymentInitationRequestResponse201> initiatePayment(String paymentService,
                                                                               String paymentProduct,
                                                                               Object body,
                                                                               RequestHeaders requestHeaders,
                                                                               RequestParams requestParams,
                                                                               Function<T, PaymentInitationRequestResponse201> mapper,
                                                                               HttpClient.ResponseHandler<T> responseHandler) {

        Map<String, String> checkedHeaders = addPsuIdHeader(requestHeaders.toMap());

        return super.initiatePayment(paymentService, paymentProduct, body, RequestHeaders.fromMap(checkedHeaders), requestParams, mapper, responseHandler);
    }

    @Override
    protected Map<String, String> addPsuIdHeader(Map<String, String> headers) {
        if (headers.containsKey(PSU_ID) && StringUtils.isNotBlank(headers.get(PSU_ID))) {
            headers.put(PSU_ID, "");
        }

        return headers;
    }
}
