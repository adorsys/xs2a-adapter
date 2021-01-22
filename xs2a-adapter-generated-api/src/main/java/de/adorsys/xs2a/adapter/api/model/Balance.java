package de.adorsys.xs2a.adapter.api.model;

import javax.annotation.Generated;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class Balance {
    private Amount balanceAmount;

    private BalanceType balanceType;

    private Boolean creditLimitIncluded;

    private OffsetDateTime lastChangeDateTime;

    private LocalDate referenceDate;

    private String lastCommittedTransaction;

    public Amount getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Amount balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public BalanceType getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(BalanceType balanceType) {
        this.balanceType = balanceType;
    }

    public Boolean getCreditLimitIncluded() {
        return creditLimitIncluded;
    }

    public void setCreditLimitIncluded(Boolean creditLimitIncluded) {
        this.creditLimitIncluded = creditLimitIncluded;
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

    public String getLastCommittedTransaction() {
        return lastCommittedTransaction;
    }

    public void setLastCommittedTransaction(String lastCommittedTransaction) {
        this.lastCommittedTransaction = lastCommittedTransaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Balance that = (Balance) o;
        return Objects.equals(balanceAmount, that.balanceAmount) &&
            Objects.equals(balanceType, that.balanceType) &&
            Objects.equals(creditLimitIncluded, that.creditLimitIncluded) &&
            Objects.equals(lastChangeDateTime, that.lastChangeDateTime) &&
            Objects.equals(referenceDate, that.referenceDate) &&
            Objects.equals(lastCommittedTransaction, that.lastCommittedTransaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balanceAmount,
            balanceType,
            creditLimitIncluded,
            lastChangeDateTime,
            referenceDate,
            lastCommittedTransaction);
    }
}
