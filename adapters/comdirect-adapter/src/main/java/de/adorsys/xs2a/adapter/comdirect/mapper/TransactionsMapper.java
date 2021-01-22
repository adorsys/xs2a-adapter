package de.adorsys.xs2a.adapter.comdirect.mapper;

import de.adorsys.xs2a.adapter.api.model.Transactions;
import de.adorsys.xs2a.adapter.comdirect.model.ComdirectTransactionDetails;

public interface TransactionsMapper {
    Transactions toTransactions(ComdirectTransactionDetails value);
}
