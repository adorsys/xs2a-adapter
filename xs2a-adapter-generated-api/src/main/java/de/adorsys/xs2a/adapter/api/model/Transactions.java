/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class Transactions {
    private String transactionId;

    private String entryReference;

    private String endToEndId;

    private Boolean batchIndicator;

    private Integer batchNumberOfTransactions;

    private String mandateId;

    private String checkId;

    private String creditorId;

    private LocalDate bookingDate;

    private LocalDate valueDate;

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

    private List<EntryDetailsElement> entryDetails;

    private String additionalInformation;

    private AdditionalInformationStructured additionalInformationStructured;

    private PurposeCode purposeCode;

    private String bankTransactionCode;

    private String proprietaryBankTransactionCode;

    private Balance balanceAfterTransaction;

    @JsonProperty("_links")
    private Map<String, HrefType> links;

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

    public Boolean getBatchIndicator() {
        return batchIndicator;
    }

    public void setBatchIndicator(Boolean batchIndicator) {
        this.batchIndicator = batchIndicator;
    }

    public Integer getBatchNumberOfTransactions() {
        return batchNumberOfTransactions;
    }

    public void setBatchNumberOfTransactions(Integer batchNumberOfTransactions) {
        this.batchNumberOfTransactions = batchNumberOfTransactions;
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

    public List<EntryDetailsElement> getEntryDetails() {
        return entryDetails;
    }

    public void setEntryDetails(List<EntryDetailsElement> entryDetails) {
        this.entryDetails = entryDetails;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public AdditionalInformationStructured getAdditionalInformationStructured() {
        return additionalInformationStructured;
    }

    public void setAdditionalInformationStructured(
        AdditionalInformationStructured additionalInformationStructured) {
        this.additionalInformationStructured = additionalInformationStructured;
    }

    public PurposeCode getPurposeCode() {
        return purposeCode;
    }

    public void setPurposeCode(PurposeCode purposeCode) {
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

    public Balance getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public void setBalanceAfterTransaction(Balance balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public Map<String, HrefType> getLinks() {
        return links;
    }

    public void setLinks(Map<String, HrefType> links) {
        this.links = links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transactions that = (Transactions) o;
        return Objects.equals(transactionId, that.transactionId) &&
            Objects.equals(entryReference, that.entryReference) &&
            Objects.equals(endToEndId, that.endToEndId) &&
            Objects.equals(batchIndicator, that.batchIndicator) &&
            Objects.equals(batchNumberOfTransactions, that.batchNumberOfTransactions) &&
            Objects.equals(mandateId, that.mandateId) &&
            Objects.equals(checkId, that.checkId) &&
            Objects.equals(creditorId, that.creditorId) &&
            Objects.equals(bookingDate, that.bookingDate) &&
            Objects.equals(valueDate, that.valueDate) &&
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
            Objects.equals(entryDetails, that.entryDetails) &&
            Objects.equals(additionalInformation, that.additionalInformation) &&
            Objects.equals(additionalInformationStructured, that.additionalInformationStructured) &&
            Objects.equals(purposeCode, that.purposeCode) &&
            Objects.equals(bankTransactionCode, that.bankTransactionCode) &&
            Objects.equals(proprietaryBankTransactionCode, that.proprietaryBankTransactionCode) &&
            Objects.equals(balanceAfterTransaction, that.balanceAfterTransaction) &&
            Objects.equals(links, that.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId,
            entryReference,
            endToEndId,
            batchIndicator,
            batchNumberOfTransactions,
            mandateId,
            checkId,
            creditorId,
            bookingDate,
            valueDate,
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
            entryDetails,
            additionalInformation,
            additionalInformationStructured,
            purposeCode,
            bankTransactionCode,
            proprietaryBankTransactionCode,
            balanceAfterTransaction,
            links);
    }
}
