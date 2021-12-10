package de.adorsys.xs2a.adapter.v139.api.model;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.time.LocalDate;
import java.util.Objects;
import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public class Consents {
  private AccountAccess access;

  private Boolean recurringIndicator;

  private LocalDate validUntil;

  private Integer frequencyPerDay;

  private Boolean combinedServiceIndicator;

  public AccountAccess getAccess() {
    return access;
  }

  public void setAccess(AccountAccess access) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Consents that = (Consents) o;
    return Objects.equals(access, that.access) &&
        Objects.equals(recurringIndicator, that.recurringIndicator) &&
        Objects.equals(validUntil, that.validUntil) &&
        Objects.equals(frequencyPerDay, that.frequencyPerDay) &&
        Objects.equals(combinedServiceIndicator, that.combinedServiceIndicator);
  }

  @Override
  public int hashCode() {
    return Objects.hash(access,
        recurringIndicator,
        validUntil,
        frequencyPerDay,
        combinedServiceIndicator);
  }
}
