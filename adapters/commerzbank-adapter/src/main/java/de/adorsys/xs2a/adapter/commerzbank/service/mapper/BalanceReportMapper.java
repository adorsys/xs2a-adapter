package de.adorsys.xs2a.adapter.commerzbank.service.mapper;

import de.adorsys.xs2a.adapter.commerzbank.service.model.CommerzbankBalanceReport;
import de.adorsys.xs2a.adapter.service.model.BalanceReport;
import org.mapstruct.Mapper;

@Mapper
public interface BalanceReportMapper extends DateTimeMapper {
    BalanceReport toBalanceReport(CommerzbankBalanceReport report);
}
