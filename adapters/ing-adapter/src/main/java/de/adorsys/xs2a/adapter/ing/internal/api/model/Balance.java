package de.adorsys.xs2a.adapter.ing.internal.api.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class Balance {
    private String balanceType;

    private Amount balanceAmount;

    private OffsetDateTime lastChangeDateTime;

    private LocalDate referenceDate;

    public String getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }

    public Amount getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Amount balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public OffsetDateTime getLastChangeDateTime() {
        return lastChangeDateTime;
    }

    public void setLastChangeDateTime(OffsetDateTime lastChangeDateTime) {
        this.lastChangeDateTime = lastChangeDateTime;
    }

    public LocalDate getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(LocalDate referenceDate) {
        this.referenceDate = referenceDate;
    }
}
