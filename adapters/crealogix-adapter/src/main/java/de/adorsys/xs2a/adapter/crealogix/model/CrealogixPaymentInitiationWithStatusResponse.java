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

package de.adorsys.xs2a.adapter.crealogix.model;

import de.adorsys.xs2a.adapter.api.model.AccountReference;
import de.adorsys.xs2a.adapter.api.model.Address;
import de.adorsys.xs2a.adapter.api.model.Amount;
import de.adorsys.xs2a.adapter.api.model.TransactionStatus;

import java.time.LocalDate;
import java.util.Objects;

public class CrealogixPaymentInitiationWithStatusResponse {
    private String endToEndIdentification;
    private AccountReference debtorAccount;
    private Amount instructedAmount;
    private AccountReference creditorAccount;
    private String creditorAgent;
    private String creditorName;
    private Address creditorAddress;
    private String remittanceInformationUnstructured;
    private LocalDate requestedExecutionDate;
    private TransactionStatus transactionStatus;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrealogixPaymentInitiationWithStatusResponse that = (CrealogixPaymentInitiationWithStatusResponse) o;
        return Objects.equals(endToEndIdentification, that.endToEndIdentification) &&
            Objects.equals(debtorAccount, that.debtorAccount) &&
            Objects.equals(instructedAmount, that.instructedAmount) &&
            Objects.equals(creditorAccount, that.creditorAccount) &&
            Objects.equals(creditorAgent, that.creditorAgent) &&
            Objects.equals(creditorName, that.creditorName) &&
            Objects.equals(creditorAddress, that.creditorAddress) &&
            Objects.equals(remittanceInformationUnstructured, that.remittanceInformationUnstructured) &&
            Objects.equals(requestedExecutionDate, that.requestedExecutionDate) &&
            transactionStatus == that.transactionStatus;
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
            requestedExecutionDate,
            transactionStatus);
    }
}
