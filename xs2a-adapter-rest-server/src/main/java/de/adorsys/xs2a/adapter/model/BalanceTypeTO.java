package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-gateway-codegen")
public enum BalanceTypeTO {
  CLOSINGBOOKED("closingBooked"),

  EXPECTED("expected"),

  AUTHORISED("authorised"),

  OPENINGBOOKED("openingBooked"),

  INTERIMAVAILABLE("interimAvailable"),

  INTERIMBOOKED("interimBooked"),

  FORWARDAVAILABLE("forwardAvailable"),

  NONINVOICED("nonInvoiced");

  private String value;

  BalanceTypeTO(String value) {
    this.value = value;
  }

  @JsonCreator
  public static BalanceTypeTO fromValue(String value) {
    for (BalanceTypeTO e : BalanceTypeTO.values()) {
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
