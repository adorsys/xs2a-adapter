package de.adorsys.xs2a.adapter.v139.api.model;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public class PaymentInitiationBulkElementJson {
  private String endToEndIdentification;

  private String instructionIdentification;

  private String debtorName;

  private String ultimateDebtor;

  private Amount instructedAmount;

  private AccountReference creditorAccount;

  private String creditorAgent;

  private String creditorAgentName;

  private String creditorName;

  private Address creditorAddress;

  private String creditorId;

  private String ultimateCreditor;

  private PurposeCode purposeCode;

  private ChargeBearer chargeBearer;

  private String remittanceInformationUnstructured;

  private String remittanceInformationStructured;

  private List<String> remittanceInformationStructuredArray;

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

  public String getCreditorAgentName() {
    return creditorAgentName;
  }

  public void setCreditorAgentName(String creditorAgentName) {
    this.creditorAgentName = creditorAgentName;
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

  public String getCreditorId() {
    return creditorId;
  }

  public void setCreditorId(String creditorId) {
    this.creditorId = creditorId;
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

  public ChargeBearer getChargeBearer() {
    return chargeBearer;
  }

  public void setChargeBearer(ChargeBearer chargeBearer) {
    this.chargeBearer = chargeBearer;
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

  public List<String> getRemittanceInformationStructuredArray() {
    return remittanceInformationStructuredArray;
  }

  public void setRemittanceInformationStructuredArray(
      List<String> remittanceInformationStructuredArray) {
    this.remittanceInformationStructuredArray = remittanceInformationStructuredArray;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PaymentInitiationBulkElementJson that = (PaymentInitiationBulkElementJson) o;
    return Objects.equals(endToEndIdentification, that.endToEndIdentification) &&
        Objects.equals(instructionIdentification, that.instructionIdentification) &&
        Objects.equals(debtorName, that.debtorName) &&
        Objects.equals(ultimateDebtor, that.ultimateDebtor) &&
        Objects.equals(instructedAmount, that.instructedAmount) &&
        Objects.equals(creditorAccount, that.creditorAccount) &&
        Objects.equals(creditorAgent, that.creditorAgent) &&
        Objects.equals(creditorAgentName, that.creditorAgentName) &&
        Objects.equals(creditorName, that.creditorName) &&
        Objects.equals(creditorAddress, that.creditorAddress) &&
        Objects.equals(creditorId, that.creditorId) &&
        Objects.equals(ultimateCreditor, that.ultimateCreditor) &&
        Objects.equals(purposeCode, that.purposeCode) &&
        Objects.equals(chargeBearer, that.chargeBearer) &&
        Objects.equals(remittanceInformationUnstructured, that.remittanceInformationUnstructured) &&
        Objects.equals(remittanceInformationStructured, that.remittanceInformationStructured) &&
        Objects.equals(remittanceInformationStructuredArray, that.remittanceInformationStructuredArray);
  }

  @Override
  public int hashCode() {
    return Objects.hash(endToEndIdentification,
        instructionIdentification,
        debtorName,
        ultimateDebtor,
        instructedAmount,
        creditorAccount,
        creditorAgent,
        creditorAgentName,
        creditorName,
        creditorAddress,
        creditorId,
        ultimateCreditor,
        purposeCode,
        chargeBearer,
        remittanceInformationUnstructured,
        remittanceInformationStructured,
        remittanceInformationStructuredArray);
  }
}
