package de.adorsys.xs2a.adapter.model;

import javax.annotation.Generated;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Generated("xs2a-codegen")
public class BalanceTO {
  private AmountTO balanceAmount;

  private BalanceTypeTO balanceType;

  private Boolean creditLimitIncluded;

  private OffsetDateTime lastChangeDateTime;

  private LocalDate referenceDate;

  private String lastCommittedTransaction;

  public AmountTO getBalanceAmount() {
    return balanceAmount;
  }

  public void setBalanceAmount(AmountTO balanceAmount) {
    this.balanceAmount = balanceAmount;
  }

  public BalanceTypeTO getBalanceType() {
    return balanceType;
  }

  public void setBalanceType(BalanceTypeTO balanceType) {
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
}
