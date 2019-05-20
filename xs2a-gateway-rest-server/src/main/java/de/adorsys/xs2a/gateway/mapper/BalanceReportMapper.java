package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ais.ReadAccountBalanceResponse200TO;
import de.adorsys.xs2a.gateway.service.account.BalanceReport;
import org.mapstruct.Mapper;

@Mapper
public interface BalanceReportMapper {

    ReadAccountBalanceResponse200TO toReadAccountBalanceResponse200TO(BalanceReport balanceReport);
}
