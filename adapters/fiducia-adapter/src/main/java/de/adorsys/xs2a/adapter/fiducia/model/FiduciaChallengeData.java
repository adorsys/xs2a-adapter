package de.adorsys.xs2a.adapter.fiducia.model;

import de.adorsys.xs2a.adapter.service.model.OtpFormat;

public class FiduciaChallengeData {
  private byte[] image;

  private String data;

  private String imageLink;

  private Integer otpMaxLength;

  private OtpFormat otpFormat;

  private String additionalInformation;

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
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

  public OtpFormat getOtpFormat() {
    return otpFormat;
  }

  public void setOtpFormat(OtpFormat otpFormat) {
    this.otpFormat = otpFormat;
  }

  public String getAdditionalInformation() {
    return additionalInformation;
  }

  public void setAdditionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
  }
}
