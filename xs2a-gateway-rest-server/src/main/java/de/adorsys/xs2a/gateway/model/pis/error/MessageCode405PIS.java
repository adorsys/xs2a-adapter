package de.adorsys.xs2a.gateway.model.pis.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Message codes defined for payment cancelations PIS for HTTP Error code 405 (METHOD NOT ALLOWED).
 */
public enum MessageCode405PIS {
  
  INVALID("SERVICE_INVALID");

  private String value;

  MessageCode405PIS(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static MessageCode405PIS fromValue(String text) {
    for (MessageCode405PIS b : MessageCode405PIS.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}

