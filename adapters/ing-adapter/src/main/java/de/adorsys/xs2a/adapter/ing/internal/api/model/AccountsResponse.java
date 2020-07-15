package de.adorsys.xs2a.adapter.ing.internal.api.model;

import java.util.List;

public class AccountsResponse {
    private List<Account> accounts;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
