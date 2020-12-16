package de.adorsys.xs2a.adapter.comdirect.mapper;

import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.comdirect.model.ComdirectOK200TransactionDetails;
import org.mapstruct.Mapper;

@Mapper
public interface OK200TransactionDetailsMapper extends RemittanceInformationStructuredMapper, TransactionDetailsBodyMapper {
    OK200TransactionDetails toOK200TransactionDetails(ComdirectOK200TransactionDetails value);
}
