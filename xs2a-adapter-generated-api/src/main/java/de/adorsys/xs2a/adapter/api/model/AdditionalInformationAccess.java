package de.adorsys.xs2a.adapter.api.model;

import javax.annotation.Generated;
import java.util.List;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class AdditionalInformationAccess {
    private List<AccountReference> ownerName;

    public List<AccountReference> getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(List<AccountReference> ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdditionalInformationAccess that = (AdditionalInformationAccess) o;
        return Objects.equals(ownerName, that.ownerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerName);
    }
}
