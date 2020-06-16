package de.adorsys.xs2a.adapter.api.model;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class ScaStatusResponse {
    private ScaStatus scaStatus;

    public ScaStatus getScaStatus() {
        return scaStatus;
    }

    public void setScaStatus(ScaStatus scaStatus) {
        this.scaStatus = scaStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScaStatusResponse that = (ScaStatusResponse) o;
        return Objects.equals(scaStatus, that.scaStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scaStatus);
    }
}
