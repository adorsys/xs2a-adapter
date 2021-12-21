package de.adorsys.xs2a.adapter.api.model;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class AccountReference {
    private String iban;

    private String bban;

    private String pan;

    private String maskedPan;

    private String msisdn;

    private String currency;

    private String otherAccountIdentification;

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBban() {
        return bban;
    }

    public void setBban(String bban) {
        this.bban = bban;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getMaskedPan() {
        return maskedPan;
    }

    public void setMaskedPan(String maskedPan) {
        this.maskedPan = maskedPan;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOtherAccountIdentification() {
        return otherAccountIdentification;
    }

    public void setOtherAccountIdentification(String otherAccountIdentification) {
        this.otherAccountIdentification = otherAccountIdentification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountReference that = (AccountReference) o;
        return Objects.equals(iban, that.iban) &&
            Objects.equals(bban, that.bban) &&
            Objects.equals(pan, that.pan) &&
            Objects.equals(maskedPan, that.maskedPan) &&
            Objects.equals(msisdn, that.msisdn) &&
            Objects.equals(currency, that.currency) &&
            Objects.equals(otherAccountIdentification, that.otherAccountIdentification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban,
            bban,
            pan,
            maskedPan,
            msisdn,
            currency,
            otherAccountIdentification);
    }
}
