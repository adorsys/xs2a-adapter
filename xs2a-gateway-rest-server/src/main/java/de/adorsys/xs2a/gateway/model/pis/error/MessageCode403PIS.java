package de.adorsys.xs2a.gateway.model.pis.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Message codes defined defined for PIS for PIS for HTTP Error code 403 (FORBIDDEN).
 */
public enum MessageCode403PIS {
  
  CONSENT_UNKNOWN("CONSENT_UNKNOWN"),
  
  SERVICE_BLOCKED("SERVICE_BLOCKED"),
  
  RESOURCE_UNKNOWN("RESOURCE_UNKNOWN"),
  
  RESOURCE_EXPIRED("RESOURCE_EXPIRED"),
  
  PRODUCT_INVALID("PRODUCT_INVALID");

  private String value;

  MessageCode403PIS(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static MessageCode403PIS fromValue(String text) {
    for (MessageCode403PIS b : MessageCode403PIS.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}

