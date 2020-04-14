package de.adorsys.xs2a.adapter.rest.psd2.model;

import javax.annotation.Generated;
import java.util.List;

@Generated("xs2a-adapter-codegen")
public class AccountListTO {
    private List<AccountDetailsTO> accounts;

    public List<AccountDetailsTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDetailsTO> accounts) {
        this.accounts = accounts;
    }
}
