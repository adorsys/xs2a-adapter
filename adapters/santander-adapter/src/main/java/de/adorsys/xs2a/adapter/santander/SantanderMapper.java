package de.adorsys.xs2a.adapter.santander;

import de.adorsys.xs2a.adapter.api.model.RemittanceInformationStructured;
import de.adorsys.xs2a.adapter.api.model.Transactions;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.santander.model.SantanderTransactionDetails;
import de.adorsys.xs2a.adapter.santander.model.SantanderTransactionResponse200Json;
import org.mapstruct.Mapper;

@Mapper
public interface SantanderMapper {
    TransactionsResponse200Json toTransactionsResponse200Json(SantanderTransactionResponse200Json value);
    Transactions toTransactions(SantanderTransactionDetails value);

    default String map(RemittanceInformationStructured value) {
        return value == null ? null : value.getReference();
    }
}
