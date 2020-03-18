package de.adorsys.xs2a.adapter.service.psd2.model;

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
}
