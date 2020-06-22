package de.adorsys.xs2a.adapter.api.model;

import javax.annotation.Generated;
import java.time.LocalDate;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class PeriodicPaymentInitiationXmlPart2StandingorderTypeJson {
    private LocalDate startDate;

    private LocalDate endDate;

    private ExecutionRule executionRule;

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

    public ExecutionRule getExecutionRule() {
        return executionRule;
    }

    public void setExecutionRule(ExecutionRule executionRule) {
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
        PeriodicPaymentInitiationXmlPart2StandingorderTypeJson that = (PeriodicPaymentInitiationXmlPart2StandingorderTypeJson) o;
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
