package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.ReadAccountBalanceResponse200TO;
import de.adorsys.xs2a.adapter.service.model.BalanceReport;
import org.mapstruct.Mapper;

@Mapper
public interface BalanceReportMapper {

    ReadAccountBalanceResponse200TO toReadAccountBalanceResponse200TO(BalanceReport balanceReport);

    BalanceReport toBalanceReport(ReadAccountBalanceResponse200TO to);
}
