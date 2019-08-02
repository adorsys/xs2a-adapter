package de.adorsys.xs2a.adapter.service;

import java.util.List;

public class PaymentInitiationAuthorisationResponse {
    private List<String> authorisationIds;

    public PaymentInitiationAuthorisationResponse() {
    }

    public PaymentInitiationAuthorisationResponse(List<String> authorisationIds) {
        this.authorisationIds = authorisationIds;
    }

    public List<String> getAuthorisationIds() {
        return authorisationIds;
    }
}
