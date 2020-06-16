package de.adorsys.xs2a.adapter.commerzbank.service.mapper;

import de.adorsys.xs2a.adapter.api.model.ReadAccountBalanceResponse200;
import de.adorsys.xs2a.adapter.commerzbank.service.model.CommerzbankBalanceReport;
import org.mapstruct.Mapper;

@Mapper
public interface BalanceReportMapper extends DateTimeMapper {
    ReadAccountBalanceResponse200 toBalanceReport(CommerzbankBalanceReport report);
}
