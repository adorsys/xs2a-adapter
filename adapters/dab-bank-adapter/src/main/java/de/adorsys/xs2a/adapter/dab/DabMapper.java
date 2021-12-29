package de.adorsys.xs2a.adapter.dab;

import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.dab.model.DabOK200TransactionDetails;
import org.mapstruct.Mapper;

@Mapper
public interface DabMapper {

    OK200TransactionDetails toOK200TransactionDetails(DabOK200TransactionDetails value);
}
