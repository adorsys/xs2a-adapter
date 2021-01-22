package de.adorsys.xs2a.adapter.api.model;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class TransactionAuthorisation {
    private String scaAuthenticationData;

    public String getScaAuthenticationData() {
        return scaAuthenticationData;
    }

    public void setScaAuthenticationData(String scaAuthenticationData) {
        this.scaAuthenticationData = scaAuthenticationData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionAuthorisation that = (TransactionAuthorisation) o;
        return Objects.equals(scaAuthenticationData, that.scaAuthenticationData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scaAuthenticationData);
    }
}
