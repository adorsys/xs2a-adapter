package de.adorsys.xs2a.adapter.service.mapper;

import de.adorsys.xs2a.adapter.service.model.TransactionsReport;
import de.adorsys.xs2a.adapter.service.model.CommerzbankTransactionsReport;
import org.mapstruct.Mapper;

@Mapper
public interface TransactionsReportMapper extends DateTimeMapper {

    TransactionsReport toTransactionsReport(CommerzbankTransactionsReport report);
}
