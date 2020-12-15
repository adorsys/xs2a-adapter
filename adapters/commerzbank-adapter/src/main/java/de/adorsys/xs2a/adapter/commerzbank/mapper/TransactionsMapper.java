package de.adorsys.xs2a.adapter.commerzbank.mapper;

import de.adorsys.xs2a.adapter.api.model.Transactions;
import de.adorsys.xs2a.adapter.commerzbank.model.CommerzbankTransactionDetails;

public interface TransactionsMapper {
    Transactions toTransactions(CommerzbankTransactionDetails value);
}
