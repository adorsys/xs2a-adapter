package de.adorsys.xs2a.adapter.consors;

import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.consors.model.ConsorsOK200TransactionDetails;
import org.mapstruct.Mapper;

@Mapper
public interface ConsorsMapper {

    OK200TransactionDetails toOK200TransactionDetails(ConsorsOK200TransactionDetails value);
}
