package de.adorsys.xs2a.adapter.commerzbank;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.ReadAccountBalanceResponse200;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.commerzbank.mapper.BalanceReportMapper;
import de.adorsys.xs2a.adapter.commerzbank.mapper.OK200TransactionDetailsMapper;
import de.adorsys.xs2a.adapter.commerzbank.mapper.TransactionsReportMapper;
import de.adorsys.xs2a.adapter.commerzbank.model.CommerzbankBalanceReport;
import de.adorsys.xs2a.adapter.commerzbank.model.CommerzbankOK200TransactionDetails;
import de.adorsys.xs2a.adapter.commerzbank.model.CommerzbankTransactionsReport;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import org.mapstruct.factory.Mappers;

public class CommerzbankAccountInformationService extends BaseAccountInformationService {

    private TransactionsReportMapper transactionsReportMapper = Mappers.getMapper(TransactionsReportMapper.class);
    private BalanceReportMapper balanceReportMapper = Mappers.getMapper(BalanceReportMapper.class);
    private final OK200TransactionDetailsMapper transactionDetailsMapper = Mappers.getMapper(OK200TransactionDetailsMapper.class);

    public CommerzbankAccountInformationService(Aspsp aspsp,
                                                HttpClient httpClient,
                                                LinksRewriter linksRewriter) {
        super(aspsp, httpClient, linksRewriter);
    }

    @Override
    public Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {
        return getTransactionList(accountId, requestHeaders, requestParams, CommerzbankTransactionsReport.class,
            transactionsReportMapper::toTransactionsReport);
    }

    @Override
    public Response<ReadAccountBalanceResponse200> getBalances(String accountId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        return getBalances(accountId, requestHeaders, requestParams, CommerzbankBalanceReport.class,
            balanceReportMapper::toBalanceReport);
    }

    @Override
    public Response<OK200TransactionDetails> getTransactionDetails(String accountId,
                                                                   String transactionId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {

        return super.getTransactionDetails(accountId,
                                           transactionId,
                                           requestHeaders,
                                           requestParams,
                                           CommerzbankOK200TransactionDetails.class,
                                           transactionDetailsMapper::toOK200TransactionDetails);
    }
}
