package de.adorsys.xs2a.adapter.v139.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.lang.Override;
import java.lang.String;
import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum ScaStatus {
  RECEIVED("received"),

  PSUIDENTIFIED("psuIdentified"),

  PSUAUTHENTICATED("psuAuthenticated"),

  SCAMETHODSELECTED("scaMethodSelected"),

  STARTED("started"),

  UNCONFIRMED("unconfirmed"),

  FINALISED("finalised"),

  FAILED("failed"),

  EXEMPTED("exempted");

  private String value;

  ScaStatus(String value) {
    this.value = value;
  }

  @JsonCreator
  public static ScaStatus fromValue(String value) {
    for (ScaStatus e : ScaStatus.values()) {
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
