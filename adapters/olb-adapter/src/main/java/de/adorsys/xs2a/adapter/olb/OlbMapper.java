package de.adorsys.xs2a.adapter.olb;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.olb.model.OlbOK200TransactionDetails;
import de.adorsys.xs2a.adapter.olb.model.OlbTransactionDetails;
import de.adorsys.xs2a.adapter.olb.model.OlbTransactionResponse200Json;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OlbMapper {
    TransactionsResponse200Json toTransactionsResponse200Json(OlbTransactionResponse200Json value);
    OK200TransactionDetails toOK200TransactionDetails(OlbOK200TransactionDetails value);
    Transactions toTransactions(OlbTransactionDetails value);

    @Mapping(target = "transactionDetails", expression = "java(toTransactions(value))")
    TransactionDetailsBody toTransactionDetailsBody(OlbTransactionDetails value);

    default String map(RemittanceInformationStructured value) {
        return value == null ? null : value.getReference();
    }
}
