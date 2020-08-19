package de.adorsys.xs2a.adapter.commerzbank.mapper;

import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.commerzbank.model.CommerzbankTransactionsReport;
import org.mapstruct.Mapper;

@Mapper
public interface TransactionsReportMapper extends DateTimeMapper {

    TransactionsResponse200Json toTransactionsReport(CommerzbankTransactionsReport report);
}
