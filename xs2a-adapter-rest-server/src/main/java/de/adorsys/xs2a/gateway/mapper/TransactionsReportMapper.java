package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.TransactionsResponse200JsonTO;
import de.adorsys.xs2a.gateway.service.account.TransactionsReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = {AccountReferenceMapper.class, BalanceMapper.class, AccountReportMapper.class})
public interface TransactionsReportMapper {

    @Mappings({
            @Mapping(source = "accountReference", target = "account")
    })
    TransactionsResponse200JsonTO toTransactionsResponse200Json(TransactionsReport transactionsReport);
}
