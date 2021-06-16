package de.adorsys.xs2a.adapter.unicredit;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.ContentType;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.api.validation.ValidationError;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;

import java.util.List;
import java.util.Map;

public class UnicreditPaymentInitiationService extends BasePaymentInitiationService {

    private static final String DEFAULT_COUNTRY_CODE = "DE";

    public UnicreditPaymentInitiationService(Aspsp aspsp,
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
        Object requestBody = body;
        if (!isXml(paymentProduct)) {
            Class<?> paymentBodyClass = getPaymentInitiationBodyClass(paymentService);
            requestBody = jsonMapper.convertValue(body, paymentBodyClass);
            addCreditorAddress(requestBody);
        }

        return super.initiatePayment(paymentService, paymentProduct, requestHeaders, requestParams, requestBody);
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

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populatePostHeaders(map);
        headers.put(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);

        return headers;
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        headers.put(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);
        return headers;
    }

    @Override
    protected Map<String, String> addPsuIdTypeHeader(Map<String, String> headers) {
        return UnicreditHeaders.addPsuIdTypeHeader(headers);
    }

    @Override
    public List<ValidationError> validateInitiatePayment(PaymentService paymentService,
                                                         PaymentProduct paymentProduct,
                                                         RequestHeaders requestHeaders,
                                                         RequestParams requestParams,
                                                         Object body) {
        return UnicreditValidators.requireTppRedirectUri(requestHeaders);
    }

    @Override
    public List<ValidationError> validateStartPaymentAuthorisation(PaymentService paymentService,
                                                                   PaymentProduct paymentProduct,
                                                                   String paymentId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {
        return UnicreditValidators.requireTppRedirectUri(requestHeaders);
    }

    @Override
    public List<ValidationError> validateStartPaymentAuthorisation(PaymentService paymentService,
                                                                   PaymentProduct paymentProduct,
                                                                   String paymentId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams,
                                                                   UpdatePsuAuthentication updatePsuAuthentication) {
        return UnicreditValidators.requireTppRedirectUri(requestHeaders);
    }
}
