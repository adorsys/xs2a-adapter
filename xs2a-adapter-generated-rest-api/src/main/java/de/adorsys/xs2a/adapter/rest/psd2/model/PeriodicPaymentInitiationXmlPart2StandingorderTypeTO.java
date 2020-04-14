package de.adorsys.xs2a.adapter.rest.psd2.model;

import javax.annotation.Generated;
import java.time.LocalDate;

@Generated("xs2a-adapter-codegen")
public class PeriodicPaymentInitiationXmlPart2StandingorderTypeTO {
    private LocalDate startDate;

    private LocalDate endDate;

    private String executionRule;

    private String frequency;

    private String dayOfExecution;

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

    public String getExecutionRule() {
        return executionRule;
    }

    public void setExecutionRule(String executionRule) {
        this.executionRule = executionRule;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDayOfExecution() {
        return dayOfExecution;
    }

    public void setDayOfExecution(String dayOfExecution) {
        this.dayOfExecution = dayOfExecution;
    }
}
