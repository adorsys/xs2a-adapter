package de.adorsys.xs2a.adapter.dab;

import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.TransactionDetailsBody;
import de.adorsys.xs2a.adapter.api.model.Transactions;
import de.adorsys.xs2a.adapter.dab.model.DabOK200TransactionDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DabMapper {

    @Mapping(target = "transactionDetails", source = "value")
    TransactionDetailsBody toTransactionDetailsBody(Transactions value);
    OK200TransactionDetails toOK200TransactionDetails(DabOK200TransactionDetails value);
}
