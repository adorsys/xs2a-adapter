package de.adorsys.xs2a.adapter.v139.api.model;

import java.lang.Object;
import java.lang.Override;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public class CardAccountList {
  private List<CardAccountDetails> cardAccounts;

  public List<CardAccountDetails> getCardAccounts() {
    return cardAccounts;
  }

  public void setCardAccounts(List<CardAccountDetails> cardAccounts) {
    this.cardAccounts = cardAccounts;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CardAccountList that = (CardAccountList) o;
    return Objects.equals(cardAccounts, that.cardAccounts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardAccounts);
  }
}
