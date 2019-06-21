package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Generated("xs2a-codegen")
public class TransactionDetailsTO {
  private String transactionId;

  private String entryReference;

  private String endToEndId;

  private String mandateId;

  private String checkId;

  private String creditorId;

  private LocalDate bookingDate;

  private LocalDate valueDate;

  private AmountTO transactionAmount;

  private List<ReportExchangeRateTO> currencyExchange;

  private String creditorName;

  private AccountReferenceTO creditorAccount;

  private String ultimateCreditor;

  private String debtorName;

  private AccountReferenceTO debtorAccount;

  private String ultimateDebtor;

  private String remittanceInformationUnstructured;

  private String remittanceInformationStructured;

  private String additionalInformation;

  private PurposeCodeTO purposeCode;

  private String bankTransactionCode;

  private String proprietaryBankTransactionCode;

  @JsonProperty("_links")
  private Map<String, HrefTypeTO> links;

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getEntryReference() {
    return entryReference;
  }

  public void setEntryReference(String entryReference) {
    this.entryReference = entryReference;
  }

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

  public AmountTO getTransactionAmount() {
    return transactionAmount;
  }

  public void setTransactionAmount(AmountTO transactionAmount) {
    this.transactionAmount = transactionAmount;
  }

  public List<ReportExchangeRateTO> getCurrencyExchange() {
    return currencyExchange;
  }

  public void setCurrencyExchange(List<ReportExchangeRateTO> currencyExchange) {
    this.currencyExchange = currencyExchange;
  }

  public String getCreditorName() {
    return creditorName;
  }

  public void setCreditorName(String creditorName) {
    this.creditorName = creditorName;
  }

  public AccountReferenceTO getCreditorAccount() {
    return creditorAccount;
  }

  public void setCreditorAccount(AccountReferenceTO creditorAccount) {
    this.creditorAccount = creditorAccount;
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

  public AccountReferenceTO getDebtorAccount() {
    return debtorAccount;
  }

  public void setDebtorAccount(AccountReferenceTO debtorAccount) {
    this.debtorAccount = debtorAccount;
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

  public String getRemittanceInformationStructured() {
    return remittanceInformationStructured;
  }

  public void setRemittanceInformationStructured(String remittanceInformationStructured) {
    this.remittanceInformationStructured = remittanceInformationStructured;
  }

  public String getAdditionalInformation() {
    return additionalInformation;
  }

  public void setAdditionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
  }

  public PurposeCodeTO getPurposeCode() {
    return purposeCode;
  }

  public void setPurposeCode(PurposeCodeTO purposeCode) {
    this.purposeCode = purposeCode;
  }

  public String getBankTransactionCode() {
    return bankTransactionCode;
  }

  public void setBankTransactionCode(String bankTransactionCode) {
    this.bankTransactionCode = bankTransactionCode;
  }

  public String getProprietaryBankTransactionCode() {
    return proprietaryBankTransactionCode;
  }

  public void setProprietaryBankTransactionCode(String proprietaryBankTransactionCode) {
    this.proprietaryBankTransactionCode = proprietaryBankTransactionCode;
  }

  public Map<String, HrefTypeTO> getLinks() {
    return links;
  }

  public void setLinks(Map<String, HrefTypeTO> links) {
    this.links = links;
  }
}
