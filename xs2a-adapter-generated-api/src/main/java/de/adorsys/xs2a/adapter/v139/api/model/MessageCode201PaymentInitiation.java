package de.adorsys.xs2a.adapter.v139.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.lang.Override;
import java.lang.String;
import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum MessageCode201PaymentInitiation {
  WARNING("WARNING"),

  BENEFICIARY_WHITELISTING_REQUIRED("BENEFICIARY_WHITELISTING_REQUIRED");

  private String value;

  MessageCode201PaymentInitiation(String value) {
    this.value = value;
  }

  @JsonCreator
  public static MessageCode201PaymentInitiation fromValue(String value) {
    for (MessageCode201PaymentInitiation e : MessageCode201PaymentInitiation.values()) {
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
