package de.adorsys.xs2a.adapter.service.impl.service;

import de.adorsys.xs2a.adapter.service.model.ConsentCreationResponse;
import de.adorsys.xs2a.adapter.service.model.Link;

import java.util.Map;

public class UnicreditCreateConsentResponseLinkModifierService {

    public ConsentCreationResponse modifyResponse(ConsentCreationResponse response) {
        Map<String, Link> links = response.getLinks();

        if (links.containsKey("startAuthorisation")) {
            // should it be "startAuthorisationWithPsuIdentification"?
            links.put("startAuthorisationWithPsuAuthentication", links.remove("startAuthorisation"));
            response.setLinks(links);
        }

        return response;
    }
}
