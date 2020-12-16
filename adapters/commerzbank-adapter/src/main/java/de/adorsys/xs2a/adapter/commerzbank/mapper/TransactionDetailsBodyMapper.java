package de.adorsys.xs2a.adapter.commerzbank.mapper;

import de.adorsys.xs2a.adapter.api.model.TransactionDetailsBody;
import de.adorsys.xs2a.adapter.commerzbank.model.CommerzbankTransactionDetails;
import org.mapstruct.Mapping;

public interface TransactionDetailsBodyMapper extends TransactionsMapper {
    @Mapping(target = "transactionDetails", expression = "java(toTransactions(value))")
    TransactionDetailsBody toTransactionDetailsBody(CommerzbankTransactionDetails value);
}
