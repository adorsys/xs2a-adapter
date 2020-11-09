package de.adorsys.xs2a.adapter.olb;

import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.RemittanceInformationStructured;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.olb.model.OlbOK200TransactionDetails;
import de.adorsys.xs2a.adapter.olb.model.OlbTransactionResponse200Json;
import org.mapstruct.Mapper;

@Mapper
public interface OlbMapper {
    TransactionsResponse200Json toTransactionsResponse200Json(OlbTransactionResponse200Json value);
    OK200TransactionDetails toOK200TransactionDetails(OlbOK200TransactionDetails value);

    default String map(RemittanceInformationStructured value) {
        return value == null ? null : value.getReference();
    }
}
