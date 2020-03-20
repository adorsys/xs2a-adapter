package de.adorsys.xs2a.adapter.service.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public class CardTransaction {
    private String cardTransactionId;

    private String terminalId;

    private LocalDate transactionDate;

    private OffsetDateTime acceptorTransactionDateTime;

    private LocalDate bookingDate;

    private Amount transactionAmount;

    private List<ReportExchangeRate> currencyExchange;

    private Amount originalAmount;

    private Amount markupFee;

    private String markupFeePercentage;

    private String cardAcceptorId;

    private Address cardAcceptorAddress;

    private String cardAcceptorPhone;

    private String merchantCategoryCode;

    private String maskedPAN;

    private String transactionDetails;

    private Boolean invoiced;

    private String proprietaryBankTransactionCode;

    public String getCardTransactionId() {
        return cardTransactionId;
    }

    public void setCardTransactionId(String cardTransactionId) {
        this.cardTransactionId = cardTransactionId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public OffsetDateTime getAcceptorTransactionDateTime() {
        return acceptorTransactionDateTime;
    }

    public void setAcceptorTransactionDateTime(OffsetDateTime acceptorTransactionDateTime) {
        this.acceptorTransactionDateTime = acceptorTransactionDateTime;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Amount getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Amount transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public List<ReportExchangeRate> getCurrencyExchange() {
        return currencyExchange;
    }

    public void setCurrencyExchange(List<ReportExchangeRate> currencyExchange) {
        this.currencyExchange = currencyExchange;
    }

    public Amount getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(Amount originalAmount) {
        this.originalAmount = originalAmount;
    }

    public Amount getMarkupFee() {
        return markupFee;
    }

    public void setMarkupFee(Amount markupFee) {
        this.markupFee = markupFee;
    }

    public String getMarkupFeePercentage() {
        return markupFeePercentage;
    }

    public void setMarkupFeePercentage(String markupFeePercentage) {
        this.markupFeePercentage = markupFeePercentage;
    }

    public String getCardAcceptorId() {
        return cardAcceptorId;
    }

    public void setCardAcceptorId(String cardAcceptorId) {
        this.cardAcceptorId = cardAcceptorId;
    }

    public Address getCardAcceptorAddress() {
        return cardAcceptorAddress;
    }

    public void setCardAcceptorAddress(Address cardAcceptorAddress) {
        this.cardAcceptorAddress = cardAcceptorAddress;
    }

    public String getCardAcceptorPhone() {
        return cardAcceptorPhone;
    }

    public void setCardAcceptorPhone(String cardAcceptorPhone) {
        this.cardAcceptorPhone = cardAcceptorPhone;
    }

    public String getMerchantCategoryCode() {
        return merchantCategoryCode;
    }

    public void setMerchantCategoryCode(String merchantCategoryCode) {
        this.merchantCategoryCode = merchantCategoryCode;
    }

    public String getMaskedPAN() {
        return maskedPAN;
    }

    public void setMaskedPAN(String maskedPAN) {
        this.maskedPAN = maskedPAN;
    }

    public String getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(String transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public Boolean getInvoiced() {
        return invoiced;
    }

    public void setInvoiced(Boolean invoiced) {
        this.invoiced = invoiced;
    }

    public String getProprietaryBankTransactionCode() {
        return proprietaryBankTransactionCode;
    }

    public void setProprietaryBankTransactionCode(String proprietaryBankTransactionCode) {
        this.proprietaryBankTransactionCode = proprietaryBankTransactionCode;
    }
}
