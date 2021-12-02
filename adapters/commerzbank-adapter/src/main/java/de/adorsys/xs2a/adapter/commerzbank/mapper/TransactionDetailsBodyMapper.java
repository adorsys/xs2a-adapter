package de.adorsys.xs2a.adapter.commerzbank.mapper;

import de.adorsys.xs2a.adapter.api.model.TransactionDetailsBody;
import de.adorsys.xs2a.adapter.api.model.Transactions;
import org.mapstruct.Mapping;

public interface TransactionDetailsBodyMapper {
    @Mapping(target = "transactionDetails", source = "value")
    TransactionDetailsBody toTransactionDetailsBody(Transactions value);
}
