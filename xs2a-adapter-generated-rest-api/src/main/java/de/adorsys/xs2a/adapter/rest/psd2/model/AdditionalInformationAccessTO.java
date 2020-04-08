package de.adorsys.xs2a.adapter.rest.psd2.model;

import javax.annotation.Generated;
import java.util.List;

@Generated("xs2a-adapter-codegen")
public class AdditionalInformationAccessTO {
    private List<AccountReferenceTO> ownerName;

    public List<AccountReferenceTO> getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(List<AccountReferenceTO> ownerName) {
        this.ownerName = ownerName;
    }
}
