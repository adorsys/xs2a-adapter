package de.adorsys.xs2a.adapter.model;

import javax.annotation.Generated;
import java.time.LocalDate;

@Generated("xs2a-gateway-codegen")
public class ReportExchangeRateTO {
  private String sourceCurrency;

  private String exchangeRate;

  private String unitCurrency;

  private String targetCurrency;

  private LocalDate quotationDate;

  private String contractIdentification;

  public String getSourceCurrency() {
    return sourceCurrency;
  }

  public void setSourceCurrency(String sourceCurrency) {
    this.sourceCurrency = sourceCurrency;
  }

  public String getExchangeRate() {
    return exchangeRate;
  }

  public void setExchangeRate(String exchangeRate) {
    this.exchangeRate = exchangeRate;
  }

  public String getUnitCurrency() {
    return unitCurrency;
  }

  public void setUnitCurrency(String unitCurrency) {
    this.unitCurrency = unitCurrency;
  }

  public String getTargetCurrency() {
    return targetCurrency;
  }

  public void setTargetCurrency(String targetCurrency) {
    this.targetCurrency = targetCurrency;
  }

  public LocalDate getQuotationDate() {
    return quotationDate;
  }

  public void setQuotationDate(LocalDate quotationDate) {
    this.quotationDate = quotationDate;
  }

  public String getContractIdentification() {
    return contractIdentification;
  }

  public void setContractIdentification(String contractIdentification) {
    this.contractIdentification = contractIdentification;
  }
}
