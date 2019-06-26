package de.adorsys.xs2a.adapter.model;

import javax.annotation.Generated;
import java.time.LocalDate;

@Generated("xs2a-codegen")
public class PeriodicPaymentInitiationJsonTO {
  private String endToEndIdentification;

  private AccountReferenceTO debtorAccount;

  private AmountTO instructedAmount;

  private AccountReferenceTO creditorAccount;

  private String creditorAgent;

  private String creditorName;

  private AddressTO creditorAddress;

  private String remittanceInformationUnstructured;

  private LocalDate startDate;

  private LocalDate endDate;

  private ExecutionRuleTO executionRule;

  private FrequencyCodeTO frequency;

  private DayOfExecutionTO dayOfExecution;

  public String getEndToEndIdentification() {
    return endToEndIdentification;
  }

  public void setEndToEndIdentification(String endToEndIdentification) {
    this.endToEndIdentification = endToEndIdentification;
  }

  public AccountReferenceTO getDebtorAccount() {
    return debtorAccount;
  }

  public void setDebtorAccount(AccountReferenceTO debtorAccount) {
    this.debtorAccount = debtorAccount;
  }

  public AmountTO getInstructedAmount() {
    return instructedAmount;
  }

  public void setInstructedAmount(AmountTO instructedAmount) {
    this.instructedAmount = instructedAmount;
  }

  public AccountReferenceTO getCreditorAccount() {
    return creditorAccount;
  }

  public void setCreditorAccount(AccountReferenceTO creditorAccount) {
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

  public AddressTO getCreditorAddress() {
    return creditorAddress;
  }

  public void setCreditorAddress(AddressTO creditorAddress) {
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

  public ExecutionRuleTO getExecutionRule() {
    return executionRule;
  }

  public void setExecutionRule(ExecutionRuleTO executionRule) {
    this.executionRule = executionRule;
  }

  public FrequencyCodeTO getFrequency() {
    return frequency;
  }

  public void setFrequency(FrequencyCodeTO frequency) {
    this.frequency = frequency;
  }

  public DayOfExecutionTO getDayOfExecution() {
    return dayOfExecution;
  }

  public void setDayOfExecution(DayOfExecutionTO dayOfExecution) {
    this.dayOfExecution = dayOfExecution;
  }
}
