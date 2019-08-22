package de.adorsys.xs2a.adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.service.ais.ConsentCreationResponse;
import de.adorsys.xs2a.adapter.service.impl.service.UnicreditLinkBuilderService;
import de.adorsys.xs2a.adapter.service.impl.service.UnicreditResponseModifierService;
import de.adorsys.xs2a.adapter.service.model.Link;

import java.util.Map;

public class UnicreditCreateConsentLinkMapper implements UnicreditLinkMapper<ConsentCreationResponse> {
    private static final String AUTHORISE_TRANSACTION_LINK = "authoriseTransaction";
    private static final String START_AUTHORISATION_WITH_PSU_AUTHENTICATION_LINK = "startAuthorisationWithPsuAuthentication";

    private final UnicreditResponseModifierService responseModifierService = new UnicreditResponseModifierService();
    private final UnicreditLinkBuilderService linkBuilderService = new UnicreditLinkBuilderService();

    @Override
    public ConsentCreationResponse modifyLinks(ConsentCreationResponse consentCreationResponse) {
        Map<String, Link> links = consentCreationResponse.getLinks();
        // 1.1 is the obsolete version for now
        if (responseModifierService.isBerlinGroupVersionObsolete(links, AUTHORISE_TRANSACTION_LINK)) {
            responseModifierService.modifyLinksToActualVersion(links, AUTHORISE_TRANSACTION_LINK, START_AUTHORISATION_WITH_PSU_AUTHENTICATION_LINK, linkBuilderService::buildStartAuthorisationUri);
        }

        return consentCreationResponse;
    }
}
