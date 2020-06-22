package de.adorsys.xs2a.adapter.comdirect.mapper;

import de.adorsys.xs2a.adapter.api.model.ReadAccountBalanceResponse200;
import de.adorsys.xs2a.adapter.comdirect.model.ComdirectBalanceReport;
import org.mapstruct.Mapper;

@Mapper
public interface BalanceReportMapper extends DateTimeMapper {
    ReadAccountBalanceResponse200 toBalanceReport(ComdirectBalanceReport report);
}
