package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-gateway-codegen")
public enum TppMessageCategoryTO {
  ERROR("ERROR"),

  WARNING("WARNING");

  private String value;

  TppMessageCategoryTO(String value) {
    this.value = value;
  }

  @JsonCreator
  public static TppMessageCategoryTO fromValue(String value) {
    for (TppMessageCategoryTO e : TppMessageCategoryTO.values()) {
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
