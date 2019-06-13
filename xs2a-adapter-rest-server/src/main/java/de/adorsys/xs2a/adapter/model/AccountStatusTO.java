package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-gateway-codegen")
public enum AccountStatusTO {
  ENABLED("enabled"),

  DELETED("deleted"),

  BLOCKED("blocked");

  private String value;

  AccountStatusTO(String value) {
    this.value = value;
  }

  @JsonCreator
  public static AccountStatusTO fromValue(String value) {
    for (AccountStatusTO e : AccountStatusTO.values()) {
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
