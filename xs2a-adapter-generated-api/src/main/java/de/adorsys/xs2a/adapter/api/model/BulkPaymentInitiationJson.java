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
public class BulkPaymentInitiationJson {
    private Boolean batchBookingPreferred;

    private AccountReference debtorAccount;

    private LocalDate requestedExecutionDate;

    private OffsetDateTime requestedExecutionTime;

    private List<PaymentInitiationBulkElementJson> payments;

    private String debtorName;

    public Boolean getBatchBookingPreferred() {
        return batchBookingPreferred;
    }

    public void setBatchBookingPreferred(Boolean batchBookingPreferred) {
        this.batchBookingPreferred = batchBookingPreferred;
    }

    public AccountReference getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(AccountReference debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    public LocalDate getRequestedExecutionDate() {
        return requestedExecutionDate;
    }

    public void setRequestedExecutionDate(LocalDate requestedExecutionDate) {
        this.requestedExecutionDate = requestedExecutionDate;
    }

    public OffsetDateTime getRequestedExecutionTime() {
        return requestedExecutionTime;
    }

    public void setRequestedExecutionTime(OffsetDateTime requestedExecutionTime) {
        this.requestedExecutionTime = requestedExecutionTime;
    }

    public List<PaymentInitiationBulkElementJson> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentInitiationBulkElementJson> payments) {
        this.payments = payments;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BulkPaymentInitiationJson that = (BulkPaymentInitiationJson) o;
        return Objects.equals(batchBookingPreferred, that.batchBookingPreferred) &&
            Objects.equals(debtorAccount, that.debtorAccount) &&
            Objects.equals(requestedExecutionDate, that.requestedExecutionDate) &&
            Objects.equals(requestedExecutionTime, that.requestedExecutionTime) &&
            Objects.equals(payments, that.payments) &&
            Objects.equals(debtorName, that.debtorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batchBookingPreferred,
            debtorAccount,
            requestedExecutionDate,
            requestedExecutionTime,
            payments,
            debtorName);
    }
}
