package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-codegen")
public enum ExecutionRuleTO {
  FOLLOWING("following"),

  PRECEDING("preceding");

  private String value;

  ExecutionRuleTO(String value) {
    this.value = value;
  }

  @JsonCreator
  public static ExecutionRuleTO fromValue(String value) {
    for (ExecutionRuleTO e : ExecutionRuleTO.values()) {
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
