package de.adorsys.xs2a.adapter.model;

import javax.annotation.Generated;
import java.util.List;

@Generated("xs2a-gateway-codegen")
public class AccountListTO {
  private List<AccountDetailsTO> accounts;

  public List<AccountDetailsTO> getAccounts() {
    return accounts;
  }

  public void setAccounts(List<AccountDetailsTO> accounts) {
    this.accounts = accounts;
  }
}
