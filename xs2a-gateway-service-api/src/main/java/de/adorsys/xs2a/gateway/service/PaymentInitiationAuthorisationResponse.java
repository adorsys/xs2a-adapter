package de.adorsys.xs2a.gateway.service;

import java.util.List;

public class PaymentInitiationAuthorisationResponse {
    private List<String> authorisationIds;

    public PaymentInitiationAuthorisationResponse(List<String> authorisationIds) {
        this.authorisationIds = authorisationIds;
    }

    public List<String> getAuthorisationIds() {
        return authorisationIds;
    }
}
