package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;
import java.time.LocalDate;
import java.util.List;

@Generated("xs2a-adapter-codegen")
public class StandingOrderDetailsTO {
    private LocalDate startDate;

    private FrequencyCodeTO frequency;

    private LocalDate endDate;

    private ExecutionRuleTO executionRule;

    private Boolean withinAMonthFlag;

    private List<MonthsOfExecutionTO> monthsOfExecution;

    private Integer multiplicator;

    private DayOfExecutionTO dayOfExecution;

    private AmountTO limitAmount;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public FrequencyCodeTO getFrequency() {
        return frequency;
    }

    public void setFrequency(FrequencyCodeTO frequency) {
        this.frequency = frequency;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ExecutionRuleTO getExecutionRule() {
        return executionRule;
    }

    public void setExecutionRule(ExecutionRuleTO executionRule) {
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

    public DayOfExecutionTO getDayOfExecution() {
        return dayOfExecution;
    }

    public void setDayOfExecution(DayOfExecutionTO dayOfExecution) {
        this.dayOfExecution = dayOfExecution;
    }

    public AmountTO getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(AmountTO limitAmount) {
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
