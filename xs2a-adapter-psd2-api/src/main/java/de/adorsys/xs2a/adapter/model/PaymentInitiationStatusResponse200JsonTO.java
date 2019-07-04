package de.adorsys.xs2a.adapter.model;

import javax.annotation.Generated;

@Generated("xs2a-codegen")
public class PaymentInitiationStatusResponse200JsonTO {
  private TransactionStatusTO transactionStatus;

  private Boolean fundsAvailable;

  public TransactionStatusTO getTransactionStatus() {
    return transactionStatus;
  }

  public void setTransactionStatus(TransactionStatusTO transactionStatus) {
    this.transactionStatus = transactionStatus;
  }

  public Boolean getFundsAvailable() {
    return fundsAvailable;
  }

  public void setFundsAvailable(Boolean fundsAvailable) {
    this.fundsAvailable = fundsAvailable;
  }
}
