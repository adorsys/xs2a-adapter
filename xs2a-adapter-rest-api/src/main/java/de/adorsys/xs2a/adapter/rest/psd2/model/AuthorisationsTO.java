package de.adorsys.xs2a.adapter.rest.psd2.model;

import java.util.List;

public class AuthorisationsTO {
    private List<String> authorisationIds;

    public List<String> getAuthorisationIds() {
        return authorisationIds;
    }

    public void setAuthorisationIds(List<String> authorisationIds) {
        this.authorisationIds = authorisationIds;
    }
}
