package de.adorsys.xs2a.adapter.service.mapper;

import de.adorsys.xs2a.adapter.service.model.BalanceReport;
import de.adorsys.xs2a.adapter.service.model.ComdirectBalanceReport;
import org.mapstruct.Mapper;

@Mapper
public interface BalanceReportMapper extends DateTimeMapper {
    BalanceReport toBalanceReport(ComdirectBalanceReport report);
}
