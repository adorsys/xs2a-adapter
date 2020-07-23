package de.adorsys.xs2a.adapter.dab;

import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.RemittanceInformationStructured;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.dab.model.DabOK200TransactionDetails;
import de.adorsys.xs2a.adapter.dab.model.DabTransactionsResponse200Json;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DabMapper {
    OK200TransactionDetails toOK200TransactionDetails(DabOK200TransactionDetails value);

    @Mapping(target = "reference", source = "value")
    @Mapping(target = "referenceType", ignore = true)
    @Mapping(target = "referenceIssuer", ignore = true)
    RemittanceInformationStructured toRemittanceInformationStructured(String value);

    TransactionsResponse200Json toTransactionsResponse200Json(DabTransactionsResponse200Json value);
}
