package de.adorsys.xs2a.adapter.commerzbank.mapper;

import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.commerzbank.model.CommerzbankOK200TransactionDetails;
import org.mapstruct.Mapper;

@Mapper
public interface OK200TransactionDetailsMapper extends TransactionDetailsBodyMapper {
    OK200TransactionDetails toOK200TransactionDetails(CommerzbankOK200TransactionDetails value);
}
