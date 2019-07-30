package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.TransactionsResponse200JsonTO;
import de.adorsys.xs2a.adapter.service.account.TransactionsReport;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {AccountReferenceMapper.class, BalanceMapper.class, AccountReportMapper.class})
public interface TransactionsReportMapper {

    @Mapping(source = "accountReference", target = "account")
    TransactionsResponse200JsonTO toTransactionsResponse200Json(TransactionsReport transactionsReport);

    @InheritInverseConfiguration
    TransactionsReport toTransactionsReport(TransactionsResponse200JsonTO to);
}
