package de.adorsys.xs2a.adapter.model;

import javax.annotation.Generated;

@Generated("xs2a-gateway-codegen")
public class AccountReferenceTO {
  private String iban;

  private String bban;

  private String pan;

  private String maskedPan;

  private String msisdn;

  private String currency;

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
}
