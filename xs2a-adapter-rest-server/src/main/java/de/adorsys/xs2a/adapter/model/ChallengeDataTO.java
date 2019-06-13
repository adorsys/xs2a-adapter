package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;
import java.util.List;

@Generated("xs2a-gateway-codegen")
public class ChallengeDataTO {
  private byte[] image;

  private List<String> data;

  private String imageLink;

  private Integer otpMaxLength;

  private OtpFormatTO otpFormat;

  private String additionalInformation;

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public List<String> getData() {
    return data;
  }

  public void setData(List<String> data) {
    this.data = data;
  }

  public String getImageLink() {
    return imageLink;
  }

  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
  }

  public Integer getOtpMaxLength() {
    return otpMaxLength;
  }

  public void setOtpMaxLength(Integer otpMaxLength) {
    this.otpMaxLength = otpMaxLength;
  }

  public OtpFormatTO getOtpFormat() {
    return otpFormat;
  }

  public void setOtpFormat(OtpFormatTO otpFormat) {
    this.otpFormat = otpFormat;
  }

  public String getAdditionalInformation() {
    return additionalInformation;
  }

  public void setAdditionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
  }

  public enum OtpFormatTO {
    CHARACTERS("characters"),

    INTEGER("integer");

    private String value;

    OtpFormatTO(String value) {
      this.value = value;
    }

    @JsonCreator
    public static OtpFormatTO fromValue(String value) {
      for (OtpFormatTO e : OtpFormatTO.values()) {
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
}
