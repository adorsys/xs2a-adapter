package de.adorsys.xs2a.adapter.sparkasse;

import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.api.model.PaymentInitationRequestResponse201;
import de.adorsys.xs2a.adapter.api.model.PeriodicPaymentInitiationMultipartBody;
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
            if (body instanceof String) {
                String xml = (String) body;
                body = removeReqdExctnDt(xml);
            } else if (body instanceof PeriodicPaymentInitiationMultipartBody) {
                PeriodicPaymentInitiationMultipartBody multipartBody = (PeriodicPaymentInitiationMultipartBody) body;
                multipartBody.setXml_sct(removeReqdExctnDt((String) multipartBody.getXml_sct()));
            }
        }
        return super.initiatePayment(paymentService, paymentProduct, requestHeaders, requestParams, body);
    }

    private String removeReqdExctnDt(String xml) {
        return xml.replaceAll("\\s*?<ReqdExctnDt>[\\s\\S]*?</ReqdExctnDt>", "");
    }
}
