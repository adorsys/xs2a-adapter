package de.adorsys.xs2a.gateway.service.account;

import java.util.List;

public class AccountListHolder {
    private List<AccountDetails> accounts;

    public List<AccountDetails> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDetails> accounts) {
        this.accounts = accounts;
    }
}
