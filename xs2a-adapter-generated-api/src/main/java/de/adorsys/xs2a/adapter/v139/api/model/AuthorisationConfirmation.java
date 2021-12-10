package de.adorsys.xs2a.adapter.v139.api.model;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;
import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public class AuthorisationConfirmation {
  private String confirmationCode;

  public String getConfirmationCode() {
    return confirmationCode;
  }

  public void setConfirmationCode(String confirmationCode) {
    this.confirmationCode = confirmationCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AuthorisationConfirmation that = (AuthorisationConfirmation) o;
    return Objects.equals(confirmationCode, that.confirmationCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(confirmationCode);
  }
}
