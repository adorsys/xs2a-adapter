package de.adorsys.xs2a.adapter.rest.psd2.model;

import de.adorsys.xs2a.adapter.model.AccountReferenceTO;
import de.adorsys.xs2a.adapter.model.BalanceTO;

import java.util.List;

public class ReadCardAccountBalanceResponseTO {
    private AccountReferenceTO cardAccount;

    private List<BalanceTO> balances;

    public AccountReferenceTO getCardAccount() {
        return cardAccount;
    }

    public void setCardAccount(AccountReferenceTO cardAccount) {
        this.cardAccount = cardAccount;
    }

    public List<BalanceTO> getBalances() {
        return balances;
    }

    public void setBalances(List<BalanceTO> balances) {
        this.balances = balances;
    }
}
