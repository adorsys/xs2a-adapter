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
import java.time.OffsetDateTime;

public class IngTransaction {
    private String transactionId;

    private String endToEndId;

    private LocalDate bookingDate;

    private LocalDate valueDate;

    private OffsetDateTime executionDateTime;

    private IngAmount transactionAmount;

    private String creditorName;

    private IngCounterpartyAccount creditorAccount;

    private String debtorName;

    private IngCounterpartyAccount debtorAccount;

    private String transactionType;

    private String remittanceInformationUnstructured;

    private IngTransactionRemittanceInformationStructured remittanceInformationStructured;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getEndToEndId() {
        return endToEndId;
    }

    public void setEndToEndId(String endToEndId) {
        this.endToEndId = endToEndId;
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

    public OffsetDateTime getExecutionDateTime() {
        return executionDateTime;
    }

    public void setExecutionDateTime(OffsetDateTime executionDateTime) {
        this.executionDateTime = executionDateTime;
    }

    public IngAmount getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(IngAmount transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    public IngCounterpartyAccount getCreditorAccount() {
        return creditorAccount;
    }

    public void setCreditorAccount(IngCounterpartyAccount creditorAccount) {
        this.creditorAccount = creditorAccount;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public IngCounterpartyAccount getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(IngCounterpartyAccount debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getRemittanceInformationUnstructured() {
        return remittanceInformationUnstructured;
    }

    public void setRemittanceInformationUnstructured(String remittanceInformationUnstructured) {
        this.remittanceInformationUnstructured = remittanceInformationUnstructured;
    }

    public IngTransactionRemittanceInformationStructured getRemittanceInformationStructured() {
        return remittanceInformationStructured;
    }

    public void setRemittanceInformationStructured(IngTransactionRemittanceInformationStructured remittanceInformationStructured) {
        this.remittanceInformationStructured = remittanceInformationStructured;
    }
}
