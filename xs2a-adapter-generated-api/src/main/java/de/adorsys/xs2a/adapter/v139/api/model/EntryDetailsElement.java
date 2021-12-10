package de.adorsys.xs2a.adapter.v139.api.model;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public class EntryDetailsElement {
  private String endToEndId;

  private String mandateId;

  private String checkId;

  private String creditorId;

  private Amount transactionAmount;

  private List<ReportExchangeRate> currencyExchange;

  private String creditorName;

  private AccountReference creditorAccount;

  private String creditorAgent;

  private String ultimateCreditor;

  private String debtorName;

  private AccountReference debtorAccount;

  private String debtorAgent;

  private String ultimateDebtor;

  private String remittanceInformationUnstructured;

  private List<String> remittanceInformationUnstructuredArray;

  private String remittanceInformationStructured;

  private List<String> remittanceInformationStructuredArray;

  private PurposeCode purposeCode;

  public String getEndToEndId() {
    return endToEndId;
  }

  public void setEndToEndId(String endToEndId) {
    this.endToEndId = endToEndId;
  }

  public String getMandateId() {
    return mandateId;
  }

  public void setMandateId(String mandateId) {
    this.mandateId = mandateId;
  }

  public String getCheckId() {
    return checkId;
  }

  public void setCheckId(String checkId) {
    this.checkId = checkId;
  }

  public String getCreditorId() {
    return creditorId;
  }

  public void setCreditorId(String creditorId) {
    this.creditorId = creditorId;
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

  public String getCreditorName() {
    return creditorName;
  }

  public void setCreditorName(String creditorName) {
    this.creditorName = creditorName;
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

  public String getUltimateCreditor() {
    return ultimateCreditor;
  }

  public void setUltimateCreditor(String ultimateCreditor) {
    this.ultimateCreditor = ultimateCreditor;
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

  public String getDebtorAgent() {
    return debtorAgent;
  }

  public void setDebtorAgent(String debtorAgent) {
    this.debtorAgent = debtorAgent;
  }

  public String getUltimateDebtor() {
    return ultimateDebtor;
  }

  public void setUltimateDebtor(String ultimateDebtor) {
    this.ultimateDebtor = ultimateDebtor;
  }

  public String getRemittanceInformationUnstructured() {
    return remittanceInformationUnstructured;
  }

  public void setRemittanceInformationUnstructured(String remittanceInformationUnstructured) {
    this.remittanceInformationUnstructured = remittanceInformationUnstructured;
  }

  public List<String> getRemittanceInformationUnstructuredArray() {
    return remittanceInformationUnstructuredArray;
  }

  public void setRemittanceInformationUnstructuredArray(
      List<String> remittanceInformationUnstructuredArray) {
    this.remittanceInformationUnstructuredArray = remittanceInformationUnstructuredArray;
  }

  public String getRemittanceInformationStructured() {
    return remittanceInformationStructured;
  }

  public void setRemittanceInformationStructured(String remittanceInformationStructured) {
    this.remittanceInformationStructured = remittanceInformationStructured;
  }

  public List<String> getRemittanceInformationStructuredArray() {
    return remittanceInformationStructuredArray;
  }

  public void setRemittanceInformationStructuredArray(
      List<String> remittanceInformationStructuredArray) {
    this.remittanceInformationStructuredArray = remittanceInformationStructuredArray;
  }

  public PurposeCode getPurposeCode() {
    return purposeCode;
  }

  public void setPurposeCode(PurposeCode purposeCode) {
    this.purposeCode = purposeCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EntryDetailsElement that = (EntryDetailsElement) o;
    return Objects.equals(endToEndId, that.endToEndId) &&
        Objects.equals(mandateId, that.mandateId) &&
        Objects.equals(checkId, that.checkId) &&
        Objects.equals(creditorId, that.creditorId) &&
        Objects.equals(transactionAmount, that.transactionAmount) &&
        Objects.equals(currencyExchange, that.currencyExchange) &&
        Objects.equals(creditorName, that.creditorName) &&
        Objects.equals(creditorAccount, that.creditorAccount) &&
        Objects.equals(creditorAgent, that.creditorAgent) &&
        Objects.equals(ultimateCreditor, that.ultimateCreditor) &&
        Objects.equals(debtorName, that.debtorName) &&
        Objects.equals(debtorAccount, that.debtorAccount) &&
        Objects.equals(debtorAgent, that.debtorAgent) &&
        Objects.equals(ultimateDebtor, that.ultimateDebtor) &&
        Objects.equals(remittanceInformationUnstructured, that.remittanceInformationUnstructured) &&
        Objects.equals(remittanceInformationUnstructuredArray, that.remittanceInformationUnstructuredArray) &&
        Objects.equals(remittanceInformationStructured, that.remittanceInformationStructured) &&
        Objects.equals(remittanceInformationStructuredArray, that.remittanceInformationStructuredArray) &&
        Objects.equals(purposeCode, that.purposeCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(endToEndId,
        mandateId,
        checkId,
        creditorId,
        transactionAmount,
        currencyExchange,
        creditorName,
        creditorAccount,
        creditorAgent,
        ultimateCreditor,
        debtorName,
        debtorAccount,
        debtorAgent,
        ultimateDebtor,
        remittanceInformationUnstructured,
        remittanceInformationUnstructuredArray,
        remittanceInformationStructured,
        remittanceInformationStructuredArray,
        purposeCode);
  }
}
