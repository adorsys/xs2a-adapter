package de.adorsys.xs2a.adapter.api.model;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class AdditionalInformationStructured {
    private StandingOrderDetails standingOrderDetails;

    public StandingOrderDetails getStandingOrderDetails() {
        return standingOrderDetails;
    }

    public void setStandingOrderDetails(StandingOrderDetails standingOrderDetails) {
        this.standingOrderDetails = standingOrderDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdditionalInformationStructured that = (AdditionalInformationStructured) o;
        return Objects.equals(standingOrderDetails, that.standingOrderDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(standingOrderDetails);
    }
}
