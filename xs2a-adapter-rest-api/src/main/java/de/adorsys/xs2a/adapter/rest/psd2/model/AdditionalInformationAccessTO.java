package de.adorsys.xs2a.adapter.rest.psd2.model;

import java.util.List;

public class AdditionalInformationAccessTO {
    private List<AccountReferenceTO> ownerName;

    public List<AccountReferenceTO> getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(List<AccountReferenceTO> ownerName) {
        this.ownerName = ownerName;
    }
}
