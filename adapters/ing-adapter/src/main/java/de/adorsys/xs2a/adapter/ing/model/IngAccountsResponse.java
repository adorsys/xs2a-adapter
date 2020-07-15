package de.adorsys.xs2a.adapter.ing.model;

import java.util.List;

public class IngAccountsResponse {
    private List<IngAccount> accounts;

    public List<IngAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<IngAccount> accounts) {
        this.accounts = accounts;
    }
}
