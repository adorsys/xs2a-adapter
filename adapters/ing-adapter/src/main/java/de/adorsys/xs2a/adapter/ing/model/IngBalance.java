package de.adorsys.xs2a.adapter.ing.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class IngBalance {
    private String balanceType;

    private IngAmount balanceAmount;

    private OffsetDateTime lastChangeDateTime;

    private LocalDate referenceDate;

    public String getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }

    public IngAmount getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(IngAmount balanceAmount) {
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
