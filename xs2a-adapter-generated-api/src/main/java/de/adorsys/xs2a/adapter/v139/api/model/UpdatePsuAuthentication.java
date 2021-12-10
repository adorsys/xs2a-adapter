package de.adorsys.xs2a.adapter.v139.api.model;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class UpdatePsuAuthentication {
    private PsuData psuData;

    public PsuData getPsuData() {
        return psuData;
    }

    public void setPsuData(PsuData psuData) {
        this.psuData = psuData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdatePsuAuthentication that = (UpdatePsuAuthentication) o;
        return Objects.equals(psuData, that.psuData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(psuData);
    }
}
