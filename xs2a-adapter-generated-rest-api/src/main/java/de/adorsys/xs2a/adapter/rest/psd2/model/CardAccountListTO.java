package de.adorsys.xs2a.adapter.rest.psd2.model;

import javax.annotation.Generated;
import java.util.List;

@Generated("xs2a-adapter-codegen")
public class CardAccountListTO {
    private List<CardAccountDetailsTO> cardAccounts;

    public List<CardAccountDetailsTO> getCardAccounts() {
        return cardAccounts;
    }

    public void setCardAccounts(List<CardAccountDetailsTO> cardAccounts) {
        this.cardAccounts = cardAccounts;
    }
}
