package de.adorsys.xs2a.adapter.model;

import javax.annotation.Generated;
import java.time.LocalDate;

@Generated("xs2a-codegen")
public class PeriodicPaymentInitiationXmlPart2StandingorderTypeJsonTO {
  private LocalDate startDate;

  private LocalDate endDate;

  private ExecutionRuleTO executionRule;

  private FrequencyCodeTO frequency;

  private DayOfExecutionTO dayOfExecution;

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

  public ExecutionRuleTO getExecutionRule() {
    return executionRule;
  }

  public void setExecutionRule(ExecutionRuleTO executionRule) {
    this.executionRule = executionRule;
  }

  public FrequencyCodeTO getFrequency() {
    return frequency;
  }

  public void setFrequency(FrequencyCodeTO frequency) {
    this.frequency = frequency;
  }

  public DayOfExecutionTO getDayOfExecution() {
    return dayOfExecution;
  }

  public void setDayOfExecution(DayOfExecutionTO dayOfExecution) {
    this.dayOfExecution = dayOfExecution;
  }
}
