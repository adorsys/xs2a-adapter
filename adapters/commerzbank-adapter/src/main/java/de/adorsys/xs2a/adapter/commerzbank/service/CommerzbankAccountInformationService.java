package de.adorsys.xs2a.adapter.commerzbank.service;

import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.commerzbank.service.mapper.BalanceReportMapper;
import de.adorsys.xs2a.adapter.commerzbank.service.mapper.TransactionsReportMapper;
import de.adorsys.xs2a.adapter.commerzbank.service.model.CommerzbankBalanceReport;
import de.adorsys.xs2a.adapter.commerzbank.service.model.CommerzbankTransactionsReport;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.BalanceReport;
import de.adorsys.xs2a.adapter.service.model.TransactionsReport;
import org.mapstruct.factory.Mappers;

public class CommerzbankAccountInformationService extends BaseAccountInformationService {

    private TransactionsReportMapper transactionsReportMapper = Mappers.getMapper(TransactionsReportMapper.class);
    private BalanceReportMapper balanceReportMapper = Mappers.getMapper(BalanceReportMapper.class);

    public CommerzbankAccountInformationService(Aspsp aspsp, HttpClient httpClient) {
        super(aspsp, httpClient);
    }

    @Override
    public Response<TransactionsReport> getTransactionList(String accountId,
                                                           RequestHeaders requestHeaders,
                                                           RequestParams requestParams) {
        return getTransactionList(accountId, requestHeaders, requestParams, CommerzbankTransactionsReport.class,
            transactionsReportMapper::toTransactionsReport);
    }

    @Override
    public Response<BalanceReport> getBalances(String accountId, RequestHeaders requestHeaders) {
        return getBalances(accountId, requestHeaders, CommerzbankBalanceReport.class,
            balanceReportMapper::toBalanceReport);
    }
}
