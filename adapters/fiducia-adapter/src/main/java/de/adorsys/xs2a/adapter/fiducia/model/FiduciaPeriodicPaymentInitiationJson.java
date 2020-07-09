package de.adorsys.xs2a.adapter.fiducia.model;

import de.adorsys.xs2a.adapter.api.model.*;

import java.time.LocalDate;
import java.util.Objects;

public class FiduciaPeriodicPaymentInitiationJson {
    private String endToEndIdentification;

    private AccountReference debtorAccount;

    private Amount instructedAmount;

    private AccountReference creditorAccount;

    private String creditorAgent;

    private String creditorName;

    private Address creditorAddress;

    private String remittanceInformationUnstructured;

    private LocalDate startDate;

    private LocalDate endDate;

    private FiduciaExecutionRule executionRule;

    private FrequencyCode frequency;

    private DayOfExecution dayOfExecution;

    public String getEndToEndIdentification() {
        return endToEndIdentification;
    }

    public void setEndToEndIdentification(String endToEndIdentification) {
        this.endToEndIdentification = endToEndIdentification;
    }

    public AccountReference getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(AccountReference debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    public Amount getInstructedAmount() {
        return instructedAmount;
    }

    public void setInstructedAmount(Amount instructedAmount) {
        this.instructedAmount = instructedAmount;
    }

    public AccountReference getCreditorAccount() {
        return creditorAccount;
    }

    public void setCreditorAccount(AccountReference creditorAccount) {
        this.creditorAccount = creditorAccount;
    }

    public String getCreditorAgent() {
        return creditorAgent;
    }

    public void setCreditorAgent(String creditorAgent) {
        this.creditorAgent = creditorAgent;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    public Address getCreditorAddress() {
        return creditorAddress;
    }

    public void setCreditorAddress(Address creditorAddress) {
        this.creditorAddress = creditorAddress;
    }

    public String getRemittanceInformationUnstructured() {
        return remittanceInformationUnstructured;
    }

    public void setRemittanceInformationUnstructured(String remittanceInformationUnstructured) {
        this.remittanceInformationUnstructured = remittanceInformationUnstructured;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public FiduciaExecutionRule getExecutionRule() {
        return executionRule;
    }

    public void setExecutionRule(FiduciaExecutionRule executionRule) {
        this.executionRule = executionRule;
    }

    public FrequencyCode getFrequency() {
        return frequency;
    }

    public void setFrequency(FrequencyCode frequency) {
        this.frequency = frequency;
    }

    public DayOfExecution getDayOfExecution() {
        return dayOfExecution;
    }

    public void setDayOfExecution(DayOfExecution dayOfExecution) {
        this.dayOfExecution = dayOfExecution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FiduciaPeriodicPaymentInitiationJson that = (FiduciaPeriodicPaymentInitiationJson) o;
        return Objects.equals(endToEndIdentification, that.endToEndIdentification) &&
            Objects.equals(debtorAccount, that.debtorAccount) &&
            Objects.equals(instructedAmount, that.instructedAmount) &&
            Objects.equals(creditorAccount, that.creditorAccount) &&
            Objects.equals(creditorAgent, that.creditorAgent) &&
            Objects.equals(creditorName, that.creditorName) &&
            Objects.equals(creditorAddress, that.creditorAddress) &&
            Objects.equals(remittanceInformationUnstructured, that.remittanceInformationUnstructured) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(executionRule, that.executionRule) &&
            Objects.equals(frequency, that.frequency) &&
            Objects.equals(dayOfExecution, that.dayOfExecution);
    }

    @Override
    public int hashCode() {
        return Objects.hash(endToEndIdentification,
            debtorAccount,
            instructedAmount,
            creditorAccount,
            creditorAgent,
            creditorName,
            creditorAddress,
            remittanceInformationUnstructured,
            startDate,
            endDate,
            executionRule,
            frequency,
            dayOfExecution);
    }
}
