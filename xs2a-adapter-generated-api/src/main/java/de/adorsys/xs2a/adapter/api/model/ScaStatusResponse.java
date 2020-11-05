package de.adorsys.xs2a.adapter.api.model;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class ScaStatusResponse {
    private ScaStatus scaStatus;

    private Boolean trustedBeneficiaryFlag;

    public ScaStatus getScaStatus() {
        return scaStatus;
    }

    public void setScaStatus(ScaStatus scaStatus) {
        this.scaStatus = scaStatus;
    }

    public Boolean getTrustedBeneficiaryFlag() {
        return trustedBeneficiaryFlag;
    }

    public void setTrustedBeneficiaryFlag(Boolean trustedBeneficiaryFlag) {
        this.trustedBeneficiaryFlag = trustedBeneficiaryFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScaStatusResponse that = (ScaStatusResponse) o;
        return Objects.equals(scaStatus, that.scaStatus) &&
            Objects.equals(trustedBeneficiaryFlag, that.trustedBeneficiaryFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scaStatus,
            trustedBeneficiaryFlag);
    }
}
