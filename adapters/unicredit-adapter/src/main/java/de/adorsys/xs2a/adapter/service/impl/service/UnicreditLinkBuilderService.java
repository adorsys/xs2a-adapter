package de.adorsys.xs2a.adapter.service.impl.service;

import de.adorsys.xs2a.adapter.http.StringUri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UnicreditLinkBuilderService {
    private static final Logger LOG = LoggerFactory.getLogger(UnicreditLinkBuilderService.class);
    private static final String AUTHORISATIONS = "authorisations";
    private static final String AUTHENTICATION_CURRENT_NUMBER_QUERY_PARAM = "authenticationCurrentNumber";

    public String buildStartAuthorisationUri(String uri) {
        return StringUri.fromElements(uri, AUTHORISATIONS);
    }

    public String buildUpdatePsuDataUri(String uri) {
        Map<String, String> queryParams = StringUri.getQueryParamsFromUri(uri);

        String authenticationCurrentNumber = queryParams.get(AUTHENTICATION_CURRENT_NUMBER_QUERY_PARAM);

        if (authenticationCurrentNumber == null || authenticationCurrentNumber.isEmpty()) {
            LOG.warn("Unexpected link [{}] for Unicredit: [{}] query param is not presented", uri, AUTHENTICATION_CURRENT_NUMBER_QUERY_PARAM);
            return uri;
        }

        return StringUri.fromElements(uri, AUTHORISATIONS, authenticationCurrentNumber);
    }
}
