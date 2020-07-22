package de.adorsys.xs2a.adapter.fiducia.model;

import java.time.LocalDate;

public class FiduciaExchangeRate {
    private String currencyFrom;

    private String rateFrom;

    private String currencyTo;

    private String rateTo;

    private LocalDate rateDate;

    private String rateContract;

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getRateFrom() {
        return rateFrom;
    }

    public void setRateFrom(String rateFrom) {
        this.rateFrom = rateFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public String getRateTo() {
        return rateTo;
    }

    public void setRateTo(String rateTo) {
        this.rateTo = rateTo;
    }

    public LocalDate getRateDate() {
        return rateDate;
    }

    public void setRateDate(LocalDate rateDate) {
        this.rateDate = rateDate;
    }

    public String getRateContract() {
        return rateContract;
    }

    public void setRateContract(String rateContract) {
        this.rateContract = rateContract;
    }
}
