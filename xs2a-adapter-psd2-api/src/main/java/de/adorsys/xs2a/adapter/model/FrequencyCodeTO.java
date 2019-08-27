package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-codegen")
public enum FrequencyCodeTO {
  DAILY("Daily"),

  WEEKLY("Weekly"),

  EVERYTWOWEEKS("EveryTwoWeeks"),

  MONTHLY("Monthly"),

  EVERYTWOMONTHS("EveryTwoMonths"),

  QUARTERLY("Quarterly"),

  SEMIANNUAL("SemiAnnual"),

  ANNUAL("Annual");

  private String value;

  FrequencyCodeTO(String value) {
    this.value = value;
  }

  @JsonCreator
  public static FrequencyCodeTO fromValue(String value) {
    for (FrequencyCodeTO e : FrequencyCodeTO.values()) {
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
