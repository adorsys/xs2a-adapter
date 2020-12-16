package de.adorsys.xs2a.adapter.api.model;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class TransactionDetailsBody {
  private Transactions transactionDetails;

  public Transactions getTransactionDetails() {
    return transactionDetails;
  }

  public void setTransactionDetails(Transactions transactionDetails) {
    this.transactionDetails = transactionDetails;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TransactionDetailsBody that = (TransactionDetailsBody) o;
    return Objects.equals(transactionDetails, that.transactionDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionDetails);
  }
}
