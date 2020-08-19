package de.adorsys.xs2a.adapter.unicredit;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.ContentType;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.AccountList;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.Consents;
import de.adorsys.xs2a.adapter.api.model.UpdatePsuAuthentication;
import de.adorsys.xs2a.adapter.api.validation.ValidationError;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;

import java.util.List;
import java.util.Map;

public class UnicreditAccountInformationService extends BaseAccountInformationService {

    public UnicreditAccountInformationService(Aspsp aspsp,
                                              HttpClient httpClient,
                                              LinksRewriter linksRewriter) {
        super(aspsp, httpClient, linksRewriter);
    }

    @Override
    public Response<AccountList> getAccountList(RequestHeaders requestHeaders, RequestParams requestParams) {
        return super.getAccountList(requestHeaders, RequestParams.builder().build());
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        map.put(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);
        return map;
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
    public List<ValidationError> validateCreateConsent(RequestHeaders requestHeaders,
                                                       RequestParams requestParams,
                                                       Consents body) {
        return UnicreditValidators.requireTppRedirectUri(requestHeaders);
    }



    @Override
    public List<ValidationError> validateStartConsentAuthorisation(String consentId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {
        return UnicreditValidators.requireTppRedirectUri(requestHeaders);
    }

    @Override
    public List<ValidationError> validateStartConsentAuthorisation(String consentId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams,
                                                                   UpdatePsuAuthentication updatePsuAuthentication) {
        return UnicreditValidators.requireTppRedirectUri(requestHeaders);
    }
}
