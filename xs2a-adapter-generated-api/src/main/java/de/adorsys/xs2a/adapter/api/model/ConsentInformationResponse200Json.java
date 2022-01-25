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

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class ConsentInformationResponse200Json {
    private AccountAccess access;

    private Boolean recurringIndicator;

    private LocalDate validUntil;

    private Integer frequencyPerDay;

    private LocalDate lastActionDate;

    private ConsentStatus consentStatus;

    @JsonProperty("_links")
    private Map<String, HrefType> links;

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

    public Map<String, HrefType> getLinks() {
        return links;
    }

    public void setLinks(Map<String, HrefType> links) {
        this.links = links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsentInformationResponse200Json that = (ConsentInformationResponse200Json) o;
        return Objects.equals(access, that.access) &&
            Objects.equals(recurringIndicator, that.recurringIndicator) &&
            Objects.equals(validUntil, that.validUntil) &&
            Objects.equals(frequencyPerDay, that.frequencyPerDay) &&
            Objects.equals(lastActionDate, that.lastActionDate) &&
            Objects.equals(consentStatus, that.consentStatus) &&
            Objects.equals(links, that.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(access,
            recurringIndicator,
            validUntil,
            frequencyPerDay,
            lastActionDate,
            consentStatus,
            links);
    }
}
