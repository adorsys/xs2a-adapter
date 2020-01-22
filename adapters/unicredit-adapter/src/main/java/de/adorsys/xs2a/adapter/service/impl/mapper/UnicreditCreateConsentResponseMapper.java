package de.adorsys.xs2a.adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.service.impl.service.UnicreditLinkBuilderService;
import de.adorsys.xs2a.adapter.service.model.ConsentCreationResponse;
import de.adorsys.xs2a.adapter.service.model.Link;

import java.util.Map;

public class UnicreditCreateConsentResponseMapper implements UnicreditResponseMapper<ConsentCreationResponse, ConsentCreationResponse> {
    private static final String AUTHORISE_TRANSACTION_LINK = "authoriseTransaction";
    private static final String START_AUTHORISATION_WITH_PSU_AUTHENTICATION_LINK = "startAuthorisationWithPsuAuthentication";

    private final UnicreditLinkBuilderService linkBuilderService = new UnicreditLinkBuilderService();

    @Override
    public ConsentCreationResponse modifyResponse(ConsentCreationResponse consentCreationResponse) {
        Map<String, Link> links = consentCreationResponse.getLinks();

        if (links != null && links.containsKey(AUTHORISE_TRANSACTION_LINK)) {
            modifyLinksToActualVersion(links, AUTHORISE_TRANSACTION_LINK, START_AUTHORISATION_WITH_PSU_AUTHENTICATION_LINK, linkBuilderService::buildStartAuthorisationUri);
        }

        return consentCreationResponse;
    }
}
