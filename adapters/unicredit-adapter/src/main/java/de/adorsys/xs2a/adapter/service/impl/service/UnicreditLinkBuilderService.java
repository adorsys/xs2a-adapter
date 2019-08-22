package de.adorsys.xs2a.adapter.service.impl.service;

import de.adorsys.xs2a.adapter.http.StringUri;

import java.util.Map;

public class UnicreditLinkBuilderService {
    private static final String AUTHORISATIONS = "authorisations";
    private static final String AUTHENTICATION_CURRENT_NUMBER_QUERY_PARAM = "authenticationCurrentNumber";

    public String buildStartAuthorisationUri(String baseUri) {
        return StringUri.fromElements(baseUri, AUTHORISATIONS);
    }

    public String buildUpdatePsuDataUri(String baseUri) {
        Map<String, String> queryParams = StringUri.getQueryParamsFromUri(baseUri);

        String authenticationCurrentNumber = queryParams.get(AUTHENTICATION_CURRENT_NUMBER_QUERY_PARAM);

        if (authenticationCurrentNumber == null) {
            // TODO come up with better solution
            throw new RuntimeException();
        }

        return StringUri.fromElements(baseUri, AUTHORISATIONS, authenticationCurrentNumber);
    }
}
