package de.adorsys.xs2a.adapter.ing.model;

import java.time.LocalDate;

public class IngPeriodicPaymentInitiationJson {
    private String endToEndIdentification;

    private IngDebtorAccount debtorAccount;

    private IngAmount instructedAmount;

    private IngCreditorAccount creditorAccount;

    private String creditorAgent;

    private String creditorName;

    private IngAddress creditorAddress;

    private IngChargeBearer chargeBearer;

    private String remittanceInformationUnstructured;

    private IngClearingSystemMemberIdentification clearingSystemMemberIdentification;

    private String debtorName;

    private String debtorAgent;

    private IngInstructionPriority instructionPriority;

    private String serviceLevelCode;

    private String localInstrumentCode;

    private String categoryPurposeCode;

    private LocalDate requestedExecutionDate;

    private LocalDate startDate;

    private LocalDate endDate;

    private IngFrequencyCode frequency;

    private IngDayOfExecution dayOfExecution;

    public String getEndToEndIdentification() {
        return endToEndIdentification;
    }

    public void setEndToEndIdentification(String endToEndIdentification) {
        this.endToEndIdentification = endToEndIdentification;
    }

    public IngDebtorAccount getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(IngDebtorAccount debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    public IngAmount getInstructedAmount() {
        return instructedAmount;
    }

    public void setInstructedAmount(IngAmount instructedAmount) {
        this.instructedAmount = instructedAmount;
    }

    public IngCreditorAccount getCreditorAccount() {
        return creditorAccount;
    }

    public void setCreditorAccount(IngCreditorAccount creditorAccount) {
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

    public IngAddress getCreditorAddress() {
        return creditorAddress;
    }

    public void setCreditorAddress(IngAddress creditorAddress) {
        this.creditorAddress = creditorAddress;
    }

    public IngChargeBearer getChargeBearer() {
        return chargeBearer;
    }

    public void setChargeBearer(IngChargeBearer chargeBearer) {
        this.chargeBearer = chargeBearer;
    }

    public String getRemittanceInformationUnstructured() {
        return remittanceInformationUnstructured;
    }

    public void setRemittanceInformationUnstructured(String remittanceInformationUnstructured) {
        this.remittanceInformationUnstructured = remittanceInformationUnstructured;
    }

    public IngClearingSystemMemberIdentification getClearingSystemMemberIdentification() {
        return clearingSystemMemberIdentification;
    }

    public void setClearingSystemMemberIdentification(IngClearingSystemMemberIdentification clearingSystemMemberIdentification) {
        this.clearingSystemMemberIdentification = clearingSystemMemberIdentification;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public String getDebtorAgent() {
        return debtorAgent;
    }

    public void setDebtorAgent(String debtorAgent) {
        this.debtorAgent = debtorAgent;
    }

    public IngInstructionPriority getInstructionPriority() {
        return instructionPriority;
    }

    public void setInstructionPriority(IngInstructionPriority instructionPriority) {
        this.instructionPriority = instructionPriority;
    }

    public String getServiceLevelCode() {
        return serviceLevelCode;
    }

    public void setServiceLevelCode(String serviceLevelCode) {
        this.serviceLevelCode = serviceLevelCode;
    }

    public String getLocalInstrumentCode() {
        return localInstrumentCode;
    }

    public void setLocalInstrumentCode(String localInstrumentCode) {
        this.localInstrumentCode = localInstrumentCode;
    }

    public String getCategoryPurposeCode() {
        return categoryPurposeCode;
    }

    public void setCategoryPurposeCode(String categoryPurposeCode) {
        this.categoryPurposeCode = categoryPurposeCode;
    }

    public LocalDate getRequestedExecutionDate() {
        return requestedExecutionDate;
    }

    public void setRequestedExecutionDate(LocalDate requestedExecutionDate) {
        this.requestedExecutionDate = requestedExecutionDate;
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

    public IngFrequencyCode getFrequency() {
        return frequency;
    }

    public void setFrequency(IngFrequencyCode frequency) {
        this.frequency = frequency;
    }

    public IngDayOfExecution getDayOfExecution() {
        return dayOfExecution;
    }

    public void setDayOfExecution(IngDayOfExecution dayOfExecution) {
        this.dayOfExecution = dayOfExecution;
    }
}

