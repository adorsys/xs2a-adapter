package de.adorsys.xs2a.adapter.unicredit;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.unicredit.model.UnicreditOK200TransactionDetails;
import de.adorsys.xs2a.adapter.unicredit.model.UnicreditTransactionDetails;
import de.adorsys.xs2a.adapter.unicredit.model.UnicreditTransactionResponse200Json;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UnicreditMapper {
    TransactionsResponse200Json toTransactionsResponse200Json(UnicreditTransactionResponse200Json value);
    OK200TransactionDetails toOK200TransactionDetails(UnicreditOK200TransactionDetails value);
    Transactions toTransactions(UnicreditTransactionDetails value);

    @Mapping(target = "transactionDetails", expression = "java(toTransactions(value))")
    TransactionDetailsBody toTransactionDetailsBody(UnicreditTransactionDetails value);

    default String map(RemittanceInformationStructured value) {
        return value == null ? null : value.getReference();
    }
}
