package de.adorsys.xs2a.adapter.api.model;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class PaymentInitiationJson {
    private String endToEndIdentification;

    private AccountReference debtorAccount;

    private Amount instructedAmount;

    private AccountReference creditorAccount;

    private String creditorAgent;

    private String creditorAgentName;

    private String creditorName;

    private Address creditorAddress;

    private String remittanceInformationUnstructured;

    public String getEndToEndIdentification() {
        return endToEndIdentification;
    }

    public void setEndToEndIdentification(String endToEndIdentification) {
        this.endToEndIdentification = endToEndIdentification;
    }

    public AccountReference getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(AccountReference debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    public Amount getInstructedAmount() {
        return instructedAmount;
    }

    public void setInstructedAmount(Amount instructedAmount) {
        this.instructedAmount = instructedAmount;
    }

    public AccountReference getCreditorAccount() {
        return creditorAccount;
    }

    public void setCreditorAccount(AccountReference creditorAccount) {
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

    public Address getCreditorAddress() {
        return creditorAddress;
    }

    public void setCreditorAddress(Address creditorAddress) {
        this.creditorAddress = creditorAddress;
    }

    public String getRemittanceInformationUnstructured() {
        return remittanceInformationUnstructured;
    }

    public void setRemittanceInformationUnstructured(String remittanceInformationUnstructured) {
        this.remittanceInformationUnstructured = remittanceInformationUnstructured;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentInitiationJson that = (PaymentInitiationJson) o;
        return Objects.equals(endToEndIdentification, that.endToEndIdentification) &&
            Objects.equals(debtorAccount, that.debtorAccount) &&
            Objects.equals(instructedAmount, that.instructedAmount) &&
            Objects.equals(creditorAccount, that.creditorAccount) &&
            Objects.equals(creditorAgent, that.creditorAgent) &&
            Objects.equals(creditorAgentName, that.creditorAgentName) &&
            Objects.equals(creditorName, that.creditorName) &&
            Objects.equals(creditorAddress, that.creditorAddress) &&
            Objects.equals(remittanceInformationUnstructured, that.remittanceInformationUnstructured);
    }

    @Override
    public int hashCode() {
        return Objects.hash(endToEndIdentification,
            debtorAccount,
            instructedAmount,
            creditorAccount,
            creditorAgent,
            creditorAgentName,
            creditorName,
            creditorAddress,
            remittanceInformationUnstructured);
    }
}
