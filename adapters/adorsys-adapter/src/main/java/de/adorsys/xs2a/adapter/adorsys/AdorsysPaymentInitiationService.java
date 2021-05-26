package de.adorsys.xs2a.adapter.adorsys;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;

import java.util.List;

public class AdorsysPaymentInitiationService extends BasePaymentInitiationService {

    private static final String DEFAULT_COUNTRY_CODE = "DE";

    public AdorsysPaymentInitiationService(Aspsp aspsp,
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
        Object requestBody = null;
        if (!isXml(paymentProduct)) {
            Class<?> paymentBodyClass = getPaymentInitiationBodyClass(paymentService);
            requestBody = jsonMapper.convertValue(body, paymentBodyClass);
            addCreditorAddress(requestBody);
        }

        return super.initiatePayment(paymentService, paymentProduct, requestHeaders, requestParams, requestBody == null ? body : requestBody);
    }

    private void addCreditorAddress(Object body) {
        if (body instanceof PaymentInitiationJson) {
            PaymentInitiationJson paymentsJson = (PaymentInitiationJson) body;
            if (paymentsJson.getCreditorAddress() == null) {
                paymentsJson.setCreditorAddress(buildDefaultAddress());
            }
        } else if (body instanceof PeriodicPaymentInitiationJson) {
            PeriodicPaymentInitiationJson periodicJson = (PeriodicPaymentInitiationJson) body;
            if (periodicJson.getCreditorAddress() == null) {
                periodicJson.setCreditorAddress(buildDefaultAddress());
            }
        }
    }

    private Address buildDefaultAddress() {
        Address address = new Address();
        address.setCountry(DEFAULT_COUNTRY_CODE);
        return address;
    }
}
