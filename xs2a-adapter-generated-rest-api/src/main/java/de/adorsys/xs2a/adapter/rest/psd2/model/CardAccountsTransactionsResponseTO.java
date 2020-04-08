package de.adorsys.xs2a.adapter.rest.psd2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.util.List;
import java.util.Map;

@Generated("xs2a-adapter-codegen")
public class CardAccountsTransactionsResponseTO {
    private AccountReferenceTO cardAccount;

    private CardAccountReportTO cardTransactions;

    private List<BalanceTO> balances;

    @JsonProperty("_links")
    private Map<String, HrefTypeTO> links;

    public AccountReferenceTO getCardAccount() {
        return cardAccount;
    }

    public void setCardAccount(AccountReferenceTO cardAccount) {
        this.cardAccount = cardAccount;
    }

    public CardAccountReportTO getCardTransactions() {
        return cardTransactions;
    }

    public void setCardTransactions(CardAccountReportTO cardTransactions) {
        this.cardTransactions = cardTransactions;
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
}
