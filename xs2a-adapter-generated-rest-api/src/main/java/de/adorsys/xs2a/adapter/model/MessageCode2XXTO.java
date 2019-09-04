package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-codegen")
public enum MessageCode2XXTO {
  WARNING("WARNING");

  private String value;

  MessageCode2XXTO(String value) {
    this.value = value;
  }

  @JsonCreator
  public static MessageCode2XXTO fromValue(String value) {
    for (MessageCode2XXTO e : MessageCode2XXTO.values()) {
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
