package de.adorsys.xs2a.adapter.comdirect.mapper;

import de.adorsys.xs2a.adapter.api.model.TransactionDetailsBody;
import de.adorsys.xs2a.adapter.comdirect.model.ComdirectTransactionDetails;
import org.mapstruct.Mapping;

public interface TransactionDetailsBodyMapper extends TransactionsMapper {
    @Mapping(target = "transactionDetails", expression = "java(toTransactions(value))")
    TransactionDetailsBody toTransactionDetailsBody(ComdirectTransactionDetails value);
}
