package de.adorsys.xs2a.adapter.sparkasse;

import de.adorsys.xs2a.adapter.adapter.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.api.model.PaymentInitationRequestResponse201;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;

public class SparkassePaymentInitiationService extends BasePaymentInitiationService {
    public SparkassePaymentInitiationService(Aspsp aspsp,
                                             HttpClient httpClient,
                                             LinksRewriter linksRewriter) {
        super(aspsp, httpClient, linksRewriter);
    }

    @Override
    public Response<PaymentInitationRequestResponse201> initiatePayment(String paymentService,
                                                                        String paymentProduct,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams,
                                                                        Object body) {
        if (paymentProduct.startsWith("pain.001")) {
            String xml = (String) body;
            body = xml.replaceAll("\\s*?<ReqdExctnDt>[\\s\\S]*?</ReqdExctnDt>", "");
        }
        return super.initiatePayment(paymentService, paymentProduct, requestHeaders, requestParams, body);
    }
}
