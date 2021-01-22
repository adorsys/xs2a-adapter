package de.adorsys.xs2a.adapter.santander;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.santander.model.SantanderOK200TransactionDetails;
import de.adorsys.xs2a.adapter.santander.model.SantanderTransactionDetails;
import de.adorsys.xs2a.adapter.santander.model.SantanderTransactionResponse200Json;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SantanderMapper {
    TransactionsResponse200Json toTransactionsResponse200Json(SantanderTransactionResponse200Json value);
    OK200TransactionDetails toOK200TransactionDetails(SantanderOK200TransactionDetails value);
    Transactions toTransactions(SantanderTransactionDetails value);

    @Mapping(target = "transactionDetails", expression = "java(toTransactions(value))")
    TransactionDetailsBody toTransactionDetailsBody(SantanderTransactionDetails value);

    default String map(RemittanceInformationStructured value) {
        return value == null ? null : value.getReference();
    }
}
