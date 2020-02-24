package de.adorsys.xs2a.adapter.model;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public class PaymentInitiationBulkElementJsonTO {
    private String endToEndIdentification;

    private AmountTO instructedAmount;

    private AccountReferenceTO creditorAccount;

    private String creditorAgent;

    private String creditorAgentName;

    private String creditorName;

    private AddressTO creditorAddress;

    private String remittanceInformationUnstructured;

    public String getEndToEndIdentification() {
        return endToEndIdentification;
    }

    public void setEndToEndIdentification(String endToEndIdentification) {
        this.endToEndIdentification = endToEndIdentification;
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

    public String getCreditorAgentName() {
        return creditorAgentName;
    }

    public void setCreditorAgentName(String creditorAgentName) {
        this.creditorAgentName = creditorAgentName;
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
}
