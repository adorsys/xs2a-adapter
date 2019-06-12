package de.adorsys.xs2a.gateway.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-gateway-codegen")
public enum BookingStatusTO {
  BOOKED("booked"),

  PENDING("pending"),

  BOTH("both");

  private String value;

  BookingStatusTO(String value) {
    this.value = value;
  }

  @JsonCreator
  public static BookingStatusTO fromValue(String value) {
    for (BookingStatusTO e : BookingStatusTO.values()) {
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
