package de.adorsys.xs2a.adapter.sparda;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.sparda.model.SpardaOK200TransactionDetails;
import de.adorsys.xs2a.adapter.sparda.model.SpardaTransactionDetails;
import de.adorsys.xs2a.adapter.sparda.model.SpardaTransactionResponse200Json;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SpardaMapper {
    TransactionsResponse200Json toTransactionsResponse200Json(SpardaTransactionResponse200Json value);
    OK200TransactionDetails toOK200TransactionDetails(SpardaOK200TransactionDetails value);
    Transactions toTransactions(SpardaTransactionDetails value);

    @Mapping(target = "transactionDetails", expression = "java(toTransactions(value))")
    TransactionDetailsBody toTransactionDetailsBody(SpardaTransactionDetails value);

    default String map(RemittanceInformationStructured value) {
        return value == null ? null : value.getReference();
    }
}
