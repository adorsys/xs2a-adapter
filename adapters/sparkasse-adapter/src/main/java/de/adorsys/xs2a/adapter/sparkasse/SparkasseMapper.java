package de.adorsys.xs2a.adapter.sparkasse;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.sparkasse.model.SparkasseOK200TransactionDetails;
import de.adorsys.xs2a.adapter.sparkasse.model.SparkasseTransactionDetails;
import de.adorsys.xs2a.adapter.sparkasse.model.SparkasseTransactionResponse200Json;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SparkasseMapper {
    TransactionsResponse200Json toTransactionsResponse200Json(SparkasseTransactionResponse200Json value);
    OK200TransactionDetails toOK200TransactionDetails(SparkasseOK200TransactionDetails value);
    Transactions toTransactions(SparkasseTransactionDetails value);

    @Mapping(target = "transactionDetails", expression = "java(toTransactions(value))")
    TransactionDetailsBody toTransactionDetailsBody(SparkasseTransactionDetails value);

    default String map(RemittanceInformationStructured value) {
        return value == null ? null : value.getReference();
    }
}
