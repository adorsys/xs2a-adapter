package de.adorsys.xs2a.adapter.consors;

import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.RemittanceInformationStructured;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.consors.model.ConsorsOK200TransactionDetails;
import de.adorsys.xs2a.adapter.consors.model.ConsorsTransactionsResponse200Json;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ConsorsMapper {
    OK200TransactionDetails toOK200TransactionDetails(ConsorsOK200TransactionDetails value);

    @Mapping(target = "reference", source = "value")
    @Mapping(target = "referenceType", ignore = true)
    @Mapping(target = "referenceIssuer", ignore = true)
    RemittanceInformationStructured toRemittanceInformationStructured(String value);

    TransactionsResponse200Json toTransactionsResponse200Json(ConsorsTransactionsResponse200Json value);
}
