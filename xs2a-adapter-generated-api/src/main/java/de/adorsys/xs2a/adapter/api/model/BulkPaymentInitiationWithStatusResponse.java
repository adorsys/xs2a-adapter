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
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class BulkPaymentInitiationWithStatusResponse {
    private Boolean batchBookingPreferred;

    private LocalDate requestedExecutionDate;

    private OffsetDateTime acceptorTransactionDateTime;

    private AccountReference debtorAccount;

    private String paymentInformationId;

    private List<PaymentInitiationBulkElementJson> payments;

    private TransactionStatus transactionStatus;

    private List<TppMessageGeneric> tppMessage;

    public Boolean getBatchBookingPreferred() {
        return batchBookingPreferred;
    }

    public void setBatchBookingPreferred(Boolean batchBookingPreferred) {
        this.batchBookingPreferred = batchBookingPreferred;
    }

    public LocalDate getRequestedExecutionDate() {
        return requestedExecutionDate;
    }

    public void setRequestedExecutionDate(LocalDate requestedExecutionDate) {
        this.requestedExecutionDate = requestedExecutionDate;
    }

    public OffsetDateTime getAcceptorTransactionDateTime() {
        return acceptorTransactionDateTime;
    }

    public void setAcceptorTransactionDateTime(OffsetDateTime acceptorTransactionDateTime) {
        this.acceptorTransactionDateTime = acceptorTransactionDateTime;
    }

    public AccountReference getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(AccountReference debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    public String getPaymentInformationId() {
        return paymentInformationId;
    }

    public void setPaymentInformationId(String paymentInformationId) {
        this.paymentInformationId = paymentInformationId;
    }

    public List<PaymentInitiationBulkElementJson> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentInitiationBulkElementJson> payments) {
        this.payments = payments;
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
        BulkPaymentInitiationWithStatusResponse that = (BulkPaymentInitiationWithStatusResponse) o;
        return Objects.equals(batchBookingPreferred, that.batchBookingPreferred) &&
            Objects.equals(requestedExecutionDate, that.requestedExecutionDate) &&
            Objects.equals(acceptorTransactionDateTime, that.acceptorTransactionDateTime) &&
            Objects.equals(debtorAccount, that.debtorAccount) &&
            Objects.equals(paymentInformationId, that.paymentInformationId) &&
            Objects.equals(payments, that.payments) &&
            Objects.equals(transactionStatus, that.transactionStatus) &&
            Objects.equals(tppMessage, that.tppMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batchBookingPreferred,
            requestedExecutionDate,
            acceptorTransactionDateTime,
            debtorAccount,
            paymentInformationId,
            payments,
            transactionStatus,
            tppMessage);
    }
}
