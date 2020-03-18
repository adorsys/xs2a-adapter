package de.adorsys.xs2a.adapter.rest.psd2.model;

import java.time.LocalDate;
import java.util.List;

public class StandingOrderDetailsTO {
    private LocalDate startDate;

    private String frequency;

    private LocalDate endDate;

    private String executionRule;

    private Boolean withinAMonthFlag;

    private List<String> monthsOfExecution;

    private Integer multiplicator;

    private String dayOfExecution;

    private AmountTO limitAmount;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
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

    public Boolean getWithinAMonthFlag() {
        return withinAMonthFlag;
    }

    public void setWithinAMonthFlag(Boolean withinAMonthFlag) {
        this.withinAMonthFlag = withinAMonthFlag;
    }

    public List<String> getMonthsOfExecution() {
        return monthsOfExecution;
    }

    public void setMonthsOfExecution(List<String> monthsOfExecution) {
        this.monthsOfExecution = monthsOfExecution;
    }

    public Integer getMultiplicator() {
        return multiplicator;
    }

    public void setMultiplicator(Integer multiplicator) {
        this.multiplicator = multiplicator;
    }

    public String getDayOfExecution() {
        return dayOfExecution;
    }

    public void setDayOfExecution(String dayOfExecution) {
        this.dayOfExecution = dayOfExecution;
    }

    public AmountTO getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(AmountTO limitAmount) {
        this.limitAmount = limitAmount;
    }
}
