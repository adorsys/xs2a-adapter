package de.adorsys.xs2a.adapter.fiducia.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.adorsys.xs2a.adapter.api.model.Amount;
import de.adorsys.xs2a.adapter.api.model.DayOfExecution;
import de.adorsys.xs2a.adapter.api.model.FrequencyCode;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class FiduciaStandingOrderDetails {
    private LocalDate startDate;

    private FrequencyCode frequency;

    private LocalDate endDate;

    private FiduciaExecutionRule executionRule;

    private Boolean withinAMonthFlag;

    private List<MonthsOfExecution> monthsOfExecution;

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

    public FiduciaExecutionRule getExecutionRule() {
        return executionRule;
    }

    public void setExecutionRule(FiduciaExecutionRule executionRule) {
        this.executionRule = executionRule;
    }

    public Boolean getWithinAMonthFlag() {
        return withinAMonthFlag;
    }

    public void setWithinAMonthFlag(Boolean withinAMonthFlag) {
        this.withinAMonthFlag = withinAMonthFlag;
    }

    public List<MonthsOfExecution> getMonthsOfExecution() {
        return monthsOfExecution;
    }

    public void setMonthsOfExecution(List<MonthsOfExecution> monthsOfExecution) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FiduciaStandingOrderDetails that = (FiduciaStandingOrderDetails) o;
        return Objects.equals(startDate, that.startDate) &&
            Objects.equals(frequency, that.frequency) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(executionRule, that.executionRule) &&
            Objects.equals(withinAMonthFlag, that.withinAMonthFlag) &&
            Objects.equals(monthsOfExecution, that.monthsOfExecution) &&
            Objects.equals(multiplicator, that.multiplicator) &&
            Objects.equals(dayOfExecution, that.dayOfExecution) &&
            Objects.equals(limitAmount, that.limitAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate,
            frequency,
            endDate,
            executionRule,
            withinAMonthFlag,
            monthsOfExecution,
            multiplicator,
            dayOfExecution,
            limitAmount);
    }

    public enum MonthsOfExecution {
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

        MonthsOfExecution(String value) {
            this.value = value;
        }

        @JsonCreator
        public static MonthsOfExecution fromValue(String value) {
            for (MonthsOfExecution e : MonthsOfExecution.values()) {
                if (e.value.equals(value)) {
                    return e;
                }
            }
            return null;
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }
}
