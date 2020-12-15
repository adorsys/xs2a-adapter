package de.adorsys.xs2a.adapter.consors;

import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.TransactionDetailsBody;
import de.adorsys.xs2a.adapter.api.model.Transactions;
import de.adorsys.xs2a.adapter.consors.model.ConsorsOK200TransactionDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ConsorsMapper {

    @Mapping(target = "transactionDetails", source = "value")
    TransactionDetailsBody toTransactionDetailsBody(Transactions value);
    OK200TransactionDetails toOK200TransactionDetails(ConsorsOK200TransactionDetails value);
}
