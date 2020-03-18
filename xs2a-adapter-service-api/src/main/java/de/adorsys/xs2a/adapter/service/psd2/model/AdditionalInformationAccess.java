package de.adorsys.xs2a.adapter.service.psd2.model;

import java.util.List;

public class AdditionalInformationAccess {
    private List<AccountReference> ownerName;

    public List<AccountReference> getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(List<AccountReference> ownerName) {
        this.ownerName = ownerName;
    }
}
