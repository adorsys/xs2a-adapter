package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.CardAccountsTransactionsResponse200TO;
import de.adorsys.xs2a.adapter.service.model.CardAccountsTransactions;
import org.mapstruct.Mapper;

@Mapper
public interface CardAccountsTransactionsMapper {
    CardAccountsTransactionsResponse200TO map(CardAccountsTransactions source);

    CardAccountsTransactions map(CardAccountsTransactionsResponse200TO source);
}
