package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.service.mapper.BalanceReportMapper;
import de.adorsys.xs2a.adapter.service.model.BalanceReport;
import de.adorsys.xs2a.adapter.service.model.ComdirectBalanceReport;
import org.mapstruct.factory.Mappers;

public class ComdirectAccountInformationService extends BaseAccountInformationService {

    private BalanceReportMapper balanceReportMapper = Mappers.getMapper(BalanceReportMapper.class);

    public ComdirectAccountInformationService(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public Response<BalanceReport> getBalances(String accountId, RequestHeaders requestHeaders) {
        return getBalances(accountId, requestHeaders, ComdirectBalanceReport.class,
                           balanceReportMapper::toBalanceReport);
    }
}
