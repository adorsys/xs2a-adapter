package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.ReadCardAccountBalanceResponse200TO;
import de.adorsys.xs2a.adapter.service.model.CardAccountBalanceReport;
import org.mapstruct.Mapper;

@Mapper
public interface CardAccountBalanceReportMapper {
    ReadCardAccountBalanceResponse200TO map(CardAccountBalanceReport source);

    CardAccountBalanceReport map(ReadCardAccountBalanceResponse200TO source);
}
