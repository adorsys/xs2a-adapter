package de.adorsys.xs2a.adapter.service.model;

import java.util.List;
import java.util.Map;

public class AccountDetails {
    private String aspspAccountId;
    private String resourceId;
    private String iban;
    private String bban;
    private String pan;
    private String maskedPan;
    private String msisdn;
    private String currency;
    private String name;
    private String product;
    private CashAccountType cashAccountType;
    private AccountStatus status;
    private String bic;
    private String linkedAccounts;
    private UsageType usageType;
    private String details;
    private List<Balance> balances;
    private Map<String, Link> links;

    public String getAspspAccountId() {
        return aspspAccountId;
    }

    public void setAspspAccountId(String aspspAccountId) {
        this.aspspAccountId = aspspAccountId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBban() {
        return bban;
    }

    public void setBban(String bban) {
        this.bban = bban;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getMaskedPan() {
        return maskedPan;
    }

    public void setMaskedPan(String maskedPan) {
        this.maskedPan = maskedPan;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public CashAccountType getCashAccountType() {
        return cashAccountType;
    }

    public void setCashAccountType(CashAccountType cashAccountType) {
        this.cashAccountType = cashAccountType;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getLinkedAccounts() {
        return linkedAccounts;
    }

    public void setLinkedAccounts(String linkedAccounts) {
        this.linkedAccounts = linkedAccounts;
    }

    public UsageType getUsageType() {
        return usageType;
    }

    public void setUsageType(UsageType usageType) {
        this.usageType = usageType;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }
}
