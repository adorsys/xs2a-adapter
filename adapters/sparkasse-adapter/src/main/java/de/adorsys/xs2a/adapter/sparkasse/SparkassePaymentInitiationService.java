package de.adorsys.xs2a.adapter.sparkasse;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;

public class SparkassePaymentInitiationService extends BasePaymentInitiationService {
    public SparkassePaymentInitiationService(Aspsp aspsp,
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
        if (isXml(paymentProduct)) {
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
