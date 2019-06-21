package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.time.LocalDate;
import java.util.Map;

@Generated("xs2a-codegen")
public class ConsentInformationResponse200JsonTO {
  private AccountAccessTO access;

  private Boolean recurringIndicator;

  private LocalDate validUntil;

  private Integer frequencyPerDay;

  private LocalDate lastActionDate;

  private ConsentStatusTO consentStatus;

  @JsonProperty("_links")
  private Map<String, HrefTypeTO> links;

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

  public LocalDate getLastActionDate() {
    return lastActionDate;
  }

  public void setLastActionDate(LocalDate lastActionDate) {
    this.lastActionDate = lastActionDate;
  }

  public ConsentStatusTO getConsentStatus() {
    return consentStatus;
  }

  public void setConsentStatus(ConsentStatusTO consentStatus) {
    this.consentStatus = consentStatus;
  }

  public Map<String, HrefTypeTO> getLinks() {
    return links;
  }

  public void setLinks(Map<String, HrefTypeTO> links) {
    this.links = links;
  }
}
