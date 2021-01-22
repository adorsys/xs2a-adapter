package de.adorsys.xs2a.adapter.sparkasse;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.PaymentInitationRequestResponse201;
import de.adorsys.xs2a.adapter.api.model.PaymentProduct;
import de.adorsys.xs2a.adapter.api.model.PaymentService;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;

public class SparkassePaymentInitiationService extends BasePaymentInitiationService {
    public SparkassePaymentInitiationService(Aspsp aspsp,
                                             HttpClient httpClient,
                                             LinksRewriter linksRewriter,
                                             HttpLogSanitizer logSanitizer) {
        super(aspsp, httpClient, linksRewriter, logSanitizer);
    }

    @Override
    public Response<PaymentInitationRequestResponse201> initiatePayment(PaymentService paymentService,
                                                                        PaymentProduct paymentProduct,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams,
                                                                        Object body) {
        if (paymentService == PaymentService.PAYMENTS && isXml(paymentProduct)) {
            if (body instanceof String) {
                String xml = (String) body;
                body = resolveReqdExctnDt(xml);
            }
        }

        return super.initiatePayment(paymentService, paymentProduct, requestHeaders, requestParams, body);
    }

    private String resolveReqdExctnDt(String body) {
        return body.replaceAll("<ReqdExctnDt>.+</ReqdExctnDt>", "<ReqdExctnDt>1999-01-01</ReqdExctnDt>");
    }
}
