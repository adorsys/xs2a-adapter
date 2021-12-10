package de.adorsys.xs2a.adapter.v139.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.lang.Override;
import java.lang.String;
import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum ExecutionRule {
  FOLLOWING("following"),

  PRECEDING("preceding");

  private String value;

  ExecutionRule(String value) {
    this.value = value;
  }

  @JsonCreator
  public static ExecutionRule fromValue(String value) {
    for (ExecutionRule e : ExecutionRule.values()) {
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
