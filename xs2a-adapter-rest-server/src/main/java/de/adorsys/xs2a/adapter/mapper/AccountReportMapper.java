package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.AccountReportTO;
import de.adorsys.xs2a.adapter.service.account.AccountReport;
import org.mapstruct.Mapper;

@Mapper(uses = {TransactionsMapper.class})
public interface AccountReportMapper {

    AccountReportTO toAccountReportTO(AccountReport accountReport);
}
