package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;
import java.util.List;
import java.util.Map;

@Generated("xs2a-codegen")
public class AccountDetailsTO {
  private String resourceId;

  private String iban;

  private String bban;

  private String msisdn;

  private String currency;

  private String name;

  private String product;

  private String cashAccountType;

  private AccountStatusTO status;

  private String bic;

  private String linkedAccounts;

  private UsageTO usage;

  private String details;

  private List<BalanceTO> balances;

  @JsonProperty("_links")
  private Map<String, HrefTypeTO> links;

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

  public AccountStatusTO getStatus() {
    return status;
  }

  public void setStatus(AccountStatusTO status) {
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

  public UsageTO getUsage() {
    return usage;
  }

  public void setUsage(UsageTO usage) {
    this.usage = usage;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public List<BalanceTO> getBalances() {
    return balances;
  }

  public void setBalances(List<BalanceTO> balances) {
    this.balances = balances;
  }

  public Map<String, HrefTypeTO> getLinks() {
    return links;
  }

  public void setLinks(Map<String, HrefTypeTO> links) {
    this.links = links;
  }

  public enum UsageTO {
    PRIV("PRIV"),

    ORGA("ORGA");

    private String value;

    UsageTO(String value) {
      this.value = value;
    }

    @JsonCreator
    public static UsageTO fromValue(String value) {
      for (UsageTO e : UsageTO.values()) {
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
