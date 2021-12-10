package de.adorsys.xs2a.adapter.v139.api.model;

import javax.annotation.Generated;
import java.time.LocalDate;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class ReportExchangeRate {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportExchangeRate that = (ReportExchangeRate) o;
        return Objects.equals(sourceCurrency, that.sourceCurrency) &&
            Objects.equals(exchangeRate, that.exchangeRate) &&
            Objects.equals(unitCurrency, that.unitCurrency) &&
            Objects.equals(targetCurrency, that.targetCurrency) &&
            Objects.equals(quotationDate, that.quotationDate) &&
            Objects.equals(contractIdentification, that.contractIdentification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceCurrency,
            exchangeRate,
            unitCurrency,
            targetCurrency,
            quotationDate,
            contractIdentification);
    }
}
