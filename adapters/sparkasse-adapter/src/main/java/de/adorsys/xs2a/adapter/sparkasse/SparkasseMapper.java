package de.adorsys.xs2a.adapter.sparkasse;

import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.RemittanceInformationStructured;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.sparkasse.model.SparkasseOK200TransactionDetails;
import de.adorsys.xs2a.adapter.sparkasse.model.SparkasseTransactionResponse200Json;
import org.mapstruct.Mapper;

@Mapper
public interface SparkasseMapper {
    TransactionsResponse200Json toTransactionsResponse200Json(SparkasseTransactionResponse200Json value);
    OK200TransactionDetails toOK200TransactionDetails(SparkasseOK200TransactionDetails value);

    default String map(RemittanceInformationStructured value) {
        return value == null ? null : value.getReference();
    }
}
