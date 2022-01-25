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

package de.adorsys.xs2a.adapter.ing.model;

import java.time.LocalDate;

public class IngPaymentInstruction {
    private String endToEndIdentification;

    private IngDebtorAccount debtorAccount;

    private IngAmount instructedAmount;

    private IngCreditorAccount creditorAccount;

    private String creditorAgent;

    private String creditorName;

    private IngAddress creditorAddress;

    private String chargeBearer;

    private String remittanceInformationUnstructured;

    private IngClearingSystemMemberIdentification clearingSystemMemberIdentification;

    private String debtorName;

    private String debtorAgent;

    private String instructionPriority;

    private String serviceLevelCode;

    private String localInstrumentCode;

    private String categoryPurposeCode;

    private LocalDate requestedExecutionDate;

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

    public String getChargeBearer() {
        return chargeBearer;
    }

    public void setChargeBearer(String chargeBearer) {
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

    public String getInstructionPriority() {
        return instructionPriority;
    }

    public void setInstructionPriority(String instructionPriority) {
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
}
