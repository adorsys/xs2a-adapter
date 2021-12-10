package de.adorsys.xs2a.adapter.v139.api.model;

import java.lang.Object;
import java.lang.Override;
import java.util.Objects;
import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public class OK200CardAccountDetails {
  private CardAccountDetails cardAccount;

  public CardAccountDetails getCardAccount() {
    return cardAccount;
  }

  public void setCardAccount(CardAccountDetails cardAccount) {
    this.cardAccount = cardAccount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OK200CardAccountDetails that = (OK200CardAccountDetails) o;
    return Objects.equals(cardAccount, that.cardAccount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardAccount);
  }
}
