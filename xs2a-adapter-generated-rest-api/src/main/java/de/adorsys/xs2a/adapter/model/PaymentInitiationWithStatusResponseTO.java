package de.adorsys.xs2a.adapter.model;

import javax.annotation.Generated;

@Generated("xs2a-codegen")
public class PaymentInitiationWithStatusResponseTO {
    private String endToEndIdentification;

    private AccountReferenceTO debtorAccount;

    private AmountTO instructedAmount;

    private AccountReferenceTO creditorAccount;

    private String creditorAgent;

    private String creditorName;

    private AddressTO creditorAddress;

    private String remittanceInformationUnstructured;

    private TransactionStatusTO transactionStatus;

    public String getEndToEndIdentification() {
        return endToEndIdentification;
    }

    public void setEndToEndIdentification(String endToEndIdentification) {
        this.endToEndIdentification = endToEndIdentification;
    }

    public AccountReferenceTO getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(AccountReferenceTO debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    public AmountTO getInstructedAmount() {
        return instructedAmount;
    }

    public void setInstructedAmount(AmountTO instructedAmount) {
        this.instructedAmount = instructedAmount;
    }

    public AccountReferenceTO getCreditorAccount() {
        return creditorAccount;
    }

    public void setCreditorAccount(AccountReferenceTO creditorAccount) {
        this.creditorAccount = creditorAccount;
    }

    public String getCreditorAgent() {
        return creditorAgent;
    }

    public void setCreditorAgent(String creditorAgent) {
        this.creditorAgent = creditorAgent;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    public AddressTO getCreditorAddress() {
        return creditorAddress;
    }

    public void setCreditorAddress(AddressTO creditorAddress) {
        this.creditorAddress = creditorAddress;
    }

    public String getRemittanceInformationUnstructured() {
        return remittanceInformationUnstructured;
    }

    public void setRemittanceInformationUnstructured(String remittanceInformationUnstructured) {
        this.remittanceInformationUnstructured = remittanceInformationUnstructured;
    }

    public TransactionStatusTO getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatusTO transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
