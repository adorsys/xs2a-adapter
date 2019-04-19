package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ais.AccountReportTO;
import de.adorsys.xs2a.gateway.service.account.AccountReport;
import org.mapstruct.Mapper;

@Mapper(uses = {TransactionsMapper.class})
public interface AccountReportMapper {

    AccountReportTO toAccountReportTO(AccountReport accountReport);
}
