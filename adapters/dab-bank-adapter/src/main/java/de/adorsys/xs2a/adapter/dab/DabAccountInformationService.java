package de.adorsys.xs2a.adapter.dab;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.dab.model.DabOK200TransactionDetails;
import de.adorsys.xs2a.adapter.dab.model.DabTransactionsResponse200Json;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import org.mapstruct.factory.Mappers;

public class DabAccountInformationService extends BaseAccountInformationService {

    private final DabMapper mapper = Mappers.getMapper(DabMapper.class);
    public DabAccountInformationService(Aspsp aspsp, HttpClient httpClient, LinksRewriter linksRewriter) {
        super(aspsp, httpClient, linksRewriter);
    }

    @Override
    public Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {
        return getTransactionList(accountId,
            requestHeaders,
            requestParams,
            DabTransactionsResponse200Json.class,
            mapper::toTransactionsResponse200Json);
    }

    @Override
    public Response<OK200TransactionDetails> getTransactionDetails(String accountId,
                                                                   String transactionId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {
        return getTransactionDetails(accountId,
            transactionId,
            requestHeaders,
            requestParams,
            DabOK200TransactionDetails.class,
            mapper::toOK200TransactionDetails);
    }
}
