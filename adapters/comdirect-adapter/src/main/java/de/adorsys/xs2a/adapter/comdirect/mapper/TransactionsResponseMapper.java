package de.adorsys.xs2a.adapter.comdirect.mapper;

import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.comdirect.model.ComdirectTransactionResponse200Json;
import org.mapstruct.Mapper;

@Mapper
public interface TransactionsResponseMapper extends RemittanceInformationStructuredMapper {
    TransactionsResponse200Json toTransactionsResponse200Json(ComdirectTransactionResponse200Json value);
}
