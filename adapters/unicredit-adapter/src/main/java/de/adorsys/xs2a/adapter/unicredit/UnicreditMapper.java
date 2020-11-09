package de.adorsys.xs2a.adapter.unicredit;

import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.RemittanceInformationStructured;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.unicredit.model.UnicreditOK200TransactionDetails;
import de.adorsys.xs2a.adapter.unicredit.model.UnicreditTransactionResponse200Json;
import org.mapstruct.Mapper;

@Mapper
public interface UnicreditMapper {
    TransactionsResponse200Json toTransactionsResponse200Json(UnicreditTransactionResponse200Json value);
    OK200TransactionDetails toOK200TransactionDetails(UnicreditOK200TransactionDetails value);

    default String map(RemittanceInformationStructured value) {
        return value == null ? null : value.getReference();
    }
}
