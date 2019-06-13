package de.adorsys.xs2a.adapter.model;

import javax.annotation.Generated;
import java.time.LocalDate;

@Generated("xs2a-gateway-codegen")
public class ConsentsTO {
  private AccountAccessTO access;

  private Boolean recurringIndicator;

  private LocalDate validUntil;

  private Integer frequencyPerDay;

  private Boolean combinedServiceIndicator;

  public AccountAccessTO getAccess() {
    return access;
  }

  public void setAccess(AccountAccessTO access) {
    this.access = access;
  }

  public Boolean getRecurringIndicator() {
    return recurringIndicator;
  }

  public void setRecurringIndicator(Boolean recurringIndicator) {
    this.recurringIndicator = recurringIndicator;
  }

  public LocalDate getValidUntil() {
    return validUntil;
  }

  public void setValidUntil(LocalDate validUntil) {
    this.validUntil = validUntil;
  }

  public Integer getFrequencyPerDay() {
    return frequencyPerDay;
  }

  public void setFrequencyPerDay(Integer frequencyPerDay) {
    this.frequencyPerDay = frequencyPerDay;
  }

  public Boolean getCombinedServiceIndicator() {
    return combinedServiceIndicator;
  }

  public void setCombinedServiceIndicator(Boolean combinedServiceIndicator) {
    this.combinedServiceIndicator = combinedServiceIndicator;
  }
}
