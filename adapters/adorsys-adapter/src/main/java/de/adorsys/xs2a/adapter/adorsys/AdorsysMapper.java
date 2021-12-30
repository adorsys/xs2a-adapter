package de.adorsys.xs2a.adapter.adorsys;

import de.adorsys.xs2a.adapter.adorsys.model.AdorsysOK200TransactionDetails;
import de.adorsys.xs2a.adapter.adorsys.model.AdorsysTransactionsResponse200Json;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AdorsysMapper {
    TransactionsResponse200Json toTransactionsResponse200Json(AdorsysTransactionsResponse200Json value);

    @Mapping(target = "transactionsDetails", source = "transactionsDetails.transactionDetails")
    OK200TransactionDetails toOK200TransactionDetails(AdorsysOK200TransactionDetails value);
}
