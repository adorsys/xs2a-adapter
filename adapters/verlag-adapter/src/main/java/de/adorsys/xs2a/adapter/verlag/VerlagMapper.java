package de.adorsys.xs2a.adapter.verlag;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.verlag.model.VerlagOK200TransactionDetails;
import de.adorsys.xs2a.adapter.verlag.model.VerlagTransactionDetails;
import de.adorsys.xs2a.adapter.verlag.model.VerlagTransactionResponse200Json;
import org.mapstruct.Mapper;

@Mapper
public interface VerlagMapper {
    TransactionsResponse200Json toTransactionsResponse200Json(VerlagTransactionResponse200Json value);
    OK200TransactionDetails toOK200TransactionDetails(VerlagOK200TransactionDetails value);
    Transactions toTransactions(VerlagTransactionDetails value);

    default String map(RemittanceInformationStructured value) {
        return value == null ? null : value.getReference();
    }
}
