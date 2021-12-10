package de.adorsys.xs2a.adapter.v139.api.model;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public class PeriodicPaymentInitiationWithStatusResponse {
  private String endToEndIdentification;

  private String instructionIdentification;

  private String debtorName;

  private AccountReference debtorAccount;

  private String ultimateDebtor;

  private Amount instructedAmount;

  private AccountReference creditorAccount;

  private String creditorAgent;

  private String creditorName;

  private Address creditorAddress;

  private String ultimateCreditor;

  private PurposeCode purposeCode;

  private String remittanceInformationUnstructured;

  private String remittanceInformationStructured;

  private LocalDate startDate;

  private LocalDate endDate;

  private ExecutionRule executionRule;

  private FrequencyCode frequency;

  private DayOfExecution dayOfExecution;

  private TransactionStatus transactionStatus;

  private List<TppMessageGeneric> tppMessage;

  public String getEndToEndIdentification() {
    return endToEndIdentification;
  }

  public void setEndToEndIdentification(String endToEndIdentification) {
    this.endToEndIdentification = endToEndIdentification;
  }

  public String getInstructionIdentification() {
    return instructionIdentification;
  }

  public void setInstructionIdentification(String instructionIdentification) {
    this.instructionIdentification = instructionIdentification;
  }

  public String getDebtorName() {
    return debtorName;
  }

  public void setDebtorName(String debtorName) {
    this.debtorName = debtorName;
  }

  public AccountReference getDebtorAccount() {
    return debtorAccount;
  }

  public void setDebtorAccount(AccountReference debtorAccount) {
    this.debtorAccount = debtorAccount;
  }

  public String getUltimateDebtor() {
    return ultimateDebtor;
  }

  public void setUltimateDebtor(String ultimateDebtor) {
    this.ultimateDebtor = ultimateDebtor;
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

  public String getUltimateCreditor() {
    return ultimateCreditor;
  }

  public void setUltimateCreditor(String ultimateCreditor) {
    this.ultimateCreditor = ultimateCreditor;
  }

  public PurposeCode getPurposeCode() {
    return purposeCode;
  }

  public void setPurposeCode(PurposeCode purposeCode) {
    this.purposeCode = purposeCode;
  }

  public String getRemittanceInformationUnstructured() {
    return remittanceInformationUnstructured;
  }

  public void setRemittanceInformationUnstructured(String remittanceInformationUnstructured) {
    this.remittanceInformationUnstructured = remittanceInformationUnstructured;
  }

  public String getRemittanceInformationStructured() {
    return remittanceInformationStructured;
  }

  public void setRemittanceInformationStructured(String remittanceInformationStructured) {
    this.remittanceInformationStructured = remittanceInformationStructured;
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

  public ExecutionRule getExecutionRule() {
    return executionRule;
  }

  public void setExecutionRule(ExecutionRule executionRule) {
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

  public TransactionStatus getTransactionStatus() {
    return transactionStatus;
  }

  public void setTransactionStatus(TransactionStatus transactionStatus) {
    this.transactionStatus = transactionStatus;
  }

  public List<TppMessageGeneric> getTppMessage() {
    return tppMessage;
  }

  public void setTppMessage(List<TppMessageGeneric> tppMessage) {
    this.tppMessage = tppMessage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PeriodicPaymentInitiationWithStatusResponse that = (PeriodicPaymentInitiationWithStatusResponse) o;
    return Objects.equals(endToEndIdentification, that.endToEndIdentification) &&
        Objects.equals(instructionIdentification, that.instructionIdentification) &&
        Objects.equals(debtorName, that.debtorName) &&
        Objects.equals(debtorAccount, that.debtorAccount) &&
        Objects.equals(ultimateDebtor, that.ultimateDebtor) &&
        Objects.equals(instructedAmount, that.instructedAmount) &&
        Objects.equals(creditorAccount, that.creditorAccount) &&
        Objects.equals(creditorAgent, that.creditorAgent) &&
        Objects.equals(creditorName, that.creditorName) &&
        Objects.equals(creditorAddress, that.creditorAddress) &&
        Objects.equals(ultimateCreditor, that.ultimateCreditor) &&
        Objects.equals(purposeCode, that.purposeCode) &&
        Objects.equals(remittanceInformationUnstructured, that.remittanceInformationUnstructured) &&
        Objects.equals(remittanceInformationStructured, that.remittanceInformationStructured) &&
        Objects.equals(startDate, that.startDate) &&
        Objects.equals(endDate, that.endDate) &&
        Objects.equals(executionRule, that.executionRule) &&
        Objects.equals(frequency, that.frequency) &&
        Objects.equals(dayOfExecution, that.dayOfExecution) &&
        Objects.equals(transactionStatus, that.transactionStatus) &&
        Objects.equals(tppMessage, that.tppMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(endToEndIdentification,
        instructionIdentification,
        debtorName,
        debtorAccount,
        ultimateDebtor,
        instructedAmount,
        creditorAccount,
        creditorAgent,
        creditorName,
        creditorAddress,
        ultimateCreditor,
        purposeCode,
        remittanceInformationUnstructured,
        remittanceInformationStructured,
        startDate,
        endDate,
        executionRule,
        frequency,
        dayOfExecution,
        transactionStatus,
        tppMessage);
  }
}
