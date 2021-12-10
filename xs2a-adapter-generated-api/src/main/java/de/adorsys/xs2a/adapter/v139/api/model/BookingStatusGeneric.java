package de.adorsys.xs2a.adapter.v139.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.lang.Override;
import java.lang.String;
import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum BookingStatusGeneric {
  INFORMATION("information"),

  BOOKED("booked"),

  PENDING("pending"),

  BOTH("both"),

  ALL("all");

  private String value;

  BookingStatusGeneric(String value) {
    this.value = value;
  }

  @JsonCreator
  public static BookingStatusGeneric fromValue(String value) {
    for (BookingStatusGeneric e : BookingStatusGeneric.values()) {
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
