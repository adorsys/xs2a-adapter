package de.adorsys.xs2a.adapter.fiducia.model;

import java.util.Objects;

public class FiduciaAdditionalInformationStructured {
    private FiduciaStandingOrderDetails standingOrderDetails;

    public FiduciaStandingOrderDetails getStandingOrderDetails() {
        return standingOrderDetails;
    }

    public void setStandingOrderDetails(FiduciaStandingOrderDetails standingOrderDetails) {
        this.standingOrderDetails = standingOrderDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FiduciaAdditionalInformationStructured that = (FiduciaAdditionalInformationStructured) o;
        return Objects.equals(standingOrderDetails, that.standingOrderDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(standingOrderDetails);
    }
}
