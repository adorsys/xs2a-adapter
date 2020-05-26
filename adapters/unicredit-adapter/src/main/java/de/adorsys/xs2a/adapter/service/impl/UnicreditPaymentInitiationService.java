package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.adapter.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.http.ContentType;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.UpdatePsuAuthentication;
import de.adorsys.xs2a.adapter.validation.ValidationError;

import java.util.List;
import java.util.Map;

public class UnicreditPaymentInitiationService extends BasePaymentInitiationService {

    public UnicreditPaymentInitiationService(Aspsp aspsp,
                                             HttpClient httpClient,
                                             LinksRewriter linksRewriter) {
        super(aspsp, httpClient, linksRewriter);
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
    public List<ValidationError> validateInitiateSinglePayment(String paymentProduct,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams,
                                                               Object body) {
        return UnicreditValidators.requireTppRedirectUri(requestHeaders);
    }

    @Override
    public List<ValidationError> validateStartSinglePaymentAuthorisation(String paymentProduct,
                                                                         String paymentId,
                                                                         RequestHeaders requestHeaders,
                                                                         RequestParams requestParams) {
        return UnicreditValidators.requireTppRedirectUri(requestHeaders);
    }

    @Override
    public List<ValidationError> validateStartSinglePaymentAuthorisation(String paymentProduct,
                                                                         String paymentId,
                                                                         RequestHeaders requestHeaders,
                                                                         RequestParams requestParams,
                                                                         UpdatePsuAuthentication updatePsuAuthentication) {
        return UnicreditValidators.requireTppRedirectUri(requestHeaders);
    }
}
