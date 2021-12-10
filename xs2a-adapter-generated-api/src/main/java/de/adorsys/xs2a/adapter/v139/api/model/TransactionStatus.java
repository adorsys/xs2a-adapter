package de.adorsys.xs2a.adapter.v139.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.lang.Override;
import java.lang.String;
import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum TransactionStatus {
  ACCC("ACCC"),

  ACCP("ACCP"),

  ACSC("ACSC"),

  ACSP("ACSP"),

  ACTC("ACTC"),

  ACWC("ACWC"),

  ACWP("ACWP"),

  RCVD("RCVD"),

  PDNG("PDNG"),

  RJCT("RJCT"),

  CANC("CANC"),

  ACFC("ACFC"),

  PATC("PATC"),

  PART("PART");

  private String value;

  TransactionStatus(String value) {
    this.value = value;
  }

  @JsonCreator
  public static TransactionStatus fromValue(String value) {
    for (TransactionStatus e : TransactionStatus.values()) {
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
