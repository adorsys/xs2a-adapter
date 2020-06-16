package de.adorsys.xs2a.adapter.comdirect.model;

import de.adorsys.xs2a.adapter.api.model.Amount;
import de.adorsys.xs2a.adapter.api.model.BalanceType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ComdirectBalance {
    private Amount balanceAmount;
    private BalanceType balanceType;
    private LocalDateTime lastChangeDateTime;
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

    public LocalDateTime getLastChangeDateTime() {
        return lastChangeDateTime;
    }

    public void setLastChangeDateTime(LocalDateTime lastChangeDateTime) {
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
}
