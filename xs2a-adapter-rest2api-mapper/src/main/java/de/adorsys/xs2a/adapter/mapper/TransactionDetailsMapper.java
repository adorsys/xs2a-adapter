package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.OK200TransactionDetailsTO;
import de.adorsys.xs2a.adapter.service.model.TransactionDetails;
import org.mapstruct.Mapper;

@Mapper(uses = TransactionsMapper.class)
public interface TransactionDetailsMapper {
    OK200TransactionDetailsTO map(TransactionDetails transactionDetails);

    TransactionDetails map(OK200TransactionDetailsTO body);
}
