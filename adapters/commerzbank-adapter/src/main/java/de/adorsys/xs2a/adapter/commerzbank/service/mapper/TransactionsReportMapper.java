package de.adorsys.xs2a.adapter.commerzbank.service.mapper;

import de.adorsys.xs2a.adapter.commerzbank.service.model.CommerzbankTransactionsReport;
import de.adorsys.xs2a.adapter.service.model.TransactionsReport;
import org.mapstruct.Mapper;

@Mapper
public interface TransactionsReportMapper extends DateTimeMapper {

    TransactionsReport toTransactionsReport(CommerzbankTransactionsReport report);
}
