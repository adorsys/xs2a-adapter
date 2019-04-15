package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.service.Headers;
import de.adorsys.xs2a.gateway.service.RequestParams;
import de.adorsys.xs2a.gateway.service.account.AccountListHolder;
import de.adorsys.xs2a.gateway.service.account.AccountService;

import java.util.Map;
import java.util.stream.Collectors;

import static de.adorsys.xs2a.gateway.service.Headers.CONSENT_ID;
import static de.adorsys.xs2a.gateway.service.Headers.RESOURCE_ID;

public class DeutscheBankAccountService extends AbstractDeutscheBankService implements AccountService {
    private static final String ACCOUNTS_URI = AIS_URI + "accounts";

    @Override
    public AccountListHolder getAccountList(Headers headers, RequestParams requestParams) {
        Map<String, String> headersMap = headers.toMap();
        addDBSpecificGetHeaders(headersMap);
        adaptConsentIdHeaderForDB(headersMap);

        String uri = buildUri(ACCOUNTS_URI, requestParams);

        return httpClient.get(uri, headersMap, getResponseHandler(AccountListHolder.class));
    }

    private void adaptConsentIdHeaderForDB(Map<String, String> headersMap) {
        // needed, as DB passes consent id value through "Resource-ID" header instead of "Consent-ID" one
        headersMap.put(RESOURCE_ID, headersMap.get(CONSENT_ID));
        headersMap.remove(CONSENT_ID);
    }

    private static String buildUri(String uri, RequestParams requestParams) {
        if (requestParams == null) {
            return uri;
        }

        Map<String, String> requestParamsMap = requestParams.toMap();

        if (requestParamsMap.isEmpty()) {
            return uri;
        }

        String requestParamsString = requestParamsMap.entrySet().stream()
                                             .map(entry -> entry.getKey() + "=" + entry.getValue())
                                             .collect(Collectors.joining("&", "?", ""));

        return uri + requestParamsString;
    }
}
