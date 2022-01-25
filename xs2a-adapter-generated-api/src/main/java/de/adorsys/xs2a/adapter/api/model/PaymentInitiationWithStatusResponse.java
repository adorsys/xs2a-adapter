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

import javax.annotation.Generated;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class PaymentInitiationWithStatusResponse {
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

    private String creditorId;

    private String ultimateCreditor;

    private PurposeCode purposeCode;

    private ChargeBearer chargeBearer;

    private String remittanceInformationUnstructured;

    private String remittanceInformationStructured;

    private List<String> remittanceInformationStructuredArray;

    private LocalDate requestedExecutionDate;

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

    public LocalDate getRequestedExecutionDate() {
        return requestedExecutionDate;
    }

    public void setRequestedExecutionDate(LocalDate requestedExecutionDate) {
        this.requestedExecutionDate = requestedExecutionDate;
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
        PaymentInitiationWithStatusResponse that = (PaymentInitiationWithStatusResponse) o;
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
            Objects.equals(creditorId, that.creditorId) &&
            Objects.equals(ultimateCreditor, that.ultimateCreditor) &&
            Objects.equals(purposeCode, that.purposeCode) &&
            Objects.equals(chargeBearer, that.chargeBearer) &&
            Objects.equals(remittanceInformationUnstructured, that.remittanceInformationUnstructured) &&
            Objects.equals(remittanceInformationStructured, that.remittanceInformationStructured) &&
            Objects.equals(remittanceInformationStructuredArray, that.remittanceInformationStructuredArray) &&
            Objects.equals(requestedExecutionDate, that.requestedExecutionDate) &&
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
            creditorId,
            ultimateCreditor,
            purposeCode,
            chargeBearer,
            remittanceInformationUnstructured,
            remittanceInformationStructured,
            remittanceInformationStructuredArray,
            requestedExecutionDate,
            transactionStatus,
            tppMessage);
    }
}
