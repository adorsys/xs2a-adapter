package de.adorsys.xs2a.adapter.fiducia.model;

import de.adorsys.xs2a.adapter.api.model.DayOfExecution;
import de.adorsys.xs2a.adapter.api.model.FrequencyCode;

import java.time.LocalDate;
import java.util.Objects;

public class FiduciaPeriodicPaymentInitiationXmlPart2StandingorderTypeJson {
    private LocalDate startDate;

    private LocalDate endDate;

    private FiduciaExecutionRule executionRule;

    private FrequencyCode frequency;

    private DayOfExecution dayOfExecution;

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

    public DayOfExecution getDayOfExecution() {
        return dayOfExecution;
    }

    public void setDayOfExecution(DayOfExecution dayOfExecution) {
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
