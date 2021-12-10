package de.adorsys.xs2a.adapter.v139.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public class AccountDetails {
  private String resourceId;

  private String iban;

  private String bban;

  private String msisdn;

  private String currency;

  private String name;

  private String displayName;

  private String product;

  private String cashAccountType;

  private AccountStatus status;

  private String bic;

  private String linkedAccounts;

  private Usage usage;

  private String details;

  private List<Balance> balances;

  @JsonProperty("_links")
  private Map<String, HrefType> links;

  private String ownerName;

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

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getProduct() {
    return product;
  }

  public void setProduct(String product) {
    this.product = product;
  }

  public String getCashAccountType() {
    return cashAccountType;
  }

  public void setCashAccountType(String cashAccountType) {
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

  public Usage getUsage() {
    return usage;
  }

  public void setUsage(Usage usage) {
    this.usage = usage;
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

  public Map<String, HrefType> getLinks() {
    return links;
  }

  public void setLinks(Map<String, HrefType> links) {
    this.links = links;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AccountDetails that = (AccountDetails) o;
    return Objects.equals(resourceId, that.resourceId) &&
        Objects.equals(iban, that.iban) &&
        Objects.equals(bban, that.bban) &&
        Objects.equals(msisdn, that.msisdn) &&
        Objects.equals(currency, that.currency) &&
        Objects.equals(name, that.name) &&
        Objects.equals(displayName, that.displayName) &&
        Objects.equals(product, that.product) &&
        Objects.equals(cashAccountType, that.cashAccountType) &&
        Objects.equals(status, that.status) &&
        Objects.equals(bic, that.bic) &&
        Objects.equals(linkedAccounts, that.linkedAccounts) &&
        Objects.equals(usage, that.usage) &&
        Objects.equals(details, that.details) &&
        Objects.equals(balances, that.balances) &&
        Objects.equals(links, that.links) &&
        Objects.equals(ownerName, that.ownerName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resourceId,
        iban,
        bban,
        msisdn,
        currency,
        name,
        displayName,
        product,
        cashAccountType,
        status,
        bic,
        linkedAccounts,
        usage,
        details,
        balances,
        links,
        ownerName);
  }

  public enum Usage {
    PRIV("PRIV"),

    ORGA("ORGA");

    private String value;

    Usage(String value) {
      this.value = value;
    }

    @JsonCreator
    public static Usage fromValue(String value) {
      for (Usage e : Usage.values()) {
        if (e.value.equals(value)) {
          return e;
        }
      }
      return null;
    }

    @Override
    @JsonValue
    public String toString() {
      return value;
    }
  }
}
