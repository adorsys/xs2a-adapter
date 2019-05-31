package de.adorsys.xs2a.gateway.model;

import javax.annotation.Generated;

@Generated("xs2a-gateway-codegen")
public class PsuDataTO {
  private String password;

  private String encryptedPassword;

  private String additionalPassword;

  private String additionalEncryptedPassword;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEncryptedPassword() {
    return encryptedPassword;
  }

  public void setEncryptedPassword(String encryptedPassword) {
    this.encryptedPassword = encryptedPassword;
  }

  public String getAdditionalPassword() {
    return additionalPassword;
  }

  public void setAdditionalPassword(String additionalPassword) {
    this.additionalPassword = additionalPassword;
  }

  public String getAdditionalEncryptedPassword() {
    return additionalEncryptedPassword;
  }

  public void setAdditionalEncryptedPassword(String additionalEncryptedPassword) {
    this.additionalEncryptedPassword = additionalEncryptedPassword;
  }
}
