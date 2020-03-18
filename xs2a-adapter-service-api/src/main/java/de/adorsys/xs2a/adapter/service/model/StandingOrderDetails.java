package de.adorsys.xs2a.adapter.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.time.LocalDate;
import java.util.List;

public class StandingOrderDetails {
    private LocalDate startDate;

    private FrequencyCode frequency;

    private LocalDate endDate;

    private ExecutionRule executionRule;

    private Boolean withinAMonthFlag;

    private List<MonthsOfExecutionTO> monthsOfExecution;

    private Integer multiplicator;

    private DayOfExecution dayOfExecution;

    private Amount limitAmount;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public FrequencyCode getFrequency() {
        return frequency;
    }

    public void setFrequency(FrequencyCode frequency) {
        this.frequency = frequency;
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

    public Boolean getWithinAMonthFlag() {
        return withinAMonthFlag;
    }

    public void setWithinAMonthFlag(Boolean withinAMonthFlag) {
        this.withinAMonthFlag = withinAMonthFlag;
    }

    public List<MonthsOfExecutionTO> getMonthsOfExecution() {
        return monthsOfExecution;
    }

    public void setMonthsOfExecution(List<MonthsOfExecutionTO> monthsOfExecution) {
        this.monthsOfExecution = monthsOfExecution;
    }

    public Integer getMultiplicator() {
        return multiplicator;
    }

    public void setMultiplicator(Integer multiplicator) {
        this.multiplicator = multiplicator;
    }

    public DayOfExecution getDayOfExecution() {
        return dayOfExecution;
    }

    public void setDayOfExecution(DayOfExecution dayOfExecution) {
        this.dayOfExecution = dayOfExecution;
    }

    public Amount getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(Amount limitAmount) {
        this.limitAmount = limitAmount;
    }

    public enum MonthsOfExecutionTO {
        _1("1"),

        _2("2"),

        _3("3"),

        _4("4"),

        _5("5"),

        _6("6"),

        _7("7"),

        _8("8"),

        _9("9"),

        _10("10"),

        _11("11"),

        _12("12");

        private String value;

        MonthsOfExecutionTO(String value) {
            this.value = value;
        }

        @JsonCreator
        public static MonthsOfExecutionTO fromValue(String value) {
            for (MonthsOfExecutionTO e : MonthsOfExecutionTO.values()) {
                if (e.value.equals(value)) {
                    return e;
                }
            }
            throw new IllegalArgumentException(value);
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }
}
