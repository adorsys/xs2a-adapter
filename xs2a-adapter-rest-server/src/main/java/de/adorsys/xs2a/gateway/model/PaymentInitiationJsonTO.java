package de.adorsys.xs2a.gateway.model;

import javax.annotation.Generated;

@Generated("xs2a-gateway-codegen")
public class PaymentInitiationJsonTO {
  private String endToEndIdentification;

  private AccountReferenceTO debtorAccount;

  private AmountTO instructedAmount;

  private AccountReferenceTO creditorAccount;

  private String creditorAgent;

  private String creditorAgentName;

  private String creditorName;

  private AddressTO creditorAddress;

  private String remittanceInformationUnstructured;

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
}
