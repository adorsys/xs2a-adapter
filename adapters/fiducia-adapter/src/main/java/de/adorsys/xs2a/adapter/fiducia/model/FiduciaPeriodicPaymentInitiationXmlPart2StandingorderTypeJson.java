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

package de.adorsys.xs2a.adapter.fiducia.model;

import de.adorsys.xs2a.adapter.api.model.FrequencyCode;

import java.time.LocalDate;
import java.util.Objects;

public class FiduciaPeriodicPaymentInitiationXmlPart2StandingorderTypeJson {
    private LocalDate startDate;

    private LocalDate endDate;

    private FiduciaExecutionRule executionRule;

    private FrequencyCode frequency;

    private FiduciaDayOfExecution dayOfExecution;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public FiduciaExecutionRule getExecutionRule() {
        return executionRule;
    }

    public void setExecutionRule(FiduciaExecutionRule executionRule) {
        this.executionRule = executionRule;
    }

    public FrequencyCode getFrequency() {
        return frequency;
    }

    public void setFrequency(FrequencyCode frequency) {
        this.frequency = frequency;
    }

    public FiduciaDayOfExecution getDayOfExecution() {
        return dayOfExecution;
    }

    public void setDayOfExecution(FiduciaDayOfExecution dayOfExecution) {
        this.dayOfExecution = dayOfExecution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FiduciaPeriodicPaymentInitiationXmlPart2StandingorderTypeJson that = (FiduciaPeriodicPaymentInitiationXmlPart2StandingorderTypeJson) o;
        return Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(executionRule, that.executionRule) &&
            Objects.equals(frequency, that.frequency) &&
            Objects.equals(dayOfExecution, that.dayOfExecution);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate,
            endDate,
            executionRule,
            frequency,
            dayOfExecution);
    }
}
