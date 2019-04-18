package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ais.TransactionsResponse200Json;
import de.adorsys.xs2a.gateway.service.account.TransactionsReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = {AccountReferenceMapper.class, BalanceMapper.class, AccountReportMapper.class})
public interface TransactionsReportMapper {

    @Mappings({
            @Mapping(source = "accountReference", target = "account"),
            @Mapping(target = "links", ignore = true)  // TODO add links mapping after Link class is designed
    })
    TransactionsResponse200Json toTransactionsResponse200Json(TransactionsReport transactionsReport);
}
