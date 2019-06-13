package de.adorsys.xs2a.adapter.service.ais;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.adorsys.xs2a.adapter.service.model.Link;

import java.time.LocalDate;
import java.util.Map;

public class ConsentInformation {
    // required
    private AccountAccess access;
    private Boolean recurringIndicator;
    private LocalDate validUntil;
    private Integer frequencyPerDay;
    private LocalDate lastActionDate;
    private ConsentStatus consentStatus;
    // optional
    @JsonProperty("_links")
    private Map<String, Link> links;

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

    public LocalDate getLastActionDate() {
        return lastActionDate;
    }

    public void setLastActionDate(LocalDate lastActionDate) {
        this.lastActionDate = lastActionDate;
    }

    public ConsentStatus getConsentStatus() {
        return consentStatus;
    }

    public void setConsentStatus(ConsentStatus consentStatus) {
        this.consentStatus = consentStatus;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }
}
