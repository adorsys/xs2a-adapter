package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ais.AccountReportTO;
import de.adorsys.xs2a.gateway.service.account.AccountReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = {TransactionsMapper.class})
public interface AccountReportMapper {

    @Mappings({
            @Mapping(target = "links", ignore = true)  // TODO add links mapping after Link class is designed
    })
    AccountReportTO toAccountReportTO(AccountReport accountReport);
}
