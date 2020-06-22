package de.adorsys.xs2a.adapter.api.model;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class RemittanceInformationStructured {
    private String reference;

    private String referenceType;

    private String referenceIssuer;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getReferenceIssuer() {
        return referenceIssuer;
    }

    public void setReferenceIssuer(String referenceIssuer) {
        this.referenceIssuer = referenceIssuer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemittanceInformationStructured that = (RemittanceInformationStructured) o;
        return Objects.equals(reference, that.reference) &&
            Objects.equals(referenceType, that.referenceType) &&
            Objects.equals(referenceIssuer, that.referenceIssuer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference,
            referenceType,
            referenceIssuer);
    }
}
