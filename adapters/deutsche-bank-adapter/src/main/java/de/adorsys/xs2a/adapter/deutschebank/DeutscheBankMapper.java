package de.adorsys.xs2a.adapter.deutschebank;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.deutschebank.model.DeutscheBankOK200TransactionDetails;
import de.adorsys.xs2a.adapter.deutschebank.model.DeutscheBankTransactionDetails;
import de.adorsys.xs2a.adapter.deutschebank.model.DeutscheBankTransactionResponse200Json;
import org.mapstruct.Mapper;

@Mapper
public interface DeutscheBankMapper {
    TransactionsResponse200Json toTransactionsResponse200Json(DeutscheBankTransactionResponse200Json value);
    OK200TransactionDetails toOK200TransactionDetails(DeutscheBankOK200TransactionDetails value);
    Transactions toTransactions(DeutscheBankTransactionDetails value);

    default String map(RemittanceInformationStructured value) {
        return value == null ? null : value.getReference();
    }
}
