package de.adorsys.xs2a.adapter.ing.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class Transaction {
    private String transactionId;

    private String endToEndId;

    private LocalDate bookingDate;

    private LocalDate valueDate;

    private OffsetDateTime executionDateTime;

    private Amount transactionAmount;

    private String creditorName;

    private CounterpartyAccount creditorAccount;

    private String debtorName;

    private CounterpartyAccount debtorAccount;

    private String transactionType;

    private String remittanceInformationUnstructured;

    private TransactionRemittanceInformationStructured remittanceInformationStructured;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getEndToEndId() {
        return endToEndId;
    }

    public void setEndToEndId(String endToEndId) {
        this.endToEndId = endToEndId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDate getValueDate() {
        return valueDate;
    }

    public void setValueDate(LocalDate valueDate) {
        this.valueDate = valueDate;
    }

    public OffsetDateTime getExecutionDateTime() {
        return executionDateTime;
    }

    public void setExecutionDateTime(OffsetDateTime executionDateTime) {
        this.executionDateTime = executionDateTime;
    }

    public Amount getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Amount transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    public CounterpartyAccount getCreditorAccount() {
        return creditorAccount;
    }

    public void setCreditorAccount(CounterpartyAccount creditorAccount) {
        this.creditorAccount = creditorAccount;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public CounterpartyAccount getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(CounterpartyAccount debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getRemittanceInformationUnstructured() {
        return remittanceInformationUnstructured;
    }

    public void setRemittanceInformationUnstructured(String remittanceInformationUnstructured) {
        this.remittanceInformationUnstructured = remittanceInformationUnstructured;
    }

    public TransactionRemittanceInformationStructured getRemittanceInformationStructured() {
        return remittanceInformationStructured;
    }

    public void setRemittanceInformationStructured(TransactionRemittanceInformationStructured remittanceInformationStructured) {
        this.remittanceInformationStructured = remittanceInformationStructured;
    }
}
