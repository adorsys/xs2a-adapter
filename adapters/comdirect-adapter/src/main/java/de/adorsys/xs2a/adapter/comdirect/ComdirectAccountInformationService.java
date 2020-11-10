package de.adorsys.xs2a.adapter.comdirect;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.ReadAccountBalanceResponse200;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.comdirect.mapper.BalanceReportMapper;
import de.adorsys.xs2a.adapter.comdirect.mapper.OK200TransactionDetailsMapper;
import de.adorsys.xs2a.adapter.comdirect.mapper.TransactionsResponseMapper;
import de.adorsys.xs2a.adapter.comdirect.model.ComdirectBalanceReport;
import de.adorsys.xs2a.adapter.comdirect.model.ComdirectOK200TransactionDetails;
import de.adorsys.xs2a.adapter.comdirect.model.ComdirectTransactionResponse200Json;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import org.mapstruct.factory.Mappers;

public class ComdirectAccountInformationService extends BaseAccountInformationService {

    private BalanceReportMapper balanceReportMapper = Mappers.getMapper(BalanceReportMapper.class);
    private TransactionsResponseMapper transactionsResponseMapper = Mappers.getMapper(TransactionsResponseMapper.class);
    private OK200TransactionDetailsMapper transactionDetailsMapper = Mappers.getMapper(OK200TransactionDetailsMapper.class);

    public ComdirectAccountInformationService(Aspsp aspsp,
                                              HttpClient httpClient,
                                              LinksRewriter linksRewriter) {
        super(aspsp, httpClient, linksRewriter);
    }

    @Override
    public Response<ReadAccountBalanceResponse200> getBalances(String accountId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        return getBalances(accountId, requestHeaders, requestParams, ComdirectBalanceReport.class,
            balanceReportMapper::toBalanceReport);
    }

    @Override
    public Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {
        return super.getTransactionList(accountId,
                                        requestHeaders,
                                        requestParams,
                                        ComdirectTransactionResponse200Json.class,
                                        transactionsResponseMapper::toTransactionsResponse200Json);
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
                                           ComdirectOK200TransactionDetails.class,
                                           transactionDetailsMapper::toOK200TransactionDetails);
    }
}
